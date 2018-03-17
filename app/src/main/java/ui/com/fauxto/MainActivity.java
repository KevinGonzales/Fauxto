package ui.com.fauxto;

import android.app.Fragment;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import ui.com.fauxto.Camera.CameraActivity;
import ui.com.fauxto.Login.LoginActivity;
import ui.com.fauxto.ProfilePage.ProfilePageController;
import ui.com.fauxto.UserFeed.UserFeedController;

/**
 * Created by kevingonzales on 3/7/18.
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{



    private FirebaseAuth mAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_app_view);

        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigationBar);

        navigationView.setOnNavigationItemSelectedListener(this);

        loadFragment(new UserFeedController());

        mAuth = FirebaseAuth.getInstance();
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        //checking if user is logged in
        if(currentUser == null){
            Intent intent = new Intent(this,LoginActivity.class);
            startActivity(intent);
        }else{

        }
        //updateUI(currentUser);
    }


    @Override //Whenever we tap on the menu on the navigation bar this method is called
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.navigation_feed:
                fragment = new UserFeedController();
                break;

            case R.id.navigation_profilePic:
                fragment = new ProfilePageController();
                break;
            case R.id.navigation_Camera:
                Intent myIntent = new Intent(this,CameraActivity.class);
                Log.d("TAG","IN");
                startActivity(myIntent);
        }

        return loadFragment(fragment);
    }

    private boolean loadFragment(Fragment fragment){
        if(fragment!=null){

            getFragmentManager().beginTransaction().replace(R.id.fragment_container,fragment).commit();

            return  true;
        }
        return false;
    }


}
