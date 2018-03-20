package ui.com.fauxto.ProfilePage;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ui.com.fauxto.R;

/**
 * Created by Nick on 3/19/2018.
 */

public class editProfileFrag extends Fragment{

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.f_editprofile,container,false);
        return view;
    }
}

