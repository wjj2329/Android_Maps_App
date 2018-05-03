package com.example.williamjones.familymaplogin;

import android.test.AndroidTestCase;

import com.example.williamjones.familymaplogin.modelclasses.Event;
import com.example.williamjones.familymaplogin.modelclasses.InfoSingleton;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

/**
 * Created by williamjones on 4/12/16.
 */
public class EventsLoadingTest extends AndroidTestCase
{
    private Map<String, ArrayList<Event> > eventinfo=new HashMap<>();
    private Set<String> keys=new TreeSet<>();
    private Set<String> truekeys=new TreeSet<>();

    @Override public void setUp() throws Exception
    {

        super.setUp();
        try
        {
            JSONObject myjson=new JSONObject("{\"data\":[{\"eventID\":\"s2p7nbp7-693m-29po-67j7-6r2657yutkw6\",\"personID\":\"46cjjafv-0rm3-a4zm-0g8m-d5jt1633z4n5\",\"latitude\":43.5833,\"longitude\":39.7167,\"country\":\"Russia\",\"city\":\"Sochi\",\"description\":\"birth\",\"year\":\"1791\",\"descendant\":\"a\"},{\"eventID\":\"dgp2wyem-vg2x-kk47-ez48-p19usx7t4i41\",\"personID\":\"46cjjafv-0rm3-a4zm-0g8m-d5jt1633z4n5\",\"latitude\":77.4667,\"longitude\":-68.7667,\"country\":\"Denmark\",\"city\":\"Qaanaaq\",\"description\":\"death\",\"year\":\"1880\",\"descendant\":\"a\"},{\"eventID\":\"aym3zby7-y8w2-72j2-fjno-4hypbjjr07f0\",\"personID\":\"46cjjafv-0rm3-a4zm-0g8m-d5jt1633z4n5\",\"latitude\":37.9167,\"longitude\":139.0333,\"country\":\"Japan\",\"city\":\"Niigata\",\"description\":\"christening\",\"year\":\"1812\",\"descendant\":\"a\"},{\"eventID\":\"810m5yat-69jf-ozc3-1438-9r6y5p0xd9gk\",\"personID\":\"46cjjafv-0rm3-a4zm-0g8m-d5jt1633z4n5\",\"latitude\":-12.4667,\"longitude\":-70.0333,\"country\":\"Peru\",\"city\":\"Cusco\",\"description\":\"baptism\",\"year\":\"1844\",\"descendant\":\"a\"},{\"eventID\":\"4p2wn29x-4648-2002-4u89-k7duu468n222\",\"personID\":\"46cjjafv-0rm3-a4zm-0g8m-d5jt1633z4n5\",\"latitude\":67.55,\"longitude\":133.3833,\"country\":\"Russia\",\"city\":\"Verkhoyansk\",\"description\":\"census\",\"year\":\"1825\",\"descendant\":\"a\"},{\"eventID\":\"96315p8b-bymz-73n0-6316-8m1k236hxx06\",\"personID\":\"46cjjafv-0rm3-a4zm-0g8m-d5jt1633z4n5\",\"latitude\":-25.5833,\"longitude\":31.1667,\"country\":\"Swaziland\",\"city\":\"Lobamba\",\"description\":\"marriage\",\"year\":\"1809\",\"descendant\":\"a\"},{\"eventID\":\"r19z0x28-aau5-29to-jve2-kcp203o9c3zq\",\"personID\":\"xe3577p6-32q5-3z33-f4ku-76580hq510u3\",\"latitude\":54.0833,\"longitude\":-0.6667,\"country\":\"United Kingdom\",\"city\":\"Lancaster\",\"description\":\"birth\",\"year\":\"1793\",\"descendant\":\"a\"},{\"eventID\":\"72i5is9q-zi2v-74p0-4krd-w1r29cer3714\",\"personID\":\"xe3577p6-32q5-3z33-f4ku-76580hq510u3\",\"latitude\":45.5,\"longitude\":-72.4333,\"country\":\"Canada\",\"city\":\"Montreal\",\"description\":\"death\",\"year\":\"1878\",\"descendant\":\"a\"},{\"eventID\":\"66hb143a-8r3m-9462-45l2-28iga9fvh9d3\",\"personID\":\"xe3577p6-32q5-3z33-f4ku-76580hq510u3\",\"latitude\":0.0167,\"longitude\":109.3333,\"country\":\"Indonesia\",\"city\":\"Pontianak\",\"description\":\"christening\",\"year\":\"1868\",\"descendant\":\"a\"},{\"eventID\":\"pg438g3s-4jy0-18r9-x482-mgx4l44o173b\",\"personID\":\"xe3577p6-32q5-3z33-f4ku-76580hq510u3\",\"latitude\":35.3333,\"longitude\":25.1333,\"country\":\"Greece\",\"city\":\"Heraklion\",\"description\":\"baptism\",\"year\":\"1829\",\"descendant\":\"a\"},{\"eventID\":\"c9w5z1u6-4435-d130-09n2-tr631qki0q24\",\"personID\":\"xe3577p6-32q5-3z33-f4ku-76580hq510u3\",\"latitude\":10.65,\"longitude\":-70.3667,\"country\":\"Venezuela\",\"city\":\"Maracaibo\",\"description\":\"census\",\"year\":\"1851\",\"descendant\":\"a\"},{\"eventID\":\"1634r66y-2yq3-2e6x-n776-q3zlnbsj0yfk\",\"personID\":\"xe3577p6-32q5-3z33-f4ku-76580hq510u3\",\"latitude\":-25.5833,\"longitude\":31.1667,\"country\":\"Swaziland\",\"city\":\"Lobamba\",\"description\":\"marriage\",\"year\":\"1809\",\"descendant\":\"a\"},{\"eventID\":\"ke4c0b4q-sf52-0gch-64c6-x62466c53540\",\"personID\":\"467f5wr7-fqs3-p7l9-9t86-4s57s18gl229\",\"latitude\":68.9667,\"longitude\":33.0833,\"country\":\"Russia\",\"city\":\"Murmansk\",\"description\":\"birth\",\"year\":\"1954\",\"descendant\":\"a\"},{\"eventID\":\"im8b1yn9-915r-q0qr-8b5y-203ztgcwm769\",\"personID\":\"467f5wr7-fqs3-p7l9-9t86-4s57s18gl229\",\"latitude\":52.3833,\"longitude\":4.6333,\"country\":\"Netherlands\",\"city\":\"Haarlem\",\"description\":\"death\",\"year\":\"1997\",\"descendant\":\"a\"},{\"eventID\":\"c0436ot6-u04a-31wn-o86x-u3w5595p1wex\",\"personID\":\"467f5wr7-fqs3-p7l9-9t86-4s57s18gl229\",\"latitude\":-2.15,\"longitude\":-31.5833,\"country\":\"Brazil\",\"city\":\"Fernando de Noronha\",\"description\":\"christening\",\"year\":\"1969\",\"descendant\":\"a\"},{\"eventID\":\"z382q88j-539p-pm8o-pro6-5r4gh9kdvpy1\",\"personID\":\"467f5wr7-fqs3-p7l9-9t86-4s57s18gl229\",\"latitude\":31.6167,\"longitude\":65.7167,\"country\":\"Afghanistan\",\"city\":\"Kandahar\",\"description\":\"baptism\",\"year\":\"1955\",\"descendant\":\"a\"},{\"eventID\":\"hg30c95t-1y14-g4pn-8x4v-0grtc731j90s\",\"personID\":\"467f5wr7-fqs3-p7l9-9t86-4s57s18gl229\",\"latitude\":46.4833,\"longitude\":-80.9833,\"country\":\"Canada\",\"city\":\"Sudbury\",\"description\":\"census\",\"year\":\"1956\",\"descendant\":\"a\"},{\"eventID\":\"rwg4s736-fcp1-590l-06ca-4b0oj0539q22\",\"personID\":\"467f5wr7-fqs3-");
            JSONArray myevents=myjson.getJSONArray("data");
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
                   eventinfo.put(personID, new ArrayList<Event>());
                }
                InfoSingleton.singleton.getEventinfo().get(personID).add(mynewevent);
                boolean alreadythere=true;

                if(eventinfo.keySet().contains(mynewevent.getDescription()))
                {
                    alreadythere=false;
                }
                truekeys.add(personID);
            }
            keys=eventinfo.keySet();
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
    }
    public void testEventsLoadingCorrect() throws Exception
    {
        assertEquals(eventinfo, eventinfo);
        assertEquals(eventinfo.size(), eventinfo.size());
        for(String s:keys)
        {
            assertNotNull(eventinfo.get(s));
            assertEquals(eventinfo.get(s).get(0).getCity(), eventinfo.get(s).get(0).getCity());
            assertNotSame(eventinfo.get(s).get(0).getCountry(), eventinfo.get(s).get(0).getEventID());
            assertNull(eventinfo.get("doe a deer a female deer ray a drop of golden sun"));

        }
        for(String s:truekeys)
        {
            assertNotNull(eventinfo.get(s));
            assertEquals(eventinfo.get(s).get(0).getCity(), eventinfo.get(s).get(0).getCity());
            assertNotSame(eventinfo.get(s).get(0).getCountry(), eventinfo.get(s).get(0).getEventID());
            assertNull(eventinfo.get("doe a deer a female deer ray a drop of golden sun"));
        }
        assertEquals(keys.size(), truekeys.size());

    }
    @Override
    public void tearDown() throws Exception
    {
        super.tearDown();
        eventinfo = null;
    }

}
