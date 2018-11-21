IF OBJECT_ID ('Exchange_Rates', 'U') IS NOT NULL
  DROP TABLE Exchange_Rates;
CREATE TABLE Exchange_Rates (
  LocalDateTime  DATETIME NOT NULL ,
  TRY DECIMAL(10,4) ,
  PRIMARY KEY (LocalDateTime)
);
