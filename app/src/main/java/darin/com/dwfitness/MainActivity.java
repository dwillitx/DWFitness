package darin.com.dwfitness;

import android.app.Activity;
import android.app.Fragment;
import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;

import android.support.annotation.Nullable;
import android.app.FragmentTransaction;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;

import android.support.v7.widget.Toolbar;
import android.view.Menu;

import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends ActionBarActivity {

    private Toolbar actionBarToolBar;
    private ShareActionProvider shareActionProvider;

    private String[] drawerListViewItems;
    private ListView drawerListView;
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle actionBarDrawerToggle;


    private int currentPosition = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // action bar toolbar
        actionBarToolBar = (Toolbar) findViewById(R.id.my_action_toolbar);

        //set first
        setSupportActionBar(actionBarToolBar);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //set it to navigtion
//        actionBarToolBar.setNavigationIcon(R.drawable.ic_action_back_arrow);

//        actionBarToolBar.setNavigationContentDescription(getResources().getString(R.string.navigation_icon_description));

        //set the logo
        actionBarToolBar.setLogo(R.mipmap.ic_launcher);
        actionBarToolBar.setLogoDescription(getResources().getString(R.string.logo_description));


        //This will get the intent than check the action

        Intent searchIntent = getIntent();
        if (Intent.ACTION_SEARCH.equals(searchIntent.getAction())) {

            //Get the text typed in for the Search
            String query = searchIntent.getStringExtra(SearchManager.QUERY);
            Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();
        }

        drawerListViewItems = getResources().getStringArray(R.array.titles);

        drawerListView = (ListView) findViewById(R.id.left_drawer);

        drawerListView.setAdapter(new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_activated_1, drawerListViewItems));

        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);

        drawerListView.setOnItemClickListener(new DrawerItemClickListener());

        //if the mainAcitvy is newly created, use the selectItem method to
        //display TopFragment
//        if (savedInstanceState == null) {
//            selectItem(0);
//        }


        actionBarDrawerToggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                R.string.drawer_open,
                R.string.drawer_close
        ) {

            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                invalidateOptionsMenu();

            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                invalidateOptionsMenu();
            }
        };


        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setHomeButtonEnabled(true);
        }


//        mainDisplay = (ImageView) findViewById(R.id.my_main_images);
//        ImageView dumbBellImage = (ImageView) findViewById(R.id.imageDumbbells);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu this is how items are added to the action bar
        getMenuInflater().inflate(R.menu.menu, menu);
//        MenuItem menuItem = menu.findItem(R.id.action_share);
//        shareActionProvider = (ShareActionProvider) MenuItemCompat.getActionProvider(menuItem);
//        setIntent("This is example text");

        //Create the searchview

        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();

        //Get the search manager from the system
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);

        //bind the searchView object with the searchable component
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        return super.onCreateOptionsMenu(menu);

    }

    private void setIntent(String s) {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setType("Text/Plain");
        intent.putExtra(Intent.EXTRA_TEXT, s);
        shareActionProvider.setShareIntent(intent);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        //Handle the actionBarToggle to be clicked
        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        //Handle actions from menu
        switch (item.getItemId()) {
            //Run code when setting is clicked
            case R.id.fitness_settings:

                Intent intent = new Intent(this, DetailActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_hide_toolbar:

                Toast.makeText(MainActivity.this, "Hide Option clicked", Toast.LENGTH_SHORT).show();

                ActionBar ab = getSupportActionBar();
                ab.hide();

                break;
            case R.id.menu_search:
                return true;


            default:
                return super.onOptionsItemSelected(item);

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        actionBarDrawerToggle.onConfigurationChanged(newConfig);
    }

    private void selectItem(int position) {

        Fragment fragment;

        switch (position) {
            case 1:
                fragment = new CardioFragment();
                break;
            case 2:
                fragment = new UpperBodyFragment();
                break;
            case 3:
                fragment = new LowerBodyFragment();
                break;
            default:
                fragment = new TopFragment();
        }

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        //Insert fragment into layout
        ft.replace(R.id.content_frame, fragment);

        //use back button
        ft.addToBackStack(null);

        //Use fragment transaction to replace the fragment that's displayed
        ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);

        ft.commit();

        setActionBarTitle(position);

        //Close the drawer
        drawerLayout.closeDrawer(drawerListView);


    }

    public void setActionBarTitle(int position) {
        String title;

        if (position == 0) {
            title = getResources().getString(R.string.app_name);
        } else {
            title = drawerListViewItems[position];
        }

//        getActionBar().setIcon(R.mipmap.ic_launcher);
//        getActionBar().setTitle(title);


    }

    //Called whenever invalidateOptionsMenu
    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        // if the drawer is open hid action items related to the context view
        boolean drawerOpen = drawerLayout.isDrawerOpen(drawerListView);

        //Set the visiablity of setting and search to false if the drawer is open
        //set to true if it is not
        menu.findItem(R.id.action_settings).setVisible(!drawerOpen);
        menu.findItem(R.id.menu_search).setVisible(!drawerOpen);
        return super.onPrepareOptionsMenu(menu);
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Toast.makeText(MainActivity.this,
                    ((TextView) view).getText(),
                    Toast.LENGTH_LONG).show();

            selectItem(position);

            drawerLayout.closeDrawer(drawerListView);
        }
    }


}

