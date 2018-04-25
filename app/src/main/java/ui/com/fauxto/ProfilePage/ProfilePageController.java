package ui.com.fauxto.ProfilePage;

import android.Manifest;
import android.app.Fragment;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
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
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import ui.com.fauxto.Login.LoginActivity;
import ui.com.fauxto.R;
import ui.com.fauxto.UserFeed.ListItem;

import static android.app.Activity.RESULT_OK;

/**
 * Created by kevingonzales on 3/5/18.
 */

public class ProfilePageController extends Fragment{

    private Target target;
    private Bitmap my_image;
    private static final int CAMERA_REQUEST = 1888;
    private Uri imageURI;
    private String mImageFileLocation = "";
    private ImageView imageView;
    private EditText name;
    private EditText Description;
    private Button logoutbtn;
    private Button updateInfoBtn;

    final Set<Target> protectedFromGarbageCollectorTargets = new HashSet<>();//need a strong refrence or something



    private DatabaseReference myUnameRef;
    DatabaseReference myDescnameRef;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance){

        View view = inflater.inflate(R.layout.profile_page,null);

        imageView = view.findViewById(R.id.ProfilePage);
        name = view.findViewById(R.id.NameText);
        Description = view.findViewById(R.id.description);
        updateInfoBtn = view.findViewById(R.id.updateInfoBtn);

        getProfilePic();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateProfilePic();
            }
        });

        updateInfoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateInfo();
            }
        });

        logoutbtn = view.findViewById(R.id.loggoutBtn);
        logoutbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent intent = new Intent(getActivity(),LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });

        //now set the info based of the DB
        myUnameRef= FirebaseDatabase.getInstance().getReference("users/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/UserName");
        myUnameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                name.setText((String)dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

        myDescnameRef = FirebaseDatabase.getInstance().getReference("users/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/Description");
        myDescnameRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Description.setText((String)dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


        return  view;
    }

    private void getProfilePic() {
        DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/ProfilePicURL");

        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String url = (String) dataSnapshot.getValue();
                if(url.equals("NA")){
                    //do nothing no pic
                }else {
                    //now do firbase thing to ger the file with picasso
                    StorageReference gsReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://fauxto-c9310.appspot.com/"+url);
                    gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
                    {
                        @Override
                        public void onSuccess(Uri downloadUrl)
                        {
                            //do something with downloadurl
                            setImage(downloadUrl.toString());
                            Log.d("URL","URL IS "+ downloadUrl.toString());

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void UpdateProfilePic() {
        callCameraApp();
    }

    private void UpdateInfo() {
        myDescnameRef.setValue(Description.getText().toString());
        myUnameRef.setValue(name.getText().toString());
    }

    private void callCameraApp() {
        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        File photoFile = null;

        try {
            photoFile = createImageFile();

        } catch (IOException e) {
            Log.e("EX", "exception with PhotoFile", e);
        }

        String authorities = "ui.com.fauxto.fileProvider";
        imageURI = FileProvider.getUriForFile(getActivity(), authorities, photoFile);
        try {

            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED){
                ActivityCompat.requestPermissions(getActivity(), new String[] {Manifest.permission.CAMERA}, CAMERA_REQUEST);
            }

            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        } catch (Exception e) {
            Log.e("EX", "exception", e);
        }
    }

    File createImageFile() throws IOException {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        mImageFileLocation = image.getAbsolutePath();

        return image;

    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (RESULT_OK == resultCode) {

            // Decode it for real
            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = false;

            //imageFilePath image path which you pass with intent
            Bitmap bmp = BitmapFactory.decodeFile(mImageFileLocation , bmpFactoryOptions);

            // Display it
            imageView.setImageBitmap(bmp);

            //now start uploading it


            final String imageName = UUID.randomUUID().toString().replace("-", "");
            DatabaseReference myRef = FirebaseDatabase.getInstance().getReference("users/"+FirebaseAuth.getInstance().getCurrentUser().getUid()+"/ProfilePicURL");
            myRef.setValue(imageName);
            //now set the user and stuff of the images
            UploadFile(imageURI,imageName);



        }

    }

    public void   UploadFile(Uri file,String imageName){

        StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
        StorageReference riversRef = mStorageRef.child(imageName);
        Log.d("TAG","The file is " + file);
        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Log.d("TAG","Passed");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        Log.d("TAG", "Failed");
                        Log.e("TAG", "exception", exception);
                    }
                });
    }

    private void setImage(String url){

        Picasso.with(getActivity())
                .load(url)
                .into(target = new Target() {
                    @Override
                    public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                        my_image = bitmap;

                        Log.d("Tag","sucess");
                        Log.d("Tag"," bitmap is not null? " + bitmap.toString() );

                        imageView.setImageBitmap(my_image);
                        Log.d("TAGL","The messege is "+my_image.toString());
                    }

                    @Override
                    public void onBitmapFailed(Drawable errorDrawable) {
                        Log.d("Tag","failed");
                    }

                    @Override
                    public void onPrepareLoad(Drawable placeHolderDrawable) {
                        Log.d("Tag","prepering");
                    }
                });

        protectedFromGarbageCollectorTargets.add(target);

    }
}
