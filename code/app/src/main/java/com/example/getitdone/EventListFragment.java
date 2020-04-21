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

import com.example.getitdone.Models.Memory;
import com.example.getitdone.Models.MemoryLab;

import java.util.ArrayList;
import java.util.List;

public class EventListFragment extends Fragment {

    private ImageView mThumbnail;

    private class CrimeHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {
        private Memory mMemory;

        private TextView mTitleTextView;
        private TextView mDateTextView;
        private ImageView mFavorited;

        public CrimeHolder(LayoutInflater inflater, ViewGroup parent)
        {
            super(inflater.inflate(R.layout.list_item_event, parent, false));
            itemView.setOnClickListener(this);

            mTitleTextView = (TextView) itemView.findViewById(R.id.event_title);
            mDateTextView = (TextView) itemView.findViewById(R.id.event_date);
            //mThumbnail = (ImageView) itemView.findViewById(R.id.event_thumbnail);
            mFavorited = (ImageView) itemView.findViewById(R.id.event_favorited);
        }

        public void bind(Memory memory)
        {
            mMemory = memory;
            mTitleTextView.setText(mMemory.getTitle());
            mDateTextView.setText(mMemory.getDate().toString());
            mFavorited.setVisibility(memory.isFavorited() ? View.VISIBLE : View.INVISIBLE);
        }

        @Override
        public void onClick(View view) {
            Intent intent = EventPagerActivity.newIntent(getActivity(), mMemory.getId());
            startActivity(intent);
        }
    }

    private class CrimeAdapter extends RecyclerView.Adapter<CrimeHolder>
    {
        private List<Memory> mMemories;

        public CrimeAdapter(List<Memory> memories)
        {
            mMemories = memories;
        }

        @NonNull
        @Override
        public CrimeHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            Log.d("adapter", "Creating");
            return new CrimeHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull CrimeHolder crimeHolder, int i) {
            Memory memory = mMemories.get(i);
            Log.d("adapter", "Binding");
            crimeHolder.bind(memory);
        }

        public void setCrimes(List<Memory> memories){
            mMemories = memories;
        }

        @Override
        public int getItemCount() {
            return mMemories.size();
        }
    }

    private boolean sorted = false;
    private RecyclerView mCrimeRecyclerView;
    private CrimeAdapter mAdapter;

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
        mCrimeRecyclerView = (RecyclerView) v.findViewById(R.id.event_recycler_view);
        mCrimeRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

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
                Memory memory = new Memory();
                MemoryLab.get(getActivity()).addMemory(memory);
                Intent intent = EventPagerActivity.newIntent(getActivity(), memory.getId());
                startActivity(intent);
                return true;
            case R.id.show_favorited:
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
        MemoryLab memoryLab = MemoryLab.get(getActivity());
        List<Memory> memories = memoryLab.getMemories();

        if(mAdapter == null) {
            mAdapter = new CrimeAdapter(memories);
            mCrimeRecyclerView.setAdapter(mAdapter);
        }
        else if(sorted == true)
        {
            List<Memory> sortedMemories = new ArrayList<>();

            //I would normally use a better algorithm to sort a random list
            for(Memory c : memories)
            {
                if(c.isFavorited() == true)
                    sortedMemories.add(c);
                if(c.isFavorited() == true && c.getMemoryPicture() != null)
                    mThumbnail.setImageBitmap(c.getMemoryPicture());
            }
            mAdapter.setCrimes(sortedMemories);
            mAdapter.notifyDataSetChanged();
        }
        else
        {
            for(Memory c : memories)
            {
                if(c.getMemoryPicture() != null)
                    mThumbnail.setImageBitmap(c.getMemoryPicture());
            }
            mAdapter.setCrimes(memories);
            mAdapter.notifyDataSetChanged();
        }
    }
}
