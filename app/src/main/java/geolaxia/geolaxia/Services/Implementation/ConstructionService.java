package geolaxia.geolaxia.Services.Implementation;

import geolaxia.geolaxia.Activities.ConstructionsActivity;
import geolaxia.geolaxia.Model.EnergyFacility;
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
    public void BuildMine(String username, String token, Mine mine, ConstructionsActivity context, ConstructionsActivity.MinesFragment fragment) {
        restService.BuildMine(username, token, mine, context, fragment);
    }

    @Override
    public void GetCurrentEnergyFacilities(String username, String token, int planetId, ConstructionsActivity context, ConstructionsActivity.EnergyFragment fragment) {
        restService.GetCurrentEnergyFacilities(username, token, planetId, context, fragment);
    }

    @Override
    public void GetEnergyFacilitiesToBuild(String username, String token, int planetId, ConstructionsActivity context, ConstructionsActivity.EnergyFragment fragment) {
        restService.GetEnergyFacilitiesToBuild(username, token, planetId, context, fragment);
    }

    @Override
    public void BuildEnergyFacility(String username, String token, EnergyFacility energyFacility, ConstructionsActivity context, ConstructionsActivity.EnergyFragment fragment) {
        restService.BuildEnergyFacility(username, token, energyFacility, context, fragment);
    }

    @Override
    public void BuildSolarPanels(String username, String token, ConstructionsActivity context, ConstructionsActivity.EnergyFragment fragment, int planetId, int qtt) {
        restService.BuildSolarPanels(username, token, fragment, context, planetId, qtt);
    }

    @Override
    public void BuildWindTurbines(String username, String token, ConstructionsActivity context, ConstructionsActivity.EnergyFragment fragment, int planetId, int qtt) {
        restService.BuildWindTurbines(username, token, fragment, context, planetId, qtt);
    }
}
