package ui.com.fauxto.UserFeed;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Debug;
import android.util.Log;
import android.widget.ImageView;

/**
 * Created by kevingonzales on 3/21/18.
 */

public class ListItem {

    private String head;
    private String desc;
    private Bitmap image;

    public ListItem(String head, String desc,Bitmap im) {
        this.head = head;
        this.desc = desc;
        this.image = im;
        Log.d("boooo","In listItem" + im.toString());
    }

    public String getHead() {
        return head;
    }

    public Bitmap getImage() {

        return image;
    }

    public String getDesc() {
        return desc;
    }
}
