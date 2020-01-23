package io.proj4.ezgas.request;

import io.proj4.ezgas.model.FuelType;
import io.proj4.ezgas.model.SortCriteria;
import org.hibernate.validator.constraints.Range;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

public class NearbyStationsQuery {
    @NotNull @Range(min = -90, max = 90)
    private Double latitude;

    @NotNull @Range(min = -180, max = 180)
    private Double longitude;

    @Range(min = 100, max = 25000)
    private float distance = 15000f;

    @NotNull
    private FuelType fuelType;

    @NotNull
    private Set<Integer> brands = new HashSet<>();

    private SortCriteria sortBy = SortCriteria.PRICE;

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public float getDistance() {
        return distance;
    }

    public FuelType getFuelType() {
        return fuelType;
    }

    public Set<Integer> getBrands() {
        return brands;
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

    public void setDistance(float range) {
        this.distance = range;
    }

    public void setFuelType(FuelType fuelType) {
        this.fuelType = fuelType;
    }

    public void setBrands(@Nullable Set<Integer> brands) {
        this.brands = brands;
    }

    public void setSortBy(SortCriteria sortBy) {
        this.sortBy = sortBy;
    }
}
