package com.example.williamjones.familymaplogin.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;


import com.example.williamjones.familymaplogin.R;
import com.example.williamjones.familymaplogin.adapters.AdapterForPerson;
import com.example.williamjones.familymaplogin.fragments.MapFragmentForHistory;
import com.example.williamjones.familymaplogin.modelclasses.Event;
import com.example.williamjones.familymaplogin.modelclasses.InfoSingleton;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

public class PersonActivity extends AppCompatActivity
{
    private View myview;
    private TextView firstname;
    private TextView lastname;
    private TextView gender;
    private android.support.v7.widget.Toolbar mytoolbar;
    private RecyclerView myrecycler;
    private List<String> names=new ArrayList<>();
    private List<String> pins=new ArrayList<>();
    private List<String> relations=new ArrayList<>();
    private List<String> icons=new ArrayList<>();
    private List<String> events=new ArrayList<>();
    private List<String> locations=new ArrayList<>();
    private RecyclerView.Adapter myadapter = new AdapterForPerson(names, events, locations, icons, pins, relations);
    private RecyclerView.LayoutManager mymanager;

    void addpeople()//add people to the recycler view
    {
         String fatherid= MapFragmentForHistory.CurrentPerson.getFather();
         String motherid=MapFragmentForHistory.CurrentPerson.getMother();

        if(fatherid!=null)
        {
            String father= InfoSingleton.singleton.getPersoninfo().get(fatherid).getFirstName()+" "+ InfoSingleton.singleton.getPersoninfo().get(fatherid).getLastName();
            ((AdapterForPerson) myadapter).addLine(father,"Father","m");
        }
        if(motherid!=null)
        {
            String mother= InfoSingleton.singleton.getPersoninfo().get(motherid).getFirstName()+" "+ InfoSingleton.singleton.getPersoninfo().get(motherid).getLastName();
            ((AdapterForPerson)myadapter).addLine(mother,"Mother","f");
        }
    }

    public void ordermyeventarray(ArrayList<Event>myevents)//organizes my events array.
    {
        Set<Integer> ordered=new TreeSet<>();
        ArrayList<Event> freshstart=new ArrayList<>();
        for(int i=0; i<myevents.size(); i++)
        {
            ordered.add(myevents.get(i).getYear());
        }
        for(Integer year:ordered)
        {
            for(int i=0; i<myevents.size(); i++)
            {
                if(myevents.get(i).getYear()==year)
                {
                    freshstart.add(myevents.get(i));
                }
            }
        }
        myevents=freshstart;

    }

    void addevents()//adds events to the Recycler view
    {
        ArrayList<Event> myevents= InfoSingleton.singleton.getEventinfo().get(MapFragmentForHistory.CurrentPerson.getPersonID());
        ordermyeventarray(myevents);
        for(int i=0 ;i<myevents.size(); i++)
        {
            String event=myevents.get(i).getDescription()+" "+myevents.get(i).getCity()+" "+myevents.get(i).getCountry()+"("+myevents.get(i).getYear()+")";
            String name=MapFragmentForHistory.CurrentPerson.getFirstName()+" "+MapFragmentForHistory.CurrentPerson.getLastName();
            ((AdapterForPerson) myadapter).addLine(event,name,"icon");

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person);
        mytoolbar = (Toolbar) findViewById(R.id.activitypersonbar);
        mytoolbar.setTitle("Family Map: Person");
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        InfoSingleton.singleton.setMyperson(this);
        myview=this.findViewById(R.id.personLayout);
        firstname=(TextView)myview.findViewById(R.id.firstname);
        lastname=(TextView)myview.findViewById(R.id.lastname);
        gender=(TextView)myview.findViewById(R.id.gender);
        firstname.setText(MapFragmentForHistory.CurrentPerson.getFirstName());
        lastname.setText(MapFragmentForHistory.CurrentPerson.getLastName());
        gender.setText(MapFragmentForHistory.CurrentPerson.getGender());
        addevents();
        addpeople();
        myrecycler = (RecyclerView) findViewById(R.id.myrecyclerforperson);
        mymanager = new LinearLayoutManager(this);
        ((LinearLayoutManager) mymanager).setOrientation(LinearLayoutManager.VERTICAL);
        myrecycler.setLayoutManager(mymanager);
        myadapter = new AdapterForPerson(names, events, locations, icons, pins, relations);
        myrecycler.setAdapter(myadapter);
    }


    @Override //sets up my toolbar
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.toolbarwithuparrow, menu);
        menu.findItem(R.id.search).setVisible(true);
        return true;
    }

    public void startTopActivity(Context context, boolean newInstance)//starts the up arrow
    {
        Intent intent=new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP  | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)//handles menu options of back arrow and up arrow
    {
        switch(item.getItemId())
        {
            case android.R.id.home:
                finish();
                return true;
            default:
                startTopActivity(this, true);
                return super.onOptionsItemSelected(item);
        }
    }
}
