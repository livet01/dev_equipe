package org.drinkalarm.presentation;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import org.drinkalarm.application.JeuControlleur;
import org.drinkalarm.metier.Action;

public class MainActivity extends Activity {

    private EditText editText;
    private ListView drawerListView;
    private JeuControlleur controller;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        Action action;

        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);

        controller = new JeuControlleur("soft");
        controller.addJoueur("David");
        controller.addJoueur("Toto");


        // get ListView defined in activity_main.xml
        drawerListView = (ListView) findViewById(R.id.left_drawer);

        // Set the adapter for the list view
        drawerListView.setAdapter(new ArrayAdapter<String>(this,R.layout.drawer_listview_item,controller.getJoueursAsString()));

        getActionBar().setDisplayHomeAsUpEnabled(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

}

