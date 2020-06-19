package com.sem.e_health;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import androidx.appcompat.app.AppCompatActivity;

import static com.sem.e_health.DoctorActivity.changeStatusBarToWhite;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    TextView registre ;
    ImageView BtLogin ;
    EditText email ;
    EditText password ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        changeStatusBarToWhite(this);


        mAuth = FirebaseAuth.getInstance();
        BtLogin = findViewById(R.id.imageView33);
        email = findViewById(R.id.edt_email);
        password = findViewById(R.id.edt_password);
                BtLogin.setOnClickListener(view -> Login());

        registre = findViewById(R.id.tc_registre);
        registre.setOnClickListener(ls);
    }




    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    View.OnClickListener ls = v -> startActivity(new Intent(MainActivity.this,Registre.class));
    private void updateUI(FirebaseUser currentUser) {


        if(currentUser != null){

            startActivity(new Intent(MainActivity.this,DoctorActivity.class));
        }

    }
    public void Login(){
        if (email.getText().toString().length() == 0){   email.setError("Email is required!");}
        else if (password.getText().toString().length() == 0) {   password.setError("Password is required!");}
        else {

            mAuth.signInWithEmailAndPassword(email.getText().toString(), password.getText().toString())
                    .addOnCompleteListener(this, task -> {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            // Log.d(TAG, "signInWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            //Log.w(TAG, "signInWithEmail:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    });
        }
    }


}
