package com.snoozieapp.app.track;

import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
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
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Track extends Fragment {

    private View view;
    private static GraphView motionGraph;
    private static GraphView lightGraph;
    private static GraphView audioGraph;

    private static ArrayList<DataPoint> motionData = new ArrayList<>(600000);
    private static ArrayList<DataPoint> lightData = new ArrayList<>(600000);
    private static ArrayList<DataPoint> audioData = new ArrayList<>(600000);

    private static LineGraphSeries<DataPoint> motionLineSeries = new LineGraphSeries<>();
    private static LineGraphSeries<DataPoint> lightLineSeries = new LineGraphSeries<>();
    private static LineGraphSeries<DataPoint> audioLineSeries = new LineGraphSeries<>();

    public static Instant start = null;

    // For ReadData()
    public static ArrayList<String> lightRead = new ArrayList<>();
    public static ArrayList<String> pressureRead = new ArrayList<>();
    public static ArrayList<String> xRead = new ArrayList<>();
    public static ArrayList<String> yRead = new ArrayList<>();
    public static ArrayList<String> zRead = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.track_page, container, false);
        view.invalidate();


        initializeMotionGraph(view);
        initializeLightGraph(view);
        initializeAudioGraph(view);

        // Example Graphs
        // exampleAudioGraph();
        // exampleMotionGraph();
        // exampleLightGraph();


        // Inflate the layout for this fragment
        return view;
    }
    
    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void graphMotion(Instant time, String data) {
        if (start == null)
        {
            start = Instant.now();
        }

        float x = ((float) ChronoUnit.MILLIS.between(start, time))/((float) 1000);
        float y = Float.valueOf(data).floatValue();

        DataPoint d = new DataPoint(x, y);
        motionLineSeries.appendData(d, false, 1000000);

        // Scale the X axis
        motionGraph.getViewport().setMaxX(x);
        if (x-1 >= 0)
        {
            motionGraph.getViewport().setMinX(x-1);
        }
        else
        {
            motionGraph.getViewport().setMinX(0);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void graphLight(Instant time, String data) {
        if (start == null)
        {
            start = Instant.now();
        }

        float x = ((float) ChronoUnit.MILLIS.between(start, time))/((float) 1000);
        float y = Float.valueOf(data).floatValue();

        DataPoint d = new DataPoint(x, y);
        lightLineSeries.appendData(d, false, 1000000);

        // Scale the X axis
        lightGraph.getViewport().setMaxX(x);
        if (x-1 >= 0)
        {
            lightGraph.getViewport().setMinX(x-1);
        }
        else
        {
            lightGraph.getViewport().setMinX(0);
        }
    }

    private static void initializeMotionGraph(View view) {
        motionGraph = (GraphView) view.findViewById(R.id.motion_graph);

        motionGraph.setTitle("Motion Data");
        motionGraph.setTitleColor(R.color.black);
        motionGraph.setTitleTextSize(50);

        // Add Data
        motionGraph.addSeries(motionLineSeries);
    }

    private static void initializeLightGraph(View view) {
        lightGraph = (GraphView) view.findViewById(R.id.light_graph);

        lightGraph.setTitle("Light Data");
        lightGraph.setTitleColor(R.color.black);
        lightGraph.setTitleTextSize(50);

        // Add Data
        lightGraph.addSeries(lightLineSeries);
    }

    private static void initializeAudioGraph(View view) {
        audioGraph = (GraphView) view.findViewById(R.id.audio_graph);

        audioGraph.setTitle("Audio Data");
        audioGraph.setTitleColor(R.color.black);
        audioGraph.setTitleTextSize(50);

        // Add Data
        audioGraph.addSeries(audioLineSeries);
    }












    private void exampleMotionGraph() {

        // Generate data
        for (int i = 0; i < 300000; i++)
        {
            DataPoint temp = new DataPoint(((float) i / 36000),10 * (Math.sin((0.021 * i *  3.14) / 2)) + 1);
            motionData.add(temp);
            motionLineSeries.appendData(temp, false, 1000000);


        }

        // Set Axis Scale
        motionGraph.getViewport().setXAxisBoundsManual(true);
        motionGraph.getViewport().setYAxisBoundsManual(true);
        motionGraph.getViewport().setMaxX(0.01);
        motionGraph.getViewport().setMinX(0);
        motionGraph.getViewport().setMaxY(20);
        motionGraph.getViewport().setMinY(0);
    }

    private void exampleLightGraph() {

        // Generate data
        for (int i = 0; i < 300000; i++)
        {
            DataPoint temp = new DataPoint(((float) i / 36000),(0.0000000002)*(Math.pow((float)(i - 72000), 2)) + 3);
            lightData.add(temp);
            lightLineSeries.appendData(temp, false, 1000000);
        }

        // Set Axis Scale
        lightGraph.getViewport().setXAxisBoundsManual(true);
        lightGraph.getViewport().setYAxisBoundsManual(true);
        lightGraph.getViewport().setMaxX(8);
        lightGraph.getViewport().setMinX(0);
        lightGraph.getViewport().setMaxY(20);
        lightGraph.getViewport().setMinY(0);

    }

    private void exampleAudioGraph() {

        // Generate data
        for (int i = 0; i < 300000; i++)
        {
            // 1/50 meaning takes 5 seconds to take a breath
            //Date time = Date
            DataPoint temp = new DataPoint(((float) i / 36000),(Math.sin((0.1 * i *  3.14) / 2)) + 1);
            audioData.add(temp);
            audioLineSeries.appendData(temp, false, 1000000);
        }

        // Set Axis Scale
        audioGraph.getViewport().setXAxisBoundsManual(true);
        audioGraph.getViewport().setYAxisBoundsManual(true);
        audioGraph.getViewport().setScrollable(true);           // idk what this does
        audioGraph.getViewport().setMaxX(0.01);
        audioGraph.getViewport().setMinX(0);
        audioGraph.getViewport().setMaxY(3);
        audioGraph.getViewport().setMinY(0);
    }
}