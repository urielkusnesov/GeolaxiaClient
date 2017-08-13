package geolaxia.geolaxia.Model.Dto;

import java.util.ArrayList;

import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.Ship;

/**
 * Created by uriel on 13/8/2017.
 */

public class ShipsDTO extends BaseDTO {
    private ArrayList<Ship> Data;

    public ArrayList<Ship> getData() {
        return Data;
    }

    public void setData(ArrayList<Ship> data) {
        this.Data = data;
    }
}
