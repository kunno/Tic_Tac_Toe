package com.vicadey.tictactoe;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //setting image view for title and button
        ImageView title = (ImageView) findViewById(R.id.title);
        Button startButton = (Button) findViewById(R.id.startButton);

        //animates title and button
        title.animate().alpha(1f).setDuration(2000);
        startButton.animate().alpha(1f).setDuration(4000);

    }

    //starts game when user clicks start
    public void startGame(View view) {
        Intent startGame = new Intent(this, StartGame.class);
        startActivity(startGame);


    }


}
