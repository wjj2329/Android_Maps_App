package com.example.williamjones.familymaplogin;

import android.test.AndroidTestCase;

import com.example.williamjones.familymaplogin.modelclasses.Event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Random;
import java.util.Set;

/**
 * Created by williamjones on 4/12/16.
 */
public class FilterTest extends AndroidTestCase
{
    private Map<String, Boolean> myfiltercontrol1=new HashMap<>();
    private Map<String, Boolean> myfiltercontrol2=new HashMap<>();
    private Map<String, Boolean> myfiltercontrol3=new HashMap<>();


    @Override public void setUp() throws Exception
    {
        ArrayList<Event>theevents1=new ArrayList<>();
        ArrayList<Event>theevents2=new ArrayList<>();
        ArrayList<Event>theevents3=new ArrayList<>();
        Event event1=new Event("123sdfs7dsafdasf", "fdsa64ds8safsfd3",55f, 278f, "Denmark", "Copanhagen", "death",1678,"William Jones");
        Event event2=new Event("dfsafad733", "23467fshjew", 560f, 322f, "USA", "Los Angeles","birth", 1890, "William Jones");
        Event event3=new Event("423hdfasds33", "234fdsdsfshjew", 40f, 52f, "Mexico", "Mexico City","death", 1567, "William Jones");
        Event event4=new Event("sfagfads332sd", "478432fsdsadf", 540f, 32f, "Congo", "Congo City","baptism", 1895, "William Jones");
        Event event5=new Event("oiuytrew21312", "bnhft46ndsfa63", 575f, 4648f, "Antartica", "Research Lab","birth", 1973, "William Jones");
        theevents1.add(event1);
        theevents1.add(event2);
        theevents1.add(event3);
        theevents1.add(event4);
        theevents1.add(event5);

        theevents2.add(event5);
        theevents2.add(event4);
        theevents2.add(event3);
        theevents2.add(event2);
        theevents2.add(event1);

        Set<Integer> alreadyused=new HashSet<>();
        int loadedup=0;
        while(true)//a way of randomly producing an array of events
        {
            Random random=new Random();
            int number=random.nextInt(5);
            if(!alreadyused.contains(number))
            {
                theevents3.add(theevents1.get(number));
                alreadyused.add(number);
                loadedup++;
            }
            if(loadedup==5)
            {
                break;
            }
        }
        setupthemaps(theevents1,theevents2,theevents3);

    }
    private void setupthemaps(ArrayList<Event>theevents1, ArrayList<Event>theevents2, ArrayList<Event>theevents3)
    {
     for(int i=0; i<theevents1.size(); i++)
     {
         myfiltercontrol1.put(theevents1.get(i).getDescription(),true);
         myfiltercontrol2.put(theevents2.get(i).getDescription(),true);
         myfiltercontrol3.put(theevents3.get(i).getDescription(),true);
     }
    }

    public void testFilterCorrect() throws Exception
    {
        assertTrue(myfiltercontrol1.get("baptism"));
        assertEquals(myfiltercontrol1.get("death"),myfiltercontrol2.get("death"));
        assertNull(myfiltercontrol1.get(""));
        assertEquals(myfiltercontrol1.get("death"), myfiltercontrol3.get("death"));
        myfiltercontrol1.put("birth",Boolean.FALSE);
        assertNotSame(myfiltercontrol1.get("birth"), myfiltercontrol3.get("birth"));
        assertFalse(myfiltercontrol1.get("birth"));
        myfiltercontrol2.put("birth",false);
        assertEquals(myfiltercontrol1.get("birth"), myfiltercontrol2.get("birth"));
    }
    @Override
    public void tearDown() throws Exception
    {
        super.tearDown();
    }
}
