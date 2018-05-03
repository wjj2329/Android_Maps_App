package com.example.williamjones.familymaplogin.fragments;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.amazon.geo.mapsv2.AmazonMap;
import com.amazon.geo.mapsv2.OnMapReadyCallback;
import com.amazon.geo.mapsv2.SupportMapFragment;
import com.amazon.geo.mapsv2.model.BitmapDescriptorFactory;
import com.amazon.geo.mapsv2.model.LatLng;
import com.amazon.geo.mapsv2.model.Marker;
import com.amazon.geo.mapsv2.model.MarkerOptions;
import com.amazon.geo.mapsv2.model.Polyline;
import com.amazon.geo.mapsv2.model.PolylineOptions;
import com.example.williamjones.familymaplogin.R;
import com.example.williamjones.familymaplogin.activities.MainActivity;
import com.example.williamjones.familymaplogin.activities.SettingsActivity;
import com.example.williamjones.familymaplogin.modelclasses.Event;
import com.example.williamjones.familymaplogin.modelclasses.InfoSingleton;
import com.example.williamjones.familymaplogin.modelclasses.Person;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

public class MapFragmentForHistory extends Fragment //I know this name is stupid but I can't change it or the maps will stop working
{
    public static Person CurrentPerson;// Everytime I click on a pin this gets updated which makes things easy for the other activites that need this.
    private AmazonMap mMap;
    private List<Polyline> polylines = new ArrayList<>();
    private List<Polyline> polylines2=new ArrayList<>();
    private SupportMapFragment mMapFragment;
    private List<Marker> markers = new ArrayList<>();
    private static TextView bottomtext;
    private static TextView eventtext;
    private ImageView mypic;
    private View.OnClickListener mylisten;
    private float width;
    private float width2;
    private PolylineOptions opt2=new PolylineOptions();
    private PolylineOptions opt = new PolylineOptions();

