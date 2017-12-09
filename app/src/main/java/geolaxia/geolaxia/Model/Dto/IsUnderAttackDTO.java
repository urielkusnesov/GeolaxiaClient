package geolaxia.geolaxia.Model.Dto;

public class IsUnderAttackDTO extends BaseDTO {
    private long Data;

    public long getData() {
        return Data;
    }

    public void setData(long data) {
        this.Data = data;
    }

    public boolean EstaLlegando() {
        return (Data > System.currentTimeMillis());
    }
}
