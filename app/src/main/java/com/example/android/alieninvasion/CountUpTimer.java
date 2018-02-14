package com.example.android.alieninvasion;

/**
 * Created by juliafiorino on 5/1/17.
 *
 * A CountUpTimer extends CountDownTimer. Increments every 100ms, and for this purpose serves as the
 * score.
 *
 * @TODO - implement a way to pause and resume the CountUpTimer
 *
 * @url -http://stackoverflow.com/questions/9276858/how-to-add-a-countup-timer-on-android
 * @url - http://stackoverflow.com/questions/5738362/pause-countdowntimer-in-android-when-activity-is-not-in-front
 */

import android.os.CountDownTimer;
import android.support.annotation.IntegerRes;

public abstract class CountUpTimer extends CountDownTimer {
    private static final long INTERVAL_MS = 100;
    private final long duration;

    /**
     * A CountUpTimer constructor. Takes in the duration in ms that the timer should run for.
     * @param durationMs
     */
    protected CountUpTimer(long durationMs) {
        super(durationMs, INTERVAL_MS);
        this.duration = durationMs;
    }

    /**
     * Implements onTick method from CountDownTimer
     * @param second - the current second
     */
    public abstract void onTick(int second);

    /**
     * Changes logic from CountDownTimer to make the timer count up, not down
     * @param msUntilFinished - milliseconds until CountDownTimer is finished
     */
    public void onTick(long msUntilFinished) {
        int second = (int) ((duration - msUntilFinished) / INTERVAL_MS);
        onTick(second);
    }

    /**
     * ???
     */
    @Override
    public void onFinish() {
        onTick(duration / 1000);
    }
}




