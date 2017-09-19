package edu.orangecoastcollege.cs273.vnguyen468.paintestimator;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    // Member Variables for views
    private EditText mLengthEditText;
    private EditText mWidthEditText;
    private EditText mHeightEditText;
    private EditText mWindowEditText;
    private EditText mDoorEditText;

    private TextView mGallonTextView;


    //Member for the Model
    private InteriorRoom mRoom = new InteriorRoom();

    //Member for SharedPreferences;
    private SharedPreferences mPrefs;

    private void initializeViews()
    {
        mLengthEditText = (EditText) findViewById(R.id.lengthEditText);
        mWidthEditText = (EditText) findViewById(R.id.widthEditText);
        mHeightEditText = (EditText) findViewById(R.id.heightEditText);
    }

    private void loadSharedPreferences()
    {
        mPrefs = getSharedPreferences("edu.orangecoastcollege.cs273.vnguyen468.PaintEstimator",MODE_PRIVATE);
        if (mPrefs != null)
        {
            //Load all the room information
            mRoom.setDoors(mPrefs.getInt("doors",0));
            mDoorEditText.setText(String.valueOf(mRoom.getDoors()));
            mRoom.setHeight(mPrefs.getFloat("height",0.0f));
            mHeightEditText.setText(String.valueOf(mRoom.getHeight()));
        }
    }

    private void saveSharedPreferences()
    {
        SharedPreferences.Editor editor = mPrefs.edit();
        editor.clear();
        editor.putFloat("length",mRoom.getLength());
        editor.putFloat("width",mRoom.getWidth());
        editor.putFloat("height",mRoom.getHeight());

        editor.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeViews();
        loadSharedPreferences();

    }

    protected void computeGallons(View v)
    {
        //Update Model
        mRoom.setLength(Float.parseFloat(mLengthEditText.getText().toString()));
        mRoom.setWidth(Float.parseFloat(mWidthEditText.getText().toString()));

        mGallonTextView.setText(getString(R.string.interior_surface_area_text) + mRoom.totalSurfaceArea()
        + "\n" + getString(R.string.gallons_needed_text));
        saveSharedPreferences();
    }

    protected void goToHelp(View v)
    {
        //construct as EXPLICIT Intent to go to Help Activity
        //Intent: specify where to start (context) and where you are going (next Activity)
        Intent helpIntent = new Intent(this, HelpActivity.class);
        helpIntent.putExtra("gallons", mRoom.gallonsOfPaintRequired());
        startActivity(helpIntent);


    }
}
