package geolaxia.geolaxia.Model;

/**
 * Created by uriel on 15/4/2017.
 */

public class Constants {
    public static String BASE_URL = "192.168.1.109";/*Server IP*/
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
    public static final String SET_WEATHER_SERVICE = "api/player/setweather";
    public static final String SET_FIREBASE_SERVICE = "api/player/setfirebase";
    public static final String CLOSER_PLAYERS_SERVICE = "api/attack/closerplayers";
    public static final String GET_PLAYER_SERVICE = "api/player/loggedplayer";
    public static final String WEATHER_SERVICE = "http://api.openweathermap.org/data/2.5/weather";
    public static final String ATTACK_SERVICE = "api/attack/attack";
    public static final String MINES_CURRENT_SERVICE = "api/construction/currentmines";
    public static final String MINES_TO_BUILD_SERVICE = "api/construction/minestobuild";
    public static final String MINES_BUILD_SERVICE = "api/construction/startbuildmine";
    public static final String ENERGY_CURRENT_SERVICE = "api/construction/currentenergyfacilities";
    public static final String ENERGY_TO_BUILD_SERVICE = "api/construction/energyfacilitiestobuild";
    public static final String ENERGY_BUILD_SERVICE = "api/construction/startbuildenergyfacility";
    public static final String SOLAR_PANEL_BUILD_SERVICE = "api/construction/startbuildsolarpanel";
    public static final String WIND_TURBINE_BUILD_SERVICE = "api/construction/startbuildwindturbine";
    public static final String HANGAR_CURRENT_SERVICE = "api/construction/currenthangar";
    public static final String HANGAR_BUILD_SERVICE = "api/construction/startbuildhangar";
    public static final String SHIP_COST_SERVICE = "api/construction/shipscost";
    public static final String SHIP_BUILD_SERVICE = "api/construction/startbuildship";
    public static final String SHIELD_CURRENT_SERVICE = "api/construction/currentshield";
    public static final String PROBE_CURRENT_SERVICE = "api/construction/currentprobes";
    public static final String TRADER_CURRENT_SERVICE = "api/construction/currenttraders";
    public static final String SHIELD_BUILD_SERVICE = "api/construction/startbuildshield";
    public static final String PROBE_BUILD_SERVICE = "api/construction/startbuildprobe";
    public static final String TRADER_BUILD_SERVICE = "api/construction/startbuildtrader";
    public static final String SHIPS_CURRENT_SERVICE = "api/construction/currentships";

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
    public static String SetWeatherServiceUrl(int weatherDesc, String weatherWindSpeed) { return (HTTP + BASE_URL + PORT + SET_WEATHER_SERVICE + "?weatherDesc=" + String.valueOf(weatherDesc) + "&windSpeed=" + weatherWindSpeed); }
    public static String SetFirebaseServiceUrl(String firebaseToken) { return (HTTP + BASE_URL + PORT + SET_FIREBASE_SERVICE + "?token=" + firebaseToken); }
    public static String getCloserPlayersServiceUrl() { return (HTTP + BASE_URL + PORT + CLOSER_PLAYERS_SERVICE); }
    public static String getPlayerServiceUrl() { return (HTTP + BASE_URL + PORT + GET_PLAYER_SERVICE); }
    public static String getWeatherServiceUrl(String latitude, String longitude) { return (WEATHER_SERVICE + "?lat=" + latitude + "&lon=" + longitude + "&units=metric&APPID=a6e31252461e3c4a9ccaf2bcb32740c5"); }
    public static String getAttackServiceUrl() { return (HTTP + BASE_URL + PORT + ATTACK_SERVICE); }
    public static String getCurrentMinesServiceUrl(int planetId) { return (HTTP + BASE_URL + PORT + MINES_CURRENT_SERVICE + "?planetId=" + String.valueOf(planetId)); }
    public static String getMinesToBuildServiceUrl(int planetId) { return (HTTP + BASE_URL + PORT + MINES_TO_BUILD_SERVICE + "?planetId=" + String.valueOf(planetId)); }
    public static String getBuildMineServiceUrl() { return (HTTP + BASE_URL + PORT + MINES_BUILD_SERVICE); }
    public static String getCurrentEnergyFacilitiesServiceUrl(int planetId) { return (HTTP + BASE_URL + PORT + ENERGY_CURRENT_SERVICE + "?planetId=" + String.valueOf(planetId)); }
    public static String getEnergyFacilitiesToBuildServiceUrl(int planetId) { return (HTTP + BASE_URL + PORT + ENERGY_TO_BUILD_SERVICE + "?planetId=" + String.valueOf(planetId)); }
    public static String getBuildEnergyFacilityServiceUrl() { return (HTTP + BASE_URL + PORT + ENERGY_BUILD_SERVICE); }
    public static String getBuildSolarPanelsServiceUrl(int planetId, int qtt) { return (HTTP + BASE_URL + PORT + SOLAR_PANEL_BUILD_SERVICE + "?planetId=" + String.valueOf(planetId) + "&" + "qtt=" + String.valueOf(qtt)); }
    public static String getBuildWindTurbinesServiceUrl(int planetId, int qtt) { return (HTTP + BASE_URL + PORT + WIND_TURBINE_BUILD_SERVICE + "?planetId=" + String.valueOf(planetId) + "&" + "qtt=" + String.valueOf(qtt)); }
    public static String getCurrentHangarServiceUrl(int planetId) { return (HTTP + BASE_URL + PORT + HANGAR_CURRENT_SERVICE + "?planetId=" + String.valueOf(planetId)); }
    public static String getBuildHangarServiceUrl(int planetId) { return (HTTP + BASE_URL + PORT + HANGAR_BUILD_SERVICE + "?planetId=" + String.valueOf(planetId)); }
    public static String getShipsServiceUrl(int planetId) { return (HTTP + BASE_URL + PORT + SHIPS_CURRENT_SERVICE + "?planetId=" + String.valueOf(planetId)); }
    public static String getShipsCostServiceUrl() { return (HTTP + BASE_URL + PORT + SHIP_COST_SERVICE); }
    public static String getBuildShipsServiceUrl(int planetId, int qtt, int shipType) { return (HTTP + BASE_URL + PORT + SHIP_BUILD_SERVICE + "?planetId=" + String.valueOf(planetId) + "&" + "qtt=" + String.valueOf(qtt)) + "&" + "shipType=" + String.valueOf(shipType); }
    public static String getCurrentShieldServiceUrl(int planetId) { return (HTTP + BASE_URL + PORT + SHIELD_CURRENT_SERVICE + "?planetId=" + String.valueOf(planetId)); }
    public static String getCurrentProbesServiceUrl(int planetId) { return (HTTP + BASE_URL + PORT + PROBE_CURRENT_SERVICE + "?planetId=" + String.valueOf(planetId)); }
    public static String getCurrentTradersServiceUrl(int planetId) { return (HTTP + BASE_URL + PORT + TRADER_CURRENT_SERVICE + "?planetId=" + String.valueOf(planetId)); }
    public static String getBuildShieldServiceUrl(int planetId) { return (HTTP + BASE_URL + PORT + SHIELD_BUILD_SERVICE + "?planetId=" + String.valueOf(planetId)); }
    public static String getBuildProbesServiceUrl(int planetId, int qtt) { return (HTTP + BASE_URL + PORT + PROBE_BUILD_SERVICE + "?planetId=" + String.valueOf(planetId) + "&" + "qtt=" + String.valueOf(qtt)); }
    public static String getBuildTradersServiceUrl(int planetId, int qtt) { return (HTTP + BASE_URL + PORT + TRADER_BUILD_SERVICE + "?planetId=" + String.valueOf(planetId) + "&" + "qtt=" + String.valueOf(qtt)); }

