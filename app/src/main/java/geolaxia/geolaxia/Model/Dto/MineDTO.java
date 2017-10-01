package geolaxia.geolaxia.Model.Dto;

import geolaxia.geolaxia.Model.Mine;

/**
 * Created by uriel on 26/9/2017.
 */

public class MineDTO extends BaseDTO {
    private Mine Data;

    public Mine getData() {
        return Data;
    }

    public void setData(Mine data) {
        this.Data = data;
    }
}
