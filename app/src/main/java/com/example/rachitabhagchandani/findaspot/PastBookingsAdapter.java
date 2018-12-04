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

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PastBookingsAdapter extends RecyclerView.Adapter<PastBookingsAdapter.ViewHolder> {

    ArrayList<BookSlotFirebase> mDataset = new ArrayList<BookSlotFirebase>();
    Context mContext;

    public PastBookingsAdapter(ArrayList<BookSlotFirebase> dataSet, Context mcontext) {
        this.mDataset = dataSet;
        this.mContext = mcontext;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        CardView past_bookings_card_view;
        TextView vehicle_id;
        TextView location;
        TextView date;
        TextView entry_time;
        TextView exit_time;
        TextView amount_paid;

        ViewHolder(View itemView){
            super(itemView);
            past_bookings_card_view = (CardView)itemView.findViewById(R.id.past_bookings_card_view);
            vehicle_id = (TextView)itemView.findViewById(R.id.vehicle_id);
            location = (TextView)itemView.findViewById(R.id.location);
            date = (TextView)itemView.findViewById(R.id.date);
            entry_time = (TextView)itemView.findViewById(R.id.entry_time);
            exit_time = (TextView)itemView.findViewById(R.id.exit_time);
            amount_paid = (TextView)itemView.findViewById(R.id.amount_paid);
        }

    }
    @Override
    public PastBookingsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_bookings, parent, false);
        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Log.d("abcd",mDataset.get(position).getVehicle_number());
        holder.vehicle_id.setText(mDataset.get(position).getVehicle_number());
        holder.location.setText(mDataset.get(position).getLocation());

        //SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        //String datestr = formatter.format(mDataset.get(position).getBooking_date());

        holder.date.setText(mDataset.get(position).getBooking_date());
        holder.entry_time.setText(mDataset.get(position).getArrival_time());
        holder.exit_time.setText(mDataset.get(position).getLeaving_time());
        holder.amount_paid.setText(Float.toString(mDataset.get(position).getAmount_paid()));
    }

    @Override
    public int getItemCount() {
        return mDataset.size();
    }
}
