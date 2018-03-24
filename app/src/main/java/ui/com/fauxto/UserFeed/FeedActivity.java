package ui.com.fauxto.UserFeed;

import android.app.Fragment;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Debug;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.ui.storage.images.FirebaseImageLoader;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import ui.com.fauxto.R;

/**
 * Created by kevingonzales on 3/21/18.
 */

public class FeedActivity extends Fragment {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private Target target;

    private List<String> imageURLs;
    private List<ListItem> listItems;
    private Bitmap my_image;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();


    final Set<Target> protectedFromGarbageCollectorTargets = new HashSet<>();//need a strong refrence or something



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle   savedInstanceState) {
        View view = inflater.inflate(R.layout.feed,null);

        imageURLs = new ArrayList<String>();
        listItems = new ArrayList<ListItem>();


        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user_images");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot


                        HashMap<String, String> ss = (HashMap<String, String>) dataSnapshot.getValue();
                        Log.d("TAGL",ss.toString());
                        for (Map.Entry<String, String> entry : ss.entrySet()){
                            //get URL Locations
                            imageURLs.add(entry.getValue());
                            Log.d("TAGL",entry.getValue());
                        }


                        //now that u got URL now get the images
                        //Log.d("TAGL","The size is "+ imageURLs.size());
                        for(int i = 0; i< imageURLs.size(); i++){

                            //now do firbase thing to ger the file with picasso
                            StorageReference gsReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://fauxto-c9310.appspot.com/"+imageURLs.get(i));
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
                            //Log.d("URL","URL is "+gsReference.getDownloadUrl().toString());
                        }
                        //not sure why but its doing out of order so inflate now
                        inflateTheUI();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        recyclerView = view.findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity())); //make a vetical recycler view

        adapter = new MyAdapter(listItems,getActivity());
        recyclerView.setAdapter(adapter);

        return view;
    }

    /*
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed);


        imageURLs = new ArrayList<String>();
        listItems = new ArrayList<ListItem>();

        //Picasso.with(getApplicationContext()).setLoggingEnabled(true);
        //final ImageView imageView1 = findViewById(R.id.imageView1);
        //Picasso.with(getApplicationContext()).load("https://firebasestorage.googleapis.com/v0/b/fauxto-c9310.appspot.com/o/images%2Ff611663ff17048b2b46aae9be12db569.jpg?alt=media&token=a0c39076-b93b-4c15-a101-614172e7f481").into(imageView1);

        //imageView1.buildDrawingCache();
        //Bitmap bmap = imageView1.getDrawingCache();


        // Read from the database
        DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("user_images");
        ref.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        //Get map of users in datasnapshot


                        HashMap<String, String> ss = (HashMap<String, String>) dataSnapshot.getValue();
                        Log.d("TAGL",ss.toString());
                        for (Map.Entry<String, String> entry : ss.entrySet()){
                            //get URL Locations
                            imageURLs.add(entry.getValue());
                            Log.d("TAGL",entry.getValue());
                        }


                        //now that u got URL now get the images
                        //Log.d("TAGL","The size is "+ imageURLs.size());
                        for(int i = 0; i< imageURLs.size(); i++){

                            //now do firbase thing to ger the file with picasso
                            StorageReference gsReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://fauxto-c9310.appspot.com/"+imageURLs.get(i));
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
                            //Log.d("URL","URL is "+gsReference.getDownloadUrl().toString());
                        }
                        //not sure why but its doing out of order so inflate now
                        inflateTheUI();
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        //handle databaseError
                    }
                });

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //make a vetical recycler view

        adapter = new MyAdapter(listItems,this);
        recyclerView.setAdapter(adapter);

    }*/

    private void inflateTheUI(){
        //have to set empty adapter when done loading update it with a full one
        //adapter = new MyAdapter(listItems,this);
        adapter = new MyAdapter(listItems,getActivity());
        recyclerView.setAdapter(adapter);
    }

    private void getDownloadURL(String imageGURL) {
        StorageReference gsReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://fauxto-c9310.appspot.com/"+imageGURL);
        gsReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>()
        {
            @Override
            public void onSuccess(Uri downloadUrl)
            {
                //do something with downloadurl
            }
        });
        Log.d("URL","URL is "+gsReference.getDownloadUrl().toString());
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


                        ListItem listItem = new ListItem("Heading" ,"desc",my_image);
                        Log.d("TAGL","The messege is "+my_image.toString());
                        listItems.add(listItem);
                        inflateTheUI();
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
