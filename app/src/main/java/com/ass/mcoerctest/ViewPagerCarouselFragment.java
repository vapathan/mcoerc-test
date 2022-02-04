package com.ass.mcoerctest;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ass.mcoerctest.models.Notification;

public class ViewPagerCarouselFragment extends Fragment {

    public static final String NOTIFICATION_KEY = "notification_key" ;
    private TextView tvTitle, tvDetails;
    private Notification notification;

    public ViewPagerCarouselFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_carousel, container, false);
        tvTitle = (TextView) view.findViewById(R.id.tv_title);
        tvDetails = (TextView) view.findViewById(R.id.tv_details);

        notification = getArguments().getParcelable(NOTIFICATION_KEY);
        if(notification!=null){
            tvTitle.setText(notification.getTitle());
            tvDetails.setText(notification.getDetails());
        }

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        return view;
    }
}