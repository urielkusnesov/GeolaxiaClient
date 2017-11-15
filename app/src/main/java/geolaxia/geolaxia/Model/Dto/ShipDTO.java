package geolaxia.geolaxia.Model.Dto;

import geolaxia.geolaxia.Model.Ship;

/**
 * Created by uriel on 11/11/2017.
 */

public class ShipDTO extends BaseDTO {
    private Ship Data;

    public Ship getData() {
        return Data;
    }

    public void setData(Ship data) {
        this.Data = data;
    }
}
