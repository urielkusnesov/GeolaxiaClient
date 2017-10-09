package geolaxia.geolaxia.Model.Dto;

import java.util.ArrayList;
import geolaxia.geolaxia.Model.Cannon;

public class CannonsDTO extends BaseDTO {
    private ArrayList<Cannon> Data;

    public ArrayList<Cannon> getData() {
        return Data;
    }

    public void setData(ArrayList<Cannon> data) {
        this.Data = data;
    }
}
