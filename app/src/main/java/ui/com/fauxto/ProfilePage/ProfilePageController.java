package ui.com.fauxto.ProfilePage;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Menu;
import android.view.ViewGroup;

import ui.com.fauxto.R;

import static android.content.ContentValues.TAG;

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

    private void setupToolBar(){
        Toolbar toolbar = (Toolbar) getView().findViewById(R.id.profileBar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                Log.d(TAG,"onMenuItemCLick: clicked menu" + item);
                switch (item.getItemId()){
                    case R.id.profileMenu:
                        Log.d(TAG, "Switching to profile settings");
                }
                return false;
            }
        });

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

    }
}
