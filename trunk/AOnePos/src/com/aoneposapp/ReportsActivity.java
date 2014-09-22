package com.aoneposapp;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;

import net.authorize.android.model.CommonCode;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.MailTo;
import android.net.ParseException;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.provider.MediaStore.Images;
import android.provider.MediaStore.Images.ImageColumns;
import android.provider.MediaStore.MediaColumns;
import android.support.v4.app.FragmentActivity;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.webkit.WebView;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.aoneposapp.adapters.EmployeeReportAdapter;
import com.aoneposapp.adapters.InvoiceReportsAdapter;
import com.aoneposapp.adapters.ReportItemAdapter;
import com.aoneposapp.mercury.ReceiptShowActivity;
import com.aoneposapp.starprinter.PrinterFunctions;
import com.aoneposapp.utils.Constants;
import com.aoneposapp.utils.DatabaseForDemo;
import com.aoneposapp.utils.Parameters;
import com.aoneposapp.utils.ServerSyncClass;
import com.epson.eposprint.Builder;
import com.epson.eposprint.EposException;
import com.epson.eposprint.Print;

public class ReportsActivity extends FragmentActivity {
//	private EditText fromdate,  totime;
	TextView todate, fromtime, fromdate,  totime;
	FragmentActivity activity;
	private Spinner productType, paymentType, reportType, orderStatus,catspr1,dptspr1,
			employee_id, stor_id1;
	ArrayList<String> autoTextStringsItems = new ArrayList<String>();
	String from = "", to = "";
	WebView web1;
	int stocksize=0;
	static final int REQUEST_CODE = 12345;
	String typeForsavefile="";
	DecimalFormat df = new DecimalFormat("#.##");
	Double Total_Avg_Cost = 0.0, Total_your_cost = 0.0, Total_Qty = 0.0,
			Total_Tax = 0.0, Total_Discount = 0.0, Total_Tax_amount = 0.0,
			subTotal = 0.0, Total_NonTax_amount = 0.0, gross = 0.0;
	ImageView slideButton, image0, image1, image2, image3, image4, image5,
			image6, image7, image8, logout, logout2;
	AutoCompleteTextView item_auto ,stockitem_auto;
	LinearLayout layout_inflate;
	ArrayList<String> itemNameArray = new ArrayList<String>();
	ArrayList<String> arr1 = new ArrayList<String>();
	ArrayList<String> arr2 = new ArrayList<String>();
	ArrayList<String> arr3 = new ArrayList<String>();
	ArrayList<String> arr4 = new ArrayList<String>();
	ArrayList<String> arr5 = new ArrayList<String>();
	ArrayList<String> arr6 = new ArrayList<String>();
	ArrayList<String> arr7 = new ArrayList<String>();
	ArrayList<String> arr8 = new ArrayList<String>();
	ArrayList<String> arr9= new ArrayList<String>();
	ArrayList<String> dparrlist= new ArrayList<String>();
	static String[] paytypes = { "All", "Cash", "Check", "Credit/Debit", "Account" };
	static String[] reporttypes = { "Invoice Total Reports", "Payment Type Reports", "Department Wise Reports", "Flash Reports", "Employee Shift Reports", "Detail Daily Reports"};
	static String[] orderstatusarr = { "complete", "hold", "void" };
	private int year;
	Calendar c;
	Button go, savefile, print_button,go_stock;
	private int month;
	private int day;
	private int hourOfDay;
	private int minute;
	private int ampm;
	boolean FDate, TDate, FTime, TTime;
	private static final int Time_PICKER_ID = 0;
	static final int DATE_DIALOG_ID = 100;
	ArrayList<String> vendorspinnerdata = new ArrayList<String>();
	ArrayList<String> employeeidData = new ArrayList<String>();
	ArrayList<String> customeridData = new ArrayList<String>();
	DatabaseForDemo sqlDBReport;
	Button invetory_reports, sales_reports;
	LinearLayout inventory_layout,sales_layout;
	SQLiteDatabase dbforloginlogoutWriteReport,dbforloginlogoutReadReport;
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.reports_activity);
		Parameters.printerContext=ReportsActivity.this;
		sqlDBReport=new DatabaseForDemo(ReportsActivity.this);
		dbforloginlogoutWriteReport = sqlDBReport.getWritableDatabase();
		dbforloginlogoutReadReport = sqlDBReport.getReadableDatabase();
		   web1 = (WebView) findViewById(R.id.webfor_print);
		layout_inflate=(LinearLayout) findViewById(R.id.layout_inflate);
		Parameters.ServerSyncTimer();
		slideButton = (ImageView) findViewById(R.id.slideButton);
		vendorspinnerdata.add("All");
		employeeidData.add("All");
		customeridData.add("All");
		sales_reports=(Button) findViewById(R.id.addreports);
		invetory_reports=(Button) findViewById(R.id.viewreports);
		inventory_layout=(LinearLayout) findViewById(R.id.invetory_reports);
	sales_layout=(LinearLayout) findViewById(R.id.sales_reports);
	invetory_reports.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				layout_inflate.removeAllViews();
				stockLayoutView();
