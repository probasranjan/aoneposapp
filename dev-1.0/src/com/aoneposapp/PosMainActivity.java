package com.aoneposapp;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Timer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.InputType;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aoneposapp.adapters.Custom_Adapter;
import com.aoneposapp.adapters.ImageAdapterForHold;
import com.aoneposapp.adapters.InventoryListAdapter;
import com.aoneposapp.adapters.InventoryListAdapter.OnDeleteClicked;
import com.aoneposapp.adapters.PaymentTypeAdapter;
import com.aoneposapp.adapters.RecallListAdapter;
import com.aoneposapp.adapters.StoreItemAdapter;
import com.aoneposapp.mercury.MagTekDemo;
import com.aoneposapp.starprinter.PrinterFunctions;
import com.aoneposapp.utils.Constants;
import com.aoneposapp.utils.DatabaseForDemo;
import com.aoneposapp.utils.Inventory;
import com.aoneposapp.utils.JsonPostMethod;
import com.aoneposapp.utils.Parameters;
import com.aoneposapp.utils.ServerSyncClass;
import com.epson.eposprint.Builder;
import com.epson.eposprint.EposException;
import com.epson.eposprint.Print;
import com.paypal.android.sdk.payments.PayPalService;

public class PosMainActivity extends Activity implements OnDeleteClicked,
		ImageAdapterForHold.fetchButtomEditInterface {
	private EditText quantityEdit;
	private EditText barcodeEdit;
	Calendar rightNow = Calendar.getInstance();
	@SuppressWarnings("unused")
	String itemidrrrr = null;
	private int saveforlistcolourchange = -1;
	String datavalforupdate = "";
	String dataval = "";
	String invoice_forHold;
	 String hold_Status="";
	String hold_Id;
	Timer myTimer = new Timer();
	boolean printBool = true;;
	private Spinner fetchprinter;
	private EditText fetchEdittext, fetchMinutes;
	private TextView fetchTextView;
	String paymentTypestr = "Cash";
	Double totalcash = 0.0, totalcheck = 0.0, totalcredit = 0.0, totalaccount = 0.0;
	boolean account = false; 
	Double changeforcredit = 0.0;
	String remainingforcredit = "";
	String checkforcredit = "";
	String accforcredit = "";
	WebView billPrint;
	private DatabaseForDemo sqq;
	final ArrayList<String> hold_invoice_id = new ArrayList<String>();
	final ArrayList<String> invoice_id = new ArrayList<String>();
	ArrayList<String> create_date = new ArrayList<String>();
	ArrayList<String> employee_invoice = new ArrayList<String>();
	ArrayList<String> customer_invoice = new ArrayList<String>();
	ArrayList<String> invoice_total = new ArrayList<String>();
	String lastpaymenttypeString = "";
	String customer_phone = " ", customer_company = " ", customer_first = "Cash Customer",
			customer_last = " ";
	 String customer_id = "101";
	private Button quantitybutton, deleteall, pricechange;
	 Button fetchOnHoldButton;
	private Button c_find;
	private Button c_add;
	private Button paybutton;
	private Button tvv;
	private Button discountbutton;
	private Button qtypopup;
	private Button mSearch;
	private Button modiferiemtButton;
	private Button creditordebit;
	private Button checkpay;
	private Button cashpay;
	private Button voidInvoiceButton;
	private Button customerInfoButton;
	private Button customerButton;
	private ListView itemlistView;
	 Inventory mSelectedItem;
	private Inventory mEnterItemInventory;
	 double mSubTotal;
	 double mTaxTotal;
	 double mGrandTotal;
	double remainingamount;
	private AlertDialog alertDialogDismiss;
	double cashpaymentAmount;
	Button tab, demobutton, printLastInvoice, recallInvoices, spiltInvoices;
	boolean mode = true;
	SlidingDrawer slidingDrawer;
	ImageView slideButton, image0, image1, image2, image3, image4, image5,
			image6, image7, image8, logout;
	 DecimalFormat df = new DecimalFormat("#.##");
	Double change = 0.0;
	StoreItemAdapter DPadapter;
	ArrayList<String> storearray = new ArrayList<String>();
	ArrayList<String> storearrayid = new ArrayList<String>();
	final ArrayList<String> deptspinnerdata = new ArrayList<String>();
	final ArrayList<String> checking = new ArrayList<String>();
	final ArrayList<String> itemchecking = new ArrayList<String>();
	final ArrayList<String> itemsdp = new ArrayList<String>();
	final ArrayList<String> itemsNo = new ArrayList<String>();
	TextView subTotalView;
	TextView taxTotalview;
	 TextView grandTotalview;
	private TextView customerDetailsText;
	 InventoryListAdapter mAdapter;
	 ArrayList<Inventory> mItemList;
	private ArrayList<Inventory> mItemDiscountList = new ArrayList<Inventory>();
	private LinearLayout showitemsll, deparmentlist;
	 int mSelectedPosition;
	String holdidexist = "";
	String invoiceidexist = "", recallidExist = "";
	boolean directpayment;
	AlertDialog alertDialogtop=null;
	LayoutInflater mInflatertop=null;
	View layouttop=null;
	String lastsavedinvoiceid = null;
	ArrayList<HashMap<String, String>> payedsofarlist = new ArrayList<HashMap<String, String>>();
	ListView listviewpayedsofar;
	DatabaseForDemo sqlitePos;
	SQLiteDatabase dbforloginlogoutWritePos,dbforloginlogoutReadPos;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.pos_activity);
		Parameters.printerContext=PosMainActivity.this;
		if(Parameters.PrinterBool){
			Parameters.theardRunForPrint();
			Parameters.PrinterBool=false;
			}
		sqlitePos = new DatabaseForDemo(PosMainActivity.this);
		dbforloginlogoutWritePos = sqlitePos.getWritableDatabase();
		dbforloginlogoutReadPos = sqlitePos.getReadableDatabase();
		billPrint=(WebView) findViewById(R.id.webfor_print_pos);
		Parameters.ServerSyncTimer();
	//	theardRunForPrint();
		// startService(new Intent(this, PrinterService.class));

		/*Intent intent = new Intent(this, PayPalService.class);
		intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT,
				PaymentActivity.ENVIRONMENT_SANDBOX);

		intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID,
				"AcSD5hA7phPfAsXzPd85LZ43vUdzEX5Bkf2P8tuTtXBCN1f3RDq_GRBuxGdM");

		startService(intent);*/
		final AlertDialog alertStore = new AlertDialog.Builder(
				PosMainActivity.this).create();
		LayoutInflater mInflater = LayoutInflater.from(PosMainActivity.this);
		View layout = mInflater.inflate(R.layout.store_selection, null);
		final Spinner selectStore = (Spinner) layout
				.findViewById(R.id.spinner1);
		Button ok = (Button) layout.findViewById(R.id.button1);

		if (Parameters.usertype.equals("employee")) {
			Cursor mCuaonersor = dbforloginlogoutReadPos.rawQuery(
					"select * from " + DatabaseForDemo.EMP_STORE_TABLE
							+ " where " + DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID
							+ "=\"" + Parameters.usertypeloginvalue + "\"", null);
			System.out.println(mCuaonersor);
			if (mCuaonersor != null) {
				if (mCuaonersor.moveToFirst()) {
					do {
						String storename = mCuaonersor
								.getString(mCuaonersor
										.getColumnIndex(DatabaseForDemo.EMP_STORE_NAME));
						String storeid = mCuaonersor.getString(mCuaonersor
								.getColumnIndex(DatabaseForDemo.EMP_STORE_ID));
						String[] itemsid = storeid.split(",");
						for (String item : itemsid) {
							if (item.length() > 1)
								storearrayid.add(item);
						}
						String[] itemsname = storename.split(",");
						for (String item : itemsname) {
							if (item.length() > 1)
								storearray.add(item);
						}

					} while (mCuaonersor.moveToNext());
				}
			}
			mCuaonersor.close();

			DPadapter = new StoreItemAdapter(PosMainActivity.this,
					android.R.layout.simple_spinner_item, storearray);
			selectStore.setAdapter(DPadapter);

		}
		if (Parameters.usertype.equals("admin")
				|| Parameters.usertype.equals("demo")) {
			Cursor mCurchisor = dbforloginlogoutReadPos.rawQuery(
					"select * from " + DatabaseForDemo.STORE_TABLE, null);
			System.out.println(mCurchisor);
			if (mCurchisor != null) {
				if (mCurchisor.moveToFirst()) {
					do {
						String storename = mCurchisor.getString(mCurchisor
								.getColumnIndex(DatabaseForDemo.STORE_NAME));
						String storeid = mCurchisor.getString(mCurchisor
								.getColumnIndex(DatabaseForDemo.STORE_ID));
						storearray.add(storename);
						storearrayid.add(storeid);
					} while (mCurchisor.moveToNext());
				}
			}
			mCurchisor.close();
			DPadapter = new StoreItemAdapter(PosMainActivity.this,
					android.R.layout.simple_spinner_item, storearray);
			selectStore.setAdapter(DPadapter);

		}
		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {

				alertStore.dismiss();
				if (storearrayid.size() > 0) {

					Parameters.store_id = storearrayid.get(selectStore
							.getSelectedItemPosition());
					String qry = "update "
							+ DatabaseForDemo.MISCELLANEOUS_TABLE + " set "
							+ DatabaseForDemo.MISCEL_STORE + "=\"" + Parameters.store_id
							+ "\"";
					dbforloginlogoutWritePos.execSQL(qry);
				}
			}
		});

		alertStore.setView(layout);
		if (storearrayid.size() > 1){
			alertStore.show();
		}else{
			if (storearrayid.size() > 0){
				Parameters.store_id = storearrayid.get(0);
				String qry = "update "
						+ DatabaseForDemo.MISCELLANEOUS_TABLE + " set "
						+ DatabaseForDemo.MISCEL_STORE + "=\"" + Parameters.store_id
						+ "\"";
				dbforloginlogoutWritePos.execSQL(qry);
			}
		}
		
		/*
		 * LayoutParams params = new LayoutParams( LayoutParams.WRAP_CONTENT,
		 * LayoutParams.WRAP_CONTENT ); params.setMargins(0, 0, 0, 0);
		 * yourbutton.setLayoutParams(params);
		 */
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
		image0.setBackgroundResource(R.drawable.po1);
		demobutton = (Button) findViewById(R.id.demo);
		demobutton.setTag("0");
		int height;
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		height = displaymetrics.heightPixels;
		slideButton.setPadding(0, 0, 0, height - 40);
		slidingDrawer.setOnDrawerOpenListener(new OnDrawerOpenListener() {
			@Override
			public void onDrawerOpened() {
				// slideButton.setBackgroundResource(R.drawable.icon);
				InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(slideButton.getWindowToken(), 0);
				slideButton.setImageResource(R.drawable.arrowleft);
			}
		});
		slidingDrawer.setOnDrawerCloseListener(new OnDrawerCloseListener() {

			@Override
			public void onDrawerClosed() {
				slideButton.setImageResource(R.drawable.arrow);
			}
		});
		demobutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String viewdata = "";
				if (demobutton.getTag().equals("0")) {
					if (Parameters.usertype.equals("admin")) {
						viewdata = "Are you sure that you want to turn off? \n"
								+ "The data will not be saved if the mode is off";
						final AlertDialog alertDialog1 = new AlertDialog.Builder(
								PosMainActivity.this).create();
						LayoutInflater mInflater = LayoutInflater
								.from(PosMainActivity.this);
						View layout = mInflater.inflate(R.layout.delete_popup,
								null);
						Button ok = (Button) layout.findViewById(R.id.ok);
						Button cancel = (Button) layout
								.findViewById(R.id.cancel);
						TextView deletetext = (TextView) layout
								.findViewById(R.id.deletetext);
						deletetext.setText("" + viewdata);
						ok.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								demobutton.setTag("1");
								demobutton.setText("Mode OFF");
								mode = false;
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
					} else {
						demobutton.setTag("1");
						demobutton.setText("Mode OFF");
						mode = false;
					}
				} else {
					demobutton.setTag("0");
					demobutton.setText("Mode ON");
					mode = true;
				}

			}
		});
		image0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		image1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Parameters.inventory_permission) {
					Intent intent1 = new Intent(PosMainActivity.this,
							InventoryActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							PosMainActivity.this,
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
				Intent intent1 = new Intent(PosMainActivity.this,
						StoresActivity.class);
				startActivity(intent1);
				finish();
			} else {
				showAlertDialog(
						PosMainActivity.this,
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
					Intent intent1 = new Intent(PosMainActivity.this,
							CustomerActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							PosMainActivity.this,
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
				Intent intent1 = new Intent(PosMainActivity.this,
						EmployeeActivity.class);
				startActivity(intent1);
				finish();
			} else {
				showAlertDialog(
						PosMainActivity.this,
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
					Intent intent1 = new Intent(PosMainActivity.this,
							ReportsActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							PosMainActivity.this,
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
					Intent intent1 = new Intent(PosMainActivity.this,
							SettingsActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							PosMainActivity.this,
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
				Intent intent1 = new Intent(PosMainActivity.this,
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
					Intent intent1 = new Intent(PosMainActivity.this,
							ProfileActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							PosMainActivity.this,
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
				Parameters.methodForLogout(PosMainActivity.this);
				finish();
			}
		});

		customerInfoButton = (Button) findViewById(R.id.customerInfoButton);
		customerButton = (Button) findViewById(R.id.customerButton);
		deparmentlist = (LinearLayout) findViewById(R.id.deparmentlist);
		cashpay = (Button) findViewById(R.id.cashpay);
		checkpay = (Button) findViewById(R.id.checkpay);
		creditordebit = (Button) findViewById(R.id.creditordebit);
		printLastInvoice = (Button) findViewById(R.id.printlastinvoice);
		recallInvoices = (Button) findViewById(R.id.recallinvoices);
	//	spiltInvoices = (Button) findViewById(R.id.spilt_invoice);
		// accountpay = (Button) findViewById(R.id.accountpay);
		voidInvoiceButton = (Button) findViewById(R.id.voidInvoiceButton);
		discountbutton = (Button) findViewById(R.id.discountbutton);
		// tslookup = (Button) findViewById(R.id.lookUpButton);
		quantityEdit = (EditText) findViewById(R.id.quantityEdit);
		qtypopup = (Button) findViewById(R.id.qtypopup);
		quantitybutton = (Button) findViewById(R.id.buttonquantity);
		paybutton = (Button) findViewById(R.id.paybutton);
		deleteall = (Button) findViewById(R.id.deleteall);
		mSearch = (Button) findViewById(R.id.mSearch);
		c_add = (Button) findViewById(R.id.findButton);
		c_find = (Button) findViewById(R.id.quickFindButton);
		pricechange = (Button) findViewById(R.id.pricechange);
		itemlistView = (ListView) findViewById(R.id.itemListView);
		subTotalView = (TextView) findViewById(R.id.subtotaltext);
		customerDetailsText = (TextView) findViewById(R.id.customerDetailsText);
		fetchOnHoldButton = (Button) findViewById(R.id.fetchOnHoldButton);
		taxTotalview = (TextView) findViewById(R.id.taxtext);
		grandTotalview = (TextView) findViewById(R.id.grandtotaltext);
		showitemsll = (LinearLayout) findViewById(R.id.showitemsll);
		barcodeEdit = (EditText) findViewById(R.id.editItemNo);
		TextView loginnameempid = (TextView)findViewById(R.id.loginnameval);
		loginnameempid.setText(Parameters.usertypeloginvalue);
		
		customerDetailsText.setText("Customer No = " + customer_id
				+ ", \n Customer Name = " + customer_first + " "
				+ customer_last);
		
		Cursor mCursora = dbforloginlogoutReadPos.rawQuery(
				"select * from " + DatabaseForDemo.DEPARTMENT_TABLE, null);
		System.out.println(mCursora);
		if (mCursora != null) {
			if (mCursora.moveToFirst()) {
				do {
					String catid = mCursora.getString(mCursora
							.getColumnIndex(DatabaseForDemo.DepartmentID));
					System.out.println(catid);
					String check = mCursora.getString(mCursora
							.getColumnIndex(DatabaseForDemo.CHECKED_VALUE));
					System.out.println(check);
					if (check.equals("true")) {
						deptspinnerdata.add(catid);
					}
					// checking.add(check);
				} while (mCursora.moveToNext());
			}
		}
		mCursora.close();
		
		barcodeEdit.setOnKeyListener(new OnKeyListener(){
		    @Override
			public boolean onKey(View v, int keyCode, KeyEvent event)
		    {
		        if (event.getAction() == KeyEvent.ACTION_DOWN)
		        {
		            switch (keyCode)
		            {
		                case KeyEvent.KEYCODE_DPAD_CENTER:
		                case KeyEvent.KEYCODE_ENTER:
		                  //  Toast.makeText(PosMainActivity.this, "Enter key detected", Toast.LENGTH_SHORT).show();
		                    String mBarcode = barcodeEdit.getText().toString();
		                    String mQuantity = "1";
		                    if (mBarcode.length() > 0 && mBarcode != null
		    						&& mQuantity != null && mQuantity.length() > 0) {
		    					mSubTotal = 0;
		    					mTaxTotal = 0;
		    					if (checkitemExists(mBarcode, mQuantity)) {
		    						getItemDetailsAndQuantitiy(true, mBarcode, mQuantity);
		    					}
		    				} else {

		    					if (mBarcode.length() > 0 && mBarcode != null) {
		    						mSubTotal = 0;
		    						mTaxTotal = 0;
		    						if (checkitemExists(mBarcode, "1")) {
		    							getItemDetails(false, mBarcode, null);
		    						}

		    					}
		    					if (mQuantity != null && mQuantity.length() > 0) {
		    						if (mSelectedItem != null) {
		    							mSubTotal = 0;
		    							mTaxTotal = 0;
		    							getItemDetails(true, mSelectedItem.getItemNoAdd(),
		    									mQuantity);
		    							// getItemDetailsAndQuantitiy(true,mBarcode,mQuantity);
		    						} else {
		    							Toast.makeText(getApplicationContext(),
		    									"Select List Item", 1000).show();
		    						}
		    					}

		    				}
		    				quantityEdit.setText("");
		    				barcodeEdit.setText("");

		                    return true;
		                default:
		                    break;
		            }
		        }
		        return false;
		    }
		});
		printLastInvoice.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(printLastInvoice.getText().toString().equals("Print Last Invoice")){
				SharedPreferences sharedPreferences = PreferenceManager
						.getDefaultSharedPreferences(PosMainActivity.this);

				String print_text = sharedPreferences.getString(
						"image_data", "");
				Bitmap map=null;
				if( !print_text.equalsIgnoreCase("") ){
				    byte[] b = Base64.decode(print_text, Base64.DEFAULT);
				     map = BitmapFactory.decodeByteArray(b, 0, b.length);
				     Log.e("mapgeted",""+map);
				}
				showAlertDialogforPrint(PosMainActivity.this,"","Do You Want Recipt?",true,"printer1", "", map);
				//printText("printer1", print_text, map);
				}else{
					Toast.makeText(getApplicationContext(),"No Data", Toast.LENGTH_LONG).show();
				/*createPrintRecipt(invoice_forHold,hold_Id, 0.0, 0.0, 0.0, 0.0, "", paymentTypestr);
				Bitmap map=null;
					printText("printer1", payclip,map);*/
				}
			}
		});
		/* spiltInvoices.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if (mItemList.size() > 0) {
					if (holdidexist.equals("")) {
						Toast.makeText(getApplicationContext(),
								"Pls Save OnHoldInvoice", Toast.LENGTH_LONG)
								.show();
					} else {

						if (mode) {
							String invoice_id = store_id + "_"
									+ Parameters.randomInvoice();
							Log.e("invoice", Parameters.randomInvoice() + "  "
									+ invoice_forHold);
							hold_Status = "complete";
							createPrintRecipt(invoice_id, "");
						}
						final AlertDialog alertDialog12 = new AlertDialog.Builder(
								PosMainActivity.this).create();
						LayoutInflater mInflater2 = LayoutInflater
								.from(PosMainActivity.this);
						View layout2 = mInflater2.inflate(R.layout.change_show,
								null);
						alertDialog12.setTitle("Enter Spilt  Number");
						final EditText totalchange = (EditText) layout2
								.findViewById(R.id.changedit);
						totalchange.setInputType(InputType.TYPE_CLASS_NUMBER);
						totalchange.setText("");
						Button okchange = (Button) layout2
								.findViewById(R.id.changeok);
						okchange.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								String value = totalchange.getText().toString()
										.trim();
								Double number = Double.valueOf(value);
								if (number > 1 && number < mGrandTotal) {
									mItemDiscountList.addAll(mItemList);
									for (int i = 0; i < mItemDiscountList
											.size(); i++) {
										Double Qtty = Double
												.valueOf(mItemDiscountList.get(
														i).getQuantity());
										Double price = Double
												.valueOf(mItemDiscountList.get(
														i).getPriceYouChange());
										Log.v("" + Qtty, "" + price);
										Double actuvalPrice = price / Qtty;
										price = actuvalPrice / number;
										mItemDiscountList.get(i)
												.setPriceYouChange(
														"" + (price * Qtty));
										Log.v("discountTotal 0", "" + price);
										Log.v("discountTotal 1", "" + Double.valueOf(df.format(price)));
									}

									for (int k = 0; k < number; k++) {
										showspiltLists(mItemDiscountList);
									}

									mItemDiscountList.clear();
									mItemList.clear();
									if (mItemList.isEmpty()) {
										fetchOnHoldButton.setTag("1");
										fetchOnHoldButton
												.setText("Fetch On Hold");
										mSubTotal = 0;
										mTaxTotal = 0;
										subTotalView.setText(String.valueOf("$"
												+ mSubTotal));
										taxTotalview.setText(String.valueOf("$"
												+ mTaxTotal));
										grandTotalview.setText(String
												.valueOf("$" + mSubTotal));
									}
									alertDialog12.dismiss();
									// showspiltLists();
								} else {
									Toast.makeText(
											getApplicationContext(),
											"Enter Number Between 1 to GrandTotal value",
											Toast.LENGTH_LONG).show();
								}

							}
						});
						alertDialog12.setView(layout2);
						alertDialog12.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
						alertDialog12.show();

						// showReciptPopup();

					}
				} else {
					Toast.makeText(getApplicationContext(),
							"select From HoldInvoice", Toast.LENGTH_LONG)
							.show();
				}
			}
		}); */
		recallInvoices.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				// SELECT * FROM department_details ORDER BY modified_timestamp
				// desc limit 5 offset 0
				if (mItemList.isEmpty()) {
				//	spiltInvoices.setEnabled(false);
					printLastInvoice.setText(" Print ");
					quantitybutton.setEnabled(false);
					deleteall.setEnabled(false);
					pricechange.setEnabled(false);
					fetchOnHoldButton.setEnabled(false);
					c_find.setEnabled(false);
					c_add.setEnabled(false);
					quantityEdit.setEnabled(false);
					barcodeEdit.setEnabled(false);
					paybutton.setText("Clear Display");
					discountbutton.setEnabled(false);
					qtypopup.setEnabled(false);
					mSearch.setEnabled(false);
					creditordebit.setEnabled(false);
					checkpay.setEnabled(false);
					cashpay.setEnabled(false);
					voidInvoiceButton.setEnabled(true);
					customerInfoButton.setEnabled(false);
					customerButton.setEnabled(false);
					final ArrayList<String> idR = new ArrayList<String>();
					final ArrayList<String> amtR = new ArrayList<String>();
					final ArrayList<String> emyR = new ArrayList<String>();
					final ArrayList<String> cosmR = new ArrayList<String>();
					final ArrayList<String> typeR = new ArrayList<String>();
					final ArrayList<String> dateR = new ArrayList<String>();

					final AlertDialog alertDialog12 = new AlertDialog.Builder(
							PosMainActivity.this,
							android.R.style.Theme_NoTitleBar).create();
					LayoutInflater mInflater2 = LayoutInflater
							.from(PosMainActivity.this);
					View layout2 = mInflater2.inflate(
							R.layout.recallinvoice_popup, null);
					RelativeLayout recallvisible = (RelativeLayout) layout2
							.findViewById(R.id.recallvisible);
					recallvisible.setVisibility(View.VISIBLE);
					ListView listt = (ListView) layout2
							.findViewById(R.id.recallList);
					Button recallCancel = (Button) layout2
							.findViewById(R.id.recallCancel);
				
					Log.v("hari", "nath");
					String selectQuery = "SELECT * FROM "
							+ DatabaseForDemo.INVOICE_TOTAL_TABLE+" where "+DatabaseForDemo.INVOICE_STATUS+"=\"complete\""
							+ " ORDER BY " + DatabaseForDemo.CREATED_DATE
							+ " desc limit 10 offset 0";

					
					Cursor mCursoraa = dbforloginlogoutReadPos.rawQuery(selectQuery, null);
					if (mCursoraa != null) {
						if (mCursoraa.moveToFirst()) {
							do {
								String custno = mCursoraa.getString(mCursoraa
										.getColumnIndex(DatabaseForDemo.INVOICE_ID));
								idR.add(custno);
								custno = mCursoraa.getString(mCursoraa
										.getColumnIndex(DatabaseForDemo.INVOICE_TOTAL_AMT));
								amtR.add(custno);
								custno = mCursoraa.getString(mCursoraa
										.getColumnIndex(DatabaseForDemo.INVOICE_EMPLOYEE));
								emyR.add(custno);
								custno = mCursoraa.getString(mCursoraa
										.getColumnIndex(DatabaseForDemo.INVOICE_CUSTOMER));
								cosmR.add(custno);
								custno = mCursoraa.getString(mCursoraa
										.getColumnIndex(DatabaseForDemo.INVOICE_PAYMENT_TYPE));
								typeR.add(custno);
								custno = mCursoraa.getString(mCursoraa
										.getColumnIndex(DatabaseForDemo.CREATED_DATE));
								dateR.add(custno);

							} while (mCursoraa.moveToNext());
						}
					}
					
					Log.v("hari", "nath" + mCursoraa.getCount());
					Log.v("hari", "nath" + mCursoraa.getColumnCount());
					RecallListAdapter arr = new RecallListAdapter(
							PosMainActivity.this, idR, amtR, emyR, cosmR,
							typeR, dateR);
					Log.v("hari", "nath");
					listt.setAdapter(arr);
					mCursoraa.close();

					listt.setOnItemClickListener(new OnItemClickListener() {
						@Override
						public void onItemClick(AdapterView<?> arg0, View arg1,
								int arg2, long arg3) {
							// TODO Auto-generated method stub
							mSubTotal = 0;
							mTaxTotal = 0;
							
							recallidExist = idR.get(arg2);
							String selectQuery = "SELECT  * FROM "
									+ DatabaseForDemo.INVOICE_ITEMS_TABLE
									+ " where " + DatabaseForDemo.INVOICE_ID
									+ "=\"" + idR.get(arg2) + "\"";

							Cursor mCursorbb = dbforloginlogoutReadPos.rawQuery(selectQuery, null);
							System.out.println("cusor countis jariskflskjfjsf:"
									+ mCursorbb.getCount());
							if (mCursorbb != null) {
								if (mCursorbb.moveToFirst()) {
									do {
										String name = mCursorbb.getString(mCursorbb
												.getColumnIndex(DatabaseForDemo.INVOICE_ITEM_ID));
										getIvoiceItemDetails(name,
												idR.get(arg2));
									} while (mCursorbb.moveToNext());
									fetchOnHoldButton.setTag("0");
									fetchOnHoldButton.setText("Save On Hold");
								}
							}
							mCursorbb.close();
							alertDialog12.dismiss();
						}
					});
					recallCancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							Log.v("1","asaqqq");
							paybutton.setText("Pay");
						//	spiltInvoices.setEnabled(true);
							Log.v("1","asaqqq2");
							printLastInvoice.setText("Print Last Invoice");
							quantitybutton.setEnabled(true);
							deleteall.setEnabled(true);
							Log.v("5","asaqqq5");
							pricechange.setEnabled(true);
							fetchOnHoldButton.setEnabled(true);
							c_find.setEnabled(true);
							c_add.setEnabled(true);
							quantityEdit.setEnabled(true);
							barcodeEdit.setEnabled(true);
							discountbutton.setEnabled(true);
							qtypopup.setEnabled(true);
							Log.v("4","asaqqq4");
							mSearch.setEnabled(true);
							creditordebit.setEnabled(true);
							checkpay.setEnabled(true);
							cashpay.setEnabled(true);
							voidInvoiceButton.setEnabled(true);
							customerInfoButton.setEnabled(true);
							customerButton.setEnabled(true);
							Log.v("2","asaqqq2");
							mItemList.clear();
							Log.v("3","asaqqq3");
							//mAdapter.notifyDataSetChanged();
							mSelectedItem = null;
							mSelectedPosition = -1;
							if (mItemList.isEmpty()) {
								mSubTotal = 0;
								mTaxTotal = 0;
								fetchOnHoldButton.setTag("1");
								fetchOnHoldButton.setText("Fetch On Hold");
								subTotalView.setText(String.valueOf("$"
										+ mSubTotal));
								taxTotalview.setText(String.valueOf("$"
										+ mTaxTotal));
								grandTotalview.setText(String.valueOf("$"
										+ mSubTotal));
							}
							alertDialog12.dismiss();
						}
					});
					alertDialog12.setView(layout2);
					alertDialog12.setCanceledOnTouchOutside(false);
					alertDialog12.setCancelable(false);
					alertDialog12.show();
					Log.v("hari", "nath");
				} else {
					Toast.makeText(getApplicationContext(), "Clear ItemList",
							Toast.LENGTH_LONG).show();
				}
			}

		});
		customerButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				customerDetailsText.setText("Customer No = " + customer_id
						+ ", \n Customer Name = " + customer_first + " "
						+ customer_last);
			}
		});
		customerInfoButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				customerDetailsText.setText("Customer Company = "
						+ customer_company + ", \n Customer Phone = "
						+ customer_phone);
			}
		});
		c_add.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(PosMainActivity.this,
						CustomerActivity.class);
				startActivity(intent1);
			}
		});
		c_find.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				final ArrayList<String> total_customerno_data = new ArrayList<String>();
				final ArrayList<String> total_customerno_datadddddd = new ArrayList<String>();
				final ArrayList<String> total_firstname_data = new ArrayList<String>();
				final ArrayList<String> total_lastname_data = new ArrayList<String>();
				final ArrayList<String> auto_data = new ArrayList<String>();
				final ArrayList<String> total_company_data = new ArrayList<String>();
				final AlertDialog alertDialog12 = new AlertDialog.Builder(
						PosMainActivity.this, android.R.style.Theme_NoTitleBar)
						.create();
				LayoutInflater mInflater2 = LayoutInflater
						.from(PosMainActivity.this);
				View layout2 = mInflater2.inflate(R.layout.customer_find, null);
				ListView listt = (ListView) layout2.findViewById(R.id.c_list);
				
				
				String selectQuery = "SELECT  * FROM "
						+ DatabaseForDemo.CUSTOMER_TABLE;
				Cursor mCursoraaq = dbforloginlogoutReadPos.rawQuery(selectQuery, null);

				if (mCursoraaq != null) {
					Log.v("errrr","null");
					if (mCursoraaq.moveToFirst()) {
						do {
							Log.v("errrr","null");
							String custno = mCursoraaq.getString(mCursoraaq
									.getColumnIndex(DatabaseForDemo.CUSTOMER_NO));
							total_customerno_data.add(custno);
							String firstname = mCursoraaq.getString(mCursoraaq
									.getColumnIndex(DatabaseForDemo.CUSTOMER_FIRST_NAME));
							total_firstname_data.add(firstname);
							String lastname = mCursoraaq.getString(mCursoraaq
									.getColumnIndex(DatabaseForDemo.CUSTOMER_LAST_NAME));
							total_lastname_data.add(lastname);
						} while (mCursoraaq.moveToNext());
					}
				}
				mCursoraaq.close();
			
				for (int ii = 0; ii < total_customerno_data.size(); ii++) {
					
					
					String selectQuery123 = "SELECT * FROM "
							+ DatabaseForDemo.CUSTOMER_GENERAL_INFO_TABLE+ " where " + DatabaseForDemo.CUSTOMER_NO + "=\""
									+ total_customerno_data.get(ii) + "\"";
					Cursor mCursoraa1234 = dbforloginlogoutReadPos.rawQuery(selectQuery123, null);
					if (mCursoraa1234 != null) {
						if(mCursoraa1234.getCount()>0){
						if (mCursoraa1234.moveToFirst()) {
								String company = mCursoraa1234.getString(mCursoraa1234
										.getColumnIndex(DatabaseForDemo.CUSTOMER_COMPANY_NAME));
								total_company_data.add(company);
								String custno = mCursoraa1234.getString(mCursoraa1234.getColumnIndex(DatabaseForDemo.CUSTOMER_PRIMARY_PHONE));
								total_customerno_datadddddd.add(""+custno);
						}
						}else{
							total_company_data.add("");
							total_customerno_datadddddd.add("");
						}
					}else{
						total_company_data.add("");
						total_customerno_datadddddd.add("");
					}
					mCursoraa1234.close();
					
					
				/*	String selectQueryforshipping = "SELECT  * FROM "
							+ DatabaseForDemo.CUSTOMER_SHIPPING_TABLE
							+ " where " + DatabaseForDemo.CUSTOMER_NO + "=\""
							+ total_customerno_data.get(ii) + "\"";

					Cursor mCursorforshipping = dbforloginlogoutReadPos.rawQuery(
							selectQueryforshipping, null);

					if (mCursorforshipping.getCount() > 0) {
						if (mCursorforshipping != null) {
							if (mCursorforshipping.moveToFirst()) {
								do {
									Log.v("errrr233","null");
									String company = mCursorforshipping.getString(mCursorforshipping
											.getColumnIndex(DatabaseForDemo.SHIPPING_COMPANY_NAME));
									total_company_data.add(company);
									String phone = mCursorforshipping.getString(mCursorforshipping
											.getColumnIndex(DatabaseForDemo.SHIPPING_PHONE));
									total_phone_data.add(phone);
								} while (mCursorforshipping.moveToNext());
							}
						}
					}
					mCursorforshipping.close();*/
				}
				Custom_Adapter arr = new Custom_Adapter(PosMainActivity.this,
						total_customerno_data, total_customerno_data,
						total_firstname_data, total_lastname_data,
						total_company_data, total_customerno_datadddddd);
				listt.setAdapter(arr);
				listt.setOnItemClickListener(new OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {
						// TODO Auto-generated method stub
						customer_id = total_customerno_data.get(arg2);
						customer_phone = total_customerno_datadddddd.get(arg2);
						customer_company = total_company_data.get(arg2);
						customer_first = total_firstname_data.get(arg2);
						customer_last = total_lastname_data.get(arg2);
						customerDetailsText.setText("Customer No="
								+ customer_id + ", \n Customer Name="
								+ customer_first + " " + customer_last);
						alertDialog12.dismiss();
					}
				});
				/*auto_data.addAll(total_phone_data);
				auto_data.addAll(total_company_data);
				auto_data.addAll(total_lastname_data);
				auto_data.addAll(total_firstname_data);
				auto_data.addAll(total_customerno_data);*/
				for (int ii1 = 0; ii1 < total_customerno_datadddddd.size(); ii1++) {
					String sss=total_customerno_datadddddd.get(ii1);
					if(sss.length()>0){
					auto_data.add(sss);
					}
				}
				final AutoCompleteTextView autocomplete = (AutoCompleteTextView) layout2
						.findViewById(R.id.auto_for_customer);
				if(auto_data.size()>-1){
				ArrayAdapter<String> arr_auto = new ArrayAdapter<String>(PosMainActivity.this,  android.R.layout.select_dialog_item, auto_data);
				autocomplete.setAdapter(arr_auto);
				}
				
				Button ok_go = (Button) layout2.findViewById(R.id.button1);
				Button cancel = (Button) layout2.findViewById(R.id.button2);
				ok_go.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String cmtrid=autocomplete.getText().toString().trim();
						int arg2=total_customerno_datadddddd.indexOf(cmtrid);
						if(arg2>=0){
						String itemnoo=total_customerno_data.get(arg2);
						
						String selectQuery = "SELECT  * FROM "
								+ DatabaseForDemo.CUSTOMER_TABLE +" where "+DatabaseForDemo.CUSTOMER_NO+"='"+itemnoo+"'";
						Cursor mCursoraa1 = dbforloginlogoutReadPos.rawQuery(selectQuery, null);

						if (mCursoraa1 != null) {
							if (mCursoraa1.moveToFirst()) {
								do {
									 customer_id = mCursoraa1.getString(mCursoraa1
											.getColumnIndex(DatabaseForDemo.CUSTOMER_NO));
									 customer_first = mCursoraa1.getString(mCursoraa1
											.getColumnIndex(DatabaseForDemo.CUSTOMER_FIRST_NAME));
									 customer_last = mCursoraa1.getString(mCursoraa1
											.getColumnIndex(DatabaseForDemo.CUSTOMER_LAST_NAME));
								} while (mCursoraa1.moveToNext());
							}
						}
						mCursoraa1.close();
						

						String selectQueryforshipping = "SELECT  * FROM "
								+ DatabaseForDemo.CUSTOMER_GENERAL_INFO_TABLE
								+ " where " + DatabaseForDemo.CUSTOMER_NO + "=\""
								+ itemnoo + "\"";

						Cursor mCursorforshipping1 = dbforloginlogoutReadPos.rawQuery(
								selectQueryforshipping, null);

						if (mCursorforshipping1.getCount() > 0) {
							if (mCursorforshipping1 != null) {
								if (mCursorforshipping1.moveToFirst()) {
									do {
										customer_company  = mCursorforshipping1.getString(mCursorforshipping1
												.getColumnIndex(DatabaseForDemo.CUSTOMER_COMPANY_NAME));
										customer_phone  = mCursorforshipping1.getString(mCursorforshipping1
												.getColumnIndex(DatabaseForDemo.CUSTOMER_PRIMARY_PHONE));
									} while (mCursorforshipping1.moveToNext());
								}
							}
						}
						mCursorforshipping1.close();
					/*	customer_id = total_customerno_data.get(arg2);
						customer_phone = total_phone_data.get(arg2);
						customer_company = total_company_data.get(arg2);
						customer_first = total_firstname_data.get(arg2);
						customer_last = total_lastname_data.get(arg2);*/
						customerDetailsText.setText("Customer No="
								+ customer_id + " \n Customer Name="
								+ customer_first + " " + customer_last);
						alertDialog12.dismiss();
						}else{
							Toast.makeText(getApplicationContext(),
									"You Have Entered Invalid Phone Number",
									Toast.LENGTH_LONG).show();
						}
					}
				});
				cancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						alertDialog12.dismiss();
					}
				});
				alertDialog12.setView(layout2);
				alertDialog12.getWindow().setSoftInputMode(
						WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
				alertDialog12.show();
			}
		});
		/*
		 * ArrayAdapter<String> DPadapter = new ArrayAdapter<String>(
		 * getApplicationContext(),
		 * android.R.layout.simple_list_item_activated_1, deptspinnerdata);
		 * deparmentlist.setAdapter(DPadapter);
		 */
		if (deptspinnerdata.size() > 0) {
			for (int dsize = 0; dsize < deptspinnerdata.size(); dsize++) {
				final int xyz = dsize;
				final TextView tv = new TextView(PosMainActivity.this);
				tv.setId(dsize);
				tv.setText(deptspinnerdata.get(dsize));
				tv.setTextAppearance(getApplicationContext(),
						android.R.style.TextAppearance_Medium);
				tv.setPadding(10, 10, 10, 10);
				if (dsize == 0) {
					tv.setBackgroundResource(R.drawable.departmentselectedhightlighted);
					tv.setTextColor(Color.BLACK);
					setitemList(deptspinnerdata.get(0));
					saveforlistcolourchange = dsize;
				}
				deparmentlist.addView(tv);
				tv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						tv.setBackgroundResource(R.drawable.departmentselectedhightlighted);
						tv.setTextColor(Color.BLACK);
						setitemList(deptspinnerdata.get(xyz));
						if (saveforlistcolourchange != -1
								&& saveforlistcolourchange != xyz) {
							TextView tvin = (TextView) findViewById(saveforlistcolourchange);
							tvin.setBackgroundResource(0);
							tvin.setTextColor(Color.WHITE);
						}
						saveforlistcolourchange = xyz;
					}
				});
			}
		}

		mItemList = new ArrayList<Inventory>();
		sqq = new DatabaseForDemo(PosMainActivity.this);
		if (deptspinnerdata.size() > 0) {
			setitemList(deptspinnerdata.get(0));
		}
		/*
		 * deparmentlist.setOnItemClickListener(new OnItemClickListener() {
		 * 
		 * @Override public void onItemClick(AdapterView<?> arg0, View arg1, int
		 * arg2, long arg3) { // TODO Auto-generated method stub
		 * deparmentlist.getSelectedView(); for (int j = 0; j <
		 * arg0.getChildCount(); j++){
		 * arg0.getChildAt(j).setBackgroundColor(Color.WHITE); }
		 * arg0.getChildAt(
		 * arg2).setBackgroundResource(R.drawable.departmentselectedhightlighted
		 * ); TextView tv = (TextView) arg1.findViewById(android.R.id.text1);
		 * tv.setTextColor(Color.BLACK);
		 * 
		 * if (saveforlistcolourchange != -1 && saveforlistcolourchange !=
		 * arg2){
		 * arg0.getChildAt(saveforlistcolourchange).setBackgroundResource(
		 * Color.TRANSPARENT);
		 * System.out.println("textcolor is:"+tv.getCurrentTextColor());
		 * if(tv.getCurrentTextColor() == -16777216){
		 * tv.setTextColor(Color.BLACK); }else if(tv.getCurrentTextColor() ==
		 * -1){ tv.setTextColor(Color.WHITE); } }
		 * setitemList(deptspinnerdata.get(arg2)); } });
		 */
		itemlistView.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {

			}
		});
		fetchOnHoldButton.setTag("1");
		fetchOnHoldButton.setText("Fetch On Hold");
		fetchOnHoldButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (fetchOnHoldButton.getTag().equals("0")) {

					if (holdidexist.equals("")) {

						final AlertDialog alertDialog12 = new AlertDialog.Builder(
								PosMainActivity.this).create();
						LayoutInflater mInflater2 = LayoutInflater
								.from(PosMainActivity.this);
						View layout2 = mInflater2.inflate(R.layout.fetch_popup,
								null);
						final EditText totalchange = (EditText) layout2
								.findViewById(R.id.namepop);
						alertDialog12
								.setTitle("Enter the Hold Order Reference Name");
						Button okchange = (Button) layout2
								.findViewById(R.id.okpop);

						okchange.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View v) {
								// TODO Auto-generated method stub
								String totalchangeval = totalchange.getText()
										.toString().trim();

								if (totalchangeval.length() > 0) {
									hold_Id = totalchange.getText().toString()
											.trim();
									String query111 = "SELECT "
											+ DatabaseForDemo.INVOICE_HOLD_ID
											+ " from "
											+ DatabaseForDemo.INVOICE_TOTAL_TABLE
											+ " where "
											+ DatabaseForDemo.INVOICE_HOLD_ID
											+ " =\"" + hold_Id + "\" and "+DatabaseForDemo.INVOICE_STATUS+"=\"hold\"";
									Cursor cursor111 = dbforloginlogoutReadPos.rawQuery(query111,
											null);
									if (cursor111.getCount() > 0) {
										Toast.makeText(getApplicationContext(),
												"Hold Id already exist",
												Toast.LENGTH_LONG).show();
										
									} else {
										
										alertDialog12.dismiss();
										if (mode) {
											invoice_forHold = Parameters
															.generateRandomNumber();
											Log.e("invoice",
													Parameters.generateRandomNumber()
															+ "  "
															+ invoice_forHold);
											hold_Status = "hold";
											System.out
													.println("hold id val is:"
															+ hold_Id);
											createPrintRecipt(invoice_forHold,
													hold_Id, 0.0, 0.0, 0.0, 0.0, "", paymentTypestr);
										}

										mSelectedItem = null;
										mSelectedPosition = -1;
										if (mItemList.isEmpty()) {
											fetchOnHoldButton.setTag("1");
											fetchOnHoldButton
													.setText("Fetch On Hold");
											mSubTotal = 0;
											mTaxTotal = 0;
											subTotalView.setText(String
													.valueOf("$" + mSubTotal));
											taxTotalview.setText(String
													.valueOf("$" + mTaxTotal));
											grandTotalview.setText(String
													.valueOf("$" + mSubTotal));
										}

										if (showPrintTimer()) {
											final AlertDialog alertDialog = new AlertDialog.Builder(
													PosMainActivity.this,
													android.R.style.Theme_Translucent_NoTitleBar)
													.create();
											LayoutInflater mInflater2 = LayoutInflater
													.from(PosMainActivity.this);
											final View layoutforprint = mInflater2
													.inflate(
															R.layout.send_print,
															null);

											final LinearLayout ll4rd = (LinearLayout) layoutforprint
													.findViewById(R.id.listView1);
											Button save = (Button) layoutforprint
													.findViewById(R.id.save);
											Button cancel = (Button) layoutforprint
													.findViewById(R.id.cancel);

											ll4rd.removeAllViews();

											for (int count = 0; count < mItemList
													.size(); count++) {
												Log.v(count + "", "" + count);

												final LinearLayout roww = new LinearLayout(
														PosMainActivity.this);
												roww.setOrientation(LinearLayout.HORIZONTAL);
												roww.setLayoutParams(new LayoutParams(
														LayoutParams.MATCH_PARENT,
														LayoutParams.WRAP_CONTENT));
												roww.setPadding(1, 1, 1, 1);
												fetchTextView = new TextView(
														PosMainActivity.this);
												fetchTextView.setText(mItemList
														.get(count)
														.getItemNameAdd());
												Log.v("textview", mItemList
														.get(count)
														.getItemNameAdd());
												fetchTextView.setTextSize(14);
												fetchTextView.setId(count);
												fetchTextView
														.setLayoutParams(new LinearLayout.LayoutParams(
																200,
																LayoutParams.WRAP_CONTENT));
												roww.addView(fetchTextView);

												fetchprinter = new Spinner(
														PosMainActivity.this);
												fetchprinter.setId(100 + count);
												fetchprinter
														.setLayoutParams(new LinearLayout.LayoutParams(
																150, 45));
												roww.addView(fetchprinter);

												fetchMinutes = new EditText(
														PosMainActivity.this);
												fetchMinutes
														.setLayoutParams(new LinearLayout.LayoutParams(
																100,
																LayoutParams.WRAP_CONTENT));
												fetchMinutes.setTextSize(14);
												fetchMinutes
														.setId(1000 + count);
												fetchMinutes
														.setInputType(InputType.TYPE_CLASS_NUMBER);
												roww.addView(fetchMinutes);

												fetchEdittext = new EditText(
														PosMainActivity.this);
												fetchEdittext
														.setLayoutParams(new LinearLayout.LayoutParams(
																500,
																LayoutParams.WRAP_CONTENT));
												fetchEdittext
														.setText(mItemList.get(
																count)
																.getQuantity()
																+ " "
																+ mItemList
																		.get(count)
																		.getItemNoAdd());
												fetchEdittext.setTextSize(14);
												fetchEdittext
														.setId(10000 + count);
												fetchEdittext.setTag(mItemList
														.get(count)
														.getItemNameAdd());
												roww.addView(fetchEdittext);

												ArrayList<String> printerlistval = new ArrayList<String>();
												printerlistval.clear();
												printerlistval.add("None");
												String query = "SELECT * from "
														+ DatabaseForDemo.PRINTER_TABLE;
												Cursor cursorpand = dbforloginlogoutReadPos
														.rawQuery(query, null);

												if (cursorpand.getCount() > 0) {
													if (cursorpand != null) {
														if (cursorpand
																.moveToFirst()) {
															do {
																if (cursorpand
																		.isNull(cursorpand
																				.getColumnIndex(DatabaseForDemo.PRINTER_ID))) {

																} else {
																	String catid = cursorpand
																			.getString(cursorpand
																					.getColumnIndex(DatabaseForDemo.PRINTER_ID));
																	printerlistval
																			.add(catid);
																}
															} while (cursorpand
																	.moveToNext());
														}
													}
												}
												cursorpand.close();
												String query1 = "SELECT "
														+ DatabaseForDemo.INVENTORY_DEPARTMENT
														+ " from "
														+ DatabaseForDemo.INVENTORY_TABLE
														+ " where "
														+ DatabaseForDemo.INVENTORY_ITEM_NO
														+ "=\""
														+ mItemList.get(count)
																.getItemNoAdd()
														+ "\"";
												Cursor cursor1asq = dbforloginlogoutReadPos
														.rawQuery(query1, null);
												String deptid = "";
												if (cursor1asq.getCount() > 0) {
													if (cursor1asq != null) {
														if (cursor1asq
																.moveToFirst()) {
															do {
																if (cursor1asq
																		.isNull(cursor1asq
																				.getColumnIndex(DatabaseForDemo.INVENTORY_DEPARTMENT))) {

																} else {
																	deptid = cursor1asq
																			.getString(cursor1asq
																					.getColumnIndex(DatabaseForDemo.INVENTORY_DEPARTMENT));
																}
															} while (cursor1asq
																	.moveToNext());
														}
													}
												}
												cursor1asq.close();
												Cursor mCursor3ba = dbforloginlogoutReadPos
														.rawQuery(
																"select *from "
																		+ DatabaseForDemo.DEPARTMENT_PRINTER_COMMANDS
																		+ " where "
																		+ DatabaseForDemo.DepartmentID
																		+ "=\""
																		+ deptid
																		+ "\"",
																null);
												String printerdata = "None";
												String timeminutes = "0", timeseconds = "0";
												if (mCursor3ba.getCount() > 0) {
													if (mCursor3ba != null) {
														if (mCursor3ba
																.moveToFirst()) {
															do {
																if (mCursor3ba
																		.isNull(mCursor3ba
																				.getColumnIndex(DatabaseForDemo.PrinterForDept))) {
																	printerdata = "None";
																} else {
																	printerdata = mCursor3ba
																			.getString(mCursor3ba
																					.getColumnIndex(DatabaseForDemo.PrinterForDept));
																	System.out
																			.println("the array values are is:"
																					+ printerdata);
																}
																if (mCursor3ba
																		.isNull(mCursor3ba
																				.getColumnIndex(DatabaseForDemo.TimeForDeptPrint))) {
																	timeminutes = "0";
																	timeseconds = "0";
																} else {
																	String timeval = mCursor3ba
																			.getString(mCursor3ba
																					.getColumnIndex(DatabaseForDemo.TimeForDeptPrint));
																	String mystring = timeval;
																	String[] a = mystring
																			.split(":");

																	timeminutes = a[0];
																	timeseconds = a[1];
																	System.out
																			.println(timeseconds
																					+ " the array values are is:"
																					+ timeminutes);
																}
															} while (mCursor3ba
																	.moveToNext());
														}
													}
												}
												mCursor3ba.close();
												ArrayAdapter<String> adapter = new ArrayAdapter<String>(
														PosMainActivity.this,
														android.R.layout.simple_list_item_1,
														printerlistval);
												adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
												fetchprinter
														.setAdapter(adapter);
												System.out
														.println("printer data val is:"
																+ printerdata);
												fetchprinter
														.setSelection(printerlistval
																.indexOf(printerdata));
												fetchMinutes
														.setText(timeseconds);
												ll4rd.addView(roww);

											}

											save.setOnClickListener(new OnClickListener() {
												@Override
												public void onClick(View v) {
													// TODO Auto-generated
													// method
													// stub
													alertDialog.dismiss();

													mAdapter.notifyDataSetChanged();
													for (int cccc = 0; cccc < mItemList
															.size(); cccc++) {
														long now = System
																.currentTimeMillis();
														now = now
																+ (Long.valueOf(((EditText) layoutforprint
																		.findViewById(1000 + cccc))
																		.getText()
																		.toString()
																		.trim()) * 60 * 1000);
														
														ContentValues contentValues = new ContentValues();
														contentValues
																.put(DatabaseForDemo.COMMANDS_ITEM_NAME,
																		""
																				+ ((TextView) layoutforprint
																						.findViewById(cccc))
																						.getText());
														contentValues
																.put(DatabaseForDemo.COMMANDS_PRINTER_NAME,
																		""
																				+ ((Spinner) layoutforprint
																						.findViewById(100 + cccc))
																						.getSelectedItem());
														contentValues
																.put(DatabaseForDemo.COMMANDS_TIME,
																		""
																				+ now);
														contentValues
																.put(DatabaseForDemo.COMMANDS_MESSAGE,
																		""
																				+ ((EditText) layoutforprint
																						.findViewById(10000 + cccc))
																						.getText());
														contentValues
																.put(DatabaseForDemo.COMMANDS_HOLDID,
																		""
																				+ hold_Id);
														contentValues
																.put(DatabaseForDemo.UNIQUE_ID,
																		""
																				+ Parameters
																						.randomValue());
														dbforloginlogoutWritePos
																.insert(DatabaseForDemo.COMMANDS_PRINTER_TABLE,
																		null,
																		contentValues);
														contentValues.clear();

													}

													mItemList.clear();
													if (mItemList.isEmpty()) {
														fetchOnHoldButton
																.setTag("1");
														fetchOnHoldButton
																.setText("Fetch On Hold");
														mSubTotal = 0;
														mTaxTotal = 0;
														subTotalView.setText(String
																.valueOf("$"
																		+ mSubTotal));
														taxTotalview.setText(String
																.valueOf("$"
																		+ mTaxTotal));
														grandTotalview.setText(String
																.valueOf("$"
																		+ mSubTotal));
													}
												}
											});

											cancel.setOnClickListener(new OnClickListener() {

												@Override
												public void onClick(View v) {
													// TODO Auto-generated
													// method
													// stub
													alertDialog.dismiss();
												}
											});

											alertDialog.setView(layoutforprint);
											alertDialog.show();
											InputMethodManager mgr = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
											mgr.hideSoftInputFromWindow(
													ll4rd.getWindowToken(), 0);
										} else {
											mItemList.clear();
											if (mItemList.isEmpty()) {
												fetchOnHoldButton.setTag("1");
												fetchOnHoldButton
														.setText("Fetch On Hold");
												mSubTotal = 0;
												mTaxTotal = 0;
												subTotalView.setText(String
														.valueOf("$"
																+ mSubTotal));
												taxTotalview.setText(String
														.valueOf("$"
																+ mTaxTotal));
												grandTotalview.setText(String
														.valueOf("$"
																+ mSubTotal));
											}
										}
									}
									cursor111.close();
								} else {
									Toast.makeText(getApplicationContext(),
											"Please enter Hold Id",
											Toast.LENGTH_LONG).show();
								}
							}

						});
						alertDialog12.setView(layout2);
						alertDialog12.show();

					} else {
						
						
						deleteInvoice(holdidexist);
						if (mode) {
						//	invoice_forHold = Parameters.generateRandomNumber();
						//	Log.e("invoice", Parameters.generateRandomNumber() + "  "
								//	+ invoice_forHold);
							hold_Status = "hold";
							System.out.println("hold id val is:" + holdidexist);
							createPrintRecipt(invoiceidexist, holdidexist, 0.0, 0.0, 0.0, 0.0, "", paymentTypestr);
						}

						if (showPrintTimer()) {
							final AlertDialog alertDialog = new AlertDialog.Builder(
									PosMainActivity.this,
									android.R.style.Theme_Translucent_NoTitleBar)
									.create();
							LayoutInflater mInflater2 = LayoutInflater
									.from(PosMainActivity.this);
							final View layoutforprint = mInflater2.inflate(
									R.layout.send_print, null);

							final LinearLayout ll4rd = (LinearLayout) layoutforprint
									.findViewById(R.id.listView1);
							Button save = (Button) layoutforprint
									.findViewById(R.id.save);
							Button cancel = (Button) layoutforprint
									.findViewById(R.id.cancel);

							ll4rd.removeAllViews();

							for (int count = 0; count < mItemList.size(); count++) {
								Log.v(count + "", "" + count);

								final LinearLayout roww = new LinearLayout(
										PosMainActivity.this);
								roww.setOrientation(LinearLayout.HORIZONTAL);
								roww.setLayoutParams(new LayoutParams(
										LayoutParams.MATCH_PARENT,
										LayoutParams.WRAP_CONTENT));
								roww.setPadding(1, 1, 1, 1);
								fetchTextView = new TextView(
										PosMainActivity.this);
								fetchTextView.setText(mItemList.get(count)
										.getItemNameAdd());
								Log.v("textview", mItemList.get(count)
										.getItemNameAdd());
								fetchTextView.setTextSize(14);
								fetchTextView.setId(count);
								fetchTextView
										.setLayoutParams(new LinearLayout.LayoutParams(
												200, LayoutParams.WRAP_CONTENT));
								roww.addView(fetchTextView);

								fetchprinter = new Spinner(PosMainActivity.this);
								fetchprinter.setId(100 + count);
								fetchprinter
										.setLayoutParams(new LinearLayout.LayoutParams(
												170, 45));
								roww.addView(fetchprinter);

								fetchMinutes = new EditText(
										PosMainActivity.this);
								fetchMinutes
										.setLayoutParams(new LinearLayout.LayoutParams(
												100, LayoutParams.WRAP_CONTENT));
								fetchMinutes.setTextSize(14);
								fetchMinutes.setId(1000 + count);
								fetchMinutes
										.setInputType(InputType.TYPE_CLASS_NUMBER);
								roww.addView(fetchMinutes);

								fetchEdittext = new EditText(
										PosMainActivity.this);
								fetchEdittext
										.setLayoutParams(new LinearLayout.LayoutParams(
												500, LayoutParams.WRAP_CONTENT));
								fetchEdittext.setText(mItemList.get(count)
										.getQuantity()
										+ " "
										+ mItemList.get(count).getItemNoAdd());
								fetchEdittext.setTextSize(14);
								fetchEdittext.setId(10000 + count);
								fetchEdittext.setTag(mItemList.get(count)
										.getItemNoAdd());
								roww.addView(fetchEdittext);

								ArrayList<String> printerlistval = new ArrayList<String>();
								printerlistval.clear();
								printerlistval.add("None");
								String query = "SELECT * from "
										+ DatabaseForDemo.PRINTER_TABLE;
								Cursor cursorqswer = dbforloginlogoutReadPos.rawQuery(query,
										null);

								if (cursorqswer.getCount() > 0) {
									if (cursorqswer != null) {
										if (cursorqswer.moveToFirst()) {
											do {
												if (cursorqswer.isNull(cursorqswer
														.getColumnIndex(DatabaseForDemo.PRINTER_ID))) {

												} else {
													String catid = cursorqswer.getString(cursorqswer
															.getColumnIndex(DatabaseForDemo.PRINTER_ID));
													printerlistval.add(catid);
												}
											} while (cursorqswer.moveToNext());
										}
									}
								}
								cursorqswer.close();
								String query1 = "SELECT "
										+ DatabaseForDemo.INVENTORY_DEPARTMENT
										+ " from "
										+ DatabaseForDemo.INVENTORY_TABLE
										+ " where "
										+ DatabaseForDemo.INVENTORY_ITEM_NO
										+ "=\""
										+ mItemList.get(count).getItemNoAdd()
										+ "\"";
								Cursor cursoasr1o = dbforloginlogoutReadPos.rawQuery(query1,
										null);
								String deptid = "";
								if (cursoasr1o.getCount() > 0) {
									if (cursoasr1o != null) {
										if (cursoasr1o.moveToFirst()) {
											do {
												if (cursoasr1o.isNull(cursoasr1o
														.getColumnIndex(DatabaseForDemo.INVENTORY_DEPARTMENT))) {

												} else {
													deptid = cursoasr1o.getString(cursoasr1o
															.getColumnIndex(DatabaseForDemo.INVENTORY_DEPARTMENT));
												}
											} while (cursoasr1o.moveToNext());
										}
									}
								}
								cursoasr1o.close();
								Cursor mCursor3sagi = dbforloginlogoutReadPos
										.rawQuery(
												"select *from "
														+ DatabaseForDemo.DEPARTMENT_PRINTER_COMMANDS
														+ " where "
														+ DatabaseForDemo.DepartmentID
														+ "=\"" + deptid + "\"",
												null);
								String printerdata = "None";
								String timeminutes = "0", timeseconds = "0";
								if (mCursor3sagi.getCount() > 0) {
									if (mCursor3sagi != null) {
										if (mCursor3sagi.moveToFirst()) {
											do {
												if (mCursor3sagi.isNull(mCursor3sagi
														.getColumnIndex(DatabaseForDemo.PrinterForDept))) {
													printerdata = "None";
												} else {
													printerdata = mCursor3sagi.getString(mCursor3sagi
															.getColumnIndex(DatabaseForDemo.PrinterForDept));
													System.out
															.println("the array values are is:"
																	+ printerdata);
												}
												if (mCursor3sagi.isNull(mCursor3sagi
														.getColumnIndex(DatabaseForDemo.TimeForDeptPrint))) {
													timeminutes = "0";
													timeseconds = "0";
												} else {
													String timeval = mCursor3sagi.getString(mCursor3sagi
															.getColumnIndex(DatabaseForDemo.TimeForDeptPrint));
													String mystring = timeval;
													String[] a = mystring
															.split(":");

													timeminutes = a[0];
													timeseconds = a[1];
													System.out
															.println(timeseconds
																	+ " the array values are is:"
																	+ timeminutes);
												}
											} while (mCursor3sagi.moveToNext());
										}
									}
								}
								mCursor3sagi.close();
								ArrayAdapter<String> adapter = new ArrayAdapter<String>(
										PosMainActivity.this,
										android.R.layout.simple_list_item_1,
										printerlistval);
								adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
								fetchprinter.setAdapter(adapter);
								System.out.println("printer data val is:"
										+ printerdata);
								fetchprinter.setSelection(printerlistval
										.indexOf(printerdata));
								fetchMinutes.setText(timeseconds);
								ll4rd.addView(roww);

							}

							save.setOnClickListener(new OnClickListener() {
								@Override
								public void onClick(View v) {
									// TODO Auto-generated method
									// stub
									alertDialog.dismiss();
try{
									mAdapter.notifyDataSetChanged();
									for (int cccc = 0; cccc < mItemList.size(); cccc++) {
										long now = System.currentTimeMillis();
										String sqw=((EditText) layoutforprint.findViewById(1000 + cccc)).getText().toString().trim();
										if(sqw.length()>0){
										}else{
											sqw="0";
										}
										now = now
												+ (Long.valueOf(sqw) * 60 * 1000);
										ContentValues contentValues = new ContentValues();
										contentValues
												.put(DatabaseForDemo.COMMANDS_ITEM_NAME,
														""
																+ ((TextView) layoutforprint
																		.findViewById(cccc))
																		.getText());
										contentValues
												.put(DatabaseForDemo.COMMANDS_PRINTER_NAME,
														""
																+ ((Spinner) layoutforprint
																		.findViewById(100 + cccc))
																		.getSelectedItem());
										contentValues.put(
												DatabaseForDemo.COMMANDS_TIME,
												"" + now);
										contentValues
												.put(DatabaseForDemo.COMMANDS_MESSAGE,
														""
																+ ((EditText) layoutforprint
																		.findViewById(10000 + cccc))
																		.getText());
										contentValues
												.put(DatabaseForDemo.COMMANDS_HOLDID,
														"" + holdidexist);
										contentValues.put(
												DatabaseForDemo.UNIQUE_ID,
												"" + Parameters.randomValue());
										dbforloginlogoutWritePos
												.insert(DatabaseForDemo.COMMANDS_PRINTER_TABLE,
														null, contentValues);
										contentValues.clear();
									}
									mItemList.clear();
									if (mItemList.isEmpty()) {
										fetchOnHoldButton.setTag("1");
										fetchOnHoldButton
												.setText("Fetch On Hold");
										mSubTotal = 0;
										mTaxTotal = 0;
										subTotalView.setText(String.valueOf("$"
												+ mSubTotal));
										taxTotalview.setText(String.valueOf("$"
												+ mTaxTotal));
										grandTotalview.setText(String
												.valueOf("$" + mSubTotal));
									}
} catch (NumberFormatException e) {
	  e.printStackTrace();
	} catch (SQLiteException e12) {
		  e12.printStackTrace();
		} catch (Exception e1) {
		  e1.printStackTrace();
		}
								}
							});

							cancel.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method
									alertDialog.dismiss();
								}
							});

							alertDialog.setView(layoutforprint);
							alertDialog.show();

						} else {

							mItemList.clear();
							if (mItemList.isEmpty()) {
								fetchOnHoldButton.setTag("1");
								fetchOnHoldButton.setText("Fetch On Hold");
								mSubTotal = 0;
								mTaxTotal = 0;
								subTotalView.setText(String.valueOf("$"
										+ mSubTotal));
								taxTotalview.setText(String.valueOf("$"
										+ mTaxTotal));
								grandTotalview.setText(String.valueOf("$"
										+ mSubTotal));
							}

						}
					}
				} else {
					ShowFetchOnHoldItems();
				}
			}
		});

		mSearch.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				Intent searchintent = new Intent(PosMainActivity.this,
						ItemSearchActivity.class);
				startActivity(searchintent);

			}
		});

		pricechange.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (Parameters.invoice_price_change_permission) {
					if (mItemList.size() > 0) {
						if (mSelectedItem != null) {

							final AlertDialog alertDialog1 = new AlertDialog.Builder(
									PosMainActivity.this).create();
							LayoutInflater mInflater = LayoutInflater
									.from(PosMainActivity.this);
							View layout = mInflater.inflate(
									R.layout.pricechange_popup, null);
							final EditText price = (EditText) layout
									.findViewById(R.id.pricesave);
							Button ok = (Button) layout.findViewById(R.id.ok);
							Button cancel = (Button) layout
									.findViewById(R.id.cancel);

							alertDialog1.setTitle("Enter Price");

							ok.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub
									String prii = price.getText().toString()
											.trim();
									if (prii != null && prii.length() > 0) {
										mSubTotal = 0;
										mTaxTotal = 0;
										try{
										getItemDetailspricechange(true,
												mSelectedItem.getItemNoAdd(),
												(df.format(Double.valueOf(prii))).toString());
										}catch(NumberFormatException e){
											e.printStackTrace();
										}catch (Exception e1) {
											// TODO: handle exception
											e1.printStackTrace();
										}
										alertDialog1.dismiss();

									} else {
										Toast.makeText(getApplicationContext(),
												"Enter Price", 1000).show();
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
							alertDialog1.show();
						} else {
							Toast.makeText(getApplicationContext(),
									"Select List Item", 1000).show();
						}
					} else {
						Toast.makeText(getApplicationContext(), "Add Item",
								Toast.LENGTH_LONG).show();
					}
				} else {
					showAlertDialog(
							PosMainActivity.this,
							"Sorry",
							"You are not authenticated to perform this operation.",
							false);
				}
			}
		});
		cashpay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (Parameters.end_transaction_permission) {
					if (Parameters.end_cash_transaction_permission) {
						paymentOptions("Cash");
					} else {
						showAlertDialog(
								PosMainActivity.this,
								"Sorry",
								"You are not authenticated to perform this operation.",
								false);
					}
				} else {
					showAlertDialog(
							PosMainActivity.this,
							"Sorry",
							"You are not authenticated to perform this operation.",
							false);
				}
			}
		});
		checkpay.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (Parameters.end_transaction_permission) {
					paymentOptions("Check");
				} else {
					showAlertDialog(
							PosMainActivity.this,
							"Sorry",
							"You are not authenticated to perform this operation.",
							false);
				}
			}
		});
		creditordebit.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (Parameters.end_transaction_permission) {
					if (Parameters.force_credit_cards_permission) {
						/*PayPalPayment payment = new PayPalPayment(
								new BigDecimal("" + mGrandTotal), "USD",
								"sales");

						Intent intent = new Intent(PosMainActivity.this,
								PaymentActivity.class);

						// comment this line out for live or set to
						// PaymentActivity.ENVIRONMENT_SANDBOX for sandbox
						intent.putExtra(
								PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT,
								PaymentActivity.ENVIRONMENT_SANDBOX);

						// it's important to repeat the clientId here so that
						// the SDK has it if Android restarts your
						// app midway through the payment UI flow.
						intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID,
								"AcSD5hA7phPfAsXzPd85LZ43vUdzEX5Bkf2P8tuTtXBCN1f3RDq_GRBuxGdM");
						intent.putExtra(PaymentActivity.EXTRA_PAYER_ID, "");
						intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL,
								"rameshbabu.nanotech@gmail.com");
						intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);
						startActivityForResult(intent, 0);*/
						// creditROdebitPayType();
						paymentOptions("Credit/Debit");
					} else {
						showAlertDialog(
								PosMainActivity.this,
								"Sorry",
								"You are not authenticated to perform this operation.",
								false);
					}
				} else {
					showAlertDialog(
							PosMainActivity.this,
							"Sorry",
							"You are not authenticated to perform this operation.",
							false);
				}
			}
		});
		/*
		 * accountpay.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) { // TODO Auto-generated
		 * method stub accountPayType(); } });
		 */
		qtypopup.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (mItemList.size() > 0) {
					if (mSelectedItem != null) {
						final AlertDialog alertDialog1 = new AlertDialog.Builder(
								PosMainActivity.this).create();
						LayoutInflater mInflater = LayoutInflater
								.from(PosMainActivity.this);
						View layout = mInflater.inflate(
								R.layout.pricechange_popup, null);
						final EditText price = (EditText) layout
								.findViewById(R.id.pricesave);
						Button ok = (Button) layout.findViewById(R.id.ok);
						Button cancel = (Button) layout
								.findViewById(R.id.cancel);

						alertDialog1.setTitle("Enter Quantity");

						ok.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								String mQuantity1 = price.getText().toString()
										.trim();

								if (mQuantity1 != null
										&& mQuantity1.length() > 0) {

									if (mSelectedItem != null) {
										mSubTotal = 0;
										mTaxTotal = 0;
										getItemDetails(true,
												mSelectedItem.getItemNoAdd(),
												mQuantity1);
										alertDialog1.dismiss();
									} else {
										Toast.makeText(getApplicationContext(),
												"Select List Item", 1000)
												.show();
									}
								} else {
									Toast.makeText(getApplicationContext(),
											"Enter Quantity", 1000).show();
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
						alertDialog1.show();
					} else {
						Toast.makeText(getApplicationContext(),
								"Select List Item", 1000).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "Add Item",
							Toast.LENGTH_LONG).show();
				}
			}
		});
		discountbutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (Parameters.invoice_discounts_permission) {
					if (mItemList.size() > 0) {
						if (mSelectedItem != null) {
							final AlertDialog alertDialog1 = new AlertDialog.Builder(
									PosMainActivity.this).create();
							LayoutInflater mInflater = LayoutInflater
									.from(PosMainActivity.this);
							View layout = mInflater.inflate(
									R.layout.pricechange_popup, null);
							final EditText price = (EditText) layout
									.findViewById(R.id.pricesave);
							Button ok = (Button) layout.findViewById(R.id.ok);
							Button cancel = (Button) layout
									.findViewById(R.id.cancel);
							final CheckBox discount_total = (CheckBox) layout
									.findViewById(R.id.discount_total);
							final Spinner discount_spinner = (Spinner) layout
									.findViewById(R.id.discountspinner);
							discount_total.setVisibility(View.VISIBLE);
							discount_spinner.setVisibility(View.INVISIBLE);
							discount_total
									.setOnCheckedChangeListener(new OnCheckedChangeListener() {

										@Override
										public void onCheckedChanged(
												CompoundButton buttonView,
												boolean isChecked) {
											// TODO Auto-generated method stub
											if (isChecked) {
												discount_spinner
														.setVisibility(View.VISIBLE);
											} else {
												discount_spinner
														.setVisibility(View.INVISIBLE);
											}
										}
									});
							final ArrayAdapter<String> typeOfDiscount = new ArrayAdapter<String>(
									PosMainActivity.this,
									android.R.layout.simple_spinner_item);
							typeOfDiscount
									.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
							typeOfDiscount.add("%");
							typeOfDiscount.add("$");
							// typeOfPrinter.add(getString(R.string.printername_p60ii));
							discount_spinner.setAdapter(typeOfDiscount);
							alertDialog1.setTitle("Enter Discount");

							ok.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub
									try{
									String discoun = price.getText().toString()
											.trim();
									if (discoun != null && discoun.length() > 0) {
										Double discount = Double
												.valueOf(discoun);
										if (discount_total.isChecked()) {
											if (discount_spinner
													.getSelectedItem()
													.toString().equals("%")) {
												if (discount <= 100) {
													mSelectedItem = null;
													mItemDiscountList
															.addAll(mItemList);
													for (int i = 0; i < mItemDiscountList
															.size(); i++) {
														Log.v("discountTotal", ""+ mItemDiscountList.get(i).getItemNameAdd());
														mSelectedPosition=i;
														mSelectedItem = mItemDiscountList.get(i);
														discountMainMethod(discoun);
														mSelectedItem = null;
													}
													mItemDiscountList.clear();
													Log.v("harinath",
															"chowdary");
													alertDialog1.dismiss();
													Log.v("harinath",
															"chowdary");
												} else {

													Toast.makeText(
															getApplicationContext(),
															"Enter Valid Value",
															1000).show();
												}
											} else {
												String name = mItemList.get(0)
														.getItemNameAdd();
												if (discount > mGrandTotal) {
													mItemList
															.get(0)
															.setItemNameAdd(
																	name
																			+ " OverallDiscount $"
																			+ mGrandTotal);
													mGrandTotal = 0;
												} else {
													mItemList
															.get(0)
															.setItemNameAdd(
																	name
																			+ " OverallDiscount $"
																			+ discount);
													mGrandTotal = mGrandTotal
															- discount;
												}
												mGrandTotal = Double.valueOf(df
														.format(mGrandTotal));
												grandTotalview.setText(String
														.valueOf("$"
																+ mGrandTotal));
												mAdapter.setListener(PosMainActivity.this);
												mAdapter.notifyDataSetChanged();
												alertDialog1.dismiss();

											}

										} else {
											if (discount <= 100) {
												discountMainMethod(discoun);
												alertDialog1.dismiss();
											} else {
												Toast.makeText(
														getApplicationContext(),
														"Enter Valid Discount",
														1000).show();
											}
										}
									} else {
										Toast.makeText(getApplicationContext(),
												"Enter Discount", 1000).show();
									}
									} catch (NumberFormatException e) {
										  e.printStackTrace();
										}catch (SQLiteException e12) {
											  e12.printStackTrace();
										}  catch (Exception e1) {
											  e1.printStackTrace();
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
							alertDialog1.show();
						} else {
							Toast.makeText(getApplicationContext(),
									"Select List Item", 1000).show();
						}
					} else {
						Toast.makeText(getApplicationContext(), "Add Item",
								Toast.LENGTH_LONG).show();
					}
				} else {
					showAlertDialog(
							PosMainActivity.this,
							"Sorry",
							"You are not authenticated to perform this operation.",
							false);
				}
			}
		});
		deleteall.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (mItemList.size() > 0) {
					final AlertDialog alertDialog1 = new AlertDialog.Builder(
							PosMainActivity.this).create();
					LayoutInflater mInflater = LayoutInflater
							.from(PosMainActivity.this);
					View layout = mInflater
							.inflate(R.layout.delete_popup, null);
					Button ok = (Button) layout.findViewById(R.id.ok);
					Button cancel = (Button) layout.findViewById(R.id.cancel);

					alertDialog1.setTitle("Delete All");

					ok.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View arg0) {
							// TODO Auto-generated method stub
							mItemList.clear();
							mAdapter.notifyDataSetChanged();
							mSelectedItem = null;
							mSelectedPosition = -1;
							if (mItemList.isEmpty()) {
								mSubTotal = 0;
								mTaxTotal = 0;
								fetchOnHoldButton.setTag("1");
								fetchOnHoldButton.setText("Fetch On Hold");
								subTotalView.setText(String.valueOf("$"
										+ mSubTotal));
								taxTotalview.setText(String.valueOf("$"
										+ mTaxTotal));
								grandTotalview.setText(String.valueOf("$"
										+ mSubTotal));
							}

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
				} else {
					Toast.makeText(getApplicationContext(), "Add Item",
							Toast.LENGTH_LONG).show();
				}
			}
		});
		voidInvoiceButton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				if (Parameters.void_invoices_permission) {
					if (mItemList.size() > 0) {
						final AlertDialog alertDialog1 = new AlertDialog.Builder(
								PosMainActivity.this).create();
						LayoutInflater mInflater = LayoutInflater
								.from(PosMainActivity.this);
						View layout = mInflater.inflate(R.layout.delete_popup,
								null);
						Button ok = (Button) layout.findViewById(R.id.ok);
						Button cancel = (Button) layout
								.findViewById(R.id.cancel);

						alertDialog1.setTitle("Delete All");

						ok.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								if (holdidexist.equals("") && recallidExist.equals("")) {
									String invoice_id = Parameters.generateRandomNumber();
									Log.e("invoice", Parameters.generateRandomNumber()
											+ "  " + invoice_forHold);
									hold_Status = "void";
									createPrintRecipt(invoice_id, "", 0.0, 0.0, 0.0, 0.0, "", paymentTypestr);
									
									System.out.println("invoice is voided");
									mItemList.clear();
									mAdapter.notifyDataSetChanged();
									mSelectedItem = null;
									mSelectedPosition = -1;
									if (mItemList.isEmpty()) {
										mSubTotal = 0;
										mTaxTotal = 0;
										fetchOnHoldButton.setTag("1");
										fetchOnHoldButton
												.setText("Fetch On Hold");
										subTotalView.setText(String.valueOf("$"
												+ mSubTotal));
										taxTotalview.setText(String.valueOf("$"
												+ mTaxTotal));
										grandTotalview.setText(String
												.valueOf("$" + mSubTotal));
									}
									
								} else {
									/*DatabaseForDemo demodbff = new DatabaseForDemo(
											PosMainActivity.this);
									SQLiteDatabase db = demodbff
											.getReadableDatabase();

									String deleteinvoicetotal = "DELETE from "
											+ DatabaseForDemo.INVOICE_TOTAL_TABLE
											+ " where "
											+ DatabaseForDemo.INVOICE_ID + "='"
											+ invoiceidexist + "'";
									String deleteinvoiceitems = "DELETE from "
											+ DatabaseForDemo.INVOICE_ITEMS_TABLE
											+ " where "
											+ DatabaseForDemo.INVOICE_ID + "='"
											+ invoiceidexist + "'";

									db.execSQL(deleteinvoiceitems);
									db.execSQL(deleteinvoicetotal);

									db.close();
									demodbff.close();*/

									if(!invoiceidexist.equals("")){
									hold_Status = "void";
									updateinvoicetable(invoiceidexist, "void", "", "", "");
									}
									if(!recallidExist.equals("")){
										updateinvoicetable(recallidExist, "void", "", "", "");
									}
									//createPrintRecipt(invoiceidexist, "", 0.0, 0.0, 0.0, 0.0, "");
									System.out.println("invoice is voided");
									mItemList.clear();
									mAdapter.notifyDataSetChanged();
									mSelectedItem = null;
									mSelectedPosition = -1;
									if (mItemList.isEmpty()) {
										mSubTotal = 0;
										mTaxTotal = 0;
										fetchOnHoldButton.setTag("1");
										fetchOnHoldButton
												.setText("Fetch On Hold");
										subTotalView.setText(String.valueOf("$"
												+ mSubTotal));
										taxTotalview.setText(String.valueOf("$"
												+ mTaxTotal));
										grandTotalview.setText(String
												.valueOf("$" + mSubTotal));
									}
								}
								alertDialog1.dismiss();
								invoiceidexist = "";
								holdidexist = "";
								recallidExist = "";
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
					} else {
						Toast.makeText(getApplicationContext(), "Add Item",
								Toast.LENGTH_LONG).show();
					}
				} else {
					showAlertDialog(
							PosMainActivity.this,
							"Sorry",
							"You are not authenticated to perform this operation.",
							false);
				}
			}
		});

		paybutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (paybutton.getText().toString().equals("Clear Display")) {
					paybutton.setText("Pay");
					printLastInvoice.setText("Print Last Invoice");
					quantitybutton.setEnabled(true);
					deleteall.setEnabled(true);
					pricechange.setEnabled(true);
					fetchOnHoldButton.setEnabled(true);
					c_find.setEnabled(true);
					c_add.setEnabled(true);
					quantityEdit.setEnabled(true);
					barcodeEdit.setEnabled(true);
					paybutton.setEnabled(true);
					discountbutton.setEnabled(true);
					qtypopup.setEnabled(true);
					mSearch.setEnabled(true);
					creditordebit.setEnabled(true);
					checkpay.setEnabled(true);
					cashpay.setEnabled(true);
					voidInvoiceButton.setEnabled(true);
					customerInfoButton.setEnabled(true);
					customerButton.setEnabled(true);
					mItemList.clear();
					mAdapter.notifyDataSetChanged();
					mSelectedItem = null;
					mSelectedPosition = -1;
					if (mItemList.isEmpty()) {
						mSubTotal = 0;
						mTaxTotal = 0;
						fetchOnHoldButton.setTag("1");
						fetchOnHoldButton.setText("Fetch On Hold");
						subTotalView.setText(String.valueOf("$" + mSubTotal));
						taxTotalview.setText(String.valueOf("$" + mTaxTotal));
						grandTotalview.setText(String.valueOf("$" + mSubTotal));
					}
				} else {
					if (mItemList.size() > 0) {
						if (Parameters.end_transaction_permission) {
							paymentOptions("Cash");
						} else {
							showAlertDialog(
									PosMainActivity.this,
									"Sorry",
									"You are not authenticated to perform this operation.",
									false);
						}
					} else {
						Toast.makeText(getApplicationContext(), "Add Item",
								Toast.LENGTH_LONG).show();
					}
				}
			}

		});
		/*
		 * tslookup.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) { // TODO Auto-generated
		 * method stub final AlertDialog alertDialog1 = new AlertDialog.Builder(
		 * PosMainActivity.this).create(); LayoutInflater mInflater =
		 * LayoutInflater .from(PosMainActivity.this); View layout =
		 * mInflater.inflate(R.layout.touchscreen, null);
		 * 
		 * alertDialog1.setView(layout); alertDialog1.show(); } });
		 */
		/*
		 * barcodebutton.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) { // TODO Auto-generated
		 * method stub quantityEdit.setEnabled(false); final AlertDialog
		 * alertDialogRecipt = new AlertDialog.Builder(
		 * PosMainActivity.this).create(); LayoutInflater mInflaterRecipt =
		 * LayoutInflater .from(PosMainActivity.this); View layoutRecipt =
		 * mInflaterRecipt.inflate( R.layout.scanbarcode_popup, null); Button
		 * scanbarcode = (Button) layoutRecipt .findViewById(R.id.scanbarcode);
		 * final EditText barcodeEdit = (EditText) layoutRecipt
		 * .findViewById(R.id.editItemNo); Button cancelbar = (Button)
		 * layoutRecipt .findViewById(R.id.cancelbar);
		 * scanbarcode.setOnClickListener(new OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated method
		 * String str = barcodeEdit.getText().toString().trim(); if (str != null
		 * && str.length() > 0) { mSubTotal = 0; mTaxTotal = 0; //
		 * if(str.equals(object)) if (checkitemExists(str)) {
		 * getItemDetails(false, str, null); } } else {
		 * Toast.makeText(getApplicationContext(), "Missing Scan Barcode",
		 * Toast.LENGTH_LONG) .show(); } quantityEdit.setEnabled(true);
		 * alertDialogRecipt.dismiss(); } }); cancelbar.setOnClickListener(new
		 * OnClickListener() {
		 * 
		 * @Override public void onClick(View v) { // TODO Auto-generated
		 * quantityEdit.setEnabled(true); alertDialogRecipt.dismiss(); } });
		 * alertDialogRecipt.setView(layoutRecipt);
		 * alertDialogRecipt.setCanceledOnTouchOutside(false);
		 * alertDialogRecipt.setCancelable(false);
		 * alertDialogRecipt.getWindow().setSoftInputMode(
		 * WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		 * alertDialogRecipt.show();
		 * 
		 * } });
		 */

		quantitybutton.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				if (mItemList.size() > 0) {
					if (mSelectedItem != null) {
						InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
						imm.hideSoftInputFromWindow(quantityEdit.getWindowToken(), 0);
						String mQuantity = quantityEdit.getText().toString().trim();
						/*final AlertDialog alertDialog1 = new AlertDialog.Builder(
								PosMainActivity.this).create();
						LayoutInflater mInflater = LayoutInflater
								.from(PosMainActivity.this);
						View layout = mInflater.inflate(
								R.layout.pricechange_popup, null);
						final EditText price = (EditText) layout
								.findViewById(R.id.pricesave);
						Button ok = (Button) layout.findViewById(R.id.ok);
						Button cancel = (Button) layout
								.findViewById(R.id.cancel);

						alertDialog1.setTitle("Enter Quantity");*/

						//ok.setOnClickListener(new OnClickListener() {

						//	@Override
							//public void onClick(View arg0) {
								// TODO Auto-generated method stub
							//	String mQuantity1 = price.getText().toString()
								//		.trim();

								if (mQuantity != null
										&& mQuantity.length() > 0) {

									if (mSelectedItem != null) {
										mSubTotal = 0;
										mTaxTotal = 0;
										getItemDetails(true,
												mSelectedItem.getItemNoAdd(),
												mQuantity);
										//alertDialog1.dismiss();
									} else {
										Toast.makeText(getApplicationContext(),
												"Select List Item", 1000)
												.show();
									}
								} else {
									Toast.makeText(getApplicationContext(),
											"Enter Quantity", 1000).show();
								}

							/*}
						});
						cancel.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								alertDialog1.dismiss();
							}
						});
						alertDialog1.setView(layout);
						alertDialog1.show();*/
					} else {
						Toast.makeText(getApplicationContext(),
								"Select List Item", 1000).show();
					}
				} else {
					Toast.makeText(getApplicationContext(), "Add Item",
							Toast.LENGTH_LONG).show();
				}
				/*System.out.println("barcode val is:"+quantityEdit.getText().toString()+":val is");
				String mBarcode = barcodeEdit.getText().toString().trim();
				if (mBarcode.length() > 0 && mBarcode != null
						&& mQuantity != null && mQuantity.length() > 0) {
					mSubTotal = 0;
					mTaxTotal = 0;
					if (checkitemExists("", mQuantity)) {
						getItemDetailsAndQuantitiy(true, mBarcode, mQuantity);
					}
				} else {

					if (mBarcode.length() > 0 && mBarcode != null) {
						mSubTotal = 0;
						mTaxTotal = 0;
						if (checkitemExists(mBarcode, "1")) {
							getItemDetails(false, mBarcode, null);
						}

					}
					if (mQuantity != null && mQuantity.length() > 0) {
						if (mSelectedItem != null) {
							mSubTotal = 0;
							mTaxTotal = 0;
							getItemDetails(true, mSelectedItem.getItemNoAdd(),
									mQuantity);
							// getItemDetailsAndQuantitiy(true,mBarcode,mQuantity);
						} else {
							Toast.makeText(getApplicationContext(),
									"Select List Item", 1000).show();
						}
					}

				}
				quantityEdit.setText("");
				barcodeEdit.setText("");*/
			}
		});
		itemlistView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				// arg1.setSelected(true);
				mSelectedPosition = arg2;
				mSelectedItem = (Inventory) mAdapter.getItem(arg2);
				Log.w("mSelectedPositions",""+arg2);
			}
		});
	}

	void getItemDetails(boolean isUpdate, String itemNo, String mQuantity) {
		try{
		List<Inventory> itemList = sqq.getAllInventoryList(itemNo, null, null,
				null);
		fetchOnHoldButton.setTag("0");
		fetchOnHoldButton.setText("Save On Hold");
		if (itemList == null) {
			Toast.makeText(PosMainActivity.this, "Wrong Barcode", 2000).show();
		}
		if (itemList.size() <= 0) {
			Toast.makeText(PosMainActivity.this, "Wrong Barcode", 2000).show();
		}
		if (mItemList == null) {
			mItemList = new ArrayList<Inventory>();
		}
		if (!isUpdate) {
			mItemList.addAll(itemList);
		} else {
			mItemList.remove(mSelectedPosition);
			mSelectedItem.setQuantity(mQuantity);
			mItemList.add(mSelectedPosition, mSelectedItem);
		}

		if (mItemList != null && mItemList.size() > 0) {

			for (int i = 0; i < mItemList.size(); i++) {
				String qtyStr = mItemList.get(i).getQuantity();
				Double qty = 1.0;
				if (qtyStr != null && qtyStr.length() > 0) {
					qty = Double.valueOf(qtyStr);
				}
				mSubTotal += ((qty) * Double.valueOf(mItemList.get(i)
						.getPriceYouChange()));

				String taxStr = mItemList.get(i).getInventoryTaxTotal();
				if (taxStr != null && taxStr.length() > 0) {
					mTaxTotal += ((qty) * Double.valueOf(mItemList.get(i)
							.getInventoryTaxTotal()));
				}
			}

			if (mAdapter == null) {
				mAdapter = new InventoryListAdapter(PosMainActivity.this,
						mItemList);
				mAdapter.setListener(PosMainActivity.this);
				itemlistView.setAdapter(mAdapter);
			} else {
				mAdapter.setListener(PosMainActivity.this);
				mAdapter.notifyDataSetChanged();
			}
			mAdapter.setListener(PosMainActivity.this);
			itemlistView.setSelection(0);
		}
		if (mItemList.isEmpty()) {
			mSubTotal = 0;
			mTaxTotal = 0;
			invoiceidexist = "";
			holdidexist = "";
			fetchOnHoldButton.setTag("1");
			fetchOnHoldButton.setText("Fetch On Hold");
			subTotalView.setText(String.valueOf("$" + mSubTotal));
			taxTotalview.setText(String.valueOf("$" + mTaxTotal));
			grandTotalview.setText(String.valueOf("$" + mSubTotal));
		}
		mSubTotal = Double.valueOf(df.format(mSubTotal));
		mTaxTotal = Double.valueOf(df.format(mTaxTotal));
		subTotalView.setText(String.valueOf("$" + mSubTotal));
		taxTotalview.setText(String.valueOf("$" + mTaxTotal));
		mGrandTotal = mSubTotal + mTaxTotal;
		mGrandTotal = Double.valueOf(df.format(mGrandTotal));
		grandTotalview.setText(String.valueOf("$" + mGrandTotal));
	} catch (NumberFormatException e) {
		  e.printStackTrace();
		} catch (SQLiteException e12) {
			  e12.printStackTrace();
			} catch (Exception e1) {
				  e1.printStackTrace();
				}
	}
	void getItemDetailsChangeName(boolean isUpdate, String itemNo, String mQuantity,String Name) {
		try{
		List<Inventory> itemList = sqq.getAllInventoryListForName(itemNo, null, null,
				null,Name);
		
		fetchOnHoldButton.setTag("0");
		fetchOnHoldButton.setText("Save On Hold");
		if (itemList == null) {
			Toast.makeText(PosMainActivity.this, "Wrong Barcode", 2000).show();
		}
		if (itemList.size() <= 0) {
			Toast.makeText(PosMainActivity.this, "Wrong Barcode", 2000).show();
		}
		if (mItemList == null) {
			mItemList = new ArrayList<Inventory>();
		}
		if (!isUpdate) {
			mItemList.addAll(itemList);
		} else {
			mItemList.remove(mSelectedPosition);
			mSelectedItem.setQuantity(mQuantity);
			mItemList.add(mSelectedPosition, mSelectedItem);
		}

		if (mItemList != null && mItemList.size() > 0) {

			for (int i = 0; i < mItemList.size(); i++) {
				String qtyStr = mItemList.get(i).getQuantity();
				Double qty = 1.0;
				if (qtyStr != null && qtyStr.length() > 0) {
					qty = Double.valueOf(qtyStr);
				}
				mSubTotal += ((qty) * Double.valueOf(mItemList.get(i)
						.getPriceYouChange()));

				String taxStr = mItemList.get(i).getInventoryTaxTotal();
				if (taxStr != null && taxStr.length() > 0) {
					mTaxTotal += ((qty) * Double.valueOf(mItemList.get(i)
							.getInventoryTaxTotal()));
				}
			}

			if (mAdapter == null) {
				mAdapter = new InventoryListAdapter(PosMainActivity.this,
						mItemList);
				mAdapter.setListener(PosMainActivity.this);
				itemlistView.setAdapter(mAdapter);
			} else {
				mAdapter.setListener(PosMainActivity.this);
				mAdapter.notifyDataSetChanged();
			}
			mAdapter.setListener(PosMainActivity.this);
			itemlistView.setSelection(0);
		}
		if (mItemList.isEmpty()) {
			mSubTotal = 0;
			mTaxTotal = 0;
			invoiceidexist = "";
			holdidexist = "";
			fetchOnHoldButton.setTag("1");
			fetchOnHoldButton.setText("Fetch On Hold");
			subTotalView.setText(String.valueOf("$" + mSubTotal));
			taxTotalview.setText(String.valueOf("$" + mTaxTotal));
			grandTotalview.setText(String.valueOf("$" + mSubTotal));
		}
		mSubTotal = Double.valueOf(df.format(mSubTotal));
		mTaxTotal = Double.valueOf(df.format(mTaxTotal));
		subTotalView.setText(String.valueOf("$" + mSubTotal));
		taxTotalview.setText(String.valueOf("$" + mTaxTotal));
		mGrandTotal = mSubTotal + mTaxTotal;
		mGrandTotal = Double.valueOf(df.format(mGrandTotal));
		grandTotalview.setText(String.valueOf("$" + mGrandTotal));
		/*
		 * Date now = new Date(); mCurrentDate = new
		 * SimpleDateFormat("dd-MM-yyyy").format(now);
		 * Log.i("String",mCurrentDate);
		 */
		} catch (NumberFormatException e) {
			  e.printStackTrace();
			} catch (SQLiteException e12) {
				  e12.printStackTrace();
				} catch (Exception e1) {
				  e1.printStackTrace();
				}
	}
	@Override
	public void onDeleteClicked(View v, String string) {
		// TODO Auto-generated method stub
		try{
		if (paybutton.getText().toString().equals("Pay")) {
			String qtyStr1 = mItemList.get(Integer.parseInt(string))
					.getQuantity();
			Double qty1 = 1.0;
			if (qtyStr1 != null && qtyStr1.length() > 0) {
				qty1 = Double.valueOf(qtyStr1);
			}
			if (mItemList.get(Integer.parseInt(string)).getPriceYouChange() != null) {
				Double changevalue = qty1
						* Double.valueOf(mItemList
								.get(Integer.parseInt(string))
								.getPriceYouChange());
				mSubTotal = mSubTotal - changevalue;
				if (mItemList.get(Integer.parseInt(string))
						.getInventoryTaxTotal() != null) {
					changevalue = qty1
							* Double.valueOf(mItemList.get(
									Integer.parseInt(string))
									.getInventoryTaxTotal());
					mTaxTotal = mTaxTotal - changevalue;
				}
			}
			mSubTotal = Double.valueOf(df.format(mSubTotal));
			mTaxTotal = Double.valueOf(df.format(mTaxTotal));
			subTotalView.setText(String.valueOf("$" + mSubTotal));
			taxTotalview.setText(String.valueOf("$" + mTaxTotal));
			mGrandTotal = mSubTotal + mTaxTotal;
			mGrandTotal = Double.valueOf(df.format(mGrandTotal));
			grandTotalview.setText(String.valueOf("$" + mGrandTotal));
			mItemList.remove(Integer.parseInt(string));
			mAdapter.notifyDataSetChanged();
			mAdapter.setListener(PosMainActivity.this);
			itemlistView.setSelection(0);
			mSelectedPosition = -1;
			mSelectedItem = null;
			if (mItemList.isEmpty()) {
				mSubTotal = 0;
				mTaxTotal = 0;
				invoiceidexist = "";
				holdidexist = "";
				fetchOnHoldButton.setTag("1");
				fetchOnHoldButton.setText("Fetch On Hold");
				subTotalView.setText(String.valueOf("$" + mSubTotal));
				taxTotalview.setText(String.valueOf("$" + mTaxTotal));
				grandTotalview.setText(String.valueOf("$" + mSubTotal));
			}
		}
		} catch (NumberFormatException e) {
			  e.printStackTrace();
			} catch (SQLiteException e12) {
				  e12.printStackTrace();
				} catch (Exception e1) {
				  e1.printStackTrace();
				}
	}

	private boolean checkitemExists(String str, String qtty) {
		try{
		if (mItemList != null && mItemList.size() > 0) {
			for (int i = 0; i < mItemList.size(); i++) {
				String qtyStr = mItemList.get(i).getItemNoAdd().trim();
				if (str.equals(qtyStr)) {
					Double vlu = Double.valueOf(mItemList.get(i).getQuantity());
					Double qty = Double.valueOf(qtty);
					vlu = vlu + qty;
					String qqqq = Double.toString(vlu);
					mSelectedPosition = i;
					mSelectedItem = (Inventory) mAdapter.getItem(i);
					getItemDetails(true, mSelectedItem.getItemNoAdd(), qqqq);
					return false;
				}
			}
		}
		} catch (NumberFormatException e) {
			  e.printStackTrace();
			  return true;
			} catch (SQLiteException e12) {
				  e12.printStackTrace();
				} catch (Exception e1) {
				  e1.printStackTrace();
				  return true;
				}
		return true;
		
	}

	private void getItemDetailspricechange(boolean isUpdate, String itemNo,
			String price) {
		try{
			Log.v("itemNo itemNo", itemNo);
		Double taxyy = 0.0;
		List<Inventory> itemList = sqq.getAllInventoryList(itemNo, null, null,
				null);
		fetchOnHoldButton.setTag("0");
		fetchOnHoldButton.setText("Save On Hold");
		if (mItemList == null) {
			mItemList = new ArrayList<Inventory>();
		}
		if (!isUpdate) {
			mItemList.addAll(itemList);
		} else {
			// Harinath
			
			  Cursor mCursorchinu = dbforloginlogoutReadPos.rawQuery( "select * from " +
			  DatabaseForDemo.INVENTORY_TABLE + " where " +
			  DatabaseForDemo.INVENTORY_ITEM_NO + "=\"" +
			  mSelectedItem.getItemNoAdd() + "\"", null);
			  System.out.println(mCursorchinu); 
			  if (mCursorchinu != null) {
				  if(mCursorchinu.moveToFirst()) {
					  do { 
						 /* String catid = mCursor.getString(mCursor
			  .getColumnIndex(DatabaseForDemo.INVENTORY_TOTAL_TAX));
						  taxyy =Double.valueOf(catid);*/			
						  String texss=mCursorchinu.getString(mCursorchinu
									.getColumnIndex(DatabaseForDemo.INVENTORY_TAXONE));
							String[] parts = texss.split(",");
							String part1 = parts[0]; 
							Log.v(""+part1,"" +"  hari   "+parts.length);
							for(int t=0;t<parts.length;t++){
								String ttt=parts[t]; 
								Log.e("",""+ttt);
								String query = "select * from "
										+ DatabaseForDemo.TAX_TABLE + " where "
										+ DatabaseForDemo.TAX_NAME + "=\""
										+ ttt + "\"";
								System.out.println(query);
								Cursor cursortax =  dbforloginlogoutReadPos.rawQuery(query, null);
								if (cursortax != null) {
									if (cursortax.moveToFirst()) {
										do {
											double taxvalpercent =cursortax.getDouble(cursortax
													.getColumnIndex(DatabaseForDemo.TAX_VALUE));
											System.out.println("taxvalpercent     " + taxvalpercent);
											taxyy += taxvalpercent;
											System.out.println("tax    " + taxyy);
										} while (cursortax.moveToNext());
									}
									cursortax.close();
							}
							}
						  } while (mCursorchinu.moveToNext()); 
					  } 
				  }
			  mCursorchinu.close(); 
			 

			mItemList.remove(mSelectedPosition);
			mSelectedItem.setPriceYouChange(price);
			 taxyy = (Double.valueOf(price) * taxyy) / 100;
			 mSelectedItem.setInventoryTaxTotal(String.valueOf(df.format(taxyy)));
			mItemList.add(mSelectedPosition, mSelectedItem);
		}

		if (mItemList != null && mItemList.size() > 0) {

			for (int i = 0; i < mItemList.size(); i++) {
				String qtyStr = mItemList.get(i).getQuantity();
				Double qty = 1.0;
				if (qtyStr != null && qtyStr.length() > 0) {
					qty = Double.valueOf(qtyStr);
				}
				mSubTotal += ((qty) * Double.valueOf(mItemList.get(i)
						.getPriceYouChange()));
				String taxStr = mItemList.get(i).getInventoryTaxTotal();
				if (taxStr != null && taxStr.length() > 0) {
					mTaxTotal += ((qty) * Double.valueOf(mItemList.get(i)
							.getInventoryTaxTotal()));
				}
				mSubTotal = Double.valueOf(df.format(mSubTotal));
				mTaxTotal = Double.valueOf(df.format(mTaxTotal));
				subTotalView.setText(String.valueOf("$" + mSubTotal));
				taxTotalview.setText(String.valueOf("$" + mTaxTotal));
				mGrandTotal = mSubTotal + mTaxTotal;
				mGrandTotal = Double.valueOf(df.format(mGrandTotal));
				grandTotalview.setText(String.valueOf("$" + mGrandTotal));
			}

			if (mAdapter == null) {
				mAdapter = new InventoryListAdapter(PosMainActivity.this,
						mItemList);
				mAdapter.setListener(PosMainActivity.this);
				itemlistView.setAdapter(mAdapter);
			} else {
				mAdapter.setListener(PosMainActivity.this);
				mAdapter.notifyDataSetChanged();
			}
			mAdapter.setListener(PosMainActivity.this);
			itemlistView.setSelection(0);
		}
	} catch (NumberFormatException e) {
		  e.printStackTrace();
		} catch (SQLiteException e12) {
			  e12.printStackTrace();
			} catch (Exception e1) {
			  e1.printStackTrace();
			}
	}

	void setitemList(String name_value) {
try{
		itemsdp.clear();
		itemsNo.clear();
		showitemsll.removeAllViews();
		String selectQuery = "SELECT  * FROM "
				+ DatabaseForDemo.INVENTORY_TABLE + " WHERE "
				+ DatabaseForDemo.INVENTORY_DEPARTMENT + "=\"" + name_value
				+ "\" ORDER BY "+DatabaseForDemo.INVENTORY_ITEM_NAME+";";
		Cursor cursoraqszx = dbforloginlogoutReadPos.rawQuery(selectQuery,
				null);
		if (cursoraqszx != null) {
			if (cursoraqszx.moveToFirst()) {
				do {
					String catname = cursoraqszx
							.getString(cursoraqszx
									.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NAME));
					String catid = cursoraqszx.getString(cursoraqszx
							.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NO));
					String itemcheck = cursoraqszx.getString(cursoraqszx
							.getColumnIndex(DatabaseForDemo.CHECKED_VALUE));
					if (itemcheck.equals("true")) {
						itemsdp.add(catname);
						itemsNo.add(catid);
					}
				} while (cursoraqszx.moveToNext());
			}
		}
		cursoraqszx.close();
		int totalcount = itemsdp.size();
		int itemcount = 3;
		int to = totalcount / itemcount;
		int too = totalcount % itemcount;
		int i = to;
		if (too != 0) {
			i = to + 1;
		}
		int stLoop = 0;
		int endLoop = 0;
		for (int llC = 1; llC <= i; llC++) {
			stLoop = (llC - 1) * itemcount;
			endLoop = llC * itemcount;
			final LinearLayout roww = new LinearLayout(PosMainActivity.this);
			roww.setOrientation(LinearLayout.HORIZONTAL);
			roww.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			roww.setPadding(1, 1, 1, 1);

			if (endLoop >= totalcount) {
				endLoop = totalcount;
			}
			for (int BTn = stLoop; BTn < endLoop; BTn++) {
				tvv = new Button(PosMainActivity.this);
				tvv.setText("" + itemsdp.get(BTn));
				tvv.setTag("" + itemsNo.get(BTn));
				tvv.setTextSize(10);
				LayoutParams lp = new LayoutParams(100, 40);
				tvv.setPadding(0, 0, 0, 0);
				roww.addView(tvv, lp);
				tvv.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
						
						if (paybutton.getText().toString().equals("Pay")) {
							String str1 = v.getTag().toString();
							
							if (str1 != null && str1.length() > 0) {
								mSubTotal = 0;
								mTaxTotal = 0;
								if(modifiersHaving(str1)){
								if (checkitemExists(str1, "1")) {
									getItemDetails(false, str1, null);
								}
								}
							} else {
								Toast.makeText(getApplicationContext(),
										"Item Number is Null",
										Toast.LENGTH_LONG).show();
							}
						}
					}
				});
			}
			showitemsll.addView(roww);
		}
} catch (NumberFormatException e) {
	  e.printStackTrace();
	} catch (SQLiteException e12) {
		  e12.printStackTrace();
		} catch (Exception e1) {
		  e1.printStackTrace();
		}
	}

	void paymentOptions(String button) {
		try{
		if (mItemList.size() > 0) {
			payedsofarlist.clear();
			totalaccount = 0.0;
			totalcash = 0.0;
			totalcheck = 0.0;
			totalcredit = 0.0;
			lastpaymenttypeString = null;
			lastsavedinvoiceid = null;
			
			String[] paytypes = { "Cash", "Check", "Credit/Debit", "Account" };
			 alertDialogtop = new AlertDialog.Builder(
					PosMainActivity.this).create();
			 mInflatertop = LayoutInflater
					.from(PosMainActivity.this);
			  layouttop = mInflatertop.inflate(R.layout.pay_popup, null);
			final EditText total = (EditText) layouttop
					.findViewById(R.id.totalsave);
			final EditText remaining = (EditText) layouttop
					.findViewById(R.id.remaining);
			Button ok = (Button) layouttop.findViewById(R.id.ok);
			Button cancel = (Button) layouttop.findViewById(R.id.cancel);
			final Button bigbutton = (Button) layouttop
					.findViewById(R.id.roundvalue);
			final Button one = (Button) layouttop.findViewById(R.id.one);
			final Button five = (Button) layouttop.findViewById(R.id.five);
			final Button ten = (Button) layouttop.findViewById(R.id.ten);
			final Button twenty = (Button) layouttop.findViewById(R.id.twenty);
			final Button fifty = (Button) layouttop.findViewById(R.id.fifty);
			final Spinner paytypespnr = (Spinner) layouttop
					.findViewById(R.id.paytypespinner);
			listviewpayedsofar = (ListView) layouttop.findViewById(R.id.payedsofar);

			/*final ArrayList<String> printernames = new ArrayList<String>();
			DatabaseForDemo sqlDB2vv = new DatabaseForDemo(PosMainActivity.this);
			SQLiteDatabase db = sqlDB2vv.getWritableDatabase();
			if (Parameters.isTableExists(db, DatabaseForDemo.PRINTER_TABLE)) {
				String selectQuery = "SELECT  * FROM "
						+ DatabaseForDemo.PRINTER_TABLE;
				Cursor mCursor = db.rawQuery(selectQuery, null);
				int count = mCursor.getColumnCount();
				System.out.println(count);
				if (mCursor != null) {
					if (mCursor.moveToFirst()) {
						do {
							String value = mCursor
									.getString(mCursor
											.getColumnIndex(DatabaseForDemo.PRINTER_ID));
							if (value != null)
								printernames.add(value);
						} while (mCursor.moveToNext());
					}

				}
				mCursor.close();
			}
			sqlDB2vv.close();
			db.close();*/
			remaining.setOnTouchListener(new OnTouchListener() {
				@Override
				public boolean onTouch(View v, MotionEvent event) {
					final int DRAWABLE_LEFT = 0;
					final int DRAWABLE_TOP = 1;
					final int DRAWABLE_RIGHT = 2;
					final int DRAWABLE_BOTTOM = 3;

					if (event.getAction() == MotionEvent.ACTION_UP) {
						if (event.getX() >= (remaining.getRight() - remaining
								.getCompoundDrawables()[DRAWABLE_RIGHT]
								.getBounds().width())) {
							// your action here
							remaining.setText("");
						}
					}
					return false;
				}
			});
			mGrandTotal = (double) Math.round(mGrandTotal * 100) / (double) 100;
			total.setText("$" + mGrandTotal);
			remaining.setText("" + mGrandTotal);
			remainingamount = mGrandTotal;
			cashpaymentAmount = Math.ceil(mGrandTotal);
			bigbutton.setText("" + cashpaymentAmount);
			change = cashpaymentAmount - mGrandTotal;

			/*
			 * this is also good code for (1234567.23) int i=348842; double
			 * i2=i/60000; DecimalFormat dtime = new DecimalFormat("#.###"); i2=
			 * Double.valueOf(dtime.format(4556676.4524624565466));
			 */

			ArrayAdapter<String> adapter123 = new ArrayAdapter<String>(
					PosMainActivity.this, android.R.layout.simple_spinner_item,
					paytypes);
			paytypespnr.setAdapter(adapter123);
			paytypespnr.setSelection(adapter123.getPosition(button));
			
			paytypespnr.setOnItemSelectedListener(new OnItemSelectedListener() {

				@Override
				public void onItemSelected(AdapterView<?> arg0, View arg1,
						int arg2, long arg3) {
					// TODO Auto-generated method stub
					TextView tv = (TextView)arg1.findViewById(android.R.id.text1);
					System.out.println("spinnervalueis:"+tv.getText().toString());
					if(tv.getText().toString().equals("Cash")){
						one.setEnabled(true);
						five.setEnabled(true);
						ten.setEnabled(true);
						twenty.setEnabled(true);
						fifty.setEnabled(true);
						bigbutton.setEnabled(true);
						paymentTypestr = "Cash";
					}else{
						one.setEnabled(false);
						five.setEnabled(false);
						ten.setEnabled(false);
						twenty.setEnabled(false);
						fifty.setEnabled(false);
						bigbutton.setEnabled(false);
					}
				}

				@Override
				public void onNothingSelected(AdapterView<?> arg0) {
					// TODO Auto-generated method stub
					
				}
			});

			one.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (remainingamount > 1) {
						remainingamount = remainingamount - 1;
						remainingamount = (double) Math
								.round(remainingamount * 100) / (double) 100;
						total.setText("$" + remainingamount);
						cashpaymentAmount = Math.ceil(remainingamount);
						bigbutton.setText("" + cashpaymentAmount);
						cashPayTypeforNotComplete(0.0, "1", "", "", paymentTypestr);
						remaining.setText(""+remainingamount);
						
						PaymentTypeAdapter adapter = new PaymentTypeAdapter(PosMainActivity.this, payedsofarlist);
						listviewpayedsofar.setAdapter(adapter);
						System.out.println("padma payment type adapter is called"+adapter.getCount());
					} else {
						change = 1 - remainingamount;
						alertDialogtop.dismiss();
						change = (double) Math.round(change * 100)
								/ (double) 100;
						cashPayType(0.0, remaining.getText().toString(), "", "");
						showchangepopup(change);
						//cashPayType(change, "", "", "");
					}
				}
			});
			five.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (remainingamount > 5) {
						remainingamount = remainingamount - 5;
						remainingamount = (double) Math
								.round(remainingamount * 100) / (double) 100;
						total.setText("$" + remainingamount);
						
						cashpaymentAmount = Math.ceil(remainingamount);
						bigbutton.setText("" + cashpaymentAmount);
						cashPayTypeforNotComplete(0.0, "5", "", "", paymentTypestr);
						remaining.setText(""+remainingamount);
						
						PaymentTypeAdapter adapter = new PaymentTypeAdapter(PosMainActivity.this, payedsofarlist);
						listviewpayedsofar.setAdapter(adapter);
						System.out.println("padma payment type adapter is called"+adapter.getCount());
					} else {
						change = 5 - remainingamount;
						alertDialogtop.dismiss();
						change = (double) Math.round(change * 100)
								/ (double) 100;
						cashPayType(0.0, remaining.getText().toString(), "", "");
						showchangepopup(change);
						//cashPayType(change, "", "", "");
					}
				}
			});
			ten.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (remainingamount > 10) {
						remainingamount = remainingamount - 10;
						remainingamount = (double) Math
								.round(remainingamount * 100) / (double) 100;
						total.setText("$" + remainingamount);
						
						cashpaymentAmount = Math.ceil(remainingamount);
						bigbutton.setText("" + cashpaymentAmount);
						
						cashPayTypeforNotComplete(0.0, "10", "", "", paymentTypestr);
						remaining.setText(""+remainingamount);
						
						PaymentTypeAdapter adapter = new PaymentTypeAdapter(PosMainActivity.this, payedsofarlist);
						listviewpayedsofar.setAdapter(adapter);
						System.out.println("padma payment type adapter is called"+adapter.getCount());
					} else {
						change = 10 - remainingamount;
						alertDialogtop.dismiss();
						change = (double) Math.round(change * 100)
								/ (double) 100;
						cashPayType(0.0, remaining.getText().toString(), "", "");
						showchangepopup(change);
						//cashPayType(change, "", "", "");
					}
				}
			});
			twenty.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (remainingamount > 20) {
						remainingamount = remainingamount - 20;
						remainingamount = (double) Math
								.round(remainingamount * 100) / (double) 100;
						total.setText("$" + remainingamount);
						
						cashpaymentAmount = Math.ceil(remainingamount);
						bigbutton.setText("" + cashpaymentAmount);
						cashPayTypeforNotComplete(0.0, "20", "", "", paymentTypestr);
						remaining.setText(""+remainingamount);
						
						PaymentTypeAdapter adapter = new PaymentTypeAdapter(PosMainActivity.this, payedsofarlist);
						listviewpayedsofar.setAdapter(adapter);
						System.out.println("padma payment type adapter is called"+adapter.getCount());
					} else {
						change = 20 - remainingamount;
						alertDialogtop.dismiss();
						change = (double) Math.round(change * 100)
								/ (double) 100;
						cashPayType(0.0, remaining.getText().toString(), "", "");
						showchangepopup(change);
						//cashPayType(change, "", "", "");
					}
				}
			});
			fifty.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (remainingamount > 50) {
						remainingamount = remainingamount - 50;
						remainingamount = (double) Math
								.round(remainingamount * 100) / (double) 100;
						total.setText("$" + remainingamount);
						
						cashpaymentAmount = Math.ceil(remainingamount);
						bigbutton.setText("" + cashpaymentAmount);
						cashPayTypeforNotComplete(0.0, "50", "", "", paymentTypestr);
						remaining.setText(""+remainingamount);
						
						PaymentTypeAdapter adapter = new PaymentTypeAdapter(PosMainActivity.this, payedsofarlist);
						listviewpayedsofar.setAdapter(adapter);
						System.out.println("padma payment type adapter is called"+adapter.getCount());
					} else {
						change = 50 - remainingamount;
						change = (double) Math.round(change * 100)
								/ (double) 100;
						alertDialogtop.dismiss();
						cashPayType(0.0, remaining.getText().toString(), "", "");
						showchangepopup(change);
						//cashPayType(change, "", "", "");
					}
				}
			});
			bigbutton.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if (cashpaymentAmount >= remainingamount) {
						change = cashpaymentAmount - remainingamount;
						change = (double) Math.round(change * 100)
								/ (double) 100;
						alertDialogtop.dismiss();
						cashPayType(0.0, remaining.getText().toString(), "", "");
						showchangepopup(change);
						//cashPayType(change, "", "", "");
					}
				}
			});
			ok.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					
					paymentTypestr = paytypespnr.getSelectedItem().toString();
					//padma
					lastpaymenttypeString = lastpaymenttypeString+","+paymentTypestr;
					if (paymentTypestr.equals("Cash")) {
						
						if (remaining.getText().toString().trim().length() > 0) {
							Double given = Double.valueOf(remaining.getText()
									.toString().trim());
							if (given == null || given == 0.00) {
								Toast.makeText(getApplicationContext(),
										"Enter Amount", 1000).show();
							} else {
								if (given >= remainingamount) {
									change = given - remainingamount;
									change = (double) Math.round(change * 100)
											/ (double) 100;
									alertDialogtop.dismiss();
									cashPayType(0.0, remaining.getText().toString(), "", "");
									showchangepopup(change);
									//showReciptPopup();
								} else {
									remainingamount = remainingamount - given;
									remainingamount = (int) Math
											.round(remainingamount * 100)
											/ (double) 100;
									total.setText("$" + remainingamount);
									cashpaymentAmount = Math
											.ceil(remainingamount);
									bigbutton.setText("" + cashpaymentAmount);
									
									cashPayTypeforNotComplete(0.0, remaining.getText().toString(), "", "", paymentTypestr);
									remaining.setText(""+remainingamount);
									PaymentTypeAdapter adapter = new PaymentTypeAdapter(PosMainActivity.this, payedsofarlist);
									listviewpayedsofar.setAdapter(adapter);
									System.out.println("padma payment type adapter is called"+adapter.getCount());
									
									/*Toast.makeText(getApplicationContext(),
											"Pay Reamaining Amount", 1000)
											.show();*/
								}
							}
						} else {
							Toast.makeText(getApplicationContext(),
									"Enter Amount", 1000).show();
						}
					} else if (paymentTypestr.equals("Check")) {
						
						if (remaining.getText().toString().trim().length() > 0) {
							Double given = Double.valueOf(remaining.getText()
									.toString().trim());
							if (given == null || given == 0.00) {
								Toast.makeText(getApplicationContext(),
										"Enter Amount", 1000).show();
							} else {
								if (given >= remainingamount) {
									change = given - remainingamount;
									change = (double) Math.round(change * 100)
											/ (double) 100;
									//padma
									checkPayType(0.0, remaining.getText().toString(), "", "");
									alertDialogtop.dismiss();
									//showchangepopup(change);
								} else {
									remainingamount = remainingamount - given;
									remainingamount = (int) Math
											.round(remainingamount * 100)
											/ (double) 100;
									total.setText("$" + remainingamount);
									cashpaymentAmount = Math
											.ceil(remainingamount);
									bigbutton.setText("" + cashpaymentAmount);
									
									//padma
									checkPayTypeforNotComplete(0.0, remaining.getText().toString(), "", "", paymentTypestr);
									remaining.setText(""+remainingamount);	
									/*Toast.makeText(getApplicationContext(),
											"Pay Reamaining Amount", 1000)
											.show();*/
								}
							}
						} else {
							Toast.makeText(getApplicationContext(),
									"Enter Amount", 1000).show();
						}
						//pathi
					} else if (paymentTypestr.equals("Credit/Debit")) {
						
						if (remaining.getText().toString().trim().length() > 0) {
							Double given = Double.valueOf(remaining.getText()
									.toString().trim());
							if (given == null || given == 0.00) {
								Toast.makeText(getApplicationContext(),
										"Enter Amount", 1000).show();
							} else {
								if (given >= remainingamount) {
									if(Parameters.paymentprocesstype.equals("Express Manual")){
										System.out.println("express manual is encountered");
										change = given - remainingamount;
										change = (double) Math.round(change * 100)
												/ (double) 100;
									//	alertDialogtop.dismiss();
										changeforcredit = 0.0;
										remainingforcredit = remaining.getText().toString();
										checkforcredit = "";
										accforcredit = "";
										alertDialogtop.dismiss();
										creditPayType(changeforcredit, remainingforcredit, accforcredit, checkforcredit);
										//showReciptPopup();
									}else{
									change = given - remainingamount;
									change = (double) Math.round(change * 100)
											/ (double) 100;
									//Toast.makeText(PosMainActivity.this, "This is first data payment", Toast.LENGTH_SHORT).show();
								//	alertDialogtop.dismiss();
									if(lastsavedinvoiceid!=null){
										Parameters.invoiceid_mercury=lastsavedinvoiceid;
									}else{
										Parameters.invoiceid_mercury=Parameters.generateRandomNumber();
										lastsavedinvoiceid=Parameters.invoiceid_mercury;
									}
									directpayment = true;
									Parameters.Cridetamount=given;
									Intent intent = new Intent(PosMainActivity.this,MagTekDemo.class);
									startActivityForResult(intent, 1);
									changeforcredit = 0.0;
									remainingforcredit = remaining.getText().toString();
									checkforcredit = "";
									accforcredit = "";
									//creditPayType(changeforcredit, remainingforcredit, accforcredit, checkforcredit);
									}
								
								} else {
									if(Parameters.paymentprocesstype.equals("Express Manual")){
										System.out.println("express manual is encountered");
										remainingamount = remainingamount - given;
										remainingamount = (int) Math
												.round(remainingamount * 100)
												/ (double) 100;
										total.setText("$" + remainingamount);
										cashpaymentAmount = Math
												.ceil(remainingamount);
										bigbutton.setText("" + cashpaymentAmount);
										
										//padma
										String val = remaining.getText().toString();
										changeforcredit = 0.0;
										remainingforcredit = remaining.getText().toString();
										checkforcredit = "";
										accforcredit = "";
										remaining.setText(""+remainingamount);
										creditPayTypeforNotComplete(changeforcredit, remainingforcredit, accforcredit, checkforcredit, paymentTypestr);
										PaymentTypeAdapter adapter = new PaymentTypeAdapter(PosMainActivity.this, payedsofarlist);
										listviewpayedsofar.setAdapter(adapter);
									}else{
//									remainingamount = remainingamount - given;
//									remainingamount = (int) Math
//											.round(remainingamount * 100)
//											/ (double) 100;
//									total.setText("$" + remainingamount);
//									cashpaymentAmount = Math
//											.ceil(remainingamount);
//									bigbutton.setText("" + cashpaymentAmount);
//									
//									directpayment = false;
//									changeforcredit = 0.0;
//									remainingforcredit = remaining.getText().toString();
//									checkforcredit = "";
//									accforcredit = "";
//									remaining.setText(""+remainingamount);
								//	Toast.makeText(PosMainActivity.this, "This is first data payment", Toast.LENGTH_SHORT).show();
									
								/*	creditPayTypeforNotComplete(changeforcredit, remainingforcredit, accforcredit, checkforcredit, paymentTypestr);
									PaymentTypeAdapter adapter = new PaymentTypeAdapter(PosMainActivity.this, payedsofarlist);
									listviewpayedsofar.setAdapter(adapter);*/
										if(lastsavedinvoiceid!=null){
											Parameters.invoiceid_mercury=lastsavedinvoiceid;
										}else{
											Parameters.invoiceid_mercury=Parameters.generateRandomNumber();
											lastsavedinvoiceid=Parameters.invoiceid_mercury;
										}
									Parameters.Cridetamount=given;
									Intent intent = new Intent(PosMainActivity.this,MagTekDemo.class);
									startActivityForResult(intent, 1);
									
									}
								}
							}
						} else {
							Toast.makeText(getApplicationContext(),
									"Enter Amount", 1000).show();
						}
						/* showReciptPopup();
						creditROdebitPayType();*/

					} else if (paymentTypestr.equals("Account")) {						
						if(account == false){
							Toast.makeText(getApplicationContext(),
									"Add Coustomer Account", 1000).show();
						}else{
						if (remaining.getText().toString().trim().length() > 0) {
							Double given = Double.valueOf(remaining.getText()
									.toString().trim());
							if (given == null || given == 0.00) {
								Toast.makeText(getApplicationContext(),
										"Enter Amount", 1000).show();
							} else {
								if (given >= remainingamount) {
									change = given - remainingamount;
									change = (double) Math.round(change * 100)
											/ (double) 100;
									alertDialogtop.dismiss();
									//padma
									//showReciptPopup();
									Toast.makeText(getApplicationContext(),
											"Add Coustomer Account", 1000).show();
									/*checkPayType();
									showReciptPopup();*/
									//showchangepopup(change);
								} else {
									remainingamount = remainingamount - given;
									remainingamount = (int) Math
											.round(remainingamount * 100)
											/ (double) 100;
									total.setText("$" + remainingamount);
									cashpaymentAmount = Math
											.ceil(remainingamount);
									bigbutton.setText("" + cashpaymentAmount);
									
									//padma
									Toast.makeText(getApplicationContext(),
											"Add Coustomer Account", 1000).show();
									remaining.setText(""+remainingamount);
									
									/*Toast.makeText(getApplicationContext(),
											"Pay Reamaining Amount", 1000)
											.show();*/
								}
							}
						} else {
							Toast.makeText(getApplicationContext(),
									"Enter Amount", 1000).show();
						}
						}
					}

				}
			});

			cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					alertDialogtop.dismiss();
				}
			});
			alertDialogtop.setView(layouttop);
			alertDialogtop.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			alertDialogtop.show();
		} else {
			Toast.makeText(getApplicationContext(), "Add Item",
					Toast.LENGTH_LONG).show();
		}
	} catch (NumberFormatException e) {
		  e.printStackTrace();
		} catch (SQLiteException e12) {
			  e12.printStackTrace();
			} catch (Exception e1) {
			  e1.printStackTrace();
			}
	}

	void printPaper(String name_value) {
		try {

			File printerDirectory = new File("/sdcard/printData");
			if (!printerDirectory.exists()) {
				printerDirectory.mkdirs();
			}
			File myFile = new File("/sdcard/printData/printRecipt.txt");
			myFile.createNewFile();
			FileOutputStream fOut = new FileOutputStream(myFile);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			myOutWriter.append(name_value);
			myOutWriter.close();
			fOut.close();
			Toast.makeText(getBaseContext(), " Done ", Toast.LENGTH_SHORT)
					.show();
		} catch (Exception e) {
			Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}

	}

	void showReciptPopup() {
		customer_id = "101";
		customer_phone = " ";
		customer_company = " "; 
		customer_first = "Cash Customer";
		customer_last = " ";
		customerDetailsText.setText("Customer No = " + customer_id
				+ ", \n Customer Name = " + customer_first + " "
				+ customer_last);
		mItemList.clear();
		mAdapter.notifyDataSetChanged();
		mSelectedItem = null;
		mSelectedPosition = -1;
		if (mItemList.isEmpty()) {
			mSubTotal = 0;
			mTaxTotal = 0;
			fetchOnHoldButton.setTag("1");
			fetchOnHoldButton.setText("Fetch On Hold");
			subTotalView.setText(String.valueOf("$" + mSubTotal));
			taxTotalview.setText(String.valueOf("$" + mTaxTotal));
			grandTotalview.setText(String.valueOf("$" + mSubTotal));
		}
		Bitmap map=null;
		showAlertDialogforPrint(PosMainActivity.this,"","Do You Want Recipt?",true,"printer1", "", map);
		
	//	printText("printer1", payclip,map);
	}

	/*void cashPayType(Double change) {
		if (mode) {
			String invoice_id = store_id + "_" + Parameters.randomInvoice();
			Log.e("invoice", Parameters.randomInvoice() + "  "
					+ invoice_forHold);
			hold_Status = "complete";
			createPrintRecipt(invoice_id, "");
		}
		final AlertDialog alertDialog12 = new AlertDialog.Builder(
				PosMainActivity.this).create();
		LayoutInflater mInflater2 = LayoutInflater.from(PosMainActivity.this);
		View layout2 = mInflater2.inflate(R.layout.change_show, null);
		alertDialog12.setTitle("Remaining Change");
		final EditText totalchange = (EditText) layout2
				.findViewById(R.id.changedit);
		totalchange.setText("" + change);
		Button okchange = (Button) layout2.findViewById(R.id.changeok);
		okchange.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				alertDialog12.dismiss();
				showReciptPopup();

			}
		});
		alertDialog12.setView(layout2);
		alertDialog12.getWindow().setSoftInputMode(
				WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
		alertDialog12.show();

		// showReciptPopup();
	}*/

	/*void checkPayType() {
		if (mItemList.size() > 0) {
			if (mode) {
				String invoice_id = store_id + "_" + Parameters.randomInvoice();
				Log.e("invoice", Parameters.randomInvoice() + "  "
						+ invoice_forHold);
				hold_Status = "complete";
				createPrintRecipt(invoice_id, "");
			}
			final AlertDialog alertDialogCheck = new AlertDialog.Builder(
					PosMainActivity.this).create();
			LayoutInflater mInflaterCheck = LayoutInflater
					.from(PosMainActivity.this);
			View layoutCheck = mInflaterCheck.inflate(R.layout.change_show,
					null);
			alertDialogCheck.setTitle("Enter Check Number");
			final EditText totalchange = (EditText) layoutCheck
					.findViewById(R.id.changedit);
			// createPrintRecipt();
			Button okchange = (Button) layoutCheck.findViewById(R.id.changeok);
			okchange.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String value = totalchange.getText().toString().trim();
					if (value.length() > 2) {
						showReciptPopup();
						alertDialogCheck.dismiss();
					} else {
						Toast.makeText(getApplicationContext(),
								"Enter Check Number", 2000).show();
					}
				}
			});
			alertDialogCheck.setView(layoutCheck);
			alertDialogCheck.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			alertDialogCheck.show();

		} else {
			Toast.makeText(getApplicationContext(), "Add Item",
					Toast.LENGTH_LONG).show();
		}
	}*/

