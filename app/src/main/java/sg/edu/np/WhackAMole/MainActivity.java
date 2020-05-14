package sg.edu.np.WhackAMole;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 2.
        - The function checkMole() takes in button selected and computes a hit or miss and adjust the score accordingly.
        - The function checkMole() also decides if the user qualifies for the advance level and triggers for a dialog box to ask for user to decide.
        - The function nextLevelQuery() builds the dialog box and shows. It also triggers the nextLevel() if user selects Yes or return to normal state if user select No.
        - The function nextLevel() launches the new advanced page.
        - Feel free to modify the function to suit your program.
    */
    private Integer Score = 0;
    private Button ButtonLeft;
    private Button ButtonMiddle;
    private Button ButtonRight;
    private TextView tv;
    private static final String TAG = "Whack-A-Mole";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //setting up buttons
        ButtonLeft = findViewById(R.id.ButtonLeft);
        ButtonMiddle = findViewById(R.id.ButtonMiddle);
        ButtonRight = findViewById(R.id.ButtonRight);
        tv  = findViewById(R.id.tv);
        Log.v(TAG, "Finished Pre-Initialisation!");



    }
    @Override
    protected void onStart(){
        super.onStart();
        setNewMole();
        Log.v(TAG, "Starting GUI!");
    }
    @Override
    protected void onPause(){
        super.onPause();
        Log.v(TAG, "Paused Whack-A-Mole!");
    }

    @Override
    protected void onStop(){
        super.onStop();
        Log.v(TAG, "Stopped Whack-A-Mole!");
        finish();
    }


    private void nextLevelQuery(){
        /*
        Builds dialog box here.
         */
        AlertDialog.Builder b = new AlertDialog.Builder(this);

        b.setTitle("Warning!");
        b.setMessage("Crazy level incoming, would you like to test your skills?");
        b.setCancelable(false);
        b.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User accepts!");
                nextLevel();
            }
        });
        b.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Log.v(TAG, "User decline!");
            }
        });
        AlertDialog alert = b.create();
        alert.show();
        Log.v(TAG, "Advanced option given to user!");

    }

    private void nextLevel(){
        /* Launch advanced page */
        Intent nextLevel = new Intent(MainActivity.this,Main2Activity.class);
        nextLevel.putExtra("Score",Score.intValue());
        startActivity(nextLevel);

    }

    private void setNewMole() {
        //Get random integer between 0 and 2
        Random ran = new Random();
        int randomLocation = ran.nextInt(3);
        //Get an array of buttons and randomly pick a button from random integer
        Button[] bArray = {ButtonLeft,ButtonMiddle,ButtonRight};
        Button mole = bArray[randomLocation];
        //Set text for each button depending on whether its a mole or not
        for (Button b : bArray){
            if (b == mole){
                b.setText("*");
            }
            else{
                b.setText("O");
            }

        }
    }
    /* Checks for hit or miss and if user qualify for advanced page.
            Triggers nextLevelQuery().*/
    public boolean checkMole(String s){
        boolean Mole;
        int tempScore = Score;
        if(s.equals("*")){
            Mole = true;
            tempScore += 1;
        }
        else{
            Mole = false;
            tempScore -= 1;
        }
        if (tempScore % 10 == 0 && tempScore != 0){
            nextLevelQuery();
        }
        return Mole;
    }
    //Update score function to be run after each round
    public void UpdateScore(){
        String score = String.valueOf(Score);
        tv.setText(score);
    }
    //Onclick button function, to handle the click event handler, i defined it in the xml file
    public void onClickButton(View v){
        //Practical 2.1
        switch(v.getId()){
            case R.id.ButtonLeft:
                Log.v(TAG,"Left Button Clicked!");
                break;

            case R.id.ButtonMiddle:
                Log.v(TAG,"Middle Button Clicked");
                break;

            default:
                Log.v(TAG,"Right Button Clicked");
        }
        //downcast v to button
        Button button = (Button) v;
        //Checks if the button pressed is a mole or not
        if(checkMole((String) button.getText())){
            //increase score
            Score++;
            Log.v(TAG,"You hit the mole!");
        }
        //Only deduct score if score is above 0
        else if(Score != 0){
            Score--;
            Log.v(TAG,"You missed the mole!");
        }
        else{
            Log.v(TAG,"You missed the mole!");
        }
        //Functions to run after each round
        setNewMole();
        UpdateScore();



    }
}