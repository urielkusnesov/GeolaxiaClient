package geolaxia.geolaxia.Services.Interface;

import geolaxia.geolaxia.Activities.ConstructionsActivity;
import geolaxia.geolaxia.Model.Planet;

/**
 * Created by uriel on 24/9/2017.
 */

public interface IConstructionService {
    void GetMinesToBuild(String username, String token, int planetId, ConstructionsActivity context, ConstructionsActivity.MinesFragment fragment);
    void BuildCrystal(String username, String token, int planetId, ConstructionsActivity context, int level, ConstructionsActivity.MinesFragment fragment);
    void BuildMetal(String username, String token, int planetId, ConstructionsActivity context, int level, ConstructionsActivity.MinesFragment fragment);
    void BuildDarkMatter(String username, String token, int planetId, ConstructionsActivity context, int level, ConstructionsActivity.MinesFragment fragment);
}
