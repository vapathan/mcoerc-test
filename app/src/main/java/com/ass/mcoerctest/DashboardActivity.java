package com.ass.mcoerctest;

import static com.ass.mcoerctest.constants.Constants.STUDENT_KEY;

import android.content.Intent;
import android.os.Bundle;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ass.mcoerctest.Adapters.DashboardListAdapter;
import com.ass.mcoerctest.Adapters.TestListAdapter;
import com.ass.mcoerctest.models.Student;
import com.ass.mcoerctest.models.Test;
import com.ass.mcoerctest.network.NetworkUtil;
import com.ass.mcoerctest.repositories.TestRepository;
import com.ass.mcoerctest.services.RetrofitApi;
import com.ass.mcoerctest.services.RetrofitApiClient;
import com.ass.mcoerctest.utilities.AppExecutor;
import com.ass.mcoerctest.utilities.ui.UIHelper;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class DashboardActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private RecyclerView recyclerView;
    private TestListAdapter mTestListAdapter;

    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private Toolbar toolbar;
    private Menu menu;
    private TextView tvStudentName;

    private TestRepository mTestRepository;


    private List<Test> mTestList;
    private ProgressBar mProgressBar;

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


    }

    @Override
    protected void onRestart() {
        super.onRestart();
        navigationView.setCheckedItem(R.id.nav_home);
    }

    private void initGui() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setTitle(R.string.tests);

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

        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

    }

    private void loadTests() {

        AppExecutor.getInstance().dbExecutor().execute(new Runnable() {
            @Override
            public void run() {


                if (NetworkUtil.hasNetworkAccess(DashboardActivity.this)) {
                    mTestListAdapter = new TestListAdapter(DashboardActivity.this, mTestList);
                    mTestRepository.getTestList(mStudent.getPrn(), recyclerView, mTestListAdapter, mProgressBar);
                }
                UIHelper.showProgressBar(mProgressBar);
                mTestList = mTestRepository.getTests();
                mTestListAdapter = new TestListAdapter(DashboardActivity.this, mTestList);
                recyclerView.setAdapter(mTestListAdapter);
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
    }
}