// https://github.com/evanemran/Android_Tutorials/tree/master/Music_Player

package com.snoozieapp.app;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.karumi.dexter.listener.single.PermissionListener;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class MusicPage extends AppCompatActivity {
    ListView listView;
    String[] items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_page);

        listView = findViewById(R.id.listViewSong);
        runtimePermission();
    }

    public void runtimePermission()
    {
        Dexter.withContext(this).withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.RECORD_AUDIO)
                .withListener(new MultiplePermissionsListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport multiplePermissionsReport) {
                        displaySongs();
                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> list, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();

                    }
                }).check();

    }

//    public ArrayList<File> findSong (File file) {
//        ArrayList<File> arrayList = new ArrayList<>();
//        File[] files = file.listFiles();
//
//        if (files != null) {
//            for (File singlefile: files) {
//
//                if (singlefile.isDirectory() && !singlefile.isHidden()) {
//                    arrayList.addAll(findSong(singlefile));
//                }
//
//                else {
//                    if (singlefile.getName().endsWith(".mp3") || singlefile.getName().endsWith(".wav")) {
//                        arrayList.add(singlefile);
//                    }
//                }
//            }
//        }
//
//        return arrayList;
//    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public ArrayList<File> findSong(File file) {
        ArrayList<File> arraylist = new ArrayList<>();

        File f = file;

        File[] files = null;

        try {
            files = f.listFiles();

            if (files.length > 0) {
                for (File singleFile : files) {

                    if (singleFile.isDirectory() && !singleFile.isHidden()) {
                        arraylist.addAll(findSong(singleFile));
                    } else {
                        if (singleFile.getName().endsWith(".mp3") || singleFile.getName().endsWith(".wav")) {

                            arraylist.add(singleFile);
                        }
                    }
                }
            } else {
                Path path = Paths.get("/storage/emulated/0/Music/"); // Might need to change path if using with actual phone as this points to emulated folder
                File filePath = path.toFile();
                arraylist.add(filePath);
            }
        } catch (NullPointerException e) {
            //toastMsg("Error : " + e + " " + e.getStackTrace()[0].getLineNumber());
        }

        return arraylist;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    void displaySongs() {
        final ArrayList<File> mySongs = findSong(Environment.getExternalStorageDirectory());
        items = new String[mySongs.size()]; // Store all songs in here

        for (int i = 0; i < mySongs.size(); i++) {
            items[i] = mySongs.get(i).getName().toString().replace(".mp3", "").replace(".wav", "");
        }

//        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);
//        listView.setAdapter(myAdapter);

        customAdapter customAdapter = new customAdapter();
        listView.setAdapter(customAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String songName = (String) listView.getItemAtPosition(i);
                startActivity(new Intent(getApplicationContext(), PlayerActivity.class)
                        .putExtra("songs", mySongs)
                        .putExtra("songname", songName)
                        .putExtra("pos", i));
            }
        });
    }

    class customAdapter extends BaseAdapter {

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public Object getItem(int i) {
            return null;
        }

        @Override
        public long getItemId(int i) {
            return 0;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {

            View myView = getLayoutInflater().inflate(R.layout.list_item, null);
            TextView textSong = myView.findViewById(R.id.txtsongname);
            textSong.setSelected(true);
            textSong.setText(items[i]);

            return myView;
        }
    }
}