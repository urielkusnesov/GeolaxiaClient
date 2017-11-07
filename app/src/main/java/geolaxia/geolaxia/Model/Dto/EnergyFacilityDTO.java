package geolaxia.geolaxia.Model.Dto;

import geolaxia.geolaxia.Model.EnergyFacility;

/**
 * Created by uriel on 28/10/2017.
 */

public class EnergyFacilityDTO extends BaseDTO {
    private EnergyFacility Data;

    public EnergyFacility getData() {
        return Data;
    }

    public void setData(EnergyFacility data) {
        this.Data = data;
    }

}
