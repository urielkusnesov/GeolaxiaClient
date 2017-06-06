package geolaxia.geolaxia.Services.Implementation;

import geolaxia.geolaxia.Activities.HomeActivity;
import geolaxia.geolaxia.Services.Interface.IRestService;
import geolaxia.geolaxia.Services.Interface.IWeatherService;

/**
 * Created by uriel on 27/5/2017.
 */

public class WeatherService implements IWeatherService {
    private final IRestService restService = RestService.getInstance();

    @Override
    public void GetWeather(String latitude, String longitud, HomeActivity context) {
        restService.GetWeather(latitude, longitud, context);
    }
}
