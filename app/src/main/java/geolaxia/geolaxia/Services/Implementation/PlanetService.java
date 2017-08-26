package geolaxia.geolaxia.Services.Implementation;

import org.json.JSONException;

import java.util.ArrayList;

import geolaxia.geolaxia.Activities.AttackActivity;
import geolaxia.geolaxia.Activities.AttackByCoordinatesActivity;
import geolaxia.geolaxia.Activities.BaseAttackActivity;
import geolaxia.geolaxia.Activities.HomeActivity;
import geolaxia.geolaxia.Activities.MenuActivity;
import geolaxia.geolaxia.Model.Galaxy;
import geolaxia.geolaxia.Model.SolarSystem;
import geolaxia.geolaxia.Services.Interface.IPlanetService;
import geolaxia.geolaxia.Services.Interface.IRestService;

/**
 * Created by uriel on 13/5/2017.
 */

public class PlanetService implements IPlanetService {
    private final IRestService restService = RestService.getInstance();

    @Override
    public void GetByPlayer(String username, String token, HomeActivity context) throws JSONException {
        //restService.GetPlanetsByPlayer(username, token, context);
    }

    @Override
    public void GetPlanet(int planetId, String username, String token, HomeActivity context) throws JSONException {
        //restService.GetPlanet(planetId, username, token, context);
    }

    @Override
    public void GetAllGalaxies(String username, String token, BaseAttackActivity context) {
        restService.GetAllGalaxies(username, token, context);
    }

    @Override
    public void GetSolarSystemsByGalaxy(String username, String token, BaseAttackActivity context, int galaxyId) {
        restService.GetSolarSystemsByGalaxy(username, token, context, galaxyId);
    }

    @Override
    public void GetPlanetsBySolarSystem(String username, String token, BaseAttackActivity context, int solarSystemId) {
        restService.GetPlanetsBySolarSystem(username, token, context, solarSystemId);
    }

    @Override
    public void GetFleet(String username, String token, BaseAttackActivity context, int planetId) {
        restService.GetPlanetFleet(username, token, context, planetId);
    }
}
