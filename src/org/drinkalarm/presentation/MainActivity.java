package org.drinkalarm.presentation;

import android.app.Activity;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.*;
import org.drinkalarm.application.JeuControlleur;
import org.drinkalarm.metier.Action;

public class MainActivity extends Activity {

    /**
     * Groupe de level
     */
    private RadioGroup level;

    /**
     * Affichage du level
     */
    private TextView level_text;

    /**
     * Layout - Liste des joueurs
     */
    private DrawerLayout mDrawerLayout;

    /**
     *
     */
    private ActionBarDrawerToggle mDrawerToggle;

    /**
     * Liste des joueurs
     */
    private ListView mDrawerList;

    /**
     * Controller
     */
    private JeuControlleur controller;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Action action;

        // Initialisation
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        // Controller init
        controller = new JeuControlleur("medium");
        controller.addJoueur("David");
        controller.addJoueur("Toto");

        // Gestion level
        level = (RadioGroup) findViewById(R.id.level);
        level_text = (TextView) findViewById(R.id.level_text);
        level.check(getResources().getIdentifier(controller.getMode().getLibelle(), "id", getPackageName()));
        level_text.setText(getResources().getIdentifier(controller.getMode().getLibelle(), "string", getPackageName()));
        level.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
        {
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                // checkedId is the RadioButton selected
                switch (checkedId){
                    case R.id.soft:
                        controller.setMode("soft");
                        break;
                    case R.id.medium:
                        controller.setMode("medium");
                        break;
                    case R.id.hard:
                        controller.setMode("hard");
                        break;
                    case R.id.legend:
                        controller.setMode("legend");
                        break;
                }
                level_text.setText(getResources().getIdentifier(controller.getMode().getLibelle(), "string", getPackageName()));
            }
        });

        // Gestion liste des joueurs
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerList = (ListView) findViewById(R.id.left_drawer);
        // Adapter pour la liste des joueurs
        mDrawerList.setAdapter(new ArrayAdapter<String>(this,
                R.layout.drawer_list_item, controller.getJoueursAsString()));
        Button btnLoadMore = new Button(this);
        btnLoadMore.setText("Load More");
        mDrawerList.addFooterView(btnLoadMore);

        // enable ActionBar app icon to behave as action to toggle nav drawer
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);

        // ActionBarDrawerToggle ties together the the proper interactions
        // between the sliding drawer and the action bar app icon
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                mDrawerLayout,         /* DrawerLayout object */
                R.drawable.ic_drawer,  /* nav drawer image to replace 'Up' caret */
                R.string.drawer_open,  /* "open drawer" description for accessibility */
                R.string.drawer_close  /* "close drawer" description for accessibility */
        ) {
            public void onDrawerClosed(View view) {
                getActionBar().setTitle(R.string.app_name);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }

            public void onDrawerOpened(View drawerView) {
                getActionBar().setTitle(R.string.joueurs);
                invalidateOptionsMenu(); // creates call to onPrepareOptionsMenu()
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);

    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        // Sync the toggle state after onRestoreInstanceState has occurred.
        mDrawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Pass the event to ActionBarDrawerToggle, if it returns
        // true, then it has handled the app icon touch event
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        // Handle your other action bar items...

        return super.onOptionsItemSelected(item);
    }

}

