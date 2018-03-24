package ui.com.fauxto.ProfilePage;

import android.app.Activity;
import android.app.Fragment;
import android.content.ContentProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
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

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


import java.util.ArrayList;

import ui.com.fauxto.R;

import static android.content.ContentValues.TAG;
import ui.com.fauxto.R;
import ui.com.fauxto.tools.gridImageAdapter;
import ui.com.fauxto.tools.imageLoader;

/**
 * Created by kevingonzales on 3/5/18.
 */

//public class ProfilePageController extends Fragment{
public class ProfilePageController extends Fragment{
    int contentView;
    private Context mContext = getActivity();//getContext().ProfilePageController.this;
    //private Context mContext = ProfilePageController.this;
    private UserModel user ;
    private ImageView profilePhoto;
    private ProgressBar mProgressBar;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener mAuthListener;
   // DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user_images");
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
        String imgURL = "www.iconspng.com/images/ninja-fb-profile/ninja-fb-profile.jpg";
        imageLoader.setImage(imgURL,profilePhoto,mProgressBar,"https://");
    }

    private void setUpWidgets(){
        mProgressBar = (ProgressBar) getView().findViewById(R.id.pLoadingBar);
        //mProgressBar = findViewById(R.id.pLoadingBar);
        mProgressBar.setVisibility(View.GONE);
        profilePhoto = (ImageView) getView().findViewById(R.id.profile_photo);
        //profilePhoto =  findViewById(R.id.profile_photo);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
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
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.profileBar);
        //Toolbar toolbar = (Toolbar) findViewById(R.id.profileBar);
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
       GridView gridView = (GridView) getView().findViewById(R.id.pGridView);
       //GridView gridView = (GridView) findViewById(R.id.pGridView);
       int gridWidth = getResources().getDisplayMetrics().widthPixels;
       int imageWidth = gridWidth/3;
       gridView.setColumnWidth(imageWidth);

       gridImageAdapter adapter = new gridImageAdapter(mContext, R.layout.layout_grid_imageview, "",imgURLs);
       gridView.setAdapter(adapter);
   }

    public void setContentView(int contentView) {
        this.contentView = contentView;
    }

    private void setupFirebaseAuth(){
        Log.d(TAG, "setupFirebaseAuth: setting up firebase auth.");

        mAuth = FirebaseAuth.getInstance();

        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = firebaseAuth.getCurrentUser();


                // ...
            }
        };
    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }
}
