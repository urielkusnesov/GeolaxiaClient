package geolaxia.geolaxia.Services.Implementation;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import geolaxia.geolaxia.Activities.HomeActivity;
import geolaxia.geolaxia.Activities.LoginActivity;
import geolaxia.geolaxia.Activities.RegisterActivity;
import geolaxia.geolaxia.Model.BlackPlanet;
import geolaxia.geolaxia.Model.BluePlanet;
import geolaxia.geolaxia.Model.Constants;
import geolaxia.geolaxia.Model.Dto.BaseDTO;
import geolaxia.geolaxia.Model.Dto.PlanetDTO;
import geolaxia.geolaxia.Model.Dto.PlanetsDTO;
import geolaxia.geolaxia.Model.Dto.PlayerDTO;
import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.Player;
import geolaxia.geolaxia.Model.WhitePlanet;
import geolaxia.geolaxia.Services.Interface.IRestService;

/**
 * Created by uriel on 15/4/2017.
 */

public class RestService implements IRestService {
    private static RestService ourInstance = null;

    public static RestService getInstance() {
        if(ourInstance == null) {
            ourInstance = new RestService();
        }
        return ourInstance;
    }

    @Override
    public void LogIn(final String username, final String password, final LoginActivity act) {
        String url = Constants.getLoginServiceUrl();

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            PlayerDTO playersContainer = new Gson().fromJson(response.toString(), PlayerDTO.class);
                            if(Constants.OK_RESPONSE.equals(playersContainer.getStatus().getResult())) {

                                //transformar a planetas segun herencia
                                ArrayList<Planet> planets = new ArrayList<Planet>();
                                for (Planet planet: playersContainer.getData().getPlanets()) {
                                    Planet newPlanet = null;
                                    switch (planet.getPlanetType()){
                                        case Constants.WHITE_PLANET:
                                            newPlanet = new WhitePlanet(planet);
                                            break;
                                        case Constants.BLUE_PLANET:
                                            newPlanet = new BluePlanet(planet);
                                            break;
                                        case Constants.BLACK_PLANET:
                                            newPlanet = new BlackPlanet(planet);
                                            break;
                                    }
                                    planets.add(newPlanet);
                                }
                                playersContainer.getData().setPlanets(planets);

                                act.LogIn(playersContainer.getData());
                            } else {
                                act.handleUnexpectedError(playersContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError(error.getMessage());
                //handle error
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("username", username);
                headers.put("password", password);
                return headers;
            }
        };

        // add the request object to the queue to be executed
        Request response = Volley.newRequestQueue(act).add(req);
    }

    @Override
    public void Register(final Player player, final RegisterActivity act) {
        String url = Constants.getRegisterServiceUrl();

        JSONObject jsonPlayer = player.toJSONObject();
        JsonObjectRequest req = new JsonObjectRequest(url, jsonPlayer,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try{
                            BaseDTO baseContainer = new Gson().fromJson(response.toString(), BaseDTO.class);
                            if(Constants.OK_RESPONSE.equals(baseContainer.getStatus().getResult())) {
                                act.registerSuccesfull();
                            } else {
                                act.handleUnexpectedError(baseContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError(error.getMessage());
            }
        });
        // add the request object to the queue to be executed
        Request response = Volley.newRequestQueue(act).add(req);
    }

/*    @Override
    public void GetPlanetsByPlayer(final String username, final String token, final HomeActivity act) {
        String url = Constants.getPlanetsByPlayerServiceUrl();

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            PlanetsDTO planetsContainer = new Gson().fromJson(response.toString(), PlanetsDTO.class);
                            if(Constants.OK_RESPONSE.equals(planetsContainer.getStatus().getResult())) {

                                //transformar a planetas segun herencia
                                ArrayList<Planet> planets = new ArrayList<Planet>();
                                for (Planet planet: planetsContainer.getData()) {
                                    Planet newPlanet = null;
                                    switch (planet.getPlanetType()){
                                        case Constants.WHITE_PLANET:
                                            newPlanet = new WhitePlanet(planet);
                                            break;
                                        case Constants.BLUE_PLANET:
                                            newPlanet = new BluePlanet(planet);
                                            break;
                                        case Constants.BLACK_PLANET:
                                            newPlanet = new BlackPlanet(planet);
                                            break;
                                    }
                                    planets.add(newPlanet);
                                }

                                act.FillPlanets(planets);
                            } else {
                                act.handleUnexpectedError(planetsContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyError volleyError = new VolleyError(new String(error.networkResponse.data));
                act.handleUnexpectedError(error.getMessage());
                //handle error
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("username", username);
                headers.put("token", token);
                return headers;
            }
        };

        // add the request object to the queue to be executed
        Request response = Volley.newRequestQueue(act).add(req);
    }

    @Override
    public void GetPlanet(final int planetId, final String username, final String token, final HomeActivity act) {
        String url = Constants.getPlanetServiceUrl();

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            PlanetDTO planetContainer = new Gson().fromJson(response.toString(), PlanetDTO.class);
                            if(Constants.OK_RESPONSE.equals(planetContainer.getStatus().getResult())) {

                                //transformar a planeta segun herencia
                                Planet newPlanet = null;
                                switch (planetContainer.getData().getPlanetType()){
                                    case Constants.WHITE_PLANET:
                                        newPlanet = new WhitePlanet(planetContainer.getData());
                                        break;
                                    case Constants.BLUE_PLANET:
                                        newPlanet = new BluePlanet(planetContainer.getData());
                                        break;
                                    case Constants.BLACK_PLANET:
                                        newPlanet = new BlackPlanet(planetContainer.getData());
                                        break;
                                }

                                act.FillPlanetInfo(newPlanet);
                            } else {
                                act.handleUnexpectedError(planetContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError(error.getMessage());
                //handle error
            }
        })
        {
            @Override
            public Map<String, String> getParams() {
                HashMap<String, String> params = new HashMap<String, String>();
                params.put("id", String.valueOf(planetId));
                return  params;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("username", username);
                headers.put("token", token);
                return headers;
            }
        };

        // add the request object to the queue to be executed
        Request response = Volley.newRequestQueue(act).add(req);
    }
*/
    public void GetWeather(final String latitude, final String longitude, final HomeActivity act) {
        String url = Constants.getWeatherServiceUrl(latitude, longitude);
        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject s) {
                        act.ParseWeather(s);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError(error.getMessage());
            }
        });

        Request response = Volley.newRequestQueue(act).add(req);
    }

}
