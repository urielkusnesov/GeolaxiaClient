package geolaxia.geolaxia.Model.Dto;

import geolaxia.geolaxia.Model.Hangar;

/**
 * Created by uriel on 11/11/2017.
 */

public class HangarDTO extends BaseDTO {
    private Hangar Data;

    public Hangar getData() {
        return Data;
    }

    public void setData(Hangar data) {
        this.Data = data;
    }

}
