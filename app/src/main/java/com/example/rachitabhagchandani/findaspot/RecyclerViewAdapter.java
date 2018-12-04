package com.example.rachitabhagchandani.findaspot;

import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    ArrayList<ParkingLocations> mDataset = new ArrayList<ParkingLocations>();
    Context mContext;

    public RecyclerViewAdapter(ArrayList<ParkingLocations> dataSet, Context mcontext) {
        this.mDataset = dataSet;
        this.mContext = mcontext;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        CardView cardView;
        TextView l_name;
        TextView l_price;
        TextView l_capacity;
        TextView l_address;
        Button button;
        IMyViewHolderClicks mListener;

        ViewHolder(View itemView, IMyViewHolderClicks mListener){
            super(itemView);
            this.mListener = mListener;
            cardView = (CardView)itemView.findViewById(R.id.card_view);
            l_name = (TextView)itemView.findViewById(R.id.location_name);
            l_price = (TextView)itemView.findViewById(R.id.location_price);
            l_capacity = (TextView)itemView.findViewById(R.id.capacity);
            l_address = (TextView)itemView.findViewById(R.id.location_address);
            button = (Button)itemView.findViewById(R.id.book_button);
            button.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
                mListener.itemClicked(v, getAdapterPosition());
        }

        public static interface IMyViewHolderClicks {
            public void itemClicked(View view, int position);
        }

    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.custom_layout, parent, false);
        ViewHolder vh = new ViewHolder(v, new RecyclerViewAdapter.ViewHolder.IMyViewHolderClicks() {
            public void itemClicked(View v, int pos) {
                Log.d("log", "click");
                DisplayParkingList displayParkingList = (DisplayParkingList) mContext;
                displayParkingList.openParkingSlotWindow(pos);
            }
        });
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.l_name.setText(mDataset.get(position).address);
        holder.l_price.setText("Rs." + String.valueOf(mDataset.get(position).charges_car));
        holder.l_capacity.setText("Capacity :" + String.valueOf(mDataset.get(position).capacity_car));
        holder.l_address.setText(String.valueOf(mDataset.get(position).city));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
