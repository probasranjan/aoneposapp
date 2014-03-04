package com.aoneposapp;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

import net.authorize.android.SDKActivity;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.ParseException;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.StrictMode;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.MediaColumns;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.webkit.URLUtil;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.aoneposapp.adapters.DetailsShowingArrayAdapter;
import com.aoneposapp.adapters.ListEditAdapter;
import com.aoneposapp.adapters.ListEditAdapterForPayment;
import com.aoneposapp.adapters.PrinterEditAdapter;
import com.aoneposapp.mercury.MagTekDemo;
import com.aoneposapp.utils.DatabaseForDemo;
import com.aoneposapp.utils.GetJSONListener;
import com.aoneposapp.utils.JSONclient;
import com.aoneposapp.utils.JsonPostMethod;
import com.aoneposapp.utils.MyApplication;
import com.aoneposapp.utils.Parameters;
import com.epson.eposprint.BatteryStatusChangeEventListener;
import com.epson.eposprint.Builder;
import com.epson.eposprint.Print;
import com.epson.eposprint.StatusChangeEventListener;
import com.ipaulpro.afilechooser.utils.FileUtils;
import com.mercurypay.ws.sdk.MPSWebRequest;

@SuppressWarnings("deprecation")
public class SettingsActivity extends Activity implements
		 StatusChangeEventListener,
		BatteryStatusChangeEventListener, ListEditAdapter.OnWidgetItemClicked,
		ListEditAdapterForPayment.OnWidgetItemClicked,
		PrinterEditAdapter.OnWidgetItemClicked {
	Button tab;
	 String randomNumbermercury=Parameters.randomValue();
	 String cuurentTimemercury=Parameters.currentTime();
	SlidingDrawer slidingDrawer;
	ImageView slideButton, image0, image1, image2, image3, image4, image5,
			image6, image7, image8, logout, logout2;
	int height;
	int wwidth;
	long count = 0;
	GetJSONListener l;
	String selectpaymentname = "", selectpaymentval = "";
	String forurlSave="";
	AutoCompleteTextView prtip;
	Spinner spinnername, printertype_spinner;
	ImageView imageView;
	Button b4, tslookup, printermainbtn, tax, paymentype, database, company,
			serverurl, card;
	static Print printer = null;
	LinearLayout list, list1, detailslayout, ll;
	ListView taxeslist, paymentlist, printerlist;
	CheckBox checkbtn, checkbtnn;
	private static final int REQUEST_CHOOSER = 1234;
	private static final int RESULT_LOAD_IMAGE = 1;
	private static final int RESTORE_CODE = 1111;
	TextView tv, tvv;
	 Uri uri1;
	private ProgressDialog pDialog;
	ImageView my_image;
	ArrayList<String> departmentListData = new ArrayList<String>();
	ArrayList<String> totalinventoryuploadingrecords = new ArrayList<String>();
	ArrayList<String> totalinventoryuploadingrecordsoptional = new ArrayList<String>();
	ArrayList<String> id_data = new ArrayList<String>();
	ArrayList<String> desc_data = new ArrayList<String>();
	ArrayList<String> total_id_data = new ArrayList<String>();
	ArrayList<String> total_desc_data = new ArrayList<String>();
	int pagenum = 1;
	Boolean update_bool=false;
	String dataval = "";
	public static int pagecount = 3;
	public static int testforid = 1;
	int stLoop = 0;
	int endLoop = 0;
	int totalcount = 0;
	int j;
	public static final int progress_bar_type = 0;
	final ArrayList<String> deptspinnerdata = new ArrayList<String>();
	final ArrayList<String> checking = new ArrayList<String>();
	final ArrayList<String> itemchecking = new ArrayList<String>();
	final ArrayList<String> itemsdp = new ArrayList<String>();
	final ArrayList<String> itemsNo = new ArrayList<String>();
	static final int SIZEWIDTH_MAX = 8;
	static final int SIZEHEIGHT_MAX = 8;
	EditText printerid;
	ArrayList<String> printVal = new ArrayList<String>();
	boolean exitActivity = false;
	public final Pattern USER_PATTERN = Pattern
			.compile("[-+]?[0-9]*\\.?[0-9]+");

	
	DatabaseForDemo sqlDB;
	SQLiteDatabase dbforloginlogoutWrite1,dbforloginlogoutRead1;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.settings_activity);
		Parameters.printerContext=SettingsActivity.this;
		Parameters.ServerSyncTimer();
		try{
		sqlDB=new DatabaseForDemo(SettingsActivity.this);
		dbforloginlogoutWrite1 = sqlDB.getWritableDatabase();
		dbforloginlogoutRead1 = sqlDB.getReadableDatabase();
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
		logout.setBackgroundResource(R.drawable.logoutnormal);
		image6.setBackgroundResource(R.drawable.st3);
		TextView loginnameempid = (TextView)findViewById(R.id.loginnameval);
		loginnameempid.setText(Parameters.usertypeloginvalue);
		detailslayout = (LinearLayout) findViewById(R.id.inflatingLayout);

		image0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(SettingsActivity.this,
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
					Intent intent1 = new Intent(SettingsActivity.this,
							InventoryActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialogforpermission(
							SettingsActivity.this,
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
				Intent intent1 = new Intent(SettingsActivity.this,
						StoresActivity.class);
				startActivity(intent1);
				finish();
			} else {
				showAlertDialogforpermission(
						SettingsActivity.this,
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
					Intent intent1 = new Intent(SettingsActivity.this,
							CustomerActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialogforpermission(
							SettingsActivity.this,
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
				if(Parameters.employee_permission){
				Intent intent1 = new Intent(SettingsActivity.this,
						EmployeeActivity.class);
				startActivity(intent1);
				finish();
			} else {
				showAlertDialogforpermission(
						SettingsActivity.this,
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
					Intent intent1 = new Intent(SettingsActivity.this,
							ReportsActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialogforpermission(
							SettingsActivity.this,
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

			}
		});
		image7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(SettingsActivity.this,
						ContactsActivity.class);
				startActivity(intent1);
				finish();
			}
		});
		image8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Parameters.profile_permission) {
					Intent intent1 = new Intent(SettingsActivity.this,
							ProfileActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialogforpermission(
							SettingsActivity.this,
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
				Parameters.methodForLogout(SettingsActivity.this);
				finish();
			}
		});
		logout2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Parameters.methodForLogout(SettingsActivity.this);
				finish();
			}
		});
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		height = displaymetrics.heightPixels;
		wwidth = displaymetrics.widthPixels;
		slideButton.setPadding(0, 0, 0, height - 40);
		// Toast.makeText(getApplicationContext(), ""+height, 2000).show();
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

		tslookup = (Button) findViewById(R.id.tslookup);
		printermainbtn = (Button) findViewById(R.id.printer);
		tax = (Button) findViewById(R.id.tax);
		paymentype = (Button) findViewById(R.id.payment);
		database = (Button) findViewById(R.id.database);
		company = (Button) findViewById(R.id.company);
		serverurl = (Button) findViewById(R.id.serverurl_button);
		card = (Button) findViewById(R.id.card);

		
		Cursor mCursorx303 = dbforloginlogoutRead1.rawQuery(
				"select * from " + DatabaseForDemo.DEPARTMENT_TABLE, null);
		if (mCursorx303 != null) {
			if (mCursorx303.moveToFirst()) {
				do {
					String catid = mCursorx303.getString(mCursorx303
							.getColumnIndex(DatabaseForDemo.DepartmentID));
					String check = mCursorx303.getString(mCursorx303
							.getColumnIndex(DatabaseForDemo.CHECKED_VALUE));
					deptspinnerdata.add(catid);
					checking.add(check);
				} while (mCursorx303.moveToNext());
			}
		}
		mCursorx303.close();
		list = (LinearLayout) findViewById(R.id.linear);
		list1 = (LinearLayout) findViewById(R.id.itemlinear);
		Button ok = (Button) findViewById(R.id.ok1);
		for (int p = 0; p < deptspinnerdata.size(); p++) {
			final LinearLayout row = new LinearLayout(this);
			row.setOrientation(LinearLayout.HORIZONTAL);
			row.setLayoutParams(new LayoutParams(
					android.view.ViewGroup.LayoutParams.FILL_PARENT,
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			row.setPadding(5, 0, 0, 0);
			checkbtn = new CheckBox(this);
			tv = new TextView(this);
			tv.setText(deptspinnerdata.get(p));
			checkbtn.setTag("" + deptspinnerdata.get(p));
			tv.setTag("" + deptspinnerdata.get(p));
			tv.setTextSize(18);
			tv.setId(100000000+p);
			// tv.setTextColor(Color.BLACK);
			boolean val = Boolean.valueOf(checking.get(p));
			checkbtn.setChecked(val);
			checkbtn.setId(p);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					android.view.ViewGroup.LayoutParams.MATCH_PARENT,
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			LayoutParams lp1 = new LayoutParams(170,
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			row.addView(tv, lp1);
			row.addView(checkbtn, lp);
			list.addView(row);
			tv.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					for (int n = 0; n < deptspinnerdata.size(); n++) {
						 ((TextView) findViewById(100000000+n))
										.setBackgroundColor(Color.TRANSPARENT);
					}
					setitemList(v.getTag().toString());
					v.setBackgroundColor(Color.GRAY);
				}
			});
		}
		
		if (deptspinnerdata.size() > 0) {
			 ((TextView) findViewById(100000000))
				.setBackgroundColor(Color.GRAY);
			setitemList(deptspinnerdata.get(0));
		}

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				
				for (int n = 0; n < deptspinnerdata.size(); n++) {
					((CheckBox) findViewById(n)).isChecked();
					Log.v("1 " + ((CheckBox) findViewById(n)).getTag(), "1 "
							+ (findViewById(n)));
					String name = "" + ((CheckBox) findViewById(n)).getTag();
					String value = ""
							+ ((CheckBox) findViewById(n)).isChecked();
					dbforloginlogoutRead1.execSQL("update " + DatabaseForDemo.DEPARTMENT_TABLE
							+ " set " + DatabaseForDemo.CHECKED_VALUE + "=\""
							+ value + "\" where " + DatabaseForDemo.DepartmentID
							+ "=\"" + name + "\"");
				}
				for (int n = 0; n < itemsdp.size(); n++) {
					((CheckBox) findViewById(1000 + n)).isChecked();
					Log.v("g " + ((CheckBox) findViewById(1000 + n)).getTag(), "g "
							+ (findViewById(1000 + n)));
					String name = ""
							+ ((CheckBox) findViewById(1000 + n)).getTag();
					String value = ""
							+ ((CheckBox) findViewById(1000 + n)).isChecked();
					Log.v("r " + name, " 1 " + value);
					dbforloginlogoutRead1.execSQL("update " + DatabaseForDemo.INVENTORY_TABLE
							+ " set " + DatabaseForDemo.CHECKED_VALUE + "=\""
							+ value + "\" where "
							+ DatabaseForDemo.INVENTORY_ITEM_NAME + "=\"" + name
							+ "\"");
				}
				if(deptspinnerdata.size()>-1)
				Toast.makeText(getApplicationContext(), "Saved", 2000).show();
			}
		});

		tslookup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				detailslayout.removeAllViews();
				deptspinnerdata.clear();
				tslookup.setBackgroundResource(R.drawable.highlightedtopmenuitem);
				printermainbtn.setBackgroundResource(R.drawable.toprightmenu);
				tax.setBackgroundResource(R.drawable.toprightmenu);
				paymentype.setBackgroundResource(R.drawable.toprightmenu);
				database.setBackgroundResource(R.drawable.toprightmenu);
				company.setBackgroundResource(R.drawable.toprightmenu);
				serverurl.setBackgroundResource(R.drawable.toprightmenu);
				card.setBackgroundResource(R.drawable.toprightmenu);
				tslookup.setTextColor(Color.BLACK);
				printermainbtn.setTextColor(Color.WHITE);
				tax.setTextColor(Color.WHITE);
				paymentype.setTextColor(Color.WHITE);
				database.setTextColor(Color.WHITE);
				company.setTextColor(Color.WHITE);
				serverurl.setTextColor(Color.WHITE);
				card.setTextColor(Color.WHITE);
				LayoutInflater inflater = getLayoutInflater();
				final View layout = inflater.inflate(R.layout.touchscreen,
						(ViewGroup) v.findViewById(R.id.inflatingLayout));


				Cursor mCursorx304 = dbforloginlogoutRead1.rawQuery(
						"select * from " + DatabaseForDemo.DEPARTMENT_TABLE,
						null);
				if (mCursorx304 != null) {
					if (mCursorx304.moveToFirst()) {
						do {
							String catid = mCursorx304.getString(mCursorx304
									.getColumnIndex(DatabaseForDemo.DepartmentID));
							String check = mCursorx304.getString(mCursorx304
									.getColumnIndex(DatabaseForDemo.CHECKED_VALUE));
							deptspinnerdata.add(catid);
							checking.add(check);
						} while (mCursorx304.moveToNext());
					}
				}
				mCursorx304.close();
				list = (LinearLayout) layout.findViewById(R.id.linear);
				list1 = (LinearLayout) layout.findViewById(R.id.itemlinear);
				Button ok = (Button) layout.findViewById(R.id.ok1);
				for (int p = 0; p < deptspinnerdata.size(); p++) {
					final LinearLayout row = new LinearLayout(
							SettingsActivity.this);
					row.setOrientation(LinearLayout.HORIZONTAL);
					row.setLayoutParams(new LayoutParams(
							android.view.ViewGroup.LayoutParams.FILL_PARENT,
							android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
					row.setPadding(5, 0, 0, 0);
					checkbtn = new CheckBox(SettingsActivity.this);
					tv = new TextView(SettingsActivity.this);
					tv.setText(deptspinnerdata.get(p));
					checkbtn.setTag("" + deptspinnerdata.get(p));
					tv.setTag("" + deptspinnerdata.get(p));
					tv.setTextSize(18);
					tv.setId(100000000+p);
					// tv.setTextColor(Color.BLACK);
					boolean val = Boolean.valueOf(checking.get(p));
					checkbtn.setChecked(val);
					checkbtn.setId(p);
					LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
							android.view.ViewGroup.LayoutParams.MATCH_PARENT,
							android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
					LayoutParams lp1 = new LayoutParams(170,
							android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
					row.addView(tv, lp1);
					row.addView(checkbtn, lp);
					list.addView(row);
					tv.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							for (int n = 0; n < deptspinnerdata.size(); n++) {
								 ((TextView) layout.findViewById(100000000+n))
												.setBackgroundColor(Color.TRANSPARENT);
							}
							
							
							System.out.println("the value is padma:"
									+ v.getTag().toString());
							v.setBackgroundColor(Color.GRAY);
							setitemList(v.getTag().toString());
						}
					});
				}
				
				if (deptspinnerdata.size() > 0) {
					 ((TextView) layout.findViewById(100000000))
						.setBackgroundColor(Color.GRAY);
					setitemList(deptspinnerdata.get(0));
				}

				ok.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						
						for (int n = 0; n < deptspinnerdata.size(); n++) {
							((CheckBox) layout.findViewById(n)).isChecked();
							Log.v("y "
									+ ((CheckBox) layout.findViewById(n))
											.getTag(),
									"g " + (layout.findViewById(n)));
							String name = " "
									+ ((CheckBox) layout.findViewById(n))
											.getTag();
							String value = " "
									+ ((CheckBox) layout.findViewById(n))
											.isChecked();
							Log.v("w " + name, "w " + value);
							dbforloginlogoutRead1.execSQL("update "
									+ DatabaseForDemo.DEPARTMENT_TABLE
									+ " set " + DatabaseForDemo.CHECKED_VALUE
									+ "=\"" + value + "\" where "
									+ DatabaseForDemo.DepartmentID + "=\""
									+ name + "\"");
						}
						for (int n = 0; n < itemsdp.size(); n++) {
							((CheckBox) layout.findViewById(1000 + n))
									.isChecked();
							Log.v("w "
									+ ((CheckBox) layout.findViewById(1000 + n))
											.getTag(),
									"w " + (layout.findViewById(1000 + n)));
							String name = ""
									+ ((CheckBox) layout.findViewById(1000 + n))
											.getTag();
							String value = ""
									+ ((CheckBox) layout.findViewById(1000 + n))
											.isChecked();
							Log.v("" + name, "" + value);
							dbforloginlogoutRead1.execSQL("update "
									+ DatabaseForDemo.INVENTORY_TABLE + " set "
									+ DatabaseForDemo.CHECKED_VALUE + "=\""
									+ value + "\" where "
									+ DatabaseForDemo.INVENTORY_ITEM_NAME
									+ "=\"" + name + "\"");
						}
						if(deptspinnerdata.size()>-1)
							Toast.makeText(getApplicationContext(), "Saved", 2000).show();
					}
				});

				detailslayout.addView(layout);
			}
		});

		printermainbtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				detailslayout.removeAllViews();
				tslookup.setBackgroundResource(R.drawable.toprightmenu);
				printermainbtn
						.setBackgroundResource(R.drawable.highlightedtopmenuitem);
				tax.setBackgroundResource(R.drawable.toprightmenu);
				paymentype.setBackgroundResource(R.drawable.toprightmenu);
				database.setBackgroundResource(R.drawable.toprightmenu);
				company.setBackgroundResource(R.drawable.toprightmenu);
				serverurl.setBackgroundResource(R.drawable.toprightmenu);
				card.setBackgroundResource(R.drawable.toprightmenu);
				tslookup.setTextColor(Color.WHITE);
				printermainbtn.setTextColor(Color.BLACK);
				tax.setTextColor(Color.WHITE);
				paymentype.setTextColor(Color.WHITE);
				database.setTextColor(Color.WHITE);
				company.setTextColor(Color.WHITE);
				serverurl.setTextColor(Color.WHITE);
				card.setTextColor(Color.WHITE);
				LayoutInflater inflater = getLayoutInflater();
				final View layout = inflater.inflate(R.layout.text,
						(ViewGroup) v.findViewById(R.id.inflatingLayout));

				final Button addcat = (Button) layout.findViewById(R.id.addcat);
				final Button viewcat = (Button) layout
						.findViewById(R.id.viewcat);
				final LinearLayout ll_add = (LinearLayout) layout
						.findViewById(R.id.add_ll);
				final LinearLayout ll_view = (LinearLayout) layout
						.findViewById(R.id.view_ll);

				addcat.setBackgroundColor(Color.parseColor("#3c6586"));
				viewcat.setBackgroundColor(Color.parseColor("#cbcbcb"));
				ll_add.setVisibility(View.VISIBLE);
				ll_view.setVisibility(View.GONE);
				addcat.setTextColor(Color.WHITE);
				viewcat.setTextColor(Color.BLACK);

				addcat.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						addcat.setBackgroundColor(Color.parseColor("#3c6586"));
						viewcat.setBackgroundColor(Color.parseColor("#cbcbcb"));
						ll_add.setVisibility(View.VISIBLE);
						ll_view.setVisibility(View.GONE);
						addcat.setTextColor(Color.WHITE);
						viewcat.setTextColor(Color.BLACK);
					}
				});

				viewcat.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						addcat.setBackgroundColor(Color.parseColor("#cbcbcb"));
						viewcat.setBackgroundColor(Color.parseColor("#3c6586"));
						ll_add.setVisibility(View.GONE);
						ll_view.setVisibility(View.VISIBLE);
						addcat.setTextColor(Color.BLACK);
						viewcat.setTextColor(Color.WHITE);
						listupdateforprinter();
					}
				});

				printerlist = (ListView) layout.findViewById(R.id.listView1);
				ll = (LinearLayout) layout.findViewById(R.id.linearLayout2);
				printerlist.setItemsCanFocus(false);
				printertype_spinner = (Spinner) layout
						.findViewById(R.id.spinner_printer_type);
				final ArrayAdapter<String> typeOfPrinter = new ArrayAdapter<String>(
						SettingsActivity.this,
						android.R.layout.simple_spinner_item);
				typeOfPrinter
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				typeOfPrinter.add("EPSON");
				typeOfPrinter.add("STAR");
				// typeOfPrinter.add(getString(R.string.printername_p60ii));
				final RadioGroup typeofprinting=(RadioGroup) layout.findViewById(R.id.typeofprinting);
				printertype_spinner.setAdapter(typeOfPrinter);
				spinnername = (Spinner) layout
						.findViewById(R.id.spinner_printer);
				final ArrayAdapter<String> adaptername = new ArrayAdapter<String>(
						SettingsActivity.this,
						android.R.layout.simple_spinner_item);
				adaptername
						.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				printertype_spinner
						.setOnItemSelectedListener(new OnItemSelectedListener() {
							@Override
							public void onItemSelected(
									AdapterView<?> parentView,
									View selectedItemView, int position, long id) {
								Log.v("janu",
										"" + typeOfPrinter.getItem(position));
								if (typeOfPrinter.getItem(position).equals(
										"EPSON")) {
									typeofprinting.setVisibility(View.GONE);
									adaptername.clear();
									adaptername
											.add(getString(R.string.printername_p60));
									adaptername
											.add(getString(R.string.printername_p60ii));
									adaptername
											.add(getString(R.string.printername_t20));
									adaptername
											.add(getString(R.string.printername_t70));
									adaptername
											.add(getString(R.string.printername_t70ii));
									adaptername
											.add(getString(R.string.printername_t81ii));
									adaptername
											.add(getString(R.string.printername_t82));
									adaptername
											.add(getString(R.string.printername_t82ii));
									adaptername
											.add(getString(R.string.printername_t88v));
									adaptername
											.add(getString(R.string.printername_u220));
								} else {
									typeofprinting.setVisibility(View.VISIBLE);
									adaptername.clear();
									adaptername
											.add(getString(R.string.printername_star1));
									adaptername
											.add(getString(R.string.printername_star2));
									adaptername
											.add(getString(R.string.printername_star3));
									adaptername
											.add(getString(R.string.printername_star4));
									adaptername
											.add(getString(R.string.printername_star5));
									adaptername
											.add(getString(R.string.printername_star6));
									adaptername
											.add(getString(R.string.printername_star7));
									adaptername
											.add(getString(R.string.printername_star8));
									adaptername
											.add(getString(R.string.printername_star9));
									adaptername
											.add(getString(R.string.printername_star10));
									adaptername
											.add(getString(R.string.printername_star11));
									adaptername
											.add(getString(R.string.printername_star12));
									adaptername
											.add(getString(R.string.printername_star13));
								}
							}

							@Override
							public void onNothingSelected(
									AdapterView<?> parentView) {
								// your code here
								Log.v("janu", "seeeeeeeeeeeeeeeeeetha");
							}

						});

				spinnername.setAdapter(adaptername);
				printerid = (EditText) layout.findViewById(R.id.name);

				/*// init font list
				Spinner spinner = (Spinner) layout
						.findViewById(R.id.spinner_font);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						SettingsActivity.this,
						android.R.layout.simple_spinner_item);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				adapter.add(getString(R.string.font_a));
				adapter.add(getString(R.string.font_b));
				adapter.add(getString(R.string.font_c));
				spinner.setAdapter(adapter);*/

				prtip = (AutoCompleteTextView) layout.findViewById(R.id.ip);
				prtip.setOnEditorActionListener(new OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_DONE) {
							InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(prtip.getWindowToken(), 0);
						}
						return false;
					}
				});
				Spinner spinner = (Spinner) layout.findViewById(R.id.spinner_align);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(SettingsActivity.this,
						android.R.layout.simple_spinner_item);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				adapter.add(getString(R.string.align_left));
				adapter.add(getString(R.string.align_center));
				adapter.add(getString(R.string.align_right));
				spinner.setAdapter(adapter);
				spinner = (Spinner) layout.findViewById(R.id.spinner_language);
				adapter = new ArrayAdapter<String>(SettingsActivity.this,
						android.R.layout.simple_spinner_item);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				adapter.add(getString(R.string.language_ank));
				adapter.add(getString(R.string.language_japanese));
				adapter.add(getString(R.string.language_simplified_chinese));
				adapter.add(getString(R.string.language_traditional_chinese));
				adapter.add(getString(R.string.language_korean));
				adapter.add(getString(R.string.language_thai));
				adapter.add(getString(R.string.language_vietnamese));
				spinner.setAdapter(adapter);
				spinner = (Spinner) layout
						.findViewById(R.id.spinner_size_width);
				adapter = new ArrayAdapter<String>(SettingsActivity.this,
						android.R.layout.simple_spinner_item);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				for (int i = 1; i <= SIZEWIDTH_MAX; i++) {
					adapter.add(String.format("%d", i));
				}
				spinner.setAdapter(adapter);

				spinner = (Spinner) layout
						.findViewById(R.id.spinner_size_height);
				adapter = new ArrayAdapter<String>(SettingsActivity.this,
						android.R.layout.simple_spinner_item);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
				for (int i = 1; i <= SIZEHEIGHT_MAX; i++) {
					adapter.add(String.format("%d", i));
				}
				spinner.setAdapter(adapter);
				TextView text = (TextView) layout
						.findViewById(R.id.editText_text);
				String value = getString(R.string.text_edit_text);
				value = value.replaceAll("\\*", " ");
				text.setText(value);
				text = (TextView) layout.findViewById(R.id.editText_linespace);
				text.setText("30");
				text = (TextView) layout.findViewById(R.id.editText_xposition);
				text.setText("0");
				text = (TextView) layout.findViewById(R.id.editText_feedunit);
				text.setText("30");
				Button button = (Button) layout.findViewById(R.id.button_print);
				button.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						RadioButton	bluetoothradio=(RadioButton) layout.findViewById(R.id.bluetoothradio);
						String type="TCP:";
						if(bluetoothradio.isChecked()){
							type="BT:";
						}
						
						printText(type);
					}
				});

				detailslayout.addView(layout);

			}
		});

		tax.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				detailslayout.removeAllViews();
				tslookup.setBackgroundResource(R.drawable.toprightmenu);
				printermainbtn.setBackgroundResource(R.drawable.toprightmenu);
				tax.setBackgroundResource(R.drawable.highlightedtopmenuitem);
				paymentype.setBackgroundResource(R.drawable.toprightmenu);
				database.setBackgroundResource(R.drawable.toprightmenu);
				company.setBackgroundResource(R.drawable.toprightmenu);
				serverurl.setBackgroundResource(R.drawable.toprightmenu);
				card.setBackgroundResource(R.drawable.toprightmenu);
				tslookup.setTextColor(Color.WHITE);
				printermainbtn.setTextColor(Color.WHITE);
				tax.setTextColor(Color.BLACK);
				paymentype.setTextColor(Color.WHITE);
				database.setTextColor(Color.WHITE);
				company.setTextColor(Color.WHITE);
				serverurl.setTextColor(Color.WHITE);
				card.setTextColor(Color.WHITE);
				LayoutInflater inflater = getLayoutInflater();
				final View layout = inflater.inflate(R.layout.taxscreen,
						(ViewGroup) v.findViewById(R.id.inflatingLayout));

				final Button addcat = (Button) layout.findViewById(R.id.addcat);
				final Button viewcat = (Button) layout
						.findViewById(R.id.viewcat);
				final LinearLayout ll_add = (LinearLayout) layout
						.findViewById(R.id.add_ll);
				final LinearLayout ll_view = (LinearLayout) layout
						.findViewById(R.id.view_ll);

				addcat.setBackgroundColor(Color.parseColor("#3c6586"));
				viewcat.setBackgroundColor(Color.parseColor("#cbcbcb"));
				ll_add.setVisibility(View.VISIBLE);
				ll_view.setVisibility(View.GONE);
				addcat.setTextColor(Color.WHITE);
				viewcat.setTextColor(Color.BLACK);

				taxeslist = (ListView) layout.findViewById(R.id.listView1);
				taxeslist.setItemsCanFocus(false);

				addcat.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						addcat.setBackgroundColor(Color.parseColor("#3c6586"));
						viewcat.setBackgroundColor(Color.parseColor("#cbcbcb"));
						ll_add.setVisibility(View.VISIBLE);
						ll_view.setVisibility(View.GONE);
						addcat.setTextColor(Color.WHITE);
						viewcat.setTextColor(Color.BLACK);
					}
				});

				viewcat.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						addcat.setBackgroundColor(Color.parseColor("#cbcbcb"));
						viewcat.setBackgroundColor(Color.parseColor("#3c6586"));
						ll_add.setVisibility(View.GONE);
						ll_view.setVisibility(View.VISIBLE);
						addcat.setTextColor(Color.BLACK);
						viewcat.setTextColor(Color.WHITE);
						listUpdatefortax();
					}
				});

				final EditText tax_name = (EditText) layout
						.findViewById(R.id.tax_name_edit);
				final EditText tax_value = (EditText) layout
						.findViewById(R.id.rate_edit);

				Button save = (Button) layout.findViewById(R.id.tax_save);
				Button cancel = (Button) layout.findViewById(R.id.tax_cancel);

				save.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub

						if (tax_name.getText().length() > 2
								&& tax_value.getText().length() > 0) {
							if (checkRate(tax_value.getText().toString())) {
								String value = tax_value.getText().toString();
								
								
								String query = "select "+DatabaseForDemo.TAX_NAME+" from "+DatabaseForDemo.TAX_TABLE+" where "+DatabaseForDemo.TAX_NAME+"=\""+tax_name.getText().toString()+"\"";
								Cursor mCursorx305 = dbforloginlogoutWrite1.rawQuery(query, null);
								if(mCursorx305.getCount() >= 1){
									Toast.makeText(SettingsActivity.this, "Tax name already exist", Toast.LENGTH_SHORT).show();
									//cursor.close();
								}else{
									//cursor.close();
								ContentValues contentValues = new ContentValues();
								Log.i("select", ""
										+ tax_name.getText().toString() + ",,"
										+ value);
								contentValues.put(DatabaseForDemo.UNIQUE_ID,
										Parameters.randomValue());
								contentValues.put(DatabaseForDemo.CREATED_DATE,
										Parameters.currentTime());
								contentValues.put(
										DatabaseForDemo.MODIFIED_DATE,
										Parameters.currentTime());
								contentValues.put(DatabaseForDemo.MODIFIED_IN,
										"Local");
								contentValues.put(DatabaseForDemo.TAX_NAME,
										tax_name.getText().toString());
								contentValues.put(DatabaseForDemo.TAX_VALUE,
										value);
								dbforloginlogoutWrite1.insert(
										DatabaseForDemo.TAX_TABLE, null,
										contentValues);
								tax_name.setText("");
								tax_value.setText("");
								Toast.makeText(getApplicationContext(),
										"Saved", 1000).show();
								}
								mCursorx305.close();
							} else {
								Toast.makeText(SettingsActivity.this,
										"Enter Currect value", 1000).show();
							}
						} else {
							Toast.makeText(getApplicationContext(),
									"Enter Data", 1000).show();
						}
					}
				});

				cancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						tax_name.setText("");
						tax_value.setText("");
					}
				});

				detailslayout.addView(layout);
			}
		});

		paymentype.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				detailslayout.removeAllViews();
				tslookup.setBackgroundResource(R.drawable.toprightmenu);
				printermainbtn.setBackgroundResource(R.drawable.toprightmenu);
				tax.setBackgroundResource(R.drawable.toprightmenu);
				paymentype
						.setBackgroundResource(R.drawable.highlightedtopmenuitem);
				database.setBackgroundResource(R.drawable.toprightmenu);
				company.setBackgroundResource(R.drawable.toprightmenu);
				serverurl.setBackgroundResource(R.drawable.toprightmenu);
				card.setBackgroundResource(R.drawable.toprightmenu);
				tslookup.setTextColor(Color.WHITE);
				printermainbtn.setTextColor(Color.WHITE);
				tax.setTextColor(Color.WHITE);
				paymentype.setTextColor(Color.BLACK);
				database.setTextColor(Color.WHITE);
				company.setTextColor(Color.WHITE);
				serverurl.setTextColor(Color.WHITE);
				card.setTextColor(Color.WHITE);
				LayoutInflater inflater = getLayoutInflater();
				final View layout = inflater.inflate(R.layout.paymentscreen,
						(ViewGroup) v.findViewById(R.id.inflatingLayout));

				final Button addcat = (Button) layout.findViewById(R.id.addcat);
				final Button viewcat = (Button) layout
						.findViewById(R.id.viewcat);
				final LinearLayout ll_add = (LinearLayout) layout
						.findViewById(R.id.add_ll);
				final LinearLayout ll_view = (LinearLayout) layout
						.findViewById(R.id.view_ll);

				addcat.setBackgroundColor(Color.parseColor("#3c6586"));
				viewcat.setBackgroundColor(Color.parseColor("#cbcbcb"));
				ll_add.setVisibility(View.VISIBLE);
				ll_view.setVisibility(View.GONE);
				addcat.setTextColor(Color.WHITE);
				viewcat.setTextColor(Color.BLACK);

				paymentlist = (ListView) layout.findViewById(R.id.listView1);
				paymentlist.setItemsCanFocus(false);

				addcat.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						addcat.setBackgroundColor(Color.parseColor("#3c6586"));
						viewcat.setBackgroundColor(Color.parseColor("#cbcbcb"));
						ll_add.setVisibility(View.VISIBLE);
						ll_view.setVisibility(View.GONE);
						addcat.setTextColor(Color.WHITE);
						viewcat.setTextColor(Color.BLACK);
					}
				});

				viewcat.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						addcat.setBackgroundColor(Color.parseColor("#cbcbcb"));
						viewcat.setBackgroundColor(Color.parseColor("#3c6586"));
						ll_add.setVisibility(View.GONE);
						ll_view.setVisibility(View.VISIBLE);
						addcat.setTextColor(Color.BLACK);
						viewcat.setTextColor(Color.WHITE);
						listUpdateforpayment();
					}
				});

				final EditText payment_name = (EditText) layout
						.findViewById(R.id.payment_name_edit);
				final RadioGroup radioValue = (RadioGroup) layout
						.findViewById(R.id.pay_radiogroup);
				Button save = (Button) layout.findViewById(R.id.payment_save);
				Button cancel = (Button) layout
						.findViewById(R.id.payment_cancel);

				save.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {

						int selectedId = radioValue.getCheckedRadioButtonId();

						RadioButton radioButton = (RadioButton) findViewById(selectedId);
						// TODO Auto-generated method stub
						payment_name.getText().toString();
						if (payment_name.getText().length() > 2) {

							
							
							
							String query = "select *from "+DatabaseForDemo.PAYMENT_TABLE+" where "+DatabaseForDemo.PAYMENT_NAME+"=\""+payment_name.getText().toString()+"\"";
							Cursor cursorz01 = dbforloginlogoutWrite1.rawQuery(query, null);
							// startManagingCursor(cursorz01);
							if(cursorz01.getCount()>=1){
								Toast.makeText(SettingsActivity.this, "Payment type already exists", Toast.LENGTH_SHORT).show();
							}else{
							
							ContentValues contentValues = new ContentValues();
							contentValues.put(DatabaseForDemo.UNIQUE_ID,
									Parameters.randomValue());
							contentValues.put(DatabaseForDemo.CREATED_DATE,
									Parameters.currentTime());
							contentValues.put(DatabaseForDemo.MODIFIED_DATE,
									Parameters.currentTime());
							contentValues.put(DatabaseForDemo.MODIFIED_IN,
									"Local");
							contentValues.put(DatabaseForDemo.PAYMENT_NAME,
									payment_name.getText().toString());
							contentValues.put(DatabaseForDemo.PAYMENT_VALUE,
									radioButton.getTag().toString());
							dbforloginlogoutWrite1.insert(
									DatabaseForDemo.PAYMENT_TABLE, null,
									contentValues);

							Toast.makeText(getApplicationContext(), "Saved",
									1000).show();
							payment_name.setText("");
							}
//							cursor.close();
						} else {
							Toast.makeText(getApplicationContext(),
									"Enter Payment Name", 1000).show();
						}
					}
				});

				cancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						payment_name.setText("");
					}
				});

				detailslayout.addView(layout);
			}
		});

		database.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				detailslayout.removeAllViews();
				tslookup.setBackgroundResource(R.drawable.toprightmenu);
				printermainbtn.setBackgroundResource(R.drawable.toprightmenu);
				tax.setBackgroundResource(R.drawable.toprightmenu);
				paymentype.setBackgroundResource(R.drawable.toprightmenu);
				database.setBackgroundResource(R.drawable.highlightedtopmenuitem);
				company.setBackgroundResource(R.drawable.toprightmenu);
				serverurl.setBackgroundResource(R.drawable.toprightmenu);
				card.setBackgroundResource(R.drawable.toprightmenu);
				tslookup.setTextColor(Color.WHITE);
				printermainbtn.setTextColor(Color.WHITE);
				tax.setTextColor(Color.WHITE);
				paymentype.setTextColor(Color.WHITE);
				database.setTextColor(Color.BLACK);
				company.setTextColor(Color.WHITE);
				serverurl.setTextColor(Color.WHITE);
				card.setTextColor(Color.WHITE);
				LayoutInflater inflater = getLayoutInflater();
				final View layout = inflater.inflate(R.layout.backup_restore,
						(ViewGroup) v.findViewById(R.id.inflatingLayout));

				Button restorebutton = (Button) layout
						.findViewById(R.id.restorebutton);
				Button backupbutton = (Button) layout
						.findViewById(R.id.backupbutton);
				Button exportbutton = (Button) layout
						.findViewById(R.id.exportbutton);
				Button importbutton = (Button) layout
						.findViewById(R.id.importbutton);
				restorebutton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						showChooser();
					}
				});
				backupbutton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						showAlertDialogforpermission1(
								SettingsActivity.this,
								"Conformation",
								"All local data will be deleted . Are you sure that you want to synchronize the data to the server? ",
								false);
					}
				});

				exportbutton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try{
						File sd = Environment.getExternalStorageDirectory();
						System.out.println("sd is:" + sd);
						File data = Environment.getDataDirectory();
						System.out.println("data is:" + data);
						FileChannel source = null;
						FileChannel destination = null;
						String currentDBPath = "/data/" + "com.example.aonepos"
								+ "/databases/" + DatabaseForDemo.DB_NAME;
						System.out.println("currentdbpath is:" + currentDBPath);
						String backupDBPath = "/Android/data/BackUpAonePos/Download/AonePosDB_Backup.db";
						System.out.println("backupdbpath is:" + backupDBPath);
						File dbdir = new File(backupDBPath);
						System.out.println("db dir is:" + dbdir);
						if (!dbdir.exists()) {
							dbdir.mkdirs();
							System.out.println("directory is created");
						}
						File currentDB = new File(data, currentDBPath);
						System.out.println("current db is:" + currentDB);
						File backupDB = new File(sd, backupDBPath);
						System.out.println("backup db is:" + backupDB);
						try {
							source = new FileInputStream(currentDB)
									.getChannel();
							destination = new FileOutputStream(backupDB)
									.getChannel();
							destination.transferFrom(source, 0, source.size());
							source.close();
							destination.close();
							Toast.makeText(SettingsActivity.this,
									"DB Exported!", Toast.LENGTH_LONG).show();
							showAlertDialogforpermission(
									SettingsActivity.this,
									"File Saved At",
									"/Android/data/BackUpAonePos/Download/AonePosDB_Backup.db",
									false);
						} catch (IOException e) {
							e.printStackTrace();
						}
						}catch(SQLiteException e1){
							e1.printStackTrace();
						}catch (Exception e2) {
							e2.printStackTrace();
						}
					}

				});

				importbutton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						showAlertDialogforrestore(SettingsActivity.this,
								"Choose File",
								"Choose DB file to restore into database",
								false);
					}
				});

				detailslayout.addView(layout);
			}
		});

		company.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				detailslayout.removeAllViews();
				tslookup.setBackgroundResource(R.drawable.toprightmenu);
				printermainbtn.setBackgroundResource(R.drawable.toprightmenu);
				tax.setBackgroundResource(R.drawable.toprightmenu);
				paymentype.setBackgroundResource(R.drawable.toprightmenu);
				database.setBackgroundResource(R.drawable.toprightmenu);
				company.setBackgroundResource(R.drawable.highlightedtopmenuitem);
				serverurl.setBackgroundResource(R.drawable.toprightmenu);
				card.setBackgroundResource(R.drawable.toprightmenu);
				tslookup.setTextColor(Color.WHITE);
				printermainbtn.setTextColor(Color.WHITE);
				tax.setTextColor(Color.WHITE);
				paymentype.setTextColor(Color.WHITE);
				database.setTextColor(Color.WHITE);
				company.setTextColor(Color.BLACK);
				serverurl.setTextColor(Color.WHITE);
				card.setTextColor(Color.WHITE);
				LayoutInflater inflater = getLayoutInflater();
				final View layout = inflater.inflate(R.layout.company_activity,
						(ViewGroup) v.findViewById(R.id.inflatingLayout));

				Button gallery = (Button) layout.findViewById(R.id.gallery);
				imageView = (ImageView) layout.findViewById(R.id.imageView1);
				final Button submitbutton = (Button) layout.findViewById(R.id.submitbutton);
				
				final Button save_marchent = (Button) layout.findViewById(R.id.save_marchent);
				final EditText companyidurl = (EditText) layout.findViewById(R.id.companyidurl);
				final EditText name_edit = (EditText) layout.findViewById(R.id.name_edit);
				final EditText address_edit = (EditText) layout.findViewById(R.id.address_edit);
				final EditText address_edit2 = (EditText) layout.findViewById(R.id.address_edit2);
				final EditText address_phone = (EditText) layout.findViewById(R.id.address_phone);
				final EditText zip_edit = (EditText) layout.findViewById(R.id.zip_edit);
				companyidurl.setOnEditorActionListener(new OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_DONE) {
							InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(companyidurl.getWindowToken(), 0);
						}
						return false;
					}
				});
				
				
				String query = "select *from "+DatabaseForDemo.MERCHANT_TABLE;
				Cursor cursorz02 = dbforloginlogoutWrite1.rawQuery(query, null);
				// startManagingCursor(cursorz02);
				 update_bool=false;
				if(cursorz02!=null){
					if(cursorz02.getCount()>0){
						if(cursorz02.moveToFirst()){
							update_bool=true;
							do{
								if(cursorz02.isNull(cursorz02.getColumnIndex(DatabaseForDemo.MERCHANT_NAME))){
									name_edit.setText("");
								}else{
									String primaryurl = cursorz02.getString(cursorz02.getColumnIndex(DatabaseForDemo.MERCHANT_NAME));
									name_edit.setText(primaryurl);
								}
								if(cursorz02.isNull(cursorz02.getColumnIndex(DatabaseForDemo.MERCHANT_ADDRESS))){
									address_edit.setText("");
								}else{
									String secondaryurl = cursorz02.getString(cursorz02.getColumnIndex(DatabaseForDemo.MERCHANT_ADDRESS));
									address_edit.setText(secondaryurl);
								}
								if(cursorz02.isNull(cursorz02.getColumnIndex(DatabaseForDemo.MERCHANT_ADDRESS2))){
									address_edit2.setText("");
								}else{
									String secondaryurl2 = cursorz02.getString(cursorz02.getColumnIndex(DatabaseForDemo.MERCHANT_ADDRESS2));
									address_edit2.setText(secondaryurl2);
								}
								if(cursorz02.isNull(cursorz02.getColumnIndex(DatabaseForDemo.MERCHANT_PHONE))){
									address_phone.setText("");
								}else{
									String secondaryurl3 = cursorz02.getString(cursorz02.getColumnIndex(DatabaseForDemo.MERCHANT_PHONE));
									address_phone.setText(secondaryurl3);
								}
								if(cursorz02.isNull(cursorz02.getColumnIndex(DatabaseForDemo.MERCHANT_ZIP))){
									zip_edit.setText("");
								}else{
									String midurl = cursorz02.getString(cursorz02.getColumnIndex(DatabaseForDemo.MERCHANT_ZIP));
									zip_edit.setText(midurl);
								}
							}while(cursorz02.moveToNext());
						}
					}
				}
				cursorz02.close();
				
				
				
				File image = new File("/mnt/sdcard/ic_file.png");
				if (image.exists()) {

					imageView.setImageBitmap(BitmapFactory.decodeFile(image
							.getAbsolutePath()));
				}
				submitbutton.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(submitbutton.getWindowToken(), 0);
						String value=companyidurl.getText().toString().trim();
					if(value.length()>2){
						if(Parameters.isNetworkAvailable(SettingsActivity.this)){
					    String	 ataval="http://192.168.1.13/xampp/sites/aone/crm/serverurl.php?companyid="+value;
					    	
					       JSONclient client = new JSONclient(SettingsActivity.this, l);
						       client.execute(ataval);	    	
						   
				}else{
					Toast.makeText(getApplicationContext(), "NetWork Error",
							Toast.LENGTH_LONG).show();
				}
					}
					}
				}); 
				save_marchent.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method 

						// TODO Auto-generated method stub
						String primaryy = name_edit.getText().toString().trim();
						String secondaryy = address_edit.getText().toString().trim();
						String secondaryy2 = address_edit2.getText().toString().trim()+" ";
						String secondaryy3 =" "+ address_phone.getText().toString().trim()+" ";
						String mid = zip_edit.getText().toString().trim();

						if (primaryy.length() > 0) {
							if (secondaryy.length() > 0) {
								if (mid.length() > 0) {
										
										String modifytimeforedit=Parameters.currentTime();
										if(update_bool){
											dbforloginlogoutWrite1.execSQL("update "
													+ DatabaseForDemo.MERCHANT_TABLE
													+ " set modified_timestamp=\""
													+ Parameters.currentTime() + "\", server_local=\""
													+ "Local" + "\", " + DatabaseForDemo.MERCHANT_NAME
													+ "=\"" + primaryy + "\", "
													+ DatabaseForDemo.MERCHANT_ADDRESS + "=\"" + secondaryy
													+ "\", "
													+ DatabaseForDemo.MERCHANT_ADDRESS2 + "=\"" + secondaryy2
													+ "\", "
													+ DatabaseForDemo.MERCHANT_PHONE + "=\"" + secondaryy3
													+ "\", "+ DatabaseForDemo.MERCHANT_ZIP
													+ "=\"" + mid + "\"");
										}else{
											ContentValues contentValues = new ContentValues();
											contentValues.put(
													DatabaseForDemo.UNIQUE_ID,
													Parameters.randomValue());
											contentValues.put(
													DatabaseForDemo.CREATED_DATE,
													Parameters.currentTime());
											contentValues.put(
													DatabaseForDemo.MODIFIED_DATE,
													modifytimeforedit);
											contentValues.put(
													DatabaseForDemo.MODIFIED_IN,
													"Local");
											contentValues
													.put(DatabaseForDemo.MERCHANT_NAME,
															primaryy);
											contentValues
													.put(DatabaseForDemo.MERCHANT_ADDRESS,
															secondaryy);
											contentValues
											.put(DatabaseForDemo.MERCHANT_ADDRESS2,
													secondaryy2);
											contentValues
											.put(DatabaseForDemo.MERCHANT_PHONE,
													secondaryy3);
											contentValues
													.put(DatabaseForDemo.MERCHANT_ZIP,
															mid);
											
											dbforloginlogoutWrite1
													.insert(DatabaseForDemo.MERCHANT_TABLE,
															null, contentValues);
											contentValues.clear();	
										}
										Toast.makeText(getApplicationContext(),
												"Saved", 1000).show();
										try {
											JSONObject data = new JSONObject();
											JSONObject jsonobj = new JSONObject();

											jsonobj.put(DatabaseForDemo.UNIQUE_ID, randomNumbermercury);
											jsonobj.put(DatabaseForDemo.CREATED_DATE, cuurentTimemercury);
											jsonobj.put(DatabaseForDemo.MODIFIED_DATE, modifytimeforedit);
											jsonobj.put(DatabaseForDemo.MODIFIED_IN, "Local");
											jsonobj.put(DatabaseForDemo.MERCHANT_NAME,primaryy);
											jsonobj.put(DatabaseForDemo.MERCHANT_ADDRESS,secondaryy);
											jsonobj.put(DatabaseForDemo.MERCHANT_ADDRESS2,secondaryy2);
											jsonobj.put(DatabaseForDemo.MERCHANT_PHONE,secondaryy3);
											jsonobj.put(DatabaseForDemo.MERCHANT_ZIP,mid);
											JSONArray fields = new JSONArray();
											fields.put(0, jsonobj);
											data.put("fields", fields);
										//	if(update_bool)
											sendToServer(DatabaseForDemo.MERCHANT_TABLE, data.toString() ,"true");
											/*else
												sendToServer(DatabaseForDemo.MERCHANT_TABLE, data.toString() ,"");*/
										} catch (JSONException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
								} else {
									Toast.makeText(getApplicationContext(),
											"Enter ZIP Code", Toast.LENGTH_SHORT)
											.show();
								}
							} else {
								Toast.makeText(getApplicationContext(),
										"Enter Address", Toast.LENGTH_SHORT).show();
							}
						} else {
							Toast.makeText(getApplicationContext(),
									"Enter Name", Toast.LENGTH_SHORT).show();
						}

															
					}
				});
				gallery.setOnClickListener(new View.OnClickListener() {

					@Override
					public void onClick(View arg0) {

						Intent i = new Intent(
								Intent.ACTION_PICK,
								android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

						startActivityForResult(i, RESULT_LOAD_IMAGE);
						
						
					}
				});

				detailslayout.addView(layout);
			}
		});
		 l = new GetJSONListener() {

			@Override
			public void onRemoteCallComplete(JSONObject jsonFromNet) {
				// TODO Auto-generated method stub
				// showDatafromJSONObject(jsonFromNet);
				Log.v("josn", "" + jsonFromNet);
				String url=" ";
				String msg=" ";
				if(jsonFromNet!=null){
				try {
					JSONObject job = jsonFromNet.getJSONObject("response");
					 msg = job.getString("result");
					if(msg.equals("true")){
					 url = job.getString("serverurl");
					}
					Toast.makeText(getApplicationContext(), "" + msg,
							Toast.LENGTH_LONG).show();
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				if(msg.equals("true")){	
				
			
				String qry = "update "
						+ DatabaseForDemo.MISCELLANEOUS_TABLE
						+ " set "
						+ DatabaseForDemo.MISCEL_PAGEURL
						+ "=\"" + url + "\"";
				dbforloginlogoutWrite1.execSQL(qry);
				Parameters.OriginalUrl = url;
				System.out.println("url final"+url);
				}
			}else{
				Toast.makeText(SettingsActivity.this, "Error", 2000).show();
			}
			}
		};
		serverurl.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				detailslayout.removeAllViews();
				tslookup.setBackgroundResource(R.drawable.toprightmenu);
				printermainbtn.setBackgroundResource(R.drawable.toprightmenu);
				tax.setBackgroundResource(R.drawable.toprightmenu);
				paymentype.setBackgroundResource(R.drawable.toprightmenu);
				database.setBackgroundResource(R.drawable.toprightmenu);
				company.setBackgroundResource(R.drawable.toprightmenu);
				serverurl
						.setBackgroundResource(R.drawable.highlightedtopmenuitem);
				card.setBackgroundResource(R.drawable.toprightmenu);
				tslookup.setTextColor(Color.WHITE);
				printermainbtn.setTextColor(Color.WHITE);
				tax.setTextColor(Color.WHITE);
				paymentype.setTextColor(Color.WHITE);
				database.setTextColor(Color.WHITE);
				company.setTextColor(Color.WHITE);
				serverurl.setTextColor(Color.BLACK);
				card.setTextColor(Color.WHITE);
				LayoutInflater inflater = getLayoutInflater();
				final View layout = inflater.inflate(R.layout.server_url,
						(ViewGroup) v.findViewById(R.id.inflatingLayout));

				final Spinner demoselection = (Spinner)layout.findViewById(R.id.demodatatypespinner);
				final EditText mUrl = (EditText) layout
						.findViewById(R.id.serverurl);
				Button mButton = (Button) layout.findViewById(R.id.urlsave);

				
				String query = "select " + DatabaseForDemo.MISCEL_PAGEURL
						+ " from " + DatabaseForDemo.MISCELLANEOUS_TABLE;
				Cursor miscursn6 = dbforloginlogoutRead1.rawQuery(query, null);
				// startManagingCursor(miscursn6);
				if (miscursn6.getCount() > 0) {
					if (miscursn6 != null) {
						if (miscursn6.moveToFirst()) {
							do {
								if (miscursn6.isNull(miscursn6
										.getColumnIndex(DatabaseForDemo.MISCEL_PAGEURL))) {
									mUrl.setText("");
								} else {
									String urlval = miscursn6.getString(miscursn6
											.getColumnIndex(DatabaseForDemo.MISCEL_PAGEURL));
									mUrl.setText(urlval);
									Parameters.OriginalUrl = urlval;
									System.out.println("url val is:" + urlval);
								}
							} while (miscursn6.moveToNext());
						}
					}
				}
				miscursn6.close();
				
				if(Parameters.OriginalUrl.equals("http://www.mydata.ws/aoneposws/RetailStore/")){
					demoselection.setSelection(1);
					mUrl.setText(Parameters.OriginalUrl);
				}else if(Parameters.OriginalUrl.equals("http://www.mydata.ws/aoneposws/MexicanTacos/")){
					demoselection.setSelection(2);
					mUrl.setText(Parameters.OriginalUrl);
				}else if(Parameters.OriginalUrl.equals("http://www.mydata.ws/aoneposws/AoneposLiquor/")){
					demoselection.setSelection(3);
					mUrl.setText(Parameters.OriginalUrl);
				}else if(Parameters.OriginalUrl.equals("http://www.mydata.ws/aoneposws/IndianCusine/")){
					demoselection.setSelection(4);
					mUrl.setText(Parameters.OriginalUrl);
				}else{
					demoselection.setSelection(0);
					mUrl.setText(Parameters.OriginalUrl);
				}
				
				demoselection.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						String urlval = demoselection.getSelectedItem().toString();
						System.out.println("selected spinner val is:"+urlval);
						if(urlval.equals("Retail Store"))
						{	
							mUrl.setText("http://www.mydata.ws/aoneposws/RetailStore/");
							
						}
						else if(urlval.equals("Mexican Tacos")){
							mUrl.setText("http://www.mydata.ws/aoneposws/MexicanTacos/");
							
						}else if(urlval.equals("Liquor")){
							mUrl.setText("http://www.mydata.ws/aoneposws/AoneposLiquor/");
					
						}else if(urlval.equals("Indian Cusine")){
							mUrl.setText("http://www.mydata.ws/aoneposws/IndianCusine/");
						
						}else{
							//mUrl.setText("");
							mUrl.setText(Parameters.OriginalUrl);
						}
						}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub
						
						
					}
				});
				
				mUrl.setOnEditorActionListener(new OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_DONE) {
							InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(mUrl.getWindowToken(), 0);
						}
						return false;
					}
				});
				
				mButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						String url = mUrl.getText().toString().trim();
						if (url.equals("")) {

						} else {
							if (url.length() > 3) {
								if (Patterns.WEB_URL.matcher(url).matches()) {
									if (URLUtil.isHttpUrl(url)) {
										forurlSave=url;
										showAlertDialogforpermission1(
												SettingsActivity.this,
												"Confirmation",
												"All local data will be deleted . Are you sure that you want to synchronize the data to the server? ",
												false);
								
									} else {
										Toast.makeText(getApplicationContext(),
												"URL is invalid!",
												Toast.LENGTH_LONG).show();
									}
								} else {
									Toast.makeText(getApplicationContext(),
											"URL is invalid!",
											Toast.LENGTH_LONG).show();
								}

							} else {
								Toast.makeText(getApplicationContext(),
										"Enter Url", Toast.LENGTH_SHORT).show();
							}
						}

					}
				});
				detailslayout.addView(layout);
			}
		});

		card.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				detailslayout.removeAllViews();
				tslookup.setBackgroundResource(R.drawable.toprightmenu);
				printermainbtn.setBackgroundResource(R.drawable.toprightmenu);
				tax.setBackgroundResource(R.drawable.toprightmenu);
				paymentype.setBackgroundResource(R.drawable.toprightmenu);
				database.setBackgroundResource(R.drawable.toprightmenu);
				company.setBackgroundResource(R.drawable.toprightmenu);
				serverurl.setBackgroundResource(R.drawable.toprightmenu);
				card.setBackgroundResource(R.drawable.highlightedtopmenuitem);
				tslookup.setTextColor(Color.WHITE);
				printermainbtn.setTextColor(Color.WHITE);
				tax.setTextColor(Color.WHITE);
				paymentype.setTextColor(Color.WHITE);
				database.setTextColor(Color.WHITE);
				company.setTextColor(Color.WHITE);
				serverurl.setTextColor(Color.WHITE);
				card.setTextColor(Color.BLACK);
				
				
				
				
				String query = "select * from "+DatabaseForDemo.PaymentProcessorPreferences;
				Cursor cursorz03 = dbforloginlogoutWrite1.rawQuery(query, null);
				// startManagingCursor(cursorz03);
			
					if(cursorz03!=null){
						if(cursorz03.getCount()>0){
						if(cursorz03.moveToFirst()){
							
						do{
							if(cursorz03.isNull(cursorz03.getColumnIndex(DatabaseForDemo.PaymentProcessorName))){
								selectpaymentname = "";
							}else{
								selectpaymentname = cursorz03.getString(cursorz03.getColumnIndex(DatabaseForDemo.PaymentProcessorName));
							}
							if(cursorz03.isNull(cursorz03.getColumnIndex(DatabaseForDemo.PaymentProcessSelectvalue))){
								selectpaymentval = "";
							}else{
								selectpaymentval = cursorz03.getString(cursorz03.getColumnIndex(DatabaseForDemo.PaymentProcessSelectvalue));
							}
						}while(cursorz03.moveToNext());
						}
					}
				}
				cursorz03.close();
				
				LayoutInflater inflater = getLayoutInflater();
				final View layout = inflater.inflate(R.layout.card_adding,
						(ViewGroup) v.findViewById(R.id.inflatingLayout));

				final Spinner cardtypespinner = (Spinner) layout
						.findViewById(R.id.paymenttypespinner);
				final LinearLayout visiblell = (LinearLayout) layout
						.findViewById(R.id.visiblelayout);
				final LinearLayout mercury_layout = (LinearLayout) layout
						.findViewById(R.id.mercury_layout);
				
				String[] cardtypearray = getResources().getStringArray(R.array.cardtypearray);
				List<String> cardtypearraylist = Arrays.asList(cardtypearray);
				cardtypespinner.setSelection(cardtypearraylist.indexOf(selectpaymentval));
				final EditText mMid = (EditText) layout.findViewById(R.id.mid);
				final EditText mTid = (EditText) layout.findViewById(R.id.tid);
				final EditText primary = (EditText) layout
						.findViewById(R.id.primary);
				final EditText secondary = (EditText) layout
						.findViewById(R.id.secondary);
				final CheckBox check1 = (CheckBox) layout
						.findViewById(R.id.checkBox1);
				final CheckBox check2 = (CheckBox) layout
						.findViewById(R.id.checkBox2);
			
				final EditText et_primaryurl = (EditText) layout.findViewById(R.id.et_primaryurl);
				final EditText et_secondayurl = (EditText) layout.findViewById(R.id.et_secondayurl);
				final EditText et_merchantID = (EditText) layout.findViewById(R.id.et_merchantID);
				final EditText et_password = (EditText) layout.findViewById(R.id.et_password);
				Button btnLogin = (Button) layout.findViewById(R.id.btnLogin);
				Button mButton = (Button) layout.findViewById(R.id.button1);
				
				cardtypespinner.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								// TODO Auto-generated method stub
								
								String value=cardtypespinner.getSelectedItem().toString();
								Parameters.paymentprocesstype = value;
								System.out.println("payment processor type is:"+Parameters.paymentprocesstype);
								
								dbforloginlogoutWrite1.delete(DatabaseForDemo.PaymentProcessorPreferences, null, null);
								ContentValues contentValues = new ContentValues();
								contentValues.put(
										DatabaseForDemo.PaymentProcessorName,
										value);
								contentValues.put(
										DatabaseForDemo.PaymentProcessSelectvalue,
										value);
								dbforloginlogoutWrite1
										.insert(DatabaseForDemo.PaymentProcessorPreferences,
												null, contentValues);			
								if (value.equals("First Data")) {
									mercury_layout.setVisibility(View.GONE);
									visiblell.setVisibility(View.VISIBLE);
									String query = "select *from "+DatabaseForDemo.CREDITCARD_TABLE+" where "+DatabaseForDemo.CREDIT_PAYMENT_NAME+"=\""+value+"\"";
									Cursor cursorz04 = dbforloginlogoutWrite1.rawQuery(query, null);
									// startManagingCursor(cursorz04);
									if(cursorz04!=null){
										if(cursorz04.getCount()>0){
											if(cursorz04.moveToFirst()){
												do{
													if(cursorz04.isNull(cursorz04.getColumnIndex(DatabaseForDemo.CREDIT_PRIMARY_URL))){
														primary.setText("");
													}else{
														String primaryurl = cursorz04.getString(cursorz04.getColumnIndex(DatabaseForDemo.CREDIT_PRIMARY_URL));
														primary.setText(primaryurl);
													}
													if(cursorz04.isNull(cursorz04.getColumnIndex(DatabaseForDemo.CREDIT_SECONDARY_URL))){
														secondary.setText("");
													}else{
														String secondaryurl = cursorz04.getString(cursorz04.getColumnIndex(DatabaseForDemo.CREDIT_SECONDARY_URL));
														secondary.setText(secondaryurl);
													}
													if(cursorz04.isNull(cursorz04.getColumnIndex(DatabaseForDemo.CREDIT_MERCHANT))){
														mMid.setText("");
													}else{
														String midurl = cursorz04.getString(cursorz04.getColumnIndex(DatabaseForDemo.CREDIT_MERCHANT));
														mMid.setText(midurl);
													}
													if(cursorz04.isNull(cursorz04.getColumnIndex(DatabaseForDemo.CREDIT_TERMINAL))){
														mTid.setText("");
													}else{
														String tidurl = cursorz04.getString(cursorz04.getColumnIndex(DatabaseForDemo.CREDIT_TERMINAL));
														mTid.setText(tidurl);
													}
												}while(cursorz04.moveToNext());
											}
										}
									}
									cursorz04.close();
									
								}else if(value.equals("Express Manual")){
									visiblell.setVisibility(View.GONE);
									mercury_layout.setVisibility(View.GONE);
								} else if(value.equals("Mercury Pay")){
									visiblell.setVisibility(View.GONE);
									mercury_layout.setVisibility(View.VISIBLE);
									String query = "select * from "+DatabaseForDemo.MERCURY_PAY_TABLE;
								
									Cursor cursorz05 = dbforloginlogoutWrite1.rawQuery(query, null);
									update_bool=false;
									if(cursorz05!=null){
										if(cursorz05.getCount()>0){
											if(cursorz05.moveToFirst()){
												 update_bool=true;
												do{
													if(cursorz05.isNull(cursorz05.getColumnIndex(DatabaseForDemo.MERCURY_PRIMARY_URL))){
														et_primaryurl.setText("");
													}else{
														String primaryurl = cursorz05.getString(cursorz05.getColumnIndex(DatabaseForDemo.MERCURY_PRIMARY_URL));
														et_primaryurl.setText(primaryurl);
													}
													
														randomNumbermercury = cursorz05.getString(cursorz05.getColumnIndex(DatabaseForDemo.UNIQUE_ID));
														 cuurentTimemercury=cursorz05.getString(cursorz05.getColumnIndex(DatabaseForDemo.CREATED_DATE));
													
													if(cursorz05.isNull(cursorz05.getColumnIndex(DatabaseForDemo.MERCURY_SECONDARY_URL))){
														et_secondayurl.setText("");
													}else{
														String secondaryurl = cursorz05.getString(cursorz05.getColumnIndex(DatabaseForDemo.MERCURY_SECONDARY_URL));
														et_secondayurl.setText(secondaryurl);
													}
													if(cursorz05.isNull(cursorz05.getColumnIndex(DatabaseForDemo.MERCURY_MERCHANT_ID))){
														et_merchantID.setText("");
													}else{
														String midurl = cursorz05.getString(cursorz05.getColumnIndex(DatabaseForDemo.MERCURY_MERCHANT_ID));
														et_merchantID.setText(midurl);
													}
													if(cursorz05.isNull(cursorz05.getColumnIndex(DatabaseForDemo.MERCURY_PASSWORD))){
														et_password.setText("");
													}else{
														String tidurl = cursorz05.getString(cursorz05.getColumnIndex(DatabaseForDemo.MERCURY_PASSWORD));
														et_password.setText(tidurl);
													}
												}while(cursorz05.moveToNext());
											}
										}
									}
									cursorz05.close();
								} else if(value.equals("Authorize.Net"))
								{
									mercury_layout.setVisibility(View.GONE);
									visiblell.setVisibility(View.GONE);
									Intent authorize=new Intent(SettingsActivity.this,SDKActivity.class);
									startActivity(authorize);
								}else{
									visiblell.setVisibility(View.GONE);
									mercury_layout.setVisibility(View.GONE);
								} 
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub

							}
						});
				
				mTid.setOnEditorActionListener(new OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_DONE) {
							InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(mTid.getWindowToken(), 0);
						}
						return false;
					}
				});
				btnLogin.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						String primaryy = et_primaryurl.getText().toString().trim();
						String secondaryy = et_secondayurl.getText().toString().trim();
						String mid = et_merchantID.getText().toString().trim();
						String tid = et_password.getText().toString()
								.trim();

						if (primaryy.length() > 0) {
							if (secondaryy.length() > 0) {
								if (mid.length() > 0) {
									if (tid.length() > 0) {
										
										String modifytimeforedit=Parameters.currentTime();
										if(update_bool){
											dbforloginlogoutWrite1.execSQL("update "
													+ DatabaseForDemo.MERCURY_PAY_TABLE
													+ " set modified_timestamp=\""
													+ Parameters.currentTime() + "\", server_local=\""
													+ "Local" + "\", " + DatabaseForDemo.MERCURY_PRIMARY_URL
													+ "=\"" + primaryy + "\", "
													+ DatabaseForDemo.MERCURY_SECONDARY_URL + "=\"" + secondaryy
													+ "\" ,"+ DatabaseForDemo.MERCURY_MERCHANT_ID
													+ "=\"" + mid + "\", "
													+ DatabaseForDemo.MERCURY_PASSWORD + "=\"" + tid
													+ "\"");
										}else{
											ContentValues contentValues = new ContentValues();
										contentValues.put(
												DatabaseForDemo.UNIQUE_ID,
												randomNumbermercury);
										contentValues.put(
												DatabaseForDemo.CREATED_DATE,
												cuurentTimemercury);
										contentValues.put(
												DatabaseForDemo.MODIFIED_DATE,
												modifytimeforedit);
										contentValues.put(
												DatabaseForDemo.MODIFIED_IN,
												"Local");
										contentValues
												.put(DatabaseForDemo.MERCURY_PRIMARY_URL,
														primaryy);
										contentValues
												.put(DatabaseForDemo.MERCURY_SECONDARY_URL,
														secondaryy);
										contentValues
												.put(DatabaseForDemo.MERCURY_MERCHANT_ID,
														mid);
										contentValues
												.put(DatabaseForDemo.MERCURY_PASSWORD,
														tid);
										
										dbforloginlogoutWrite1
												.insert(DatabaseForDemo.MERCURY_PAY_TABLE,
														null, contentValues);
										contentValues.clear();
										}
										Toast.makeText(getApplicationContext(),
												"Saved", 1000).show();
										try {
											JSONObject data = new JSONObject();
											JSONObject jsonobj = new JSONObject();

											jsonobj.put(DatabaseForDemo.UNIQUE_ID, randomNumbermercury);
											jsonobj.put(DatabaseForDemo.CREATED_DATE, cuurentTimemercury);
											jsonobj.put(DatabaseForDemo.MODIFIED_DATE, modifytimeforedit);
											jsonobj.put(DatabaseForDemo.MODIFIED_IN, "Local");
											jsonobj.put(DatabaseForDemo.MERCURY_PRIMARY_URL,primaryy);
											jsonobj.put(DatabaseForDemo.MERCURY_SECONDARY_URL,secondaryy);
											jsonobj.put(DatabaseForDemo.MERCURY_MERCHANT_ID,mid);
											jsonobj.put(DatabaseForDemo.MERCURY_PASSWORD,tid);
											JSONArray fields = new JSONArray();
											fields.put(0, jsonobj);
											data.put("fields", fields);
										//	if(update_bool)
											sendToServer(DatabaseForDemo.MERCURY_PAY_TABLE, data.toString() , "true");
											/*else
											sendToServer(DatabaseForDemo.MERCURY_PAY_TABLE, data.toString() , "");*/
										} catch (JSONException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
									} else {
										Toast.makeText(getApplicationContext(),
												"Enter password", Toast.LENGTH_SHORT)
												.show();
									}
								} else {
									Toast.makeText(getApplicationContext(),
											"Enter Merchant id", Toast.LENGTH_SHORT)
											.show();
								}
							} else {
								Toast.makeText(getApplicationContext(),
										"Enter SECONDARY URL", Toast.LENGTH_SHORT).show();
							}
						} else {
							Toast.makeText(getApplicationContext(),
									"Enter PRIMARY URL", Toast.LENGTH_SHORT).show();
						}

					}
				});
				mButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View arg0) {
						// TODO Auto-generated method stub
						String mid = mMid.getText().toString().trim();
						String tid = mTid.getText().toString().trim();
						String primaryy = primary.getText().toString().trim();
						String secondaryy = secondary.getText().toString()
								.trim();
						String card = "NO";
						if (check1.isChecked())
							card = "YES";
						String require = "NO";
						if (check2.isChecked())
							require = "YES";

						if (primaryy.length() > 0) {
							if (secondaryy.length() > 0) {
								if (mid.length() > 0) {
									if (tid.length() > 0) {

										
										ContentValues contentValues = new ContentValues();
										contentValues.put(
												DatabaseForDemo.UNIQUE_ID,
												Parameters.randomValue());
										contentValues.put(
												DatabaseForDemo.CREATED_DATE,
												Parameters.currentTime());
										contentValues.put(
												DatabaseForDemo.MODIFIED_DATE,
												Parameters.currentTime());
										contentValues.put(
												DatabaseForDemo.MODIFIED_IN,
												"Local");
										contentValues
												.put(DatabaseForDemo.CREDIT_PRIMARY_URL,
														primaryy);
										contentValues
												.put(DatabaseForDemo.CREDIT_SECONDARY_URL,
														secondaryy);
										contentValues
												.put(DatabaseForDemo.CREDIT_MERCHANT,
														mid);
										contentValues
												.put(DatabaseForDemo.CREDIT_TERMINAL,
														tid);
										contentValues
												.put(DatabaseForDemo.CREDIT_DEBITCARD,
														"");
										contentValues
												.put(DatabaseForDemo.CREDIT_REQUIRE_CVV2,
														"");
										contentValues
										.put(DatabaseForDemo.CREDIT_PAYMENT_NAME,
												cardtypespinner.getSelectedItem().toString());
										contentValues
										.put(DatabaseForDemo.CREDIT_USERNAME,
												"");
										contentValues
										.put(DatabaseForDemo.CREDIT_TIME_OUT,
												"");

										dbforloginlogoutWrite1
												.insert(DatabaseForDemo.CREDITCARD_TABLE,
														null, contentValues);
										mTid.setText("");
										mMid.setText("");
										primary.setText("");
										secondary.setText("");

										contentValues.clear();
										Toast.makeText(getApplicationContext(),
												"Saved", 1000).show();
									} else {
										Toast.makeText(getApplicationContext(),
												"Enter Tid", Toast.LENGTH_SHORT)
												.show();
									}
								} else {
									Toast.makeText(getApplicationContext(),
											"Enter Mid", Toast.LENGTH_SHORT)
											.show();
								}
							} else {
								Toast.makeText(getApplicationContext(),
										"Enter URL", Toast.LENGTH_SHORT).show();
							}
						} else {
							Toast.makeText(getApplicationContext(),
									"Enter URL", Toast.LENGTH_SHORT).show();
						}

					}
				});
				detailslayout.addView(layout);

			}
		});
		}catch (Exception e) {
			// TODO: handle exception
			Log.e("on create error", "g " +e.getLocalizedMessage());
		}
	}

	private void showChooser() {
		// Use the GET_CONTENT intent from the utility class
		Intent target = FileUtils.createGetContentIntent();
		// Create the chooser Intent
		Intent intent = Intent.createChooser(target, "Select a file");
		try {
			startActivityForResult(intent, REQUEST_CHOOSER);
		} catch (ActivityNotFoundException e) {
			// The reason for the existence of aFileChooser
		}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		try{
		switch (requestCode) {
		case REQUEST_CHOOSER:
			// If the file selection was successful
			if (resultCode == RESULT_OK) {
				if (data != null) {
					// Get the URI of the selected file
					 uri1 = data.getData();
					departmentListData.clear();
					try {
						// Create a file instance from the URI
						final File file = FileUtils.getFile(uri1);
						// Toast.makeText(BackupRestoreActivity.this,
						// "File Selected: "+file.getAbsolutePath(),
						// Toast.LENGTH_LONG).show();
						if (file.getAbsolutePath().endsWith(".xls")) {
							Parameters.stopTimer();
							DownloadWebTask task = new DownloadWebTask();
						    task.execute("");
						} else {
							Toast.makeText(
									SettingsActivity.this,
									"Please check whether it is a valid XL sheet or not",
									Toast.LENGTH_LONG).show();
						}
					} catch (Exception e) {
						Log.e("FileSelectorTestActivity", "g " +e.getLocalizedMessage());
					}
				}
			}
			break;
		case RESULT_LOAD_IMAGE:
			if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
					&& null != data) {
				Uri selectedImage = data.getData();
				String[] filePathColumn = { MediaColumns.DATA };
				Bitmap bm=null;
				Cursor cursorz09 = getContentResolver().query(selectedImage,
						filePathColumn, null, null, null);
				// startManagingCursor(cursorz09);
				if(cursorz09!=null){
				cursorz09.moveToFirst();

				int columnIndex = cursorz09.getColumnIndex(filePathColumn[0]);
				String picturePath = cursorz09.getString(columnIndex);
				
//				cursor.close();
try{
				imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
				 bm = BitmapFactory.decodeFile(picturePath);
}catch(Exception e){
	e.getLocalizedMessage();
}
				}
				cursorz09.close();
				File filename;
				try {
					String path1 = android.os.Environment
							.getExternalStorageDirectory().toString();
					File file1 = new File(path1 + "/harinath");
					if (!file1.exists())
						file1.mkdirs();
					filename = new File(file1.getAbsolutePath() + "/" + "hari"
							+ ".jpg");
					FileOutputStream out = new FileOutputStream(filename);
					bm.compress(Bitmap.CompressFormat.JPEG, 90, out);
					out.flush();
					out.close();
					ContentValues image = new ContentValues();
					image.put(MediaColumns.TITLE, "harinath");
					image.put(MediaColumns.DISPLAY_NAME, "hari");
					image.put(ImageColumns.DESCRIPTION, "App Image");
					image.put(MediaColumns.DATE_ADDED,
							System.currentTimeMillis());
					image.put(MediaColumns.MIME_TYPE, "image/jpg");
					image.put(ImageColumns.ORIENTATION, 0);
					File parent = filename.getParentFile();
					image.put(Images.ImageColumns.BUCKET_ID, parent.toString()
							.toLowerCase().hashCode());
					image.put(Images.ImageColumns.BUCKET_DISPLAY_NAME, parent
							.getName().toLowerCase());
					image.put(MediaColumns.SIZE, filename.length());
					image.put(MediaColumns.DATA, filename.getAbsolutePath());
					Uri result = getContentResolver()
							.insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
									image);
					Toast.makeText(getApplicationContext(),
							"File is Saved in  " + filename, Toast.LENGTH_SHORT)
							.show();
					Log.v("hari", ""
							+ MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
					Log.v("path", "" + filename.getAbsolutePath());

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			break;
		case RESTORE_CODE:
			final Uri uri = data.getData();
			final File file = FileUtils.getFile(uri);
			// File sd = Environment.getExternalStorageDirectory();
			// System.out.println("sd is:"+sd);
			File dataval = Environment.getDataDirectory();
			System.out.println("data is:" + data);
			FileChannel source = null;
			FileChannel destination = null;
			String currentDBPath = file.getAbsolutePath();
			System.out.println("currentdbpath is:" + currentDBPath);
			String backupDBPath = "/data/" + "com.example.aonepos"
					+ "/databases/" + DatabaseForDemo.DB_NAME;
			System.out.println("backupdbpath is:" + backupDBPath);
			File dbdir = new File(backupDBPath);
			System.out.println("db dir is:" + dbdir);
			if (!dbdir.exists()) {
				dbdir.mkdirs();
				System.out.println("directory is created");
			}
			File currentDB = new File("", currentDBPath);
			System.out.println("current db is:" + currentDB);
			File backupDB = new File(dataval, backupDBPath);
			System.out.println("backup db is:" + backupDB);
			try {
				source = new FileInputStream(currentDB).getChannel();
				destination = new FileOutputStream(backupDB).getChannel();
				destination.transferFrom(source, 0, source.size());
				source.close();
				destination.close();
				Toast.makeText(SettingsActivity.this, "DB Restored!",
						Toast.LENGTH_LONG).show();

			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		}
		}catch (Exception e) {
			// TODO: handle exception
			Log.e("onActivityResult error","g " + e.getLocalizedMessage());
		}
		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	protected Dialog onCreateDialog(int id) {
		switch (id) {
		case progress_bar_type: // we set this to 0
			pDialog = new ProgressDialog(this);
			pDialog.setMessage("Downloading file. Please wait...");
			pDialog.setIndeterminate(false);
			pDialog.setMax(100);
			pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
			pDialog.setCancelable(true);
			pDialog.show();
			return pDialog;
		default:
			return null;
		}
	}
	
	
private class DownloadWebTask extends AsyncTask<String, Void, String> {
		
		ProgressDialog mSpinnerProgress;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mSpinnerProgress = new ProgressDialog(SettingsActivity.this);
	        mSpinnerProgress.setIndeterminate(true);
	        mSpinnerProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
	        mSpinnerProgress.setMessage("Please wait, data is fetching.");
	        mSpinnerProgress.setCancelable(false);
	        mSpinnerProgress.setCanceledOnTouchOutside(false);
	        mSpinnerProgress.show();
		}
		
	    @Override
	    protected String doInBackground(String... urls) {
	      String responseaa = "";

			try {
				try {
					String uniqueis = Parameters.randomValue();
					ContentValues contentValuesforcat = new ContentValues();
					contentValuesforcat.put(DatabaseForDemo.UNIQUE_ID,
							uniqueis);
					contentValuesforcat.put(DatabaseForDemo.CREATED_DATE,
							Parameters.currentTime());
					contentValuesforcat.put(
							DatabaseForDemo.MODIFIED_DATE,
							Parameters.currentTime());
					contentValuesforcat.put(DatabaseForDemo.MODIFIED_IN,
							"Local");
					contentValuesforcat.put(DatabaseForDemo.CategoryId,
							"None");
					contentValuesforcat.put(DatabaseForDemo.CategoryDesp,
							"None");
					dbforloginlogoutWrite1.insert(DatabaseForDemo.CATEGORY_TABLE, null,
							contentValuesforcat);
					
					try {
						JSONObject data = new JSONObject();
						JSONObject jsonobj = new JSONObject();
						jsonobj.put(DatabaseForDemo.CategoryId, "None");
						jsonobj.put(DatabaseForDemo.CategoryDesp,
								"None");
						jsonobj.put(DatabaseForDemo.UNIQUE_ID,
								uniqueis);
						JSONArray fields = new JSONArray();
						fields.put(0, jsonobj);
						data.put("fields", fields);
						dataval = data.toString();
						System.out
								.println("data val is:" + dataval);
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (Parameters.OriginalUrl.equals("")) {
						System.out
								.println("there is no server url val");
					} else {
						boolean isnet = Parameters.isNetworkAvailable(SettingsActivity.this);
						if (isnet) {
							
									JsonPostMethod jsonpost = new JsonPostMethod();
									String response = jsonpost
											.postmethodfordirect(
													"admin",
													"abcdefg",
													DatabaseForDemo.CATEGORY_TABLE,
													Parameters
															.currentTime(),
													Parameters
															.currentTime(),
													dataval, "");
									System.out
											.println("response test is:"
													+ response);
									
									String servertiem = null;
									try {
										JSONObject obj = new JSONObject(
												response);
										JSONObject responseobj = obj
												.getJSONObject("response");
										servertiem = responseobj
												.getString("server-time");
										System.out
												.println("servertime is:"
														+ servertiem);
										JSONArray array = obj
												.getJSONArray("insert-queries");
										System.out.println("array list length for insert is:"
												+ array.length());
										JSONArray array2 = obj
												.getJSONArray("delete-queries");
										System.out.println("array2 list length for delete is:"
												+ array2.length());
										for (int jj = 0, ii = 0; jj < array2
												.length()
												&& ii < array
														.length(); jj++, ii++) {
											String deletequerytemp = array2
													.getString(jj);
											String deletequery1 = deletequerytemp
													.replace("'",
															"\"");
											String deletequery = deletequery1
													.replace(
															"\\\"",
															"'");
											System.out
													.println("delete query"
															+ jj
															+ " is :"
															+ deletequery);

											String insertquerytemp = array
													.getString(ii);
											String insertquery1 = insertquerytemp
													.replace("'",
															"\"");
											String insertquery = insertquery1
													.replace(
															"\\\"",
															"'");
											System.out
													.println("delete query"
															+ jj
															+ " is :"
															+ insertquery);
try{
											dbforloginlogoutWrite1.execSQL(deletequery);
											dbforloginlogoutWrite1.execSQL(insertquery);
}catch(SQLException e){
e.printStackTrace();
}
											System.out
													.println("queries executed"
															+ ii);
										}
									} catch (JSONException e) {
										// TODO Auto-generated catch
										// block
										e.printStackTrace();
									}

									String select = "select *from "
											+ DatabaseForDemo.MISCELLANEOUS_TABLE;
									Cursor cursorz06 = dbforloginlogoutWrite1.rawQuery(
											select, null);
									if(cursorz06!=null){
									// startManagingCursor(cursorz06);
									if (cursorz06.getCount() > 0) {
										dbforloginlogoutWrite1.execSQL("update "
												+ DatabaseForDemo.MISCELLANEOUS_TABLE
												+ " set "
												+ DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL
												+ "=\"" + servertiem
												+ "\"");
									} else {
										ContentValues contentValues1 = new ContentValues();
										contentValues1
												.put(DatabaseForDemo.MISCEL_STORE,
														"store1");
										contentValues1
												.put(DatabaseForDemo.MISCEL_PAGEURL,
														"");
										contentValues1
												.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL,
														Parameters
																.currentTime());
										contentValues1
												.put(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL,
														Parameters
																.currentTime());
										dbforloginlogoutWrite1.insert(
												DatabaseForDemo.MISCELLANEOUS_TABLE,
												null,
												contentValues1);
									}
									}
									cursorz06.close();
									dataval = "";
								
						} else {
							
							ContentValues contentValues1 = new ContentValues();
							contentValues1.put(
									DatabaseForDemo.QUERY_TYPE,
									"insert");
							contentValues1
									.put(DatabaseForDemo.PENDING_USER_ID,
											Parameters.userid);
							contentValues1.put(
									DatabaseForDemo.PAGE_URL,
									"saveinfo.php");
							contentValues1
									.put(DatabaseForDemo.TABLE_NAME_PENDING,
											DatabaseForDemo.CATEGORY_TABLE);
							contentValues1
									.put(DatabaseForDemo.CURRENT_TIME_PENDING,
											Parameters
													.currentTime());
							contentValues1.put(
									DatabaseForDemo.PARAMETERS,
									dataval);
							dbforloginlogoutRead1.insert(
									DatabaseForDemo.PENDING_QUERIES_TABLE,
									null, contentValues1);
							dataval = "";
						}
					}
				//	int p = 0;
					String name = null, cost = null, no = null, dp = null, pri = null, st = null, vlu, taxone = null, checkedval = null;
					final File file = FileUtils.getFile(uri1);
					FileInputStream is = new FileInputStream(file.getAbsolutePath());
					POIFSFileSystem myFileSystem = new POIFSFileSystem(
							is);
				//	Log.e(" before myFileSystem", ""
					//		+ myFileSystem);
					// Create a workbookusing the File
					// System
					HSSFWorkbook myWorkBook = new HSSFWorkbook(
							myFileSystem);
				//	Log.e(" before myWorkBook", ""
					//		+ myWorkBook); // Get the
											// first
											// sheet
											// from
											// workbook
					HSSFSheet mySheet = myWorkBook
							.getSheetAt(0);
				//	Log.e(" before mySheet", ""
						//	+ mySheet);

					Iterator<org.apache.poi.ss.usermodel.Row> rowIter = mySheet
							.rowIterator();
				//	Log.e(" before rowIter", ""
						//	+ rowIter);
					String datavalstarting = "{\"fields\":[";
					String datavalending = "]}";
					String datavaltotal = "";
					String datavaltotaloptional = "";
					String dataval = "";
					String datavaloptional ="";
					while (rowIter.hasNext()) {

						HSSFRow myRow = (HSSFRow) rowIter
								.next();
					//	Log.e("  myRow", "" + myRow);
						Iterator<Cell> cellIter = myRow
								.cellIterator();
					//	Log.e("  cellIter", ""
							//	+ cellIter);
						//if (p<=50000) {
						//	p++;
							int i = 0;
							ContentValues contentValues = new ContentValues();
							String random = Parameters.randomValue();
							String random1 = Parameters.randomValue();
							while (cellIter.hasNext()) {
								HSSFCell myCell = (HSSFCell) cellIter
										.next();
							//	Log.e("  myCell", ""
									//	+ myCell);

								if (i == 0)
									name = "" + myCell;
								if (i == 1)
									no = "" + myCell;
								if (i == 2){
									dp = "" + myCell;
									if(departmentListData.contains(dp)){
										
									}else{
										departmentListData.add(dp);
										System.out.println("department val is:"+dp);
									}
								}
								if (i == 3)
									cost = "" + myCell;
								if (i == 4)
									st = "" + myCell;
								if (i == 5)
									vlu = "" + myCell;
								if (i == 6)
									pri = "" + myCell;
								if (i == 7)
									taxone = ""
											+ myCell;
								if (i == 8)
									checkedval = ""
											+ myCell;
							//	Log.w("FileUtils",
									//	"Cell Value: "
											//	+ myCell.toString());
								i++;
							}
							
							String now_date = Parameters
									.currentTime();
							contentValues
									.put(DatabaseForDemo.INVENTORY_AVG_COST,
											cost);
							contentValues
									.put(DatabaseForDemo.INVENTORY_DEPARTMENT,
											dp);
							contentValues
									.put(DatabaseForDemo.INVENTORY_IN_STOCK,
											st);
							contentValues
									.put(DatabaseForDemo.INVENTORY_ITEM_NAME,
											name);
							contentValues
									.put(DatabaseForDemo.INVENTORY_ITEM_NO,
											no);
							contentValues
									.put(DatabaseForDemo.INVENTORY_PRICE_CHANGE,
											pri);
							contentValues
									.put(DatabaseForDemo.INVENTORY_PRICE_TAX,
											"44");
							contentValues
									.put(DatabaseForDemo.INVENTORY_QUANTITY,
											"1");
							contentValues
									.put(DatabaseForDemo.INVENTORY_SECOND_DESCRIPTION,
											"Description");
							contentValues
									.put(DatabaseForDemo.INVENTORY_TAXONE,
											taxone);
							contentValues
									.put(DatabaseForDemo.UNIQUE_ID,
											random);
							contentValues
									.put(DatabaseForDemo.CREATED_DATE,
											now_date);
							contentValues
									.put(DatabaseForDemo.MODIFIED_DATE,
											now_date);
							contentValues
									.put(DatabaseForDemo.MODIFIED_IN,
											"Local");
							contentValues
									.put(DatabaseForDemo.INVENTORY_VENDOR,
											"12");
							contentValues
									.put(DatabaseForDemo.INVENTORY_NOTES,
											"");
							contentValues
									.put(DatabaseForDemo.CHECKED_VALUE,
											"true");
							contentValues
									.put(DatabaseForDemo.INVENTORY_TOTAL_TAX,
											"8.25");
							dbforloginlogoutWrite1
									.insert(DatabaseForDemo.INVENTORY_TABLE,
											null,
											contentValues);
							contentValues.clear();

							//harinath
							ContentValues	optional_info = new ContentValues();
							optional_info.put(
									DatabaseForDemo.UNIQUE_ID,
									random1);
							optional_info.put(
									DatabaseForDemo.CREATED_DATE,
									Parameters.currentTime());
							optional_info.put(
									DatabaseForDemo.MODIFIED_DATE,
									Parameters.currentTime());
							optional_info.put(
									DatabaseForDemo.MODIFIED_IN,
									"Local");
							optional_info.put(
									DatabaseForDemo.BONUS_POINTS,
									"");
							optional_info.put(DatabaseForDemo.BARCODES,
									"");
							optional_info.put(DatabaseForDemo.LOCATION,
									"");
							optional_info.put(
									DatabaseForDemo.UNIT_SIZE,
									"");
							optional_info.put(
									DatabaseForDemo.UNIT_TYPE,
									"");
							optional_info
									.put(DatabaseForDemo.COMMISSION_OPTIONAL_INFO,
											"");
							optional_info
									.put(DatabaseForDemo.INVENTORY_ALLOW_BUYBACK,
											"no");
							optional_info
									.put(DatabaseForDemo.INVENTORY_PROMPT_PRICE,
											"no");
							optional_info
									.put(DatabaseForDemo.INVENTORY_FOODSTAMPABLE,
											"no");
							optional_info
									.put(DatabaseForDemo.INVENTORY_PRINT_ON_RECEIPT,
											"no");
							optional_info
									.put(DatabaseForDemo.INVENTORY_COUNT_THIS_ITEM,
											"no");
							optional_info
									.put(DatabaseForDemo.INVENTORY_MODIFIER_ITEM,
											"no");
							optional_info.put(
									DatabaseForDemo.INVENTORY_ITEM_NO,
									no);
							dbforloginlogoutWrite1.insert(
									DatabaseForDemo.OPTIONAL_INFO_TABLE,
									null, optional_info);
							//harinath
							
							
							count = count+1;
							System.out
									.println("count val is:"+count);
							if(count == 50 || !rowIter.hasNext()){
								datavaltotal = datavaltotal+"{\""+DatabaseForDemo.INVENTORY_DEPARTMENT+"\":\""+dp+"\",\""+DatabaseForDemo.INVENTORY_ITEM_NO+"\":\""+no+"\"," +
										"\""+DatabaseForDemo.INVENTORY_ITEM_NAME+"\":\""+name+"\",\""+DatabaseForDemo.INVENTORY_SECOND_DESCRIPTION+"\":\"Description\"," +
												"\""+DatabaseForDemo.INVENTORY_AVG_COST+"\":\""+cost+"\",\""+DatabaseForDemo.INVENTORY_PRICE_CHANGE+"\":\""+pri+"\"," +
														"\""+DatabaseForDemo.INVENTORY_PRICE_TAX+"\":\""+(Double.parseDouble(pri)+(Double.parseDouble(pri)*8.25/100))+"\",\""+DatabaseForDemo.INVENTORY_IN_STOCK+"\":\""+st+"\"," +
																"\""+DatabaseForDemo.INVENTORY_VENDOR+"\":\"vendor\",\""+DatabaseForDemo.INVENTORY_TAXONE+"\":\""+taxone+"\"," +
																		"\""+DatabaseForDemo.INVENTORY_NOTES+"\":\"\",\""+DatabaseForDemo.INVENTORY_QUANTITY+"\":\"1\","+
																		"\""+DatabaseForDemo.CHECKED_VALUE+"\":\"true\",\""+DatabaseForDemo.INVENTORY_TOTAL_TAX+"\":\"8.25\"," +
																				"\""+DatabaseForDemo.UNIQUE_ID+"\":\""+random+"\"}";
								
								datavaltotaloptional = datavaltotaloptional+"{\""+DatabaseForDemo.BONUS_POINTS+"\":\" \",\""+DatabaseForDemo.INVENTORY_ITEM_NO+"\":\""+no+"\"," +
										"\""+DatabaseForDemo.BARCODES+"\":\" \",\""+DatabaseForDemo.LOCATION+"\":\" \"," +
												"\""+DatabaseForDemo.UNIT_SIZE+"\":\" \",\""+DatabaseForDemo.UNIT_TYPE+"\":\" \"," +
														"\""+DatabaseForDemo.COMMISSION_OPTIONAL_INFO+"\":\" \",\""+DatabaseForDemo.INVENTORY_ALLOW_BUYBACK+"\":\"no\"," +
																"\""+DatabaseForDemo.INVENTORY_PROMPT_PRICE+"\":\"no\",\""+DatabaseForDemo.INVENTORY_FOODSTAMPABLE+"\":\"no\"," +
																		"\""+DatabaseForDemo.INVENTORY_PRINT_ON_RECEIPT+"\":\"no\",\""+DatabaseForDemo.INVENTORY_COUNT_THIS_ITEM+"\":\"no\","+
																		"\""+DatabaseForDemo.INVENTORY_MODIFIER_ITEM+"\":\"no\",\""+DatabaseForDemo.UNIQUE_ID+"\":\""+random1+"\"}";
								
							}else{
							datavaltotal = datavaltotal+"{\""+DatabaseForDemo.INVENTORY_DEPARTMENT+"\":\""+dp+"\",\""+DatabaseForDemo.INVENTORY_ITEM_NO+"\":\""+no+"\"," +
									"\""+DatabaseForDemo.INVENTORY_ITEM_NAME+"\":\""+name+"\",\""+DatabaseForDemo.INVENTORY_SECOND_DESCRIPTION+"\":\"Description\"," +
											"\""+DatabaseForDemo.INVENTORY_AVG_COST+"\":\""+cost+"\",\""+DatabaseForDemo.INVENTORY_PRICE_CHANGE+"\":\""+pri+"\"," +
													"\""+DatabaseForDemo.INVENTORY_PRICE_TAX+"\":\""+(Double.parseDouble(pri)+(Double.parseDouble(pri)*8.25/100))+"\",\""+DatabaseForDemo.INVENTORY_IN_STOCK+"\":\""+st+"\"," +
															"\""+DatabaseForDemo.INVENTORY_VENDOR+"\":\"vendor\",\""+DatabaseForDemo.INVENTORY_TAXONE+"\":\""+taxone+"\"," +
																	"\""+DatabaseForDemo.INVENTORY_NOTES+"\":\"\",\""+DatabaseForDemo.INVENTORY_QUANTITY+"\":\"1\","+
																	"\""+DatabaseForDemo.CHECKED_VALUE+"\":\"true\",\""+DatabaseForDemo.INVENTORY_TOTAL_TAX+"\":\"8.25\"," +
																			"\""+DatabaseForDemo.UNIQUE_ID+"\":\""+random+"\"},";
							
							datavaltotaloptional = datavaltotaloptional+"{\""+DatabaseForDemo.BONUS_POINTS+"\":\" \",\""+DatabaseForDemo.INVENTORY_ITEM_NO+"\":\""+no+"\"," +
									"\""+DatabaseForDemo.BARCODES+"\":\" \",\""+DatabaseForDemo.LOCATION+"\":\" \"," +
											"\""+DatabaseForDemo.UNIT_SIZE+"\":\" \",\""+DatabaseForDemo.UNIT_TYPE+"\":\" \"," +
													"\""+DatabaseForDemo.COMMISSION_OPTIONAL_INFO+"\":\" \",\""+DatabaseForDemo.INVENTORY_ALLOW_BUYBACK+"\":\"no\"," +
															"\""+DatabaseForDemo.INVENTORY_PROMPT_PRICE+"\":\"no\",\""+DatabaseForDemo.INVENTORY_FOODSTAMPABLE+"\":\"no\"," +
																	"\""+DatabaseForDemo.INVENTORY_PRINT_ON_RECEIPT+"\":\"no\",\""+DatabaseForDemo.INVENTORY_COUNT_THIS_ITEM+"\":\"no\","+
																	"\""+DatabaseForDemo.INVENTORY_MODIFIER_ITEM+"\":\"no\",\""+DatabaseForDemo.UNIQUE_ID+"\":\""+random1+"\"},";
							}
							totalinventoryuploadingrecords.add(datavaltotal);
							totalinventoryuploadingrecordsoptional.add(datavaltotaloptional);
							/*String dataval = "";
							 * JSONObject data = new JSONObject();
							JSONObject jsonobj = new JSONObject();
							jsonobj.put(
									DatabaseForDemo.INVENTORY_DEPARTMENT,
									dp);
							jsonobj.put(
									DatabaseForDemo.INVENTORY_ITEM_NO,
									no);
							jsonobj.put(
									DatabaseForDemo.INVENTORY_ITEM_NAME,
									name);
							jsonobj.put(
									DatabaseForDemo.INVENTORY_SECOND_DESCRIPTION,
									"Description");
							jsonobj.put(
									DatabaseForDemo.INVENTORY_AVG_COST,
									cost);
							jsonobj.put(
									DatabaseForDemo.INVENTORY_PRICE_CHANGE,
									pri);
							jsonobj.put(
									DatabaseForDemo.INVENTORY_PRICE_TAX,
									"44");
							jsonobj.put(
									DatabaseForDemo.INVENTORY_IN_STOCK,
									st);
							jsonobj.put(
									DatabaseForDemo.INVENTORY_VENDOR,
									"12");
							jsonobj.put(
									DatabaseForDemo.INVENTORY_TAXONE,
									taxone);
							jsonobj.put(
									DatabaseForDemo.INVENTORY_NOTES,
									"");
							jsonobj.put(
									DatabaseForDemo.INVENTORY_QUANTITY,
									"1");
							jsonobj.put(
									DatabaseForDemo.CHECKED_VALUE,
									"true");
							jsonobj.put(
									DatabaseForDemo.INVENTORY_TOTAL_TAX,
									"8.25");
							jsonobj.put(
									DatabaseForDemo.UNIQUE_ID,
									random);
							JSONArray fields = new JSONArray();
							System.out.println("p val is:"+p);
							fields.put(p, jsonobj);
							data.put("fields", fields);
							dataval = data.toString();*/
							/*totalinventoryuploadingrecords.add(dataval);
							count = count+1;*/
							//System.out
								//	.println("data val is:"
									//		+ dataval);
							if(count == 50 || !rowIter.hasNext()){
								dataval = datavalstarting+datavaltotal+datavalending;
								datavaloptional = datavalstarting+datavaltotaloptional+datavalending;
								System.out.println("json Obj "+datavaloptional);
							if (Parameters.OriginalUrl.equals("")) {
								System.out	.println("there is no server url val");
							} else {
								boolean isnet = Parameters.isNetworkAvailable(SettingsActivity.this);
								if (isnet) {
							JsonPostMethod post = new JsonPostMethod();
							String response = post
									.postmethodfordirect(
											"admin",
											"abcdefg",
											DatabaseForDemo.INVENTORY_TABLE,
											Parameters
													.currentTime(),
											Parameters
													.currentTime(),
											dataval, "");
							sendDatatoServer(response);
							String response1 = post
									.postmethodfordirect(
											"admin",
											"abcdefg",
											DatabaseForDemo.OPTIONAL_INFO_TABLE,
											Parameters
													.currentTime(),
											Parameters
													.currentTime(),
													datavaloptional, "");
							sendDatatoServer(response1);
						
				} else {
					

					ContentValues contentValues2 = new ContentValues();
					contentValues2.put(
							DatabaseForDemo.QUERY_TYPE,
							"insert");
					contentValues2
							.put(DatabaseForDemo.PENDING_USER_ID,
									Parameters.userid);
					contentValues2.put(
							DatabaseForDemo.PAGE_URL,
							"saveinfo.php");
					contentValues2
							.put(DatabaseForDemo.TABLE_NAME_PENDING,
									DatabaseForDemo.INVENTORY_TABLE);
					contentValues2
							.put(DatabaseForDemo.CURRENT_TIME_PENDING,
									Parameters
											.currentTime());
					contentValues2.put(
							DatabaseForDemo.PARAMETERS,
							dataval);
					dbforloginlogoutRead1.insert(
							DatabaseForDemo.PENDING_QUERIES_TABLE,
							null, contentValues2);
					dataval = "";
					contentValues2.clear();
					contentValues2.put(
							DatabaseForDemo.QUERY_TYPE,
							"insert");
					contentValues2
							.put(DatabaseForDemo.PENDING_USER_ID,
									Parameters.userid);
					contentValues2.put(
							DatabaseForDemo.PAGE_URL,
							"saveinfo.php");
					contentValues2
							.put(DatabaseForDemo.TABLE_NAME_PENDING,
									DatabaseForDemo.OPTIONAL_INFO_TABLE);
					contentValues2
							.put(DatabaseForDemo.CURRENT_TIME_PENDING,
									Parameters
											.currentTime());
					contentValues2.put(
							DatabaseForDemo.PARAMETERS,
							datavaloptional);
					dbforloginlogoutRead1.insert(
							DatabaseForDemo.PENDING_QUERIES_TABLE,
							null, contentValues2);
					datavaloptional = "";
				
				}
			}
							totalinventoryuploadingrecords.clear();
							totalinventoryuploadingrecordsoptional.clear();
							dataval = "";
							datavaloptional = "";
							count = 0;
							datavaltotal = "";
							datavaltotaloptional = "";
							}
					//	}/*else{
						//	break;
						//}*/
					}
					
					/*JSONObject data = new JSONObject();
					JSONObject jsonobj = new JSONObject();
					JSONArray fields = new JSONArray();*/
					for(int i = 0; i<departmentListData.size(); i++){
						String uniqueval = Parameters.randomValue();
						String nowdate = Parameters.currentTime();
						ContentValues contentValues = new ContentValues();
						contentValues.put(DatabaseForDemo.UNIQUE_ID,uniqueval);
						contentValues.put(DatabaseForDemo.CREATED_DATE,	nowdate);
						contentValues.put(DatabaseForDemo.MODIFIED_DATE, nowdate);
						contentValues.put(DatabaseForDemo.MODIFIED_IN, "Local");
						contentValues.put(DatabaseForDemo.DepartmentID, departmentListData.get(i));
						contentValues.put(DatabaseForDemo.DepartmentDesp, departmentListData.get(i));
						contentValues.put(DatabaseForDemo.CategoryForDepartment, "None");
						contentValues.put(DatabaseForDemo.FoodstampableForDept,	"no");
						contentValues.put(DatabaseForDemo.TaxValForDept, "");
						contentValues.put(DatabaseForDemo.CHECKED_VALUE, "true");
						dbforloginlogoutWrite1.insert(DatabaseForDemo.DEPARTMENT_TABLE, null, contentValues);

						ContentValues contentValues1 = new ContentValues();
						contentValues1.put(DatabaseForDemo.UNIQUE_ID,uniqueval);
						contentValues1.put(DatabaseForDemo.CREATED_DATE,nowdate);
						contentValues1.put(DatabaseForDemo.MODIFIED_DATE,nowdate);
						contentValues1.put(DatabaseForDemo.MODIFIED_IN,	"Local");
						contentValues1.put(DatabaseForDemo.PrinterForDept, "None");
						contentValues1.put(DatabaseForDemo.TimeForDeptPrint,"0:0");
						contentValues1.put(DatabaseForDemo.DepartmentID, departmentListData.get(i));
						dbforloginlogoutWrite1.insert(DatabaseForDemo.DEPARTMENT_PRINTER_COMMANDS,null, contentValues1);
						
						if(i == departmentListData.size()-1){
						datavaltotal = datavaltotal+"{\""+DatabaseForDemo.DepartmentID+"\":\""+departmentListData.get(i)+"\",\""+DatabaseForDemo.DepartmentDesp+"\":\""+departmentListData.get(i)+"\"," +
								"\""+DatabaseForDemo.CategoryForDepartment+"\":\"None\",\""+DatabaseForDemo.CHECKED_VALUE+"\":\"true\"," +
										"\""+DatabaseForDemo.FoodstampableForDept+"\":\"no\",\""+DatabaseForDemo.TaxValForDept+"\":\"Tax1,\"," +
																		"\""+DatabaseForDemo.UNIQUE_ID+"\":\""+uniqueval+"\"}";
					}else{
						datavaltotal = datavaltotal+"{\""+DatabaseForDemo.DepartmentID+"\":\""+departmentListData.get(i)+"\",\""+DatabaseForDemo.DepartmentDesp+"\":\""+departmentListData.get(i)+"\"," +
								"\""+DatabaseForDemo.CategoryForDepartment+"\":\"None\",\""+DatabaseForDemo.CHECKED_VALUE+"\":\"true\"," +
										"\""+DatabaseForDemo.FoodstampableForDept+"\":\"no\",\""+DatabaseForDemo.TaxValForDept+"\":\"Tax1,\"," +
																		"\""+DatabaseForDemo.UNIQUE_ID+"\":\""+uniqueval+"\"},";
					}
						
						}
					dataval = datavalstarting+datavaltotal+datavalending;
					System.out.println("dataval for department is:"+dataval);
					if (Parameters.OriginalUrl.equals("")) {
						System.out
								.println("there is no server url val");
					} else {
						boolean isnet = Parameters.isNetworkAvailable(SettingsActivity.this);
						if (isnet) {
							
									JsonPostMethod jsonpost = new JsonPostMethod();
									String response = jsonpost
											.postmethodfordirect(
													"admin",
													"abcdefg",
													DatabaseForDemo.DEPARTMENT_TABLE,
													Parameters
															.currentTime(),
													Parameters
															.currentTime(),
													dataval, "");
									System.out.println("response test is:"	+ response);
									
									
									String servertiem = null;
									try {
										JSONObject obj = new JSONObject(
												response);
										JSONObject responseobj = obj
												.getJSONObject("response");
										servertiem = responseobj
												.getString("server-time");
									//	System.out
											//	.println("servertime is:"
												//		+ servertiem);
										JSONArray array = obj
												.getJSONArray("insert-queries");
									//	System.out.println("array list length for insert is:"
										//		+ array.length());
										JSONArray array2 = obj
												.getJSONArray("delete-queries");
									//	System.out.println("array2 list length for delete is:"
											//	+ array2.length());
										for (int jj = 0, ii = 0; jj < array2
												.length()
												&& ii < array
														.length(); jj++, ii++) {
											String deletequerytemp = array2
													.getString(jj);
											String deletequery1 = deletequerytemp
													.replace(
															"'",
															"\"");
											String deletequery = deletequery1
													.replace(
															"\\\"",
															"'");
										//	System.out
												//	.println("delete query"
													//		+ jj
													//		+ " is :"
													//		+ deletequery);

											String insertquerytemp = array
													.getString(ii);
											String insertquery1 = insertquerytemp
													.replace(
															"'",
															"\"");
											String insertquery = insertquery1
													.replace(
															"\\\"",
															"'");
											try{
											dbforloginlogoutWrite1.execSQL(deletequery);
											dbforloginlogoutWrite1.execSQL(insertquery);
											}catch(SQLException e){
												e.printStackTrace();
											}
										//	System.out
												//	.println("queries executed"
													//		+ ii);

										}
									} catch (JSONException e) {
										// TODO Auto-generated
										e.printStackTrace();
									}

									String select = "select *from "
											+ DatabaseForDemo.MISCELLANEOUS_TABLE;
									Cursor cursorz08 = dbforloginlogoutWrite1
											.rawQuery(select,
													null);
									// startManagingCursor(cursorz08);
									if(cursorz08!=null){
									if (cursorz08.getCount() > 0) {
										dbforloginlogoutWrite1.execSQL("update "
												+ DatabaseForDemo.MISCELLANEOUS_TABLE
												+ " set "
												+ DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL
												+ "=\""
												+ servertiem
												+ "\"");
									} else {
										ContentValues contentValues2 = new ContentValues();
										contentValues2
												.put(DatabaseForDemo.MISCEL_STORE,
														"store1");
										contentValues2
												.put(DatabaseForDemo.MISCEL_PAGEURL,
														"");
										contentValues2
												.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL,
														Parameters
																.currentTime());
										contentValues2
												.put(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL,
														Parameters
																.currentTime());
										dbforloginlogoutWrite1.insert(
												DatabaseForDemo.MISCELLANEOUS_TABLE,
												null,
												contentValues2);

									}
									}
									cursorz08.close();
									dataval = "";
								
						} else {
							

							ContentValues contentValues2 = new ContentValues();
							contentValues2.put(
									DatabaseForDemo.QUERY_TYPE,
									"insert");
							contentValues2
									.put(DatabaseForDemo.PENDING_USER_ID,
											Parameters.userid);
							contentValues2.put(
									DatabaseForDemo.PAGE_URL,
									"saveinfo.php");
							contentValues2
									.put(DatabaseForDemo.TABLE_NAME_PENDING,
											DatabaseForDemo.DEPARTMENT_TABLE);
							contentValues2
									.put(DatabaseForDemo.CURRENT_TIME_PENDING,
											Parameters
													.currentTime());
							contentValues2.put(
									DatabaseForDemo.PARAMETERS,
									dataval);
							dbforloginlogoutRead1.insert(
									DatabaseForDemo.PENDING_QUERIES_TABLE,
									null, contentValues2);
							dataval = "";
						}
					}
					
					
				} catch (Exception ex) {
					ex.printStackTrace();
				}

			} catch (Throwable t) {
				// just end the background thread
				Log.i("Animation", "Thread  exception "
						+ t);
				t.printStackTrace();
			}
		
					
	      return responseaa;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	    	Log.v("Log...response12 ",""+result);
	    	
	    	mSpinnerProgress.cancel();
	    	Parameters.ServerSyncTimer();
	    }	
	    
	  }
/*	@SuppressWarnings("deprecation")
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
		alertDialog.setButton("YES", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// new DownloadFileFromURL().execute(file_url);
				// http://www.androidhive.info/2012/04/android-downloading-file-by-showing-progress-bar/
				// 888888888888888888888888888
				if (Parameters.OriginalUrl.equals("")) {
					System.out.println("there is no server url");
				} else {
					boolean isnet = Parameters.isNetworkAvailable(SettingsActivity.this);
					if (isnet) {
						dialog.dismiss();
						new ProgressCountClass().execute();
					} else {
						Toast.makeText(SettingsActivity.this,
								"No Internet Connection", Toast.LENGTH_LONG)
								.show();
						dialog.dismiss();
					}
				}
			}
		});
		alertDialog.setButton2("NO", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});

		// Showing Alert Message
		alertDialog.show();
	}*/

	@SuppressWarnings("deprecation")
	public void showAlertDialogforpermission(Context context, String title,
			String message, Boolean status) {
		try{
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
		alertDialog.show();
		}catch(Exception e){
			Log.e("showAlertDialogforpermission","g " + e.getLocalizedMessage());
		}
	}

	@SuppressWarnings("deprecation")
	public void showAlertDialogforpermission1(Context context, String title,
			String message, Boolean status) {
		try{
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		// Setting Dialog Title
		alertDialog.setTitle(title);

		// Setting Dialog Message
		alertDialog.setMessage(message);

		// Setting alert dialog icon
		// alertDialog.setIcon((status) ? R.drawable.success :
		// "No Internet Connection");

		// Setting OK Button
		alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				try{
				deletetablesdata();
				deleteadmintablesdata();
								
				if(forurlSave.length()>3){
					Log.v("seeewww", "forurlSave");
					
					String qry = "update "
							+ DatabaseForDemo.MISCELLANEOUS_TABLE
							+ " set "
							+ DatabaseForDemo.MISCEL_PAGEURL
							+ "=\"" + forurlSave + "\"";
					dbforloginlogoutWrite1.execSQL(qry);
					Parameters.OriginalUrl = forurlSave;
					forurlSave="";
					System.out.println("kdfhjfahgadhg   "+forurlSave);
					Parameters.methodForLogout(SettingsActivity.this);
						finish();
				}else{
					System.out.println("kdfhjfahgadhg  sudha ");
					try {
						synctoserverMethod();
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						Log.e("showAlertDialogforpermission1","g " + e.getLocalizedMessage());
					}
				}
				}catch(Exception e){
					Log.e("Error", "g " +e.getLocalizedMessage());
				}
			}
		});
		alertDialog.setButton2("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		alertDialog.show();
		}catch(Exception e){
			Log.e("showAlertDialogforpermission1", "g " +e.getLocalizedMessage());
		}
	}
	
	String localSystemTimeString="", previousServerSyncTimeString="",forAsync="";
	long diff;
	
