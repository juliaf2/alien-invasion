package com.example.android.alieninvasion;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class HomeActivity extends AppCompatActivity {
    /**
     * The Home Activity contains a start page that has a TextView that allows the user to navigate
     * to the Game Activity.
     * @url - http://stackoverflow.com/questions/3438276/how-to-change-the-text-on-the-action-bar
     *
     * @param savedInstanceState - a bundle containing the previous instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        getSupportActionBar().setTitle(this.getApplicationContext().getResources().getString(R.string.empty_string));

        TextView clickToStart = (TextView) findViewById(R.id.startGameTextView);
        clickToStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, GameActivity.class);
                startActivity(intent);
            }
        });

        TextView getToHighScores = (TextView) findViewById(R.id.highScoreTableTextView);
        getToHighScores.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(HomeActivity.this, HighScoreTableActivity.class);
                startActivity(intent);
            }
        });
    }
}
