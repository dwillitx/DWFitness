package darin.com.dwfitness;

import android.app.SearchManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;

public class DetailActivity extends ActionBarActivity {

    private Toolbar actionBarToolBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_layout);

        // action bar toolbar
        actionBarToolBar = (Toolbar) findViewById(R.id.my_action_toolbar);

        //set first
        setSupportActionBar(actionBarToolBar);

        //set it to navigtion
        actionBarToolBar.setNavigationIcon(R.drawable.ic_action_back_arrow);

        actionBarToolBar.setNavigationContentDescription(getResources().getString(R.string.navigation_icon_description));

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);



        //set the logo
        actionBarToolBar.setLogo(R.mipmap.ic_launcher);
        actionBarToolBar.setLogoDescription(getResources().getString(R.string.logo_description));
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        //Inflate the menu this is how items are added to the action bar
        getMenuInflater().inflate(R.menu.menu, menu);

        //Create the searchview
        SearchView searchView = (SearchView) menu.findItem(R.id.menu_search).getActionView();

        //Get the search manager from the system
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);

        //bind the searchView object with the searchable component
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));


        return super.onCreateOptionsMenu(menu);

    }
}
