package com.snoozieapp.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Audio extends Fragment {
    private Button musicBtn;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.audio_page, container, false);

        musicBtn = (Button) view.findViewById(R.id.musicBtn);
        musicBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openMusicPage();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void openMusicPage() {
        Intent intent = new Intent(getView().getContext(), MusicPage.class);
        startActivity(intent);
    }
}