package vn.asiantech.internship.drawerlayout.activities;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import vn.asiantech.internship.drawerlayout.adapter.DrawerAdapter;
import vn.asiantech.internship.drawerlayout.model.DrawerItem;

/**
 * Created by sony on 12/06/2017.
 */

public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;
    private RecyclerView mRecyclerView;
    private DrawerAdapter mAdapter;
    private List<DrawerItem> mDrawerItems;
    private Toolbar mToolbar;
    private ActionBarDrawerToggle mDrawerToggle;
    private TextView mTvResult;
    private  int mPositionSelected = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);

        mTvResult = (TextView) findViewById(R.id.tvResult);
        mDrawerItems = new ArrayList<>();
        mDrawerItems.add(new DrawerItem(R.mipmap.ic_feed, getString(R.string.feed)));
        mDrawerItems.add(new DrawerItem(R.mipmap.ic_activity, getString(R.string.activity)));
        mDrawerItems.add(new DrawerItem(R.mipmap.ic_profile, getString(R.string.profile)));
        mDrawerItems.add(new DrawerItem(R.mipmap.ic_friends, getString(R.string.friends)));
        mDrawerItems.add(new DrawerItem(R.mipmap.ic_map, getString(R.string.map)));
        mDrawerItems.add(new DrawerItem(R.mipmap.ic_chat, getString(R.string.chat)));
        mDrawerItems.add(new DrawerItem(R.mipmap.ic_setting, getString(R.string.setting)));
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerview_funtion);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        mRecyclerView.setLayoutManager(layoutManager);
        mAdapter = new DrawerAdapter(mDrawerItems);
        mRecyclerView.setAdapter(mAdapter);
        navigation();
        mAdapter.setOnItemClickListener(new DrawerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick( int position) {
                mTvResult.setText(mDrawerItems.get(position).getName());
                mDrawerLayout.closeDrawers();
                if(mPositionSelected>=0){
                    mDrawerItems.get(mPositionSelected).setState(false);
                }
                mPositionSelected = position;
                mDrawerItems.get(position).setState(true);
                mAdapter.notifyDataSetChanged();
            }
        });
    }

    private void navigation() {
        mToolbar = (Toolbar) findViewById(R.id.toolBar);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        setSupportActionBar(mToolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout, mToolbar, R.string.app_name, R.string.app_name) {
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
            }

            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }
        };
        mDrawerLayout.addDrawerListener(mDrawerToggle);
        mDrawerToggle.setDrawerIndicatorEnabled(true);
        mDrawerToggle.syncState();
    }
}
