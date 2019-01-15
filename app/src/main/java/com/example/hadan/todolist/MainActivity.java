package com.example.hadan.todolist;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SearchView;

import java.util.List;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    MyRecyclerViewAdapter myReViewAdapter;
    RecyclerView recyclerView;
    List<Note> data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /* Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();*/
               Intent intent = new Intent(MainActivity.this, NoteActivity.class);
               startActivity(intent);
            }
        });


        recyclerView = findViewById(R.id.listNote);
        //load data from database and init myReviewAdapter
        data = new ArrayList<>();
        data = loadDataFromDatabase();

        myReViewAdapter = new MyRecyclerViewAdapter(this, data);
        myReViewAdapter.setOnItemClickedListenter(new MyRecyclerViewAdapter.OnItemClickedListenter() {
            @Override
            public void onItemClick(Intent intent) {
                startActivity(intent);
            }
        });
        //set layout for note in recyclerview
        LinearLayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(myReViewAdapter);
    }

    @Override
    public void onResume(){
        data = loadDataFromDatabase();
        myReViewAdapter.setData(data);
        recyclerView.setAdapter(myReViewAdapter);
        super.onResume();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem menuItem = menu.findItem(R.id.action_find);
        SearchView searchView = (SearchView)menuItem.getActionView();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {

                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {

                List<Note>searchResult = new ArrayList<>();

                for(Note note: data){
                    if (note.getTitle().contains(newText)){
                        searchResult.add(note);
                    }
                }

                myReViewAdapter.setData(searchResult);
                recyclerView.setAdapter(myReViewAdapter);
                return false;
            }
        });
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
        else if (id == R.id.action_find){
            onSearchRequested();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public List<Note> loadDataFromDatabase(){
        SQLiteDatabase database;
        MyDatabaseAdapter myDBAdapter;
        myDBAdapter = new MyDatabaseAdapter(this);
        database = myDBAdapter.getReadableDatabase();
        return myDBAdapter.SelectAll(database);
    }
}
