package ui.com.fauxto.Camera;
import android.Manifest;
import android.app.Activity;
import android.app.Fragment;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import ui.com.fauxto.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static android.app.Activity.RESULT_OK;
import static android.content.ContentValues.TAG;

public class CameraActivity extends Fragment {

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private String mImageFileLocation = "";
    private StorageReference mStorageRef;
    private Uri imageURI;
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle   savedInstanceState) {
        View view = inflater.inflate(R.layout.camera_view, null);

        this.imageView = (ImageView)view.findViewById(R.id.imageView1);
        Button photoButton = (Button) view.findViewById(R.id.button1);
        mStorageRef = FirebaseStorage.getInstance().getReference();
        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCameraApp();
            }
        });

        return view;
    }

    /*public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_view);
        this.imageView = (ImageView)this.findViewById(R.id.imageView1);
        Button photoButton = (Button) this.findViewById(R.id.button1);
        mStorageRef = FirebaseStorage.getInstance().getReference();

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCameraApp();
            }
        });
    }*/

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


            myRef = database.getReference("user_images/"+randomString());
            myRef.setValue(UploadFile(imageURI));


        }

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

    public String  UploadFile(Uri file){
        String uuid = UUID.randomUUID().toString().replace("-", "");
        uuid = "images/"+uuid+".jpg";
        StorageReference riversRef = mStorageRef.child(uuid);
        Log.d("TAG","The file is " + file);
        riversRef.putFile(file)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        // Get a URL to the uploaded content
                        Uri downloadUrl = taskSnapshot.getDownloadUrl();
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
        return uuid;
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

    public static String randomString() {
        Random generator = new Random();
        StringBuilder randomStringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(5);
        char tempChar;
        for (int i = 0; i < randomLength; i++){
            tempChar = (char) (generator.nextInt(96) + 32);
            randomStringBuilder.append(tempChar);
        }
        return randomStringBuilder.toString();
    }



}
