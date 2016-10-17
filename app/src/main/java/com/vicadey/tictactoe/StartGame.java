package com.vicadey.tictactoe;

import android.annotation.TargetApi;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.stream.IntStream;

import static com.vicadey.tictactoe.R.id.connectGrid;

public class StartGame extends AppCompatActivity {

    //keeps track of counter color to drop in. 0 = red, 1 = yellow
    public static int counterColor = 0;
    public static final int ARRAY_SIZE = 9;
    public static final float TRANSLATION_OFFSET = 1000f;
    public static final int DURATION = 300;
    public static final int ROTATION = 360;
    public static final int STATE_TRACKER = 2;
    public static int[] gameState = new int[ARRAY_SIZE];
    public static final String errorMessage = "Invalid move! Please select a valid move.";
    public static final int[][] winningCombo = {{0, 1, 2}, {3, 4, 5}, {6, 7, 8}, {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, {0, 4, 8}, {2, 4, 6}};
    public static boolean gameActive;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);

        GridLayout connectGrid = (GridLayout) findViewById(R.id.connectGrid);

        connectGrid.setTranslationY(-TRANSLATION_OFFSET);

        connectGrid.animate().translationYBy(TRANSLATION_OFFSET).setDuration(DURATION);
        newGameState();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    public void newGame(View view) {
        RelativeLayout gameOver = (RelativeLayout) findViewById(R.id.gameOver);
        GridLayout connectGrid = (GridLayout) findViewById(R.id.connectGrid);

        gameOver.setVisibility(View.INVISIBLE);
        connectGrid.setVisibility(View.VISIBLE);
        Arrays.fill(gameState, STATE_TRACKER);
        counterColor = 0;
        for (int i = 0; i < connectGrid.getChildCount(); i++) {
            ((ImageView) connectGrid.getChildAt(i)).setImageResource(0);

        }
        gameActive = true;
    }


    public void newGameState() {
        Arrays.fill(gameState, STATE_TRACKER);
        counterColor = 0;
        gameActive = true;

    }



    public void dropIn(View view) {

        if (gameActive) {
            ImageView counter = (ImageView) view;
            int tappedCounter = Integer.parseInt(counter.getTag().toString());
            counter.setTranslationY(-TRANSLATION_OFFSET);

            System.out.println(Arrays.toString(gameState));


            if (counterColor == 0 && gameState[tappedCounter] == STATE_TRACKER) {
                counter.setImageResource(R.drawable.red);
                counter.animate().translationYBy(TRANSLATION_OFFSET).rotation(ROTATION).setDuration(DURATION);
                gameState[tappedCounter] = counterColor;
                counterColor = 1;
            } else if (counterColor == 1 && gameState[tappedCounter] == STATE_TRACKER) {
                counter.setImageResource(R.drawable.yellow);
                counter.animate().translationYBy(TRANSLATION_OFFSET).rotation(ROTATION).setDuration(DURATION);
                gameState[tappedCounter] = counterColor;
                counterColor = 0;
            } else {
                Toast.makeText(getApplicationContext(), errorMessage, Toast.LENGTH_SHORT).show();
                if (counterColor == 1) {
                    counter.setImageResource(R.drawable.red);
                    counter.animate().translationYBy(TRANSLATION_OFFSET).setDuration(0);
                } else {
                    counter.setImageResource(R.drawable.yellow);
                    counter.animate().translationYBy(TRANSLATION_OFFSET).setDuration(0);
                }
            }
        }

        for (int hasWon[] : winningCombo) {
            RelativeLayout gameOver = (RelativeLayout) findViewById(R.id.gameOver);
            TextView winnerMessage = (TextView) findViewById(R.id.winnerMessage);
            final int sdk = android.os.Build.VERSION.SDK_INT;


            if (gameState[hasWon[0]] == gameState[hasWon[1]] &&
                    gameState[hasWon[1]] == gameState[hasWon[STATE_TRACKER]] &&
                    gameState[hasWon[0]] != STATE_TRACKER) {


                String winner = "Yellow";
                if (gameState[hasWon[0]] == 0) {
                    winner = "Red";
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        gameOver.setBackgroundDrawable( getResources().getDrawable(R.color.red) );
                    } else {
                        gameOver.setBackground( getResources().getDrawable(R.color.red));
                    }


                }
                else{
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        gameOver.setBackgroundDrawable( getResources().getDrawable(R.color.yellow) );
                    } else {
                        gameOver.setBackground( getResources().getDrawable(R.color.yellow));
                    }
                }


                winnerMessage.setText(winner + " has won!");

                gameOver.setVisibility(View.VISIBLE);

                gameActive = false;

                System.out.println("You win!");

            } else {
                boolean gameIsOver = true;

                for (int counterState : gameState) {
                    if (counterState == STATE_TRACKER) gameIsOver = false;
                }

                if (gameIsOver) {


                    winnerMessage.setText("Tie Game!");
                    if(sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                        gameOver.setBackgroundDrawable( getResources().getDrawable(R.color.grey) );
                    } else {
                        gameOver.setBackground( getResources().getDrawable(R.color.grey));
                    }
                    gameOver.setVisibility(View.VISIBLE);
                    gameActive = false;

                    System.out.println("Tie Game!");

                }
            }

        }

    }

    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("StartGame Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }
}
