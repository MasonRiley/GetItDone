package com.example.getitdone;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class EventActivity extends SingleFragmentActivity {

    private static final String EXTRA_CRIME_ID = "crime_id";

    @Override
    public Fragment createFragment() {
        UUID crimeId = (UUID) getIntent().getSerializableExtra(EXTRA_CRIME_ID);

        return EventFragment.newInstance(crimeId);
    }

    public static Intent newIntent(Context packageContext, UUID memoryId)
    {
        Intent intent = new Intent(packageContext, EventActivity.class);
        intent.putExtra(EXTRA_CRIME_ID, memoryId);
        return intent;
    }
}
