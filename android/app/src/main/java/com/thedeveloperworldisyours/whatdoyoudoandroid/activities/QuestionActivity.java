package com.thedeveloperworldisyours.whatdoyoudoandroid.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
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
    private Node mCurrentNode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.question_activity);

        Bundle extras = getIntent().getExtras();
        if (extras == null) {
            return;
        }
        String nodeID = extras.getString(Constants.ID_INTENT_NODE);

        mNodeDAO = new NodeDAO(this);
        mCurrentNode =  mNodeDAO.readWhere(Constants.COLUMN_ID, nodeID);

        mTextStep = (TextView) findViewById(R.id.activity_question_step);

        mTextQuestion = (TextView) findViewById(R.id.activity_question_step);

        mTextAnswerA = (TextView) findViewById(R.id.activity_question_answer_a);
        mTextAnswerB = (TextView) findViewById(R.id.activity_question_answer_b);

        Button AButton = (Button) findViewById(R.id.activity_question_a_button);
        Button BButton = (Button) findViewById(R.id.activity_question_b_button);

        AButton.setOnClickListener(this);
        BButton.setOnClickListener(this);

        mTextStep.setText(mCurrentNode.getQuestion());
        mTextQuestion.setText(mCurrentNode.getText());
        mTextAnswerA.setText(mCurrentNode.getAnswer1());
        mTextAnswerB.setText(mCurrentNode.getAnswer2());


    }

    private void nextEvent(String nodeID)
    {
        Node nextNode = mNodeDAO.readWhere(Constants.COLUMN_ID, nodeID);

        Intent intent;
        // Send next node to activity
        switch (nextNode.getStatus()) {
            case Constants.STATUS_WIN:
                intent = new Intent(this, FinishActivity.class);
                intent.putExtra(Constants.ID_INTENT_FINISH, nodeID);
                startActivity(intent);
                break;
            case Constants.STATUS_LOSE:
                intent = new Intent(this, FinishActivity.class);
                intent.putExtra(Constants.ID_INTENT_FINISH, nodeID);
                startActivity(intent);
                break;
            case Constants.STATUS_CONTINUE:
                intent = new Intent(this, QuestionActivity.class);
                intent.putExtra(Constants.ID_INTENT_NODE, nodeID);
                startActivity(intent);
        }
                finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.activity_question_a_button:
                nextEvent(mCurrentNode.getNode1());
                break;
            case R.id.activity_question_b_button:
                nextEvent(mCurrentNode.getNode2());
                break;
            default:
        }
    }
}
