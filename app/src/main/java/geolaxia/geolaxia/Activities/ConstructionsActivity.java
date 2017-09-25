package geolaxia.geolaxia.Activities;

import android.content.Intent;
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

import java.util.ArrayList;

import geolaxia.geolaxia.Model.Cost;
import geolaxia.geolaxia.Model.CrystalMine;
import geolaxia.geolaxia.Model.DarkMatterMine;
import geolaxia.geolaxia.Model.MetalMine;
import geolaxia.geolaxia.Model.Mine;
import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.Player;
import geolaxia.geolaxia.R;
import geolaxia.geolaxia.Services.Implementation.ConstructionService;
import geolaxia.geolaxia.Services.Interface.IConstructionService;

public class ConstructionsActivity extends MenuActivity {
    private Player player;
    private Planet planet;

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
            View rootView = inflater.inflate(R.layout.fragment_constructions_mines, container, false);

            fragment = this;
            act = (ConstructionsActivity) getActivity();
            constructionService = new ConstructionService();
            constructionService.GetMinesToBuild(act.player.getUsername(), act.player.getToken(), act.planet.getId(), act, fragment);

            buildCrystal = (Button) getView().findViewById(R.id.cristalBuildButton);
            buildMetal = (Button) getView().findViewById(R.id.metalBuildButton);
            buildDarkMatter = (Button) getView().findViewById(R.id.darkMatterBuildButton);

            return rootView;
        }

        public void setCosts(CrystalMine crystalMine, MetalMine metalMine, DarkMatterMine darkMatterMine){

            TextView crystalCost = (TextView) getView().findViewById(R.id.cristalCostText);
            crystalCost.setText("Costo: Cristal " + String.valueOf(crystalMine.getCost().getCrystalCost()) + " Metal " +
                    String.valueOf(crystalMine.getCost().getMetalCost()) + " Materia oscura: " + String.valueOf(crystalMine.getCost().getDarkMatterCost()));
            TextView crystalEnergyCost = (TextView) getView().findViewById(R.id.cristalEnergyCostText);
            crystalEnergyCost.setText("Energia necesaria: " + String.valueOf(crystalMine.getEnergyConsumption()));
            TextView crystalTimeCost = (TextView) getView().findViewById(R.id.cristalTimeText);
            crystalTimeCost.setText("Tiempo finalizacion: " + String.valueOf(crystalMine.getConstructionTime()));
            if(crystalMine.getCost().getCrystalCost() >= act.planet.getCrystal() && crystalMine.getCost().getMetalCost() >= act.planet.getMetal()
                && crystalMine.getCost().getDarkMatterCost() >= act.planet.getDarkMatter() && crystalMine.getEnergyConsumption() >= act.planet.getEnergy()){

                buildCrystal.setEnabled(true);
            }

            TextView metalCost = (TextView) getView().findViewById(R.id.metalCostText);
            metalCost.setText("Costo: Cristal " + String.valueOf(metalMine.getCost().getCrystalCost()) + " Metal " +
                    String.valueOf(metalMine.getCost().getMetalCost()) + " Materia oscura: " + String.valueOf(metalMine.getCost().getDarkMatterCost()));
            TextView metalEnergyCost = (TextView) getView().findViewById(R.id.metalEnergyCostText);
            metalEnergyCost.setText("Energia necesaria: " + String.valueOf(metalMine.getEnergyConsumption()));
            TextView metalTimeCost = (TextView) getView().findViewById(R.id.metalTimeText);
            metalTimeCost.setText("Tiempo finalizacion: " + String.valueOf(metalMine.getConstructionTime()));
            if(metalMine.getCost().getCrystalCost() >= act.planet.getCrystal() && metalMine.getCost().getMetalCost() >= act.planet.getMetal()
                    && metalMine.getCost().getDarkMatterCost() >= act.planet.getDarkMatter() && metalMine.getEnergyConsumption() >= act.planet.getEnergy()){

                buildMetal.setEnabled(true);
            }

            TextView darkMatterCost = (TextView) getView().findViewById(R.id.darkMatterCostText);
            darkMatterCost.setText("Costo: Cristal " + String.valueOf(darkMatterMine.getCost().getCrystalCost()) + " Metal " +
                    String.valueOf(darkMatterMine.getCost().getMetalCost()) + " Materia oscura: " + String.valueOf(darkMatterMine.getCost().getDarkMatterCost()));
            TextView darkMatterEnergyCost = (TextView) getView().findViewById(R.id.darkMatterEnergyCostText);
            darkMatterEnergyCost.setText("Energia necesaria: " + String.valueOf(darkMatterMine.getEnergyConsumption()));
            TextView darkMatterTimeCost = (TextView) getView().findViewById(R.id.darkMatterTimeText);
            darkMatterTimeCost.setText("Tiempo finalizacion: " + String.valueOf(darkMatterMine.getConstructionTime()));
            if(darkMatterMine.getCost().getCrystalCost() >= act.planet.getCrystal() && darkMatterMine.getCost().getMetalCost() >= act.planet.getMetal()
                    && darkMatterMine.getCost().getDarkMatterCost() >= act.planet.getDarkMatter() && darkMatterMine.getEnergyConsumption() >= act.planet.getEnergy()){

                buildDarkMatter.setEnabled(true);
            }
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
