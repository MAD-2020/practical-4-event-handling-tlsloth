package sg.edu.np.WhackAMole;

import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Main2Activity extends AppCompatActivity {
    /* Hint
        - The function setNewMole() uses the Random class to generate a random value ranged from 0 to 8.
        - The function doCheck() takes in button selected and computes a hit or miss and adjust the score acordingly.
        - The functions readTimer() and placeMoleTimer() are to inform the user X seconds before starting and loading new mole.
        - Feel free to modify the function to suit your program.
    */
    private String TAG = "Whack-A-Mole 2.0";
    private Integer advancedScore;
    CountDownTimer getReady;
    CountDownTimer InfiniteLoop;
    TextView tv;
    List<Button> buttonList = new ArrayList<>();
    boolean gamestart = false;



    private void readyTimer(){
        /*  HINT:
            The "Get Ready" Timer.
            Log.v(TAG, "Ready CountDown!" + millisUntilFinished/ 1000);
            Toast message -"Get Ready In X seconds"
            Log.v(TAG, "Ready CountDown Complete!");
            Toast message - "GO!"
            belongs here.
            This timer countdown from 10 seconds to 0 seconds and stops after "GO!" is shown.
         */
        final Toast[] ToastArray = {null};
        getReady = new CountDownTimer(10000,1000) {
            @Override
            public void onTick(long l) {
                Log.v(TAG, "Ready CountDown!" + l/ 1000);
                Toast.makeText(getApplicationContext(),"Get ready in "+ l/1000 + "seconds!",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFinish() {
                Log.v(TAG, "Ready CountDown Complete!");
                Toast.makeText(getApplicationContext(),"GO!",Toast.LENGTH_SHORT).show();
                getReady.cancel();
                placeMoleTimer();
                gamestart = true;
            }
        };
        getReady.start();


    }
    private void placeMoleTimer(){
        /* HINT:
           Creates new mole location each second.
           Log.v(TAG, "New Mole Location!");
           setNewMole();
           belongs here.
           This is an infinite countdown timer.
         */
        InfiniteLoop = new CountDownTimer(1000,1000) {
            @Override
            public void onTick(long l) {
                setNewMole();
            }

            @Override
            public void onFinish() {
                InfiniteLoop.start();
            }

        };
        InfiniteLoop.start();

    }
    private static final int[] BUTTON_IDS = {
        /* HINT:
            Stores the 9 buttons IDs here for those who wishes to use array to create all 9 buttons.
            You may use if you wish to change or remove to suit your codes.*/
        R.id.button,R.id.button2,R.id.button3,R.id.button4,R.id.button5,R.id.button6,R.id.button7,R.id.button8,R.id.button9
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*Hint:
            This starts the countdown timers one at a time and prepares the user.
            This also prepares the existing score brought over.
            It also prepares the button listeners to each button.
            You may wish to use the for loop to populate all 9 buttons with listeners.
         */

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Intent receiveScore = getIntent();
        advancedScore = receiveScore.getIntExtra("Score",10);
        readyTimer();
        tv = findViewById(R.id.tv);
        for(int id : BUTTON_IDS){
            Button button = findViewById(id);
            buttonList.add(button);
        }

        Log.v(TAG, "Current User Score: " + advancedScore);

    }
    @Override
    protected void onStart(){
        setNewMole();
        UpdateScore();
        super.onStart();
    }
    private boolean doCheck(Button checkButton)
    {
        /* Hint:
            Checks for hit or miss
            Log.v(TAG, "Hit, score added!");
            Log.v(TAG, "Missed, point deducted!");
            belongs here.
        */
        String text = (String) checkButton.getText();
        return text == "*";
    }

    public void setNewMole()
    {
        // Hint:
        //   Clears the previous mole location and gets a new random location of the next mole location.
        //   Sets the new location of the mole.
        //
        Random ran = new Random();
        int randomLocation = ran.nextInt(9);
        Button mole = buttonList.get(randomLocation);
        for (Button button : buttonList){
            if(button == mole){
                button.setText("*");
            }
            else{
                button.setText("O");
            }
        }
        Log.v(TAG,"New mole location!");

    }
    public void UpdateScore(){
        String score = String.valueOf(advancedScore);
        tv.setText(score);
    }

    public void onClickButton(View v){
        if (gamestart) {
            Button b = (Button) v;
            if (doCheck(b)) {
                advancedScore++;
                Log.v(TAG, "Mole hit! Point added!");
            } else {
                advancedScore--;
                Log.v(TAG, "Mole missed, Point deducted.");
            }
            UpdateScore();
        }

    }
}

