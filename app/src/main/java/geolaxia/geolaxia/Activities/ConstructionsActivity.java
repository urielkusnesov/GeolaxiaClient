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
import geolaxia.geolaxia.Model.Cost;
import geolaxia.geolaxia.Model.CrystalMine;
import geolaxia.geolaxia.Model.DarkMatterMine;
import geolaxia.geolaxia.Model.EnergyCentral;
import geolaxia.geolaxia.Model.EnergyFacility;
import geolaxia.geolaxia.Model.EnergyFuelCentral;
import geolaxia.geolaxia.Model.Facility;
import geolaxia.geolaxia.Model.Helpers;
import geolaxia.geolaxia.Model.MetalMine;
import geolaxia.geolaxia.Model.Mine;
import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.Player;
import geolaxia.geolaxia.Model.SolarPanel;
import geolaxia.geolaxia.Model.WindTurbine;
import geolaxia.geolaxia.R;
import geolaxia.geolaxia.Services.Implementation.ConstructionService;
import geolaxia.geolaxia.Services.Interface.IConstructionService;

public class ConstructionsActivity extends MenuActivity {

    private int crystalUsed;
    private int metalUsed;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_constructions);

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

    public static class MinesFragment extends Fragment {
        private ConstructionsActivity act;
        private IConstructionService constructionService;
        private MinesFragment fragment;

        Button buildCrystal;
        Button buildMetal;
        Button buildDarkMatter;
        TextView timerView;

        CrystalMine nextCrystal;
        MetalMine nextMetal;
        DarkMatterMine nextDarkMatter;

        public MinesFragment() {
        }

        public static MinesFragment newInstance() {
            MinesFragment fragment = new MinesFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_constructions_mines, container, false);

            fragment = this;
            act = (ConstructionsActivity) getActivity();
            constructionService = new ConstructionService();
            constructionService.GetCurrentMines(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);
            constructionService.GetMinesToBuild(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);
            constructionService.GetBuildingTime(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);

            buildCrystal = (Button) rootView.findViewById(R.id.cristalBuildButton);
            buildCrystal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timerView = (TextView) rootView.findViewById(R.id.crystal_timer);
                    confirmConstruction(nextCrystal);
                }
            });

            buildMetal = (Button) rootView.findViewById(R.id.metalBuildButton);
            buildMetal.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timerView = (TextView) rootView.findViewById(R.id.metal_timer);
                    confirmConstruction(nextMetal);
                }
            });

            buildDarkMatter = (Button) rootView.findViewById(R.id.darkMatterBuildButton);
            buildDarkMatter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timerView = (TextView) rootView.findViewById(R.id.darkMatter_timer);
                    confirmConstruction(nextDarkMatter);
                }
            });

            return rootView;
        }

        public void confirmConstruction(Mine mine){
            mine.setPlanet(act.planet);
            final Mine mineToAdd = mine;
            SweetAlertDialog dialog = Helpers.getConfirmationDialog(act, "Confirmar", "¿Está seguro que desea comenzar la construcción?", "Construir", "Cancelar");
            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    constructionService.BuildMine(act.player.getUsername(), act.player.getToken(), mineToAdd, act, fragment);
                    act.crystalUsed = mineToAdd.getCost().getCrystalCost();
                    act.metalUsed = mineToAdd.getCost().getMetalCost();
                    SetConstructionTimer(timerView, mineToAdd.getConstructionTime());
                    sweetAlertDialog.cancel();
                }
            });

            dialog.show();
        }

        public void InConstruction(Button buildButton, TextView timer, long buildingTime){
            buildButton.setPaintFlags(buildButton.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            buildButton.setEnabled(false);
            SetConstructionTimer(timer, buildingTime);
        }

        public void setCurrentConstructionTimers(long crystalTime, long metalTime, long darkMatterTime){
            if(crystalTime > 0){
                InConstruction(buildCrystal, (TextView)getView().findViewById(R.id.crystal_timer), crystalTime);
            }
            if(metalTime > 0){
                InConstruction(buildMetal, (TextView)getView().findViewById(R.id.metal_timer), metalTime);
            }
            if(darkMatterTime > 0){
                InConstruction(buildDarkMatter, (TextView)getView().findViewById(R.id.darkMatter_timer), darkMatterTime);
            }
        }

        public void setCurrentValues(CrystalMine crystalMine, MetalMine metalMine, DarkMatterMine darkMatterMine){
            TextView crystalCurrentLevel = (TextView) getView().findViewById(R.id.cristalLevelText);
            crystalCurrentLevel.setText("Nivel Actual: " + String.valueOf(crystalMine.getLevel()));
            TextView crystalProductivity = (TextView) getView().findViewById(R.id.cristalProductionText);
            crystalProductivity.setText("Producción actual: " + String.valueOf(crystalMine.getProductivity()));
            TextView crystalEnergyConsumption = (TextView) getView().findViewById(R.id.cristalConsumptionText);
            crystalEnergyConsumption.setText("Energía consumida actual: " + String.valueOf(crystalMine.getEnergyConsumption()));

            TextView metalCurrentLevel = (TextView) getView().findViewById(R.id.metalLevelText);
            metalCurrentLevel.setText("Nivel Actual: " + String.valueOf(metalMine.getLevel()));
            TextView metalProductivity = (TextView) getView().findViewById(R.id.metalProductionText);
            metalProductivity.setText("Producción actual: " + String.valueOf(metalMine.getProductivity()));
            TextView metalEnergyConsumption = (TextView) getView().findViewById(R.id.metalConsumptionText);
            metalEnergyConsumption.setText("Energía consumida actual: " + String.valueOf(metalMine.getEnergyConsumption()));

            TextView darkMatterCurrentLevel = (TextView) getView().findViewById(R.id.darkMatterLevelText);
            darkMatterCurrentLevel.setText("Nivel Actual: " + String.valueOf(darkMatterMine.getLevel()));
            TextView darkMatterProductivity = (TextView) getView().findViewById(R.id.darkMatterProductionText);
            darkMatterProductivity.setText("Producción actual: " + String.valueOf(darkMatterMine.getProductivity()));
            TextView darkMatterEnergyConsumption = (TextView) getView().findViewById(R.id.darkMatterConsumptionText);
            darkMatterEnergyConsumption.setText("Energía consumida actual: " + String.valueOf(darkMatterMine.getEnergyConsumption()));
        }

        public void setCosts(CrystalMine crystalMine, MetalMine metalMine, DarkMatterMine darkMatterMine){
            nextCrystal = crystalMine;
            TextView crystalCost = (TextView) getView().findViewById(R.id.cristalCostText);
            crystalCost.setText("Costo: Cristal: " + String.valueOf(crystalMine.getCost().getCrystalCost()) + ", Metal: " +
                    String.valueOf(crystalMine.getCost().getMetalCost()) + ", M.Oscura: " + String.valueOf(crystalMine.getCost().getDarkMatterCost()));
            TextView crystalEnergyCost = (TextView) getView().findViewById(R.id.cristalEnergyCostText);
            crystalEnergyCost.setText("Energía necesaria: " + String.valueOf(crystalMine.getEnergyConsumption()));
            TextView crystalTimeCost = (TextView) getView().findViewById(R.id.cristalTimeText);
            crystalTimeCost.setText("Tiempo finalización: " + String.valueOf(crystalMine.getConstructionTime()));
            TextView crystalNewProductivity = (TextView) getView().findViewById(R.id.cristalNewProductionText);
            crystalNewProductivity.setText("Producción por hora: " + String.valueOf(crystalMine.getProductivity()));
            buildCrystal.setText("Construir Nivel: " + String.valueOf(crystalMine.getLevel()));

            nextMetal = metalMine;
            TextView metalCost = (TextView) getView().findViewById(R.id.metalCostText);
            metalCost.setText("Costo: Cristal: " + String.valueOf(metalMine.getCost().getCrystalCost()) + ", Metal: " +
                    String.valueOf(metalMine.getCost().getMetalCost()) + ", M.Oscura: " + String.valueOf(metalMine.getCost().getDarkMatterCost()));
            TextView metalEnergyCost = (TextView) getView().findViewById(R.id.metalEnergyCostText);
            metalEnergyCost.setText("Energía necesaria: " + String.valueOf(metalMine.getEnergyConsumption()));
            TextView metalTimeCost = (TextView) getView().findViewById(R.id.metalTimeText);
            metalTimeCost.setText("Tiempo finalizacion: " + String.valueOf(metalMine.getConstructionTime()));
            TextView metalNewProductivity = (TextView) getView().findViewById(R.id.metalNewProductionText);
            metalNewProductivity.setText("Producción por hora: " + String.valueOf(metalMine.getProductivity()));
            buildMetal.setText("Construir Nivel: " + String.valueOf(metalMine.getLevel()));

            nextDarkMatter = darkMatterMine;
            TextView darkMatterCost = (TextView) getView().findViewById(R.id.darkMatterCostText);
            darkMatterCost.setText("Costo: Cristal: " + String.valueOf(darkMatterMine.getCost().getCrystalCost()) + ", Metal: " +
                    String.valueOf(darkMatterMine.getCost().getMetalCost()) + ", M.Oscura: " + String.valueOf(darkMatterMine.getCost().getDarkMatterCost()));
            TextView darkMatterEnergyCost = (TextView) getView().findViewById(R.id.darkMatterEnergyCostText);
            darkMatterEnergyCost.setText("Energía necesaria: " + String.valueOf(darkMatterMine.getEnergyConsumption()));
            TextView darkMatterTimeCost = (TextView) getView().findViewById(R.id.darkMatterTimeText);
            darkMatterTimeCost.setText("Tiempo finalización: " + String.valueOf(darkMatterMine.getConstructionTime()));
            TextView darkMatterNewProductivity = (TextView) getView().findViewById(R.id.darkMatterNewProductionText);
            darkMatterNewProductivity.setText("Producción por hora: " + String.valueOf(darkMatterMine.getProductivity()));
            buildDarkMatter.setText("Construir Nivel: " + String.valueOf(darkMatterMine.getLevel()));

            checkAvailability(crystalMine, metalMine, darkMatterMine);
        }

        public void checkAvailability(CrystalMine crystalMine, MetalMine metalMine, DarkMatterMine darkMatterMine){
            if(!hasResources(crystalMine.getCost().getCrystalCost(), crystalMine.getCost().getMetalCost() ,crystalMine.getCost().getDarkMatterCost(), crystalMine.getEnergyConsumption())){
                buildCrystal.setPaintFlags(buildCrystal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                buildCrystal.setEnabled(false);
            } else {
                buildCrystal.setPaintFlags(0);
                buildCrystal.setEnabled(true);
            }

            if(!hasResources(metalMine.getCost().getCrystalCost(), metalMine.getCost().getMetalCost() ,metalMine.getCost().getDarkMatterCost(), metalMine.getEnergyConsumption())){
                buildMetal.setPaintFlags(buildMetal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                buildMetal.setEnabled(false);
            } else{
                buildMetal.setPaintFlags(0);
                buildMetal.setEnabled(true);
            }

            if(!hasResources(darkMatterMine.getCost().getCrystalCost(), darkMatterMine.getCost().getMetalCost() ,darkMatterMine.getCost().getDarkMatterCost(), darkMatterMine.getEnergyConsumption())){
                buildDarkMatter.setPaintFlags(buildDarkMatter.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                buildDarkMatter.setEnabled(false);
            } else{
                buildDarkMatter.setPaintFlags(0);
                buildDarkMatter.setEnabled(true);
            }
        }

        public boolean hasResources(int crystal, int metal, int darkMatter, int energy){
            if(crystal <= act.planet.getCrystal() && metal <= act.planet.getMetal()
                    && darkMatter <= act.planet.getDarkMatter() && energy <= act.planet.getEnergy()){
                return true;
            }
            return false;
        }

        public void MineBuilt(Mine mine){
            act.player.getPlanet(act.planet.getId()).setCrystal(act.planet.getCrystal() - act.crystalUsed);
            act.player.getPlanet(act.planet.getId()).setMetal(act.planet.getMetal() - act.metalUsed);
            SweetAlertDialog dialog = Helpers.getSuccesDialog(act, "Construcción", "La construcción de la mina ha comenzado!");
            dialog.show();
        }

        private void SetConstructionTimer(final TextView timer, long fechaFinalizacion) {
            long tiempoRestante = fechaFinalizacion - System.currentTimeMillis();

            new CountDownTimer(fechaFinalizacion, 1000) {
                public void onTick(long millisUntilFinished) {
                    timer.setVisibility(View.VISIBLE);
                    timer.setText("Tiempo para finalización: " + act.ObtenerHora(millisUntilFinished));
                    timer.setTextColor(Color.GREEN);
                    timer.setTypeface(null, Typeface.BOLD);
                }

                public void onFinish() {
                    timer.setVisibility(View.INVISIBLE);
                    SweetAlertDialog dialog = Helpers.getSuccesDialog(act, "Construcciones", "La construcción ha sido finalizada");
                    dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                        @Override
                        public void onClick(SweetAlertDialog sweetAlertDialog) {
                            constructionService.GetCurrentMines(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);
                            constructionService.GetMinesToBuild(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);
                            sweetAlertDialog.cancel();
                        }
                    });

                    dialog.show();
                }

            }.start();
        }
    }

    public static class EnergyFragment extends Fragment {
        private ConstructionsActivity act;
        private IConstructionService constructionService;
        private EnergyFragment fragment;

        Button buildEnergyCentral;
        Button buildEnergyFuelCentral;
        Button buildSolarPanel;
        Button buildWindTurbine;
        TextView timerView;

        EnergyCentral nextEnergyCentral;
        EnergyFuelCentral nextEnergyFuelCentral;
        int solarPanelsQttToBuild;
        int windTurbinesQttToBuild;

        public EnergyFragment() {
        }

        public static EnergyFragment newInstance() {
            EnergyFragment fragment = new EnergyFragment();
            Bundle args = new Bundle();
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_constructions_energy, container, false);

            fragment = this;
            act = (ConstructionsActivity) getActivity();
            constructionService = new ConstructionService();
            constructionService.GetCurrentEnergyFacilities(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);
            constructionService.GetEnergyFacilitiesToBuild(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);
            constructionService.GetEnergyFacilitiesBuildingTime(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);

            buildEnergyCentral = (Button) rootView.findViewById(R.id.energyCentralBuildButton);
            buildEnergyCentral.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timerView = (TextView) rootView.findViewById(R.id.energyCentral_timer);
                    confirmConstruction(nextEnergyCentral);
                }
            });

            buildEnergyFuelCentral = (Button) rootView.findViewById(R.id.energyFuelCentralBuildButton);
            buildEnergyFuelCentral.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timerView = (TextView) rootView.findViewById(R.id.energyFuelCentral_timer);
                    confirmConstruction(nextEnergyFuelCentral);
                }
            });

            buildSolarPanel = (Button) rootView.findViewById(R.id.solarPanelBuildButton);
            buildSolarPanel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timerView = (TextView) rootView.findViewById(R.id.solarPanel_timer);
                    solarPanelsQttToBuild = ((NumberPicker)rootView.findViewById(R.id.solarPanel)).getValue();
                    confirmConstructionSolarPanel(solarPanelsQttToBuild);
                }
            });

            buildWindTurbine = (Button) rootView.findViewById(R.id.windTurbineBuildButton);
            buildWindTurbine.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    timerView = (TextView) rootView.findViewById(R.id.windTurbine_timer);
                    windTurbinesQttToBuild = ((NumberPicker)rootView.findViewById(R.id.windTurbine)).getValue();
                    confirmConstructionWindTurbine(windTurbinesQttToBuild);
                }
            });

            LoadConstructionSolarPanels(rootView);
            LoadConstructionWindTurbines(rootView);

            return rootView;
        }

        private void LoadConstructionSolarPanels(View rootView) {
            NumberPicker np = (NumberPicker) rootView.findViewById(R.id.solarPanel);
            Helpers.setNumberPickerTextColor(np, Color.WHITE);
            np.setMinValue(0);
            np.setMaxValue(50);
            np.setWrapSelectorWheel(true);

            np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    TextView solarPanelCostText = (TextView) fragment.getView().findViewById(R.id.solarPanelCostText);
                    TextView solarPanelTimeText = (TextView) fragment.getView().findViewById(R.id.solarPanelTimeText);

                    if (newVal > 0) {
                        int crystalCost = newVal * 5;
                        int metalCost = newVal * 20;
                        int timeCost =  newVal * 2;

                        solarPanelCostText.setText("Costo: Cristal: " + String.valueOf(crystalCost) + ", Metal: " + String.valueOf(metalCost));
                        solarPanelTimeText.setText("Tiempo finalización: " + String.valueOf(timeCost));

                        if (hasResources(crystalCost, metalCost, 0)) {
                            buildSolarPanel.setPaintFlags(0);
                            buildSolarPanel.setEnabled(true);
                        } else {
                            buildSolarPanel.setPaintFlags(buildSolarPanel.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            buildSolarPanel.setEnabled(false);
                        }
                    } else {
                        buildSolarPanel.setPaintFlags(buildSolarPanel.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        buildSolarPanel.setEnabled(false);
                        solarPanelCostText.setText("Costo: ");
                        solarPanelTimeText.setText("Tiempo finalización: ");
                    }
                }
            });
        }

        private void LoadConstructionWindTurbines(View rootView) {
            NumberPicker np = (NumberPicker) rootView.findViewById(R.id.windTurbine);
            Helpers.setNumberPickerTextColor(np, Color.WHITE);
            np.setMinValue(0);
            np.setMaxValue(50);
            np.setWrapSelectorWheel(true);

            np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
                @Override
                public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                    TextView windTurbineCostText = (TextView) fragment.getView().findViewById(R.id.windTurbineCostText);
                    TextView windTurbineTimeText = (TextView) fragment.getView().findViewById(R.id.windTurbineTimeText);

                    if (newVal > 0) {
                        int crystalCost = newVal * 5;
                        int metalCost = newVal * 20;
                        int timeCost =  newVal * 2;

                        windTurbineCostText.setText("Costo: Cristal: " + String.valueOf(crystalCost) + ", Metal: " + String.valueOf(metalCost));
                        windTurbineTimeText.setText("Tiempo finalización: " + String.valueOf(timeCost));

                        if (hasResources(crystalCost, metalCost, 0)) {
                            buildWindTurbine.setPaintFlags(0);
                            buildWindTurbine.setEnabled(true);
                        } else {
                            buildWindTurbine.setPaintFlags(buildWindTurbine.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                            buildWindTurbine.setEnabled(false);
                        }
                    } else {
                        buildWindTurbine.setPaintFlags(buildWindTurbine.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                        buildWindTurbine.setEnabled(false);
                        windTurbineCostText.setText("Costo: ");
                        windTurbineTimeText.setText("Tiempo finalaización: ");
                    }
                }
            });
        }

        public void confirmConstruction(EnergyFacility energyFacility){
            energyFacility.setPlanet(act.planet);
            final EnergyFacility energyFacilityToAdd = energyFacility;
            SweetAlertDialog dialog = Helpers.getConfirmationDialog(act, "Confirmar", "Esta seguro que desea comenzar la construccion?", "Construir", "Cancelar");
            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    constructionService.BuildEnergyFacility(act.player.getUsername(), act.player.getToken(), energyFacilityToAdd, act, fragment);
                    act.crystalUsed = energyFacilityToAdd.getCost().getCrystalCost();
                    act.metalUsed = energyFacilityToAdd.getCost().getMetalCost();
                    sweetAlertDialog.cancel();
                }
            });

            dialog.show();
        }

        public void confirmConstructionSolarPanel(final int qtt){
            SweetAlertDialog dialog = Helpers.getConfirmationDialog(act, "Confirmar", "Esta seguro que desea comenzar la construccion?", "Construir", "Cancelar");
            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    constructionService.BuildSolarPanels(act.player.getUsername(), act.player.getToken(), act, fragment, act.planet.getId(), qtt);
                    act.crystalUsed = qtt * 5;
                    act.metalUsed = qtt * 20;
                    sweetAlertDialog.cancel();
                }
            });

            dialog.show();
        }

        public void confirmConstructionWindTurbine(final int qtt){
            SweetAlertDialog dialog = Helpers.getConfirmationDialog(act, "Confirmar", "Esta seguro que desea comenzar la construccion?", "Construir", "Cancelar");
            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    constructionService.BuildWindTurbines(act.player.getUsername(), act.player.getToken(), act, fragment, act.planet.getId(), qtt);
                    act.crystalUsed = qtt * 5;
                    act.metalUsed = qtt * 20;
                    sweetAlertDialog.cancel();
                }
            });

            dialog.show();
        }

        public void InConstruction(Button buildButton, TextView timer, long buildingTime){
            buildButton.setPaintFlags(buildButton.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            buildButton.setEnabled(false);
            SetConstructionTimer(timer, buildingTime);
        }

        public void setCurrentConstructionTimers(long energyCentralTime, long energyFuelCentralTime, long solarPanelTime, long windTurbineTime){
            if(energyCentralTime > 0){
                InConstruction(buildEnergyCentral, (TextView)getView().findViewById(R.id.energyCentral_timer), energyCentralTime);
            }
            if(energyFuelCentralTime > 0){
                InConstruction(buildEnergyFuelCentral, (TextView)getView().findViewById(R.id.energyFuelCentral_timer), energyFuelCentralTime);
            }
            if(solarPanelTime > 0){
                InConstruction(buildSolarPanel, (TextView)getView().findViewById(R.id.solarPanel_timer), solarPanelTime);
            }
            if(windTurbineTime > 0){
                InConstruction(buildWindTurbine, (TextView)getView().findViewById(R.id.windTurbine_timer), windTurbineTime);
            }
        }

        public void setCurrentValues(EnergyCentral energyCentral, EnergyFuelCentral energyFuelCentral, ArrayList<SolarPanel> solarPanels, ArrayList<WindTurbine> windTurbines){
            TextView energyCentralCurrentLevel = (TextView) getView().findViewById(R.id.energyCentralLevelText);
            energyCentralCurrentLevel.setText("Nivel Actual: " + String.valueOf(energyCentral.getLevel()));
            TextView energyCentralProductivity = (TextView) getView().findViewById(R.id.energyCentralProductionText);
            energyCentralProductivity.setText("Producción actual: " + String.valueOf(energyCentral.getProductivity()));
            TextView energyFuelCentralCurrentLevel = (TextView) getView().findViewById(R.id.energyFuelCentralLevelText);
            energyFuelCentralCurrentLevel.setText("Nivel Actual: " + String.valueOf(energyFuelCentral.getLevel()));
            TextView energyFuelCentralProductivity = (TextView) getView().findViewById(R.id.energyFuelCentralProductionText);
            energyFuelCentralProductivity.setText("Producción actual: " + String.valueOf(energyFuelCentral.getProductivity()));
            TextView energyFuelCentralDarkMatter = (TextView) getView().findViewById(R.id.energyFuelCentralDarkMatterText);
            energyFuelCentralDarkMatter.setText("M.Oscura consumida actual: " + String.valueOf(energyFuelCentral.getDarkMatterConsumption()));

            TextView solarPanelQtt = (TextView) getView().findViewById(R.id.solarPanelQttText);
            solarPanelQtt.setText("Cantidad Actual: " + String.valueOf(solarPanels.size()));

            TextView windTurbineQtt = (TextView) getView().findViewById(R.id.windTurbineQttText);
            windTurbineQtt.setText("Cantidad Actual: " + String.valueOf(windTurbines.size()));
        }

        public void setCosts(EnergyCentral energyCentral, EnergyFuelCentral energyFuelCentral){
            nextEnergyCentral = energyCentral;
            TextView energyCentralCost = (TextView) getView().findViewById(R.id.energyCentralCostText);
            energyCentralCost.setText("Costo: Cristal: " + String.valueOf(energyCentral.getCost().getCrystalCost()) + ", Metal: " + String.valueOf(energyCentral.getCost().getMetalCost()) + ", M.Oscura: " + String.valueOf(energyCentral.getCost().getDarkMatterCost()));
            TextView energyCentralTimeCost = (TextView) getView().findViewById(R.id.energyCentralTimeText);
            energyCentralTimeCost.setText("Tiempo finalización: " + String.valueOf(energyCentral.getConstructionTime()));
            TextView energyCentralNewProductivity = (TextView) getView().findViewById(R.id.energyCentralNewProductionText);
            energyCentralNewProductivity.setText("Producción por hora: " + String.valueOf(energyCentral.getProductivity()));
            buildEnergyCentral.setText("Construir Nivel " + String.valueOf(energyCentral.getLevel()));

            nextEnergyFuelCentral = energyFuelCentral;
            TextView energyFuelCentralCost = (TextView) getView().findViewById(R.id.energyFuelCentralCostText);
            energyFuelCentralCost.setText("Costo: Cristal: " + String.valueOf(energyFuelCentral.getCost().getCrystalCost()) + ", Metal: " + String.valueOf(energyFuelCentral.getCost().getMetalCost()) + ", M.Oscura: " + String.valueOf(energyFuelCentral.getCost().getDarkMatterCost()));
            TextView energyFuelCentralTimeCost = (TextView) getView().findViewById(R.id.energyFuelCentralTimeText);
            energyFuelCentralTimeCost.setText("Tiempo finalización: " + String.valueOf(energyFuelCentral.getConstructionTime()));
            TextView energyFuelCentralNewProductivity = (TextView) getView().findViewById(R.id.energyFuelCentralNewProductionText);
            energyFuelCentralNewProductivity.setText("Producción por hora: " + String.valueOf(energyFuelCentral.getProductivity()));
            TextView energyFuelCentralNewDarkMatter = (TextView) getView().findViewById(R.id.energyFuelCentralNextDarkMatterText);
            energyFuelCentralNewDarkMatter.setText("Consumo M.Oscura: " + String.valueOf(energyFuelCentral.getDarkMatterConsumption()));
            buildEnergyFuelCentral.setText("Construir Nivel: " + String.valueOf(energyFuelCentral.getLevel()));

            checkAvailability(energyCentral, energyFuelCentral);
        }

        public void checkAvailability(EnergyCentral energyCentral, EnergyFuelCentral energyFuelCentral){
            if(!hasResources(energyCentral.getCost().getCrystalCost(), energyCentral.getCost().getMetalCost() ,energyCentral.getCost().getDarkMatterCost())){
                buildEnergyCentral.setPaintFlags(buildEnergyCentral.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                buildEnergyCentral.setEnabled(false);
            } else {
                buildEnergyCentral.setPaintFlags(0);
                buildEnergyCentral.setEnabled(true);
            }

            if(!hasResources(energyFuelCentral.getCost().getCrystalCost(), energyFuelCentral.getCost().getMetalCost() ,energyFuelCentral.getCost().getDarkMatterCost())){
                buildEnergyFuelCentral.setPaintFlags(buildEnergyFuelCentral.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                buildEnergyFuelCentral.setEnabled(false);
            } else{
                buildEnergyFuelCentral.setPaintFlags(0);
                buildEnergyFuelCentral.setEnabled(true);
            }
        }

        public boolean hasResources(int crystal, int metal, int darkMatter){
            if(crystal <= act.planet.getCrystal() && metal <= act.planet.getMetal() && darkMatter <= act.planet.getDarkMatter()){
                return true;
            }
            return false;
        }

        public void EnergyFacilityBuilt(EnergyFacility energyFacility){
            SetConstructionTimer(timerView, energyFacility.getEnableDate().getTime());
            act.player.getPlanet(act.planet.getId()).setCrystal(act.planet.getCrystal() - act.crystalUsed);
            act.player.getPlanet(act.planet.getId()).setMetal(act.planet.getMetal() - act.metalUsed);
            constructionService.GetCurrentEnergyFacilities(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);
            constructionService.GetEnergyFacilitiesToBuild(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);
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
                    constructionService.GetCurrentEnergyFacilities(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);
                    constructionService.GetEnergyFacilitiesToBuild(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);
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

                case 0: return MinesFragment.newInstance();
                case 1: return EnergyFragment.newInstance();
                default: return MinesFragment.newInstance();
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
                    return "Minas";
                case 1:
                    return "Energía";
            }
            return null;
        }
    }
}
