package com.example.getitdone;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.getitdone.Models.Event;
import com.example.getitdone.Models.EventLab;

import java.util.ArrayList;
import java.util.List;

public class EventListFragment extends Fragment {

    private ImageView mThumbnail;

    private class EventHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private Event mEvent;

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mFavorited;

        public EventHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.list_item_event, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.event_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.event_date);
            mThumbnail = (ImageView) itemView.findViewById(R.id.event_picture);
            mFavorited = (ImageView) itemView.findViewById(R.id.event_favorited);
        }

        public void bind(Event event)
        {
            mEvent = event;
            mTitleTextView.setText(mEvent.getTitle());
            mDateTextView.setText(mEvent.getDate().toString());
            mFavorited.setVisibility(event.isFavorited() ? View.VISIBLE : View.INVISIBLE);
        }

        @Override
        public void onClick(View view) {
            Intent intent = EventPagerActivity.newIntent(getActivity(), mEvent.getId());
            startActivity(intent);
        }
    }

    private class EventAdapter extends RecyclerView.Adapter<EventHolder>
    {
        private List<Event> mEvents;

        public EventAdapter(List<Event> events)
        {
            mEvents = events;
        }

        @NonNull
        @Override
        public EventHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            Log.d("adapter", "Creating");
            return new EventHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull EventHolder eventHolder, int i) {
            Event event = mEvents.get(i);
            Log.d("adapter", "Binding");
            eventHolder.bind(event);
        }

        public void setEvents(List<Event> events){
            mEvents = events;
        }

        @Override
        public int getItemCount() {
            return mEvents.size();
        }
    }

    private boolean sorted = false;
    private RecyclerView mEventRecyclerView;
    private EventAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event_list, container, false);
        mEventRecyclerView = (RecyclerView) v.findViewById(R.id.event_recycler_view);
        mEventRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        boolean sorted = false;

        return v;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
    {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_event_list, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch(item.getItemId())
        {
            case R.id.new_event:
                Event event = new Event();
                EventLab.get(getActivity()).addEvent(event);
                Intent intent = EventPagerActivity.newIntent(getActivity(), event.getId());
                startActivity(intent);
                return true;
            case R.id.show_alerted:
                sorted = true;
                updateUI();
                return true;
            case R.id.show_all:
                sorted = false;
                updateUI();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onResume()
    {
        Log.wtf("ATTENTION:", "MADE IT THROUGH~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        updateUI();
        super.onResume();
    }

    private void updateUI()
    {
        EventLab eventLab = EventLab.get(getActivity());
        List<Event> events = eventLab.getEvents();

        if(mAdapter == null) {
            mAdapter = new EventAdapter(events);
            mEventRecyclerView.setAdapter(mAdapter);
        }
        else if(sorted == true)
        {
            List<Event> sortedMemories = new ArrayList<>();

            //I would normally use a better algorithm to sort a random list
            for(Event c : events)
            {
                if(c.isFavorited() == true)
                    sortedMemories.add(c);
                if(c.isFavorited() == true && c.getMemoryPicture() != null)
                    mThumbnail.setImageBitmap(c.getMemoryPicture());
            }
            mAdapter.setEvents(sortedMemories);
            mAdapter.notifyDataSetChanged();
        }
        else
        {
            for(Event c : events)
            {
                if(c.getMemoryPicture() != null)
                    mThumbnail.setImageBitmap(c.getMemoryPicture());
            }
            mAdapter.setEvents(events);
            mAdapter.notifyDataSetChanged();
        }
    }
}
