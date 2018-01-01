package geolaxia.geolaxia.Services.Interface;

import geolaxia.geolaxia.Activities.AttackActivity;
import geolaxia.geolaxia.Activities.ConstructionsActivity;
import geolaxia.geolaxia.Activities.MilitaryConstructionsActivity;
import geolaxia.geolaxia.Model.EnergyFacility;
import geolaxia.geolaxia.Model.Mine;
import geolaxia.geolaxia.Model.Planet;

/**
 * Created by uriel on 24/9/2017.
 */

public interface IConstructionService {
    void GetCurrentMines(String username, String token, int planetId, ConstructionsActivity context, ConstructionsActivity.MinesFragment fragment);
    void GetMinesToBuild(String username, String token, int planetId, ConstructionsActivity context, ConstructionsActivity.MinesFragment fragment);
    void GetBuildingTime(String username, String token, int planetId, ConstructionsActivity context, ConstructionsActivity.MinesFragment fragment);
    void BuildMine(String username, String token, Mine mine, ConstructionsActivity context, ConstructionsActivity.MinesFragment fragment);

    void GetCurrentEnergyFacilities(String username, String token, int planetId, ConstructionsActivity context, ConstructionsActivity.EnergyFragment fragment);
    void GetEnergyFacilitiesToBuild(String username, String token, int planetId, ConstructionsActivity context, ConstructionsActivity.EnergyFragment fragment);
    void GetEnergyFacilitiesBuildingTime(String username, String token, int planetId, ConstructionsActivity context, ConstructionsActivity.EnergyFragment fragment);
    void BuildEnergyFacility(String username, String token, EnergyFacility energyFacility, ConstructionsActivity context, ConstructionsActivity.EnergyFragment fragment);
    void BuildSolarPanels(String username, String token, ConstructionsActivity context, ConstructionsActivity.EnergyFragment fragment, int planetId, int qtt);
    void BuildWindTurbines(String username, String token, ConstructionsActivity context, ConstructionsActivity.EnergyFragment fragment, int planetId, int qtt);
    void GetCurrentHangar(String username, String token, int planetId, MilitaryConstructionsActivity context, MilitaryConstructionsActivity.HangarFragment fragment);
    void GetHangarBuildingTime(String username, String token, int planetId, MilitaryConstructionsActivity context, MilitaryConstructionsActivity.HangarFragment fragment);
    void BuildHangar(String username, String token, int planetId, MilitaryConstructionsActivity context, MilitaryConstructionsActivity.HangarFragment fragment);
    void GetFleet(String username, String token, MilitaryConstructionsActivity act, MilitaryConstructionsActivity.ShipsFragment context, int planetId);
    void GetShipsCost(String username, String token, MilitaryConstructionsActivity act, MilitaryConstructionsActivity.ShipsFragment context);
    void GetShipsBuildingTime(String username, String token, int planetId, MilitaryConstructionsActivity act, MilitaryConstructionsActivity.ShipsFragment context);
    void BuildShips(String username, String token, MilitaryConstructionsActivity act, MilitaryConstructionsActivity.ShipsFragment context, int planetId, int qtt, int shipType);
    void GetCurrentShield(String username, String token, int planetId, MilitaryConstructionsActivity context, MilitaryConstructionsActivity.OthersFragment fragment);
    void GetCurrentProbes(String username, String token, int planetId, MilitaryConstructionsActivity context, MilitaryConstructionsActivity.OthersFragment fragment);
    void GetCurrentTraders(String username, String token, int planetId, MilitaryConstructionsActivity context, MilitaryConstructionsActivity.OthersFragment fragment);
    void GetOthersBuildingTime(String username, String token, int planetId, MilitaryConstructionsActivity context, MilitaryConstructionsActivity.OthersFragment fragment);
    void BuildShield(String username, String token, int planetId, MilitaryConstructionsActivity context, MilitaryConstructionsActivity.OthersFragment fragment);
    void BuildProbes(String username, String token, MilitaryConstructionsActivity context, MilitaryConstructionsActivity.OthersFragment fragment, int planetId, int qtt);
    void BuildTraders(String username, String token, MilitaryConstructionsActivity context, MilitaryConstructionsActivity.OthersFragment fragment, int planetId, int qtt);
}
