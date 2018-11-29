IF NOT EXISTS(SELECT *
              FROM Currency
              WHERE name IN ('CAD', 'CZK', 'DKK', 'EUR', 'GBP', 'HUF', 'JPY', 'NOK', 'SEK', 'TRY', 'USD'))
  BEGIN
    INSERT INTO Currency (name)
    VALUES ('CAD'),
           ('CZK'),
           ('DKK'),
           ('EUR'),
           ('GBP'),
           ('HUF'),
           ('JPY'),
           ('NOK'),
           ('SEK'),
           ('TRY'),
           ('USD')
  END;

IF NOT EXISTS(SELECT *
              FROM Country
              WHERE name IN
                    ('Belgium', 'Canada', 'Czech Republic', 'Denmark', 'Estonia', 'Finland', 'France', 'Germany',
                     'Greece', 'Hungary', 'Ireland', 'Italy', 'Japan', 'Luxembourg', 'Netherlands', 'Norway',
                     'Portugal', 'Slovak Republic', 'Slovenia', 'Spain', 'Sweden', 'Turkey', 'United Kingdom',
                     'United States'))
  BEGIN
    INSERT INTO Country (currency_id, name)
    VALUES (4, 'Belgium'),
           (1, 'Canada'),
           (2, 'Czech Republic'),
           (3, 'Denmark'),
           (4, 'Estonia'),
           (4, 'Finland'),
           (4, 'France'),
           (4, 'Germany'),
           (4, 'Greece'),
           (6, 'Hungary'),
           (4, 'Ireland'),
           (4, 'Italy'),
           (7, 'Japan'),
           (4, 'Luxembourg'),
           (4, 'Netherlands'),
           (8, 'Norway'),
           (4, 'Portugal'),
           (4, 'Slovak Republic'),
           (4, 'Slovenia'),
           (4, 'Spain'),
           (9, 'Sweden'),
           (10, 'Turkey'),
           (5, 'United Kingdom'),
           (11, 'United States')
  END;
