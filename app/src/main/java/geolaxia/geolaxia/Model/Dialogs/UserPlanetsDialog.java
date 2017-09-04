package geolaxia.geolaxia.Model.Dialogs;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;

import geolaxia.geolaxia.Activities.AttackActivity;
import geolaxia.geolaxia.Model.Adapters.PlanetListAdapter;
import geolaxia.geolaxia.Model.Adapters.UserPlanetsAdapter;
import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.R;

import static geolaxia.geolaxia.R.id.planetList;

/**
 * Created by uriel on 3/9/2017.
 */

public class UserPlanetsDialog extends Dialog implements android.view.View.OnClickListener{
    public AttackActivity context;
    public Dialog d;
    public Button ok;
    public String userName;
    public ArrayList<Planet> userPlanets;

    RecyclerView planetList;
    LinearLayoutManager planetListManager;
    UserPlanetsAdapter planetListAdapter;


    public UserPlanetsDialog(AttackActivity a, String userName, ArrayList<Planet> userPlanets) {
        super(a);
        this.context = a;
        this.userName = userName;
        this.userPlanets = userPlanets;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.user_planets_dialog);
        ok = (Button) findViewById(R.id.confirm);
        ok.setOnClickListener(this);

        TextView userNameText = (TextView) findViewById(R.id.userName);
        userNameText.setText(userName);

        planetList = (RecyclerView) findViewById(R.id.planetList);
        planetListManager = new LinearLayoutManager(context);
        planetList.setLayoutManager(planetListManager);

        if(userPlanets.size() > 0){
            planetListAdapter = new UserPlanetsAdapter(userPlanets, context);
            planetList.setAdapter(planetListAdapter);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ok:
                dismiss();
                break;
            default:
                break;
        }
        dismiss();
    }
}
