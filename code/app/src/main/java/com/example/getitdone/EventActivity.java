package com.example.getitdone;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class EventActivity extends SingleFragmentActivity {

    private static final String EXTRA_EVENT_ID = "event_id";

    @Override
    public Fragment createFragment() {
        UUID eventId = (UUID) getIntent().getSerializableExtra(EXTRA_EVENT_ID);

        return EventFragment.newInstance(eventId);
    }

    public static Intent newIntent(Context packageContext, UUID eventId)
    {
        Intent intent = new Intent(packageContext, EventActivity.class);
        intent.putExtra(EXTRA_EVENT_ID, eventId);
        return intent;
    }
}
