package com.example.eznote;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class EditContentFragment extends Fragment {

    private static View view;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.edit_content_fragment,container,false);
        this.view = view;
        return view;
    }

    public static void getContent(){
        EditText titleText =  view.findViewById(R.id.titleText);
        Edit.title = titleText.getText().toString();

        EditText contentText =  view.findViewById(R.id.contentText);
        Edit.content = contentText.getText().toString();

    }

    public static void set(String title, String content){
        EditText titleText =  view.findViewById(R.id.titleText);
        titleText.setText(Edit.title);

        EditText contentText =  view.findViewById(R.id.contentText);
        contentText.setText(Edit.content);
    }
}
