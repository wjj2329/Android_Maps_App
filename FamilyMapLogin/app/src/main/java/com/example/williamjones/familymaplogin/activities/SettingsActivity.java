package com.example.williamjones.familymaplogin.activities;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.amazon.geo.mapsv2.model.BitmapDescriptorFactory;
import com.example.williamjones.familymaplogin.R;
import com.example.williamjones.familymaplogin.fragments.Login_Fragment;
import com.example.williamjones.familymaplogin.internetservices.HttpClient;
import com.example.williamjones.familymaplogin.modelclasses.Event;
import com.example.williamjones.familymaplogin.modelclasses.InfoSingleton;
import com.example.williamjones.familymaplogin.modelclasses.Person;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SettingsActivity extends AppCompatActivity
{
    static public String lifestorycolor="Red";
    static public String familytreecolor="Red";
    static public String spouselinescolor="Red";
    static public String mapkind="Normal";
    private TextView logout;
    private TextView logout2;
    private TextView resync;
    private TextView resync2;
    private Switch lifestoryswitch;
    private Switch familytree;
    private Switch spouselines1;
    private android.support.v7.widget.Toolbar mytoolbar;

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.blanktoolbar, menu);
        return true;
    }

    //how Settings can have spinners what color they should be
    private String colordecider(int position)
    {
        switch(position)
        {
            case(0):
            {
                return "Red";
            }
            case(1):
            {
                return "Blue";
            }
            case(2):
            {
                return "Green";
            }
        }
        return null;
    }
