package comglebzavyalov.vk.rglass.Activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import comglebzavyalov.vk.rglass.R;

public class LoginActivity extends AppCompatActivity {


    private FirebaseAuth userAuth;
    TextView textViewReg;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userAuth = FirebaseAuth.getInstance();

        textViewReg = (TextView) findViewById(R.id.textViewRegLabel);


        textViewReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegActivity.class);
                startActivity(intent);
            }
        });




    }

    public void clickSignIn(View view){

        Intent intent = new Intent(LoginActivity.this, LoginingActivity.class);
        startActivity(intent);

//        userAuth.signInWithEmailAndPassword(editTextLogin.getText().toString(), editTextPass.getText().toString())
//                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
//                    @Override
//                    public void onComplete(@NonNull Task<AuthResult> task) {
//                        if (task.isSuccessful()) {
//                            // Sign in success, update UI with the signed-in user's information
//                            FirebaseUser user = userAuth.getCurrentUser();
//                            Intent intent  = new Intent(LoginActivity.this, MainActivity.class);
//                            startActivity(intent);
//                           // updateUI(user);
//                        } else {
//                            // If sign in fails, display a message to the user.
////                            Log.w(TAG, "signInWithEmail:failure", task.getException());
//                            Toast.makeText(LoginActivity.this, "Authentication failed.",
//                                    Toast.LENGTH_SHORT).show();
//                            //updateUI(null);
//                        }
//
//
//                        // ...
//                    }
//                });
    }


    @Override
    public void onStart(){
        super.onStart();

        FirebaseUser  currentUser = userAuth.getCurrentUser();

        if(currentUser != null){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);

        }

    }





}
