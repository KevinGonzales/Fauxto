package ui.com.fauxto.UserFeed;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import ui.com.fauxto.R;

/**
 * Created by kevingonzales on 3/21/18.
 */

public class FeedActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.feed);

        listItems = new ArrayList<ListItem>();

        for(int i = 0; i<=100;i++){
            ListItem listItem = new ListItem("Heading" + i+1,"Description Text");

            listItems.add(listItem);
        }

        recyclerView = findViewById(R.id.my_recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //make a vetical recycler view

        adapter = new MyAdapter(listItems,this);

        recyclerView.setAdapter(adapter);
    }

}