/*	void creditROdebitPayType() {
		PayPalPayment payment = new PayPalPayment(new BigDecimal(""
				+ mGrandTotal), "USD", "sales");

		Intent intent = new Intent(PosMainActivity.this, PaymentActivity.class);
		intent.putExtra(PaymentActivity.EXTRA_PAYPAL_ENVIRONMENT,
				PaymentActivity.ENVIRONMENT_SANDBOX);
		intent.putExtra(PaymentActivity.EXTRA_CLIENT_ID,
				"AcSD5hA7phPfAsXzPd85LZ43vUdzEX5Bkf2P8tuTtXBCN1f3RDq_GRBuxGdM");
		intent.putExtra(PaymentActivity.EXTRA_PAYER_ID, "");

		intent.putExtra(PaymentActivity.EXTRA_RECEIVER_EMAIL,
				"rameshbabu.nanotech@gmail.com");
		intent.putExtra(PaymentActivity.EXTRA_PAYMENT, payment);

		startActivityForResult(intent, 0);

		if (mItemList.size() > 0) {

			showReciptPopup();
		} else {
			Toast.makeText(getApplicationContext(), "Add Item",
					Toast.LENGTH_LONG).show();
		}

	}*/
	
	void showchangepopup(Double change){
		String listofitems = "";
		Double AvgCost = 0.00;
		String timeC = Parameters.currentTime();
		for (int i = 0; i < mItemList.size(); i++) {
			String u_id0 = Parameters.randomValue();
			String Qtty = mItemList.get(i).getQuantity();
			String nameI = mItemList.get(i).getItemNameAdd();
			String nameId = mItemList.get(i).getItemNoAdd();
			String pricc = mItemList.get(i).getPriceYouChange();
			String AvgCos = mItemList.get(i).getAvgCost();
			String Description = mItemList.get(i).getSecondDescription();
			String taxx = mItemList.get(i).getInventoryTaxTotal();
			String department = mItemList.get(i).getDepartmentAdd();
			String vendor = mItemList.get(i).getInventoryVndr();
			String itemone = "<tr style='height:40px;overflow:hidden;' valign='top'><td align='left'>"+Qtty+" "+nameI+"</td><td align='right'>$"+pricc+"</td></tr>";
			listofitems = listofitems + itemone;
			AvgCost += Double.valueOf(AvgCos);
		}
		printStringCreation(lastsavedinvoiceid, listofitems, Parameters.Cridetamount, 0.0, 0.0, 0.0);
	lastsavedinvoiceid = null;
	lastpaymenttypeString = "";
	customer_phone = " ";
	customer_company = " ";
	customer_first = "Cash Customer";
	customer_last = " "; 
	customer_id = "101";
	customerDetailsText.setText("Customer No = " + customer_id
			+ ", \n Customer Name = " + customer_first + " "
			+ customer_last);
	final AlertDialog alertDialog12 = new AlertDialog.Builder(
			PosMainActivity.this).create();
	LayoutInflater mInflater2 = LayoutInflater.from(PosMainActivity.this);
	View layout2 = mInflater2.inflate(R.layout.change_show, null);
	alertDialog12.setTitle("Remaining Change");
	final EditText totalchange = (EditText) layout2
			.findViewById(R.id.changedit);
	totalchange.setText("" + change);
	Button okchange = (Button) layout2.findViewById(R.id.changeok);
	okchange.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			alertDialog12.dismiss();
			
			showReciptPopup();

		}
	});
	alertDialog12.setView(layout2);
	alertDialog12.getWindow().setSoftInputMode(
			WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
	alertDialog12.show();
	}

	void cashPayType(Double change, String remaining, String accountno, String chequeno) {
		try{
		if (mode) {
			String paymenttypeval = paymentTypestr;
			//padma
			if(invoiceidexist.equals("")){
			if(lastsavedinvoiceid!=null){
				System.out.println("updated invoice is called");
				updateinvoicetable(lastsavedinvoiceid, "complete", "", "", "");
				updateTheInvoice(lastsavedinvoiceid, remaining, accountno, chequeno, paymenttypeval);
			}else{
				System.out.println("invoice id exist value is padmavathi555555555555555"+ invoiceidexist);
			String invoice_id =  Parameters.generateRandomNumber();
			Log.e("invoice", Parameters.generateRandomNumber() + "  "
					+ invoice_forHold);
			hold_Status = "complete";
			lastsavedinvoiceid = invoice_id;
			System.out.println("last saved invoice id is:"+lastsavedinvoiceid);
			createPrintRecipt(invoice_id, "", Double.parseDouble(remaining), 0.0, 0.0, 0.0, "", paymentTypestr);
			}
			}else{
				System.out.println("hold invoice id val is 555555:"+invoiceidexist);
				if(lastsavedinvoiceid!=null){
					System.out.println("updated invoice is called");
					updateinvoicetable(invoiceidexist, "complete", "", "multiple", "");
					updateTheInvoice(invoiceidexist, remaining, accountno, chequeno, paymenttypeval);
					invoiceidexist = "";
					holdidexist = "";
				}else{
				//String invoice_id =  Parameters.generateRandomNumber();
				//Log.e("invoice", Parameters.generateRandomNumber() + "  "
					//	+ invoice_forHold);
				hold_Status = "complete";
				lastsavedinvoiceid = invoiceidexist;
				System.out.println("last saved invoice id is:"+lastsavedinvoiceid);
				updateinvoicetable(lastsavedinvoiceid, "complete", "", paymenttypeval, "");
				//createPrintRecipt(invoiceidexist, "", Double.parseDouble(remaining), 0.0, 0.0, 0.0, "");
				}
			}
		}
		// showReciptPopup();
		} catch (NumberFormatException e) {
			  e.printStackTrace();
			} catch (SQLiteException e12) {
				  e12.printStackTrace();
				} catch (Exception e1) {
				  e1.printStackTrace();
				}
	}
	
	void cashPayTypeforNotComplete(Double change, String remaining, String accountno, String chequeno, String paymenttypeval){
	try{
		if(mode){
			if(invoiceidexist.equals("")){
			if(lastsavedinvoiceid == null){
				String invoice_id =  Parameters.generateRandomNumber();
				Log.e("invoice", Parameters.generateRandomNumber() + "  "
						+ invoice_forHold);
				hold_Status = "incomplete";
				paymentTypestr = "multiple";
				lastsavedinvoiceid = invoice_id;
				System.out.println("last saved invoice id is:"+lastsavedinvoiceid);
				createPrintRecipt(invoice_id, "", Double.parseDouble(remaining), 0.0, 0.0, 0.0, "", paymentTypestr);
				paymentTypestr = paymenttypeval;
				updateTheInvoice(lastsavedinvoiceid, remaining, accountno, chequeno, paymenttypeval);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("paymentType", paymenttypeval);
				map.put("amount", remaining);
				map.put("details", "");
				payedsofarlist.add(map);
			}else{
				updateTheInvoice(lastsavedinvoiceid, remaining, accountno, chequeno, paymenttypeval);
				String listofitems = "";
				Double AvgCost = 0.00;
				String timeC = Parameters.currentTime();
				for (int i = 0; i < mItemList.size(); i++) {
					String u_id0 = Parameters.randomValue();
					String Qtty = mItemList.get(i).getQuantity();
					String nameI = mItemList.get(i).getItemNameAdd();
					String nameId = mItemList.get(i).getItemNoAdd();
					String pricc = mItemList.get(i).getPriceYouChange();
					String AvgCos = mItemList.get(i).getAvgCost();
					String Description = mItemList.get(i).getSecondDescription();
					String taxx = mItemList.get(i).getInventoryTaxTotal();
					String department = mItemList.get(i).getDepartmentAdd();
					String vendor = mItemList.get(i).getInventoryVndr();
					String itemone = "<tr style='height:40px;overflow:hidden;' valign='top'><td align='left'>"+Qtty+" "+nameI+"</td><td align='right'>$"+pricc+"</td></tr>";
					listofitems = listofitems + itemone;
					AvgCost += Double.valueOf(AvgCos);
				}
				printStringCreation(lastsavedinvoiceid, listofitems, Double.parseDouble(remaining), 0.0, 0.0, 0.0);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("paymentType", paymenttypeval);
				map.put("amount", remaining);
				map.put("details", "");
				payedsofarlist.add(map);
				System.out.println("invoice is updated");
			}
			}else{
				System.out.println("hold id val is:9999999"+invoiceidexist);
				if(lastsavedinvoiceid == null){
				//	String invoice_id =  Parameters.generateRandomNumber();
				//	Log.e("invoice", Parameters.generateRandomNumber() + "  "
				//			+ invoice_forHold);
					hold_Status = "incomplete";
					paymentTypestr = "multiple";
					lastsavedinvoiceid = invoiceidexist;
					System.out.println("last saved invoice id is:"+lastsavedinvoiceid);
					updateinvoicetable(lastsavedinvoiceid, hold_Status, "", "multiple", "");
				//	createPrintRecipt(invoiceidexist, "", Double.parseDouble(remaining), 0.0, 0.0, 0.0, "");
					paymentTypestr = paymenttypeval;
					updateTheInvoice(lastsavedinvoiceid, remaining, accountno, chequeno, paymenttypeval);
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("paymentType", paymenttypeval);
					map.put("amount", remaining);
					map.put("details", "");
					payedsofarlist.add(map);
				}else{
					updateTheInvoice(invoiceidexist, remaining, accountno, chequeno, paymenttypeval);
					String listofitems = "";
					Double AvgCost = 0.00;
					String timeC = Parameters.currentTime();
					for (int i = 0; i < mItemList.size(); i++) {
						String u_id0 = Parameters.randomValue();
						String Qtty = mItemList.get(i).getQuantity();
						String nameI = mItemList.get(i).getItemNameAdd();
						String nameId = mItemList.get(i).getItemNoAdd();
						String pricc = mItemList.get(i).getPriceYouChange();
						String AvgCos = mItemList.get(i).getAvgCost();
						String Description = mItemList.get(i).getSecondDescription();
						String taxx = mItemList.get(i).getInventoryTaxTotal();
						String department = mItemList.get(i).getDepartmentAdd();
						String vendor = mItemList.get(i).getInventoryVndr();
						String itemone = "<tr style='height:40px;overflow:hidden;' valign='top'><td align='left'>"+Qtty+" "+nameI+"</td><td align='right'>$"+pricc+"</td></tr>";
						listofitems = listofitems + itemone;
						AvgCost += Double.valueOf(AvgCos);
					}
					printStringCreation(invoiceidexist, listofitems, Double.parseDouble(remaining), 0.0, 0.0, 0.0);
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("paymentType", paymenttypeval);
					map.put("amount", remaining);
					map.put("details", "");
					payedsofarlist.add(map);
					System.out.println("invoice is updated");
				}
			}
		}
	} catch (NumberFormatException e) {
		  e.printStackTrace();
		} catch (SQLiteException e12) {
			  e12.printStackTrace();
			} catch (Exception e1) {
			  e1.printStackTrace();
			}
	}
	
	void creditPayType(Double change, String remaining, String accountno, String chequeno) {
		try{
		if (mode) {
			String paymenttypeval = paymentTypestr;
			//padma
			if(invoiceidexist.equals("")){
			if(lastsavedinvoiceid!=null){
				System.out.println("updated invoice is called");
				updateinvoicetable(lastsavedinvoiceid, "complete", "", paymenttypeval, "");
				updateTheInvoice(lastsavedinvoiceid, remaining, accountno, chequeno, paymenttypeval);
				if(Parameters.paymentprocesstype.equals("Express Manual")){
					System.out.println("complete credit payment popup");
					showAlertDialogforcredit(PosMainActivity.this, "Payment Alert", "Payment successfully completed", true);
				}else{
				//	showReciptPopup();
				}
			}else{
			String invoice_id = Parameters.invoiceid_mercury;
			hold_Status = "complete";
			lastsavedinvoiceid = invoice_id;
			System.out.println("last saved invoice id is:"+lastsavedinvoiceid);
			createPrintRecipt(invoice_id, "", 0.0, 0.0, 0.0, Double.parseDouble(remaining), "", paymentTypestr); //mcp
			if(Parameters.paymentprocesstype.equals("Express Manual")){
				System.out.println("complete credit payment popup");
				showAlertDialogforcredit(PosMainActivity.this, "Payment Alert", "Payment successfully completed", true);
			}else{
			//	showReciptPopup();
			}
			}
			}else{
				
				if(lastsavedinvoiceid!=null){
					System.out.println("updated invoice is called");
					updateinvoicetable(invoiceidexist, "complete", "", "multiple", "");
					updateTheInvoice(invoiceidexist, remaining, accountno, chequeno, paymenttypeval);
					if(Parameters.paymentprocesstype.equals("Express Manual")){
						System.out.println("complete credit payment popup");
						showAlertDialogforcredit(PosMainActivity.this, "Payment Alert", "Payment successfully completed", true);
					}else{
					//	showReciptPopup();
					}
					invoiceidexist = "";
					holdidexist = "";
				}else{
				//String invoice_id =  Parameters.generateRandomNumber();
				//Log.e("invoice", Parameters.generateRandomNumber() + "  "
					//	+ invoice_forHold);
				hold_Status = "complete";
				lastsavedinvoiceid = invoiceidexist;
				System.out.println("last saved invoice id is:"+lastsavedinvoiceid);
				updateinvoicetable(invoiceidexist, "complete", "", paymenttypeval, "");
				//createPrintRecipt(invoiceidexist, "", 0.0, 0.0, 0.0, Double.parseDouble(remaining), "");
				if(Parameters.paymentprocesstype.equals("Express Manual")){
					System.out.println("complete credit payment popup");
					showAlertDialogforcredit(PosMainActivity.this, "Payment Alert", "Payment successfully completed", true);
				}else{
				//	showReciptPopup();
				}
				}
			}
		}
		// showReciptPopup();
		} catch (NumberFormatException e) {
			  e.printStackTrace();
			} catch (SQLiteException e12) {
				  e12.printStackTrace();
				} catch (Exception e1) {
				  e1.printStackTrace();
				}
	}
	
	void creditPayTypeforNotComplete(Double change, String remaining, String accountno, String chequeno, String paymenttypeval){
		try{
		if(mode){
			if(invoiceidexist.equals("")){
			if(lastsavedinvoiceid == null){
				String invoice_id =  Parameters.generateRandomNumber();
				Log.e("invoice", invoice_id + "  "
						+ invoice_forHold);
				hold_Status = "incomplete";
				paymentTypestr = "multiple";
				lastsavedinvoiceid = invoice_id;
				System.out.println("last saved invoice id is:"+lastsavedinvoiceid);
				createPrintRecipt(invoice_id, "", 0.0, 0.0, 0.0, Double.parseDouble(remaining), "", paymentTypestr);
				paymentTypestr = paymenttypeval;
				updateTheInvoice(lastsavedinvoiceid, remaining, accountno, chequeno, paymenttypeval);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("paymentType", paymenttypeval);
				map.put("amount", remaining);
				map.put("details", "");
				payedsofarlist.add(map);
				if(Parameters.paymentprocesstype.equals("Express Manual")){
					showAlertDialogforcredit(PosMainActivity.this, "Payment Alert", "Payment successfully completed", true);
				}else{
					Intent intentmercury=new Intent(PosMainActivity.this,MagTekDemo.class);
					startActivityForResult(intentmercury, 1);
					//showReciptPopup();
				}
			}else{
				updateTheInvoice(lastsavedinvoiceid, remaining, accountno, chequeno, paymenttypeval);
				String listofitems = "";
				Double AvgCost = 0.00;
				String timeC = Parameters.currentTime();
				for (int i = 0; i < mItemList.size(); i++) {
					String u_id0 = Parameters.randomValue();
					String Qtty = mItemList.get(i).getQuantity();
					String nameI = mItemList.get(i).getItemNameAdd();
					String nameId = mItemList.get(i).getItemNoAdd();
					String pricc = mItemList.get(i).getPriceYouChange();
					String AvgCos = mItemList.get(i).getAvgCost();
					String Description = mItemList.get(i).getSecondDescription();
					String taxx = mItemList.get(i).getInventoryTaxTotal();
					String department = mItemList.get(i).getDepartmentAdd();
					String vendor = mItemList.get(i).getInventoryVndr();
					String itemone = "<tr style='height:40px;overflow:hidden;' valign='top'><td align='left'>"+Qtty+" "+nameI+"</td><td align='right'>$"+pricc+"</td></tr>";
					listofitems = listofitems + itemone;
					AvgCost += Double.valueOf(AvgCos);
				}
				printStringCreation(lastsavedinvoiceid, listofitems, Double.parseDouble(remaining), 0.0, 0.0, 0.0);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("paymentType", paymenttypeval);
				map.put("amount", remaining);
				map.put("details", "");
				payedsofarlist.add(map);
				if(Parameters.paymentprocesstype.equals("Express Manual")){
					showAlertDialog(PosMainActivity.this, "Payment Alert", "Payment successfully completed", true);
				}else{
					Intent intentmercury=new Intent(PosMainActivity.this,MagTekDemo.class);
					startActivityForResult(intentmercury, 1);
				}
				System.out.println("invoice is updated");
			}
			}else{
				if(lastsavedinvoiceid == null){
				//	String invoice_id =  Parameters.generateRandomNumber();
				//	Log.e("invoice", Parameters.generateRandomNumber() + "  "
					//		+ invoice_forHold);
					hold_Status = "incomplete";
					paymentTypestr = "multiple";
					lastsavedinvoiceid = invoiceidexist;
					Parameters.invoiceid_mercury= invoiceidexist;
					System.out.println("last saved invoice id is:"+lastsavedinvoiceid);
					updateinvoicetable(lastsavedinvoiceid, hold_Status, "", "multiple", "");
				//	createPrintRecipt(lastsavedinvoiceid, "", 0.0, 0.0, 0.0, Double.parseDouble(remaining), "");
					paymentTypestr = paymenttypeval;
					updateTheInvoice(lastsavedinvoiceid, remaining, accountno, chequeno, paymenttypeval);
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("paymentType", paymenttypeval);
					map.put("amount", remaining);
					map.put("details", "");
					payedsofarlist.add(map);
					if(Parameters.paymentprocesstype.equals("Express Manual")){
						showAlertDialogforcredit(PosMainActivity.this, "Payment Alert", "Payment successfully completed", true);
					}else{
						Intent intentmercury=new Intent(PosMainActivity.this,MagTekDemo.class);
						startActivityForResult(intentmercury, 1);
						//showReciptPopup();
					}
				}else{
					updateTheInvoice(invoiceidexist, remaining, accountno, chequeno, paymenttypeval);
					HashMap<String, String> map = new HashMap<String, String>();
					map.put("paymentType", paymenttypeval);
					map.put("amount", remaining);
					map.put("details", "");
					payedsofarlist.add(map);
					if(Parameters.paymentprocesstype.equals("Express Manual")){
						showAlertDialog(PosMainActivity.this, "Payment Alert", "Payment successfully completed", true);
					}else{
						Intent intentmercury=new Intent(PosMainActivity.this,MagTekDemo.class);
						startActivityForResult(intentmercury, 1);
					}
					System.out.println("invoice is updated");
				}
			}
		}
		} catch (NumberFormatException e) {
			  e.printStackTrace();
			} catch (SQLiteException e12) {
				  e12.printStackTrace();
				} catch (Exception e1) {
				  e1.printStackTrace();
				}
	}
	void creditPayTypeforMercuryNotComplete(Double change, String remaining, String accountno, String chequeno, String paymenttypeval){
		try{
		if(mode){
			if(invoiceidexist.equals("")){
			if(lastsavedinvoiceid == null){
				String invoice_id =  Parameters.generateRandomNumber();
				Log.e("invoice", invoice_id + "  "
						+ invoice_forHold);
				hold_Status = "incomplete";
				paymentTypestr = "multiple";
				lastsavedinvoiceid = invoice_id;
				System.out.println("last saved invoice id is:"+lastsavedinvoiceid);
				createPrintRecipt(invoice_id, "", 0.0, 0.0, 0.0, Double.parseDouble(remaining), "", paymentTypestr);
				paymentTypestr = paymenttypeval;
				updateTheInvoice(lastsavedinvoiceid, remaining, accountno, chequeno, paymenttypeval);
				/*HashMap<String, String> map = new HashMap<String, String>();
				map.put("paymentType", paymenttypeval);
				map.put("amount", remaining);
				map.put("details", "");
				payedsofarlist.add(map);*/
			}else{
				updateTheInvoice(lastsavedinvoiceid, remaining, accountno, chequeno, paymenttypeval);
				String listofitems = "";
				Double AvgCost = 0.00;
				String timeC = Parameters.currentTime();
				for (int i = 0; i < mItemList.size(); i++) {
					String u_id0 = Parameters.randomValue();
					String Qtty = mItemList.get(i).getQuantity();
					String nameI = mItemList.get(i).getItemNameAdd();
					String nameId = mItemList.get(i).getItemNoAdd();
					String pricc = mItemList.get(i).getPriceYouChange();
					String AvgCos = mItemList.get(i).getAvgCost();
					String Description = mItemList.get(i).getSecondDescription();
					String taxx = mItemList.get(i).getInventoryTaxTotal();
					String department = mItemList.get(i).getDepartmentAdd();
					String vendor = mItemList.get(i).getInventoryVndr();
					String itemone = "<tr style='height:40px;overflow:hidden;' valign='top'><td align='left'>"+Qtty+" "+nameI+"</td><td align='right'>$"+pricc+"</td></tr>";
					listofitems = listofitems + itemone;
					AvgCost += Double.valueOf(AvgCos);
				}
				printStringCreation(lastsavedinvoiceid, listofitems, Double.parseDouble(remaining), 0.0, 0.0, 0.0);
				/*HashMap<String, String> map = new HashMap<String, String>();
				map.put("paymentType", paymenttypeval);
				map.put("amount", remaining);
				map.put("details", "");
				payedsofarlist.add(map);*/
				System.out.println("invoice is updated");
			}
			}else{
				if(lastsavedinvoiceid == null){
				//	String invoice_id =  Parameters.generateRandomNumber();
				//	Log.e("invoice", Parameters.generateRandomNumber() + "  "
					//		+ invoice_forHold);
					hold_Status = "incomplete";
					paymentTypestr = "multiple";
					lastsavedinvoiceid = invoiceidexist;
					Parameters.invoiceid_mercury = invoiceidexist;
					System.out.println("last saved invoice id is:"+lastsavedinvoiceid);
					updateinvoicetable(lastsavedinvoiceid, hold_Status, "", "multiple", "");
				//	createPrintRecipt(lastsavedinvoiceid, "", 0.0, 0.0, 0.0, Double.parseDouble(remaining), "");
					paymentTypestr = paymenttypeval;
					updateTheInvoice(lastsavedinvoiceid, remaining, accountno, chequeno, paymenttypeval);
					/*HashMap<String, String> map = new HashMap<String, String>();
					map.put("paymentType", paymenttypeval);
					map.put("amount", remaining);
					map.put("details", "");
					payedsofarlist.add(map);*/
					
				}else{
					updateTheInvoice(invoiceidexist, remaining, accountno, chequeno, paymenttypeval);
					String listofitems = "";
					Double AvgCost = 0.00;
					String timeC = Parameters.currentTime();
					for (int i = 0; i < mItemList.size(); i++) {
						String u_id0 = Parameters.randomValue();
						String Qtty = mItemList.get(i).getQuantity();
						String nameI = mItemList.get(i).getItemNameAdd();
						String nameId = mItemList.get(i).getItemNoAdd();
						String pricc = mItemList.get(i).getPriceYouChange();
						String AvgCos = mItemList.get(i).getAvgCost();
						String Description = mItemList.get(i).getSecondDescription();
						String taxx = mItemList.get(i).getInventoryTaxTotal();
						String department = mItemList.get(i).getDepartmentAdd();
						String vendor = mItemList.get(i).getInventoryVndr();
						String itemone = "<tr style='height:40px;overflow:hidden;' valign='top'><td align='left'>"+Qtty+" "+nameI+"</td><td align='right'>$"+pricc+"</td></tr>";
						listofitems = listofitems + itemone;
						AvgCost += Double.valueOf(AvgCos);
					}
					printStringCreation(invoiceidexist, listofitems, Double.parseDouble(remaining), 0.0, 0.0, 0.0);
					/*HashMap<String, String> map = new HashMap<String, String>();
					map.put("paymentType", paymenttypeval);
					map.put("amount", remaining);
					map.put("details", "");
					payedsofarlist.add(map);*/
					System.out.println("invoice is updated");
				}
			}
		}
		} catch (NumberFormatException e) {
			  e.printStackTrace();
			} catch (SQLiteException e12) {
				  e12.printStackTrace();
				} catch (Exception e1) {
				  e1.printStackTrace();
				}
	}
	
	
	void updateinvoicetable(String invoice_id, String status, String holdid, String paymenttype, String checkno){
		try{
		String query;
		System.out.println("343434343434343434343434 payment type val is:"+paymenttype);
		if(paymenttype.equals("") && checkno.equals("")){
			query = "update "+DatabaseForDemo.INVOICE_TOTAL_TABLE+" set "+DatabaseForDemo.INVOICE_STATUS+" =\""+status+"\", "
					+DatabaseForDemo.INVOICE_HOLD_ID+"=\""+holdid+"\" where "+DatabaseForDemo.INVOICE_ID+" =\""+invoice_id+"\"";
		}else if(paymenttype.equals("") && !checkno.equals("")){
			query = "update "+DatabaseForDemo.INVOICE_TOTAL_TABLE+" set "+DatabaseForDemo.INVOICE_STATUS+" =\""+status+"\", "
					+DatabaseForDemo.INVOICE_HOLD_ID+"=\""+holdid+"\", "+DatabaseForDemo.INVOICE_CHEQUE_NO+"=\""+checkno
					+"\" where "+DatabaseForDemo.INVOICE_ID+" =\""+invoice_id+"\"";
		}else if(!paymenttype.equals("") && checkno.equals("")){
			query = "update "+DatabaseForDemo.INVOICE_TOTAL_TABLE+" set "+DatabaseForDemo.INVOICE_STATUS+" =\""+status+"\", "
								+DatabaseForDemo.INVOICE_HOLD_ID+"=\""+holdid+"\", "+DatabaseForDemo.INVOICE_PAYMENT_TYPE+"=\""+paymenttype
								+"\" where "+DatabaseForDemo.INVOICE_ID+" =\""+invoice_id+"\"";
		}else{
			query = "update "+DatabaseForDemo.INVOICE_TOTAL_TABLE+" set "+DatabaseForDemo.INVOICE_STATUS+" =\""+status+"\", "
							+DatabaseForDemo.INVOICE_HOLD_ID+"=\""+holdid+"\", "+DatabaseForDemo.INVOICE_PAYMENT_TYPE+"=\""+paymenttype+"\", "
							+DatabaseForDemo.INVOICE_CHEQUE_NO+"=\""+checkno+"\" where "+DatabaseForDemo.INVOICE_ID+
							" =\""+invoice_id+"\"";
		}
		dbforloginlogoutWritePos.execSQL(query);
		
		final ArrayList<JSONObject> getlist = new ArrayList<JSONObject>();
		String selectQueryforinstantpo1 = "SELECT  * FROM "
				+ DatabaseForDemo.INVOICE_TOTAL_TABLE + " where "
				+ DatabaseForDemo.INVOICE_ID + "=\"" + invoice_id
				+ "\"";

		Cursor mCursorforvendor2 = dbforloginlogoutReadPos.rawQuery(selectQueryforinstantpo1, null);
		System.out.println("cursor val count is:"+ mCursorforvendor2.getCount());

		if (mCursorforvendor2 != null) {
			if (mCursorforvendor2.moveToFirst()) {
				do {
					try {
						JSONObject jsonobj = new JSONObject();
						String itemnoval = mCursorforvendor2.getString(mCursorforvendor2
								.getColumnIndex(DatabaseForDemo.INVOICE_ID));
						jsonobj.put(
								DatabaseForDemo.INVOICE_ID,
								itemnoval);
						String department = mCursorforvendor2.getString(mCursorforvendor2
								.getColumnIndex(DatabaseForDemo.INVOICE_PROFIT));
						jsonobj.put(DatabaseForDemo.INVOICE_PROFIT,
								department);
						String itemname = mCursorforvendor2.getString(mCursorforvendor2
								.getColumnIndex(DatabaseForDemo.INVOICE_TOTAL_AMT));
						jsonobj.put(
								DatabaseForDemo.INVOICE_TOTAL_AMT,
								itemname);
						String desc = mCursorforvendor2.getString(mCursorforvendor2
								.getColumnIndex(DatabaseForDemo.INVOICE_STATUS));
						jsonobj.put(
								DatabaseForDemo.INVOICE_STATUS,
								desc);
						String avgcost = mCursorforvendor2.getString(mCursorforvendor2
								.getColumnIndex(DatabaseForDemo.INVOICE_EMPLOYEE));
						jsonobj.put(
								DatabaseForDemo.INVOICE_EMPLOYEE,
								avgcost);
						String pricechange = mCursorforvendor2.getString(mCursorforvendor2
								.getColumnIndex(DatabaseForDemo.INVOICE_CUSTOMER));
						jsonobj.put(
								DatabaseForDemo.INVOICE_CUSTOMER,
								pricechange);
						String taxprice = mCursorforvendor2.getString(mCursorforvendor2
								.getColumnIndex(DatabaseForDemo.INVOICE_PAYMENT_TYPE));
						jsonobj.put(
								DatabaseForDemo.INVOICE_PAYMENT_TYPE,
								taxprice);
						String instock = mCursorforvendor2.getString(mCursorforvendor2
								.getColumnIndex(DatabaseForDemo.INVOICE_TOTAL_AVG));
						jsonobj.put(DatabaseForDemo.INVOICE_TOTAL_AVG,
								instock);
						String vendor = mCursorforvendor2.getString(mCursorforvendor2
								.getColumnIndex(DatabaseForDemo.INVOICE_STORE_ID));
						jsonobj.put(DatabaseForDemo.INVOICE_STORE_ID,
								vendor);
						String taxone = mCursorforvendor2.getString(mCursorforvendor2
								.getColumnIndex(DatabaseForDemo.INVOICE_HOLD_ID));
						jsonobj.put(
								DatabaseForDemo.INVOICE_HOLD_ID,
								taxone);
						String uniqueidval = mCursorforvendor2.getString(mCursorforvendor2
								.getColumnIndex(DatabaseForDemo.UNIQUE_ID));
						jsonobj.put(DatabaseForDemo.UNIQUE_ID,
								uniqueidval);
						getlist.add(jsonobj);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} while (mCursorforvendor2.moveToNext());
			}
		}
		mCursorforvendor2.close();
		try {
			JSONObject data = new JSONObject();
			JSONArray fields = new JSONArray();
			for (int i = 0; i < getlist.size(); i++) {
				fields.put(i, getlist.get(i));
				data.put("fields", fields);
				dataval = data.toString();
				System.out.println("data val is:" + dataval);
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (Parameters.OriginalUrl.equals("")) {
			System.out.println("there is no server url val");
		} else {
			boolean isnet = Parameters.isNetworkAvailable(PosMainActivity.this);
			if (isnet) {
				new Thread(new Runnable() {
					@Override
					public void run() {

						JsonPostMethod jsonpost = new JsonPostMethod();
						String response = jsonpost
								.postmethodfordirect(
										"admin",
										"abcdefg",
										DatabaseForDemo.INVOICE_TOTAL_TABLE,
										Parameters.currentTime(),
										Parameters.currentTime(),
										dataval, "true");
						System.out.println("response test is:"
								+ response);
						try {
							JSONObject obj = new JSONObject(
									response);
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
							for (int jj = 0, ii = 0; jj < array2
									.length()
									&& ii < array.length(); jj++, ii++) {
								String deletequerytemp = array2
										.getString(jj);
								String deletequery1 = deletequerytemp
										.replace("'", "\"");
								String deletequery = deletequery1
										.replace("\\\"", "'");
								System.out.println("delete query"
										+ jj + " is :"
										+ deletequery);

								String insertquerytemp = array
										.getString(ii);
								String insertquery1 = insertquerytemp
										.replace("'", "\"");
								String insertquery = insertquery1
										.replace("\\\"", "'");
								System.out.println("delete query"
										+ jj + " is :"
										+ insertquery);
try{
								dbforloginlogoutWritePos.execSQL(deletequery);
								dbforloginlogoutWritePos.execSQL(insertquery);
}catch(SQLiteException qq){
	qq.printStackTrace();
}
								System.out
										.println("queries executed"
												+ ii);

							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			} else {
				ContentValues contentValues1 = new ContentValues();
				contentValues1.put(DatabaseForDemo.QUERY_TYPE,
						"update");
				contentValues1.put(DatabaseForDemo.PENDING_USER_ID,
						Parameters.userid);
				contentValues1.put(DatabaseForDemo.PAGE_URL,
						"saveinfo.php");
				contentValues1.put(
						DatabaseForDemo.TABLE_NAME_PENDING,
						DatabaseForDemo.INVOICE_TOTAL_TABLE);
				contentValues1.put(
						DatabaseForDemo.CURRENT_TIME_PENDING,
						Parameters.currentTime());
				contentValues1.put(DatabaseForDemo.PARAMETERS,
						dataval);
				dbforloginlogoutWritePos.insert(DatabaseForDemo.PENDING_QUERIES_TABLE,
						null, contentValues1);
			}
		}
		
		String queryforitem;
		if(paymenttype.equals("")){
			queryforitem = "update "+DatabaseForDemo.INVOICE_ITEMS_TABLE+" set "+DatabaseForDemo.INVOICE_STATUS+" =\""
								+status+"\" where "+DatabaseForDemo.INVOICE_ID+" =\""+invoice_id+"\"";
		}else{
			queryforitem = "update "+DatabaseForDemo.INVOICE_ITEMS_TABLE+" set "
					+DatabaseForDemo.INVOICE_STATUS+" =\""+status+"\","+DatabaseForDemo.INVOICE_PAYMENT_TYPE+"=\""
						+paymenttype+"\" where "+DatabaseForDemo.INVOICE_ID+" =\""+invoice_id+"\"";	
		}
		dbforloginlogoutWritePos.execSQL(queryforitem);
		
		final ArrayList<JSONObject> getlistforitem = new ArrayList<JSONObject>();
		String selectQueryforinstantpo1for = "SELECT  * FROM "
				+ DatabaseForDemo.INVOICE_ITEMS_TABLE + " where "
				+ DatabaseForDemo.INVOICE_ID + "=\"" + invoice_id
				+ "\"";

		Cursor mCursorforvendor3 = dbforloginlogoutReadPos.rawQuery(selectQueryforinstantpo1for, null);
		System.out.println("cursor val count is:"+ mCursorforvendor3.getCount());

		if (mCursorforvendor3 != null) {
			if (mCursorforvendor3.moveToFirst()) {
				do {
					try {
						JSONObject jsonobj = new JSONObject();
						String itemnoval = mCursorforvendor3.getString(mCursorforvendor3
								.getColumnIndex(DatabaseForDemo.INVOICE_ID));
						jsonobj.put(
								DatabaseForDemo.INVOICE_ID,
								itemnoval);
						String department = mCursorforvendor3.getString(mCursorforvendor3
								.getColumnIndex(DatabaseForDemo.INVOICE_ITEM_ID));
						jsonobj.put(DatabaseForDemo.INVOICE_ITEM_ID,
								department);
						String itemname = mCursorforvendor3.getString(mCursorforvendor3
								.getColumnIndex(DatabaseForDemo.INVOICE_ITEM_NAME));
						jsonobj.put(
								DatabaseForDemo.INVOICE_ITEM_NAME,
								itemname);
						String desc = mCursorforvendor3.getString(mCursorforvendor3
								.getColumnIndex(DatabaseForDemo.INVOICE_YOUR_COST));
						jsonobj.put(
								DatabaseForDemo.INVOICE_YOUR_COST,
								desc);
						String avgcost = mCursorforvendor3.getString(mCursorforvendor3
								.getColumnIndex(DatabaseForDemo.INVOICE_AVG_COST));
						jsonobj.put(
								DatabaseForDemo.INVOICE_AVG_COST,
								avgcost);
						String pricechange = mCursorforvendor3.getString(mCursorforvendor3
								.getColumnIndex(DatabaseForDemo.INVOICE_DISCOUNT));
						jsonobj.put(
								DatabaseForDemo.INVOICE_DISCOUNT,
								pricechange);
						String taxprice = mCursorforvendor3.getString(mCursorforvendor3
								.getColumnIndex(DatabaseForDemo.INVOICE_TAX));
						jsonobj.put(
								DatabaseForDemo.INVOICE_TAX,
								taxprice);
						String instock = mCursorforvendor3.getString(mCursorforvendor3
								.getColumnIndex(DatabaseForDemo.INVOICE_DEPARTMETNT));
						jsonobj.put(DatabaseForDemo.INVOICE_DEPARTMETNT,
								instock);
						String vendor = mCursorforvendor3.getString(mCursorforvendor3
								.getColumnIndex(DatabaseForDemo.INVOICE_VENDOR));
						jsonobj.put(DatabaseForDemo.INVOICE_VENDOR,
								vendor);
						String taxone = mCursorforvendor3.getString(mCursorforvendor3
								.getColumnIndex(DatabaseForDemo.INVOICE_STATUS));
						jsonobj.put(
								DatabaseForDemo.INVOICE_STATUS,
								taxone);
						String instock1 = mCursorforvendor3.getString(mCursorforvendor3
								.getColumnIndex(DatabaseForDemo.INVOICE_QUANTITY));
						jsonobj.put(DatabaseForDemo.INVOICE_QUANTITY,
								instock1);
						String vendor1 = mCursorforvendor3.getString(mCursorforvendor3
								.getColumnIndex(DatabaseForDemo.INVOICE_STORE_ID));
						jsonobj.put(DatabaseForDemo.INVOICE_STORE_ID,
								vendor1);
						String taxone1 = mCursorforvendor3.getString(mCursorforvendor3
								.getColumnIndex(DatabaseForDemo.INVOICE_PAYMENT_TYPE));
						jsonobj.put(
								DatabaseForDemo.INVOICE_PAYMENT_TYPE,
								taxone1);
						String uniqueidval = mCursorforvendor3.getString(mCursorforvendor3
								.getColumnIndex(DatabaseForDemo.UNIQUE_ID));
						jsonobj.put(DatabaseForDemo.UNIQUE_ID,
								uniqueidval);
						String vendor2 = mCursorforvendor3.getString(mCursorforvendor3
								.getColumnIndex(DatabaseForDemo.INVOICE_EMPLOYEE));
						jsonobj.put(DatabaseForDemo.INVOICE_EMPLOYEE,
								vendor2);
						String taxone2 = mCursorforvendor3.getString(mCursorforvendor3
								.getColumnIndex(DatabaseForDemo.INVOICE_CUSTOMER));
						jsonobj.put(
								DatabaseForDemo.INVOICE_CUSTOMER,
								taxone2);
						String uniqueidval2 = mCursorforvendor3.getString(mCursorforvendor3
								.getColumnIndex(DatabaseForDemo.INVOICE_DISCRIPTION));
						jsonobj.put(DatabaseForDemo.INVOICE_DISCRIPTION,
								uniqueidval2);
						getlistforitem.add(jsonobj);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} while (mCursorforvendor3.moveToNext());
			}
		}
		mCursorforvendor3.close();
		try {
			JSONObject data = new JSONObject();
			JSONArray fields = new JSONArray();
			for (int i = 0; i < getlistforitem.size(); i++) {
				fields.put(i, getlistforitem.get(i));
				data.put("fields", fields);
				dataval = data.toString();
				System.out.println("data val is:" + dataval);
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		if (Parameters.OriginalUrl.equals("")) {
			System.out.println("there is no server url val");
		} else {
			boolean isnet = Parameters.isNetworkAvailable(PosMainActivity.this);
			if (isnet) {
				new Thread(new Runnable() {
					@Override
					public void run() {

						JsonPostMethod jsonpost = new JsonPostMethod();
						String response = jsonpost
								.postmethodfordirect(
										"admin",
										"abcdefg",
										DatabaseForDemo.INVOICE_ITEMS_TABLE,
										Parameters.currentTime(),
										Parameters.currentTime(),
										dataval, "true");
						System.out.println("response test is:"
								+ response);
						try {
							JSONObject obj = new JSONObject(
									response);
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
							for (int jj = 0, ii = 0; jj < array2
									.length()
									&& ii < array.length(); jj++, ii++) {
								String deletequerytemp = array2
										.getString(jj);
								String deletequery1 = deletequerytemp
										.replace("'", "\"");
								String deletequery = deletequery1
										.replace("\\\"", "'");
								System.out.println("delete query"
										+ jj + " is :"
										+ deletequery);

								String insertquerytemp = array
										.getString(ii);
								String insertquery1 = insertquerytemp
										.replace("'", "\"");
								String insertquery = insertquery1
										.replace("\\\"", "'");
								System.out.println("delete query"
										+ jj + " is :"
										+ insertquery);
try{
								dbforloginlogoutWritePos.execSQL(deletequery);
								dbforloginlogoutWritePos.execSQL(insertquery);
							}catch(SQLiteException qq){
								qq.printStackTrace();
							}
									
								System.out
										.println("queries executed"
												+ ii);

							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
				}).start();
			} else {
				ContentValues contentValues1 = new ContentValues();
				contentValues1.put(DatabaseForDemo.QUERY_TYPE,
						"update");
				contentValues1.put(DatabaseForDemo.PENDING_USER_ID,
						Parameters.userid);
				contentValues1.put(DatabaseForDemo.PAGE_URL,
						"saveinfo.php");
				contentValues1.put(
						DatabaseForDemo.TABLE_NAME_PENDING,
						DatabaseForDemo.INVOICE_ITEMS_TABLE);
				contentValues1.put(
						DatabaseForDemo.CURRENT_TIME_PENDING,
						Parameters.currentTime());
				contentValues1.put(DatabaseForDemo.PARAMETERS,
						dataval);
				dbforloginlogoutWritePos.insert(DatabaseForDemo.PENDING_QUERIES_TABLE,
						null, contentValues1);
			}
		}
		} catch (NumberFormatException e) {
			  e.printStackTrace();
			} catch (SQLiteException e12) {
				  e12.printStackTrace();
				} catch (Exception e1) {
				  e1.printStackTrace();
				}
		
	}
	
	void updateTheInvoice(String invoice_id, String remain, String accountno, String chequeno, String paymenttype){
		try{
			
		String uniqueval = Parameters.randomValue();
		String createdtime = Parameters.currentTime();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseForDemo.SPLIT_INVOICE_ID, invoice_id);
		contentValues.put(DatabaseForDemo.SPLIT_PAYMENT_TYPE, paymenttype);
		contentValues.put(DatabaseForDemo.SPLIT_AMOUNT, remain);
		if(paymentTypestr.equals("Account")){
		contentValues.put(DatabaseForDemo.SPLIT_ACCOUNT_NO, accountno);
		}
		if(paymentTypestr.equals("Check")){
		contentValues.put(DatabaseForDemo.SPLIT_CHEQUE_NO, chequeno);
		}
		contentValues.put(DatabaseForDemo.CREATED_DATE, createdtime);
		contentValues.put(DatabaseForDemo.UNIQUE_ID, uniqueval);
		contentValues.put(DatabaseForDemo.MODIFIED_DATE, Parameters.currentTime());
		contentValues.put(DatabaseForDemo.MODIFIED_IN, "local");
		dbforloginlogoutWritePos.insert(DatabaseForDemo.SPLIT_INVOICE_TABLE, null,
				contentValues);
		
		try {
			JSONObject data = new JSONObject();
			JSONObject jsonobj = new JSONObject();
			JSONArray fields = new JSONArray();

				fields.put(0, jsonobj);
				jsonobj.put(DatabaseForDemo.SPLIT_INVOICE_ID, invoice_id);
				jsonobj.put(DatabaseForDemo.SPLIT_PAYMENT_TYPE, paymenttype);
				jsonobj.put(DatabaseForDemo.SPLIT_AMOUNT, remain);
				jsonobj.put(DatabaseForDemo.SPLIT_ACCOUNT_NO, accountno);
				jsonobj.put(DatabaseForDemo.SPLIT_CHEQUE_NO, chequeno);
				jsonobj.put(DatabaseForDemo.CREATED_DATE, createdtime);
				jsonobj.put(DatabaseForDemo.MODIFIED_DATE, createdtime);
				jsonobj.put(DatabaseForDemo.MODIFIED_IN, "Local");
				jsonobj.put(DatabaseForDemo.UNIQUE_ID, uniqueval);
			data.put("fields", fields);
			dataval = data.toString();
			System.out.println("data val is:" + dataval);
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (Parameters.OriginalUrl.equals("")) {
			System.out.println("there is no server url val");
		} else {
			boolean isnet = Parameters.isNetworkAvailable(PosMainActivity.this);
			if (isnet) {
				new Thread(new Runnable() {
					@Override
					public void run() {

						JsonPostMethod jsonpost = new JsonPostMethod();
						String response = jsonpost
								.postmethodfordirect(
										"admin",
										"abcdefg",
										DatabaseForDemo.SPLIT_INVOICE_TABLE,
										Parameters
												.currentTime(),
										Parameters
												.currentTime(),
										dataval, "");
						System.out
								.println("response test is:"
										+ response);
						try {
							JSONObject obj = new JSONObject(
									response);
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
									&& ii < array.length(); jj++, ii++) {
								String deletequerytemp = array2
										.getString(jj);
								String deletequery1 = deletequerytemp
										.replace("'", "\"");
								String deletequery = deletequery1
										.replace("\\\"",
												"'");
								System.out
										.println("delete query"
												+ jj
												+ " is :"
												+ deletequery);

								String insertquerytemp = array
										.getString(ii);
								String insertquery1 = insertquerytemp
										.replace("'", "\"");
								String insertquery = insertquery1
										.replace("\\\"",
												"'");
								System.out
										.println("delete query"
												+ jj
												+ " is :"
												+ insertquery);
try{
								dbforloginlogoutWritePos.execSQL(deletequery);
								dbforloginlogoutWritePos.execSQL(insertquery);
}catch(SQLiteException qq){
	qq.printStackTrace();
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
						dataval = "";
					}
				}).start();
			} else {

				ContentValues contentValues1 = new ContentValues();
				contentValues1.put(
						DatabaseForDemo.QUERY_TYPE,
						"insert");
				contentValues1.put(
						DatabaseForDemo.PENDING_USER_ID,
						Parameters.userid);
				contentValues1.put(
						DatabaseForDemo.PAGE_URL,
						"saveinfo.php");
				contentValues1.put(
						DatabaseForDemo.TABLE_NAME_PENDING,
						DatabaseForDemo.SPLIT_INVOICE_TABLE);
				contentValues1
						.put(DatabaseForDemo.CURRENT_TIME_PENDING,
								Parameters.currentTime());
				contentValues1
						.put(DatabaseForDemo.PARAMETERS,
								dataval);
				dbforloginlogoutWritePos.insert(
						DatabaseForDemo.PENDING_QUERIES_TABLE,
						null, contentValues1);
				dataval = "";
			}

		}

		contentValues.clear();
		} catch (NumberFormatException e) {
			  e.printStackTrace();
			} catch (SQLiteException e12) {
				  e12.printStackTrace();
				} catch (Exception e1) {
				  e1.printStackTrace();
				}
	}

	void checkPayType(Double change, final String remaining, final String accountno, final String chequeno) {
		try{
		if (mItemList.size() > 0) {
			final AlertDialog alertDialogCheck = new AlertDialog.Builder(
					PosMainActivity.this).create();
			LayoutInflater mInflaterCheck = LayoutInflater
					.from(PosMainActivity.this);
			View layoutCheck = mInflaterCheck.inflate(R.layout.change_show,
					null);
			alertDialogCheck.setTitle("Enter Check Number");
			final EditText totalchange = (EditText) layoutCheck
					.findViewById(R.id.changedit);
			// createPrintRecipt();
			Button okchange = (Button) layoutCheck.findViewById(R.id.changeok);
			okchange.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String value = totalchange.getText().toString().trim();
					if (value.length() > 2) {
						if (mode) {
							String paymenttypeval = paymentTypestr;
							//padma
							if(invoiceidexist.equals("")){
							if(lastsavedinvoiceid!=null){
								System.out.println("updated invoice is called");
								updateinvoicetable(lastsavedinvoiceid, "complete", "", "", "");
								updateTheInvoice(lastsavedinvoiceid, remaining, accountno, value, paymenttypeval);
							}else{
							String invoice_id =  Parameters.generateRandomNumber();
							Log.e("invoice", Parameters.generateRandomNumber() + "  "
									+ invoice_forHold);
							hold_Status = "complete";
							lastsavedinvoiceid = invoice_id;
							System.out.println("last saved invoice id is:"+lastsavedinvoiceid);
							createPrintRecipt(invoice_id, "", 0.0, Double.parseDouble(remaining), 0.0, 0.0, value, paymentTypestr);
							}
							}else{
								if(lastsavedinvoiceid!=null){
									System.out.println("updated invoice is called");
									updateinvoicetable(invoiceidexist, "complete", "", "multiple", "");
									updateTheInvoice(invoiceidexist, remaining, accountno, value, "");
									invoiceidexist = "";
									holdidexist = "";
								}else{
							//	String invoice_id =  Parameters.generateRandomNumber();
							//	Log.e("invoice", Parameters.generateRandomNumber() + "  "
							//			+ invoice_forHold);
								hold_Status = "complete";
								lastsavedinvoiceid = invoiceidexist;
								System.out.println("last saved invoice id is:"+lastsavedinvoiceid);
								updateinvoicetable(invoiceidexist, "complete", "", paymenttypeval, value);
								//createPrintRecipt(invoice_id, "", 0.0, Double.parseDouble(remaining), 0.0, 0.0, value);
								}
							}
						}
						String listofitems = "";
						Double AvgCost = 0.00;
						String timeC = Parameters.currentTime();
						for (int i = 0; i < mItemList.size(); i++) {
							String u_id0 = Parameters.randomValue();
							String Qtty = mItemList.get(i).getQuantity();
							String nameI = mItemList.get(i).getItemNameAdd();
							String nameId = mItemList.get(i).getItemNoAdd();
							String pricc = mItemList.get(i).getPriceYouChange();
							String AvgCos = mItemList.get(i).getAvgCost();
							String Description = mItemList.get(i).getSecondDescription();
							String taxx = mItemList.get(i).getInventoryTaxTotal();
							String department = mItemList.get(i).getDepartmentAdd();
							String vendor = mItemList.get(i).getInventoryVndr();
							String itemone = "<tr style='height:40px;overflow:hidden;' valign='top'><td align='left'>"+Qtty+" "+nameI+"</td><td align='right'>$"+pricc+"</td></tr>";
							listofitems = listofitems + itemone;
							AvgCost += Double.valueOf(AvgCos);
						}
						printStringCreation(lastsavedinvoiceid, listofitems, Parameters.Cridetamount, 0.0, 0.0, 0.0);
						showReciptPopup();
						alertDialogCheck.dismiss();
					} else {
						Toast.makeText(getApplicationContext(),
								"Enter Check Number", 2000).show();
					}
				}
			});
			alertDialogCheck.setView(layoutCheck);
			alertDialogCheck.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			alertDialogCheck.show();

		} else {
			Toast.makeText(getApplicationContext(), "Add Item",
					Toast.LENGTH_LONG).show();
		}
		} catch (NumberFormatException e) {
			  e.printStackTrace();
			} catch (SQLiteException e12) {
				  e12.printStackTrace();
				} catch (Exception e1) {
				  e1.printStackTrace();
				}
	}
	
	void checkPayTypeforNotComplete(Double change, final String remaining, final String accountno, final String chequeno, final String paymenttypeval) {
		try{
		if (mItemList.size() > 0) {
			final AlertDialog alertDialogCheck = new AlertDialog.Builder(
					PosMainActivity.this).create();
			LayoutInflater mInflaterCheck = LayoutInflater
					.from(PosMainActivity.this);
			View layoutCheck = mInflaterCheck.inflate(R.layout.change_show,
					null);
			alertDialogCheck.setTitle("Enter Check Number");
			final EditText totalchange = (EditText) layoutCheck
					.findViewById(R.id.changedit);
			// createPrintRecipt();
			Button okchange = (Button) layoutCheck.findViewById(R.id.changeok);
			okchange.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					String value = totalchange.getText().toString().trim();
					if (value.length() > 2) {
						if(mode){
							if(invoiceidexist.equals("")){
							if(lastsavedinvoiceid == null){
								String invoice_id =  Parameters.generateRandomNumber();
								Log.e("invoice", Parameters.generateRandomNumber() + "  "
										+ invoice_forHold);
								hold_Status = "incomplete";
								paymentTypestr = "multiple";
								lastsavedinvoiceid = invoice_id;
								System.out.println("last saved invoice id is:"+lastsavedinvoiceid);
								createPrintRecipt(invoice_id, "", 0.0, Double.parseDouble(remaining), 0.0, 0.0, "", paymentTypestr);
								paymentTypestr = paymenttypeval;
								updateTheInvoice(lastsavedinvoiceid, remaining, accountno, value, paymenttypeval);
								HashMap<String, String> map = new HashMap<String, String>();
								map.put("paymentType", paymenttypeval);
								map.put("amount", remaining);
								map.put("details", value);
								payedsofarlist.add(map);
								PaymentTypeAdapter adapter = new PaymentTypeAdapter(PosMainActivity.this, payedsofarlist);
								listviewpayedsofar.setAdapter(adapter);
							}else{
								updateTheInvoice(lastsavedinvoiceid, remaining, accountno, value, paymenttypeval);
								String listofitems = "";
								Double AvgCost = 0.00;
								String timeC = Parameters.currentTime();
								for (int i = 0; i < mItemList.size(); i++) {
									String u_id0 = Parameters.randomValue();
									String Qtty = mItemList.get(i).getQuantity();
									String nameI = mItemList.get(i).getItemNameAdd();
									String nameId = mItemList.get(i).getItemNoAdd();
									String pricc = mItemList.get(i).getPriceYouChange();
									String AvgCos = mItemList.get(i).getAvgCost();
									String Description = mItemList.get(i).getSecondDescription();
									String taxx = mItemList.get(i).getInventoryTaxTotal();
									String department = mItemList.get(i).getDepartmentAdd();
									String vendor = mItemList.get(i).getInventoryVndr();
									String itemone = "<tr style='height:40px;overflow:hidden;' valign='top'><td align='left'>"+Qtty+" "+nameI+"</td><td align='right'>$"+pricc+"</td></tr>";
									listofitems = listofitems + itemone;
									AvgCost += Double.valueOf(AvgCos);
								}
								printStringCreation(lastsavedinvoiceid, listofitems, Double.parseDouble(remaining), 0.0, 0.0, 0.0);
								HashMap<String, String> map = new HashMap<String, String>();
								map.put("paymentType", paymenttypeval);
								map.put("amount", remaining);
								map.put("details", value);
								payedsofarlist.add(map);
								PaymentTypeAdapter adapter = new PaymentTypeAdapter(PosMainActivity.this, payedsofarlist);
								listviewpayedsofar.setAdapter(adapter);
								System.out.println("invoice is updated");
							}
							}else{
								if(lastsavedinvoiceid == null){
								//	String invoice_id =  Parameters.generateRandomNumber();
								//	Log.e("invoice", Parameters.generateRandomNumber() + "  "
								//			+ invoice_forHold);
									hold_Status = "incomplete";
									paymentTypestr = "multiple";
									lastsavedinvoiceid = invoiceidexist;
									System.out.println("last saved invoice id is:"+lastsavedinvoiceid);
									updateinvoicetable(invoiceidexist, hold_Status, "", "multiple", value);
								//	createPrintRecipt(invoice_id, "", 0.0, Double.parseDouble(remaining), 0.0, 0.0, "");
									paymentTypestr = paymenttypeval;
									updateTheInvoice(lastsavedinvoiceid, remaining, accountno, value, paymenttypeval);
									HashMap<String, String> map = new HashMap<String, String>();
									map.put("paymentType", paymenttypeval);
									map.put("amount", remaining);
									map.put("details", value);
									payedsofarlist.add(map);
									PaymentTypeAdapter adapter = new PaymentTypeAdapter(PosMainActivity.this, payedsofarlist);
									listviewpayedsofar.setAdapter(adapter);
								}else{
									updateTheInvoice(invoiceidexist, remaining, accountno, value, paymenttypeval);
									String listofitems = "";
									Double AvgCost = 0.00;
									String timeC = Parameters.currentTime();
									for (int i = 0; i < mItemList.size(); i++) {
										String u_id0 = Parameters.randomValue();
										String Qtty = mItemList.get(i).getQuantity();
										String nameI = mItemList.get(i).getItemNameAdd();
										String nameId = mItemList.get(i).getItemNoAdd();
										String pricc = mItemList.get(i).getPriceYouChange();
										String AvgCos = mItemList.get(i).getAvgCost();
										String Description = mItemList.get(i).getSecondDescription();
										String taxx = mItemList.get(i).getInventoryTaxTotal();
										String department = mItemList.get(i).getDepartmentAdd();
										String vendor = mItemList.get(i).getInventoryVndr();
										String itemone = "<tr style='height:40px;overflow:hidden;' valign='top'><td align='left'>"+Qtty+" "+nameI+"</td><td align='right'>$"+pricc+"</td></tr>";
										listofitems = listofitems + itemone;
										AvgCost += Double.valueOf(AvgCos);
									}
									printStringCreation(invoiceidexist, listofitems, Double.parseDouble(remaining), 0.0, 0.0, 0.0);
									HashMap<String, String> map = new HashMap<String, String>();
									map.put("paymentType", paymenttypeval);
									map.put("amount", remaining);
									map.put("details", value);
									payedsofarlist.add(map);
									PaymentTypeAdapter adapter = new PaymentTypeAdapter(PosMainActivity.this, payedsofarlist);
									listviewpayedsofar.setAdapter(adapter);
									System.out.println("invoice is updated");
								}
							}
						}
						alertDialogCheck.dismiss();
					} else {
						Toast.makeText(getApplicationContext(),
								"Enter Check Number", 2000).show();
					}
				}
			});
			alertDialogCheck.setView(layoutCheck);
			alertDialogCheck.getWindow().setSoftInputMode(
					WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
			alertDialogCheck.show();

		} else {
			Toast.makeText(getApplicationContext(), "Add Item",
					Toast.LENGTH_LONG).show();
		}
		} catch (NumberFormatException e) {
			  e.printStackTrace();
			} catch (SQLiteException e12) {
				  e12.printStackTrace();
				} catch (Exception e1) {
				  e1.printStackTrace();
				}
	}

	void accountPayType() {
		if (mItemList.size() > 0) {
			Toast.makeText(getApplicationContext(), "Add Coustomer Account",
					1000).show();

		} else {
			Toast.makeText(getApplicationContext(), "Add Item",
					Toast.LENGTH_LONG).show();
		}

	}

	Print printer;

	void createPrintRecipt(String invoice_id, String hold_id, Double cash, Double check, Double account, Double creditdebit, String chequeno, String paymenttype) {
	try{
		String listofitems = "";
		Double AvgCost = 0.00;
		String timeC = Parameters.currentTime();
		Log.w("errrorr","fsfgbfgb   "+mItemList.size());
		for (int i = 0; i < mItemList.size(); i++) {
			String u_id0 = Parameters.randomValue();
			String Qtty = mItemList.get(i).getQuantity();
			String nameI = mItemList.get(i).getItemNameAdd();
			String nameId = mItemList.get(i).getItemNoAdd();
			String pricc = mItemList.get(i).getPriceYouChange();
			String AvgCos = mItemList.get(i).getAvgCost();
			String Description = mItemList.get(i).getSecondDescription();
			String taxx = mItemList.get(i).getInventoryTaxTotal();
			String department = mItemList.get(i).getDepartmentAdd();
			String vendor = mItemList.get(i).getInventoryVndr();
			String stock=mItemList.get(i).getInStock();
			Log.w("errrorr","fsfgbfgb");
			String itemone = "<tr style='height:40px;overflow:hidden;' valign='top'><td align='left' style='height:40px;overflow:hidden;' >"+Qtty+" "+nameI+"</td><td align='right'>$"+pricc+"</td></tr>";
			listofitems = listofitems + itemone;
			AvgCost += Double.valueOf(AvgCos);
			if(hold_Status.equals("complete")){
				Double stockval=Double.valueOf(stock)-Double.valueOf(Qtty);
				dbforloginlogoutWritePos.execSQL("update "
					+ DatabaseForDemo.INVENTORY_TABLE + " set "
					+ DatabaseForDemo.MODIFIED_DATE + "=\""
					+ Parameters.currentTime() + "\", "
					+ DatabaseForDemo.MODIFIED_IN + "=\"" + "Local"
					+ "\", " + DatabaseForDemo.INVENTORY_IN_STOCK + "=\""
					+ df.format(stockval) + "\" where "
					+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\"" + nameId
					+ "\"");
				updateInventory_Stock(nameId,""+stockval);
			}
			
			
			/*Double taxyyy = 0.0;

			Cursor mCursor = sqlDB1nn.getReadableDatabase().rawQuery(
					"select * from " + DatabaseForDemo.INVENTORY_TABLE
							+ " where " + DatabaseForDemo.INVENTORY_ITEM_NO
							+ "='" + nameId + "'", null);

			if (mCursor != null) {
				if (mCursor.moveToFirst()) {
					do {
						String catid = mCursor
								.getString(mCursor
										.getColumnIndex(DatabaseForDemo.INVENTORY_TOTAL_TAX));
						taxyyy = Double.valueOf(catid);
					} while (mCursor.moveToNext());
				}
			}
			mCursor.close();*/
			try{
			ContentValues contentValues = new ContentValues();
			contentValues.put(DatabaseForDemo.INVOICE_ID, invoice_id);
			contentValues.put(DatabaseForDemo.INVOICE_ITEM_ID, nameId);
			contentValues.put(DatabaseForDemo.INVOICE_ITEM_NAME, nameI);
			contentValues.put(DatabaseForDemo.INVOICE_YOUR_COST, pricc);
			contentValues.put(DatabaseForDemo.INVOICE_AVG_COST, AvgCos);
			contentValues.put(DatabaseForDemo.INVOICE_DISCOUNT, "0");
			contentValues.put(DatabaseForDemo.INVOICE_TAX, taxx);
			contentValues.put(DatabaseForDemo.CREATED_DATE, "" + timeC);
			contentValues.put(DatabaseForDemo.INVOICE_DEPARTMETNT, department);
			contentValues.put(DatabaseForDemo.INVOICE_VENDOR, vendor);
			contentValues.put(DatabaseForDemo.INVOICE_STATUS, hold_Status);
			contentValues.put(DatabaseForDemo.UNIQUE_ID, u_id0);
			contentValues.put(DatabaseForDemo.INVOICE_QUANTITY, "" + Qtty);
			contentValues.put(DatabaseForDemo.INVOICE_STORE_ID, "" + Parameters.store_id);
			contentValues.put(DatabaseForDemo.INVOICE_PAYMENT_TYPE, ""
					+ paymenttype);
			contentValues.put(DatabaseForDemo.INVOICE_EMPLOYEE, "" + Parameters.usertypeloginvalue);
			contentValues.put(DatabaseForDemo.INVOICE_CUSTOMER, "" + customer_id);
			contentValues.put(DatabaseForDemo.INVOICE_DISCRIPTION, Description);
			dbforloginlogoutWritePos.insert(DatabaseForDemo.INVOICE_ITEMS_TABLE, null,
					contentValues);
			Log.w("errrorr","insert");
			contentValues.clear();
			}catch(SQLiteException e){
				e.printStackTrace();
			}
				try {
					JSONObject data = new JSONObject();
					JSONObject jsonobj = new JSONObject();
					jsonobj.put(DatabaseForDemo.INVOICE_ID, invoice_id);
					jsonobj.put(DatabaseForDemo.INVOICE_ITEM_ID, nameId);
					jsonobj.put(DatabaseForDemo.INVOICE_ITEM_NAME, nameI);
					jsonobj.put(DatabaseForDemo.INVOICE_YOUR_COST, pricc);
					jsonobj.put(DatabaseForDemo.INVOICE_AVG_COST, AvgCos);
					jsonobj.put(DatabaseForDemo.INVOICE_DISCOUNT, "0");
					jsonobj.put(DatabaseForDemo.INVOICE_TAX, taxx);
					jsonobj.put(DatabaseForDemo.UNIQUE_ID, u_id0);
					jsonobj.put(DatabaseForDemo.INVOICE_DEPARTMETNT, department);
					jsonobj.put(DatabaseForDemo.INVOICE_VENDOR, vendor);
					jsonobj.put(DatabaseForDemo.INVOICE_STATUS, hold_Status);
					jsonobj.put(DatabaseForDemo.CREATED_DATE, "" + timeC);
					jsonobj.put(DatabaseForDemo.INVOICE_QUANTITY, "" + Qtty);
					jsonobj.put(DatabaseForDemo.INVOICE_STORE_ID, "" + Parameters.store_id);
					jsonobj.put(DatabaseForDemo.INVOICE_EMPLOYEE, "" + Parameters.usertypeloginvalue);
					jsonobj.put(DatabaseForDemo.INVOICE_CUSTOMER, "" + customer_id);
					jsonobj.put(DatabaseForDemo.INVOICE_PAYMENT_TYPE, ""
							+ paymenttype);
					jsonobj.put(DatabaseForDemo.INVOICE_DISCRIPTION,
							Description);
					JSONArray fields = new JSONArray();
					fields.put(0, jsonobj);
					data.put("fields", fields);
					dataval = data.toString();
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				if (Parameters.OriginalUrl.equals("")) {
					System.out.println("there is no server url val");
				} else {
				 boolean isnet = Parameters
						  .isNetworkAvailable(getApplicationContext()); if (isnet) { new
						  Thread(new Runnable() {
						  
						  @Override public void run() { JsonPostMethod jsonpost = new
						  JsonPostMethod(); String response =
						  jsonpost.postmethodfordirect("admin", "abcdefg",
						  DatabaseForDemo.INVOICE_ITEMS_TABLE, Parameters.currentTime(),
						  Parameters.currentTime(), dataval, "");
						  System.out.println("response test is:" + response);
						   String servertiem = null; try {
						  JSONObject obj = new JSONObject(response); JSONObject responseobj
						  = obj .getJSONObject("response"); servertiem =
						  responseobj.getString("server-time");
						  System.out.println("servertime is:" + servertiem); JSONArray
						  array = obj .getJSONArray("insert-queries"); System.out
						  .println("array list length for insert is:" + array.length());
						  JSONArray array2 = obj .getJSONArray("delete-queries");
						  System.out .println("array2 list length for delete is:" +
						  array2.length()); for (int jj = 0, ii = 0; jj < array2.length()
						  && ii < array.length(); jj++, ii++) { String deletequerytemp =
						  array2.getString(jj); String deletequery1 =
						  deletequerytemp.replace( "'", "\""); String deletequery =
						  deletequery1.replace( "\\\"", "'");
						  System.out.println("delete query" + jj + " is :" + deletequery);
						  
						  String insertquerytemp = array.getString(ii); String insertquery1
						  = insertquerytemp.replace( "'", "\""); String insertquery =
						  insertquery1.replace( "\\\"", "'");
						  System.out.println("delete query" + jj + " is :" + insertquery);
						  try{
						  dbforloginlogoutWritePos.execSQL(deletequery); dbforloginlogoutWritePos.execSQL(insertquery);
						  }catch(SQLiteException qq){
								qq.printStackTrace();
							}
									
						  System.out.println("queries executed" + ii); } } catch
						  (JSONException e) { // TODO Auto-generated catch block
						  e.printStackTrace(); }
						  
						  String select = "select *from " + DatabaseForDemo.MISCELLANEOUS_TABLE; 
						  Cursor cursor =
								  dbforloginlogoutReadPos.rawQuery(select, null);
						  if (cursor.getCount() > 0) {
									  dbforloginlogoutWritePos.execSQL("update " + DatabaseForDemo.MISCELLANEOUS_TABLE +
						  " set " + DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL + "=\"" +
						  servertiem + "\"");
									  } else { 
							  ContentValues contentValues1 = new
						  ContentValues(); 
							  contentValues1.put(DatabaseForDemo.MISCEL_STORE, "store1"); 
							  contentValues1 .put(DatabaseForDemo.MISCEL_PAGEURL,"http://www.mydata.ws/aoneposws/webserviceoriginal/saveinfo.php");
							  contentValues1.put( DatabaseForDemo.MISCEL_UPDATE_LOCAL, Parameters.currentTime());
							  contentValues1.put(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL, Parameters.currentTime());
						  dbforloginlogoutWritePos.insert(DatabaseForDemo.MISCELLANEOUS_TABLE, null, contentValues1);
						  } dataval = "";
						  }
						  }).start();
						  } else {
						
						ContentValues contentValues1 = new ContentValues();
						contentValues1.put(DatabaseForDemo.QUERY_TYPE, "insert");
						contentValues1.put(DatabaseForDemo.PENDING_USER_ID,
								Parameters.userid);
						contentValues1.put(DatabaseForDemo.PAGE_URL, "saveinfo.php");
						contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING,
								DatabaseForDemo.INVOICE_TOTAL_TABLE);
						contentValues1.put(DatabaseForDemo.CURRENT_TIME_PENDING,
								Parameters.currentTime());
						contentValues1.put(DatabaseForDemo.PARAMETERS, dataval);
						dbforloginlogoutWritePos.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null,
								contentValues1);
						dataval = "";
						 }
				}
		//	}
		}

		printStringCreation(invoice_id, listofitems, cash, check, account, creditdebit);
		String u_id = Parameters.randomValue();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseForDemo.INVOICE_ID, invoice_id);
		contentValues.put(DatabaseForDemo.INVOICE_PROFIT, ""
				+ mTaxTotal);
		contentValues.put(DatabaseForDemo.UNIQUE_ID, u_id);
		contentValues.put(DatabaseForDemo.INVOICE_TOTAL_AMT, "" + mGrandTotal);
		contentValues.put(DatabaseForDemo.INVOICE_STATUS, hold_Status);
		contentValues.put(DatabaseForDemo.INVOICE_PAYMENT_TYPE, paymenttype);
		contentValues.put(DatabaseForDemo.INVOICE_TOTAL_AVG, "" + mSubTotal);
		contentValues.put(DatabaseForDemo.CREATED_DATE, "" + timeC);
		contentValues.put(DatabaseForDemo.INVOICE_STORE_ID, "" + Parameters.store_id);
		contentValues.put(DatabaseForDemo.INVOICE_HOLD_ID, "" + hold_id);
		contentValues.put(DatabaseForDemo.INVOICE_EMPLOYEE, "" + Parameters.usertypeloginvalue);
		contentValues.put(DatabaseForDemo.INVOICE_CUSTOMER, "" + customer_id);
		contentValues.put(DatabaseForDemo.INVOICE_CHEQUE_NO, chequeno);
		dbforloginlogoutWritePos.insert(DatabaseForDemo.INVOICE_TOTAL_TABLE, null,
				contentValues);
		contentValues.clear();
		System.out.println("hold id is saved in local data");
			try {
				JSONObject data = new JSONObject();
				JSONObject jsonobj = new JSONObject();
				jsonobj.put(DatabaseForDemo.INVOICE_ID, invoice_id);
				jsonobj.put(DatabaseForDemo.INVOICE_PROFIT, ""
						+mTaxTotal);
				jsonobj.put(DatabaseForDemo.INVOICE_TOTAL_AMT, "" + mGrandTotal);
				jsonobj.put(DatabaseForDemo.INVOICE_STATUS, hold_Status);
				jsonobj.put(DatabaseForDemo.INVOICE_HOLD_ID, hold_id);
				jsonobj.put(DatabaseForDemo.INVOICE_PAYMENT_TYPE,
						paymenttype);
				jsonobj.put(DatabaseForDemo.UNIQUE_ID, u_id);
				jsonobj.put(DatabaseForDemo.INVOICE_TOTAL_AVG, "" + mSubTotal);
				jsonobj.put(DatabaseForDemo.CREATED_DATE, "" + timeC);
				jsonobj.put(DatabaseForDemo.INVOICE_STORE_ID, "" + Parameters.store_id);
				jsonobj.put(DatabaseForDemo.INVOICE_EMPLOYEE, "" + Parameters.usertypeloginvalue);
				jsonobj.put(DatabaseForDemo.INVOICE_CUSTOMER, "" + customer_id);
				jsonobj.put(DatabaseForDemo.INVOICE_CHEQUE_NO, chequeno);
				JSONArray fields = new JSONArray();
				fields.put(0, jsonobj);
				data.put("fields", fields);
				dataval = data.toString();
				System.out.println("data val is:" + dataval);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

			if (Parameters.OriginalUrl.equals("")) {
				System.out.println("there is no server url val");
			} else {
			  boolean isnet = Parameters
			  .isNetworkAvailable(getApplicationContext()); if (isnet) { new
			  Thread(new Runnable() {
			  
			  @Override public void run() { JsonPostMethod jsonpost = new
			  JsonPostMethod(); String response =
			  jsonpost.postmethodfordirect("admin", "abcdefg",
			  DatabaseForDemo.INVOICE_TOTAL_TABLE, Parameters.currentTime(),
			  Parameters.currentTime(), dataval, "");
			  System.out.println("response test is:" + response);
			  String servertiem = null; try {
			  JSONObject obj = new JSONObject(response); JSONObject responseobj
			  = obj .getJSONObject("response"); servertiem =
			  responseobj.getString("server-time");
			  System.out.println("servertime is:" + servertiem); JSONArray
			  array = obj .getJSONArray("insert-queries"); System.out
			  .println("array list length for insert is:" + array.length());
			  JSONArray array2 = obj .getJSONArray("delete-queries");
			  System.out .println("array2 list length for delete is:" +
			  array2.length()); for (int jj = 0, ii = 0; jj < array2.length()
			  && ii < array.length(); jj++, ii++) { String deletequerytemp =
			  array2.getString(jj); String deletequery1 =
			  deletequerytemp.replace( "'", "\""); String deletequery =
			  deletequery1.replace( "\\\"", "'");
			  System.out.println("delete query" + jj + " is :" + deletequery);
			  
			  String insertquerytemp = array.getString(ii); String insertquery1
			  = insertquerytemp.replace( "'", "\""); String insertquery =
			  insertquery1.replace( "\\\"", "'");
			  System.out.println("delete query" + jj + " is :" + insertquery);
			  try{
			  dbforloginlogoutWritePos.execSQL(deletequery); dbforloginlogoutWritePos.execSQL(insertquery);
			  }catch(SQLiteException qq){
					qq.printStackTrace();
				}
			  System.out.println("queries executed" + ii); } } catch
			  (JSONException e) { // TODO Auto-generated catch block
			  e.printStackTrace(); }
			  
			  String select = "select *from " +
			  DatabaseForDemo.MISCELLANEOUS_TABLE; Cursor cursor =
					  dbforloginlogoutReadPos.rawQuery(select, null); if (cursor.getCount() > 0) {
						  dbforloginlogoutWritePos.execSQL("update " + DatabaseForDemo.MISCELLANEOUS_TABLE +
			  " set " + DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL + "=\"" +
			  servertiem + "\"");  } else {  ContentValues contentValues1 = new
			  ContentValues(); contentValues1.put(DatabaseForDemo.MISCEL_STORE,
			  "store1"); contentValues1 .put(DatabaseForDemo.MISCEL_PAGEURL,
			  "http://www.mydata.ws/aoneposws/webserviceoriginal/saveinfo.php"
			  ); contentValues1.put( DatabaseForDemo.MISCEL_UPDATE_LOCAL,
			  Parameters.currentTime()); contentValues1.put(
			  DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL,
			  Parameters.currentTime());
			  dbforloginlogoutWritePos.insert(DatabaseForDemo.MISCELLANEOUS_TABLE, null,
			  contentValues1); } dataval = ""; }
			  }).start(); } else {
			 

			ContentValues contentValues1 = new ContentValues();
			contentValues1.put(DatabaseForDemo.QUERY_TYPE, "insert");
			contentValues1.put(DatabaseForDemo.PENDING_USER_ID,
					Parameters.userid);
			contentValues1.put(DatabaseForDemo.PAGE_URL, "saveinfo.php");
			contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING,
					DatabaseForDemo.INVOICE_TOTAL_TABLE);
			contentValues1.put(DatabaseForDemo.CURRENT_TIME_PENDING,
					Parameters.currentTime());
			contentValues1.put(DatabaseForDemo.PARAMETERS, dataval);
			dbforloginlogoutWritePos.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null,
					contentValues1);
			dataval = "";
			 }
		}
	} catch (NumberFormatException e) {
		  e.printStackTrace();
		}catch (SQLiteException e12) {
		  e12.printStackTrace();
		}catch (Exception e1) {
			  e1.printStackTrace();
			}
	}
	
	public void printStringCreation(String invoice_id, String listofitems, Double cash, Double check, Double account, Double creditdebit){
		try{
			totalcash = 0.0;
			totalcheck = 0.0;
			totalcredit = 0.0;
			totalaccount = 0.0;
			Boolean boolvalue=true;
			String selectQuery = "SELECT  * FROM "
					+ DatabaseForDemo.SPLIT_INVOICE_TABLE+" where "+DatabaseForDemo.SPLIT_INVOICE_ID+"='"+invoice_id+"'"; 
			Cursor mCursorade1 = dbforloginlogoutReadPos.rawQuery(selectQuery, null);
			if (mCursorade1 != null) {
				if (mCursorade1.getCount() > 0) {
					if (mCursorade1.moveToFirst()) {
						do {
							boolvalue=false;
							String paymenttype=mCursorade1.getString(mCursorade1
									.getColumnIndex(DatabaseForDemo.SPLIT_PAYMENT_TYPE));
							String paytype=mCursorade1.getString(mCursorade1
									.getColumnIndex(DatabaseForDemo.SPLIT_AMOUNT));
							Double valuee=Double.valueOf(paytype);
							if(paymenttype.equals("Cash")){
								totalcash = totalcash+valuee;
							}else if(paymenttype.equals("Check")){
								totalcheck = totalcheck+valuee;
							}else if(paymenttype.equals("Credit/Debit")){
								totalcredit = totalcredit+valuee;
							}else if(paymenttype.equals("Account")){
								totalaccount = totalaccount+valuee;
							}
						} while (mCursorade1.moveToNext());
					}
			}
				}else{
				}
			
			if(boolvalue){
				String selectQuery12 = "SELECT  * FROM "
						+ DatabaseForDemo.INVOICE_TOTAL_TABLE+" where "+DatabaseForDemo.INVOICE_ID+"='"+invoice_id+"'"; 
				Cursor mCursormang = dbforloginlogoutReadPos.rawQuery(selectQuery12, null);
				if (mCursormang != null) {
					if (mCursormang.getCount() > 0) {
						if (mCursormang.moveToFirst()) {
							do {
								boolvalue=false;
								String paymenttype=mCursormang.getString(mCursormang.getColumnIndex(DatabaseForDemo.INVOICE_PAYMENT_TYPE));
								String paytype=mCursormang.getString(mCursormang.getColumnIndex(DatabaseForDemo.INVOICE_TOTAL_AVG));
							//	Double valuee=Double.valueOf(paytype);
								if(paymenttype.equals("Cash")){
									totalcash = mGrandTotal;
								}else if(paymenttype.equals("Check")){
									totalcheck =mGrandTotal;
								}else if(paymenttype.equals("Credit/Debit")){
									totalcredit = mGrandTotal;
								}else if(paymenttype.equals("Account")){
									totalaccount = mGrandTotal;
								}
							} while (mCursormang.moveToNext());
						}
				}
					}else{
						
					}
			}
			
	/*	totalcash = totalcash+cash;
		totalcheck = totalcheck+check;
		totalcredit = totalcredit+creditdebit;
		totalaccount = totalaccount+account;*/
		String payclip = "" 
		        + " \n \n"
		        + " INVOICE#" + invoice_id + "       \n"
				+ "   Closed to Cash Purchase   \n  \n" + " DATE/TIME: "
				+ Parameters.currentTime() + " \n" + " CASHIER:   " + 1234
				+ "\n" + " STATION:   " + 01 + " \n  \n" + "Item Count: "
				+ mItemList.size() + "\n"
				+ "=========================================" + ""
				+ listofitems + "\n"
				+ "=========================================\n"
				+ "Subtotal            " + mSubTotal + "\n"
				+ "Tax                 " + mTaxTotal + "\n"
				+ "GRAND TOTAL         " + mGrandTotal + "\n \n"
				+ "Cash                " + Double.valueOf(df.format(totalcash)) + "\n"
				+ "Check               " + Double.valueOf(df.format(totalcheck)) + "\n"
				+ "Credit/Debit        " + Double.valueOf(df.format(totalcredit)) + "\n"
				+ "Account             " + Double.valueOf(df.format(totalaccount)) + "\n"
				+ "Amt Tendered        " + mGrandTotal + "\n"
				+ "Change              " + Double.valueOf(df.format(change)) + "\n \n \n"
				+ "Thank you for visiting \n \n \n" + "    Invoice: "
				+ invoice_id;
		Log.e("asdf",payclip);
		String headlines=Parameters.printTextgetFromDatabase(PosMainActivity.this,"printer1");
	String	printstring="<html> <head><style type='text/css'>body { background-color:none;font-family : Verdana;color:#000000;font-size:20px;} .tablehead{font-family : Verdana;color:#000000;font-size:32px;} table td{font-family : Verdana;color:#000000;font-size:32px; line-height:40px; padding:0px 4px;}</style></head><body on style='background-color:none;'><center><table id='page' cellspasing='3' width='100%' align='center' border='0'>"
				+"<tr> <th align='center' colspan='2'><span class='tablehead'>"+headlines+"</span></th></tr>"
			    +"<tr><th align='center' colspan='2'><span class='tablehead'></span></th></tr>"
				+"<tr><td align='center' colspan='2' style='font-size:22px'>INVOICE# "+invoice_id+" </td></tr>"
				+"<tr><td align='left' style='font-size:22px'>DATE/TIME:  "+Parameters.currentTime()+" </td></tr>"
				+"<tr><td align='left' style='font-size:22px'>CASHIER:    "+Parameters.userid+" </td></tr>"
				+"<tr><td align='left' style='font-size:22px'>STATION:    "+1+" </td></tr>"
				+"<tr><td align='left' style='font-size:22px'>Item Count: "+mItemList.size()+" </td></tr>"
				+"<tr><td colspan='2'>&nbsp;</td> <!--  for blank row --></tr>"
				+listofitems
				+"<tr><td colspan='2'>&nbsp;</td> <!--  for blank row --></tr>"
				+"<tr><td align='left'>Subtotal</td><td align='right'>$"+mSubTotal+"</td></tr>"
				+"<tr><td align='left'>Tax</td><td align='right'>$"+mTaxTotal+"</td></tr>"
				+"<tr><td align='left'>GRAND TOTAL</td><td align='right'>$"+mGrandTotal+"</td></tr>"
				+"<tr><td align='left'>Cash</td><td align='right'>$"+Double.valueOf(df.format(totalcash))+"</td></tr>"
				+"<tr><td align='left'>Cheque</td><td align='right'>$"+Double.valueOf(df.format(totalcheck))+"</td></tr>"
				+"<tr><td align='left'>Credit/Debit</td><td align='right'>$"+Double.valueOf(df.format(totalcredit))+"</td></tr>"
				+"<tr><td align='left'>Account</td><td align='right'>$"+Double.valueOf(df.format(totalaccount))+"</td></tr>"
				+"<tr><td align='left'>Amt Tendered</td><td align='right'>$"+mGrandTotal+"</td></tr>"
				+"<tr><td align='left'>Change</td><td align='right'>$"+Double.valueOf(df.format(change))+"</td></tr>"
				+"<tr><td colspan='2'>&nbsp;</td> <!--  for blank row --></tr>"
				+"<tr><td align='center' colspan='2'>Thank you for visiting</td></tr>"
				+"<tr><td colspan='2'>&nbsp;</td> <!--  for blank row --></tr>"
				 +"</table></center></body></html>";
	Log.v("h "+billPrint.getHeight(), "dsds "+printstring);
	createHtmlFile(printstring);
	  billPrint.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/printData/billFile.html");
		Log.v("h "+billPrint.getHeight(), "dsdssdslist  w "+billPrint.getWidth());
		LayoutParams params = billPrint.getLayoutParams();
		int jjjj=1100;
		jjjj+=mItemList.size()*40;
		params.width = 550;
		params.height = jjjj;
		billPrint.setLayoutParams(params);
		billPrint.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/printData/billFile.html");
		Log.v("h "+billPrint.getHeight(), "dsdssdslist  w "+billPrint.getWidth());
		} catch (NumberFormatException e) {
			  e.printStackTrace();
		}catch (SQLiteException e12) {
			  e12.printStackTrace();
			} catch (Exception e1) {
				  e1.printStackTrace();
				}
	}


	private void printText(String printerid, String print_text,Bitmap map) {
		try{
		boolean forprintid = false;
		ArrayList<String> printVal = new ArrayList<String>();
			String selectQuery = "SELECT  * FROM "
					+ DatabaseForDemo.PRINTER_TABLE; // + " where "+
														// DatabaseForDemo.PRINTER_ID
														// + "='" + printerid +
														// "'";
			Cursor mCursordwe2kop1 = dbforloginlogoutReadPos.rawQuery(selectQuery, null);
			int count = mCursordwe2kop1.getColumnCount();
			System.out.println("hari  " + mCursordwe2kop1);
			System.out.println("hari  " + count);
			if (mCursordwe2kop1 != null) {
				Log.e("getCount", "parddhu1     v:" + mCursordwe2kop1.getCount());
				if (mCursordwe2kop1.getCount() > 0) {
					Log.e("Rama", "parddhu2 " + mCursordwe2kop1.getCount());
					if (mCursordwe2kop1.moveToFirst()) {
						Log.e("Rama", "parddhu3");
						do {
							if (printerid
									.equals(mCursordwe2kop1.getString(mCursordwe2kop1
											.getColumnIndex(DatabaseForDemo.PRINTER_ID)))) {
								for (int i = 0; i < count; i++) {
									String value = mCursordwe2kop1.getString(i);
									printVal.add(value);
								}
								forprintid = true;
							}

						} while (mCursordwe2kop1.moveToNext());
					}
					if (forprintid) {

					} else {
						if (mCursordwe2kop1.moveToFirst()) {
							for (int i = 0; i < count; i++) {
								String value = mCursordwe2kop1.getString(i);
								printVal.add(value);
							}
						}
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"Set The Printer Settings", 1000).show();
				}
			}
			mCursordwe2kop1.close();
		//}
		Log.e("Rama", "parddhu4");
		if (printVal.size() > 14) {
			if(map==null){
			billPrint.setDrawingCacheEnabled(true);

			billPrint.buildDrawingCache();

	        Bitmap map1=billPrint.getDrawingCache();
	        billPrint.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/printData/billFile.html");
	         if(billPrint.getHeight()>0&&billPrint.getWidth()>0)
	         map1=drawableToBitmap(getBitmapFromView(billPrint));
	         billPrint.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/printData/billFile.html");
	         billPrint.setDrawingCacheEnabled(true);

	         billPrint.buildDrawingCache();
	         map1=billPrint.getDrawingCache();
	         if(billPrint.getHeight()>0&&billPrint.getWidth()>0){
		         map1=drawableToBitmap(getBitmapFromView(billPrint));
		         Log.v("srii","sriiiimmmmm");
		         }
	         
			PrintingNow(printVal, map1);
			}else{
				PrintingNow(printVal, map);
			}
		}
		} catch (NumberFormatException e) {
			  e.printStackTrace();
			} catch (SQLiteException e12) {
				  e12.printStackTrace();
				} catch (Exception e1) {
				  e1.printStackTrace();
				}
	}
	
	void PrintingNow(ArrayList<String> printVal, Bitmap printingImage) {
		try{
		billPrintPreferences("image_data", printingImage);
		if (printVal.get(15).equals("EPSON")) {
			Builder builder = null;
			String method = "";
			Log.e("Rama", "parddhu5");
			try {
				// create builder
				method = "Builder";
				try {
					builder = new Builder("" + printVal.get(12), 0,
							getApplicationContext());
				} catch (Exception e) {

				}
				 if(printingImage == null){
					 Log.e("Rama", "parddhu4336");
			            ShowMsg.showError(R.string.errmsg_noimage, PosMainActivity.this);
			            return ;
			        }
				 Log.v(Math.min(542, printingImage.getWidth())+"", printingImage.getHeight()+"H  W"+ printingImage.getWidth());
				  builder.addImage(printingImage, 0, 0, Math.min(542, printingImage.getWidth()), printingImage.getHeight(), Builder.COLOR_1, 
						  Builder.MODE_MONO, Builder.HALFTONE_THRESHOLD, 1.0);
				  builder.addCut(Builder.CUT_FEED);
				Log.e("Rama", "parddhu6");
				/*harinath     
			    method = "addTextFont";
				builder.addTextFont(Integer.parseInt(printVal.get(2)));

				method = "addTextAlign";
				builder.addTextAlign(Integer.parseInt(printVal.get(3)));

				method = "addTextLineSpace";
				builder.addTextLineSpace(Integer.parseInt(printVal.get(4)));
				Log.e("Rama", "parddhu7");
				method = "addTextLang";
				builder.addTextLang(Integer.parseInt(printVal.get(5)));

				method = "addTextSize";
				builder.addTextSize(Integer.parseInt(printVal.get(6)) + 1,
						Integer.parseInt(printVal.get(7)) + 1);

				method = "addTextStyle";
				builder.addTextStyle(Builder.FALSE,
						Integer.parseInt(printVal.get(9)),
						Integer.parseInt(printVal.get(8)), Builder.COLOR_1);
				Log.e("Rama", "parddhu8");
				method = "addTextPosition";
				builder.addTextPosition(Integer.parseInt(printVal.get(10)));

				method = "addText";
				builder.addText("" + printVal.get(1) + " \n " + printingText);

				method = "addFeedUnit";
				builder.addFeedUnit(Integer.parseInt(printVal.get(11)));
				Log.e("Rama", "parddhu9");  harinath*/
				// send builder data
				int[] status = new int[1];
				int[] battery = new int[1];
				Log.e("Rama", "parddhu10");
				try {

					int deviceType = Print.DEVTYPE_TCP;
					try {
						Log.e("Rama", "parddhu11");
						printer = new Print(PosMainActivity.this);
						printer.openPrinter(deviceType, "" + printVal.get(13),
								Print.TRUE, 1000);

					} catch (Exception e) {
						Log.e("Rama", "parddhu12");
						ShowMsg.showException(e, "openPrinter",
								PosMainActivity.this);
						return;
					}
					printer.sendData(builder, Constants.SEND_TIMEOUT, status,
							battery);
					printer.closePrinter();
					Log.e("Rama", "parddhu13");
				} catch (EposException e) {
					ShowMsg.showStatus(e.getErrorStatus(),
							e.getPrinterStatus(), e.getBatteryStatus(),
							PosMainActivity.this);
					printer.closePrinter();
				}
			} catch (Exception e) {
				ShowMsg.showException(e, method, PosMainActivity.this);
				try {
					Log.e("Rama", "parddhu14");
					printer.closePrinter();
				} catch (EposException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				printer = null;
			}
			Log.e("Rama", "parddhu15");
			// remove builder
			if (builder != null) {
				try {
					builder.clearCommandBuffer();
					builder = null;
				} catch (Exception e) {
					builder = null;
				}
			}
			Log.e("Rama", "parddhu16");
			/*
			 * try { Log.e("Rama", "parddhu17"); //printer.closePrinter(); }
			 * catch (EposException e) { // TODO Auto-generated catch block
			 * e.printStackTrace(); }
			 */
			printer = null;
		} else {
			try {
				boolean compressionEnable = false;
				String commandType  = "Raster";
				String typp="TCP:";
				if(printVal.get(2).equals("BT:")){
					typp="BT:";
				}
				 if(printingImage == null){
					 Log.e("Rama", "parddhu4336");
			            ShowMsg.showError(R.string.errmsg_noimage, PosMainActivity.this);
			            return ;
			        }
				PrinterFunctions.PrintBitmapImage(PosMainActivity.this, typp+""+printVal.get(13), "", getResources(), printingImage, 546, compressionEnable);
	        //	PrinterFunctions.PrintSampleReceipt(ReportsActivity.this,  "TCP:"+printVal.get(13),   "",  commandType, getResources(), "3inch (78mm)", printingText);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		Log.e("Rama", "parddhu20");
		} catch (NumberFormatException e) {
			  e.printStackTrace();
			} catch (SQLiteException e12) {
				  e12.printStackTrace();
				} catch (Exception e1) {
				  e1.printStackTrace();
				}
	}
//harinath
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Parameters.printerContext=PosMainActivity.this;
		super.onResume();
		if (Parameters.mSearchItemStatus) {
			if (Parameters.mSearchItemId != null){
				mSubTotal = 0;
				mTaxTotal = 0;
				if(modifiersHaving(Parameters.mSearchItemId)){
				if (checkitemExists(Parameters.mSearchItemId, "1")) {
					getItemDetails(false, Parameters.mSearchItemId, null);
				}
				}
			}
			Parameters.mSearchItemStatus = false;
		}
	}

	@SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message,
			Boolean status) {
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
	
	@SuppressWarnings("deprecation")
	public void showAlertDialogforcredit(Context context, String title, String message,
			Boolean status) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
				String listofitems = "";
				Double AvgCost = 0.00;
				String timeC = Parameters.currentTime();
				for (int i = 0; i < mItemList.size(); i++) {
					String u_id0 = Parameters.randomValue();
					String Qtty = mItemList.get(i).getQuantity();
					String nameI = mItemList.get(i).getItemNameAdd();
					String nameId = mItemList.get(i).getItemNoAdd();
					String pricc = mItemList.get(i).getPriceYouChange();
					String AvgCos = mItemList.get(i).getAvgCost();
					String Description = mItemList.get(i).getSecondDescription();
					String taxx = mItemList.get(i).getInventoryTaxTotal();
					String department = mItemList.get(i).getDepartmentAdd();
					String vendor = mItemList.get(i).getInventoryVndr();
					String itemone = "<tr style='height:40px;overflow:hidden;' valign='top'><td align='left'>"+Qtty+" "+nameI+"</td><td align='right'>$"+pricc+"</td></tr>";
					listofitems = listofitems + itemone;
					AvgCost += Double.valueOf(AvgCos);
				}
				printStringCreation(lastsavedinvoiceid, listofitems, Parameters.Cridetamount, 0.0, 0.0, 0.0);
				showReciptPopup();
			}
		});
		alertDialog.show();
	}

	private void savePreferences(String key, String value) {
		SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();
	}

	private void billPrintPreferences(String key, Bitmap value) {
		/*SharedPreferences sharedPreferences = PreferenceManager
				.getDefaultSharedPreferences(this);
		Editor editor = sharedPreferences.edit();
		editor.putString(key, value);
		editor.commit();*/
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		value.compress(Bitmap.CompressFormat.JPEG, 100, baos);   
		byte[] b = baos.toByteArray(); 

		String encodedImage = Base64.encodeToString(b, Base64.DEFAULT);
		SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
		Editor edit=sharedPreferences.edit();
		edit.putString("image_data",encodedImage);
		edit.commit();
	}

	void ShowFetchOnHoldItems() {
		hold_invoice_id.clear();
		invoice_id.clear();
		create_date.clear();
		employee_invoice.clear();
		customer_invoice.clear();
		invoice_total.clear();
		invoiceidexist = "";
		holdidexist = "";
		
		alertDialogDismiss = new AlertDialog.Builder(PosMainActivity.this,
				android.R.style.Theme_Translucent_NoTitleBar).create();
		LayoutInflater mInflater2 = LayoutInflater.from(PosMainActivity.this);
		View layout2 = mInflater2.inflate(R.layout.view_details, null);
		final ListView list = (ListView) layout2.findViewById(R.id.listView1);
		Button head = (Button) layout2.findViewById(R.id.button2);
		head.setText("Hold_Details");
		

		String selectQuery = "SELECT  * FROM "
				+ DatabaseForDemo.INVOICE_TOTAL_TABLE + " where "
				+ DatabaseForDemo.INVOICE_STATUS + "=\"hold\"";

		Cursor mCursordgsdfg = dbforloginlogoutReadPos.rawQuery(selectQuery, null);
		if (mCursordgsdfg != null) {
			if (mCursordgsdfg.moveToFirst()) {
				System.out.println(mCursordgsdfg.getCount() + "hariiiiiiiiii");
				do {
					if (mCursordgsdfg.isNull(mCursordgsdfg
							.getColumnIndex(DatabaseForDemo.INVOICE_HOLD_ID))) {
						hold_invoice_id.add("");
					} else {
						String hold = mCursordgsdfg
								.getString(mCursordgsdfg
										.getColumnIndex(DatabaseForDemo.INVOICE_HOLD_ID));
						hold_invoice_id.add(hold);
					}
					String invoiceid = mCursordgsdfg.getString(mCursordgsdfg
							.getColumnIndex(DatabaseForDemo.INVOICE_ID));
					invoice_id.add(invoiceid);
					String dateandtime = mCursordgsdfg.getString(mCursordgsdfg
							.getColumnIndex(DatabaseForDemo.CREATED_DATE));
					create_date.add(dateandtime);
					if (mCursordgsdfg.isNull(mCursordgsdfg
							.getColumnIndex(DatabaseForDemo.INVOICE_EMPLOYEE))) {
						employee_invoice.add("");
					} else {
						String ip = mCursordgsdfg
								.getString(mCursordgsdfg
										.getColumnIndex(DatabaseForDemo.INVOICE_EMPLOYEE));
						employee_invoice.add(ip);
					}
					if (mCursordgsdfg.isNull(mCursordgsdfg
							.getColumnIndex(DatabaseForDemo.INVOICE_CUSTOMER))) {
						customer_invoice.add("");
					} else {
						String name = mCursordgsdfg
								.getString(mCursordgsdfg
										.getColumnIndex(DatabaseForDemo.INVOICE_CUSTOMER));
						customer_invoice.add(name);
					}
					String ip1 = mCursordgsdfg.getString(mCursordgsdfg
							.getColumnIndex(DatabaseForDemo.INVOICE_TOTAL_AMT));
					invoice_total.add(ip1);

				} while (mCursordgsdfg.moveToNext());
			}
		}
		mCursordgsdfg.close();
		list.setItemsCanFocus(false);
		ImageAdapterForHold adapter = new ImageAdapterForHold(
				PosMainActivity.this, hold_invoice_id, invoice_id, create_date,
				employee_invoice, customer_invoice, invoice_total);
		adapter.setListener(PosMainActivity.this);
		list.setAdapter(adapter);
		list.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				mSubTotal = 0;
				mTaxTotal = 0;
				invoiceidexist = invoice_id.get(arg2);
				holdidexist = hold_invoice_id.get(arg2);
				String selectQuery = "SELECT  * FROM "
						+ DatabaseForDemo.INVOICE_ITEMS_TABLE + " where "
						+ DatabaseForDemo.INVOICE_ID + "=\""
						+ invoice_id.get(arg2) + "\"";

				Cursor mCursorsfasdsp = dbforloginlogoutReadPos.rawQuery(selectQuery, null);
				System.out.println("cusor countis jariskflskjfjsf:"
						+ mCursorsfasdsp.getCount());
				if (mCursorsfasdsp != null) {
					if (mCursorsfasdsp.moveToFirst()) {
						do {
							String name = mCursorsfasdsp.getString(mCursorsfasdsp
									.getColumnIndex(DatabaseForDemo.INVOICE_ITEM_ID));
							getIvoiceItemDetails(name, invoice_id.get(arg2));
						} while (mCursorsfasdsp.moveToNext());
						fetchOnHoldButton.setTag("0");
						fetchOnHoldButton.setText("Save On Hold");
					}
				}
				mCursorsfasdsp.close();
				alertDialogDismiss.dismiss();
			}
		});
		alertDialogDismiss.setView(layout2);
		alertDialogDismiss.show();

	}

	void getIvoiceItemDetails(String itemNo, String invoiceid) {
		try{
		List<Inventory> itemList = sqq.getSelectInvoiceItemList(itemNo,
				invoiceid);
		if (itemList == null) {
			Toast.makeText(getApplicationContext(), "Wrong Barcode", 1000)
					.show();
		}
		if (mItemList == null) {
			mItemList = new ArrayList<Inventory>();
		}

		mItemList.addAll(itemList);
		if (mItemList != null && mItemList.size() > 0) {
			mSubTotal = 0;
			mTaxTotal = 0;
			for (int i = 0; i < mItemList.size(); i++) {
				String qtyStr = mItemList.get(i).getQuantity();
				Double qty = 1.0;
				if (qtyStr != null && qtyStr.length() > 0) {
					qty = Double.valueOf(qtyStr);
				}

				mSubTotal += ((qty) * Double.valueOf(mItemList.get(i)
						.getPriceYouChange()));

				String taxStr = mItemList.get(i).getInventoryTaxTotal();
				if (taxStr != null && taxStr.length() > 0) {
					mTaxTotal += ((qty) * Double.valueOf(mItemList.get(i)
							.getInventoryTaxTotal()));
				}
			}

			if (mAdapter == null) {
				mAdapter = new InventoryListAdapter(PosMainActivity.this,
						mItemList);
				mAdapter.setListener(PosMainActivity.this);
				itemlistView.setAdapter(mAdapter);

			} else {
				mAdapter.setListener(PosMainActivity.this);
				mAdapter.notifyDataSetChanged();
			}
			mSubTotal = Double.valueOf(df.format(mSubTotal));
			mTaxTotal = Double.valueOf(df.format(mTaxTotal));
			mGrandTotal = Double.valueOf(df.format(mSubTotal + mTaxTotal));
			subTotalView.setText(String.valueOf("$" + mSubTotal));
			taxTotalview.setText(String.valueOf("$" + mTaxTotal));
			grandTotalview.setText(String.valueOf("$" + (mGrandTotal)));
			mAdapter.setListener(PosMainActivity.this);
			itemlistView.setSelection(0);
		}
		} catch (NumberFormatException e) {
			  e.printStackTrace();
			}catch (SQLiteException e12) {
				  e12.printStackTrace();
				}  catch (Exception e1) {
				  e1.printStackTrace();
				}
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		Log.v("seema", "tapa");
		hold_Status = "complete";
		/*HashMap<String, String> map = new HashMap<String, String>();
		map.put("paymentType", "Credit/Debit");
		map.put("amount", ""+Parameters.Cridetamount);
		map.put("details", "");
		payedsofarlist.add(map);*/
		if(Parameters.mercury_result.equals("Approved")){
			
			//if(Parameters.Cridetamount>=remainingamount){
				Log.w(""+Parameters.sentAmount, ""+remainingamount);
				Double sentValue=0.0;
				try{
			 sentValue=Double.valueOf(Parameters.sentAmount);
				}catch(NumberFormatException e){
					e.printStackTrace();
				}
				if (sentValue >= remainingamount) {
					change =sentValue- remainingamount;
					change = (double) Math.round(change * 100)
							/ (double) 100;
					directpayment = true;
					alertDialogtop.dismiss();
					/* HashMap<String, String> map = new HashMap<String, String>();
						map.put("paymentType", "Credit/Debit");
						map.put("amount", ""+Parameters.Cridetamount);
						map.put("details", "");*/
						//payedsofarlist.add(map);
					changeforcredit = 0.0;
					remainingforcredit =""+Parameters.sentAmount;
					checkforcredit = "";
					accforcredit = "";
					creditPayType(changeforcredit, remainingforcredit, accforcredit, checkforcredit);
					String listofitems = "";
					Double AvgCost = 0.00;
					for (int i = 0; i < mItemList.size(); i++) {
						String u_id0 = Parameters.randomValue();
						String Qtty = mItemList.get(i).getQuantity();
						String nameI = mItemList.get(i).getItemNameAdd();
						String nameId = mItemList.get(i).getItemNoAdd();
						String pricc = mItemList.get(i).getPriceYouChange();
						String AvgCos = mItemList.get(i).getAvgCost();
						String Description = mItemList.get(i).getSecondDescription();
						String taxx = mItemList.get(i).getInventoryTaxTotal();
						String department = mItemList.get(i).getDepartmentAdd();
						String vendor = mItemList.get(i).getInventoryVndr();
						String itemone = "<tr style='height:40px;overflow:hidden;' valign='top'><td align='left'>"+Qtty+" "+nameI+"</td><td align='right'>$"+pricc+"</td></tr>";
						listofitems = listofitems + itemone;
						AvgCost += Double.valueOf(AvgCos);
					}
					printStringCreation(lastsavedinvoiceid, listofitems, sentValue, 0.0, 0.0, 0.0);
					showReciptPopup();
				} else {
					remainingamount = remainingamount - sentValue;
					remainingamount = (int) Math
							.round(remainingamount * 100)
							/ (double) 100;
					  EditText total = (EditText) layouttop
								.findViewById(R.id.totalsave);
							  total.setText(""+remainingamount);
						 EditText remaining = (EditText) layouttop
								.findViewById(R.id.remaining);
						 remaining.setText(""+remainingamount);
					total.setText("$" + remainingamount);
					cashpaymentAmount = Math
							.ceil(remainingamount);
					 Button bigbutton = (Button) layouttop
							.findViewById(R.id.roundvalue);
					bigbutton.setText("" + cashpaymentAmount);
					directpayment = false;
					changeforcredit = 0.0;
					remainingforcredit = remaining.getText().toString();
					checkforcredit = "";
					accforcredit = "";
					
					remaining.setText(""+remainingamount);
					 HashMap<String, String> map = new HashMap<String, String>();
						map.put("paymentType", "Credit/Debit");
						map.put("amount", ""+Parameters.sentAmount);
						map.put("details", "");
						payedsofarlist.add(map);
				creditPayTypeforMercuryNotComplete(changeforcredit, ""+Parameters.sentAmount, accforcredit, checkforcredit, paymentTypestr);
					PaymentTypeAdapter adapter = new PaymentTypeAdapter(PosMainActivity.this, payedsofarlist);
					listviewpayedsofar.setAdapter(adapter);
				}
			
//				if(Parameters.Cridetamount==Parameters.sentAmount){
//					Log.w("e"+Parameters.Cridetamount, "e"+Parameters.sentAmount);
//					alertDialogtop.dismiss();
//				updateinvoicetable(lastsavedinvoiceid, "complete", "", "", "");
//				updateTheInvoice(lastsavedinvoiceid,""+Parameters.Cridetamount, "", "", "Credit/Debit");
//				String listofitems = "";
//				Double AvgCost = 0.00;
//		    	//createPrintRecipt(lastsavedinvoiceid, "",0.0, 0.0, 0.0, Parameters.Cridetamount, "", "Credit/Debit" );
//				for (int i = 0; i < mItemList.size(); i++) {
//					Log.v("seema", "tapadcf");
//					String u_id0 = Parameters.randomValue();
//					String Qtty = mItemList.get(i).getQuantity();
//					String nameI = mItemList.get(i).getItemNameAdd();
//					String nameId = mItemList.get(i).getItemNoAdd();
//					String pricc = mItemList.get(i).getPriceYouChange();
//					String AvgCos = mItemList.get(i).getAvgCost();
//					String Description = mItemList.get(i).getSecondDescription();
//					String taxx = mItemList.get(i).getInventoryTaxTotal();
//					String department = mItemList.get(i).getDepartmentAdd();
//					String vendor = mItemList.get(i).getInventoryVndr();
//					String itemone = "<tr style='height:40px;overflow:hidden;' valign='top'><td align='left'>"+Qtty+" "+nameI+"</td><td align='right'>$"+pricc+"</td></tr>";
//					listofitems = listofitems + itemone;
//					AvgCost += Double.valueOf(AvgCos);
//					
//				}
//				printStringCreation(lastsavedinvoiceid, listofitems, 0.0, 0.0, 0.0, Parameters.Cridetamount);
//				showReciptPopup();
//				}else{
//					Log.w("d"+Parameters.Cridetamount, "d"+Parameters.sentAmount);
//					remainingamount=Parameters.sentAmount-Parameters.Cridetamount;
//					  EditText total = (EditText) layouttop
//						.findViewById(R.id.totalsave);
//					  total.setText(""+remainingamount);
//				 EditText remaining = (EditText) layouttop
//						.findViewById(R.id.remaining);
//				 remaining.setText(""+remainingamount);
//				 HashMap<String, String> map = new HashMap<String, String>();
//					map.put("paymentType", "Credit/Debit");
//					map.put("amount", ""+Parameters.Cridetamount);
//					map.put("details", "");
//					payedsofarlist.add(map);
//					changeforcredit = 0.0;
//					remainingforcredit =""+Parameters.Cridetamount;
//					checkforcredit = "";
//					accforcredit = "";
//					creditPayTypeforMercuryNotComplete(changeforcredit, remainingforcredit, accforcredit, checkforcredit, paymentTypestr);
//					PaymentTypeAdapter adapter = new PaymentTypeAdapter(PosMainActivity.this, payedsofarlist);
//					listviewpayedsofar.setAdapter(adapter);
//				}
			/*}else{
				updateTheInvoice(lastsavedinvoiceid,""+Parameters.Cridetamount, "", "", "Credit/Debit");
			}*/
	 	
    	
	//	createPrintRecipt("12345", "", Parameters.Cridetamount, 0.0, 0.0, 0.0, "", "Credit/Debit");
		
	}
		if (resultCode ==2) {
			String value=data.getStringExtra("price");
			Log.v("price", value);
		}
	/*	if (resultCode == Activity.RESULT_OK) {
			PaymentConfirmation confirm = data
					.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);
			if (confirm != null) {
				try {
					JSONObject response = confirm.toJSONObject();

					JSONObject proof = response
							.getJSONObject("proof_of_payment");
					final String url;
					if (proof.has("rest_api")) {
						JSONObject innerobj = proof.getJSONObject("rest_api");
						String state = innerobj.getString("state");
						System.out.println("State val is:" + state);
						String paymentid = innerobj.getString("payment_id");
						System.out.println("payment id val is:" + paymentid);
						url = "https://api.sandbox.paypal.com/v1/payments/payment/"
								+ paymentid;

						if (Parameters.isNetworkAvailable(PosMainActivity.this)) {
							new Thread(new Runnable() {
								@Override
								public void run() {
									HttpClient httpclient = new DefaultHttpClient();
									HttpGet httppost = new HttpGet(url);
									httppost.setHeader("Content-Type",
											"application/x-www-form-urlencoded");
									httppost.setHeader("Authorization",
											"Bearer ENxom5Fof1KqAffEsXtxwEDa6E1HTEK__KVdIsaCYF8C");
									HttpResponse response1;
									try {
										response1 = httpclient
												.execute(httppost);
										HttpEntity entity = response1
												.getEntity();
										String responseText = EntityUtils
												.toString(entity);
										System.out
												.println("payment verification response is:"
														+ responseText);
									} catch (ClientProtocolException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}).start();
						} else {
							System.out.println("no network connection");
						}
					} else if (proof.has("adaptive_payment")) {
						JSONObject innerobj = proof
								.getJSONObject("adaptive_payment");
						String paykey = innerobj.getString("pay_key");
						System.out.println("paykey val is:" + paykey);
						final String appid = innerobj.getString("app_id");
						System.out.println("payment id val is:" + appid);
						url = "https://svcs.sandbox.paypal.com/AdaptivePayments/PaymentDetails?payKey="
								+ paykey
								+ "&requestEnvelope.errorLanguage=en_US";

						if (Parameters.isNetworkAvailable(PosMainActivity.this)) {

							new Thread(new Runnable() {
								@Override
								public void run() {
									HttpClient httpclient = new DefaultHttpClient();
									HttpGet httppost = new HttpGet(url);
									httppost.setHeader(
											"X-PAYPAL-SECURITY-USERID",
											"rameshbabu.nanotech-facilitator_api1.gmail.com");
									httppost.setHeader(
											"X-PAYPAL-SECURITY-PASSWORD",
											"1385190377");
									httppost.setHeader(
											"X-PAYPAL-SECURITY-SIGNATURE",
											"AiPC9BjkCyDFQXbSkoZcgqH3hpacAOSW2eCR1lVyn2qOKOPT9Ee-5iMC");
									httppost.setHeader(
											"X-PAYPAL-REQUEST-DATA-FORMAT",
											"NV");
									httppost.setHeader(
											"X-PAYPAL-RESPONSE-DATA-FORMAT",
											"NV");
									httppost.setHeader(
											"X-PAYPAL-APPLICATION-ID", appid);
									HttpResponse response1;

									try {
										response1 = httpclient
												.execute(httppost);
										HttpEntity entity = response1
												.getEntity();
										String responseText = EntityUtils
												.toString(entity);
										System.out
												.println("payment verification response is:"
														+ responseText);
									} catch (ClientProtocolException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									} catch (IOException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}
								}
							}).start();
						} else {
							System.out.println("No network connection");
						}
					} else {
						System.out
								.println("There is no response from the server");
					}

				} catch (JSONException e) {
					Log.e("paymentExample",
							"an extremely unlikely failure occurred: ", e);
				}
			}
		} else if (resultCode == Activity.RESULT_CANCELED) {
			Log.i("paymentExample", "The user canceled.");
		} else if (resultCode == PaymentActivity.RESULT_PAYMENT_INVALID) {
			Log.i("paymentExample",
					"An invalid payment was submitted. Please see the docs.");
		}else if (resultCode == 9876) {
			Log.i("paymentExample",
			"An invalid payment was submitted. Please see the docs.");
	System.out.println("the result code is 000");
	if(directpayment == true){
		creditPayType(changeforcredit, remainingforcredit, accforcredit, checkforcredit);
		showReciptPopup();
	}else{
		creditPayTypeforNotComplete(changeforcredit, remainingforcredit, accforcredit, checkforcredit, paymentTypestr);
		PaymentTypeAdapter adapter = new PaymentTypeAdapter(PosMainActivity.this, payedsofarlist);
		listviewpayedsofar.setAdapter(adapter);
	}
	if(lastsavedinvoiceid!=null){
		System.out.println("updated invoice is called");
		//updateTheInvoice(lastsavedinvoiceid);
	}else{
		invoice_forHold = store_id + "_"
				+ Parameters.randomInvoice();
		Log.e("invoice", Parameters.randomInvoice() + "  "
				+ invoice_forHold);
		lastsavedinvoiceid = invoice_forHold;
		System.out.println("last saved invoice id is:"+lastsavedinvoiceid);
		createPrintRecipt(invoice_forHold, "");
	}
}else if (resultCode == 12345) {
	System.out.println("payment is not done");
}else if (resultCode == 123456) {
	System.out.println("payment is not done in 6");
}*/
	}

	@Override
	public void onDestroy() {
		stopService(new Intent(this, PayPalService.class));
		super.onDestroy();
	}

	public interface OnHoldButtonClicked {

		void onEditClickedforpayment(View v, String string, String tag);

	}

	@Override
	public void onEditClickedforfetch(View v, final String stringhold) {
		// TODO Auto-generated method stub
try{
		mSubTotal = 0;
		mTaxTotal = 0;
		fetchOnHoldButton.setTag("0");
		fetchOnHoldButton.setText("Save On Hold");
		String selectQuery = "SELECT  * FROM "
				+ DatabaseForDemo.COMMANDS_PRINTER_TABLE + " where "
				+ DatabaseForDemo.COMMANDS_HOLDID + "=\"" + stringhold + "\"";
		final ArrayList<String> print_itemname = new ArrayList<String>();
		ArrayList<String> print_printername = new ArrayList<String>();
		ArrayList<String> print_time = new ArrayList<String>();
		ArrayList<String> print_message = new ArrayList<String>();
		Cursor mCudfhdfrsor = dbforloginlogoutReadPos.rawQuery(selectQuery, null);
		System.out.println("count for cursor   " + mCudfhdfrsor.getCount());
		if (mCudfhdfrsor != null) {
			if (mCudfhdfrsor.moveToFirst()) {
				do {
					String name = mCudfhdfrsor
							.getString(mCudfhdfrsor
									.getColumnIndex(DatabaseForDemo.COMMANDS_ITEM_NAME));
					print_itemname.add(name);
					name = mCudfhdfrsor
							.getString(mCudfhdfrsor
									.getColumnIndex(DatabaseForDemo.COMMANDS_PRINTER_NAME));
					print_printername.add(name);
					name = mCudfhdfrsor.getString(mCudfhdfrsor
							.getColumnIndex(DatabaseForDemo.COMMANDS_TIME));
					print_time.add(name);
					name = mCudfhdfrsor.getString(mCudfhdfrsor
							.getColumnIndex(DatabaseForDemo.COMMANDS_MESSAGE));
					print_message.add(name);

				} while (mCudfhdfrsor.moveToNext());
			}
		}
		mCudfhdfrsor.close();
		if (mode) {
			invoice_forHold = Parameters.randomValue();
			hold_Status = "hold";
			System.out.println("hold id val is:" + hold_Id);
			// createPrintRecipt(invoice_forHold,
			// hold_Id);
		}

		mSelectedItem = null;
		mSelectedPosition = -1;
		if (mItemList.isEmpty()) {
			fetchOnHoldButton.setTag("1");
			fetchOnHoldButton.setText("Fetch On Hold");
			mSubTotal = 0;
			mTaxTotal = 0;
			subTotalView.setText(String.valueOf("$" + mSubTotal));
			taxTotalview.setText(String.valueOf("$" + mTaxTotal));
			grandTotalview.setText(String.valueOf("$" + mSubTotal));
		}

		final AlertDialog alertDialog = new AlertDialog.Builder(
				PosMainActivity.this,
				android.R.style.Theme_Translucent_NoTitleBar).create();
		LayoutInflater mInflater2 = LayoutInflater.from(PosMainActivity.this);
		final View layoutforprint = mInflater2.inflate(R.layout.send_print,
				null);

		final LinearLayout ll4rd = (LinearLayout) layoutforprint
				.findViewById(R.id.listView1);
		Button save = (Button) layoutforprint.findViewById(R.id.save);
		Button cancel = (Button) layoutforprint.findViewById(R.id.cancel);

		ll4rd.removeAllViews();
		for (int count = 0; count < print_itemname.size(); count++) {
			final LinearLayout roww = new LinearLayout(PosMainActivity.this);
			roww.setOrientation(LinearLayout.HORIZONTAL);
			roww.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT,
					LayoutParams.WRAP_CONTENT));
			roww.setPadding(1, 1, 1, 1);
			fetchTextView = new TextView(PosMainActivity.this);
			fetchTextView.setText(print_itemname.get(count));

			fetchTextView.setId(count);
			fetchTextView.setLayoutParams(new LinearLayout.LayoutParams(200,
					LayoutParams.WRAP_CONTENT));
			roww.addView(fetchTextView);

			fetchprinter = new Spinner(PosMainActivity.this);
			fetchprinter.setId(100 + count);
			fetchprinter
					.setLayoutParams(new LinearLayout.LayoutParams(150, 45));
			roww.addView(fetchprinter);

			fetchMinutes = new EditText(PosMainActivity.this);
			fetchMinutes.setLayoutParams(new LinearLayout.LayoutParams(100,
					LayoutParams.WRAP_CONTENT));
			fetchMinutes.setTextSize(14);
			fetchMinutes.setId(1000 + count);
			fetchMinutes.setInputType(InputType.TYPE_CLASS_NUMBER);
			// fetchMinutes.setInputType(MODE_APPEND);
			roww.addView(fetchMinutes);

			fetchEdittext = new EditText(PosMainActivity.this);
			fetchEdittext.setLayoutParams(new LinearLayout.LayoutParams(500,
					LayoutParams.WRAP_CONTENT));
			fetchEdittext.setText(print_message.get(count));
			fetchEdittext.setTextSize(14);
			fetchEdittext.setId(10000 + count);
			fetchEdittext.setTag(print_message.get(count));
			roww.addView(fetchEdittext);

			ArrayList<String> printerlistval = new ArrayList<String>();
			printerlistval.clear();
			printerlistval.add("None");
			String query = "SELECT * from " + DatabaseForDemo.PRINTER_TABLE;
			Cursor cursdfhdfhor = dbforloginlogoutReadPos.rawQuery(query, null);

			if (cursdfhdfhor.getCount() > 0) {
				if (cursdfhdfhor != null) {
					if (cursdfhdfhor.moveToFirst()) {
						do {
							if (cursdfhdfhor
									.isNull(cursdfhdfhor
											.getColumnIndex(DatabaseForDemo.PRINTER_ID))) {

							} else {
								String catid = cursdfhdfhor
										.getString(cursdfhdfhor
												.getColumnIndex(DatabaseForDemo.PRINTER_ID));
								printerlistval.add(catid);
							}
						} while (cursdfhdfhor.moveToNext());
					}
				}
			}
			cursdfhdfhor.close();

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					PosMainActivity.this, android.R.layout.simple_list_item_1,
					printerlistval);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			fetchprinter.setAdapter(adapter);
			fetchprinter.setSelection(printerlistval.indexOf(print_printername
					.get(count)));
			long current = System.currentTimeMillis();
			current = Long.valueOf(print_time.get(count)) - current;
			current = current / 60000;
			fetchMinutes.setText("" + current);
			ll4rd.addView(roww);

		}

		save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method
				// stub
				alertDialog.dismiss();
				
				String here = DatabaseForDemo.COMMANDS_HOLDID + "=?";
				dbforloginlogoutWritePos.delete(DatabaseForDemo.COMMANDS_PRINTER_TABLE,
						here, new String[] { stringhold });
				mAdapter.notifyDataSetChanged();
				for (int cccc = 0; cccc < print_itemname.size(); cccc++) {
					long now = System.currentTimeMillis();
					now = now
							+ (Long.valueOf(((EditText) layoutforprint
									.findViewById(1000 + cccc)).getText()
									.toString().trim()) * 60 * 1000);

					ContentValues contentValues = new ContentValues();
					contentValues.put(
							DatabaseForDemo.COMMANDS_ITEM_NAME,
							""
									+ ((TextView) layoutforprint
											.findViewById(cccc)).getText());
					contentValues.put(
							DatabaseForDemo.COMMANDS_PRINTER_NAME,
							""
									+ ((Spinner) layoutforprint
											.findViewById(100 + cccc))
											.getSelectedItem());
					contentValues.put(DatabaseForDemo.COMMANDS_TIME, "" + now);
					contentValues.put(
							DatabaseForDemo.COMMANDS_MESSAGE,
							""
									+ ((EditText) layoutforprint
											.findViewById(10000 + cccc))
											.getText());
					contentValues.put(DatabaseForDemo.COMMANDS_HOLDID, ""
							+ stringhold);
					contentValues.put(DatabaseForDemo.UNIQUE_ID, ""
							+ Parameters.randomValue());
					dbforloginlogoutWritePos.insert(
							DatabaseForDemo.COMMANDS_PRINTER_TABLE, null,
							contentValues);
					contentValues.clear();
				}
				mItemList.clear();
				mSelectedItem = null;
				mSelectedPosition = -1;
				if (mItemList.isEmpty()) {
					fetchOnHoldButton.setTag("1");
					fetchOnHoldButton.setText("Fetch On Hold");
					mSubTotal = 0;
					mTaxTotal = 0;
					subTotalView.setText(String.valueOf("$" + mSubTotal));
					taxTotalview.setText(String.valueOf("$" + mTaxTotal));
					grandTotalview.setText(String.valueOf("$" + mSubTotal));
				}
			}
		});

		cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method
				// stub
				alertDialog.dismiss();
			}
		});

		alertDialog.setView(layoutforprint);
		alertDialog.show();
		/*
		 * mItemList.clear(); mSelectedItem = null; mSelectedPosition = -1; if
		 * (mItemList.isEmpty()) { fetchOnHoldButton.setTag("1");
		 * fetchOnHoldButton.setText("Fetch On Hold"); mSubTotal = 0; mTaxTotal
		 * = 0; subTotalView.setText(String.valueOf("$" + mSubTotal));
		 * taxTotalview.setText(String.valueOf("$" + mTaxTotal));
		 * grandTotalview.setText(String.valueOf("$" + mSubTotal));
		 * 
		 * }
		 */
} catch (NumberFormatException e) {
	  e.printStackTrace();
	} catch (SQLiteException e12) {
		  e12.printStackTrace();
		} catch (Exception e1) {
		  e1.printStackTrace();
		}
	}

	@Override
	public void onClickedforfetchDelete(View v, final String stringhold) {
		// TODO Auto-generated method stub

		final AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
		LayoutInflater mInflater = LayoutInflater.from(this);
		View layout = mInflater.inflate(R.layout.delete_popup, null);
		Button ok = (Button) layout.findViewById(R.id.ok);
		Button cancel = (Button) layout.findViewById(R.id.cancel);

		alertDialog1.setTitle("Delete");

		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				deleteInvoice(stringhold);
				alertDialogDismiss.dismiss();
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
	}

	void deleteInvoice(String stringhold) {
		String where = DatabaseForDemo.COMMANDS_HOLDID + "=?";
		dbforloginlogoutWritePos.delete(DatabaseForDemo.COMMANDS_PRINTER_TABLE, where,
				new String[] { stringhold });
		String selectQuery = "SELECT  * FROM "
				+ DatabaseForDemo.INVOICE_TOTAL_TABLE + " where "
				+ DatabaseForDemo.INVOICE_HOLD_ID + "=\"" + stringhold + "\"";
		String name = "ll";
		Cursor mCurqkldeovsor = dbforloginlogoutReadPos.rawQuery(selectQuery, null);
		if (mCurqkldeovsor != null) {
			if (mCurqkldeovsor.moveToFirst()) {
				do {
					name = mCurqkldeovsor.getString(mCurqkldeovsor
							.getColumnIndex(DatabaseForDemo.INVOICE_ID));

				} while (mCurqkldeovsor.moveToNext());
			}
		}
		mCurqkldeovsor.close();
		String bhere = DatabaseForDemo.INVOICE_ID + "=?";
		dbforloginlogoutWritePos.delete(DatabaseForDemo.INVOICE_ITEMS_TABLE, bhere,
				new String[] { name });

		String here = DatabaseForDemo.INVOICE_HOLD_ID + "=?";
		dbforloginlogoutWritePos.delete(DatabaseForDemo.INVOICE_TOTAL_TABLE, here,
				new String[] { stringhold });
	}

	

	@SuppressWarnings("deprecation")
	private boolean showPrintTimer() {
		Boolean printTimer = false;
		Log.e("sizzee", mItemList.size() + "");
		for (int count = 0; count < mItemList.size(); count++) {
			String query1 = "SELECT " + DatabaseForDemo.INVENTORY_DEPARTMENT
					+ " from " + DatabaseForDemo.INVENTORY_TABLE + " where "
					+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\""
					+ mItemList.get(count).getItemNoAdd() + "\"";
			Cursor cufdfdhrsor1 = dbforloginlogoutReadPos.rawQuery(query1, null);
			String deptid = "";
			if (cufdfdhrsor1.getCount() > 0) {
				if (cufdfdhrsor1 != null) {
					if (cufdfdhrsor1.moveToFirst()) {
						do {
							if (cufdfdhrsor1
									.isNull(cufdfdhrsor1
											.getColumnIndex(DatabaseForDemo.INVENTORY_DEPARTMENT))) {

							} else {
								deptid = cufdfdhrsor1
										.getString(cufdfdhrsor1
												.getColumnIndex(DatabaseForDemo.INVENTORY_DEPARTMENT));
							}
						} while (cufdfdhrsor1.moveToNext());
					}
				}
			}
			cufdfdhrsor1.close();
			Cursor mCurseksdhor3 = dbforloginlogoutReadPos.rawQuery("select *from "
					+ DatabaseForDemo.DEPARTMENT_PRINTER_COMMANDS + " where "
					+ DatabaseForDemo.DepartmentID + "=\"" + deptid + "\"", null);

			String printerdata = "None";
			if (mCurseksdhor3.getCount() > 0) {
				if (mCurseksdhor3 != null) {
					if (mCurseksdhor3.moveToFirst()) {
						do {
							if (mCurseksdhor3
									.isNull(mCurseksdhor3
											.getColumnIndex(DatabaseForDemo.PrinterForDept))) {
								printerdata = "None";
							} else {
								printerdata = mCurseksdhor3
										.getString(mCurseksdhor3
												.getColumnIndex(DatabaseForDemo.PrinterForDept));
							}
						} while (mCurseksdhor3.moveToNext());
					}
				}
			}
			mCurseksdhor3.close();
			if (!printerdata.equals("None")) {
				printTimer = true;
				Log.e("", printTimer + "      printer data val is:  "
						+ printerdata);
				break;
			}
			Log.e("", printTimer + "      printer data val is:  " + printerdata);
		}
		return printTimer;
	}

	void getItemDetailsAndQuantitiy(boolean isUpdate, String itemNo,
			String mQuantity) {
		try{
		Log.v("" + itemNo, "" + mQuantity);

		List<Inventory> itemList = null;
		Log.v("hai", "srat  oooooo");
		itemList = sqq.getAllInventoryList(itemNo, null, null, null);
		Log.v("hai", "srat  oooooo2");
		mEnterItemInventory = null;
		if (itemList == null) {
			Log.v("hai", "srat  toast");
			Toast.makeText(PosMainActivity.this, "Wrong Barcode", 2000).show();
		} else {
			Log.v("hai", "srat  not");
			if (itemList.size() <= 0) {
				Log.v("hai", "srat  tata");
				Toast.makeText(PosMainActivity.this, "Wrong Barcode", 2000)
						.show();
			} else {
				Log.v("hai", "srat  " + itemList.size());
				mEnterItemInventory = itemList.get(0);
				fetchOnHoldButton.setTag("0");
				fetchOnHoldButton.setText("Save On Hold");
			}
		}
		Log.v("hai", "srat  sttt");

		Log.v("hai", "srat  naottt");
		if (mItemList == null) {
			mItemList = new ArrayList<Inventory>();
		}
		if (mEnterItemInventory != null) {
			Log.v("hai", "srat  soo");
			mEnterItemInventory.setQuantity(mQuantity);
			mItemList.add(mEnterItemInventory);
		} else {
			Toast.makeText(PosMainActivity.this, "Wrong Barcode", 2000).show();
			// mItemList.remove(mSelectedPosition);
			// mSelectedItem.setQuantity(mQuantity);
			// mItemList.add(mSelectedPosition, mSelectedItem);
		}

		if (mItemList != null && mItemList.size() > 0) {

			for (int i = 0; i < mItemList.size(); i++) {
				String qtyStr = mItemList.get(i).getQuantity();
				Double qty = 1.0;
				if (qtyStr != null && qtyStr.length() > 0) {
					qty = Double.valueOf(qtyStr);
				}
				mSubTotal += ((qty) * Double.valueOf(mItemList.get(i)
						.getPriceYouChange()));

				String taxStr = mItemList.get(i).getInventoryTaxTotal();
				if (taxStr != null && taxStr.length() > 0) {
					mTaxTotal += ((qty) * Double.valueOf(mItemList.get(i)
							.getInventoryTaxTotal()));
				}
			}

			if (mAdapter == null) {
				mAdapter = new InventoryListAdapter(PosMainActivity.this,
						mItemList);
				mAdapter.setListener(PosMainActivity.this);
				itemlistView.setAdapter(mAdapter);
			} else {
				mAdapter.setListener(PosMainActivity.this);
				mAdapter.notifyDataSetChanged();
			}
			mAdapter.setListener(PosMainActivity.this);
			itemlistView.setSelection(0);
		}

		mSubTotal = Double.valueOf(df.format(mSubTotal));
		mTaxTotal = Double.valueOf(df.format(mTaxTotal));
		subTotalView.setText(String.valueOf("$" + mSubTotal));
		taxTotalview.setText(String.valueOf("$" + mTaxTotal));
		mGrandTotal = mSubTotal + mTaxTotal;
		mGrandTotal = Double.valueOf(df.format(mGrandTotal));
		grandTotalview.setText(String.valueOf("$" + mGrandTotal));
		} catch (NumberFormatException e) {
			  e.printStackTrace();
			} catch (SQLiteException e12) {
				  e12.printStackTrace();
				} catch (Exception e1) {
				  e1.printStackTrace();
				}
	}

	void discountMainMethod(String discoun) {
		try{
		Double discount = Double.valueOf(discoun);
		Double pric = 0.0;
		Cursor mCubdfhdfgqrsor = dbforloginlogoutReadPos.rawQuery(
				"select * from " + DatabaseForDemo.INVENTORY_TABLE + " where "
						+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\""
						+ mSelectedItem.getItemNoAdd() + "\"", null);
		System.out.println(mCubdfhdfgqrsor);
		if (mCubdfhdfgqrsor != null) {
			if (mCubdfhdfgqrsor.moveToFirst()) {
				do {
					String catid = mCubdfhdfgqrsor
							.getString(mCubdfhdfgqrsor
									.getColumnIndex(DatabaseForDemo.INVENTORY_PRICE_CHANGE));
					pric = Double.valueOf(catid);
				} while (mCubdfhdfgqrsor.moveToNext());
			}
		}
		mCubdfhdfgqrsor.close();
		mSubTotal = 0;
		mTaxTotal = 0;
		Double qqnty = Double.valueOf(mSelectedItem.getQuantity());
		Double pric1 = pric / qqnty;
		pric1 = pric * (discount / 100);
		pric = pric - pric1;
		String nameee = mSelectedItem.getItemNameAdd();
		if (nameee.contains(" Discount ")) {
			nameee.indexOf(" Discount ");
			String newStr = nameee.substring(0, nameee.indexOf(" Discount "));
			mSelectedItem.setItemNameAdd(newStr + " Discount " + discoun + "%");
		} else {
			mSelectedItem.setItemNameAdd(nameee + " Discount " + discoun + "%");
		}
		getItemDetailspricechange(true, mSelectedItem.getItemNoAdd(),
				(df.format(pric)).toString());
		} catch (NumberFormatException e) {
			  e.printStackTrace();
			} catch (SQLiteException e12) {
				  e12.printStackTrace();
				} catch (Exception e1) {
				  e1.printStackTrace();
				}
	}

	private void showspiltLists(ArrayList<Inventory> mItemDiscountList) {
		// TODO Auto-generated method stub

		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseForDemo.SPILT_HOLDID, "" + holdidexist);
		contentValues.put(DatabaseForDemo.SPILT_HOLDID_UNIQE,
				"" + Parameters.generateRandomNumber());
		contentValues.put(DatabaseForDemo.SPILT_LIST, "" + mItemDiscountList);
		dbforloginlogoutWritePos.insert(DatabaseForDemo.SPILT_LOCAL_TABLE, null,
				contentValues);
		Log.v("hari","januuuu");
		contentValues.clear();
	}
	
	void createHtmlFile(String webtext){
			try {
				File myFile = new File("/sdcard/printData/billFile.html");
				myFile.createNewFile();
				FileOutputStream fOut = new FileOutputStream(myFile);
				OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
				myOutWriter.append(""+webtext);
				myOutWriter.close();
				fOut.close();
				System.out.println("html written successfully..");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
				showAlertDialog(PosMainActivity.this, "Print Text", "Getting an Error" ,
						false);
			} catch (IOException e) {
				showAlertDialog(PosMainActivity.this, "Print Text", "Getting an Error" ,
						false);
				e.printStackTrace();
			}
			billPrint.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/printData/billFile.html");
		}
	
	public Drawable getBitmapFromView(View view) {
	    Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
	    Canvas canvas = new Canvas(returnedBitmap);
	    Drawable bgDrawable =view.getBackground();
	    if (bgDrawable!=null) 
	        bgDrawable.draw(canvas);
	    else 
	        canvas.drawColor(Color.WHITE);
	    view.draw(canvas);
	    Drawable d =new BitmapDrawable(getResources(),returnedBitmap);
	    return d;
	}
	
	 Bitmap drawableToBitmap(Drawable drawable) {
	    if (drawable instanceof BitmapDrawable) {
	        return ((BitmapDrawable)drawable).getBitmap();
	    }

	    int width = drawable.getIntrinsicWidth();
	    width = width > 0 ? width : 1;
	    int height = drawable.getIntrinsicHeight();
	    height = height > 0 ? height : 1;

	    Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
	    Canvas canvas = new Canvas(bitmap); 
	    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
	    drawable.draw(canvas);

	    return bitmap;
	}
	@SuppressWarnings("deprecation")
	public void showAlertDialogforPrint(Context context, String title,
			String message, Boolean status,final String printername, final String print_text,final Bitmap map) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();

		alertDialog.setTitle(title);

		alertDialog.setMessage(message);

		
		alertDialog.setButton("Yes", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				printText(printername, print_text, map);
				dialog.dismiss();
			}
		});
		alertDialog.setButton2("No", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		});
		alertDialog.show();
	}
	Boolean modifiersHaving( String itemnum){
		Boolean boolValue = false;
		 
		modifierItemArr.clear();
		String selectQuerysrii= "SELECT  * FROM " + DatabaseForDemo.INVENTORY_TABLE + " WHERE "
				+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\"" + itemnum + "\";";
		Cursor mCursorsrii = dbforloginlogoutReadPos.rawQuery(selectQuerysrii, null);
		if (mCursorsrii != null) {
			if (mCursorsrii.moveToFirst()) {
				do {
					itemidrrrr = mCursorsrii
							.getString(mCursorsrii
									.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NAME));
				} while (mCursorsrii.moveToNext());
			}
		}
		mCursorsrii.close();
		
		String selectQuery = "SELECT  "+DatabaseForDemo.MODIFIER_ITEM_NO+","+DatabaseForDemo.INVENTORY_ITEM_NAME+" FROM " + DatabaseForDemo.MODIFIER_TABLE + " WHERE "
				+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\"" + itemnum + "\";";
		Cursor mCursoiwiwiwr = dbforloginlogoutReadPos.rawQuery(selectQuery, null);
		System.out.println(mCursoiwiwiwr);
		if (mCursoiwiwiwr != null) {
			if (mCursoiwiwiwr.moveToFirst()) {
				do {
					HashMap<String, String> map = new HashMap<String, String>();
					String itemid = mCursoiwiwiwr
							.getString(mCursoiwiwiwr
									.getColumnIndex(DatabaseForDemo.MODIFIER_ITEM_NO));
					String itemname = mCursoiwiwiwr
							.getString(mCursoiwiwiwr
									.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NAME));
					map.put("itemno",
							itemid);
					map.put("itemname",
							itemname);
					modifierItemArr.add(map);
					Log.v("modifyers",""+map);
				} while (mCursoiwiwiwr.moveToNext());
			}
		}
		mCursoiwiwiwr.close();
