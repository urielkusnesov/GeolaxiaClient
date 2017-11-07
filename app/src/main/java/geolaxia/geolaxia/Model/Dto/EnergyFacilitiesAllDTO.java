package geolaxia.geolaxia.Model.Dto;

import java.util.ArrayList;

import geolaxia.geolaxia.Model.EnergyFacility;

/**
 * Created by uriel on 30/10/2017.
 */

public class EnergyFacilitiesAllDTO extends BaseDTO {
    private ArrayList<ArrayList<EnergyFacility>> Data;

    public ArrayList<ArrayList<EnergyFacility>> getData() {
        return Data;
    }

    public void setData(ArrayList<ArrayList<EnergyFacility>> data) {
        this.Data = data;
    }
}
