package com.snoozieapp.app.track;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GridLabelRenderer;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;
import com.snoozieapp.app.R;

public class ReviewPage extends AppCompatActivity {
    // UI elements
    private static TextView title;
    private static GraphView motionGraph;
    private static GraphView lightGraph;
    private static GraphView pressureGraph;

    private static LineGraphSeries<DataPoint> motionXLineSeries;
    private static LineGraphSeries<DataPoint> motionYLineSeries;
    private static LineGraphSeries<DataPoint> motionZLineSeries;
    private static LineGraphSeries<DataPoint> lightLineSeries;
    private static LineGraphSeries<DataPoint> pressureLineSeries;

    private static int size = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        title = findViewById(R.id.title);
        motionGraph = findViewById(R.id.motion_graphs);
        lightGraph = findViewById(R.id.light_graphs);
        pressureGraph = findViewById(R.id.pressure_graphs);

        size = Track.motionZData.size();

        initializeMotionGraph();
        initializeLightGraph();
        initializePressureGraph();
    }


    public static void initializeMotionGraph() {
        System.out.print("called");

        // Titles
        motionGraph.setTitle("Motion Data");
        motionGraph.setTitleColor(R.color.black);
        motionGraph.setTitleTextSize(50);

        // Convert array list
        DataPoint [] xArray = new DataPoint[size];
        DataPoint [] yArray = new DataPoint[size];
        DataPoint [] zArray = new DataPoint[size];

        for(int i = 0; i < size; i++)
        {
            xArray[i] = Track.motionXData.get(i);
            yArray[i] = Track.motionYData.get(i);
            zArray[i] = Track.motionZData.get(i);
        }

        // Set color and title of different axes
        motionXLineSeries = new LineGraphSeries<>(xArray);
        motionYLineSeries = new LineGraphSeries<>(yArray);
        motionZLineSeries = new LineGraphSeries<>(zArray);
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

        // Convert array list
        DataPoint [] lightArray = new DataPoint[size];

        for(int i = 0; i < size; i++)
        {
            lightArray[i] = Track.lightData.get(i);
        }

        // Add Data
        lightLineSeries = new LineGraphSeries<>(lightArray);
        lightGraph.addSeries(lightLineSeries);

        // Axis Labels
        GridLabelRenderer gridLabel = lightGraph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Time (s)");
    }

    public static void initializePressureGraph() {
        pressureGraph.setTitle("Pressure Data");
        pressureGraph.setTitleColor(R.color.black);
        pressureGraph.setTitleTextSize(50);

        // Convert array list
        DataPoint [] pressureArray = new DataPoint[size];

        for(int i = 0; i < size; i++)
        {
            pressureArray[i] = Track.pressureData.get(i);
        }

        // Add Data
        pressureLineSeries = new LineGraphSeries<>(pressureArray);
        pressureGraph.addSeries(pressureLineSeries);

        // Axis Labels
        GridLabelRenderer gridLabel = pressureGraph.getGridLabelRenderer();
        gridLabel.setHorizontalAxisTitle("Time (s)");
    }
}
