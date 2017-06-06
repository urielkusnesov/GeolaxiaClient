package geolaxia.geolaxia.Model.Dto;

import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.Player;

/**
 * Created by uriel on 21/5/2017.
 */

public class PlanetDTO extends BaseDTO {
    private Planet Data;

    public Planet getData() {
        return Data;
    }

    public void setData(Planet data) {
        this.Data = data;
    }

}
