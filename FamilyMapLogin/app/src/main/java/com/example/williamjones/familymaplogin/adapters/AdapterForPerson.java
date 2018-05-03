package com.example.williamjones.familymaplogin.adapters;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.williamjones.familymaplogin.R;
import com.example.williamjones.familymaplogin.fragments.MapFragmentForHistory;
import com.example.williamjones.familymaplogin.activities.MapsActivity;
import com.example.williamjones.familymaplogin.activities.PersonActivity;
import com.example.williamjones.familymaplogin.modelclasses.InfoSingleton;
import com.example.williamjones.familymaplogin.modelclasses.Person;

import java.util.List;

/**
 * Created by williamjones on 4/7/16.
 */
public class AdapterForPerson extends RecyclerView.Adapter<AdapterForPerson.ViewHolder>{
    private List<String> events;
    private List<String> locations;
    private List<String> icons;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView nameorevent;
        private TextView locationorrelation;
        private ImageView genderoricon;

        public void changetoperson()
        {
            Intent myintent;
            myintent=new Intent(InfoSingleton.singleton.getMyperson(), PersonActivity.class);
            InfoSingleton.singleton.getMyperson().startActivity(myintent);
        }

        public void changetomap()
        {
            Intent myintent;
            myintent=new Intent(InfoSingleton.singleton.getMyperson(), MapsActivity.class);
            InfoSingleton.singleton.getMyperson().startActivity(myintent);
        }

        public ViewHolder(LinearLayout v)
        {
            super(v);
            nameorevent = (TextView)v.findViewById(R.id.eventorname);
            locationorrelation=(TextView)v.findViewById(R.id.locationorparent);
            genderoricon=(ImageView)v.findViewById(R.id.picture);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if(locationorrelation.getText().toString().equals("Mother")||locationorrelation.getText().toString().equals("Father"))
                    {
                        Person newcurrentperson;
                        if(locationorrelation.getText().toString().equals("Mother"))
                        {
                            newcurrentperson= InfoSingleton.singleton.getPersoninfo().get(MapFragmentForHistory.CurrentPerson.getMother());
                        }
                        else
                        {
                            newcurrentperson= InfoSingleton.singleton.getPersoninfo().get(MapFragmentForHistory.CurrentPerson.getFather());
                        }
                        //Updates my public static Current Person so that the new person activity will load it's info.
                        MapFragmentForHistory.CurrentPerson=newcurrentperson;
                        changetoperson();
                    }
                    else
                    {
                        changetomap();
                    }
                }
            });
        }
        public void bindLine(final String name_event, final String location_relation, final String gender)
        {
            nameorevent.setText(name_event);
            locationorrelation.setText(location_relation);
            if(gender.equals("m"))
            {
                genderoricon.setImageResource(R.drawable.male);
            }
            if(gender.equals("f"))
            {
                genderoricon.setImageResource(R.drawable.female);
            }
        }
    }

    public AdapterForPerson(List<String> names, List<String> events, List<String> locations, List<String> icons, List<String> pins, List<String> relations)
    {
        this.events=events;
        this.locations=locations;
        this.icons=icons;
    }

    @Override
    public AdapterForPerson.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        // create a new view
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.person_layout, parent, false);
        // set the view's size, margins, paddings and layout parameters
        //...
        ViewHolder vh = new ViewHolder((LinearLayout) v);

        return vh;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdapterForPerson that = (AdapterForPerson) o;

        if (!events.equals(that.events)) return false;
        if (!locations.equals(that.locations)) return false;
        return icons.equals(that.icons);

    }

    @Override
    public int hashCode() {
        int result = events.hashCode();
        result = 31 * result + locations.hashCode();
        result = 31 * result + icons.hashCode();
        return result;
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position)

    {
            holder.bindLine(events.get(position), locations.get(position), icons.get(position));
    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount()
    {
        return events.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void addLine(String nameorevent, String relationorlocation, String iconorgender )
    {
            events.add(nameorevent);
            locations.add(relationorlocation);
            icons.add(iconorgender);
            notifyDataSetChanged();
    }

}

