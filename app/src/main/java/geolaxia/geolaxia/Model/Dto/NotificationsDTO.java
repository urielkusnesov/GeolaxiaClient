package geolaxia.geolaxia.Model.Dto;

import java.util.ArrayList;

import geolaxia.geolaxia.Model.Notification;

/**
 * Created by uriel on 13/8/2017.
 */

public class NotificationsDTO extends BaseDTO{
    private ArrayList<Notification> Data;

    public ArrayList<Notification> getData() {
        return Data;
    }

    public void setData(ArrayList<Notification> data) {
        this.Data = data;
    }
}
