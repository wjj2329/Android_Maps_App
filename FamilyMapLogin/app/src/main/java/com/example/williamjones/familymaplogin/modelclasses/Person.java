package com.example.williamjones.familymaplogin.modelclasses;

import com.amazon.geo.mapsv2.model.LatLng;

/**
 * Created by williamjones on 3/16/16.  A class that store info about people from the JSON Array.
 */
public class Person
{
    private String descendant;
    private String PersonID;
    private String firstName;
    private String lastName;
    private String gender;
    private String spouse;
    private String father;
    private String mother;
    private LatLng mylatlng;

    public Person(String descendant, String PersonID, String firstName, String lastName, String gender, String spouse, String father, String mother)
    {
        this.descendant=descendant;
        this.PersonID=PersonID;
        this.firstName=firstName;
        this.lastName=lastName;
        this.gender=gender;
        this.spouse=spouse;
        this.father=father;
        this.mother=mother;

    }
    //my setters and getters
    public LatLng getMylatlng() {
        return mylatlng;
    }

    public void setMylatlng(LatLng mylatlng) {
        this.mylatlng = mylatlng;
    }

    public String getFather()
    {
        return father;
    }

    public String getMother()
    {
        return mother;
    }

    public String getDescendant() {return descendant;}

    public void setDescendant(String descendant) {
        this.descendant = descendant;
    }

    public String getPersonID() {
        return PersonID;
    }

    public void setPersonID(String personID) {
        PersonID = personID;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getSpouse() {
        return spouse;
    }

    public void setSpouse(String spouse) {
        this.spouse = spouse;
    }
}
