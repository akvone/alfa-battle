package com.akvone.core;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import lombok.Data;
import lombok.Setter;

@Entity
@Table(name = "branches")
@Data
public class BranchEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  public Integer id;
  private String title;
  private String lon;
  private String lat;
  private String address;

}
