-- ****************** SqlDBM: Microsoft SQL Server ******************
-- ******************************************************************

-- ************************************** [dbo].[Currency]
IF OBJECT_ID('Currency', 'U') IS NULL
CREATE TABLE [dbo].[Currency]
(
  [currency_id] INT      NOT NULL IDENTITY (1,1),
  [name]        CHAR(10) NOT NULL UNIQUE,

  CONSTRAINT [PK_Currency] PRIMARY KEY CLUSTERED ([currency_id] ASC)
);

-- ************************************** [dbo].[Exchange_Rate]
IF OBJECT_ID('Exchange_Rate', 'U') IS NULL
  BEGIN
    CREATE TABLE [dbo].[Exchange_Rate]
    (
      [exchange_rate_id] INT            NOT NULL IDENTITY (1,1),
      [date]             DATETIME       NOT NULL,
      [currency_id]      INT            NOT NULL,
      [value]            DECIMAL(10, 5) NOT NULL,

      CONSTRAINT [PK_Exchange_Rate] PRIMARY KEY CLUSTERED ([exchange_rate_id] DESC),
      CONSTRAINT [exchange_rates_assignment] FOREIGN KEY ([currency_id]) REFERENCES [dbo].[Currency] ([currency_id])
    )
    CREATE NONCLUSTERED INDEX [FK_Exchange_Rate_Currency] ON [dbo].[Exchange_Rate] ([currency_id] ASC)
  END;

-- ************************************** [dbo].[Country]
IF OBJECT_ID('Country', 'U') IS NULL
  BEGIN
    CREATE TABLE [dbo].[Country]
    (
      [country_id]   INT         NOT NULL IDENTITY (1,1),
      [currency_id]  INT         NOT NULL,
      [name]         VARCHAR(50) NOT NULL UNIQUE,
      [abbreviation] CHAR(10)    NOT NULL UNIQUE,

      CONSTRAINT [PK_Country] PRIMARY KEY CLUSTERED ([country_id] ASC),
      CONSTRAINT [currency_assignment] FOREIGN KEY ([currency_id]) REFERENCES [dbo].[Currency] ([currency_id])
    )
    CREATE NONCLUSTERED INDEX [FK_Country_Currency] ON [dbo].[Country] ([currency_id] ASC)
  END;

-- ************************************** [dbo].[Unemployment_Rate]
IF OBJECT_ID('Unemployment_Rate', 'U') IS NULL
  BEGIN
    CREATE TABLE [dbo].[Unemployment_Rate]
    (
      [unemployment_rate_id] INT            NOT NULL IDENTITY (1,1),
      [country_id]           INT            NOT NULL,
      [date]                 DATE           NOT NULL,
      [value]                DECIMAL(10, 2) NOT NULL,

      CONSTRAINT [PK_Unemployment_Rate] PRIMARY KEY CLUSTERED ([unemployment_rate_id] ASC),
      CONSTRAINT [unemployment_rate_assignment] FOREIGN KEY ([country_id]) REFERENCES [dbo].[Country] ([country_id])
    )
    CREATE NONCLUSTERED INDEX [FK_Unemployment_Rate_Country] ON [dbo].[Unemployment_Rate] ([country_id] ASC)
  END;

-- ************************************** [dbo].[Real_GDP_Growth_Rate]
IF OBJECT_ID('Real_GDP_Growth_Rate', 'U') IS NULL
  BEGIN
    CREATE TABLE [dbo].[Real_GDP_Growth_Rate]
    (
      [real_gdp_growth_rate_id] INT            NOT NULL IDENTITY (1,1),
      [country_id]              INT            NOT NULL,
      [date]                    DATE           NOT NULL,
      [value]                   DECIMAL(20, 6) NOT NULL,

      CONSTRAINT [PK_Real_GDP_Growth_Rate] PRIMARY KEY CLUSTERED ([real_gdp_growth_rate_id] ASC),
      CONSTRAINT [real_gdp_growth_rate_assignment] FOREIGN KEY ([country_id]) REFERENCES [dbo].[Country] ([country_id])
    )
    CREATE NONCLUSTERED INDEX [FK_Real_GDP_Growth_Rate_Country] ON [dbo].[Real_GDP_Growth_Rate] ([country_id] ASC)
  END;

