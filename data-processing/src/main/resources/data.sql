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
    INSERT INTO Country (currency_id, name, abbreviation)
    VALUES (4, 'Belgium', 'BEL'),
           (1, 'Canada', 'CAN'),
           (2, 'Czech Republic','CZE'),
           (3, 'Denmark','DNK'),
           (4, 'Estonia','EST'),
           (4, 'Finland','FIN'),
           (4, 'France','FRA'),
           (4, 'Germany','DEU'),
           (4, 'Greece','GRC'),
           (6, 'Hungary','HUN'),
           (4, 'Ireland','IRL'),
           (4, 'Italy','ITA'),
           (7, 'Japan','JPN'),
           (4, 'Luxembourg','LUX'),
           (4, 'Netherlands','NLD'),
           (8, 'Norway','NOR'),
           (4, 'Portugal','PRT'),
           (4, 'Slovak Republic','SVK'),
           (4, 'Slovenia','SVN'),
           (4, 'Spain','ESP'),
           (9, 'Sweden','SWE'),
           (10, 'Turkey','TUR'),
           (5, 'United Kingdom','GBR'),
           (11, 'United States','USA')
  END;
