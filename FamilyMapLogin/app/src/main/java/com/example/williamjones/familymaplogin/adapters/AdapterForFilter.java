package com.example.williamjones.familymaplogin.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.example.williamjones.familymaplogin.R;
import com.example.williamjones.familymaplogin.modelclasses.InfoSingleton;

import java.util.List;

/**
 * Created by williamjones on 3/29/16.
 */

public class AdapterForFilter extends RecyclerView.Adapter<AdapterForFilter.ViewHolder>{
    private List<String> lines;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AdapterForFilter that = (AdapterForFilter) o;

        return lines != null ? lines.equals(that.lines) : that.lines == null;

    }

    @Override
    public int hashCode() {
        return lines != null ? lines.hashCode() : 0;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder
    {
        // each data item is just a string in this case
        private TextView mTextView;
        private Switch mswitch;
        public ViewHolder(LinearLayout v) {
            super(v);
            mTextView = (TextView)v.findViewById(R.id.mytextview);
            mswitch=(Switch)v.findViewById(R.id.myswitch);
        }
        public void bindLine(final String line)
        {
            mTextView.setText(line);
            mswitch.setChecked(InfoSingleton.singleton.getEventswitches().get(line));
            mswitch.setOnCheckedChangeListener( new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    InfoSingleton.singleton.getEventswitches().put(line, isChecked);
                    InfoSingleton.singleton.getMymap().clearmypins();
                }
            });
        }
    }

    public AdapterForFilter(List<String> lines) {
        this.lines = lines;
    }

    @Override
    public AdapterForFilter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.my_text_view, parent, false);
        ViewHolder vh = new ViewHolder((LinearLayout) v);
        return vh;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position)
    {
        holder.bindLine(lines.get(position));
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

    public void addLine(String line)
    {
        if(line!=null) {
            lines.add(line);
            notifyDataSetChanged();
        }
    }

}


