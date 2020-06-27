package com.akvone.core;

import java.sql.Timestamp;
import java.util.Comparator;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.apache.commons.math3.stat.descriptive.rank.Median;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MainService {

  private final JdbcTemplate jdbcTemplate;
  private final BranchRepository branchRepository;

  BranchDTO getBranchDTO(Integer id) {
    var entity = branchRepository.getOne(id);
    return new BranchDTO(entity.getId(), entity.getTitle(), entity.getLon(), entity.getLat(), entity.getAddress());

  }

  public BranchWithPredictDTO predict(int id, int dayOfWeek, int hourOfDay) {
    var branchDTO = getBranchDTO(id);

    var sqlTemplate =
        "SELECT EXTRACT(EPOCH  from (end_time_of_wait - start_time_of_wait)) as wait_time\n"
            + "FROM  (SELECT *, date_part('dow', data) as dow FROM queue_log WHERE branches_id = %d "
            + "AND end_time_of_wait  >= '%d:00:00' "
            + "AND end_time_of_wait < '%d:00:00')\n"
            + "    as sub\n"
            + "WHERE dow = %d\n"
            + "ORDER BY wait_time";
    var countedDOW = dayOfWeek == 7 ? 0 : dayOfWeek;
    var sql = String.format(sqlTemplate, id, hourOfDay, hourOfDay + 1, countedDOW);
    double[] waitTimes = jdbcTemplate.queryForList(sql, Integer.class).stream()
        .mapToDouble(value -> value)
        .toArray();

    int predicting = (int) Math.round(new Median().evaluate(waitTimes));

    return new BranchWithPredictDTO(
        branchDTO.getId(),
        branchDTO.getTitle(),
        branchDTO.getLon(),
        branchDTO.getLat(),
        branchDTO.getAddress(),
        dayOfWeek,
        hourOfDay,
        predicting
    );
  }


  BranchWithDistanceDTO getBranchWithDistanceDTO(Double lat, Double lon) {
    var list = branchRepository.findAll().stream()
        .map(value -> MathUtils.countDistance(lat, value.getLat(), lon, value.getLon())).collect(
            Collectors.toList());

    var entity = branchRepository.findAll().stream()
        .min(Comparator.comparingDouble(value -> MathUtils.countDistance(lat, value.getLat(), lon, value.getLon())))
        .get();
    Integer minDistance = MathUtils.countDistance(lat, entity.getLat(), lon, entity.getLon()).intValue();

    return new BranchWithDistanceDTO(entity.getId(), entity.getTitle(), entity.getLon(),
        entity.getLat(), entity.getAddress(), minDistance);
  }

  @Data
  @AllArgsConstructor
  public static class PartLog {

    private Integer id;
    private Timestamp startTimeOfWait;
    private Timestamp endTimeOfWait;
  }
}
