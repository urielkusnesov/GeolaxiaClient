package geolaxia.geolaxia.Services.Interface;

import org.json.JSONException;

import geolaxia.geolaxia.Activities.AttackActivity;
import geolaxia.geolaxia.Activities.ColonizeActivity;
import geolaxia.geolaxia.Activities.ConstructionsActivity;
import geolaxia.geolaxia.Activities.DefenseActivity;
import geolaxia.geolaxia.Activities.DefenseQuestionActivity;
import geolaxia.geolaxia.Activities.HomeActivity;
import geolaxia.geolaxia.Activities.LoginActivity;
import geolaxia.geolaxia.Activities.MilitaryConstructionsActivity;
import geolaxia.geolaxia.Activities.RegisterActivity;
import geolaxia.geolaxia.Model.Attack;
import geolaxia.geolaxia.Model.EnergyFacility;
import geolaxia.geolaxia.Model.Military;
import geolaxia.geolaxia.Model.Mine;
import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.Player;

/**
 * Created by uriel on 15/4/2017.
 */

public interface IRestService {
    public final static String RESPONSE_CODE = "com.example.uriel.ordertracker.App.Services.RestService.CODE";
    public final static String LOGIN_RESPONSE_ID = "com.example.uriel.ordertracker.App.Services.RestService.LOGIN_RESPONSE_ID";
    public final static String LOGIN_RESPONSE_NAME = "com.example.uriel.ordertracker.App.Services.RestService.LOGIN_RESPONSE_NAME";
    public final static String LOGIN_TOKEN = "com.example.uriel.ordertracker.App.Services.RestService.LOGIN_TOKEN";
    public final static String LOGIN_PASSWORD = "com.example.uriel.ordertracker.App.Services.RestService.LOGIN_PASSWORD";
    public final static String LOGIN_LOCATION = "com.example.uriel.ordertracker.App.Services.RestService.LOGIN_LOCATION";
    public final static String LOGIN_IMAGE = "com.example.uriel.ordertracker.App.Services.RestService.LOGIN_IMAGE";

    //Login
    void LogIn(final String username, final String password, final LoginActivity act);
    void FacebookLogIn(Player player, String token, LoginActivity context) throws JSONException;
    void Register(Player player, final RegisterActivity act);

    //Attack
    void GetPlanetsByPlayer(final String username, final String token, final HomeActivity act);
    //void GetPlanet(final int planetId, final String username, final String token, final HomeActivity act);
    void GetAllGalaxies(String username, String token, AttackActivity act, AttackActivity.AttackFragment context);
    void GetAllGalaxies(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context);
    void GetSolarSystemsByGalaxy(String username, String token, AttackActivity act, AttackActivity.AttackFragment context, int galaxyId);
    void GetSolarSystemsByGalaxy(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context, int galaxyId);
    void GetPlanetsBySolarSystem(String username, String token, AttackActivity act, AttackActivity.AttackFragment context, int solarSystemId);
    void GetPlanetsBySolarSystem(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context, int solarSystemId);
    void GetPlanetFleet(String username, String token, AttackActivity act, AttackActivity.AttackFragment context, int planetId);
    void GetPlanetFleet(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context, int planetId);
    void GetPlanetFleet(String username, String token, AttackActivity act, AttackActivity.CloseAttackFragment context, int planetId);
    void Attack(String username, String token, AttackActivity context, Attack planetId);

    //home
    void SetLastPosition(String latitude, String longitude, Player player, HomeActivity act);
    void SetWeather(String username, String token, int weatherDesc, String weatherWindSpeed, HomeActivity act);
    void GetCloserPlayers(String username, String token, AttackActivity.CloseAttackFragment context, AttackActivity act);
    void GetWeather(String latitude, String longitude, final HomeActivity act);

    //construction
    void GetCurrentMines(String username, String token, int planetId, ConstructionsActivity context, ConstructionsActivity.MinesFragment fragment);
    void GetMinesToBuild(String username, String token, int planetId, ConstructionsActivity context, ConstructionsActivity.MinesFragment fragment);
    void BuildMine(String username, String token, Mine mine, ConstructionsActivity context, ConstructionsActivity.MinesFragment fragment);
    void GetCurrentEnergyFacilities(String username, String token, int planetId, ConstructionsActivity context, ConstructionsActivity.EnergyFragment fragment);
    void GetEnergyFacilitiesToBuild(String username, String token, int planetId, ConstructionsActivity context, ConstructionsActivity.EnergyFragment fragment);
    void BuildEnergyFacility(String username, String token, EnergyFacility energyFacility, ConstructionsActivity context, ConstructionsActivity.EnergyFragment fragment);
    void BuildSolarPanels(final String username, final String token, final ConstructionsActivity.EnergyFragment fragment, final ConstructionsActivity context, int planetId, int qtt);
    void BuildWindTurbines(final String username, final String token, final ConstructionsActivity.EnergyFragment fragment, final ConstructionsActivity context, int planetId, int qtt);

