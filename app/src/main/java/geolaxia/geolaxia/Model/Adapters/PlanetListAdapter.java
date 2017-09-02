package geolaxia.geolaxia.Model.Adapters;

import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import geolaxia.geolaxia.Activities.AttackActivity;
import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.R;

/**
 * Created by uriel on 26/8/2017.
 */

public class PlanetListAdapter extends RecyclerView.Adapter<PlanetListAdapter.ViewHolder> {
    private ArrayList<Planet> planets;
    private AttackActivity.CoordinatesFragment context;

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case
        public TextView name;

        public ViewHolder(View v) {
            super(v);
            v.setClickable(true);
            name = (TextView) v.findViewById(R.id.name);
        }
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public PlanetListAdapter(ArrayList<Planet> planets, AttackActivity.CoordinatesFragment context)
    {
        this.planets = planets;
        this.context = context;
    }

    @Override
    public PlanetListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.planet_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if(planets.get(position).getConqueror() != null){
            holder.name.setText(planets.get(position).getName() + " - " + planets.get(position).getConqueror().getUsername());
        }else{
            holder.name.setText(planets.get(position).getName() + " - No Colonizado");
        }

        holder.itemView.setClickable(true);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                context.selectTargetPlanet(planets.get(position));
            }
        });
    }

    @Override
    public int getItemCount() {
        return planets.size();
    }
}