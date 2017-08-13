package geolaxia.geolaxia.Model.Dto;

import java.util.ArrayList;

import geolaxia.geolaxia.Model.Galaxy;
import geolaxia.geolaxia.Model.SolarSystem;

/**
 * Created by uriel on 13/8/2017.
 */

public class SolarSystemsDTO extends BaseDTO{
    private ArrayList<SolarSystem> Data;

    public ArrayList<SolarSystem> getData() {
        return Data;
    }

    public void setData(ArrayList<SolarSystem> data) {
        this.Data = data;
    }

}
