package geolaxia.geolaxia.Services.Implementation;

import android.app.Activity;
import android.app.Fragment;

import org.json.JSONException;

import geolaxia.geolaxia.Activities.AttackActivity;
import geolaxia.geolaxia.Activities.ColonizeActivity;
import geolaxia.geolaxia.Activities.HomeActivity;
import geolaxia.geolaxia.Services.Interface.IPlanetService;
import geolaxia.geolaxia.Services.Interface.IRestService;

/**
 * Created by uriel on 13/5/2017.
 */

public class PlanetService implements IPlanetService {
    private final IRestService restService = RestService.getInstance();

    @Override
    public void GetByPlayer(String username, String token, HomeActivity context) {
        restService.GetPlanetsByPlayer(username, token, context);
    }

    @Override
    public void GetPlanet(int planetId, String username, String token, HomeActivity context) {
        //restService.GetPlanet(planetId, username, token, context);
    }

    @Override
    public void GetAllGalaxies(String username, String token, AttackActivity act, AttackActivity.AttackFragment context) {
        restService.GetAllGalaxies(username, token, act, context);
    }

    @Override
    public void GetAllGalaxies(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context) {
        restService.GetAllGalaxies(username, token, act, context);
    }

    @Override
    public void GetSolarSystemsByGalaxy(String username, String token, AttackActivity act, AttackActivity.AttackFragment context, int galaxyId) {
        restService.GetSolarSystemsByGalaxy(username, token, act, context, galaxyId);
    }

    @Override
    public void GetSolarSystemsByGalaxy(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context, int galaxyId) {
        restService.GetSolarSystemsByGalaxy(username, token, act, context, galaxyId);
    }

    @Override
    public void GetPlanetsBySolarSystem(String username, String token, AttackActivity act, AttackActivity.AttackFragment context, int solarSystemId) {
        restService.GetPlanetsBySolarSystem(username, token, act, context, solarSystemId);
    }

    @Override
    public void GetPlanetsBySolarSystem(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context, int solarSystemId) {
        restService.GetPlanetsBySolarSystem(username, token, act, context, solarSystemId);
    }

    @Override
    public void GetFleet(String username, String token, AttackActivity act, AttackActivity.AttackFragment context, int planetId) {
        restService.GetPlanetFleet(username, token, act, context, planetId);
    }

    @Override
    public void GetFleet(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context, int planetId) {
        restService.GetPlanetFleet(username, token, act, context, planetId);
    }

    @Override
    public void GetFleet(String username, String token, AttackActivity act, AttackActivity.CloseAttackFragment context, int planetId) {
        restService.GetPlanetFleet(username, token, act, context, planetId);
    }

    @Override
    public void GetAllGalaxies(String username, String token, ColonizeActivity act, ColonizeActivity.ColonizeFragment context) {
        restService.GetAllGalaxies(username, token, act, context);
    }

    @Override
    public void GetSolarSystemsByGalaxy(String username, String token, ColonizeActivity act, ColonizeActivity.ColonizeFragment context, int galaxyId) {
        restService.GetSolarSystemsByGalaxy(username, token, act, context, galaxyId);
    }

    @Override
    public void GetPlanetsBySolarSystem(String username, String token, ColonizeActivity act, ColonizeActivity.ColonizeFragment context, int solarSystemId) {
        restService.GetPlanetsBySolarSystem(username, token, act, context, solarSystemId);
    }

    @Override
    public void GetAllGalaxies(String username, String token, ColonizeActivity act, ColonizeActivity.CoordinatesFragment context) {
        restService.GetAllGalaxies(username, token, act, context);
    }

    @Override
    public void GetSolarSystemsByGalaxy(String username, String token, ColonizeActivity act, ColonizeActivity.CoordinatesFragment context, int galaxyId) {
        restService.GetSolarSystemsByGalaxy(username, token, act, context, galaxyId);
    }

    @Override
    public void GetPlanetsBySolarSystem(String username, String token, ColonizeActivity act, ColonizeActivity.CoordinatesFragment context, int solarSystemId) {
        restService.GetPlanetsBySolarSystem(username, token, act, context, solarSystemId);
    }
}
