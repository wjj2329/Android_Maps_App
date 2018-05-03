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

import java.util.List;
import java.util.Set;

/**
 * Created by williamjones on 3/31/16.
 */
public class AdapterForSearch extends RecyclerView.Adapter<AdapterForSearch.ViewHolder> {
    private List<String> lines;
    private List<String> genders;

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        private  TextView mTextView;
        private  ImageView myimage;

        public void changetoperson()
        {
            Intent myintent;
            myintent=new Intent(InfoSingleton.singleton.getMysearch(), PersonActivity.class);
            InfoSingleton.singleton.getMysearch().startActivity(myintent);
        }

        public void changetomap()
        {
            Intent myintent;
            myintent=new Intent(InfoSingleton.singleton.getMysearch(), MapsActivity.class);
            InfoSingleton.singleton.getMysearch().startActivity(myintent);
        }

        public ViewHolder(LinearLayout v) {
            super(v);
            myimage=(ImageView)v.findViewById(R.id.pictureicon);
            mTextView = (TextView)v.findViewById(R.id.mytextviewsearch);
            v.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v)
                {
                    if(!mTextView.getText().toString().contains("\n"))//this is the condition if I need to move to a person or map activity.  The new line means I must have two lines which means I need a person
                    {
                        String personname = ((TextView) v.findViewById(R.id.mytextviewsearch)).getText().toString();
                        String[] splited = personname.split(" ");
                        String firstname = splited[0];
                        Set<String> mykey = InfoSingleton.singleton.getPersoninfo().keySet();
                        for (String s : mykey) {
                            if (InfoSingleton.singleton.getPersoninfo().get(s).getFirstName().equals(firstname)) {
                                MapFragmentForHistory.CurrentPerson = InfoSingleton.singleton.getPersoninfo().get(s);
                                changetoperson();
                            }
                        }
                    }
                    else
                    {
                        String personname=((TextView)v.findViewById(R.id.mytextviewsearch)).getText().toString();
                        String [] splited=personname.split(" ");
                        String firstname=splited[1];
                        Set<String> mykey= InfoSingleton.singleton.getPersoninfo().keySet();
                        for(String s:mykey)
                        {
                            if(InfoSingleton.singleton.getPersoninfo().get(s).getFirstName().equals(firstname))
                            {
                                MapFragmentForHistory.CurrentPerson= InfoSingleton.singleton.getPersoninfo().get(s);
                                changetomap();
                            }
                        }
                    }
                }
            });
        }

        public void bindLine(final String line, final String gender)//binds text view and image
        {
            mTextView.setText(line);
            if(gender.equals("m"))
            {
                myimage.setImageResource(R.drawable.male);
            }
            else if(gender.equals("f"))
            {
                myimage.setImageResource(R.drawable.female);
            }
            else
            {
                myimage.setImageResource(R.drawable.pin);
            }
        }
    }

    public AdapterForSearch(List<String> lines, List<String> gender)
    {
        this.genders=gender;
        this.lines = lines;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_search_view, parent, false);
        ViewHolder vh = new ViewHolder((LinearLayout) v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.bindLine(lines.get(position), genders.get(position));
    }

    @Override
    public int getItemCount() {
        return lines.size();
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView)
    {
        super.onAttachedToRecyclerView(recyclerView);
    }

    public void addLine(String line, String gender)
    {
        if(line!=null&&gender!=null)
        {
            genders.add(gender);
            lines.add(line);
            notifyDataSetChanged();
        }
    }

    public void clear()
    {
        for(int i=0; i<lines.size(); i++)
        {
            genders.remove(i);
            lines.remove(i);
        }
        lines.clear();
        genders.clear();
    }

}


