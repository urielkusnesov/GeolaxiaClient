package geolaxia.geolaxia.Services.Interface;

import org.json.JSONException;

import geolaxia.geolaxia.Activities.HomeActivity;
import geolaxia.geolaxia.Activities.MenuActivity;

/**
 * Created by uriel on 13/5/2017.
 */

public interface IPlanetService {
    void GetByPlayer(String username, String token, HomeActivity context) throws JSONException;
    void GetPlanet(int planetId, String username, String token, HomeActivity context) throws JSONException;
}