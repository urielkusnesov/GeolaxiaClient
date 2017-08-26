package geolaxia.geolaxia.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;
import geolaxia.geolaxia.Model.Adapters.PlanetListAdapter;
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
import geolaxia.geolaxia.Services.Implementation.AttackService;
import geolaxia.geolaxia.Services.Implementation.PlanetService;
import geolaxia.geolaxia.Services.Interface.IAttackService;
import geolaxia.geolaxia.Services.Interface.IPlanetService;

public class AttackByCoordinatesActivity extends BaseAttackActivity {

    RecyclerView planetList;
    LinearLayoutManager planetListManager;
    PlanetListAdapter planetListAdapter;

    private IPlanetService planetService;
    private IAttackService attackService;
    private Player player;
    private Planet planet;
    private AttackByCoordinatesActivity context;
    private ArrayList<ShipX> availableFleetX = new ArrayList<>();
    private ArrayList<ShipY> availableFleetY = new ArrayList<>();
    private ArrayList<ShipZ> availableFleetZ = new ArrayList<>();
    private String[] galaxiesSelected;
    private String[] solarSystemsSelected;
    private String[] planetsSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attack_by_coordinates);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mFormView = findViewById(R.id.scroll_form);
        mProgressView = findViewById(R.id.progress);

        planetList = (RecyclerView) findViewById(R.id.planetList);
        planetListManager = new LinearLayoutManager(this);
        planetList.setLayoutManager(planetListManager);

        Intent intent = getIntent();
        player = (Player) intent.getExtras().getSerializable("player");
        planet = (Planet) intent.getExtras().getSerializable("planet");

        context = this;
        attackService = new AttackService();
        planetService = new PlanetService();
        planetService.GetAllGalaxies(player.getUsername(), player.getToken(), this);
        planetService.GetFleet(player.getUsername(), player.getToken(), this, planet.getId());

        NumberPicker galaxyPicker = (NumberPicker) findViewById(R.id.galaxy);
        galaxyPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                planetService.GetSolarSystemsByGalaxy(player.getUsername(), player.getToken(), context, newVal);
            }
        });

        NumberPicker solarSystemPicker = (NumberPicker) findViewById(R.id.solarSystem);
        solarSystemPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                planetService.GetPlanetsBySolarSystem(player.getUsername(), player.getToken(), context, newVal);
            }
        });
        
        Button confirm = (Button)findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NumberPicker planetsPicker = (NumberPicker) findViewById(R.id.planet);
                int planetId = Integer.valueOf(planetsSelected[planetsPicker.getValue()].split("-")[0]);
                Planet targetPlanet = new Planet();
                targetPlanet.setId(planetId);

                ArrayList<Ship> fleet = new ArrayList<>();
                NumberPicker xPicker = (NumberPicker) findViewById(R.id.x);
                int shipsX = xPicker.getValue();
                for (int i = 0; i < shipsX; i++) {
                    fleet.add(availableFleetX.get(i));
                }
                NumberPicker yPicker = (NumberPicker) findViewById(R.id.y);
                int shipsY = yPicker.getValue();
                for (int i = 0; i < shipsY; i++) {
                    fleet.add(availableFleetY.get(i));
                }
                NumberPicker zPicker = (NumberPicker) findViewById(R.id.z);
                int shipsZ = zPicker.getValue();
                for (int i = 0; i < shipsZ; i++) {
                    fleet.add(availableFleetZ.get(i));
                }

                Date departure = Calendar.getInstance().getTime();
                Date arrival = Calendar.getInstance().getTime();//calculateArrivalTime();
                Attack attack = new Attack(player, planet, null, targetPlanet, fleet, departure, arrival);

                attackService.Attack(player.getUsername(), player.getToken(), context, attack);
            }
        });
    }

    @Override
    public void FillGalaxies(ArrayList<Galaxy> galaxies){
        NumberPicker galaxyPicker = (NumberPicker) findViewById(R.id.galaxy);
        galaxyPicker.setMinValue(0);
        galaxyPicker.setMaxValue(galaxies.size() - 1);

        ArrayList<String> galaxyNames = new ArrayList<>();
        for (Galaxy galaxy: galaxies) {
            galaxyNames.add(galaxy.getId() + "-" + galaxy.getName());
        }
        galaxiesSelected = new String[galaxyNames.size()];
        galaxiesSelected = galaxyNames.toArray(galaxiesSelected);
        galaxyPicker.setDisplayedValues(galaxiesSelected);

        planetService.GetSolarSystemsByGalaxy(player.getUsername(), player.getToken(), this, galaxies.get(0).getId());
    }

    @Override
    public void FillSolarSystems(ArrayList<SolarSystem> solarSystems){
        NumberPicker solarSystemPicker = (NumberPicker) findViewById(R.id.solarSystem);
        solarSystemPicker.setMinValue(0);
        solarSystemPicker.setMaxValue(solarSystems.size() - 1);

        ArrayList<String> solarSystemNames = new ArrayList<>();
        for (SolarSystem solarSystem: solarSystems) {
            solarSystemNames.add(solarSystem.getId() + "-" + solarSystem.getName());
        }
        solarSystemsSelected = new String[solarSystemNames.size()];
        solarSystemsSelected = solarSystemNames.toArray(solarSystemsSelected);
        solarSystemPicker.setDisplayedValues(solarSystemsSelected);

        planetService.GetPlanetsBySolarSystem(player.getUsername(), player.getToken(), this, solarSystems.get(0).getId());
    }

    @Override
    public void FillPlanets(ArrayList<Planet> planets){
        planetListAdapter = new PlanetListAdapter(planets);
        planetList.setAdapter(planetListAdapter);
    }

    @Override
    public void FillFleets(ArrayList<ShipX> shipsX, ArrayList<ShipY> shipsY, ArrayList<ShipZ> shipsZ){
        availableFleetX.addAll(shipsX);
        availableFleetY.addAll(shipsY);
        availableFleetZ.addAll(shipsZ);

        NumberPicker xPicker = (NumberPicker) findViewById(R.id.x);
        xPicker.setMinValue(0);
        xPicker.setMaxValue(shipsX.size());

        NumberPicker yPicker = (NumberPicker) findViewById(R.id.y);
        yPicker.setMinValue(0);
        yPicker.setMaxValue(shipsY.size());

        NumberPicker zPicker = (NumberPicker) findViewById(R.id.z);
        zPicker.setMinValue(0);
        zPicker.setMaxValue(shipsZ.size());
    }

    public void AttackSaved(){
        SweetAlertDialog dialog = Helpers.getSuccesDialog(this, "A conquistar!", "El ataque esta en marcha");
        dialog.show();
    }
}
