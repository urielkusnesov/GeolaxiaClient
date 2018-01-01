package geolaxia.geolaxia.Model.Dto;

import java.util.ArrayList;

/**
 * Created by uriel on 28/12/2017.
 */

public class IsBuildingFaciltyDTO extends BaseDTO {
    private ArrayList<Long> Data;

    public ArrayList<Long> getData() {
        return Data;
    }

    public void setData(ArrayList<Long> data) {
        this.Data = data;
    }
}
