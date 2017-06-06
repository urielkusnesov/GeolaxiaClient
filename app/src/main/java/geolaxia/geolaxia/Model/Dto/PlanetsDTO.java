package geolaxia.geolaxia.Model.Dto;

import java.util.ArrayList;

import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.Player;

/**
 * Created by uriel on 13/5/2017.
 */

public class PlanetsDTO extends BaseDTO {
    private ArrayList<Planet> Data;

    public ArrayList<Planet> getData() {
        return Data;
    }

    public void setData(ArrayList<Planet> data) {
        this.Data = data;
    }
}
