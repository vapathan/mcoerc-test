package com.ass.mcoerctest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.ass.mcoerctest.Adapters.TestDashboardListAdapter;
import com.ass.mcoerctest.models.Student;
import com.ass.mcoerctest.models.Subject;
import com.ass.mcoerctest.network.NetworkUtil;
import com.ass.mcoerctest.repositories.SubjectRepository;
import com.ass.mcoerctest.repositories.TestRepository;
import com.ass.mcoerctest.utilities.AppExecutor;
import com.ass.mcoerctest.utilities.ui.UIHelper;

import java.util.List;

import static com.ass.mcoerctest.constants.Constants.STUDENT_KEY;

public class TestsDashboardActivity extends AppCompatActivity {

    private SubjectRepository mSubjectRepository;
    private ProgressBar mProgressBar;
    private RecyclerView recyclerView;

    private List<Subject> mSubjectList;
    private Student mStudent;
    private TestDashboardListAdapter mTestDashboardListAdapter;
    private TestRepository mTestRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tests_dashboard);

        getSupportActionBar().setTitle(R.string.tests);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        mStudent = intent.getParcelableExtra(STUDENT_KEY);

        mSubjectRepository = SubjectRepository.getInstance(this);
        mTestRepository = TestRepository.getInstance(this);

        initGui();

        if (NetworkUtil.hasNetworkAccess(getApplicationContext())) {
            mTestRepository.getTestList(mProgressBar);
        }

        loadSubjects();
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void initGui() {

        mProgressBar = (ProgressBar) findViewById(R.id.progressbar);
        recyclerView = findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
    }

    private void loadSubjects() {
        AppExecutor.getInstance().dbExecutor().execute(new Runnable() {
            @Override
            public void run() {
                UIHelper.showProgressBar(mProgressBar);

                mSubjectList = mSubjectRepository.getSubjects();

                mTestDashboardListAdapter = new TestDashboardListAdapter(TestsDashboardActivity.this, mSubjectList);
                recyclerView.setAdapter(mTestDashboardListAdapter);

                mTestDashboardListAdapter.notifyDataSetChanged();

                UIHelper.hideProgressBar(mProgressBar);
            }
        });
    }

}