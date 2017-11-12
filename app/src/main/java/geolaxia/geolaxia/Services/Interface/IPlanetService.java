package geolaxia.geolaxia.Services.Interface;

import android.app.Activity;
import android.app.Fragment;

import org.json.JSONException;

import geolaxia.geolaxia.Activities.AttackActivity;
import geolaxia.geolaxia.Activities.ColonizeActivity;
import geolaxia.geolaxia.Activities.HomeActivity;

/**
 * Created by uriel on 13/5/2017.
 */

public interface IPlanetService {
    void GetByPlayer(String username, String token, HomeActivity context) throws JSONException;
    void GetPlanet(int planetId, String username, String token, HomeActivity context) throws JSONException;
    void GetAllGalaxies(String username, String token, AttackActivity act, AttackActivity.AttackFragment context);
    void GetAllGalaxies(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context);
    void GetSolarSystemsByGalaxy(String username, String token, AttackActivity act, AttackActivity.AttackFragment context, int galaxyId);
    void GetSolarSystemsByGalaxy(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context, int galaxyId);
    void GetPlanetsBySolarSystem(String username, String token, AttackActivity act, AttackActivity.AttackFragment context, int solarSystemId);
    void GetPlanetsBySolarSystem(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context, int solarSystemId);
    void GetFleet(String username, String token, AttackActivity act, AttackActivity.AttackFragment context, int planetId);
    void GetFleet(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context, int planetId);
    void GetFleet(String username, String token, AttackActivity act, AttackActivity.CloseAttackFragment context, int planetId);

    void GetAllGalaxies(String username, String token, ColonizeActivity act, ColonizeActivity.ColonizeFragment context);
    void GetSolarSystemsByGalaxy(String username, String token, ColonizeActivity act, ColonizeActivity.ColonizeFragment context, int galaxyId);
    void GetPlanetsBySolarSystem(String username, String token, ColonizeActivity act, ColonizeActivity.ColonizeFragment context, int solarSystemId);
    void GetAllGalaxies(String username, String token, ColonizeActivity act, ColonizeActivity.CoordinatesFragment context);
    void GetSolarSystemsByGalaxy(String username, String token, ColonizeActivity act, ColonizeActivity.CoordinatesFragment context, int galaxyId);
    void GetPlanetsBySolarSystem(String username, String token, ColonizeActivity act, ColonizeActivity.CoordinatesFragment context, int solarSystemId);
}