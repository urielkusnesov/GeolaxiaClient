package geolaxia.geolaxia.Services.Implementation;

import geolaxia.geolaxia.Activities.ConstructionsActivity;
import geolaxia.geolaxia.Model.Mine;
import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Services.Interface.IConstructionService;
import geolaxia.geolaxia.Services.Interface.IRestService;

/**
 * Created by uriel on 24/9/2017.
 */

public class ConstructionService implements IConstructionService {
    private final IRestService restService = RestService.getInstance();

    @Override
    public void GetCurrentMines(String username, String token, int planetId, ConstructionsActivity context, ConstructionsActivity.MinesFragment fragment) {
        restService.GetCurrentMines(username, token, planetId, context, fragment);
    }

    @Override
    public void GetMinesToBuild(String username, String token, int planetId, ConstructionsActivity context, ConstructionsActivity.MinesFragment fragment) {
        restService.GetMinesToBuild(username, token, planetId, context, fragment);
    }

    @Override
    public void Build(String username, String token, Mine mine, ConstructionsActivity context, ConstructionsActivity.MinesFragment fragment) {
        restService.BuildMine(username, token, mine, context, fragment);
    }
}
