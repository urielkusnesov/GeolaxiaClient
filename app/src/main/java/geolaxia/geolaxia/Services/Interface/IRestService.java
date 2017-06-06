package geolaxia.geolaxia.Services.Interface;

import geolaxia.geolaxia.Activities.HomeActivity;
import geolaxia.geolaxia.Activities.MenuActivity;
import geolaxia.geolaxia.Activities.LoginActivity;
import geolaxia.geolaxia.Activities.RegisterActivity;
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
    void Register(Player player, final RegisterActivity act);
    //void GetPlanetsByPlayer(final String username, final String token, final HomeActivity act);
    //void GetPlanet(final int planetId, final String username, final String token, final HomeActivity act);
    void GetWeather(String latitude, String longitude, final HomeActivity act);
}
