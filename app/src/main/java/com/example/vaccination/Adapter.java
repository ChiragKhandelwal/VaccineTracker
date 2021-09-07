package com.example.vaccination;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.ViewHolder> {
    private ArrayList<CentreModel> list;

    public Adapter(ArrayList<CentreModel> list) {
        this.list = list;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCentrName,tvCentreAddress,tvTiming,tvVaccineName,tvFee,tvAge,tvAvailability;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCentrName=itemView.findViewById(R.id.idTVCenterName);
            tvCentreAddress=itemView.findViewById(R.id.idTVCenterAddress);
            tvTiming=itemView.findViewById(R.id.idTVCenterTimings);
            tvVaccineName=itemView.findViewById(R.id.idTVVaccineName);
            tvFee=itemView.findViewById(R.id.idTVFeeType);
            tvAge=itemView.findViewById(R.id.idTVAgeLimit);
            tvAvailability=itemView.findViewById(R.id.idTVAvaliablity);
        }
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(parent.getContext()).inflate(R.layout.row,parent,false);

        return new ViewHolder(v);
       // return null;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
      CentreModel model=list.get(position);
      holder.tvCentrName.setText(model.centreName);
      holder.tvAvailability.setText(model.slotsRemaining);
      holder.tvAge.setText(model.ageLimit);
      holder.tvFee.setText(model.type);
      holder.tvVaccineName.setText(model.vaccineName);
      holder.tvCentreAddress.setText(model.centreAddress);
      holder.tvTiming.setText(model.startTime + " - " + model.endTime);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}
