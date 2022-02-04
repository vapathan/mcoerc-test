package com.ass.mcoerctest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.ass.mcoerctest.constants.Api;
import com.ass.mcoerctest.models.Student;
import com.ass.mcoerctest.repositories.StudentRepository;
import com.ass.mcoerctest.services.RetrofitApi;
import com.ass.mcoerctest.services.RetrofitApiClient;
import com.ass.mcoerctest.utilities.Validator;
import com.ass.mcoerctest.utilities.ui.UIHelper;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.facebook.stetho.Stetho;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ass.mcoerctest.constants.Constants.OTP_REQUEST;
import static com.ass.mcoerctest.constants.Constants.PHONE_KEY;
import static com.ass.mcoerctest.constants.Constants.STUDENT_KEY;
import static com.ass.mcoerctest.constants.Constants.VALID_USER_KEY;


public class LoginActivity extends AppCompatActivity {

    private EditText etMobile;
    private Button btnLogin;
    private ProgressBar progressBar;

    private String mobile;
    private RetrofitApi mRetrofitApi;
    private StudentRepository mStudentRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();

        RetrofitApiClient retrofitApiClient = RetrofitApiClient.getInstance();
        mRetrofitApi = retrofitApiClient.getRetrofitApi();
            mStudentRepository = StudentRepository.getInstance(this);
        initGUI();

        Stetho.initializeWithDefaults(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == OTP_REQUEST && resultCode == RESULT_OK) {
            boolean isValidUser = data.getBooleanExtra(VALID_USER_KEY, false);
            loginStudent();
        }
    }



    private void initGUI() {
        progressBar = (ProgressBar) findViewById(R.id.progressbar);
        etMobile = (EditText) findViewById(R.id.etMobile);
        btnLogin = (Button) findViewById(R.id.btn_login);

        btnLogin.setOnClickListener(view -> {
            if (Validator.isValidInteger(etMobile.getText().toString())) {
                mobile = etMobile.getText().toString();
               //verifyPhoneNumber("+91" + mobile);
                loginStudent();

            } else {
                etMobile.setError("Please enter valid 10 digit mobile");
            }
        });
    }

    private void verifyPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(this, PhoneAuthenticationActivity.class);
        intent.putExtra(PHONE_KEY, phoneNumber);
        startActivityForResult(intent, OTP_REQUEST);
    }

    private void loginStudent() {

        Call<Student> call = mRetrofitApi.getStudentByMobile(Api.API_KEY, mobile);
        UIHelper.showProgressBar(progressBar);
        call.enqueue(new Callback<Student>() {
            @Override
            public void onResponse(Call<Student> call, Response<Student> response) {
                if (response.isSuccessful()) {
                    Student student = response.body();
                    if (student != null) {
                        mStudentRepository.saveStudent(student);
                        Intent intent = new Intent(LoginActivity.this, DashboardActivity.class);
                        intent.putExtra(STUDENT_KEY, student);
                        finish();
                        startActivity(intent);
                    } else {
                        showError();
                    }
                } else {
                    showError();
                }
                UIHelper.hideProgressBar(progressBar);
            }
            @Override
            public void onFailure(Call<Student> call, Throwable t) {
                UIHelper.hideProgressBar(progressBar);
                showError();
            }
        });
    }

    private void showError() {
        Toast.makeText(LoginActivity.this, "Sorry, error occurred while login, please try again.", Toast.LENGTH_LONG).show();
    }

}