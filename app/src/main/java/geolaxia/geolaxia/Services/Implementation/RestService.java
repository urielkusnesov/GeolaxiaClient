package geolaxia.geolaxia.Services.Implementation;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import geolaxia.geolaxia.Activities.BaseAttackActivity;
import geolaxia.geolaxia.Activities.HomeActivity;
import geolaxia.geolaxia.Activities.LoginActivity;
import geolaxia.geolaxia.Activities.RegisterActivity;
import geolaxia.geolaxia.Model.Attack;
import geolaxia.geolaxia.Model.BlackPlanet;
import geolaxia.geolaxia.Model.BluePlanet;
import geolaxia.geolaxia.Model.Constants;
import geolaxia.geolaxia.Model.Dto.AttackDTO;
import geolaxia.geolaxia.Model.Dto.BaseDTO;
import geolaxia.geolaxia.Model.Dto.GalaxiesDTO;
import geolaxia.geolaxia.Model.Dto.PlanetsDTO;
import geolaxia.geolaxia.Model.Dto.PlayerDTO;
import geolaxia.geolaxia.Model.Dto.ShipsDTO;
import geolaxia.geolaxia.Model.Dto.SolarSystemsDTO;
import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.Player;
import geolaxia.geolaxia.Model.Ship;
import geolaxia.geolaxia.Model.ShipX;
import geolaxia.geolaxia.Model.ShipY;
import geolaxia.geolaxia.Model.ShipZ;
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
    public void FacebookLogIn(final Player player, final String token, final LoginActivity act) {
        String url = Constants.getFacebookLoginServiceUrl();

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
                headers.put("userid", player.getFacebookId());
                headers.put("token", token);
                headers.put("username", player.getUsername());
                headers.put("email", player.getEmail());
                headers.put("firstName", player.getFirstName());
                headers.put("lastName", player.getLastName());
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

    @Override
    public void GetAllGalaxies(final String username, final String token, final BaseAttackActivity act) {
        String url = Constants.getGalaxiesServiceUrl();

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            GalaxiesDTO galaxiesContainer = new Gson().fromJson(response.toString(), GalaxiesDTO.class);
                            if(Constants.OK_RESPONSE.equals(galaxiesContainer.getStatus().getResult())) {
                                act.FillGalaxies(galaxiesContainer.getData());
                            } else {
                                act.handleUnexpectedError(galaxiesContainer.getStatus().getDescription());
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
                headers.put("token", token);
                return headers;
            }
        };

        // add the request object to the queue to be executed
        Request response = Volley.newRequestQueue(act).add(req);
    }

    @Override
    public void GetSolarSystemsByGalaxy(final String username, final String token, final BaseAttackActivity act, final int galaxyId) {
        String url = Constants.getSolarSystemsServiceUrl(galaxyId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            SolarSystemsDTO solarSystemsContainer = new Gson().fromJson(response.toString(), SolarSystemsDTO.class);
                            if(Constants.OK_RESPONSE.equals(solarSystemsContainer.getStatus().getResult())) {
                                act.FillSolarSystems(solarSystemsContainer.getData());
                            } else {
                                act.handleUnexpectedError(solarSystemsContainer.getStatus().getDescription());
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
                headers.put("token", token);
                return headers;
            }
        };

        // add the request object to the queue to be executed
        Request response = Volley.newRequestQueue(act).add(req);
    }

    @Override
    public void GetPlanetsBySolarSystem(final String username, final String token, final BaseAttackActivity act, final int solarSystemId) {
        String url = Constants.getPlanetsbySolarSystemService(solarSystemId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            PlanetsDTO planetsContainer = new Gson().fromJson(response.toString(), PlanetsDTO.class);
                            if(Constants.OK_RESPONSE.equals(planetsContainer.getStatus().getResult())) {
                                act.FillPlanets(planetsContainer.getData());
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
    public void GetPlanetFleet(final String username, final String token, final BaseAttackActivity act, final int planetId) {
        String url = Constants.getPlanetFleetServiceUrl(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            ShipsDTO shipsContainer = new Gson().fromJson(response.toString(), ShipsDTO.class);
                            if(Constants.OK_RESPONSE.equals(shipsContainer.getStatus().getResult())) {
                                //transformar a planetas segun herencia
                                ArrayList<ShipX> shipsX = new ArrayList<ShipX>();
                                ArrayList<ShipY> shipsY = new ArrayList<ShipY>();
                                ArrayList<ShipZ> shipsZ = new ArrayList<ShipZ>();
                                for (Ship ship: shipsContainer.getData()) {
                                    switch (ship.getShipType()){
                                        case Constants.SHIP_X:
                                            ShipX newShipX = new ShipX(ship);
                                            shipsX.add(newShipX);
                                            break;
                                        case Constants.SHIP_Y:
                                            ShipY newShipY = new ShipY(ship);
                                            shipsY.add(newShipY);
                                            break;
                                        case Constants.SHIP_Z:
                                            ShipZ newShipZ = new ShipZ(ship);
                                            shipsZ.add(newShipZ);
                                            break;
                                    }
                                }

                                act.FillFleets(shipsX, shipsY, shipsZ);
                            } else {
                                act.handleUnexpectedError(shipsContainer.getStatus().getDescription());
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
                headers.put("token", token);
                return headers;
            }
        };

        // add the request object to the queue to be executed
        Request response = Volley.newRequestQueue(act).add(req);
    }

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

    @Override
    public void Attack(final String username, final String token, final BaseAttackActivity context, final Attack attack) {
        String url = Constants.getAttackServiceUrl();

        JSONObject jsonAttack = attack.toJSONObject();
        JsonObjectRequest req = new JsonObjectRequest(url, jsonAttack,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            AttackDTO attackContainer = new Gson().fromJson(response.toString(), AttackDTO.class);
                            if(Constants.OK_RESPONSE.equals(attackContainer.getStatus().getResult())) {
                                context.AttackSaved();
                            } else {
                                context.handleUnexpectedError(attackContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            context.handleUnexpectedError("Ocurrio un error");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                context.handleUnexpectedError(error.getMessage());
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
        Request response = Volley.newRequestQueue(context).add(req);
    }


}
