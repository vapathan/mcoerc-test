package com.ass.mcoerctest;

import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import com.ass.mcoerctest.models.Student;
import com.ass.mcoerctest.repositories.StudentRepository;
import com.ass.mcoerctest.utilities.AppExecutor;
import com.ass.mcoerctest.utilities.ImageHelper;
import com.ass.mcoerctest.utilities.PermissionHandler;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import static com.ass.mcoerctest.constants.Constants.STUDENT_KEY;


public class SplashActivity extends AppCompatActivity {
    private static int SPLASH_SCREEN_DELAY = 3000;
    private ImageView imageView;
    private TextView textView;
    private Animation topAnimation, bottomAnimation;

    private PermissionHandler mPermissionHandler;

    public static final int PERMISSION_REQUEST_CODE = 1240;
    private String[] appPermissions = {Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE};

    private StudentRepository mStudentRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mPermissionHandler = new PermissionHandler(this);
        mPermissionHandler.requestMultiplePermissions(appPermissions, PERMISSION_REQUEST_CODE);

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getSupportActionBar().hide();
        initGUi();

        mStudentRepository = StudentRepository.getInstance(this);

        SplashScreen();
    }

    private void initGUi() {

        imageView = findViewById(R.id.iv);
        textView = findViewById(R.id.tv);

        imageView.setImageBitmap(ImageHelper.getImageFromAssetsFile(this, "mcoerc.png"));

        topAnimation = AnimationUtils.loadAnimation(this, R.anim.top_anim);
        bottomAnimation = AnimationUtils.loadAnimation(this, R.anim.bottom_anim);

        imageView.setAnimation(topAnimation);
        textView.setAnimation(bottomAnimation);
    }

    private void SplashScreen() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {

                AppExecutor.getInstance().dbExecutor().execute(() -> {
                    Student student = mStudentRepository.getStudent();
                    if (student == null) {
                        // Log.i("TEST", student.getFirstName());
                        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                        startActivity(intent);
                        finish();
                        Animatoo.animateInAndOut(SplashActivity.this);
                    } else {
                        Intent intent = new Intent(getApplicationContext(), DashboardActivity.class);
                        intent.putExtra(STUDENT_KEY, student);
                        startActivity(intent);
                        finish();
                        Animatoo.animateInAndOut(SplashActivity.this);
                    }
                });
            }
        }, SPLASH_SCREEN_DELAY);
    }

}