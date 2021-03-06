package geolaxia.geolaxia.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import geolaxia.geolaxia.Model.Adapters.PlanetColonizerListAdapter;
import geolaxia.geolaxia.Model.Adapters.PlanetListAdapter;
//import geolaxia.geolaxia.Model.Colonizer;
import geolaxia.geolaxia.Model.Dto.IsSendingColonizerDTO;
import geolaxia.geolaxia.Model.Galaxy;
import geolaxia.geolaxia.Model.Helpers;
import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.Player;
import geolaxia.geolaxia.Model.Probe;
import geolaxia.geolaxia.Model.SolarSystem;
import geolaxia.geolaxia.R;
import geolaxia.geolaxia.Services.Implementation.ColonizeService;
import geolaxia.geolaxia.Services.Implementation.PlanetService;
import geolaxia.geolaxia.Services.Interface.IColonizeService;
import geolaxia.geolaxia.Services.Interface.IPlanetService;

public class ColonizeActivity extends MenuActivity {
    final Activity context = this;

    private IColonizeService colonizeService;
    private IPlanetService planetService;

    private boolean estaColonizando = false;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    static final long ONE_MINUTE_IN_MILLIS = 60000;
    static final int COSTO_COMBUSTIBLE = 10;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.Constructor();
        this.ConstructorServicios();
    }

    private void Constructor() {
        //INICIO BASE.
        setContentView(R.layout.activity_colonize);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        super.onCreateDrawer();

        mFormView = findViewById(R.id.scroll_form);
        mProgressView = findViewById(R.id.progress);

        Intent intent = getIntent();
        player = (Player) intent.getExtras().getSerializable("player");
        planet = (Planet) intent.getExtras().getSerializable("planet");
        //FIN BASE.

        //INICIO FRAGMENTS.
        mSectionsPagerAdapter = new ColonizeActivity.SectionsPagerAdapter(getSupportFragmentManager());

        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        //FIN FRAGMENTS.
    }

    private void ConstructorServicios() {
        this.colonizeService = new ColonizeService();
        this.planetService = new PlanetService();
    }

    public Date calculateArrivalTime(Planet target){
        int lowerSpeed = 5;

        int distance = planet.getOrder() > target.getOrder() ? planet.getOrder() - target.getOrder() : target.getOrder() - planet.getOrder();
        int minutesToAdd = Math.round(distance*1000/lowerSpeed);

        Calendar date = Calendar.getInstance();
        long t= date.getTimeInMillis();
        return new Date(t + (minutesToAdd * ONE_MINUTE_IN_MILLIS));
    }

    private boolean TieneRecursosNecesariosParaColonizar(int mo) {
        boolean tieneRecursosSuficientes = true;

        if (mo > this.planet.getDarkMatter()) {
            tieneRecursosSuficientes = false;
        }

        return (tieneRecursosSuficientes);
    }

    // -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

    public static class ColonizeFragment extends Fragment {
        private ColonizeActivity act;
        private ColonizeFragment context;

        private View rootView;
        private Boolean _areLecturesLoaded = false;
        private Boolean _activityEnabled = false;

        private String[] galaxiesSelected;
        private String[] solarSystemsSelected;
        private String[] planetsSelected;

        private HashMap<Integer, Planet> availablePlanets = new HashMap<>();

        public ColonizeFragment() {
        }

        public static ColonizeFragment newInstance() {
            ColonizeFragment fragment = new ColonizeFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_colonize, container, false);
            this._activityEnabled = true;

            this.act = (ColonizeActivity) getActivity();
            this.context = this;

            this.CargarPantallaInicial();

            return (rootView);
        }

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