//				fromdate.setText("");
//				todate.setText("");
//				fromtime.setText("");
//				totime.setText("");
				fromdate.setText(CommonCode.getDateString("MM-dd-yyyy"));
				todate.setText(CommonCode.getDateString("MM-dd-yyyy"));
				fromtime.setText("12:00");
				totime.setText("12:00");
				invetory_reports.setBackgroundResource(R.drawable.highlightedtopmenuitem);
				sales_reports.setBackgroundResource(R.drawable.toprightmenu);
				inventory_layout.setVisibility(View.VISIBLE);
				sales_layout.setVisibility(View.GONE);
				invetory_reports.setTextColor(Color.BLACK);
				sales_reports.setTextColor(Color.WHITE);
			}
		});
	sales_reports.setOnClickListener(new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			layout_inflate.removeAllViews();
//			fromdate.setText("");
//			todate.setText("");
//			fromtime.setText("");
//			totime.setText("");
			fromdate.setText(CommonCode.getDateString("MM-dd-yyyy"));
			todate.setText(CommonCode.getDateString("MM-dd-yyyy"));
			fromtime.setText("12:00");
			totime.setText("12:00");
			sales_reports.setBackgroundResource(R.drawable.highlightedtopmenuitem);
			invetory_reports.setBackgroundResource(R.drawable.toprightmenu);
			sales_layout.setVisibility(View.VISIBLE);
			inventory_layout.setVisibility(View.GONE);
			sales_reports.setTextColor(Color.BLACK);
			invetory_reports.setTextColor(Color.WHITE);
		}
	});
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
		image5.setBackgroundResource(R.drawable.r3);
		TextView loginnameempid = (TextView) findViewById(R.id.loginnameval);
		loginnameempid.setText(Parameters.usertypeloginvalue);
		logout.setBackgroundResource(R.drawable.logoutnormal);
		image0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(ReportsActivity.this,
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
					Intent intent1 = new Intent(ReportsActivity.this,
							InventoryActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							ReportsActivity.this,
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
				Intent intent1 = new Intent(ReportsActivity.this,
						StoresActivity.class);
				startActivity(intent1);
				finish();
			} else {
				showAlertDialog(
						ReportsActivity.this,
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
					Intent intent1 = new Intent(ReportsActivity.this,
							CustomerActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							ReportsActivity.this,
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
				Intent intent1 = new Intent(ReportsActivity.this,
						EmployeeActivity.class);
				startActivity(intent1);
				finish();
			} else {
				showAlertDialog(
						ReportsActivity.this,
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
			}
		});
		image6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Parameters.settings_permission) {
					Intent intent1 = new Intent(ReportsActivity.this,
							SettingsActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							ReportsActivity.this,
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
				Intent intent1 = new Intent(ReportsActivity.this,
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
					Intent intent1 = new Intent(ReportsActivity.this,
							ProfileActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							ReportsActivity.this,
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
				Parameters.methodForLogout(ReportsActivity.this);
				finish();
			}
		});
		logout2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Parameters.methodForLogout(ReportsActivity.this);
				finish();
			}
		});
		SlidingDrawer slidingDrawer = (SlidingDrawer) findViewById(R.id.SlidingDrawer);
		DisplayMetrics displaymetrics = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
		int height = displaymetrics.heightPixels;
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
			}
		});
		go = (Button) findViewById(R.id.go);
		savefile = (Button) findViewById(R.id.savefile);
		print_button = (Button) findViewById(R.id.print_button);
		fromdate = (TextView) findViewById(R.id.fromDate);
		todate = (TextView) findViewById(R.id.toDate);
		fromtime = (TextView) findViewById(R.id.fromTime);
		totime = (TextView) findViewById(R.id.toTime);
		
		fromdate.setText(CommonCode.getDateString("MM-dd-yyyy"));
		todate.setText(CommonCode.getDateString("MM-dd-yyyy"));
		fromtime.setText("12:00");
		totime.setText("12:00");
		productType = (Spinner) findViewById(R.id.prodactType);
		item_auto = (AutoCompleteTextView) findViewById(R.id.itemauto);
		reportType = (Spinner) findViewById(R.id.reporttype);
		
		paymentType = (Spinner) findViewById(R.id.paymenttype);
		employee_id = (Spinner) findViewById(R.id.employeeid);
		stor_id1 = (Spinner) findViewById(R.id.customerid);
		orderStatus = (Spinner) findViewById(R.id.orderStatus);
		c = Calendar.getInstance();

		year = c.get(Calendar.YEAR);
		month = c.get(Calendar.MONTH);
		day = c.get(Calendar.DAY_OF_MONTH);
		hourOfDay = c.get(Calendar.HOUR_OF_DAY);
		minute = c.get(Calendar.MINUTE);
		ampm = c.get(Calendar.AM_PM);
		Cursor mCursor2 = dbforloginlogoutReadReport.rawQuery(
				"select " + DatabaseForDemo.DepartmentID + " from "
						+ DatabaseForDemo.DEPARTMENT_TABLE, null);
		Log.v("DEPARTMENT_TABLE", "DEPARTMENT_TABLE");
		System.out.println(mCursor2);
		if (mCursor2 != null) {
			if (mCursor2.moveToFirst()) {
				do {
					String catid = mCursor2.getString(mCursor2
							.getColumnIndex(DatabaseForDemo.DepartmentID));
					vendorspinnerdata.add(catid);
				} while (mCursor2.moveToNext());
			}
		}
		mCursor2 = dbforloginlogoutReadReport.rawQuery(
				"select " + DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID + " from "
						+ DatabaseForDemo.EMPLOYEE_TABLE, null);
		System.out.println(mCursor2);
		Log.v("DEPARTMENT_TABLE", "mCursor2");
		if (mCursor2 != null) {
			if (mCursor2.moveToFirst()) {
				do {
					String eId = mCursor2
							.getString(mCursor2
									.getColumnIndex(DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID));
					employeeidData.add(eId);
				} while (mCursor2.moveToNext());
			}
		}
		mCursor2 = dbforloginlogoutReadReport.rawQuery(
				"select " + DatabaseForDemo.STORE_ID + " from "
						+ DatabaseForDemo.STORE_TABLE, null);
		System.out.println(mCursor2);
		Log.v("mCursor2", "mCursor2");
		if (mCursor2 != null) {
			if (mCursor2.moveToFirst()) {
				do {
					String catid = mCursor2.getString(mCursor2
							.getColumnIndex(DatabaseForDemo.STORE_ID));
					customeridData.add(catid);
				} while (mCursor2.moveToNext());
			}
		}
		mCursor2.close();
		ArrayAdapter<String> adapter12 = new ArrayAdapter<String>(
				ReportsActivity.this, android.R.layout.simple_spinner_item,
				vendorspinnerdata);
		productType.setAdapter(adapter12);
		ArrayAdapter<String> adpEmployee = new ArrayAdapter<String>(
				ReportsActivity.this, android.R.layout.simple_spinner_item,
				customeridData);
		stor_id1.setAdapter(adpEmployee);
		ArrayAdapter<String> adpCustomer = new ArrayAdapter<String>(
				ReportsActivity.this, android.R.layout.simple_spinner_item,
				employeeidData);
		employee_id.setAdapter(adpCustomer);

		ArrayAdapter<String> adapter123 = new ArrayAdapter<String>(
				ReportsActivity.this, android.R.layout.simple_spinner_item,
				paytypes);
		ArrayAdapter<String> reporttypeadapter = new ArrayAdapter<String>(
				ReportsActivity.this, android.R.layout.simple_spinner_item,
				reporttypes);
		reportType.setAdapter(reporttypeadapter);

		reportType.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				if (reportType.getItemAtPosition(arg2).equals(
						"Payment Type Reports")) {
					layout_inflate.removeAllViews();
					paymentType.setSelection(0);
					orderStatus.setSelection(0);
					productType.setSelection(0);
					employee_id.setSelection(0);
					stor_id1.setSelection(0);
					item_auto.setText("");
					web1.setVisibility(View.GONE);
					paymentType.setEnabled(true);
					orderStatus.setEnabled(false);
					productType.setEnabled(false);
					item_auto.setEnabled(false);
					employee_id.setEnabled(false);
					stor_id1.setEnabled(false);
				} else if (reportType.getItemAtPosition(arg2).equals(
						"Department Wise Reports")) {
					layout_inflate.removeAllViews();
					paymentType.setSelection(0);
					orderStatus.setSelection(0);
					productType.setSelection(0);
					employee_id.setSelection(0);
					stor_id1.setSelection(0);
					item_auto.setText("");
					web1.setVisibility(View.GONE);
					paymentType.setEnabled(false);
					orderStatus.setEnabled(false);
					productType.setEnabled(true);
					item_auto.setEnabled(false);
					employee_id.setEnabled(false);
					stor_id1.setEnabled(true);
				} else if (reportType.getItemAtPosition(arg2).equals(
						"Flash Reports")) {
					layout_inflate.removeAllViews();
					paymentType.setSelection(0);
					orderStatus.setSelection(0);
					productType.setSelection(0);
					employee_id.setSelection(0);
					stor_id1.setSelection(0);
					item_auto.setText("");
					web1.setVisibility(View.INVISIBLE);
					File file = new File("/sdcard/printData/webfile.html");
						if(file.exists()){
							Log.v("file", "exists");
					    web1.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/printData/webfile.html");
						}
					paymentType.setEnabled(false);
					orderStatus.setEnabled(false);
					productType.setEnabled(false);
					item_auto.setEnabled(false);
					employee_id.setEnabled(false);
					stor_id1.setEnabled(false);
				} else if (reportType.getItemAtPosition(arg2).equals(
						"Detail Daily Reports")){
					layout_inflate.removeAllViews();
					paymentType.setSelection(0);
					orderStatus.setSelection(0);
					productType.setSelection(0);
					employee_id.setSelection(0);
					stor_id1.setSelection(0);
					item_auto.setText("");
					web1.setVisibility(View.INVISIBLE);
						File file = new File("/sdcard/printData/webfile.html");
						if(file.exists()){
							Log.v("file", "exists");
					    web1.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/printData/webfile.html");
						}
					paymentType.setEnabled(false);
					orderStatus.setEnabled(true);
					productType.setEnabled(false);
					item_auto.setEnabled(false);
					employee_id.setEnabled(false);
					stor_id1.setEnabled(false);
				} else if (reportType.getItemAtPosition(arg2).equals(
						"Employee Shift Reports")) {
					layout_inflate.removeAllViews();
					paymentType.setSelection(0);
					orderStatus.setSelection(0);
					productType.setSelection(0);
					employee_id.setSelection(0);
					stor_id1.setSelection(0);
					item_auto.setText("");
					web1.setVisibility(View.GONE);
					paymentType.setEnabled(false);
					orderStatus.setEnabled(false);
					productType.setEnabled(false);
					item_auto.setEnabled(false);
					employee_id.setEnabled(true);
					stor_id1.setEnabled(false);
				} else {
					layout_inflate.removeAllViews();
					paymentType.setSelection(0);
					orderStatus.setSelection(0);
					productType.setSelection(0);
					employee_id.setSelection(0);
					stor_id1.setSelection(0);
					item_auto.setText("");
					web1.setVisibility(View.GONE);
					paymentType.setEnabled(false);
					orderStatus.setEnabled(true);
					productType.setEnabled(false);
					item_auto.setEnabled(false);
					employee_id.setEnabled(true);
					stor_id1.setEnabled(true);
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
			}
		});
		paymentType.setAdapter(adapter123);
		ArrayAdapter<String> orderadapter = new ArrayAdapter<String>(
				ReportsActivity.this, android.R.layout.simple_spinner_item,
				orderstatusarr);
		orderStatus.setAdapter(orderadapter);
		ArrayAdapter<String> autoAdapter = new ArrayAdapter<String>(
				ReportsActivity.this, android.R.layout.select_dialog_item,
				itemNameArray);
		item_auto.setAdapter(autoAdapter);
		Log.e("aaaaaaa", "ssssss");
		go.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				arr1.clear();
				arr2.clear();
				arr3.clear();
				arr4.clear();
				arr5.clear();
				arr6.clear();
				arr7.clear();
				arr8.clear();
				arr9.clear();
				Total_Avg_Cost = 0.0;
				Total_your_cost = 0.0;
				Total_Qty = 0.0;
				subTotal = 0.0;
				Total_Tax = 0.0;
				Total_Discount = 0.0;
				Total_Tax_amount = 0.0;
				Total_NonTax_amount = 0.0;
				gross = 0.0;
				String qry = "";
				String auto_name = item_auto.getText().toString().trim();
				from = fromdate.getText().toString().trim() + " "
						+ fromtime.getText().toString().trim();
				to = todate.getText().toString().trim() + " "
						+ totime.getText().toString().trim();
				String dp = productType.getSelectedItem().toString();
				String pay = paymentType.getSelectedItem().toString();
				String status = orderStatus.getSelectedItem().toString();
				String selectRtype=reportType.getSelectedItem().toString();
				String emp_id=employee_id.getSelectedItem().toString();
				String stor_id=stor_id1.getSelectedItem().toString();
				if (selectRtype.equals("Employee Shift Reports")) {
					employeeidReports(emp_id);
				} else if(selectRtype.equals("Payment Type Reports")) {
					  Log.v("asd","sdasdadasdasdadasdasds");
					  if(paymentType.getSelectedItem().toString().equals("All")){
						  Double cashValue=paymentTypeReports("Cash",from,to);
						  Double checkValue=paymentTypeReports( "Check",from,to);
						  Double CreditValue=paymentTypeReports("Credit/Debit",from,to);
						  Double accountValue=paymentTypeReports("Account",from,to);
						  showAlertDialog(ReportsActivity.this, "Payment Type Reports",  "\n Cash        \t " +cashValue + "\n Check        \t " 
						+checkValue + "\n Credit/Debit \t  "+ CreditValue
						+ "\n Account        \t  "+accountValue  ,
									false);
						}else{
							Double value=paymentTypeReports(paymentType.getSelectedItem().toString(),from,to);
							  showAlertDialog(ReportsActivity.this, "Payment Type Reports",  "\n "+paymentType.getSelectedItem().toString()+"    \t " + value ,
										false);
						}
				}else if(selectRtype.equals("Invoice Total Reports")) {
					if(emp_id.equals("All"))
						emp_id="";
					if(stor_id.equals("All"))
							stor_id="";
					String qryyy="select * from "+DatabaseForDemo.INVOICE_TOTAL_TABLE;
				if (from.length() > 3 && to.length() > 3) {
				 qryyy = "select * from "+DatabaseForDemo.INVOICE_TOTAL_TABLE+" where "+DatabaseForDemo.CREATED_DATE+" between \""+from+"\" and \""+to+"\" and  "+DatabaseForDemo.INVOICE_EMPLOYEE
						+" like \"%"+emp_id+"%\" and "+DatabaseForDemo.INVOICE_STORE_ID+" like \"%"+stor_id+"%\"  and "+DatabaseForDemo.INVOICE_STATUS+" like \"%"+status+"%\"";
				}else{
					 qryyy = "select * from "+DatabaseForDemo.INVOICE_TOTAL_TABLE+" where "+DatabaseForDemo.INVOICE_EMPLOYEE
							+" like \"%"+emp_id+"%\" and "+DatabaseForDemo.INVOICE_STORE_ID+" like \"%"+stor_id+"%\"  and "+DatabaseForDemo.INVOICE_STATUS+" like \"%"+status+"%\"";
				}
					invoiceTotalData(qryyy);
					typeForsavefile="Invoice Total Reports";
				}else if (reportType.getSelectedItem().toString().equals("Flash Reports")){
					if (from.length() > 3 && to.length() > 3) {
						qry = "select * from "
								+ DatabaseForDemo.INVOICE_ITEMS_TABLE
								+ " where "
								+ DatabaseForDemo.INVOICE_STATUS + "=\""
								+ status + "\" and "
								+ DatabaseForDemo.CREATED_DATE
								+ " between \"" + from + "\" and \"" + to
								+ "\";";
							Log.v("ffff"," Flash Reports"+ qry);
			    	}else {
			    		qry ="select * from "
								+ DatabaseForDemo.INVOICE_ITEMS_TABLE
								+ " where "
								+ DatabaseForDemo.INVOICE_STATUS + "=\""
								+ status + "\";";
						Log.v("ffff"," Flash Reports"+ qry);
			    	}

					  Double cashValue=paymentTypeReports("Cash",from,to);
					  Double checkValue=paymentTypeReports( "Check",from,to);
					  Double CreditValue=paymentTypeReports("Credit/Debit",from,to);
					  Double accountValue=paymentTypeReports("Account",from,to);
					
					flashReportsShow(qry,cashValue,checkValue,CreditValue,accountValue);
				}else if (selectRtype.equals("Department Wise Reports")) {
					if(emp_id.equals("All"))
						emp_id="";
					if(stor_id.equals("All"))
							stor_id="";
					if(dp.equals("All"))
						dp="";
					String qryyy="select * from "+DatabaseForDemo.INVOICE_ITEMS_TABLE;
				if (from.length() > 3 && to.length() > 3) {
				 qryyy = "select * from "+DatabaseForDemo.INVOICE_ITEMS_TABLE+" where "+DatabaseForDemo.CREATED_DATE+" between \""+from+"\" and \""+to+"\" and  "
				+DatabaseForDemo.INVOICE_STORE_ID+" like \"%"+stor_id+"%\"  and "+DatabaseForDemo.INVOICE_DEPARTMETNT+" like \"%"+dp+"%\"";
				// DatabaseForDemo.INVOICE_EMPLOYEE+" like \"%"+emp_id+"%\" and "+
				}else{
					 qryyy = "select * from "+DatabaseForDemo.INVOICE_ITEMS_TABLE+" where "+DatabaseForDemo.INVOICE_STORE_ID+" like \"%"+stor_id+"%\"  and "+DatabaseForDemo.INVOICE_DEPARTMETNT+" like \"%"+dp+"%\"";
				}
				//	invoiceTotalData(qryyy);
				invoiceTotalReports(qryyy,"Department Wise Reports");
				typeForsavefile="Department Wise Reports";
					
				} else if (reportType.getSelectedItem().toString().equals("Detail Daily Reports")){
					dparrlist.clear();
					String flashval;
					if (from.length() > 3 && to.length() > 3) {
						qry = "select * from "
								+ DatabaseForDemo.INVOICE_ITEMS_TABLE
								+ " where "
								+ DatabaseForDemo.INVOICE_STATUS + "=\""
								+ status + "\" and "
								+ DatabaseForDemo.CREATED_DATE
								+ " between \"" + from + "\" and \"" + to
								+ "\";";
							Log.v("ffff"," dd Reports"+ qry);
			    	}else {
			    		qry ="select * from "
								+ DatabaseForDemo.INVOICE_ITEMS_TABLE
								+ " where "
								+ DatabaseForDemo.INVOICE_STATUS + "=\""
								+ status + "\";";
						Log.v("ffff"," dd Reports"+ qry);
			    	}
					 Double cashValue=paymentTypeReports("Cash",from,to);
					  Double checkValue=paymentTypeReports( "Check",from,to);
					  Double CreditValue=paymentTypeReports("Credit/Debit",from,to);
					  Double accountValue=paymentTypeReports("Account",from,to);
					  String dpqry;
					 flashval= detailDailyReportsShow(qry,cashValue,checkValue,CreditValue,accountValue);
					 
					  for(int dpcount=0;dpcount<dparrlist.size();dpcount++){
						  Log.e("dd ka name "+dparrlist.get(dpcount),""+dpcount);
					  if (from.length() > 3 && to.length() > 3) {
						  dpqry = "select * from "+DatabaseForDemo.INVOICE_ITEMS_TABLE+" where "+DatabaseForDemo.CREATED_DATE+" between \""+from+"\" and \""+to+"\" and "
									 + DatabaseForDemo.INVOICE_STATUS + "=\""+ status + "\" and "+DatabaseForDemo.INVOICE_DEPARTMETNT+" = \""+dparrlist.get(dpcount)+"\"";
							}else{
								dpqry = "select * from "+DatabaseForDemo.INVOICE_ITEMS_TABLE+" where "+ DatabaseForDemo.INVOICE_STATUS + "=\""+ status + "\" and "
							+DatabaseForDemo.INVOICE_DEPARTMETNT+" = \""+dparrlist.get(dpcount)+"\"";
							}
					  String sss= dpWiseSetString(dpqry, dparrlist.get(dpcount));
					 
					   flashval += sss;
					  }
					  
					  flashval +="</table></center></body></html>";
					  createHtmlFile(flashval);
					  web1.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/printData/webfile.html");
					Log.v("h "+web1.getHeight(), "dsdssdslist  w "+web1.getWidth());
					LayoutParams params = web1.getLayoutParams();
					int jjjj=1000;
					jjjj+=dparrlist.size()*360;
					params.width = 550;
					params.height = jjjj;
					web1.setLayoutParams(params);
					web1.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/printData/webfile.html");
					Log.v("h "+web1.getHeight(), "dsdssdslist  w "+web1.getWidth());
					  
				 		final AlertDialog alertDialog2 = new AlertDialog.Builder(
								ReportsActivity.this).create();
						LayoutInflater mInflater1 = LayoutInflater
								.from(ReportsActivity.this);
						View layout1 = mInflater1.inflate(
								R.layout.licence_agreement, null);
						Button accept = (Button) layout1
								.findViewById(R.id.accept);
						Button decline = (Button) layout1
								.findViewById(R.id.decline);
						WebView licence = (WebView) layout1
								.findViewById(R.id.licence);

						licence.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/printData/webfile.html");
						accept.setText("Print");
						decline.setText("Cancel");
						accept.setOnClickListener(new OnClickListener() {

							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								 web1.setDrawingCacheEnabled(true);

							        web1.buildDrawingCache();

							        Bitmap map1=web1.getDrawingCache();
							         if(web1.getHeight()>0&&web1.getWidth()>0)
							         map1=drawableToBitmap(getBitmapFromView(web1));
						web1.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/printData/webfile.html");
							         web1.setDrawingCacheEnabled(true);

								        web1.buildDrawingCache();
							         map1=web1.getDrawingCache();
							         if(web1.getHeight()>0&&web1.getWidth()>0){
								         map1=drawableToBitmap(getBitmapFromView(web1));
								         Log.v("srii","sriiiimmmmm");
								         }
								alertDialog2.dismiss();
								 printText("printer1", "",map1);
							}
						});
					
						decline.setOnClickListener(new OnClickListener() {
							@Override
							public void onClick(View arg0) {
								// TODO Auto-generated method stub
								alertDialog2.dismiss();
							}
						});
						alertDialog2.setView(layout1);
						alertDialog2.show();
			}
			}
		});

		activity = ReportsActivity.this;
		fromdate.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				CommonCode.DatePickerFragment datePickerFragment = new CommonCode.DatePickerFragment(null, fromdate, false);
				datePickerFragment.show(getSupportFragmentManager(), "datePicker");
			}
		});
		todate.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				CommonCode.DatePickerFragment datePickerFragment = new CommonCode.DatePickerFragment(null, todate, false);
				datePickerFragment.show(getSupportFragmentManager(), "datePicker");
			}
		});
		fromtime.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				CommonCode.TimePickerFragment datePickerFragment = new CommonCode.TimePickerFragment(ReportsActivity.this,null,  fromtime, activity);
				datePickerFragment.show(getSupportFragmentManager(), "datePicker");
			}
		});
		totime.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				CommonCode.TimePickerFragment datePickerFragment = new CommonCode.TimePickerFragment(ReportsActivity.this,null,  totime, activity);
				datePickerFragment.show(getSupportFragmentManager(), "datePicker");
			}
		});
