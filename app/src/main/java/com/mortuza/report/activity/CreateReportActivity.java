package com.mortuza.report.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.Scroller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mortuza.report.R;

public class CreateReportActivity extends AppCompatActivity {

    private Toolbar mToolbar;
    private TextInputEditText mReporttitle;
    private TextInputLayout mReportlayout;
    private TextInputEditText mReportdetail;
    private TextInputLayout mReportdetaillayout;
    private Button mSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_report);
        initViews();
        mToolbar.setTitle("Create Report");
        mToolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(mToolbar);

        //EditText scroller enable
        mReportdetail.setScroller(new Scroller(this));
        mReportdetail.setVerticalScrollBarEnabled(true);
        mReportdetail.setMovementMethod(new ScrollingMovementMethod());
    }

    private void initViews() {
        mToolbar = findViewById(R.id.toolbar);
        mReporttitle = findViewById(R.id.reportTitle);
        mReportlayout = findViewById(R.id.reportLayout);
        mReportdetail = findViewById(R.id.reportDetail);
        mReportdetaillayout = findViewById(R.id.reportDetailLayout);
        mSubmit = findViewById(R.id.submit);
    }
}
