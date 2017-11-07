package geolaxia.geolaxia.Model.Dto;

import java.util.ArrayList;

import geolaxia.geolaxia.Model.EnergyFacility;


/**
 * Created by uriel on 28/10/2017.
 */

public class EnergyFacilitiesDTO extends BaseDTO {
    private ArrayList<EnergyFacility> Data;

    public ArrayList<EnergyFacility> getData() {
        return Data;
    }

    public void setData(ArrayList<EnergyFacility> data) {
        this.Data = data;
    }
}
