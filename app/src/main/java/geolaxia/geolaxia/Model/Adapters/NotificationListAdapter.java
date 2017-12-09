package geolaxia.geolaxia.Model.Adapters;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import geolaxia.geolaxia.Activities.AttackActivity;
import geolaxia.geolaxia.Activities.HomeActivity;
import geolaxia.geolaxia.Model.Dto.NotificationsDTO;
import geolaxia.geolaxia.Model.Notification;
import geolaxia.geolaxia.Model.Planet;
import geolaxia.geolaxia.R;

/**
 * Created by uriel on 26/8/2017.
 */

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> {
    private ArrayList<Notification> Notifications;
    private HomeActivity Context;
    private int selectedPostion = -1;

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
    public NotificationListAdapter(ArrayList<Notification> notifications, HomeActivity context)
    {
        this.Notifications = notifications;
        this.Context = context;
    }

    @Override
    public NotificationListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // create a new view
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.notification_list, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        if(this.Notifications.get(position).getTipoNotificacion().equals("Ataque")){
            holder.name.setText("Ataque - Llega en " + this.ObtenerHora(this.Notifications.get(position).getTime()) + " al planeta " + this.Notifications.get(position).getPlanetNameT() + " del jugador " + this.Notifications.get(position).getPlayerName() + ".");
            holder.name.setTextColor(Color.GREEN);
        } else if(this.Notifications.get(position).getTipoNotificacion().equals("Defensa")){
            holder.name.setText("Defensa - El ataque del jugador " + this.Notifications.get(position).getPlayerName() + " desde el planeta " + this.Notifications.get(position).getPlanetNameO() + " llega en " + this.ObtenerHora(this.Notifications.get(position).getTime()) + " al planeta " + this.Notifications.get(position).getPlanetNameT() + ".");
            holder.name.setTextColor(Color.RED);
        } else if(this.Notifications.get(position).getTipoNotificacion().equals("Colonización")){
            holder.name.setText("Colonización - Llega en " + this.ObtenerHora(this.Notifications.get(position).getTime()) + " al planeta " + this.Notifications.get(position).getPlanetNameT() + ".");
            holder.name.setTextColor(Color.YELLOW);
        } else {
            holder.name.setText("No hay notificaciones.");
            holder.name.setTextColor(Color.WHITE);
        }

        holder.itemView.setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    public int getItemCount() {
        return this.Notifications.size();
    }

    private String ObtenerHora(long arrivo) {
        String tiempoExtension = "";
        long time = arrivo - System.currentTimeMillis();

        long dias = TimeUnit.MILLISECONDS.toDays(time);
        long horas = TimeUnit.MILLISECONDS.toHours(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toDays(time));
        long minutos = TimeUnit.MILLISECONDS.toMinutes(time) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(time));
        long segundos = TimeUnit.MILLISECONDS.toSeconds(time) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(time));

        tiempoExtension += (dias > 0) ? String.valueOf(dias) + ((dias == 1) ? " día" : " días") + " : " : "";
        tiempoExtension += (horas > 0) ? String.valueOf(horas) + ((horas == 1) ? " hora" : " horas") + " : " : "";
        tiempoExtension += (minutos > 0) ? String.valueOf(minutos) + ((minutos == 1) ? " min" : " mins") + " : " : "";
        tiempoExtension += (segundos > 0) ? String.valueOf(segundos) + ((segundos == 1) ? " seg" : " segs") : "";

        tiempoExtension.trim();

        return(tiempoExtension);
    }
}