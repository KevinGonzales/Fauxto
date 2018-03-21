package ui.com.fauxto.ProfilePage;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

import ui.com.fauxto.R;

/**
 * Created by Nick on 3/19/2018.
 */

public class editProfileFrag extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_editprofile,container,false);
        TextView editProfile = (TextView) view.findViewById(R.id.textEditProfile);
        editProfile.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });


        return view;
    }
}

