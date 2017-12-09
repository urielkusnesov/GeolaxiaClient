package geolaxia.geolaxia.Model;

import java.util.Date;

public class Shield extends Military{
    private boolean IsActive;

    public Shield (int id, String name, int constructinTime, Cost cost, Planet planet, int requiredLevel, Date enableDate, boolean isActive) {
        super(id, name, constructinTime, cost, planet, requiredLevel, enableDate);
        this.setStatus(isActive);
    }
    /*public Shield(int id, String name, int constructinTime, Cost cost, Planet planet, int requiredLevel, Date enableDate, int attack, int defense){
        super(id, name, constructinTime, cost, planet, requiredLevel, enableDate);
        this.Id = id;
    }*/

    /*public Shield(int id, String name, int constructinTime, geolaxia.geolaxia.Model.Cost cost, geolaxia.geolaxia.Model.Planet planet, int requiredLevel, Date enableDate) {
        super(id, name, constructinTime, cost, planet, requiredLevel, enableDate);
    }*/

    public boolean getStatus() {
        return this.IsActive;
    }

    public void setStatus(boolean isActive) {
        this.IsActive = isActive;
    }
}
