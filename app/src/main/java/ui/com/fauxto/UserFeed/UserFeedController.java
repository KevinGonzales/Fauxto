package ui.com.fauxto.UserFeed;

import ui.com.fauxto.ProfilePage.UserModel;

/**
 * Created by kevingonzales on 3/5/18.
 */

public class UserFeedController {
    private UserModel user;

    //get the user feed to cuztomize their feed
    public UserFeedController(UserModel user){
        this.user = user;
    }

    public UserModel getUser() {
        return user;
    }

    public void setUser(UserModel user) {
        this.user = user;
    }


}
