package com.example.danielgalarza.chatdummy;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.firebase.ui.FirebaseListAdapter;

import java.util.Random;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Firebase mFirebase;
    ListView mMessagesView;
    private FirebaseListAdapter mAdapter;
    private EditText mMessageText;
    Button mShootButton;
    private Random r = new Random();
    private String name = "User" + r.nextInt(9999); //userID during a chat.


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        //List View for chat messages.
        mMessagesView = (ListView) findViewById(R.id.message_list_view);
        Firebase.setAndroidContext(this);  //Giving the Firebase an Android context.

        //Our Firebase Reference.
        mFirebase = new Firebase("https://chatdummy.firebaseio.com/chat");

        //Setting a Firebase List Adapter to facilitate the name and message from each user.
        mAdapter = new FirebaseListAdapter<Chat>(this, Chat.class, android.R.layout.two_line_list_item, mFirebase) {

            @Override
            protected void populateView(View view, Chat chatMessage) {

                //Populates List View.
                ((TextView)view.findViewById(android.R.id.text1)).setText(chatMessage.getName());
                ((TextView)view.findViewById(android.R.id.text2)).setText(chatMessage.getMessage());

            }
        };

        //giving the List View the Firebase List Adapter.
        mMessagesView.setAdapter(mAdapter);

        mMessageText = (EditText) this.findViewById(R.id.text_edit);  //Input for chat messages.
        mShootButton = (Button) this.findViewById(R.id.shoot_button); //Button to send message.

        mShootButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Firebase's code
                //String msg = mMessageText.getText().toString();
                //Map<String,Object> values = new HashMap<>();
                //values.put("name", "Android User");
                //values.put("message", msg);
                // mFirebase.push().setValue(values);
                //mMessageText.setText("");

                //My code
                String msg = mMessageText.getText().toString();
                Chat c = new Chat(name, msg);
                mFirebase.push().setValue(c);
                mMessageText.setText("");

            }
        });

    }

    //TEST
    public void createMessages() {

        //TEST MESSAGES
        Chat msg = new Chat("user1", "message 1");
        mFirebase.push().setValue(msg);

        Chat msg2 = new Chat("user2", "message 2");
        mFirebase.push().setValue(msg2);

        Chat msg3 = new Chat("user3", "message 3");
        mFirebase.push().setValue(msg3);

        Chat msg4 = new Chat("user4", "message 4");
        mFirebase.push().setValue(msg4);

        Chat msg5 = new Chat("user5", "message 5");
        mFirebase.push().setValue(msg5);

        Chat msg6 = new Chat("user6", "message 6");
        mFirebase.push().setValue(msg6);
    }

    @Override
    public void onStart() {

        super.onStart();

        //FOR DEBUGGING PURPOSES
        mFirebase.limitToLast(2).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot msgSnapshot : snapshot.getChildren()) {

                    Log.d("onDataChange: ", "I'm in the foreach loop");
                    Chat msg = msgSnapshot.getValue(Chat.class);
                    Log.d("Chat", msg.getName() + ": " + msg.getMessage());
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
            }
        });


        //Creating hard coded messages.
        //createMessages();

    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        //Cleans up the message board when app is destroyed.
        mAdapter.cleanup();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_todolist) {
            //Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_maps) {

        } else if (id == R.id.nav_chat) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
