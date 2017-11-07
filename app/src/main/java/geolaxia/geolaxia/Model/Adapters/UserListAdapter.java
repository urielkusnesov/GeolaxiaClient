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
import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.Model.Player;
import geolaxia.geolaxia.R;

/**
 * Created by uriel on 26/8/2017.
 */

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private ArrayList<Player> players;
    private AttackActivity.CloseAttackFragment context;

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
    public UserListAdapter(ArrayList<Player> players, AttackActivity.CloseAttackFragment context)
    {
        this.players = players;
        this.context = context;
    }

    @Override
    public UserListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
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
        holder.name.setText(players.get(position).getUsername());
        holder.itemView.setClickable(true);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserPlanetsDialog dialog = new UserPlanetsDialog((AttackActivity) context.getActivity(),
                                                players.get(position).getUsername(), players.get(position).getPlanets(), context);

                dialog.show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return players.size();
    }
}