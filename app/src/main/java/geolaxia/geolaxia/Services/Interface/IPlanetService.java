package geolaxia.geolaxia.Services.Interface;

import org.json.JSONException;

import java.util.ArrayList;

import geolaxia.geolaxia.Activities.AttackActivity;
import geolaxia.geolaxia.Activities.AttackByCoordinatesActivity;
import geolaxia.geolaxia.Activities.BaseAttackActivity;
import geolaxia.geolaxia.Activities.HomeActivity;
import geolaxia.geolaxia.Activities.MenuActivity;
import geolaxia.geolaxia.Model.Galaxy;
import geolaxia.geolaxia.Model.SolarSystem;

/**
 * Created by uriel on 13/5/2017.
 */

public interface IPlanetService {
    void GetByPlayer(String username, String token, HomeActivity context) throws JSONException;
    void GetPlanet(int planetId, String username, String token, HomeActivity context) throws JSONException;
    void GetAllGalaxies(String username, String token, BaseAttackActivity context);
    void GetSolarSystemsByGalaxy(String username, String token, BaseAttackActivity context, int galaxyId);
    void GetPlanetsBySolarSystem(String username, String token, BaseAttackActivity context, int solarSystemId);
    void GetFleet(String username, String token, BaseAttackActivity context, int planetId);
}