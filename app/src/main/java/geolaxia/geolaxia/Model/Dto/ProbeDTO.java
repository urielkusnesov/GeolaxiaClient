package geolaxia.geolaxia.Model.Dto;

import geolaxia.geolaxia.Model.Probe;

/**
 * Created by uriel on 11/11/2017.
 */

public class ProbeDTO extends BaseDTO {
    private Probe Data;

    public Probe getData() {
        return Data;
    }

    public void setData(Probe data) {
        this.Data = data;
    }

}
