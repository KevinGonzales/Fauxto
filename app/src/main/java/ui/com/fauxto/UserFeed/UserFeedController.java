package ui.com.fauxto.UserFeed;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import ui.com.fauxto.ProfilePage.UserModel;
import ui.com.fauxto.R;

/**
 * Created by kevingonzales on 3/5/18.
 */

public class UserFeedController extends Fragment {
    private UserModel user;

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstance){
        return  inflater.inflate(R.layout.feed,null);
    }

    /*//get the user feed to cuztomize their feed
    public UserFeedController(UserModel user){
        this.user = user;
    }*/

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }


}