if(modifierItemArr.size()>0){
		final AlertDialog alertDialog1fff = new AlertDialog.Builder(PosMainActivity.this).create();
		LayoutInflater mInflater = LayoutInflater.from(this);
		View layout = mInflater.inflate(R.layout.modifiers_item, null);
		 showmodifiers = (LinearLayout) layout.findViewById(R.id.showmodifiers);
		Button ok = (Button) layout.findViewById(R.id.m_ok);
		Button cancel = (Button) layout.findViewById(R.id.m_cancel);
		alertDialog1fff.setTitle("Please Select The Modifiers");
		final ArrayList<String> senditems=new ArrayList<String>();
		int totalcount = modifierItemArr.size();
		int itemcount = 3;
		int to = totalcount / itemcount;
		int too = totalcount % itemcount;
		int i = to;
		if (too != 0) {
			i = to + 1;
		}
		int stLoop = 0;
		int endLoop = 0;
		for (int llC = 1; llC <= i; llC++) {
			stLoop = (llC - 1) * itemcount;
			endLoop = llC * itemcount;
			 LinearLayout rowlayout = new LinearLayout(PosMainActivity.this);
			rowlayout.setOrientation(LinearLayout.HORIZONTAL);
			rowlayout.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
					LayoutParams.WRAP_CONTENT));
			rowlayout.setPadding(10, 10, 20, 10);

			if (endLoop >= totalcount) {
				endLoop = totalcount;
			}
			for (int BTn = stLoop; BTn < endLoop; BTn++) {
				modiferiemtButton = new Button(PosMainActivity.this);
				modiferiemtButton.setText("" + modifierItemArr.get(BTn)
						.get("itemname"));
				modiferiemtButton.setTag("" + modifierItemArr.get(BTn)
						.get("itemno"));
				modiferiemtButton.setTextSize(15);
				LayoutParams lp = new LayoutParams(130, 60);
				modiferiemtButton.setPadding(0, 0, 0, 0);
				View vvvv=new View(PosMainActivity.this);
				LayoutParams lpf = new LayoutParams(10, 10);
				rowlayout.addView(vvvv, lpf);
				modiferiemtButton.setBackgroundResource(R.drawable.bluebutton);
				rowlayout.addView(modiferiemtButton, lp);
				modiferiemtButton.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String str1 = v.getTag().toString();
						
						if(senditems.contains(str1)){
							senditems.remove(str1);
							v.setBackgroundResource(R.drawable.bluebutton);
						}else{
							senditems.add(str1);
							v.setBackgroundResource(R.drawable.buttonbackground);
						}
					}
				});
			}
			showmodifiers.addView(rowlayout);
		}
		ok.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				for (int BTn1 = 0; BTn1 < senditems.size(); BTn1++) {
					Log.v("BTn1",""+senditems.get(BTn1));
					mSubTotal = 0;
					mTaxTotal = 0;
					if (checkitemExists(senditems.get(BTn1), "1")) {
						//getItemDetails(false, senditems.get(BTn1), null);
						Log.v("BTn1",""+senditems.get(BTn1));
						getItemDetailsChangeName(false, senditems.get(BTn1), null,itemidrrrr);
					}
				}
				alertDialog1fff.dismiss();
			}
		});
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				alertDialog1fff.dismiss();
			}
		});
		alertDialog1fff.setView(layout);
		alertDialog1fff.show();
}else{
	boolValue=true;
}
		return boolValue;
	}
	
	LinearLayout showmodifiers;
	ArrayList<HashMap<String, String>> modifierItemArr = new ArrayList<HashMap<String, String>>();
	void modifiersSelectPopup(){
		
	}
	void updateInventory_Stock(String itemno,String stockval){
			final ArrayList<JSONObject> getlist = new ArrayList<JSONObject>();
			String selectQueryforinstantpo = "SELECT  * FROM "
					+ DatabaseForDemo.INVENTORY_TABLE + " where "
					+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\"" + itemno
					+ "\"";

			Cursor mCursorforvendor1 = dbforloginlogoutReadPos.rawQuery(
					selectQueryforinstantpo, null);
			if (mCursorforvendor1 != null) {
				if (mCursorforvendor1.moveToFirst()) {
					do {
						try {
							JSONObject jsonobj = new JSONObject();
							String departmentforproduct = mCursorforvendor1.getString(mCursorforvendor1
									.getColumnIndex(DatabaseForDemo.INVENTORY_DEPARTMENT));
							jsonobj.put(
									DatabaseForDemo.INVENTORY_DEPARTMENT,
									departmentforproduct);
							String itemnos = mCursorforvendor1.getString(mCursorforvendor1
									.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NO));
							jsonobj.put(
									DatabaseForDemo.INVENTORY_ITEM_NO,
									itemnos);
							String itemname = mCursorforvendor1.getString(mCursorforvendor1
									.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NAME));
							jsonobj.put(
									DatabaseForDemo.INVENTORY_ITEM_NAME,
									itemname);
							String desc2 = mCursorforvendor1.getString(mCursorforvendor1
									.getColumnIndex(DatabaseForDemo.INVENTORY_SECOND_DESCRIPTION));
							jsonobj.put(
									DatabaseForDemo.INVENTORY_SECOND_DESCRIPTION,
									desc2);
							String cost = mCursorforvendor1.getString(mCursorforvendor1
									.getColumnIndex(DatabaseForDemo.INVENTORY_AVG_COST));
							jsonobj.put(
									DatabaseForDemo.INVENTORY_AVG_COST,
									cost);
							String pricecharge = mCursorforvendor1.getString(mCursorforvendor1
									.getColumnIndex(DatabaseForDemo.INVENTORY_PRICE_CHANGE));
							jsonobj.put(
									DatabaseForDemo.INVENTORY_PRICE_CHANGE,
									pricecharge);
							String pricetax = mCursorforvendor1.getString(mCursorforvendor1
									.getColumnIndex(DatabaseForDemo.INVENTORY_PRICE_TAX));
							jsonobj.put(
									DatabaseForDemo.INVENTORY_PRICE_TAX,
									pricetax);
							String instock = mCursorforvendor1.getString(mCursorforvendor1
									.getColumnIndex(DatabaseForDemo.INVENTORY_IN_STOCK));
							jsonobj.put(
									DatabaseForDemo.INVENTORY_IN_STOCK,
									instock);
							Log.v("instock",""+instock);
							String vendor = mCursorforvendor1.getString(mCursorforvendor1
									.getColumnIndex(DatabaseForDemo.INVENTORY_VENDOR));
							jsonobj.put(
									DatabaseForDemo.INVENTORY_VENDOR,
									vendor);
							String taxprod = mCursorforvendor1.getString(mCursorforvendor1
									.getColumnIndex(DatabaseForDemo.INVENTORY_PRICE_TAX));
							jsonobj.put(
									DatabaseForDemo.INVENTORY_PRICE_TAX,
									taxprod);
							String uniqueid = mCursorforvendor1.getString(mCursorforvendor1
									.getColumnIndex(DatabaseForDemo.UNIQUE_ID));
							jsonobj.put(DatabaseForDemo.UNIQUE_ID,
									uniqueid);
							String taxone = mCursorforvendor1.getString(mCursorforvendor1
									.getColumnIndex(DatabaseForDemo.INVENTORY_TAXONE));
							jsonobj.put(
									DatabaseForDemo.INVENTORY_TAXONE,
									taxone);
							String notes = mCursorforvendor1.getString(mCursorforvendor1
									.getColumnIndex(DatabaseForDemo.INVENTORY_NOTES));
							jsonobj.put(
									DatabaseForDemo.INVENTORY_NOTES,
									notes);
							
							jsonobj.put(DatabaseForDemo.CHECKED_VALUE,
									"true");
							String quantity = mCursorforvendor1.getString(mCursorforvendor1
									.getColumnIndex(DatabaseForDemo.INVENTORY_QUANTITY));
							jsonobj.put(
									DatabaseForDemo.INVENTORY_QUANTITY,
									quantity);
							getlist.add(jsonobj);
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					} while (mCursorforvendor1.moveToNext());
				}
			}
			mCursorforvendor1.close();
			try {
				JSONObject data = new JSONObject();
				JSONArray fields = new JSONArray();
				for (int i = 0; i < getlist.size(); i++) {
					fields.put(i, getlist.get(i));
					data.put("fields", fields);
					dataval = data.toString();
					System.out.println("data val is:" + dataval);
				}
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			if (Parameters.OriginalUrl.equals("")) {
				System.out.println("there is no server url val");
			} else {
				boolean isnet = Parameters.isNetworkAvailable(PosMainActivity.this);
				if (isnet) {
					new Thread(new Runnable() {
						@Override
						public void run() {
							JsonPostMethod jsonpost = new JsonPostMethod();
							String response = jsonpost
									.postmethodfordirect(
											"admin",
											"abcdefg",
											DatabaseForDemo.INVENTORY_TABLE,
											Parameters.currentTime(),
											Parameters.currentTime(),
											dataval, "true");
							System.out.println("response test is:"
									+ response);
							String servertiem = null;
							try {
								JSONObject obj = new JSONObject(
										response);
								JSONObject responseobj = obj
										.getJSONObject("response");
								servertiem = responseobj
										.getString("server-time");
								System.out.println("servertime is:"
										+ servertiem);
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
								for (int jj = 0, ii = 0; jj < array2
										.length()
										&& ii < array.length(); jj++, ii++) {
									String deletequerytemp = array2
											.getString(jj);
									String deletequery1 = deletequerytemp
											.replace("'", "\"");
									String deletequery = deletequery1
											.replace("\\\"", "'");
									System.out.println("delete query"
											+ jj + " is :"
											+ deletequery);

									String insertquerytemp = array
											.getString(ii);
									String insertquery1 = insertquerytemp
											.replace("'", "\"");
									String insertquery = insertquery1
											.replace("\\\"", "'");
									System.out.println("delete query"
											+ jj + " is :"
											+ insertquery);
try{
									dbforloginlogoutWritePos.execSQL(deletequery);
									dbforloginlogoutWritePos.execSQL(insertquery);
}catch(SQLiteException qq){
	qq.printStackTrace();
}
		
									System.out
											.println("queries executed"
													+ ii);

								}
							} catch (JSONException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							dataval = "";

							String select = "select *from "
									+ DatabaseForDemo.MISCELLANEOUS_TABLE;
							Cursor cursoqmjgfr = dbforloginlogoutWritePos.rawQuery(select, null);
							if (cursoqmjgfr.getCount() > 0) {
								dbforloginlogoutWritePos.execSQL("update "
										+ DatabaseForDemo.MISCELLANEOUS_TABLE
										+ " set "
										+ DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL
										+ "=\"" + servertiem + "\"");
								
							} else {
								
								ContentValues contentValues1 = new ContentValues();
								contentValues1.put(
										DatabaseForDemo.MISCEL_STORE,
										"store1");
								contentValues1.put(
										DatabaseForDemo.MISCEL_PAGEURL,
										"");
								contentValues1
										.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL,
												Parameters
														.currentTime());
								contentValues1
										.put(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL,
												Parameters
														.currentTime());
								dbforloginlogoutWritePos.insert(
										DatabaseForDemo.MISCELLANEOUS_TABLE,
										null, contentValues1);
							}
							cursoqmjgfr.close();
						}
					}).start();
				} else {

					ContentValues contentValues1 = new ContentValues();
					contentValues1.put(DatabaseForDemo.QUERY_TYPE,
							"insert");
					contentValues1.put(DatabaseForDemo.PENDING_USER_ID,
							Parameters.userid);
					contentValues1.put(DatabaseForDemo.PAGE_URL,
							"saveinfo.php");
					contentValues1.put(
							DatabaseForDemo.TABLE_NAME_PENDING,
							DatabaseForDemo.VENDOR_TABLE);
					contentValues1.put(
							DatabaseForDemo.CURRENT_TIME_PENDING,
							Parameters.currentTime());
					contentValues1.put(DatabaseForDemo.PARAMETERS,
							dataval);
					dbforloginlogoutWritePos.insert(DatabaseForDemo.PENDING_QUERIES_TABLE,
							null, contentValues1);
					dataval = "";
				}
			}
	}

}
