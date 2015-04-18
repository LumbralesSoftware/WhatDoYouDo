package com.thedeveloperworldisyours.whatdoyoudoandroid.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.thedeveloperworldisyours.whatdoyoudoandroid.R;
import com.thedeveloperworldisyours.whatdoyoudoandroid.utils.Constants;

public class IntroActivity extends ActionBarActivity {

    private final Handler mHandler = new Handler();
    private Runnable mRunnable;
    private TextView mTextIntro;
    private TextView mNumberText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        mTextIntro = (TextView) findViewById(R.id.activity_intro_text);
        mNumberText = (TextView) findViewById(R.id.activity_intro_number);

        timer();
    }

    public void timer() {

        mRunnable = new Runnable() {
            int x = 1;

            public void run() {
                switch (x) {
                    case 1:
                        mNumberText.setText(getString(R.string.activity_intro_nine));
                        break;
                    case 2:
                        mNumberText.setText(getString(R.string.activity_intro_eight));
                        break;
                    case 3:
                        mNumberText.setText(getString(R.string.activity_intro_seven));
                        break;
                    case 4:
                        mNumberText.setText(getString(R.string.activity_intro_six));
                        break;
                    case 5:
                        mNumberText.setText(getString(R.string.activity_intro_five));
                        break;
                    case 6:
                        mNumberText.setText(getString(R.string.activity_intro_four));
                        break;
                    case 7:
                        mNumberText.setText(getString(R.string.activity_intro_three));
                        break;
                    case 8:
                        mNumberText.setText(getString(R.string.activity_intro_two));
                        break;
                    case 9:
                        mNumberText.setText(getString(R.string.activity_intro_one));
                        break;
                    default:
                        mNumberText.setText(getString(R.string.activity_intro_cero));
                        goToQuestion();
                        break;
                }

                x++;
                mHandler.postDelayed(this, Constants.INTRO_TIME);

            }
        };
        mHandler.postDelayed(mRunnable, Constants.INTRO_TIME);

    }

    public void goToQuestion() {
        Intent intent = new Intent(this, QuestionActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onPause() {
        mHandler.removeCallbacks(mRunnable);
        super.onPause();
    }
}
