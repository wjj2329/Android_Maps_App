package com.example.williamjones.familymaplogin;

import android.test.AndroidTestCase;

import com.example.williamjones.familymaplogin.modelclasses.Event;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by williamjones on 4/12/16.
 */
public class OrderingTest extends AndroidTestCase
{
    private ArrayList<Event>myevents1=new ArrayList<>();
    private ArrayList<Event>myevents2=new ArrayList<>();
    private ArrayList<Event>myevents3=new ArrayList<>();

    @Override public void setUp() throws Exception
    {
        super.setUp();
        Event event1=new Event("123sdfs7dsafdasf", "fdsa64ds8safsfd3",55f, 278f, "Denmark", "Copanhagen", "death",1678,"William Jones");
        Event event2=new Event("dfsafad733", "23467fshjew", 560f, 322f, "USA", "Los Angeles","birth", 1890, "William Jones");
        Event event3=new Event("423hdfasds33", "234fdsdsfshjew", 40f, 52f, "Mexico", "Mexico City","death", 1567, "William Jones");
        Event event4=new Event("sfagfads332sd", "478432fsdsadf", 540f, 32f, "Congo", "Congo City","baptism", 1895, "William Jones");
        Event event5=new Event("oiuytrew21312", "bnhft46ndsfa63", 575f, 4648f, "Antartica", "Research Lab","birth", 1973, "William Jones");
        myevents1.add(event1);
        myevents1.add(event2);
        myevents1.add(event3);
        myevents1.add(event4);
        myevents1.add(event5);

        myevents2.add(event5);
        myevents2.add(event4);
        myevents2.add(event3);
        myevents2.add(event2);
        myevents2.add(event1);

        Set<Integer>alreadyused=new HashSet<>();
        int loadedup=0;
        while(true)//a way of randomly producing an array of events
        {
            Random random=new Random();
            int number=random.nextInt(5);
         if(!alreadyused.contains(number))
         {
             myevents3.add(myevents1.get(number));
             alreadyused.add(number);
             loadedup++;
         }
            if(loadedup==5)
            {
                break;
            }

        }
    }

    private ArrayList<Event> ordermyarray(ArrayList<Event>myevents)//a nifty function that sorts my Events array in order of earliest events
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
        return freshstart;
    }

    public void testEventsLoadingCorrect() throws Exception
    {
       myevents1= ordermyarray(myevents1);
       myevents2= ordermyarray(myevents2);
       myevents3=ordermyarray(myevents3);

        assertEquals(myevents1.get(0).getCountry(),myevents2.get(0).getCountry());
        assertEquals(myevents1.size(), myevents2.size());
        assertEquals(myevents3.size(), myevents2.size());
        assertEquals(myevents1.get(0),myevents2.get(0));
        assertEquals(myevents3.get(0), myevents2.get(0));
        assertEquals(myevents3.get(1).getCountry(), myevents2.get(1).getCountry());
        assertEquals(myevents3.get(2).getCity(), myevents1.get(2).getCity());
        assertNotSame(myevents3.get(4).getCity(), myevents2.get(0).getCity());
        assertEquals(myevents3.get(1).getLatitude(), myevents2.get(1).getLatitude());
        assertEquals(myevents3.get(4).getEventID(), myevents2.get(4).getEventID());
        assertNotSame(myevents3.get(2).getEventID(), myevents2.get(3).getCity());
    }
    @Override
    public void tearDown() throws Exception
    {
        super.tearDown();
    }
}
