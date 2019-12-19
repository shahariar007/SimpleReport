package com.mortuza.report.activity;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.github.ybq.android.spinkit.style.CubeGrid;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.mortuza.report.Network.APIClient;
import com.mortuza.report.Network.APIServices;
import com.mortuza.report.R;
import com.mortuza.report.model.user.UserLogin;

import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private TextView mShowtexts;
    private TextInputEditText mUserid;
    private TextInputLayout mUserlayout;
    private TextInputEditText mPassword;
    private TextInputLayout mPasswordlayout;
    private CheckBox mRemember;
    private Button mUserlogin;
    ProgressBar progressBar;
    APIServices apiServices;
    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        apiServices = APIClient.getInstance().create(APIServices.class);

        CubeGrid doubleBounce = new CubeGrid();
        progressBar.setIndeterminateDrawable(doubleBounce);
        progressBar.setVisibility(View.GONE);
        mUserlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               // startActivity(new Intent(MainActivity.this, ReportActivity.class));

                String password = mPassword.getText().toString();
                String userid = mUserid.getText().toString();

                if (!mPassword.getText().toString().isEmpty() && !mUserid.getText().toString().isEmpty()) {
                    serverCall(userid, password);
                } else {
                    Toast.makeText(MainActivity.this, "User name and password can't empty", Toast.LENGTH_SHORT).show();
                }
            }
        });

        if (PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getBoolean("REM", false)) {
            mPassword.setText(PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("PASS", ""));
            mUserid.setText(PreferenceManager.getDefaultSharedPreferences(MainActivity.this).getString("ID", ""));
            mRemember.setChecked(true);
        }
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

    public void serverCall(final String userID, final String password) {
        progressBar.setVisibility(View.VISIBLE);
        Call<UserLogin> userLoginCall = apiServices.login(userID,password);
        userLoginCall.enqueue(new Callback<UserLogin>() {
            @Override
            public void onResponse(Call<UserLogin> call, Response<UserLogin> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getStatus()) {
                    PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("ID", userID).apply();
                    PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putString("PASS", password).apply();

                    if (mRemember.isChecked())
                        PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putBoolean("REM", true).apply();
                    else {
                        PreferenceManager.getDefaultSharedPreferences(MainActivity.this).edit().putBoolean("REM", false).apply();
                    }
                    next();
                } else {
                    Toast.makeText(MainActivity.this, "User name and password wrong", Toast.LENGTH_SHORT).show();
                }
                progressBar.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<UserLogin> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Network connection error or Time out", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);
                Log.d(TAG, "onFailure: "+t.getMessage());
            }
        });
    }


    public void next() {
        Intent intent = new Intent(MainActivity.this, ReportActivity.class);
        startActivity(intent);
        finish();
    }
}





