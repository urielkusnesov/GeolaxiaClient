package geolaxia.geolaxia.Activities;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.SweepGradient;
import android.location.Location;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import geolaxia.geolaxia.Model.Helpers;
import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.Player;
import geolaxia.geolaxia.R;
import geolaxia.geolaxia.Services.Implementation.LoginService;
import geolaxia.geolaxia.Services.Implementation.PlanetService;
import geolaxia.geolaxia.Services.Implementation.WeatherService;
import geolaxia.geolaxia.Services.Interface.ILoginService;
import geolaxia.geolaxia.Services.Interface.IPlanetService;
import geolaxia.geolaxia.Services.Interface.IWeatherService;

public class HomeActivity extends MenuActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener , LocationListener {

    final HomeActivity context = this;

    private Player player;
    private Planet planet;
    private IPlanetService planetService;
    private ILoginService loginService;
    private IWeatherService weatherService;

    Spinner planetSpinner;
    TextView metalQtt;
    TextView crystalQtt;
    TextView darkMatterQtt;
    TextView energyQtt;
    TextView levelText;

    //for getting current location;
    private GoogleApiClient mGoogleApiClient;
    private Location mLocation;
    private LocationManager locationManager;
    private LocationRequest mLocationRequest;
    private String latitude;
    private String longitude;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        checkLocation();

        Intent intent = getIntent();
        player = (Player) intent.getSerializableExtra("player");
        super.onCreateDrawer();

        mFormView = findViewById(R.id.scroll_form);
        mProgressView = findViewById(R.id.progress);

        planetSpinner = (Spinner) findViewById(R.id.planetSpinner);
        metalQtt = (TextView) findViewById(R.id.metalQtt);
        crystalQtt = (TextView) findViewById(R.id.cristalQtt);
        darkMatterQtt = (TextView) findViewById(R.id.darkMatterQtt);
        energyQtt = (TextView) findViewById(R.id.energyQtt);
        levelText = (TextView) findViewById(R.id.levelText);

        planetService = new PlanetService();
        loginService = new LoginService();
        weatherService = new WeatherService();
        FillPlanets(player.getPlanets());

