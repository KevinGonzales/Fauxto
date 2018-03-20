package ui.com.fauxto.Camera;
import android.Manifest;
import android.app.Activity;
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
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

import ui.com.fauxto.R;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import static android.content.ContentValues.TAG;

public class CameraActivity extends Activity {

    private static final int CAMERA_REQUEST = 1888;
    private ImageView imageView;
    private String mImageFileLocation = "";

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.camera_view);
        this.imageView = (ImageView)this.findViewById(R.id.imageView1);
        Button photoButton = (Button) this.findViewById(R.id.button1);

        photoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                callCameraApp();
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (RESULT_OK == resultCode) {

            // Decode it for real
            BitmapFactory.Options bmpFactoryOptions = new BitmapFactory.Options();
            bmpFactoryOptions.inJustDecodeBounds = false;

            //imageFilePath image path which you pass with intent
            Bitmap bmp = BitmapFactory.decodeFile(mImageFileLocation , bmpFactoryOptions);

            // Display it
            imageView.setImageBitmap(bmp);
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
        Uri imageURI = FileProvider.getUriForFile(this, authorities, photoFile);
        try {
            cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, imageURI);
            startActivityForResult(cameraIntent, CAMERA_REQUEST);
        } catch (Exception e) {
            Log.e("EX", "exception", e);
        }
    }

    /*public void  UploadFile(Uri file){
        final String uuid = UUID.randomUUID().toString().replace("-", "");
        StorageReference riversRef = mStorageRef.child("images/"+uuid+".jpg");
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
    }*/

    File createImageFile() throws IOException {

        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );
        mImageFileLocation = image.getAbsolutePath();

        return image;

    }



}
