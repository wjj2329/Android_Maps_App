package com.example.williamjones.familymaplogin;

import android.test.AndroidTestCase;

import com.example.williamjones.familymaplogin.modelclasses.Person;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by williamjones on 4/12/16.
 */
public class FamilyRelationShipTest extends AndroidTestCase
{
 Map<String, Person> mapofpeople=new HashMap<>();
    Person person1;
    Person person2;
    Person person3;
    Person person4;
    Person person5;
    @Override public void setUp() throws Exception
    {
        person1=new Person("William Jones", "fdahsf324432","Bob", "Heberger", "m", null,"324sfasfd32", "fadsf324f324" );
        person2=new Person("William Jones", "fdasjf423324", "Kevin", "Johnston", "m","fadshfsh32432", null, null );//kevin married to Noel
        person3=new Person("William Jones", "fadshfsh32432", "Noel", "O'Brian", "f","fdasjf423324","fdasf324sf3" ,"fdasfs334432" );//parents are patricia and Lindon;
        person4=new Person("William Jones", "fdasfs334432", "Patricia", "Cisak", "f", "fdasf324sf3", null, null);//have no parent
        person5=new Person("William Jones", "fdasf324sf3", "Lindon", "Overhizer", "m","fdasfs334432", null, null);//have no partent

        mapofpeople.put(person1.getPersonID(), person1);
        mapofpeople.put(person2.getPersonID(), person2);
        mapofpeople.put(person3.getPersonID(), person3);
        mapofpeople.put(person4.getPersonID(), person4);
        mapofpeople.put(person5.getPersonID(),person5);
    }
    private  Person findmother(Person person)
    {
        String motherid=person.getMother();
        if(motherid!=null)
        {
            Set<String> mykeys = mapofpeople.keySet();
            for (String s : mykeys)
            {
                if(mapofpeople.get(s).getPersonID().equals(motherid))
                {
                    return mapofpeople.get(s);
                }
            }
        }
        return null;
    }

    private Person findfather(Person person)
    {
        String motherid=person.getFather();
        if(motherid!=null)
        {
            Set<String> mykeys = mapofpeople.keySet();
            for (String s : mykeys)
            {
                if(mapofpeople.get(s).getPersonID().equals(motherid))
                {
                    return mapofpeople.get(s);
                }
            }
        }
        return null;
    }
    private Person findchild(Person person)
    {
        String myid=person.getPersonID();
        Set<String> mykeys=mapofpeople.keySet();
        for(String s:mykeys)
        {
            if(mapofpeople.get(s).getFather()!=null&&mapofpeople.get(s).getMother()!=null) {
                if (mapofpeople.get(s).getFather().equals(myid) || mapofpeople.get(s).getMother().equals(myid)) {
                    return mapofpeople.get(s);
                }
            }
        }
        return null;
    }
    private Person findspouse(Person person)
    {
        String spouse=person.getSpouse();
        Set<String> mykeys=mapofpeople.keySet();
        for(String s:mykeys)
        {
            if(mapofpeople.get(s).getPersonID().equals(spouse))
            {
                return mapofpeople.get(s);
            }
        }
        return null;
    }

    public void testFamilyLoadingCorrect() throws Exception
    {
        assertNull(person1.getSpouse());
        assertNull(findspouse(person1));
        assertEquals(findmother(person3).getPersonID(),person4.getPersonID() );
        assertNotSame(findmother(person3).getSpouse(), person3.getMother());
        assertNull(findmother(person5));
        assertEquals(findfather(person3).getPersonID(), person5.getPersonID());
        assertEquals(findspouse(person3).getPersonID(), person2.getPersonID());
        assertEquals(findspouse(person2).getPersonID(), person3.getPersonID());
        assertNull(findchild(person2));
        assertEquals(findchild(person4).getPersonID(), person3.getPersonID());
        assertEquals(findchild(person5).getPersonID(), person3.getPersonID());
    }
    @Override
    public void tearDown() throws Exception
    {
        super.tearDown();
    }
}
