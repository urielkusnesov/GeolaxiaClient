package geolaxia.geolaxia.Services.Interface;

import org.json.JSONException;

import geolaxia.geolaxia.Activities.AttackActivity;
import geolaxia.geolaxia.Activities.HomeActivity;
import geolaxia.geolaxia.Activities.LoginActivity;
import geolaxia.geolaxia.Activities.RegisterActivity;
import geolaxia.geolaxia.Model.Attack;
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

    void LogIn(final String username, final String password, final LoginActivity act);
    void FacebookLogIn(Player player, String token, LoginActivity context) throws JSONException;
    void Register(Player player, final RegisterActivity act);

    //void GetPlanetsByPlayer(final String username, final String token, final HomeActivity act);
    //void GetPlanet(final int planetId, final String username, final String token, final HomeActivity act);
    void GetAllGalaxies(String username, String token, AttackActivity act, AttackActivity.AttackFragment context);
    void GetAllGalaxies(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context);
    void GetSolarSystemsByGalaxy(String username, String token, AttackActivity act, AttackActivity.AttackFragment context, int galaxyId);
    void GetSolarSystemsByGalaxy(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context, int galaxyId);
    void GetPlanetsBySolarSystem(String username, String token, AttackActivity act, AttackActivity.AttackFragment context, int solarSystemId);
    void GetPlanetsBySolarSystem(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context, int solarSystemId);
    void GetPlanetFleet(String username, String token, AttackActivity act, AttackActivity.AttackFragment context, int planetId);
    void GetPlanetFleet(String username, String token, AttackActivity act, AttackActivity.CoordinatesFragment context, int planetId);

    void SetLastPosition(String latitude, String longitude, Player player, HomeActivity act);
    void GetWeather(String latitude, String longitude, final HomeActivity act);

    void Attack(String username, String token, AttackActivity context, Attack planetId);
}
