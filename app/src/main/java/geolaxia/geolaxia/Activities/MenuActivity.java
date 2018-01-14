package geolaxia.geolaxia.Activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import geolaxia.geolaxia.Model.Helpers;
import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.Player;
import geolaxia.geolaxia.R;

public class MenuActivity extends BaseActivity {

    private Activity context = this;
    protected Player player;
    protected Planet planet;
    private Toolbar toolbar;                              // Declaring the Toolbar Object
    private DrawerLayout drawerLayout;                 // Declaring Action Bar Drawer Toggle
    private LocationManager locationManager;

    protected void onCreateDrawer() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        player = (Player) intent.getExtras().getSerializable("player");
        planet = (Planet) intent.getExtras().getSerializable("planet");

        initNavigationDrawer();

        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
    }

    public void initNavigationDrawer() {
        NavigationView navigationView = (NavigationView) findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                if (!isLocationEnabled()) {
                    alertGPS();
                } else {
                    int id = menuItem.getItemId();

                    switch (id) {
                        case R.id.home:
                            drawerLayout.closeDrawers();
                            Intent homeIntent = new Intent(context, HomeActivity.class);
                            homeIntent.putExtra("player", player);
                            startActivity(homeIntent);
                            break;
                        case R.id.attack:
                            drawerLayout.closeDrawers();
                            Intent attackIntent = new Intent(context, AttackActivity.class);
                            attackIntent.putExtra("player", player);
                            attackIntent.putExtra("planet", planet);
                            startActivity(attackIntent);
                            break;
                        case R.id.defense:
                            drawerLayout.closeDrawers();
                            Intent defenseIntent = new Intent(context, DefenseActivity.class);
                            defenseIntent.putExtra("player", player);
                            defenseIntent.putExtra("planet", planet);
                            startActivity(defenseIntent);
                            break;
                        case R.id.constructions:
                            drawerLayout.closeDrawers();
                            Intent constructionIntent = new Intent(context, ConstructionsActivity.class);
                            constructionIntent.putExtra("player", player);
                            constructionIntent.putExtra("planet", planet);
                            startActivity(constructionIntent);
                            break;
                        case R.id.military:
                            drawerLayout.closeDrawers();
                            Intent militaryConstructionIntent = new Intent(context, MilitaryConstructionsActivity.class);
                            militaryConstructionIntent.putExtra("player", player);
                            militaryConstructionIntent.putExtra("planet", planet);
                            startActivity(militaryConstructionIntent);
                            break;
                        case R.id.colonization:
                            drawerLayout.closeDrawers();
                            //Intent intent = new Intent(context, DefenseQuestionActivity.class);
                            Intent intent = new Intent(context, ColonizeActivity.class);
                            intent.putExtra("player", player);
                            intent.putExtra("planet", planet);
                            startActivity(intent);
                            break;
                        case R.id.help:
                            drawerLayout.closeDrawers();
                            Intent helpIntent = new Intent(context, HelpActivity.class);
                            helpIntent.putExtra("player", player);
                            helpIntent.putExtra("planet", planet);
                            startActivity(helpIntent);
                            break;
                    }
                }
                return true;
            }
        });

        View header = navigationView.getHeaderView(0);
        TextView nameText = (TextView) header.findViewById(R.id.name);
        TextView levelText = (TextView) header.findViewById(R.id.level);
        nameText.setText(player.getUsername());
        levelText.setText("Nivel " + player.getLevel());
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View v) {
                super.onDrawerClosed(v);
            }

            @Override
            public void onDrawerOpened(View v) {
                super.onDrawerOpened(v);
            }
        };
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        //if (id == R.id.action_settings) {
            //return true;
        //}

        return super.onOptionsItemSelected(item);
    }

    protected void changePlanet(Planet planet){
        this.planet = planet;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("player", player);
        intent.putExtra("planet", planet);
        startActivity(intent);
    }

    private void alertGPS() {
        if(!isLocationEnabled()) {
            SweetAlertDialog dialog = Helpers.getErrorDialog(this, "No tiene activado el GPS!", "Por favor active el GPS para poder jugar.");
            dialog.show();
        }
    }

    private boolean isLocationEnabled() {
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER) ||
                locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
    }

    protected String ObtenerHora(long tiempo) {
        String tiempoExtension = "";
        //long time = tiempo - System.currentTimeMillis();
        long time = tiempo;

        long dias = TimeUnit.MILLISECONDS.toDays(time);
        long horas = TimeUnit.MILLISECONDS.toHours(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toDays(time));
        long minutos = TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time));
        long segundos = TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time));

        tiempoExtension += (dias > 0) ? (((dias >= 10) ? String.valueOf(dias) : "0" + String.valueOf(dias)) + ((dias == 1) ? " día" : " día") + " : ") : "";
        tiempoExtension += (horas > 0) ? (((horas >= 10) ? String.valueOf(horas) : "0" + String.valueOf(horas)) + ((horas == 1) ? " hora" : " hora") + " : ") : "";
        tiempoExtension += (minutos > 0) ? (((minutos >= 10) ? String.valueOf(minutos) : "0" + String.valueOf(minutos)) + ((minutos == 1) ? " min" : " min") + " : ") : "";
        tiempoExtension += (segundos > 0) ? (((segundos >= 10) ? String.valueOf(segundos) : "0" + String.valueOf(segundos)) + ((segundos == 1) ? " seg" : " seg")) : "";

        tiempoExtension.trim();

        return(tiempoExtension);
    }

}
