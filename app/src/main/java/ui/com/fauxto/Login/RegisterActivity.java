package ui.com.fauxto.Login;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ui.com.fauxto.MainActivity;
import ui.com.fauxto.R;

/**
 * Created by myka on 3/7/18.
 */

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";

    //firebase authentication
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;

    private Context mContext;
    private String email, firstName, lastName, username, password;
    private EditText emailInput, firstNameInput, lastNameInput, usernameInput, passwordInput;
    private TextView pleaseWait;
    private Button registerButton;
    private ProgressBar progressBar;

    private void initFields() {
        emailInput = (EditText) findViewById(R.id.emailInput);
        firstNameInput = (EditText) findViewById(R.id.firstNameInput);
        lastNameInput = (EditText) findViewById(R.id.lastNameInput);
        usernameInput = (EditText) findViewById(R.id.usernameInput);
        passwordInput = (EditText) findViewById(R.id.passwordInput);
        registerButton = (Button) findViewById(R.id.registerButton);
        mContext = RegisterActivity.this;

    }

    private void onClickedRegistButton() {
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                email = emailInput.getText().toString();
                firstName = firstNameInput.getText().toString();
                lastName = lastNameInput.getText().toString();
                username = usernameInput.getText().toString();
                password = passwordInput.getText().toString();

                //empty or null?
                if(email.equals("") || firstName.equals("") || lastName.equals("") || username.equals("") || password.equals("")){
                    Toast.makeText(mContext, "All fields must be filled out", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, "Signing up...", Toast.LENGTH_SHORT).show();
                    //should be an email?
                    createAccount(email, password);
                    changeViewToMain();
                }
            }
        });
    }

    public void createAccount(String email, String password) {
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "createUserWithEmail:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(mContext, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_page);
        initFields();
        mAuth = FirebaseAuth.getInstance();
        onClickedRegistButton();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        updateUI(currentUser);
    }

    public void updateUI(FirebaseUser user) {
        if(user == null) {
            Log.d(TAG, "user logged out:");
        } else {
            Log.d(TAG, "user logged in: " + user.getUid());
        }
    }

    public void changeViewToMain()
    {
        Intent myIntent = new Intent(this,MainActivity.class);
        Log.d(TAG,"switched view from register to main");
        startActivity(myIntent);
    }

}
