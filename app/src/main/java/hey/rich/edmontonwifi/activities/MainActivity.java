package hey.rich.edmontonwifi.activities;

import android.app.ActionBar;
import android.app.Activity;
import android.app.SearchManager;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.SearchView;

import hey.rich.edmontonwifi.R;
import hey.rich.edmontonwifi.fragments.ClearSearchHistoryDialogFragment;
import hey.rich.edmontonwifi.fragments.NavigationDrawerFragment;

public class MainActivity extends Activity implements
        NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final String TAG = MainActivity.class.getName();

    private SharedPreferences prefs;

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}
     */
    private CharSequence mTitle;

    private int currentFragment = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Set default values if they haven't been set in the past
        PreferenceManager.setDefaultValues(this, R.xml.preferences, false);

        mNavigationDrawerFragment = (NavigationDrawerFragment)
                getFragmentManager().findFragmentById(R.id.navigation_drawer);

        // setup the drawer
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        currentFragment = position;
        try {
            mTitle = getResources().getStringArray(R.array.navigation_drawer_tabs)[position];
        } catch (ArrayIndexOutOfBoundsException e) {
            Log.e(TAG, "Got invalid navigation drawer tab");
            mTitle = getTitle();
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    protected void onResume() {
        super.onStart();

        prefs = getSharedPreferences("hey.rich.EdmontonWifi",
                Context.MODE_PRIVATE);
        // From this beauty: http://stackoverflow.com/a/5878986

        /*if (prefs.getBoolean("firstrun", false)) {
            prefs.edit().putBoolean("firstrun", false).apply();
            new ShowcaseView.Builder(this, true)
                    .setTarget(new ViewTarget(findViewById(R.id.main_activity_listview)))
                    .setContentTitle("Select a Wifi")
                    .setContentText("To get more information about it and to perform actions")
                    .setStyle(R.style.ShowcaseViewTheme)
                    .build();
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inf = getMenuInflater();

        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            switch (currentFragment) {
                case 0: // Wifi menu
                    inf.inflate(R.menu.main, menu);
                    // Get the searchview and set the searchable conf
                    SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
                    SearchView searchView = (SearchView) menu.findItem(R.id.menu_search)
                            .getActionView();
                    // Assumes current activity is the searchable activity
                    searchView.setSearchableInfo(searchManager
                            .getSearchableInfo(getComponentName()));
                    // Don't iconify the widget; expand it by default
                    searchView.setIconifiedByDefault(true);

                    break;
                case 1: // Construction menu
                    break;
            }
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.menu_clear_search_history:
                clearSearchHistory();
                return false;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void clearSearchHistory() {
        ClearSearchHistoryDialogFragment dialog = new ClearSearchHistoryDialogFragment();
        dialog.show(getFragmentManager(), "ClearSearchHistoryDialogFragment");
    }

}