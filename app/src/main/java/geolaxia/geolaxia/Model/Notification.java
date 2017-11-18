package geolaxia.geolaxia.Model;

import java.util.Date;

public class Notification {
    private long Time;
    private String TipoNotificacion;

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
}
