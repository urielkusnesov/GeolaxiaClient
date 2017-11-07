package geolaxia.geolaxia.Model.Adapters;

import android.graphics.Color;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import geolaxia.geolaxia.Activities.AttackActivity;
import geolaxia.geolaxia.Model.Dialogs.UserPlanetsDialog;
import geolaxia.geolaxia.Model.Galaxy;
import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.SolarSystem;
import geolaxia.geolaxia.R;

/**
 * Created by uriel on 26/8/2017.
 */

public class UserPlanetsAdapter extends RecyclerView.Adapter<UserPlanetsAdapter.ViewHolder> {
    private ArrayList<Planet> planets;
    private AttackActivity.CloseAttackFragment context;
    private int selectedPostion = -1;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name;

        public ViewHolder(View v) {
            super(v);
            name = (TextView) v.findViewById(R.id.name);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public UserPlanetsAdapter(ArrayList<Planet> planets, AttackActivity.CloseAttackFragment context)
    {
        this.planets = planets;
        this.context = context;
    }

    @Override
    public UserPlanetsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.planet_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        final Planet planet = planets.get(position);
        SolarSystem solarSystem = planet.getSolarSystem();
        Galaxy galaxy = solarSystem.getGalaxy();
        holder.name.setText(galaxy.getName() + " - " + solarSystem.getName() + " - " + planet.getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.selectTargetPlanet(planet);
                selectedPostion = position;
                notifyDataSetChanged();
            }
        });

        if(selectedPostion==position){
            holder.itemView.setBackgroundColor(Color.WHITE);
            holder.name.setTextColor(Color.BLACK);
        }
        else
        {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT);
            holder.name.setTextColor(Color.WHITE);
        }
    }

    @Override
    public int getItemCount() {
        return planets.size();
    }
}