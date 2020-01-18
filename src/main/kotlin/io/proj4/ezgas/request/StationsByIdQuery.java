package io.proj4.ezgas.request;

import io.proj4.ezgas.model.FuelType;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.util.Set;

public class StationsByIdQuery {
    @NotEmpty @Size(max = 50)
    private Set<Integer> ids;

    @Nullable
    private Set<FuelType> fuelType;

    public Set<Integer> getIds() {
        return ids;
    }

    @Nullable
    public Set<FuelType> getFuelType() {
        return fuelType;
    }

    public void setIds(Set<Integer> ids) {
        this.ids = ids;
    }

    public void setFuelType(@Nullable Set<FuelType> fuelType) {
        this.fuelType = fuelType;
    }
}