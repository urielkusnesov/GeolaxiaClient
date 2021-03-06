package geolaxia.geolaxia.Services.Implementation;

import org.json.JSONException;

import geolaxia.geolaxia.Activities.AttackActivity;
import geolaxia.geolaxia.Activities.HomeActivity;
import geolaxia.geolaxia.Activities.LoginActivity;
import geolaxia.geolaxia.Activities.RegisterActivity;
import geolaxia.geolaxia.Model.Player;
import geolaxia.geolaxia.Services.Interface.ILoginService;
import geolaxia.geolaxia.Services.Interface.IRestService;

/**
 * Created by uriel on 15/4/2017.
 */

public class LoginService implements ILoginService {
    private final IRestService restService = RestService.getInstance();

    @Override
    public void LogIn(String username, String password, LoginActivity context) throws JSONException {
        restService.LogIn(username, password, context);
    }

    @Override
    public void FacebookLogIn(Player player, String token, LoginActivity context) throws JSONException {
        restService.FacebookLogIn(player, token, context);
    }

    @Override
    public void Register(Player player, RegisterActivity context) throws JSONException {
        restService.Register(player, context);
    }

    @Override
    public void SetLastPosition(String latitud, String longitude, Player player, HomeActivity context) {
        restService.SetLastPosition(latitud, longitude, player, context);
    }

    @Override
    public void SetWeather(String username, String token, int weatherDesc, String weatherWindSpeed, HomeActivity act) {
        restService.SetWeather(username, token, weatherDesc, weatherWindSpeed, act);
    }

    @Override
    public void GetCloserPlayers(String username, String token, AttackActivity.CloseAttackFragment context, AttackActivity act){
        restService.GetCloserPlayers(username, token, context, act);
    }

    @Override
    public void GetNotifications(String username, String token, Player player, HomeActivity act){
        restService.GetNotifications(username, token, player, act);
    }

    @Override
    public void SetFirebaseToken(String username, String token, String firebaseToken, HomeActivity act) {
        restService.SetFirebaseToken(username, token, firebaseToken, act);
    }
}
