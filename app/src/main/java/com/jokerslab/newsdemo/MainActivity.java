package com.jokerslab.newsdemo;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.jokerslab.newsdemo.network.ServerCalls;

import java.util.ArrayList;
import java.util.HashMap;

import static com.jokerslab.newsdemo.NewsCategory.ALL;
import static com.jokerslab.newsdemo.NewsCategory.ECONOMY;
import static com.jokerslab.newsdemo.NewsCategory.EDUCATION;
import static com.jokerslab.newsdemo.NewsCategory.FOREIGN_AFFAIR;
import static com.jokerslab.newsdemo.NewsCategory.LIFE_STYLE;
import static com.jokerslab.newsdemo.NewsCategory.SPORTS;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, NewsCategoryFragment.OnListFragmentInteractionListener {

    private HashMap<Integer, ArrayList<NewsModel>> newsCache = new HashMap<>();
    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (getIntent().getExtras() != null) {
            for (String key : getIntent().getExtras().keySet()) {
                Object value = getIntent().getExtras().get(key);
                Log.d(TAG, "Key: " + key + " Value: " + value);
            }
        }

        saveTokenToServer();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        NewsCategoryFragment newsCategoryFragment = NewsCategoryFragment.newInstance(1, ALL);
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.content_main, newsCategoryFragment, NewsCategoryFragment.TAG);
        fragmentTransaction.commit();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ArrayList<NewsModel> list = new ArrayList<>();

        NewsModel newsModel = new NewsModel();
        newsModel.setTitle("dfsd");
        newsModel.setCategory(NewsCategory.ECONOMY);
        list.add(newsModel);

        newsModel = new NewsModel();
        newsModel.setTitle("dfsd");
        newsModel.setCategory(NewsCategory.ECONOMY);
        list.add(newsModel);


        newsModel = new NewsModel();
        newsModel.setTitle("dfsd");
        newsModel.setCategory(NewsCategory.ECONOMY);
        list.add(newsModel);

        NewsList newsList = new NewsList();
        newsList.setNews(list);
        newsList.setCount(5);

        String s = newsList.getJsonString();
        Log.d(TAG, s);
    }

    private void saveTokenToServer() {
        String token = Util.getFromPref(this, getString(R.string.token_key), null);
        if (!Util.isEmpty(token)) {
            ServerCalls.saveToken(this, TAG, token, new ServerCalls.ResponseListener() {
                @Override
                public void onResponse(int code, ArrayList<NewsModel> model, String response) {
                    if (code == ServerCalls.NetworkResponseCode.RESULT_OK) {
                        Util.saveToPref(MainActivity.this, getString(R.string.token_key), "");
                    }
                }
            });
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_home) {
            loadNewsSummaryByCategory(ALL);
        } else if (id == R.id.nav_sports) {
            loadNewsSummaryByCategory(SPORTS);
        } else if (id == R.id.nav_economy) {
            loadNewsSummaryByCategory(ECONOMY);
        } else if (id == R.id.nav_life_style) {
            loadNewsSummaryByCategory(LIFE_STYLE);
        } else if (id == R.id.nav_education) {
            loadNewsSummaryByCategory(EDUCATION);
        } else if (id == R.id.nav_foreign_affairs) {
            loadNewsSummaryByCategory(FOREIGN_AFFAIR);
        } else if (id == R.id.nav_settings) {
            startActivity(new Intent(this, SettingsActivity.class));
        } else if (id == R.id.nav_help) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void loadNewsSummaryByCategory(@NewsCategory int newsCategory) {
        NewsCategoryFragment fragment = (NewsCategoryFragment) getSupportFragmentManager().findFragmentByTag(NewsCategoryFragment.TAG);
        if (fragment != null) {
            fragment.loadNews(newsCategory, newsCache.get(newsCategory));
        }
    }

    @Override
    public void storeNews(@NewsCategory int newsCategory, ArrayList<NewsModel> newsModelItemList) {
        if (newsCache.get(newsCategory) == null) {
            newsCache.put(newsCategory, newsModelItemList);
        }
    }
}
