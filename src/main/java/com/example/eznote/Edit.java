package com.example.eznote;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class Edit extends AppCompatActivity {

    static int id;
    public static String title,content;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        i = new Intent(this, EzNote.class);

        getData();

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                EditContentFragment.getContent();

                if (id == -1)
                    createNote();
                else
                    saveNote();

                startActivity(i);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.delete_note) {
            EzNote.dbh.delete(this.id);
            startActivity(i);
            return true;
        }

        if (id == R.id.about) {
            Intent i1 = new Intent(this, About.class);
            startActivity(i1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void getData(){
        Bundle data = getIntent().getExtras();
        if (data == null)
            return;
        id = data.getInt("id");

        if (id == -1)
            return;

        Cursor c = EzNote.dbh.getMemo(id);
        c.moveToFirst();
        while (!c.isAfterLast()){
            title = c.getString(c.getColumnIndex("title"));
            content = c.getString(c.getColumnIndex("content"));
            c.moveToNext();
        }

        EditContentFragment.set(title,content);
    }

    private void createNote(){
        EzNote.dbh.addMemo(title, content);
    }

    private void saveNote(){
        EzNote.dbh.edit(id, title, content);
    }
}