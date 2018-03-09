package ui.com.fauxto.ProfilePage;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ui.com.fauxto.R;

/**
 * Created by kevingonzales on 3/5/18.
 */

public class ProfilePageController extends Fragment{

    private UserModel user ;
    /*ProfilePageController(UserModel user){
        this.user = user;

    }*/

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance){
        return  inflater.inflate(R.layout.profile_page,null);
    }

}
