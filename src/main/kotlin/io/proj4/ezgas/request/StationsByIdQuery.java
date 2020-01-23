package io.proj4.ezgas.request;

import io.proj4.ezgas.model.FuelType;
import org.jetbrains.annotations.Nullable;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

public class StationsByIdQuery {
    @NotEmpty @Size(max = 50)
    private Set<Integer> ids;

    @NotNull
    private Set<FuelType> fuelTypes = new HashSet<>();

    public Set<Integer> getIds() {
        return ids;
    }

    public Set<FuelType> getFuelTypes() {
        return fuelTypes;
    }

    public void setIds(Set<Integer> ids) {
        this.ids = ids;
    }

    public void setFuelTypes(@Nullable Set<FuelType> fuelTypes) {
        this.fuelTypes = fuelTypes;
    }
}