-- ************************************** [dbo].[Real_GDP]
IF OBJECT_ID('Real_GDP', 'U') IS NULL
  BEGIN
    CREATE TABLE [dbo].[Real_GDP]
    (
      [real_gdp_id] INT            NOT NULL IDENTITY (1,1),
      [country_id]  INT            NOT NULL,
      [date]        DATE           NOT NULL,
      [value]       DECIMAL(20, 6) NOT NULL,

      CONSTRAINT [PK_Real_GDP] PRIMARY KEY CLUSTERED ([real_gdp_id] ASC),
      CONSTRAINT [real_gdp_assignment] FOREIGN KEY ([country_id]) REFERENCES [dbo].[Country] ([country_id])
    )
    CREATE NONCLUSTERED INDEX [FK_Real_GDP_Country] ON [dbo].[Real_GDP] ([country_id] ASC)
  END;

-- ************************************** [dbo].[Labor_Cost_Index]
IF OBJECT_ID('Labor_Cost_Index', 'U') IS NULL
  BEGIN
    CREATE TABLE [dbo].[Labor_Cost_Index]
    (
      [labor_cost_index_id] INT            NOT NULL IDENTITY (1,1),
      [country_id]          INT            NOT NULL,
      [date]                DATE           NOT NULL,
      [value]               DECIMAL(20, 5) NOT NULL,

      CONSTRAINT [PK_Labor_Cost_Index] PRIMARY KEY CLUSTERED ([labor_cost_index_id] ASC),
      CONSTRAINT [labor_cost_index_assignment] FOREIGN KEY ([country_id]) REFERENCES [dbo].[Country] ([country_id])
    )
    CREATE NONCLUSTERED INDEX [FK_Labor_Cost_Index_Country] ON [dbo].[Labor_Cost_Index] ([country_id] ASC)
  END;

-- ************************************** [dbo].[Inflation_Rate]
IF OBJECT_ID('Inflation_Rate', 'U') IS NULL
  BEGIN
    CREATE TABLE [dbo].[Inflation_Rate]
    (
      [inflation_rate_id] INT             NOT NULL IDENTITY (1,1),
      [country_id]        INT             NOT NULL,
      [date]              DATE            NOT NULL,
      [value]             DECIMAL(20, 15) NOT NULL,

      CONSTRAINT [PK_Inflation_Rate] PRIMARY KEY CLUSTERED ([inflation_rate_id] ASC),
      CONSTRAINT [inflation_rate_assignment] FOREIGN KEY ([country_id]) REFERENCES [dbo].[Country] ([country_id])
    )
    CREATE NONCLUSTERED INDEX [FK_Inflation_Rate_Country] ON [dbo].[Inflation_Rate] ([country_id] ASC)
  END;

-- ************************************** [dbo].[Imports_Of_Goods_And_Services]
IF OBJECT_ID('Imports_Of_Goods_And_Services', 'U') IS NULL
  BEGIN
    CREATE TABLE [dbo].[Imports_Of_Goods_And_Services]
    (
      [imports_of_goods_and_services_id] INT             NOT NULL IDENTITY (1,1),
      [country_id]                       INT             NOT NULL,
      [date]                             DATE            NOT NULL,
      [value]                            DECIMAL(20, 13) NOT NULL,

      CONSTRAINT [PK_Imports_Of_Goods_And_Services] PRIMARY KEY CLUSTERED ([imports_of_goods_and_services_id] ASC),
      CONSTRAINT [imports_of_goods_and_services_assignment] FOREIGN KEY ([country_id]) REFERENCES [dbo].[Country] ([country_id])
    )
    CREATE NONCLUSTERED INDEX [FK_Imports_Of_Goods_And_Services_Country] ON [dbo].[Imports_Of_Goods_And_Services]
      (
       [country_id] ASC
        )
  END;

