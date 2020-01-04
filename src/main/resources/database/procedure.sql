DELIMITER ;;
CREATE PROCEDURE nearbyGasStations(IN lat DOUBLE, IN lng DOUBLE, IN range DOUBLE)
BEGIN
    SELECT id AS locationId, (
        6371 * acos(cos(radians(lat))
        * cos(radians(latitude))
        * cos(radians(longitude) - radians(lng))
        + sin (radians(lat))
        * sin(radians(latitude)))
    ) AS distance FROM Address
    WHERE distance <= range ORDER BY distance
END ;;
DELIMITER ;

fun dummyData() = listOf(
            GasStationDto(
                    2340, "Roni Comércio de Combustíveis Ltda",
                    "2019-04-08T00:00:00",
                    BrandDto(6, "Branca"),
                    AddressDto(
                            -14.808355, -39.2960107,
                            "Rodovia Br 101, Km 507",
                            "s/n",
                            "TAVEIROLANDIA",
                            "Itabuna",
                            "Bahia"
                    ),
                    setOf(FuelDto(GASOLINE, "4.68"))
            ),
            GasStationDto(
                    2341, "Ferradas Comércio e Derivados de Petróleo Ltda",
                    "2019-04-08T00:00:00",
                    BrandDto(25, "Petrobrás"),
                    AddressDto(
                            -14.8503667, -39.3387818,
                            null,
                            "s/n",
                            "Ferradas",
                            "Itabuna",
                            "Bahia"
                    ),
                    setOf(FuelDto(GASOLINE, "4.68"))
            ),
            GasStationDto(
                    2342, "Comercial de Derivados de Petróleo da Hora Ltda Epp",
                    "2019-04-08T00:00:00",
                    BrandDto(6, "Branca"),
                    AddressDto(
                            -14.7905134, -39.2781267,
                            "Avenida Inácio Tosta Filho",
                            "508",
                            "Centro",
                            "Itabuna",
                            "Bahia"
                    ),
                    setOf(FuelDto(GASOLINE, "4.68"))
            ),
            GasStationDto(
                    2343, "Fama Comércio de Combustíveis Eireli",
                    "2019-04-08T00:00:00",
                    BrandDto(6, "Branca"),
                    AddressDto(
                            -14.7914557, -39.2803586,
                            "Avenida Amélia Amado",
                            "987",
                            "Centro",
                            "Itabuna",
                            "Bahia"
                    ),
                    setOf(FuelDto(GASOLINE, "4.70"))
            ),
            GasStationDto(
                    2344, "Lopes Lemos Comércio de Combustíveis Ltda",
                    "2019-04-08T00:00:00",
                    BrandDto(6, "Branca"),
                    AddressDto(
                            -14.8035111, -39.2935516,
                            "BR-101",
                            "s/n",
                            "Manuel Leal",
                            "Itabuna",
                            "Bahia"
                    ),
                    setOf(FuelDto(GASOLINE, "4.59"))
            ),
            GasStationDto(
                    2345, "Derivados de Petróleo Teuna Ltda",
                    "2019-04-08T00:00:00",
                    BrandDto(18, "Ipiranga"),
                    AddressDto(
                            -14.7918712, -39.2807482,
                            "Avenida Inácio Tosta Filho",
                            "834",
                            "Centro",
                            "Itabuna",
                            "Bahia"
                    ),
                    setOf(FuelDto(GASOLINE, "4.70"))
            ),
            GasStationDto(
                    2346, "Peixoto Produtos de Petróleo Ltda",
                    "2019-04-08T00:00:00",
                    BrandDto(31, "Raízen"),
                    AddressDto(
                            -14.7816881, -39.2680318,
                            "Avenida Juracy Magalhães",
                            "321a",
                            "Nossa Senhora de Fátima",
                            "Itabuna",
                            "Bahia"
                    ),
                    setOf(FuelDto(GASOLINE, "4.69"))
            ),
            GasStationDto(
                    2347, "Apg Comércio de Combustíveis Ltda",
                    "2019-04-08T00:00:00",
                    BrandDto(2, "Alesat"),
                    AddressDto(
                            -14.7914527, -39.2803445,
                            "Avenida Inácio Tosta Filho",
                            "923",
                            "Centro",
                            "Itabuna",
                            "Bahia"
                    ),
                    setOf(FuelDto(GASOLINE, "4.49"))
            ),
            GasStationDto(
                    2349, "Posto Universal Comércio de Combustíveis Ltda",
                    "2019-04-08T00:00:00",
                    BrandDto(25, "Petrobrás"),
                    AddressDto(
                            -14.7836127, -39.2682028,
                            "Rua Maria Olívia Rebouças",
                            "s/n",
                            null,
                            "Itabuna",
                            "Bahia"
                    ),
                    setOf(FuelDto(GASOLINE, "4.70"))
            )
    )
