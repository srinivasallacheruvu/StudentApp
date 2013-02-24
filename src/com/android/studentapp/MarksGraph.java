package com.android.studentapp;

import java.util.ArrayList;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.Menu;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MarksGraph extends Activity {
	
	private GraphicalView mChart;
	int i=0;
	SQLiteDatabase db;
    private XYSeries visitsSeries;
    private XYSeries hindi;
    private XYSeries english;
    private XYSeries maths;
    private XYSeries science;
    private XYSeries social;
    private XYMultipleSeriesDataset dataset;
 
    private XYSeriesRenderer visitsRenderer;
    private XYSeriesRenderer hindiRender;
    private XYSeriesRenderer englishRender;
    private XYSeriesRenderer mathsRender;
    private XYSeriesRenderer scienceRender;
    private XYSeriesRenderer socialRender;
    
    private XYMultipleSeriesRenderer multiRenderer;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.marks_graph);
		
		// Setting up chart
        setupChart();
        // Start plotting chart
        new ChartTask().execute();
        
	}
	
	private void setupChart(){
   	 
        // Creating an  XYSeries for Visits
        visitsSeries = new XYSeries("Telugu");
        hindi = new XYSeries("Hindi");
        english = new XYSeries("English");
        maths = new XYSeries("Maths");
        science = new XYSeries("Science");
        social = new XYSeries("Social");
        
        
        
        
        // Creating a dataset to hold each series
        dataset = new XYMultipleSeriesDataset();
        // Adding Visits Series to the dataset
        
        dataset.addSeries(visitsSeries);
        dataset.addSeries(hindi);
        dataset.addSeries(english);
        dataset.addSeries(maths);
        dataset.addSeries(science);
        dataset.addSeries(social);
        
        // Creating XYSeriesRenderer to customize visitsSeries
        
        
        
        visitsRenderer = new XYSeriesRenderer();
        visitsRenderer.setColor(Color.CYAN);
        //visitsRenderer.setPointStyle(PointStyle.CIRCLE);
        visitsRenderer.setFillPoints(true);
        visitsRenderer.setLineWidth(5);
        visitsRenderer.setDisplayChartValues(true);
        
        hindiRender = new XYSeriesRenderer();
        hindiRender.setColor(Color.YELLOW);
        //hindiRender.setPointStyle(PointStyle.CIRCLE);
        
        
        englishRender = new XYSeriesRenderer();
        englishRender.setColor(Color.BLUE);
        //englishRender.setPointStyle(PointStyle.CIRCLE);
        
        
        mathsRender = new XYSeriesRenderer();
        mathsRender.setColor(Color.CYAN);
        //mathsRender.setPointStyle(PointStyle.CIRCLE);
        
        
        scienceRender = new XYSeriesRenderer();
        scienceRender.setColor(Color.DKGRAY);
        //scienceRender.setPointStyle(PointStyle.CIRCLE);
        scienceRender.setFillPoints(true);
        scienceRender.setLineWidth(5);
        scienceRender.setDisplayChartValues(true);
        
        socialRender = new XYSeriesRenderer();
        socialRender.setColor(Color.MAGENTA);
        //socialRender.setPointStyle(PointStyle.CIRCLE);
        //socialRender.setFillPoints(true);
        //socialRender.setLineWidth(5);
        //socialRender.setDisplayChartValues(true);
 
        // Creating a XYMultipleSeriesRenderer to customize the whole chart
        
        
        
        
        multiRenderer = new XYMultipleSeriesRenderer();
 
        multiRenderer.setChartTitle("Chandrababu Designed Chart");
        multiRenderer.setXTitle("Subjects");
        multiRenderer.setYTitle("Percentage");
        multiRenderer.setZoomButtonsVisible(true);
 
        multiRenderer.setXAxisMin(0);
        multiRenderer.setXAxisMax(6);
 
        multiRenderer.setYAxisMin(0);
        multiRenderer.setYAxisMax(100);
 
        multiRenderer.setBarSpacing(2);
 
        // Adding visitsRenderer to multipleRenderer
        // Note: The order of adding dataseries to dataset and renderers to multipleRenderer
        // should be same
        multiRenderer.addSeriesRenderer(visitsRenderer);
        multiRenderer.addSeriesRenderer(hindiRender);
        multiRenderer.addSeriesRenderer(englishRender);
        multiRenderer.addSeriesRenderer(mathsRender);
        multiRenderer.addSeriesRenderer(scienceRender);
        multiRenderer.addSeriesRenderer(socialRender);
 
        // Getting a reference to LinearLayout of the MainActivity Layout
        LinearLayout chartContainer = (LinearLayout) findViewById(R.id.LinearLayout01);
        
        
 
        
        mChart = (GraphicalView) ChartFactory.getBarChartView(getBaseContext(), dataset, multiRenderer, Type.DEFAULT);
 
        // Adding the Line Chart to the LinearLayout
        chartContainer.addView(mChart);
    }
 
    private class ChartTask extends AsyncTask<Void, String, Void>{
 
        // Generates dummy data in a non-ui thread
        @Override
        protected Void doInBackground(Void... params) {
            int i = 0;
            int j = 1;
            String id = getIntent().getExtras().getString("id");
            /*TextView marks = (TextView)findViewById(R.id.marks);*/
            //StringBuffer sb = null;
            try {
            	db = openOrCreateDatabase("StudentAppl.db", SQLiteDatabase.CREATE_IF_NECESSARY, null);
            } catch(Exception e) { }
            //Cursor c = db.rawQuery("select * from MarksTable", null);
			//sb = new StringBuffer();
            Cursor c=db.rawQuery("select * from marks_details where _id=?",new String[] {id});
            ArrayList<Integer> list = new ArrayList<Integer>();
 			c.moveToFirst();
			while(!c.isAfterLast()) {
				
				int telugu = Integer.parseInt(c.getString(1));
				int hindi = Integer.parseInt(c.getString(2));
				int english = Integer.parseInt(c.getString(3));
				int maths = Integer.parseInt(c.getString(4));
				int science = Integer.parseInt(c.getString(5));
				int social = Integer.parseInt(c.getString(6));
				String exam_type = c.getString(8);
				
				//int tp = Integer.parseInt(telugu);
				
				//list.add(tp);
				double tres, hres, eres, mres, scres, sores;
				int tperc, hperc, eperc, mperc, scperc, soperc;
				tperc = hperc = eperc = mperc = scperc = soperc = 0;
				
				tres = 100*telugu;
				hres = 100*hindi;
				eres = 100*english;
				mres = 100*maths;
				scres = 100*science;
				sores = 100*social;
				
				if(exam_type.equals("Unit Test-1") || exam_type.equals("Unit Test-2") || exam_type.equals("Unit Test-3")) {
					
					tperc =(int) tres/25;
					hperc = (int)hres/25;
					eperc = (int)eres/25;
					mperc = (int)mres/25;
					scperc = (int)scres/25;
					soperc = (int)sores/25;
					
				}
				if(exam_type.equals("Quarterly") || exam_type.equals("Half Yearly") || exam_type.equals("Yearly")) {
					
					tperc =(int) tres/100;
					hperc = (int)hres/100;
					eperc = (int)eres/100;
					mperc = (int)mres/100;
					scperc = (int)scres/100;
					soperc = (int)sores/100;
				}
				
				
				
				list.add(tperc);
				list.add(hperc);
				list.add(eperc);
				list.add(mperc);
				list.add(scperc);
				list.add(soperc);
				
				c.moveToNext();
			}
             
            
            try{
            	 int val[]={1,2,6,3,4,90};
                do{
                    String [] values = new String[2];
                    Random r = new Random();
                   // int visits = r.nextInt(5);
                   
                	
                    values[0] = Integer.toString(j);
                    //int lv = list.get(i);
                    values[1] = Integer.toString(list.get(i));
 
                    publishProgress(values);
                    Thread.sleep(1000);
                    i++;
                    j++;
                }while(i<6);
                    }catch(Exception e){ }
                return null;
            }
 
            // Plotting generated data in the graph
            @Override
            protected void onProgressUpdate(String... values) {
            	visitsSeries.add(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
                mChart.repaint();
            }
        }

	

}
