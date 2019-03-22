package org.example.pacman;

import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;


public class MainActivity extends AppCompatActivity {
    //reference to the main view
    GameView gameView;
    //reference to the game class.
    Game game;
    public boolean running = false;
    //private int counter = 0;
    TextView counterTextView;
    private Timer myTimer;
    MenuItem pauseResumeMenuItem;

    // -- Setting variables for shared preferences to store highscore -- //
    private SharedPreferences mPreferences;
    private SharedPreferences.Editor mEditor;

    TextView highscoreTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //saying we want the game to run in one mode only
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        setContentView(R.layout.activity_main);

        gameView =  findViewById(R.id.gameView);
        TextView textView = findViewById(R.id.points);
        TextView highscoreTextView = findViewById(R.id.highscore);

        TextView textViewHighscore = findViewById(R.id.highscore);

        //game = new Game(this,textView);
        //game = new Game(this,textView, gameView);
        game = new Game(this,textView, highscoreTextView);

        game.setGameView(gameView);
        gameView.setGame(game);

        game.newGame();

        Button buttonRight = findViewById(R.id.moveRight);
        //listener of our pacman, when somebody clicks it
        buttonRight.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                game.changeDirection("Right");
                //game.movePacmanRight(10);
            }
        });

        Button buttonLeft = findViewById(R.id.moveLeft);
        //listener of our pacman, when somebody clicks it
        buttonLeft.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                game.changeDirection("Left");
                //game.movePacmanLeft(10);
            }
        });

        Button buttonUp = findViewById(R.id.moveUp);
        //listener of our pacman, when somebody clicks it
        buttonUp.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                game.changeDirection("Up");
                //game.movePacmanUp(10);
            }
        });

        Button buttonDown = findViewById(R.id.moveDown);
        //listener of our pacman, when somebody clicks it
        buttonDown.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                game.changeDirection("Down");
                //game.movePacmanDown(10);
            }
        });

        counterTextView = findViewById(R.id.counter);
        myTimer = new Timer();

        // -- Setting running boolean to true -- //
        running = true; //should the game be running?

        // -- Timer which is calling the TimerMethod() 5 times per second -- //
        myTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                TimerMethod();
            }

        }, 0, 200);

        pauseResumeMenuItem = findViewById(R.id.action_pauseResumeGame);

        // -- Getting shared preference which stores the high score -- //
        mPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mPreferences.edit();

        // -- Retrieving highscore name and highscore points from shared preferences
        checkSharedPreferences();

        highscoreTextView = findViewById(R.id.highscore);
        highscoreTextView.setText(" Highscore: " +  game.getHighscorePoints());

        Toast.makeText(this,"Level 1",Toast.LENGTH_LONG).show();
    }



    private void checkSharedPreferences(){

        // -- Retrieving the stored shared preference for highscore -- //
        int highscorePoints = mPreferences.getInt(getString(R.string.highscorePoints), 0);

        // -- Setting the retrieved highscore into the Game property -- //
        game.setHighscorePoints(highscorePoints);

    }

    // -- Timer method, running the Timer_Tick Runnable() -- //
    private void TimerMethod()
    {
        this.runOnUiThread(Timer_Tick);
    }

    // -- Setting the Runnable method -- //
    private Runnable Timer_Tick = new Runnable() {
        public void run() {

            // -- If the boolean "running" is set to true then the game is running -- //
            if (running)
            {
                // -- Ticking up the counter -- //
                game.setCounter(game.getCounter() + 1);

                // -- Updating the view with the new game count -- //
                counterTextView.setText(" Time: "+game.getCounter());

                // -- Randomize the movement of the enemies -- //
                game.moveEnemiesRandomly(5);

                // -- Move the pacman 15 pixels for each run -- //
                game.movePacman(15);

            }
        }
    };


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    // -- Menu button methods -- //
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        // -- If the menu link "Reset highscore" is hit -- //
        if (id == R.id.action_resetHighscore) {
            Toast.makeText(this, "High score reset", Toast.LENGTH_LONG).show();
            mEditor.putInt(getString(R.string.highscorePoints), 0);
            mEditor.commit();
            checkSharedPreferences();
            highscoreTextView = findViewById(R.id.highscore);
            highscoreTextView.setText(" Highscore: " +  game.getHighscorePoints());
        }

        // -- Menu item NewGame -- //
        else if (id == R.id.action_newGame) {

            // -- Make a toast message telling that new game has been clicked -- //
            Toast.makeText(this,"New Game started",Toast.LENGTH_LONG).show();

            // -- Reset the counter when NewGame is clicked -- //
            game.setCounter(0);

            // -- If the current points are higher than the highscore, then the points should be set as the new highscore -- //
            if(game.getHighscorePoints() < game.getPoints()) {

                // -- Getting points to be set as new high score. Converting to Integer, then converting to string -- //
                // -- Updating the sharedPreferences with the new high score using current score -- //
                mEditor.putInt(getString(R.string.highscorePoints), game.getPoints());
                mEditor.commit();

                // -- Retrieving the new shared preferences -- //
                checkSharedPreferences();

                // -- Setting the new highscore in the highscoreTextView, so the new high score is displayed -- //
                highscoreTextView = findViewById(R.id.highscore);
                highscoreTextView.setText(" Highscore: " + game.getHighscorePoints());
            }

            game.newGame();

            return true;
        }

        // -- Menu item Pause/Resume -- //
        else if (id == R.id.action_pauseResumeGame) {

            // -- If game is running, then it should be paused ... -- //
            // -- ... make toast text and set running boolean to false to stop the timer from executing running code -- //
            if(running == true) {
                Toast.makeText(this, "Pause Game clicked", Toast.LENGTH_LONG).show();
                item.setTitle("Resume");
                running = false;
            }

            // -- If game is stopped, then it should be start to run again ... -- //
            // -- ... make toast text and set running boolean to true to allow the timer to execute running code -- //
            else if(running == false) {
                Toast.makeText(this, "Resume Game clicked", Toast.LENGTH_LONG).show();
                item.setTitle("Pause");
                running = true;
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

//    public void updateHighscoreTextView (String newHighScore){
//        highscoreTextView = findViewById(R.id.highscore);
//        highscoreTextView.setText(" Highscore: " +  game.getHighscorePoints());
//    }

//    public void setNewHighscore (String newHighScore){
//        String mytest = newHighScore;
//        //game.setHighscorePoints(newHighScore);
//
//        // -- THE FOLLOWING LINES BREAK. Exception: -- //
//        // -- java.lang.NullPointerException: Attempt to invoke virtual method 'void org.example.pacman.Game.setHighscorePoints(java.lang.String)' on a null object reference -- //
//        Toast.makeText(this, newHighScore, Toast.LENGTH_LONG).show();
//
//        //mEditor.putString(getString(R.string.highscorePoints), newHighScore);
//        //mEditor.commit();
//
//        // -- REGION 02 - Second attempt -- //
//        // -- Updating the sharedPreferences with the new high score using current score -- //
//        mEditor.putInt(getString(R.string.highscorePoints), game.getPoints());
//        mEditor.commit();
//        // -- Retrieving the new shared preferences -- //
//        checkSharedPreferences();
//        // -- Setting the new highscore in the highscoreTextView, so the new high score is displayed -- //
//        highscoreTextView = findViewById(R.id.highscore);
//        highscoreTextView.setText(" Highscore: " +  game.getHighscorePoints());
//        // -- REGION 02 - Second attempt -- //
//
//    }

//    public void gameWon(){
//        Toast.makeText(this,"Game is won",Toast.LENGTH_LONG).show();
//    }
}
