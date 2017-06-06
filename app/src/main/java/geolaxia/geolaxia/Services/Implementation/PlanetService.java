package geolaxia.geolaxia.Services.Implementation;

import org.json.JSONException;

import geolaxia.geolaxia.Activities.HomeActivity;
import geolaxia.geolaxia.Activities.MenuActivity;
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

}
