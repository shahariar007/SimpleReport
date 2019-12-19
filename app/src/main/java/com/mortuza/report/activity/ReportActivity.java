package com.mortuza.report.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.ybq.android.spinkit.style.CubeGrid;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.mortuza.report.Network.APIClient;
import com.mortuza.report.Network.APIServices;
import com.mortuza.report.R;
import com.mortuza.report.adapter.Adapters;
import com.mortuza.report.model.report.Datum;
import com.mortuza.report.model.report.UserReport;
import com.mortuza.report.utills.CallBack;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ReportActivity extends AppCompatActivity implements CallBack {

    private Toolbar mToolbar;
    private RecyclerView mReportrecycle;
    private FloatingActionButton mAdd;
    private ProgressBar progressBar;
    private APIServices apiServices;
    String idUser;
    List<Datum> datumList;
    private Adapters adapters;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report);
        initViews();
        progressBar.setVisibility(View.GONE);
        apiServices = APIClient.getInstance().create(APIServices.class);

        mToolbar.setTitle("Report");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);
        mAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ReportActivity.this, CreateReportActivity.class));

            }
        });

        CubeGrid doubleBounce = new CubeGrid();
        progressBar.setIndeterminateDrawable(doubleBounce);


        int resId = R.anim.layout_anim;
        LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(this, resId);


        mReportrecycle.setLayoutManager(new LinearLayoutManager(this));
        adapters = new Adapters(this, this);
        mReportrecycle.setLayoutAnimation(animation);
        mReportrecycle.setAdapter(adapters);
        mReportrecycle.scheduleLayoutAnimation();

        idUser = PreferenceManager.getDefaultSharedPreferences(ReportActivity.this).getString("ID", "");
        if (idUser != null && !idUser.isEmpty()) {
            serverCall(idUser);
        } else
            Toast.makeText(ReportActivity.this, "Please login again", Toast.LENGTH_LONG).show();


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.sync) {
            serverCall(idUser);
        } else if (id == R.id.status) {

            Toast.makeText(this, "status", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    private void initViews() {
        mToolbar = findViewById(R.id.toolbar);
        mReportrecycle = findViewById(R.id.reportRecycle);
        mAdd = findViewById(R.id.add);
        progressBar = (ProgressBar) findViewById(R.id.progress);

    }

    public void serverCall(final String userID) {
        progressBar.setVisibility(View.VISIBLE);
        datumList = new ArrayList<>();
        Call<UserReport> reportConfirmationCall = apiServices.getReport(userID);
        reportConfirmationCall.enqueue(new Callback<UserReport>() {
            @Override
            public void onResponse(Call<UserReport> call, Response<UserReport> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getStatus()) {
                    if (response.body().getData().size() > 0) {
                        datumList.addAll(response.body().getData());
                        if (adapters != null) {
                            adapters.setDatumList(datumList);
                            adapters.notifyDataSetChanged();
                        }
                    } else {
                        Toast.makeText(ReportActivity.this, "No report found.Please add report", Toast.LENGTH_LONG).show();
                    }
                } else {
                    Toast.makeText(ReportActivity.this, "Can't get report.Please try again", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<UserReport> call, Throwable t) {
                Toast.makeText(ReportActivity.this, "Network connection error or Time out", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    @Override
    public void now(int position) {
        if (datumList != null && datumList.size() > 0) {
            Intent intent = new Intent(ReportActivity.this, CreateReportActivity.class);
            intent.putExtra("title", datumList.get(position).getTitle());
            intent.putExtra("description", datumList.get(position).getDescription());
            intent.putExtra("id", datumList.get(position).getId());
            String d = datumList.get(position).getStatus();

            if (d.equalsIgnoreCase("0")) //pending
            {
                intent.putExtra("status", "Pending");
            } else if (d.equalsIgnoreCase("1")) //seen
            {
                intent.putExtra("status", "Seen");
            } else if (d.equalsIgnoreCase("2")) {//solved
                intent.putExtra("status", "Solved");
            } else if (d.equalsIgnoreCase("3")) // not solved
            {
                intent.putExtra("status", "Not solved");
            }
        startActivity(intent);
    } else

    {
        Toast.makeText(this, "Error on position", Toast.LENGTH_SHORT).show();
    }
}
}
