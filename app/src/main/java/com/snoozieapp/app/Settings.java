package com.snoozieapp.app;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class Settings extends Fragment {
    private Button bluetoothSettingsBtn;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.settings_page, container, false);

        bluetoothSettingsBtn = (Button) view.findViewById(R.id.bluetoothBtn);
        bluetoothSettingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openBluetoothPage();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }
    public void openBluetoothPage() {
        Intent intent = new Intent(getView().getContext(), BluetoothPage.class);
        startActivity(intent);
    }
}