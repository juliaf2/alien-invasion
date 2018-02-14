package com.example.android.alieninvasion;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * The game activity consists of the actual game that the user will be playing. Consists of a number
 * of ufos and cannons paired together. The user should click the cannons to shoot the ufos falling
 * down from the top of the screen before they hit the cannons.
 */
public class GameActivity extends Activity {
    /**
     * Initializes all of the ufo and cannon ImageViews and animations. Each ufo has an AnimatorSet
     * consisting of a delay and a fall. The game logic is called from the Game class.
     *
     * @url https://code.tutsplus.com/tutorials/android-sdk-creating-a-simple-property-animation--mobile-15022
     */
    public static ArrayList<AnimatorSet> allUfosSet = new ArrayList<>();
    public static TextView levelNotifier;
    public Game mGame;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final Context mContext = this.getApplicationContext();
        mGame = new Game(mContext);

        final ImageView firstUfo = (ImageView) findViewById(R.id.ufo1);
        final ImageView secondUfo = (ImageView) findViewById(R.id.ufo2);
        final ImageView thirdUfo = (ImageView) findViewById(R.id.ufo3);
        final ImageView fourthUfo = (ImageView) findViewById(R.id.ufo4);
        final ImageView fifthUfo = (ImageView) findViewById(R.id.ufo5);
        final ImageView sixthUfo = (ImageView) findViewById(R.id.ufo6);
        final ImageView seventhUfo = (ImageView) findViewById(R.id.ufo7);

        final ImageView firstCannon = (ImageView) findViewById(R.id.cannon1);
        final ImageView secondCannon = (ImageView) findViewById(R.id.cannon2);
        final ImageView thirdCannon = (ImageView) findViewById(R.id.cannon3);
        final ImageView fourthCannon = (ImageView) findViewById(R.id.cannon4);
        final ImageView fifthCannon = (ImageView) findViewById(R.id.cannon5);
        final ImageView sixthCannon = (ImageView) findViewById(R.id.cannon6);
        final ImageView seventhCannon = (ImageView) findViewById(R.id.cannon7);

        final Animator firstUfoAnimation = AnimatorInflater.loadAnimator(this, R.animator.ufo_fall);
        final Animator secondUfoAnimation = AnimatorInflater.loadAnimator(this, R.animator.ufo_fall);
        final Animator thirdUfoAnimation = AnimatorInflater.loadAnimator(this, R.animator.ufo_fall);
        final Animator fourthUfoAnimation = AnimatorInflater.loadAnimator(this, R.animator.ufo_fall);
        final Animator fifthUfoAnimation = AnimatorInflater.loadAnimator(this, R.animator.ufo_fall);
        final Animator sixthUfoAnimation = AnimatorInflater.loadAnimator(this, R.animator.ufo_fall);
        final Animator seventhUfoAnimation = AnimatorInflater.loadAnimator(this, R.animator.ufo_fall);

        final Animator firstUfoDelay = AnimatorInflater.loadAnimator(this, R.animator.delay);
        final Animator secondUfoDelay = AnimatorInflater.loadAnimator(this, R.animator.delay);
        final Animator thirdUfoDelay = AnimatorInflater.loadAnimator(this, R.animator.delay);
        final Animator fourthUfoDelay = AnimatorInflater.loadAnimator(this, R.animator.delay);
        final Animator fifthUfoDelay = AnimatorInflater.loadAnimator(this, R.animator.delay);
        final Animator sixthUfoDelay = AnimatorInflater.loadAnimator(this, R.animator.delay);
        final Animator seventhUfoDelay = AnimatorInflater.loadAnimator(this, R.animator.delay);

        final AnimatorSet firstUfoSet = new AnimatorSet();
        final AnimatorSet secondUfoSet = new AnimatorSet();
        final AnimatorSet thirdUfoSet = new AnimatorSet();
        final AnimatorSet fourthUfoSet = new AnimatorSet();
        final AnimatorSet fifthUfoSet = new AnimatorSet();
        final AnimatorSet sixthUfoSet = new AnimatorSet();
        final AnimatorSet seventhUfoSet = new AnimatorSet();

        allUfosSet.add(firstUfoSet);
        allUfosSet.add(secondUfoSet);
        allUfosSet.add(thirdUfoSet);
        allUfosSet.add(fourthUfoSet);
        allUfosSet.add(fifthUfoSet);
        allUfosSet.add(sixthUfoSet);
        allUfosSet.add(seventhUfoSet);

        levelNotifier = (TextView) findViewById(R.id.newLevelNotification);

        mGame.pairObjects(firstUfo, firstCannon, firstUfoSet, firstUfoAnimation, firstUfoDelay);
        mGame.pairObjects(secondUfo, secondCannon, secondUfoSet, secondUfoAnimation, secondUfoDelay);
        mGame.pairObjects(thirdUfo, thirdCannon, thirdUfoSet, thirdUfoAnimation, thirdUfoDelay);
        mGame.pairObjects(fourthUfo, fourthCannon, fourthUfoSet, fourthUfoAnimation, fourthUfoDelay);
        mGame.pairObjects(fifthUfo, fifthCannon, fifthUfoSet, fifthUfoAnimation, fifthUfoDelay);
        mGame.pairObjects(sixthUfo, sixthCannon, sixthUfoSet, sixthUfoAnimation, sixthUfoDelay);
        mGame.pairObjects(seventhUfo, seventhCannon, seventhUfoSet, seventhUfoAnimation, seventhUfoDelay);

        mGame.startAnimation(allUfosSet);

        final TextView runningScore = (TextView) findViewById(R.id.runningScore);

        final CountUpTimer timer = new CountUpTimer(Integer.MAX_VALUE) {
            public void onTick(int second) {
                String text = mContext.getResources().getString(R.string.status_indicator_top, mGame.getLevel(), second, mGame.getLives());
                runningScore.setText(text);
                mGame.setScore(second);
            }
        };

        timer.start();

        final ImageView pauseIcon = (ImageView) findViewById(R.id.pauseIcon);
        pauseIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pauseAllAnimation(allUfosSet);
                timer.cancel();
                startActivity(new Intent(GameActivity.this, PausePopUp.class));
            }
        });
    }

    /**
     * Pauses the AnimatorSet of each ufo
     * @param allUfosSet - an ArrayList of all of the ufo's AnimatorSets
     */
    public void pauseAllAnimation(ArrayList<AnimatorSet> allUfosSet) {
        for(AnimatorSet ufoSet : allUfosSet) {
            ufoSet.pause();
        }
    }

    /**
     * Resumes the AnimatorSet of each ufo
     * @param allUfosSet - an ArrayList of all of the ufo's AnimatorSets
     */
    public static void resumeAllAnimation(ArrayList<AnimatorSet> allUfosSet) {
        for(AnimatorSet ufoSet : allUfosSet) {
            ufoSet.resume();
        }
    }
}