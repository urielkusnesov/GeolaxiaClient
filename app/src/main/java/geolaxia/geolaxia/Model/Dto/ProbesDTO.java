package geolaxia.geolaxia.Model.Dto;

import java.util.ArrayList;

import geolaxia.geolaxia.Model.Probe;

/**
 * Created by uriel on 11/11/2017.
 */

public class ProbesDTO extends BaseDTO {
    private ArrayList<Probe> Data;

    public ArrayList<Probe> getData() {
        return Data;
    }

    public void setData(ArrayList<Probe> data) {
        this.Data = data;
    }

}
