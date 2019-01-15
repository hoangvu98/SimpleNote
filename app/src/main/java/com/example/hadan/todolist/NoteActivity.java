package com.example.hadan.todolist;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Date;

public class NoteActivity extends AppCompatActivity {

    EditText titleEdText;
    EditText contentEdText;
    Date dateCreate;
    SQLiteDatabase db;
    MyDatabaseAdapter dbHelper;

    String tmpTitle;
    String tmpContent;

    boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_note);

        flag = false;
        dbHelper = new MyDatabaseAdapter(this);
        db = dbHelper.getWritableDatabase();

        titleEdText = findViewById(R.id.TileEdText);
        contentEdText = findViewById(R.id.ContentEdText);

        tmpTitle = titleEdText.getText().toString();
        tmpContent = contentEdText.getText().toString();


        FloatingActionButton fabSave = findViewById(R.id.fabSave);
        fabSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!titleEdText.getText().toString().equals(tmpTitle) ||
                        !contentEdText.getText().toString().equals(tmpContent)) {
                    if (flag == false) {
                        flag = true;
                        tmpTitle = titleEdText.getText().toString();
                        tmpContent = contentEdText.getText().toString();

                        dateCreate = new Date();
                        InsertDatabase(titleEdText.getText().toString(), contentEdText.getText().toString(),
                                dateCreate);
                    } else {
                        Note note = dbHelper.SelectLast();

                        tmpTitle = titleEdText.getText().toString();
                        tmpContent = contentEdText.getText().toString();

                        dbHelper.Update(note.getId(),
                                titleEdText.getText().toString(),
                                contentEdText.getText().toString(),
                                new Date());
                    }
                }
            }
        });

    }

    @Override
    public void onBackPressed(){
        if (!titleEdText.getText().toString().equals(tmpTitle) ||
                !contentEdText.getText().toString().equals(tmpContent)){
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);

            //set tile for dialog
            alertDialogBuilder.setTitle("Do you want to save changes ?");
            alertDialogBuilder.setCancelable(true);

            //set button yes
            alertDialogBuilder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    if (flag == false) {
                        flag = true;
                        dateCreate = new Date();
                        InsertDatabase(titleEdText.getText().toString(), contentEdText.getText().toString(),
                                dateCreate);
                    }
                    else {
                        Note note = dbHelper.SelectLast();

                        tmpTitle = titleEdText.getText().toString();
                        tmpContent = contentEdText.getText().toString();

                        dbHelper.Update(note.getId(),
                                titleEdText.getText().toString(),
                                contentEdText.getText().toString(),
                                new Date());
                    }
                    finish();

                }
            });

            alertDialogBuilder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            //set button no
            alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    finish();
                }
            });

            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }
        else finish();

    }

    public void InsertDatabase(String tile, String content, Date dateCreate)
    {
        ContentValues values=new ContentValues();
        values.put(dbHelper.Tile, tile);
        values.put(dbHelper.Content, content);
        values.put(dbHelper.Date, dateCreate.toString());
        String mgs="";
        if(db.insert(dbHelper.TABLE_NAME, null, values) == -1)
        {
            mgs = "FAIL";
        }
        else mgs = "SUCCESSFUL";
        Toast.makeText(this, mgs, Toast.LENGTH_LONG).show();
    }

}
