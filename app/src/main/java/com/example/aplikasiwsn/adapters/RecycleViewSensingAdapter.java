package com.example.aplikasiwsn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasiwsn.R;
import com.example.aplikasiwsn.models.NodeSensor;
import com.example.aplikasiwsn.models.Tanah;

import java.util.ArrayList;

public class RecycleViewSensingAdapter extends RecyclerView.Adapter<RecycleViewSensingAdapter.ViewHolder> {
    private ArrayList<Tanah> nodes;
    private LayoutInflater mInflater;
    private RecycleViewSensingAdapter.ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    public RecycleViewSensingAdapter(Context context, ArrayList<Tanah> nodes) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.nodes = nodes;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public RecycleViewSensingAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycleview_sensing_item, parent, false);
        return new RecycleViewSensingAdapter.ViewHolder(view);
    }

    // binds the data to the TextView in each cell

    @Override
    public void onBindViewHolder(@NonNull RecycleViewSensingAdapter.ViewHolder holder, int position) {
        if (nodes.get(position).getKode_petak().equalsIgnoreCase("1")) {
            holder.img_sensing.setImageResource(R.drawable.back_icon);
        }
        else if (nodes.get(position).getKode_petak().equalsIgnoreCase("2")){
            holder.img_sensing.setImageResource(R.drawable.tes_icon);
        }
        else if (nodes.get(position).getKode_petak().equalsIgnoreCase("3")){
            holder.img_sensing.setImageResource(R.drawable.back_icon);
        }

        String namaNode = "Node " + nodes.get(position).getKode_petak();
        String keasaman = nodes.get(position).getPh_tanah();
        String kelembabanTanah = nodes.get(position).getKelembaban_tanah();
        String suhuTanah = nodes.get(position).getSuhu_tanah();
        String suhuUdara = nodes.get(position).getSuhu_udara();
        String waktu = nodes.get(position).getWaktu_sensing();
        String kelembabanUdara = nodes.get(position).getKelembaban_udara();

        holder.tv_rvsensing_name.setText(namaNode);
        holder.tv_rvsensing_keasaman_text.setText(keasaman);
        holder.tv_rvsensing_kelembaban_text.setText(kelembabanTanah);
        holder.tv_rvsensing_tanah_text.setText(suhuTanah);
        holder.tv_rvsensing_udara_text.setText(suhuUdara);
        holder.tv_rvsensing_kelembaban_udara_text.setText(kelembabanUdara);
        holder.tv_rvsensing_waktu_text.setText(waktu);
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return nodes.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img_sensing;
        TextView tv_rvsensing_name;
        TextView tv_rvsensing_keasaman_text;
        TextView tv_rvsensing_kelembaban_text;
        TextView tv_rvsensing_tanah_text;
        TextView tv_rvsensing_udara_text;
        TextView tv_rvsensing_waktu_text;
        TextView tv_rvsensing_kelembaban_udara_text;
        ConstraintLayout clSensing;

        ViewHolder(View itemView) {
            super(itemView);
            img_sensing= itemView.findViewById(R.id.img_sensing);
            tv_rvsensing_name = itemView.findViewById(R.id.tv_rvsensing_name);
            tv_rvsensing_keasaman_text = itemView.findViewById(R.id.tv_rvsensing_keasaman_text);
            tv_rvsensing_kelembaban_text = itemView.findViewById(R.id.tv_rvsensing_kelembaban_text);
            tv_rvsensing_tanah_text = itemView.findViewById(R.id.tv_rvsensing_tanah_text);
            tv_rvsensing_udara_text = itemView.findViewById(R.id.tv_rvsensing_udara_text);
            tv_rvsensing_waktu_text = itemView.findViewById(R.id.tv_rvsensing_waktu_text);
            tv_rvsensing_kelembaban_udara_text = itemView.findViewById(R.id.tv_rvsensing_kelembaban_udara_text);
            clSensing = itemView.findViewById(R.id.cl_sensing);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // allows clicks events to be caught
    public void setClickListener(RecycleViewSensingAdapter.ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }

    public void changeData(ArrayList<Tanah> tanah) {
        this.nodes = tanah;
        notifyDataSetChanged();
    }
}
