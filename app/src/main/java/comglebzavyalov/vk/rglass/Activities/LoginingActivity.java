package comglebzavyalov.vk.rglass.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import comglebzavyalov.vk.rglass.R;

public class LoginingActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;

    EditText editTextEmail, editTextPass;
    Button buttonLog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logining);

        mAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPass = (EditText) findViewById(R.id.editTextPass);
        buttonLog = (Button) findViewById(R.id.buttonLog);






    }

    public void clickLogin(View view){
        mAuth.signInWithEmailAndPassword(editTextEmail.getText().toString(), editTextPass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user = mAuth.getCurrentUser();
                            Intent intent = new Intent(LoginingActivity.this, MainActivity.class);
                            startActivity(intent);

                            SharedPreferences sharedPreferences = getSharedPreferences("RGlass", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("mUid", user.getUid());
                            editor.apply();


                        } else {

                            Toast.makeText(LoginingActivity.this, "Email or password is incorrect!", Toast.LENGTH_SHORT).show();


                        }

                        // ...
                    }
                });
    }



}
