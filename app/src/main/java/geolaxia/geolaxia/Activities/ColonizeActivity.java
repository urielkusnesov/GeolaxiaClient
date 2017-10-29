package geolaxia.geolaxia.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.Player;
import geolaxia.geolaxia.R;
import geolaxia.geolaxia.Services.Implementation.DefenseService;
import geolaxia.geolaxia.Services.Implementation.PlanetService;
import geolaxia.geolaxia.Services.Interface.IDefenseService;
import geolaxia.geolaxia.Services.Interface.IPlanetService;

public class ColonizeActivity extends MenuActivity {
    final Activity context = this;

    private IDefenseService defenseService;
    private IPlanetService planetService;

    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.Constructor();
        //this.ConstructorServicios();
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
        this.defenseService = new DefenseService();
        this.planetService = new PlanetService();
    }

    // -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

    public static class ColonizeFragment extends Fragment {
        private ColonizeActivity.ColonizeFragment context;
        private ColonizeActivity act;

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
            final View rootView = inflater.inflate(R.layout.fragment_colonize, container, false);
            this.context = this;
            this.act = (ColonizeActivity) getActivity();

            return (rootView);
        }
    }

    public static class CoordinatesFragment extends Fragment {
        private CoordinatesFragment context;
        private ColonizeActivity act;

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
            final View rootView = inflater.inflate(R.layout.fragment_colonize_coordinates, container, false);
            this.context = this;
            this.act = (ColonizeActivity) getActivity();

            return rootView;
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
