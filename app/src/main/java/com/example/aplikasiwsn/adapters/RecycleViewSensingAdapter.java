package com.example.aplikasiwsn.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasiwsn.R;
import com.example.aplikasiwsn.models.NodeSensor;

import java.util.ArrayList;

public class RecycleViewSensingAdapter extends RecyclerView.Adapter<RecycleViewSensingAdapter.ViewHolder> {
    private ArrayList<NodeSensor> nodes;
    private LayoutInflater mInflater;
    private RecycleViewSensingAdapter.ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    public RecycleViewSensingAdapter(Context context, ArrayList<NodeSensor> nodes) {
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
        holder.img_sensing.setImageResource(nodes.get(position).getImageId());
        holder.tv_rvsensing_name.setText(nodes.get(position).getName());
        holder.tv_rvsensing_keasaman_text.setText(nodes.get(position).getKeasaman());
        holder.tv_rvsensing_kelembaban_text.setText(nodes.get(position).getKelembaban());
        holder.tv_rvsensing_tanah_text.setText(nodes.get(position).getSuhuTanah());
        holder.tv_rvsensing_udara_text.setText(nodes.get(position).getSuhuUdara());
        holder.tv_rvsensing_waktu_text.setText(nodes.get(position).getWaktu());
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
}
