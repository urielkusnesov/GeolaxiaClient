package geolaxia.geolaxia.Model;

import org.json.JSONObject;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by uriel on 15/4/2017.
 */

public class Player implements Serializable {
    private int Id;
    private int Level;
    private int ResourcesUsed;
    private String UserName;
    private String Password;
    private String Token;
    private String FirstName;
    private String LastName;
    private String Email;
    private String FacebookId;
    private ArrayList<Planet> Planets;
    private String LastLatitude;
    private String LastLongitude;

    public Player() {

    }

    public Player(int id, int level, int resourcesUsed, String username, String password, String firstName, String lastName,
                  String email, String facebookId, ArrayList<Planet> planets, String lastLatitude, String lastLongitude){
        this.Id = id;
        this.Level = level;
        this.ResourcesUsed = resourcesUsed;
        this.UserName = username;
        this.Password = password;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Email = email;
        this.FacebookId = facebookId;
        this.Planets = planets;
        this.LastLatitude = lastLatitude;
        this.LastLongitude = lastLongitude;
    }

    public int getId() {
        return Id;
    }

    public void setId(int id) {
        this.Id = id;
    }

    public int getLevel() {
        return Level;
    }

    public void setLevel(int level) {
        this.Level = level;
    }

    public int getResourcesUsed() {
        return ResourcesUsed;
    }

    public void setResourcesUsed(int resourcesUsed) {
        this.ResourcesUsed = resourcesUsed;
    }

    public String getUsername() {
        return UserName;
    }

    public void setUsername(String username) {
        this.UserName = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        this.Password = password;
    }

    public String getToken() {
        return Token;
    }

    public void setToken(String token) { this.Token = token; }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        this.FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        this.LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        this.Email = email;
    }

    public String getFacebookId() {
        return FacebookId;
    }

    public void setFacebookId(String facebookId) {
        this.FacebookId = facebookId;
    }

    public String getLastLatitude() {
        return LastLatitude;
    }

    public void setLastLatitude(String lastLatitude) {
        LastLatitude = lastLatitude;
    }

    public String getLastLongitude() {
        return LastLongitude;
    }

    public void setLastLongitude(String lastLongitude) {
        LastLongitude = lastLongitude;
    }

    public ArrayList<Planet> getPlanets() {
        return Planets;
    }

    public void setPlanets(ArrayList<Planet> planets) {
        this.Planets = planets;
    }

    public Planet getPlanet(int id) {
        for (Planet planet: Planets) {
            if(planet.getId() == id){
                return planet;
            }
        }
        return null;
    }

    public JSONObject toJSONObject(){
        HashMap<String,String> params = new HashMap<String,String>();
        if(this.Id > 0){
            params.put("id", String.valueOf(this.Id));
        }
        params.put("level", String.valueOf(this.Level));
        params.put("resourcesUsed", String.valueOf(this.ResourcesUsed));
        if (this.UserName != null){
            params.put("username", this.UserName);
        }else {
            params.put("username", "");
        }
        if (this.Password != null){
            params.put("password", this.Password);
        }else {
            params.put("password", "");
        }
        if (this.FirstName != null){
            params.put("firstname", this.FirstName);
        }else {
            params.put("firstname", "");
        }
        if (this.LastName != null){
            params.put("lastname", this.LastName);
        }else {
            params.put("lastname", "");
        }
        if (this.Email != null){
            params.put("email", this.Email);
        }else {
            params.put("email", "");
        }
        if (this.FacebookId != null){
            params.put("facebookId", this.FacebookId);
        }else {
            params.put("facebookId", "");
        }
        params.put("lastLatitude", this.LastLatitude);
        params.put("lastLongitude", this.LastLongitude);
        return new JSONObject(params);
    }
}
