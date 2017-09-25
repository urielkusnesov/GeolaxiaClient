package geolaxia.geolaxia.Activities;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import org.json.JSONException;

import java.util.ArrayList;

import cn.pedant.SweetAlert.SweetAlertDialog;
import geolaxia.geolaxia.Model.Helpers;
import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.Player;
import geolaxia.geolaxia.R;
import geolaxia.geolaxia.Services.Implementation.LoginService;
import geolaxia.geolaxia.Services.Interface.ILoginService;

public class RegisterActivity extends BaseActivity {

    final Activity context = this;
    private ILoginService loginService;

    /*EditText firstNameText;
    EditText lastNameText;*/
    EditText emailText;
    EditText usernameText;
    EditText passwordText;
    EditText confirmPasswordText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        loginService = new LoginService();

        /*firstNameText = (EditText) findViewById(R.id.first_name);
        lastNameText = (EditText) findViewById(R.id.last_name);*/
        emailText = (EditText) findViewById(R.id.email);
        usernameText = (EditText) findViewById(R.id.username);
        passwordText = (EditText) findViewById(R.id.password);
        confirmPasswordText = (EditText) findViewById(R.id.confirm_password);

        Button registerButton = (Button) findViewById(R.id.register);
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    Register();
                }catch (JSONException ex){

                }
            }
        });

        mFormView = findViewById(R.id.register_form);
        mProgressView = findViewById(R.id.progress);
    }

    private void Register() throws JSONException {

        // Reset errors.
        /*firstNameText.setError(null);
        lastNameText.setError(null);*/
        emailText.setError(null);
        usernameText.setError(null);
        passwordText.setError(null);
        confirmPasswordText.setError(null);

        // Store values at the time of the login attempt.
        /*String firstname = firstNameText.getText().toString();
        String lastname = lastNameText.getText().toString();*/
        String email = emailText.getText().toString();
        String username = usernameText.getText().toString();
        String password = passwordText.getText().toString();
        String confirmPassword = confirmPasswordText.getText().toString();

        boolean cancel = false;
        View focusView = null;

        /*if (TextUtils.isEmpty(firstname)) {
            this.firstNameText.setError(getString(R.string.error_field_required));
            focusView = this.firstNameText;
            cancel = true;
        }
        if (TextUtils.isEmpty(lastname)) {
            this.lastNameText.setError(getString(R.string.error_field_required));
            focusView = this.lastNameText;
            cancel = true;
        }*/
        if (TextUtils.isEmpty(email)) {
            this.emailText.setError(getString(R.string.error_field_required));
            focusView = this.emailText;
            cancel = true;
        }
        if (TextUtils.isEmpty(username)) {
            this.usernameText.setError(getString(R.string.error_field_required));
            focusView = this.usernameText;
            cancel = true;
        }
        if (TextUtils.isEmpty(password)) {
            this.passwordText.setError(getString(R.string.error_field_required));
            focusView = this.passwordText;
            cancel = true;
        }
        if (!password.equals(confirmPassword)) {
            this.confirmPasswordText.setError(getString(R.string.password_not_match));
            focusView = this.confirmPasswordText;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt register and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            //Player player = new Player(0, 1, 0, username, password, firstname, lastname, email, "", new ArrayList<Planet>(), "", "");
            Player player = new Player(0, 1, 0, username, password, "", "", email, "", new ArrayList<Planet>(), "", "");
            loginService.Register(player, this);
        }
    }

    public void registerSuccesfull(){
        showProgress(false);
        SweetAlertDialog dialog = Helpers.getSuccesDialog(this, "Registracion", "El usuario se registro con exito");

        dialog.setCancelable(false);
        dialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                Intent intent = new Intent(context, LoginActivity.class);
                startActivity(intent);
                sweetAlertDialog.cancel();
            }
        });

        dialog.show();
    }
}
