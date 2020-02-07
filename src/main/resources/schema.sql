DROP FUNCTION IF EXISTS distance$$

CREATE FUNCTION distance(lat0 FLOAT, lng0 FLOAT, lat1 FLOAT, lng1 FLOAT) RETURNS FLOAT
BEGIN
    DECLARE dist FLOAT;
    SET dist =
        6371392.896 * acos(
            cos(radians(lat1))
            * cos(radians(lat0))
            * cos(radians(lng0) - radians(lng1))
            + sin(radians(lat1))
            * sin(radians(lat0))
        );

    RETURN dist;
END $$