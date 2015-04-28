package com.thedeveloperworldisyours.whatdoyoudoandroid.activities;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.thedeveloperworldisyours.whatdoyoudoandroid.R;
import com.thedeveloperworldisyours.whatdoyoudoandroid.adapters.MySimpleArrayAdapter;
import com.thedeveloperworldisyours.whatdoyoudoandroid.dao.MissionDAO;
import com.thedeveloperworldisyours.whatdoyoudoandroid.dao.NodeDAO;
import com.thedeveloperworldisyours.whatdoyoudoandroid.models.Mission;
import com.thedeveloperworldisyours.whatdoyoudoandroid.models.Node;
import com.thedeveloperworldisyours.whatdoyoudoandroid.utils.Constants;
import com.thedeveloperworldisyours.whatdoyoudoandroid.utils.Utils;
import com.thedeveloperworldisyours.whatdoyoudoandroid.webservice.Client;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    private MissionDAO mMissionDAO;
    private NodeDAO mNodeDAO;
    private ListView mListView;
    private ProgressDialog mProgress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_activity);

        mListView = (ListView) findViewById(R.id.main_activity_listView);
        mListView.setOnItemClickListener(this);

        mProgress = new ProgressDialog(this,R.style.Transparent);

        mMissionDAO = new MissionDAO(this);
        mNodeDAO = new NodeDAO(this);

        getInfo();


    }

    public void getInfo(){
        if(exitsDB()){
            getInfoMissionFromDB();
            buildList(mMissionDAO.readAllAsc());
        }else if (Utils.isOnline(this)) {

            getMissions();
        }else{
            Toast.makeText(this,R.string.no_connection,Toast.LENGTH_SHORT).show();
        }
    }

    public void clearDB(){
        mMissionDAO.deleteAll();
        mNodeDAO.deleteAll();
    }

    public void getInfoMissionFromDB(){
        List<Mission> listMision= new ArrayList<Mission>();
        listMision=mMissionDAO.readAllAsc();
    }

    public boolean exitsDB(){
        int missions = mMissionDAO.getCount();
        int nodes = mNodeDAO.getCount();

        return missions > 0 && nodes > 0;
    }

    public void buildList(List<Mission> listMision){
        final MySimpleArrayAdapter adapter = new MySimpleArrayAdapter(this, (ArrayList<Mission>) listMision);
        mListView.setAdapter(adapter);
    }

    public void getMissions(){
        mProgress.show();
        Callback<List<Mission>> callback = new Callback<List<Mission>>() {
            @Override
            public void success(List<Mission> missions, Response response) {
                Log.v("Client", "success");
                clearDB();
                insertMission(missions);
            }

            @Override
            public void failure(RetrofitError error)
            {
                mProgress.cancel();
                Log.v("Client", "failure");
            }

        };
        Client.initRestAdapter().getMissions(Utils.checkLanguaje(), callback);
    }

    public void insertMission(List<Mission> listMission){
        for (int i=0; i< listMission.size();i++){
            mMissionDAO.create(listMission.get(i));
            getNodes(listMission.get(i).getId());
        }
        getInfoMissionFromDB();
        buildList(mMissionDAO.readAllAsc());
    }

    public void getNodes(String idMission){
        Callback<List<Node>> callback = new Callback<List<Node>>() {
            @Override
            public void success(List<Node> nodes, Response response) {
                Log.v("Client", "success");
                insertNodes(nodes);
                mProgress.cancel();

            }

            @Override
            public void failure(RetrofitError error) {
                mProgress.cancel();
                Log.v("Client", "failure");
            }
        };
        Client.initRestAdapter().getNodes(idMission,callback);
    }

    public void insertNodes(List<Node> listNodes){
        for (int i=0;i<listNodes.size();i++){
            mNodeDAO.create(listNodes.get(i));
        }
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

        switch (item.getItemId()){
        //noinspection SimplifiableIfStatement
            case R.id.action_refresh:
                if (Utils.isOnline(this)) {
                    getMissions();
                }else{
                    Toast.makeText(this,R.string.no_connection,Toast.LENGTH_SHORT).show();
                }
                break;
            case  R.id.action_rate_app:
                appRate();
                break;
            case R.id.action_about_us:
                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.ABOUT_US));
                startActivity(browserIntent);
                break;
            case R.id.action_our_app:
                Intent browserIntentApps = new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.OUR_APPS));
                startActivity(browserIntentApps);
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    public void appRate(){
        AlertDialog.Builder alert = new AlertDialog.Builder(MainActivity.this);
        alert.setTitle(getString(R.string.alert_dialog_rate_app_title));
        alert.setMessage(getString(R.string.alert_dialog_rate_app_continue));
        alert.setCancelable(false);
        alert.setPositiveButton(getString(R.string.alert_dialog_rate_app_positive),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(Constants.APP_NAME)));
                    }
                }
        );
        alert.setNegativeButton(getString(R.string.alert_dialog_rate_app_cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
    }

    public void startMission(String id){
        Intent intent = new Intent(this, IntroActivity.class);
        intent.putExtra(Constants.ID_INTENT_MISSION, id);
        startActivity(intent);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        String tag = view.getTag().toString();
        startMission(tag);
    }

}
