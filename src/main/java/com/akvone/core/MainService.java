package com.akvone.core;

import java.sql.Timestamp;
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

  public double predict(int id, int dayOfWeek, int hourOfDay) {
    var sqlTemplate =
        "SELECT EXTRACT(EPOCH  from (end_time_of_wait - start_time_of_wait)) as wait_time\n"
            + "FROM  (SELECT *, date_part('dow', data) + 1 as dow FROM queue_log WHERE branches_id = %d "
//            + "AND start_time_of_wait  >= '%d:00:00' "
            + "AND end_time_of_wait < '%d:00:00')\n"
            + "    as sub\n"
            + "WHERE dow = %d\n"
            + "ORDER BY wait_time";
    var sql = String.format(sqlTemplate, id, hourOfDay + 1 , dayOfWeek);
    double[] waitTimes = jdbcTemplate.queryForList(sql, Integer.class).stream()
        .mapToDouble(value -> value)
        .toArray();

    var result = new Median().evaluate(waitTimes);

    return result;
  }

  @Data
  @AllArgsConstructor
  public static class PartLog{
    private Integer id;
    private Timestamp startTimeOfWait;
    private Timestamp endTimeOfWait;
  }
}
