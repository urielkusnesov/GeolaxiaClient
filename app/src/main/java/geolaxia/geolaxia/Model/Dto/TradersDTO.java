package geolaxia.geolaxia.Model.Dto;

import java.util.ArrayList;

import geolaxia.geolaxia.Model.Trader;

/**
 * Created by uriel on 11/11/2017.
 */

public class TradersDTO extends BaseDTO {
    private ArrayList<Trader> Data;

    public ArrayList<Trader> getData() {
        return Data;
    }

    public void setData(ArrayList<Trader> data) {
        this.Data = data;
    }

}
