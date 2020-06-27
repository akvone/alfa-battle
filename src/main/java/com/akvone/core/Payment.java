package com.akvone.core;

import lombok.Data;

@Data
public class Payment {

  String ref;
  Integer categoryId;
  String userId;
  String recipientId;
  String desc;
  Double amount;
}
