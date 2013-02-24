package com.android.studentapp;

import java.util.ArrayList;
import java.util.Random;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.BarChart.Type;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.os.AsyncTask;
import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.view.Menu;
import android.widget.LinearLayout;

public class ExamResultGraph extends Activity {

	private GraphicalView mChart;
	int i=0;
	SQLiteDatabase db;
    private XYSeries unit1;
    private XYSeries unit2;
    private XYSeries unit3;
    private XYSeries quarterly;
    private XYSeries halfyearly;
    private XYSeries yearly;
    private XYMultipleSeriesDataset dataset;
 
    private XYSeriesRenderer unit1Render;
    private XYSeriesRenderer unit2Render;
    private XYSeriesRenderer unit3Render;
    private XYSeriesRenderer quarterlyRender;
    private XYSeriesRenderer halfRender;
    private XYSeriesRenderer yearlyRender;
    
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
        unit1 = new XYSeries("UnitTest-1");
        unit2 = new XYSeries("UnitTest-2");
        unit3 = new XYSeries("UnitTest-3");
        quarterly = new XYSeries("Quarterly");
        halfyearly = new XYSeries("HalfYearly");
        yearly = new XYSeries("Yearly");
        
        
        
        
        // Creating a dataset to hold each series
        dataset = new XYMultipleSeriesDataset();
        // Adding Visits Series to the dataset
        
        dataset.addSeries(unit1);
        dataset.addSeries(unit2);
        dataset.addSeries(unit3);
        dataset.addSeries(quarterly);
        dataset.addSeries(halfyearly);
        dataset.addSeries(yearly);
        
        // Creating XYSeriesRenderer to customize visitsSeries
        
        
        
        unit1Render = new XYSeriesRenderer();
        unit1Render.setColor(Color.CYAN);
        //visitsRenderer.setPointStyle(PointStyle.CIRCLE);
        unit1Render.setFillPoints(true);
        unit1Render.setLineWidth(5);
        unit1Render.setDisplayChartValues(true);
        
        unit2Render = new XYSeriesRenderer();
        unit2Render.setColor(Color.YELLOW);
        //hindiRender.setPointStyle(PointStyle.CIRCLE);
        
        
        unit3Render = new XYSeriesRenderer();
        unit3Render.setColor(Color.BLUE);
        //englishRender.setPointStyle(PointStyle.CIRCLE);
        
        
        quarterlyRender = new XYSeriesRenderer();
        quarterlyRender.setColor(Color.CYAN);
        //mathsRender.setPointStyle(PointStyle.CIRCLE);
        
        
        halfRender = new XYSeriesRenderer();
        halfRender.setColor(Color.DKGRAY);
        //scienceRender.setPointStyle(PointStyle.CIRCLE);
        
        yearlyRender = new XYSeriesRenderer();
        yearlyRender.setColor(Color.MAGENTA);
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
        multiRenderer.addSeriesRenderer(unit1Render);
        multiRenderer.addSeriesRenderer(unit2Render);
        multiRenderer.addSeriesRenderer(unit3Render);
        multiRenderer.addSeriesRenderer(quarterlyRender);
        multiRenderer.addSeriesRenderer(halfRender);
        multiRenderer.addSeriesRenderer(yearlyRender);
 
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
            int id_student = 0;
 			c.moveToFirst();
			while(!c.isAfterLast()) {
				id_student = Integer.parseInt(c.getString(9));
				c.moveToNext();
			}
			Cursor cur=db.rawQuery("select * from marks_details", null);
			cur.moveToFirst();
			while(!cur.isAfterLast()) {
				int student = Integer.parseInt(cur.getString(9));
				String exam_type = cur.getString(8);
				int total = Integer.parseInt(cur.getString(7));
				
				if(id_student == student) {
					if(exam_type.equals("Unit Test-1") || exam_type.equals("Unit Test-2") || exam_type.equals("Unit Test-3")) {
						double total_per = 100*total;
						int unit_per =(int) total_per/150;
						list.add(unit_per);
					}
					if(exam_type.equals("Quarterly") || exam_type.equals("Half Yearly") || exam_type.equals("Yearly")) {
						double total_per = 100*total;
						int yearly_per =(int) total_per/600;
						list.add(yearly_per);
					}
				}
				
				
				
				cur.moveToNext();
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
            	unit1.add(Integer.parseInt(values[0]), Integer.parseInt(values[1]));
                mChart.repaint();
            }
        }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.exam_result_graph, menu);
		return true;
	}

}
