package ui.com.fauxto.UserFeed;

public class feedModel {
    private String imageName;
    private String Description;
    private String User;

    public feedModel()
    {

    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        this.Description = description;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        this.User = user;
    }

    public String toString(){

        return "My models imageName is "+ getImageName()+" , user is "+ getUser()+" , and description is "+ getDescription();
    }


}
