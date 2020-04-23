package com.example.getitdone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import com.example.getitdone.Models.Event;
import com.example.getitdone.Models.EventLab;

import java.util.List;
import java.util.UUID;

public class EventPagerActivity extends AppCompatActivity {

    private static final String EXTRA_CRIME_ID = "event_id";

    private ViewPager mViewPager;
    private List<Event> mMemories;

    public static Intent newIntent(Context packageContext, UUID crimeId){
        Intent intent = new Intent(packageContext, EventPagerActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, crimeId);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_pager);

        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        mViewPager = (ViewPager) findViewById(R.id.event_view_pager);
        mMemories = EventLab.get(this).getEvents();
        FragmentManager fragmentManager = getSupportFragmentManager();
        mViewPager.setAdapter(new FragmentStatePagerAdapter(fragmentManager) {
            @Override
            public Fragment getItem(int position) {
                Event event = mMemories.get(position);
                return EventFragment.newInstance(event.getId());
            }

            @Override
            public int getCount() {
                return mMemories.size();
            }
        });

        for(int i = 0; i < mMemories.size(); ++i)
        {
            if(mMemories.get(i).getId().equals(crimeId))
            {
                mViewPager.setCurrentItem(i);
                break;
            }
        }
    }
}
