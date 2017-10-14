package geolaxia.geolaxia.Services.Interface;

import geolaxia.geolaxia.Activities.ConstructionsActivity;
import geolaxia.geolaxia.Model.Mine;
import geolaxia.geolaxia.Model.Planet;

/**
 * Created by uriel on 24/9/2017.
 */

public interface IConstructionService {
    void GetCurrentMines(String username, String token, int planetId, ConstructionsActivity context, ConstructionsActivity.MinesFragment fragment);
    void GetMinesToBuild(String username, String token, int planetId, ConstructionsActivity context, ConstructionsActivity.MinesFragment fragment);
    void Build(String username, String token, Mine mine, ConstructionsActivity context, ConstructionsActivity.MinesFragment fragment);
}
