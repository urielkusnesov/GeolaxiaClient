package geolaxia.geolaxia.Services.Interface;

import org.json.JSONException;

import geolaxia.geolaxia.Activities.LoginActivity;
import geolaxia.geolaxia.Activities.RegisterActivity;
import geolaxia.geolaxia.Model.Player;

/**
 * Created by uriel on 15/4/2017.
 */

public interface ILoginService {
    void LogIn(String username, String password, LoginActivity context) throws JSONException;
    void Register(Player player, RegisterActivity context) throws JSONException;
}