    //military construction
    void GetCurrentHangar(String username, String token, int planetId, MilitaryConstructionsActivity context, MilitaryConstructionsActivity.HangarFragment fragment);
    void BuildHangar(String username, String token, int planetId, MilitaryConstructionsActivity context, MilitaryConstructionsActivity.HangarFragment fragment);
    void GetCurrentShield(String username, String token, int planetId, MilitaryConstructionsActivity context, MilitaryConstructionsActivity.OthersFragment fragment);
    void GetCurrentProbes(String username, String token, int planetId, MilitaryConstructionsActivity context, MilitaryConstructionsActivity.OthersFragment fragment);
    void GetCurrentTraders(String username, String token, int planetId, MilitaryConstructionsActivity context, MilitaryConstructionsActivity.OthersFragment fragment);
    void BuildShield(String username, String token, int planetId, MilitaryConstructionsActivity context, MilitaryConstructionsActivity.OthersFragment fragment);
    void BuildProbes(final String username, final String token, final MilitaryConstructionsActivity.OthersFragment fragment, final MilitaryConstructionsActivity context, int planetId, int qtt);
    void BuildTraders(final String username, final String token, final MilitaryConstructionsActivity.OthersFragment fragment, final MilitaryConstructionsActivity context, int planetId, int qtt);

    //defense
    void GetShieldStatus(String username, String token, DefenseActivity context, int planetId);
    void GetCannons(String username, String token, DefenseActivity context, int planetId);
    void BuildCannons(String username, String token, DefenseActivity context, int planetId, int cant);
    void IsBuildingCannons(String username, String token, DefenseActivity context, int planetId);
    void GetPlanetFleet(String username, String token, MilitaryConstructionsActivity act, MilitaryConstructionsActivity.ShipsFragment context, int planetId);
    void GetShipsCost(String username, String token, MilitaryConstructionsActivity act, MilitaryConstructionsActivity.ShipsFragment context);
    void BuildShips(String username, String token, MilitaryConstructionsActivity act, MilitaryConstructionsActivity.ShipsFragment context, int planetId, int qtt, int shipType);

    void Get3RandomQuestions(String username, String token, DefenseQuestionActivity context);

    //colonize
    void GetAllGalaxies(String username, String token, ColonizeActivity act, ColonizeActivity.ColonizeFragment context);
    void GetSolarSystemsByGalaxy(String username, String token, ColonizeActivity act, ColonizeActivity.ColonizeFragment context, int galaxyId);
    void GetPlanetsBySolarSystem(String username, String token, ColonizeActivity act, ColonizeActivity.ColonizeFragment context, int solarSystemId);
    void GetColonizers(String username, String token, ColonizeActivity act, ColonizeActivity.ColonizeFragment context, int planetId);
    void IsSendingColonizer(String username, String token, ColonizeActivity act, ColonizeActivity.ColonizeFragment context, int planetId);
    void SendColonizer(String username, String token, ColonizeActivity act, ColonizeActivity.ColonizeFragment context, int planetId, int planetIdTarget, long time);

    void GetAllGalaxies(String username, String token, ColonizeActivity act, ColonizeActivity.CoordinatesFragment context);
    void GetSolarSystemsByGalaxy(String username, String token, ColonizeActivity act, ColonizeActivity.CoordinatesFragment context, int galaxyId);
    void GetPlanetsBySolarSystem(String username, String token, ColonizeActivity act, ColonizeActivity.CoordinatesFragment context, int solarSystemId);
    void GetColonizers(String username, String token, ColonizeActivity act, ColonizeActivity.CoordinatesFragment context, int planetId);
    void IsSendingColonizer(String username, String token, ColonizeActivity act, ColonizeActivity.CoordinatesFragment context, int planetId);
    void SendColonizer(String username, String token, ColonizeActivity act, ColonizeActivity.CoordinatesFragment context, int planetId, int planetIdTarget, long time);
}
