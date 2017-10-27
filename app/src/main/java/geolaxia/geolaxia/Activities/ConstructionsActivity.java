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
import geolaxia.geolaxia.Model.Helpers;
import geolaxia.geolaxia.Model.MetalMine;
import geolaxia.geolaxia.Model.Mine;
import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.Player;
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
            SweetAlertDialog dialog = Helpers.getConfirmationDialog(act, "Confirmar", "Esta seguro que desea comenzar la construccion?", "Construir", "Cancelar");
            dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    constructionService.Build(act.player.getUsername(), act.player.getToken(), mineToAdd, act, fragment);
                    act.crystalUsed = mineToAdd.getCost().getCrystalCost();
                    act.metalUsed = mineToAdd.getCost().getMetalCost();
                    sweetAlertDialog.cancel();
                }
            });

            dialog.show();
        }
        public void setCurrentValues(CrystalMine crystalMine, MetalMine metalMine, DarkMatterMine darkMatterMine){
            Date currentDate = Calendar.getInstance().getTime();
            if(currentDate.compareTo(crystalMine.getEnableDate()) == -1){
                buildCrystal.setPaintFlags(buildCrystal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                buildCrystal.setEnabled(false);
                SetConstructionTimer((TextView)getView().findViewById(R.id.crystal_timer), crystalMine.getEnableDate().getTime());
            }
            if(currentDate.compareTo(metalMine.getEnableDate()) == -1){
                buildMetal.setPaintFlags(buildMetal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                buildMetal.setEnabled(false);
                SetConstructionTimer((TextView)getView().findViewById(R.id.metal_timer), metalMine.getEnableDate().getTime());
            }
            if(currentDate.compareTo(darkMatterMine.getEnableDate()) == -1){
                buildDarkMatter.setPaintFlags(buildDarkMatter.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                buildDarkMatter.setEnabled(false);
                SetConstructionTimer((TextView)getView().findViewById(R.id.darkMatter_timer), darkMatterMine.getEnableDate().getTime());
            }

            TextView crystalCurrentLevel = (TextView) getView().findViewById(R.id.cristalLevelText);
            crystalCurrentLevel.setText("Nivel Actual: " + String.valueOf(crystalMine.getLevel()));
            TextView crystalProductivity = (TextView) getView().findViewById(R.id.cristalProductionText);
            crystalProductivity.setText("Produccion actual: " + String.valueOf(crystalMine.getProductivity()));
            TextView crystalEnergyConsumption = (TextView) getView().findViewById(R.id.cristalConsumptionText);
            crystalEnergyConsumption.setText("Energia consumida actual: " + String.valueOf(crystalMine.getEnergyConsumption()));

            TextView metalCurrentLevel = (TextView) getView().findViewById(R.id.metalLevelText);
            metalCurrentLevel.setText("Nivel Actual: " + String.valueOf(metalMine.getLevel()));
            TextView metalProductivity = (TextView) getView().findViewById(R.id.metalProductionText);
            metalProductivity.setText("Produccion actual: " + String.valueOf(metalMine.getProductivity()));
            TextView metalEnergyConsumption = (TextView) getView().findViewById(R.id.metalConsumptionText);
            metalEnergyConsumption.setText("Energia consumida actual: " + String.valueOf(metalMine.getEnergyConsumption()));

            TextView darkMatterCurrentLevel = (TextView) getView().findViewById(R.id.darkMatterLevelText);
            darkMatterCurrentLevel.setText("Nivel Actual: " + String.valueOf(darkMatterMine.getLevel()));
            TextView darkMatterProductivity = (TextView) getView().findViewById(R.id.darkMatterProductionText);
            darkMatterProductivity.setText("Produccion actual: " + String.valueOf(darkMatterMine.getProductivity()));
            TextView darkMatterEnergyConsumption = (TextView) getView().findViewById(R.id.darkMatterConsumptionText);
            darkMatterEnergyConsumption.setText("Energia consumida actual: " + String.valueOf(darkMatterMine.getEnergyConsumption()));
        }

        public void setCosts(CrystalMine crystalMine, MetalMine metalMine, DarkMatterMine darkMatterMine){
            nextCrystal = crystalMine;
            TextView crystalCost = (TextView) getView().findViewById(R.id.cristalCostText);
            crystalCost.setText("Costo: Cristal " + String.valueOf(crystalMine.getCost().getCrystalCost()) + " Metal " +
                    String.valueOf(crystalMine.getCost().getMetalCost()) + " Materia oscura: " + String.valueOf(crystalMine.getCost().getDarkMatterCost()));
            TextView crystalEnergyCost = (TextView) getView().findViewById(R.id.cristalEnergyCostText);
            crystalEnergyCost.setText("Energia necesaria: " + String.valueOf(crystalMine.getEnergyConsumption()));
            TextView crystalTimeCost = (TextView) getView().findViewById(R.id.cristalTimeText);
            crystalTimeCost.setText("Tiempo finalizacion: " + String.valueOf(crystalMine.getConstructionTime()));
            TextView crystalNewProductivity = (TextView) getView().findViewById(R.id.cristalNewProductionText);
            crystalNewProductivity.setText("Produccion por hora: " + String.valueOf(crystalMine.getProductivity()));
            buildCrystal.setText("Construir Nivel " + String.valueOf(crystalMine.getLevel()));

            nextMetal = metalMine;
            TextView metalCost = (TextView) getView().findViewById(R.id.metalCostText);
            metalCost.setText("Costo: Cristal " + String.valueOf(metalMine.getCost().getCrystalCost()) + " Metal " +
                    String.valueOf(metalMine.getCost().getMetalCost()) + " Materia oscura: " + String.valueOf(metalMine.getCost().getDarkMatterCost()));
            TextView metalEnergyCost = (TextView) getView().findViewById(R.id.metalEnergyCostText);
            metalEnergyCost.setText("Energia necesaria: " + String.valueOf(metalMine.getEnergyConsumption()));
            TextView metalTimeCost = (TextView) getView().findViewById(R.id.metalTimeText);
            metalTimeCost.setText("Tiempo finalizacion: " + String.valueOf(metalMine.getConstructionTime()));
            TextView metalNewProductivity = (TextView) getView().findViewById(R.id.metalNewProductionText);
            metalNewProductivity.setText("Produccion por hora: " + String.valueOf(metalMine.getProductivity()));
            buildMetal.setText("Construir Nivel " + String.valueOf(metalMine.getLevel()));

            nextDarkMatter = darkMatterMine;
            TextView darkMatterCost = (TextView) getView().findViewById(R.id.darkMatterCostText);
            darkMatterCost.setText("Costo: Cristal " + String.valueOf(darkMatterMine.getCost().getCrystalCost()) + " Metal " +
                    String.valueOf(darkMatterMine.getCost().getMetalCost()) + " Materia oscura: " + String.valueOf(darkMatterMine.getCost().getDarkMatterCost()));
            TextView darkMatterEnergyCost = (TextView) getView().findViewById(R.id.darkMatterEnergyCostText);
            darkMatterEnergyCost.setText("Energia necesaria: " + String.valueOf(darkMatterMine.getEnergyConsumption()));
            TextView darkMatterTimeCost = (TextView) getView().findViewById(R.id.darkMatterTimeText);
            darkMatterTimeCost.setText("Tiempo finalizacion: " + String.valueOf(darkMatterMine.getConstructionTime()));
            TextView darkMatterNewProductivity = (TextView) getView().findViewById(R.id.darkMatterNewProductionText);
            darkMatterNewProductivity.setText("Produccion por hora: " + String.valueOf(darkMatterMine.getProductivity()));
            buildDarkMatter.setText("Construir Nivel " + String.valueOf(darkMatterMine.getLevel()));

            checkAvailability(crystalMine, metalMine, darkMatterMine);
        }

        public void checkAvailability(CrystalMine crystalMine, MetalMine metalMine, DarkMatterMine darkMatterMine){
            if(!hasResources(crystalMine.getCost().getCrystalCost(), crystalMine.getCost().getMetalCost() ,crystalMine.getCost().getDarkMatterCost(), crystalMine.getEnergyConsumption())){
                buildCrystal.setPaintFlags(buildCrystal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                buildCrystal.setEnabled(false);
            } else {
                buildCrystal.setPaintFlags(0);
                //buildCrystal.setPaintFlags(buildCrystal.getPaintFlags() | (~ Paint.STRIKE_THRU_TEXT_FLAG));
                buildCrystal.setEnabled(true);
            }

            if(!hasResources(metalMine.getCost().getCrystalCost(), metalMine.getCost().getMetalCost() ,metalMine.getCost().getDarkMatterCost(), metalMine.getEnergyConsumption())){
                buildMetal.setPaintFlags(buildMetal.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                buildMetal.setEnabled(false);
            } else{
                //buildMetal.setPaintFlags(buildMetal.getPaintFlags() | (~ Paint.STRIKE_THRU_TEXT_FLAG));
                buildMetal.setPaintFlags(0);
                buildMetal.setEnabled(true);
            }

            if(!hasResources(darkMatterMine.getCost().getCrystalCost(), darkMatterMine.getCost().getMetalCost() ,darkMatterMine.getCost().getDarkMatterCost(), darkMatterMine.getEnergyConsumption())){
                buildDarkMatter.setPaintFlags(buildDarkMatter.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
                buildDarkMatter.setEnabled(false);
            } else{
                //buildDarkMatter.setPaintFlags(buildDarkMatter.getPaintFlags() | (~ Paint.STRIKE_THRU_TEXT_FLAG));
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
            SetConstructionTimer(timerView, mine.getEnableDate().getTime());
            act.player.getPlanet(act.planet.getId()).setCrystal(act.planet.getCrystal() - act.crystalUsed);
            act.player.getPlanet(act.planet.getId()).setMetal(act.planet.getMetal() - act.metalUsed);
            constructionService.GetCurrentMines(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);
            constructionService.GetMinesToBuild(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);
            SweetAlertDialog dialog = Helpers.getSuccesDialog(act, "Construccion", "La construccion de la mina ha comenzado!");
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
                    constructionService.GetCurrentMines(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);
                    constructionService.GetMinesToBuild(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);
                }

            }.start();
        }
    }

    public static class EnergyFragment extends Fragment {

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
            View rootView = inflater.inflate(R.layout.fragment_constructions_mines, container, false);

            return rootView;
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
                    return "Energia";
            }
            return null;
        }
    }
}
