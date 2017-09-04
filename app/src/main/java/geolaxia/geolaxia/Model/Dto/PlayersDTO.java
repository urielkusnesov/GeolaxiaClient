package geolaxia.geolaxia.Model.Dto;

import java.util.ArrayList;

import geolaxia.geolaxia.Model.Player;

/**
 * Created by uriel on 3/9/2017.
 */

public class PlayersDTO extends BaseDTO {
    private ArrayList<Player> Data;

    public ArrayList<Player> getData() {
        return Data;
    }

    public void setData(ArrayList<Player> data) {
        this.Data = data;
    }

}
