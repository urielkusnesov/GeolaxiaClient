package geolaxia.geolaxia.Model.Dto;

import geolaxia.geolaxia.Model.Attack;
import geolaxia.geolaxia.Model.Planet;

/**
 * Created by uriel on 13/8/2017.
 */

public class AttackDTO extends BaseDTO{
    private Attack Data;

    public Attack getData() {
        return Data;
    }

    public void setData(Attack data) {
        this.Data = data;
    }
}
