package com.ass.mcoerctest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ProgressBar;

import com.ass.mcoerctest.Adapters.TestListAdapter;
import com.ass.mcoerctest.database.AppDatabase;
import com.ass.mcoerctest.models.Student;
import com.ass.mcoerctest.models.Subject;
import com.ass.mcoerctest.models.Test;
import com.ass.mcoerctest.network.NetworkUtil;
import com.ass.mcoerctest.repositories.TestRepository;
import com.ass.mcoerctest.utilities.AppExecutor;
import com.ass.mcoerctest.utilities.ui.UIHelper;

import java.util.List;

import static com.ass.mcoerctest.constants.Constants.STUDENT_KEY;

public class TestListActivity extends AppCompatActivity {

    private RecyclerView rvTests;
    private ProgressBar mProgressBar;

    private TestListAdapter mTestListAdapter;
    private TestRepository mTestRepository;
    private Subject mSubject;
    private List<Test> mTestList;
    private AppDatabase mDb;
    private Student mStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_list);

        mTestRepository = TestRepository.getInstance(this);
        mDb = AppDatabase.getInstance(this);

        Intent intent = getIntent();
        mStudent = intent.getParcelableExtra(STUDENT_KEY);

        getSupportActionBar().setTitle("Tests");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        initGui();

        initRecyclerViews();

        if (NetworkUtil.hasNetworkAccess(getApplicationContext())) {
            mTestRepository.getTestList(mProgressBar);
        }

        loadTests();
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
    }

    private void initRecyclerViews() {
        rvTests = (RecyclerView) findViewById(R.id.rv_tests);
        rvTests.setHasFixedSize(true);
        final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        rvTests.setLayoutManager(linearLayoutManager);
    }

    private void loadTests() {
        AppExecutor.getInstance().dbExecutor().execute(new Runnable() {
            @Override
            public void run() {
                UIHelper.showProgressBar(mProgressBar);

                mTestList = mTestRepository.getTests();
                if (mTestList != null) {
                    mTestListAdapter = new TestListAdapter(getApplicationContext(), mTestList);
                    rvTests.setAdapter(mTestListAdapter);
                }
                UIHelper.hideProgressBar(mProgressBar);
            }
        });
    }
}