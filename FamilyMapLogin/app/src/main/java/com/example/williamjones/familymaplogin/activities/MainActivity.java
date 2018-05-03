package com.example.williamjones.familymaplogin.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.williamjones.familymaplogin.R;
import com.example.williamjones.familymaplogin.fragments.Login_Fragment;
import com.example.williamjones.familymaplogin.fragments.MapFragmentForHistory;
import com.example.williamjones.familymaplogin.modelclasses.InfoSingleton;
//The class that controls the First Main Activity
public class MainActivity extends AppCompatActivity
{
    android.support.v7.widget.Toolbar mytoolbar;
    private Login_Fragment login_fragment;
    private MapFragmentForHistory map_fragment;
    //This needs to be updated in other Activities specfically the Settings
    public static boolean loggedin=false;
    public void changetomaps()
    {
        loggedin=true;
        InfoSingleton.singleton.setInmapactivity(false);
        FragmentManager fm = getSupportFragmentManager();
        map_fragment = (MapFragmentForHistory) fm.findFragmentById(R.id.mapfragment);
        if (map_fragment== null)
        {
            map_fragment = InfoSingleton.singleton.getMymap();
            fm.beginTransaction()
                    .replace(R.id.container, map_fragment)
                    .commit();
        }
        setSupportActionBar(mytoolbar);
    }
    public void changetoPerson()
    {
        loggedin=true;
        Intent myintent=new Intent(this, PersonActivity.class);
        this.startActivity(myintent);

    }

    @Override
    public void onResume()
    {
        super.onResume();
        if(!loggedin)//if not logged in load the login fragment
        {
            setContentView(R.layout.activity_main_menu);
            mytoolbar= (Toolbar) findViewById(R.id.mytoolbar1);
            mytoolbar.setTitle("Family Map");
            setSupportActionBar(mytoolbar);
            FragmentManager fm = this.getSupportFragmentManager();
            login_fragment = (Login_Fragment) fm.findFragmentById(R.id.loginfragment);
            if (login_fragment == null)
            {
                login_fragment = new Login_Fragment();
                fm.beginTransaction()
                        .replace(R.id.container, login_fragment)
                        .commit();
            }
        }
        else//if logged in change to the maps fragment
            changetomaps();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        if(!loggedin)
        {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main_menu);
            mytoolbar= (Toolbar) findViewById(R.id.mytoolbar1);
            mytoolbar.setTitle("Family Map");
            
            setSupportActionBar(mytoolbar);
            FragmentManager fm = this.getSupportFragmentManager();
            login_fragment = (Login_Fragment) fm.findFragmentById(R.id.loginfragment);
            if (login_fragment == null)
            {
                login_fragment = new Login_Fragment();
                fm.beginTransaction()
                        .replace(R.id.container, login_fragment)
                        .commit();
            }
        }else
            changetomaps();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)//set up the toolbar
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mytoolbar, menu);
        menu.findItem(R.id.search).setVisible(loggedin);
        menu.findItem(R.id.filter).setVisible(loggedin);
        menu.findItem(R.id.settings).setVisible(loggedin);
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu)//make the tool bar visible
    {
        menu.findItem(R.id.search).setVisible(loggedin);
        menu.findItem(R.id.filter).setVisible(loggedin);
        menu.findItem(R.id.settings).setVisible(loggedin);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item)//handles what happens when different icons on toolbar are selected.
    {
        Intent myintent;
        switch (item.getItemId())
        {
            case R.id.search:
                myintent=new Intent(this, SearchActivity.class);
                this.startActivity(myintent);
                break;

            case R.id.settings:
                myintent=new Intent(this, SettingsActivity.class);
                this.startActivity(myintent);
                break;

            case R.id.filter:
                myintent=new Intent(this, FilterActivity.class);
                this.startActivity(myintent);
                break;

        }
        return super.onOptionsItemSelected(item);
    }
}
