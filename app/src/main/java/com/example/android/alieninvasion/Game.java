package com.example.android.alieninvasion;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

/**
 * Created by juliafiorino on 5/1/17.
 *
 * A game consists of a context, level, lives, score, and number of ufos to shoot for that level.
 * Contains the game logic. Durations for animations are computed randomly within bounds.
 */

public class Game {
    private Context context;

    private int level;
    private int lives;
    private int score;
    private int ufosToShoot;

    /**
     * Game constructor. Initializes the level, lives, score and ufos to shoot to values predetermined
     * in the integers.xml. Context is passed in because many methods using game objects require context
     * to get values from xmls.
     * @param context - the context passed from the GameActivity
     */
    public Game(Context context) {
        this.context = context;
        this.level = context.getResources().getInteger(R.integer.start_level);
        this.lives = context.getResources().getInteger(R.integer.start_lives);
        this.score = context.getResources().getInteger(R.integer.start_score);
        this.ufosToShoot = this.getUfosToShootByLevel();
    }

    /**
     * Randomizes the duration of an animation according to getFallDuration
     * @param animation - an fall animation to randomize the duration of
     */
    public void randomizeFallDuration(Animator animation) {
        long randomDuration = this.getFallDuration();
        animation.setDuration(randomDuration);
    }

    /**
     * Gets a random long between the fall upper and lower bound based on the level
     * @return - randomFallDuration a random long between the fall upper and lower bound
     */
    public long getFallDuration() {
        long lowerBound = this.getFallLowerBound();
        long upperBound = this.getFallUpperBound();

        int intRandomFallDuration = (int)(lowerBound + Math.random()*(upperBound - lowerBound));
        long randomFallDuration = (long)intRandomFallDuration;

        return randomFallDuration;
    }

    /**
     * Gets a long for the lower bound for the duration of the fall animation. The lower bound
     * decreases as level increases and doesn't go below the minimum lower bound
     * @return - lowerBound - a lower bound for the fall duration as determined by the level
     */
    public long getFallLowerBound() {
        long LEVEL_ONE_LOWER_BOUND = this.context.getResources().getInteger(R.integer.fall_level_one_lower_bound);
        long BOUND_DECREASE_PER_LEVEL = this.context.getResources().getInteger(R.integer.fall_bound_decrease_per_level);
        long MINIMUM_LOWER_BOUND = this.context.getResources().getInteger(R.integer.fall_minimum_lower_bound);
        long lowerBound = LEVEL_ONE_LOWER_BOUND - (this.getLevel() - 1)*BOUND_DECREASE_PER_LEVEL;

        if(lowerBound > MINIMUM_LOWER_BOUND) {
            return lowerBound;
        }

        return MINIMUM_LOWER_BOUND;
    }

    /**
     * Gets a long for the upper bound for the duration of the fall animation. The upper bound
     * decreases as level increases and doesn't go below the minimum upper bound
     * @return - upperBound - an upper bound for the fall duration as determined by the level
     */
    public long getFallUpperBound() {
        long LEVEL_ONE_UPPER_BOUND = this.context.getResources().getInteger(R.integer.fall_level_one_upper_bound);
        long BOUND_DECREASE_PER_LEVEL = this.context.getResources().getInteger(R.integer.fall_bound_decrease_per_level);
        long MINIMUM_UPPER_BOUND = this.context.getResources().getInteger(R.integer.fall_minimum_upper_bound);
        long upperBound = LEVEL_ONE_UPPER_BOUND - (this.getLevel() - 1)*BOUND_DECREASE_PER_LEVEL;

        if(upperBound > MINIMUM_UPPER_BOUND) {
            return upperBound;
        }

        return MINIMUM_UPPER_BOUND;
    }

    /**
     * Randomizes the duration of an animation according to getDelayDuration
     * @param animation - an delay animation to randomize the duration of
     */
    public void randomizeDelayDuration(Animator animation) {
        long randomDuration = this.getDelayDuration();
        animation.setDuration(randomDuration);
    }

