package geolaxia.geolaxia.Services.Interface;

import geolaxia.geolaxia.Activities.HomeActivity;

/**
 * Created by uriel on 27/5/2017.
 */

public interface IWeatherService {
    void GetWeather(String latitude, String longitude, HomeActivity context);
}
