package org.example.pacman;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.preference.PreferenceManager;
import android.widget.TextView;
import android.widget.Toast;


import java.util.ArrayList;
import java.util.Random;

/**
 *
 * This class should contain all your game logic
 */

public class Game {

    //context is a reference to the activity
    private Context context;
    private int points = 0; //how points do we have
    private int counter = 0;
    private int level = 1;

    //bitmap of the pacman
    private Bitmap pacBitmap;

    // -- bitmap of goldcoin -- //
    private Bitmap goldcoinBitmap;

    // -- bitmap of enemy -- //
    private Bitmap enemyBitmap;

    //textview reference to points
    private TextView pointsView;
    private int pacx, pacy;

    //the list of goldcoins - initially empty
    private ArrayList<GoldCoin> goldcoins = new ArrayList<>();

    //a reference to the gameview
    private GameView gameView;

    private int h,w; //height and width of screen

    // --  ArrayList of Enemies -- //
    private ArrayList<Enemy> enemies = new ArrayList<>();

    // -- Setting the direction for the pacman to move -- //
    public String direction;

    private TextView highscoreView;
    private String highscoreName = "";
    private int highscorePoints = 0;

    //MainActivity mActivity = new MainActivity();


    //public Game(Context context, TextView view, GameView gameView)
    public Game(Context context, TextView view, TextView highscoreTextView)
    //public Game(Context context, TextView view)
    {
        this.context = context;
        this.pointsView = view;
        this.highscoreView = highscoreTextView;
        pacBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.pacman);

