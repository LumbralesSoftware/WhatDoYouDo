package com.thedeveloperworldisyours.whatdoyoudoandroid.activities;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.thedeveloperworldisyours.whatdoyoudoandroid.R;
import com.thedeveloperworldisyours.whatdoyoudoandroid.dao.NodeDAO;
import com.thedeveloperworldisyours.whatdoyoudoandroid.models.Node;
import com.thedeveloperworldisyours.whatdoyoudoandroid.utils.Constants;

public class QuestionActivity extends ActionBarActivity implements View.OnClickListener {

    private TextView mTextStep;
    private TextView mTextQuestion;
    private TextView mTextAnswerA;
    private TextView mTextAnswerB;
    private NodeDAO mNodeDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_question);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        String nodeID = extras.getString(Constants.ID_INTENT_NODE);

        mNodeDAO = new NodeDAO(this);
        Node currentNode =  mNodeDAO.readWhere(Constants.COLUMN_ID, nodeID);

        mTextStep = (TextView) findViewById(R.id.activity_question_step);

        mTextQuestion = (TextView) findViewById(R.id.activity_question_step);

        mTextAnswerA = (TextView) findViewById(R.id.activity_question_answer_a);
        mTextAnswerB = (TextView) findViewById(R.id.activity_question_answer_b);

        mTextStep.setText(currentNode.getQuestion());
        mTextQuestion.setText(currentNode.getText());
        mTextAnswerA.setText(currentNode.getAnswer1());
        mTextAnswerB.setText(currentNode.getAnswer2());


    }

    private void nextEvent()
    {
        // Replace by get current node
        Node currentNode = new Node();
        String idNextNode;
        // Click on Answer 1
        if (true) {
            idNextNode = currentNode.getNode1();
        } else {
            idNextNode = currentNode.getNode2();
        }
        Node nextNode = mNodeDAO.readWhere(Constants.COLUMN_ID, idNextNode);

        Intent intent;
        // Send next node to activity
        switch (nextNode.getStatus()) {
            case Constants.STATUS_WIN:
                intent = new Intent(this, IntroActivity.class);
                startActivity(intent);
                finish();
                break;
            case Constants.STATUS_LOSE:
                intent = new Intent(this, IntroActivity.class);
                startActivity(intent);
                finish();
                break;
            case Constants.STATUS_CONTINUE:
                intent = new Intent(this, QuestionActivity.class);
                startActivity(intent);
                finish();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_question, menu);
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
            case R.id.activity_question_a_button:
                break;
            case R.id.activity_question_b_button:
                break;
            default:
        }
    }
}
