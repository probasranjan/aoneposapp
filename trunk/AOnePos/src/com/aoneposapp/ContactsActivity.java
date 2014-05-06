package com.aoneposapp;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ParseException;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.TextView;
import android.widget.Toast;

import com.aoneposapp.utils.Parameters;

@SuppressWarnings("deprecation")
public class ContactsActivity extends Activity implements
		android.view.View.OnClickListener {
	Button tab;
	SlidingDrawer slidingDrawer;
	ImageView slideButton, image0, image1, image2, image3, image4, image5,
			image6, image7, image8, logout, logout2;
	int height;
	int wwidth;
	Button b4;
	/*
	 * String address = "                 A-ONEPOS                 \n" +
	 * "          2544 Southwell Rd            \n" +
	 * "                    Dallas                  \n" +
	 * "                   TX-75229                 \n" +
	 * "           Tel : 469-557-1117          \n" +
	 * "           Fax : 1-888-889-1347        \n" +
	 * "           www.aonepos.com             \n" +
	 * "           email:info@aonepos.com        ";
	 */
	WebView web1;
	TextView addressview;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.contact_us);
		Parameters.printerContext=ContactsActivity.this;
		Parameters.ServerSyncTimer();
		slideButton = (ImageView) findViewById(R.id.slideButton);
		slidingDrawer = (SlidingDrawer) findViewById(R.id.SlidingDrawer);
		image0 = (ImageView) findViewById(R.id.image0);
		image1 = (ImageView) findViewById(R.id.image1);
		image2 = (ImageView) findViewById(R.id.image2);
		image3 = (ImageView) findViewById(R.id.image3);
		image4 = (ImageView) findViewById(R.id.image4);
		image5 = (ImageView) findViewById(R.id.image5);
		image6 = (ImageView) findViewById(R.id.image6);
		image7 = (ImageView) findViewById(R.id.image7);
		image8 = (ImageView) findViewById(R.id.image8);
		logout = (ImageView) findViewById(R.id.logout);
		logout2 = (ImageView) findViewById(R.id.logout2);
		image7.setBackgroundResource(R.drawable.c3);
		TextView loginnameempid = (TextView)findViewById(R.id.loginnameval);
		loginnameempid.setText(Parameters.usertypeloginvalue);
		image0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(ContactsActivity.this,
						PosMainActivity.class);
				startActivity(intent1);
				finish();
			}
		});
		image1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Parameters.inventory_permission) {
					Intent intent1 = new Intent(ContactsActivity.this,
							InventoryActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							ContactsActivity.this,
							"Sorry",
							"You are not authenticated to perform this operation.",
							false);
				}
			}
		});
		image2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Parameters.stores_permission){
				Intent intent1 = new Intent(ContactsActivity.this,
						StoresActivity.class);
				startActivity(intent1);
				finish();
			} else {
				showAlertDialog(
						ContactsActivity.this,
						"Sorry",
						"You are not authenticated to perform this operation.",
						false);
			}
			}
		});
		image3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Parameters.customer_permission) {
					Intent intent1 = new Intent(ContactsActivity.this,
							CustomerActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							ContactsActivity.this,
							"Sorry",
							"You are not authenticated to perform this operation.",
							false);
				}
			}
		});
		image4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Parameters.employee_permission) {
				Intent intent1 = new Intent(ContactsActivity.this,
						EmployeeActivity.class);
				startActivity(intent1);
				finish();
			} else {
				showAlertDialog(
						ContactsActivity.this,
						"Sorry",
						"You are not authenticated to perform this operation.",
						false);
			}
			}
		});
		image5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Parameters.reports_permission) {
					Intent intent1 = new Intent(ContactsActivity.this,
							ReportsActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							ContactsActivity.this,
							"Sorry",
							"You are not authenticated to perform this operation.",
							false);
				}
			}
		});
		image6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Parameters.settings_permission) {
					Intent intent1 = new Intent(ContactsActivity.this,
							SettingsActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							ContactsActivity.this,
							"Sorry",
							"You are not authenticated to perform this operation.",
							false);
				}
			}
		});
		image7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		image8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Parameters.profile_permission){
				Intent intent1 = new Intent(ContactsActivity.this,
						ProfileActivity.class);
				startActivity(intent1);
				finish();
				} else {
					showAlertDialog(
							ContactsActivity.this,
							"Sorry",
							"You are not authenticated to perform this operation.",
							false);
				}
			}
		});

		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				Parameters.methodForLogout(ContactsActivity.this);
				finish();
			}
		});
		logout2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Parameters.methodForLogout(ContactsActivity.this);
				finish();
			}
		});
		// slideButton.setTop(15);
		// slideButton.setY(20);
		/*
		 * addressview=(TextView) findViewById(R.id.textaddress);
		 * addressview.setText(address);
		 */
		web1 = (WebView) findViewById(R.id.webView1incontact);
		web1.loadUrl("file:///android_asset/contactus.html");

		/*
		 * Intent intent=new Intent(Intent.ACTION_SEND); String[]
		 * recipients={"info@aonepos.com"}; intent.putExtra(Intent.EXTRA_EMAIL,
		 * recipients); intent.putExtra(Intent.EXTRA_SUBJECT,"");
		 * intent.putExtra(Intent.EXTRA_TEXT,"");
		 * intent.putExtra(Intent.EXTRA_CC,""); intent.setType("text/html");
		 * startActivity(Intent.createChooser(intent, "Send mail"));
		 */

		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				/*
				 * Intent intent = new Intent(getApplicationContext(),
				 * LoginHomeActivity.class);
				 * intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				 * startActivity(intent);
				 */
				if (Parameters.dbbooleanvalue) {
					Parameters.dbbooleanvalue = false;
				} else {
					Parameters.dbbooleanvalue = true;
				}
			}
		});
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		height = displaymetrics.heightPixels;
		wwidth = displaymetrics.widthPixels;
		slideButton.setPadding(0, 0, 0, height - 40);
		slidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				// slideButton.setBackgroundResource(R.drawable.icon);
				slideButton.setImageResource(R.drawable.arrowleft);
			}
		});

		slidingDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {

			@Override
			public void onDrawerClosed() {
				slideButton.setImageResource(R.drawable.arrow);
				// slideButton.setBackgroundResource(R.drawable.icon);
			}
		});

	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
		Toast.makeText(getApplicationContext(), "clicked on" + arg0.getId(),
				500).show();
	}

	@SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message,Boolean status) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		alertDialog.setTitle(title);

		alertDialog.setMessage(message);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		alertDialog.show();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Parameters.printerContext=ContactsActivity.this;
		super.onResume();
	}
}