    //Login
    public static final String LOGIN_GETNOTIFICATIONS_SERVICE = "api/notification/GetNotifications";

    public static String getNotifications(int playerId) { return (HTTP + BASE_URL + PORT + LOGIN_GETNOTIFICATIONS_SERVICE + "?playerId=" + String.valueOf(playerId)); }

    //Defense
    public static final String DEFENSE_GETCANNONS_SERVICE = "api/defense/getCannons";
    public static final String DEFENSE_BUILD_SERVICE = "api/defense/buildCannons";
    public static final String DEFENSE_GETSHIELDSTATUS_SERVICE = "api/defense/getShieldStatus";
    public static final String DEFENSE_ISBUILDINGCANNONS_SERVICE = "api/defense/IsBuildingCannons";
    public static final String DEFENSE_GET_RANDOM_QUESTIONS_SERVICE = "api/defense/Get3RandomQuestions";
    public static final String DEFENSE_GETATTACKIDNODEFENDIDO_SERVICE = "api/defense/ObtenerIdAtaqueMasProximoNoDefendido";
    public static final String DEFENSE_DEFENSEFROMATTACK_SERVICE = "api/defense/DefenseFromAttack";

    public static String getCannons(int planetId) { return (HTTP + BASE_URL + PORT + DEFENSE_GETCANNONS_SERVICE + "?planetId=" + String.valueOf(planetId)); }
    public static String BuildCannons(int planetId, int cant) { return (HTTP + BASE_URL + PORT + DEFENSE_BUILD_SERVICE + "?planetId=" + String.valueOf(planetId) + "&" + "cant=" + String.valueOf(cant)); }
    public static String getShieldStatus(int planetId) { return (HTTP + BASE_URL + PORT + DEFENSE_GETSHIELDSTATUS_SERVICE + "?planetId=" + String.valueOf(planetId)); }
    public static String isBuldingCannons(int planetId) { return (HTTP + BASE_URL + PORT + DEFENSE_ISBUILDINGCANNONS_SERVICE + "?planetId=" + String.valueOf(planetId)); }
    public static String getRandomQuestions() { return (HTTP + BASE_URL + PORT + DEFENSE_GET_RANDOM_QUESTIONS_SERVICE); }
    //public static String getRandomQuestionsConAtaque(int attackId) { return (HTTP + BASE_URL + PORT + DEFENSE_GET_RANDOM_QUESTIONS_SERVICE + "?attackId=" + String.valueOf(attackId)); }
    public static String getAttackIdNoDefendido(int planetId) { return (HTTP + BASE_URL + PORT + DEFENSE_GETATTACKIDNODEFENDIDO_SERVICE + "?planetId=" + String.valueOf(planetId)); }
    public static String defenseFromAttack(int attackId, int idPregunta1, int idPregunta2, int idPregunta3, int cantidadCorrectas) { return (HTTP + BASE_URL + PORT + DEFENSE_DEFENSEFROMATTACK_SERVICE + "?attackId=" + String.valueOf(attackId) + "&idPregunta1=" + String.valueOf(idPregunta1) + "&idPregunta2=" + String.valueOf(idPregunta2) + "&idPregunta3=" + String.valueOf(idPregunta3) + "&cantidadCorrectas=" + String.valueOf(cantidadCorrectas)); }