        levelText.setText("Nivel " + String.valueOf(player.getLevel()));
    }

    private boolean checkLocation() {
        if(!isLocationEnabled()) {
            SweetAlertDialog dialog = Helpers.getErrorDialog(this, "No tiene activado el GPS!", "Por favor active el GPS para poder jugar.");
            dialog.show();
        }
        return isLocationEnabled();
    }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    private void LoadImages(int width, int height){
        ImageView cristalImage = (ImageView) findViewById(R.id.cristal);
        Picasso.with(this)
                .load(R.drawable.cristal)
                .resize(width, height)
                .into(cristalImage);

        ImageView metalImage = (ImageView) findViewById(R.id.metal);
        Picasso.with(this)
                .load(R.drawable.metal)
                .resize(width, height)
                .into(metalImage);

        ImageView darkMatterImage = (ImageView) findViewById(R.id.materiaOscura);
        Picasso.with(this)
                .load(R.drawable.materia_oscura)
                .resize(width, height)
                .into(darkMatterImage);

        ImageView energyImage = (ImageView) findViewById(R.id.energia);
        Picasso.with(this)
                .load(R.drawable.energia)
                .resize(width, height)
                .into(energyImage);
    }

    public void FillPlanets(ArrayList<Planet> planets){
        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<Planet> adapter = new ArrayAdapter<Planet>(this, android.R.layout.simple_spinner_item, planets);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        planetSpinner.setAdapter(adapter);
        planetSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Planet planet = (Planet) planetSpinner.getSelectedItem();
                FillPlanetInfo(planet);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    public void FillPlanetInfo(Planet planet){
        ImageView planetImage = (ImageView) findViewById(R.id.planet);
        Picasso.with(this)
                .load(planet.getImage())
                .resize(400, 400)
                .into(planetImage);

        LoadImages(200, 200);
        metalQtt.setText(String.valueOf(planet.getMetal()));
        crystalQtt.setText(String.valueOf(planet.getCrystal()));
        darkMatterQtt.setText(String.valueOf(planet.getDarkMatter()));
        energyQtt.setText(String.valueOf(planet.getEnergy()));

        super.changePlanet(planet);
    }

    public void ParseWeather(JSONObject weatherResponse) {
        try {
            JSONArray weather = weatherResponse.getJSONArray("weather");
            String mainWeather = weather.getJSONObject(0).getString("description");
            mainWeather = showWeather(mainWeather);
            JSONObject main = weatherResponse.getJSONObject("main");
            String temp = main.getString("temp");
            TextView temperatureText = (TextView) findViewById(R.id.temperatureText);
            temperatureText.setText(mainWeather + ": " + temp + "°C");

            JSONObject wind = weatherResponse.getJSONObject("wind");
            String speed = wind.getString("speed");
            TextView windText = (TextView) findViewById(R.id.windText);
            windText.setText("Viento: " + speed + "km/h");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private String showWeather(String weather){
        String description = "";
        String imageUrl = "";
        switch (weather){
            case "clear sky":
                description = "Soleado";
                imageUrl = "http://openweathermap.org/img/w/01d.png";
                break;
            case "few clouds":
                description = "Parcialmente Nublado";
                imageUrl = "http://openweathermap.org/img/w/02d.png";
                break;
            case "scattered clouds":
                description = "Nublado";
                imageUrl = "http://openweathermap.org/img/w/03d.png";
                break;
            case "broken clouds":
                description = "Nublado";
                imageUrl = "http://openweathermap.org/img/w/04d.png";
                break;
            case "shower rain":
                description = "Llovizna";
                imageUrl = "http://openweathermap.org/img/w/09d.png";
                break;
            case "rain":
                description = "Lluvioso";
                imageUrl = "http://openweathermap.org/img/w/10d.png";
                break;
            case "thunderstorm":
                description = "Tormenta Electrica";
                imageUrl = "http://openweathermap.org/img/w/11d.png";
                break;
            case "snow":
                description = "Nevado";
                imageUrl = "http://openweathermap.org/img/w/13d.png";
                break;
            case "mist":
                description = "Ventoso";
                imageUrl = "http://openweathermap.org/img/w/50d.png";
                break;
            default:
                description = "Soleado";
                imageUrl = "http://openweathermap.org/img/w/01d.png";
                break;
        }

        ImageView weatherImage = (ImageView) findViewById(R.id.weatherImage);
        Picasso.with(this)
                .load(imageUrl)
                .resize(150, 150)
                .into(weatherImage);

        return description;
    }

    @Override
    public void onConnected(Bundle bundle) {
        if (!(Build.VERSION.SDK_INT < Build.VERSION_CODES.M)) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                //no hay permisos para acceder a la ubicacion
                ActivityCompat.requestPermissions( this, new String[] {  android.Manifest.permission.ACCESS_COARSE_LOCATION  }, 1);
            }
        }
        startLocationUpdates();
        mLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        if(mLocation == null){
            startLocationUpdates();
        }
        if (mLocation != null) {
            latitude = String.valueOf(mLocation.getLatitude());
            longitude = String.valueOf(mLocation.getLongitude());
            player.setLastLatitude(latitude);
            player.setLastLongitude(longitude);
            loginService.SetLastPosition(latitude, longitude, player, this);
            //weatherService.GetWeather(latitude, longitude, this);
        } else {
            // no se puedo detectar la ubicacion
        }
    }

    protected void startLocationUpdates() {
        // Create the location request
        mLocationRequest = LocationRequest.create()
                .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)
                .setInterval(600)
                .setFastestInterval(500);
        // Request location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            //no hay permisos para acceder a la ubicacion
            return;
        }
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient,
                mLocationRequest, this);
    }

    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        //no se pudo conectar con google play services
    }

    @Override
    public void onStart() {
        super.onStart();
        mGoogleApiClient.connect();
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }
    @Override
    public void onLocationChanged(Location location) {

    }

    @Override
    public void onBackPressed() {
        SweetAlertDialog dialog = Helpers.getConfirmationDialog(this, "Salir", "Esta seguro que desea cerrar sesion?", "Cerrar sesion", "Cancelar");

        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Intent intent = new Intent(context, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                sweetAlertDialog.cancel();
            }
        });

        dialog.show();
    }
}
