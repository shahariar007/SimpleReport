package com.mortuza.report.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.sprite.Sprite;
import com.github.ybq.android.spinkit.style.CubeGrid;
import com.github.ybq.android.spinkit.style.DoubleBounce;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mortuza.report.R;

public class MainActivity extends AppCompatActivity {

    private TextView mShowtexts;
    private TextInputEditText mUserid;
    private TextInputLayout mUserlayout;
    private TextInputEditText mPassword;
    private TextInputLayout mPasswordlayout;
    private CheckBox mRemember;
    private Button mUserlogin;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        CubeGrid doubleBounce = new CubeGrid();
        progressBar.setIndeterminateDrawable(doubleBounce);

        mUserlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(MainActivity.this, ReportActivity.class));
                progressBar.setVisibility(View.VISIBLE);
            }
        });
    }


    private void initViews() {
        mShowtexts = findViewById(R.id.showTexts);
        mUserid = findViewById(R.id.userID);
        mUserlayout = findViewById(R.id.userLayout);
        mPassword = findViewById(R.id.password);
        mPasswordlayout = findViewById(R.id.passwordLayout);
        mRemember = findViewById(R.id.remember);
        mUserlogin = findViewById(R.id.userLogin);
        progressBar = (ProgressBar) findViewById(R.id.progress);
    }
}