//		fromdate.setOnFocusChangeListener(new OnFocusChangeListener() {
//			@Override
//			public void onFocusChange(View arg0, boolean arg1) {
//				// TODO Auto-generated method stub
//				if (arg1) {
//					FDate = true;
//					showDialog(DATE_DIALOG_ID);
//				}
//			}
//		});
//		
//		todate.setOnFocusChangeListener(new OnFocusChangeListener() {
//
//			@Override
//			public void onFocusChange(View arg0, boolean arg1) {
//				// TODO Auto-generated method stub
//				if (arg1) {
//					TDate = true;
//					showDialog(DATE_DIALOG_ID);
//				}
//			}
//		});
//		fromtime.setOnFocusChangeListener(new OnFocusChangeListener() {
//			@Override
//			public void onFocusChange(View arg0, boolean arg1) {
//				// TODO Auto-generated method stub
//				if (arg1) {
//					FTime = true;
//					showDialog(Time_PICKER_ID);
//				}
//			}
//		});
//		totime.setOnFocusChangeListener(new OnFocusChangeListener() {
//			@Override
//			public void onFocusChange(View arg0, boolean arg1) {
//				// TODO Auto-generated method stub
//				if (arg1) {
//					TTime = true;
//					showDialog(Time_PICKER_ID);
//				}
//			}
//		});
		print_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				 boolean installed  =   appInstalledOrNot("com.dynamixsoftware.printershare");  
			        if(installed) {
			            Intent LaunchIntent = getPackageManager()
			               .getLaunchIntentForPackage("com.dynamixsoftware.printershare");
			            startActivity(LaunchIntent);
			            System.out.println("App already installed on your phone");        
			        }
			        else {
			        	Intent intent = new Intent(Intent.ACTION_VIEW);
			        	intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=com.dynamixsoftware.printershare"));
			        	startActivity(intent);
			            System.out.println("App is not installed on your phone");
			            Toast.makeText(getApplicationContext(), "Please Install PrinterShare Application", 2000).show();
			        }
			  
			}
		});
	
		savefile.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Log.v("ascvcdff", "mnb000");
				if(typeForsavefile.equals("Department Wise Reports")){
					printPaper("Department Wise Reports");
				}
				if(typeForsavefile.equals("Invoice Total Reports")){
					printPaperForInvoiceTotal("Invoice Total Reports");
				}
			
			}
		});
	}
	void invoiceTotalData(String qry) {
		try{
		layout_inflate.removeAllViews();
		LayoutInflater inflater = getLayoutInflater();
		final View layout = inflater.inflate(R.layout.employee_reports,
				(ViewGroup) go.findViewById(R.id.layout_inflate));
		TextView tv1=(TextView)  layout.findViewById(R.id.textView1);
		tv1.setText("Date/Time");
		TextView tv2=(TextView)  layout.findViewById(R.id.textView2);
		tv2.setText("Store Id");
		TextView tv3=(TextView)  layout.findViewById(R.id.textView3);
		tv3.setText("Invoice Id");
		TextView tv4=(TextView)  layout.findViewById(R.id.textView4);
		tv4.setText("Employee Id");
		TextView tv5=(TextView)  layout.findViewById(R.id.textView5);
		tv5.setText("Pay Type");
		TextView tv6=(TextView)  layout.findViewById(R.id.textView6);
		tv6.setText("Total Price");
		TextView tv7=(TextView)  layout.findViewById(R.id.textView7);
		tv7.setText("Total Tax");
		TextView tv8=(TextView)  layout.findViewById(R.id.textView8);
		tv8.setText("G Totals");
		Log.w("qrty",""+qry);
ListView invoice_reports=(ListView) layout.findViewById(R.id.emp_reports);
		Cursor mCursoritem1 = dbforloginlogoutReadReport.rawQuery(qry,
				null);
		Total_Avg_Cost = 0.0;
		Total_your_cost = 0.0;
		Total_Qty = 0.0;
		subTotal = 0.0;
		Total_Tax = 0.0;
		Total_Discount = 0.0;
		Total_Tax_amount = 0.0;
		Total_NonTax_amount = 0.0;
		gross = 0.0;
		String dpname = "";
		String dp_items = "";
		Log.e("aaaaaaa", "ssssddddddddddddddss");
		if (mCursoritem1 != null) {
			if (mCursoritem1.moveToFirst()) {
				do {
					String avgg = mCursoritem1
							.getString(
									mCursoritem1
											.getColumnIndex(DatabaseForDemo.INVOICE_CUSTOMER))
							.trim();
					arr4.add(avgg);
					String your = mCursoritem1
							.getString(
									mCursoritem1
											.getColumnIndex(DatabaseForDemo.INVOICE_EMPLOYEE))
							.trim();
					arr5.add(your);
					String tax = "0.0";
					String discount = mCursoritem1
							.getString(
									mCursoritem1
											.getColumnIndex(DatabaseForDemo.INVOICE_PAYMENT_TYPE))
							.trim();
					arr6.add(discount);
					String Qty = mCursoritem1
							.getString(
									mCursoritem1
											.getColumnIndex(DatabaseForDemo.INVOICE_TOTAL_AMT))
							.trim();
					arr9.add(Qty);
					String date = mCursoritem1
							.getString(
									mCursoritem1
											.getColumnIndex(DatabaseForDemo.CREATED_DATE))
							.trim();
					arr1.add(date);
					String iddddd = mCursoritem1
							.getString(
									mCursoritem1
											.getColumnIndex(DatabaseForDemo.INVOICE_STORE_ID))
							.trim();
					arr2.add(iddddd);
					String nameee = mCursoritem1
							.getString(
									mCursoritem1
											.getColumnIndex(DatabaseForDemo.INVOICE_ID)).trim();
					arr3.add(nameee);
					dpname = mCursoritem1
							.getString(
									mCursoritem1
											.getColumnIndex(DatabaseForDemo.INVOICE_TOTAL_AVG
													))
							.trim();
					arr7.add(dpname);
					dpname = mCursoritem1
							.getString(
									mCursoritem1
											.getColumnIndex(DatabaseForDemo.INVOICE_PROFIT
													))
							.trim();
					arr8.add(dpname);
				} while (mCursoritem1.moveToNext());
			}
			if(mCursoritem1.getCount()>0){
			InvoiceReportsAdapter inoiceAdapter=new InvoiceReportsAdapter(ReportsActivity.this, mCursoritem1);
			invoice_reports.setAdapter(inoiceAdapter);
			//printPaperForInvoiceTotal("Invoice Total Reports");
			}else{
				Toast.makeText(getApplicationContext(), "No Data", 2000).show();
			}
		
		}
		layout_inflate.addView(layout);
		
		}catch (NumberFormatException e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		catch (Exception e1) {
			// TODO: handle exception
			e1.printStackTrace();
		}
	}
	void printPaper(String reporttype) {
		
		Double avgCost=0.0;
		Double totalTax=0.0;
		Double gTotal=0.0;
		Double gQty=0.0;
		Double dist=0.0;
		
				HSSFWorkbook workbook = new HSSFWorkbook();
				HSSFSheet sheet = workbook.createSheet("Calculate Simple Interest");

				HSSFFont font = workbook.createFont();
				font.setBoldweight(Font.BOLDWEIGHT_BOLD);
				HSSFCellStyle style = workbook.createCellStyle();
				style.setFont(font);
				
				Row header = sheet.createRow(0);
				header.createCell(0).setCellValue("");
				header.createCell(1).setCellValue("");
				header.createCell(2).setCellValue("");
				header.createCell(3).setCellValue("");
				header.createCell(4).setCellValue(""+reporttype);
				header.createCell(5).setCellValue("");
				header.createCell(6).setCellValue("");
				header.createCell(7).setCellValue("");
				header.createCell(8).setCellValue("");
				header.createCell(9).setCellValue("");
				
				Row header1 = sheet.createRow(1);
				header1.createCell(0).setCellValue("");
				header1.createCell(1).setCellValue("");
				header1.createCell(2).setCellValue("");
				header1.createCell(3).setCellValue("");
				header1.createCell(4).setCellValue("AONEPOS");
				header1.createCell(5).setCellValue("");
				header1.createCell(6).setCellValue("");
				header1.createCell(7).setCellValue("");
				header1.createCell(8).setCellValue("");
				header1.createCell(9).setCellValue("");
				
				Row header2 = sheet.createRow(2);
				header2.createCell(0).setCellValue("S.NO ");
				header2.createCell(1).setCellValue("ITEM ID");
				header2.createCell(2).setCellValue("ITEM NAME");
				header2.createCell(3).setCellValue("DISCOUND");
				header2.createCell(4).setCellValue("TAX");
				header2.createCell(5).setCellValue("YOUR COST");
				header2.createCell(6).setCellValue("AVG COST ");
				header2.createCell(7).setCellValue("QUANTITY");
				header2.createCell(8).setCellValue("DATE ");
				header2.createCell(9).setCellValue("DEPARTMENT");
				
				for (int p = 0; p < arr2.size(); p++) {
					Row dataRow = sheet.createRow(p + 3);
					dataRow.createCell(0).setCellValue("" + (p + 1));
					dataRow.createCell(1).setCellValue("" + arr1.get(p));
					dataRow.createCell(2).setCellValue("" + arr2.get(p));
					dataRow.createCell(3).setCellValue("" + arr3.get(p));
					dataRow.createCell(4).setCellValue("" + arr4.get(p));
					dataRow.createCell(5).setCellValue("" + arr5.get(p));
					dataRow.createCell(6).setCellValue("" + arr6.get(p));
					dataRow.createCell(7).setCellValue("" + arr7.get(p));
					dataRow.createCell(8).setCellValue("" + arr8.get(p));
					dataRow.createCell(9).setCellValue("" + arr9.get(p));
					avgCost +=Double.valueOf(arr6.get(p));
					gTotal +=Double.valueOf(arr5.get(p));
					totalTax +=Double.valueOf(arr4.get(p));
					gQty +=Double.valueOf(arr7.get(p));
					dist+= Double.valueOf(arr3.get(p));
				}
					Row dataRow = sheet.createRow(arr2.size() + 4);
					dataRow.createCell(0).setCellValue(""+Parameters.currentTime());
					dataRow.createCell(1).setCellValue("Grand Totals");
					dataRow.createCell(2).setCellValue("");
					dataRow.createCell(3).setCellValue(""+Double.valueOf(df.format(dist)));
					dataRow.createCell(4).setCellValue(""+Double.valueOf(df.format(totalTax)));
					dataRow.createCell(5).setCellValue(""+Double.valueOf(df.format(avgCost)));
					dataRow.createCell(6).setCellValue(""+Double.valueOf(df.format(gTotal)));
					dataRow.createCell(7).setCellValue(""+Double.valueOf(df.format(gQty)));
					dataRow.createCell(8).setCellValue("");
					dataRow.createCell(9).setCellValue("");
				try {
					String sss="/sdcard/printData/"+reporttype+Parameters.currentTime()+".xls";
					File myFile = new File(sss);
					myFile.createNewFile();
					FileOutputStream out = new FileOutputStream(myFile);
					workbook.write(out);
					out.close();
					System.out.println("Excel written successfully..");
					showAlertDialog(ReportsActivity.this, "Reports", " Excel written successfully.. \n File Path is \n" + sss,
							false);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					showAlertDialog(ReportsActivity.this, "Reports", "Getting an Error" ,
							false);
				} catch (IOException e) {
					showAlertDialog(ReportsActivity.this, "Reports", "Getting an Error" ,
							false);
					e.printStackTrace();
				}

	}
	
	String methodForQuary(String auto_name,String dp,String pay,String status){
		String qry= "select * from " + DatabaseForDemo.INVOICE_ITEMS_TABLE;
		if (auto_name.length() > 1) {
			qry = "select* from " + DatabaseForDemo.INVOICE_ITEMS_TABLE
					+ " where " + DatabaseForDemo.INVOICE_ITEM_NAME
					+ "=\"" + auto_name + "\";";
		} else {
			if (status.equals("All")) {
				if (from.length() > 3 && to.length() > 3) {

					if (dp.equals("All") && pay.equals("All")) {
						qry = "select* from "
								+ DatabaseForDemo.INVOICE_ITEMS_TABLE
								+ " where "
								+ DatabaseForDemo.CREATED_DATE
								+ " between \"" + from + "\" and \"" + to
								+ "\";";
						Log.v("ffff", qry);
					} else {

						/*
						 * SELECT invoice_total_table.invoice_id,
						 * invoice_total_table.payment_type, total_amt,
						 * invoice_total_table.created_timestamp FROM
						 * invoice_total_table WHERE
						 * LOWER(invoice_total_table.payment_type) =
						 * 'cash' UNION SELECT
						 * invoice_total_table.invoice_id,
						 * invoice_total_table.payment_type, amount,
						 * invoice_total_table.created_timestamp FROM
						 * invoice_total_table, split_invoice_table
						 * WHERE invoice_total_table.invoice_id =
						 * split_invoice_table.invoice_id AND
						 * invoice_total_table.payment_type= 'multiple'
						 * AND LOWER(split_invoice_table.payment_type)=
						 * 'cash'
						 */

						if (!dp.equals("All") && !pay.equals("All")) {
							qry = "select* from "
									+ DatabaseForDemo.INVOICE_ITEMS_TABLE
									+ " where "
									+ DatabaseForDemo.INVOICE_DEPARTMETNT
									+ "=\""
									+ dp
									+ "\" and "
									+ DatabaseForDemo.INVOICE_PAYMENT_TYPE
									+ "=\"" + pay + "\" and "
									+ DatabaseForDemo.CREATED_DATE
									+ " between \"" + from + "\" and \""
									+ to + "\";";
							Log.v("ffff", qry);
						}

						if (dp.equals("All") && !pay.equals("All")) {
							qry = "select* from "
									+ DatabaseForDemo.INVOICE_ITEMS_TABLE
									+ " where "
									+ DatabaseForDemo.INVOICE_PAYMENT_TYPE
									+ "=\"" + pay + "\" and "
									+ DatabaseForDemo.CREATED_DATE
									+ " between \"" + from + "\" and \""
									+ to + "\";";
							Log.v("ffff", qry);
						}
						if (!dp.equals("All") && pay.equals("All")) {
							qry = "select* from "
									+ DatabaseForDemo.INVOICE_ITEMS_TABLE
									+ " where "
									+ DatabaseForDemo.INVOICE_DEPARTMETNT
									+ "=\"" + dp + "\" and "
									+ DatabaseForDemo.CREATED_DATE
									+ " between \"" + from + "\" and \""
									+ to + "\";";
							Log.v("ffff", qry);
						}
					}

				} else {
					if (dp.equals("All") && pay.equals("All")) {
						qry = "select* from "
								+ DatabaseForDemo.INVOICE_ITEMS_TABLE;
						Log.v("ffff", qry);
					} else {

						if (!dp.equals("All") && !pay.equals("All")) {
							qry = "select* from "
									+ DatabaseForDemo.INVOICE_ITEMS_TABLE
									+ " where "
									+ DatabaseForDemo.INVOICE_DEPARTMETNT
									+ "=\""
									+ dp
									+ "\" and "
									+ DatabaseForDemo.INVOICE_PAYMENT_TYPE
									+ "=\"" + pay + "\";";
							Log.v("ffff", qry);
						}

						if (dp.equals("All") && !pay.equals("All")) {
							qry = "select* from "
									+ DatabaseForDemo.INVOICE_ITEMS_TABLE
									+ " where "
									+ DatabaseForDemo.INVOICE_PAYMENT_TYPE
									+ "=\"" + pay + "\";";
							Log.v("ffff", qry);
						}
						if (!dp.equals("All") && pay.equals("All")) {
							qry = "select* from "
									+ DatabaseForDemo.INVOICE_ITEMS_TABLE
									+ " where "
									+ DatabaseForDemo.INVOICE_DEPARTMETNT
									+ "=\"" + dp + "\";";
							Log.v("ffff", qry);
						}
					}
				}
			} else {

				if (from.length() > 3 && to.length() > 3) {

					if (dp.equals("All") && pay.equals("All")) {
						qry = "select* from "
								+ DatabaseForDemo.INVOICE_ITEMS_TABLE
								+ " where "
								+ DatabaseForDemo.INVOICE_STATUS + "=\""
								+ status + "\" and "
								+ DatabaseForDemo.CREATED_DATE
								+ " between \"" + from + "\" and \"" + to
								+ "\";";
						Log.v("ffff", qry);
					} else {

						if (!dp.equals("All") && !pay.equals("All")) {
							qry = "select* from "
									+ DatabaseForDemo.INVOICE_ITEMS_TABLE
									+ " where "
									+ DatabaseForDemo.INVOICE_STATUS
									+ "=\""
									+ status
									+ "\" and "
									+ DatabaseForDemo.INVOICE_DEPARTMETNT
									+ "=\""
									+ dp
									+ "\" and "
									+ DatabaseForDemo.INVOICE_PAYMENT_TYPE
									+ "=\"" + pay + "\" and "
									+ DatabaseForDemo.CREATED_DATE
									+ " between \"" + from + "\" and \""
									+ to + "\";";
							Log.v("ffff", qry);
						}

						if (dp.equals("All") && !pay.equals("All")) {
							qry = "select* from "
									+ DatabaseForDemo.INVOICE_ITEMS_TABLE
									+ " where "
									+ DatabaseForDemo.INVOICE_STATUS
									+ "=\""
									+ status
									+ "\" and "
									+ DatabaseForDemo.INVOICE_PAYMENT_TYPE
									+ "=\"" + pay + "\" and "
									+ DatabaseForDemo.CREATED_DATE
									+ " between \"" + from + "\" and \""
									+ to + "\";";
							Log.v("ffff", qry);
						}
						if (!dp.equals("All") && pay.equals("All")) {
							qry = "select * from "
									+ DatabaseForDemo.INVOICE_ITEMS_TABLE
									+ " where "
									+ DatabaseForDemo.INVOICE_STATUS
									+ "=\""
									+ status
									+ "\" and "
									+ DatabaseForDemo.INVOICE_DEPARTMETNT
									+ "=\"" + dp + "\" and "
									+ DatabaseForDemo.CREATED_DATE
									+ " between \"" + from + "\" and \""
									+ to + "\";";
							Log.v("ffff", qry);
						}
					}

				} else {
					if (dp.equals("All") && pay.equals("All")) {
						qry = "select* from "
								+ DatabaseForDemo.INVOICE_ITEMS_TABLE
								+ " where "
								+ DatabaseForDemo.INVOICE_STATUS + "=\""
								+ status + "\";";
						Log.v("ffff", qry);
					} else {

						if (!dp.equals("All") && !pay.equals("All")) {
							qry = "select* from "
									+ DatabaseForDemo.INVOICE_ITEMS_TABLE
									+ " where "
									+ DatabaseForDemo.INVOICE_STATUS
									+ "=\""
									+ status
									+ "\" and "
									+ DatabaseForDemo.INVOICE_DEPARTMETNT
									+ "=\""
									+ dp
									+ "\" and "
									+ DatabaseForDemo.INVOICE_PAYMENT_TYPE
									+ "=\"" + pay + "\";";
							Log.v("ffff", qry);
						}

						if (dp.equals("All") && !pay.equals("All")) {
							qry = "select* from "
									+ DatabaseForDemo.INVOICE_ITEMS_TABLE
									+ " where "
									+ DatabaseForDemo.INVOICE_STATUS
									+ "=\""
									+ status
									+ "\" and "
									+ DatabaseForDemo.INVOICE_PAYMENT_TYPE
									+ "=\"" + pay + "\";";
							Log.v("ffff", qry);
						}
						if (!dp.equals("All") && pay.equals("All")) {
							qry = "select* from "
									+ DatabaseForDemo.INVOICE_ITEMS_TABLE
									+ " where "
									+ DatabaseForDemo.INVOICE_STATUS
									+ "=\""
									+ status
									+ "\" and "
									+ DatabaseForDemo.INVOICE_DEPARTMETNT
									+ "=\"" + dp + "\";";
							Log.v("ffff", qry);
						}
					}
				}

			}
		}
		return qry;
	}
	
	 private boolean appInstalledOrNot(String uri) {
	        PackageManager pm = getPackageManager();
	        boolean app_installed = false;
	        try {
	        
	            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
	            app_installed = true;
	        }
	        catch (PackageManager.NameNotFoundException e) {
	            app_installed = false;
	        }
	        return app_installed ;
	    }
	 
	void printPaperForInvoiceTotal(String reporttype) {
		
Double avgCost=0.0;
Double totalTax=0.0;
Double gTotal=0.0;
		HSSFWorkbook workbook = new HSSFWorkbook();
		HSSFSheet sheet = workbook.createSheet("Calculate Simple Interest");

		HSSFFont font = workbook.createFont();
		font.setBoldweight(Font.BOLDWEIGHT_BOLD);
		HSSFCellStyle style = workbook.createCellStyle();
		style.setFont(font);
		
		Row header = sheet.createRow(0);
		header.createCell(0).setCellValue(" ");
		header.createCell(1).setCellValue("");
		header.createCell(2).setCellValue("");
		header.createCell(3).setCellValue("");
		header.createCell(4).setCellValue("");
		header.createCell(5).setCellValue("Invoice Totals Reports");
		header.createCell(6).setCellValue("");
		header.createCell(7).setCellValue("");
		header.createCell(8).setCellValue("");
		header.createCell(9).setCellValue("");
		header.createCell(10).setCellValue("");
		
		Row header1 = sheet.createRow(1);
		header1.createCell(0).setCellValue("");
		header1.createCell(1).setCellValue("");
		header1.createCell(2).setCellValue("");
		header1.createCell(3).setCellValue("");
		header1.createCell(4).setCellValue("");
		header1.createCell(5).setCellValue("AONEPOS");
		header1.createCell(6).setCellValue("");
		header1.createCell(7).setCellValue("");
		header1.createCell(8).setCellValue("");
		header1.createCell(9).setCellValue("");
		header1.createCell(10).setCellValue("");
		
		Row header2 = sheet.createRow(2);
		header2.createCell(0).setCellValue("S.NO ");
		header2.createCell(1).setCellValue("Date/Time");
		header2.createCell(2).setCellValue("Store Id");
		header2.createCell(3).setCellValue("Invoice Id");
		header2.createCell(4).setCellValue("Customer Id");
		header2.createCell(5).setCellValue("Employee Id");
		header2.createCell(6).setCellValue("Pay Type");
		header2.createCell(7).setCellValue("Total Price");
		header2.createCell(8).setCellValue("Total Tax");
		header2.createCell(9).setCellValue("G Totals");
		header2.createCell(10).setCellValue("Gross");
		try{
		for (int p = 0; p < arr2.size(); p++) {
			Row dataRow = sheet.createRow(p + 3);
			dataRow.createCell(0).setCellValue("" + (p + 1));
			dataRow.createCell(1).setCellValue("" + arr1.get(p));
			dataRow.createCell(2).setCellValue("" + arr2.get(p));
			dataRow.createCell(3).setCellValue("" + arr3.get(p));
			dataRow.createCell(4).setCellValue("" + arr4.get(p));
			dataRow.createCell(5).setCellValue("" + arr5.get(p));
			dataRow.createCell(6).setCellValue("" + arr6.get(p));
			dataRow.createCell(7).setCellValue("" + arr7.get(p));
			dataRow.createCell(8).setCellValue("" + arr8.get(p));
			dataRow.createCell(9).setCellValue("" + arr9.get(p));
			dataRow.createCell(10).setCellValue("" + arr7.get(p));
			avgCost +=Double.valueOf(arr7.get(p));
			totalTax +=Double.valueOf(arr8.get(p));
			gTotal +=Double.valueOf(arr9.get(p));
		}
		}catch(NumberFormatException e){
			e.printStackTrace();
		}
			Row dataRow = sheet.createRow(arr2.size() + 4);
			dataRow.createCell(0).setCellValue("" );
			dataRow.createCell(1).setCellValue(""+Parameters.currentTime());
			dataRow.createCell(2).setCellValue("" );
			dataRow.createCell(3).setCellValue("");
			dataRow.createCell(4).setCellValue("");
			dataRow.createCell(5).setCellValue("Grand Totals");
			dataRow.createCell(6).setCellValue("");
			dataRow.createCell(7).setCellValue(""+Double.valueOf(df.format(avgCost)));
			dataRow.createCell(8).setCellValue(""+Double.valueOf(df.format(totalTax)));
			dataRow.createCell(9).setCellValue(""+Double.valueOf(df.format(gTotal)));
			dataRow.createCell(10).setCellValue(""+Double.valueOf(df.format(avgCost)));
		try {
			String sss="/sdcard/printData/"+reporttype+Parameters.currentTime()+".xls";
			File myFile = new File(sss);
			myFile.createNewFile();
			FileOutputStream out = new FileOutputStream(myFile);
			workbook.write(out);
			out.close();
			System.out.println("Excel written successfully..");
			showAlertDialog(ReportsActivity.this, "Reports", " Excel written successfully.. \n File Path is \n" + sss,
					false);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			showAlertDialog(ReportsActivity.this, "Reports", "Getting an Error" ,
					false);
		} catch (IOException e) {
			showAlertDialog(ReportsActivity.this, "Reports", "Getting an Error" ,
					false);
			e.printStackTrace();
		}

		/*try {

			File printerDirectory = new File("/sdcard/printData");
			if (!printerDirectory.exists()) {
				printerDirectory.mkdirs();
			}
			File myFile = new File("/sdcard/printData/"+reporttype+Parameters.currentTime()+".txt");
			myFile.createNewFile();
			FileOutputStream fOut = new FileOutputStream(myFile);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			myOutWriter.append(""+name_value);
			myOutWriter.close();
			fOut.close();

		} catch (Exception e) {
			Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_SHORT)
					.show();
		}*/

	}

	@Override
	protected Dialog onCreateDialog(int id) {

		switch (id) {
		case Time_PICKER_ID:
			return new TimePickerDialog(this, TimePickerListener, hourOfDay,
					minute, false);

		case DATE_DIALOG_ID:
			// set date picker as current date
			return new DatePickerDialog(this, datePickerListener, year, month,
					day);
		}

		return new DatePickerDialog(this, datePickerListener, year, month, day);
	}

	private TimePickerDialog.OnTimeSetListener TimePickerListener = new TimePickerDialog.OnTimeSetListener() {

		// while dialog box is closed, below method is called.
		@Override
		public void onTimeSet(TimePicker view, int hour, int minute) {
try{
			c.set(Calendar.HOUR_OF_DAY, hour);
			c.set(Calendar.MINUTE, minute);

			int hour12format = c.get(Calendar.HOUR);
			hourOfDay = c.get(Calendar.HOUR);
			minute = c.get(Calendar.MINUTE);
			ampm = c.get(Calendar.AM_PM);
			Log.v("" + ampm, "kkkkkkkkk");
			String ampmStr = (ampm == 0) ? "AM" : "PM";
			if (ampmStr.equals("PM")) {
				hour12format = hour12format + 12;
			}
			// Set the Time String in Button
			if (FTime)
				fromtime.setText(pad(hour12format) + ":" + pad(minute));
			if (TTime)
				totime.setText(pad(hour12format) + ":" + pad(minute));
			TTime = false;
			FTime = false;
}catch(Exception e){
	e.printStackTrace();
}
		}

	};
	
void invoiceTotalReports(String quary,String reportsType){
	arr1.clear();
	arr2.clear();
	arr3.clear();
	arr4.clear();
	arr5.clear();
	arr6.clear();
	arr7.clear();
	arr8.clear();
	arr9.clear();
	layout_inflate.removeAllViews();
	LayoutInflater inflater = getLayoutInflater();
	final View layout = inflater.inflate(R.layout.employee_reports,
			(ViewGroup) go.findViewById(R.id.layout_inflate));
	TextView tv1=(TextView)  layout.findViewById(R.id.textView1);
	tv1.setText("Item Id");
	TextView tv2=(TextView)  layout.findViewById(R.id.textView2);
	tv2.setText("Item Name");
	TextView tv3=(TextView)  layout.findViewById(R.id.textView3);
	tv3.setText("Discount");
	TextView tv4=(TextView)  layout.findViewById(R.id.textView4);
	tv4.setText("Tax");
	TextView tv5=(TextView)  layout.findViewById(R.id.textView5);
	tv5.setText("Your Cost");
	TextView tv6=(TextView)  layout.findViewById(R.id.textView6);
	tv6.setText("Avg Cost");
	TextView tv7=(TextView)  layout.findViewById(R.id.textView7);
	tv7.setText("Quantity");
	TextView tv8=(TextView)  layout.findViewById(R.id.textView8);
	tv8.setText("Date");
	
ListView items_reports=(ListView) layout.findViewById(R.id.emp_reports);
	
	itemNameArray.clear();
	try{
	Cursor mCursoritem1 =dbforloginlogoutReadReport.rawQuery(quary,
			null);
	Total_Avg_Cost = 0.0;
	Total_your_cost = 0.0;
	Total_Qty = 0.0;
	subTotal = 0.0;
	Total_Tax = 0.0;
	Total_Discount = 0.0;
	Total_Tax_amount = 0.0;
	Total_NonTax_amount = 0.0;
	gross = 0.0;
	String dpname = "";
	String dp_items = "";
	Log.w("qrty",quary);
	Log.e("aaaaaaa", "xxxxxxxxxx");
	if (mCursoritem1 != null) {
		Log.e("aaaaaaa", "vvvvv");
		if (mCursoritem1.moveToFirst()) {
			Log.e("aaaaaaa", "bbbbbbbb");
			do {
				Log.e("aaaaaaa", "mmmmmmmm");
				String avgg = mCursoritem1
						.getString(
								mCursoritem1
										.getColumnIndex(DatabaseForDemo.INVOICE_AVG_COST))
						.trim();
				String your = mCursoritem1
						.getString(
								mCursoritem1
										.getColumnIndex(DatabaseForDemo.INVOICE_YOUR_COST))
						.trim();
				String tax = "0.0";
				 tax = mCursoritem1
						.getString(
								mCursoritem1
										.getColumnIndex(DatabaseForDemo.INVOICE_TAX))
						.trim();
				String discount = mCursoritem1
						.getString(
								mCursoritem1
										.getColumnIndex(DatabaseForDemo.INVOICE_DISCOUNT))
						.trim();
				String Qty = mCursoritem1
						.getString(
								mCursoritem1
										.getColumnIndex(DatabaseForDemo.INVOICE_QUANTITY))
						.trim();
				if(your.length()>0&&Qty.length()>0){
				Double yourww=Double.valueOf(your)*Double.valueOf(Qty);
				your=yourww.toString();
				}
				if(avgg.length()>0&&Qty.length()>0){
				Double avggww=Double.valueOf(avgg)*Double.valueOf(Qty);
				avgg=avggww.toString();
				}
				String date = mCursoritem1
						.getString(
								mCursoritem1
										.getColumnIndex(DatabaseForDemo.CREATED_DATE))
						.trim();
				String dpart = mCursoritem1
						.getString(
								mCursoritem1
										.getColumnIndex(DatabaseForDemo.INVOICE_DEPARTMETNT))
						.trim();
			arr9.add(""+dpart);
				arr8.add(date);
				String iddddd = mCursoritem1
						.getString(
								mCursoritem1
										.getColumnIndex(DatabaseForDemo.INVOICE_ITEM_ID))
						.trim();
				arr1.add(iddddd);
				String nameee = mCursoritem1
						.getString(
								mCursoritem1
										.getColumnIndex(DatabaseForDemo.INVOICE_ITEM_NAME))
						.trim();
				arr2.add(nameee);
				if (nameee.length() > 0)
					itemNameArray.add(nameee);
				dpname = mCursoritem1
						.getString(
								mCursoritem1
										.getColumnIndex(DatabaseForDemo.INVOICE_DEPARTMETNT))
						.trim();
				if (avgg.length() > 0) {
					Total_Avg_Cost += Double.valueOf(avgg);
				} else {
					avgg = "  ";
				}
				arr6.add(avgg);
				if (your.length() > 0) {
					Total_your_cost += Double.valueOf(your);
				} else {
					your = "  ";
				}
				arr5.add(your);
				if (discount.length() > 0) {
					Total_Discount += Double.valueOf(discount);
				} else {
					discount = "  ";
				}
				arr3.add(discount);
				if (Qty.length() > 0) {
					Total_Qty += Double.valueOf(Qty);
				} else {
					Qty = "  ";
				}
				
				arr7.add(Qty);
				if (tax.length() > 0) {
					Total_Tax += Double.valueOf(tax);
				} else {
					tax = "  ";
				}
				arr4.add(tax);
				if (avgg.length() > 0) {
					if (tax.length() > 0) {
						Total_Tax_amount += Double.valueOf(avgg);
						Total_Tax += Double.valueOf(tax);
					} else {
						Total_NonTax_amount += Double.valueOf(avgg);
					}
				}

				dp_items = dp_items
						+ "\n"
						+ mCursoritem1
								.getString(
										mCursoritem1
												.getColumnIndex(DatabaseForDemo.INVOICE_ITEM_NAME))
								.trim()
						+ " \t "
						+ (Double.valueOf(your) + Double.valueOf(tax))
						+ " \t "
						+ Qty
						+ " \t "
						+ (Double.valueOf(Qty) * (Double.valueOf(your) + Double
								.valueOf(tax)));
				subTotal += Double.valueOf(Qty)
						* (Double.valueOf(your) + Double.valueOf(tax));
				Log.e("aaaaaaa", "ssssssccccccccccc");
			} while (mCursoritem1.moveToNext());
		}
	
		if(mCursoritem1.getCount()>0){
			ReportItemAdapter adapterReporst = new ReportItemAdapter(
					ReportsActivity.this, mCursoritem1);
			items_reports.setAdapter(adapterReporst);
			}else{
				Toast.makeText(getApplicationContext(), "No Data", 2000).show();
			}
	}
	
	String dppp = dpname + "\n " + dp_items
			+ " \n -------------------------------------------- \n"
			+ dpname + " SubTotal :     " + Total_Qty + " \t" + subTotal;
	// mCursoritem1.close();
	gross = Total_Avg_Cost + Total_Tax;
	Total_your_cost = Total_your_cost - Total_Avg_Cost;
	layout_inflate.addView(layout);
	}catch (NumberFormatException e1) {
		// TODO: handle exception
		e1.printStackTrace();
	}
	catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
//	Log.v("cc1", Total_your_cost + "  " + Total_Avg_Cost);
String printstring = "            FLASH REPORT             \n "
			+ " Store: 1001                             \n "
			+ "                                \n " + ""
			+ from
			+ "                             \n "
			+ " "
			+ to
			+ "                               \n "
			+ "            SALES TOTALS             \n "
			+ "======================================\n "
			+ " Net Sales                  \t"
			+ Total_Avg_Cost
			+ "  \n "
			+ " Net Sales - Taxed        \t"
			+ Total_Tax_amount
			+ "  \n "
			+ " Net Sales _ Non Taxed   \t"
			+ Total_NonTax_amount
			+ "  \n "
			+ " Exempt Sales            \t     0.00  \n "
			+ " Taxes                     \t"
			+ Total_Tax
			+ "  \n "
			+ " Net Discount             \t"
			+ Total_Discount
			+ "  \n "
			+ " Net Qty                 \t"
			+ Total_Qty
			+ "  \n \n "
			+ " Gross Sales        \t $"
			+ gross
			+ "  \n-------------------------------------- \n \n \n "
			+ "            MEDIA TOTALS             \n "
			+ "======================================\n"
			+ " Cash  \t$"
			+ gross
			+ " \n Checks  \t$"
			+ 0.00
			+ " \n Credit/Debit  \t$"
			+ 0.00 + " \n On Account  \t$" + 0.00;
//	printPaper(reportsType);
	/*showAlertDialog(ReportsActivity.this, "Reports", " " + printstring,
			false);*/
	
}
void departmentidReports(String emp_value) {
	// TODO Auto-generated method stub
	arr1.clear();
	arr2.clear();
	arr3.clear();
	arr4.clear();
	arr5.clear();
	arr6.clear();
	arr7.clear();
	arr8.clear();
	arr9.clear();
	layout_inflate.removeAllViews();
	LayoutInflater inflater = getLayoutInflater();
	final View layout = inflater.inflate(R.layout.employee_reports,
			(ViewGroup) go.findViewById(R.id.layout_inflate));
	TextView tv1=(TextView)  layout.findViewById(R.id.textView1);
	tv1.setText("");
	TextView tv2=(TextView)  layout.findViewById(R.id.textView2);
	tv2.setText("Employee Name");
	TextView tv3=(TextView)  layout.findViewById(R.id.textView3);
	tv3.setText("   Login Time");
	TextView tv4=(TextView)  layout.findViewById(R.id.textView4);
	tv4.setText("   Logout Time");
	TextView tv5=(TextView)  layout.findViewById(R.id.textView5);
	tv5.setText("Different Minutes");
	TextView tv6=(TextView)  layout.findViewById(R.id.textView6);
	tv6.setText("Different Hours");
	TextView tv7=(TextView)  layout.findViewById(R.id.textView7);
	tv7.setText("Wages");
	TextView tv8=(TextView)  layout.findViewById(R.id.textView8);
	tv8.setText("");
	
ListView emp_reports=(ListView) layout.findViewById(R.id.emp_reports);
String selectQuery = "SELECT  * FROM "
		+ DatabaseForDemo.LOGIN_LOGOUT_TABLE; 
		if (!emp_value.equals("All")) {
			selectQuery = "select * from "  + DatabaseForDemo.LOGIN_LOGOUT_TABLE + " where " + DatabaseForDemo.LOGIN_EMPLOYEE_ID + "= \""+ emp_value +"\";";
		}
Cursor mCursor1 = dbforloginlogoutReadReport.rawQuery(selectQuery, null);
if (mCursor1 != null) {
	if (mCursor1.moveToFirst()) {
		do {
			String avgg = mCursor1 .getString( mCursor1 .getColumnIndex(DatabaseForDemo.LOGIN_EMPLOYEE_ID)) .trim();
			arr1.add(avgg);
			String your = mCursor1 .getString( mCursor1 .getColumnIndex(DatabaseForDemo.LOGIN_EMPLOYEE_NAME)) .trim();
			arr2.add(your);
			String discount = mCursor1 .getString( mCursor1 .getColumnIndex(DatabaseForDemo.LOGIN_TIME)) .trim();
			arr3.add(discount);
			String Qty = mCursor1 .getString( mCursor1 .getColumnIndex(DatabaseForDemo.LOGOUT_TIME)) .trim();
			arr4.add(Qty);
			String date = mCursor1 .getString( mCursor1 .getColumnIndex(DatabaseForDemo.DIFF_MINUTES)) .trim();
			arr5.add(date);
			String iddddd = mCursor1 .getString( mCursor1 .getColumnIndex(DatabaseForDemo.DIFF_HOURS)) .trim();
			arr6.add(iddddd);
			String nameee = mCursor1 .getString( mCursor1 .getColumnIndex(DatabaseForDemo.WAGES)) .trim();
			arr7.add(nameee);
			if (nameee.length() > 0)
				itemNameArray.add(nameee);
			arr8.add("");
		} while (mCursor1.moveToNext());
//	}

	/*if (mCursor1.getCount() > 0) {
		if (mCursor1.moveToFirst()) {
			do {
				String	login_time = mCursor1.getString(mCursor1.getColumnIndex(DatabaseForDemo.LOGIN_TIME));
				String	logout_time = mCursor1.getString(mCursor1.getColumnIndex(DatabaseForDemo.LOGOUT_TIME));
				Log.e(" "+login_time," "+logout_time);
			} while (mCursor1.moveToNext());
		}*/
	} else {
		Toast.makeText(getApplicationContext(),
				"No Data", 1000).show();
	}
}
EmployeeReportAdapter emp_reports_adapter = new EmployeeReportAdapter(
		ReportsActivity.this, mCursor1);
emp_reports.setAdapter(emp_reports_adapter);
	layout_inflate.addView(layout);
}
	
	
	void employeeidReports(String emp_value) {
		// TODO Auto-generated method stub
		arr1.clear();
		arr2.clear();
		arr3.clear();
		arr4.clear();
		arr5.clear();
		arr6.clear();
		arr7.clear();
		arr8.clear();
		arr9.clear();
		layout_inflate.removeAllViews();
		LayoutInflater inflater = getLayoutInflater();
		final View layout = inflater.inflate(R.layout.employee_reports,
				(ViewGroup) go.findViewById(R.id.layout_inflate));
		TextView tv1=(TextView)  layout.findViewById(R.id.textView1);
		tv1.setText("Employee Id");
		TextView tv2=(TextView)  layout.findViewById(R.id.textView2);
		tv2.setText("Employee Name");
		TextView tv3=(TextView)  layout.findViewById(R.id.textView3);
		tv3.setText("   Login Time");
		TextView tv4=(TextView)  layout.findViewById(R.id.textView4);
		tv4.setText("   Logout Time");
		TextView tv5=(TextView)  layout.findViewById(R.id.textView5);
		tv5.setText("Different Minutes");
		TextView tv6=(TextView)  layout.findViewById(R.id.textView6);
		tv6.setText("Different Hours");
		TextView tv7=(TextView)  layout.findViewById(R.id.textView7);
		tv7.setText("Wages");
		TextView tv8=(TextView)  layout.findViewById(R.id.textView8);
		tv8.setText("");
		
ListView emp_reports=(ListView) layout.findViewById(R.id.emp_reports);
	String selectQuery = "SELECT  * FROM "
			+ DatabaseForDemo.LOGIN_LOGOUT_TABLE; 
			if (!emp_value.equals("All")) {
				selectQuery = "select * from "  + DatabaseForDemo.LOGIN_LOGOUT_TABLE + " where " + DatabaseForDemo.LOGIN_EMPLOYEE_ID + "= \""+ emp_value +"\";";
			}
			System.out.println("============++++++++++++++++++++");
			Log.v("ffff", selectQuery);
	Cursor mCursor1 = dbforloginlogoutReadReport.rawQuery(selectQuery, null);
	if (mCursor1 != null) {
		if (mCursor1.moveToFirst()) {
			do {
				String avgg = mCursor1 .getString( mCursor1 .getColumnIndex(DatabaseForDemo.LOGIN_EMPLOYEE_ID)) .trim();
				arr1.add(avgg);
				String your = mCursor1 .getString( mCursor1 .getColumnIndex(DatabaseForDemo.LOGIN_EMPLOYEE_NAME)) .trim();
				arr2.add(your);
				String discount = mCursor1 .getString( mCursor1 .getColumnIndex(DatabaseForDemo.LOGIN_TIME)) .trim();
				arr3.add(discount);
				String Qty = mCursor1 .getString( mCursor1 .getColumnIndex(DatabaseForDemo.LOGOUT_TIME)) .trim();
				arr4.add(Qty);
				String date = mCursor1 .getString( mCursor1 .getColumnIndex(DatabaseForDemo.DIFF_MINUTES)) .trim();
				arr5.add(date);
				String iddddd = mCursor1 .getString( mCursor1 .getColumnIndex(DatabaseForDemo.DIFF_HOURS)) .trim();
				arr6.add(iddddd);
				String nameee = mCursor1 .getString( mCursor1 .getColumnIndex(DatabaseForDemo.WAGES)) .trim();
				arr7.add("");
				arr8.add("");
			} while (mCursor1.moveToNext());
	//	}
	
		/*if (mCursor1.getCount() > 0) {
			if (mCursor1.moveToFirst()) {
				do {
					String	login_time = mCursor1.getString(mCursor1.getColumnIndex(DatabaseForDemo.LOGIN_TIME));
					String	logout_time = mCursor1.getString(mCursor1.getColumnIndex(DatabaseForDemo.LOGOUT_TIME));
					Log.e(" "+login_time," "+logout_time);
				} while (mCursor1.moveToNext());
			}*/
		} else {
			Toast.makeText(getApplicationContext(),
					"No Data", 1000).show();
		}
	}
	EmployeeReportAdapter emp_reports_adapter = new EmployeeReportAdapter(
			ReportsActivity.this, mCursor1);
	emp_reports.setAdapter(emp_reports_adapter);
		layout_inflate.addView(layout);
	}
	
	Double paymentTypeReports(String paytyp ,String dateF,String DateT ) {
		// SELECT invoice_total_table.invoice_id,  invoice_total_table.payment_type, total_amt,  invoice_total_table.created_timestamp FROM invoice_total_table WHERE  invoice_total_table.payment_type = 'Cash'
			//	AND invoice_total_table.created_timestamp BETWEEN '2014-01-07 11:35:21' AND '2014-01-07 11:35:21' UNION SELECT  invoice_total_table.invoice_id, invoice_total_table.payment_type, amount, 
			//	invoice_total_table.created_timestamp FROM  invoice_total_table, split_invoice_table  WHERE invoice_total_table.invoice_id =  split_invoice_table.invoice_id AND  invoice_total_table.payment_type= 'multiple' 
			//	AND split_invoice_table.payment_type=  'Cash'
		String quary;
		if(dateF.length()>3&&DateT.length()>3){
		 quary="SELECT invoice_total_table.invoice_id,  invoice_total_table.payment_type, total_amt,  invoice_total_table.created_timestamp FROM invoice_total_table WHERE  invoice_total_table.payment_type = \""
				+paytyp
				+"\" AND invoice_total_table.created_timestamp BETWEEN \""+dateF+"\" AND \""+DateT+"\"  UNION SELECT  invoice_total_table.invoice_id, invoice_total_table.payment_type, amount,  invoice_total_table.created_timestamp FROM  invoice_total_table, split_invoice_table "+
				" WHERE invoice_total_table.invoice_id =  split_invoice_table.invoice_id AND  invoice_total_table.payment_type= \"multiple\"  AND split_invoice_table.payment_type=  \""+paytyp+"\"";
		}else{
			 quary="SELECT invoice_total_table.invoice_id,  invoice_total_table.payment_type, total_amt,  invoice_total_table.created_timestamp FROM invoice_total_table WHERE  invoice_total_table.payment_type = \""
						+paytyp
						+"\"  UNION SELECT  invoice_total_table.invoice_id, invoice_total_table.payment_type, amount,  invoice_total_table.created_timestamp FROM  invoice_total_table, split_invoice_table "+
						" WHERE invoice_total_table.invoice_id =  split_invoice_table.invoice_id AND  invoice_total_table.payment_type= \"multiple\"  AND split_invoice_table.payment_type=  \""+paytyp+"\"";
		}
		Cursor mCursoritem1 =dbforloginlogoutReadReport.rawQuery(
				quary, null);
	Double	Total_Cost = 0.0;
	try{
		if (mCursoritem1 != null) {
			if (mCursoritem1.moveToFirst()) {
				do {
			
				String	dpname1 = mCursoritem1
							.getString(
									mCursoritem1
											.getColumnIndex(DatabaseForDemo.INVOICE_TOTAL_AMT))
							.trim();
				Log.v("aa", ""+dpname1);
				Total_Cost += Double.valueOf(dpname1);
				} while (mCursoritem1.moveToNext());
			}
		}
	}catch(NumberFormatException n){
		n.printStackTrace();
	}catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
		mCursoritem1.close();
		return Total_Cost;
		
	}
	private DatePickerDialog.OnDateSetListener datePickerListener = new DatePickerDialog.OnDateSetListener() {

		// when dialog box is closed, below method will be called.
		@Override
		public void onDateSet(DatePicker view, int selectedYear,
				int selectedMonth, int selectedDay) {
			try{
			year = selectedYear;
			month = selectedMonth + 1;
			day = selectedDay;
			if (FDate)
				fromdate.setText(new StringBuilder().append(pad(month))
						.append("-").append(pad(day)).append("-")
						.append(pad(year)));
			if (TDate)
				todate.setText(new StringBuilder().append(month).append("-")
						.append(pad(day)).append("-").append(pad(year)));
			FDate = false;
			TDate = false;
			}catch(NumberFormatException n){
				n.printStackTrace();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}

	};

	private static String pad(int c) {
		return c >= 10 ? "" + c : "0" + c;
	}
	private String dpWiseSetString(String qry,String dpname){
		Log.i(qry,dpname);
		String printstring ="";
		try{
		Cursor mCursoritem1 = dbforloginlogoutReadReport.rawQuery(qry,
				null);
		Total_Avg_Cost = 0.0;
		Total_your_cost = 0.0;
		Total_Qty = 0.0;
		subTotal = 0.0;
		Total_Tax = 0.0;
		Total_Discount = 0.0;
		Total_Tax_amount = 0.0;
		Total_NonTax_amount = 0.0;
		gross = 0.0;
		String tax = "0.0";
		int foritem=0;
		ArrayList<String> invoiceArr=new ArrayList<String>();
		if (mCursoritem1 != null) {
			if (mCursoritem1.moveToFirst()) {
				do {
					foritem++;
				String	invoiceid = mCursoritem1
								.getString(
										mCursoritem1
												.getColumnIndex(DatabaseForDemo.INVOICE_ID))
								.trim();
				String	invoice_dpid = mCursoritem1
						.getString(
								mCursoritem1
										.getColumnIndex(DatabaseForDemo.INVOICE_DEPARTMETNT))
						.trim();
				Log.v("invoice_dpid",""+invoice_dpid);
				String	name = mCursoritem1
						.getString(
								mCursoritem1
										.getColumnIndex(DatabaseForDemo.INVOICE_ITEM_NAME))
						.trim();
				Log.v("name",""+name);
				
					if(!invoiceArr.contains(invoiceid)){
						invoiceArr.add(invoiceid);
					}
					String your = mCursoritem1
							.getString(
									mCursoritem1
											.getColumnIndex(DatabaseForDemo.INVOICE_YOUR_COST))
							.trim();
					String qty = mCursoritem1
							.getString(
									mCursoritem1
											.getColumnIndex(DatabaseForDemo.INVOICE_QUANTITY))
							.trim();
					Double yourww=0.00;
					if(your.length()>0){
					 yourww=Double.valueOf(your);
					}
					if(qty.length()>0){
					 yourww=Double.valueOf(your)*Double.valueOf(qty);
					}
					 tax = mCursoritem1
							.getString(
									mCursoritem1
											.getColumnIndex(DatabaseForDemo.INVOICE_TAX))
							.trim();
					 
						if (tax.length() > 0) {
							if(Double.valueOf(tax)>0.00){
							Total_Tax_amount += yourww;
							Total_Tax += Double.valueOf(tax);
							}else{
								Total_NonTax_amount += yourww;
							}
						} else {
							Total_NonTax_amount += yourww;
						}
				} while (mCursoritem1.moveToNext());
			}
		}
		 mCursoritem1.close();
		subTotal=Total_Tax_amount+Total_NonTax_amount;
		gross = subTotal + Total_Tax;
		
		 printstring ="<tr><td align='center' colspan='2'><h2>"+dpname+"</h2></td></tr>"
		+"<tr><td align='left'>Net Sales</td><td align='right'>$"+Double.valueOf(df.format(subTotal))+"</td></tr>"
		+"<tr><td align='left'>Net Sales - Taxed</td><td align='right'>$"+Double.valueOf(df.format(Total_Tax_amount))+"</td></tr>"
		+"<tr><td align='left'>Net Sales - Non Taxed</td><td align='right'>$"+Double.valueOf(df.format(Total_NonTax_amount))+"</td></tr>"
		+"<tr><td align='left'>Exempt Sales</td><td align='right'>$0.00</td></tr>"
		+"<tr><td align='left'>Taxes </td><td align='right'>$"+Double.valueOf(df.format(Total_Tax))+"</td></tr>"
		+"<tr><td align='left'>Gross Sales</td><td align='right'>$"+Double.valueOf(df.format(gross))+"</td></tr>"
		+"<tr><td align='left'>Item Count</td><td align='right'>"+foritem+"</td></tr>"
		+"<tr><td colspan='2'>&nbsp;</td> <!--  for blank row --></tr>";
		Log.i(qry,printstring);
		
		
		}catch(NumberFormatException n){
			n.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return printstring;
	}
	private String detailDailyReportsShow(String quary,Double cashValue,Double checkValue,Double creditValue,Double accountValue) {
		String  printstring="";
		try{
		// TODO Auto-generated method stub
		itemNameArray.clear();
		Cursor mCursoritem1 =dbforloginlogoutReadReport.rawQuery(quary,
				null);
		Total_Avg_Cost = 0.0;
		Total_your_cost = 0.0;
		Total_Qty = 0.0;
		subTotal = 0.0;
		Total_Tax = 0.0;
		Total_Discount = 0.0;
		Total_Tax_amount = 0.0;
		Total_NonTax_amount = 0.0;
		gross = 0.0;
		String tax = "0.0";
		ArrayList<String> invoiceArr=new ArrayList<String>();
		if (mCursoritem1 != null) {
			if (mCursoritem1.moveToFirst()) {
				do {

				String	invoiceid = mCursoritem1
								.getString(
										mCursoritem1
												.getColumnIndex(DatabaseForDemo.INVOICE_ID))
								.trim();
				String	invoice_dpid = mCursoritem1
						.getString(
								mCursoritem1
										.getColumnIndex(DatabaseForDemo.INVOICE_DEPARTMETNT))
						.trim();
				if(!dparrlist.contains(invoice_dpid)){
					dparrlist.add(invoice_dpid);
					Log.e("invoice_dpid",  "" +invoice_dpid);
				}
				
					if(!invoiceArr.contains(invoiceid)){
						invoiceArr.add(invoiceid);
					}
					Log.v("idsize",mCursoritem1.getCount()+" "+invoiceArr.size());
					String your = mCursoritem1
							.getString(
									mCursoritem1
											.getColumnIndex(DatabaseForDemo.INVOICE_YOUR_COST))
							.trim();                                               
					String qty = mCursoritem1
							.getString(
									mCursoritem1
											.getColumnIndex(DatabaseForDemo.INVOICE_QUANTITY))
							.trim();
					Double yourww=0.00;
					if(your.length()>0){
					 yourww=Double.valueOf(your);
					}
					if(qty.length()>0){
					 yourww=Double.valueOf(your)*Double.valueOf(qty);
					}
					 tax = mCursoritem1
							.getString(
									mCursoritem1
											.getColumnIndex(DatabaseForDemo.INVOICE_TAX))
							.trim();
					 
						if (tax.length() > 0) {
							if(Double.valueOf(tax)>0.00){
							Total_Tax_amount += yourww;
							Total_Tax += Double.valueOf(tax);
							}else{
								Total_NonTax_amount += yourww;
							}
						} else {
							Total_NonTax_amount += yourww;
						}

				} while (mCursoritem1.moveToNext());
			}
		}
		 mCursoritem1.close();
		subTotal=Total_Tax_amount+Total_NonTax_amount;
		gross = subTotal + Total_Tax;
	//	Total_your_cost = Total_your_cost - Total_Avg_Cost;
		Log.v("cc1", Total_your_cost + "  " + Total_Avg_Cost);
		
		String headlines=Parameters.printTextgetFromDatabase(ReportsActivity.this,"printer1");
		printstring="<html> <head><style type='text/css'>body { background-color:none;font-family : Verdana;color:#000000;font-size:20px;} .tablehead{font-family : Verdana;color:#000000;font-size:32px;} table td{font-family : Verdana;color:#000000;font-size:28px; padding:0px 10px;}</style></head><body on style='background-color:none;'><center><table id='page' cellspasing='10' width='100%' align='center' border='0'><tr> <th align='center' colspan='2'>"
		        +"<span class='tablehead'>"+headlines+"</span></th></tr><tr><th align='center' colspan='2'><span class='tablehead'>Detailed Daily Report</span></th></tr>"
				+"<tr><th align='center' colspan='2'>"+from+" </th></tr>"
				+"<tr><th align='center' colspan='2'>"+to+" </th></tr>"
				+"<tr><td colspan='2'>&nbsp;</td> <!--  for blank row --></tr>"
				+"<tr><td align='center' colspan='2'><h2>Sales Total</h2></td></tr>"
				+"<tr><td align='left'>Net Sales</td><td align='right'>$"+Double.valueOf(df.format(subTotal))+"</td></tr>"
				+"<tr><td align='left'>Net Sales - Taxed</td><td align='right'>$"+Double.valueOf(df.format(Total_Tax_amount))+"</td></tr>"
				+"<tr><td align='left'>Net Sales - Non Taxed</td><td align='right'>$"+Double.valueOf(df.format(Total_NonTax_amount))+"</td></tr>"
				+"<tr><td align='left'>Exempt Sales</td><td align='right'>$0.00</td></tr>"
				+"<tr><td align='left'>Taxes </td><td align='right'>$"+Double.valueOf(df.format(Total_Tax))+"</td></tr>"
				+"<tr><td align='left'>Gross Sales</td><td align='right'>$"+Double.valueOf(df.format(gross))+"</td></tr>"
				+"<tr><td colspan='2'>&nbsp;</td> <!--  for blank row --></tr>"
				+"<tr><td align='center' colspan='2'><h2>Pament Types</h2></td></tr>"
				+"<tr><td align='left'>Cash</td><td align='right'>$"+Double.valueOf(df.format(cashValue))+"</td></tr>"
				+"<tr><td align='left'>Checks</td><td align='right'>$"+Double.valueOf(df.format(checkValue))+"</td></tr>"
				+"<tr><td align='left'>Credit/Debit</td><td align='right'>$"+Double.valueOf(df.format(creditValue))+"</td></tr>"
				+"<tr><td align='left'>On Account</td><td align='right'>$"+Double.valueOf(df.format(accountValue))+"</td></tr>"
				+"<tr><td colspan='2'>&nbsp;</td> <!--  for blank row --></tr>";
		
	
	
		}catch(NumberFormatException n){
			n.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		return printstring;
	}
	@SuppressWarnings("deprecation")
	private void flashReportsShow(String quary,Double cashValue,Double checkValue,Double creditValue,Double accountValue) {
		 try{
		itemNameArray.clear();
		Cursor mCursoritem1 = dbforloginlogoutReadReport.rawQuery(quary,
				null);
		
		Total_Avg_Cost = 0.0;
		Total_your_cost = 0.0;
		Total_Qty = 0.0;
		subTotal = 0.0;
		Total_Tax = 0.0;
		Total_Discount = 0.0;
		Total_Tax_amount = 0.0;
		Total_NonTax_amount = 0.0;
		gross = 0.0;
		String tax = "0.0";
		ArrayList<String> invoiceArr=new ArrayList<String>();
		if (mCursoritem1 != null) {
			if (mCursoritem1.moveToFirst()) {
				do {

				String	invoiceid = mCursoritem1
								.getString(
										mCursoritem1
												.getColumnIndex(DatabaseForDemo.INVOICE_ID))
								.trim();
					if(!invoiceArr.contains(invoiceid)){
						invoiceArr.add(invoiceid);
					}
					Log.v("idsize",mCursoritem1.getCount()+" "+invoiceArr.size());
					String your = mCursoritem1
							.getString(
									mCursoritem1
											.getColumnIndex(DatabaseForDemo.INVOICE_YOUR_COST))
							.trim();
					String qty = mCursoritem1
							.getString(
									mCursoritem1
											.getColumnIndex(DatabaseForDemo.INVOICE_QUANTITY))
							.trim();
					Double yourww=0.00;
					if(your.length()>0){
					 yourww=Double.valueOf(your);
					}
					if(qty.length()>0){
					 yourww=Double.valueOf(your)*Double.valueOf(qty);
					}
					 tax = mCursoritem1
							.getString(
									mCursoritem1
											.getColumnIndex(DatabaseForDemo.INVOICE_TAX))
							.trim();
					 
						if (tax.length() > 0) {
							if(Double.valueOf(tax)>0.00){
							Total_Tax_amount += yourww;
							Total_Tax += Double.valueOf(tax);
							}else{
								Total_NonTax_amount += yourww;
							}
						} else {
							Total_NonTax_amount += yourww;
						}

				} while (mCursoritem1.moveToNext());
			}
		}
		 mCursoritem1.close();
		subTotal=Total_Tax_amount+Total_NonTax_amount;
		gross = subTotal + Total_Tax;
	//	Total_your_cost = Total_your_cost - Total_Avg_Cost;
		Log.v("cc1", Total_your_cost + "  " + Total_Avg_Cost);
		String printstring = "    FLASH REPORT   \n \n "
				+ "    Store: 1001    \n "
				+ "                   \n " + " "
				+ from
				+ "      \n "
				+ " "
				+ to
				+ "            \n   \n "
				+ " SALES TOTALS   \n "
				+ "===================\n"
				+ " Net Sales \t    $"
				+ Double.valueOf(df.format(subTotal))
				+ "  \n "
				+ " Net Sales - Taxed   $"
				+ Double.valueOf(df.format(Total_Tax_amount))
				+ "  \n "
				+ " Net Sales - Non Taxed $"
				+ Double.valueOf(df.format(Total_NonTax_amount))
				+ "\n"
				+ " Exempt Sales   $0.00\n"
				+ " Taxes        $"
				+ Double.valueOf(df.format(Total_Tax))
				+ "  \n "
				+ " Gross Sales    $"
				+ Double.valueOf(df.format(gross))
				+ "\n -----------------------\n \n "
				+ "         MEDIA TOTALS         \n "
				+ "===================\n"
				+ " Cash                      $"
				+ Double.valueOf(df.format(cashValue))
				+ "\n Checks                 $"
				+ Double.valueOf(df.format(checkValue))
				+ "\n Credit/Debit           $"
				+ Double.valueOf(df.format(creditValue)) 
				+ "\n On Account            $" + Double.valueOf(df.format(accountValue))
				+ "\n ----------------------\n \n "
				+ " PERFORMANCE STATISTICS  \n "
				+ "===================\n"
				+ " # Transactions      $"
				+ invoiceArr.size()
				+ " \n Avg Transaction $"
				+ Double.valueOf(df.format(gross/invoiceArr.size()))
			+ "\n --------------------\n ";
		String headlines=Parameters.printTextgetFromDatabase(ReportsActivity.this,"printer1");
		printstring="<html> <head><style type='text/css'>body { background-color:none;font-family : Verdana;color:#000000;font-size:20px;}"
			+" .tablehead{font-family : Verdana;color:#000000;font-size:32px;} table td{font-family : Verdana;color:#000000;font-size:28px; padding:0px 10px;}</style></head><body on style='background-color:none;'><center><table id='page' cellspasing='10' width='100%' align='center' border='0'><tr> <th align='center' colspan='2'><span class='tablehead'>"+headlines+"</span></th></tr><tr><th align='center' colspan='2'><span class='tablehead'>Flash Report</span></th></tr>"
				+"<tr><th align='center' colspan='2'>"+from+" </th></tr>"
				+"<tr><th align='center' colspan='2'>"+to+" </th></tr>"
				+"<tr><td colspan='2'>&nbsp;</td> <!--  for blank row --></tr>"
				+"<tr><td align='center' colspan='2'><h2>Sales Total</h2></td></tr>"
				+"<tr><td align='left'>Net Sales</td><td align='right'>$"+Double.valueOf(df.format(subTotal))+"</td></tr>"
				+"<tr><td align='left'>Net Sales - Taxed</td><td align='right'>$"+Double.valueOf(df.format(Total_Tax_amount))+"</td></tr>"
				+"<tr><td align='left'>Net Sales - Non Taxed</td><td align='right'>$"+Double.valueOf(df.format(Total_NonTax_amount))+"</td></tr>"
				+"<tr><td align='left'>Exempt Sales</td><td align='right'>$0.00</td></tr>"
				+"<tr><td align='left'>Taxes </td><td align='right'>$"+Double.valueOf(df.format(Total_Tax))+"</td></tr>"
				+"<tr><td align='left'>Gross Sales</td><td align='right'>$"+Double.valueOf(df.format(gross))+"</td></tr>"
				+"<tr><td colspan='2'>&nbsp;</td> <!--  for blank row --></tr>"
				+"<tr><td align='center' colspan='2'><h2>Pament Types</h2></td></tr>"
				+"<tr><td align='left'>Cash</td><td align='right'>$"+Double.valueOf(df.format(cashValue))+"</td></tr>"
				+"<tr><td align='left'>Checks</td><td align='right'>$"+Double.valueOf(df.format(checkValue))+"</td></tr>"
				+"<tr><td align='left'>Credit/Debit</td><td align='right'>$"+Double.valueOf(df.format(creditValue))+"</td></tr>"
				+"<tr><td align='left'>On Account</td><td align='right'>$"+Double.valueOf(df.format(accountValue))+"</td></tr>"
				+"<tr><td colspan='2'>&nbsp;</td> <!--  for blank row --></tr>"
				+"<tr><td align='center' colspan='2'><h2>Performance Statistics</h2></td></tr>"
				+"<tr><td align='left'># Transactions</td><td align='right'>"+invoiceArr.size()+"</td></tr>"
				+"<tr>	<td align='left'>Avg Transaction</td><td align='right'>$"+Double.valueOf(df.format(gross/invoiceArr.size()))+"</td></tr>"
				+"<tr><td colspan='2'>&nbsp;</td> <!--  for blank row --></tr>"
		 +"</table></center></body></html>";
		  Log.e("dd reports",""+printstring);
		  createHtmlFile(printstring);
		Log.v("h "+web1.getHeight(), "dsdssdslist  w "+web1.getWidth());
		LayoutParams params = web1.getLayoutParams();
		params.width = 550;
		params.height = 1000;
		web1.setLayoutParams(params);
		
		web1.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/printData/webfile.html");
		Log.v("h "+web1.getHeight(), "dsdssdslist  w "+web1.getWidth());
	       /*  File filename = null;
	 		try {
	 			String path1 = android.os.Environment
	 					.getExternalStorageDirectory().toString();
	 			File file1 = new File("/sdcard/printData/FlashReport");
	 			if (!file1.exists())
	 				file1.mkdirs();
	 			String path2=file1.getAbsolutePath() + "/" + "flashreport"+Parameters.currentTime()
	 					+ ".jpg";
	 			
	 			filename = new File(path2);
	 			Log.v("patadfasfh", "" + path2);
	 			FileOutputStream out = new FileOutputStream(filename);
	 			map.compress(Bitmap.CompressFormat.JPEG, 90, out);
	 			out.flush();
	 			out.close();
	 			Log.v("patfdfsdfsdfh", "1");
	 			ContentValues image = new ContentValues();
	 			image.put(MediaColumns.TITLE, "harinath");
	 			image.put(MediaColumns.DISPLAY_NAME, "hari");
	 			image.put(ImageColumns.DESCRIPTION, "App Image");
	 			image.put(MediaColumns.DATE_ADDED,
	 					System.currentTimeMillis());
	 			Log.v("patfdfsdfsdfh", "12");
	 			image.put(MediaColumns.MIME_TYPE, "image/jpg");
	 			image.put(ImageColumns.ORIENTATION, 0);
	 			File parent = filename.getParentFile();
	 			image.put(Images.ImageColumns.BUCKET_ID, parent.toString()
	 					.toLowerCase().hashCode());
	 			Log.v("patfdfsdfsdfh", "123");
	 			image.put(Images.ImageColumns.BUCKET_DISPLAY_NAME, parent
	 					.getName().toLowerCase());
	 			image.put(MediaColumns.SIZE, filename.length());
	 			image.put(MediaColumns.DATA, filename.getAbsolutePath());
	 			Log.v("patfdfsdfsdfh", "11111");
	 			Uri result = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
	 							image);
	 			Log.v("patfdfsdfsdfh", "" + result);

	 			Toast.makeText(getApplicationContext(),
	 					"File is Saved in  " + filename, Toast.LENGTH_SHORT)
	 					.show();
	 			Log.v("path", "" + filename.getAbsolutePath());
	 			
	 		} catch (Exception e) {
	 			e.printStackTrace();
	 		}
	 		Log.e("hhhF "+map.getHeight(),"wwwF "+map.getWidth());*/
		//  printText("printer1", "",map);
		

			final AlertDialog alertDialog2 = new AlertDialog.Builder(
					ReportsActivity.this).create();
			LayoutInflater mInflater1 = LayoutInflater
					.from(ReportsActivity.this);
			View layout1 = mInflater1.inflate(
					R.layout.licence_agreement, null);
			Button accept = (Button) layout1
					.findViewById(R.id.accept);
			Button decline = (Button) layout1
					.findViewById(R.id.decline);
			WebView licence = (WebView) layout1
					.findViewById(R.id.licence);

			licence.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/printData/webfile.html");
			accept.setText("Print");
			decline.setText("Cancel");
			accept.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					 web1.setDrawingCacheEnabled(true);

				        web1.buildDrawingCache();

				        Bitmap map1=web1.getDrawingCache();
				         if(web1.getHeight()>0&&web1.getWidth()>0)
				         map1=drawableToBitmap(getBitmapFromView(web1));
			web1.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/printData/webfile.html");
				         web1.setDrawingCacheEnabled(true);

					        web1.buildDrawingCache();
				         map1=web1.getDrawingCache();
				         if(web1.getHeight()>0&&web1.getWidth()>0){
					         map1=drawableToBitmap(getBitmapFromView(web1));
					         Log.v("srii","sriiiimmmmm");
					         }
					alertDialog2.dismiss();
					 printText("printer1", "",map1);
				}
			});
			decline.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					alertDialog2.dismiss();
				}
			});
			alertDialog2.setView(layout1);
			alertDialog2.show();
		 }catch(NumberFormatException n){
				n.printStackTrace();
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
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
	private void printText(String printerid,String print_text, Bitmap print_image) {
		boolean forprintid = false;
		ArrayList<String> printVal = new ArrayList<String>();
	
			String selectQuery = "SELECT  * FROM "
					+ DatabaseForDemo.PRINTER_TABLE; 
			Cursor mCursor1 = dbforloginlogoutReadReport.rawQuery(selectQuery, null);
			int count = mCursor1.getColumnCount();
			System.out.println("hari  " + mCursor1);
			System.out.println("hari  " + count);
			if (mCursor1 != null) {
				Log.e("getCount", "parddhu1     v:" + mCursor1.getCount());
				if (mCursor1.getCount() > 0) {
					Log.e("Rama", "parddhu2 " + mCursor1.getCount());
					if (mCursor1.moveToFirst()) {
						Log.e("Rama", "parddhu3");
						do {
							if (printerid
									.equals(mCursor1.getString(mCursor1
											.getColumnIndex(DatabaseForDemo.PRINTER_ID)))) {
								for (int i = 0; i < count; i++) {
									String value = mCursor1.getString(i);
									printVal.add(value);
								}
								forprintid = true;
							}

						} while (mCursor1.moveToNext());
					}
					if (forprintid) {

					} else {
						if (mCursor1.moveToFirst()) {
							for (int i = 0; i < count; i++) {
								String value = mCursor1.getString(i);
								printVal.add(value);
							}
						}
					}
				} else {
					Toast.makeText(getApplicationContext(),
							"Set The Printer Settings", 1000).show();
				}
			}
			mCursor1.close();
		//}

		Log.e("Rama", "parddhu4");
		if (printVal.size() > 14) {
			PrintingNow(printVal, print_text,print_image);
		}

	}
	Print printer;
	void PrintingNow(ArrayList<String> printVal, String printingText,Bitmap printingImage) {

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
			            ShowMsg.showError(R.string.errmsg_noimage, ReportsActivity.this);
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
						printer = new Print(ReportsActivity.this);
						printer.openPrinter(deviceType, "" + printVal.get(13),
								Print.TRUE, 1000);

					} catch (Exception e) {
						Log.e("Rama", "parddhu12");
						ShowMsg.showException(e, "openPrinter",
								ReportsActivity.this);
						return;
					}
					printer.sendData(builder, Constants.SEND_TIMEOUT, status,
							battery);
					printer.closePrinter();
					Log.e("Rama", "parddhu13");
				} catch (EposException e) {
					ShowMsg.showStatus(e.getErrorStatus(),
							e.getPrinterStatus(), e.getBatteryStatus(),
							ReportsActivity.this);
					printer.closePrinter();
				}
			} catch (Exception e) {
				ShowMsg.showException(e, method, ReportsActivity.this);
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
				            ShowMsg.showError(R.string.errmsg_noimage, ReportsActivity.this);
				            return ;
				        }
				PrinterFunctions.PrintBitmapImage(ReportsActivity.this, typp+""+printVal.get(13), "", getResources(), printingImage, 546, compressionEnable);
	        //	PrinterFunctions.PrintSampleReceipt(ReportsActivity.this,  "TCP:"+printVal.get(13),   "",  commandType, getResources(), "3inch (78mm)", printingText);
			} catch (Exception e) {
				System.out.println(e);
			}
		}
		Log.e("Rama", "parddhu20");

	}
	public Drawable getBitmapFromView(View view) {
		if(view.getHeight()<5000){
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
		}else{
			 Drawable d =new BitmapDrawable();
			    return d;
		}
	}
		
	public static Bitmap drawableToBitmap(Drawable drawable) {
	   if (drawable instanceof BitmapDrawable) {
	        return ((BitmapDrawable)drawable).getBitmap();
	    }
Log.e("error " ,"bitmap");
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
	void createHtmlFile(String webtext){
		try {
			File myFile = new File("/sdcard/printData/webfile.html");
			myFile.createNewFile();
			FileOutputStream fOut = new FileOutputStream(myFile);
			OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
			myOutWriter.append(""+webtext);
			myOutWriter.close();
			fOut.close();
			System.out.println("html written successfully..");
			System.out.println(""+webtext);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			showAlertDialog(ReportsActivity.this, "Reports", "Getting an Error" ,
					false);
		} catch (IOException e) {
			showAlertDialog(ReportsActivity.this, "Reports", "Getting an Error" ,
					false);
			e.printStackTrace();
		}
		web1.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/printData/webfile.html");
		web1.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/printData/webfile.html");
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		sqlDBReport.close();
		super.onDestroy();
	}
	void stockLayoutView(){
		web1.setVisibility(View.INVISIBLE);
		File file = new File("/sdcard/printData/webfile.html");
			if(file.exists()){
				Log.v("file", "exists");
		    web1.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/printData/webfile.html");
			}
		autoTextStringsItems.clear();
		final ArrayList<String> autoTextStrings = new ArrayList<String>();
		final ArrayList<String> autoTextStringsname = new ArrayList<String>();
	//	final ArrayList<String> autoTextStringsvendor = new ArrayList<String>();
		final ArrayList<String> catspinnerdataSearch = new ArrayList<String>();
		final ArrayList<String> deptspinnerdataSearch = new ArrayList<String>();
		catspinnerdataSearch.add("All");
		catspr1 = (Spinner) findViewById(R.id.category_spinner);
		dptspr1 = (Spinner) findViewById(R.id.department_spinner);
		go_stock=(Button) findViewById(R.id.go_stock);
		/*Cursor mCursorqwqe = dbforloginlogoutReadReport.rawQuery(
				"select " + DatabaseForDemo.DepartmentID + " from "
						+ DatabaseForDemo.DEPARTMENT_TABLE, null);
		startManagingCursor(mCursorqwqe);
		System.out.println(mCursorqwqe);
		if (mCursorqwqe != null) {
			if (mCursorqwqe.moveToFirst()) {
				do {
					String catid = mCursorqwqe.getString(mCursorqwqe
							.getColumnIndex(DatabaseForDemo.DepartmentID));
					deptspinnerdataSearch.add(catid);
				} while (mCursorqwqe.moveToNext());
			}
		}
		mCursorqwqe.close();*/
		final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
				ReportsActivity.this, android.R.layout.simple_spinner_item,
				deptspinnerdataSearch);
		//adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
		//dptspr1.setAdapter(adapter1);
		
		Cursor mCursor3qwe = dbforloginlogoutReadReport.rawQuery(
				"select " + DatabaseForDemo.CategoryId + " from "
						+ DatabaseForDemo.CATEGORY_TABLE, null);
		if (mCursor3qwe != null) {
			if (mCursor3qwe.moveToFirst()) {
				do {
					String catid = mCursor3qwe.getString(mCursor3qwe
							.getColumnIndex(DatabaseForDemo.CategoryId));
					catspinnerdataSearch.add(catid);
				} while (mCursor3qwe.moveToNext());
			}
		}
		mCursor3qwe.close();
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
				ReportsActivity.this, android.R.layout.simple_spinner_item,
				catspinnerdataSearch);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
		catspr1.setAdapter(adapter2);
		catspr1.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
				deptspinnerdataSearch.clear();
				deptspinnerdataSearch.add("All");
				String urlval = catspr1.getSelectedItem().toString();
				String qqqqqq="select " + DatabaseForDemo.DepartmentID + " from "
						+ DatabaseForDemo.DEPARTMENT_TABLE;
				if(!urlval.equals("All")){

					qqqqqq="select " + DatabaseForDemo.DepartmentID + " from "
							+ DatabaseForDemo.DEPARTMENT_TABLE +" where "+DatabaseForDemo.CategoryForDepartment+ "=\""
									+ urlval + "\"";
				}
				Cursor mCursorewq = dbforloginlogoutReadReport.rawQuery(qqqqqq
						, null);
				System.out.println(mCursorewq);
				if (mCursorewq != null) {
					if (mCursorewq.moveToFirst()) {
						do {
							String catid = mCursorewq.getString(mCursorewq
									.getColumnIndex(DatabaseForDemo.DepartmentID));
							deptspinnerdataSearch.add(catid);
						} while (mCursorewq.moveToNext());
					}
				}
				mCursorewq.close();
				adapter1.setNotifyOnChange(true);
				adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
				dptspr1.setAdapter(adapter1);
				}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		Cursor itemandNoCursor = dbforloginlogoutReadReport.rawQuery(
				"select " + DatabaseForDemo.INVENTORY_ITEM_NO + " , "+DatabaseForDemo.INVENTORY_VENDOR+ " , "+
						 DatabaseForDemo.INVENTORY_ITEM_NAME + " from "
						+ DatabaseForDemo.INVENTORY_TABLE, null);
		System.out.println(itemandNoCursor);
		if (itemandNoCursor != null) {
			if (itemandNoCursor.moveToFirst()) {
				do {

					autoTextStrings
							.add(itemandNoCursor.getString(itemandNoCursor
									.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NO)));
					
					autoTextStringsname
							.add(itemandNoCursor.getString(itemandNoCursor
									.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NAME)));

				/*	autoTextStringsvendor
							.add(itemandNoCursor.getString(itemandNoCursor
									.getColumnIndex(DatabaseForDemo.INVENTORY_VENDOR)));*/

				} while (itemandNoCursor.moveToNext());
			}
		}
		itemandNoCursor.close();
		autoTextStringsItems.addAll(autoTextStringsname);
		ArrayAdapter<String> adapterauto = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, autoTextStringsItems);
		stockitem_auto=(AutoCompleteTextView) findViewById(R.id.stock_autocomplate);
		stockitem_auto.setThreshold(1);
		stockitem_auto.setAdapter(adapterauto);
		stockitem_auto.setTextColor(Color.BLACK);
		
		go_stock.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				stocksize=0;
				String auto_itemname=stockitem_auto.getText().toString().trim();
				String dp_stock=dptspr1.getSelectedItem().toString();
				from = fromdate.getText().toString().trim() + " "
						+ fromtime.getText().toString().trim();
				to = todate.getText().toString().trim() + " "
						+ totime.getText().toString().trim();
				String qry="";
			if (from.length() > 3 && to.length() > 3) {
				if(auto_itemname.length()>1){
					String webString=headTag(from, to);
					  webString=webString+stockReportString(auto_itemname,from,to);
					  webString=webString+endTag();
										imageForItemStock(webString);
					}else{
					if(dp_stock.equals("All")){
						Toast.makeText(ReportsActivity.this, "select the Department", 2000).show();
					}else{
					 qry="Select "+DatabaseForDemo.INVOICE_ITEM_NAME+", "+DatabaseForDemo.INVOICE_ITEM_ID+" FROM "+DatabaseForDemo.INVOICE_ITEMS_TABLE
						 +" WHERE ("+DatabaseForDemo.INVOICE_DEPARTMETNT+"=\""+dp_stock+"\") AND ("+DatabaseForDemo.CREATED_DATE+" between '"+from+"' and '"+to+"')";
					 dpQryForStock(qry);
					}
				}
				}else{
					if(auto_itemname.length()>1){
      String webString=headTag(from, to);
	  webString+=stockReportString(auto_itemname,from,to);
	  webString+=endTag();
						imageForItemStock(webString);
					}else{
						if(dp_stock.equals("All")){
							Toast.makeText(ReportsActivity.this, "select the Department", 2000).show();
						}else{
						 qry="Select "+DatabaseForDemo.INVOICE_ITEM_NAME+", "+DatabaseForDemo.INVOICE_ITEM_ID+" FROM "+DatabaseForDemo.INVOICE_ITEMS_TABLE
								 +" WHERE ("+DatabaseForDemo.INVOICE_DEPARTMETNT+"=\""+dp_stock+"\")";
						 dpQryForStock(qry);
						}
					}
			}
			
			}
		});
		
	}
	void dpQryForStock(String dpQry){
		String webString=headTag(from, to);
		Cursor gangCursor = dbforloginlogoutReadReport.rawQuery(dpQry,null);
		if (gangCursor != null) {
			if (gangCursor.moveToFirst()) {
				do {
					String catid = gangCursor.getString(gangCursor
							.getColumnIndex(DatabaseForDemo.INVOICE_ITEM_NAME));
					 webString+=stockReportString(catid,from,to);
				} while (gangCursor.moveToNext());
			}
		}else{
			Toast.makeText(ReportsActivity.this, "No Data", 2000).show();
		}
		  gangCursor.close();
		  webString=webString+endTag();
			imageForItemStock(webString);
	}
	
	
	void imageForItemStock(String flashval){
		  createHtmlFile(flashval);
		  web1.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/printData/webfile.html");
		LayoutParams params = web1.getLayoutParams();
		params.width = 550;
		params.height = stocksize;
		web1.setLayoutParams(params);
		web1.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/printData/webfile.html");
	 		final AlertDialog alertDialog2 = new AlertDialog.Builder(
					ReportsActivity.this).create();
			LayoutInflater mInflater1 = LayoutInflater
					.from(ReportsActivity.this);
			View layout1 = mInflater1.inflate(
					R.layout.licence_agreement, null);
			Button accept = (Button) layout1
					.findViewById(R.id.accept);
			Button decline = (Button) layout1
					.findViewById(R.id.decline);
			WebView licence = (WebView) layout1
					.findViewById(R.id.licence);

			licence.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/printData/webfile.html");
			accept.setText("Print");
			decline.setText("Cancel");
			accept.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					 web1.setDrawingCacheEnabled(true);

				        web1.buildDrawingCache();

				        Bitmap map1=web1.getDrawingCache();
				         if(web1.getHeight()>0&&web1.getWidth()>0)
				         map1=drawableToBitmap(getBitmapFromView(web1));
			web1.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/printData/webfile.html");
				         web1.setDrawingCacheEnabled(true);

					        web1.buildDrawingCache();
				         map1=web1.getDrawingCache();
				         if(web1.getHeight()>0&&web1.getWidth()>0){
					         map1=drawableToBitmap(getBitmapFromView(web1));
					         Log.v("srii","sriiiimmmmm");
					         }
					alertDialog2.dismiss();
					 printText("printer1", "",map1);
				}
			});
		
			decline.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					alertDialog2.dismiss();
				}
			});
			alertDialog2.setView(layout1);
			alertDialog2.show();

	}
	
	String stockReportString(String item_nam, String fTime,String tTime){
		String middil=
	ValuesThroughItemName(item_nam , fTime,tTime)
	+"<tr><td colspan='2'><table class='stockmodification' cellspacing='0' cellpadding='0'>"
    +"<tr><th colspan='3'>Stock Editing History</th></tr><tr><th>Date &amp; Time</th><th>Edited Quantity</th><th>Employee ID</th></tr>"
	+stockHistory(item_nam)
	+"</table></td> </tr>";
		return middil;
	}
	
	String headTag(String fTime,String tTime){
		stocksize+=130;
		String headlines=Parameters.printTextgetFromDatabase(ReportsActivity.this,"printer1");
		String head="<html><head><meta http-equiv='Content-Type' content='text/html; charset=UTF-8'>"
	+"<title>Day 1 Stock Report</title><style type='text/css'>td { background-color:none;font-family : Verdana;color:#000000;font-size:28px;}"
	+"table.stockmodification td,table.stockmodification th{border:1px dotted #000000; border-top:0px; border-right:0px; padding:6px;}"
	+"	table.stockmodification{border:1px dotted #000000; border-bottom:0px; border-left:0px;}table.stockmodification td{Verdana;color:#000000;font-size:28px;} table.stockmodification th{Verdana;color:#000000;font-size:28px;}</style></head>"
	+"<body style='background-color:none;'><center><table width='400' align='center' border='0'><tr>"
	+"<th align='center' colspan='2'>"+headlines+"</th></tr><tr><th align='center' colspan='2'>Stock Report</th></tr>"
	+"<tr><th align='center' colspan='2'>"+fTime+"   "+tTime+"</th></tr>";
		return head;
	}
	String endTag(){
		String end="</table> </center></body></html>";
		return end;
	}
	String ValuesThroughItemName(String item_nam,String fTime,String tTime){
		Double saleQty1=0.0;
		Double saleTax1=0.0;
		Double saleAmount1=0.0;
		Double totalStock=0.0;
		String qry1="";
		try{
		if(fTime.length() > 3 && tTime.length() > 3){
		 qry1="Select SUM("+DatabaseForDemo.INVOICE_QUANTITY+") as 'totalquantity', SUM("+DatabaseForDemo.INVOICE_TAX+") as 'totaltax', SUM("+DatabaseForDemo.INVOICE_YOUR_COST
				+" * "+DatabaseForDemo.INVOICE_QUANTITY+")  as 'Totalprice' FROM "+DatabaseForDemo.INVOICE_ITEMS_TABLE+" WHERE ("+DatabaseForDemo.INVOICE_ITEM_NAME
				 +"='"+item_nam+"') AND ("+DatabaseForDemo.CREATED_DATE+" between '"+fTime+"' and '"+tTime+"')";
		}else{
			 qry1="Select SUM("+DatabaseForDemo.INVOICE_QUANTITY+") as 'totalquantity', SUM("+DatabaseForDemo.INVOICE_TAX+") as 'totaltax', SUM("+DatabaseForDemo.INVOICE_YOUR_COST
						+" * "+DatabaseForDemo.INVOICE_QUANTITY+")  as 'Totalprice' FROM "+DatabaseForDemo.INVOICE_ITEMS_TABLE+" WHERE ("+DatabaseForDemo.INVOICE_ITEM_NAME
						 +"='"+item_nam+"')";
		}
		Cursor itemnCursor = dbforloginlogoutReadReport.rawQuery(qry1,null);
		System.out.println(itemnCursor);
		if (itemnCursor != null) {
			if (itemnCursor.moveToFirst()) {
				do {
					saleQty1=itemnCursor.getDouble(0);
					saleTax1=itemnCursor.getDouble(1);
					saleAmount1=itemnCursor.getDouble(2);
					Log.v(""+saleQty1,saleTax1+"  "+saleAmount1);
				} while (itemnCursor.moveToNext());
			}
		}else{
		}
		saleAmount1=saleAmount1+saleTax1;
		itemnCursor.close();
		
		String invetory_qty="Select SUM("+DatabaseForDemo.INVENTORY_IN_STOCK+") as 'totalstock' FROM "+DatabaseForDemo.INVENTORY_TABLE+" WHERE ("+DatabaseForDemo.INVENTORY_ITEM_NAME+"='"+item_nam+"');";
		Cursor inventoryCursor = dbforloginlogoutReadReport.rawQuery(invetory_qty,null);
		if (inventoryCursor != null) {
			if (inventoryCursor.moveToFirst()) {
				do {
					totalStock=inventoryCursor.getDouble(0);
					Log.v(""+totalStock,"gggggg");
				} while (inventoryCursor.moveToNext());
			}
		}else{
		}
		inventoryCursor.close();
		}catch(NumberFormatException n){
			n.printStackTrace();
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
		String itemstr= "<tr><td align='center' colspan='2'><h2><u>"+item_nam+"</u></h2></td></tr>"
					+"<tr><td align='left'>Net Sales</td><td align='right'>$"+df.format(saleAmount1)+"</td></tr>"
				    +"<tr><td align='left'>Total Items Sold</td><td align='right'>"+df.format(saleQty1)+"</td></tr>"
					+"<tr><td align='left'>Current Stock Count</td><td align='right'>"+df.format(totalStock)+"</td></tr>";
		stocksize+=170;
		if(item_nam.length()>15){
			stocksize+=50;
		}
		if(item_nam.length()>30){
			stocksize+=50;
		}
		return itemstr;
	}
	String stockHistory(String item_num){
		stocksize+=130;
		String stock_qty="Select * FROM "+DatabaseForDemo.STOCK_MODIFICATION_TABLE+" WHERE ("+DatabaseForDemo.MDF_ITEM_NAME+"='"+item_num+"');";
		Cursor inventoryCursor = dbforloginlogoutReadReport.rawQuery(stock_qty,null);
		String forStock="";
		System.out.println(inventoryCursor);
		if (inventoryCursor != null) {
			if (inventoryCursor.getCount()>0){
			if (inventoryCursor.moveToFirst()) {
				do {
					String one=inventoryCursor.getString(inventoryCursor
							.getColumnIndex(DatabaseForDemo.MODIFIED_DATE));
					String two=inventoryCursor.getString(inventoryCursor
							.getColumnIndex(DatabaseForDemo.MDF_STOCK_COUNT));
					String three=inventoryCursor.getString(inventoryCursor
							.getColumnIndex(DatabaseForDemo.MDF_EMP_ID));
					stocksize+=70;
					 forStock += "<tr><th>"+one+"</th><th>"+two+"</th><th>"+three+"</th></tr>";
				} while (inventoryCursor.moveToNext());
			}
			}else{
				stocksize+=70;
				forStock= "<tr><th>--</th><th>--</th><th>--</th></tr>";
			}
		}else{
			stocksize+=70;
			forStock= "<tr><th>--</th><th>--</th><th>--</th></tr>";
		}
		inventoryCursor.close();
		return forStock;
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Parameters.printerContext=ReportsActivity.this;
		super.onResume();
	}
	
}