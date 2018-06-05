package com.germiyanoglu.android.unigramproject.settings;

import android.content.Context;
import android.opengl.EGLExt;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.germiyanoglu.android.unigramproject.R;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

//  TODO 89 ) Creating SettingsActivity
public class SettingsActivity extends AppCompatActivity {

    private static final String TAG = SettingsActivity.class.getName();

    @BindView(R.id.settings_content_items)
    ListView listView;

    @BindView(R.id.settings_top_bar_toolbar)
    Toolbar toolbar;

    private Context mContext;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);
        Log.d(TAG,"onCreate is starting");
        mContext = SettingsActivity.this;

        //  TODO 91 ) Calling createSettingsItems
        createSettingsItems();

        //  TODO 92 ) Calling enableBackButton
        enableBackButton();
    }


    //  TODO 90 ) Creating settings items
    private void createSettingsItems(){
        Log.d(TAG,"createSettingsItems is starting");

        ArrayList<String> settingsItem = new ArrayList<>();

        settingsItem.add("Edit Profile");
        settingsItem.add("Sign out");
        // More is coming

        ArrayAdapter adapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_list_item_1, settingsItem);
        listView.setAdapter(adapter);


    }


    private void enableBackButton(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
