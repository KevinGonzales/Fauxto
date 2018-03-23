package ui.com.fauxto.ProfilePage;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.view.View.OnClickListener;

import java.util.ArrayList;

import ui.com.fauxto.R;

import static android.content.ContentValues.TAG;
import ui.com.fauxto.R;
import ui.com.fauxto.tools.gridImageAdapter;
import ui.com.fauxto.tools.imageLoader;

/**
 * Created by kevingonzales on 3/5/18.
 */

public class ProfilePageController extends Activity{

    int contentView;
    private Context mContext = ProfilePageController.this;
    private UserModel user ;
    private ImageView profilePhoto;
    private ProgressBar mProgressBar;
    /*ProfilePageController(UserModel user){
        this.user = user;

    }*/

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile_page);
        setUpWidgets();
        setProfileImage();
        tempGridSetup();
        //mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
       // mProgressBar.setVisibility(View.GONE);
        setupToolBar();
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~TERST");
    }

    private void tempGridSetup(){
        ArrayList<String> imgURLs = new ArrayList<>();
        imgURLs.add("https://wallpaperbrowse.com/media/images/968361.jpg");
        imgURLs.add("https://wallpaperbrowse.com/media/images/141216183300-simpsons-25-anniversary-image-4-horizontal-large-gallery.jpg");
        imgURLs.add("http://images5.fanpop.com/image/photos/31000000/haters-gonna-hate-random-31076705-550-413.jpg");
        imgURLs.add("http://rkvc.net/wp-content/uploads/2014/01/dog-in-paper-bag-so-many-crazy-things-RKVC.jpg");

        setupImageGrid(imgURLs);
    }

    private void setProfileImage(){
        String imgURL = "https://www.iconspng.com/images/ninja-fb-profile/ninja-fb-profile.jpg";
        imageLoader.setImage(imgURL,profilePhoto,mProgressBar,"");
    }

    private void setUpWidgets(){
        mProgressBar = (ProgressBar) findViewById(R.id.pLoadingBar);
        mProgressBar.setVisibility(View.GONE);
        profilePhoto = (ImageView) findViewById(R.id.profile_photo);
    }

    /*public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance){
        View view = inflater.inflate(R.layout.f_editprofile,container,false);
        TextView editProfile = (TextView) view.findViewById(R.id.textEditProfile);
        super.onCreate(savedInstance);
        setContentView(R.layout.profile_page);
        //editProfile.setOnClickListener();

        return  inflater.inflate(R.layout.profile_page,null);
    }*/

    private void setupToolBar(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.profileBar);
        /*toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d(TAG,"onMenuItemCLick: clicked menu" + item);
                switch (item.getItemId()){
                    case R.id.profileMenu:
                        Log.d(TAG, "Switching to profile settings");
                }
                return false;
            }
        }
        );*/

    }

    private void setUpGridView(){
        //final ArrayList<>
    }

   /* @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }*/

   public void setupImageGrid(ArrayList<String> imgURLs){
       GridView gridView = (GridView) findViewById(R.id.pGridView);

       int gridWidth = getResources().getDisplayMetrics().widthPixels;
       int imageWidth = gridWidth/3;
       gridView.setColumnWidth(imageWidth);

       gridImageAdapter adapter = new gridImageAdapter(mContext, R.layout.layout_grid_imageview, "",imgURLs);
       gridView.setAdapter(adapter);
   }

    public void setContentView(int contentView) {
        this.contentView = contentView;
    }
}
