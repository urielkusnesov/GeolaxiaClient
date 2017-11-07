package geolaxia.geolaxia.Services.Interface;

import org.json.JSONException;

import geolaxia.geolaxia.Activities.AttackActivity;
import geolaxia.geolaxia.Activities.HomeActivity;

/**
 * Created by uriel on 13/5/2017.
 */

public interface IPlanetService {
    void GetByPlayer(String username, String token, HomeActivity context);
    void GetPlanet(int planetId, String username, String token, HomeActivity context);
    void GetAllGalaxies(String username, String token, AttackActivity act, AttackActivity.AttackFragment context);
    void GetAllGalaxies(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context);
    void GetSolarSystemsByGalaxy(String username, String token, AttackActivity act, AttackActivity.AttackFragment context, int galaxyId);
    void GetSolarSystemsByGalaxy(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context, int galaxyId);
    void GetPlanetsBySolarSystem(String username, String token, AttackActivity act, AttackActivity.AttackFragment context, int solarSystemId);
    void GetPlanetsBySolarSystem(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context, int solarSystemId);
    void GetFleet(String username, String token, AttackActivity act, AttackActivity.AttackFragment context, int planetId);
    void GetFleet(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context, int planetId);
    void GetFleet(String username, String token, AttackActivity act, AttackActivity.CloseAttackFragment context, int planetId);
}