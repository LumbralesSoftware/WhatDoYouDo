package com.thedeveloperworldisyours.whatdoyoudoandroid.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.thedeveloperworldisyours.whatdoyoudoandroid.R;
import com.thedeveloperworldisyours.whatdoyoudoandroid.dao.NodeDAO;
import com.thedeveloperworldisyours.whatdoyoudoandroid.models.Node;
import com.thedeveloperworldisyours.whatdoyoudoandroid.utils.Constants;
import com.thedeveloperworldisyours.whatdoyoudoandroid.utils.Utils;

import java.util.Random;

public class QuestionActivity extends ActionBarActivity implements View.OnClickListener {

    private TextView mTextStep;
    private TextView mTextQuestion;
    private TextView mTextAnswerA;
    private TextView mTextAnswerB;
    private TextView mNumberText;
    private final Handler mHandler = new Handler();
    private Runnable mRunnable;
    private LinearLayout mLinearLayoutMain;
    private NodeDAO mNodeDAO;
    private Node mCurrentNode;
    private String mNodoFinish;

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
                intent.putExtra(Constants.ID_INTENT_BOOLEAN,"true");
                startActivity(intent);
                finish();
                break;
            case Constants.STATUS_LOSE:
                shared(nodeID);
                break;
            case Constants.STATUS_CONTINUE:
                intent = new Intent(this, QuestionActivity.class);
                intent.putExtra(Constants.ID_INTENT_NODE, nodeID);
                startActivity(intent);
                finish();
        }

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

    public void shared(final String nodeID)
    {
        mNodoFinish= nodeID;


        String array[] = getResources().getStringArray(R.array.arrayLose);

        Random rand = new Random();
        int randomNum = rand.nextInt((array.length - 1) + 1) + 0;

        final String stringShared = array[randomNum];
        AlertDialog.Builder alert = new AlertDialog.Builder(QuestionActivity.this);
        alert.setTitle(getString(R.string.alert_dialog_title));
        alert.setMessage(getString(R.string.alert_dialog_continue));
        alert.setCancelable(false);

        alert.setPositiveButton(R.string.alert_dialog_shared_possitive,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Utils.sharedWhatsapp(QuestionActivity.this, stringShared);
                    }
                }
        );
        alert.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK||event.getAction() == KeyEvent.ACTION_UP||!event.isCanceled()||event.getAction() == KeyEvent.ACTION_DOWN) {
                    goToFinishActivity();
                    dialog.cancel();
                    return true;
                }
                return false;
            }
        });
        alert.setNegativeButton(getString(R.string.alert_dialog_shared_cancel),
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        goToFinishActivity();
                    }
                }
        );
        AlertDialog alertDialog = alert.create();
        alertDialog.show();
        mNumberText = (TextView) alertDialog.findViewById(android.R.id.message);
        timer();

    }

    public void goToFinishActivity(){
        Intent intent = new Intent(QuestionActivity.this, FinishActivity.class);
        intent.putExtra(Constants.ID_INTENT_FINISH, mNodoFinish);
        intent.putExtra(Constants.ID_INTENT_BOOLEAN,"false");
        startActivity(intent);
        finish();
    }

    public String builderString(String number){
        StringBuilder builder = new StringBuilder();
        builder.append(getString(R.string.alert_dialog_continue));
        builder.append("    ");
        builder.append(number);
        return builder.toString();
    }

    public void timer() {

        mRunnable = new Runnable() {
            int x = 1;

            public void run() {
                switch (x) {
                    case 1:
                        mNumberText.setText(builderString(getString(R.string.activity_intro_nine)));
                        break;
                    case 2:
                        mNumberText.setText(builderString(getString(R.string.activity_intro_eight)));
                        break;
                    case 3:
                        mNumberText.setText(builderString(getString(R.string.activity_intro_seven)));
                        break;
                    case 4:
                        mNumberText.setText(builderString(getString(R.string.activity_intro_six)));
                        break;
                    case 5:
                        mNumberText.setText(builderString(getString(R.string.activity_intro_five)));
                        break;
                    case 6:
                        mNumberText.setText(builderString(getString(R.string.activity_intro_four)));
                        break;
                    case 7:
                        mNumberText.setText(builderString(getString(R.string.activity_intro_three)));
                        break;
                    case 8:
                        mNumberText.setText(builderString(getString(R.string.activity_intro_two)));
                        break;
                    case 9:
                        mNumberText.setText(builderString(getString(R.string.activity_intro_one)));
                        break;
                    default:
                        mNumberText.setText(builderString(getString(R.string.activity_intro_cero)));
                        goToFinishActivity();
                        break;
                }

                x++;
                mHandler.postDelayed(this, Constants.INTRO_TIME);

            }
        };
        mHandler.postDelayed(mRunnable, Constants.INTRO_TIME);

    }

    @Override
    protected void onPause() {
        mHandler.removeCallbacks(mRunnable);
        super.onPause();
    }

}