    /**
     * Gets a random long between the delay upper and lower bound based on the level
     * @return - randomDelay a random long between the delay upper and lower bound
     */
    public long getDelayDuration() {
        long lowerBound = this.getDelayLowerBound();
        long upperBound = this.getDelayUpperBound();

        int intRandomDelay = (int)(lowerBound + Math.random()*(upperBound - lowerBound));
        long randomDelay = (long)intRandomDelay;

        return randomDelay;
    }

    /**
     * Gets a long for the lower bound for the duration of the delay animation. The lower bound
     * decreases as level increases and doesn't go below the minimum lower bound
     * @return - lowerBound - a lower bound for the delay duration as determined by the level
     */
    public long getDelayLowerBound() {
        long LEVEL_ONE_LOWER_BOUND = this.context.getResources().getInteger(R.integer.delay_level_one_lower_bound);
        long BOUND_DECREASE_PER_LEVEL = this.context.getResources().getInteger(R.integer.delay_bound_decrease_per_level);
        long MINIMUM_LOWER_BOUND = this.context.getResources().getInteger(R.integer.delay_minimum_lower_bound);
        long lowerBound = LEVEL_ONE_LOWER_BOUND - ((this.getLevel() - 1) * BOUND_DECREASE_PER_LEVEL);

        if(lowerBound > MINIMUM_LOWER_BOUND) {
            return lowerBound;
        }

        return MINIMUM_LOWER_BOUND;
    }


    /**
     * Gets a long for the upper bound for the duration of the delay animation. The upper bound
     * decreases as level increases and doesn't go below the minimum upper bound
     * @return - upperBound - an upper bound for the delay duration as determined by the level
     */
    public long getDelayUpperBound() {
        long LEVEL_ONE_UPPER_BOUND = this.context.getResources().getInteger(R.integer.delay_level_one_upper_bound);
        long BOUND_DECREASE_PER_LEVEL = this.context.getResources().getInteger(R.integer.delay_bound_decrease_per_level);
        long MINIMUM_UPPER_BOUND = this.context.getResources().getInteger(R.integer.delay_minimum_upper_bound);
        long upperBound = LEVEL_ONE_UPPER_BOUND - ((this.getLevel() - 1) * BOUND_DECREASE_PER_LEVEL);

        if(upperBound > MINIMUM_UPPER_BOUND) {
            return upperBound;
        }

        return MINIMUM_UPPER_BOUND;
    }

    /**
     * Multiplies the level by the ufo_level_multiplier to get the number of ufos the user has to shoot for a level
     * @return ufosToShoot - the number of ufos to shoot for a specific level
     */
    public int getUfosToShootByLevel() {
        int UFO_LEVEL_MULTIPLIER = this.context.getResources().getInteger(R.integer.ufo_level_multiplier);
        int ufosToShoot = this.getLevel() * UFO_LEVEL_MULTIPLIER;
        return ufosToShoot;
    }

    /**
     * Clears the animations of each AnimatorSet in an ArrayList of AnimatorSets
     * @param allUfosSet - the ArrayList of ufo AnimatorSets
     */
    public void clearAnimation(ArrayList<AnimatorSet> allUfosSet) {
        for(AnimatorSet ufoSet : allUfosSet) {
            ufoSet.cancel();
        }
    }

    /**
     * Starts the animations of each AnimatorSet in an ArrayList of AnimatorSets
     * @param allUfosSet - the ArrayList of ufo AnimatorSets
     */
    public void startAnimation(ArrayList<AnimatorSet> allUfosSet) {
        for(AnimatorSet ufoSet : allUfosSet) {
            ufoSet.start();
        }
    }

