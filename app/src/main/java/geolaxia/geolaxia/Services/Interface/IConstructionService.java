package geolaxia.geolaxia.Services.Interface;

import geolaxia.geolaxia.Activities.ConstructionsActivity;
import geolaxia.geolaxia.Model.EnergyFacility;
import geolaxia.geolaxia.Model.Mine;
import geolaxia.geolaxia.Model.Planet;

/**
 * Created by uriel on 24/9/2017.
 */

public interface IConstructionService {
    void GetCurrentMines(String username, String token, int planetId, ConstructionsActivity context, ConstructionsActivity.MinesFragment fragment);
    void GetMinesToBuild(String username, String token, int planetId, ConstructionsActivity context, ConstructionsActivity.MinesFragment fragment);
    void BuildMine(String username, String token, Mine mine, ConstructionsActivity context, ConstructionsActivity.MinesFragment fragment);

    void GetCurrentEnergyFacilities(String username, String token, int planetId, ConstructionsActivity context, ConstructionsActivity.EnergyFragment fragment);
    void GetEnergyFacilitiesToBuild(String username, String token, int planetId, ConstructionsActivity context, ConstructionsActivity.EnergyFragment fragment);
    void BuildEnergyFacility(String username, String token, EnergyFacility energyFacility, ConstructionsActivity context, ConstructionsActivity.EnergyFragment fragment);
    void BuildSolarPanels(String username, String token, ConstructionsActivity context, ConstructionsActivity.EnergyFragment fragment, int planetId, int qtt);
    void BuildWindTurbines(String username, String token, ConstructionsActivity context, ConstructionsActivity.EnergyFragment fragment, int planetId, int qtt);
}
