package ui.com.fauxto.ProfilePage;

import java.util.ArrayList;

/**
 * Created by kevingonzales on 3/5/18.
 */

public class UserModel {

    private ArrayList<UserModel> friends;
    private String firstName;
    private String lastName;
    private String userName;

    public ArrayList<UserModel> getFriends() {
        return friends;
    }

    public void setFriends(ArrayList<UserModel> friends) {
        this.friends = friends;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