    public MapFragmentForHistory() {}// Required empty public constructor

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)//sets up the pic and text views
    {
        View v =  inflater.inflate(R.layout.fragment_map, container, false);
        mylisten=new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                ((MainActivity)getActivity()).changetoPerson();
            }
        };

        bottomtext= (TextView)v.findViewById(R.id.textViewPerson);
        eventtext=(TextView)v.findViewById(R.id.textevent);
        mypic=(ImageView)v.findViewById(R.id.maleorfemale);
        if(InfoSingleton.singleton.isInmapactivity())
        {
            bottomtext.setText(MapFragmentForHistory.CurrentPerson.getFirstName()+" "+MapFragmentForHistory.CurrentPerson.getLastName());
            eventtext.setText("THE EVENT");
            if(MapFragmentForHistory.CurrentPerson.getGender().equals("m"))
            {
                String uri = "@drawable/male";
                int imageRes = getResources().getIdentifier(uri, null, getActivity().getPackageName());
                Drawable res = getResources().getDrawable(imageRes);
                mypic.setImageDrawable(res);
            }
            else
            {
                String uri = "@drawable/female";
                int imageRes = getResources().getIdentifier(uri, null, getActivity().getPackageName());
                Drawable res = getResources().getDrawable(imageRes);
                mypic.setImageDrawable(res);
            }
        }
        bottomtext.setOnClickListener(mylisten);
        eventtext.setOnClickListener(mylisten);
        mypic.setOnClickListener(mylisten);
        mMapFragment = (SupportMapFragment) getChildFragmentManager().findFragmentById(R.id.mapFragment2);
        mapsettings();
        return v;
    }

    int colordecider(String decider)//get the color of the lines that I want from each spinner
    {
        switch (decider)
        {
            case("Red"):
            {
                return Color.RED;
            }
            case("Blue"):
            {
                return Color.BLUE;
            }
            case("Green"):
            {
                return Color.GREEN;
            }

        }
        return 0;
    }

    int themapIwanttouse()//gets the kind of map I want to load from maptype spinner
    {
        switch(SettingsActivity.mapkind)
        {
            case ("Normal"):
            {
                return AmazonMap.MAP_TYPE_NORMAL;
            }
            case ("Hybrid"):
            {
                return AmazonMap.MAP_TYPE_HYBRID;
            }
            case("Saterlite"):
            {
                return AmazonMap.MAP_TYPE_SATELLITE;
            }
            case("Terrain"):
            {
                return AmazonMap.MAP_TYPE_TERRAIN;
            }
        }
        return 0;
    }

    float setupthecolors(String description) //sets up random colors to each event for the pins on the map
    {
        if(InfoSingleton.singleton.getColorsforevents().get(description)==null)
        {
            Random random=new Random();
            int position=random.nextInt(InfoSingleton.singleton.getColors().size());
            InfoSingleton.singleton.getColorsforevents().put(description, InfoSingleton.singleton.getColors().get(position));
            float returning= InfoSingleton.singleton.getColors().get(position);
            InfoSingleton.singleton.getColors().remove(position);
            return returning;
        }
        else
        {
            return InfoSingleton.singleton.getColorsforevents().get(description);
        }
    }

    void colordeciderforpin(String s, int i, LatLng point,  String personid, String event,Event myevent)//actually colors the pins
    {
        float color=setupthecolors(InfoSingleton.singleton.getEventinfo().get(s).get(i).getDescription());
            MarkerOptions opt = new MarkerOptions().position(point).title(InfoSingleton.singleton.getEventinfo().get(s).get(i).getDescription())
                    .snippet(point.toString())
                    .icon(BitmapDescriptorFactory.defaultMarker(color));
            Marker m = mMap.addMarker(opt);
        InfoSingleton.singleton.getMyevents().put(m.getId(),myevent);
        m.setTitle(personid);
        m.setSnippet(event);
        markers.add(m);
    }

    public void  clearmypins()
    {
        mMap.clear();
        for(int i=0; i<markers.size(); i++)
        {
            markers.get(i).remove();
        }
        markers=new ArrayList<>();
        for(int i=0; i<polylines.size(); i++)
        {
            polylines.get(i).remove();
        }
        for(int i=0; i<polylines2.size(); i++)
        {
            polylines2.get(i).remove();
        }
        polylines2=new ArrayList<>();

        polylines=new ArrayList<>();
        opt=new PolylineOptions();
        opt2=new PolylineOptions();
        mapsettings();
    }

    public boolean fathers_side(Event myevent)//returns if the pin can be added based on the switch value for fathers side
    {
        if(InfoSingleton.singleton.getEventswitches().get("Father's Side").booleanValue())
        {
            return true;
        }
        else
        {
            return !InfoSingleton.singleton.getFathersEvents().contains(myevent.getPersonID());
        }
    }

    public boolean mothers_side(Event myevent)//returns if the pin can be added based on the switch value for mothers side
    {
        if(InfoSingleton.singleton.getEventswitches().get("Mother's Side").booleanValue())
        {
            return true;
        }
        else
        {
            return !InfoSingleton.singleton.getMotherEvents().contains(myevent.getPersonID());
        }
    }

    public boolean femalefilter(Event myevent)//returns if the pin can be added based on the switch value for female events
    {
        if(InfoSingleton.singleton.getEventswitches().get("Female Events").booleanValue())
        {
            return true;
        }
        else
        {
            return !InfoSingleton.singleton.getPersoninfo().get(myevent.getPersonID()).getGender().equals("f");

        }
    }

    public boolean malefilter(Event myevent)//returns if the pin can be added based on the switch value for male events
    {
        if(InfoSingleton.singleton.getEventswitches().get("Male Events").booleanValue())
        {
            return true;
        }
        else
        {
            return !InfoSingleton.singleton.getPersoninfo().get(myevent.getPersonID()).getGender().equals("m");
        }
    }

    private void createancestormother(Person currentperson)//recursively gets all of the mothers ancestors for the logged in person
    {
        if(currentperson==null)
        {
            return;
        }
        InfoSingleton.singleton.getMotherEvents().add(currentperson.getPersonID());
        if(currentperson.getFather()!=null)
        {
            createancestormother(InfoSingleton.singleton.getPersoninfo().get(currentperson.getFather()));
        }
        if(currentperson.getMother()!=null) {

            createancestormother(InfoSingleton.singleton.getPersoninfo().get(currentperson.getMother()));
        }
    }

    private void createancestorfather(Person currentperson) //recursively gets all of the fathers ancestors for the logged in person
    {
        if(currentperson==null)
        {
            return;
        }
        InfoSingleton.singleton.getFathersEvents().add(currentperson.getPersonID());
        if(currentperson.getFather()!=null)
        {
            createancestorfather(InfoSingleton.singleton.getPersoninfo().get(currentperson.getFather()));
        }
        if(currentperson.getMother()!=null)
        {
            createancestorfather(InfoSingleton.singleton.getPersoninfo().get(currentperson.getMother()));
        }
    }

    public boolean pinfilter(Event myevent)//handles all switches in filter for events to be turned on or off
    {
        return !InfoSingleton.singleton.getEventswitches().get(myevent.getDescription()).equals(false);
    }

    private boolean checkformarker(Event currentevent)//a function to see if in all of the markers I have I contain a mark for the said event.
    {
        for(int i=0; i<markers.size(); i++)
        {
            if(currentevent.getDescription().equals(InfoSingleton.singleton.getMyevents().get(markers.get(i).getId()).getDescription()))
            {
                return true;
            }
        }
        return  false;
    }

    private void linesbackforfather(Event currentevent, ArrayList<LatLng> points)//handles recursive lines for fathers side
    {
        if(currentevent==null)
        {
            return;
        }
        LatLng person=new LatLng(currentevent.getLatitude(), currentevent.getLongitude());
        if(!points.contains(person))
        {
            if(checkformarker(currentevent)) {
                width *= .5f;
                points.add(person);
                opt.add(person)
                        .color(colordecider(SettingsActivity.familytreecolor));
                Polyline p = mMap.addPolyline(opt);
                p.setWidth(width);
                polylines.add(p);
            }
        }
        ArrayList<Event>mylist= InfoSingleton.singleton.getEventinfo().get(InfoSingleton.singleton.getPersoninfo().get(currentevent.getPersonID()).getMother());
        boolean hasbirth=false;
        if(mylist!=null) {
            for (int i = 0; i < mylist.size(); i++) {
                if (mylist.get(i).getDescription().equals("birth")) {
                    linesbackforfather(mylist.get(i), points);
                    hasbirth=true;
                }
            }
            if(!hasbirth&&mylist!=null)
            {
                int smallestyear=1000000;
                int indexforchoosesnevent=0;
                for(int i=0; i<mylist.size(); i++)
                {
                    if(mylist.get(i).getYear()<smallestyear)
                    {
                        indexforchoosesnevent=i;
                        smallestyear=mylist.get(i).getYear();
                    }
                }
                linesbackforfather(mylist.get(indexforchoosesnevent), points);
            }
        }

        hasbirth=false;
        mylist = InfoSingleton.singleton.getEventinfo().get(InfoSingleton.singleton.getPersoninfo().get(currentevent.getPersonID()).getFather());
        if(mylist!=null) {
            for (int i = 0; i < mylist.size(); i++) {
                if (mylist.get(i).getDescription().equals("birth"))
                {
                    linesbackforfather(mylist.get(i), points);
                    hasbirth=true;
                }
            }
            if(!hasbirth&&mylist!=null)
            {
                int smallestyear=1000000;
                int indexforchoosesnevent=0;
                for(int i=0; i<mylist.size(); i++)
                {
                    if(mylist.get(i).getYear()<smallestyear)
                    {
                        indexforchoosesnevent=i;
                        smallestyear=mylist.get(i).getYear();
                    }
                }
                linesbackforfather(mylist.get(indexforchoosesnevent), points);
            }
        }
    }

    private void linesbackformother(Event currentevent, ArrayList<LatLng> points2)//handles recursive lines for mothers side
    {
        if(currentevent==null)
        {
            return;
        }
        LatLng person=new LatLng(currentevent.getLatitude(), currentevent.getLongitude());
        if(!points2.contains(person))
        {
            if(checkformarker(currentevent)) {
                width2 *= .5f;
                points2.add(person);
                opt2.add(person)
                        .color(colordecider(SettingsActivity.familytreecolor));
                Polyline p2 = mMap.addPolyline(opt2);
                p2.setWidth(width2);
                polylines2.add(p2);
            }
        }
        ArrayList<Event>mylist= InfoSingleton.singleton.getEventinfo().get(InfoSingleton.singleton.getPersoninfo().get(currentevent.getPersonID()).getMother());
        boolean hasbirth=false;
        if(mylist!=null) {
            for (int i = 0; i < mylist.size(); i++) {
                if (mylist.get(i).getDescription().equals("birth")) {
                    linesbackformother(mylist.get(i), points2);
                    hasbirth=true;
                }
            }
            if(!hasbirth&&mylist!=null)
            {
                int smallestyear=1000000;
                int indexforchoosesnevent=0;
                for(int i=0; i<mylist.size(); i++)
                {
                    if(mylist.get(i).getYear()<smallestyear)
                    {
                        indexforchoosesnevent=i;
                        smallestyear=mylist.get(i).getYear();
                    }
                }
                linesbackformother(mylist.get(indexforchoosesnevent), points2);
            }
        }

        hasbirth=false;
        mylist = InfoSingleton.singleton.getEventinfo().get(InfoSingleton.singleton.getPersoninfo().get(currentevent.getPersonID()).getFather());
        if(mylist!=null) {
            for (int i = 0; i < mylist.size(); i++) {
                if (mylist.get(i).getDescription().equals("birth")) {
                    linesbackforfather(mylist.get(i), points2);
                    hasbirth=true;
                }
            }
            if(!hasbirth)
            {
                int smallestyear=1000000;
                int indexforchoosesnevent=0;
                for(int i=0; i<mylist.size(); i++)
                {
                    if(mylist.get(i).getYear()<smallestyear)
                    {
                        indexforchoosesnevent=i;
                        smallestyear=mylist.get(i).getYear();
                    }
                }
                linesbackforfather(mylist.get(indexforchoosesnevent), points2);
            }
        }
    }


    public void ordermyarray(ArrayList<Event>myevents)//a nifty function that sorts my Events array in order of earliest events
    {
        Set<Integer>ordered=new TreeSet<>();
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

    public void lifestorylines(Event event,ArrayList<LatLng> points )
    {
        if (InfoSingleton.singleton.getSettingsswitches().get(0))
        {
        ArrayList<Event> myevents = InfoSingleton.singleton.getEventinfo().get(CurrentPerson.getPersonID());
        ordermyarray(myevents);
        for (int h = 0; h < myevents.size(); h++)
        {
            event = myevents.get(h);
            LatLng mypoint = new LatLng(event.getLatitude(), event.getLongitude());
            points.add(mypoint);
            PolylineOptions opt = new PolylineOptions()
                    .addAll(points)
                    .color(colordecider(SettingsActivity.lifestorycolor));
            Polyline p = mMap.addPolyline(opt);
            polylines.add(p);
        }
        }
    }

    public void familytreelines(ArrayList<LatLng> points, Marker marker,ArrayList<LatLng> points2)
    {
        if (InfoSingleton.singleton.getSettingsswitches().get(1)) {
            LatLng commander = new LatLng(InfoSingleton.singleton.getMyevents().get(marker.getId()).getLatitude(), InfoSingleton.singleton.getMyevents().get(marker.getId()).getLongitude());
            points.add(commander);
            PolylineOptions test = new PolylineOptions();
            width = test.getWidth();
            width2=test.getWidth();
            String personid = InfoSingleton.singleton.getMyevents().get(marker.getId()).getPersonID();
            String motherid = InfoSingleton.singleton.getPersoninfo().get(personid).getMother();
            ArrayList<Event> mylist = InfoSingleton.singleton.getEventinfo().get(motherid);
            Event myevent = InfoSingleton.singleton.getMyevents().get(marker.getId());
            LatLng mypoint = new LatLng(myevent.getLatitude(), myevent.getLongitude());
            opt.add(mypoint);
            boolean hasbirth=false;//need to do other events if no birth.
            if (mylist != null) {
                for (int i = 0; i < mylist.size(); i++)
                {
                    if (mylist.get(i).getDescription().equals("birth"))
                    {
                        linesbackforfather(mylist.get(i), points);
                        hasbirth=true;
                    }
                }
                if(!hasbirth&&mylist!=null)
                {
                    int smallestyear=1000000;
                    int indexforchoosesnevent=0;
                    for(int i=0; i<mylist.size(); i++)
                    {
                        if(mylist.get(i).getYear()<smallestyear)
                        {
                            indexforchoosesnevent=i;
                            smallestyear=mylist.get(i).getYear();
                        }
                    }
                    linesbackforfather(mylist.get(indexforchoosesnevent), points);
                }
            }
            hasbirth=false;
            points2.add(commander);
            opt2.add(mypoint);
            mylist = InfoSingleton.singleton.getEventinfo().get(InfoSingleton.singleton.getPersoninfo().get(InfoSingleton.singleton.getMyevents().get(marker.getId()).getPersonID()).getFather());
            if (mylist != null)
            {
                for (int i = 0; i < mylist.size(); i++)
                {
                    if (mylist.get(i).getDescription().equals("birth"))
                    {
                        linesbackformother(mylist.get(i), points2);
                        hasbirth=true;
                    }
                }
            }
            if(!hasbirth && mylist!=null)
            {
                int smallestyear=1000000;
                int indexforchoosesnevent=0;
                for(int i=0; i<mylist.size(); i++)
                {
                    if(mylist.get(i).getYear()<smallestyear)
                    {
                        indexforchoosesnevent=i;
                        smallestyear=mylist.get(i).getYear();
                    }
                }
                linesbackformother(mylist.get(indexforchoosesnevent), points2);
            }
        }
    }

    public void spouselines(Marker marker, ArrayList<LatLng>points)
    {
        if (InfoSingleton.singleton.getSettingsswitches().get(2))
        {
            LatLng commander = new LatLng(InfoSingleton.singleton.getMyevents().get(marker.getId()).getLatitude(), InfoSingleton.singleton.getMyevents().get(marker.getId()).getLongitude());
            ArrayList<Event> myevent = null;
            myevent = InfoSingleton.singleton.getEventinfo().get((InfoSingleton.singleton.getPersoninfo().get(InfoSingleton.singleton.getMyevents().get(marker.getId()).getPersonID())).getSpouse());
            boolean hasbirth = false;
            if (myevent != null) {
                for (int i = 0; i < myevent.size(); i++)
                {
                    if (myevent.get(i).getDescription().equals("birth"))
                    {
                        hasbirth = true;
                        points.add(commander);
                        LatLng marriagelocation = new LatLng(myevent.get(i).getLatitude(), myevent.get(i).getLongitude());
                        points.add(marriagelocation);
                        PolylineOptions opt = new PolylineOptions()
                                .addAll(points)
                                .color(colordecider(SettingsActivity.spouselinescolor));
                        Polyline p = mMap.addPolyline(opt);
                        polylines.add(p);
                    }
                }
                if (!hasbirth)
                {
                    Event earliest = null;
                    int smallest = Integer.MAX_VALUE;
                    for (int i = 0; i < myevent.size(); i++)
                    {
                        if (myevent.get(i).getYear() < smallest) {
                            earliest = myevent.get(i);
                            smallest = myevent.get(i).getYear();
                        }
                    }
                    points.add(commander);
                    LatLng newpoint = new LatLng(earliest.getLatitude(), earliest.getLongitude());
                    points.add(newpoint);
                }
            }
        }
    }
    void cleansemymaplines(ArrayList<LatLng> points, ArrayList<LatLng> points2)//clears my map of all polylines
    {
        for(int i=0; i<polylines.size(); i++)
        {
            polylines.get(i).remove();
        }
        for(int i=0; i<polylines2.size(); i++)
        {
            polylines2.get(i).remove();
        }
        for(int i=0; i<points.size(); i++)
        {
            points.remove(i);
        }
        for(int i=0; i<points2.size(); i++)
        {
            points2.remove(i);
        }
    }

     public void mapsettings()
     {
         InfoSingleton.singleton.getFathersEvents().clear();
         InfoSingleton.singleton.getMotherEvents().clear();
         if(InfoSingleton.singleton.getPersonid()!=null)
         {
             createancestorfather(InfoSingleton.singleton.getPersoninfo().get(InfoSingleton.singleton.getPersoninfo().get(InfoSingleton.singleton.getPersonid()).getFather()));
             createancestormother(InfoSingleton.singleton.getPersoninfo().get(InfoSingleton.singleton.getPersoninfo().get(InfoSingleton.singleton.getPersonid()).getMother()));
         }
        mMapFragment.getMapAsync(new OnMapReadyCallback()
        {
            ArrayList<LatLng> points = new ArrayList<>(markers.size());
            ArrayList<LatLng> points2=new ArrayList<>(markers.size());
            @Override
            public void onMapReady(AmazonMap amazonMap) {

                amazonMap.setMapType(themapIwanttouse());
                mMap = amazonMap;
                Set<String> mykeys = InfoSingleton.singleton.getEventinfo().keySet();
                for (String s : mykeys) {//this double for loop lets me go through all of my events from my singleton
                    for (int i = 0; i < InfoSingleton.singleton.getEventinfo().get(s).size(); i++)
                    {
                        //this excedingly long if statement is what checks my filter switch booleans to see if it's okay to add the event pin I am currently analyzing.
                        if ((pinfilter(InfoSingleton.singleton.getEventinfo().get(s).get(i)) && femalefilter(InfoSingleton.singleton.getEventinfo().get(s).get(i)) && malefilter(InfoSingleton.singleton.getEventinfo().get(s).get(i)) && fathers_side(InfoSingleton.singleton.getEventinfo().get(s).get(i)) && mothers_side(InfoSingleton.singleton.getEventinfo().get(s).get(i)))) {
                            final LatLng point = new LatLng(InfoSingleton.singleton.getEventinfo().get(s).get(i).getLatitude(), InfoSingleton.singleton.getEventinfo().get(s).get(i).getLongitude());
                            String content = InfoSingleton.singleton.getPersoninfo().get(InfoSingleton.singleton.getEventinfo().get(s).get(i).getPersonID()).getPersonID();
                            String event = InfoSingleton.singleton.getEventinfo().get(s).get(i).getDescription();
                            event += ": " + InfoSingleton.singleton.getEventinfo().get(s).get(i).getCity() + ", " + InfoSingleton.singleton.getEventinfo().get(s).get(i).getCity() + "(" + InfoSingleton.singleton.getEventinfo().get(s).get(i).getYear() + ")";
                            colordeciderforpin(s, i, point, content, event, InfoSingleton.singleton.getEventinfo().get(s).get(i));
                            amazonMap.setOnMarkerClickListener(new AmazonMap.OnMarkerClickListener() {
                                @Override
                                public boolean onMarkerClick(Marker marker) {
                                    cleansemymaplines(points, points2);
                                    opt=new PolylineOptions();
                                    opt2=new PolylineOptions();
                                    CurrentPerson = InfoSingleton.singleton.getPersoninfo().get(marker.getTitle());
                                    Event event=null;
                                    lifestorylines(event, points);
                                    familytreelines(points, marker, points2);
                                    spouselines(marker, points);
                                    if (InfoSingleton.singleton.getPersoninfo().get(InfoSingleton.singleton.getMyevents().get(marker.getId()).getPersonID()).getGender().equals("m"))
                                    {
                                        mypic.setImageResource(R.drawable.male);
                                    }
                                    else
                                    {
                                        mypic.setImageResource(R.drawable.female);
                                    }
                                    bottomtext.setText(CurrentPerson.getFirstName() + " " + CurrentPerson.getLastName());
                                    eventtext.setText(marker.getSnippet());
                                    return false;
                                }
                            });
                        }
                    }
                }
            }
        });
    }

}
