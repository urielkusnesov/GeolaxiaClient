package geolaxia.geolaxia.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.json.JSONException;

import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.Player;
import geolaxia.geolaxia.R;
import geolaxia.geolaxia.Services.Implementation.DefenseService;
import geolaxia.geolaxia.Services.Interface.IDefenseService;

public class DefenseActivity extends MenuActivity {
    private Player player;
    private Planet planet;
    final Activity context = this;

    private IDefenseService defenseService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //INICIO BASE.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_defense);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        super.onCreateDrawer();

        mFormView = findViewById(R.id.scroll_form);
        mProgressView = findViewById(R.id.progress);

        Intent intent = getIntent();
        player = (Player) intent.getExtras().getSerializable("player");
        planet = (Planet) intent.getExtras().getSerializable("planet");
        //FIN BASE.

        this.Init();

        TextView cantCanonesActivos = (TextView)findViewById(R.id.defense_cant_canones_activos);
        cantCanonesActivos.setText("50");

        NumberPicker np = (NumberPicker) findViewById(R.id.defense_cant_canones_construccion);
        np.setMinValue(0);
        np.setMaxValue(50);
        np.setWrapSelectorWheel(true);

        this.CargarEstadoEscudo();
    }

    private void Init(){
        this.defenseService = new DefenseService();
    }

    private void CargarEstadoEscudo() {
        TextView estadoEscudo = (TextView)findViewById(R.id.defense_estado_escudo);
        estadoEscudo.setTypeface(null, Typeface.BOLD);

        if(this.defenseService.GetShieldStatus(this.player.getUsername(), this.player.getToken(), this)){
            estadoEscudo.setText("Activado");
            estadoEscudo.setTextColor(Color.GREEN);
        } else {
            estadoEscudo.setText("Desactivado");
            estadoEscudo.setTextColor(Color.RED);
        }
    }
}
