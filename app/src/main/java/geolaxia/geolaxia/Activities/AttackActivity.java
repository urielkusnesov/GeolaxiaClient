package geolaxia.geolaxia.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ThemedSpinnerAdapter;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import geolaxia.geolaxia.Model.Attack;
import geolaxia.geolaxia.Model.Galaxy;
import geolaxia.geolaxia.Model.Helpers;
import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.Player;
import geolaxia.geolaxia.Model.Ship;
import geolaxia.geolaxia.Model.ShipX;
import geolaxia.geolaxia.Model.ShipY;
import geolaxia.geolaxia.Model.ShipZ;
import geolaxia.geolaxia.Model.SolarSystem;
import geolaxia.geolaxia.R;
import geolaxia.geolaxia.Services.Implementation.PlanetService;
import geolaxia.geolaxia.Services.Interface.IPlanetService;

public class AttackActivity extends MenuActivity {

    private IPlanetService planetService;
    private Player player;
    private Planet planet;
    private ArrayList<Ship> availableFleet;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        super.onCreateDrawer();

        mFormView = findViewById(R.id.scroll_form);
        mProgressView = findViewById(R.id.progress);

        Intent intent = getIntent();
        player = (Player) intent.getExtras().getSerializable("player");
        planet = (Planet) intent.getExtras().getSerializable("planet");

        planetService = new PlanetService();
        planetService.GetAllGalaxies(player.getUsername(), player.getToken(), this);
        planetService.GetFleet(player.getUsername(), player.getToken(), this, planet.getId());

        Button confirm = (Button)findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    public void FillGalaxies(ArrayList<Galaxy> galaxies){
        NumberPicker galaxyPicker = (NumberPicker) findViewById(R.id.galaxy);
        galaxyPicker.setMinValue(0);
        galaxyPicker.setMaxValue(galaxies.size() - 1);

        ArrayList<String> galaxyNames = new ArrayList<>();
        for (Galaxy galaxy: galaxies) {
            galaxyNames.add(galaxy.getName());
        }
        galaxyPicker.setDisplayedValues((String[]) galaxyNames.toArray());

        planetService.GetSolarSystemsByGalaxy(player.getUsername(), player.getToken(), this, galaxies.get(0).getId());
    }

    public void FillSolarSystems(ArrayList<SolarSystem> solarSystems){
        NumberPicker solarSystemPicker = (NumberPicker) findViewById(R.id.solarSystem);
        solarSystemPicker.setMinValue(0);
        solarSystemPicker.setMaxValue(solarSystems.size() - 1);

        ArrayList<String> solarSystemNames = new ArrayList<>();
        for (SolarSystem solarSystem: solarSystems) {
            solarSystemNames.add(solarSystem.getName());
        }
        solarSystemPicker.setDisplayedValues((String[]) solarSystemNames.toArray());

        planetService.GetPlanetsBySolarSystem(player.getUsername(), player.getToken(), this, solarSystems.get(0).getId());
    }

    public void FillPlanets(ArrayList<Planet> planets){
        NumberPicker planetsPicker = (NumberPicker) findViewById(R.id.planet);
        planetsPicker.setMinValue(0);
        planetsPicker.setMaxValue(planets.size() - 1);

        ArrayList<String> solarSystemNames = new ArrayList<>();
        for (Planet planet: planets) {
            solarSystemNames.add(planet.getName());
        }
        planetsPicker.setDisplayedValues((String[]) solarSystemNames.toArray());
    }

    public void FillFleets(ArrayList<ShipX> shipsX, ArrayList<ShipY> shipsY, ArrayList<ShipZ> shipsZ){
        NumberPicker xPicker = (NumberPicker) findViewById(R.id.x);
        xPicker.setMinValue(0);
        xPicker.setMaxValue(shipsX.size() - 1);

        NumberPicker yPicker = (NumberPicker) findViewById(R.id.y);
        yPicker.setMinValue(0);
        yPicker.setMaxValue(shipsX.size() - 1);

        NumberPicker zPicker = (NumberPicker) findViewById(R.id.z);
        zPicker.setMinValue(0);
        zPicker.setMaxValue(shipsX.size() - 1);
    }

    public void AttackSaved(){
        SweetAlertDialog dialog = Helpers.getSuccesDialog(this, "A conquistar!", "El ataque esta en marcha");
        dialog.show();
    }
}
