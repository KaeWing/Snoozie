package com.snoozieapp.app.track;

import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.snoozieapp.app.R;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

public class Track extends Fragment {

    private View view;
    private static GraphView motionGraph;
    private static GraphView lightGraph;
    private static GraphView pressureGraph;

    private static TextView tempBox, humBox, stageTextBox;

    public static ArrayList<DataPoint> motionXData = new ArrayList<>(300000);
    public static ArrayList<DataPoint> motionYData = new ArrayList<>(300000);
    public static ArrayList<DataPoint> motionZData = new ArrayList<>(300000);
    public static ArrayList<DataPoint> lightData = new ArrayList<>(300000);
    public static ArrayList<DataPoint> pressureData = new ArrayList<>(300000);

    private static LineGraphSeries<DataPoint> motionXLineSeries = new LineGraphSeries<>();
    private static LineGraphSeries<DataPoint> motionYLineSeries = new LineGraphSeries<>();
    private static LineGraphSeries<DataPoint> motionZLineSeries = new LineGraphSeries<>();
    private static LineGraphSeries<DataPoint> lightLineSeries = new LineGraphSeries<>();
    private static LineGraphSeries<DataPoint> pressureLineSeries = new LineGraphSeries<>();

    public static Instant start = null;
    public static Instant cycleTime = null;

    // For ReadData()
    public static ArrayList<String> lightRead = new ArrayList<>();
    public static ArrayList<String> pressureRead = new ArrayList<>();
    public static ArrayList<String> xRead = new ArrayList<>();
    public static ArrayList<String> yRead = new ArrayList<>();
    public static ArrayList<String> zRead = new ArrayList<>();

