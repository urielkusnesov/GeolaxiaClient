package geolaxia.geolaxia.Activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import geolaxia.geolaxia.Model.Helpers;
import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.Player;
import geolaxia.geolaxia.Model.Question;
import geolaxia.geolaxia.R;
import geolaxia.geolaxia.Services.Implementation.DefenseService;
import geolaxia.geolaxia.Services.Interface.IDefenseService;

public class DefenseQuestionActivity extends MenuActivity {
    final Activity context = this;

    private IDefenseService defenseService;

    private int attackId;
    //private int defenseId;

    private int idPregunta1;
    private int idPregunta2;
    private int idPregunta3;
    private String respuesta1;
    private String respuesta2;
    private String respuesta3;
    private String respuesta1_correcta;
    private String respuesta2_correcta;
    private String respuesta3_correcta;

    private CountDownTimer counterTimer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.Constructor();
        this.ConstructorServicios();

        this.CargarPreguntas();
        this.CargarBotonDefender();

        this.CargarTimer(20);
    }

    private void CargarTimer(int segundos) {
        int countDownInterval = 1000;

        counterTimer = new CountDownTimer(segundos * 1000, countDownInterval) {
            TextView timer = (TextView) findViewById(R.id.defense_preguntas_timer);

            public void onFinish() {
                //finish your activity here
                SweetAlertDialog dialog = Helpers.getErrorDialog(context, "Defender", "Tiempo agotado. " + ObtenerMsj());

                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        MandarRespuestas();

                        GoHome();
                    }
                });

                dialog.show();
            }

            public void onTick(long millisUntilFinished) {
                //called every 1 sec coz countDownInterval = 1000 (1 sec)
                int segsFaltantes = (int) (millisUntilFinished / 1000);
                String segs = (segsFaltantes < 10) ? "0" + String.valueOf(segsFaltantes) : String.valueOf(segsFaltantes);

                if (segsFaltantes > 10) {
                    timer.setText("00:" + segs + ":00");
                    timer.setTextColor(Color.GREEN);
                } else if (segsFaltantes <= 10 && segsFaltantes >= 3) {
                    timer.setText("00:" + segs + ":00");
                    timer.setTextColor(Color.YELLOW);
                } else {
                    timer.setText("00:" + segs + ":00");
                    timer.setTextColor(Color.RED);
                }
            }
        };
        counterTimer.start();
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
        attackId = (int) intent.getExtras().getSerializable("attackId");
        //FIN BASE.
    }

    private void ConstructorServicios() {
        this.defenseService = new DefenseService();
    }

    // -*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-

    private void CargarPreguntas() {
        //this.defenseService.Get3RandomQuestions(this.player.getUsername(), this.player.getToken(), this, this.attackId);
        this.defenseService.Get3RandomQuestions(this.player.getUsername(), this.player.getToken(), this);
    }

    public void CargarPreguntasAhora(ArrayList<Question> questions) {
        for (int i = 1; i <= questions.size(); i++) {
            this.CargarIdPregunta(i, questions.get(i-1));
            this.CargarPregunta(i, questions.get(i-1));
            this.CargarRespuestasCorrectas(i, questions.get(i-1));
        }
    }

    private void CargarIdPregunta(int numeroPregunta, Question question) {
        if (numeroPregunta == 1) {
            this.idPregunta1 = question.GetId();
        } else if (numeroPregunta == 2) {
            this.idPregunta2 = question.GetId();
        } else if (numeroPregunta == 3) {
            this.idPregunta3 = question.GetId();
        }
    }

    private void CargarPregunta(int numeroPregunta, Question question) {
        int preguntaId = getResources().getIdentifier("P"+String.valueOf(numeroPregunta), "id", this.getPackageName());
        TextView pregunta = (TextView)findViewById(preguntaId);
        pregunta.setText(question.GetQuestion());

        ArrayList<String> answers = question.GetAnswers();

        int grupoId = getResources().getIdentifier("P"+String.valueOf(numeroPregunta)+"_R", "id", this.getPackageName());
        RadioGroup rbtnGrp = (RadioGroup)findViewById(grupoId);

        for (int i = 0; i < rbtnGrp.getChildCount(); i++) {
            ((RadioButton) rbtnGrp.getChildAt(i)).setText(answers.get(i));
        }
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
                counterTimer.cancel();
                SweetAlertDialog dialog = Helpers.getSuccesDialog(context, "Defender", ObtenerMsj());

                dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        MandarRespuestas();

                        GoHome();
                    }
                });

                dialog.show();
            }
        });
    }

    private String ObtenerMsj() {
        String msj = "";

        if (this.ObtenerCantRespuestasCorrectas() == 3) {
            msj = "Muy Bien hecho!. Has contestado 3 respuestas correctas.";
        } else if(this.ObtenerCantRespuestasCorrectas() == 2) {
            msj = "No está nada mal!. Has contestado 2 respuestas correctas.";
        } else if(this.ObtenerCantRespuestasCorrectas() == 1) {
            msj = "Bien hecho!. Has contestado 1 respuesta correcta.";
        } else {
            msj = "Suerte para la próxima!. Has contestado 0 respuestas correctas.";
        }

        return(msj);
    }

    private void MandarRespuestas() {
        this.defenseService.DefenseFromAttack(this.player.getUsername(), this.player.getToken(), this, this.attackId, this.idPregunta1, this.idPregunta2, this.idPregunta3, this.ObtenerCantRespuestasCorrectas());
    }

    private void GoHome() {
        DefenseQuestionActivity.this.finish();

        Intent homeIntent = new Intent(context, HomeActivity.class);
        homeIntent.putExtra("player", player);
        homeIntent.putExtra("planet", planet);
        startActivity(homeIntent);
    }

    private int ObtenerCantRespuestasCorrectas() {
        int cantRespuestasCorrectas = 0;

        if (respuesta1 != null && respuesta1_correcta != null) {
            if (respuesta1.equals(respuesta1_correcta)) {
                cantRespuestasCorrectas++;
            }
        }

        if (respuesta2 != null && respuesta2_correcta != null) {
            if (respuesta2.equals(respuesta2_correcta)) {
                cantRespuestasCorrectas++;
            }
        }

        if (respuesta3 != null && respuesta3_correcta != null) {
            if (respuesta3.equals(respuesta3_correcta)) {
                cantRespuestasCorrectas++;
            }
        }

        return(cantRespuestasCorrectas);
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
