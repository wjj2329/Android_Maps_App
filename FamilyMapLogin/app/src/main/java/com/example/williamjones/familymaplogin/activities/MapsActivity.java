package com.example.williamjones.familymaplogin.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.williamjones.familymaplogin.R;
import com.example.williamjones.familymaplogin.fragments.MapFragmentForHistory;
import com.example.williamjones.familymaplogin.modelclasses.InfoSingleton;
//activity to handle map activity.  Pretty simple really takes most functionality from the map fragment.
public class MapsActivity extends AppCompatActivity
{
    private android.support.v7.widget.Toolbar mytoolbar;
    private MapFragmentForHistory map_fragment;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        mytoolbar = (Toolbar) findViewById(R.id.activityMap);
        mytoolbar.setTitle("Family Map");
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        InfoSingleton.singleton.setInmapactivity(true);
        FragmentManager fm = getSupportFragmentManager();
        map_fragment = (MapFragmentForHistory) fm.findFragmentById(R.id.justthemap);
        if (map_fragment== null)
        {
            map_fragment = new MapFragmentForHistory();
            fm.beginTransaction().replace(R.id.containerformapactivity, map_fragment).commit();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) //set up toolbar
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbarwithuparrow, menu);
        menu.findItem(R.id.search).setVisible(true);
        return true;
    }

    public void startTopActivity(Context context)//set up, up arrow
    {
        Intent intent=new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP| Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)//set up stuff on toolbar
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                startTopActivity(this);
                return super.onOptionsItemSelected(item);
        }
    }

}
