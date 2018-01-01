package geolaxia.geolaxia.Services.Implementation;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
import geolaxia.geolaxia.Model.BlackPlanet;
import geolaxia.geolaxia.Model.BluePlanet;
import geolaxia.geolaxia.Model.Constants;
import geolaxia.geolaxia.Model.CrystalMine;
import geolaxia.geolaxia.Model.DarkMatterMine;
import geolaxia.geolaxia.Model.Dto.AttackDTO;
import geolaxia.geolaxia.Model.Dto.AttackIdDTO;
import geolaxia.geolaxia.Model.Dto.BaseDTO;
import geolaxia.geolaxia.Model.Dto.CannonsDTO;
import geolaxia.geolaxia.Model.Dto.EnergyFacilitiesAllDTO;
import geolaxia.geolaxia.Model.Dto.EnergyFacilitiesDTO;
import geolaxia.geolaxia.Model.Dto.EnergyFacilityDTO;
//import geolaxia.geolaxia.Model.Dto.ColonizersDTO;
import geolaxia.geolaxia.Model.Dto.GalaxiesDTO;
import geolaxia.geolaxia.Model.Dto.HangarDTO;
import geolaxia.geolaxia.Model.Dto.IsBuildingCannonsDTO;
import geolaxia.geolaxia.Model.Dto.IsBuildingFaciltyDTO;
import geolaxia.geolaxia.Model.Dto.IsSendingColonizerDTO;
import geolaxia.geolaxia.Model.Dto.MineDTO;
import geolaxia.geolaxia.Model.Dto.MinesDTO;
import geolaxia.geolaxia.Model.Dto.NotificationsDTO;
import geolaxia.geolaxia.Model.Dto.PlanetsDTO;
import geolaxia.geolaxia.Model.Dto.PlayerDTO;
import geolaxia.geolaxia.Model.Dto.PlayersDTO;
import geolaxia.geolaxia.Model.Dto.ProbeDTO;
import geolaxia.geolaxia.Model.Dto.ProbesDTO;
import geolaxia.geolaxia.Model.Dto.QuestionDTO;
import geolaxia.geolaxia.Model.Dto.ShieldDTO;
import geolaxia.geolaxia.Model.Dto.ShipDTO;
import geolaxia.geolaxia.Model.Dto.ShipsDTO;
import geolaxia.geolaxia.Model.Dto.SolarSystemsDTO;
import geolaxia.geolaxia.Model.Dto.TraderDTO;
import geolaxia.geolaxia.Model.Dto.TradersDTO;
import geolaxia.geolaxia.Model.EnergyCentral;
import geolaxia.geolaxia.Model.EnergyFacility;
import geolaxia.geolaxia.Model.EnergyFuelCentral;
import geolaxia.geolaxia.Model.MetalMine;
import geolaxia.geolaxia.Model.Mine;
import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.Player;
import geolaxia.geolaxia.Model.Ship;
import geolaxia.geolaxia.Model.ShipX;
import geolaxia.geolaxia.Model.ShipY;
import geolaxia.geolaxia.Model.ShipZ;
import geolaxia.geolaxia.Model.SolarPanel;
import geolaxia.geolaxia.Model.WhitePlanet;
import geolaxia.geolaxia.Model.WindTurbine;
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

    private void SetRetryPolicy(JsonObjectRequest request){
        int timeout = 10000;//10 segundos
        RetryPolicy policy = new DefaultRetryPolicy(timeout, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
        request.setRetryPolicy(policy);
    }

    @Override
    public void GetNotifications(final String username, final String token, final Player player, final HomeActivity context){
        String url = Constants.getNotifications(player.getId());

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            NotificationsDTO notifications = gSon.fromJson(response.toString(), NotificationsDTO.class);

                            if(Constants.OK_RESPONSE.equals(notifications.getStatus().getResult())) {
                                context.CargarNotificacionesAhora(notifications.getData());
                            } else {
                                context.handleUnexpectedError("Error obteniendo notificaciones", notifications.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            context.handleUnexpectedError("Ocurrio un error", "No se pudo obtener las notificaciones. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                context.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

        req.setRetryPolicy(new DefaultRetryPolicy(
                60000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES,
                DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        Request response = Volley.newRequestQueue(context).add(req);
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
                                act.handleUnexpectedError("Error al intentar loguearse en Geolaxia", playersContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No pudo loguearse en Geolaxia. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
                                act.handleUnexpectedError("Error obteniendo planetas", playersContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener los planetas. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
                                act.handleUnexpectedError("Error", baseContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo registrar el usuario. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
            }
        });
        // add the request object to the queue to be executed
        Request response = Volley.newRequestQueue(act).add(req);
    }

    @Override
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
                                act.handleUnexpectedError("Error obteniendo planetas", planetsContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener los planetas. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

        /*@Override
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
    public void GetAllGalaxies(final String username, final String token, final AttackActivity act, final AttackActivity.AttackFragment fr) {
        String url = Constants.getGalaxiesServiceUrl();

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            GalaxiesDTO galaxiesContainer = new Gson().fromJson(response.toString(), GalaxiesDTO.class);
                            if(Constants.OK_RESPONSE.equals(galaxiesContainer.getStatus().getResult())) {
                                fr.FillGalaxies(galaxiesContainer.getData());
                            } else {
                                act.handleUnexpectedError("Error obteniendo galaxias",galaxiesContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener las galaxias. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void GetAllGalaxies(final String username, final String token, final AttackActivity act, final AttackActivity.CoordinatesFragment fr) {
        String url = Constants.getGalaxiesServiceUrl();

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            GalaxiesDTO galaxiesContainer = new Gson().fromJson(response.toString(), GalaxiesDTO.class);
                            if(Constants.OK_RESPONSE.equals(galaxiesContainer.getStatus().getResult())) {
                                fr.FillGalaxies(galaxiesContainer.getData());
                            } else {
                                act.handleUnexpectedError("Error obteniendo galaxias",galaxiesContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener las galaxias. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void GetSolarSystemsByGalaxy(final String username, final String token, final AttackActivity act, final AttackActivity.AttackFragment fr, final int galaxyId) {
        String url = Constants.getSolarSystemsServiceUrl(galaxyId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            SolarSystemsDTO solarSystemsContainer = new Gson().fromJson(response.toString(), SolarSystemsDTO.class);
                            if(Constants.OK_RESPONSE.equals(solarSystemsContainer.getStatus().getResult())) {
                                fr.FillSolarSystems(solarSystemsContainer.getData());
                            } else {
                                act.handleUnexpectedError("Error obteniendo sistemas solares", solarSystemsContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener los sistemas solares. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void GetSolarSystemsByGalaxy(final String username, final String token, final AttackActivity act, final AttackActivity.CoordinatesFragment fr, final int galaxyId) {
        String url = Constants.getSolarSystemsServiceUrl(galaxyId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            SolarSystemsDTO solarSystemsContainer = new Gson().fromJson(response.toString(), SolarSystemsDTO.class);
                            if(Constants.OK_RESPONSE.equals(solarSystemsContainer.getStatus().getResult())) {
                                fr.FillSolarSystems(solarSystemsContainer.getData());
                            } else {
                                act.handleUnexpectedError("Error obteniendo sistemas solares", solarSystemsContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener los sistemas solares. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void GetPlanetsBySolarSystem(final String username, final String token, final AttackActivity act, final AttackActivity.AttackFragment fr, final int solarSystemId) {
        String url = Constants.getPlanetsbySolarSystemService(solarSystemId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            PlanetsDTO planetsContainer = new Gson().fromJson(response.toString(), PlanetsDTO.class);
                            if(Constants.OK_RESPONSE.equals(planetsContainer.getStatus().getResult())) {
                                fr.FillPlanets(planetsContainer.getData());
                            } else {
                                act.handleUnexpectedError("Error obteniendo planetas", planetsContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener los planetas. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void GetPlanetsBySolarSystem(final String username, final String token, final AttackActivity act, final AttackActivity.CoordinatesFragment fr, final int solarSystemId) {
        String url = Constants.getPlanetsbySolarSystemService(solarSystemId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            PlanetsDTO planetsContainer = new Gson().fromJson(response.toString(), PlanetsDTO.class);
                            if(Constants.OK_RESPONSE.equals(planetsContainer.getStatus().getResult())) {
                                fr.FillPlanets(planetsContainer.getData());
                            } else {
                                act.handleUnexpectedError("Error obteniendo planetas", planetsContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener los planetas. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void GetPlanetFleet(final String username, final String token, final AttackActivity act, final AttackActivity.AttackFragment fr, final int planetId) {
        String url = Constants.getPlanetFleetServiceUrl(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            ShipsDTO shipsContainer = gSon.fromJson(response.toString(), ShipsDTO.class);
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

                                fr.FillFleets(shipsX, shipsY, shipsZ);
                            } else {
                                act.handleUnexpectedError("Error obteniendo las naves", shipsContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener las naves. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void GetPlanetFleet(final String username, final String token, final AttackActivity act, final AttackActivity.CoordinatesFragment fr, final int planetId) {
        String url = Constants.getPlanetFleetServiceUrl(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            ShipsDTO shipsContainer = gSon.fromJson(response.toString(), ShipsDTO.class);
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

                                fr.FillFleets(shipsX, shipsY, shipsZ);
                            } else {
                                act.handleUnexpectedError("Error obteniendo las naves", shipsContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener las naves. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void GetPlanetFleet(final String username, final String token, final AttackActivity act, final AttackActivity.CloseAttackFragment fr, final int planetId) {
        String url = Constants.getPlanetFleetServiceUrl(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            ShipsDTO shipsContainer = gSon.fromJson(response.toString(), ShipsDTO.class);
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

                                fr.FillFleets(shipsX, shipsY, shipsZ);
                            } else {
                                act.handleUnexpectedError("Error obteniendo las naves", shipsContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener las naves. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void SetLastPosition(final String latitude, final String longitude, final Player player, final HomeActivity act) {
        String url = Constants.SetLastPositionServiceUrl();

        JSONObject jsonPlayer = player.toJSONObject();
        JsonObjectRequest req = new JsonObjectRequest(url, jsonPlayer,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
            }
        })
        {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("username", player.getUsername());
                headers.put("token", player.getToken());
                return headers;
            }
        };

        // add the request object to the queue to be executed
        Request response = Volley.newRequestQueue(act).add(req);
    }

    @Override
    public void SetWeather(final String username, final String token, final int weatherDesc, final String weatherWindSpeed, final HomeActivity act) {
        String url = Constants.SetWeatherServiceUrl(weatherDesc, weatherWindSpeed);

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void SetFirebaseToken(final String username, final String token, final String firebaseToken, final HomeActivity act) {
        String url = Constants.SetFirebaseServiceUrl(firebaseToken);

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

    public void GetCloserPlayers(final String username, final String token, final AttackActivity.CloseAttackFragment fr, final AttackActivity act){
        String url = Constants.getCloserPlayersServiceUrl();

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            PlayersDTO playersContainer = new Gson().fromJson(response.toString(), PlayersDTO.class);
                            if(Constants.OK_RESPONSE.equals(playersContainer.getStatus().getResult())) {
                                fr.FillUsers(playersContainer.getData());
                            } else {
                                act.handleUnexpectedError("Error obteniendo jugadores cercanos", playersContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener jugadores cercanos. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
            }
        });

        Request response = Volley.newRequestQueue(act).add(req);
    }

    @Override
    public void Attack(final String username, final String token, final AttackActivity context, final Attack attack) {
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
                                context.handleUnexpectedError("Error realizando el ataque", attackContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            context.handleUnexpectedError("Ocurrio un error", "No se pudo realizar el ataque. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                context.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

    public void GetCurrentMines(final String username, final String token, final int planetId, final ConstructionsActivity act, final ConstructionsActivity.MinesFragment fragment){
        String url = Constants.getCurrentMinesServiceUrl(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            MinesDTO minesContainer = gSon.fromJson(response.toString(), MinesDTO.class);
                            if(Constants.OK_RESPONSE.equals(minesContainer.getStatus().getResult())) {
                                //transformar a planetas segun herencia
                                CrystalMine crystalMine = null;
                                MetalMine metalMine = null;
                                DarkMatterMine darkMatterMine = null;
                                for (Mine mine: minesContainer.getData()) {
                                    switch (mine.getMineType()){
                                        case Constants.MINE_CRYSTAL:
                                            crystalMine = new CrystalMine(mine);
                                            break;
                                        case Constants.MINE_METAL:
                                            metalMine = new MetalMine(mine);
                                            break;
                                        case Constants.SHIP_Z:
                                            darkMatterMine = new DarkMatterMine(mine);
                                            break;
                                    }
                                }

                                fragment.setCurrentValues(crystalMine, metalMine, darkMatterMine);
                            } else {
                                act.handleUnexpectedError("Erro obteniendo minas", minesContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener las minas. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

    public void GetMinesToBuild(final String username, final String token, final int planetId, final ConstructionsActivity act, final ConstructionsActivity.MinesFragment fragment){
        String url = Constants.getMinesToBuildServiceUrl(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            MinesDTO minesContainer = gSon.fromJson(response.toString(), MinesDTO.class);
                            if(Constants.OK_RESPONSE.equals(minesContainer.getStatus().getResult())) {
                                //transformar a planetas segun herencia
                                CrystalMine crystalMine = null;
                                MetalMine metalMine = null;
                                DarkMatterMine darkMatterMine = null;
                                for (Mine mine: minesContainer.getData()) {
                                    switch (mine.getMineType()){
                                        case Constants.MINE_CRYSTAL:
                                            crystalMine = new CrystalMine(mine);
                                            break;
                                        case Constants.MINE_METAL:
                                            metalMine = new MetalMine(mine);
                                            break;
                                        case Constants.SHIP_Z:
                                            darkMatterMine = new DarkMatterMine(mine);
                                            break;
                                    }
                                }

                                fragment.setCosts(crystalMine, metalMine, darkMatterMine);
                            } else {
                                act.handleUnexpectedError("Error obteniendo minas", minesContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener las minas. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

    public void GetBuildingTime(final String username, final String token, final int planetId, final ConstructionsActivity act, final ConstructionsActivity.MinesFragment fragment){
        String url = Constants.getMineBuildingTimeServiceUrl(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            IsBuildingFaciltyDTO buildingTimesContainer = gSon.fromJson(response.toString(), IsBuildingFaciltyDTO.class);
                            if(Constants.OK_RESPONSE.equals(buildingTimesContainer.getStatus().getResult())) {
                                ArrayList<Long> times = buildingTimesContainer.getData();
                                if(times.size() == 3){
                                    fragment.setCurrentConstructionTimers(times.get(0), times.get(1), times.get(2));
                                }
                            } else {
                                act.handleUnexpectedError("Error obteniendo minas", buildingTimesContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener las minas. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

    public void BuildMine(final String username, final String token, final Mine mine, final ConstructionsActivity context, final ConstructionsActivity.MinesFragment fragment){
        String url = Constants.getBuildMineServiceUrl();

        JSONObject jsonMine = mine.toJSONObject();
        JsonObjectRequest req = new JsonObjectRequest(url, jsonMine,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            MineDTO mineContainer = gSon.fromJson(response.toString(), MineDTO.class);
                            if(Constants.OK_RESPONSE.equals(mineContainer.getStatus().getResult())) {
                                fragment.MineBuilt(mineContainer.getData());
                            } else {
                                context.handleUnexpectedError("Error construyendo mina", mineContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            context.handleUnexpectedError("Ocurrio un error", "No se pudo construir la mina. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                context.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

    public void GetCurrentEnergyFacilities(final String username, final String token, final int planetId, final ConstructionsActivity act, final ConstructionsActivity.EnergyFragment fragment){
        String url = Constants.getCurrentEnergyFacilitiesServiceUrl(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            EnergyFacilitiesAllDTO energyFacilitiesContainer = gSon.fromJson(response.toString(), EnergyFacilitiesAllDTO.class);
                            if(Constants.OK_RESPONSE.equals(energyFacilitiesContainer.getStatus().getResult())) {
                                //transformar segun herencia
                                EnergyCentral energyCentral = null;
                                EnergyFuelCentral energyFuelCentral = null;
                                ArrayList<SolarPanel> solarPanels = new ArrayList<>();
                                ArrayList<WindTurbine> windTurbines = new ArrayList<>();
                                for (ArrayList<EnergyFacility> energyFacilityList: energyFacilitiesContainer.getData()) {
                                    for (EnergyFacility energyFacility : energyFacilityList) {
                                        switch (energyFacility.getEnergyFacilityType()){
                                            case Constants.EF_ENERGY_CENTRAL:
                                                energyCentral = new EnergyCentral(energyFacility);
                                                break;
                                            case Constants.EF_ENERGY_FUEL_CENTRAL:
                                                int darkMatterConsumption = 25 * (energyFacility.getLevel() == 0 ? 0 : energyFacility.getLevel() - 1) + 10 * (energyFacility.getLevel() == 1 ? 1 : 0);
                                                energyFuelCentral = new EnergyFuelCentral(energyFacility, darkMatterConsumption);
                                                break;
                                            case Constants.EF_SOLAR_PANEL:
                                                SolarPanel solarPanel = new SolarPanel(energyFacility, 10, 25, 5);
                                                solarPanels.add(solarPanel);
                                                break;
                                            case Constants.EF_WIND_TURBINE:
                                                WindTurbine windTurbine = new WindTurbine(energyFacility, 15, 25, 10);
                                                windTurbines.add(windTurbine);
                                                break;
                                        }
                                    }
                                }

                                fragment.setCurrentValues(energyCentral, energyFuelCentral, solarPanels, windTurbines);
                            } else {
                                act.handleUnexpectedError("Error obteniendo construcciones de energia", energyFacilitiesContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener las construcciones de energia. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

    public void GetEnergyFacilitiesToBuild(final String username, final String token, final int planetId, final ConstructionsActivity act, final ConstructionsActivity.EnergyFragment fragment){
        String url = Constants.getEnergyFacilitiesToBuildServiceUrl(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            EnergyFacilitiesDTO energyFacilitiesContainer = gSon.fromJson(response.toString(), EnergyFacilitiesDTO.class);
                            if(Constants.OK_RESPONSE.equals(energyFacilitiesContainer.getStatus().getResult())) {
                                //transformar segun herencia
                                EnergyCentral energyCentral = null;
                                EnergyFuelCentral energyFuelCentral = null;
                                ArrayList<SolarPanel> solarPanels = new ArrayList<>();
                                ArrayList<WindTurbine> windTurbines = new ArrayList<>();
                                for (EnergyFacility energyFacility: energyFacilitiesContainer.getData()) {
                                    switch (energyFacility.getEnergyFacilityType()){
                                        case Constants.EF_ENERGY_CENTRAL:
                                            energyCentral = new EnergyCentral(energyFacility);
                                            break;
                                        case Constants.EF_ENERGY_FUEL_CENTRAL:
                                            int darkMatterConsumption = 25 * (energyFacility.getLevel() - 1) + 10 * (energyFacility.getLevel() == 1 ? 1 : 0);
                                            energyFuelCentral = new EnergyFuelCentral(energyFacility, darkMatterConsumption);
                                            break;
                                    }
                                }

                                fragment.setCosts(energyCentral, energyFuelCentral);
                            } else {
                                act.handleUnexpectedError("Error obteniendo construcciones de energia", energyFacilitiesContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener las construcciones de energia. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

    public void GetEnergyFacilitiesBuildingTime(final String username, final String token, final int planetId, final ConstructionsActivity act, final ConstructionsActivity.EnergyFragment fragment){
        String url = Constants.getEnergyFacilitiesBuildingTimeServiceUrl(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            IsBuildingFaciltyDTO isBuildingContainer = gSon.fromJson(response.toString(), IsBuildingFaciltyDTO.class);
                            if(Constants.OK_RESPONSE.equals(isBuildingContainer.getStatus().getResult())) {
                                ArrayList<Long> times = isBuildingContainer.getData();
                                if(times.size() == 4){
                                    fragment.setCurrentConstructionTimers(times.get(0), times.get(1), times.get(2), times.get(3));
                                }
                            } else {
                                act.handleUnexpectedError("Error obteniendo construcciones de energia", isBuildingContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener las construcciones de energia. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

    public void BuildEnergyFacility(final String username, final String token, final EnergyFacility energyFacility, final ConstructionsActivity context, final ConstructionsActivity.EnergyFragment fragment){
        String url = Constants.getBuildEnergyFacilityServiceUrl();

        JSONObject jsonEnergyFacility = energyFacility.toJSONObject();
        JsonObjectRequest req = new JsonObjectRequest(url, jsonEnergyFacility,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            EnergyFacilityDTO energyFacilitiesContainer = gSon.fromJson(response.toString(), EnergyFacilityDTO.class);
                            if(Constants.OK_RESPONSE.equals(energyFacilitiesContainer.getStatus().getResult())) {
                                fragment.EnergyFacilityBuilt(energyFacilitiesContainer.getData());
                            } else {
                                context.handleUnexpectedError("Error construyendo central de energia", energyFacilitiesContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            context.handleUnexpectedError("Ocurrio un error", "No se pudo construir la central de energia. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                context.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

    // -*-*-*-*
    // DEFENSE.
    // -*-*-*-*
    @Override
    public void BuildSolarPanels(final String username, final String token, final ConstructionsActivity.EnergyFragment fragment, final ConstructionsActivity context, int planetId, int qtt) {
        String url = Constants.getBuildSolarPanelsServiceUrl(planetId, qtt);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            EnergyFacilityDTO solarPanelContainer = gSon.fromJson(response.toString(), EnergyFacilityDTO.class);

                            if(Constants.OK_RESPONSE.equals(solarPanelContainer.getStatus().getResult())) {
                                fragment.EnergyFacilityBuilt(solarPanelContainer.getData());
                            } else {
                                context.handleUnexpectedError("Error construyendo panel solar", solarPanelContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            context.handleUnexpectedError("Ocurrio un error", "No se pudo construir el panel solar. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                context.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

        Request response = Volley.newRequestQueue(context).add(req);
    }

    @Override
    public void BuildWindTurbines(final String username, final String token, final ConstructionsActivity.EnergyFragment fragment, final ConstructionsActivity context, int planetId, int qtt) {
        String url = Constants.getBuildWindTurbinesServiceUrl(planetId, qtt);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            EnergyFacilityDTO windTurbineContainer = gSon.fromJson(response.toString(), EnergyFacilityDTO.class);

                            if(Constants.OK_RESPONSE.equals(windTurbineContainer.getStatus().getResult())) {
                                fragment.EnergyFacilityBuilt(windTurbineContainer.getData());
                            } else {
                                context.handleUnexpectedError("Error construyendo turbina eolica", windTurbineContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            context.handleUnexpectedError("Ocurrio un error", "No se pudo construir la turbina eolica. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                context.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

        Request response = Volley.newRequestQueue(context).add(req);
    }

    @Override
    public void GetShieldStatus(final String username, final String token, final DefenseActivity context, int planetId) {
        String url = Constants.getShieldStatus(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            ShieldDTO shield = gSon.fromJson(response.toString(), ShieldDTO.class);

                            if(Constants.OK_RESPONSE.equals(shield.getStatus().getResult())) {
                                context.CargarEstadoEscudoAhora(shield.getData());
                            } else {
                                context.handleUnexpectedError("Error obteniendo escudo", shield.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            context.handleUnexpectedError("Ocurrio un error", "No se pudo obtener el escudo. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                context.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

        Request response = Volley.newRequestQueue(context).add(req);
    }

    @Override
    public void GetCannons(final String username, final String token, final DefenseActivity context, int planetId) {
        String url = Constants.getCannons(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            CannonsDTO cannons = gSon.fromJson(response.toString(), CannonsDTO.class);

                            if(Constants.OK_RESPONSE.equals(cannons.getStatus().getResult())) {
                                context.CargarCanonesAhora(cannons.getData());
                            } else {
                                context.handleUnexpectedError("Error obteniendo cañones", cannons.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            context.handleUnexpectedError("Ocurrio un error", "No se pudo obtener los cañones. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                context.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

        Request response = Volley.newRequestQueue(context).add(req);
    }

    @Override
    public void BuildCannons(final String username, final String token, final DefenseActivity context, int planetId, int cant) {
        String url = Constants.BuildCannons(planetId, cant);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            BaseDTO base = gSon.fromJson(response.toString(), BaseDTO.class);

                            if(Constants.OK_RESPONSE.equals(base.getStatus().getResult())) {
                                context.CargarTiempoConstruccionCanonesAhora();
                            } else {
                                context.handleUnexpectedError("Error construyendo cañon",base.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            context.handleUnexpectedError("Ocurrio un error", "No se pudo construir el cañon. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                context.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

        Request response = Volley.newRequestQueue(context).add(req);
    }

    @Override
    public void IsBuildingCannons(final String username, final String token, final DefenseActivity context, int planetId) {
        String url = Constants.isBuldingCannons(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            IsBuildingCannonsDTO building = gSon.fromJson(response.toString(), IsBuildingCannonsDTO.class);

                            if(Constants.OK_RESPONSE.equals(building.getStatus().getResult())) {
                                context.EstaConstruyendoCanonesAhora(building);
                            } else {
                                context.handleUnexpectedError("Error obteniendo estado cañones", building.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            context.handleUnexpectedError("Ocurrio un error", "No se pudo obtener el estado de los cañones. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                context.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

        Request response = Volley.newRequestQueue(context).add(req);
    }

    @Override
    public void Get3RandomQuestions(final String username, final String token, final DefenseQuestionActivity context) {
        String url = Constants.getRandomQuestions();

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            QuestionDTO questions = gSon.fromJson(response.toString(), QuestionDTO.class);

                            if(Constants.OK_RESPONSE.equals(questions.getStatus().getResult())) {
                                context.CargarPreguntasAhora(questions.getData());
                            } else {
                                context.handleUnexpectedError("Error obteniendo preguntas", questions.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            context.handleUnexpectedError("Ocurrio un error", "No se pudo obtener las preguntas. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                context.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

        Request response = Volley.newRequestQueue(context).add(req);
    }

    /*@Override
    public void Get3RandomQuestions(final String username, final String token, final DefenseQuestionActivity context, final int attackId) {
        *//*String url = Constants.getRandomQuestionsConAtaque(attackId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            QuestionDTO questions = gSon.fromJson(response.toString(), QuestionDTO.class);

                            if(Constants.OK_RESPONSE.equals(questions.getStatus().getResult())) {
                                context.CargarPreguntasAhora(questions.getData());
                            } else {
                                context.handleUnexpectedError("Error obteniendo preguntas", questions.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            context.handleUnexpectedError("Ocurrio un error", "No se pudo obtener las preguntas. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                context.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

        Request response = Volley.newRequestQueue(context).add(req);*//*
    }*/

    @Override
    public  void ObtenerAtaqueMasProximoNoDefendido(final String username, final String token, final DefenseActivity context, final int planetId) {
        String url = Constants.getAttackIdNoDefendido(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            AttackIdDTO attack = gSon.fromJson(response.toString(), AttackIdDTO.class);

                            if(Constants.OK_RESPONSE.equals(attack.getStatus().getResult())) {
                                context.EstaRecibiendoAtaqueAhora(attack);
                            } else {
                                context.handleUnexpectedError("Error obteniendo el ataque para su defensa", attack.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            context.handleUnexpectedError("Ocurrio un error", "No se pudo obtener el ataque para su defensa. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                context.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

        Request response = Volley.newRequestQueue(context).add(req);
    }

    @Override
    public void DefenseFromAttack(final String username, final String token, final DefenseQuestionActivity context, final int attackId, final int idPregunta1, final int idPregunta2, final int idPregunta3, final int cantidadCorrectas) {
        String url = Constants.defenseFromAttack(attackId, idPregunta1, idPregunta2, idPregunta3, cantidadCorrectas);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            /*AttackIdDTO attack = gSon.fromJson(response.toString(), AttackIdDTO.class);

                            if(Constants.OK_RESPONSE.equals(attack.getStatus().getResult())) {
                                context.EstaRecibiendoAtaqueAhora(attack);
                            } else {
                                context.handleUnexpectedError("Error obteniendo el ataque para su defensa", attack.getStatus().getDescription());
                            }*/
                        }catch (Exception e){
                            context.handleUnexpectedError("Ocurrio un error", "No se pudo obtener el ataque para su defensa. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                context.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

        Request response = Volley.newRequestQueue(context).add(req);
    }

    // -*-*-*-*-*-*-
    // CONSTRUCTION.
    // -*-*-*-*-*-*-
    @Override
    public void GetCurrentHangar(final String username, final String token, final int planetId, final MilitaryConstructionsActivity act, final MilitaryConstructionsActivity.HangarFragment fragment){
        String url = Constants.getCurrentHangarServiceUrl(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            HangarDTO hangarContainer = gSon.fromJson(response.toString(), HangarDTO.class);
                            if(Constants.OK_RESPONSE.equals(hangarContainer.getStatus().getResult())) {
                                fragment.setHangarValues(hangarContainer.getData());
                            } else {
                                act.handleUnexpectedError("Error obteniendo hangar", hangarContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener el hangar. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void GetHangarBuildingTime(final String username, final String token, final int planetId, final MilitaryConstructionsActivity act, final MilitaryConstructionsActivity.HangarFragment fragment){
        String url = Constants.getHangarBuildingTimeServiceUrl(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            IsBuildingFaciltyDTO hangarBuildingContainer = gSon.fromJson(response.toString(), IsBuildingFaciltyDTO.class);
                            if(Constants.OK_RESPONSE.equals(hangarBuildingContainer.getStatus().getResult())) {
                                fragment.SetCurrentConstructionValues(hangarBuildingContainer.getData().get(0));
                            } else {
                                act.handleUnexpectedError("Error obteniendo hangar", hangarBuildingContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener el hangar. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void BuildHangar(final String username, final String token, final int planetId, final MilitaryConstructionsActivity context, final MilitaryConstructionsActivity.HangarFragment fragment){
        String url = Constants.getBuildHangarServiceUrl(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            HangarDTO hangarContainer = gSon.fromJson(response.toString(), HangarDTO.class);
                            if(Constants.OK_RESPONSE.equals(hangarContainer.getStatus().getResult())) {
                                fragment.HangarBuilt(hangarContainer.getData());
                            } else {
                                context.handleUnexpectedError("Error construyendo hangar", hangarContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            context.handleUnexpectedError("Ocurrio un error", "No se pudo construir el hangar de energia. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                context.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

    @Override
    public void GetPlanetFleet(final String username, final String token, final MilitaryConstructionsActivity act, final MilitaryConstructionsActivity.ShipsFragment fr, final int planetId) {
        String url = Constants.getShipsServiceUrl(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            ShipsDTO shipsContainer = gSon.fromJson(response.toString(), ShipsDTO.class);
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

                                fr.setCurrentValues(shipsX, shipsY, shipsZ);
                            } else {
                                act.handleUnexpectedError("Error obteniendo las naves", shipsContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener las naves. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void GetShipsCost(final String username, final String token, final MilitaryConstructionsActivity act, final MilitaryConstructionsActivity.ShipsFragment fr) {
        String url = Constants.getShipsCostServiceUrl();

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            ShipsDTO shipsContainer = gSon.fromJson(response.toString(), ShipsDTO.class);
                            if(Constants.OK_RESPONSE.equals(shipsContainer.getStatus().getResult())) {
                                //transformar a naves segun herencia
                                ShipX shipX = null;
                                ShipY shipY = null;
                                ShipZ shipZ = null;
                                for (Ship ship: shipsContainer.getData()) {
                                    switch (ship.getShipType()){
                                        case Constants.SHIP_X:
                                            shipX = new ShipX(ship);
                                            break;
                                        case Constants.SHIP_Y:
                                            shipY = new ShipY(ship);
                                            break;
                                        case Constants.SHIP_Z:
                                            shipZ = new ShipZ(ship);
                                            break;
                                    }
                                }

                                fr.setCosts(shipX, shipY, shipZ);
                            } else {
                                act.handleUnexpectedError("Error obteniendo las naves", shipsContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener las naves. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void GetShipsBuildingTime(final String username, final String token, final int planetId, final MilitaryConstructionsActivity act, final MilitaryConstructionsActivity.ShipsFragment fragment){
        String url = Constants.getShipBuildingTimeServiceUrl(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            IsBuildingFaciltyDTO shipBuildingContainer = gSon.fromJson(response.toString(), IsBuildingFaciltyDTO.class);
                            if(Constants.OK_RESPONSE.equals(shipBuildingContainer.getStatus().getResult())) {
                                ArrayList<Long> times = shipBuildingContainer.getData();
                                if(times.size() == 3){
                                    fragment.setCurrentConstructionValues(times.get(0), times.get(1), times.get(2));
                                }
                            } else {
                                act.handleUnexpectedError("Error obteniendo hangar", shipBuildingContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener el hangar. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void BuildShips(final String username, final String token, final MilitaryConstructionsActivity act, final MilitaryConstructionsActivity.ShipsFragment fragment, final int planetId, final int qtt, final int shipType) {
        String url = Constants.getBuildShipsServiceUrl(planetId, qtt, shipType);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            ShipDTO shipContainer = gSon.fromJson(response.toString(), ShipDTO.class);

                            if(Constants.OK_RESPONSE.equals(shipContainer.getStatus().getResult())) {
                                fragment.ShipBuilt(shipContainer.getData());
                            } else {
                                act.handleUnexpectedError("Error construyendo turbina eolica", shipContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo construir la turbina eolica. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

        Request response = Volley.newRequestQueue(act).add(req);
    }

    @Override
    public void GetCurrentShield(final String username, final String token, final int planetId, final MilitaryConstructionsActivity act, final MilitaryConstructionsActivity.OthersFragment fragment){
        String url = Constants.getCurrentShieldServiceUrl(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            ShieldDTO shieldContainer = gSon.fromJson(response.toString(), ShieldDTO.class);
                            if(Constants.OK_RESPONSE.equals(shieldContainer.getStatus().getResult())) {
                                fragment.setShieldValues(shieldContainer.getData());
                            } else {
                                act.handleUnexpectedError("Error obteniendo escudo", shieldContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener el escudo. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void GetCurrentProbes(final String username, final String token, final int planetId, final MilitaryConstructionsActivity act, final MilitaryConstructionsActivity.OthersFragment fragment){
        String url = Constants.getCurrentProbesServiceUrl(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            ProbesDTO probesContainer = gSon.fromJson(response.toString(), ProbesDTO.class);
                            if(Constants.OK_RESPONSE.equals(probesContainer.getStatus().getResult())) {
                                fragment.setProbesValues(probesContainer.getData());
                            } else {
                                act.handleUnexpectedError("Error obteniendo sondas", probesContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener las sondas. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void GetCurrentTraders(final String username, final String token, final int planetId, final MilitaryConstructionsActivity act, final MilitaryConstructionsActivity.OthersFragment fragment){
        String url = Constants.getCurrentTradersServiceUrl(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            TradersDTO tradersContainer = gSon.fromJson(response.toString(), TradersDTO.class);
                            if(Constants.OK_RESPONSE.equals(tradersContainer.getStatus().getResult())) {
                                fragment.setTradersValues(tradersContainer.getData());
                            } else {
                                act.handleUnexpectedError("Error obteniendo escudo", tradersContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener el escudo. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void GetOthersBuildingTime(final String username, final String token, final int planetId, final MilitaryConstructionsActivity act, final MilitaryConstructionsActivity.OthersFragment fragment){
        String url = Constants.getOthersBuildingTimeServiceUrl(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            IsBuildingFaciltyDTO shipBuildingContainer = gSon.fromJson(response.toString(), IsBuildingFaciltyDTO.class);
                            if(Constants.OK_RESPONSE.equals(shipBuildingContainer.getStatus().getResult())) {
                                ArrayList<Long> times = shipBuildingContainer.getData();
                                if(times.size() == 3){
                                    fragment.setCurrentConstructionValues(times.get(0), times.get(1), times.get(2));
                                }
                            } else {
                                act.handleUnexpectedError("Error obteniendo hangar", shipBuildingContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener el hangar. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void BuildShield(final String username, final String token, final int planetId, final MilitaryConstructionsActivity context, final MilitaryConstructionsActivity.OthersFragment fragment){
        String url = Constants.getBuildShieldServiceUrl(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            ShieldDTO shieldContainer = gSon.fromJson(response.toString(), ShieldDTO.class);
                            if(Constants.OK_RESPONSE.equals(shieldContainer.getStatus().getResult())) {
                                fragment.ShieldBuilt(shieldContainer.getData());
                            } else {
                                context.handleUnexpectedError("Error construyendo hangar", shieldContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            context.handleUnexpectedError("Ocurrio un error", "No se pudo construir el hangar de energia. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                context.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

    @Override
    public void BuildProbes(final String username, final String token, final MilitaryConstructionsActivity.OthersFragment fragment, final MilitaryConstructionsActivity context, int planetId, int qtt) {
        String url = Constants.getBuildProbesServiceUrl(planetId, qtt);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            ProbeDTO probeContainer = gSon.fromJson(response.toString(), ProbeDTO.class);

                            if(Constants.OK_RESPONSE.equals(probeContainer.getStatus().getResult())) {
                                fragment.ProbeBuilt(probeContainer.getData());
                            } else {
                                context.handleUnexpectedError("Error construyendo turbina eolica", probeContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            context.handleUnexpectedError("Ocurrio un error", "No se pudo construir la turbina eolica. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                context.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

        Request response = Volley.newRequestQueue(context).add(req);
    }

    @Override
    public void BuildTraders(final String username, final String token, final MilitaryConstructionsActivity.OthersFragment fragment, final MilitaryConstructionsActivity context, int planetId, int qtt) {
        String url = Constants.getBuildTradersServiceUrl(planetId, qtt);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            TraderDTO traderContainer = gSon.fromJson(response.toString(), TraderDTO.class);

                            if(Constants.OK_RESPONSE.equals(traderContainer.getStatus().getResult())) {
                                fragment.TraderBuilt(traderContainer.getData());
                            } else {
                                context.handleUnexpectedError("Error construyendo cargueros", traderContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            context.handleUnexpectedError("Ocurrio un error", "No se pudo construir el carguero. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                context.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

        Request response = Volley.newRequestQueue(context).add(req);
    }

    // -*-*-*-*-
    // COLONIZE.
    // -*-*-*-*-
    @Override
    public void GetAllGalaxies(final String username, final String token, final ColonizeActivity act, final ColonizeActivity.ColonizeFragment context){
        String url = Constants.getGalaxiesServiceUrl();

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            GalaxiesDTO galaxiesContainer = new Gson().fromJson(response.toString(), GalaxiesDTO.class);
                            if(Constants.OK_RESPONSE.equals(galaxiesContainer.getStatus().getResult())) {
                                context.FillGalaxies(galaxiesContainer.getData());
                            } else {
                                act.handleUnexpectedError("Error obteniendo galaxias", galaxiesContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener las galaxias. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void GetSolarSystemsByGalaxy(final String username, final String token, final ColonizeActivity act, final ColonizeActivity.ColonizeFragment context, final int galaxyId) {
        String url = Constants.getSolarSystemsServiceUrl(galaxyId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            SolarSystemsDTO solarSystemsContainer = new Gson().fromJson(response.toString(), SolarSystemsDTO.class);
                            if(Constants.OK_RESPONSE.equals(solarSystemsContainer.getStatus().getResult())) {
                                context.FillSolarSystems(solarSystemsContainer.getData());
                            } else {
                                act.handleUnexpectedError("Error obteniendo sistemas solares", solarSystemsContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener los sistemas solares. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void GetPlanetsBySolarSystem(final String username, final String token, final ColonizeActivity act, final ColonizeActivity.ColonizeFragment context, final int solarSystemId) {
        String url = Constants.getPlanetsbySolarSystemService(solarSystemId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            PlanetsDTO planetsContainer = new Gson().fromJson(response.toString(), PlanetsDTO.class);
                            if(Constants.OK_RESPONSE.equals(planetsContainer.getStatus().getResult())) {
                                context.FillPlanets(planetsContainer.getData());
                            } else {
                                act.handleUnexpectedError("Error obteniendo planetas", planetsContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener los planetas. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void GetColonizers(final String username, final String token, final ColonizeActivity act, final ColonizeActivity.ColonizeFragment context, int planetId) {
        String url = Constants.getColonizers(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            ProbesDTO colonizers = gSon.fromJson(response.toString(), ProbesDTO.class);

                            if(Constants.OK_RESPONSE.equals(colonizers.getStatus().getResult())) {
                                context.CargarColonizadoresAhora(colonizers.getData());
                            } else {
                                act.handleUnexpectedError("Error obteniendo sondas", colonizers.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener las sondas. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

        Request response = Volley.newRequestQueue(act).add(req);
    }

    @Override
    public void SendColonizer(final String username, final String token, final ColonizeActivity act, final ColonizeActivity.ColonizeFragment context, final int planetId, final int planetIdTarget, final long time){
        String url = Constants.sendColonizer(planetId, planetIdTarget, time);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            BaseDTO base = gSon.fromJson(response.toString(), BaseDTO.class);

                            if(Constants.OK_RESPONSE.equals(base.getStatus().getResult())) {
                                context.CargarTiempoEnvioColonizadorAhora();
                            } else {
                                act.handleUnexpectedError("Error enviando sonda", base.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo enviar la sonda. Intenten nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

        Request response = Volley.newRequestQueue(act).add(req);
    }

    @Override
    public void IsSendingColonizer(final String username, final String token, final ColonizeActivity act, final ColonizeActivity.ColonizeFragment context, final int planetId){
        String url = Constants.isSendingColonizer(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            IsSendingColonizerDTO sending = gSon.fromJson(response.toString(), IsSendingColonizerDTO.class);

                            if(Constants.OK_RESPONSE.equals(sending.getStatus().getResult())) {
                                context.EstaEnviandoColonizadorAhora(sending);
                            } else {
                                act.handleUnexpectedError("Error obteniendo estado de sonda", sending.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener el estado de la sonda. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

        Request response = Volley.newRequestQueue(act).add(req);
    }

    //-----------------
    @Override
    public void GetAllGalaxies(final String username, final String token, final ColonizeActivity act, final ColonizeActivity.CoordinatesFragment context) {
        String url = Constants.getGalaxiesServiceUrl();

        JsonObjectRequest req = new JsonObjectRequest(url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            GalaxiesDTO galaxiesContainer = new Gson().fromJson(response.toString(), GalaxiesDTO.class);
                            if(Constants.OK_RESPONSE.equals(galaxiesContainer.getStatus().getResult())) {
                                context.FillGalaxies(galaxiesContainer.getData());
                            } else {
                                act.handleUnexpectedError("Error obteniendo galaxias", galaxiesContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener las galaxias. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void GetSolarSystemsByGalaxy(final String username, final String token, final ColonizeActivity act, final ColonizeActivity.CoordinatesFragment context, final int galaxyId) {
        String url = Constants.getSolarSystemsServiceUrl(galaxyId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            SolarSystemsDTO solarSystemsContainer = new Gson().fromJson(response.toString(), SolarSystemsDTO.class);
                            if(Constants.OK_RESPONSE.equals(solarSystemsContainer.getStatus().getResult())) {
                                context.FillSolarSystems(solarSystemsContainer.getData());
                            } else {
                                act.handleUnexpectedError("Error obteniendo sistemas solares", solarSystemsContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener los sistemas solares. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void GetPlanetsBySolarSystem(final String username, final String token, final ColonizeActivity act, final ColonizeActivity.CoordinatesFragment context, final int solarSystemId) {
        String url = Constants.getPlanetsbySolarSystemService(solarSystemId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            PlanetsDTO planetsContainer = new Gson().fromJson(response.toString(), PlanetsDTO.class);
                            if(Constants.OK_RESPONSE.equals(planetsContainer.getStatus().getResult())) {
                                context.FillPlanets(planetsContainer.getData());
                            } else {
                                act.handleUnexpectedError("Error obteniendo planetas", planetsContainer.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener los planetas. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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
    public void GetColonizers(final String username, final String token, final ColonizeActivity act, final ColonizeActivity.CoordinatesFragment context, final int planetId) {
        String url = Constants.getColonizers(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            ProbesDTO colonizers = gSon.fromJson(response.toString(), ProbesDTO.class);

                            if(Constants.OK_RESPONSE.equals(colonizers.getStatus().getResult())) {
                                context.CargarColonizadoresAhora(colonizers.getData());
                            } else {
                                act.handleUnexpectedError("Error obteniendo sondas", colonizers.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener las sondas. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

        Request response = Volley.newRequestQueue(act).add(req);
    }

    @Override
    public void IsSendingColonizer(final String username, final String token, final ColonizeActivity act, final ColonizeActivity.CoordinatesFragment context, final int planetId) {
        String url = Constants.isSendingColonizer(planetId);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            IsSendingColonizerDTO sending = gSon.fromJson(response.toString(), IsSendingColonizerDTO.class);

                            if(Constants.OK_RESPONSE.equals(sending.getStatus().getResult())) {
                                context.EstaEnviandoColonizadorAhora(sending);
                            } else {
                                act.handleUnexpectedError("Error obteniendo estado de la sonda", sending.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo obtener el estado de la sonda. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

        Request response = Volley.newRequestQueue(act).add(req);
    }

    @Override
    public void SendColonizer(final String username, final String token, final ColonizeActivity act, final ColonizeActivity.CoordinatesFragment context, final int planetId, final int planetIdTarget, final long time) {
        String url = Constants.sendColonizer(planetId, planetIdTarget, time);

        JsonObjectRequest req = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            Gson gSon=  new GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create();
                            BaseDTO base = gSon.fromJson(response.toString(), BaseDTO.class);

                            if(Constants.OK_RESPONSE.equals(base.getStatus().getResult())) {
                                context.CargarTiempoEnvioColonizadorAhora();
                            } else {
                                act.handleUnexpectedError("Error enviando sonda", base.getStatus().getDescription());
                            }
                        }catch (Exception e){
                            act.handleUnexpectedError("Ocurrio un error", "No se pudo enviar sonda. Intente nuevamente");
                        }
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                act.handleUnexpectedError("Error de conexion", "No se pudo conectar. Intente nuevamente");
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

        Request response = Volley.newRequestQueue(act).add(req);
    }
}
