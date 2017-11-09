package geolaxia.geolaxia.Model.Dto;

public class IsSendingColonizerDTO extends BaseDTO {
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
