-- Brands
INSERT INTO Brand(id, name) VALUES
    (2, 'Alesat'),
    (6, 'Branca'),
    (18, 'Ipiranga'),
    (25, 'Petrobrás'),
    (31, 'Raízen')$$

-- States
INSERT INTO State(code, name) VALUES
    ('BA', 'Bahia')$$

-- Cities
INSERT INTO City(id, name, stateCode) VALUES
    (2222, 'Itabuna', 'BA')$$

-- Gas stations
INSERT INTO Station(id, company, brandId, latitude, longitude, address, number, area, cityId) VALUES
    (2340, 'Roni Comércio de Combustíveis Ltda', 6, -14.808355, -39.2960107, 'Rodovia Br 101, Km 507', 's/n', 'Taveirolandia', 2222),
    (2341, 'Ferradas Comércio e Derivados de Petróleo Ltda', 25, -14.8503667, -39.3387818, null, 's/n', 'Ferradas', 2222),
    (2342, 'Comercial de Derivados de Petróleo da Hora Ltda Epp', 6, -14.7905134, -39.2781267, 'Avenida Inácio Tosta Filho', '508', 'Centro', 2222),
    (2343, 'Fama Comércio de Combustíveis Eireli', 6, -14.7914557, -39.2803586, 'Avenida Amélia Amado', '987', 'Centro', 2222),
    (2344, 'Lopes Lemos Comércio de Combustíveis Ltda', 6, -14.8035111, -39.2935516, 'BR-101', 's/n', 'Manuel Leal', 2222),
    (2345, 'Derivados de Petróleo Teuna Ltda', 18, -14.7918712, -39.2807482, 'Avenida Inácio Tosta Filho', '834', 'Centro', 2222),
    (2346, 'Peixoto Produtos de Petróleo Ltda', 31, -14.7816881, -39.2680318, 'Avenida Juracy Magalhães', '321a', 'Nossa Senhora de Fátima', 2222),
    (2347, 'Apg Comércio de Combustíveis Ltda', 2, -14.7914527, -39.2803445, 'Avenida Inácio Tosta Filho', '923', 'Centro', 2222),
    (2349, 'Posto Universal Comércio de Combustíveis Ltda', 25, -14.7836127, -39.2682028, 'Rua Maria Olívia Rebouças', 's/n', null, 2222)$$

-- Fuels
INSERT INTO Fuel(stationId, type, updated, price) VALUES
    (2340, 'GASOLINE', '2019-04-08T00:00:00', 4.65),
    (2340, 'ETHANOL', '2019-04-08T00:00:00', 4.68),
    (2340, 'DIESEL', '2019-04-08T00:00:00', 4.68),
    (2340, 'DIESEL_S10', '2019-04-08T00:00:00', 4.68),
    (2341, 'GASOLINE', '2019-04-08T00:00:00', 4.68),
    (2341, 'ETHANOL', '2019-04-08T00:00:00', 2.68),
    (2341, 'DIESEL', '2019-04-08T00:00:00', 4.68),
    (2341, 'DIESEL_S10', '2019-04-08T00:00:00', 4.68),
    (2343, 'GASOLINE', '2019-04-08T00:00:00', 4.68),
    (2343, 'ETHANOL', '2019-04-08T00:00:00', 4.68),
    (2343, 'DIESEL', '2019-04-08T00:00:00', 4.68),
    (2343, 'DIESEL_S10', '2019-04-08T00:00:00', 4.68),
    (2344, 'GASOLINE', '2019-04-08T00:00:00', 4.68),
    (2344, 'ETHANOL', '2019-04-08T00:00:00', 4.68),
    (2344, 'DIESEL', '2019-04-08T00:00:00', 4.68),
    (2344, 'DIESEL_S10', '2019-04-08T00:00:00', 4.68),
    (2345, 'GASOLINE', '2019-04-08T00:00:00', 4.68),
    (2345, 'ETHANOL', '2019-04-08T00:00:00', 4.68),
    (2345, 'DIESEL', '2019-04-08T00:00:00', 4.68),
    (2345, 'DIESEL_S10', '2019-04-08T00:00:00', 4.68),
    (2346, 'GASOLINE', '2019-04-08T00:00:00', 4.68),
    (2346, 'ETHANOL', '2019-04-08T00:00:00', 4.68),
    (2346, 'DIESEL', '2019-04-08T00:00:00', 4.68),
    (2346, 'DIESEL_S10', '2019-04-08T00:00:00', 4.68),
    (2347, 'ETHANOL', '2019-04-08T00:00:00', 4.68),
    (2347, 'DIESEL', '2019-04-08T00:00:00', 4.68),
    (2347, 'DIESEL_S10', '2019-04-08T00:00:00', 4.68),
    (2349, 'GASOLINE', '2019-04-08T00:00:00', 4.68),
    (2349, 'ETHANOL', '2019-04-08T00:00:00', 4.68),
    (2349, 'DIESEL_S10', '2019-04-08T00:00:00', 4.68)$$