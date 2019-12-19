package com.mortuza.report.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mortuza.report.R;
import com.mortuza.report.model.report.Datum;
import com.mortuza.report.utills.CallBack;

import java.util.ArrayList;
import java.util.List;

public class Adapters extends RecyclerView.Adapter<Adapters.ViewHolders> {
    Context context;
    List<Datum> datumList = new ArrayList<>();
    public CallBack callBack;

    public Adapters(Context context, CallBack callBack) {
        this.context = context;
        this.callBack = callBack;
    }

    public void setDatumList(List<Datum> datumList) {
        this.datumList = datumList;
    }

    @NonNull
    @Override
    public ViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolders(LayoutInflater.from(context).inflate(R.layout.report_raw, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolders holder, int position) {

        holder.mReportid.setText("Report ID: "+datumList.get(position).getId());
        holder.mReportdetails.setText("Report Title: \n"+datumList.get(position).getTitle());
        String d = datumList.get(position).getStatus();
        if (d.equalsIgnoreCase("0")) //pending
        {
            holder.reportStatus.setText("status:pending");
            holder.reportStatus.setTextColor(Color.BLACK);
        } else if (d.equalsIgnoreCase("1")) //seen
        {
            holder.reportStatus.setText("status:seen");
            holder.reportStatus.setTextColor(Color.BLUE);
        } else if (d.equalsIgnoreCase("2")) //solved
        {
            holder.reportStatus.setText("status:solved");
            holder.reportStatus.setTextColor(Color.GREEN);
        } else if (d.equalsIgnoreCase("3")) // not solved
        {
            holder.reportStatus.setText("status:not solved");
            holder.reportStatus.setTextColor(Color.RED);
        }


    }

    @Override
    public int getItemCount() {
        return datumList.size();
    }

    public class ViewHolders extends RecyclerView.ViewHolder {

        TextView mReportid;
        TextView mReportdetails;
        TextView reportStatus;
        LinearLayout detailsLayout;

        public ViewHolders(@NonNull View itemView) {
            super(itemView);
            this.mReportid = itemView.findViewById(R.id.reportID);
            this.mReportdetails = itemView.findViewById(R.id.reportDetails);
            this.reportStatus = itemView.findViewById(R.id.reportStatus);
            this.detailsLayout = itemView.findViewById(R.id.detailsLayout);
            detailsLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callBack != null) {
                        callBack.now(getAdapterPosition());
                    }else
                    {
                        Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
    }

}
