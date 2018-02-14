package com.example.android.alieninvasion;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * An activity that contains a RecyclerView with high scores composed of a username and a score
 * from the firebase real-time database.
 */
public class HighScoreTableActivity extends AppCompatActivity {
    private FirebaseDatabase mDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference mHighScoreReference;
    private RecyclerView mRecyclerView;
    private FirebaseRecyclerAdapter<HighScore, HighScoreViewHolder> mHighScoreViewAdapter;
    //make a collection

    /**
     * Sets up the high score reference and recyclerview for the firebase database
     * @param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score_table);

        String databaseKey = getResources().getString(R.string.database_key);
        mHighScoreReference = mDatabase.getReference(databaseKey);
        //mHighScoreReference.orderByChild("score").orderByValue();

        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Creates a FirebaseRecyclerAdapter to populate the recyclerView with high scores and
     * sets the recyclerView adapter to the adapter created.
     */
    @Override
    protected void onStart() {
        super.onStart();
        mHighScoreViewAdapter = new FirebaseRecyclerAdapter<HighScore,
                HighScoreViewHolder>(HighScore.class,
                R.layout.high_score_item, HighScoreViewHolder.class, mHighScoreReference) {
            @Override
            protected void populateViewHolder(HighScoreViewHolder viewHolder,
                                              HighScore model, int position) {
                viewHolder.bind(model);
            }
        };
        mRecyclerView.setAdapter(mHighScoreViewAdapter);
    }

    /**
     * A HighScoreViewHolder has a textView for the username and score.
     */
    public static class HighScoreViewHolder extends RecyclerView.ViewHolder {
        private final TextView mUsername;
        private final TextView mScore;

        /**
         * A simple constructor for a HighScoreViewHolder
         * @param itemView - the ItemView containing the TextViews
         */
        public HighScoreViewHolder(View itemView) {
            super(itemView);
            mUsername = (TextView) itemView.findViewById(R.id.username);
            mScore = (TextView) itemView.findViewById(R.id.score);
        }

        /**
         * Sets the the TextViews of the HighScoreViewHolder to username and score
         * @param highScore - the highScore to get the username and score from
         */
        public void bind(HighScore highScore) {
            //list.add high score
            mUsername.setText(highScore.username);
            mScore.setText(highScore.score);
        }
    }

    /**
     * Cleans up the HighScoreViewAdapter if it is non-null
     */
    @Override
    protected void onStop() {
        super.onStop();
        if (mHighScoreViewAdapter != null) {
            mHighScoreViewAdapter.cleanup();
        }
    }
}
