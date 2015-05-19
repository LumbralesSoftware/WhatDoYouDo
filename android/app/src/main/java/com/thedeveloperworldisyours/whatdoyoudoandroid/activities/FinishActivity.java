package com.thedeveloperworldisyours.whatdoyoudoandroid.activities;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;
import com.google.android.gms.analytics.HitBuilders;
import com.google.android.gms.analytics.Tracker;
import com.thedeveloperworldisyours.whatdoyoudoandroid.MyApp;
import com.thedeveloperworldisyours.whatdoyoudoandroid.R;
import com.thedeveloperworldisyours.whatdoyoudoandroid.dao.NodeDAO;
import com.thedeveloperworldisyours.whatdoyoudoandroid.models.Node;
import com.thedeveloperworldisyours.whatdoyoudoandroid.utils.Constants;
import com.thedeveloperworldisyours.whatdoyoudoandroid.utils.Utils;

public class FinishActivity extends ActionBarActivity implements View.OnClickListener {

    private boolean mWin;
    private TextView mMessage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.finish_acitivity);



        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        String nodeID = extras.getString(Constants.ID_INTENT_FINISH);
        String booleanString = extras.getString(Constants.ID_INTENT_BOOLEAN);

        mWin = Boolean.parseBoolean(booleanString);

        NodeDAO nodeDAO = new NodeDAO(this);
        Node currentNode =  nodeDAO.readWhere(Constants.COLUMN_ID, nodeID);

        mMessage = (TextView) findViewById(R.id.activity_finish_text);
        mMessage.setText(currentNode.getText());

        if (mWin){
        ImageView imageView = (ImageView) findViewById(R.id.activity_finish_imageView);
            imageView.setImageDrawable(getResources().getDrawable(R.drawable.win));
        }

        Button share = (Button) findViewById(R.id.activity_finish_share_button);
        share.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        googleAnalitys();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_finish_share_button:
                Utils.shared(this,"",mWin);
                break;
        }
    }

    public void googleAnalitys(){
        // Get tracker.
        Tracker t =((MyApp) getApplication()).getTracker(MyApp.TrackerName.APP_TRACKER);


        // Set screen name.
        t.setScreenName(getString(R.string.title_activity_finish_acitivity));

        // Send a screen view.
        t.send(new HitBuilders.AppViewBuilder().build());
    }

    /**
     * this method was created for animation between activities
     */
    public void cameback()
    {
        finish();
        overridePendingTransition(R.anim.right, R.anim.left);
    }

    @Override
    public void onBackPressed()
    {
        cameback();
    }

}
