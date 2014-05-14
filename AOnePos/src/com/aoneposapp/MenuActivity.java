package com.aoneposapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.aoneposapp.adapters.MenuGridViewAdapter;
import com.aoneposapp.utils.Parameters;

public class MenuActivity extends Activity {
	private GridView mGridView;
	
	ImageView logout, help;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.menu_activity);
		Parameters.ServerSyncTimer();
		Parameters.printerContext=MenuActivity.this;
		mGridView = (GridView) findViewById(R.id.menuGrid);
		logout = (ImageView) findViewById(R.id.logoutbutton);
		help = (ImageView) findViewById(R.id.helpbutton);
		MenuGridViewAdapter mAdapter=new MenuGridViewAdapter(this);
		mGridView.setAdapter(mAdapter);
		
		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				
				Parameters.methodForLogout(MenuActivity.this);
				finish();
			
			}
		});
		help.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Toast.makeText(getApplicationContext(), "help", 1000).show();
			}
		});
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		mGridView.setAdapter(new MenuGridViewAdapter(this));		
	}
	
	@SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message,
			Boolean status) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		// Setting alert dialog icon
		// alertDialog.setIcon((status) ? R.drawable.success :
		// "No Internet Connection");

		// Setting OK Button
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}
	@Override
	public void onBackPressed() {

	};
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		if(Parameters.menufinish){
			finish();
		}
		Parameters.printerContext=MenuActivity.this;
		super.onResume();
	}
	
}
