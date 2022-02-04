package com.ass.mcoerctest;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.ass.mcoerctest.Adapters.DashboardListAdapter;
import com.ass.mcoerctest.constants.Api;
import com.ass.mcoerctest.models.Notification;
import com.ass.mcoerctest.models.Student;
import com.ass.mcoerctest.models.Test;
import com.ass.mcoerctest.network.NetworkUtil;
import com.ass.mcoerctest.repositories.ChapterRepository;
import com.ass.mcoerctest.repositories.SubjectRepository;
import com.ass.mcoerctest.repositories.TestRepository;
import com.ass.mcoerctest.services.RetrofitApi;
import com.ass.mcoerctest.services.RetrofitApiClient;
import com.ass.mcoerctest.utilities.AppExecutor;
import com.ass.mcoerctest.utilities.ui.UIHelper;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.ass.mcoerctest.constants.Constants.STUDENT_KEY;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private DashboardListAdapter dashboardListAdapter;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Menu menu;
    private TextView tvStudentName;

    private TestRepository mTestRepository;


    private List<Test> mTestList;
    private ProgressBar mProgressBar;

    private ViewPagerCarouselView viewPagerCarouselView;
    private Student mStudent;
    private RetrofitApi mRetrofitApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Intent intent = getIntent();
        mStudent = intent.getParcelableExtra(STUDENT_KEY);

        mTestRepository = TestRepository.getInstance(this);

        initGui();

        mRetrofitApi = RetrofitApiClient.getInstance().getRetrofitApi();

        loadTests();

        long carouselSlideInterval = 4000; // 4 SECONDS
        if (NetworkUtil.hasNetworkAccess(getApplicationContext())) {
            AppExecutor.getInstance().dbExecutor().execute(() -> {

                //Get Notifications from remote server
                final List<Notification>[] notificationList = new List[]{new ArrayList<>()};

                Call<Notification[]> call = mRetrofitApi.getNotifications(Api.API_KEY);
                call.enqueue(new Callback<Notification[]>() {
                    @Override
                    public void onResponse(Call<Notification[]> call, Response<Notification[]> response) {
                        if (response.body() != null) {
                            notificationList[0] = Arrays.asList(response.body());
                            viewPagerCarouselView.setData(getSupportFragmentManager(), notificationList[0], carouselSlideInterval);
                        }

                    }

                    @Override
                    public void onFailure(Call<Notification[]> call, Throwable t) {
                        notificationList[0] = null;
                        Log.i("INFO", t.getMessage());
                    }
                });

            });
        }

        /*List<Notification> notificationList = new ArrayList<>();
        Notification notification = new Notification();
        notification.setId(1);
        notification.setTitle("Notice");
        notification.setDetails("Detail");
        notificationList.add(notification);
        viewPagerCarouselView.setData(getSupportFragmentManager(), notificationList, carouselSlideInterval);*/

    }

    @Override
    protected void onRestart() {
        super.onRestart();
        navigationView.setCheckedItem(R.id.nav_home);
    }

    private void initGui() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.menu_question_bank);

        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.nav_view);
        View header = navigationView.getHeaderView(0);
        tvStudentName = header.findViewById(R.id.tv_student_name);
        tvStudentName.setText(mStudent.getName());

        navigationView.bringToFront();
        ActionBarDrawerToggle toggle = new
                ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);

        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        viewPagerCarouselView = (ViewPagerCarouselView) findViewById(R.id.carousel_view);


    }

    private void loadTests() {

        if (NetworkUtil.hasNetworkAccess(this)) {
            dashboardListAdapter = new DashboardListAdapter(DashboardActivity.this, mTestList);
            mTestRepository.getTestList(mStudent.getPrn(), recyclerView, dashboardListAdapter, mProgressBar);
        }
        AppExecutor.getInstance().dbExecutor().execute(new Runnable() {
            @Override
            public void run() {
                UIHelper.showProgressBar(mProgressBar);
                mTestList = mTestRepository.getTests();
                dashboardListAdapter = new DashboardListAdapter(DashboardActivity.this, mTestList);
                recyclerView.setAdapter(dashboardListAdapter);
                UIHelper.hideProgressBar(mProgressBar);
            }
        });

    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                break;
            case R.id.nav_tests:
                Intent intent = new Intent(this, TestListActivity.class);
                intent.putExtra(STUDENT_KEY, mStudent);
                startActivity(intent);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        viewPagerCarouselView.onDestroy();
    }
}