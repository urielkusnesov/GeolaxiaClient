package geolaxia.geolaxia.Services.Interface;

import org.json.JSONException;

import geolaxia.geolaxia.Activities.AttackActivity;
import geolaxia.geolaxia.Activities.DefenseActivity;
import geolaxia.geolaxia.Activities.HomeActivity;

public interface IDefenseService {
    void GetShieldStatus(String username, String token, DefenseActivity context, int planetId);
    void GetCannons(String username, String token, DefenseActivity context, int planetId);
    void BuildCannons(int cant, String username, String token, DefenseActivity context);
//    void GetByPlayer(String username, String token, HomeActivity context) throws JSONException;
//    void GetPlanet(int planetId, String username, String token, HomeActivity context) throws JSONException;
//    void GetAllGalaxies(String username, String token, AttackActivity act, AttackActivity.AttackFragment context);
//    void GetAllGalaxies(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context);
//    void GetSolarSystemsByGalaxy(String username, String token, AttackActivity act, AttackActivity.AttackFragment context, int galaxyId);
//    void GetSolarSystemsByGalaxy(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context, int galaxyId);
//    void GetPlanetsBySolarSystem(String username, String token, AttackActivity act, AttackActivity.AttackFragment context, int solarSystemId);
//    void GetPlanetsBySolarSystem(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context, int solarSystemId);
//    void GetFleet(String username, String token, AttackActivity act, AttackActivity.AttackFragment context, int planetId);
//    void GetFleet(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context, int planetId);
//    void GetFleet(String username, String token, AttackActivity act, AttackActivity.CloseAttackFragment context, int planetId);
}