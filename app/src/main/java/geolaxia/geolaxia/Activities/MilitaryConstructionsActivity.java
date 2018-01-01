package geolaxia.geolaxia.Activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.support.design.widget.TabLayout;
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

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import geolaxia.geolaxia.Model.Hangar;
import geolaxia.geolaxia.Model.Helpers;
import geolaxia.geolaxia.Model.Military;
import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.Player;
import geolaxia.geolaxia.Model.Probe;
import geolaxia.geolaxia.Model.Shield;
import geolaxia.geolaxia.Model.Ship;
import geolaxia.geolaxia.Model.ShipX;
import geolaxia.geolaxia.Model.ShipY;
import geolaxia.geolaxia.Model.ShipZ;
import geolaxia.geolaxia.Model.Trader;
import geolaxia.geolaxia.R;
import geolaxia.geolaxia.Services.Implementation.ConstructionService;
import geolaxia.geolaxia.Services.Interface.IConstructionService;

public class MilitaryConstructionsActivity extends MenuActivity {

    private int crystalUsed;
    private int metalUsed;
    private boolean hasHangar;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_military_constructions);

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

        hasHangar = false;
    }

    public static class HangarFragment extends Fragment {
        private MilitaryConstructionsActivity act;
        private IConstructionService constructionService;
        private HangarFragment fragment;

        Button build;
        TextView timerView;

        Hangar hangar;

        public HangarFragment() {
        }

        public static HangarFragment newInstance() {
            HangarFragment fragment = new HangarFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_military_constructions_hangar, container, false);

            fragment = this;
            act = (MilitaryConstructionsActivity) getActivity();
            constructionService = new ConstructionService();
            constructionService.GetCurrentHangar(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);
            constructionService.GetHangarBuildingTime(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);

            timerView = (TextView) rootView.findViewById(R.id.timer);

            build = (Button) rootView.findViewById(R.id.buildButton);
            build.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    confirmConstruction(hangar);
                }
            });

            return rootView;
        }

        public void confirmConstruction(final Hangar hangar){
            hangar.setPlanet(act.planet);
            final Hangar hangarToAdd = hangar;
            SweetAlertDialog dialog = Helpers.getConfirmationDialog(act, "Confirmar", "Esta seguro que desea comenzar la construccion?", "Construir", "Cancelar");
            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    constructionService.BuildHangar(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);
                    act.crystalUsed = hangarToAdd.getCost().getCrystalCost();
                    act.metalUsed = hangarToAdd.getCost().getMetalCost();
                    sweetAlertDialog.cancel();
                }
            });

            dialog.show();
        }

        public void setHangarValues(Hangar hangar){
            this.hangar = hangar;
            TextView cost = (TextView) getView().findViewById(R.id.costText);
            cost.setText("Costo: Cristal " + String.valueOf(hangar.getCost().getCrystalCost()) + " Metal " +
                    String.valueOf(hangar.getCost().getMetalCost()) + " Materia oscura: " + String.valueOf(hangar.getCost().getDarkMatterCost()));
            TextView timeCost = (TextView) getView().findViewById(R.id.timeText);
            timeCost.setText("Tiempo finalizacion: " + String.valueOf(hangar.getConstructionTime()));
            TextView requiredLevel = (TextView) getView().findViewById(R.id.requiredLevel);
            requiredLevel.setText("Nivel requerido: " + String.valueOf(hangar.getRequiredLevel()));
            build.setText("Construir");

            checkAvailability(hangar);

            if(hangar.getPlanet().getId() == act.planet.getId()){
                build.setPaintFlags(build.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                build.setEnabled(false);
                act.hasHangar = true;
            }

            if(act.player.getLevel() < hangar.getRequiredLevel()){
                build.setPaintFlags(build.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                build.setEnabled(false);
            }
        }

        public void SetCurrentConstructionValues(long buildingTime){
            if(buildingTime > 0){
                InConstruction(buildingTime);
            }
        }

        public void InConstruction(long buildingTime){
            build.setPaintFlags(build.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            build.setEnabled(false);
            SetConstructionTimer(timerView, buildingTime);
        }

        public void checkAvailability(Hangar hangar){
            if(!hasResources(hangar.getCost().getCrystalCost(), hangar.getCost().getMetalCost() ,hangar.getCost().getDarkMatterCost())){
                build.setPaintFlags(build.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                build.setEnabled(false);
            } else {
                build.setPaintFlags(0);
                build.setEnabled(true);
            }
        }

        public boolean hasResources(int crystal, int metal, int darkMatter){
            if(crystal <= act.planet.getCrystal() && metal <= act.planet.getMetal()
                    && darkMatter <= act.planet.getDarkMatter()){
                return true;
            }
            return false;
        }

        public void HangarBuilt(Hangar hangar){
            SetConstructionTimer(timerView, hangar.getEnableDate().getTime());
            act.player.getPlanet(act.planet.getId()).setCrystal(act.planet.getCrystal() - act.crystalUsed);
            act.player.getPlanet(act.planet.getId()).setMetal(act.planet.getMetal() - act.metalUsed);
            SweetAlertDialog dialog = Helpers.getSuccesDialog(act, "Construccion", "La construccion del hangar ha comenzado!");
            dialog.show();
        }

        private void SetConstructionTimer(final TextView timer, long fechaFinalizacion) {
            long tiempoRestante = fechaFinalizacion - System.currentTimeMillis();

            new CountDownTimer(tiempoRestante, 1000) {
                public void onTick(long millisUntilFinished) {
                    timer.setVisibility(View.VISIBLE);
                    timer.setText("Tiempo para finalización: " +
                            String.valueOf(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)) + ":" +
                            String.valueOf(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished))) + ":" +
                            String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))
                    );
                    timer.setTextColor(Color.GREEN);
                    timer.setTypeface(null, Typeface.BOLD);
                }

                public void onFinish() {
                    timer.setVisibility(View.INVISIBLE);
                    build.setPaintFlags(build.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    build.setEnabled(false);
                }

            }.start();
        }
    }

    public static class ShipsFragment extends Fragment {
        private MilitaryConstructionsActivity act;
        private IConstructionService constructionService;
        private ShipsFragment fragment;

        Button buildShipX;
        Button buildShipY;
        Button buildShipZ;
        TextView timerView;

        int shipXQttToBuild;
        int shipYQttToBuild;
        int shipZQttToBuild;

        public ShipsFragment() {
        }

        public static ShipsFragment newInstance() {
            ShipsFragment fragment = new ShipsFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_military_constructions_ships, container, false);

            fragment = this;
            act = (MilitaryConstructionsActivity) getActivity();
            constructionService = new ConstructionService();
            constructionService.GetFleet(act.player.getUsername(), act.player.getToken(), act, fragment, act.planet.getId());
            constructionService.GetShipsCost(act.player.getUsername(), act.player.getToken(), act, fragment);
            constructionService.GetShipsBuildingTime(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);

            buildShipX = (Button) rootView.findViewById(R.id.shipXBuildButton);
            buildShipX.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timerView = (TextView) rootView.findViewById(R.id.shipX_timer);
                    shipXQttToBuild = ((NumberPicker)rootView.findViewById(R.id.shipX)).getValue();
                    confirmConstructionShips(shipXQttToBuild, 0);
                }
            });

            buildShipY = (Button) rootView.findViewById(R.id.shipYBuildButton);
            buildShipY.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timerView = (TextView) rootView.findViewById(R.id.shipY_timer);
                    shipYQttToBuild = ((NumberPicker)rootView.findViewById(R.id.shipY)).getValue();
                    confirmConstructionShips(shipYQttToBuild, 1);
                }
            });

            buildShipZ = (Button) rootView.findViewById(R.id.shipZBuildButton);
            buildShipZ.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timerView = (TextView) rootView.findViewById(R.id.shipZ_timer);
                    shipZQttToBuild = ((NumberPicker)rootView.findViewById(R.id.shipZ)).getValue();
                    confirmConstructionShips(shipZQttToBuild, 2);
                }
            });

            LoadConstructionShipX(rootView);
            LoadConstructionShipY(rootView);
            LoadConstructionShipZ(rootView);

            return rootView;
        }

        private void LoadConstructionShipX(View rootView) {
            NumberPicker np = (NumberPicker) rootView.findViewById(R.id.shipX);
            Helpers.setNumberPickerTextColor(np, Color.WHITE);
            np.setMinValue(0);
            np.setMaxValue(50);
            np.setWrapSelectorWheel(true);

            np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                TextView shipXCostText = (TextView) fragment.getView().findViewById(R.id.shipXCostText);
                TextView shipXTimeText = (TextView) fragment.getView().findViewById(R.id.shipXTimeText);

                if (newVal > 0) {
                    int crystalCost = newVal * 50;
                    int metalCost = newVal * 250;
                    int timeCost =  newVal * 5;

                    shipXCostText.setText("Costo: Cristal " + String.valueOf(crystalCost) + " Metal " + String.valueOf(metalCost));
                    shipXTimeText.setText("Tiempo finalizacion: " + String.valueOf(timeCost));

                    if (hasResources(crystalCost, metalCost, 0) && act.player.getLevel() >= 2) {
                        buildShipX.setPaintFlags(0);
                        buildShipX.setEnabled(true);
                    } else {
                        buildShipX.setPaintFlags(buildShipX.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        buildShipX.setEnabled(false);
                    }
                } else {
                    buildShipX.setPaintFlags(buildShipX.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    buildShipX.setEnabled(false);
                    shipXCostText.setText("Costo: ");
                    shipXTimeText.setText("Tiempo finalizacion: ");
                }
                }
            });
        }

        private void LoadConstructionShipY(View rootView) {
            NumberPicker np = (NumberPicker) rootView.findViewById(R.id.shipY);
            Helpers.setNumberPickerTextColor(np, Color.WHITE);
            np.setMinValue(0);
            np.setMaxValue(50);
            np.setWrapSelectorWheel(true);

            np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                TextView shipYCostText = (TextView) fragment.getView().findViewById(R.id.shipYCostText);
                TextView shipYTimeText = (TextView) fragment.getView().findViewById(R.id.shipYTimeText);

                if (newVal > 0) {
                    int crystalCost = newVal * 75;
                    int metalCost = newVal * 500;
                    int timeCost =  newVal * 10;

                    shipYCostText.setText("Costo: Cristal " + String.valueOf(crystalCost) + " Metal " + String.valueOf(metalCost));
                    shipYTimeText.setText("Tiempo finalizacion: " + String.valueOf(timeCost));

                    if (hasResources(crystalCost, metalCost, 0) && act.player.getLevel() >= 3) {
                        buildShipY.setPaintFlags(0);
                        buildShipY.setEnabled(true);
                    } else {
                        buildShipY.setPaintFlags(buildShipY.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        buildShipY.setEnabled(false);
                    }
                } else {
                    buildShipY.setPaintFlags(buildShipY.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    buildShipY.setEnabled(false);
                    shipYCostText.setText("Costo: ");
                    shipYTimeText.setText("Tiempo finalizacion: ");
                }
                }
            });
        }

        private void LoadConstructionShipZ(View rootView) {
            NumberPicker np = (NumberPicker) rootView.findViewById(R.id.shipZ);
            Helpers.setNumberPickerTextColor(np, Color.WHITE);
            np.setMinValue(0);
            np.setMaxValue(50);
            np.setWrapSelectorWheel(true);

            np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                TextView shipZCostText = (TextView) fragment.getView().findViewById(R.id.shipZCostText);
                TextView shipZTimeText = (TextView) fragment.getView().findViewById(R.id.shipZTimeText);

                if (newVal > 0) {
                    int crystalCost = newVal * 100;
                    int metalCost = newVal * 1000;
                    int timeCost =  newVal * 15;

                    shipZCostText.setText("Costo: Cristal " + String.valueOf(crystalCost) + " Metal " + String.valueOf(metalCost));
                    shipZTimeText.setText("Tiempo finalizacion: " + String.valueOf(timeCost));

                    if (hasResources(crystalCost, metalCost, 0) && act.player.getLevel() >= 5) {
                        buildShipZ.setPaintFlags(0);
                        buildShipZ.setEnabled(true);
                    } else {
                        buildShipZ.setPaintFlags(buildShipZ.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        buildShipZ.setEnabled(false);
                    }
                } else {
                    buildShipZ.setPaintFlags(buildShipZ.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                    buildShipZ.setEnabled(false);
                    shipZCostText.setText("Costo: ");
                    shipZTimeText.setText("Tiempo finalizacion: ");
                }
                }
            });
        }

        public boolean hasResources(int crystal, int metal, int darkMatter){
            if(crystal <= act.planet.getCrystal() && metal <= act.planet.getMetal()
                    && darkMatter <= act.planet.getDarkMatter()){
                return true;
            }
            return false;
        }

        public void confirmConstructionShips(final int qtt, final int shipType){
            SweetAlertDialog dialog = Helpers.getConfirmationDialog(act, "Confirmar", "Esta seguro que desea comenzar la construccion?", "Construir", "Cancelar");
            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    constructionService.BuildShips(act.player.getUsername(), act.player.getToken(), act, fragment, act.planet.getId(), qtt, shipType);
                    act.crystalUsed = qtt * (50 + 25*shipType);
                    act.metalUsed = qtt * (20 + (250*shipType));
                    sweetAlertDialog.cancel();
                }
            });

            dialog.show();
        }

        public void setCurrentConstructionValues(long shipXBuildingTime, long shipYBuildingTime, long shipZBuildingTime){
            if(shipXBuildingTime > 0){
                InConstruction(buildShipX, (TextView)getView().findViewById(R.id.shipX_timer), shipXBuildingTime);
            }
            if(shipYBuildingTime > 0){
                InConstruction(buildShipY, (TextView)getView().findViewById(R.id.shipY_timer), shipYBuildingTime);
            }
            if(shipZBuildingTime > 0){
                InConstruction(buildShipZ, (TextView)getView().findViewById(R.id.shipZ_timer), shipZBuildingTime);
            }
        }

        public void InConstruction(Button buildButton, TextView timer, long buildingTime){
            buildButton.setPaintFlags(buildButton.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            buildButton.setEnabled(false);
            SetConstructionTimer(timer, buildingTime);
        }

        public void setCurrentValues(ArrayList<ShipX> shipsX, ArrayList<ShipY> shipsY, ArrayList<ShipZ> shipsZ){
            TextView shipXQtt = (TextView) getView().findViewById(R.id.shipXQttText);
            shipXQtt.setText("Cantidad Actual: " + String.valueOf(shipsX.size()));

            TextView shipYQtt = (TextView) getView().findViewById(R.id.shipYQttText);
            shipYQtt.setText("Cantidad Actual: " + String.valueOf(shipsY.size()));

            TextView shipZQtt = (TextView) getView().findViewById(R.id.shipZQttText);
            shipZQtt.setText("Cantidad Actual: " + String.valueOf(shipsZ.size()));

            if(!act.hasHangar){
                buildShipX.setPaintFlags(buildShipX.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                buildShipX.setEnabled(false);
                buildShipY.setPaintFlags(buildShipY.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                buildShipY.setEnabled(false);
                buildShipZ.setPaintFlags(buildShipZ.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                buildShipZ.setEnabled(false);
            }
        }

        public void setCosts(ShipX shipX, ShipY shipY, ShipZ shipZ){
            TextView shipXAttack = (TextView) getView().findViewById(R.id.shipXAttackText);
            shipXAttack.setText("Ataque: " + shipX.getAttack());
            TextView shipXDefense = (TextView) getView().findViewById(R.id.shipXDefenseText);
            shipXDefense.setText("Defensa: " + shipX.getDefence());
            TextView shipXSpeed = (TextView) getView().findViewById(R.id.shipXSpeedText);
            shipXSpeed.setText("Velocidad: " + shipX.getSpeed());
            TextView shipXRequiredLevel = (TextView) getView().findViewById(R.id.shipXRequiredLevelText);
            shipXRequiredLevel.setText("Nivel Requerido: " + shipX.getRequiredLevel());

            TextView shipYAttack = (TextView) getView().findViewById(R.id.shipYAttackText);
            shipYAttack.setText("Ataque: " + shipY.getAttack());
            TextView shipYDefense = (TextView) getView().findViewById(R.id.shipYDefenseText);
            shipYDefense.setText("Defensa: " + shipY.getDefence());
            TextView shipYSpeed = (TextView) getView().findViewById(R.id.shipYSpeedText);
            shipYSpeed.setText("Velocidad: " + shipY.getSpeed());
            TextView shipYRequiredLevel = (TextView) getView().findViewById(R.id.shipYRequiredLevelText);
            shipYRequiredLevel.setText("Nivel Requerido: " + shipY.getRequiredLevel());

            TextView shipZAttack = (TextView) getView().findViewById(R.id.shipZAttackText);
            shipZAttack.setText("Ataque: " + shipZ.getAttack());
            TextView shipZDefense = (TextView) getView().findViewById(R.id.shipZDefenseText);
            shipZDefense.setText("Defensa: " + shipZ.getDefence());
            TextView shipZSpeed = (TextView) getView().findViewById(R.id.shipZSpeedText);
            shipZSpeed.setText("Velocidad: " + shipZ.getSpeed());
            TextView shipZRequiredLevel = (TextView) getView().findViewById(R.id.shipZRequiredLevelText);
            shipZRequiredLevel.setText("Nivel Requerido: " + shipZ.getRequiredLevel());
        }

        public void ShipBuilt(Ship ship){
            SetConstructionTimer(timerView, ship.getEnableDate().getTime());
            act.player.getPlanet(act.planet.getId()).setCrystal(act.planet.getCrystal() - act.crystalUsed);
            act.player.getPlanet(act.planet.getId()).setMetal(act.planet.getMetal() - act.metalUsed);
            constructionService.GetFleet(act.player.getUsername(), act.player.getToken(), act, fragment, act.planet.getId());
            SweetAlertDialog dialog = Helpers.getSuccesDialog(act, "Construccion", "La construccion de las instalaciones ha comenzado!");
            dialog.show();
        }

        private void SetConstructionTimer(final TextView timer, long fechaFinalizacion) {
            long tiempoRestante = fechaFinalizacion - System.currentTimeMillis();

            new CountDownTimer(tiempoRestante, 1000) {
                public void onTick(long millisUntilFinished) {
                    timer.setVisibility(View.VISIBLE);
                    timer.setText("Tiempo para finalización: " +
                            String.valueOf(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)) + ":" +
                            String.valueOf(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished))) + ":" +
                            String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))
                    );
                    timer.setTextColor(Color.GREEN);
                    timer.setTypeface(null, Typeface.BOLD);
                }

                public void onFinish() {
                    timer.setVisibility(View.INVISIBLE);
                    constructionService.GetFleet(act.player.getUsername(), act.player.getToken(), act, fragment, act.planet.getId());
                }

            }.start();
        }
    }

    public static class OthersFragment extends Fragment {
        private MilitaryConstructionsActivity act;
        private IConstructionService constructionService;
        private OthersFragment fragment;

        Button buildShield;
        Button buildProbes;
        Button buildTraders;
        TextView timerView;

        Shield shield;
        int probeQttToBuild;
        int traderQttToBuild;

        public OthersFragment() {
        }

        public static OthersFragment newInstance() {
            OthersFragment fragment = new OthersFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_military_constructions_others, container, false);

            fragment = this;
            act = (MilitaryConstructionsActivity) getActivity();
            constructionService = new ConstructionService();
            constructionService.GetCurrentShield(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);
            constructionService.GetCurrentProbes(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);
            constructionService.GetCurrentTraders(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);
            constructionService.GetOthersBuildingTime(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);

            buildShield = (Button) rootView.findViewById(R.id.shieldBuildButton);
            buildShield.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timerView = (TextView) rootView.findViewById(R.id.shield_timer);
                    confirmConstruction(shield);
                }
            });

            buildProbes = (Button) rootView.findViewById(R.id.probeBuildButton);
            buildProbes.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timerView = (TextView) rootView.findViewById(R.id.probe_timer);
                    probeQttToBuild = ((NumberPicker) rootView.findViewById(R.id.probe)).getValue();
                    confirmConstructionProbes(probeQttToBuild);
                }
            });

            buildTraders = (Button) rootView.findViewById(R.id.freighterBuildButton);
            buildTraders.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timerView = (TextView) rootView.findViewById(R.id.freighter_timer);
                    traderQttToBuild = ((NumberPicker) rootView.findViewById(R.id.freighter)).getValue();
                    confirmConstructionTraders(traderQttToBuild);
                }
            });

            LoadConstructionProbes(rootView);
            LoadConstructionTraders(rootView);

            return rootView;
        }

        private void LoadConstructionProbes(View rootView) {
            NumberPicker np = (NumberPicker) rootView.findViewById(R.id.probe);
            Helpers.setNumberPickerTextColor(np, Color.WHITE);
            np.setMinValue(0);
            np.setMaxValue(50);
            np.setWrapSelectorWheel(true);

            np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    TextView probeCostText = (TextView) fragment.getView().findViewById(R.id.probeCostText);
                    TextView probeTimeText = (TextView) fragment.getView().findViewById(R.id.probeTimeText);

                    if (newVal > 0) {
                        int crystalCost = newVal * 50;
                        int metalCost = newVal * 250;
                        int timeCost = newVal * 30;

                        probeCostText.setText("Costo: Cristal " + String.valueOf(crystalCost) + " Metal " + String.valueOf(metalCost));
                        probeTimeText.setText("Tiempo finalizacion: " + String.valueOf(timeCost));

                        if (hasResources(crystalCost, metalCost, 0) && act.player.getLevel() >= 5) {
                            buildProbes.setPaintFlags(0);
                            buildProbes.setEnabled(true);
                        } else {
                            buildProbes.setPaintFlags(buildProbes.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            buildProbes.setEnabled(false);
                        }
                    } else {
                        buildProbes.setPaintFlags(buildProbes.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        buildProbes.setEnabled(false);
                        probeCostText.setText("Costo: ");
                        probeTimeText.setText("Tiempo finalizacion: ");
                    }
                }
            });
        }

        private void LoadConstructionTraders(View rootView) {
            NumberPicker np = (NumberPicker) rootView.findViewById(R.id.freighter);
            Helpers.setNumberPickerTextColor(np, Color.WHITE);
            np.setMinValue(0);
            np.setMaxValue(50);
            np.setWrapSelectorWheel(true);

            np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    TextView freighterCostText = (TextView) fragment.getView().findViewById(R.id.freighterCostText);
                    TextView freighterTimeText = (TextView) fragment.getView().findViewById(R.id.freighterTimeText);

                    if (newVal > 0) {
                        int crystalCost = newVal * 50;
                        int metalCost = newVal * 250;
                        int timeCost = newVal * 30;

                        freighterCostText.setText("Costo: Cristal " + String.valueOf(crystalCost) + " Metal " + String.valueOf(metalCost));
                        freighterTimeText.setText("Tiempo finalizacion: " + String.valueOf(timeCost));

                        if (hasResources(crystalCost, metalCost, 0) && act.player.getLevel() >= 5) {
                            buildTraders.setPaintFlags(0);
                            buildTraders.setEnabled(true);
                        } else {
                            buildTraders.setPaintFlags(buildTraders.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            buildTraders.setEnabled(false);
                        }
                    } else {
                        buildTraders.setPaintFlags(buildTraders.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        buildTraders.setEnabled(false);
                        freighterCostText.setText("Costo: ");
                        freighterCostText.setText("Tiempo finalizacion: ");
                    }
                }
            });
        }

        public void confirmConstruction(final Shield shield) {
            shield.setPlanet(act.planet);
            final Shield shieldToAdd = shield;
            SweetAlertDialog dialog = Helpers.getConfirmationDialog(act, "Confirmar", "Esta seguro que desea comenzar la construccion?", "Construir", "Cancelar");
            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    constructionService.BuildShield(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);
                    act.crystalUsed = shieldToAdd.getCost().getCrystalCost();
                    act.metalUsed = shieldToAdd.getCost().getMetalCost();
                    sweetAlertDialog.cancel();
                }
            });

            dialog.show();
        }

        public void confirmConstructionProbes(final int qtt) {
            SweetAlertDialog dialog = Helpers.getConfirmationDialog(act, "Confirmar", "Esta seguro que desea comenzar la construccion?", "Construir", "Cancelar");
            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    constructionService.BuildProbes(act.player.getUsername(), act.player.getToken(), act, fragment, act.planet.getId(), qtt);
                    act.crystalUsed = qtt * 3000;
                    act.metalUsed = qtt * 3000;
                    sweetAlertDialog.cancel();
                }
            });

            dialog.show();
        }

        public void confirmConstructionTraders(final int qtt) {
            SweetAlertDialog dialog = Helpers.getConfirmationDialog(act, "Confirmar", "Esta seguro que desea comenzar la construccion?", "Construir", "Cancelar");
            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    constructionService.BuildTraders(act.player.getUsername(), act.player.getToken(), act, fragment, act.planet.getId(), qtt);
                    act.crystalUsed = qtt * 500;
                    act.metalUsed = qtt * 500;
                    sweetAlertDialog.cancel();
                }
            });

            dialog.show();
        }

        public void setCurrentConstructionValues(long shieldTime, long probeTime, long traderTime){
            if(shieldTime > 0){
                InConstruction(buildShield, (TextView)getView().findViewById(R.id.shield_timer), shieldTime);
            }
            if(probeTime > 0){
                InConstruction(buildProbes, (TextView)getView().findViewById(R.id.probe_timer), probeTime);
            }
            if(shieldTime > 0){
                InConstruction(buildTraders, (TextView)getView().findViewById(R.id.freighter_timer), traderTime);
            }
        }

        public void InConstruction(Button buildButton, TextView timer, long buildingTime){
            buildButton.setPaintFlags(buildButton.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            buildButton.setEnabled(false);
            SetConstructionTimer(timer, buildingTime);
        }

        public void setProbesValues(ArrayList<Probe> probes){
            TextView probeQtt = (TextView) getView().findViewById(R.id.probeQttText);
            probeQtt.setText("Cantidad Actual: " + String.valueOf(probes.size()));
            TextView probeRequiredLevelText = (TextView) fragment.getView().findViewById(R.id.probeRequiredLevelText);
            probeRequiredLevelText.setText("Nivel Requerido: 5");
        }

        public void setTradersValues(ArrayList<Trader> traders){
            TextView traderQtt = (TextView) getView().findViewById(R.id.freighterQttText);
            traderQtt.setText("Cantidad Actual: " + String.valueOf(traders.size()));
            TextView traderRequiredLevelText = (TextView) fragment.getView().findViewById(R.id.freighterRequiredLevelText);
            traderRequiredLevelText.setText("Nivel Requerido: 5");
        }

        public void setShieldValues(Shield shield){
            this.shield = shield;
            TextView cost = (TextView) getView().findViewById(R.id.shieldCostText);
            cost.setText("Costo: Cristal " + String.valueOf(shield.getCost().getCrystalCost()) + " Metal " +
                    String.valueOf(shield.getCost().getMetalCost()) + " Materia oscura: " + String.valueOf(shield.getCost().getDarkMatterCost()));
            TextView timeCost = (TextView) getView().findViewById(R.id.shieldTimeText);
            timeCost.setText("Tiempo finalizacion: " + String.valueOf(shield.getConstructionTime()));
            TextView requiredLevel = (TextView) getView().findViewById(R.id.shieldRequiredLevel);
            requiredLevel.setText("Nivel requerido: " + String.valueOf(shield.getRequiredLevel()));
            buildShield.setText("Construir");

            checkAvailability(shield);

            if(shield.getPlanet().getId() == act.planet.getId()){
                buildShield.setPaintFlags(buildShield.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                buildShield.setEnabled(false);
            }

            if(act.player.getLevel() < shield.getRequiredLevel()){
                buildShield.setPaintFlags(buildShield.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                buildShield.setEnabled(false);
            }
        }

        public void checkAvailability(Shield shield){
            if(!hasResources(shield.getCost().getCrystalCost(), shield.getCost().getMetalCost() ,shield.getCost().getDarkMatterCost())){
                buildShield.setPaintFlags(buildShield.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                buildShield.setEnabled(false);
            } else {
                buildShield.setPaintFlags(0);
                buildShield.setEnabled(true);
            }
        }

        public boolean hasResources(int crystal, int metal, int darkMatter){
            if(crystal <= act.planet.getCrystal() && metal <= act.planet.getMetal()
                    && darkMatter <= act.planet.getDarkMatter()){
                return true;
            }
            return false;
        }

        public void ShieldBuilt(Shield shield){
            SetConstructionTimer(timerView, shield.getEnableDate().getTime());
            act.player.getPlanet(act.planet.getId()).setCrystal(act.planet.getCrystal() - act.crystalUsed);
            act.player.getPlanet(act.planet.getId()).setMetal(act.planet.getMetal() - act.metalUsed);
            SweetAlertDialog dialog = Helpers.getSuccesDialog(act, "Construccion", "La construccion del escudo ha comenzado!");
            dialog.show();
        }

        public void ProbeBuilt(Probe probe){
            SetConstructionTimer(timerView, probe.getEnableDate().getTime());
            act.player.getPlanet(act.planet.getId()).setCrystal(act.planet.getCrystal() - act.crystalUsed);
            act.player.getPlanet(act.planet.getId()).setMetal(act.planet.getMetal() - act.metalUsed);
            constructionService.GetCurrentProbes(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);
            SweetAlertDialog dialog = Helpers.getSuccesDialog(act, "Construccion", "La construccion de las sondas ha comenzado!");
            dialog.show();
        }

        public void TraderBuilt(Trader trader){
            SetConstructionTimer(timerView, trader.getEnableDate().getTime());
            act.player.getPlanet(act.planet.getId()).setCrystal(act.planet.getCrystal() - act.crystalUsed);
            act.player.getPlanet(act.planet.getId()).setMetal(act.planet.getMetal() - act.metalUsed);
            constructionService.GetCurrentTraders(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);
            SweetAlertDialog dialog = Helpers.getSuccesDialog(act, "Construccion", "La construccion de los cargueros ha comenzado!");
            dialog.show();
        }

        private void SetConstructionTimer(final TextView timer, long fechaFinalizacion) {
            long tiempoRestante = fechaFinalizacion - System.currentTimeMillis();

            new CountDownTimer(tiempoRestante, 1000) {
                public void onTick(long millisUntilFinished) {
                    timer.setVisibility(View.VISIBLE);
                    timer.setText("Tiempo para finalización: " +
                            String.valueOf(TimeUnit.MILLISECONDS.toHours(millisUntilFinished)) + ":" +
                            String.valueOf(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millisUntilFinished))) + ":" +
                            String.valueOf(TimeUnit.MILLISECONDS.toSeconds(millisUntilFinished) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millisUntilFinished)))
                    );
                    timer.setTextColor(Color.GREEN);
                    timer.setTypeface(null, Typeface.BOLD);
                }

                public void onFinish() {
                    timer.setVisibility(View.INVISIBLE);
                    constructionService.GetCurrentShield(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);
                    constructionService.GetCurrentProbes(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);
                }

            }.start();
        }
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch(position) {

                case 0: return HangarFragment.newInstance();
                case 1: return ShipsFragment.newInstance();
                default: return OthersFragment.newInstance();
            }
        }

        @Override
        public int getCount() {
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Hangar";
                case 1:
                    return "Naves";
                case 2:
                    return "Otros";
            }
            return null;
        }
    }
}
