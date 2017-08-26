package geolaxia.geolaxia.Activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.ArrayList;

import geolaxia.geolaxia.Model.Galaxy;
import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.ShipX;
import geolaxia.geolaxia.Model.ShipY;
import geolaxia.geolaxia.Model.ShipZ;
import geolaxia.geolaxia.Model.SolarSystem;

public abstract class BaseAttackActivity extends MenuActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    public abstract void FillGalaxies(ArrayList<Galaxy> galaxies);
    public abstract void FillSolarSystems(ArrayList<SolarSystem> solarSystems);
    public abstract void FillPlanets(ArrayList<Planet> planets);
    public abstract void FillFleets(ArrayList<ShipX> shipsX, ArrayList<ShipY> shipsY, ArrayList<ShipZ> shipsZ);
    public abstract void AttackSaved();
}
