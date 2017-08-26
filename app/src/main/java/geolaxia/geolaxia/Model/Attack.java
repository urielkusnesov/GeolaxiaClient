package geolaxia.geolaxia.Model;

import org.json.JSONObject;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by uriel on 13/8/2017.
 */

public class Attack implements Serializable{
    private int Id;
    private Player AttackerPlayer;
    private Planet AttackerPlanet;
    private Player DestinationPlayer;
    private Planet DestinationPlanet;
    private ArrayList<Ship> Fleet;
    private Date FleetDeparture;
    private Date FleetArrival;

    public Attack(Player attackerPlayer, Planet attackerPlanet, Player destinationPlayer, Planet destinationPlanet, ArrayList<Ship> fleet,
                  Date fleetDeparture, Date fleetArrival){
        this.AttackerPlayer = attackerPlayer;
        this.AttackerPlanet = attackerPlanet;
        this.DestinationPlayer = destinationPlayer;
        this.DestinationPlanet = destinationPlanet;
        this.Fleet = fleet;
        this.FleetDeparture = fleetDeparture;
        this.FleetArrival = fleetArrival;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        Id = id;
    }

    public Player getAttackerPlayer() {
        return AttackerPlayer;
    }

    public void setAttackerPlayer(Player attackerPlayer) {
        AttackerPlayer = attackerPlayer;
    }

    public Planet getAttackerPlanet() {
        return AttackerPlanet;
    }

    public void setAttackerPlanet(Planet attackerPlanet) {
        AttackerPlanet = attackerPlanet;
    }

    public Player getDestinationPlayer() {
        return DestinationPlayer;
    }

    public void setDestinationPlayer(Player destinationPlayer) {
        DestinationPlayer = destinationPlayer;
    }

    public Planet getDestinationPlanet() {
        return DestinationPlanet;
    }

    public void setDestinationPlanet(Planet destinationPlanet) {
        DestinationPlanet = destinationPlanet;
    }

    public ArrayList<Ship> getFleet() {
        return Fleet;
    }

    public void setFleet(ArrayList<Ship> fleet) {
        Fleet = fleet;
    }

    public Date getFleetDeparture() {
        return FleetDeparture;
    }

    public void setFleetDeparture(Date fleetDeparture) {
        FleetDeparture = fleetDeparture;
    }

    public Date getFleetArrival() {
        return FleetArrival;
    }

    public void setFleetArrival(Date fleetArrival) {
        FleetArrival = fleetArrival;
    }

    public JSONObject toJSONObject(){
        HashMap<String,String> params = new HashMap<String,String>();
        if (this.AttackerPlayer != null) params.put("AttackerPlayerId", String.valueOf(this.AttackerPlayer.getId()));
        if (this.AttackerPlanet != null) params.put("AttackerPlanetId", String.valueOf(this.AttackerPlanet.getId()));
        if (this.DestinationPlayer != null) params.put("DestinationPlayerId", String.valueOf(this.DestinationPlayer.getId()));
        if (this.DestinationPlanet != null) params.put("DestinationPlanetId", String.valueOf(this.DestinationPlanet.getId()));
        String ships = "";
        for (Ship ship: this.Fleet) {
            ships += ship.getId() + ",";
        }
        ships = ships.substring(0, ships.length()-1);
        if (this.Fleet.size() > 0) params.put("FleetIds", ships);

        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH-mm-ss");
        if (this.FleetDeparture != null) params.put("FleetDeparture", String.valueOf(df.format(this.FleetDeparture.getTime())));
        if (this.FleetArrival != null) params.put("FleetArrival", String.valueOf(df.format(this.FleetArrival.getTime())));
        return new JSONObject(params);
    }
}
