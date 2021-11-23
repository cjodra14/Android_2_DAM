package com.jodra.taranis;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class AdaptadorRecyclerTiempo extends RecyclerView.Adapter<AdaptadorRecyclerTiempo.ViewHolder> {

    private Context context;
    private ArrayList<TiempoRecyclerContenedor> arrayListTiempoContenedor;

    public AdaptadorRecyclerTiempo(Context context, ArrayList<TiempoRecyclerContenedor> arrayListTiempoContenedor) {
        this.context = context;
        this.arrayListTiempoContenedor = arrayListTiempoContenedor;
    }

    @NonNull
    @Override
    public AdaptadorRecyclerTiempo.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View vista = LayoutInflater.from(context).inflate(R.layout.tiempo_hora_recycler,parent,false);

        return new ViewHolder(vista);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        TiempoRecyclerContenedor contenedor= arrayListTiempoContenedor.get(position);

        Picasso.with(context).load("http:".concat(contenedor.getImagen())).into(holder.imgTiempoHora);
        holder.txtTiempoHora.setText(contenedor.getTemperatura()+" ºC");
        holder.txtVientoHora.setText(contenedor.getVelocidadDelViento() + " Km/h");
        SimpleDateFormat respuestaHora = new SimpleDateFormat("yyy-MM-dd HH:mm");
        SimpleDateFormat salidaHora = new SimpleDateFormat("HH:mm ");
        try {
            Date fecha_hora = respuestaHora.parse(contenedor.getHora());
            holder.txtHoraCarta.setText(salidaHora.format(fecha_hora));
        }catch (ParseException e){
                System.err.println("Error al parsear la Hora");
                e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return arrayListTiempoContenedor.size();
    }
    protected class ViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTiempoHora, txtVientoHora, txtHoraCarta;
        private ImageView imgTiempoHora;

        protected ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTiempoHora = itemView.findViewById(R.id.txtHoraCarta);
            txtVientoHora = itemView.findViewById(R.id.txtVientoCarta);
            txtHoraCarta = itemView.findViewById(R.id.txtTempCarta);
            imgTiempoHora = itemView.findViewById(R.id.imgCartaCondicion);
        }
    }
}
