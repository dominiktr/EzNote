package com.example.eznote;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class CardViewFragment extends Fragment {
    String title = EzNote.title;
    public int id = EzNote.id;
    private Intent i;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.card_view,container,false);
        TextView textView =  view.findViewById(R.id.titleTextView);
        textView.setText(title);
        i = new Intent(getContext(), Edit.class);

        view.findViewById(R.id.cardview).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor c = EzNote.dbh.getMemo(id);
                c.moveToFirst();
                if (!c.isAfterLast()){
                    i.putExtra("id", Integer.parseInt(c.getString(c.getColumnIndex("_id"))));
                    startActivity(i);
                }

            }
        });
        return view;
    }


}
