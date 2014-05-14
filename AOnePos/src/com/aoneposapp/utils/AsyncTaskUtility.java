package com.aoneposapp.utils;

import java.util.HashMap;

import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.widget.Toast;

import com.aoneposapp.R;
import com.aoneposapp.utils.Parameters;

public class AsyncTaskUtility 
{
	private Context context;
	private AsyncTaskInterface asyncTaskInterface;
	private HashMap<String, String> map;
	private String strURL;
	boolean isProgressDialog = true;

	public AsyncTaskUtility(Context _context, HashMap<String, String> hashMap, String str_URL) 
	{
		this.context = _context;
		this.map=hashMap;
		this.strURL=str_URL;
	}
	
	public AsyncTaskUtility(Context _context, HashMap<String, String> hashMap, String str_URL, boolean isProgressDialog) 
	{
		this.context = _context;
		this.map=hashMap;
		this.strURL=str_URL;
		this.isProgressDialog=isProgressDialog;
	}
	public void getJSONResponse(AsyncTaskInterface _asyncTaskInterface)
	{
		asyncTaskInterface = _asyncTaskInterface;
		if(Parameters.isNetworkAvailable(context))
		{
			new AsyncTaskAction().execute();
		}
	}

	public class AsyncTaskAction extends AsyncTask<String, Void, Void>
	{
		private ProgressDialog progressBar;
		private String status = "";
		private JSONObject json;

		public AsyncTaskAction() 
		{
		}

		protected void onPreExecute() 
		{
			super.onPreExecute();
			if(isProgressDialog)
			{
				this.progressBar = new ProgressDialog(new ContextThemeWrapper(context,R.style.AppBaseTheme));
				this.progressBar.setCancelable(false);
				this.progressBar.setMessage("Loading please wait");
				this.progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
				this.progressBar.show();
			}
		}
		protected Void doInBackground(String... params) 
		{
			try 
			{
				JsonParser parser = new JsonParser();
				json = parser.getJsonObjectFromWebservice(strURL, map);
			}
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			return null;
		}
		protected void onPostExecute(Void paramVoid)
		{
			super.onPostExecute(paramVoid);
			if(isProgressDialog)
			{
				this.progressBar.dismiss();
			}
			Log.d("sss","JSON: "+json);
			try 
			{
				if (json != null)
				{
					if(json.has("msg"))
					{
						String strMsg = json.getString("msg");
						if(strMsg.toString().trim().length()!=0)
							Toast.makeText(context, strMsg, Toast.LENGTH_SHORT).show();
					}
					if(json.has("status"))
						status = json.getString("status");
				} 
				else
				{
					Toast.makeText(context, "Server Issue Please try Again Later", Toast.LENGTH_SHORT).show();
//					myClass.showToast(context.getResources().getString(R.string.Server_Issue_Please_try_Again_Later));
				}
			}
			catch (Exception e) 
			{
				e.printStackTrace();
				Toast.makeText(context, "Error Please try later.", Toast.LENGTH_SHORT).show();
			}
//			if (this.status.equalsIgnoreCase("Success"))
//				asyncTaskInterface.getJSONObjectFromAsynTask(json);
//			else if(!isProgressDialog)
//				asyncTaskInterface.getJSONObjectFromAsynTask(json);
			if(json!=null)
				asyncTaskInterface.getJSONObjectFromAsynTask(json);
		}
	}
}
