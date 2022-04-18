package com.snoozieapp.app.settings;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.snoozieapp.app.R;
import com.snoozieapp.app.bluetooth.BluetoothPage;
import com.snoozieapp.app.track.ReviewPage;
import com.snoozieapp.app.track.Track;

public class Settings extends Fragment {
    private Button bluetoothSettingsBtn, clearDataBtn, reviewBtn;
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

        clearDataBtn = (Button) view.findViewById(R.id.clearDataBtn);
        clearDataBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View view) {
                Track.resetPressure();
                Track.resetLight();
                Track.resetMotion();
                Track.resetTemp();
            }
        });

        reviewBtn = (Button) view.findViewById(R.id.reviewBtn);
        reviewBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openReview();
            }
        });

        // Inflate the layout for this fragment
        return view;
    }

    public void openBluetoothPage() {
        Intent intent = new Intent(getView().getContext(), BluetoothPage.class);
        startActivity(intent);
    }

    public void openReview()
    {
        Intent intent = new Intent(getView().getContext(), ReviewPage.class);
        startActivity(intent);
    }
}