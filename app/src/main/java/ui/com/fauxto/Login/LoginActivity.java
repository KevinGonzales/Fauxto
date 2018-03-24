package ui.com.fauxto.Login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import ui.com.fauxto.MainActivity;
import ui.com.fauxto.R;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void LogInHandler(View view)
    {
        Intent myIntent = new Intent(this,MainActivity.class);
        Log.d("TAG","MAdeIt");
        startActivity(myIntent);
    }

    public void RegisterHandler(View view) {
        Intent myIntent = new Intent(this,RegisterActivity.class);
        Log.d("TAG","register button clicked");
        startActivity(myIntent);
    }


}
