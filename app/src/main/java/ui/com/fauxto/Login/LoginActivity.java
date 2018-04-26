package ui.com.fauxto.Login;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ui.com.fauxto.MainActivity;
import ui.com.fauxto.R;

public class LoginActivity extends AppCompatActivity {

    private EditText email;
    private EditText pass;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        email = findViewById(R.id.emailInput);
        pass = findViewById(R.id.passwordInput);
        progressBar = (ProgressBar) findViewById(R.id.loginProgressCircle);
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if(currentUser != null)
        {
            final Intent myIntent = new Intent(this,MainActivity.class);
            startActivity(myIntent);
            finish();
        }
    }

    public void LogInHandler(View view)
    {
        try {
            progressBar.setVisibility(View.VISIBLE);
            final Intent myIntent = new Intent(this,MainActivity.class);
            Log.d("TAG","MAdeIt");
            FirebaseAuth.getInstance().signInWithEmailAndPassword(email.getText().toString(), pass.getText().toString())
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                                startActivity(myIntent);
                                finish();
                            } else {
                                //error signing in
                            }

                            // ...
                        }
                    });

        }catch (Exception e){

        }


        Toast.makeText(this,"Wrong email or password",Toast.LENGTH_LONG);


    }

    public void RegisterHandler(View view) {
        Intent myIntent = new Intent(this,RegisterActivity.class);
        Log.d("TAG","register button clicked");
        startActivity(myIntent);
    }


}