void synctoserverMethod(){

	System.out.println("general method is called");
	try{
	String query = "select "+DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL+ " from "+DatabaseForDemo.MISCELLANEOUS_TABLE;
	Cursor cursorass = dbforloginlogoutWrite1.rawQuery(query, null);
	if(cursorass.getCount()>0){
		if(cursorass!=null){
			if(cursorass.moveToFirst()){
			do{
				if(cursorass.isNull(cursorass.getColumnIndex(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL))){
					previousServerSyncTimeString = "previousday";
				}else{
				String servertime = cursorass.getString(cursorass.getColumnIndex(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL)).trim();
				if(servertime.length()<1||servertime.equals("")||servertime==null){
				previousServerSyncTimeString = "previousday";
				}else{
					previousServerSyncTimeString=servertime;
				}
				}
			}while(cursorass.moveToNext());
			}
		}
	}else{
		previousServerSyncTimeString = "previousday";
	}
	cursorass.close();
	localSystemTimeString = Parameters.currentTime();
	System.out.println("server time is:"+previousServerSyncTimeString);
	System.out.println("local time ishari:"+localSystemTimeString);
	
	if(previousServerSyncTimeString.equals("previousday")){
		forAsync="true";
		DownloadserverTask task = new DownloadserverTask();
	    task.execute("");
	}else{
	String format = "yyyy-MM-dd hh:mm:ss";
    SimpleDateFormat sdf = new SimpleDateFormat(format);
    Date dateObj1;
	try {
		 System.out.println("difference is:"+diff);
		dateObj1 = sdf.parse(localSystemTimeString);
		 Date dateObj2 = sdf.parse(previousServerSyncTimeString);
		    diff = dateObj1.getTime() - dateObj2.getTime();
		    System.out.println("difference is:"+diff);
	} catch (ParseException e3) {
		// TODO Auto-generated catch block
		e3.printStackTrace();
	} catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	DownloadserverTask task = new DownloadserverTask();
    task.execute("");
	}
	}catch(Exception e){
		 e.printStackTrace();
	}

}

