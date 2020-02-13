package io.proj4.ezgas.request;

import io.proj4.ezgas.model.FuelType;
import io.proj4.ezgas.model.SortCriteria;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

import static io.proj4.ezgas.model.SortCriteria.PRICE;

public class NearbyQuery {
    @NotNull @Range(min = -90, max = 90)
    private Double latitude;

    @NotNull @Range(min = -180, max = 180)
    private Double longitude;

    @NotNull @Range(min = 100, max = 25000)
    private Float distance = 10000f;

    @NotNull
    private FuelType fuelType;

    @NotNull
    private SortCriteria sortBy = SortCriteria.PRICE;

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public Float getDistance() {
        return distance;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public SortCriteria getSortBy() {
        return sortBy;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public void setSortBy(SortCriteria sortBy) {
        this.sortBy = sortBy;
    }
}
