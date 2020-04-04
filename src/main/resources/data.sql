start transaction;

-- States
insert into State(code, name) values('AC', 'Acre');
insert into State(code, name) values('AL', 'Alagoas');
insert into State(code, name) values('AP', 'Amapá');
insert into State(code, name) values('AM', 'Amazonas');
insert into State(code, name) values('BA', 'Bahia');
insert into State(code, name) values('CE', 'Ceará');
insert into State(code, name) values('ES', 'Espírito Santo');
insert into State(code, name) values('GO', 'Goiás');
insert into State(code, name) values('MA', 'Maranhão');
insert into State(code, name) values('MT', 'Mato Grosso');
insert into State(code, name) values('MS', 'Mato Grosso do Sul');
insert into State(code, name) values('MG', 'Minas Gerais');
insert into State(code, name) values('PA', 'Pará');
insert into State(code, name) values('PB', 'Paraíba');
insert into State(code, name) values('PR', 'Paraná');
insert into State(code, name) values('PE', 'Pernambuco');
insert into State(code, name) values('PI', 'Piauí');
insert into State(code, name) values('RJ', 'Rio de Janeiro');
insert into State(code, name) values('RN', 'Rio Grande do Norte');
insert into State(code, name) values('RS', 'Rio Grande do Sul');
insert into State(code, name) values('RO', 'Rondônia');
insert into State(code, name) values('RR', 'Roraima');
insert into State(code, name) values('SC', 'Santa Catarina');
insert into State(code, name) values('SP', 'São Paulo');
insert into State(code, name) values('SE', 'Sergipe');
insert into State(code, name) values('TO', 'Tocantins');

-- Cities
insert into City(id, name, stateCode) values(2222, 'Itabuna', 'BA');

-- Brands
insert into Brand(id, name) values(2, 'Alesat');
insert into Brand(id, name) values(6, 'Branca');
insert into Brand(id, name) values(18, 'Ipiranga');
insert into Brand(id, name) values(25, 'Petrobrás');
insert into Brand(id, name) values(31, 'Raízen');

-- Stations
insert into Station(id, company, brandId, latitude, longitude, address, number, area, cityId) values(2340, 'Roni Comércio de Combustíveis Ltda', 6, -14.808355, -39.2960107, 'Rodovia Br 101, Km 507', 's/n', 'Taveirolandia', 2222);
insert into Station(id, company, brandId, latitude, longitude, address, number, area, cityId) values(2341, 'Ferradas Comércio e Derivados de Petróleo Ltda', 25, -14.8503667, -39.3387818, null, 's/n', 'Ferradas', 2222);
insert into Station(id, company, brandId, latitude, longitude, address, number, area, cityId) values(2342, 'Comercial de Derivados de Petróleo da Hora Ltda Epp', 6, -14.7905134, -39.2781267, 'Avenida Inácio Tosta Filho', '508', 'Centro', 2222);
insert into Station(id, company, brandId, latitude, longitude, address, number, area, cityId) values(2343, 'Fama Comércio de Combustíveis Eireli', 6, -14.7914557, -39.2803586, 'Avenida Amélia Amado', '987', 'Centro', 2222);
insert into Station(id, company, brandId, latitude, longitude, address, number, area, cityId) values(2344, 'Lopes Lemos Comércio de Combustíveis Ltda', 6, -14.8035111, -39.2935516, 'BR-101', 's/n', 'Manuel Leal', 2222);
insert into Station(id, company, brandId, latitude, longitude, address, number, area, cityId) values(2345, 'Derivados de Petróleo Teuna Ltda', 18, -14.7918712, -39.2807482, 'Avenida Inácio Tosta Filho', '834', 'Centro', 2222);
insert into Station(id, company, brandId, latitude, longitude, address, number, area, cityId) values(2346, 'Peixoto Produtos de Petróleo Ltda', 31, -14.7816881, -39.2680318, 'Avenida Juracy Magalhães', '321a', 'Nossa Senhora de Fátima', 2222);
insert into Station(id, company, brandId, latitude, longitude, address, number, area, cityId) values(2347, 'Apg Comércio de Combustíveis Ltda', 2, -14.7914527, -39.2803445, 'Avenida Inácio Tosta Filho', '923', 'Centro', 2222);
insert into Station(id, company, brandId, latitude, longitude, address, number, area, cityId) values(2349, 'Posto Universal Comércio de Combustíveis Ltda', 25, -14.7836127, -39.2682028, 'Rua Maria Olívia Rebouças', 's/n', null, 2222);

-- Fuels
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('GASOLINE', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2340);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('ETHANOL', 3.45, 3.00, '2019-04-08T00:00:00', 'ANP', 2340);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('DIESEL', 3.43, 3.00, '2019-04-08T00:00:00', 'ANP', 2340);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('DIESEL_S10', 3.53, 3.00, '2019-04-08T00:00:00', 'ANP', 2340);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('GASOLINE', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2341);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('ETHANOL', 2.68, 2.00, '2019-04-08T00:00:00', 'ANP', 2341);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('DIESEL', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2341);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('DIESEL_S10', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2341);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('GASOLINE', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2343);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('ETHANOL', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2343);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('DIESEL', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2343);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('DIESEL_S10', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2343);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('GASOLINE', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2344);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('ETHANOL', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2344);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('DIESEL', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2344);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('DIESEL_S10', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2344);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('GASOLINE', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2345);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('ETHANOL', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2345);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('DIESEL', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2345);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('DIESEL_S10', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2345);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('GASOLINE', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2346);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('ETHANOL', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2346);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('DIESEL', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2346);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('DIESEL_S10', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2346);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('ETHANOL', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP' ,2347);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('DIESEL', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2347);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('DIESEL_S10', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2347);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('GASOLINE', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2349);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('ETHANOL', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2349);
insert into Fuel(type, salePrice, purchasePrice, updated, source, stationId) values('DIESEL_S10', 4.68, 4.00, '2019-04-08T00:00:00', 'ANP', 2349);

-- Anp stations
insert into AnpStation(anpKey, stationId) values('89d62ae8d7d71aad1f177b8cbcfe059d41beb8c6', 2340);
insert into AnpStation(anpKey, stationId) values('3ff221ae260be560ef090e0955cf92e2b92dc552', 2341);
insert into AnpStation(anpKey, stationId) values('23d913a9310e00186b82022810394159e44537ab', 2342);
insert into AnpStation(anpKey, stationId) values('f807b4c0e98cda80c18763c9bccf9e8e8e089494', 2343);
insert into AnpStation(anpKey, stationId) values('95acecdeed1abc12faa9e3c94dae66a3f4ed9322', 2344);
insert into AnpStation(anpKey, stationId) values('da6fdf56c25f40c73767d2dea72b7e8e19ab9e3b', 2345);
insert into AnpStation(anpKey, stationId) values('068e6e8fc5b6ba0bb3fd798e10a2c5d5606f5a34', 2346);
insert into AnpStation(anpKey, stationId) values('4dea39c3a222599c58eca38fcfcc50ad28b92649', 2347);
insert into AnpStation(anpKey, stationId) values('420d0eef42ee9a2e027959adaa0deed5fed916e3', 2349);

commit;
