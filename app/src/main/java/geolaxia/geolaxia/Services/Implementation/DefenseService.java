package geolaxia.geolaxia.Services.Implementation;

import org.json.JSONException;

import geolaxia.geolaxia.Activities.AttackActivity;
import geolaxia.geolaxia.Activities.DefenseActivity;
import geolaxia.geolaxia.Activities.HomeActivity;
import geolaxia.geolaxia.Services.Interface.IDefenseService;
import geolaxia.geolaxia.Services.Interface.IPlanetService;
import geolaxia.geolaxia.Services.Interface.IRestService;

public class DefenseService implements IDefenseService {
    private final IRestService restService = RestService.getInstance();

    @Override
    public void GetShieldStatus(String username, String token, DefenseActivity context, int planetId) {
        restService.GetShieldStatus(username, token, context, planetId);
    }

    @Override
    public void GetCannons(String username, String token, DefenseActivity context, int planetId) {
        restService.GetCannons(username, token, context, planetId);
    }

    @Override
    public void BuildCannons(String username, String token, DefenseActivity context, int planetId, int cant) {
        restService.BuildCannons(username, token, context, planetId, cant);
    }

//    @Override
//    public void GetByPlayer(String username, String token, HomeActivity context) throws JSONException {
//        //restService.GetPlanetsByPlayer(username, token, context);
//    }
//
//    @Override
//    public void GetPlanet(int planetId, String username, String token, HomeActivity context) throws JSONException {
//        //restService.GetPlanet(planetId, username, token, context);
//    }
//
//    @Override
//    public void GetAllGalaxies(String username, String token, AttackActivity act, AttackActivity.AttackFragment context) {
//        restService.GetAllGalaxies(username, token, act, context);
//    }
//
//    @Override
//    public void GetAllGalaxies(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context) {
//        restService.GetAllGalaxies(username, token, act, context);
//    }
//
//    @Override
//    public void GetSolarSystemsByGalaxy(String username, String token, AttackActivity act, AttackActivity.AttackFragment context, int galaxyId) {
//        restService.GetSolarSystemsByGalaxy(username, token, act, context, galaxyId);
//    }
//
//    @Override
//    public void GetSolarSystemsByGalaxy(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context, int galaxyId) {
//        restService.GetSolarSystemsByGalaxy(username, token, act, context, galaxyId);
//    }
//
//    @Override
//    public void GetPlanetsBySolarSystem(String username, String token, AttackActivity act, AttackActivity.AttackFragment context, int solarSystemId) {
//        restService.GetPlanetsBySolarSystem(username, token, act, context, solarSystemId);
//    }
//
//    @Override
//    public void GetPlanetsBySolarSystem(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context, int solarSystemId) {
//        restService.GetPlanetsBySolarSystem(username, token, act, context, solarSystemId);
//    }
//
//    @Override
//    public void GetFleet(String username, String token, AttackActivity act, AttackActivity.AttackFragment context, int planetId) {
//        restService.GetPlanetFleet(username, token, act, context, planetId);
//    }
//
//    @Override
//    public void GetFleet(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context, int planetId) {
//        restService.GetPlanetFleet(username, token, act, context, planetId);
//    }
//
//    @Override
//    public void GetFleet(String username, String token, AttackActivity act, AttackActivity.CloseAttackFragment context, int planetId) {
//        restService.GetPlanetFleet(username, token, act, context, planetId);
//    }
}
