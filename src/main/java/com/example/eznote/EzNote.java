package com.example.eznote;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.FragmentTransaction;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;

import java.util.ArrayList;
import java.util.List;

public class EzNote extends AppCompatActivity {

    public static DBHandler dbh;
    TableLayout tableLayout;
    public static String title="";
    public static int id = 0;
    List<int[]> rows = new ArrayList<>();
    private Intent i;

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbh = new DBHandler(this,null);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        tableLayout = findViewById(R.id.tableLayout);

        //System.out.println(dbh.databaseToString());

        loadNotes();

        i = new Intent(this, Edit.class);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                i.putExtra("id", -1);
                startActivity(i);

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main_screen, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.about) {
            Intent i1 = new Intent(this, About.class);
            startActivity(i1);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void addOnlyNoteIcon(TableRow tr, String title){
        ConstraintLayout cl = new ConstraintLayout(this);
        cl.setId(View.generateViewId());
        tr.addView(cl);
        CardViewFragment cv = new CardViewFragment();

        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(cl.getId(), cv);
        ft.commit();

        rows.get(rows.size()-1)[0] ++;
    }

    private void addNoteIcon(String title, int id){
        TableRow row = null;
        if (rows.size()==0){
            System.out.println("fd");
            row = addRow();
        }else if (rows.get(rows.size()-1)[0]>2){
            row = addRow();
        }else {
            row = findViewById(rows.get(rows.size()-1)[1]);
        }

        this.title = title;
        this.id = id;
        addOnlyNoteIcon(row,title);
    }

    private TableRow addRow(){
        TableRow row;
        row = new TableRow(this);
        row.setId(View.generateViewId());
        rows.add(new int[]{0,row.getId()});
        tableLayout.addView(row);
        return row;
    }

    private void loadNotes(){
        Cursor c = dbh.getAllNotes();
        c.moveToFirst();
        while (!c.isAfterLast()){
            id =  Integer.parseInt(c.getString(c.getColumnIndex("_id")));
            //id = Integer.parseInt(c.getString(c.getColumnIndex("id")));
            title = c.getString(c.getColumnIndex("title"));
            addNoteIcon(title, id);
            c.moveToNext();
        }
    }

}