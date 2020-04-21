package com.example.getitdone;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Switch;

import com.example.getitdone.Models.Memory;
import com.example.getitdone.Models.MemoryLab;

import java.io.ByteArrayOutputStream;
import java.util.Date;
import java.util.UUID;

import static android.app.Activity.RESULT_OK;

public class EventFragment extends Fragment {

    private static final String ARG_MEMORY_ID = "memory_id";
    private static final String DIALOG_DATE = "dialog_date";
    private static final int REQUEST_DATE = 0;
    private static final int REQUEST_IMAGE_CAPTURE = 1;

    private Memory mMemory;
    private EditText mTitleField;
    private ImageButton mCameraButton;
    private Button mDateButton;
    private Switch mFavoriteSwitch;
    private ImageView mMemoryImageView;

    public static EventFragment newInstance(UUID crimeID)
    {
        EventDB eb = new EventDB();
        eb.getRemoteConnection();
        Bundle args = new Bundle();
        args.putSerializable(ARG_MEMORY_ID, crimeID);

        EventFragment fragment = new EventFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        UUID memoryId = (UUID) getArguments().getSerializable(ARG_MEMORY_ID);
        mMemory = MemoryLab.get(getActivity()).getMemory(memoryId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_event, container, false);

        mTitleField = v.findViewById(R.id.event_title);
        mTitleField.setText(mMemory.getTitle());
        mTitleField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //this is useless
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mMemory.setTitle(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {
                //yeehaw!
            }
        });

        /*mMemoryImageView = v.findViewById(R.id.event_picture);
        if(mMemory.getMemoryPicture() != null)
        {
            mMemoryImageView.setImageBitmap(mMemory.getMemoryPicture());
        }

        mCameraButton = v.findViewById(R.id.button_camera);
        mCameraButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dispatchTakePictureIntent();
            }
        });*/

        mDateButton = v.findViewById(R.id.event_date);
        updateDate();
        mDateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager manager = getFragmentManager();
                DatePickerFragment dialog = DatePickerFragment.newInstance(mMemory.getDate());
                dialog.setTargetFragment(EventFragment.this, REQUEST_DATE);
                dialog.show(manager,DIALOG_DATE);
            }
        });

        mFavoriteSwitch = v.findViewById(R.id.event_favorited);
        mFavoriteSwitch.setChecked(mMemory.isFavorited());
        mFavoriteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                mMemory.setFavorited(b);
                MemoryLab.get(getActivity()).updateMemory(mMemory);
            }
        });

        return v;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        if(resultCode != RESULT_OK)
        {
            return;
        }
        if(requestCode == REQUEST_DATE)
        {
            Date date = (Date) data.getSerializableExtra(DatePickerFragment.EXTRA_DATE);
            mMemory.setDate(date);
            updateDate();
        }
        if(requestCode == REQUEST_IMAGE_CAPTURE) {
            Bundle extras = data.getExtras();
            Bitmap b = (Bitmap) extras.get("data");
            ByteArrayOutputStream blob = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.PNG, 0, blob);
            mMemory.setMemoryPicture(blob.toByteArray());
            mMemoryImageView.setImageBitmap(mMemory.getMemoryPicture());
            MemoryLab.get(getActivity()).updateMemory(mMemory);
        }
    }

    @Override
    public void onPause()
    {
        MemoryLab.get(getActivity()).updateMemory(mMemory);
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    private void updateDate() {
        mDateButton.setText(mMemory.getDate().toString());
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.fragment_event, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.delete_event:
                MemoryLab.get(getActivity()).deleteMemory(mMemory);
                getActivity().finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
        }
    }
}