    private static Integer pressureReading = null;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.track_page, container, false);
        view.invalidate();

        lightGraph = (GraphView) view.findViewById(R.id.light_graph);
        pressureGraph = (GraphView) view.findViewById(R.id.pressure_graph);
        motionGraph = (GraphView) view.findViewById(R.id.motion_graph);
        tempBox = (TextView) view.findViewById(R.id.tempBox);
        humBox = (TextView) view.findViewById(R.id.humidBox);
        stageTextBox = (TextView) view.findViewById(R.id.stageBox);

        initializeMotionGraph();
        initializeLightGraph();
        initializePressureGraph();

        // Example Graphs
        // examplePressureGraph();
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

        try
        {
            // Split Data
            String [] dataValues = data.split(",");

            if (dataValues.length == 3)
            {
                float x = ((float) ChronoUnit.MILLIS.between(start, time))/((float) 1000);
                float y1 = Float.valueOf(dataValues[0]).floatValue();
                float y2 = Float.valueOf(dataValues[1]).floatValue();
                float y3 = Float.valueOf(dataValues[2]).floatValue();

                DataPoint d1 = new DataPoint(x, y1);
                DataPoint d2 = new DataPoint(x, y2);
                DataPoint d3 = new DataPoint(x, y3);

                motionXLineSeries.appendData(d1, false, 1000000);
                motionYLineSeries.appendData(d2, false, 1000000);
                motionZLineSeries.appendData(d3, false, 1000000);

                // Saving data (not tested)
                motionXData.add(d1);
                motionYData.add(d2);
                motionZData.add(d3);

                // Scale the X axis
                motionGraph.getViewport().setMaxX(x);
                if (x-1.5 >= 0)
                {
                    motionGraph.getViewport().setMinX(x - 1.5);
                }
                else
                {
                    motionGraph.getViewport().setMinX(0);
                }
            }
        }
        catch(NullPointerException e)
        {
            System.out.println("uh oh");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void graphLight(Instant time, String data) {
        if (start == null)
        {
            start = Instant.now();
        }

        try
        {
            float x = ((float) ChronoUnit.MILLIS.between(start, time))/((float) 1000);
            float y = Float.valueOf(data).floatValue();

            DataPoint d = new DataPoint(x, y);
            lightLineSeries.appendData(d, false, 1000000);

            // Saving data (not tested)
            lightData.add(d);

            // Scale the X axis
            lightGraph.getViewport().setMaxX(x);
            if (x-1.5 >= 0)
            {
                lightGraph.getViewport().setMinX(x-1.5);
            }
            else
            {
                lightGraph.getViewport().setMinX(0);
            }
        }
        catch(NullPointerException e)
        {
            System.out.println("uh oh");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void graphPressure(Instant time, String data) {
        if (start == null)
        {
            start = Instant.now();
        }

        try
        {
            float x = ((float) ChronoUnit.MILLIS.between(start, time))/((float) 1000);
            float y = Float.valueOf(data).floatValue();
            pressureReading = (int) y;

            DataPoint d = new DataPoint(x, y);
            pressureLineSeries.appendData(d, false, 1000000);

            // Saving data (not tested)
            pressureData.add(d);

            // Scale the X axis
            pressureGraph.getViewport().setMaxX(x);
            if (x-1.5 >= 0)
            {
                pressureGraph.getViewport().setMinX(x-1.5);
            }
            else
            {
                pressureGraph.getViewport().setMinX(0);
            }

//            if ( stageTextBox != null ) {
//
//                if ( y <= 100) {
//                    stageTextBox.setText("Falling Asleep");
//                    cycleTime = Instant.now();
//                }
//
////            // < 10
////            else if (ChronoUnit.MILLIS.between(cycleTime, time) <= 600000)
////            {
////                stageTextBox.setText("Falling Asleep");
////            }
//
//                // 10
//                else if (ChronoUnit.MILLIS.between(cycleTime, time) >= 600000)
//                {
//                    stageTextBox.setText("Light Sleep");
//                }
//
//                // 35
//                else if ( ((ChronoUnit.MILLIS.between(cycleTime, time)) / (float) 1000 ) >= 2100)
//                {
//                    stageTextBox.setText("Deep Sleep");
//                }
//
//                // 65
//                else if ( ((ChronoUnit.MILLIS.between(cycleTime, time)) / (float) 1000 ) >= 3900)
//                {
//                    stageTextBox.setText("REM Sleep");
//                }
//
//                // Reset after a certain amount of time of REM (100 mins)
//                else if ( ((ChronoUnit.MILLIS.between(cycleTime, time)) / (float) 1000 ) >= 6000)
//                {
//                    stageTextBox.setText("Falling Asleep");
//                    cycleTime = Instant.now();
//                }
//            }


        }
        catch(NullPointerException e)
        {
            System.out.println("uh oh");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void updateSleepCycle(Instant time) {

        if (cycleTime == null)
        {
            cycleTime = Instant.now();
        }

        try {
            if ( stageTextBox != null ) {

                // If pressure sensor's value is less than 100, reset cycletime
                if ( pressureReading <= 100 ) {
                    stageTextBox.setText("Falling Asleep");
                    cycleTime = Instant.now();
                }

//            // < 10
//            else if (ChronoUnit.MILLIS.between(cycleTime, time) <= 600000)
//            {
//                stageTextBox.setText("Falling Asleep");
//            }

                // Reset after a certain amount of time of REM (100 mins)
                else if ( ChronoUnit.SECONDS.between(cycleTime, time) >= 120)
                {
                    stageTextBox.setText("Falling Asleep");
                    cycleTime = Instant.now();
//                    System.out.println("Stage: 1");
                }

                // 65
                else if ( ChronoUnit.SECONDS.between(cycleTime, time) >= 90)
                {
                    stageTextBox.setText("REM Sleep");
//                    System.out.println("Stage: 4");
                }

                // 35
                else if ( ChronoUnit.SECONDS.between(cycleTime, time) >= 60)
                {
                    stageTextBox.setText("Deep Sleep");
//                    System.out.println("Stage: 3");
                }

                // 10
                else if (ChronoUnit.SECONDS.between(cycleTime, time) >= 30)
                {
                    stageTextBox.setText("Light Sleep");
//                    System.out.println("Stage: 2");
                }

            }
        } catch (Exception e) {

        }
    }

    public static void initializeMotionGraph() {
        System.out.print("called");

        // Titles
        motionGraph.setTitle("Motion Data");
        motionGraph.setTitleColor(R.color.black);
        motionGraph.setTitleTextSize(50);

        // Set color and title of different axes
        motionXLineSeries.setColor(Color.RED);
        motionXLineSeries.setTitle("X");
        motionYLineSeries.setColor(Color.BLUE);
        motionYLineSeries.setTitle("Y");
        motionZLineSeries.setColor(Color.GREEN);
        motionZLineSeries.setTitle("Z");

        // Add Data
        motionGraph.addSeries(motionXLineSeries);
        motionGraph.addSeries(motionYLineSeries);
        motionGraph.addSeries(motionZLineSeries);

        // Legend
        motionGraph.getLegendRenderer().setVisible(true);

        // Axis Labels
        GridLabelRenderer gridLabel = motionGraph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Time (s)");
    }

    public static void initializeLightGraph() {

        lightGraph.setTitle("Light Data");
        lightGraph.setTitleColor(R.color.black);
        lightGraph.setTitleTextSize(50);

        // Add Data
        lightGraph.addSeries(lightLineSeries);

        // Axis Labels
        GridLabelRenderer gridLabel = lightGraph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Time (s)");
    }

    public static void initializePressureGraph() {
        pressureGraph.setTitle("Pressure Data");
        pressureGraph.setTitleColor(R.color.black);
        pressureGraph.setTitleTextSize(50);

        // Add Data
        pressureGraph.addSeries(pressureLineSeries);

        // Axis Labels
        GridLabelRenderer gridLabel = pressureGraph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Time (s)");
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void resetPressure()
    {
        start = Instant.now();
        pressureData = new ArrayList<>(600000);
        pressureRead = new ArrayList<>();
        pressureLineSeries = new LineGraphSeries<>();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void resetMotion()
    {
        start = Instant.now();
        motionXData = new ArrayList<>(600000);
        motionYData = new ArrayList<>(600000);
        motionZData = new ArrayList<>(600000);
        xRead = new ArrayList<>();
        yRead = new ArrayList<>();
        zRead = new ArrayList<>();
        motionXLineSeries = new LineGraphSeries<>();
        motionYLineSeries = new LineGraphSeries<>();
        motionZLineSeries = new LineGraphSeries<>();

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    public static void resetLight()
    {
        start = Instant.now();
        lightData = new ArrayList<>(600000);
        lightRead = new ArrayList<>();
        lightLineSeries = new LineGraphSeries<>();
    }

    public static void setTemp(String data)
    {
        if (tempBox != null && humBox != null)
        {
            // Split multiple tokens
            String [] dataValues = data.split(",");

            if (dataValues.length == 2)
            {
                float F = (Float.valueOf(dataValues[0]).floatValue() * (float) 1.8) + (float) 32;
                tempBox.setText("Temp: " + F + " F");
                humBox.setText("Humid: " + dataValues[1]);
            }
        }
    }

    public static void resetTemp()
    {
        if (tempBox != null) {
            tempBox.setText("");
        }
    }










    private void exampleMotionGraph() {

        // Generate data
        for (int i = 0; i < 300000; i++)
        {
            DataPoint temp = new DataPoint(((float) i / 36000),10 * (Math.sin((0.021 * i *  3.14) / 2)) + 1);
            motionXData.add(temp);
            motionXLineSeries.appendData(temp, false, 1000000);


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

    private void examplePressureGraph() {

        // Generate data
        for (int i = 0; i < 300000; i++)
        {
            // 1/50 meaning takes 5 seconds to take a breath
            //Date time = Date
            DataPoint temp = new DataPoint(((float) i / 36000),(Math.sin((0.1 * i *  3.14) / 2)) + 1);
            pressureData.add(temp);
            pressureLineSeries.appendData(temp, false, 1000000);
        }

        // Set Axis Scale
        pressureGraph.getViewport().setXAxisBoundsManual(true);
        pressureGraph.getViewport().setYAxisBoundsManual(true);
        pressureGraph.getViewport().setScrollable(true);           // idk what this does
        pressureGraph.getViewport().setMaxX(0.01);
        pressureGraph.getViewport().setMinX(0);
        pressureGraph.getViewport().setMaxY(3);
        pressureGraph.getViewport().setMinY(0);
    }
}