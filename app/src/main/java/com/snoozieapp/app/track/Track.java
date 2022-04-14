package com.snoozieapp.app.track;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.snoozieapp.app.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Track extends Fragment {

    private View view;
    private GraphView audioGraph;
    private GraphView motionGraph;
    private GraphView lightGraph;

    // we can calculate array capacity at runtime once the alarm is set
    // something like:
    //      capacity = length of alarm in hours * ( datapoints read by sensor / hour )

    private DataPoint [] audioData;
    private DataPoint [] motionData;
    private DataPoint [] lightData;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.track_page, container, false);

        // Create Graphs
        createAudioGraph(view);
        createMotionGraph(view);
        createLightGraph(view);
        readData(); // Comment this out if app doesn't work

        // Inflate the layout for this fragment
        return view;
    }

    // // Comment this out if app doesn't work
    private void readData() {
        InputStream is = getResources().openRawResource(R.raw.data);
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(is, Charset.forName("UTF-8"))
        );

        String line  = "";

            try {
                while ( (line = reader.readLine()) != null) {
                    //Log.d("MyActivity", "Line: " + line);
                    String[] tokens = line.split(",");
                    //System.out.println("Light: " + tokens[0] + " Pressure: " + tokens[1] + " X: " + tokens[2] + " Y: " + tokens[3] + " Z: " + tokens[4]);
                }
            } catch (IOException e) {
                Log.wtf("MyActivity,", "Error reading data file on line " + line, e);
                e.printStackTrace();
            }


    }

    private void createAudioGraph(View view) {
        audioGraph = (GraphView) view.findViewById(R.id.audio_graph);
        // 1/10th second for 8h
        audioData = new DataPoint[288000];

        // Generate data
        for (int i = 0; i < audioData.length; i++)
        {
            // 1/50 meaning takes 5 seconds to take a breath
            //Date time = Date
            audioData[i] = new DataPoint(((float) i / 36000),(Math.sin((0.1 * i *  3.14) / 2)) + 1);
        }

        LineGraphSeries<DataPoint> lineSeries = new LineGraphSeries<>(audioData);

        // Add Title
        audioGraph.setTitle("Audio Data");
        audioGraph.setTitleColor(R.color.black);
        audioGraph.setTitleTextSize(50);

        // Add Data
        audioGraph.addSeries(lineSeries);

        // Set Axis Scale
        audioGraph.getViewport().setXAxisBoundsManual(true);
        audioGraph.getViewport().setYAxisBoundsManual(true);
        audioGraph.getViewport().setScrollable(true);
        audioGraph.getViewport().setMaxX(0.01);
        audioGraph.getViewport().setMinX(0);
        audioGraph.getViewport().setMaxY(3);
        audioGraph.getViewport().setMinY(0);
    }

    private void createMotionGraph(View view) {
        motionGraph = (GraphView) view.findViewById(R.id.motion_graph);
        motionData = new DataPoint[288000];

        // Generate data
        for (int i = 0; i < motionData.length; i++)
        {
            motionData[i] = new DataPoint(((float) i / 36000),10 * (Math.sin((0.021 * i *  3.14) / 2)) + 1);
        }

        LineGraphSeries<DataPoint> lineSeries = new LineGraphSeries<>(motionData);

        // Add Title
        motionGraph.setTitle("Motion Data");
        motionGraph.setTitleColor(R.color.black);
        motionGraph.setTitleTextSize(50);

        // Add Data
        motionGraph.addSeries(lineSeries);

        // Set Axis Scale
        motionGraph.getViewport().setXAxisBoundsManual(true);
        motionGraph.getViewport().setYAxisBoundsManual(true);
        //motionGraph.getViewport().setScrollable(true);
        motionGraph.getViewport().setMaxX(0.01);
        motionGraph.getViewport().setMinX(0);
        motionGraph.getViewport().setMaxY(20);
        motionGraph.getViewport().setMinY(0);
    }

    private void createLightGraph(View view) {
        lightGraph = (GraphView) view.findViewById(R.id.light_graph);
        lightData = new DataPoint[288000];

        // Generate data
        for (int i = 0; i < lightData.length; i++)
        {
            lightData[i] = new DataPoint(((float) i / 36000),(0.0000000002)*(Math.pow((float)(i - 72000), 2)) + 3);
        }

        LineGraphSeries<DataPoint> lineSeries = new LineGraphSeries<>(lightData);

        // Add Title
        lightGraph.setTitle("Light Data");
        lightGraph.setTitleColor(R.color.black);
        lightGraph.setTitleTextSize(50);

        // Add Data
        lightGraph.addSeries(lineSeries);

        // Set Axis Scale
        lightGraph.getViewport().setXAxisBoundsManual(true);
        lightGraph.getViewport().setYAxisBoundsManual(true);
        //motionGraph.getViewport().setScrollable(true);
        lightGraph.getViewport().setMaxX(8);
        lightGraph.getViewport().setMinX(0);
        lightGraph.getViewport().setMaxY(20);
        lightGraph.getViewport().setMinY(0);

    }


}