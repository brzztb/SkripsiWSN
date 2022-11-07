package com.example.aplikasiwsn.adapters;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.aplikasiwsn.R;
import com.example.aplikasiwsn.activities.ChartMenuActivity;
import com.example.aplikasiwsn.activities.HistoryActivity;
import com.example.aplikasiwsn.activities.LoginActivity;
import com.example.aplikasiwsn.activities.MyCropActivity;
import com.example.aplikasiwsn.activities.SensingActivity;
import com.example.aplikasiwsn.activities.StatusActivity;
import com.example.aplikasiwsn.applications.CredentialSharedPreferences;

public class RecycleViewMenuAdapter extends RecyclerView.Adapter<RecycleViewMenuAdapter.ViewHolder> {
    private String[] mData;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public RecycleViewMenuAdapter(Context context, String[] data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    // inflates the cell layout from xml when needed
    @Override
    @NonNull
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.recycleview_menu_item, parent, false);
        return new ViewHolder(view);
    }

    // binds the data to the TextView in each cell
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.rvMenuName.setText(mData[position]);
        holder.clMenu.setOnClickListener(view -> {
            Context context = view.getContext();
            Intent intent;
            switch (position) {
                case 0:
                    intent = new Intent(context, StatusActivity.class);
                    context.startActivity(intent);
                    break;
                case 1:
                    intent = new Intent(context, SensingActivity.class);
                    context.startActivity(intent);
                    break;
                case 2:
                    intent = new Intent(context, HistoryActivity.class);
                    context.startActivity(intent);
                    break;
                case 3:
                    intent = new Intent(context, MyCropActivity.class);
                    context.startActivity(intent);
                    break;
                case 4:
                    intent = new Intent(context, ChartMenuActivity.class);
                    context.startActivity(intent);
                    break;
                case 5:
                    CredentialSharedPreferences cred = new CredentialSharedPreferences(context);
                    cred.clearCredential();
                    intent = new Intent(context, LoginActivity.class);
                    context.startActivity(intent);
                    Activity activity = (Activity)context;
                    activity.finish();
            }
        });
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return mData.length;
    }


    // stores and recycles views as they are scrolled off screen
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView rvMenuName;
        ConstraintLayout clMenu;

        ViewHolder(View itemView) {
            super(itemView);
            rvMenuName = itemView.findViewById(R.id.tv_rvmenu_name);
            clMenu = itemView.findViewById(R.id.cl_menu);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
        }
    }

    // convenience method for getting data at click position
    public String getItem(int id) {
        return mData[id];
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
