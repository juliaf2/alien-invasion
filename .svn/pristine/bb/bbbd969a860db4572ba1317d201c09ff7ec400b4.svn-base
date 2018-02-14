package com.example.android.alieninvasion;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * Instrumentation test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mHighScoreReference = mDatabase.getReference("high_score_key");

    /**
     * Tests that the app is using the correct context
     * @throws Exception
     */
    @Test
    public void useAppContext() throws Exception {
        // Context of the app under test.
        Context appContext = InstrumentationRegistry.getTargetContext();

        assertEquals("com.example.android.alieninvasion", appContext.getPackageName());
    }

    /**
     * Tests writing to the firebase database by pushing a high score and setting an OnCompleteListener.
     * If the signal counts down to 0, then we can assume that the task (writing to the database)
     * was successfully completed.
     * @throws Exception
     */
    @Test
    public void writeToDatabase() throws Exception {
        final CountDownLatch signal = new CountDownLatch(1);

        HighScore highScore = new HighScore("Julia", "430");
        mHighScoreReference.push().setValue(highScore).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                signal.countDown();
            }
        });
        signal.await(1, TimeUnit.SECONDS);

        assertEquals(0, signal.getCount());
    }

    /**
     * Tests reading from the database by comparing the string "user1" to a value for a string
     * that we already know to be in the database.
     * @throws Exception
     */
    @Test
    public void readFromDatabase() throws Exception {
        mHighScoreReference.child("high_score_1").child("username").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                assertEquals(dataSnapshot.getValue(), "user1");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }
}

