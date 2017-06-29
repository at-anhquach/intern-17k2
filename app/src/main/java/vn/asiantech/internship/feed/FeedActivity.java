package vn.asiantech.internship.feed;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import vn.asiantech.internship.R;

/**
 * Copyright © 2016 AsianTech inc.
 * Created by datbu on 15-06-2017.
 */
public class FeedActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        FeedFragment feedFragment = new FeedFragment();
        transaction.replace(R.id.fragmentFeed, feedFragment).commit();
    }
}