private class DownloadserverTask extends AsyncTask<String, Void, String> {
	
	ProgressDialog mSpinnerProgress;
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mSpinnerProgress = new ProgressDialog(SettingsActivity.this);
        mSpinnerProgress.setIndeterminate(true);
        mSpinnerProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mSpinnerProgress.setMessage("Please wait... synchronizing to server...");
        mSpinnerProgress.setCancelable(false);
        mSpinnerProgress.setCanceledOnTouchOutside(false);
        mSpinnerProgress.show();
	}
	
    @Override
    protected String doInBackground(String... urls) {
      String response = "";
      try{
      pendingclear();
	  System.out.println("pendingclear"+diff);
		JsonPostMethod postmethod = new JsonPostMethod();
		String responsess = postmethod.postmethodforbackup(localSystemTimeString, "", "abcedefg", forAsync);
		backupcall(responsess);
      }catch(Exception e){
    	  e.printStackTrace();
      }
      return response;
    }

    @Override
    protected void onPostExecute(String result) {
    	
    	mSpinnerProgress.cancel();
    	Parameters.methodForLogout(SettingsActivity.this);
		finish();
    }
    
  }
	@SuppressWarnings("deprecation")
	public void showAlertDialogforrestore(Context context, String title,
			String message, Boolean status) {
		try{
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
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_GET_CONTENT);
				Uri uri = Uri.parse(Environment.getExternalStorageDirectory()
						.getPath() + "/Android/data/BackUpAonePos/Download");
				intent.setDataAndType(uri, "file/*");
				startActivityForResult(intent, RESTORE_CODE);
			}
		});
		alertDialog.show();
		}catch(Exception e){
			Log.e("showAlertDialogforrestore","g " + e.getLocalizedMessage());
		}
	}

	/*public class ProgressCountClass extends AsyncTask<Void, Void, Void> {
		public ProgressDialog loginDialog = new ProgressDialog(
				SettingsActivity.this);
		public Boolean flag;

		@Override
		protected void onPreExecute() {
			loginDialog.setMessage("Please wait synchronization is doing");
			loginDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			TotalServerSyncClass synccall = new TotalServerSyncClass();
			try {
				synccall.generalMethod();
			} catch (ParseException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			} catch (java.text.ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println("method is executed");
			return null;
		}

		@Override
		protected void onPostExecute(Void unused) {
			if (loginDialog.isShowing()) {
				loginDialog.dismiss();
				Toast.makeText(SettingsActivity.this,
						"Synchronization is completed", Toast.LENGTH_LONG)
						.show();
			} else {

			}
		}
	}
*/
	/*
	 * class ProgressCountClass extends AsyncTask<String, String, String> {
	 * 
	 * @Override protected void onPreExecute() { super.onPreExecute();
	 * showDialog(progress_bar_type); }
	 * 
	 * @Override protected String doInBackground(String... f_url) { int count =
	 * 0; try { URL url = new URL(f_url[0]); URLConnection conection =
	 * url.openConnection(); conection.connect(); // this will be useful so that
	 * you can show a tipical 0-100% progress bar int lenghtOfFile =
	 * conection.getContentLength();
	 * 
	 * // download the file InputStream input = new
	 * BufferedInputStream(url.openStream(), 8192);
	 * 
	 * // Output stream OutputStream output = new
	 * FileOutputStream("/sdcard/downloadedfile.jpg");
	 * 
	 * byte data[] = new byte[1024];
	 * 
	 * long total = 0; int lenghtOfFile =0;
	 * 
	 * for(int i = 0; i<=lenghtOfFile; i++){ total += count;
	 * publishProgress(""+(int)((total*100)/lenghtOfFile)); ServerSyncClass
	 * padma = new ServerSyncClass(); lenghtOfFile =
	 * Integer.parseInt(ServerSyncClass.rowcount);
	 * System.out.println("length of file is:"+lenghtOfFile);
	 * System.out.println("class object is created"); try {
	 * padma.generalMethod(); } catch (ParseException e2) { // TODO
	 * Auto-generated catch block e2.printStackTrace(); } catch
	 * (java.text.ParseException e) { // TODO Auto-generated catch block
	 * e.printStackTrace(); } System.out.println("method is executed"); }
	 * 
	 * while ((count = input.read(data)) != -1) { total += count; // publishing
	 * the progress.... // After this onProgressUpdate will be called
	 * publishProgress(""+(int)((total*100)/lenghtOfFile));
	 * 
	 * // writing data to file output.write(data, 0, count); }
	 * 
	 * // flushing output output.flush();
	 * 
	 * // closing streams output.close(); input.close();
	 * 
	 * } catch (Exception e) { Log.e("Error: ", e.getMessage()); }
	 * 
	 * return null; }
	 * 
	 * protected void onProgressUpdate(String... progress) { // setting progress
	 * percentage pDialog.setProgress(Integer.parseInt(progress[0])); }
	 * 
	 * @Override protected void onPostExecute(String file_url) { // dismiss the
	 * dialog after the file was downloaded dismissDialog(progress_bar_type);
	 * 
	 * // Displaying downloaded image into image view // Reading image path from
	 * sdcard // String imagePath =
	 * Environment.getExternalStorageDirectory().toString() +
	 * "/downloadedfile.jpg"; // setting downloaded into image view //
	 * my_image.setImageDrawable(Drawable.createFromPath(imagePath));
	 * Toast.makeText(SettingsActivity.this, "Synchronization is completed",
	 * Toast.LENGTH_LONG).show(); }
	 * 
	 * }
	 */

	/*public boolean isConnectingToInternet() {
		ConnectivityManager manager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
		try{
				// For 3G check
				Log.v("error","conection");
			//	boolean is3g = manager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE)
				//		.isConnectedOrConnecting();
				// For WiFi Check
				Log.v("error","conection");
				boolean isWifi = manager.getNetworkInfo(ConnectivityManager.TYPE_WIFI)
						.isConnectedOrConnecting();
				Log.v("error","conection");
				// Toast.makeText(getBaseContext(), is3g + " net " + isWifi,
				// 6000).show();

				if (!isWifi) {

					return false;
				} else {
					return true;
				}
		}catch(Exception e){
			System.out.println(e);
			return true;
		}
	}
*/
	public void listUpdateforpayment() {
		try{
		String selectQuery = "SELECT  * FROM " + DatabaseForDemo.PAYMENT_TABLE;

		Cursor cursorz010 = dbforloginlogoutRead1.rawQuery(selectQuery, null);
		// startManagingCursor(cursorz010);
		if(cursorz010!=null){
		ListEditAdapterForPayment adapter = new ListEditAdapterForPayment(
				SettingsActivity.this, cursorz010);
		adapter.setListener(this);
		paymentlist.setAdapter(adapter);
		}
		}catch(Exception e){
			Log.e("listUpdateforpayment","g " +e.getLocalizedMessage());
		}
	}

	public void count(int j) {
		try{
		testforid = 1;
		stLoop = (j - 1) * pagecount;
		endLoop = j * pagecount;
		if (endLoop >= totalcount) {
			endLoop = totalcount;
		}
		Log.v("lop", stLoop + "" + endLoop);
		id_data = getData(stLoop, endLoop, total_id_data);
		desc_data = getData(stLoop, endLoop, total_desc_data);
		Log.v("lop", "" + desc_data);
		System.out.println("vlaues_first size:" + id_data.size());
		System.out.println("vlaues_first size:" + desc_data.size());

		PrinterEditAdapter adapter = new PrinterEditAdapter(this, id_data,
				desc_data);
		adapter.setListener(SettingsActivity.this);
		printerlist.setAdapter(adapter);
		}catch(Exception e){
			Log.e("count","g " +e.getLocalizedMessage());
		}
	}

	public ArrayList<String> getData(int offset, int limit,
			ArrayList<String> list) {
		ArrayList<String> newList = new ArrayList<String>(limit);
		int end = limit;

		if (end > list.size()) {
			end = list.size();
		}
		newList.addAll(list.subList(offset, end));
		return newList;
	}

	public void listupdateforprinter() {
		id_data.clear();
		desc_data.clear();
		total_id_data.clear();
		total_desc_data.clear();
		ll.removeAllViews();
		
try{
		String selectQuery = "SELECT  * FROM " + DatabaseForDemo.PRINTER_TABLE;

		Cursor cursorz011 = dbforloginlogoutRead1.rawQuery(selectQuery, null);
		// startManagingCursor(cursorz011);
		if (cursorz011 != null) {
			if (cursorz011.moveToFirst()) {
				do {
					String name = cursorz011.getString(cursorz011
							.getColumnIndex(DatabaseForDemo.PRINTER_ID));
					String ip = cursorz011.getString(cursorz011
							.getColumnIndex(DatabaseForDemo.PRINTER_IP));
					total_id_data.add(name);
					total_desc_data.add(ip);
				} while (cursorz011.moveToNext());
			}
		
			totalcount = total_id_data.size();
			System.out.println("total count ve is:" + totalcount);

			int to = totalcount / pagecount;
			int too = totalcount % pagecount;
			int i = to;
			if (too != 0) {
				i = to + 1;
			}
			count(1);
			ll.setWeightSum(i);
			System.out.println("total count value :" + i);
			for (j = 1; j <= i; j++) {
				final Button btn = new Button(this);
				btn.setText("" + j);
				btn.setId(j);
				btn.setBackgroundResource(R.drawable.pnormal);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						android.view.ViewGroup.LayoutParams.MATCH_PARENT,
						android.view.ViewGroup.LayoutParams.WRAP_CONTENT, 1f);

				LinearLayout space = new LinearLayout(this);
				LayoutParams lp1 = new LayoutParams(1,
						android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
				ll.addView(btn, lp);
				ll.addView(space, lp1);
				((Button) findViewById(1))
						.setBackgroundResource(R.drawable.pactive);
				((Button) findViewById(1)).setTextColor(Color.WHITE);
				btn.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						for (int l = 1; l <= j - 1; l++) {
							((Button) findViewById(l))
									.setBackgroundResource(R.drawable.pnormal);
							((Button) findViewById(l))
									.setTextColor(Color.BLACK);
							System.out.println("total btn is:" + l);
						}
						btn.setBackgroundResource(R.drawable.pactive);
						btn.setTextColor(Color.WHITE);
						System.out.println("total count id is:" + btn.getId());
						count(Integer.parseInt(btn.getText().toString().trim()));
						testforid = Integer.parseInt(btn.getText().toString()
								.trim());
					}

				});

			}

		}
		cursorz011.close();
}catch (Exception e) {
	// TODO: handle exception
	e.getLocalizedMessage();
}
	}

	@Override
	public void onEditClickedforpayment(View v, final String string, String tag) {
		// TODO Auto-generated method stub
		try{
		final AlertDialog alertDialog1 = new AlertDialog.Builder(
				SettingsActivity.this).create();
		Log.e("tagdd", ".....gggggg");
		final EditText payment_name;
		Button save, cancel;
		final RadioGroup radioValue;
		final RadioButton yess, noo;

		LayoutInflater mInflater = LayoutInflater.from(SettingsActivity.this);
		View layout = mInflater.inflate(R.layout.payment_add_details, null);
		payment_name = (EditText) layout.findViewById(R.id.payment_name_edit);
		payment_name.setText(string);

		radioValue = (RadioGroup) layout.findViewById(R.id.pay_radiogroup);
		yess = (RadioButton) layout.findViewById(R.id.yes);
		noo = (RadioButton) layout.findViewById(R.id.no);
		if (tag.equals("YES")) {
			yess.setChecked(true);
			noo.setChecked(false);
		} else {
			noo.setChecked(true);
			yess.setChecked(false);
		}
		save = (Button) layout.findViewById(R.id.payment_save);
		cancel = (Button) layout.findViewById(R.id.payment_cancel);

		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String checked = null;
				if (yess.isChecked())
					checked = "YES";
				if (noo.isChecked())
					checked = "NO";
				payment_name.getText().toString();
				if (payment_name.getText().length() > 2) {

					
					dbforloginlogoutWrite1.execSQL("update "
							+ DatabaseForDemo.PAYMENT_TABLE
							+ " set unique_id=\"" + Parameters.randomValue()
							+ "\", created_timestamp=\""
							+ Parameters.currentTime()
							+ "\", modified_timestamp=\""
							+ Parameters.currentTime() + "\", server_local=\""
							+ "Local" + "\", " + DatabaseForDemo.PAYMENT_NAME
							+ "=\"" + payment_name.getText().toString() + "\", "
							+ DatabaseForDemo.PAYMENT_VALUE + "=\"" + checked
							+ "\" where payment_name=\"" + string + "\"");
					Toast.makeText(SettingsActivity.this, "Saved", 1000).show();
					listUpdateforpayment();
					alertDialog1.dismiss();
				} else {
					Toast.makeText(SettingsActivity.this, "Enter Data", 1000)
							.show();
				}

			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				alertDialog1.dismiss();
			}
		});
		alertDialog1.setView(layout);

		// Showing Alert Message
		alertDialog1.show();
		}catch(Exception e){
			Log.e("onEditClickedforpayment","g " +e.getLocalizedMessage());
		}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onDeleteClickedforpayment(View v, final String string) {
		// TODO Auto-generated method stub
		try{
		final AlertDialog alertDialog1 = new AlertDialog.Builder(
				SettingsActivity.this).create();
		LayoutInflater mInflater = LayoutInflater.from(SettingsActivity.this);
		View layout = mInflater.inflate(R.layout.delete_popup, null);
		Button ok = (Button) layout.findViewById(R.id.ok);
		Button cancel = (Button) layout.findViewById(R.id.cancel);

		alertDialog1.setTitle("Delete");

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				
				String where = DatabaseForDemo.PAYMENT_NAME + "=?";
				dbforloginlogoutWrite1.delete(DatabaseForDemo.PAYMENT_TABLE, where,
						new String[] { string });

				Toast.makeText(SettingsActivity.this, "deleted", 1000).show();
				listUpdateforpayment();
				alertDialog1.dismiss();

			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				alertDialog1.dismiss();
			}
		});
		alertDialog1.setView(layout);
		alertDialog1.show();
	}catch(Exception e){
		Log.e("onDeleteClickedforpayment","g " +e.getLocalizedMessage());
	}
	}

	public void listUpdatefortax() {
		try{

		String selectQuery = "SELECT  * FROM " + DatabaseForDemo.TAX_TABLE;

		Cursor 	cursorz012 = dbforloginlogoutRead1.rawQuery(selectQuery, null);
		ListEditAdapter adapter = new ListEditAdapter(SettingsActivity.this,
				cursorz012);
		adapter.setListener(this);
		taxeslist.setAdapter(adapter);
		}catch(Exception e){
		Log.e("listUpdatefortax","g " +e.getLocalizedMessage());
		}
	}

	@Override
	public void onEditClicked(View v, final String string, String tag) {
		// TODO Auto-generated method stub
		try{
		final AlertDialog alertDialog1 = new AlertDialog.Builder(
				SettingsActivity.this).create();
		Log.e("tagdd", ".....gggggg");
		final EditText tax_name;
		final EditText tax_value;
		Button save, cancel;

		LayoutInflater mInflater = LayoutInflater.from(SettingsActivity.this);
		View layout = mInflater.inflate(R.layout.add_tax_details, null);
		tax_name = (EditText) layout.findViewById(R.id.tax_name_edit);
		tax_name.setText(string);
		tax_value = (EditText) layout.findViewById(R.id.rate_edit);
		// tax_value.setInputType(InputType.TYPE_CLASS_TEXT);
		tax_value.setText(tag);

		save = (Button) layout.findViewById(R.id.tax_save);
		cancel = (Button) layout.findViewById(R.id.tax_cancel);
		// tax_value.setInputType(InputType.TYPE_CLASS_NUMBER);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (tax_name.getText().length() > 2
						&& tax_value.getText().length() > 0) {
					if (checkRate(tax_value.getText().toString())) {
						
						dbforloginlogoutWrite1.execSQL("update taxes set unique_id=\""
								+ Parameters.randomValue()
								+ "\", created_timestamp=\""
								+ Parameters.currentTime()
								+ "\", modified_timestamp=\""
								+ Parameters.currentTime()
								+ "\", server_local=\"" + "Local"
								+ "\", taxes_name=\""
								+ tax_name.getText().toString()
								+ "\", taxes_value=\""
								+ tax_value.getText().toString()
								+ "\" where taxes_name=\"" + string + "\"");
						Toast.makeText(SettingsActivity.this, "Saved", 1000)
								.show();
						listUpdatefortax();
						alertDialog1.dismiss();
						
						final String query1 = "update "+DatabaseForDemo.INVENTORY_TABLE+" set " +
								DatabaseForDemo.INVENTORY_TAXONE+" = REPLACE("+DatabaseForDemo.INVENTORY_TAXONE+", \""+string+"\", \""+tax_name.getText().toString()+"\") where " +
								DatabaseForDemo.INVENTORY_TAXONE+" LIKE \"%"+string+"%\"";
						
						final String query2 = "update "+DatabaseForDemo.DEPARTMENT_TABLE+" set " +
								DatabaseForDemo.TaxValForDept+" = REPLACE("+DatabaseForDemo.TaxValForDept+", \""+string+"\", \""+tax_name.getText().toString()+"\") where " +
								DatabaseForDemo.TaxValForDept+" LIKE \"%"+string+"%\"";
						
						dbforloginlogoutWrite1.execSQL(query1);
						System.out.println("query executed in local");
						dbforloginlogoutWrite1.execSQL(query2);
						System.out.println("query executed in local2");
						
						if(Parameters.OriginalUrl.equals("")){
							System.out.println("there is no server url");
						}else{
							if(Parameters.isNetworkAvailable(SettingsActivity.this)){
								new Thread(new Runnable() {
									@Override
									public void run() {
										
										try {
											JSONArray querystring = new JSONArray();
											querystring.put(0, query1);
											querystring.put(1, query2);
											dataval = querystring.toString();
											System.out.println("data val is:" + dataval);
										} catch (JSONException e1) {
											// TODO Auto-generated catch block
											e1.printStackTrace();
										}
										JsonPostMethod post = new JsonPostMethod();
										String response = post.postmethodfortaxediting(dataval);
										System.out.println("query executed in thread"+response);

										String servertiem = null;
										try {
											JSONObject obj = new JSONObject(response);
											JSONObject responseobj = obj.getJSONObject("response");
											servertiem = responseobj.getString("server-time");
											System.out.println("servertime is:"+ servertiem);
										} catch (JSONException e) {
											// TODO Auto-generated catch
											// block
											e.printStackTrace();
										} catch (Exception e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}
										
										try{
										String select = "select *from "
												+ DatabaseForDemo.MISCELLANEOUS_TABLE;
										Cursor cursorz013 = dbforloginlogoutRead1.rawQuery(select,
												null);
										
										if (cursorz013.getCount() > 0) {
											dbforloginlogoutRead1.execSQL("update "
													+ DatabaseForDemo.MISCELLANEOUS_TABLE
													+ " set "
													+ DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL
													+ "=\"" + servertiem
													+ "\"");
											
										} else {
											
											ContentValues contentValues1 = new ContentValues();
											contentValues1
													.put(DatabaseForDemo.MISCEL_STORE,
															"store1");
											contentValues1
													.put(DatabaseForDemo.MISCEL_PAGEURL,
															"");
											contentValues1
													.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL,
															Parameters
																	.currentTime());
											contentValues1
													.put(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL,
															Parameters
																	.currentTime());
											dbforloginlogoutRead1.insert(
													DatabaseForDemo.MISCELLANEOUS_TABLE,
													null, contentValues1);
										}
										}catch(SQLException e){
											e.getLocalizedMessage();
										}
									}
								}).start();
								
							}else{
								
								ContentValues contentValues1 = new ContentValues();
								contentValues1.put(
										DatabaseForDemo.QUERY_TYPE,
										"edit");
								contentValues1.put(
										DatabaseForDemo.PENDING_USER_ID,
										Parameters.userid);
								contentValues1.put(
										DatabaseForDemo.PAGE_URL,
										"execute.php");
								contentValues1.put(
										DatabaseForDemo.TABLE_NAME_PENDING,"");
								contentValues1
										.put(DatabaseForDemo.CURRENT_TIME_PENDING,
												Parameters.currentTime());
								contentValues1
										.put(DatabaseForDemo.PARAMETERS,
												dataval);
								dbforloginlogoutRead1.insert(
										DatabaseForDemo.PENDING_QUERIES_TABLE,
										null, contentValues1);
								dataval = "";
						}
						}
					} else {
						Toast.makeText(SettingsActivity.this,
								"Enter float or integer value", 1000).show();
					}
				} else {
					Toast.makeText(SettingsActivity.this, "Enter Data", 1000)
							.show();
				}

			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				alertDialog1.dismiss();
			}
		});
		alertDialog1.setView(layout);

		// Showing Alert Message
		alertDialog1.show();
		}catch(Exception e){
			Log.e("onEditClicked","g " +e.getLocalizedMessage());
			}
	}

	@SuppressWarnings("deprecation")
	@Override
	public void onDeleteClicked(View v, final String string) {
		// TODO Auto-generated method stub
		try{
		final AlertDialog alertDialog1 = new AlertDialog.Builder(
				SettingsActivity.this).create();
		LayoutInflater mInflater = LayoutInflater.from(SettingsActivity.this);
		View layout = mInflater.inflate(R.layout.delete_popup, null);
		Button ok = (Button) layout.findViewById(R.id.ok);
		Button cancel = (Button) layout.findViewById(R.id.cancel);

		alertDialog1.setTitle("Delete");

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String where = DatabaseForDemo.TAX_NAME + "=?";
				dbforloginlogoutWrite1.delete(DatabaseForDemo.TAX_TABLE, where,
						new String[] { string });

				Toast.makeText(SettingsActivity.this, "deleted", 1000).show();
				listUpdatefortax();
				alertDialog1.dismiss();

			}
		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				alertDialog1.dismiss();
			}
		});
		alertDialog1.setView(layout);
		alertDialog1.show();
		}catch(Exception e){
			Log.e("onDeleteClicked", "g " +e.getLocalizedMessage());
			}
	}

	private boolean checkRate(String rate) {
		return USER_PATTERN.matcher(rate).matches();
	}

	void setitemList(String name_value) {
try{
		System.out.println("name_value is:" + name_value);
		itemsdp.clear();
		itemsNo.clear();
		itemchecking.clear();
		list1.removeAllViews();
		
		String selectQuery = "SELECT  * FROM "
				+ DatabaseForDemo.INVENTORY_TABLE + " WHERE "
				+ DatabaseForDemo.INVENTORY_DEPARTMENT + "=\"" + name_value
				+ "\" ORDER BY "+DatabaseForDemo.INVENTORY_ITEM_NAME+";";
		Cursor cursory201 =dbforloginlogoutRead1
				.rawQuery(selectQuery, null);
		// startManagingCursor(cursory201);
		if (cursory201 != null) {
			if (cursory201.moveToFirst()) {
				do {
					String catname = cursory201
							.getString(cursory201
									.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NAME));
					// System.out.println(catname);
					String catid = cursory201.getString(cursory201
							.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NO));
					// System.out.println(catid);
					String itemcheck = cursory201.getString(cursory201
							.getColumnIndex(DatabaseForDemo.CHECKED_VALUE));
					// System.out.println(itemcheck);
					itemsdp.add(catname);
					itemsNo.add(catid);
					itemchecking.add(itemcheck);
				} while (cursory201.moveToNext());
			}
		}
		cursory201.close();
		for (int p = 0; p < itemsdp.size(); p++) {
			final LinearLayout roww = new LinearLayout(SettingsActivity.this);
			roww.setOrientation(LinearLayout.HORIZONTAL);
			roww.setLayoutParams(new LayoutParams(
					android.view.ViewGroup.LayoutParams.FILL_PARENT,
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			roww.setPadding(5, 0, 0, 0);
			checkbtnn = new CheckBox(SettingsActivity.this);
			tvv = new TextView(SettingsActivity.this);
			tvv.setText(itemsdp.get(p));
			checkbtnn.setTag("" + itemsdp.get(p));
			tvv.setTag("" + itemsdp.get(p));
			tvv.setTextSize(18);
			// tvv.setTextColor(Color.BLACK);
			boolean val1 = Boolean.valueOf(itemchecking.get(p));
			checkbtnn.setChecked(val1);
			checkbtnn.setId(1000 + p);
			LinearLayout.LayoutParams lp0 = new LinearLayout.LayoutParams(
					android.view.ViewGroup.LayoutParams.MATCH_PARENT,
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			LayoutParams lp10 = new LayoutParams(170,
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			roww.addView(tvv, lp10);
			roww.addView(checkbtnn, lp0);
			list1.addView(roww);

		}
}catch(Exception e){
	Log.e("setitemList",""+e.getLocalizedMessage());
	}
	}

	@Override
	public void onDeleteClickedforprinter(View v, final String id) {
		// TODO Auto-generated method stub
		try{
		final AlertDialog alertDialog1 = new AlertDialog.Builder(
				SettingsActivity.this).create();
		LayoutInflater mInflater = LayoutInflater.from(SettingsActivity.this);
		View layout = mInflater.inflate(R.layout.delete_popup, null);
		Button ok = (Button) layout.findViewById(R.id.ok);
		Button cancel = (Button) layout.findViewById(R.id.cancel);

		alertDialog1.setTitle("Delete");

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				String name = "";
				
				String where = DatabaseForDemo.PRINTER_ID + "=?";
				dbforloginlogoutWrite1.delete(DatabaseForDemo.PRINTER_TABLE, where,
						new String[] { id });

				Toast.makeText(SettingsActivity.this,
						"Store deleted successfully", Toast.LENGTH_SHORT)
						.show();
				id_data.clear();
				desc_data.clear();
				total_id_data.clear();
				total_desc_data.clear();
				listupdateforprinter();
				alertDialog1.dismiss();

			}

		});
		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				alertDialog1.dismiss();
			}
		});
		alertDialog1.setView(layout);
		alertDialog1.show();
		}catch(Exception e){
			Log.e("onDeleteClickedforprinter","1 "+e.getLocalizedMessage());
			}
	}

	@Override
	public void onViewClickedforprinter(View v, final String id) {
		// TODO Auto-generated method stub
try{
		final AlertDialog alertDialog1 = new AlertDialog.Builder(
				SettingsActivity.this).create();
		LayoutInflater mInflater = LayoutInflater.from(SettingsActivity.this);
		View layout = mInflater.inflate(R.layout.view_details, null);
		ListView listview = (ListView) layout.findViewById(R.id.listView1);
		Button head = (Button) layout.findViewById(R.id.button2);
		head.setText("Printer_Details");
		ArrayList<HashMap<String, String>> listData = new ArrayList<HashMap<String, String>>();
		
		Cursor cursory202 = dbforloginlogoutWrite1.rawQuery("select * from "
				+ DatabaseForDemo.PRINTER_TABLE + " where "
				+ DatabaseForDemo.PRINTER_ID + "=\"" + id + "\"", null);
		// startManagingCursor(cursory202);
		if (cursory202 != null) {
			int count = cursory202.getColumnCount();
			if (cursory202.moveToFirst()) {
				do {
					for (int i = 1; i < count; i++) {
						HashMap<String, String> map = new HashMap<String, String>();
						if(!cursory202.getColumnName(i).toString().equals("fontsize")){
						map.put("key", cursory202.getColumnName(i));
						map.put("value", cursory202.getString(i));
						listData.add(map);
						}
					}
				} while (cursory202.moveToNext());
			}
			Log.v("adapter", " " + listData.size());
			DetailsShowingArrayAdapter adapter1 = new DetailsShowingArrayAdapter(
					SettingsActivity.this, listData, R.layout.list_item,
					new String[] { "key", "value" }, new int[] {
							R.id.textView_id, R.id.textView_name });
			Log.v("adapter", "1 " + adapter1.getCount());
			Log.v("list", "1 " + listview);
			// listview.addHeaderView(header);
			listview.setAdapter(adapter1);

		} else {
			Toast.makeText(getApplicationContext(), "This is no data",
					Toast.LENGTH_SHORT).show();
		}
		cursory202.close();
		alertDialog1.setView(layout);
		alertDialog1.show();
}catch(Exception e){
	Log.e("onViewClickedforprinter","1 "+e.getLocalizedMessage());
	}
	}

	@Override
	public void onEditClickedforprinter(View v, final String id) {
		// TODO Auto-generated method stub
try{
		final int SIZEWIDTH_MAX = 8;
		final int SIZEHEIGHT_MAX = 8;
		final EditText prtname, texttext, textfeed, textline, textX, printerid;
		final AutoCompleteTextView prtip;
		final Spinner spinnername, spinneralign, spinnerlang, spinnerW, spinnerH;
		final ToggleButton togglebold;
		final ToggleButton toggleunder;
		final ArrayList<String> printVal = new ArrayList<String>();
		boolean exitActivity = false;
		String[] fontsiz = { "Small", "Medium", "Large" };
		final AlertDialog alertDialog1 = new AlertDialog.Builder(
				SettingsActivity.this,
				android.R.style.Theme_Translucent_NoTitleBar).create();
		LayoutInflater mInflater = LayoutInflater.from(SettingsActivity.this);
		View layout = mInflater.inflate(R.layout.text, null);
		printertype_spinner = (Spinner) layout
				.findViewById(R.id.spinner_printer_type);
		final ArrayAdapter<String> typeOfPrinter = new ArrayAdapter<String>(
				SettingsActivity.this, android.R.layout.simple_spinner_item);
		typeOfPrinter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		typeOfPrinter.add("EPSON");
		typeOfPrinter.add("STAR");
		printertype_spinner.setAdapter(typeOfPrinter);
		final RadioGroup typeofprinting=(RadioGroup) layout.findViewById(R.id.typeofprinting);
		spinnername = (Spinner) layout.findViewById(R.id.spinner_printer);
		final ArrayAdapter<String> adaptername = new ArrayAdapter<String>(
				SettingsActivity.this, android.R.layout.simple_spinner_item);
		adaptername
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		printertype_spinner
				.setOnItemSelectedListener(new OnItemSelectedListener() {
					@Override
					public void onItemSelected(AdapterView<?> parentView,
							View selectedItemView, int position, long id) {
						Log.v("janu", "1 " + typeOfPrinter.getItem(position));
						if (typeOfPrinter.getItem(position).equals("EPSON")) {
							adaptername.clear();
							typeofprinting.setVisibility(View.GONE);
							adaptername
									.add(getString(R.string.printername_p60));
							adaptername
									.add(getString(R.string.printername_p60ii));
							adaptername
									.add(getString(R.string.printername_t20));
							adaptername
									.add(getString(R.string.printername_t70));
							adaptername
									.add(getString(R.string.printername_t70ii));
							adaptername
									.add(getString(R.string.printername_t81ii));
							adaptername
									.add(getString(R.string.printername_t82));
							adaptername
									.add(getString(R.string.printername_t82ii));
							adaptername
									.add(getString(R.string.printername_t88v));
							adaptername
									.add(getString(R.string.printername_u220));
						} else {
							typeofprinting.setVisibility(View.VISIBLE);
							adaptername.clear();
							adaptername
									.add(getString(R.string.printername_star1));
							adaptername
									.add(getString(R.string.printername_star2));
							adaptername
									.add(getString(R.string.printername_star3));
							adaptername
									.add(getString(R.string.printername_star4));
							adaptername
									.add(getString(R.string.printername_star5));
							adaptername
									.add(getString(R.string.printername_star6));
							adaptername
									.add(getString(R.string.printername_star7));
							adaptername
									.add(getString(R.string.printername_star8));
							adaptername
									.add(getString(R.string.printername_star9));
							adaptername
									.add(getString(R.string.printername_star10));
							adaptername
									.add(getString(R.string.printername_star11));
							adaptername
									.add(getString(R.string.printername_star12));
							adaptername
									.add(getString(R.string.printername_star13));
						}
					}

					@Override
					public void onNothingSelected(AdapterView<?> parentView) {
						// your code here
						Log.v("janu", "seeeeeeeeeeeeeeeeeetha");
					}

				});
		spinnername.setAdapter(adaptername);

		

		String selectQuery = "SELECT  * FROM " + DatabaseForDemo.PRINTER_TABLE
				+ " where " + DatabaseForDemo.PRINTER_ID + "=\"" + id + "\"";
		Cursor cursory203 = dbforloginlogoutWrite1.rawQuery(selectQuery, null);
		
		if (cursory203 != null) {
			int count = cursory203.getColumnCount();
			if (cursory203.moveToFirst()) {

				for (int i = 0; i < count; i++) {
					String value = cursory203.getString(i);
					printVal.add(value);
					Log.v(" " + i," "+ printVal.get(i));
				}

			}

		}
		cursory203.close();

		// init font list
		/*final Spinner spinnerfont = (Spinner) layout
				.findViewById(R.id.spinner_font);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				SettingsActivity.this, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		Log.v("ccccccccsssss", "gt");
		adapter.add(getString(R.string.font_a));
		adapter.add(getString(R.string.font_b));
		adapter.add(getString(R.string.font_c));
		spinnerfont.setAdapter(adapter);
		Log.v("ccccccsssssssssssscc", "gt");
		if (printVal.size() > 2) {
			spinnerfont.setSelection(Integer.parseInt(printVal.get(2)));
			Log.v("cccc,,,ggggggggggg,,cccc", "gt");
		}*/
		// init align list
		final RadioButton	bluetoothradio=(RadioButton) layout.findViewById(R.id.bluetoothradio);
		final RadioButton	wifiradio=(RadioButton) layout.findViewById(R.id.wifiradio);
		if (printVal.size() > 2) {
		if(printVal.get(2).equals("BT:")){
			bluetoothradio.setChecked(true);
			wifiradio.setChecked(false);
		}
		}
		prtip = (AutoCompleteTextView) layout.findViewById(R.id.ip);
		prtip.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(prtip.getWindowToken(), 0);
				}
				return false;
			}
		});
		spinneralign = (Spinner) layout.findViewById(R.id.spinner_align);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(SettingsActivity.this,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter.add(getString(R.string.align_left));
		adapter.add(getString(R.string.align_center));
		adapter.add(getString(R.string.align_right));
		spinneralign.setAdapter(adapter);
		Log.v("cccccccc", "gt");
		if (printVal.size() > 2)
			spinneralign.setSelection(Integer.parseInt(printVal.get(3)));
		// init language list
		spinnerlang = (Spinner) layout.findViewById(R.id.spinner_language);
		adapter = new ArrayAdapter<String>(SettingsActivity.this,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		adapter.add(getString(R.string.language_ank));
		adapter.add(getString(R.string.language_japanese));
		adapter.add(getString(R.string.language_simplified_chinese));
		adapter.add(getString(R.string.language_traditional_chinese));
		adapter.add(getString(R.string.language_korean));
		adapter.add(getString(R.string.language_thai));
		adapter.add(getString(R.string.language_vietnamese));
		spinnerlang.setAdapter(adapter);
		if (printVal.size() > 4)
			spinnerlang.setSelection(Integer.parseInt(printVal.get(5)));
		// init size list
		spinnerW = (Spinner) layout.findViewById(R.id.spinner_size_width);
		adapter = new ArrayAdapter<String>(SettingsActivity.this,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		for (int i = 1; i <= SIZEWIDTH_MAX; i++) {
			adapter.add(String.format("%d", i));
		}
		spinnerW.setAdapter(adapter);
		if (printVal.size() > 5)
			spinnerW.setSelection(Integer.parseInt(printVal.get(6)) - 1);

		spinnerH = (Spinner) layout.findViewById(R.id.spinner_size_height);
		adapter = new ArrayAdapter<String>(SettingsActivity.this,
				android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		for (int i = 1; i <= SIZEHEIGHT_MAX; i++) {
			adapter.add(String.format("%d", i));
		}
		spinnerH.setAdapter(adapter);
		if (printVal.size() > 6)
			spinnerH.setSelection(Integer.parseInt(printVal.get(7)) - 1);
		// default setting
		texttext = (EditText) layout.findViewById(R.id.editText_text);
		String value = getString(R.string.text_edit_text);
		value = value.replaceAll("\\*", " ");
		texttext.setText(value);
		if (printVal.size() > 0)
			texttext.setText(printVal.get(1));
		textline = (EditText) layout.findViewById(R.id.editText_linespace);
		textline.setText("30");
		if (printVal.size() > 3)
			textline.setText(printVal.get(4));
		textX = (EditText) layout.findViewById(R.id.editText_xposition);
		textX.setText("0");
		if (printVal.size() > 9)
			textX.setText(printVal.get(10));
		textfeed = (EditText) layout.findViewById(R.id.editText_feedunit);
		textfeed.setText("30");
		if (printVal.size() > 10)
			textfeed.setText(printVal.get(11));
		togglebold = (ToggleButton) layout
				.findViewById(R.id.toggleButton_style_bold);
		if (printVal.size() > 7) {
			if (printVal.get(8).equals("1"))
				togglebold.setChecked(true);
		}
		toggleunder = (ToggleButton) layout
				.findViewById(R.id.toggleButton_style_underline);
		printerid = (EditText) layout.findViewById(R.id.name);
		if (printVal.size() > 8) {
			if (printVal.get(9).equals("1"))
				toggleunder.setChecked(true);
			if (printVal.get(15).equals("STAR")) {
				if (typeOfPrinter.getPosition(printVal.get(15)) > -1)
					printertype_spinner.setSelection(typeOfPrinter
							.getPosition(printVal.get(15)));
				typeofprinting.setVisibility(View.VISIBLE);
				adaptername.clear();
				adaptername.add(getString(R.string.printername_star1));
				adaptername.add(getString(R.string.printername_star2));
				adaptername.add(getString(R.string.printername_star3));
				adaptername.add(getString(R.string.printername_star4));
				adaptername.add(getString(R.string.printername_star5));
				adaptername.add(getString(R.string.printername_star6));
				adaptername.add(getString(R.string.printername_star7));
				adaptername.add(getString(R.string.printername_star8));
				adaptername.add(getString(R.string.printername_star9));
				adaptername.add(getString(R.string.printername_star10));
				adaptername.add(getString(R.string.printername_star11));
				adaptername.add(getString(R.string.printername_star12));
				adaptername.add(getString(R.string.printername_star13));
				
			} else {
				typeofprinting.setVisibility(View.GONE);
				if (typeOfPrinter.getPosition(printVal.get(15)) > -1)
					printertype_spinner.setSelection(typeOfPrinter
							.getPosition(printVal.get(15)));
				adaptername.clear();
				adaptername.add(getString(R.string.printername_p60));
				adaptername.add(getString(R.string.printername_p60ii));
				adaptername.add(getString(R.string.printername_t20));
				adaptername.add(getString(R.string.printername_t70));
				adaptername.add(getString(R.string.printername_t70ii));
				adaptername.add(getString(R.string.printername_t81ii));
				adaptername.add(getString(R.string.printername_t82));
				adaptername.add(getString(R.string.printername_t82ii));
				adaptername.add(getString(R.string.printername_t88v));
				adaptername.add(getString(R.string.printername_u220));
			}
			spinnername.setAdapter(adaptername);
			if (adaptername.getPosition(printVal.get(12)) > -1)
				spinnername.setSelection(adaptername.getPosition(printVal
						.get(12)));
			prtip.setText(printVal.get(13));
			printerid.setText(printVal.get(14));

			Log.v("ccccdddddbbbbcccc", "gt");
		}
		Log.v("cccccccc", "rrrrrrrrgt");
		// Registration ClickListener
		Button button = (Button) layout.findViewById(R.id.button_print);
		button.setText("Update");
		Button viewcat = (Button) layout.findViewById(R.id.viewcat);
		Button addcat = (Button) layout.findViewById(R.id.addcat);
		viewcat.setVisibility(View.GONE);
		addcat.setVisibility(View.GONE);
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				String valuee="TCP:";
				if(bluetoothradio.isChecked()){
					valuee="BT:";
				}
				boolean bharatham = true;
				
				if (printerid.getText().toString().trim().length() > 2) {
					if (printVal.get(14).equals(
							printerid.getText().toString().trim())) {
					} else {
						if (Parameters.isTableExists(dbforloginlogoutWrite1,
								DatabaseForDemo.PRINTER_TABLE)) {
							String selectQuery = "SELECT  * FROM "
									+ DatabaseForDemo.PRINTER_TABLE + " where "
									+ DatabaseForDemo.PRINTER_ID + "=\""
									+ printerid.getText().toString().trim()
									+ "\"";
							Cursor cursory204 = dbforloginlogoutWrite1.rawQuery(
									selectQuery, null);
							if (cursory204 != null) {
								int count = cursory204.getColumnCount();
								if (cursory204.getCount() > 0) {
									Toast.makeText(getApplicationContext(),
											"Id All Ready Exists Enter New Id",
											2000).show();
									bharatham = false;
								}
							}
							cursory204.close();
						}
					}
					if (bharatham) {
						if (prtip.getText().length() > 2) {

							/*
							 * printer = new Print(getApplicationContext());
							 * boolean valuu; try{ int deviceType =
							 * Print.DEVTYPE_TCP;
							 * printer.openPrinter(deviceType,
							 * prtip.getText().toString(), Print.TRUE, 1000);
							 * valuu=true; }catch(Exception e){
							 * ShowMsg.showException(e, "openPrinter" ,
							 * SettingsActivity.this);
							 * Log.v("dfffd","fgffgfgfgf"); valuu=false; }
							 * closePrinter(); if(valuu) {
							 */
							String spinneralign1, spinnerlang1, spinnerW1, spinnerH1, spinnerfont1, togglebold1, toggleunder1;

							/*switch (spinnerfont.getSelectedItemPosition()) {
							case 1:
								spinnerfont1 = "" + Builder.FONT_B;
								break;
							case 2:
								spinnerfont1 = "" + Builder.FONT_C;
								break;
							case 0:
							default:
								spinnerfont1 = "" + Builder.FONT_A;
								break;
							}*/

							switch (spinneralign.getSelectedItemPosition()) {
							case 1:
								spinneralign1 = "" + Builder.ALIGN_CENTER;
								break;
							case 2:
								spinneralign1 = "" + Builder.ALIGN_RIGHT;
								break;
							case 0:
							default:
								spinneralign1 = "" + Builder.ALIGN_LEFT;
								break;
							}

							switch (spinnerlang.getSelectedItemPosition()) {
							case 1:
								spinnerlang1 = "" + Builder.LANG_JA;
								break;
							case 2:
								spinnerlang1 = "" + Builder.LANG_ZH_CN;
								break;
							case 3:
								spinnerlang1 = "" + Builder.LANG_ZH_TW;
								break;
							case 4:
								spinnerlang1 = "" + Builder.LANG_KO;
								break;
							case 5:
								spinnerlang1 = "" + Builder.LANG_TH;
								break;
							case 6:
								spinnerlang1 = "" + Builder.LANG_VI;
								break;
							default:
								spinnerlang1 = "" + Builder.LANG_EN;
								break;
							}

							spinnerW1 = ""
									+ (spinnerW.getSelectedItemPosition() + 1);

							spinnerH1 = ""
									+ (spinnerH.getSelectedItemPosition() + 1);

							if (togglebold.isChecked()) {
								togglebold1 = "" + Builder.TRUE;
							} else {
								togglebold1 = "" + Builder.FALSE;
							}

							if (toggleunder.isChecked()) {
								toggleunder1 = "" + Builder.TRUE;
							} else {
								toggleunder1 = "" + Builder.FALSE;
							}
							Log.v("cccc77777777cccc", "rrrrrrrrgt");
							dbforloginlogoutWrite1.execSQL("update "
									+ DatabaseForDemo.PRINTER_TABLE
									+ " set "
									+ DatabaseForDemo.PRINTER_TEXT
									+ "=\""
									+ " "+texttext.getText().toString().trim()
									+ "\", "
									+ DatabaseForDemo.PRINTER_LANGUAGE
									+ "=\""
									+ spinnerlang1
									+ "\", "
									+ DatabaseForDemo.PRINTER_UNIT
									+ "=\""
									+ textfeed.getText().toString().trim()
									+ "\", "
									+ DatabaseForDemo.PRINTER_SPACING
									+ "=\""
									+ textline.getText().toString().trim()
									+ "\", "
									+ DatabaseForDemo.PRINTER_FONT
									+ "=\""
									+ valuee
									+ "\", "
									+ DatabaseForDemo.PRINTER_ALIGN
									+ "=\""
									+ spinneralign1
									+ "\", "
									+ DatabaseForDemo.PRINTER_WSIZE
									+ "=\""
									+ spinnerW1
									+ "\", "
									+ DatabaseForDemo.PRINTER_HSIZE
									+ "=\""
									+ spinnerH1
									+ "\", "
									+ DatabaseForDemo.PRINTER_BOLD
									+ "=\""
									+ togglebold1
									+ "\", "
									+ DatabaseForDemo.PRINTER_UNDERLINE
									+ "=\""
									+ toggleunder1
									+ "\", "
									+ DatabaseForDemo.PRINTER_XPOSITION
									+ "=\""
									+ textX.getText().toString().trim()
									+ "\", "
									+ DatabaseForDemo.PRINTER_TYPE
									+ "=\""
									+ printertype_spinner.getSelectedItem()
											.toString().trim()
									+ "\", "
									+ DatabaseForDemo.PRINTER_NAME
									+ "=\""
									+ spinnername.getSelectedItem().toString()
											.trim() + "\", "
									+ DatabaseForDemo.PRINTER_IP + "=\""
									+ prtip.getText().toString().trim() + "\", "
									+ DatabaseForDemo.PRINTER_ID + "=\""
									+ printerid.getText().toString().trim()
									+ "\" where " + DatabaseForDemo.PRINTER_ID
									+ "=\"" + id + "\"");

							Toast.makeText(SettingsActivity.this, "Updated",
									1000).show();
							listupdateforprinter();
							alertDialog1.dismiss();
							// hide keyboard
							// this.getWindow().setSoftInputMode(LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
							// }
						} else {
							Toast.makeText(SettingsActivity.this,
									"Enter IP Address", 1000).show();
						}
					}
				} else {
					Toast.makeText(SettingsActivity.this, "Enter Id", 1000)
							.show();
				}

			}
		});
		alertDialog1.setView(layout);
		alertDialog1.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		alertDialog1.show();
}catch(Exception e){
	Log.e("onEditClickedforprinter","1 "+e.getLocalizedMessage());
	}
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
	}

	public static void closePrinter() {
		try {
			printer.closePrinter();
			printer = null;
		} catch (Exception e) {
			printer = null;
		}
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		sqlDB.close();
		if (!exitActivity) {
			closePrinter();
		}
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			setResult(0, null);
			exitActivity = true;
		}

		return super.onKeyDown(keyCode, event);
	}

	private void printText(String valuee) {
		try{
		TextView text = (TextView) findViewById(R.id.editText_text);
		TextView tename = (TextView) findViewById(R.id.name);
		
		if (tename.getText().toString().trim().isEmpty()) {
			Toast.makeText(getApplicationContext(), "Enter Id", 2000).show();
			return;
		} else {

			if (Parameters.isTableExists(dbforloginlogoutWrite1,
					DatabaseForDemo.PRINTER_TABLE)) {
				String selectQuery = "SELECT  * FROM "
						+ DatabaseForDemo.PRINTER_TABLE + " where "
						+ DatabaseForDemo.PRINTER_ID + "=\""
						+ tename.getText().toString().trim() + "\"";
				Cursor mCursorx301 = dbforloginlogoutWrite1.rawQuery(selectQuery, null);
			
				if (mCursorx301 != null) {
					int count = mCursorx301.getColumnCount();
					if (mCursorx301.getCount() > 0) {
						Toast.makeText(getApplicationContext(),
								"Id All Ready Exists Enter New Id", 2000)
								.show();
						return;
					}
				}
				mCursorx301.close();
			}
		}
		if (text.getText().toString().isEmpty()) {
			Toast.makeText(getApplicationContext(), "Enter IP Address", 2000)
					.show();
			return;
		}
		/*
		 * printer = new Print(getApplicationContext()); boolean valuu; try{ int
		 * deviceType = Print.DEVTYPE_TCP; printer = new
		 * Print(getApplicationContext()); printer.openPrinter(deviceType,
		 * prtip.getText().toString(), Print.TRUE, 1000); valuu=true;
		 * }catch(Exception e){ ShowMsg.showException(e, "openPrinter" ,
		 * SettingsActivity.this); Log.v("dfffd","fgffgfgfgf"); valuu=false; }
		 * closePrinter(); if(valuu) {
		 */
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseForDemo.PRINTER_TEXT, "" + getBuilderText());
		contentValues.put(DatabaseForDemo.PRINTER_LANGUAGE, ""
				+ getBuilderLanguage());
		contentValues.put(DatabaseForDemo.PRINTER_UNIT, ""
				+ getBuilderFeedUnit());
		contentValues.put(DatabaseForDemo.PRINTER_SPACING, ""
				+ getBuilderLineSpace());
	
	if(printertype_spinner.getSelectedItem().toString().equals("EPSON")){
		contentValues.put(DatabaseForDemo.PRINTER_FONT, "d");
		}else{
			contentValues.put(DatabaseForDemo.PRINTER_FONT, valuee);
		}
		contentValues
				.put(DatabaseForDemo.PRINTER_ALIGN, "" + getBuilderAlign());
		contentValues
				.put(DatabaseForDemo.PRINTER_WSIZE, "" + getBuilderSizeW());
		contentValues
				.put(DatabaseForDemo.PRINTER_HSIZE, "" + getBuilderSizeH());
		contentValues.put(DatabaseForDemo.PRINTER_BOLD, ""
				+ getBuilderStyleBold());
		contentValues.put(DatabaseForDemo.PRINTER_UNDERLINE, ""
				+ getBuilderStyleUnderline());
		contentValues.put(DatabaseForDemo.PRINTER_XPOSITION, ""
				+ getBuilderXPosition());
		contentValues.put(DatabaseForDemo.PRINTER_TYPE, printertype_spinner
				.getSelectedItem().toString().trim());
		
		contentValues.put(DatabaseForDemo.PRINTER_NAME, ""
				+ spinnername.getSelectedItem().toString());
		contentValues.put(DatabaseForDemo.PRINTER_IP, ""
				+ prtip.getText().toString().trim());
		contentValues.put(DatabaseForDemo.PRINTER_ID, printerid.getText()
				.toString().trim());
		Log.i("select", " " + contentValues);
		dbforloginlogoutWrite1.insert(DatabaseForDemo.PRINTER_TABLE, null,
				contentValues);
		Log.v("getBuilderText()", "" + getBuilderText());
		Log.v("+getBuilderLanguage()", "" + getBuilderLanguage());
		Log.v("getBuilderFeedUnit()", "" + getBuilderFeedUnit());
		Log.v("+getBuilderLineSpace()", "" + getBuilderLineSpace());
		Log.v("+getBuilderAlign()", "" + getBuilderAlign());
		Log.v("+getBuilderSizeW()", "" + getBuilderSizeW());
		Log.v("+getBuilderSizeH()", "" + getBuilderSizeH());
		Log.v("+getBuilderStyleBold()", "" + getBuilderStyleBold());
		Log.v("+getBuilderStyleUnderline()", "" + getBuilderStyleUnderline());
		Log.v("+getBuilderXPosition()", "" + getBuilderXPosition());

		Toast.makeText(SettingsActivity.this, "Saved", 1000).show();
		contentValues.clear();
		}catch(Exception e){
			Log.e("printText","1 "+e.getLocalizedMessage());
			}
	}

	/*private int getBuilderFont() {
		Spinner spinner = (Spinner) findViewById(R.id.spinner_font);
		switch (spinner.getSelectedItemPosition()) {
		case 1:
			return Builder.FONT_B;
		case 2:
			return Builder.FONT_C;
		case 0:
		default:
			return Builder.FONT_A;
		}
	}*/

	private int getBuilderAlign() {
		Spinner spinner = (Spinner) findViewById(R.id.spinner_align);
		switch (spinner.getSelectedItemPosition()) {
		case 1:
			return Builder.ALIGN_CENTER;
		case 2:
			return Builder.ALIGN_RIGHT;
		case 0:
		default:
			return Builder.ALIGN_LEFT;
		}
	}

	private int getBuilderLineSpace() {
		TextView text = (TextView) findViewById(R.id.editText_linespace);
		try {
			return Integer.parseInt(text.getText().toString());
		} catch (Exception e) {
			return 0;
		}
	}

	private int getBuilderLanguage() {
		Spinner spinner = (Spinner) findViewById(R.id.spinner_language);
		switch (spinner.getSelectedItemPosition()) {
		case 1:
			return Builder.LANG_JA;
		case 2:
			return Builder.LANG_ZH_CN;
		case 3:
			return Builder.LANG_ZH_TW;
		case 4:
			return Builder.LANG_KO;
		case 5:
			return Builder.LANG_TH;
		case 6:
			return Builder.LANG_VI;
		case 0:
		default:
			return Builder.LANG_EN;
		}
	}

	public int getBuilderSizeW() {
		Spinner spinner = (Spinner) findViewById(R.id.spinner_size_width);
		return spinner.getSelectedItemPosition() + 1;
	}

	public int getBuilderSizeH() {
		Spinner spinner = (Spinner) findViewById(R.id.spinner_size_height);
		return spinner.getSelectedItemPosition() + 1;
	}

	public int getBuilderStyleBold() {
		ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton_style_bold);
		if (toggle.isChecked()) {
			return Builder.TRUE;
		} else {
			return Builder.FALSE;
		}
	}

	public int getBuilderStyleUnderline() {
		ToggleButton toggle = (ToggleButton) findViewById(R.id.toggleButton_style_underline);
		if (toggle.isChecked()) {
			return Builder.TRUE;
		} else {
			return Builder.FALSE;
		}
	}

	public int getBuilderXPosition() {
		TextView text = (TextView) findViewById(R.id.editText_xposition);
		try {
			return Integer.parseInt(text.getText().toString());
		} catch (Exception e) {
			return 0;
		}
	}

	public String getBuilderText() {
		TextView text = (TextView) findViewById(R.id.editText_text);
		return " "+text.getText().toString();
	}

	public int getBuilderFeedUnit() {
		TextView text = (TextView) findViewById(R.id.editText_feedunit);
		try {
			return Integer.parseInt(text.getText().toString());
		} catch (Exception e) {
			return 0;
		}
	}

	@Override
	public void onStatusChangeEvent(final String deviceName, final int status) {
		runOnUiThread(new Runnable() {
			@Override
			public synchronized void run() {
				ShowMsg.showStatusChangeEvent(deviceName, status,
						SettingsActivity.this);
			}
		});
	}

	@Override
	public void onBatteryStatusChangeEvent(final String deviceName,
			final int battery) {
		runOnUiThread(new Runnable() {
			@Override
			public synchronized void run() {
				ShowMsg.showBatteryStatusChangeEvent(deviceName, battery,
						SettingsActivity.this);
			}
		});
	}

	private int getIndex(Spinner spinner, String myString) {

		int index = 0;

		for (int i = 0; i < spinner.getCount(); i++) {
			if (spinner.getItemAtPosition(i).equals(myString)) {
				index = i;
			}
		}
		return index;
	}
