package geolaxia.geolaxia.Services.Implementation;

import geolaxia.geolaxia.Activities.AttackActivity;
import geolaxia.geolaxia.Activities.ConstructionsActivity;
import geolaxia.geolaxia.Activities.MilitaryConstructionsActivity;
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

    @Override
    public void GetCurrentHangar(String username, String token, int planetId, MilitaryConstructionsActivity context, MilitaryConstructionsActivity.HangarFragment fragment) {
        restService.GetCurrentHangar(username, token, planetId, context, fragment);
    }

    @Override
    public void BuildHangar(String username, String token, int planetId, MilitaryConstructionsActivity context, MilitaryConstructionsActivity.HangarFragment fragment) {
        restService.BuildHangar(username, token, planetId, context, fragment);
    }

    @Override
    public void GetFleet(String username, String token, MilitaryConstructionsActivity act, MilitaryConstructionsActivity.ShipsFragment context, int planetId) {
        restService.GetPlanetFleet(username, token, act, context, planetId);
    }

    @Override
    public void GetShipsCost(String username, String token, MilitaryConstructionsActivity act, MilitaryConstructionsActivity.ShipsFragment context) {
        restService.GetShipsCost(username, token, act, context);
    }

    @Override
    public void BuildShips(String username, String token, MilitaryConstructionsActivity act, MilitaryConstructionsActivity.ShipsFragment context, int planetId, int qtt, int shipType) {
        restService.BuildShips(username, token, act, context, planetId, qtt, shipType);
    }

    @Override
    public void GetCurrentShield(String username, String token, int planetId, MilitaryConstructionsActivity context, MilitaryConstructionsActivity.OthersFragment fragment) {
        restService.GetCurrentShield(username, token, planetId, context, fragment);
    }

    @Override
    public void GetCurrentProbes(String username, String token, int planetId, MilitaryConstructionsActivity context, MilitaryConstructionsActivity.OthersFragment fragment) {
        restService.GetCurrentProbes(username, token, planetId, context, fragment);
    }

    @Override
    public void GetCurrentTraders(String username, String token, int planetId, MilitaryConstructionsActivity context, MilitaryConstructionsActivity.OthersFragment fragment) {
        restService.GetCurrentTraders(username, token, planetId, context, fragment);
    }

    @Override
    public void BuildShield(String username, String token, int planetId, MilitaryConstructionsActivity context, MilitaryConstructionsActivity.OthersFragment fragment) {
        restService.BuildShield(username, token, planetId, context, fragment);
    }

    @Override
    public void BuildProbes(String username, String token, MilitaryConstructionsActivity context, MilitaryConstructionsActivity.OthersFragment fragment, int planetId, int qtt) {
        restService.BuildProbes(username, token, fragment, context, planetId, qtt);
    }

    @Override
    public void BuildTraders(String username, String token, MilitaryConstructionsActivity context, MilitaryConstructionsActivity.OthersFragment fragment, int planetId, int qtt) {
        restService.BuildTraders(username, token, fragment, context, planetId, qtt);
    }
}
