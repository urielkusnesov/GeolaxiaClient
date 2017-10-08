package geolaxia.geolaxia.Model;

/**
 * Created by uriel on 15/4/2017.
 */

public class Constants {
    public static String BASE_URL = "192.168.1.102";/*Server IP*/
    //public static String BASE_URL = "192.168.0.11";/*Server IP*/
    public static final String SERVER_URL_PROPERTY = "SERVER_URL";
    public static final String SERVER_IP_REQUEST = "http://martinhernan.xyz/ip";
    public static final String PORT = ":62078/";
    public static final String HTTP = "http://";
    public static final String LOGIN_SERVICE = "api/player/login";
    public static final String FACEBOOK_LOGIN_SERVICE = "api/player/facebooklogin";
    public static final String LOGOUT_SERVICE = "api/logout";
    public static final String REGISTER_SERVICE = "api/player/register";
    public static final String PLANETSBYPLAYER_SERVICE = "api/planet/getbyplayer";
    public static final String PLANET_SERVICE = "api/planet/getbyId";
    public static final String GALAXIES_SERVICE = "api/attack/galaxies";
    public static final String SOLARSYSTEMS_SERVICE = "api/attack/solarsystems";
    public static final String PLANETSS_SERVICE = "api/attack/planetsbyss";
    public static final String FLEET_SERVICE = "api/attack/fleet";
    public static final String LAST_POSITION_SERVICE = "api/player/setposition";
    public static final String CLOSER_PLAYERS_SERVICE = "api/attack/closerplayers";
    public static final String WEATHER_SERVICE = "http://api.openweathermap.org/data/2.5/weather";
    public static final String ATTACK_SERVICE = "api/attack/attack";
    public static final String MINES_CURRENT_SERVICE = "api/construction/currentmines";
    public static final String MINES_TO_BUILD_SERVICE = "api/construction/minestobuild";
    public static final String MINES_BUILD_SERVICE = "api/construction/buildmine";
    public static final String DEFENSE_GETCANNONS_SERVICE = "api/defense/getCannons";
    public static final long MINIMUM_REQUEST_IP_TIME = 5000; // 5s = 5000ms

    /*URLs*/
    public static String getLoginServiceUrl() { return (HTTP + BASE_URL + PORT + LOGIN_SERVICE); }
    public static String getFacebookLoginServiceUrl() { return (HTTP + BASE_URL + PORT + FACEBOOK_LOGIN_SERVICE); }
    public static String getLogoutServiceUrl() { return (HTTP + BASE_URL + PORT + LOGOUT_SERVICE); }
    public static String getRegisterServiceUrl() { return (HTTP + BASE_URL + PORT + REGISTER_SERVICE); }
    public static String getPlanetsByPlayerServiceUrl() { return (HTTP + BASE_URL + PORT + PLANETSBYPLAYER_SERVICE); }
    public static String getPlanetServiceUrl() { return (HTTP + BASE_URL + PORT + PLANET_SERVICE); }
    public static String getGalaxiesServiceUrl() { return (HTTP + BASE_URL + PORT + GALAXIES_SERVICE); }
    public static String getSolarSystemsServiceUrl(int galaxyId) { return (HTTP + BASE_URL + PORT + SOLARSYSTEMS_SERVICE + "?galaxyId=" + String.valueOf(galaxyId)); }
    public static String getPlanetsbySolarSystemService(int solarSystemId) { return (HTTP + BASE_URL + PORT + PLANETSS_SERVICE + "?solarSystemId=" + String.valueOf(solarSystemId)); }
    public static String getPlanetFleetServiceUrl(int planetId) { return (HTTP + BASE_URL + PORT + FLEET_SERVICE + "?planetId=" + String.valueOf(planetId)); }
    public static String SetLastPositionServiceUrl() { return (HTTP + BASE_URL + PORT + LAST_POSITION_SERVICE); }
    public static String getCloserPlayersServiceUrl() { return (HTTP + BASE_URL + PORT + CLOSER_PLAYERS_SERVICE); }
    public static String getWeatherServiceUrl(String latitude, String longitude) { return (WEATHER_SERVICE + "?lat=" + latitude + "&lon=" + longitude + "&units=metric&APPID=a6e31252461e3c4a9ccaf2bcb32740c5"); }
    public static String getAttackServiceUrl() { return (HTTP + BASE_URL + PORT + ATTACK_SERVICE); }
    public static String getCurrentMinesServiceUrl(int planetId) { return (HTTP + BASE_URL + PORT + MINES_CURRENT_SERVICE + "?planetId=" + String.valueOf(planetId)); }
    public static String getMinesToBuildServiceUrl(int planetId) { return (HTTP + BASE_URL + PORT + MINES_TO_BUILD_SERVICE + "?planetId=" + String.valueOf(planetId)); }
    public static String getBuildMineServiceUrl() { return (HTTP + BASE_URL + PORT + MINES_BUILD_SERVICE); }
    public static String getCannons(int planetId) { return (HTTP + BASE_URL + PORT + DEFENSE_GETCANNONS_SERVICE + "?planetId=" + String.valueOf(planetId)); }

    //ERRORS
    public static final String ERROR_RESPONSE = "error";
    public static final String OK_RESPONSE = "ok";

    public static final int INVALID_USERNAME = 1;
    public static final int INVALID_PASSWORD = 2;
    public static final int INVALID_TOKEN = 3;
    public static final int ERROR_SEND_MESSAGE = 4;
    public static final int ERROR_USER_PROFILE_DOESNT_EXISTS = 5;
    public static final int USERNAME_ALREADY_EXISTS = 6;
    public static final int NO_PASSWORD = 7;
    public static final int NO_USERNAME = 8;

    //Common
    public static final String TITLES[] = {"Principal","Ataque","Defensa","Construcciones","Construcciones militares", "Ayuda"};
    public static final int WHITE_PLANET = 0;
    public static final int BLUE_PLANET = 1;
    public static final int BLACK_PLANET = 2;
    public static final int SHIP_X = 0;
    public static final int SHIP_Y = 1;
    public static final int SHIP_Z = 2;
    public static final int MINE_CRYSTAL = 0;
    public static final int MINE_METAL = 1;
    public static final int MINE_DARKMATTER = 2;
}
