-- ****************** SqlDBM: Microsoft SQL Server ******************
-- ******************************************************************

-- ************************************** [dbo].[Currency]

CREATE TABLE [dbo].[Currency]
(
 [currency_id] smallint NOT NULL ,
 [name]        text NOT NULL ,

 CONSTRAINT [PK_Currency] PRIMARY KEY CLUSTERED ([currency_id] ASC)
);
GO








-- ************************************** [dbo].[Exchange_Rate]

CREATE TABLE [dbo].[Exchange_Rate]
(
 [exchange_rate_id] datetime NOT NULL ,
 [currency_id]      smallint NOT NULL ,
 [value]            decimal(10,5) NOT NULL ,

 CONSTRAINT [PK_Exchange_Rate] PRIMARY KEY CLUSTERED ([exchange_rate_id] DESC),
 CONSTRAINT [exchange_rates_assignment] FOREIGN KEY ([currency_id])  REFERENCES [dbo].[Currency]([currency_id])
);
GO


CREATE NONCLUSTERED INDEX [FK_Exchange_Rate_Currency] ON [dbo].[Exchange_Rate] 
 (
  [currency_id] ASC
 )

GO







-- ************************************** [dbo].[Country]

CREATE TABLE [dbo].[Country]
(
 [country_id]  smallint NOT NULL ,
 [currency_id] smallint NOT NULL ,
 [name]        text NOT NULL ,

 CONSTRAINT [PK_Country] PRIMARY KEY CLUSTERED ([country_id] ASC),
 CONSTRAINT [currency_assignment] FOREIGN KEY ([currency_id])  REFERENCES [dbo].[Currency]([currency_id])
);
GO


CREATE NONCLUSTERED INDEX [FK_Country_Currency] ON [dbo].[Country] 
 (
  [currency_id] ASC
 )

GO







-- ************************************** [dbo].[Unemployment_Rate]

CREATE TABLE [dbo].[Unemployment_Rate]
(
 [unemployment_rate_id] int NOT NULL ,
 [country_id]           smallint NOT NULL ,
 [date]                 date NOT NULL ,
 [value]                decimal(10,2) NOT NULL ,

 CONSTRAINT [PK_Unemployment_Rate] PRIMARY KEY CLUSTERED ([unemployment_rate_id] ASC),
 CONSTRAINT [unemployment_rate_assignment] FOREIGN KEY ([country_id])  REFERENCES [dbo].[Country]([country_id])
);
GO


CREATE NONCLUSTERED INDEX [FK_Unemployment_Rate_Country] ON [dbo].[Unemployment_Rate] 
 (
  [country_id] ASC
 )

GO







-- ************************************** [dbo].[Real_GDP_Growth_Rate]

CREATE TABLE [dbo].[Real_GDP_Growth_Rate]
(
 [real_gdp_growth_rate_id] int NOT NULL ,
 [country_id]              smallint NOT NULL ,
 [date]                    date NOT NULL ,
 [value]                   decimal(20,6) NOT NULL ,

 CONSTRAINT [PK_Real_GDP_Growth_Rate] PRIMARY KEY CLUSTERED ([real_gdp_growth_rate_id] ASC),
 CONSTRAINT [real_gdp_growth_rate_assignment] FOREIGN KEY ([country_id])  REFERENCES [dbo].[Country]([country_id])
);
GO


CREATE NONCLUSTERED INDEX [FK_Real_GDP_Growth_Rate_Country] ON [dbo].[Real_GDP_Growth_Rate] 
 (
  [country_id] ASC
 )

GO







-- ************************************** [dbo].[Real_GDP]

CREATE TABLE [dbo].[Real_GDP]
(
 [real_gdp_id] int NOT NULL ,
 [country_id]  smallint NOT NULL ,
 [date]        date NOT NULL ,
 [value]       decimal(20,6) NOT NULL ,

 CONSTRAINT [PK_Real_GDP] PRIMARY KEY CLUSTERED ([real_gdp_id] ASC),
 CONSTRAINT [real_gdp_assignment] FOREIGN KEY ([country_id])  REFERENCES [dbo].[Country]([country_id])
);
GO


CREATE NONCLUSTERED INDEX [FK_Real_GDP_Country] ON [dbo].[Real_GDP] 
 (
  [country_id] ASC
 )

GO