//what kind of map I want
   private  void mapdecider(int position)
    {
        switch (position)
        {
            case(0):
                mapkind="Normal";
                break;
            case(1):
                mapkind="Hybrid";
                break;
            case(2):
                mapkind="Saterlite";
                break;
            case(3):
                mapkind="Terrain";
                break;
        }
    }
    //controls the spinners
    void spinnercontrol(Spinner lifestory, final Spinner familytreelines, Spinner spouselines, final Spinner maptype)
    {
        lifestory.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                lifestorycolor = colordecider(position);
                InfoSingleton.singleton.setLifestorylinespostion( position);
                InfoSingleton.singleton.getMymap().clearmypins();
            }
            @Override//useless garbage
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        familytreelines.setSelection(InfoSingleton.singleton.getFamilylinesposition());
        familytreelines.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                familytreecolor = colordecider(position);
                InfoSingleton.singleton.setFamilylinesposition(position);
                InfoSingleton.singleton.getMymap().clearmypins();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        spouselines.setSelection(InfoSingleton.singleton.getSpouselinespostion());
        spouselines.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                spouselinescolor = colordecider(position);
                InfoSingleton.singleton.setSpouselinespostion(position);
                InfoSingleton.singleton.getMymap().clearmypins();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        maptype.setSelection(InfoSingleton.singleton.getMaptypeposition());
        maptype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                mapdecider(position);
                InfoSingleton.singleton.setMaptypeposition(position);
                InfoSingleton.singleton.getMymap().mapsettings();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
    //this function controls all the spinners and switches and buttons for the activity
    void setupthesettings(Spinner lifestory, final Spinner familytreelines, Spinner spouselines, final Spinner maptype )
    {
        lifestoryswitch.setChecked(InfoSingleton.singleton.getSettingsswitches().get(0));
        lifestoryswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                InfoSingleton.singleton.getSettingsswitches().set(0, isChecked);
                InfoSingleton.singleton.getMymap().clearmypins();
            }
        });
        familytree.setChecked(InfoSingleton.singleton.getSettingsswitches().get(1));
        familytree.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                InfoSingleton.singleton.getSettingsswitches().set(1, isChecked);
                InfoSingleton.singleton.getMymap().clearmypins();
            }
        });
        spouselines1.setChecked(InfoSingleton.singleton.getSettingsswitches().get(2));
        spouselines1.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                InfoSingleton.singleton.getSettingsswitches().set(2, isChecked);
                InfoSingleton.singleton.getMymap().clearmypins();
            }
        });
        lifestory.setSelection(InfoSingleton.singleton.getLifestorylinespostion());
        spinnercontrol(lifestory, familytreelines, spouselines, maptype);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)//sets up all the variables
    {
        final Context mycontext=this;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        View v=this.findViewById(R.id.settingsview);
        View.OnClickListener mylisten;
        mylisten=new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                MainActivity.loggedin=false;
                finish();
            }
        };
        View.OnClickListener logoutlisten;
        logoutlisten=new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
               InfoSingleton.singleton.clear();
                MainActivity.loggedin=true;
                Toast.makeText(mycontext, "Data Resync", Toast.LENGTH_SHORT).show();
                try {
                     Loginforsettings task = new Loginforsettings();

                    task.execute(new URL("http://localhost:3000/"));

                }
                catch (MalformedURLException e)
                {

                }

            }
        };
        mytoolbar = (Toolbar) findViewById(R.id.filter);
        mytoolbar.setTitle("Family Map: Settings");
        setSupportActionBar(mytoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        lifestoryswitch=(Switch)v.findViewById(R.id.lifestory);
        familytree=(Switch)v.findViewById(R.id.familytree);
        spouselines1=(Switch)v.findViewById(R.id.spouse);
        resync=(TextView)v.findViewById(R.id.resync);
        resync2=(TextView)v.findViewById(R.id.resync2);
        logout2=(TextView)v.findViewById(R.id.Logout2);
        logout=(TextView)v.findViewById(R.id.Logout);
        logout.setOnClickListener(mylisten);
        logout2.setOnClickListener(mylisten);
        resync.setOnClickListener(logoutlisten);
        resync2.setOnClickListener(logoutlisten);
        Spinner lifestory=(Spinner)v.findViewById(R.id.lifestorylines);
        final Spinner familytreelines=(Spinner)v.findViewById(R.id.familytreelines);
        Spinner spouselines=(Spinner)v.findViewById(R.id.spouselines);
        final Spinner maptype=(Spinner)v.findViewById(R.id.maptype);
        setupthesettings(lifestory, familytreelines,spouselines, maptype);

    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                InfoSingleton.singleton.getMymap().clearmypins();
                finish();
                return true;
        }
        return true;
    }




    //handles resync

        public  class Loginforsettings extends AsyncTask<URL, Integer, Boolean>
        {
            String firstname=null;
            String lastname=null;
            protected Boolean doInBackground(URL... urls)
            {
                HttpClient httpClient = new HttpClient();
                String response=httpClient.postUrl(Login_Fragment.username, Login_Fragment.password, Login_Fragment.serverhost, Login_Fragment.serverport);
                String token=null;
                String personid=null;
                System.out.println(response);
                JSONObject myobject;
                try
                {
                    myobject = new JSONObject(response);
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                    return false;
                }
                if(myobject.has("message"))
                {
                    return false;
                }
                else
                {
                    try
                    {
                        token = myobject.getString("Authorization");
                        personid=myobject.getString("personId");
                        InfoSingleton.singleton.setPersonid(personid);
                    }
                    catch (Exception e)
                    {
                        e.printStackTrace();
                        return false;
                    }
                    String event="event";
                    response=httpClient.getUrl( Login_Fragment.serverhost, Login_Fragment.serverport, event, token);
                    createEvents(response);
                    System.out.println("my events are "+response);
                    event="person";
                    response=httpClient.getUrl(Login_Fragment.serverhost, Login_Fragment.serverport, event, token);
                    System.out.println("my people are" + response);
                    createPeople(response);
                    event="person/"+personid;
                    response=httpClient.getUrl(Login_Fragment.serverhost, Login_Fragment.serverport, event, token);
                    try
                    {
                        JSONObject anobject = new JSONObject(response);
                        firstname=anobject.getString("firstName");
                        lastname=anobject.getString("lastName");
                    }
                    catch(Exception e)
                    {
                        e.printStackTrace();
                    }
                    return true;
                }

            }
            @Override
            protected void onPostExecute(Boolean result) {
                if(result==false)
                {
                    System.out.println("YOU FAILED");
                }
                else
                {
                    InfoSingleton.singleton.getMymap().clearmypins();
                    finish();
                }
            }
            private void createPeople(String people)
            {
                try
                {
                    JSONObject mypeopleobject = new JSONObject(people);
                    JSONArray mypeople = mypeopleobject.getJSONArray("data");
                    for(int i=0; i<mypeople.length(); i++)
                    {
                        ArrayList<String>otherfamilymembers=new ArrayList<>();
                        String descendant=mypeople.getJSONObject(i).getString("descendant");
                        String personID=mypeople.getJSONObject(i).getString("personID");
                        String firstName=mypeople.getJSONObject(i).getString("firstName");
                        String lastName=mypeople.getJSONObject(i).getString("lastName");
                        String gender=mypeople.getJSONObject(i).getString("gender");
                        String father=null;
                        String mother=null;
                        String spouse=null;
                        try
                        {
                            mother=mypeople.getJSONObject(i).getString("mother");

                        }
                        catch(Exception e)
                        {
                        }
                        try
                        {
                            father=mypeople.getJSONObject(i).getString("father");

                        }
                        catch (Exception e)
                        {

                        }
                        try
                        {
                            spouse = mypeople.getJSONObject(i).getString("spouse");
                        }
                        catch(Exception e)
                        {

                        }
                        Person myperson=new Person(descendant, personID, firstName, lastName, gender, spouse, father, mother);
                        if(!InfoSingleton.singleton.getPersoninfo().containsKey(personID))
                        {
                            InfoSingleton.singleton.getPersoninfo().put(personID, new Person (null, null, null, null, null, null, null, null));
                        }
                        InfoSingleton.singleton.getPersoninfo().put(personID,myperson);
                    }
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }

            }
            private void  createEvents(String events)
            {
                try
                {
                    JSONObject myeventsobject = new JSONObject(events);
                    JSONArray myevents=myeventsobject.getJSONArray("data");
                    for(int i=0; i<myevents.length(); i++)
                    {
                        String eventID=myevents.getJSONObject(i).getString("eventID");
                        String personID=myevents.getJSONObject(i).getString("personID");
                        double latitude=myevents.getJSONObject(i).getDouble("latitude");
                        double longitude=myevents.getJSONObject(i).getDouble("longitude");
                        String country=myevents.getJSONObject(i).getString("country");
                        String city=myevents.getJSONObject(i).getString("city");
                        String description=myevents.getJSONObject(i).getString("description");
                        int year=myevents.getJSONObject(i).getInt("year");
                        String descendant=myevents.getJSONObject(i).getString("descendant");
                        Event mynewevent=new Event(eventID, personID, latitude, longitude, country, city, description, year, descendant);
                        if(!InfoSingleton.singleton.getEventinfo().containsKey(personID))
                        {
                            InfoSingleton.singleton.getEventinfo().put(personID, new ArrayList<Event>());
                        }
                        InfoSingleton.singleton.getEventinfo().get(personID).add(mynewevent);
                        boolean alreadythere=true;
                        if(InfoSingleton.singleton.getEventswitches().keySet().contains(mynewevent.getDescription()))
                        {
                            alreadythere=false;
                        }
                        if(alreadythere)
                        {
                            InfoSingleton.singleton.getEventswitches().put(mynewevent.getDescription(), new Boolean(true));
                        }
                    }
                    InfoSingleton.singleton.getEventswitches().put("Father's Side",new Boolean(true));
                    InfoSingleton.singleton.getEventswitches().put("Mother's Side", new Boolean(true));
                    InfoSingleton.singleton.getEventswitches().put("Male Events", new Boolean(true));
                    InfoSingleton.singleton.getEventswitches().put("Female Events", new Boolean(true));
                    InfoSingleton.singleton.getColors().add(BitmapDescriptorFactory.HUE_ORANGE);
                    InfoSingleton.singleton.getColors().add(BitmapDescriptorFactory.HUE_AZURE);
                    InfoSingleton.singleton.getColors().add(BitmapDescriptorFactory.HUE_BLUE);
                    InfoSingleton.singleton.getColors().add(BitmapDescriptorFactory.HUE_CYAN);
                    InfoSingleton.singleton.getColors().add(BitmapDescriptorFactory.HUE_GREEN);
                    InfoSingleton.singleton.getColors().add(BitmapDescriptorFactory.HUE_MAGENTA);
                    InfoSingleton.singleton.getColors().add(BitmapDescriptorFactory.HUE_RED);
                    InfoSingleton.singleton.getColors().add(BitmapDescriptorFactory.HUE_VIOLET);
                    InfoSingleton.singleton.getColors().add(BitmapDescriptorFactory.HUE_YELLOW);
                    InfoSingleton.singleton.getColors().add(BitmapDescriptorFactory.HUE_ROSE);
                    for(int i=0; i<3; i++)
                    {
                        System.out.println("i add a bool");
                        InfoSingleton.singleton.getSettingsswitches().add(new Boolean(true));
                    }
                }

                catch(Exception e)
                {
                    e.printStackTrace();
                }
            }

    }

}
