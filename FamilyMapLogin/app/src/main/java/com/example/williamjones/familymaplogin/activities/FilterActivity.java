package com.example.williamjones.familymaplogin.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.williamjones.familymaplogin.R;
import com.example.williamjones.familymaplogin.adapters.AdapterForFilter;
import com.example.williamjones.familymaplogin.modelclasses.InfoSingleton;

import java.util.ArrayList;
import java.util.List;
//The class that controls the filter.
public class FilterActivity extends AppCompatActivity
{
    private android.support.v7.widget.Toolbar mytoolbar;
    private RecyclerView myrecycler;
    private List<String> lines = new ArrayList<>();
    private RecyclerView.Adapter myadapter = new AdapterForFilter(lines);
    private RecyclerView.LayoutManager mymanager;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_filter);
        for (String s : InfoSingleton.singleton.getEventswitches().keySet())
        {
            ((AdapterForFilter) myadapter).addLine(s);
        }
        mytoolbar = (Toolbar) findViewById(R.id.filter);
        mytoolbar.setTitle("Family Map: Filter");
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        myrecycler = (RecyclerView) findViewById(R.id.myrecycler);
        mymanager = new LinearLayoutManager(this);
        ((LinearLayoutManager) mymanager).setOrientation(LinearLayoutManager.VERTICAL);
        myrecycler.setLayoutManager(mymanager);
        myadapter = new AdapterForFilter(lines);
        myrecycler.setAdapter(myadapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.blanktoolbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case android.R.id.home:
                InfoSingleton.singleton.getMymap().clearmypins();
                finish();
                return true;
        }
        return true;
    }

}
