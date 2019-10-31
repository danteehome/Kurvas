package com.example.kurvas.musicgame.Gamefolder;

import android.content.Intent;
import android.os.Bundle;

import com.example.kurvas.musicgame.MainActivity;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import androidx.annotation.NonNull;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kurvas.musicgame.R;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;


public class GameIdlePage extends MainActivity {


    private FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
    private int CurrentHighscore=200;
    private static final String TAG = "MainActivity";
    final FirebaseFirestore db = FirebaseFirestore.getInstance();


// ...


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_idle_page);

        TextView CurrentLogin = findViewById(R.id.NameHolder);
        Button btnLogout = findViewById(R.id.btnLogout);
        Button btnhighscore = findViewById(R.id.btnhighscore);
        Button btngethighscore=findViewById(R.id.btngethighscore);
        Button btnGame=findViewById(R.id.btn_startgame);

        Bundle userinfo=new Bundle();
        Intent receive=new Intent();
        userinfo=receive.getExtras();




        // Access a Cloud Firestore instance from your Activity




        btnhighscore.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            setHighscore();

        }
    });


        btngethighscore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getHighscore();
            }
        });

        btnGame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(GameIdlePage.this,Game.class);
                startActivity(intent);
            }
        });



        String[] spilt;
        spilt= user.getEmail().split("@",2);
        CurrentLogin.setText("Welcome! "+ spilt[0]);
        if (user.getUid() != null) {
            btnLogout.setVisibility(View.VISIBLE);
            btnLogout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    signOut();
                }
            });

        }

    }
    public void setHighscore(){
        final Map<String, Object> highscore = new HashMap<>();
        highscore.put("email", user.getEmail());
        highscore.put("highscore", CurrentHighscore);
        db.collection("UserScore").document(user.getEmail())
                .set(highscore)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void Avoid) {
                        Log.d(TAG,"DocumentSnapshot added with ID: " );
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
    public void getHighscore(){
        DocumentReference docRef = db.collection("UserScore").document(user.getEmail());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());

                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }

    private void signOut() {
        // [START auth_fui_signout]
        AuthUI.getInstance()
                .signOut(this)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NonNull Task<Void> task) {
                        // ...
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        Toast.makeText(GameIdlePage.this, "You had signed out, " + user.getEmail(), Toast.LENGTH_SHORT).show();
                    }
                });
                /*.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(LoginActivity.this);
                alertDialog.setCancelable(false);
                alertDialog.setMessage("Warning: Login failed. Please check your info");
                alertDialog.setPositiveButton("Try again", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent= new Intent(LoginActivity.this,MainActivity.class);
                        startActivity(intent);
                    }
                });

            }
        });*/
        // [END auth_fui_signout]



    }
}
