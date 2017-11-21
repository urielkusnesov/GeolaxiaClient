package geolaxia.geolaxia.Model;

import java.util.Date;

public class Notification {
    private long Time;
    private String TipoNotificacion;
    private String PlayerName;
    private String PlanetNameO;
    private String PlanetNameT;

    public long getTime() {
        return Time;
    }

    public void setTime(long data) {
        this.Time = data;
    }

    public String getTipoNotificacion() {
        return TipoNotificacion;
    }

    public void setTipoNotificacion(String data) {
        this.TipoNotificacion = data;
    }

    public String getPlayerName() {
        return PlayerName;
    }

    public void setPlayerName(String data) {
        this.PlayerName = data;
    }

    public String getPlanetNameO() {
        return PlanetNameO;
    }

    public void setPlanetNameO(String data) {
        this.PlanetNameO = data;
    }

    public String getPlanetNameT() {
        return PlanetNameT;
    }

    public void setPlanetNameT(String data) {
        this.PlanetNameT = data;
    }
}
