package geolaxia.geolaxia.Activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.Player;
import geolaxia.geolaxia.Model.Question;
import geolaxia.geolaxia.R;
import geolaxia.geolaxia.Services.Implementation.DefenseService;
import geolaxia.geolaxia.Services.Interface.IDefenseService;

public class DefenseQuestionActivity extends MenuActivity {
    final Activity context = this;

    private IDefenseService defenseService;

    private String respuesta1;
    private String respuesta2;
    private String respuesta3;
    private String respuesta1_correcta;
    private String respuesta2_correcta;
    private String respuesta3_correcta;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.Constructor();
        this.ConstructorServicios();

        this.CargarPreguntas();
        this.CargarBotonDefender();
    }

    private void Constructor() {
        //INICIO BASE.
        setContentView(R.layout.activity_defense_question);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        super.onCreateDrawer();

        mFormView = findViewById(R.id.scroll_form);
        mProgressView = findViewById(R.id.progress);

        Intent intent = getIntent();
        player = (Player) intent.getExtras().getSerializable("player");
        planet = (Planet) intent.getExtras().getSerializable("planet");
        //FIN BASE.
    }

    private void ConstructorServicios() {
        this.defenseService = new DefenseService();
    }

    // -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

    private void CargarPreguntas() {
        this.defenseService.Get3RandomQuestions(this.player.getUsername(), this.player.getToken(), this);
    }

    public void CargarPreguntasAhora(ArrayList<Question> questions) {
        for (int i = 1; i <= questions.size(); i++) {
            this.CargarPregunta(i, questions.get(i-1));
            this.CargarRespuestasCorrectas(i, questions.get(i-1));
        }
    }

    private void CargarPregunta(int numeroPregunta, Question question) {
        int preguntaId = getResources().getIdentifier("P"+String.valueOf(numeroPregunta), "id", this.getPackageName());
        TextView pregunta = (TextView)findViewById(preguntaId);
        pregunta.setText(question.GetQuestion());

//        int respuestaId = 0;
//       TextView respuesta = null;
        ArrayList<String> answers = question.GetAnswers();

        int grupoId = getResources().getIdentifier("P"+String.valueOf(numeroPregunta)+"_R", "id", this.getPackageName());
        RadioGroup rbtnGrp = (RadioGroup)findViewById(grupoId);

        for (int i = 0; i < rbtnGrp.getChildCount(); i++) {
            ((RadioButton) rbtnGrp.getChildAt(i)).setText(answers.get(i));
        }

//        for (int i = 1; i <= answers.size(); i++) {
//            respuestaId = getResources().getIdentifier("P"+String.valueOf(numeroPregunta)+"_R"+String.valueOf(i), "id", this.getPackageName());
//            respuesta = (RadioButton)findViewById(respuestaId);
//            respuesta.setText(answers.get(i-1));
//        }
    }

    private void CargarRespuestasCorrectas(int numeroPregunta, Question question) {
        if (numeroPregunta == 1) {
            this.respuesta1_correcta = question.GetCorrectAnswer();
        } else if (numeroPregunta == 2) {
            this.respuesta2_correcta = question.GetCorrectAnswer();
        } else if (numeroPregunta == 3) {
            this.respuesta3_correcta = question.GetCorrectAnswer();
        }
    }

    // -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

    // Carga la seccion para boton defender.
    private void CargarBotonDefender() {
        Button DefenderBoton = (Button) findViewById(R.id.defense_question_defender_boton);
        DefenderBoton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    BotonDefender();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void BotonDefender() throws JSONException{
        int cantRespuestasCorrectas = 0;

        if (respuesta1.equals(respuesta1_correcta)) {
            cantRespuestasCorrectas++;
        }

        if (respuesta2.equals(respuesta2_correcta)) {
            cantRespuestasCorrectas++;
        }

        if (respuesta3.equals(respuesta3_correcta)) {
            cantRespuestasCorrectas++;
        }

        //this.defenseService.Defense(this.player.getUsername(), this.player.getToken(), this, cantRespuestasCorrectas);
    }

    // -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

    public void onRadioButtonClicked_P1(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.P1_R1:
            case R.id.P1_R2:
            case R.id.P1_R3:
            case R.id.P1_R4:
            case R.id.P1_R5:
                if (checked) {
                    this.respuesta1 = ((RadioButton) view).getText().toString();
                    break;
                }

            default:
                this.respuesta1 = "";
                break;
        }
    }

    public void onRadioButtonClicked_P2(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.P2_R1:
            case R.id.P2_R2:
            case R.id.P2_R3:
            case R.id.P2_R4:
            case R.id.P2_R5:
                if (checked) {
                    this.respuesta2 = ((RadioButton) view).getText().toString();
                    break;
                }

            default:
                this.respuesta2 = "";
                break;
        }
    }

    public void onRadioButtonClicked_P3(View view) {
        boolean checked = ((RadioButton) view).isChecked();

        switch(view.getId()) {
            case R.id.P3_R1:
            case R.id.P3_R2:
            case R.id.P3_R3:
            case R.id.P3_R4:
            case R.id.P3_R5:
                if (checked) {
                    this.respuesta3 = ((RadioButton) view).getText().toString();
                    break;
                }

            default:
                this.respuesta3 = "";
                break;
        }
    }
}
