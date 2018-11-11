IF OBJECT_ID ('Exchange_Rates', 'U') IS NULL
  CREATE TABLE Exchange_Rates (
    LocalDateTime  DATETIME NOT NULL ,
    TRY DECIMAL(10,2) NOT NULL ,
    PRIMARY KEY (LocalDateTime)
  );


