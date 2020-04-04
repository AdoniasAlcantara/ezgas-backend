-- Tables

drop table if exists AnpStation;

drop table if exists FuelHistory;

drop table if exists Fuel;

drop table if exists Station;

drop table if exists Brand;

drop table if exists City;

drop table if exists State;

create table State
(
	code char(2) null,
	name varchar(255) not null,
	constraint pkState primary key (code)
);

create table City
(
	id int auto_increment,
	name varchar(255) not null,
	stateCode char(2) not null,
	constraint cityPk primary key (id),
	constraint cityStateFk foreign key (stateCode) references State (code) on update cascade
);

create table Brand
(
	id int auto_increment,
	name varchar(255) not null,
	constraint brandPk primary key (id)
);

create table Station
(
	id int auto_increment,
	company varchar(255) not null,
	latitude double not null,
	longitude double not null,
	number char(16) null,
	address varchar(255) null,
	area varchar(255) null,
	brandId int not null,
	cityId int not null,
	constraint stationPk primary key (id),
	constraint stationBrandFk foreign key (brandId) references Brand (id) on update cascade,
	constraint stationCityFk foreign key (cityId) references City (id) on update cascade
);

create table Fuel
(
	type char(16) not null,
	salePrice decimal(10,2) not null,
	purchasePrice decimal(10,2) null,
	updated datetime not null,
	source varchar(255) not null,
	stationId int not null,
	constraint fuelPk primary key (type, stationId),
	constraint fuelStationFk foreign key (stationId) references Station (id) on update cascade
);

create table FuelHistory
(
	type char(16) not null,
	salePrice decimal(10,2) not null,
	purchasePrice decimal(10,2) null,
	updated datetime not null,
	source varchar(255) not null,
    stationId int not null,
    constraint fuelHistoryPk primary key (stationId),
    constraint fuelHistoryStationFk foreign key (stationId) references Station (id) on update cascade
);

create table AnpStation
(
	anpKey char(41) null,
	stationId int not null,
	constraint anpStationPk primary key (anpKey),
	constraint anpStationStationFk foreign key (stationId) references Station (id) on update cascade
);

-- Functions, triggers..

create or replace function distance(fromLat double, fromLng double, toLat double, toLng double) returns float
begin
    declare result float;
    set result =
        6371392.896 * acos(
            cos(radians(toLat))
            * cos(radians(fromLat))
            * cos(radians(fromLng) - radians(toLng))
            + sin(radians(toLat))
            * sin(radians(fromLat))
        );

    return result;
end;

create trigger onUpdateFuel
    after update on Fuel
    for each row
begin
    insert into FuelHistory(type, salePrice, purchasePrice, updated, source, stationId)
    values (old.type, old.salePrice, old.purchasePrice, old.updated, old.source, old.stationId);
end;