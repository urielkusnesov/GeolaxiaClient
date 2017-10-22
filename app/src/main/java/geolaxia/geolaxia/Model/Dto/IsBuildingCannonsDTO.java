package geolaxia.geolaxia.Model.Dto;

import java.sql.Time;
import java.util.ArrayList;

import geolaxia.geolaxia.Model.Cannon;

public class IsBuildingCannonsDTO extends BaseDTO {
    private long Data;

    public long getData() {
        return Data;
    }

    public void setData(long data) {
        this.Data = data;
    }

    public boolean IsBuilding() {
        return (Data > System.currentTimeMillis());
    }
}
