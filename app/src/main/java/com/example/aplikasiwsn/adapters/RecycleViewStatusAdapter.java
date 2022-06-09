package com.example.aplikasiwsn.adapters;
import android.annotation.SuppressLint;
import android.content.Context;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasiwsn.R;
import com.example.aplikasiwsn.models.NodeSensor;
import com.example.aplikasiwsn.models.NodeSensorStatus;

import java.util.ArrayList;

public class RecycleViewStatusAdapter extends RecyclerView.Adapter<RecycleViewStatusAdapter.ViewHolder> {
    private ArrayList<NodeSensorStatus> nodeArrayListStatusData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;
    private Context context;

    // data is passed into the constructor
    public RecycleViewStatusAdapter(Context context, ArrayList<NodeSensorStatus> nodeArrayListStatusData) {
        this.context = context;
        this.mInflater = LayoutInflater.from(context);
        this.nodeArrayListStatusData = nodeArrayListStatusData;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycleview_status_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String statusNode = nodeArrayListStatusData.get(position).getStatus_node();
        String statusSensingNode = nodeArrayListStatusData.get(position).getStatus_sensing();

        if (statusNode.equalsIgnoreCase("Online")) {
            holder.tv_rvstatus_status_text.setTextColor(context.getResources().getColor(R.color.lime));
        }
        else {
            holder.tv_rvstatus_status_text.setTextColor(context.getResources().getColor(R.color.red));
        }
        if (statusSensingNode.equalsIgnoreCase("Online")) {
            holder.tv_rvstatus_statussensing_text.setTextColor(context.getResources().getColor(R.color.lime));
        }
        else {
            holder.tv_rvstatus_statussensing_text.setTextColor(context.getResources().getColor(R.color.red));
        }
        holder.tv_rvstatus_statussensing_text.setText(nodeArrayListStatusData.get(position).getStatus_sensing());

        holder.img_status.setImageResource(R.drawable.tes_icon);
        holder.tv_rvstatus_name.setText(nodeArrayListStatusData.get(position).getNama_node());
        holder.tv_rvstatus_status_text.setText(nodeArrayListStatusData.get(position).getStatus_node());
        holder.tv_rvstatus_statussensing_text.setText(nodeArrayListStatusData.get(position).getStatus_sensing());
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return nodeArrayListStatusData.size();
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView img_status;
        TextView tv_rvstatus_name;
        TextView tv_rvstatus_status_text;
        TextView tv_rvstatus_statussensing_text;
        ConstraintLayout clStatus;

        ViewHolder(View itemView) {
            super(itemView);
            img_status = itemView.findViewById(R.id.img_status);
            tv_rvstatus_name = itemView.findViewById(R.id.tv_rvstatus_name);
            tv_rvstatus_status_text = itemView.findViewById(R.id.tv_rvstatus_status_text);
            tv_rvstatus_statussensing_text = itemView.findViewById(R.id.tv_rvstatus_statussensing_text);
            clStatus = itemView.findViewById(R.id.cl_status);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // allows clicks events to be caught
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