        // -- Bitmap of goldcoin. Used to select which image should be drawn on the view -- //
        goldcoinBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.goldcoin);

        // -- Bitmap of enemy. Used to select which image should be drawn on the view -- //
        enemyBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.redpacmanghost);

    }



    public void newGame()
    {
        // -- Setting the game level to 1 -- //
        level = 1;

        // -- Remove existing enemies -- //
        enemies.clear();

        // -- Remove existing goldcoins -- //
        goldcoins.clear();

        // -- Which direction should the pacman be heading when timer is running -- //
        direction = "";

        // -- Start coordinates of pacman -- //
        pacx = 50;
        pacy = 400;

        // -- Points reset -- //
        points = 0;
        pointsView.setText(context.getResources().getString(R.string.points)+" "+points);

        // -- Adding gold coin to game -- //
        GoldCoin coin1 = new GoldCoin();
        coin1.goldcoinX = 300;
        coin1.goldcoinY = 400;
        coin1.taken = false;
        goldcoins.add(coin1);

        // -- Adding gold coin to game -- //
        GoldCoin coin2 = new GoldCoin();
        coin2.goldcoinX = 300;
        coin2.goldcoinY = 200;
        coin2.taken = false;
        goldcoins.add(coin2);

        // -- Adding gold coin to game -- //
        GoldCoin coin3 = new GoldCoin();
        coin3.goldcoinX = 300;
        coin3.goldcoinY = 600;
        coin3.taken = false;
        goldcoins.add(coin3);

        // -- Adding enemy to game -- //
        Enemy enemy1 = new Enemy();
        enemy1.enemyX = 500;
        enemy1.enemyY = 400;
        enemies.add((enemy1));

        // -- Adding enemy to game -- //
        Enemy enemy2 = new Enemy();
        enemy2.enemyX = 500;
        enemy2.enemyY = 600;
        enemies.add((enemy2));

        if(level >= 2) {
            // -- Adding enemy to game -- //
            Enemy enemy3 = new Enemy();
            enemy3.enemyX = 500;
            enemy3.enemyY = 200;
            enemies.add((enemy3));
        }

        gameView.invalidate(); //redraw screen

    }

    public void nextLevel()
    {
        level++;
        Toast.makeText(context, "Level " + level, Toast.LENGTH_SHORT).show();

        // -- Remove existing enemies -- //
        enemies.clear();

        // -- Remove existing goldcoins -- //
        goldcoins.clear();

        direction = "";

        pacx = 50;
        pacy = 400; //just some starting coordinates
        //reset the points
        //points = 0;
        //pointsView.setText(context.getResources().getString(R.string.points)+" "+points);


        //highscoreView.setText("Highscore:");

        // -- Adding gold coin to game -- //
        GoldCoin coin1 = new GoldCoin();
        coin1.goldcoinX = 300;
        coin1.goldcoinY = 400;
        coin1.taken = false;
        goldcoins.add(coin1);

        // -- Adding gold coin to game -- //
        GoldCoin coin2 = new GoldCoin();
        coin2.goldcoinX = 300;
        coin2.goldcoinY = 200;
        coin2.taken = false;
        goldcoins.add(coin2);

        // -- Adding gold coin to game -- //
        GoldCoin coin3 = new GoldCoin();
        coin3.goldcoinX = 300;
        coin3.goldcoinY = 600;
        coin3.taken = false;
        goldcoins.add(coin3);

        if(level >= 2) {
            // -- Adding gold coin to game -- //
            GoldCoin coin4 = new GoldCoin();
            coin4.goldcoinX = 50;
            coin4.goldcoinY = 200;
            coin4.taken = false;
            goldcoins.add(coin4);
        }

        if(level >= 3) {
            // -- Adding gold coin to game -- //
            GoldCoin coin5 = new GoldCoin();
            coin5.goldcoinX = 50;
            coin5.goldcoinY = 600;
            coin5.taken = false;
            goldcoins.add(coin5);
        }

        if(level >= 3) {
            // -- Adding gold coin to game -- //
            GoldCoin coin6 = new GoldCoin();
            coin6.goldcoinX = 50;
            coin6.goldcoinY = 800;
            coin6.taken = false;
            goldcoins.add(coin6);
        }

        if(level >= 3) {
            // -- Adding gold coin to game -- //
            GoldCoin coin7 = new GoldCoin();
            coin7.goldcoinX = 300;
            coin7.goldcoinY = 800;
            coin7.taken = false;
            goldcoins.add(coin7);
        }

        // -- Adding enemy to game -- //
        Enemy enemy1 = new Enemy();
        enemy1.enemyX = 500;
        enemy1.enemyY = 400;
        enemies.add((enemy1));

        // -- Adding enemy to game -- //
        Enemy enemy2 = new Enemy();
        enemy2.enemyX = 500;
        enemy2.enemyY = 600;
        enemies.add((enemy2));

        if(level >= 2) {
            // -- Adding enemy to game -- //
            Enemy enemy3 = new Enemy();
            enemy3.enemyX = 500;
            enemy3.enemyY = 200;
            enemies.add((enemy3));
        }

        if(level >= 3) {
            // -- Adding enemy to game -- //
            Enemy enemy4 = new Enemy();
            enemy4.enemyX = 500;
            enemy4.enemyY = 800;
            enemies.add((enemy4));
        }

        gameView.invalidate(); //redraw screen

    }

    public void setSize(int h, int w)
    {
        this.h = h;
        this.w = w;
    }

    public void changeDirection(String newDirection){
        direction = newDirection;
    }

    public void movePacman(int pixels){
        if(direction == "Right"){
            this.movePacmanRight(pixels);
        }
        else if(direction == "Left"){
            this.movePacmanLeft(pixels);
        }
        else if(direction == "Up"){
            this.movePacmanUp(pixels);
        }
        else if(direction == "Down"){
            this.movePacmanDown(pixels);
        }
    }

    // -- Move the pacman right -- //
    public void movePacmanRight(int pixels)
    {
        // -- Is pacman still within the GameView boundaries -- //
        if (pacx+pixels+pacBitmap.getWidth()<w) {

            // -- Setting the new X coordinate for pacman -- //
            pacx = pacx + pixels;

            // -- Check whether a coin or enemy is hit -- //
            doCollisionCheck();

            // -- Redraw the GameView -- //
            gameView.invalidate();
        }
    }

    // -- Move the pacman left -- //
    public void movePacmanLeft(int pixels)
    {
        // -- Is pacman still within the GameView boundaries -- //
        if (pacx>0) {

            // -- Setting the new X coordinate for pacman -- //
            pacx = pacx - pixels;

            // -- Check whether a coin or enemy is hit -- //
            doCollisionCheck();

            // -- Redraw the GameView -- //
            gameView.invalidate();
        }
    }

    // -- Move the pacman up -- //
    public void movePacmanUp(int pixels)
    {
        // -- Is pacman still within the GameView boundaries -- //
        if (pacy>0) {

            // -- Setting the new Y coordinate for pacman -- //
            pacy = pacy - pixels;

            // -- Check whether a coin or enemy is hit -- //
            doCollisionCheck();

            // -- Redraw the GameView -- //
            gameView.invalidate();
        }
    }

    // -- Move the pacman down -- //
    public void movePacmanDown(int pixels)
    {
        // -- Is pacman still within the GameView boundaries -- //
        if (pacy+pixels+pacBitmap.getHeight()<h) {

            // -- Setting the new Y coordinate for pacman -- //
            pacy = pacy + pixels;

            // -- Check whether a coin or enemy is hit -- //
            doCollisionCheck();

            // -- Redraw the GameView -- //
            gameView.invalidate();
        }
    }

    // -- Move enemies in random directions -- //
    public void moveEnemiesRandomly(int pixels){

        Random rand = new Random();

        for (Enemy myEnemy : getEnemies()) {

            // -- Getting a random integer between 0-4 -- //
            int n = rand.nextInt(4);

            // -- Moving the enemy in the direction depending on the retrieved random number -- //
            if (n == 0) {
                moveEnemyDown(myEnemy, 5);
            } else if (n == 1) {
                moveEnemyUp(myEnemy, 5);
            } else if (n == 2) {
                moveEnemyRight(myEnemy, 5);
            } else if (n == 3) {
                moveEnemyLeft(myEnemy, 5);
            }
        }
        doCollisionCheck();
    }

    public void moveEnemyLeft(Enemy myEnemy, int pixels)
    {
        //still within our boundaries?
        if (myEnemy.enemyX>0) {
            myEnemy.enemyX = myEnemy.enemyX - pixels;
        }
        //doCollisionCheck();
        gameView.invalidate();
    }

    public void moveEnemyRight(Enemy myEnemy, int pixels)
    {
        //still within our boundaries?
        if (myEnemy.enemyX+pixels+pacBitmap.getWidth()<w) {
            myEnemy.enemyX = myEnemy.enemyX + pixels;
        }
        //doCollisionCheck();
        gameView.invalidate();
    }

    public void moveEnemyUp(Enemy myEnemy, int pixels)
    {
        //still within our boundaries?
        if (myEnemy.enemyY>0) {
            myEnemy.enemyY = myEnemy.enemyY - pixels;
        }
        //doCollisionCheck();
        gameView.invalidate();
    }

    public void moveEnemyDown(Enemy myEnemy, int pixels)
    {
        //still within our boundaries?
        if (myEnemy.enemyY+pixels+pacBitmap.getHeight()<h) {
            myEnemy.enemyY = myEnemy.enemyY + pixels;
        }
        //doCollisionCheck();
        gameView.invalidate();
    }

    // -- Checking whether pacman has hit a coin or an enemy -- //
    public void doCollisionCheck()
    {
        // -- Check each gold coin if it is hit -- //
        for (GoldCoin coin : goldcoins) {

            // -- Calculate the coordinate difference between the coin and pacman to see if coin is hit -- //
            int xDifference = coin.goldcoinX - pacx;
            int yDifference = coin.goldcoinY - pacy;

            // -- If the coin is not already taken and coordinate difference indicate a hit -- //
            if (coin.taken == false
                    && xDifference <= 140 && xDifference >= -140
                    && yDifference <= 150 && yDifference >= -150
            ) {

                // -- Increasing the points by 1 for picking up the coin -- //
                points = points + 1;

                // -- Display the new score -- //
                pointsView.setText(context.getResources().getString(R.string.points) + " " + points);

                // -- Setting the coin property "taken" to true -- //
                coin.taken = true;
            }
        }

        // -- Check whether all coins has been taken to start a new level -- //
        checkWinningGame();

        // -- Check if any enemy is hit -- //
        for (Enemy enemy:enemies) {

            // -- Calculating the distance between each enemy and the pacman -- //
            int xDifference = enemy.enemyX - pacx;
            int yDifference = enemy.enemyY - pacy;

            // -- If enemy is hit. Checking whether both differences of X and Y coordinates between pacman and enemy are within collision limitation -- //
            if(xDifference <= 140 && xDifference >= -140
                    && yDifference <= 150 && yDifference >= -150){

                // -- Show a message that enemy is hit and a new game is started -- //
                Toast.makeText(context, "ENEMY HIT - New game started", Toast.LENGTH_SHORT).show();

                // -- Reset the counter to 0 when enemy is hit -- //
                this.setCounter(0);

                // -- Retrieving preferences from MainActivity instance (context) -- //
                SharedPreferences mPreferences = PreferenceManager.getDefaultSharedPreferences(context);
                SharedPreferences.Editor mEditor = mPreferences.edit();

                int oldHighscorePoints = mPreferences.getInt(context.getString(R.string.highscorePoints), 0);

                // -- If the current points are higher than the highscore, then the points should be set as the new highscore -- //
                if(oldHighscorePoints < points) {

                    // -- Setting the new highscore in the preferences -- //
                    mEditor.putInt(context.getString(R.string.highscorePoints), getPoints());
                    mEditor.commit();

                    // -- Retrieving the newly set highScore from the preferences. (may not be necessary as it is retrived in getPoints() -- //
                    int newHighscorePoints = mPreferences.getInt(context.getString(R.string.highscorePoints), 0);
                    setHighscorePoints(newHighscorePoints);

                    // -- Display the new highscore to the view -- //
                    highscoreView.setText(" Highscore: " + getHighscorePoints());
                }

                // -- Restarting the game -- //
                this.newGame();

                // -- Break the loop. No more enemies are needed to be iterated. If one of them is hit the game restarts -- //
                break;

            }

        }

    }

    // -- Check if the player has won. This is done by checking remaining gold coins -- //
    public void checkWinningGame()
    {
        // -- Create a new ArrayList to contain remaining gold coins -- //
        ArrayList<GoldCoin> GoldcoinsRemaining = new ArrayList<>();

        // -- Run through all the gold coins in ArrayList and check if there is any remaining -- //
        for (GoldCoin coin:goldcoins) {
            if(coin.taken == false){
                GoldcoinsRemaining.add(coin);
            }
        }

        // -- If there is no gold coins remaining, then the game is won -- //
        if(GoldcoinsRemaining.size() == 0) {

            // -- If the current level is 1 and all coins are taken, then call method nextLevel() -- //
            if(level == 1) {
                nextLevel();
            }

            // -- If the current level is 2 and all coins are taken, then call method nextLevel() -- //
            else if(level == 2) {
                nextLevel();
            }

            // -- If the current level is 3 and all coins are taken, then the game is won -- //
            else {
                Toast.makeText(context, "Game is won", Toast.LENGTH_SHORT).show();
            }
        }
    }

    // -- Setting the GameView -- //
    public void setGameView(GameView view)
    {
        this.gameView = view;
    }

    // -- Getting GameView -- //
    public GameView getGameView() {
        return gameView;
    }

    // -- Getting pacman X coordinate -- //
    public int getPacx()
    {
        return pacx;
    }

    // -- Getting pacman Y coordinate -- //
    public int getPacy()
    {
        return pacy;
    }

    // -- Getting points / score -- //
    public int getPoints()
    {
        return points;
    }

    // -- Getting gold coins -- //
    public ArrayList<GoldCoin> getCoins()
    {
        return goldcoins;
    }

    // -- Getting enemies -- //
    public ArrayList<Enemy> getEnemies()
    {
        return enemies;
    }

    // -- Getting bitmap of pacman -- //
    public Bitmap getPacBitmap()
    {
        return pacBitmap;
    }

    // -- Getting bitmap of goldcoin -- //
    public Bitmap getGoldcoinBitmap()
    {
        return goldcoinBitmap;
    }

    // -- Getting bitmap of enemy -- //
    public Bitmap getEnemyBitmap()
    {
        return enemyBitmap;
    }

    // -- Setting the highscore -- //
    public void setHighscorePoints(int highscorePoints) {
        this.highscorePoints = highscorePoints;
    }

    // -- Retrieving the highscore -- //
    public int getHighscorePoints() {
        return highscorePoints;
    }

    // -- Retrieving the counter / timer -- //
    public void setCounter(int counter) {
        this.counter = counter;
    }

    // -- Retrieving the counter / timer -- //
    public int getCounter() {
        return counter;
    }
}
