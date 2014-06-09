package com.example.test;


import java.io.IOException;
import java.util.Random;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class Class2 extends Activity {
	
	private Handler handler;
	public static String FILENAME = "Highscore";
	SharedPreferences prefs;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		handler = new Handler();
		
		//Fullscreen
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		setContentView(R.layout.activity_class2);

		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.class2, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_class2,
					container, false);
			return rootView;
		}
	}
	
	//Gör så inte bakåtknappen funkar!
	@Override
	public void onBackPressed() {
	}
	
	//Gör så inte menyknappen funkar!
	@Override
	 public boolean onPrepareOptionsMenu (Menu menu) {
	     return false;
	 }
	int HScore;
	int count = 0;
	int size = 200;
	int timer = 100;
	
	public void StartPlay(View view) {
		size = 200;
		
		//Skapar en ny tråd
		Thread Start = new Thread(new Task());
		Start.start();
	
		
		final Button btn = (Button) findViewById(R.id.Start);
		btn.setBackgroundColor(Color.BLACK);
		btn.setBackgroundResource(R.drawable.hej);
		btn.setText("");
		
		
		count ++;
		if(timer <= 100 && timer >= 45)
		{
			timer -= 5;
		}
		if(timer <= 40 && timer >= 30)
		{
			timer -= 2;
		}
		if(timer <= 28 && timer >= 11)
		{
			timer -= 1;
		}
		
		RelativeLayout linLay = (RelativeLayout) findViewById(R.id.root);
		
		//Ändra bakgrunden
		linLay.setBackgroundResource(R.drawable.background);
		
		//Ändra texten
		Typeface tf = Typeface.createFromAsset(getAssets(),
	            "fonts/OCR A Std.ttf");
		
		TextView counter = (TextView)findViewById(R.id.counter);
		
		counter.setTypeface(tf);
		counter.setText(String.valueOf(count));
		
		//Ge cirklen en random position
		Random r = new Random();
		int newx = r.nextInt(470 - 60) + 60;
		int newy = r.nextInt(1040 - 120) + 120;
		btn.setX(newx);
		btn.setY(newy);
		
		//Hur man få fram upplösningen på skärmen som jag inte använder mig av.
		
		//Display display = getWindowManager().getDefaultDisplay();
		//Point screenSize = new Point();
		//display.getSize(screenSize);
		//int width = screenSize.x;
		//int height = screenSize.y;
	    
    }
	
	public void Save(int count) throws IOException
	{
		//Läser ut de sparade talet.
		prefs = this.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		int HScore = prefs.getInt(FILENAME, 0);
		
		//Om sista rundans poäng är större än rekodet så spara ner de.
		if(count > HScore)
		{
			Editor edit = prefs.edit();
			edit.putInt(FILENAME, count);
			edit.commit();
		}
		
		//Skickar vidare till resultat rutan med poänget från förra rundan.
		hej(count);
	}
	
	@SuppressWarnings("deprecation")
	public void hej(int count) throws IOException
	{
		final Button btn = (Button) findViewById(R.id.Start);
		btn.setX(100000);
		
		AlertDialog alertDialog = new AlertDialog.Builder(
                Class2.this).create();
		
		TextView HScoree = new TextView(getBaseContext());
		
		prefs = this.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
		int HScore = prefs.getInt(FILENAME, 0);
		
		alertDialog.setView(HScoree);
		HScoree.setPadding(170, 50, 1, 50);
		HScoree.setTextSize(20);
		
		HScoree.setText("High Score:"+ HScore +  " \n \n"+ "         Score: " +String.valueOf(count));
		
		
		// Sätt Dialog Title
		alertDialog.setTitle("Game over!");
		
		// Replay knapp
		alertDialog.setButton("Replay", new DialogInterface.OnClickListener() {
		        public void onClick(DialogInterface dialog, int which) {
		        	finish();
		        	startActivity(getIntent());
		        }
		});
		
		// Visa rutan.
				alertDialog.show();
	}
	
	//Skapar funktionen / spelloopen som kör snabbare och snabbare för varje klick och reandera ut cirklen för varje "tick"
	class Task implements Runnable {
		
		        @Override
		        public void run() {
		            for (int i = 200; i > 10; i--) {
		            	if(size >= 10){
		            		if(Thread.activeCount() <= 5)
		            		{
		            			size -= 2;
		                try {
		                    Thread.sleep(timer);
		                } catch (Exception e) {
		                	
		                }
		
		                handler.post(new Runnable() {
		                	
		                    @Override
		                    public void run() {
		                    	
		                    	if(size == 8)
		                    	{
		                    		Thread.currentThread().interrupt();
		                    		try {
										Save(count);
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
		                    	}
		                    	
		                    	final Button btn = (Button) findViewById(R.id.Start);
		
		                    	ViewGroup.LayoutParams params = btn.getLayoutParams();
		                		
		                    	
		                	    params.width = size;
		                	    params.height = size;

		                	    btn.setLayoutParams(params);
		                    }
		
		                });
		            		}
		            		else
		            		{
		            			Thread.currentThread();
								Thread.interrupted();
		            		}
			            }
			        	}
		        }
		        
	}

}
