package vn.asiantech.internship.ui.splash;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import vn.asiantech.internship.R;
import vn.asiantech.internship.feed.FeedActivity;
import vn.asiantech.internship.friend.ListFriendActivity;
import vn.asiantech.internship.ui.main.MainActivity;

/**
 * Created by PC on 6/15/2017.
 */

public class SplashActivity extends AppCompatActivity implements View.OnClickListener {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        Button btnDay5 = (Button) findViewById(R.id.btnDay5);
        Button btnDay6 = (Button) findViewById(R.id.btnDay6);
        Button btnDay9 = (Button) findViewById(R.id.btnDay9);

        btnDay5.setOnClickListener(this);
        btnDay6.setOnClickListener(this);
        btnDay9.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDay5:
                Intent intent = new Intent(SplashActivity.this, ListFriendActivity.class);
                startActivity(intent);
                break;
            case R.id.btnDay6:
                Intent intent1 = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent1);
                break;
            case R.id.btnDay9:
                Intent intent3 = new Intent(SplashActivity.this, FeedActivity.class);
                startActivity(intent3);
                break;

        }
    }
}
