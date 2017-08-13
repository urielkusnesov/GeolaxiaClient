package geolaxia.geolaxia.Model.Dto;

import java.util.ArrayList;

import geolaxia.geolaxia.Model.Galaxy;
import geolaxia.geolaxia.Model.Planet;

/**
 * Created by uriel on 13/8/2017.
 */

public class GalaxiesDTO extends BaseDTO {
    private ArrayList<Galaxy> Data;

    public ArrayList<Galaxy> getData() {
        return Data;
    }

    public void setData(ArrayList<Galaxy> data) {
        this.Data = data;
    }
}
