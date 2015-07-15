package com.example.navigationdrawerexample;

//Thank you to https://www.codeofaninja.com/2014/02/android-navigation-drawer-example.html
//for the source code of the basic layout of the navigation drawer!

import android.content.Intent;
import android.os.Bundle;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class MainActivity extends Activity {

	// declare properties
    private String[] mNavigationDrawerItemTitles;
    private DrawerLayout mDrawerLayout;
    private ListView mDrawerList;
    public static String MESSAGE = "play";

    private ActionBarDrawerToggle mDrawerToggle;

    // nav drawer title
    private CharSequence mDrawerTitle;
 
    // used to store app title
    private CharSequence mTitle;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		// for proper titles
		mTitle = mDrawerTitle = getTitle();
		
		// initialize properties
		mNavigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        
        // list the drawer items
        ObjectDrawerItem[] drawerItem = new ObjectDrawerItem[4];
        
        drawerItem[0] = new ObjectDrawerItem(R.drawable.ic_action_home, "Home");
        drawerItem[1] = new ObjectDrawerItem(R.drawable.ic_action_play, "Play");
        drawerItem[2] = new ObjectDrawerItem(R.drawable.ic_action_learn, "Learn");
        drawerItem[3] = new ObjectDrawerItem(R.drawable.ic_action_connect, "Connect");
        
        // Pass the folderData to our ListView adapter
        DrawerItemCustomAdapter adapter = new DrawerItemCustomAdapter(this, R.layout.listview_item_row, drawerItem);
        
        // Set the adapter for the list view
        mDrawerList.setAdapter(adapter);
        
        // set the item click listener
        mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
        
        
        

        
        // for app icon control for nav drawer
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer icon to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description */
                R.string.drawer_close  /* "close drawer" description */
                ) {
        	
            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                getActionBar().setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(mDrawerTitle);
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawerLayout.setDrawerListener(mDrawerToggle);
        
        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        
        
        
        if (savedInstanceState == null) {
            // on first time display view for first nav item
        	selectItem(0);
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
		
        // The action bar home/up action should open or close the drawer.
        // ActionBarDrawerToggle will take care of this.
       if (mDrawerToggle.onOptionsItemSelected(item)) {
           return true;
       }
       
       return super.onOptionsItemSelected(item);
	}
	
	// to change up caret
    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }
	
	
	// navigation drawer click listener
	private class DrawerItemClickListener implements ListView.OnItemClickListener {
		
	    @Override
	    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
	        selectItem(position);
	    }
	    
	}

    private void selectItem(int position) {
    	
        // update the main content by replacing fragments
    	
        Fragment fragment = null;
        
        switch (position) {
        case 0:
            fragment = new HomeFragment();
            break;
        case 1:
            fragment = new PlayFragment();
            break;
        case 2:
            fragment = new LearnFragment();
            break;
        case 3:
            fragment = new ConnectFragment();
            break;
 
        default:
            break;
        }
        
        if (fragment != null) {
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
 
            // update selected item and title, then close the drawer
            mDrawerList.setItemChecked(position, true);
            mDrawerList.setSelection(position);
            setTitle(mNavigationDrawerItemTitles[position]);
            mDrawerLayout.closeDrawer(mDrawerList);
            
        } else {
            // error in creating fragment
            Log.e("MainActivity", "Error in creating fragment");
        }
    }
    
    @Override
    public void setTitle(CharSequence title) {
        mTitle = title;
        getActionBar().setTitle(mTitle);
    }

    public void pressHomeButton(View view) {
        Intent intent = new Intent(this, HomeActivity.class);

        switch (view.getId()) {
            case R.id.play:
                MESSAGE = "play";
                break;
            case R.id.learn:
                MESSAGE = "learn";
                break;
            case R.id.connect:
                MESSAGE = "connect";
                break;

        }

        startActivity(intent);


    }

    //takes you to the videoActivity page via HomeFrag and opens YOUTUBE for playing video
    public void pressVideoButton(View view) {
        Intent intent = new Intent(this, VideoActivity.class);
        intent.putExtra("url", "https://youtu.be/zndy7aCQyUo");
        switch (view.getId()) {
            case R.id.play:
                MESSAGE = "play";
                break;
            case R.id.learn:
                MESSAGE = "learn";
                break;
            case R.id.connect:
                MESSAGE = "connect";
                break;
            case R.id.buttonVideo:
                MESSAGE = "video";
                break;

        }

        startActivity(intent);


    }

    public void pressMentorButton(View view) {
        Intent mentoring = new Intent(this, MentorActivity.class);
        mentoring.putExtra("url","https://docs.google.com/forms/d/1IjfXRGAFEoskViuCaDeX8AbPVYti_JMhhblE7k2UcT4/viewform?c=0&w=1");
        switch (view.getId()) {
            case R.id.locator:
                MESSAGE = "locator";
                break;
            case R.id.mentoring:
                MESSAGE = "mentoring";
                break;
            case R.id.calendar:
                MESSAGE = "calendar";
                break;
        }
        startActivity(mentoring);
    }

    public void pressCalendarButton(View view) {
        Intent calendar = new Intent(this, CalendarActivity.class);
        calendar.putExtra("url","https://www.google.com/calendar/embed?src=vdudc68j102b36b47n8mirg33c%40group.calendar.google.com&ctz=America/Los_Angeles");
        switch (view.getId()) {
            case R.id.locator:
                MESSAGE = "locator";
                break;
            case R.id.mentoring:
                MESSAGE = "mentoring";
                break;
            case R.id.calendar:
                MESSAGE = "calendar";
                break;
        }
        startActivity(calendar);


    }

    public void pressLocatorButton(View view) {
        Intent locator = new Intent(this, LocatorActivity.class);
        switch (view.getId()) {
            case R.id.locator:
                MESSAGE = "locator";
                break;
            case R.id.mentoring:
                MESSAGE = "mentoring";
                break;
            case R.id.calendar:
                MESSAGE = "calendar";
                break;
        }
        startActivity(locator);
    }

    public void pressLearnButton(View view) {
        Intent intent = new Intent(this, LearnActivity.class);
        switch (view.getId()) {
            case R.id.javaButton:
                MESSAGE = "java";
                break;
            case R.id.pythonButton:
                MESSAGE = "python";
                break;
        }
        startActivity(intent);
    }

}