//            if (isVisibleToUser && !_areLecturesLoaded ) {
//                _areLecturesLoaded = true;
//
//                // Code executes ONLY THE FIRST TIME fragment is viewed.
//                int a = 0;
//            }

            if (isVisibleToUser && this._activityEnabled) {
                //Code executes EVERY TIME user views the fragment
                this.CargarPantallaInicial();
            }
        }

        private void CargarPantallaInicial() {
            this.VaciarPantalla();
            this.PantallaSegunEnvio();
            this.CargarColonizadores();
            act.planetService.GetAllGalaxies(act.player.getUsername(), act.player.getToken(), act, this);
            this.CargarSelector();
            this.EstaEnviandoColonizador();
        }

        // -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
        // Carga la seccion de Colonizadores del usuario.
        private void CargarColonizadores() {
            act.colonizeService.GetColonizers(act.player.getUsername(), act.player.getToken(), act, context, act.planet.getId());
        }

        public void CargarColonizadoresAhora(ArrayList<Probe> colonizers){
            TextView cantColonizadoresActivos = (TextView) rootView.findViewById(R.id.colonization_cant_colonizadores_disponibles);

            if (colonizers != null && !colonizers.isEmpty()) {

                int canonesActivos = colonizers.size();
                cantColonizadoresActivos.setText(String.valueOf(canonesActivos));
            } else {
                cantColonizadoresActivos.setText(String.valueOf(0));
            }
        }

        // -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
        // Carga los pickers de galaxia, sistema y planetas.
        private void CargarSelector() {
            NumberPicker galaxyPicker = (NumberPicker) rootView.findViewById(R.id.galaxy);
            Helpers.setNumberPickerTextColor(galaxyPicker, Color.WHITE);
            galaxyPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    act.planetService.GetSolarSystemsByGalaxy(act.player.getUsername(), act.player.getToken(), act, context, newVal);
                }
            });

            NumberPicker solarSystemPicker = (NumberPicker) rootView.findViewById(R.id.solarSystem);
            Helpers.setNumberPickerTextColor(solarSystemPicker, Color.WHITE);
            solarSystemPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    act.planetService.GetPlanetsBySolarSystem(act.player.getUsername(), act.player.getToken(), act, context, newVal);
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

            //setArrivalTime();
        }

        public void FillGalaxies(ArrayList<Galaxy> galaxies){
            NumberPicker galaxyPicker = (NumberPicker) getView().findViewById(R.id.galaxy);
            galaxyPicker.setMinValue(0);
            galaxyPicker.setMaxValue(galaxies.size() - 1);

            ArrayList<String> galaxyNames = new ArrayList<>();

            for (Galaxy galaxy: galaxies) {
                //galaxyNames.add(galaxy.getId() + "-" + galaxy.getName());
                galaxyNames.add(String.valueOf(galaxy.getId()));
            }

            galaxiesSelected = new String[galaxyNames.size()];
            galaxiesSelected = galaxyNames.toArray(galaxiesSelected);
            galaxyPicker.setDisplayedValues(galaxiesSelected);

            act.planetService.GetSolarSystemsByGalaxy(act.player.getUsername(), act.player.getToken(), act, this, galaxies.get(0).getId());
        }

        public void FillSolarSystems(ArrayList<SolarSystem> solarSystems){
            NumberPicker solarSystemPicker = (NumberPicker) getView().findViewById(R.id.solarSystem);
            solarSystemPicker.setMinValue(0);
            solarSystemPicker.setMaxValue(solarSystems.size() - 1);

            ArrayList<String> solarSystemNames = new ArrayList<>();

            for (SolarSystem solarSystem: solarSystems) {
                //solarSystemNames.add(solarSystem.getId() + "-" + solarSystem.getName());
                solarSystemNames.add(String.valueOf(solarSystem.getId()));
            }

            solarSystemsSelected = new String[solarSystemNames.size()];
            solarSystemsSelected = solarSystemNames.toArray(solarSystemsSelected);
            solarSystemPicker.setDisplayedValues(solarSystemsSelected);

            act.planetService.GetPlanetsBySolarSystem(act.player.getUsername(), act.player.getToken(), act, this, solarSystems.get(0).getId());
        }

        public void FillPlanets(ArrayList<Planet> planets){
            ArrayList<Planet> finalPlanets = new ArrayList<>();
            for (Planet planet: planets) {
                if(planet.getConqueror() == null){
                    finalPlanets.add(planet);
                }
            }

            NumberPicker planetsPicker = (NumberPicker) getView().findViewById(R.id.planet);
            planetsPicker.setMinValue(0);
            planetsPicker.setMaxValue(finalPlanets.size() - 1);

            ArrayList<String> planetNames = new ArrayList<>();

            for (Planet planet: finalPlanets) {
                availablePlanets.put(planet.getOrder(), planet);
                //planetNames.add(planet.getOrder() + "-" + planet.getName());
                planetNames.add(String.valueOf(planet.getOrder()));
            }

            planetsSelected = new String[planetNames.size()];
            planetsSelected = planetNames.toArray(planetsSelected);
            planetsPicker.setDisplayedValues(planetsSelected);
        }

        private void setArrivalTime(){
            TextView estimateArrival = (TextView) rootView.findViewById(R.id.costo_tiempo_valor);
            TextView costoMO = (TextView) rootView.findViewById(R.id.costo_materia_oscura_valor);
            TextView cantColonizadores = (TextView) rootView.findViewById(R.id.colonization_cant_colonizadores_disponibles);

            int cantColo = (cantColonizadores.getText().toString().length() > 0) ? Integer.valueOf(cantColonizadores.getText().toString()) : 0;

            if(cantColo > 0) {
                Planet targetPlanet = this.GetTargetPlanet();
                Date arrival = this.act.calculateArrivalTime(targetPlanet);

                long totalDifferenceInSeconds = (arrival.getTime() - Calendar.getInstance().getTime().getTime()) / 1000;
                long hours = Math.abs(totalDifferenceInSeconds / 3600);
                long minutes = (totalDifferenceInSeconds % 3600) / 60;
                long seconds = totalDifferenceInSeconds - (hours * 3600) - (minutes * 60);

                long horasTotales = (minutes > 0 || seconds > 0) ? hours + 1 : hours;
                long combustible = horasTotales * COSTO_COMBUSTIBLE;

                //if  ((combustible <= this.act.planet.getDarkMatter())){
                if  (act.TieneRecursosNecesariosParaColonizar((int)combustible) && !act.estaColonizando){
                    this.CargarBotonEnviar();
                    SetearBotonEnviar(true);
                    costoMO.setText(String.valueOf(combustible));
                    estimateArrival.setText(String.valueOf(hours) + ":" + String.valueOf(minutes) + ":" + String.valueOf(seconds));
                } else {
                    SetearBotonEnviar(false);
                }
            }
        }

        // -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

        private void CargarBotonEnviar() {
            Button boton = (Button) rootView.findViewById(R.id.colonization_enviar_boton);
            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SweetAlertDialog dialog = Helpers.getConfirmationDialog(act, "Enviar", "¿Está seguro que desea enviar el Colonizador?", "Si", "No");

                    dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            //try {
                                Planet targetPlanet = GetTargetPlanet();
                                Date arrival = act.calculateArrivalTime(targetPlanet);
                                long totalDifference = (arrival.getTime() - Calendar.getInstance().getTime().getTime());

                                act.colonizeService.SendColonizer(act.player.getUsername(), act.player.getToken(), act, context, act.planet.getId(), targetPlanet.getId(), totalDifference);
                                sweetAlertDialog.cancel();
                            //} catch (JSONException e) {
                            //    e.printStackTrace();
                            //}
                        }
                    });

                    dialog.show();
                }
            });
        }

        public void CargarTiempoEnvioColonizadorAhora(){
            //TextView estimateArrival = (TextView) getView().findViewById(R.id.costo_tiempo_valor);
            TextView costoMO = (TextView) getView().findViewById(R.id.costo_materia_oscura_valor);
            //TextView cantColonizadores = (TextView) getView().findViewById(R.id.colonization_cant_colonizadores_disponibles);

            Planet targetPlanet = GetTargetPlanet();
            Date arrival = act.calculateArrivalTime(targetPlanet);
            //long totalDifference = (arrival.getTime() - Calendar.getInstance().getTime().getTime());

            act.planet.setDarkMatter(act.planet.getDarkMatter() - Integer.valueOf(costoMO.getText().toString()));

            this.act.estaColonizando = true;

            this.CargarTiempoLlegada(arrival.getTime());

            SweetAlertDialog dialog = Helpers.getSuccesDialog(act, "Colonización", "La misión de la sonda colonizadora ha comenzado!");
            dialog.show();
        }

        private void CargarTiempoLlegada(long fechaFinalizacion){
            long tiempoRestante = fechaFinalizacion - System.currentTimeMillis();

            new CountDownTimer(tiempoRestante, 1000) {
                TextView timer = (TextView) getView().findViewById(R.id.colonization_envio_timer);

                public void onTick(long millisUntilFinished) {
                    timer.setVisibility(View.VISIBLE);
                    timer.setText("Tiempo de llegada: " + act.ObtenerHora(millisUntilFinished));
                    timer.setTextColor(Color.GREEN);
                    timer.setTypeface(null, Typeface.BOLD);
                }

                public void onFinish() {
                    timer.setVisibility(View.INVISIBLE);
                    act.estaColonizando = false;
                    CargarColonizadores();
                    PantallaSegunEnvio();
                }
            }.start();

            this.PantallaSegunEnvio();
        }

        private void EstaEnviandoColonizador() {
            act.colonizeService.IsSendingColonizer(act.player.getUsername(), act.player.getToken(), act, this, act.planet.getId());
        }

        public void EstaEnviandoColonizadorAhora(IsSendingColonizerDTO tiempoLlegada){
            if (tiempoLlegada.IsSending()) {
                this.act.estaColonizando = true;
                this.CargarTiempoLlegada(tiempoLlegada.getData());
            }
        }

        // -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

        private void VaciarPantalla(){
            TextView estimateArrival = (TextView) rootView.findViewById(R.id.costo_tiempo_valor);
            TextView costoMO = (TextView) rootView.findViewById(R.id.costo_materia_oscura_valor);

            estimateArrival.setText("-");
            costoMO.setText("-");

            this.SetearTimerEnvio(false);
        }

        private boolean EstaColonizando() {
            /*TextView timer = (TextView) rootView.findViewById(R.id.colonization_envio_timer);

            if (timer.getVisibility() == View.VISIBLE) {
                return (true);
            }

            return (false);*/
            return(this.act.estaColonizando);
        }

        private void HabilitarPantalla() {
            NumberPicker galaxyPicker = (NumberPicker) rootView.findViewById(R.id.galaxy);
            NumberPicker solarSystemPicker = (NumberPicker) rootView.findViewById(R.id.solarSystem);
            NumberPicker planetPicker = (NumberPicker) rootView.findViewById(R.id.planet);

            galaxyPicker.setEnabled(true);
            solarSystemPicker.setEnabled(true);
            planetPicker.setEnabled(true);

            this.SetearBotonEnviar(true);
        }

        private void DeshabilitarPantalla() {
            NumberPicker galaxyPicker = (NumberPicker) rootView.findViewById(R.id.galaxy);
            NumberPicker solarSystemPicker = (NumberPicker) rootView.findViewById(R.id.solarSystem);
            NumberPicker planetPicker = (NumberPicker) rootView.findViewById(R.id.planet);

            galaxyPicker.setEnabled(false);
            solarSystemPicker.setEnabled(false);
            planetPicker.setEnabled(false);

            this.SetearBotonEnviar(false);

            this.VaciarPantalla();
        }

        private void PantallaSegunEnvio() {
            if (this.EstaColonizando()) {
                this.DeshabilitarPantalla();
            } else {
                this.HabilitarPantalla();
            }
        }

        private Planet GetTargetPlanet() {
            NumberPicker planetsPicker = (NumberPicker) getView().findViewById(R.id.planet);
            //int planetOrder = Integer.valueOf(planetsSelected[planetsPicker.getValue()].split("-")[0]);
            int planetOrder = Integer.valueOf(planetsSelected[planetsPicker.getValue()]);
            Planet targetPlanet = availablePlanets.get(planetOrder);

            return(targetPlanet);
        }

        private void SetearBotonEnviar(boolean activo) {
            Button boton = (Button) rootView.findViewById(R.id.colonization_enviar_boton);
            boton.setEnabled(activo);
            boton.setPaintFlags((!activo) ? Paint.STRIKE_THRU_TEXT_FLAG : 0);
        }

        private void SetearTimerEnvio(boolean activo) {
            TextView timer = (TextView) rootView.findViewById(R.id.colonization_envio_timer);

            if (activo) {
                timer.setVisibility(View.VISIBLE);
            } else {
                timer.setVisibility(View.INVISIBLE);
            }
        }
    }

    public static class CoordinatesFragment extends Fragment {
        private CoordinatesFragment context;
        private ColonizeActivity act;
        RecyclerView planetList;
        LinearLayoutManager planetListManager;
        PlanetColonizerListAdapter planetListAdapter;
        private HashMap<Integer, Planet> availablePlanets = new HashMap<>();
        private String[] galaxiesSelected;
        private String[] solarSystemsSelected;
        private Planet targetPlanet;
        private View rootView;
        private Boolean _areLecturesLoaded = false;
        private Boolean _activityEnabled = false;

        public CoordinatesFragment() {
        }

        public static CoordinatesFragment newInstance() {
            CoordinatesFragment fragment = new CoordinatesFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);

            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            rootView = inflater.inflate(R.layout.fragment_colonize_coordinates, container, false);
            this._activityEnabled = true;

            this.context = this;
            this.act = (ColonizeActivity) getActivity();

            planetList = (RecyclerView) rootView.findViewById(R.id.planetList);
            planetListManager = new LinearLayoutManager(act);
            planetList.setLayoutManager(planetListManager);

            this.CargarPantallaInicial();

            return rootView;
        }

        @Override
        public void setUserVisibleHint(boolean isVisibleToUser) {
            super.setUserVisibleHint(isVisibleToUser);

//            if (isVisibleToUser && !_areLecturesLoaded ) {
//                _areLecturesLoaded = true;
//
//                // Code executes ONLY THE FIRST TIME fragment is viewed.
//                int a = 0;
//            }

            if (isVisibleToUser && this._activityEnabled) {
                //Code executes EVERY TIME user views the fragment
                this.CargarPantallaInicial();
            }
        }

        private void CargarPantallaInicial(){
            this.VaciarPantalla();
            this.PantallaSegunEnvio();
            this.CargarColonizadores();
            act.planetService.GetAllGalaxies(act.player.getUsername(), act.player.getToken(), act, this);
            this.CargarSelector();
            this.EstaEnviandoColonizador();
        }

        // -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
        // Carga la seccion de Colonizadores del usuario.
        private void CargarColonizadores() {
            act.colonizeService.GetColonizers(act.player.getUsername(), act.player.getToken(), act, context, act.planet.getId());
        }

        public void CargarColonizadoresAhora(ArrayList<Probe> colonizers){
            TextView cantColonizadoresActivos = (TextView) rootView.findViewById(R.id.colonization_cant_colonizadores_disponibles);

            if (colonizers != null && !colonizers.isEmpty()) {

                int canonesActivos = colonizers.size();
                cantColonizadoresActivos.setText(String.valueOf(canonesActivos));
            } else {
                cantColonizadoresActivos.setText(String.valueOf(0));
            }
        }

        // -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
        // Carga los pickers de galaxia, sistema y planetas.
        private void CargarSelector() {
            NumberPicker galaxyPicker = (NumberPicker) rootView.findViewById(R.id.galaxy);
            Helpers.setNumberPickerTextColor(galaxyPicker, Color.WHITE);
            galaxyPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    act.planetService.GetSolarSystemsByGalaxy(act.player.getUsername(), act.player.getToken(), act, context, newVal);
                }
            });

            NumberPicker solarSystemPicker = (NumberPicker) rootView.findViewById(R.id.solarSystem);
            Helpers.setNumberPickerTextColor(solarSystemPicker, Color.WHITE);
            solarSystemPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    act.planetService.GetPlanetsBySolarSystem(act.player.getUsername(), act.player.getToken(), act, context, newVal);
                }
            });
        }

        public void FillGalaxies(ArrayList<Galaxy> galaxies){
            NumberPicker galaxyPicker = (NumberPicker) getView().findViewById(R.id.galaxy);
            galaxyPicker.setMinValue(0);
            galaxyPicker.setMaxValue(galaxies.size() - 1);

            ArrayList<String> galaxyNames = new ArrayList<>();
            for (Galaxy galaxy: galaxies) {
                //galaxyNames.add(galaxy.getId() + "-" + galaxy.getName());
                galaxyNames.add(String.valueOf(galaxy.getId()));
            }
            galaxiesSelected = new String[galaxyNames.size()];
            galaxiesSelected = galaxyNames.toArray(galaxiesSelected);
            galaxyPicker.setDisplayedValues(galaxiesSelected);

            act.planetService.GetSolarSystemsByGalaxy(act.player.getUsername(), act.player.getToken(), act, this, galaxies.get(0).getId());
        }

        public void FillSolarSystems(ArrayList<SolarSystem> solarSystems){
            NumberPicker solarSystemPicker = (NumberPicker) getView().findViewById(R.id.solarSystem);
            solarSystemPicker.setMinValue(0);
            solarSystemPicker.setMaxValue(solarSystems.size() - 1);

            ArrayList<String> solarSystemNames = new ArrayList<>();
            for (SolarSystem solarSystem: solarSystems) {
                //solarSystemNames.add(solarSystem.getId() + "-" + solarSystem.getName());
                solarSystemNames.add(String.valueOf(solarSystem.getId()));
            }
            solarSystemsSelected = new String[solarSystemNames.size()];
            solarSystemsSelected = solarSystemNames.toArray(solarSystemsSelected);
            solarSystemPicker.setDisplayedValues(solarSystemsSelected);

            act.planetService.GetPlanetsBySolarSystem(act.player.getUsername(), act.player.getToken(), act, this, solarSystems.get(0).getId());
        }

        public void FillPlanets(ArrayList<Planet> planets){
            ArrayList<Planet> finalPlanets = new ArrayList<>();
            for (Planet planet: planets) {
                if(planet.getConqueror() == null){
                    availablePlanets.put(planet.getOrder(), planet);
                    finalPlanets.add(planet);
                }
            }

            planetListAdapter = new PlanetColonizerListAdapter(finalPlanets, this);
            planetList.setAdapter(planetListAdapter);
            targetPlanet = finalPlanets.get(0);
        }

        public void selectTargetPlanet(Planet targetPlanet){
            if (!EstaColonizando()) {
                this.targetPlanet = targetPlanet;
                setArrivalTime();
            }
        }

        private void setArrivalTime(){
            TextView estimateArrival = (TextView) rootView.findViewById(R.id.costo_tiempo_valor);
            TextView costoMO = (TextView) rootView.findViewById(R.id.costo_materia_oscura_valor);
            TextView cantColonizadores = (TextView) rootView.findViewById(R.id.colonization_cant_colonizadores_disponibles);

            int cantColo = (cantColonizadores.getText().toString().length() > 0) ? Integer.valueOf(cantColonizadores.getText().toString()) : 0;

            if(cantColo > 0) {
                Planet targetPlanet = this.GetTargetPlanet();
                Date arrival = this.act.calculateArrivalTime(targetPlanet);

                long totalDifferenceInSeconds = (arrival.getTime() - Calendar.getInstance().getTime().getTime()) / 1000;
                long hours = Math.abs(totalDifferenceInSeconds / 3600);
                long minutes = (totalDifferenceInSeconds % 3600) / 60;
                long seconds = totalDifferenceInSeconds - (hours * 3600) - (minutes * 60);

                long horasTotales = (minutes > 0 || seconds > 0) ? hours + 1 : hours;
                long combustible = horasTotales * COSTO_COMBUSTIBLE;

                if  (act.TieneRecursosNecesariosParaColonizar((int)combustible) && !act.estaColonizando){
                    this.CargarBotonEnviar();
                    SetearBotonEnviar(true);
                    costoMO.setText(String.valueOf(combustible));
                    estimateArrival.setText(String.valueOf(hours) + ":" + String.valueOf(minutes) + ":" + String.valueOf(seconds));
                } else {
                    SetearBotonEnviar(false);
                }
            }
        }

        // -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

        private void CargarBotonEnviar() {
            Button boton = (Button) rootView.findViewById(R.id.colonization_enviar_boton);
            boton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    SweetAlertDialog dialog = Helpers.getConfirmationDialog(act, "Enviar", "¿Está seguro que desea enviar el Colonizador?", "Si", "No");

                    dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            Planet targetPlanet = GetTargetPlanet();
                            Date arrival = act.calculateArrivalTime(targetPlanet);
                            long totalDifference = (arrival.getTime() - Calendar.getInstance().getTime().getTime());

                            act.colonizeService.SendColonizer(act.player.getUsername(), act.player.getToken(), act, context, act.planet.getId(), targetPlanet.getId(), totalDifference);
                            sweetAlertDialog.cancel();
                        }
                    });

                    dialog.show();
                }
            });
        }

        public void CargarTiempoEnvioColonizadorAhora(){
            TextView costoMO = (TextView) getView().findViewById(R.id.costo_materia_oscura_valor);

            Planet targetPlanet = GetTargetPlanet();
            Date arrival = act.calculateArrivalTime(targetPlanet);

            act.planet.setDarkMatter(act.planet.getDarkMatter() - Integer.valueOf(costoMO.getText().toString()));

            this.act.estaColonizando = true;

            this.CargarTiempoLlegada(arrival.getTime());

            SweetAlertDialog dialog = Helpers.getSuccesDialog(act, "Colonización", "La misión de la sonda colonizadora ha comenzado!");
            dialog.show();
        }

        private void CargarTiempoLlegada(long fechaFinalizacion){
            long tiempoRestante = fechaFinalizacion - System.currentTimeMillis();

            new CountDownTimer(tiempoRestante, 1000) {
                TextView timer = (TextView) getView().findViewById(R.id.colonization_envio_timer);

                public void onTick(long millisUntilFinished) {
                    timer.setVisibility(View.VISIBLE);
                    timer.setText("Tiempo de llegada: " + act.ObtenerHora(millisUntilFinished));
                    timer.setTextColor(Color.GREEN);
                    timer.setTypeface(null, Typeface.BOLD);
                }

                public void onFinish() {
                    timer.setVisibility(View.INVISIBLE);
                    act.estaColonizando = false;
                    CargarColonizadores();
                    PantallaSegunEnvio();
                }
            }.start();

            this.PantallaSegunEnvio();
        }

        private void EstaEnviandoColonizador() {
            act.colonizeService.IsSendingColonizer(act.player.getUsername(), act.player.getToken(), act, this, act.planet.getId());
        }

        public void EstaEnviandoColonizadorAhora(IsSendingColonizerDTO tiempoLlegada){
            if (tiempoLlegada.IsSending()) {
                this.act.estaColonizando = true;
                this.CargarTiempoLlegada(tiempoLlegada.getData());
            }
        }

        // -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

        private void VaciarPantalla(){
            TextView estimateArrival = (TextView) rootView.findViewById(R.id.costo_tiempo_valor);
            TextView costoMO = (TextView) rootView.findViewById(R.id.costo_materia_oscura_valor);

            estimateArrival.setText("-");
            costoMO.setText("-");

            this.SetearTimerEnvio(false);
        }

        private boolean EstaColonizando() {
            /*TextView timer = (TextView) rootView.findViewById(R.id.colonization_envio_timer);

            if (timer.getVisibility() == View.VISIBLE) {
                return (true);
            }

            return (false);*/
            return (this.act.estaColonizando);
        }

        private void HabilitarPantalla() {
            NumberPicker galaxyPicker = (NumberPicker) rootView.findViewById(R.id.galaxy);
            NumberPicker solarSystemPicker = (NumberPicker) rootView.findViewById(R.id.solarSystem);

            galaxyPicker.setEnabled(true);
            solarSystemPicker.setEnabled(true);

            this.SetearBotonEnviar(true);
        }

        private void DeshabilitarPantalla() {
            NumberPicker galaxyPicker = (NumberPicker) rootView.findViewById(R.id.galaxy);
            NumberPicker solarSystemPicker = (NumberPicker) rootView.findViewById(R.id.solarSystem);

            galaxyPicker.setEnabled(false);
            solarSystemPicker.setEnabled(false);

            this.SetearBotonEnviar(false);

            this.VaciarPantalla();
        }

        private void PantallaSegunEnvio() {
            boolean estaConstruyendo = this.EstaColonizando();

            if (estaConstruyendo) {
                this.DeshabilitarPantalla();
            } else {
                this.HabilitarPantalla();
            }
        }

        private Planet GetTargetPlanet() {
            return(this.targetPlanet);
        }

        private void SetearBotonEnviar(boolean activo) {
            Button boton = (Button) rootView.findViewById(R.id.colonization_enviar_boton);
            boton.setEnabled(activo);
            boton.setPaintFlags((!activo) ? Paint.STRIKE_THRU_TEXT_FLAG : 0);
        }

        private void SetearTimerEnvio(boolean activo) {
            TextView timer = (TextView) rootView.findViewById(R.id.colonization_envio_timer);

            if (activo) {
                timer.setVisibility(View.VISIBLE);
            } else {
                timer.setVisibility(View.INVISIBLE);
            }
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {
        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {

                case 0: return ColonizeFragment.newInstance();
                case 1: return CoordinatesFragment.newInstance();
                default: return ColonizeFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Colonizar";
                case 1:
                    return "Por Coordenadas";
            }
            return null;
        }
    }
}

