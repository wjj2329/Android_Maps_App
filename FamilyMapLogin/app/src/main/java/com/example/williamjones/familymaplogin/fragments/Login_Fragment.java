package com.example.williamjones.familymaplogin.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.amazon.geo.mapsv2.model.BitmapDescriptorFactory;
import com.example.williamjones.familymaplogin.R;
import com.example.williamjones.familymaplogin.activities.MainActivity;
import com.example.williamjones.familymaplogin.internetservices.HttpClient;
import com.example.williamjones.familymaplogin.modelclasses.Event;
import com.example.williamjones.familymaplogin.modelclasses.InfoSingleton;
import com.example.williamjones.familymaplogin.modelclasses.Person;

import org.json.JSONArray;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class Login_Fragment extends Fragment{
    private Button button;
    private EditText user_name=null;
    private EditText pass_word=null;
    private EditText server_host=null;
    private EditText server_port=null;
    public static String serverhost;
    public static String serverport;
    static public String username;
    static public String password;
    public Login_Fragment() {}  // Required empty public constructor
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override//here is for the button
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        View v = inflater.inflate(R.layout.fragment_blank, container, false);
        user_name=(EditText)v.findViewById(R.id.editText5);
        pass_word=(EditText)v.findViewById(R.id.editText6);
        server_host=(EditText)v.findViewById(R.id.editText7);
        server_port=(EditText)v.findViewById(R.id.editText8);
        button = (Button) v.findViewById((R.id.button2));
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onButtonClicked();
            }
        });
        return v;
    }

    private void onButtonClicked()//if log in button is clicked
    {
        if(server_host!=null&&server_port!=null) {
            serverhost = server_host.getText().toString();
            serverport = server_port.getText().toString();
            username=user_name.getText().toString();
            password=pass_word.getText().toString();
       }

        try {
            Login task = new Login();

            task.execute(new URL("http://localhost:3000/"));
        }
        catch (MalformedURLException e) {
            Toast.makeText(getContext(), "Failed", Toast.LENGTH_LONG).show();
            //Toast mytoast=new Toast(getContext());
            //mytoast.makeText(getContext(), "Failure", Toast.LENGTH_LONG);
            //mytoast.show();
            Log.e("MainActivity", e.getMessage(), e);
        }
    }



    //handles the login
    public class Login extends AsyncTask<URL, Integer, Boolean>
    {
        String firstname=null;
        String lastname=null;
        protected Boolean doInBackground(URL... urls)
        {
            HttpClient httpClient = new HttpClient();
            String response=httpClient.postUrl(username, password, serverhost, serverport);
            String token;
            String personid;
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
                System.out.println("Suceess!");
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
                response=httpClient.getUrl(serverhost, serverport, event, token);
                //System.out.println("another one" +response);
                createEvents(response);
                event="person";
                response=httpClient.getUrl(serverhost, serverport, event, token);
                //System.out.println("my response "+ response);
                createPeople(response);
                event="person/"+personid;
                response=httpClient.getUrl( serverhost, serverport, event, token);
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
        protected void onPostExecute(Boolean result) //if successful on logging in or not.
        {
            if(!result)
            {
                Toast.makeText(getContext(), "Invalid Login", Toast.LENGTH_LONG).show();
            }
            else
            {
             Toast.makeText(getContext(),"Welcome "+firstname+" "+lastname, Toast.LENGTH_LONG).show();
                ((MainActivity)getActivity()).changetomaps();
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