@Override
protected void onResume() {
	// TODO Auto-generated method stub
	Parameters.printerContext=SettingsActivity.this;
	super.onResume();
}
void sendToServer(final String tablename, final String datavalforedit ,final String update){
	
try{
	if(Parameters.OriginalUrl.equals("")){
		System.out.println("there is no server url val");
	}else{
	boolean isnet = Parameters
			.isNetworkAvailable(getApplicationContext());
	if (isnet) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				JsonPostMethod jsonpost = new JsonPostMethod();
				String response = jsonpost.postmethodfordirect("admin",
						"abcdefg", tablename ,
						Parameters.currentTime(),
						Parameters.currentTime(), datavalforedit, update);
				System.out.println("response test is:" + response);
				String servertiem = null;
				try {
					JSONObject obj = new JSONObject(response);
					JSONObject responseobj = obj
							.getJSONObject("response");
					servertiem = responseobj.getString("server-time");
					System.out.println("servertime is:" + servertiem);
					JSONArray array = obj
							.getJSONArray("insert-queries");
					System.out
							.println("array list length for insert is:"
									+ array.length());
					JSONArray array2 = obj
							.getJSONArray("delete-queries");
					System.out
							.println("array2 list length for delete is:"
									+ array2.length());
					for (int jj = 0, ii = 0; jj < array2.length()
							&& ii < array.length(); jj++, ii++) {
						String deletequerytemp = array2.getString(jj);
						String deletequery1 = deletequerytemp.replace(
								"'", "\"");
						String deletequery = deletequery1.replace(
								"\\\"", "'");
						System.out.println("delete query" + jj
								+ " is :" + deletequery);

						String insertquerytemp = array.getString(ii);
						String insertquery1 = insertquerytemp.replace(
								"'", "\"");
						String insertquery = insertquery1.replace(
								"\\\"", "'");
						System.out.println("delete query" + jj
								+ " is :" + insertquery);
try{
						dbforloginlogoutWrite1.execSQL(deletequery);
						dbforloginlogoutWrite1.execSQL(insertquery);
}catch(SQLException e){
	e.printStackTrace();
}
						System.out.println("queries executed" + ii);
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				String select = "select *from "
						+ DatabaseForDemo.MISCELLANEOUS_TABLE;
				Cursor mCursorx302 = dbforloginlogoutRead1.rawQuery(select, null);
				if (mCursorx302.getCount() > 0) {
					dbforloginlogoutWrite1.execSQL("update "
							+ DatabaseForDemo.MISCELLANEOUS_TABLE
							+ " set "
							+ DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL
							+ "=\"" + servertiem + "\"");
				} else {
					ContentValues contentValues1 = new ContentValues();
					contentValues1.put(DatabaseForDemo.MISCEL_STORE,
							"store1");
					contentValues1
							.put(DatabaseForDemo.MISCEL_PAGEURL,
									"");
					contentValues1.put(
							DatabaseForDemo.MISCEL_UPDATE_LOCAL,
							Parameters.currentTime());
					contentValues1.put(
							DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL,
							Parameters.currentTime());
					dbforloginlogoutWrite1.insert(DatabaseForDemo.MISCELLANEOUS_TABLE,
							null, contentValues1);
				}
				mCursorx302.close();
			}
		}).start();
	} else {
		ContentValues contentValues1 = new ContentValues();
		contentValues1.put(DatabaseForDemo.QUERY_TYPE, "insert");
		contentValues1.put(DatabaseForDemo.PENDING_USER_ID, Parameters.userid);
		contentValues1.put(DatabaseForDemo.PAGE_URL, "saveinfo.php");
		contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING,tablename);
		contentValues1.put(DatabaseForDemo.CURRENT_TIME_PENDING,Parameters.currentTime());
		contentValues1.put(DatabaseForDemo.PARAMETERS, datavalforedit);
		dbforloginlogoutWrite1.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null,
				contentValues1);
	}
	}
}catch(Exception e){
	Log.e("sendToServer", "1 "+e.getLocalizedMessage());
}
}
void sendDatatoServer(String response){
	String servertiem = null;
	try {
		JSONObject obj = new JSONObject(
				response);
		JSONObject responseobj = obj
				.getJSONObject("response");
		servertiem = responseobj
				.getString("server-time");
		String recevieddatafileds = responseobj
				.getString("received-datafields");
		System.out
				.println("received data fields is:"+recevieddatafileds);
	//	System.out
			//	.println("servertime is:"
				//		+ servertiem);
		JSONArray array = obj
				.getJSONArray("insert-queries");
	//	System.out.println("array list length for insert is:"
			//	+ array.length());
		JSONArray array2 = obj
				.getJSONArray("delete-queries");
	//	System.out.println("array2 list length for delete is:"
			//	+ array2.length());
		for (int jj = 0, ii = 0; jj < array2
				.length()
				&& ii < array
						.length(); jj++, ii++) {
			String deletequerytemp = array2
					.getString(jj);
			String deletequery1 = deletequerytemp
					.replace(
							"'",
							"\"");
			String deletequery = deletequery1
					.replace(
							"\\\"",
							"'");
		//	System.out
					//.println("delete query"
						//	+ jj
						//	+ " is :"
						//	+ deletequery);

			String insertquerytemp = array
					.getString(ii);
			String insertquery1 = insertquerytemp
					.replace(
							"'",
							"\"");
			String insertquery = insertquery1
					.replace(
							"\\\"",
							"'");
			//System.out
					//.println("delete query"
						//	+ jj
						//	+ " is :"
						//	+ insertquery);
try{
			dbforloginlogoutWrite1.execSQL(deletequery);
			dbforloginlogoutWrite1.execSQL(insertquery);
}catch(SQLException e){
	e.printStackTrace();
}
		//	System.out
				//	.println("queries executed"
						//	+ ii);

		}
	} catch (JSONException e) {
		// TODO Auto-generated
		// catch block
		e.printStackTrace();
	}

	String select = "select *from "
			+ DatabaseForDemo.MISCELLANEOUS_TABLE;
	Cursor cursorz07 = dbforloginlogoutWrite1
			.rawQuery(select,
					null);
	if(cursorz07!=null){
	if (cursorz07.getCount() > 0) {
		dbforloginlogoutWrite1.execSQL("update "
				+ DatabaseForDemo.MISCELLANEOUS_TABLE
				+ " set "
				+ DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL
				+ "=\""
				+ servertiem
				+ "\"");
	} else {
		ContentValues contentValues2 = new ContentValues();
		contentValues2
				.put(DatabaseForDemo.MISCEL_STORE,
						"store1");
		contentValues2
				.put(DatabaseForDemo.MISCEL_PAGEURL,
						"");
		contentValues2
				.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL,
						Parameters
								.currentTime());
		contentValues2
				.put(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL,
						Parameters
								.currentTime());
		dbforloginlogoutWrite1.insert(
				DatabaseForDemo.MISCELLANEOUS_TABLE,
				null,
				contentValues2);

	}
	}
	cursorz07.close();
	dataval = "";
}
public void pendingclear(){
	
	String selectQuerytotal = "SELECT  * FROM " + DatabaseForDemo.PENDING_QUERIES_TABLE+" where "+DatabaseForDemo.PENDING_USER_ID+"=\""
								+Parameters.userid+"\" ORDER BY "+DatabaseForDemo.CURRENT_TIME_PENDING+" ASC" ;
	Cursor cursortotalf = dbforloginlogoutRead1.rawQuery(selectQuerytotal, null);
	int count = cursortotalf.getCount();
	int offset = 0;
	for(; offset<count;){
	
	System.out.println("offset value is:"+offset);
	String selectQuery = "SELECT  * FROM " + DatabaseForDemo.PENDING_QUERIES_TABLE+" where "+DatabaseForDemo.PENDING_USER_ID+"=\""
							+Parameters.userid+"\" ORDER BY "+DatabaseForDemo.CURRENT_TIME_PENDING+" ASC limit 100 offset "+offset;
	if(count<100){
		offset = offset+count;
	}else{
		offset = offset+100;
		count = count - offset;
	}
	Cursor mCursofgnnr1 = dbforloginlogoutRead1.rawQuery(selectQuery, null);
	ArrayList<HashMap<String, String>> data = new ArrayList<HashMap<String, String>>();
	String querytype, userid = null, pageurl, tablename = null, time = null, parameter = null;
	if(mCursofgnnr1!=null){
		if(mCursofgnnr1.moveToFirst()){
			do{
				try{
				HashMap<String, String> map = new HashMap<String, String>();	
				querytype =  mCursofgnnr1.getString(mCursofgnnr1.getColumnIndex(DatabaseForDemo.QUERY_TYPE));
				map.put("querytype", querytype);
				userid = mCursofgnnr1.getString(mCursofgnnr1.getColumnIndex(DatabaseForDemo.PENDING_USER_ID));
				map.put("userid", userid);
				pageurl = mCursofgnnr1.getString(mCursofgnnr1.getColumnIndex(DatabaseForDemo.PAGE_URL));
				map.put("pageurl", pageurl);
				tablename = mCursofgnnr1.getString(mCursofgnnr1.getColumnIndex(DatabaseForDemo.TABLE_NAME_PENDING));
				map.put("tablename", tablename);
				time = mCursofgnnr1.getString(mCursofgnnr1.getColumnIndex(DatabaseForDemo.CURRENT_TIME_PENDING));
				map.put("time", time);
				parameter = mCursofgnnr1.getString(mCursofgnnr1.getColumnIndex(DatabaseForDemo.PARAMETERS));
				map.put("parameter", parameter);
				data.add(map);
				}catch(Exception e){
					
				}
			}while(mCursofgnnr1.moveToNext());
	}
		
		JsonPostMethod jsonpost = new JsonPostMethod();
		for(int i=0; i<data.size();i++){
			if(data.get(i).get("querytype").equals("createadminprofile")){
				String responseproduct2 = jsonpost.postmethodforcreateprofile("01", "admin", data.get(i).get("parameter"));
				System.out.println("response is:"+responseproduct2);
				
				JSONObject obj;
				try {
					obj = new JSONObject(responseproduct2);
				
				String insertquery = obj.getString("insert-query");
				String deletequery = obj.getString("delete-query");
				String status = obj.getString("status");
				String message = obj.getString("message");
				
					String deletequery1 = deletequery.replace("'", "\"");
					String deletequery2 = deletequery1.replace("\\\"", "'");
					System.out.println("delete query is :"+deletequery2);
					
					String insertquery1 = insertquery.replace("'", "\"");
					String insertquery2 = insertquery1.replace("\\\"", "'");
					System.out.println("delete query is :"+insertquery2);
					try{
					dbforloginlogoutWrite1.execSQL(deletequery);
					dbforloginlogoutWrite1.execSQL(insertquery);
					}catch(SQLException e){
						e.printStackTrace();
					}
					System.out.println("queries executed");
				
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}catch (SQLiteException eq) {
					// TODO: handle exception
					eq.printStackTrace();
				}
			
			}
			
			if(data.get(i).get("querytype").equals("userlogin")){
				String responseproduct2 = jsonpost.postmethodforlogininget1(data.get(i).get("parameter"));
				System.out.println("response is:"+responseproduct2);
				
				try {
					JSONObject obj = new JSONObject(responseproduct2);
					String loginstatus = obj.getString("login-status");
					if(loginstatus.equals("true")){
						String sessionid = obj.getString("sessionid");
						Parameters.sessionidforloginlogout = sessionid;
						String useridval = obj.getString("userid");
						Parameters.userid = useridval;
					}else{
						String message = obj.getString("message");
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			if(data.get(i).get("querytype").equals("userlogout")){
				String responseproduct2 = jsonpost.postmethodforlogininget1(data.get(i).get("parameter"));
				System.out.println("response is:"+responseproduct2);
				
				try {
					JSONObject obj = new JSONObject(responseproduct2);
					String loginstatus = obj.getString("response");
					
						String sessionid = obj.getString("logout");
						if(sessionid.equals("true")){
							System.out.println("successfully logged out");
						}
					
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
	
		if(data.get(i).get("querytype").equals("insert")){
			String responseproduct2 = jsonpost.postmethodfordirect(data.get(i).get("userid"), "abcdefg", data.get(i).get("tablename"), data.get(i).get("time"), Parameters.currentTime(), data.get(i).get("parameter"), "");
			System.out.println("response is:"+responseproduct2);
			try {
				JSONObject obj = new JSONObject(responseproduct2);
				JSONArray array = obj.getJSONArray("insert-queries");
				System.out.println("array list length for insert is:"+array.length());
				JSONArray array2 = obj.getJSONArray("delete-queries");
				System.out.println("array2 list length for delete is:"+array2.length());
				for(int jj = 0,ii = 0; jj<array2.length() && ii<array.length(); jj++,ii++){
					try{
					String deletequerytemp = array2.getString(jj);
					String deletequery1 = deletequerytemp.replace("'", "\"");
					String deletequery = deletequery1.replace("\\\"", "'");
					System.out.println("delete query"+jj+" is :"+deletequery);
					
					String insertquerytemp = array.getString(ii);
					String insertquery1 = insertquerytemp.replace("'", "\"");
					String insertquery = insertquery1.replace("\\\"", "'");
					System.out.println("delete query"+jj+" is :"+insertquery);
					
					dbforloginlogoutWrite1.execSQL(deletequery);
					dbforloginlogoutWrite1.execSQL(insertquery);
					System.out.println("queries executed"+ii);
				}catch(SQLiteException ee){
					ee.printStackTrace();
				}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(data.get(i).get("querytype").equals("update")){
			String responseproduct2 = jsonpost.postmethodfordirect(data.get(i).get("userid"), "abcdefg", data.get(i).get("tablename"), data.get(i).get("time"), Parameters.currentTime(), data.get(i).get("parameter"), "true");
			System.out.println("response is:"+responseproduct2);
			try {
				JSONObject obj = new JSONObject(responseproduct2);
				JSONArray array = obj.getJSONArray("insert-queries");
				System.out.println("array list length for insert is:"+array.length());
				JSONArray array2 = obj.getJSONArray("delete-queries");
				System.out.println("array2 list length for delete is:"+array2.length());
				for(int jj = 0,ii = 0; jj<array2.length() && ii<array.length(); jj++,ii++){
					try{
					String deletequerytemp = array2.getString(jj);
					String deletequery1 = deletequerytemp.replace("'", "\"");
					String deletequery = deletequery1.replace("\\\"", "'");
					System.out.println("delete query"+jj+" is :"+deletequery);
					
					String insertquerytemp = array.getString(ii);
					String insertquery1 = insertquerytemp.replace("'", "\"");
					String insertquery = insertquery1.replace("\\\"", "'");
					System.out.println("delete query"+jj+" is :"+insertquery);
					
					dbforloginlogoutWrite1.execSQL(deletequery);
					dbforloginlogoutWrite1.execSQL(insertquery);
					System.out.println("queries executed"+ii);
					}catch(SQLiteException ee){
						ee.printStackTrace();
					}
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if(data.get(i).get("querytype").equals("delete")){
			String responseproduct2 = jsonpost.postmethodfordirectdelete(data.get(i).get("userid"), "abcdefg", data.get(i).get("tablename"), data.get(i).get("time"), Parameters.currentTime(), data.get(i).get("parameter"));
			System.out.println("response is:"+responseproduct2);
		}
		}
	}
	
	String truncatequery = "Delete from "+DatabaseForDemo.PENDING_QUERIES_TABLE;
	dbforloginlogoutWrite1.execSQL(truncatequery);
	}
}

public void backupcall(String response){
	System.out.println("backupcall is called");
	try {
		JSONObject obj = new JSONObject(response);
		String nexturl = obj.getString("next-url");
		String server_time = obj.getString("server-time");
		String select = "select *from "+DatabaseForDemo.MISCELLANEOUS_TABLE;
		Cursor cursor = dbforloginlogoutRead1.rawQuery(select, null);
		if(cursor.getCount()>0){
			dbforloginlogoutWrite1.execSQL("update "+DatabaseForDemo.MISCELLANEOUS_TABLE+" set "+DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL+"=\""+server_time+"\"");
		}else{
			ContentValues contentValues1 = new ContentValues();
			contentValues1.put(DatabaseForDemo.MISCEL_STORE,  "store1");
			contentValues1.put(DatabaseForDemo.MISCEL_PAGEURL, Parameters.OriginalUrl+"saveinfo.php");
			contentValues1.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL, Parameters.currentTime());
			contentValues1.put(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL, Parameters.currentTime());
			dbforloginlogoutWrite1.insert(DatabaseForDemo.MISCELLANEOUS_TABLE, null, contentValues1);
		}
		JSONArray queriesarray = obj.getJSONArray("queries-array");
		for(int i=0; i<queriesarray.length(); i++){
			try{
			String querytemp = queriesarray.getString(i);
			String query1 = querytemp.replace("'", "\"");
			String query = query1.replace("\\\"", "'");
			dbforloginlogoutWrite1.execSQL(query);
			}catch(SQLiteException e){
				e.printStackTrace();
			}
		}
		if(nexturl.equals("null")){
			
		}else{
			JsonPostMethod postmethod = new JsonPostMethod();
			String responsetext = postmethod.postmethodforbackupnexttime(nexturl);
			backupcall(responsetext);
		}
	} catch (JSONException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}catch(Exception e){
		e.printStackTrace();
	}
}
private void deletetablesdata(){
	Log.v("open", "deletetablesdata");
	try{
	//db1.delete(DatabaseForDemo.TAX_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.DEPARTMENT_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.CATEGORY_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.VENDOR_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.INVENTORY_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.OPTIONAL_INFO_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.ORDERING_INFO_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.ALTERNATE_SKU_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.PRODUCT_PRINTERS_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.MODIFIER_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.CUSTOMER_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.CUSTOMER_GENERAL_INFO_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.CUSTOMER_EXTENDED_INFO_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.CUSTOMER_SHIPPING_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.CUSTOMER_STORES_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.EMP_STORE_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.CREDITCARD_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.EMP_PERMISSIONS_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.EMP_PAYROLL_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.EMP_PERSONAL_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.EMPLOYEE_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.INVOICE_TOTAL_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.INVOICE_ITEMS_TABLE, null, null);
	//dbforloginlogoutWrite1.delete(DatabaseForDemo.DP_CHECKBOX_TABLE, null, null);
	//dbforloginlogoutWrite1.delete(DatabaseForDemo.ITEM_CHECKBOX_TABLE, null, null);
	//db1.delete(DatabaseForDemo.PAYMENT_TABLE, null, null);
	//db1.delete(DatabaseForDemo.PRINTER_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.STORE_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.SPLIT_INVOICE_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.COMMANDS_PRINTER_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.DEPARTMENT_PRINTER_COMMANDS, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.SPILT_LOCAL_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.LOGIN_LOGOUT_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.STOCK_MODIFICATION_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.MERCURY_PAY_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.MERCHANT_TABLE, null, null);
	}catch (SQLiteException eq) {
		// TODO: handle exception
		eq.printStackTrace();
	}
}

private void deleteadmintablesdata(){
	Log.v("123","123");
	//db1.delete(DatabaseForDemo.TAX_TABLE, null, null);
	dbforloginlogoutWrite1.delete(DatabaseForDemo.ADMIN_TABLE, null, null);
}
}