    /**
     * Pairs imageviews of ufos and cannons with the corresponding animations. Implements game logic.
     * If the user clicks a cannon while the ufo is falling, the cannon resets. If the cannon completes
     * its animation, the user loses a life. If the user loses all of their lives, the game ends.
     * @param ufo - a ufo ImageView
     * @param cannon - a cannon ImageView
     * @param ufoSet - the AnimatorSet for each ufo consisting of a delay and a fall
     * @param ufoAnimation - the fall animation for the ufo
     * @param ufoDelay - the delay animation for the ufo
     */
    public void pairObjects(final ImageView ufo, ImageView cannon, final AnimatorSet ufoSet,
                            final Animator ufoAnimation, final Animator ufoDelay) {

        ufoSet.play(ufoDelay).before(ufoAnimation);
        ufoSet.setTarget(ufo);

        this.randomizeDelayDuration(ufoDelay);
        this.randomizeFallDuration(ufoAnimation);

        GameActivity.levelNotifier.setText(Game.this.context.getResources().getString(R.string.level_indicator, Game.this.getLevel()));

        cannon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //user successfully shot a ufo
                if(ufo.getVisibility() == View.VISIBLE) {
                    Game.this.decrementUfosToShoot();
                    //since when the animation is cancelled for the ufo, a life is lost, we have to add
                    //back a life for that ufo
                    Game.this.incrementLives(1);
                    GameActivity.levelNotifier.setText(Game.this.context.getResources().getString(R.string.empty_string));

                    ufoSet.cancel();
                    Game.this.randomizeDelayDuration(ufoDelay);
                    Game.this.randomizeFallDuration(ufoAnimation);
                    ufoSet.start();
                }

                //new level
                if(Game.this.ufosToShoot <= 0) {
                    Game.this.incrementLevel();
                    Game.this.setUfosToShoot();
                    //since when the animations are cancelled, a life is lost, we have to add back
                    //a life for each ufo because each animation is cancelled
                    Game.this.incrementLives(Game.this.context.getResources().getInteger(R.integer.number_of_ufos));
                    clearAnimation(GameActivity.allUfosSet);

                    GameActivity.levelNotifier.setText(Game.this.context.getResources().getString(R.string.level_indicator, Game.this.getLevel()));
                    //makes a pause for the textview to show up
                    ufoDelay.start();
                    startAnimation(GameActivity.allUfosSet);
                }
            }
        });

        ufoSet.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ufo.setVisibility(View.INVISIBLE);
                Game.this.loseALife();

                //GAME OVER
                if(Game.this.getLives() <= 0) {
                    Intent intent = new Intent(Game.this.context, EnterUsernameActivity.class);
                    intent.putExtra("SCORE", Game.this.getScore());
                    Game.this.context.startActivity(intent);
                }
            }
        });

        ufoDelay.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                ufo.setVisibility(View.VISIBLE);
            }

            @Override
            public void onAnimationStart(Animator animation) {
                super.onAnimationStart(animation);
                ufo.setVisibility(View.INVISIBLE);
            }
        });

    }

    /**
     * Returns the level of the game
     * @return - level
     */
    public int getLevel() {
        return this.level;
    }

    /**
     * Returns the number of lives left in the game
     * @return - lives
     */
    public int getLives() {
        return this.lives;
    }

    /**
     * Returns the score of the game
     * @return - score
     */
    public int getScore() {
        return this.score;
    }

    /**
     * Updates the game's score
     * @param score - the score to update to
     */
    public void setScore(int score) {
        this.score = score;
    }

    /**
     * Decrements ufos to shoot
     */
    public void decrementUfosToShoot() {
        this.ufosToShoot--;
    }

    /**
     * Sets ufos to shoots by level
     */
    public void setUfosToShoot() {
        this.ufosToShoot = this.getUfosToShootByLevel();
    }

    /**
     * Increments lives in cases where too many lives are removed
     * @param lives - number of lives to increment by
     */
    public void incrementLives(int lives) {
        this.lives += lives;
    }

    /**
     * If there are lives left, decrements lives
     */
    public void loseALife() {
        if (this.lives > 0) {
            lives--;
        }
    }

    /**
     * Increases level by 1
     */
    public void incrementLevel() {
        this.level++;
    }
}
