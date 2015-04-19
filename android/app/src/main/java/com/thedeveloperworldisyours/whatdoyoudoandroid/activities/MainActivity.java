package com.thedeveloperworldisyours.whatdoyoudoandroid.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.thedeveloperworldisyours.whatdoyoudoandroid.R;
import com.thedeveloperworldisyours.whatdoyoudoandroid.dao.MissionDAO;
import com.thedeveloperworldisyours.whatdoyoudoandroid.models.Mission;


public class MainActivity extends ActionBarActivity implements View.OnClickListener {
    private Button mMission;
    private MissionDAO mMissionDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMission = (Button) findViewById(R.id.main_activity_mission_button);
        mMission.setOnClickListener(this);

        mMissionDAO = new MissionDAO(this);
        Mission mission = new Mission();
        mission.setId(23423);
        mission.setName("asdf");
        mission.setText("lalalala");
        mission.setBeginning(123);
        mMissionDAO.create(mission);
        Mission r = mMissionDAO.readWhere("_id", "23423");
        Log.v("MainActivity",r.getName());

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_activity_mission_button:
                startMission();
                break;
            default:
        }

    }

    public void startMission(){
        Intent intent = new Intent(this, IntroActivity.class);
        startActivity(intent);
    }
}
