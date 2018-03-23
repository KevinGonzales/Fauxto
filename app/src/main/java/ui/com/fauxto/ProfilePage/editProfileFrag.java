package ui.com.fauxto.ProfilePage;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import ui.com.fauxto.R;
import ui.com.fauxto.tools.imageLoader;

/**
 * Created by Nick on 3/19/2018.
 */

public class editProfileFrag extends Fragment{

    private ImageView mProfilePhoto;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        mProfilePhoto = (ImageView) getView().findViewById(R.id.profile_photo);


        setProfileImage();

        View view = inflater.inflate(R.layout.f_editprofile,container,false);
        TextView editProfile = (TextView) view.findViewById(R.id.textEditProfile);
        editProfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return view;
    }




    private void setProfileImage(){
        String imageURL = "https://www.iconspng.com/images/ninja-fb-profile/ninja-fb-profile.jpg";
        imageLoader.setImage(imageURL, mProfilePhoto,null,"");
    }
}

