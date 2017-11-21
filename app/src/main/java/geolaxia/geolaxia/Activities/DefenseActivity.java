package geolaxia.geolaxia.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.CountDownTimer;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.TextView;

import org.json.JSONException;

import java.sql.Time;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import cn.pedant.SweetAlert.SweetAlertDialog;
import geolaxia.geolaxia.Model.Cannon;
import geolaxia.geolaxia.Model.Dto.IsBuildingCannonsDTO;
import geolaxia.geolaxia.Model.Helpers;
import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.Player;
import geolaxia.geolaxia.Model.Shield;
import geolaxia.geolaxia.R;
import geolaxia.geolaxia.Services.Implementation.DefenseService;
import geolaxia.geolaxia.Services.Interface.IDefenseService;
import geolaxia.geolaxia.Services.Implementation.PlanetService;
import geolaxia.geolaxia.Services.Interface.IPlanetService;

public class DefenseActivity extends MenuActivity {
    final Activity context = this;

    private IDefenseService defenseService;
    private IPlanetService planetService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.Constructor();
        this.ConstructorServicios();

        this.CargarCanones();
        this.CargarCanonesConstruccion();
        this.CargarBotonConstruir();
        this.CargarEstadoEscudo();

        this.EstaConstruyendoCanones();

        this.VaciarPantalla();
    }

    private void Constructor() {
        //INICIO BASE.
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

        //this.defenseService = new DefenseService();
        //this.planetService = new PlanetService();
    }

    private void ConstructorServicios() {
        this.defenseService = new DefenseService();
        this.planetService = new PlanetService();
    }

    // -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-
    // Carga la seccion de canones del usuario.
    private void CargarCanones() {
        this.defenseService.GetCannons(this.player.getUsername(), this.player.getToken(), this, this.planet.getId());
    }

    // Respuesta del service.
    public void CargarCanonesAhora(ArrayList<Cannon> cannons){
        TextView cantCanonesActivos = (TextView)findViewById(R.id.defense_cant_canones_activos);

        if (cannons != null && !cannons.isEmpty()) {

            int canonesActivos = cannons.size();
            cantCanonesActivos.setText(String.valueOf(canonesActivos));
        } else {
            cantCanonesActivos.setText(String.valueOf(0));
        }
    }

    // -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

    // Carga la seccion de seleccionar canones para construir.
    private void CargarCanonesConstruccion() {
        NumberPicker np = (NumberPicker) findViewById(R.id.defense_cant_canones_construccion);
        Helpers.setNumberPickerTextColor(np, Color.WHITE);
        np.setMinValue(0);
        np.setMaxValue(50);
        np.setWrapSelectorWheel(true);

        np.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                TextView cantCanonesCostoCristal = (TextView) findViewById(R.id.defense_cant_canones_construccion_costo_cristal_valor);
                TextView cantCanonesCostoMetal = (TextView) findViewById(R.id.defense_cant_canones_construccion_costo_metal_valor);
                TextView cantCanonesCostoTiempo = (TextView) findViewById(R.id.defense_cant_canones_construccion_costo_tiempo_valor);

                if (newVal > 0) {
                    int valorCristal = newVal * 50;
                    int valorMetal = newVal * 100;
                    int valorTiempo =  newVal * 3;

                    cantCanonesCostoCristal.setText(String.valueOf(valorCristal));
                    cantCanonesCostoMetal.setText(String.valueOf(valorMetal));
                    cantCanonesCostoTiempo.setText(String.valueOf(valorTiempo) + " mins");

                    if (TieneRecursosNecesariosParaConstruir(valorMetal, valorCristal)) {
                        SetearBotonConstruir(true);
                    } else {
                        SetearBotonConstruir(false);
                    }
                } else {
                    VaciarPantalla();
                }
            }
        });
    }

    // Carga la seccion de seleccionar canones para construir boton construir.
    private void CargarBotonConstruir() {
        Button construirBoton = (Button) findViewById(R.id.defense_cant_canones_construccion_boton);
        construirBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SweetAlertDialog dialog = Helpers.getConfirmationDialog(context, "Construir", "¿Está seguro que desea construir esta cantidad de Cañones?", "Si", "No");

                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        try {
                            BotonConstruir();
                            sweetAlertDialog.cancel();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                dialog.show();
            }
        });
    }

    // Carga la seccion de seleccionar canones para construir boton construir.
    private void BotonConstruir() throws JSONException{
        NumberPicker np = (NumberPicker) findViewById(R.id.defense_cant_canones_construccion);
        int cantCanonesAContruir = np.getValue();

        if (cantCanonesAContruir > 0) {
            this.defenseService.BuildCannons(this.player.getUsername(), this.player.getToken(), this, this.planet.getId(), cantCanonesAContruir);
        }
    }

    // Respuesta del service.
    public void CargarTiempoConstruccionCanonesAhora(){
        NumberPicker np = (NumberPicker) findViewById(R.id.defense_cant_canones_construccion);
        int cantCanonesAContruir = np.getValue();

        this.CargarTiempoConstruccion(cantCanonesAContruir);

        this.planet.setMetal(this.planet.getMetal() - 100 * cantCanonesAContruir);
        this.planet.setCrystal(this.planet.getCrystal() - 50 * cantCanonesAContruir);

        SweetAlertDialog dialog = Helpers.getSuccesDialog(context, "Defensa", "La construcción de los cañones ha comenzado!");
        dialog.show();
    }

    // -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

    // Carga la seccion de escudo del planeta.
    private void CargarEstadoEscudo() {
        this.defenseService.GetShieldStatus(this.player.getUsername(), this.player.getToken(), this, this.planet.getId());
    }

    // Respuesta del service.
    public void CargarEstadoEscudoAhora(Shield shield) {
        TextView estadoEscudo = (TextView)findViewById(R.id.defense_estado_escudo);
        estadoEscudo.setTypeface(null, Typeface.BOLD);

        if(shield != null && shield.getStatus()){
            estadoEscudo.setText("Activado");
            estadoEscudo.setTextColor(Color.GREEN);
        } else {
            estadoEscudo.setText("Desactivado");
            estadoEscudo.setTextColor(Color.RED);
        }
    }

    private void CargerBotonDefender() {
        Button defenderBoton = (Button) findViewById(R.id.defense_question_defender_boton);
        defenderBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SweetAlertDialog dialog = Helpers.getConfirmationDialog(context, "Defender", "¿Está preparado para responder 3 preguntas? Recuerde que tiene 10 segundos.", "Si", "No");

                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                    Intent defenseIntent = new Intent(context, DefenseQuestionActivity.class);
                    defenseIntent.putExtra("player", player);
                    defenseIntent.putExtra("planet", planet);
                    startActivity(defenseIntent);
                    sweetAlertDialog.cancel();
                    }
                });

                dialog.show();
            }
        });
    }

    // -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

    // Carga el tiempo si esta construyendo canones.
    private void EstaConstruyendoCanones() {
        this.defenseService.IsBuildingCannons(this.player.getUsername(), this.player.getToken(), this, this.planet.getId());
    }

    // Respuesta del service.
    public void EstaConstruyendoCanonesAhora(IsBuildingCannonsDTO tiempoFinalizacion){
        if (tiempoFinalizacion.IsBuilding()) {
            this.CargarTiempoConstruccion(tiempoFinalizacion.getData());
        }
    }

    // -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

    private boolean TieneRecursosNecesariosParaConstruir(int metal, int cristal) {
        boolean tieneRecursosSuficientes = true;

        if (metal > this.planet.getMetal() || cristal > this.planet.getCrystal()) {
            tieneRecursosSuficientes = false;
        }

        return (tieneRecursosSuficientes);
    }

    private void VaciarPantalla(){
        NumberPicker np = (NumberPicker) findViewById(R.id.defense_cant_canones_construccion);
        TextView cantCanonesCostoCristal = (TextView) findViewById(R.id.defense_cant_canones_construccion_costo_cristal_valor);
        TextView cantCanonesCostoMetal = (TextView) findViewById(R.id.defense_cant_canones_construccion_costo_metal_valor);
        TextView cantCanonesCostoTiempo = (TextView) findViewById(R.id.defense_cant_canones_construccion_costo_tiempo_valor);

        np.setValue(0);
        cantCanonesCostoCristal.setText("-");
        cantCanonesCostoMetal.setText("-");
        cantCanonesCostoTiempo.setText("-");

        this.SetearBotonConstruir(false);
    }

    private void SetearBotonConstruir(boolean activo) {
        Button construirBoton = (Button) findViewById(R.id.defense_cant_canones_construccion_boton);
        construirBoton.setEnabled(activo);
        construirBoton.setPaintFlags((!activo) ? Paint.STRIKE_THRU_TEXT_FLAG : 0);
    }

    private void CargarTiempoConstruccion(int cantCanonesAContruir) {
        new CountDownTimer(cantCanonesAContruir * 180000, 1000) {
            TextView timer = (TextView) findViewById(R.id.defense_cant_canones_construccion_timer);
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
                CargarCanones();
            }
        }.start();

        VaciarPantalla();
        PantallaSegunConstruccion();
    }

    private void CargarTiempoConstruccion(long fechaFinalizacion) {
        long tiempoRestante = fechaFinalizacion - System.currentTimeMillis();

        new CountDownTimer(tiempoRestante, 1000) {
            TextView timer = (TextView) findViewById(R.id.defense_cant_canones_construccion_timer);

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
                CargarCanones();
            }

        }.start();

        VaciarPantalla();
        PantallaSegunConstruccion();
    }

    private boolean EstaConstruyendo() {
        TextView timer = (TextView) findViewById(R.id.defense_cant_canones_construccion_timer);

        if (timer.getVisibility() == View.VISIBLE) {
            return (true);
        }

        return (false);
    }

    private void HabilitarPantalla() {
        NumberPicker np = (NumberPicker) findViewById(R.id.defense_cant_canones_construccion);
        np.setEnabled(true);

        this.SetearBotonConstruir(true);
    }

    private void DeshabilitarPantalla() {
        NumberPicker np = (NumberPicker) findViewById(R.id.defense_cant_canones_construccion);
        np.setEnabled(false);

        this.SetearBotonConstruir(false);
    }

    private void PantallaSegunConstruccion() {
        boolean estaConstruyendo = this.EstaConstruyendo();

        if (estaConstruyendo) {
            this.DeshabilitarPantalla();
        } else {
            this.HabilitarPantalla();
        }
    }
}
