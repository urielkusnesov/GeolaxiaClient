package geolaxia.geolaxia.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;

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

public class AttackActivity extends MenuActivity {
    private Player player;
    private Planet planet;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    static final long ONE_MINUTE_IN_MILLIS = 60000;

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

        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }

    public Date calculateArrivalTime(ArrayList<Ship> fleet, Planet target){
        int lowerSpeed = 0;
        for (Ship ship: fleet) {
            if(lowerSpeed == 0 || ship.getSpeed() < lowerSpeed){
                lowerSpeed = ship.getSpeed();
            }
        }

        int distance = planet.getOrder() > target.getOrder() ? planet.getOrder() - target.getOrder() : target.getOrder() - planet.getOrder();
        int minutesToAdd = lowerSpeed != 0 ? Math.round(distance*1000/lowerSpeed) : 0;

        Calendar date = Calendar.getInstance();
        long t= date.getTimeInMillis();
        return new Date(t + (minutesToAdd * ONE_MINUTE_IN_MILLIS));
    }

    public void AttackSaved(){
        SweetAlertDialog dialog = Helpers.getSuccesDialog(this, "A conquistar!", "El ataque esta en marcha");
        dialog.show();
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class AttackFragment extends Fragment {

        private IPlanetService planetService;
        private IAttackService attackService;
        private ArrayList<ShipX> availableFleetX = new ArrayList<>();
        private ArrayList<ShipY> availableFleetY = new ArrayList<>();
        private ArrayList<ShipZ> availableFleetZ = new ArrayList<>();
        private HashMap<Integer, Planet> availablePlanets = new HashMap<>();
        private AttackFragment context;
        private AttackActivity act;
        private String[] galaxiesSelected;
        private String[] solarSystemsSelected;
        private String[] planetsSelected;

        public AttackFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static AttackFragment newInstance() {
            AttackFragment fragment = new AttackFragment();
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            final View rootView = inflater.inflate(R.layout.fragment_attack, container, false);
            act = (AttackActivity) getActivity();

            attackService = new AttackService();
            planetService = new PlanetService();
            planetService.GetAllGalaxies(act.player.getUsername(), act.player.getToken(), act, this);
            planetService.GetFleet(act.player.getUsername(), act.player.getToken(), act, this, act.planet.getId());

            context = this;

            NumberPicker galaxyPicker = (NumberPicker) rootView.findViewById(R.id.galaxy);
            Helpers.setNumberPickerTextColor(galaxyPicker, Color.WHITE);
            galaxyPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    planetService.GetSolarSystemsByGalaxy(act.player.getUsername(), act.player.getToken(), act, context, newVal);
                }
            });

            NumberPicker solarSystemPicker = (NumberPicker) rootView.findViewById(R.id.solarSystem);
            Helpers.setNumberPickerTextColor(solarSystemPicker, Color.WHITE);
            solarSystemPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    planetService.GetPlanetsBySolarSystem(act.player.getUsername(), act.player.getToken(), act, context, newVal);
                }
            });

            NumberPicker planetPicker = (NumberPicker) rootView.findViewById(R.id.planet);
            Helpers.setNumberPickerTextColor(planetPicker, Color.WHITE);
            planetPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    setArrivalTime();
                }
            });

            NumberPicker xPicker = (NumberPicker) rootView.findViewById(R.id.x);
            Helpers.setNumberPickerTextColor(xPicker, Color.WHITE);
            xPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    setArrivalTime();
                }
            });

            NumberPicker yPicker = (NumberPicker) rootView.findViewById(R.id.y);
            Helpers.setNumberPickerTextColor(yPicker, Color.WHITE);
            yPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    setArrivalTime();
                }
            });

            NumberPicker zPicker = (NumberPicker) rootView.findViewById(R.id.z);
            Helpers.setNumberPickerTextColor(zPicker, Color.WHITE);
            zPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    setArrivalTime();
                }
            });

            Button confirm = (Button)rootView.findViewById(R.id.confirm);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    NumberPicker planetsPicker = (NumberPicker) rootView.findViewById(R.id.planet);
                    int planetOrder = Integer.valueOf(planetsSelected[planetsPicker.getValue()].split("-")[0]);
                    Planet targetPlanet = availablePlanets.get(planetOrder);

                    ArrayList<Ship> fleet = getAttackFleet();

                    Date departure = Calendar.getInstance().getTime();
                    Date arrival = act.calculateArrivalTime(fleet, targetPlanet);
                    Attack attack = new Attack(act.player, act.planet, null, targetPlanet, fleet, departure, arrival);

                    attackService.Attack(act.player.getUsername(), act.player.getToken(), act, attack);
                }
            });

            return rootView;
        }

        public void setArrivalTime(){
            TextView estimateArrival = (TextView) getView().findViewById(R.id.estimateText);

            NumberPicker planetsPicker = (NumberPicker) getView().findViewById(R.id.planet);
            int planetOrder = Integer.valueOf(planetsSelected[planetsPicker.getValue()].split("-")[0]);
            Planet targetPlanet = availablePlanets.get(planetOrder);

            ArrayList<Ship> fleet = getAttackFleet();
            if(fleet.size() > 0){
                Date arrival = act.calculateArrivalTime(fleet, targetPlanet);

                long totalDifferenceInSeconds = (arrival.getTime() - Calendar.getInstance().getTime().getTime())/1000;
                long hours = Math.abs(totalDifferenceInSeconds/3600);
                long minutes = (totalDifferenceInSeconds%3600)/60;
                long seconds = totalDifferenceInSeconds - (hours*3600) - (minutes*60);

            }else{
                estimateArrival.setText("El ataque llegar en: ");
            }
        }

        public ArrayList<Ship> getAttackFleet(){
            ArrayList<Ship> fleet = new ArrayList<>();
            NumberPicker xPicker = (NumberPicker) getView().findViewById(R.id.x);
            int shipsX = xPicker.getValue();
            for (int i = 0; i < shipsX; i++) {
                fleet.add(availableFleetX.get(i));
            }
            NumberPicker yPicker = (NumberPicker) getView().findViewById(R.id.y);
            int shipsY = yPicker.getValue();
            for (int i = 0; i < shipsY; i++) {
                fleet.add(availableFleetY.get(i));
            }
            NumberPicker zPicker = (NumberPicker) getView().findViewById(R.id.z);
            int shipsZ = zPicker.getValue();
            for (int i = 0; i < shipsZ; i++) {
                fleet.add(availableFleetZ.get(i));
            }

            return fleet;
        }

        public void FillGalaxies(ArrayList<Galaxy> galaxies){
            NumberPicker galaxyPicker = (NumberPicker) getView().findViewById(R.id.galaxy);
            galaxyPicker.setMinValue(0);
            galaxyPicker.setMaxValue(galaxies.size() - 1);

            ArrayList<String> galaxyNames = new ArrayList<>();
            for (Galaxy galaxy: galaxies) {
                galaxyNames.add(galaxy.getId() + "-" + galaxy.getName());
            }
            galaxiesSelected = new String[galaxyNames.size()];
            galaxiesSelected = galaxyNames.toArray(galaxiesSelected);
            galaxyPicker.setDisplayedValues(galaxiesSelected);

            planetService.GetSolarSystemsByGalaxy(act.player.getUsername(), act.player.getToken(), act, this, galaxies.get(0).getId());
        }

        public void FillSolarSystems(ArrayList<SolarSystem> solarSystems){
            NumberPicker solarSystemPicker = (NumberPicker) getView().findViewById(R.id.solarSystem);
            solarSystemPicker.setMinValue(0);
            solarSystemPicker.setMaxValue(solarSystems.size() - 1);

            ArrayList<String> solarSystemNames = new ArrayList<>();
            for (SolarSystem solarSystem: solarSystems) {
                solarSystemNames.add(solarSystem.getId() + "-" + solarSystem.getName());
            }
            solarSystemsSelected = new String[solarSystemNames.size()];
            solarSystemsSelected = solarSystemNames.toArray(solarSystemsSelected);
            solarSystemPicker.setDisplayedValues(solarSystemsSelected);

            planetService.GetPlanetsBySolarSystem(act.player.getUsername(), act.player.getToken(), act, this, solarSystems.get(0).getId());
        }

        public void FillPlanets(ArrayList<Planet> planets){
            ArrayList<Planet> finalPlanets = new ArrayList<>();
            for (Planet planet: planets) {
                if(planet.getConqueror() == null || !planet.getConqueror().getUsername().equals(act.player.getUsername())){
                    finalPlanets.add(planet);
                }
            }

            NumberPicker planetsPicker = (NumberPicker) getView().findViewById(R.id.planet);
            planetsPicker.setMinValue(0);
            planetsPicker.setMaxValue(finalPlanets.size() - 1);

            ArrayList<String> planetNames = new ArrayList<>();
            for (Planet planet: finalPlanets) {
                availablePlanets.put(planet.getOrder(), planet);
                planetNames.add(planet.getOrder() + "-" + planet.getName());
            }
            planetsSelected = new String[planetNames.size()];
            planetsSelected = planetNames.toArray(planetsSelected);
            planetsPicker.setDisplayedValues(planetsSelected);
        }

        public void FillFleets(ArrayList<ShipX> shipsX, ArrayList<ShipY> shipsY, ArrayList<ShipZ> shipsZ){
            availableFleetX.addAll(shipsX);
            availableFleetY.addAll(shipsY);
            availableFleetZ.addAll(shipsZ);

            NumberPicker xPicker = (NumberPicker) getView().findViewById(R.id.x);
            xPicker.setMinValue(0);
            xPicker.setMaxValue(shipsX.size());

            NumberPicker yPicker = (NumberPicker) getView().findViewById(R.id.y);
            yPicker.setMinValue(0);
            yPicker.setMaxValue(shipsY.size());

            NumberPicker zPicker = (NumberPicker) getView().findViewById(R.id.z);
            zPicker.setMinValue(0);
            zPicker.setMaxValue(shipsZ.size());
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class CoordinatesFragment extends Fragment {
        RecyclerView planetList;
        LinearLayoutManager planetListManager;
        PlanetListAdapter planetListAdapter;

        private IPlanetService planetService;
        private IAttackService attackService;
        private AttackActivity act;
        private CoordinatesFragment context;
        private ArrayList<ShipX> availableFleetX = new ArrayList<>();
        private ArrayList<ShipY> availableFleetY = new ArrayList<>();
        private ArrayList<ShipZ> availableFleetZ = new ArrayList<>();
        private HashMap<Integer, Planet> availablePlanets = new HashMap<>();
        private String[] galaxiesSelected;
        private String[] solarSystemsSelected;
        private Planet targetPlanet;

        public CoordinatesFragment() {

        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static CoordinatesFragment newInstance() {
            CoordinatesFragment fragment = new CoordinatesFragment();
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_attack_coordinates, container, false);
            act = (AttackActivity) getActivity();

            planetList = (RecyclerView) rootView.findViewById(R.id.planetList);
            planetListManager = new LinearLayoutManager(act);
            planetList.setLayoutManager(planetListManager);

            context = this;

            attackService = new AttackService();
            planetService = new PlanetService();
            planetService.GetAllGalaxies(act.player.getUsername(), act.player.getToken(), act, this);
            planetService.GetFleet(act.player.getUsername(), act.player.getToken(), act, this, act.planet.getId());

            NumberPicker galaxyPicker = (NumberPicker) rootView.findViewById(R.id.galaxy);
            Helpers.setNumberPickerTextColor(galaxyPicker, Color.WHITE);
            galaxyPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    planetService.GetSolarSystemsByGalaxy(act.player.getUsername(), act.player.getToken(), act, context, newVal);
                }
            });

            NumberPicker solarSystemPicker = (NumberPicker) rootView.findViewById(R.id.solarSystem);
            Helpers.setNumberPickerTextColor(solarSystemPicker, Color.WHITE);
            solarSystemPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    planetService.GetPlanetsBySolarSystem(act.player.getUsername(), act.player.getToken(), act, context, newVal);
                }
            });

            NumberPicker xPicker = (NumberPicker) rootView.findViewById(R.id.x);
            Helpers.setNumberPickerTextColor(xPicker, Color.WHITE);
            xPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    setArrivalTime();
                }
            });

            NumberPicker yPicker = (NumberPicker) rootView.findViewById(R.id.y);
            Helpers.setNumberPickerTextColor(yPicker, Color.WHITE);
            yPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    setArrivalTime();
                }
            });

            NumberPicker zPicker = (NumberPicker) rootView.findViewById(R.id.z);
            Helpers.setNumberPickerTextColor(zPicker, Color.WHITE);
            zPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    setArrivalTime();
                }
            });

            Button confirm = (Button)rootView.findViewById(R.id.confirm);
            confirm.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    ArrayList<Ship> fleet = getAttackFleet();

                    Date departure = Calendar.getInstance().getTime();
                    Date arrival = act.calculateArrivalTime(fleet, targetPlanet);
                    Attack attack = new Attack(act.player, act.planet, null, targetPlanet, fleet, departure, arrival);

                    attackService.Attack(act.player.getUsername(), act.player.getToken(), act, attack);
                }
            });

            return rootView;
        }

        public void setArrivalTime(){
            TextView estimateArrival = (TextView) getView().findViewById(R.id.estimateText);

            ArrayList<Ship> fleet = getAttackFleet();
            if(fleet.size() > 0){
                Date arrival = act.calculateArrivalTime(fleet, this.targetPlanet);

                long totalDifferenceInSeconds = (arrival.getTime() - Calendar.getInstance().getTime().getTime())/1000;
                long hours = Math.abs(totalDifferenceInSeconds/3600);
                long minutes = (totalDifferenceInSeconds%3600)/60;
                long seconds = totalDifferenceInSeconds - (hours*3600) - (minutes*60);

                estimateArrival.setText("El ataque llegara en: " +
                        String.valueOf(hours) + ":" + String.valueOf(minutes) + ":" + String.valueOf(seconds));
            }else{
                estimateArrival.setText("El ataque llegar en: ");
            }
        }

        public void selectTargetPlanet(Planet targetPlanet){
            this.targetPlanet = targetPlanet;
            setArrivalTime();
        }

        public ArrayList<Ship> getAttackFleet(){
            ArrayList<Ship> fleet = new ArrayList<>();
            NumberPicker xPicker = (NumberPicker) getView().findViewById(R.id.x);
            int shipsX = xPicker.getValue();
            for (int i = 0; i < shipsX; i++) {
                fleet.add(availableFleetX.get(i));
            }
            NumberPicker yPicker = (NumberPicker) getView().findViewById(R.id.y);
            int shipsY = yPicker.getValue();
            for (int i = 0; i < shipsY; i++) {
                fleet.add(availableFleetY.get(i));
            }
            NumberPicker zPicker = (NumberPicker) getView().findViewById(R.id.z);
            int shipsZ = zPicker.getValue();
            for (int i = 0; i < shipsZ; i++) {
                fleet.add(availableFleetZ.get(i));
            }

            return fleet;
        }

        public void FillGalaxies(ArrayList<Galaxy> galaxies){
            NumberPicker galaxyPicker = (NumberPicker) getView().findViewById(R.id.galaxy);
            galaxyPicker.setMinValue(0);
            galaxyPicker.setMaxValue(galaxies.size() - 1);

            ArrayList<String> galaxyNames = new ArrayList<>();
            for (Galaxy galaxy: galaxies) {
                galaxyNames.add(galaxy.getId() + "-" + galaxy.getName());
            }
            galaxiesSelected = new String[galaxyNames.size()];
            galaxiesSelected = galaxyNames.toArray(galaxiesSelected);
            galaxyPicker.setDisplayedValues(galaxiesSelected);

            planetService.GetSolarSystemsByGalaxy(act.player.getUsername(), act.player.getToken(), act, this, galaxies.get(0).getId());
        }

        public void FillSolarSystems(ArrayList<SolarSystem> solarSystems){
            NumberPicker solarSystemPicker = (NumberPicker) getView().findViewById(R.id.solarSystem);
            solarSystemPicker.setMinValue(0);
            solarSystemPicker.setMaxValue(solarSystems.size() - 1);

            ArrayList<String> solarSystemNames = new ArrayList<>();
            for (SolarSystem solarSystem: solarSystems) {
                solarSystemNames.add(solarSystem.getId() + "-" + solarSystem.getName());
            }
            solarSystemsSelected = new String[solarSystemNames.size()];
            solarSystemsSelected = solarSystemNames.toArray(solarSystemsSelected);
            solarSystemPicker.setDisplayedValues(solarSystemsSelected);

            planetService.GetPlanetsBySolarSystem(act.player.getUsername(), act.player.getToken(), act, this, solarSystems.get(0).getId());
        }

        public void FillPlanets(ArrayList<Planet> planets){
            ArrayList<Planet> finalPlanets = new ArrayList<>();
            for (Planet planet: planets) {
                if(planet.getConqueror() == null || !planet.getConqueror().getUsername().equals(act.player.getUsername())){
                    availablePlanets.put(planet.getOrder(), planet);
                    finalPlanets.add(planet);
                }
            }

            planetListAdapter = new PlanetListAdapter(finalPlanets, this);
            planetList.setAdapter(planetListAdapter);
            targetPlanet = finalPlanets.get(0);
        }

        public void FillFleets(ArrayList<ShipX> shipsX, ArrayList<ShipY> shipsY, ArrayList<ShipZ> shipsZ){
            availableFleetX.addAll(shipsX);
            availableFleetY.addAll(shipsY);
            availableFleetZ.addAll(shipsZ);

            NumberPicker xPicker = (NumberPicker) getView().findViewById(R.id.x);
            xPicker.setMinValue(0);
            xPicker.setMaxValue(shipsX.size());

            NumberPicker yPicker = (NumberPicker) getView().findViewById(R.id.y);
            yPicker.setMinValue(0);
            yPicker.setMaxValue(shipsY.size());

            NumberPicker zPicker = (NumberPicker) getView().findViewById(R.id.z);
            zPicker.setMinValue(0);
            zPicker.setMaxValue(shipsZ.size());
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class CloseAttackFragment extends Fragment {
        public CloseAttackFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static CloseAttackFragment newInstance() {
            CloseAttackFragment fragment = new CloseAttackFragment();
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_attack, container, false);
            return rootView;
        }
    }

    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch(position) {

                case 0: return AttackFragment.newInstance();
                case 1: return CoordinatesFragment.newInstance();
                case 2: return CloseAttackFragment.newInstance();
                default: return AttackFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Atacar";
                case 1:
                    return "Por Coordenadas";
                case 2:
                    return "Por Cercania";
            }
            return null;
        }
    }
}
