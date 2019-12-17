package com.mortuza.report.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        mUserlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, ReportActivity.class));
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
    }
}





