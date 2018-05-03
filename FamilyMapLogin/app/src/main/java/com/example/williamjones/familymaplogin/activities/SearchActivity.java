package com.example.williamjones.familymaplogin.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.williamjones.familymaplogin.R;
import com.example.williamjones.familymaplogin.adapters.AdapterForSearch;
import com.example.williamjones.familymaplogin.modelclasses.Event;
import com.example.williamjones.familymaplogin.modelclasses.InfoSingleton;
import com.example.williamjones.familymaplogin.modelclasses.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SearchActivity extends AppCompatActivity
{

    private android.support.v7.widget.Toolbar mytoolbar;
    private RecyclerView myrecycler;
    private List<String> lines=new ArrayList<>();
    private List<String> gender=new ArrayList<>();
    private RecyclerView.Adapter myadapter = new AdapterForSearch(lines, gender);
    private RecyclerView.LayoutManager mymanager;
    private EditText searchbar;

    void addpeople(String searchtext)//adds people to my search recycler
    {
        Set <String>mykeys= InfoSingleton.singleton.getPersoninfo().keySet();
        ((AdapterForSearch)myadapter).clear();
        for(String key:mykeys)
        {
            Person currentperson= InfoSingleton.singleton.getPersoninfo().get(key);
            if(currentperson.getFirstName().toLowerCase().contains(searchtext)||currentperson.getLastName().toLowerCase().contains(searchtext))
            {
                ((AdapterForSearch) myadapter).addLine(currentperson.getFirstName()+" "+currentperson.getLastName(), currentperson.getGender());
            }
        }
    }

    void addevents(String searchtext)//add events to my search recycler.
    {
        Set<String>mykeys= InfoSingleton.singleton.getMyevents().keySet();
        for(String key:mykeys)
        {
            Event currentevent= InfoSingleton.singleton.getMyevents().get(key);
            String firstname= InfoSingleton.singleton.getPersoninfo().get(currentevent.getPersonID()).getFirstName().toLowerCase();
            String lastname= InfoSingleton.singleton.getPersoninfo().get(currentevent.getPersonID()).getLastName().toLowerCase();
            if (currentevent.getDescription().toLowerCase().contains(searchtext) || firstname.contains(searchtext)||lastname.contains(searchtext))
            {
                String line=currentevent.getDescription()+":"+currentevent.getDescription()+","+currentevent.getCountry()+"("+currentevent.getYear()+")"+"\n"+" "+ InfoSingleton.singleton.getPersoninfo().get(currentevent.getPersonID()).getFirstName()+" "+ InfoSingleton.singleton.getPersoninfo().get(currentevent.getPersonID()).getLastName();
                ((AdapterForSearch) myadapter).addLine(line, InfoSingleton.singleton.getPersoninfo().get(currentevent.getPersonID()).getFirstName()+" "+ InfoSingleton.singleton.getPersoninfo().get(currentevent.getPersonID()).getLastName());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        mytoolbar = (Toolbar) findViewById(R.id.activitysearch);
        mytoolbar.setTitle("Family Map: Search");
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Activity myactivty=this;
        InfoSingleton.singleton.setMysearch(this);
        searchbar=(EditText)this.findViewById(R.id.searching);
        //set up listener on my search tool input text box to start the search process
        searchbar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String currenttext = searchbar.getText().toString().toLowerCase();
                addpeople(currenttext);
                addevents(currenttext);
                myrecycler = (RecyclerView) findViewById(R.id.myrecyclersearch);
                mymanager = new LinearLayoutManager(myactivty);
                ((LinearLayoutManager) mymanager).setOrientation(LinearLayoutManager.VERTICAL);
                myrecycler.setLayoutManager(mymanager);
                myadapter = new AdapterForSearch(lines, gender);
                myrecycler.setAdapter(myadapter);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)//set up toolbar
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.blanktoolbar, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)//set up back arrow
    {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
        }
        return true;
    }

}
