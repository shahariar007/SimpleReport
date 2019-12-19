package com.mortuza.report.activity;

import android.content.DialogInterface;
import android.graphics.Color;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.github.ybq.android.spinkit.style.CubeGrid;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mortuza.report.Network.APIClient;
import com.mortuza.report.Network.APIServices;
import com.mortuza.report.R;
import com.mortuza.report.model.report.ReportConfirmation;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CreateReportActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextInputEditText mReporttitle;
    private TextInputLayout mReportlayout;
    private TextInputEditText mReportdetail;
    private TextInputLayout mReportdetaillayout;
    private Button mSubmit;
    private TextView mTextcount;
    private ProgressBar progressBar;
    private APIServices apiServices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);
        initViews();
        //EditText scroller enable
        mReportdetail.setScroller(new Scroller(this));
        mReportdetail.setVerticalScrollBarEnabled(true);
        mReportdetail.setMovementMethod(new ScrollingMovementMethod());
        progressBar.setVisibility(View.GONE);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String title = bundle.getString("title");
            String description = bundle.getString("description");
            String id = bundle.getString("id");
            String status = bundle.getString("status");
            mReporttitle.setText(title);
            mReporttitle.setEnabled(false);

            mReportdetail.setText(description);
            mReportdetail.setEnabled(false);
            mSubmit.setVisibility(View.GONE);
            mToolbar.setTitle("Report ID:"+id +" [" +status+ "]" );
            mToolbar.setTitleTextColor(Color.WHITE);
            setSupportActionBar(mToolbar);
            return;

        } else {
            mReporttitle.setEnabled(true);
            mReporttitle.setSelection(0);
            mReportdetail.setEnabled(true);
            mReportdetail.setSelection(0);
            mReportdetail.setGravity(Gravity.TOP);

            mSubmit.setVisibility(View.VISIBLE);
            mToolbar.setTitle("Create Report");
            mToolbar.setTitleTextColor(Color.WHITE);
            setSupportActionBar(mToolbar);
        }


        CubeGrid doubleBounce = new CubeGrid();
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.GONE);
        apiServices = APIClient.getInstance().create(APIServices.class);
        mReporttitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                mTextcount.setText(s.length() + "/150");
            }
        });


        mSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = mReporttitle.getText().toString();
                String description = mReportdetail.getText().toString();

                if (!mReporttitle.getText().toString().isEmpty() && !mReportdetail.getText().toString().isEmpty()) {
                    String id = PreferenceManager.getDefaultSharedPreferences(CreateReportActivity.this).getString("ID", "");
                    if (id != null && !id.isEmpty()) {
                        serverCall(id, title, description);
                    } else
                        Toast.makeText(CreateReportActivity.this, "Please login again", Toast.LENGTH_LONG).show();

                } else {
                    Toast.makeText(CreateReportActivity.this, "Report title and description can't be empty", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void initViews() {
        mToolbar = findViewById(R.id.toolbar);
        mReporttitle = findViewById(R.id.reportTitle);
        mReportlayout = findViewById(R.id.reportLayout);
        mReportdetail = findViewById(R.id.reportDetail);
        mReportdetaillayout = findViewById(R.id.reportDetailLayout);
        mSubmit = findViewById(R.id.submit);
        mTextcount = findViewById(R.id.textCount);
        progressBar = findViewById(R.id.progress);
    }

    public void serverCall(final String userID, final String title, final String reportDescription) {
        progressBar.setVisibility(View.VISIBLE);
        Call<ReportConfirmation> reportConfirmationCall = apiServices.reportCreate(userID, title, reportDescription);
        reportConfirmationCall.enqueue(new Callback<ReportConfirmation>() {
            @Override
            public void onResponse(Call<ReportConfirmation> call, Response<ReportConfirmation> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getStatus()) {
                    alertDialog(response.body().getReportId());
                } else {
                    Toast.makeText(CreateReportActivity.this, "Can't send report.Please try again", Toast.LENGTH_LONG).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<ReportConfirmation> call, Throwable t) {
                Toast.makeText(CreateReportActivity.this, "Network connection error or Time out", Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });
    }

    public void alertDialog(int reportID) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateReportActivity.this);
        builder.setTitle("Report Status");
        builder.setCancelable(false);
        builder.setMessage("Your Report submit successfully. Your report ID is: " + reportID);
        builder.setPositiveButton("Done", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });
        builder.show();
    }
}