-- ************************************** [dbo].[Labor_Cost_Index]

CREATE TABLE [dbo].[Labor_Cost_Index]
(
 [labor_cost_index_id] int NOT NULL ,
 [country_id]          smallint NOT NULL ,
 [date]                date NOT NULL ,
 [value]               decimal(20,5) NOT NULL ,

 CONSTRAINT [PK_Labor_Cost_Index] PRIMARY KEY CLUSTERED ([labor_cost_index_id] ASC),
 CONSTRAINT [labor_cost_index_assignment] FOREIGN KEY ([country_id])  REFERENCES [dbo].[Country]([country_id])
);
GO


CREATE NONCLUSTERED INDEX [FK_Labor_Cost_Index_Country] ON [dbo].[Labor_Cost_Index] 
 (
  [country_id] ASC
 )

GO







-- ************************************** [dbo].[Inflation_Rate]

CREATE TABLE [dbo].[Inflation_Rate]
(
 [inflation_rate_id] int NOT NULL ,
 [country_id]        smallint NOT NULL ,
 [date]              date NOT NULL ,
 [value]             decimal(20,15) NOT NULL ,

 CONSTRAINT [PK_Inflation_Rate] PRIMARY KEY CLUSTERED ([inflation_rate_id] ASC),
 CONSTRAINT [inflation_rate_assignment] FOREIGN KEY ([country_id])  REFERENCES [dbo].[Country]([country_id])
);
GO


CREATE NONCLUSTERED INDEX [FK_Inflation_Rate_Country] ON [dbo].[Inflation_Rate] 
 (
  [country_id] ASC
 )

GO







-- ************************************** [dbo].[Imports_Of_Goods_And_Services]

CREATE TABLE [dbo].[Imports_Of_Goods_And_Services]
(
 [imports_of_goods_and_services_id] int NOT NULL ,
 [country_id]                       smallint NOT NULL ,
 [date]                             date NOT NULL ,
 [value]                            decimal(20,13) NOT NULL ,

 CONSTRAINT [PK_Imports_Of_Goods_And_Services] PRIMARY KEY CLUSTERED ([imports_of_goods_and_services_id] ASC),
 CONSTRAINT [imports_of_goods_and_services_assignment] FOREIGN KEY ([country_id])  REFERENCES [dbo].[Country]([country_id])
);
GO


CREATE NONCLUSTERED INDEX [FK_Imports_Of_Goods_And_Services_Country] ON [dbo].[Imports_Of_Goods_And_Services] 
 (
  [country_id] ASC
 )

GO







-- ************************************** [dbo].[Exports_Of_Goods_And_Services]

CREATE TABLE [dbo].[Exports_Of_Goods_And_Services]
(
 [exports_of_goods_and_services_id] int NOT NULL ,
 [country_id]                       smallint NOT NULL ,
 [date]                             date NOT NULL ,
 [value]                            decimal(20,13) NOT NULL ,

 CONSTRAINT [PK_Exports_Of_Goods_And_Services] PRIMARY KEY CLUSTERED ([exports_of_goods_and_services_id] ASC),
 CONSTRAINT [exports_of_goods_and_services_assignment] FOREIGN KEY ([country_id])  REFERENCES [dbo].[Country]([country_id])
);
GO


CREATE NONCLUSTERED INDEX [FK_Exports_Of_Goods_And_Services_Country] ON [dbo].[Exports_Of_Goods_And_Services] 
 (
  [country_id] ASC
 )

GO







-- ************************************** [dbo].[Business_Registration_Procedures]

CREATE TABLE [dbo].[Business_Registration_Procedures]
(
 [business_registration_procedures_id] int NOT NULL ,
 [country_id]                          smallint NOT NULL ,
 [date]                                date NOT NULL ,
 [value]                               decimal(18,14) NOT NULL ,

 CONSTRAINT [PK_Business_Registration_Procedures] PRIMARY KEY CLUSTERED ([business_registration_procedures_id] ASC),
 CONSTRAINT [business_registration_procedures_assignment] FOREIGN KEY ([country_id])  REFERENCES [dbo].[Country]([country_id])
);
GO


CREATE NONCLUSTERED INDEX [FK_Business_Registration_Procedures_Country] ON [dbo].[Business_Registration_Procedures] 
 (
  [country_id] ASC
 )

GO






