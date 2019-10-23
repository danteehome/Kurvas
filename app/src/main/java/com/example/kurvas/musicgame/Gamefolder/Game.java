package com.example.kurvas.musicgame.Gamefolder;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.kurvas.musicgame.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class Game extends AppCompatActivity {

    private ImageView dropper1;
    private ImageView dropper2;
    private ImageView dropper3;
    private ImageView dropper4;
    private TextView username;
    private TextView scoreboard;
    private TextView pause;

    private static final String TAG = "MainActivity";


    private int screenWidth, screenHeight;
    private int basemovespeed =20;
    private int score=0;
    private int highScore;
    private int globaldelay;
    private static int flag=0;
    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private String userName[];




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        //declair what's going on here
        //canvas contain a game
        //buttons flow down
        //click button when they touch the bottom, otherwise not count
        //button drop faster overtime

        //map layouts
        dropper1=findViewById(R.id.dropper1);
        dropper2=findViewById(R.id.dropper2);
        dropper3=findViewById(R.id.dropper3);
        dropper4=findViewById(R.id.dropper4);
        username=findViewById(R.id.player_name);
        scoreboard=findViewById(R.id.scorecontainer);
        pause=findViewById(R.id.menu);


        final FirebaseFirestore db = FirebaseFirestore.getInstance();
        userName=user.getEmail().split("@",2);
        username.setText(userName[0]);

        DocumentReference docRef = db.collection("UserScore").document(user.getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                        Map<String,Object> temp=new HashMap();
                        temp=document.getData();
                        highScore=Integer.parseInt(temp.get("highscore").toString());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });



        //get windows size
        WindowManager wm = getWindowManager();
        Display display= wm.getDefaultDisplay();
        Point size= new Point();
        display.getSize(size);
        screenHeight=size.y;
        screenWidth=size.x;

        //setup basic attributes
        dropper1.setY(-240.0f);
        dropper2.setY(-240.0f);
        dropper3.setY(-240.0f);
        dropper4.setY(-240.0f);
        scoreboard.setText(score);

        globaldelay=(int)(Math.random()*1000)+1500;


        dropRandomizer(dropper1);
        dropRandomizer(dropper2);
        dropRandomizer(dropper3);
        dropRandomizer(dropper4);

        //potential improvement: add a area of click, that only click below certain line will grant score
        dropper1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score++;

                //reset the position and set speed to 0;
                flag=1;
            }
        });
        dropper2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score++;
                scoreboard.setText(score);
                //reset the position and set speed to 0;
                flag=1;
            }
        });
        dropper3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score++;
                scoreboard.setText(score);
                //reset the position and set speed to 0;
                flag=1;
            }
        });
        dropper4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                score++;
                scoreboard.setText(score);
                //reset the position and set speed to 0;
                flag=1;
            }
        });








    }
    public void dropRandomizer(final ImageView dropper){
        //drop at random time, so need to set a timer schedule to call the drop function every several second
        final int slowdropRand,fastdropRand;
        slowdropRand=(int)(Math.random()*5000)+2000;
        fastdropRand=(int)(Math.random()*5000)+1000;
        //random decide if it's slow drop or fast drop
        final int period=(Math.random()<0.7 )? slowdropRand:fastdropRand;
        final Timer timer=new Timer();
        final Handler handler=new Handler();

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        changePos(dropper,globaldelay);
                    }
                });
            }
        },0,period);

    }


    //make a method so that they don't run unstoppable
    //
    public void Move(ImageView dropper, int movespeed){
        float pos = dropper.getY();
        pos+=movespeed;
        if (dropper.getY()>screenHeight){
            System.out.println(dropper.getY()+" "+screenHeight+ " "+pos);
            pos=-240.0f;
        }
        dropper.setY(pos);
    }
    public void resetPos(ImageView dropper){
        dropper.setY(-240.0f);
    }

    //make a function for movestring
    public void changePos(final ImageView dropper,int delay){
        final Timer timer=new Timer();
        final Handler handler=new Handler();
        final int random=(int)(Math.random()*10)+15;
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        //move the dropper in 20 milesecond interval
                        int currentmovespeed=basemovespeed;
                        Move(dropper, currentmovespeed + random);
                        //stop the timer when the dropper drop to bottom or get clicked
                        if (dropper.getY()>screenHeight|| flag==1){
                            timer.cancel();
                            resetPos(dropper);
                            flag=0;
                        }
                    }
                });
            }
        },delay,20);

    }
    public void setScore(int score, int highscore){
        if(score>highscore){
            highscore=score;
            scoreboard.setText(score);
        }

    }
}


