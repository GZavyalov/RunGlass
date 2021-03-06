package comglebzavyalov.vk.rglass.Activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import comglebzavyalov.vk.rglass.R;

public class RegActivity extends AppCompatActivity {
    EditText editTextEmail, editTextPass;
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reg);
        editTextEmail = (EditText) findViewById(R.id.editTextEmail);
        editTextPass = (EditText) findViewById(R.id.editTextPass);
        mAuth = FirebaseAuth.getInstance();


    }
    public void clickReg(View view){

        mAuth.createUserWithEmailAndPassword(editTextEmail.getText().toString(), editTextPass.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();

                            SharedPreferences sharedPreferences = getSharedPreferences("RGlass", Context.MODE_PRIVATE);
                            SharedPreferences.Editor editor = sharedPreferences.edit();

                            editor.putString("mUid", user.getUid());
                            editor.apply();

                            Intent intent = new Intent(RegActivity.this, MainActivity.class);
                            startActivity(intent);
                        } else {
                            Toast.makeText(RegActivity.this, "Regestration failed.",
                                    Toast.LENGTH_SHORT).show();
                        }

                    }
                });

    }
}
