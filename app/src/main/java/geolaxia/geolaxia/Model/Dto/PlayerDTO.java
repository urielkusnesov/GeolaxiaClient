package geolaxia.geolaxia.Model.Dto;

import java.util.ArrayList;

import geolaxia.geolaxia.Model.Player;

/**
 * Created by uriel on 15/4/2017.
 */

public class PlayerDTO extends BaseDTO {
    private Player Data;

    public Player getData() {
        return Data;
    }

    public void setData(Player data) {
        this.Data = data;
    }
}
