package geolaxia.geolaxia.Model.Dto;

import geolaxia.geolaxia.Model.Trader;

/**
 * Created by uriel on 11/11/2017.
 */

public class TraderDTO extends BaseDTO {
    private Trader Data;

    public Trader getData() {
        return Data;
    }

    public void setData(Trader data) {
        this.Data = data;
    }
}
