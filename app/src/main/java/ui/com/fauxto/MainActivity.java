package ui.com.fauxto;

import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;
import android.widget.Toast;
import android.Manifest;
import ui.com.fauxto.Camera.CameraActivity;
import ui.com.fauxto.ProfilePage.ProfilePageController;
import ui.com.fauxto.UserFeed.FeedActivity;
import ui.com.fauxto.UserFeed.UserFeedController;

/**
 * Created by kevingonzales on 3/7/18.
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{

    private final int CAMERA_PERMISSION =1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_app_view);

        BottomNavigationView navigationView = (BottomNavigationView) findViewById(R.id.navigationBar);

        navigationView.setOnNavigationItemSelectedListener(this);

        //loadFragment(new UserFeedController());
    }

    @Override //Whenever we tap on the menu on the navigation bar this method is called
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()){
            case R.id.navigation_feed:
                Intent myIntenst = new Intent(this,FeedActivity.class);
                startActivity(myIntenst);
                break;

            case R.id.navigation_profilePic:
                fragment = new ProfilePageController();
                break;
            case R.id.navigation_Camera:

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
                    }
                }
                try{
                    Intent myIntent = new Intent(this,CameraActivity.class);
                    startActivity(myIntent);
                }catch (Exception e){
                    Log.e("ERR","Error here",e);
                }

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


    @Override
    public void onRequestPermissionsResult(int requestCode,  String permissions[], int[] grantResults) {
        switch (requestCode) {
            case CAMERA_PERMISSION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    if(MainActivity.class != null) {
                        Intent myIntent = new Intent(this,CameraActivity.class);
                        startActivity(myIntent);
                    }
                } else {
                    Toast.makeText(this, "Please grant camera permission to use the Camera", Toast.LENGTH_SHORT).show();
                }
                return;
        }
    }




}
