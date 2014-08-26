package com.aoneposapp;

import java.util.Locale;

import net.authorize.android.model.CommonCode;
import net.authorize.android.model.Parser;
import net.authorize.android.model.SystemSession;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;

public class Splash_Activity extends Activity {

	private static String TAG = Splash_Activity.class.getName();
	private static long SLEEP_TIME = 3; // Sleep for some time ...
	Locale myLocale;
	SystemSession session;
	CommonCode commonCode;
	String name;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		// load xml file...
		setContentView(R.layout.activity_splash);
		session = new SystemSession(Splash_Activity.this);
		commonCode = new CommonCode(Splash_Activity.this);
		
		if(!session.getData("message").equalsIgnoreCase("done")){
		if (commonCode.checkInternet())
		{
			try
			{
				name = CommonCode.getEmail(this);
				System.out.println("Email id name " + name);
				new Send_mail().execute();

			} catch (Exception e)
			{

			}
		} else
		{
			// Start timer and launch main activity ...
			IntentLauncher launcher = new IntentLauncher();
			launcher.start();
		}
		}else {
			// Start timer and launch main activity ...
						IntentLauncher launcher = new IntentLauncher();
						launcher.start();
		}
	}
	
public class Send_mail extends AsyncTask<Void, Void, Void>{
		
//		private ProgressDialog dialog;
		String status = "" ;
		String sid;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			
//			dialog = new ProgressDialog(Splash_Activity.this);
//			dialog.setMessage("Loading....");
//			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//			dialog.setCancelable(false);
//			dialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			Parser parser = new Parser();
			
			try {
				JSONObject json = parser.getJSONFromUrl("http://mydatalive.com/wp-content/uploads/api/send_email.php?email="+name);
				
				if (json == null)
					return null;
				
				if (json != null) {
				
				 status = json.getString("status");
				 if(status.contains("Success")){
					 
				 }
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			super.onPostExecute(result);
//			dialog.dismiss();
//			dialog = null;
			
			if(status.equalsIgnoreCase("Success")){
				session.addData("message", "done");
				Intent in = new Intent(Splash_Activity.this, LoginHomeActivity.class);
				Splash_Activity.this.startActivity(in);
				Splash_Activity.this.finish();
			}
		}
	}


	private class IntentLauncher extends Thread {
		@Override
		/** * Sleep for some time and than start new activity. */
		public void run() {
			try { // Sleeping ...
				Thread.sleep(SLEEP_TIME * 1000);
			} catch (Exception e) {
				Log.e(TAG, e.getMessage());
			}
			// Start main activity ...
//			String demo_database = session.getData("Data_value");
//			if(demo_database.equalsIgnoreCase("")){
//				Intent in = new Intent(Splash_Activity.this, Select_Demo_database_Activity.class);
//				Splash_Activity.this.startActivity(in);
//				Splash_Activity.this.finish();
//			}else {
				Intent in = new Intent(Splash_Activity.this, LoginHomeActivity.class);
				Splash_Activity.this.startActivity(in);
				Splash_Activity.this.finish();
//			}
		}
	}
}