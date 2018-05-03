package com.example.williamjones.familymaplogin.modelclasses;

import com.example.williamjones.familymaplogin.fragments.MapFragmentForHistory;
import com.example.williamjones.familymaplogin.activities.PersonActivity;
import com.example.williamjones.familymaplogin.activities.SearchActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by williamjones on 3/16/16.
 * A Singleton class created for the ease of having my Json info being able to be accessed with ease between my activities
 */
public class InfoSingleton
{
    public static InfoSingleton singleton=new InfoSingleton(); //creation of my singleton

    public InfoSingleton getSingleton()//guarntees to get something.
    {
        return  singleton;
    }
    //Information read From the Json
    private Map<String, Person >Personinfo=new HashMap<>();
    private Map<String, ArrayList<Event> >Eventinfo=new HashMap<>();
    private String personid;//of logged in user

    //markers connection to events
    private  Map<String, Event>myevents=new HashMap<>();

    //boolean data structures to remember positions for various Activities;
    private Map<String,Boolean> eventswitches=new LinkedHashMap<>();
    private Integer lifestorylinespostion=new Integer(0);
    private Integer familylinesposition=new Integer(0);
    private Integer spouselinespostion=new Integer(0);
    private Integer maptypeposition=new Integer(0);
    private ArrayList<Boolean>settingsswitches=new ArrayList<>();
    private boolean inmapactivity=false; //to know if I'm in the mapactvity

    //instances of Activities
    private PersonActivity myperson;
    private SearchActivity mysearch;

    //Different cattergories of events for mothers and fathers.  Used for filter
    private Set<String>FathersEvents=new HashSet<>();
    private Set<String>MotherEvents=new HashSet<>();

    //an instance of the map to keep track of.
    private MapFragmentForHistory mymap=new MapFragmentForHistory();

    //keeps track of my colors for my pins on map
    private Map<String, Float>colorsforevents=new HashMap<>();
    private ArrayList<Float>colors=new ArrayList<>();

    //setters
    private void setEventinfo(Map<String, ArrayList<Event>> eventinfo)
    {
        Eventinfo = eventinfo;
    }
    private void setPersoninfo(Map<String, Person> personinfo)
    {
        Personinfo = personinfo;
    }

    //getters and setters
    public Map<String, Person> getPersoninfo()
    {
        return Personinfo;
    }

    public Map<String, ArrayList<Event> > getEventinfo()
    {
        return Eventinfo;
    }

    public Map<String, Boolean> getEventswitches() {return eventswitches;}

    public void setEventswitches(Map<String, Boolean> eventswitches) {this.eventswitches = eventswitches;}

    public Integer getLifestorylinespostion() {return lifestorylinespostion;}

    public void setLifestorylinespostion(Integer lifestorylinespostion) {this.lifestorylinespostion = lifestorylinespostion;}

    public Integer getFamilylinesposition() {return familylinesposition;}

    public void setFamilylinesposition(Integer familylinesposition) {this.familylinesposition = familylinesposition;}

    public Integer getSpouselinespostion() {return spouselinespostion;}

    public void setSpouselinespostion(Integer spouselinespostion) {this.spouselinespostion = spouselinespostion;}

    public Integer getMaptypeposition() {return maptypeposition;}

    public void setMaptypeposition(Integer maptypeposition) {this.maptypeposition = maptypeposition;}

    public boolean isInmapactivity() {return inmapactivity;}

    public void setInmapactivity(boolean inmapactivity) {this.inmapactivity = inmapactivity;}

    public ArrayList<Boolean> getSettingsswitches() {return settingsswitches;}

    public void setSettingsswitches(ArrayList<Boolean> settingsswitches) {this.settingsswitches = settingsswitches;}

    public String getPersonid() {return personid;}

    public void setPersonid(String personid) {this.personid = personid;}

    public PersonActivity getMyperson() {return myperson;}

    public void setMyperson(PersonActivity myperson) {this.myperson = myperson;}

    public SearchActivity getMysearch() {return mysearch;}

    public void setMysearch(SearchActivity mysearch) {this.mysearch = mysearch;}

    public Set<String> getFathersEvents() {return FathersEvents;}

    public void setFathersEvents(Set<String> fathersEvents) {FathersEvents = fathersEvents;}

    public Set<String> getMotherEvents() {return MotherEvents;}

    public void setMotherEvents(Set<String> motherEvents) {MotherEvents = motherEvents;}

    public MapFragmentForHistory getMymap() {return mymap;}

    public void setMymap(MapFragmentForHistory mymap) {this.mymap = mymap;}

    public Map<String, Float> getColorsforevents() {return colorsforevents;}

    public void setColorsforevents(Map<String, Float> colorsforevents) {this.colorsforevents = colorsforevents;}

    public ArrayList<Float> getColors() {return colors;}

    public void setColors(ArrayList<Float> colors) {this.colors = colors;}

    public Map<String, Event> getMyevents() {return myevents;}

    public void setMyevents(Map<String, Event> myevents) {this.myevents = myevents;}

    public void clear()//a nice clear function for logging out.
    {
        mymap.clearmypins();
        eventswitches=new LinkedHashMap<>();
        lifestorylinespostion=new Integer(0);
        familylinesposition=new Integer(0);
        spouselinespostion=new Integer(0);
        maptypeposition=new Integer(0);
        Personinfo=new HashMap<>();
        Eventinfo=new HashMap<>();
        colorsforevents=new HashMap<>();
        colors=new ArrayList<>();
        myevents=new HashMap<>();
        personid="";
    }

}
