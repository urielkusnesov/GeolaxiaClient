package geolaxia.geolaxia.Services.Interface;

import org.json.JSONException;

import geolaxia.geolaxia.Activities.AttackActivity;
import geolaxia.geolaxia.Activities.HomeActivity;
import geolaxia.geolaxia.Activities.LoginActivity;
import geolaxia.geolaxia.Activities.RegisterActivity;
import geolaxia.geolaxia.Model.Player;

/**
 * Created by uriel on 15/4/2017.
 */

public interface ILoginService {
    void LogIn(String username, String password, LoginActivity context) throws JSONException;
    void FacebookLogIn(Player player, String token, LoginActivity context) throws JSONException;
    void Register(Player player, RegisterActivity context) throws JSONException;
    void SetLastPosition(String latitud, String longitude, Player player, HomeActivity context);
    void SetWeather(String username, String token, int weatherDesc, String weatherWindSpeed, HomeActivity act);
    void GetCloserPlayers(String username, String token, AttackActivity.CloseAttackFragment context, AttackActivity act);
}
