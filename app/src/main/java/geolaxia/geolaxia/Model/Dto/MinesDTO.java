package geolaxia.geolaxia.Model.Dto;

import java.util.ArrayList;

import geolaxia.geolaxia.Model.Mine;

/**
 * Created by uriel on 24/9/2017.
 */

public class MinesDTO extends BaseDTO {
    private ArrayList<Mine> Data;

    public ArrayList<Mine> getData() {
        return Data;
    }

    public void setData(ArrayList<Mine> data) {
        this.Data = data;
    }
}
