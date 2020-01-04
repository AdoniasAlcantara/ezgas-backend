-- Brands
INSERT INTO Brand(id, name) VALUES
    (2, 'Alesat'),
    (6, 'Branca'),
    (18, 'Ipiranga'),
    (25, 'Petrobrás'),
    (31, 'Raízen');

-- States
INSERT INTO State(code, name) VALUES
    ('BA', 'Bahia');

-- Cities
INSERT INTO City(id, name, stateCode) VALUES
    (2222, 'Itabuna', 'BA');

-- Gas stations
INSERT INTO GasStation(id, company, lastUpdate, brandId, latitude, longitude, address, number, neighborhood, cityId) VALUES
    (2340, 'Roni Comércio de Combustíveis Ltda', '2019-04-08T00:00:00', 6, -14.808355, -39.2960107, 'Rodovia Br 101, Km 507', 's/n', 'Taveirolandia', 2222);

-- Fuels
INSERT INTO Fuel(id, type, price, gasStationId) VALUES
    (1, 'GASOLINE', 4.68, 2340),
    (2, 'ETHANOL', 4.68, 2340),
    (3, 'DIESEL', 4.68, 2340),
    (4, 'DIESEL_S10', 4.68, 2340);


