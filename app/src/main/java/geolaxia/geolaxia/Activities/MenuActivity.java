package geolaxia.geolaxia.Activities;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.Player;
import geolaxia.geolaxia.R;

public class MenuActivity extends BaseActivity {

    private Activity context = this;
    protected Player player;
    protected Planet planet;
    private Toolbar toolbar;                              // Declaring the Toolbar Object
    private DrawerLayout drawerLayout;                 // Declaring Action Bar Drawer Toggle

    protected void onCreateDrawer() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        player = (Player) intent.getExtras().getSerializable("player");
        planet = (Planet) intent.getExtras().getSerializable("planet");

        initNavigationDrawer();
    }

    public void initNavigationDrawer() {

        NavigationView navigationView = (NavigationView)findViewById(R.id.navigation_view);
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {

                int id = menuItem.getItemId();

                switch (id){
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
                        break;
                    case R.id.colonization:
                        drawerLayout.closeDrawers();
                        Intent DefenseQuestionIntent = new Intent(context, DefenseQuestionActivity.class);
                        DefenseQuestionIntent.putExtra("player", player);
                        DefenseQuestionIntent.putExtra("planet", planet);
                        startActivity(DefenseQuestionIntent);
                        break;
                    case R.id.help:
                        drawerLayout.closeDrawers();
                        Intent helpIntent = new Intent(context, HelpActivity.class);
                        helpIntent.putExtra("player", player);
                        helpIntent.putExtra("planet", planet);
                        startActivity(helpIntent);
                        break;
                }
                return true;
            }
        });
        View header = navigationView.getHeaderView(0);
        TextView nameText = (TextView)header.findViewById(R.id.name);
        TextView levelText = (TextView)header.findViewById(R.id.level);
        nameText.setText(player.getUsername());
        levelText.setText("Nivel " + player.getLevel());
        drawerLayout = (DrawerLayout)findViewById(R.id.drawer);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.openDrawer,R.string.closeDrawer){

            @Override
            public void onDrawerClosed(View v){
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
}