-- ************************************** [dbo].[Exports_Of_Goods_And_Services]
IF OBJECT_ID('Exports_Of_Goods_And_Services', 'U') IS NULL
  BEGIN
    CREATE TABLE [dbo].[Exports_Of_Goods_And_Services]
    (
      [exports_of_goods_and_services_id] INT             NOT NULL IDENTITY (1,1),
      [country_id]                       INT             NOT NULL,
      [date]                             DATE            NOT NULL,
      [value]                            DECIMAL(20, 13) NOT NULL,

      CONSTRAINT [PK_Exports_Of_Goods_And_Services] PRIMARY KEY CLUSTERED ([exports_of_goods_and_services_id] ASC),
      CONSTRAINT [exports_of_goods_and_services_assignment] FOREIGN KEY ([country_id]) REFERENCES [dbo].[Country] ([country_id])
    )
    CREATE NONCLUSTERED INDEX [FK_Exports_Of_Goods_And_Services_Country] ON [dbo].[Exports_Of_Goods_And_Services]
      (
       [country_id] ASC
        )
  END;

-- ************************************** [dbo].[Business_Registration_Procedures]
IF OBJECT_ID('Business_Registration_Procedures', 'U') IS NULL
  BEGIN
    CREATE TABLE [dbo].[Business_Registration_Procedures]
    (
      [business_registration_procedures_id] INT            NOT NULL IDENTITY (1,1),
      [country_id]                          INT            NOT NULL,
      [date]                                DATE           NOT NULL,
      [value]                               DECIMAL(10, 2) NOT NULL,

      CONSTRAINT [PK_Business_Registration_Procedures] PRIMARY KEY CLUSTERED ([business_registration_procedures_id] ASC),
      CONSTRAINT [business_registration_procedures_assignment] FOREIGN KEY ([country_id]) REFERENCES [dbo].[Country] ([country_id])
    )
    CREATE NONCLUSTERED INDEX [FK_Business_Registration_Procedures_Country] ON [dbo].[Business_Registration_Procedures]
      (
       [country_id] ASC
        )
  END;


-- ************************************** [dbo].[Merged_Table]
IF OBJECT_ID('Merged_Table', 'U') IS NULL
  BEGIN
    CREATE TABLE Merged_Table
    (
      [country]                          VARCHAR(50),
      [currency]                         CHAR(10),
      [date]                             DATETIME,
      [business_registration_procedures] DECIMAL(10, 2),
      [exports]                          DECIMAL(20, 13),
      [imports]                          DECIMAL(20, 13),
      [inflation_rate]                   DECIMAL(20, 15),
      [labor_cost_index]                 DECIMAL(20, 6),
      [gdp]                              DECIMAL(20, 6),
      [gdp_rate]                         DECIMAL(20, 6),
      [unemployment_rate]                DECIMAL(10, 2),
      [exchange_rate]                    DECIMAL(10, 5)
    )
  END;


-- ************************************** Stored Procedures
CREATE OR ALTER PROCEDURE sp_add_business_registration_procedures AS
BEGIN
  SET NOCOUNT ON
  INSERT INTO Merged_Table(country, date, business_registration_procedures)
  SELECT c.name, b.date, b.value
  FROM Country c
         LEFT OUTER JOIN Business_Registration_Procedures b ON c.country_id = b.country_id
  WHERE NOT EXISTS(SELECT country, date, business_registration_procedures
                   FROM Merged_Table m
                   WHERE m.country = c.name
                     AND m.date = b.date
                     AND m.business_registration_procedures = b.value)