    //Colonize
    public static final String COLONIZE_GETCOLONIZERS_SERVICE = "api/colonize/GetColonizers";
    public static final String COLONIZE_SENDCOLONIZERS_SERVICE = "api/colonize/SendColonizer";
    public static final String DEFENSE_ISSENDINDCOLONIZER_SERVICE = "api/colonize/IsSendingColonizer";

    public static String getColonizers(int planetId) { return (HTTP + BASE_URL + PORT + COLONIZE_GETCOLONIZERS_SERVICE + "?planetId=" + String.valueOf(planetId)); }
    public static String sendColonizer(int planetId, int planetIdTarget, long time) { return (HTTP + BASE_URL + PORT + COLONIZE_SENDCOLONIZERS_SERVICE + "?planetId=" + String.valueOf(planetId) + "&planetIdTarget=" + String.valueOf(planetIdTarget) + "&time=" + String.valueOf(time)); }
    public static String isSendingColonizer(int planetId) { return (HTTP + BASE_URL + PORT + DEFENSE_ISSENDINDCOLONIZER_SERVICE + "?planetId=" + String.valueOf(planetId)); }

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
    public static final int WEATHER_CLOUDY = 0;
    public static final int WEATHER_SUNNY = 1;
    public static final int WEATHER_RAINY = 2;
    public static final int WHITE_PLANET = 0;
    public static final int BLUE_PLANET = 1;
    public static final int BLACK_PLANET = 2;
    public static final int SHIP_X = 0;
    public static final int SHIP_Y = 1;
    public static final int SHIP_Z = 2;
    public static final int MINE_CRYSTAL = 0;
    public static final int MINE_METAL = 1;
    public static final int MINE_DARKMATTER = 2;
    public static final int EF_ENERGY_CENTRAL = 0;
    public static final int EF_ENERGY_FUEL_CENTRAL = 1;
    public static final int EF_SOLAR_PANEL = 2;
    public static final int EF_WIND_TURBINE = 3;
}