END;

  CREATE OR ALTER PROCEDURE sp_add_exports AS
  BEGIN
    SET NOCOUNT ON
    INSERT INTO Merged_Table(country, date, exports)
    SELECT c.name, e.date, e.value
    FROM Country c
           LEFT OUTER JOIN Exports_Of_Goods_And_Services e ON c.country_id = e.country_id
    WHERE NOT EXISTS(SELECT country, date, exports
                     FROM Merged_Table m
                     WHERE m.country = c.name
                       AND m.date = e.date
                       AND m.exports = e.value)
  END;

    CREATE OR ALTER PROCEDURE sp_add_imports AS
    BEGIN
      SET NOCOUNT ON
      INSERT INTO Merged_Table(country, date, imports)
      SELECT c.name, i.date, i.value
      FROM Country c
             LEFT OUTER JOIN Imports_Of_Goods_And_Services i ON c.country_id = i.country_id
      WHERE NOT EXISTS(SELECT country, date, imports
                       FROM Merged_Table m
                       WHERE m.country = c.name
                         AND m.date = i.date
                         AND m.imports = i.value)
    END;

      CREATE OR ALTER PROCEDURE sp_add_inflation_rate AS
      BEGIN
        SET NOCOUNT ON
        INSERT INTO Merged_Table(country, date, inflation_rate)
        SELECT c.name, i.date, i.value
        FROM Country c
               LEFT OUTER JOIN Inflation_Rate i ON c.country_id = i.country_id
        WHERE NOT EXISTS(SELECT country, date, inflation_rate
                         FROM Merged_Table m
                         WHERE m.country = c.name
                           AND m.date = i.date
                           AND m.inflation_rate = i.value)
      END;

        CREATE OR ALTER PROCEDURE sp_add_labor_cost_index AS
        BEGIN
          SET NOCOUNT ON
          INSERT INTO Merged_Table(country, date, labor_cost_index)
          SELECT c.name, l.date, l.value
          FROM Country c
                 LEFT OUTER JOIN Labor_Cost_Index l ON c.country_id = l.country_id
          WHERE NOT EXISTS(SELECT country, date, labor_cost_index
                           FROM Merged_Table m
                           WHERE m.country = c.name
                             AND m.date = l.date
                             AND m.labor_cost_index = l.value)
        END;

          CREATE OR ALTER PROCEDURE sp_add_real_gdp_rate AS
          BEGIN
            SET NOCOUNT ON
            INSERT INTO Merged_Table(country, date, gdp_rate)
            SELECT c.name, g.date, g.value
            FROM Country c
                   LEFT OUTER JOIN Real_GDP_Growth_Rate g ON c.country_id = g.country_id
            WHERE NOT EXISTS(SELECT country, date, gdp_rate
                             FROM Merged_Table m
                             WHERE m.country = c.name
                               AND m.date = g.date
                               AND m.gdp_rate = g.value)
          END;

            CREATE OR ALTER PROCEDURE sp_add_real_gdp AS
            BEGIN
              SET NOCOUNT ON
              INSERT INTO Merged_Table(country, date, gdp)
              SELECT c.name, g.date, g.value
              FROM Country c
                     LEFT OUTER JOIN Real_GDP g ON c.country_id = g.country_id
              WHERE NOT EXISTS(SELECT country, date, gdp
                               FROM Merged_Table m
                               WHERE m.country = c.name
                                 AND m.date = g.date
                                 AND m.gdp = g.value)
            END;

              CREATE OR ALTER PROCEDURE sp_add_unemployment_rate AS
              BEGIN
                SET NOCOUNT ON
                INSERT INTO Merged_Table(country, date, unemployment_rate)
                SELECT c.name, u.date, u.value
                FROM Country c
                       LEFT OUTER JOIN Unemployment_Rate u ON c.country_id = u.country_id
                WHERE NOT EXISTS(SELECT country, date, unemployment_rate
                                 FROM Merged_Table m
                                 WHERE m.country = c.name
                                   AND m.date = u.date
                                   AND m.unemployment_rate = u.value)
              END;

                CREATE OR ALTER PROCEDURE sp_add_exchange_rate AS
                BEGIN
                  SET NOCOUNT ON
                  INSERT INTO Merged_Table(country, date, currency, exchange_rate)
                  SELECT c.name, e.date, r.name, e.value
                  FROM (Country c
                    LEFT OUTER JOIN Currency r ON c.currency_id = r.currency_id)
                         LEFT OUTER JOIN Exchange_Rate e ON c.currency_id = e.currency_id
                  WHERE NOT EXISTS(SELECT country, date, exchange_rate
                                   FROM Merged_Table m
                                   WHERE m.country = c.name
                                     AND m.date = e.date
                                     AND m.exchange_rate = e.value)
                END;