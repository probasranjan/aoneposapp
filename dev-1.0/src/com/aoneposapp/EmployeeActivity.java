package com.aoneposapp;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ParseException;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsListView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aoneposapp.adapters.EmployeeEditAdapter;
import com.aoneposapp.utils.DatabaseForDemo;
import com.aoneposapp.utils.Employee;
import com.aoneposapp.utils.JsonPostMethod;
import com.aoneposapp.utils.Parameters;
import com.aoneposapp.utils.PayrollEmployee;
import com.aoneposapp.utils.Permisstions_Employee;
import com.aoneposapp.utils.PersonalEmployee;

@SuppressWarnings("deprecation")
public class EmployeeActivity extends Activity implements
		android.view.View.OnClickListener, EmployeeEditAdapter.OnWidgetItemClicked {
	Button tab;
	SlidingDrawer slidingDrawer;
	ImageView slideButton, image0, image1, image2, image3, image4, image5,
			image6, image7, image8, logout, logout2;
	int height;
	int wwidth;
	Button b4, addcat, viewcat;
	LinearLayout ll_add, ll_view;
	EditText mDepartment, mEmployeeId, mPassword, mDisplayName;
	EditText mCardswipeId, mCustomer, mHourlyWage;
	int checkk;
	boolean permissionval = false;
	String dataval="";
	String item_store_name="";
	String item_store_id="";
	 RadioButton radioButton;
	CheckBox cc_tips, disable, admin_card;
	Button permitions, personalinfo, payrollinfo, mAdd, mCancel,storeinfo;
String emp_emp_Id,cuurentTime,randomNumber;
PersonalEmployee personalemployee = new PersonalEmployee();
PayrollEmployee payrollemployee = new PayrollEmployee();
Permisstions_Employee permissionsemployee = new Permisstions_Employee();

String datavalforedit = "";
ArrayList<String> id_data = new ArrayList<String>();
ArrayList<String> desc_data = new ArrayList<String>();
ArrayList<String> total_id_data = new ArrayList<String>();
ArrayList<String> total_desc_data = new ArrayList<String>();
DatabaseForDemo demodbforedit;
int pagenum = 1;
public static int pagecount = 3;
public static int testforid = 1;
int stLoop = 0;
int endLoop = 0;
int totalcount = 0;
LinearLayout llforedit,ll11foredit;
int j;
private ListView tax_listforedit;
Button headlineforedit;
int checkkforedit;
String[] data1 = null;
String[] data12 = null;
String[] data123 = null;
RadioButton radioButtonforedit;
String store_nameforedit = "";
String store_idforedit = "";
String item_store_idforedit = "";
String item_store_nameforedit = "";
ArrayList<String> emp_id = new ArrayList<String>();
ArrayList<String> emp_name = new ArrayList<String>();
final PersonalEmployee personalemployeeforedit = new PersonalEmployee();
final PayrollEmployee payrollemployeeforedit = new PayrollEmployee();
final Permisstions_Employee permissionsemployeeforedit = new Permisstions_Employee();
String emp_emp_Idforedit;
String emp_emp_Nameforedit;
String cuurentTimeforedit, modifytimeforedit, randomNumberforedit,passwordText;
DatabaseForDemo sqlDBEmployee;
SQLiteDatabase dbforloginlogoutWriteEmployee ,dbforloginlogoutReadEmployee;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.employee_activity);
		sqlDBEmployee=new DatabaseForDemo(EmployeeActivity.this);
		dbforloginlogoutWriteEmployee = sqlDBEmployee.getWritableDatabase();
		dbforloginlogoutReadEmployee = sqlDBEmployee.getReadableDatabase();
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
		image4.setBackgroundResource(R.drawable.e3);
		logout.setBackgroundResource(R.drawable.logoutnormal);
		TextView loginnameempid = (TextView)findViewById(R.id.loginnameval);
		loginnameempid.setText(Parameters.usertypeloginvalue);
		image0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(EmployeeActivity.this,
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
					Intent intent1 = new Intent(EmployeeActivity.this,
							InventoryActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							EmployeeActivity.this,
							"Sorry",
							"You are not authenticated to perform this operation",
							false);
				}
			}
		});
		image2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Parameters.stores_permission){
				Intent intent1 = new Intent(EmployeeActivity.this,
						StoresActivity.class);
				startActivity(intent1);
				finish();
			} else {
				showAlertDialog(
						EmployeeActivity.this,
						"Sorry",
						"You are not authenticated to perform this operation",
						false);
			}
			}
		});
		image3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Parameters.customer_permission) {
					Intent intent1 = new Intent(EmployeeActivity.this,
							CustomerActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							EmployeeActivity.this,
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
			}
		});
		image5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Parameters.reports_permission) {
					Intent intent1 = new Intent(EmployeeActivity.this,
							ReportsActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							EmployeeActivity.this,
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
					Intent intent1 = new Intent(EmployeeActivity.this,
							SettingsActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							EmployeeActivity.this,
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
				Intent intent1 = new Intent(EmployeeActivity.this,
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
					Intent intent1 = new Intent(EmployeeActivity.this,
							ProfileActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog(
							EmployeeActivity.this,
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
				Parameters.methodForLogout(EmployeeActivity.this);
				finish();
			}
		});
		logout2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Parameters.methodForLogout(EmployeeActivity.this);
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
		
		addcat = (Button) findViewById(R.id.addcat);
		viewcat = (Button) findViewById(R.id.viewcat);
		ll_add = (LinearLayout) findViewById(R.id.add_ll);
		ll_view = (LinearLayout) findViewById(R.id.view_ll);
		
		addcat.setBackgroundResource(R.drawable.highlightedtopmenuitem);
		viewcat.setBackgroundResource(R.drawable.toprightmenu);
		ll_add.setVisibility(View.VISIBLE);
		ll_view.setVisibility(View.GONE);
		addcat.setTextColor(Color.BLACK);
		viewcat.setTextColor(Color.WHITE);
		
		tax_listforedit = (ListView) findViewById(R.id.listView1);
		llforedit = (LinearLayout) findViewById(R.id.linearLayout1);
		ll11foredit = (LinearLayout) findViewById(R.id.viewnames);
		ll11foredit.setVisibility(View.VISIBLE);
		headlineforedit = (Button) findViewById(R.id.button2);
		headlineforedit.setText("Employee Details");
		
		addcat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addcat.setBackgroundResource(R.drawable.highlightedtopmenuitem);
				viewcat.setBackgroundResource(R.drawable.toprightmenu);
				ll_add.setVisibility(View.VISIBLE);
				ll_view.setVisibility(View.GONE);
				addcat.setTextColor(Color.BLACK);
				viewcat.setTextColor(Color.WHITE);
			}
		});

		viewcat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				addcat.setBackgroundResource(R.drawable.toprightmenu);
				viewcat.setBackgroundResource(R.drawable.highlightedtopmenuitem);
				ll_add.setVisibility(View.GONE);
				ll_view.setVisibility(View.VISIBLE);
				addcat.setTextColor(Color.WHITE);
				viewcat.setTextColor(Color.BLACK);
				listUpdate();
			}
		});
		
		mDepartment = (EditText) findViewById(R.id.adminEmployeeDepartmentEdit);
		mEmployeeId = (EditText) findViewById(R.id.editText3);
		mPassword = (EditText) findViewById(R.id.adminEmployeePasswordEdit);
		mDisplayName = (EditText) findViewById(R.id.adminEmployeeDisplayEdit);
		mCardswipeId = (EditText) findViewById(R.id.editText5);
		mCustomer = (EditText) findViewById(R.id.adminEmployeeCustomerEdit);
		mHourlyWage = (EditText) findViewById(R.id.adminEmployeeHourlyWageEdit);
		cc_tips = (CheckBox) findViewById(R.id.checkBox2);
		disable = (CheckBox) findViewById(R.id.checkBox1);
		admin_card = (CheckBox) findViewById(R.id.checkBox3);
		mAdd = (Button) findViewById(R.id.employeesave);
		mAdd.setOnClickListener(addOnClickListener);
		mCancel = (Button) findViewById(R.id.employeecancel);
		mCancel.setOnClickListener(cancelOnClickListener);
	//	picture = (TextView) findViewById(R.id.picture);
		//mImageView = (ImageView) findViewById(R.id.imageView1);
		permitions = (Button) findViewById(R.id.permitions);
		personalinfo = (Button) findViewById(R.id.personalinfo);
		payrollinfo = (Button) findViewById(R.id.payroll);
		storeinfo=(Button) findViewById(R.id.storeinfo);
		permitions.setOnClickListener(permissionClickListener);
		personalinfo.setOnClickListener(personalClickListener);
		payrollinfo.setOnClickListener(payrollClickListener);
		storeinfo.setOnClickListener(storeinfoonClockListener);
		
		/*picture.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {

				Intent i = new Intent(
						Intent.ACTION_PICK,
						android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

				startActivityForResult(i, RESULT_LOAD_IMAGE);
			}
		});*/
		
		admin_card.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
				// TODO Auto-generated method stub
				if(isChecked){
					permissionval = true;
				}else{
					permissionval = false;
				}
			}
		});
	}
	
	@Override
	public void onEditClicked(View v, final String string) {
		// TODO Auto-generated method stub
		final AlertDialog alertDialog1 = new AlertDialog.Builder(
				EmployeeActivity.this,android.R.style.Theme_Translucent_NoTitleBar).create();
		final EditText mDepartment;
		final EditText mEmployeeId, mPassword, mDisplayName;
		final EditText mCardswipeId, mCustomer, mHourlyWage;
		final CheckBox cc_tips, disable, admin_card;
		final Button permitions, personalinfo, payrollinfo, mAdd, mCancel, storeinfo;

		LayoutInflater mInflater = LayoutInflater.from(EmployeeActivity.this);
		View layout = mInflater.inflate(R.layout.add_employee, null);
		mDepartment = (EditText) layout
				.findViewById(R.id.adminEmployeeDepartmentEdit);
		mEmployeeId = (EditText) layout.findViewById(R.id.editText3);
		mPassword = (EditText) layout
				.findViewById(R.id.adminEmployeePasswordEdit);
		mDisplayName = (EditText) layout
				.findViewById(R.id.adminEmployeeDisplayEdit);
		storeinfo = (Button) layout.findViewById(R.id.storeinfo);
		mCardswipeId = (EditText) layout.findViewById(R.id.editText5);
		mCustomer = (EditText) layout
				.findViewById(R.id.adminEmployeeCustomerEdit);
		mHourlyWage = (EditText) layout
				.findViewById(R.id.adminEmployeeHourlyWageEdit);
		cc_tips = (CheckBox) layout.findViewById(R.id.checkBox2);
		disable = (CheckBox) layout.findViewById(R.id.checkBox1);
		admin_card = (CheckBox) layout.findViewById(R.id.checkBox3);
		mAdd = (Button) layout.findViewById(R.id.employeesave);
		TextView tv=(TextView) layout.findViewById(R.id.for_passwordchangetext);
		tv.setVisibility(View.VISIBLE);
		mCancel = (Button) layout.findViewById(R.id.employeecancel);
		permitions = (Button) layout.findViewById(R.id.permitions);
		personalinfo = (Button) layout.findViewById(R.id.personalinfo);
		payrollinfo = (Button) layout.findViewById(R.id.payroll);

		Cursor mCursor = dbforloginlogoutReadEmployee.rawQuery("select * from "
				+ DatabaseForDemo.EMPLOYEE_TABLE + " where "
				+ DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID + "=\"" + string + "\"",
				null);
		System.out.println(mCursor);
		if (mCursor != null) {
			int count = mCursor.getColumnCount();
			System.out.println(count);
			if (mCursor.moveToFirst()) {
				do {
					String[] data = new String[count];
					for (int i = 1; i < count; i++) {
						System.out.println(mCursor.getString(i).length());
						data[i] = mCursor.getString(i);
						System.out.println(mCursor.getString(i));
					}

					cuurentTimeforedit = data[2];
				 randomNumberforedit = data[1];
					mDepartment.setText(data[5]);
					mEmployeeId.setText(data[6]);
					mEmployeeId.setEnabled(false);
					 emp_emp_Idforedit = data[6];
					//mPassword.setText(data[7]);
						passwordText=""+data[7];
					mDisplayName.setText(data[8]);
					 emp_emp_Nameforedit = data[8];
					mDisplayName.setEnabled(false);
					mCardswipeId.setText(data[9]);
					mCustomer.setText(data[10]);
					mHourlyWage.setText(data[11]);
					if (data[12].equals("true"))
						disable.setChecked(true);
					if (data[13].equals("true"))
						cc_tips.setChecked(true);
					if (data[14].equals("true"))
						admin_card.setChecked(true);

				} while (mCursor.moveToNext());

			}
		}
		System.out.println("fffffffffffffffffffggg");
		mCursor.close();
		Cursor mCursor1 = dbforloginlogoutReadEmployee.rawQuery("select * from "
				+ DatabaseForDemo.EMP_PERMISSIONS_TABLE + " where "
				+ DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID + "=\"" + string + "\"",
				null);
		System.out.println(mCursor1);
		if (mCursor1 != null) {
			int count = mCursor1.getColumnCount();
			System.out.println(count);
			if (mCursor1.moveToFirst()) {
				do {
					data1 = new String[count];
					for (int i = 1; i < count; i++) {
						System.out.println(mCursor1.getCount());
						System.out.println(mCursor1.getString(i));
						data1[i] = mCursor1.getString(i);
					}
					permissionsemployeeforedit.setmInventoryT(data1[5]);
					permissionsemployeeforedit.setmCustomersT(data1[7]);
					permissionsemployeeforedit.setmReportsT(data1[8]);
					permissionsemployeeforedit.setmDiscountsT(data1[9]);
					permissionsemployeeforedit.setmSettingsT(data1[10]);
					permissionsemployeeforedit.setmPriceChangesT(data1[11]);
					permissionsemployeeforedit.setmAllowExitT(data1[12]);
					permissionsemployeeforedit.setmVendorPayoutsT(data1[13]);
					permissionsemployeeforedit.setmDeleteItemsT(data1[14]);
					permissionsemployeeforedit.setmVoidInvoicesT(data1[15]);
					permissionsemployeeforedit.setmTransactionsT(data1[16]);
					permissionsemployeeforedit.setmHoldPrintT(data1[17]);
					permissionsemployeeforedit.setmCreditcardsT(data1[18]);
					permissionsemployeeforedit.setmEndCashT(data1[19]);

				} while (mCursor1.moveToNext());

			}
		}

		mCursor1.close();
		Cursor mCursor12 = dbforloginlogoutReadEmployee.rawQuery("select * from "
				+ DatabaseForDemo.EMP_PERSONAL_TABLE + " where "
				+ DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID + "=\"" + string + "\"",
				null);
		System.out.println(mCursor12);
		if (mCursor12 != null) {
			int count = mCursor12.getColumnCount();
			System.out.println(count);
			if (mCursor12.moveToFirst()) {
				do {
					data12 = new String[count];
					for (int i = 1; i < count; i++) {
						System.out.println(mCursor12.getCount());
						System.out.println(mCursor12.getString(i));
						data12[i] = mCursor12.getString(i);
					}

					personalemployeeforedit.setEmp_name(data12[5]);
					personalemployeeforedit.setEmp_id(data12[7]);
					personalemployeeforedit.setEmp_email(data12[8]);
					personalemployeeforedit.setEmp_phone(data12[9]);
					personalemployeeforedit.setEmp_birth(data12[10]);
					personalemployeeforedit.setEmp_address(data12[11]);
					personalemployeeforedit.setEmp_city(data12[12]);
					personalemployeeforedit.setEmp_country(data12[13]);
					personalemployeeforedit.setEmp_state(data12[14]);
					personalemployeeforedit.setEmp_postal(data12[15]);
				} while (mCursor12.moveToNext());

			}
		}

		mCursor12.close();

		Cursor mCursor123 = dbforloginlogoutReadEmployee.rawQuery("select * from "
				+ DatabaseForDemo.EMP_PAYROLL_TABLE + " where "
				+ DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID + "=\"" + string + "\"",
				null);
		System.out.println(mCursor123);
		if (mCursor123 != null) {
			int count = mCursor123.getColumnCount();
			System.out.println(count);
			if (mCursor123.moveToFirst()) {
				do {
					data123 = new String[count];
					for (int i = 1; i < count; i++) {
						System.out.println(mCursor123.getCount());
						System.out.println(mCursor123.getString(i));
						data123[i] = "" + mCursor123.getString(i);
					}

				} while (mCursor123.moveToNext());

			}

			payrollemployeeforedit.setFederal(data123[5]);
			payrollemployeeforedit.setAmount(data123[7]);
			payrollemployeeforedit.setStatea(data123[8]);
			payrollemployeeforedit.setStateAmount(data123[9]);
			payrollemployeeforedit.setCredits(data123[10]);
			payrollemployeeforedit.setExcludeCheck(data123[13]);
			// payrollemployee.setExempt(exempt.getSelectedItem().toString().trim());
			// payrollemployee.setFilingstatus(filingstatus.getSelectedItem().toString().trim());
		}

		mCursor123.close();
		Cursor mCursor1234 = dbforloginlogoutReadEmployee.rawQuery("select * from "
				+ DatabaseForDemo.EMP_STORE_TABLE + " where "
				+ DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID + "=\"" + string + "\"",
				null);
		System.out.println(mCursor1234);
		if (mCursor1234 != null) {
			int count = mCursor1234.getColumnCount();
			System.out.println(count);
			String[] data1234 = new String[count];
			if (mCursor1234.moveToFirst()) {
				do {

					for (int i = 1; i < count; i++) {
						data1234[i] = "" + mCursor1234.getString(i);
						System.out.println(mCursor1234.getString(i));
					}

				} while (mCursor1234.moveToNext());

			}
			store_nameforedit = "" + data1234[3];
			store_idforedit = "" + data1234[2];
			System.out.println(store_nameforedit);
			System.out.println(store_idforedit);
		}

		mCursor1234.close();
		permitions.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.v("<>", "ffgdfg");
				final AlertDialog alertDialog12 = new AlertDialog.Builder(
						EmployeeActivity.this,android.R.style.Theme_Translucent_NoTitleBar).create();
				LayoutInflater mInflater2 = LayoutInflater
						.from(EmployeeActivity.this);
				final View layout2 = mInflater2.inflate(
						R.layout.permitions_employee, null);
				Button permitionssave = (Button) layout2
						.findViewById(R.id.permitionssave);
				Button permisstioncancel = (Button) layout2
						.findViewById(R.id.permisstioncancel);
				final RadioGroup mInventory = (RadioGroup) layout2
						.findViewById(R.id.nventory);
				final RadioGroup mCustomers = (RadioGroup) layout2
						.findViewById(R.id.ustomers);
				final RadioGroup mReports = (RadioGroup) layout2
						.findViewById(R.id.eports);
				final RadioGroup mDiscounts = (RadioGroup) layout2
						.findViewById(R.id.iscounts);
				final RadioGroup mSettings = (RadioGroup) layout2
						.findViewById(R.id.ettings);
				final RadioGroup mPriceChanges = (RadioGroup) layout2
						.findViewById(R.id.riceChanges);
				final RadioGroup mAllowExit = (RadioGroup) layout2
						.findViewById(R.id.llowExit);
				final RadioGroup mVendorPayouts = (RadioGroup) layout2
						.findViewById(R.id.endorPayouts);
				final RadioGroup mDeleteItems = (RadioGroup) layout2
						.findViewById(R.id.eleteItems);
				final RadioGroup mVoidInvoices = (RadioGroup) layout2
						.findViewById(R.id.oidInvoices);
				final RadioGroup mTransactions = (RadioGroup) layout2
						.findViewById(R.id.ransactions);
				final RadioGroup mHoldPrint = (RadioGroup) layout2
						.findViewById(R.id.oldPrint);
				final RadioGroup mCreditcards = (RadioGroup) layout2
						.findViewById(R.id.reditcards);
				final RadioGroup mEndCash = (RadioGroup) layout2
						.findViewById(R.id.ndCash);

				RadioButton a0 = (RadioButton) layout2
						.findViewById(R.id.radio0a);
				RadioButton a1 = (RadioButton) layout2
						.findViewById(R.id.radio1a);
				RadioButton b0 = (RadioButton) layout2
						.findViewById(R.id.radio0b);
				RadioButton b1 = (RadioButton) layout2
						.findViewById(R.id.radio1b);
				RadioButton c0 = (RadioButton) layout2
						.findViewById(R.id.radio0c);
				RadioButton c1 = (RadioButton) layout2
						.findViewById(R.id.radio1c);
				RadioButton d0 = (RadioButton) layout2
						.findViewById(R.id.radio0d);
				RadioButton d1 = (RadioButton) layout2
						.findViewById(R.id.radio1d);
				RadioButton e0 = (RadioButton) layout2
						.findViewById(R.id.radio0e);
				RadioButton e1 = (RadioButton) layout2
						.findViewById(R.id.radio1e);
				RadioButton f0 = (RadioButton) layout2
						.findViewById(R.id.radio0f);
				RadioButton f1 = (RadioButton) layout2
						.findViewById(R.id.radio1f);
				RadioButton g0 = (RadioButton) layout2
						.findViewById(R.id.radio0g);
				RadioButton g1 = (RadioButton) layout2
						.findViewById(R.id.radio1g);
				RadioButton h0 = (RadioButton) layout2
						.findViewById(R.id.radio0h);
				RadioButton h1 = (RadioButton) layout2
						.findViewById(R.id.radio1h);
				RadioButton i0 = (RadioButton) layout2
						.findViewById(R.id.radio0i);
				RadioButton i1 = (RadioButton) layout2
						.findViewById(R.id.radio1i);
				RadioButton j0 = (RadioButton) layout2
						.findViewById(R.id.radio0j);
				RadioButton j1 = (RadioButton) layout2
						.findViewById(R.id.radio1j);
				RadioButton k0 = (RadioButton) layout2
						.findViewById(R.id.radio0k);
				RadioButton k1 = (RadioButton) layout2
						.findViewById(R.id.radio1k);
				RadioButton l0 = (RadioButton) layout2
						.findViewById(R.id.radio0l);
				RadioButton l1 = (RadioButton) layout2
						.findViewById(R.id.radio1l);
				RadioButton m0 = (RadioButton) layout2
						.findViewById(R.id.radio0m);
				RadioButton m1 = (RadioButton) layout2
						.findViewById(R.id.radio1m);
				RadioButton n0 = (RadioButton) layout2
						.findViewById(R.id.radio0n);
				RadioButton n1 = (RadioButton) layout2
						.findViewById(R.id.radio1n);

				if (data1[5].equals("Enable")) {
					a0.setChecked(true);
				} else {
					a1.setChecked(true);
				}
				if (data1[7].equals("Enable")) {
					b0.setChecked(true);
				} else {
					b1.setChecked(true);
				}
				if (data1[8].equals("Enable")) {
					c0.setChecked(true);
				} else {
					c1.setChecked(true);
				}
				if (data1[9].equals("Enable")) {
					d0.setChecked(true);
				} else {
					d1.setChecked(true);
				}
				if (data1[10].equals("Enable")) {
					e0.setChecked(true);
				} else {
					e1.setChecked(true);
				}
				if (data1[11].equals("Enable")) {
					f0.setChecked(true);
				} else {
					f1.setChecked(true);
				}
				if (data1[12].equals("Enable")) {
					g0.setChecked(true);
				} else {
					g1.setChecked(true);
				}
				if (data1[13].equals("Enable")) {
					h0.setChecked(true);
				} else {
					h1.setChecked(true);
				}
				if (data1[14].equals("Enable")) {
					i0.setChecked(true);
				} else {
					i1.setChecked(true);
				}
				if (data1[15].equals("Enable")) {
					j0.setChecked(true);
				} else {
					j1.setChecked(true);
				}
				if (data1[16].equals("Enable")) {
					k0.setChecked(true);
				} else {
					k1.setChecked(true);
				}
				if (data1[17].equals("Enable")) {
					l0.setChecked(true);
				} else {
					l1.setChecked(true);
				}
				if (data1[18].equals("Enable")) {
					m0.setChecked(true);
				} else {
					m1.setChecked(true);
				}
				if (data1[19].equals("Enable")) {
					n0.setChecked(true);
				} else {
					n1.setChecked(true);
				}

				permitionssave.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						checkkforedit = mInventory.getCheckedRadioButtonId();
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						checkkforedit = mCustomers.getCheckedRadioButtonId();
						permissionsemployeeforedit.setmInventoryT(radioButtonforedit
								.getText().toString().trim());
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);

						checkkforedit = mReports.getCheckedRadioButtonId();
						permissionsemployeeforedit.setmCustomersT(radioButtonforedit
								.getText().toString().trim());
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						permissionsemployeeforedit.setmReportsT(radioButtonforedit.getText()
								.toString().trim());
						checkkforedit = mDiscounts.getCheckedRadioButtonId();
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						permissionsemployeeforedit.setmDiscountsT(radioButtonforedit
								.getText().toString().trim());
						checkkforedit = mSettings.getCheckedRadioButtonId();
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						permissionsemployeeforedit.setmSettingsT(radioButtonforedit.getText()
								.toString().trim());
						checkkforedit = mPriceChanges.getCheckedRadioButtonId();
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						permissionsemployeeforedit.setmPriceChangesT(radioButtonforedit
								.getText().toString().trim());
						checkkforedit = mAllowExit.getCheckedRadioButtonId();
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						permissionsemployeeforedit.setmAllowExitT(radioButtonforedit
								.getText().toString().trim());
						checkkforedit = mVendorPayouts.getCheckedRadioButtonId();
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						permissionsemployeeforedit.setmVendorPayoutsT(radioButtonforedit
								.getText().toString().trim());
						checkkforedit = mDeleteItems.getCheckedRadioButtonId();
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						permissionsemployeeforedit.setmDeleteItemsT(radioButtonforedit
								.getText().toString().trim());
						checkkforedit = mVoidInvoices.getCheckedRadioButtonId();
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						permissionsemployeeforedit.setmVoidInvoicesT(radioButtonforedit
								.getText().toString().trim());
						checkkforedit = mTransactions.getCheckedRadioButtonId();
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						permissionsemployeeforedit.setmTransactionsT(radioButtonforedit
								.getText().toString().trim());
						checkkforedit = mHoldPrint.getCheckedRadioButtonId();
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						permissionsemployeeforedit.setmHoldPrintT(radioButtonforedit
								.getText().toString().trim());
						checkkforedit = mCreditcards.getCheckedRadioButtonId();
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						permissionsemployeeforedit.setmCreditcardsT(radioButtonforedit
								.getText().toString().trim());
						checkkforedit = mEndCash.getCheckedRadioButtonId();
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						permissionsemployeeforedit.setmEndCashT(radioButtonforedit.getText()
								.toString().trim());
						alertDialog12.dismiss();

					}
				});
				permisstioncancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						alertDialog12.dismiss();

					}
				});

				alertDialog12.setView(layout2);
				alertDialog12.show();
			}
		});
		personalinfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final AlertDialog alertDialog12 = new AlertDialog.Builder(
						EmployeeActivity.this).create();
				LayoutInflater mInflater2 = LayoutInflater
						.from(EmployeeActivity.this);
				View layout2 = mInflater2.inflate(R.layout.personal_employee,
						null);
				final EditText emp_name, emp_id, emp_email, emp_phone, emp_birth, emp_address, emp_city, emp_country, emp_state, emp_postal;

				emp_name = (EditText) layout2.findViewById(R.id.emp_name);
				emp_id = (EditText) layout2.findViewById(R.id.emp_id);
				emp_email = (EditText) layout2.findViewById(R.id.emp_email);
				emp_phone = (EditText) layout2.findViewById(R.id.emp_phone);
				emp_birth = (EditText) layout2.findViewById(R.id.emp_birth);
				emp_address = (EditText) layout2.findViewById(R.id.emp_address);
				emp_city = (EditText) layout2.findViewById(R.id.emp_city);
				emp_country = (EditText) layout2.findViewById(R.id.emp_country);
				emp_state = (EditText) layout2.findViewById(R.id.emp_state);
				emp_postal = (EditText) layout2
						.findViewById(R.id.emp_postalcode);

				emp_name.setText(data12[5]);
				emp_id.setText(data12[7]);
				emp_email.setText(data12[8]);
				emp_phone.setText(data12[9]);
				emp_birth.setText(data12[10]);
				emp_address.setText(data12[11]);
				emp_city.setText(data12[12]);
				emp_country.setText(data12[13]);
				emp_state.setText(data12[14]);
				emp_postal.setText(data12[15]);

				Button personal_save = (Button) layout2
						.findViewById(R.id.personal_save);
				Button personal_cancel = (Button) layout2
						.findViewById(R.id.personal_cancel);
				personal_save.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						personalemployeeforedit.setEmp_name(emp_name.getText()
								.toString().trim());
						personalemployeeforedit.setEmp_id(emp_id.getText().toString()
								.trim().trim());
						personalemployeeforedit.setEmp_email(emp_email.getText()
								.toString().trim());
						personalemployeeforedit.setEmp_phone(emp_phone.getText()
								.toString().trim());
						personalemployeeforedit.setEmp_birth(emp_birth.getText()
								.toString().trim());
						personalemployeeforedit.setEmp_address(emp_address.getText()
								.toString().trim());
						personalemployeeforedit.setEmp_city(emp_city.getText()
								.toString().trim());
						personalemployeeforedit.setEmp_country(emp_country.getText()
								.toString().trim());
						personalemployeeforedit.setEmp_state(emp_state.getText()
								.toString().trim());
						personalemployeeforedit.setEmp_postal(emp_postal.getText()
								.toString().trim());

						alertDialog12.dismiss();

					}
				});
				personal_cancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						alertDialog12.dismiss();

					}
				});
				alertDialog12.setView(layout2);
				alertDialog12.show();
			}
		});
		storeinfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				final AlertDialog alertDialog12 = new AlertDialog.Builder(
						EmployeeActivity.this).create();
				LayoutInflater mInflater2 = LayoutInflater
						.from(EmployeeActivity.this);
				View layout2 = mInflater2.inflate(R.layout.storeinfo_employee,
						null);
				final ListView storelist = (ListView) layout2
						.findViewById(R.id.storelist);
				Button store_save = (Button) layout2
						.findViewById(R.id.savestore);
				Button store_cancel = (Button) layout2
						.findViewById(R.id.cancelstore);
				ArrayList<String> storearray = new ArrayList<String>();
				final ArrayList<String> storearrayid = new ArrayList<String>();
				if (Parameters.isTableExists(dbforloginlogoutReadEmployee, DatabaseForDemo.STORE_TABLE)) {

					Cursor mCursor = dbforloginlogoutReadEmployee.rawQuery("select * from "
							+ DatabaseForDemo.STORE_TABLE, null);
					System.out.println(mCursor);
					if (mCursor != null) {
						if (mCursor.moveToFirst()) {
							do {
								String storename = mCursor.getString(mCursor
										.getColumnIndex(DatabaseForDemo.STORE_NAME));
								String storeid = mCursor.getString(mCursor
										.getColumnIndex(DatabaseForDemo.STORE_ID));
								storearray.add(storename);
								storearrayid.add(storeid);
							} while (mCursor.moveToNext());
						}
					}
					mCursor.close();
				}
				
				final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						EmployeeActivity.this,
						android.R.layout.simple_list_item_multiple_choice,
						storearray);
				storelist.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
				storelist.setAdapter(adapter);
				
				String[] idss = store_idforedit.split(",");
				for (String s : idss) {
					System.out.println(s);
					Log.e("storearrayid.indexOf(s)",""+storearrayid.indexOf(s));
						if(storearrayid.indexOf(s)>-1){
						storelist.setItemChecked(storearrayid.indexOf(s), true);
						}
				}
				String[] storenames = store_nameforedit.split(",");
				for (String s : storenames) {
					System.out.println(s);
				}

				store_save.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						item_store_idforedit = "";
						item_store_nameforedit = "";
						SparseBooleanArray checked = storelist
								.getCheckedItemPositions();
						ArrayList<String> selectedItems = new ArrayList<String>();
						for (int i = 0; i < checked.size(); i++) {
							// Item position in adapter
							int position = checked.keyAt(i);
							// Add sport if it is checked i.e.) == TRUE!
							if (checked.valueAt(i)) {
								selectedItems.add(adapter.getItem(position));
								item_store_idforedit += storearrayid.get(position)
										+ ",";
								item_store_nameforedit += adapter.getItem(position)
										+ ",";
							}
						}

						String[] outputStrArr = new String[selectedItems.size()];

						for (int i = 0; i < selectedItems.size(); i++) {
							outputStrArr[i] = selectedItems.get(i);
						}
						alertDialog12.dismiss();
					}
				});
				store_cancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						alertDialog12.dismiss();

					}
				});
				alertDialog12.setView(layout2);
				alertDialog12.show();
			}
		});
		payrollinfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final AlertDialog alertDialog12 = new AlertDialog.Builder(
						EmployeeActivity.this).create();
				LayoutInflater mInflater2 = LayoutInflater
						.from(EmployeeActivity.this);
				View layout2 = mInflater2.inflate(R.layout.payroll_employee,
						null);
				Button payrollsave = (Button) layout2
						.findViewById(R.id.payrollsave);
				Button payrollcancel = (Button) layout2
						.findViewById(R.id.payrollcancel);
				final EditText federal, amount, statea, stateAmount, credits;
				final Spinner filingstatus, exempt;
				final CheckBox excludeCheck;
				excludeCheck = (CheckBox) layout2
						.findViewById(R.id.excludeCheck);
				federal = (EditText) layout2.findViewById(R.id.federal);
				amount = (EditText) layout2.findViewById(R.id.amount);
				statea = (EditText) layout2.findViewById(R.id.statea);
				stateAmount = (EditText) layout2.findViewById(R.id.stateAmount);
				credits = (EditText) layout2.findViewById(R.id.credits);
				filingstatus = (Spinner) layout2
						.findViewById(R.id.fillingStatus);
				exempt = (Spinner) layout2.findViewById(R.id.exempt);

				payrollemployeeforedit.setFederal(data123[5]);
				payrollemployeeforedit.setAmount(data123[7]);
				payrollemployeeforedit.setStatea(data123[8]);
				payrollemployeeforedit.setStateAmount(data123[9]);
				payrollemployeeforedit.setCredits(data123[10]);

				payrollemployeeforedit.setExcludeCheck(data123[13]);

				federal.setText(data123[5]);
				amount.setText(data123[7]);
				statea.setText(data123[8]);
				stateAmount.setText(data123[9]);
				credits.setText(data123[10]);
				if (data123[13].equals("true"))
					excludeCheck.setChecked(true);

				payrollsave.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						payrollemployeeforedit.setAmount(amount.getText().toString()
								.trim().trim());
						payrollemployeeforedit.setCredits(credits.getText().toString()
								.trim().trim());
						payrollemployeeforedit.setExcludeCheck(""
								+ excludeCheck.isChecked());
						// payrollemployee.setExempt(exempt.getSelectedItem().toString().trim());
						payrollemployeeforedit.setFederal(federal.getText().toString()
								.trim().trim());
						// payrollemployee.setFilingstatus(filingstatus.getSelectedItem().toString().trim());
						payrollemployeeforedit.setStatea(statea.getText().toString()
								.trim().trim());
						payrollemployeeforedit.setStateAmount(stateAmount.getText()
								.toString().trim());
						alertDialog12.dismiss();

					}
				});
				payrollcancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						alertDialog12.dismiss();

					}
				});
				alertDialog12.setView(layout2);
				alertDialog12.show();
			}
		});
		mAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				deleteEmployeeTablesforedit(DatabaseForDemo.EMPLOYEE_TABLE,
						string);
				deleteEmployeeTablesforedit(DatabaseForDemo.EMP_PERMISSIONS_TABLE,
						string);

				deleteEmployeeTablesforedit(DatabaseForDemo.EMP_PERSONAL_TABLE, string);
				deleteEmployeeTablesforedit(DatabaseForDemo.EMP_PAYROLL_TABLE, string);
				deleteEmployeeTablesforedit(DatabaseForDemo.EMP_STORE_TABLE, string);

				modifytimeforedit = Parameters.currentTime();
				Employee employee = new Employee();

				employee.setmDepartment(mDepartment.getText().toString().trim());
				employee.setmEmployeeId(mEmployeeId.getText().toString().trim());
				if(mPassword.getText().toString().trim().length()>2){
					employee.setmPassword(Parameters.MD5(mPassword.getText().toString().trim()));
				}else{
					employee.setmPassword(passwordText);
				}
				employee.setmDisplayName(mDisplayName.getText().toString()
						.trim());
				employee.setmCardswipeId(mCardswipeId.getText().toString()
						.trim());
				employee.setmCustomer(mCustomer.getText().toString().trim());
				employee.setmHoursWage(mHourlyWage.getText().toString().trim());
				employee.setEmployee_cc_tips("" + cc_tips.isChecked());
				employee.setEmployee_admin_card("" + admin_card.isChecked());
				employee.setEmployee_disable("" + disable.isChecked());
				if( admin_card.isChecked()){
					permissionsAll("Enable");
			}
				
				insertEmployeeDetailsforedit(employee);
				insertPeyrollDetailsforedit();
				storeInfoDetailsforedit();
				insertPermisstionsDetailsforedit();
				insertPersonalDetailsforedit();
				id_data.clear();
				desc_data.clear();
				total_id_data.clear();
				total_desc_data.clear();
				//onCreate(savedInstanceState);
				listUpdate();
				permissionsAll("Disable");
				alertDialog1.dismiss();

			}

		});

		mCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				alertDialog1.dismiss();
			}
		});
		alertDialog1.setView(layout);

		// Showing Alert Message
		alertDialog1.show();
	}
	
	public void deleteEmployeeTablesforedit(final String tablename, String value) {

		final ArrayList<String> getlist = new ArrayList<String>();
		String selectQueryforinstantpo = "SELECT  * FROM "
				+ tablename + " where " + DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID + "=\""
				+ value + "\"";

		Cursor mCursorforvendor1 = dbforloginlogoutReadEmployee.rawQuery(
				selectQueryforinstantpo, null);
		if (mCursorforvendor1 != null) {
			if (mCursorforvendor1.moveToFirst()) {
				do {
					String uniqueid = mCursorforvendor1
							.getString(mCursorforvendor1
									.getColumnIndex(DatabaseForDemo.UNIQUE_ID));
					getlist.add(uniqueid);
				} while (mCursorforvendor1.moveToNext());
			}
		}
		mCursorforvendor1.close();
		String where = DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID + "=?";
		dbforloginlogoutWriteEmployee.delete(tablename, where, new String[] { value });

		try {
			JSONArray unique_ids = new JSONArray();
			for (int i = 0; i < getlist.size(); i++) {
				unique_ids.put(i, getlist.get(i));
				datavalforedit = unique_ids.toString();
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		if(Parameters.OriginalUrl.equals("")){
			System.out.println("there is no server url val");
		}else{
		boolean isnet = Parameters.isNetworkAvailable(getApplicationContext());
		if (isnet) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					JsonPostMethod jsonpost = new JsonPostMethod();
					String response = jsonpost.postmethodfordirectdelete(
							"admin", "abcdefg", tablename,
							Parameters.currentTime(), Parameters.currentTime(),
							datavalforedit);
					String servertiem = null;
					try {
						JSONObject obj = new JSONObject(response);
						JSONObject responseobj = obj.getJSONObject("response");
						servertiem = responseobj.getString("server-time");
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					String select = "select *from "
							+ DatabaseForDemo.MISCELLANEOUS_TABLE;
					Cursor cursor = dbforloginlogoutReadEmployee.rawQuery(select, null);
					if (cursor.getCount() > 0) {
						dbforloginlogoutWriteEmployee.execSQL("update "
								+ DatabaseForDemo.MISCELLANEOUS_TABLE + " set "
								+ DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL
								+ "=\"" + servertiem + "\"");
						cursor.close();
					} else {
						cursor.close();
						ContentValues contentValues1 = new ContentValues();
						contentValues1.put(DatabaseForDemo.MISCEL_STORE,
								"store1");
						contentValues1
								.put(DatabaseForDemo.MISCEL_PAGEURL,
										"");
						contentValues1.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL,
								Parameters.currentTime());
						contentValues1.put(
								DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL,
								Parameters.currentTime());
						dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.MISCELLANEOUS_TABLE, null,
								contentValues1);
					}
					datavalforedit = "";
				}
			}).start();
		} else {
			ContentValues contentValues1 = new ContentValues();
			contentValues1.put(DatabaseForDemo.QUERY_TYPE, "delete");
			contentValues1.put(DatabaseForDemo.PENDING_USER_ID, Parameters.userid);
			contentValues1.put(DatabaseForDemo.PAGE_URL, "deleteinfo.php");
			contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING, tablename);
			contentValues1.put(DatabaseForDemo.CURRENT_TIME_PENDING,
					Parameters.currentTime());
			contentValues1.put(DatabaseForDemo.PARAMETERS, datavalforedit);
			dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null,
					contentValues1);
			datavalforedit = "";
		}
		}
	}
	private void storeInfoDetailsforedit() {

		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseForDemo.UNIQUE_ID, randomNumberforedit);
		contentValues.put(DatabaseForDemo.CREATED_DATE, cuurentTimeforedit);
		contentValues.put(DatabaseForDemo.MODIFIED_DATE, modifytimeforedit);
		contentValues.put(DatabaseForDemo.MODIFIED_IN, "Local");
		contentValues.put(DatabaseForDemo.EMP_STORE_ID, item_store_idforedit);
		contentValues.put(DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID, emp_emp_Idforedit);
		contentValues.put(DatabaseForDemo.EMP_STORE_NAME, item_store_nameforedit);
		dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.EMP_STORE_TABLE, null,
				contentValues);
		contentValues.clear();
	//	if (Parameters.mfindServer_url) {
			try {
				JSONObject data = new JSONObject();
				JSONObject jsonobj = new JSONObject();

				jsonobj.put(DatabaseForDemo.UNIQUE_ID, randomNumberforedit);
				jsonobj.put(DatabaseForDemo.CREATED_DATE, cuurentTimeforedit);
				jsonobj.put(DatabaseForDemo.MODIFIED_DATE, modifytimeforedit);
				jsonobj.put(DatabaseForDemo.MODIFIED_IN, "Local");
				jsonobj.put(DatabaseForDemo.EMP_STORE_ID, item_store_idforedit);
				jsonobj.put(DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID, emp_emp_Idforedit);
				jsonobj.put(DatabaseForDemo.EMP_STORE_NAME, item_store_nameforedit);

				JSONArray fields = new JSONArray();
				fields.put(0, jsonobj);
				data.put("fields", fields);
				datavalforedit = data.toString();
				System.out.println("data val is:" + datavalforedit);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

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
								"abcdefg", DatabaseForDemo.EMP_STORE_TABLE,
								Parameters.currentTime(),
								Parameters.currentTime(), datavalforedit, "");
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

								dbforloginlogoutWriteEmployee.execSQL(deletequery);
								dbforloginlogoutWriteEmployee.execSQL(insertquery);
								System.out.println("queries executed" + ii);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						String select = "select *from "
								+ DatabaseForDemo.MISCELLANEOUS_TABLE;
						Cursor cursor = dbforloginlogoutReadEmployee.rawQuery(select, null);
						if (cursor.getCount() > 0) {
							dbforloginlogoutWriteEmployee.execSQL("update "
									+ DatabaseForDemo.MISCELLANEOUS_TABLE
									+ " set "
									+ DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL
									+ "=\"" + servertiem + "\"");
							cursor.close();
						} else {
							cursor.close();
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
							dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.MISCELLANEOUS_TABLE,
									null, contentValues1);
						}
						datavalforedit = "";
					}
				}).start();
			} else {
				ContentValues contentValues1 = new ContentValues();
				contentValues1.put(DatabaseForDemo.QUERY_TYPE, "insert");
				contentValues1.put(DatabaseForDemo.PENDING_USER_ID, Parameters.userid);
				contentValues1.put(DatabaseForDemo.PAGE_URL, "saveinfo.php");
				contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING,
						DatabaseForDemo.EMP_STORE_TABLE);
				contentValues1.put(DatabaseForDemo.CURRENT_TIME_PENDING,
						Parameters.currentTime());
				contentValues1.put(DatabaseForDemo.PARAMETERS, datavalforedit);
				dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null,
						contentValues1);
				datavalforedit = "";
			}
			}

		//}
	}

	
	@Override
	public void onViewClicked(View v, final String string) {
		// TODO Auto-generated method stub

		// TODO Auto-generated method stub
		final AlertDialog alertDialog1 = new AlertDialog.Builder(
				EmployeeActivity.this,android.R.style.Theme_Translucent_NoTitleBar).create();
		ImageView mImageView;
		final EditText mDepartment;
		final EditText mEmployeeId, mPassword, mDisplayName;
		final EditText mCardswipeId, mCustomer, mHourlyWage;
		final CheckBox cc_tips, disable, admin_card;
		final Button permitions, personalinfo, payrollinfo, mAdd, mCancel, storeinfo;

		LayoutInflater mInflater = LayoutInflater.from(EmployeeActivity.this);
		View layout = mInflater.inflate(R.layout.add_employee, null);
		mDepartment = (EditText) layout
				.findViewById(R.id.adminEmployeeDepartmentEdit);
		mEmployeeId = (EditText) layout.findViewById(R.id.editText3);
		mPassword = (EditText) layout
				.findViewById(R.id.adminEmployeePasswordEdit);
		mDisplayName = (EditText) layout
				.findViewById(R.id.adminEmployeeDisplayEdit);
		mCardswipeId = (EditText) layout.findViewById(R.id.editText5);
		mCustomer = (EditText) layout
				.findViewById(R.id.adminEmployeeCustomerEdit);
		mHourlyWage = (EditText) layout
				.findViewById(R.id.adminEmployeeHourlyWageEdit);
		cc_tips = (CheckBox) layout.findViewById(R.id.checkBox2);
		disable = (CheckBox) layout.findViewById(R.id.checkBox1);
		admin_card = (CheckBox) layout.findViewById(R.id.checkBox3);
		mAdd = (Button) layout.findViewById(R.id.employeesave);

		mCancel = (Button) layout.findViewById(R.id.employeecancel);
		mImageView = (ImageView) layout.findViewById(R.id.imageView1);
		permitions = (Button) layout.findViewById(R.id.permitions);
		personalinfo = (Button) layout.findViewById(R.id.personalinfo);
		payrollinfo = (Button) layout.findViewById(R.id.payroll);
		storeinfo = (Button) layout.findViewById(R.id.storeinfo);
		Cursor mCursor = dbforloginlogoutReadEmployee.rawQuery("select * from "
				+ DatabaseForDemo.EMPLOYEE_TABLE + " where "
				+ DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID + "=\"" + string + "\"",
				null);
		System.out.println(mCursor);
		if (mCursor != null) {
			int count = mCursor.getColumnCount();
			System.out.println(count);
			if (mCursor.moveToFirst()) {
				do {
					String[] data = new String[count];
					for (int i = 1; i < count; i++) {
						System.out.println(mCursor.getColumnCount());
						data[i] = mCursor.getString(i);
						Log.e("i number",""+i);
						System.out.println(mCursor.getString(i));
					}

					mDepartment.setText(data[5]);
					mEmployeeId.setText(data[6]);
					//mPassword.setText(data[7]);
					mDisplayName.setText(data[8]);
					mCardswipeId.setText(data[9]);
					mCustomer.setText(data[10]);
					mHourlyWage.setText(data[11]);
					if (data[12].equals("true"))
						disable.setChecked(true);
					if (data[13].equals("true"))
						cc_tips.setChecked(true);
					if (data[14].equals("true"))
						admin_card.setChecked(true);

				} while (mCursor.moveToNext());

			}
		}

		mCursor.close();
		Cursor mCursor1 = dbforloginlogoutReadEmployee.rawQuery("select * from "
				+ DatabaseForDemo.EMP_PERMISSIONS_TABLE + " where "
				+ DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID + "=\"" + string + "\"",
				null);
		System.out.println(mCursor1);
		if (mCursor1 != null) {
			int count = mCursor1.getColumnCount();
			System.out.println(count);
			if (mCursor1.moveToFirst()) {
				do {
					data1 = new String[count];
					for (int i = 1; i < count; i++) {
						System.out.println(mCursor1.getCount());
						System.out.println(mCursor1.getString(i));
						data1[i] = mCursor1.getString(i);
					}
					permissionsemployeeforedit.setmInventoryT(data1[5]);
					permissionsemployeeforedit.setmCustomersT(data1[7]);
					permissionsemployeeforedit.setmReportsT(data1[8]);
					permissionsemployeeforedit.setmDiscountsT(data1[9]);
					permissionsemployeeforedit.setmSettingsT(data1[10]);
					permissionsemployeeforedit.setmPriceChangesT(data1[11]);
					permissionsemployeeforedit.setmAllowExitT(data1[12]);
					permissionsemployeeforedit.setmVendorPayoutsT(data1[13]);
					permissionsemployeeforedit.setmDeleteItemsT(data1[14]);
					permissionsemployeeforedit.setmVoidInvoicesT(data1[15]);
					permissionsemployeeforedit.setmTransactionsT(data1[16]);
					permissionsemployeeforedit.setmHoldPrintT(data1[17]);
					permissionsemployeeforedit.setmCreditcardsT(data1[18]);
					permissionsemployeeforedit.setmEndCashT(data1[19]);

				} while (mCursor1.moveToNext());

			}
		}

		mCursor1.close();

		Cursor mCursor12 = dbforloginlogoutReadEmployee.rawQuery("select * from "
				+ DatabaseForDemo.EMP_PERSONAL_TABLE + " where "
				+ DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID + "=\"" + string + "\"",
				null);
		System.out.println(mCursor12);
		if (mCursor12 != null) {
			int count = mCursor12.getColumnCount();
			System.out.println(count);
			if (mCursor12.moveToFirst()) {
				do {
					data12 = new String[count];
					for (int i = 1; i < count; i++) {
						System.out.println(mCursor12.getCount());
						System.out.println(mCursor12.getString(i));
						data12[i] = mCursor12.getString(i);
					}

					personalemployeeforedit.setEmp_name(data12[5]);
					personalemployeeforedit.setEmp_id(data12[7]);
					personalemployeeforedit.setEmp_email(data12[8]);
					personalemployeeforedit.setEmp_phone(data12[9]);
					personalemployeeforedit.setEmp_birth(data12[10]);
					personalemployeeforedit.setEmp_address(data12[11]);
					personalemployeeforedit.setEmp_city(data12[12]);
					personalemployeeforedit.setEmp_country(data12[13]);
					personalemployeeforedit.setEmp_state(data12[14]);
					personalemployeeforedit.setEmp_postal(data12[15]);
				} while (mCursor12.moveToNext());

			}
		}

		mCursor12.close();

		Cursor mCursor123 = dbforloginlogoutReadEmployee.rawQuery("select * from "
				+ DatabaseForDemo.EMP_PAYROLL_TABLE + " where "
				+ DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID + "=\"" + string + "\"",
				null);
		System.out.println(mCursor123);
		if (mCursor123 != null) {
			int count = mCursor123.getColumnCount();
			System.out.println(count);
			if (mCursor123.moveToFirst()) {
				do {
					data123 = new String[count];
					for (int i = 1; i < count; i++) {
						System.out.println(mCursor123.getCount());
						System.out.println(mCursor123.getString(i));
						data123[i] = "" + mCursor123.getString(i);
					}

				} while (mCursor123.moveToNext());

			}

			payrollemployeeforedit.setFederal(data123[5]);
			payrollemployeeforedit.setAmount(data123[7]);
			payrollemployeeforedit.setStatea(data123[8]);
			payrollemployeeforedit.setStateAmount(data123[9]);
			payrollemployeeforedit.setCredits(data123[10]);
			payrollemployeeforedit.setExcludeCheck(data123[13]);
			// payrollemployee.setExempt(exempt.getSelectedItem().toString().trim());
			// payrollemployee.setFilingstatus(filingstatus.getSelectedItem().toString().trim());

		}

		mCursor123.close();
		Cursor mCursor1234 = dbforloginlogoutReadEmployee.rawQuery("select * from "
				+ DatabaseForDemo.EMP_STORE_TABLE + " where "
				+ DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID + "=\"" + string + "\"",
				null);
		System.out.println(mCursor1234);
		if (mCursor1234 != null) {
			int count = mCursor1234.getColumnCount();
			System.out.println(count);
			String[] data1234 = new String[count];
			if (mCursor1234.moveToFirst()) {
				do {

					for (int i = 1; i < count; i++) {
						data1234[i] = "" + mCursor1234.getString(i);
						System.out.println(mCursor1234.getString(i));
					}

				} while (mCursor1234.moveToNext());

			}
			store_nameforedit = "" + data1234[3];
			store_idforedit = "" + data1234[2];
			System.out.println(store_nameforedit);
			System.out.println(store_idforedit);
		}

		mCursor1234.close();
		permitions.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.v("<>", "ffgdfg");
				final AlertDialog alertDialog12 = new AlertDialog.Builder(
						EmployeeActivity.this,android.R.style.Theme_Translucent_NoTitleBar).create();
				LayoutInflater mInflater2 = LayoutInflater
						.from(EmployeeActivity.this);
				final View layout2 = mInflater2.inflate(
						R.layout.permitions_employee, null);
				Button permitionssave = (Button) layout2
						.findViewById(R.id.permitionssave);
				Button permisstioncancel = (Button) layout2
						.findViewById(R.id.permisstioncancel);
				final RadioGroup mInventory = (RadioGroup) layout2
						.findViewById(R.id.nventory);
				final RadioGroup mCustomers = (RadioGroup) layout2
						.findViewById(R.id.ustomers);
				final RadioGroup mReports = (RadioGroup) layout2
						.findViewById(R.id.eports);
				final RadioGroup mDiscounts = (RadioGroup) layout2
						.findViewById(R.id.iscounts);
				final RadioGroup mSettings = (RadioGroup) layout2
						.findViewById(R.id.ettings);
				final RadioGroup mPriceChanges = (RadioGroup) layout2
						.findViewById(R.id.riceChanges);
				final RadioGroup mAllowExit = (RadioGroup) layout2
						.findViewById(R.id.llowExit);
				final RadioGroup mVendorPayouts = (RadioGroup) layout2
						.findViewById(R.id.endorPayouts);
				final RadioGroup mDeleteItems = (RadioGroup) layout2
						.findViewById(R.id.eleteItems);
				final RadioGroup mVoidInvoices = (RadioGroup) layout2
						.findViewById(R.id.oidInvoices);
				final RadioGroup mTransactions = (RadioGroup) layout2
						.findViewById(R.id.ransactions);
				final RadioGroup mHoldPrint = (RadioGroup) layout2
						.findViewById(R.id.oldPrint);
				final RadioGroup mCreditcards = (RadioGroup) layout2
						.findViewById(R.id.reditcards);
				final RadioGroup mEndCash = (RadioGroup) layout2
						.findViewById(R.id.ndCash);

				RadioButton a0 = (RadioButton) layout2
						.findViewById(R.id.radio0a);
				RadioButton a1 = (RadioButton) layout2
						.findViewById(R.id.radio1a);
				RadioButton b0 = (RadioButton) layout2
						.findViewById(R.id.radio0b);
				RadioButton b1 = (RadioButton) layout2
						.findViewById(R.id.radio1b);
				RadioButton c0 = (RadioButton) layout2
						.findViewById(R.id.radio0c);
				RadioButton c1 = (RadioButton) layout2
						.findViewById(R.id.radio1c);
				RadioButton d0 = (RadioButton) layout2
						.findViewById(R.id.radio0d);
				RadioButton d1 = (RadioButton) layout2
						.findViewById(R.id.radio1d);
				RadioButton e0 = (RadioButton) layout2
						.findViewById(R.id.radio0e);
				RadioButton e1 = (RadioButton) layout2
						.findViewById(R.id.radio1e);
				RadioButton f0 = (RadioButton) layout2
						.findViewById(R.id.radio0f);
				RadioButton f1 = (RadioButton) layout2
						.findViewById(R.id.radio1f);
				RadioButton g0 = (RadioButton) layout2
						.findViewById(R.id.radio0g);
				RadioButton g1 = (RadioButton) layout2
						.findViewById(R.id.radio1g);
				RadioButton h0 = (RadioButton) layout2
						.findViewById(R.id.radio0h);
				RadioButton h1 = (RadioButton) layout2
						.findViewById(R.id.radio1h);
				RadioButton i0 = (RadioButton) layout2
						.findViewById(R.id.radio0i);
				RadioButton i1 = (RadioButton) layout2
						.findViewById(R.id.radio1i);
				RadioButton j0 = (RadioButton) layout2
						.findViewById(R.id.radio0j);
				RadioButton j1 = (RadioButton) layout2
						.findViewById(R.id.radio1j);
				RadioButton k0 = (RadioButton) layout2
						.findViewById(R.id.radio0k);
				RadioButton k1 = (RadioButton) layout2
						.findViewById(R.id.radio1k);
				RadioButton l0 = (RadioButton) layout2
						.findViewById(R.id.radio0l);
				RadioButton l1 = (RadioButton) layout2
						.findViewById(R.id.radio1l);
				RadioButton m0 = (RadioButton) layout2
						.findViewById(R.id.radio0m);
				RadioButton m1 = (RadioButton) layout2
						.findViewById(R.id.radio1m);
				RadioButton n0 = (RadioButton) layout2
						.findViewById(R.id.radio0n);
				RadioButton n1 = (RadioButton) layout2
						.findViewById(R.id.radio1n);

				if (data1[5].equals("Enable")) {
					a0.setChecked(true);
				} else {
					a1.setChecked(true);
				}
				if (data1[7].equals("Enable")) {
					b0.setChecked(true);
				} else {
					b1.setChecked(true);
				}
				if (data1[8].equals("Enable")) {
					c0.setChecked(true);
				} else {
					c1.setChecked(true);
				}
				if (data1[9].equals("Enable")) {
					d0.setChecked(true);
				} else {
					d1.setChecked(true);
				}
				if (data1[10].equals("Enable")) {
					e0.setChecked(true);
				} else {
					e1.setChecked(true);
				}
				if (data1[11].equals("Enable")) {
					f0.setChecked(true);
				} else {
					f1.setChecked(true);
				}
				if (data1[12].equals("Enable")) {
					g0.setChecked(true);
				} else {
					g1.setChecked(true);
				}
				if (data1[13].equals("Enable")) {
					h0.setChecked(true);
				} else {
					h1.setChecked(true);
				}
				if (data1[14].equals("Enable")) {
					i0.setChecked(true);
				} else {
					i1.setChecked(true);
				}
				if (data1[15].equals("Enable")) {
					j0.setChecked(true);
				} else {
					j1.setChecked(true);
				}
				if (data1[16].equals("Enable")) {
					k0.setChecked(true);
				} else {
					k1.setChecked(true);
				}
				if (data1[17].equals("Enable")) {
					l0.setChecked(true);
				} else {
					l1.setChecked(true);
				}
				if (data1[18].equals("Enable")) {
					m0.setChecked(true);
				} else {
					m1.setChecked(true);
				}
				if (data1[19].equals("Enable")) {
					n0.setChecked(true);
				} else {
					n1.setChecked(true);
				}

				permitionssave.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						checkkforedit = mInventory.getCheckedRadioButtonId();
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						checkkforedit = mCustomers.getCheckedRadioButtonId();
						permissionsemployeeforedit.setmInventoryT(radioButtonforedit
								.getText().toString().trim());
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);

						checkkforedit = mReports.getCheckedRadioButtonId();
						permissionsemployeeforedit.setmCustomersT(radioButtonforedit
								.getText().toString().trim());
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						permissionsemployeeforedit.setmReportsT(radioButtonforedit.getText()
								.toString().trim());
						checkkforedit = mDiscounts.getCheckedRadioButtonId();
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						permissionsemployeeforedit.setmDiscountsT(radioButtonforedit
								.getText().toString().trim());
						checkkforedit = mSettings.getCheckedRadioButtonId();
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						permissionsemployeeforedit.setmSettingsT(radioButtonforedit.getText()
								.toString().trim());
						checkkforedit = mPriceChanges.getCheckedRadioButtonId();
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						permissionsemployeeforedit.setmPriceChangesT(radioButtonforedit
								.getText().toString().trim());
						checkkforedit = mAllowExit.getCheckedRadioButtonId();
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						permissionsemployeeforedit.setmAllowExitT(radioButtonforedit
								.getText().toString().trim());
						checkkforedit = mVendorPayouts.getCheckedRadioButtonId();
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						permissionsemployeeforedit.setmVendorPayoutsT(radioButtonforedit
								.getText().toString().trim());
						checkkforedit = mDeleteItems.getCheckedRadioButtonId();
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						permissionsemployeeforedit.setmDeleteItemsT(radioButtonforedit
								.getText().toString().trim());
						checkkforedit = mVoidInvoices.getCheckedRadioButtonId();
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						permissionsemployeeforedit.setmVoidInvoicesT(radioButtonforedit
								.getText().toString().trim());
						checkkforedit = mTransactions.getCheckedRadioButtonId();
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						permissionsemployeeforedit.setmTransactionsT(radioButtonforedit
								.getText().toString().trim());
						checkkforedit = mHoldPrint.getCheckedRadioButtonId();
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						permissionsemployeeforedit.setmHoldPrintT(radioButtonforedit
								.getText().toString().trim());
						checkkforedit = mCreditcards.getCheckedRadioButtonId();
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						permissionsemployeeforedit.setmCreditcardsT(radioButtonforedit
								.getText().toString().trim());
						checkkforedit = mEndCash.getCheckedRadioButtonId();
						radioButtonforedit = (RadioButton) layout2
								.findViewById(checkkforedit);
						permissionsemployeeforedit.setmEndCashT(radioButtonforedit.getText()
								.toString().trim());
						alertDialog12.dismiss();

					}
				});
				permisstioncancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						alertDialog12.dismiss();

					}
				});

				alertDialog12.setView(layout2);
				alertDialog12.show();
			}
		});
		personalinfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final AlertDialog alertDialog12 = new AlertDialog.Builder(
						EmployeeActivity.this).create();
				LayoutInflater mInflater2 = LayoutInflater
						.from(EmployeeActivity.this);
				View layout2 = mInflater2.inflate(R.layout.personal_employee,
						null);
				final EditText emp_name, emp_id, emp_email, emp_phone, emp_birth, emp_address, emp_city, emp_country, emp_state, emp_postal;

				emp_name = (EditText) layout2.findViewById(R.id.emp_name);
				emp_id = (EditText) layout2.findViewById(R.id.emp_id);
				emp_email = (EditText) layout2.findViewById(R.id.emp_email);
				emp_phone = (EditText) layout2.findViewById(R.id.emp_phone);
				emp_birth = (EditText) layout2.findViewById(R.id.emp_birth);
				emp_address = (EditText) layout2.findViewById(R.id.emp_address);
				emp_city = (EditText) layout2.findViewById(R.id.emp_city);
				emp_country = (EditText) layout2.findViewById(R.id.emp_country);
				emp_state = (EditText) layout2.findViewById(R.id.emp_state);
				emp_postal = (EditText) layout2
						.findViewById(R.id.emp_postalcode);

				emp_name.setText(data12[5]);
				emp_id.setText(data12[7]);
				emp_email.setText(data12[8]);
				emp_phone.setText(data12[9]);
				emp_birth.setText(data12[10]);
				emp_address.setText(data12[11]);
				emp_city.setText(data12[12]);
				emp_country.setText(data12[13]);
				emp_state.setText(data12[14]);
				emp_postal.setText(data12[15]);

				Button personal_save = (Button) layout2
						.findViewById(R.id.personal_save);
				Button personal_cancel = (Button) layout2
						.findViewById(R.id.personal_cancel);
				personal_save.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						personalemployeeforedit.setEmp_name(emp_name.getText()
								.toString().trim());
						personalemployeeforedit.setEmp_id(emp_id.getText().toString()
								.trim().trim());
						personalemployeeforedit.setEmp_email(emp_email.getText()
								.toString().trim());
						personalemployeeforedit.setEmp_phone(emp_phone.getText()
								.toString().trim());
						personalemployeeforedit.setEmp_birth(emp_birth.getText()
								.toString().trim());
						personalemployeeforedit.setEmp_address(emp_address.getText()
								.toString().trim());
						personalemployeeforedit.setEmp_city(emp_city.getText()
								.toString().trim());
						personalemployeeforedit.setEmp_country(emp_country.getText()
								.toString().trim());
						personalemployeeforedit.setEmp_state(emp_state.getText()
								.toString().trim());
						personalemployeeforedit.setEmp_postal(emp_postal.getText()
								.toString().trim());

						alertDialog12.dismiss();

					}
				});
				personal_cancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						alertDialog12.dismiss();

					}
				});
				alertDialog12.setView(layout2);
				alertDialog12.show();
			}
		});
		storeinfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View view) {
				final AlertDialog alertDialog12 = new AlertDialog.Builder(
						EmployeeActivity.this).create();
				LayoutInflater mInflater2 = LayoutInflater
						.from(EmployeeActivity.this);
				View layout2 = mInflater2.inflate(R.layout.storeinfo_employee,
						null);
				final ListView storelist = (ListView) layout2
						.findViewById(R.id.storelist);
				Button store_save = (Button) layout2
						.findViewById(R.id.savestore);
				Button store_cancel = (Button) layout2
						.findViewById(R.id.cancelstore);
				ArrayList<String> storearray = new ArrayList<String>();
				final ArrayList<String> storearrayid = new ArrayList<String>();
				if (Parameters.isTableExists(dbforloginlogoutReadEmployee, DatabaseForDemo.STORE_TABLE)) {

					Cursor mCursor = dbforloginlogoutReadEmployee.rawQuery("select * from "
							+ DatabaseForDemo.STORE_TABLE, null);
					System.out.println(mCursor);
					if (mCursor != null) {
						if (mCursor.moveToFirst()) {
							do {
								String storename = mCursor.getString(mCursor
										.getColumnIndex(DatabaseForDemo.STORE_NAME));
								String storeid = mCursor.getString(mCursor
										.getColumnIndex(DatabaseForDemo.STORE_ID));
								storearray.add(storename);
								storearrayid.add(storeid);
							} while (mCursor.moveToNext());
						}
					}
					mCursor.close();
				}

				final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						EmployeeActivity.this,
						android.R.layout.simple_list_item_multiple_choice,
						storearray);
				storelist.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
				storelist.setAdapter(adapter);

				String[] idss = store_idforedit.split(",");
				for (String s : idss) {
					System.out.println(s);
				}
				String[] storenames = store_nameforedit.split(",");
				for (String s : storenames) {
					System.out.println(s);
				}

				store_save.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						item_store_nameforedit = "";
						item_store_idforedit = "";
						SparseBooleanArray checked = storelist
								.getCheckedItemPositions();
						ArrayList<String> selectedItems = new ArrayList<String>();
						for (int i = 0; i < checked.size(); i++) {
							// Item position in adapter
							int position = checked.keyAt(i);
							// Add sport if it is checked i.e.) == TRUE!
							if (checked.valueAt(i)) {
								selectedItems.add(adapter.getItem(position));
								item_store_idforedit += storearrayid.get(position)
										+ ",";
								item_store_nameforedit += adapter.getItem(position)
										+ ",";
							}
						}

						String[] outputStrArr = new String[selectedItems.size()];

						for (int i = 0; i < selectedItems.size(); i++) {
							outputStrArr[i] = selectedItems.get(i);
						}
						alertDialog12.dismiss();
					}
				});
				store_cancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						alertDialog12.dismiss();

					}
				});
				alertDialog12.setView(layout2);
				alertDialog12.show();
			}
		});
		payrollinfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final AlertDialog alertDialog12 = new AlertDialog.Builder(
						EmployeeActivity.this).create();
				LayoutInflater mInflater2 = LayoutInflater
						.from(EmployeeActivity.this);
				View layout2 = mInflater2.inflate(R.layout.payroll_employee,
						null);
				Button payrollsave = (Button) layout2
						.findViewById(R.id.payrollsave);
				Button payrollcancel = (Button) layout2
						.findViewById(R.id.payrollcancel);
				final EditText federal, amount, statea, stateAmount, credits;
				final Spinner filingstatus, exempt;
				final CheckBox excludeCheck;
				excludeCheck = (CheckBox) layout2
						.findViewById(R.id.excludeCheck);
				federal = (EditText) layout2.findViewById(R.id.federal);
				amount = (EditText) layout2.findViewById(R.id.amount);
				statea = (EditText) layout2.findViewById(R.id.statea);
				stateAmount = (EditText) layout2.findViewById(R.id.stateAmount);
				credits = (EditText) layout2.findViewById(R.id.credits);
				filingstatus = (Spinner) layout2
						.findViewById(R.id.fillingStatus);
				exempt = (Spinner) layout2.findViewById(R.id.exempt);

				payrollemployeeforedit.setFederal(data123[5]);
				payrollemployeeforedit.setAmount(data123[7]);
				payrollemployeeforedit.setStatea(data123[8]);
				payrollemployeeforedit.setStateAmount(data123[9]);
				payrollemployeeforedit.setCredits(data123[10]);

				payrollemployeeforedit.setExcludeCheck(data123[13]);

				federal.setText(data123[5]);
				amount.setText(data123[7]);
				statea.setText(data123[8]);
				stateAmount.setText(data123[9]);
				credits.setText(data123[10]);
				if (data123[13].equals("true"))
					excludeCheck.setChecked(true);

				payrollsave.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub

						payrollemployeeforedit.setAmount(amount.getText().toString()
								.trim().trim());
						payrollemployeeforedit.setCredits(credits.getText().toString()
								.trim().trim());
						payrollemployeeforedit.setExcludeCheck(""
								+ excludeCheck.isChecked());
						// payrollemployee.setExempt(exempt.getSelectedItem().toString().trim());
						payrollemployeeforedit.setFederal(federal.getText().toString()
								.trim().trim());
						// payrollemployee.setFilingstatus(filingstatus.getSelectedItem().toString().trim());
						payrollemployeeforedit.setStatea(statea.getText().toString()
								.trim().trim());
						payrollemployeeforedit.setStateAmount(stateAmount.getText()
								.toString().trim());
						alertDialog12.dismiss();

					}
				});
				payrollcancel.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						alertDialog12.dismiss();

					}
				});
				alertDialog12.setView(layout2);
				alertDialog12.show();
			}
		});
		mAdd.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				cuurentTimeforedit=Parameters.currentTime();
				modifytimeforedit=Parameters.currentTime();
				randomNumberforedit=Parameters.randomValue();
				Employee employee = new Employee();

				employee.setmDepartment(mDepartment.getText().toString().trim());
				emp_emp_Idforedit = mEmployeeId.getText().toString().trim();
				if(mPassword.getText().toString().trim().length()>2){
				employee.setmPassword(Parameters.MD5(mPassword.getText().toString().trim()));
				emp_emp_Nameforedit = mDisplayName.getText().toString().trim();
				employee.setmDisplayName(mDisplayName.getText().toString()
						.trim());
				employee.setmCardswipeId(mCardswipeId.getText().toString()
						.trim());
				employee.setmCustomer(mCustomer.getText().toString().trim());
				employee.setmHoursWage(mHourlyWage.getText().toString().trim());
				employee.setEmployee_cc_tips("" + cc_tips.isChecked());
				employee.setEmployee_admin_card("" + admin_card.isChecked());
				employee.setEmployee_disable("" + disable.isChecked());
				if(emp_emp_Idforedit.length()<3){
					Toast.makeText(EmployeeActivity.this, "Enter Id above 3 characters", Toast.LENGTH_LONG).show();
				}else{
					if(emp_emp_Nameforedit.length()<3){
						Toast.makeText(EmployeeActivity.this, "Enter Name above 3 characters", Toast.LENGTH_LONG).show();
					}else{
				if (total_id_data.contains(emp_emp_Idforedit)) {
					Toast.makeText(getApplicationContext(),
							"ID Already Exists ", 2000).show();
				} else {
					if (total_desc_data.contains(emp_emp_Nameforedit)) {
						Toast.makeText(getApplicationContext(),
								"Name Already Exists ", 2000).show();
					} else {
						insertEmployeeDetailsforedit(employee);
						insertPeyrollDetailsforedit();
						insertPermisstionsDetailsforedit();
						insertPersonalDetailsforedit();
						storeInfoDetailsforedit();
						id_data.clear();
						desc_data.clear();
						total_id_data.clear();
						total_desc_data.clear();
						//onCreate(savedInstanceState);
						listUpdate();
						alertDialog1.dismiss();
					 }
				}
			}
					}
				}
			}

		});

		mCancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				alertDialog1.dismiss();
			}
		});
		alertDialog1.setView(layout);

		// Showing Alert Message
		alertDialog1.show();

	}

	
	private void insertPeyrollDetailsforedit() {

		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseForDemo.UNIQUE_ID, randomNumberforedit);
		contentValues.put(DatabaseForDemo.CREATED_DATE, cuurentTimeforedit);
		contentValues.put(DatabaseForDemo.MODIFIED_DATE, modifytimeforedit);
		contentValues.put(DatabaseForDemo.MODIFIED_IN, "Local");
		contentValues.put(DatabaseForDemo.EMP_FEDERAL,
				payrollemployeeforedit.getFederal());
		contentValues.put(DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID, emp_emp_Idforedit);
		contentValues.put(DatabaseForDemo.EMP_AMOUNT,
				payrollemployeeforedit.getAmount());
		contentValues.put(DatabaseForDemo.EMP_STATEA,
				payrollemployeeforedit.getStatea());
		contentValues.put(DatabaseForDemo.EMP_STATEAMOUNT,
				payrollemployeeforedit.getStateAmount());
		contentValues.put(DatabaseForDemo.EMP_CREDITS,
				payrollemployeeforedit.getCredits());
		// contentValues.put(SQLITEDatabase.EMP_FILLINGSTATUS,payrollemployee.getFilingstatus());
		// contentValues.put(SQLITEDatabase.EMP_EXEMPT,payrollemployee.getExempt());

		contentValues.put(DatabaseForDemo.EMP_FILLINGSTATUS, "jkjh");
		contentValues.put(DatabaseForDemo.EMP_EXEMPT, "jjj");
		contentValues.put(DatabaseForDemo.EMP_EXCLUDECHECK,
				payrollemployeeforedit.getExcludeCheck());
		dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.EMP_PAYROLL_TABLE, null,
				contentValues);
		contentValues.clear();

		//if (Parameters.mfindServer_url) {
			try {
				JSONObject data = new JSONObject();
				JSONObject jsonobj = new JSONObject();

				jsonobj.put(DatabaseForDemo.UNIQUE_ID, randomNumberforedit);
				jsonobj.put(DatabaseForDemo.CREATED_DATE, cuurentTimeforedit);
				jsonobj.put(DatabaseForDemo.MODIFIED_DATE, modifytimeforedit);
				jsonobj.put(DatabaseForDemo.MODIFIED_IN, "Local");
				jsonobj.put(DatabaseForDemo.EMP_FEDERAL,
						payrollemployeeforedit.getFederal());
				jsonobj.put(DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID, emp_emp_Idforedit);
				jsonobj.put(DatabaseForDemo.EMP_AMOUNT,
						payrollemployeeforedit.getAmount());
				jsonobj.put(DatabaseForDemo.EMP_STATEA,
						payrollemployeeforedit.getStatea());
				jsonobj.put(DatabaseForDemo.EMP_STATEAMOUNT,
						payrollemployeeforedit.getStateAmount());
				jsonobj.put(DatabaseForDemo.EMP_CREDITS,
						payrollemployeeforedit.getCredits());
				// contentValues.put(SQLITEDatabase.EMP_FILLINGSTATUS,payrollemployee.getFilingstatus());
				// contentValues.put(SQLITEDatabase.EMP_EXEMPT,payrollemployee.getExempt());

				jsonobj.put(DatabaseForDemo.EMP_FILLINGSTATUS, "jkjh");
				jsonobj.put(DatabaseForDemo.EMP_EXEMPT, "jjj");
				jsonobj.put(DatabaseForDemo.EMP_EXCLUDECHECK,
						payrollemployeeforedit.getExcludeCheck());

				JSONArray fields = new JSONArray();
				fields.put(0, jsonobj);
				data.put("fields", fields);
				datavalforedit = data.toString();
				System.out.println("data val is:" + datavalforedit);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

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
								"abcdefg", DatabaseForDemo.EMP_PAYROLL_TABLE,
								Parameters.currentTime(),
								Parameters.currentTime(), datavalforedit, "");
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

								dbforloginlogoutWriteEmployee.execSQL(deletequery);
								dbforloginlogoutWriteEmployee.execSQL(insertquery);
								System.out.println("queries executed" + ii);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						String select = "select *from "
								+ DatabaseForDemo.MISCELLANEOUS_TABLE;
						Cursor cursor = dbforloginlogoutReadEmployee.rawQuery(select, null);
						if (cursor.getCount() > 0) {
							dbforloginlogoutWriteEmployee.execSQL("update "
									+ DatabaseForDemo.MISCELLANEOUS_TABLE
									+ " set "
									+ DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL
									+ "=\"" + servertiem + "\"");
							cursor.close();
						} else {
							cursor.close();
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
							dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.MISCELLANEOUS_TABLE,
									null, contentValues1);
						}
						datavalforedit = "";
					}
				}).start();
			} else {
				ContentValues contentValues1 = new ContentValues();
				contentValues1.put(DatabaseForDemo.QUERY_TYPE, "insert");
				contentValues1.put(DatabaseForDemo.PENDING_USER_ID, Parameters.userid);
				contentValues1.put(DatabaseForDemo.PAGE_URL, "saveinfo.php");
				contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING,
						DatabaseForDemo.EMP_PAYROLL_TABLE);
				contentValues1.put(DatabaseForDemo.CURRENT_TIME_PENDING,
						Parameters.currentTime());
				contentValues1.put(DatabaseForDemo.PARAMETERS, datavalforedit);
				dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null,
						contentValues1);
				datavalforedit = "";
			}
			}
	//	}

	}

	
	private void insertPersonalDetailsforedit() {

		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseForDemo.UNIQUE_ID, randomNumberforedit);
		contentValues.put(DatabaseForDemo.CREATED_DATE, cuurentTimeforedit);
		contentValues.put(DatabaseForDemo.MODIFIED_DATE, modifytimeforedit);
		contentValues.put(DatabaseForDemo.MODIFIED_IN, "Local");
		contentValues.put(DatabaseForDemo.EMP_NAME,
				personalemployeeforedit.getEmp_name());
		contentValues.put(DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID, emp_emp_Idforedit);
		contentValues.put(DatabaseForDemo.EMP_ID, personalemployeeforedit.getEmp_id());
		contentValues.put(DatabaseForDemo.EMP_EMAIL,
				personalemployeeforedit.getEmp_email());
		contentValues.put(DatabaseForDemo.EMP_PHONE,
				personalemployeeforedit.getEmp_phone());
		contentValues.put(DatabaseForDemo.EMP_BIRTH,
				personalemployeeforedit.getEmp_birth());
		contentValues.put(DatabaseForDemo.EMP_ADDRESS,
				personalemployeeforedit.getEmp_address());
		contentValues.put(DatabaseForDemo.EMP_CITY,
				personalemployeeforedit.getEmp_city());
		contentValues.put(DatabaseForDemo.EMP_COUNTRY,
				personalemployeeforedit.getEmp_country());
		contentValues.put(DatabaseForDemo.EMP_STATE,
				personalemployeeforedit.getEmp_state());
		contentValues.put(DatabaseForDemo.EMP_POSTAL,
				personalemployeeforedit.getEmp_postal());
		dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.EMP_PERSONAL_TABLE, null,
				contentValues);
		contentValues.clear();
	//	if (Parameters.mfindServer_url) {
			try {
				JSONObject data = new JSONObject();
				JSONObject jsonobj = new JSONObject();

				jsonobj.put(DatabaseForDemo.UNIQUE_ID, randomNumberforedit);
				jsonobj.put(DatabaseForDemo.CREATED_DATE, cuurentTimeforedit);
				jsonobj.put(DatabaseForDemo.MODIFIED_DATE, modifytimeforedit);
				jsonobj.put(DatabaseForDemo.MODIFIED_IN, "Local");
				jsonobj.put(DatabaseForDemo.EMP_NAME,
						personalemployeeforedit.getEmp_name());
				jsonobj.put(DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID, emp_emp_Idforedit);
				jsonobj.put(DatabaseForDemo.EMP_ID, personalemployeeforedit.getEmp_id());
				jsonobj.put(DatabaseForDemo.EMP_EMAIL,
						personalemployeeforedit.getEmp_email());
				jsonobj.put(DatabaseForDemo.EMP_PHONE,
						personalemployeeforedit.getEmp_phone());
				jsonobj.put(DatabaseForDemo.EMP_BIRTH,
						personalemployeeforedit.getEmp_birth());
				jsonobj.put(DatabaseForDemo.EMP_ADDRESS,
						personalemployeeforedit.getEmp_address());
				jsonobj.put(DatabaseForDemo.EMP_CITY,
						personalemployeeforedit.getEmp_city());
				jsonobj.put(DatabaseForDemo.EMP_COUNTRY,
						personalemployeeforedit.getEmp_country());
				jsonobj.put(DatabaseForDemo.EMP_STATE,
						personalemployeeforedit.getEmp_state());
				jsonobj.put(DatabaseForDemo.EMP_POSTAL,
						personalemployeeforedit.getEmp_postal());

				JSONArray fields = new JSONArray();
				fields.put(0, jsonobj);
				data.put("fields", fields);
				datavalforedit = data.toString();
				System.out.println("data val is:" + datavalforedit);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

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
								"abcdefg", DatabaseForDemo.EMP_PERSONAL_TABLE,
								Parameters.currentTime(),
								Parameters.currentTime(), datavalforedit, "");
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

								dbforloginlogoutWriteEmployee.execSQL(deletequery);
								dbforloginlogoutWriteEmployee.execSQL(insertquery);
								System.out.println("queries executed" + ii);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						String select = "select *from "
								+ DatabaseForDemo.MISCELLANEOUS_TABLE;
						Cursor cursor = 		dbforloginlogoutReadEmployee.rawQuery(select, null);
						if (cursor.getCount() > 0) {
							dbforloginlogoutWriteEmployee.execSQL("update "
									+ DatabaseForDemo.MISCELLANEOUS_TABLE
									+ " set "
									+ DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL
									+ "=\"" + servertiem + "\"");
							cursor.close();
						} else {
							cursor.close();
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
							dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.MISCELLANEOUS_TABLE,
									null, contentValues1);
						}
						datavalforedit = "";
					}
				}).start();
			} else {
				ContentValues contentValues1 = new ContentValues();
				contentValues1.put(DatabaseForDemo.QUERY_TYPE, "insert");
				contentValues1.put(DatabaseForDemo.PENDING_USER_ID, Parameters.userid);
				contentValues1.put(DatabaseForDemo.PAGE_URL, "saveinfo.php");
				contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING,
						DatabaseForDemo.EMP_PERSONAL_TABLE);
				contentValues1.put(DatabaseForDemo.CURRENT_TIME_PENDING,
						Parameters.currentTime());
				contentValues1.put(DatabaseForDemo.PARAMETERS, datavalforedit);
				dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null,
						contentValues1);
				datavalforedit = "";
			}
			}
		//}
	}

	
	private void insertPermisstionsDetailsforedit() {
		ContentValues contentValues = new ContentValues();
		
		contentValues.put(DatabaseForDemo.UNIQUE_ID, randomNumberforedit);
		contentValues.put(DatabaseForDemo.CREATED_DATE, cuurentTimeforedit);
		contentValues.put(DatabaseForDemo.MODIFIED_DATE, modifytimeforedit);
		contentValues.put(DatabaseForDemo.MODIFIED_IN, "Local");
		contentValues.put(DatabaseForDemo.EMP_INVENTORY,
				permissionsemployeeforedit.getmInventoryT());
		contentValues.put(DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID, emp_emp_Idforedit);
		contentValues.put(DatabaseForDemo.EMP_CUSTOMERS,
				permissionsemployeeforedit.getmCustomersT());
		contentValues.put(DatabaseForDemo.EMP_REPORTS,
				permissionsemployeeforedit.getmReportsT());
		contentValues.put(DatabaseForDemo.EMP_DISCOUNTS,
				permissionsemployeeforedit.getmDiscountsT());
		contentValues.put(DatabaseForDemo.EMP_SETTINGS,
				permissionsemployeeforedit.getmSettingsT());
		contentValues.put(DatabaseForDemo.EMP_PRICE,
				permissionsemployeeforedit.getmPriceChangesT());
		contentValues.put(DatabaseForDemo.EMP_EXIT,
				permissionsemployeeforedit.getmAllowExitT());
		contentValues.put(DatabaseForDemo.EMP_PAYOUTS,
				permissionsemployeeforedit.getmVendorPayoutsT());
		contentValues.put(DatabaseForDemo.EMP_DELETE,
				permissionsemployeeforedit.getmDeleteItemsT());
		contentValues.put(DatabaseForDemo.EMP_VOID,
				permissionsemployeeforedit.getmVoidInvoicesT());
		contentValues.put(DatabaseForDemo.EMP_TRANSACTIONS,
				permissionsemployeeforedit.getmTransactionsT());
		contentValues.put(DatabaseForDemo.EMP_HOLDPRINTS,
				permissionsemployeeforedit.getmHoldPrintT());
		contentValues.put(DatabaseForDemo.EMP_CREDIT,
				permissionsemployeeforedit.getmCreditcardsT());
		contentValues.put(DatabaseForDemo.EMP_ENDCASH,
				permissionsemployeeforedit.getmEndCashT());
		dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.EMP_PERMISSIONS_TABLE, null,
				contentValues);
		contentValues.clear();
	//	if (Parameters.mfindServer_url) {
			try {
				JSONObject data = new JSONObject();
				JSONObject jsonobj = new JSONObject();

				jsonobj.put(DatabaseForDemo.UNIQUE_ID, randomNumberforedit);
				jsonobj.put(DatabaseForDemo.CREATED_DATE, cuurentTimeforedit);
				jsonobj.put(DatabaseForDemo.MODIFIED_DATE, modifytimeforedit);
				jsonobj.put(DatabaseForDemo.MODIFIED_IN, "Local");
				jsonobj.put(DatabaseForDemo.EMP_INVENTORY,
						permissionsemployeeforedit.getmInventoryT());
				jsonobj.put(DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID, emp_emp_Idforedit);
				jsonobj.put(DatabaseForDemo.EMP_CUSTOMERS,
						permissionsemployeeforedit.getmCustomersT());
				jsonobj.put(DatabaseForDemo.EMP_REPORTS,
						permissionsemployeeforedit.getmReportsT());
				jsonobj.put(DatabaseForDemo.EMP_DISCOUNTS,
						permissionsemployeeforedit.getmDiscountsT());
				jsonobj.put(DatabaseForDemo.EMP_SETTINGS,
						permissionsemployeeforedit.getmSettingsT());
				jsonobj.put(DatabaseForDemo.EMP_PRICE,
						permissionsemployeeforedit.getmPriceChangesT());
				jsonobj.put(DatabaseForDemo.EMP_EXIT,
						permissionsemployeeforedit.getmAllowExitT());
				jsonobj.put(DatabaseForDemo.EMP_PAYOUTS,
						permissionsemployeeforedit.getmVendorPayoutsT());
				jsonobj.put(DatabaseForDemo.EMP_DELETE,
						permissionsemployeeforedit.getmDeleteItemsT());
				jsonobj.put(DatabaseForDemo.EMP_VOID,
						permissionsemployeeforedit.getmVoidInvoicesT());
				jsonobj.put(DatabaseForDemo.EMP_TRANSACTIONS,
						permissionsemployeeforedit.getmTransactionsT());
				jsonobj.put(DatabaseForDemo.EMP_HOLDPRINTS,
						permissionsemployeeforedit.getmHoldPrintT());
				jsonobj.put(DatabaseForDemo.EMP_CREDIT,
						permissionsemployeeforedit.getmCreditcardsT());
				jsonobj.put(DatabaseForDemo.EMP_ENDCASH,
						permissionsemployeeforedit.getmEndCashT());

				JSONArray fields = new JSONArray();
				fields.put(0, jsonobj);
				data.put("fields", fields);
				datavalforedit = data.toString();
				System.out.println("data val is:" + datavalforedit);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

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
								"abcdefg",
								DatabaseForDemo.EMP_PERMISSIONS_TABLE,
								Parameters.currentTime(),
								Parameters.currentTime(), datavalforedit, "");
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

								dbforloginlogoutWriteEmployee.execSQL(deletequery);
								dbforloginlogoutWriteEmployee.execSQL(insertquery);
								System.out.println("queries executed" + ii);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						String select = "select *from "
								+ DatabaseForDemo.MISCELLANEOUS_TABLE;
						Cursor cursor = dbforloginlogoutWriteEmployee.rawQuery(select, null);
						if (cursor.getCount() > 0) {
							dbforloginlogoutWriteEmployee.execSQL("update "
									+ DatabaseForDemo.MISCELLANEOUS_TABLE
									+ " set "
									+ DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL
									+ "=\"" + servertiem + "\"");
							cursor.close();
						} else {
							cursor.close();
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
							dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.MISCELLANEOUS_TABLE,
									null, contentValues1);
						}
						datavalforedit = "";
					}
				}).start();
			} else {

				ContentValues contentValues1 = new ContentValues();
				contentValues1.put(DatabaseForDemo.QUERY_TYPE, "insert");
				contentValues1.put(DatabaseForDemo.PENDING_USER_ID, Parameters.userid);
				contentValues1.put(DatabaseForDemo.PAGE_URL, "saveinfo.php");
				contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING,
						DatabaseForDemo.EMP_PERMISSIONS_TABLE);
				contentValues1.put(DatabaseForDemo.CURRENT_TIME_PENDING,
						Parameters.currentTime());
				contentValues1.put(DatabaseForDemo.PARAMETERS, datavalforedit);
				dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null,
						contentValues1);
				datavalforedit = "";
			}
			}

	//	}
	}

	
	private void insertEmployeeDetailsforedit(Employee employee) {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseForDemo.UNIQUE_ID, randomNumberforedit);
		contentValues.put(DatabaseForDemo.CREATED_DATE, cuurentTimeforedit);
		contentValues.put(DatabaseForDemo.MODIFIED_DATE, modifytimeforedit);
		contentValues.put(DatabaseForDemo.MODIFIED_IN, "Local");
		contentValues.put(DatabaseForDemo.EMPLOYEE_DEPARTMENT,
				employee.getmDepartment());
		contentValues.put(DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID, emp_emp_Idforedit);
		contentValues.put(DatabaseForDemo.EMPLOYEE_DISPLAY_NAME, emp_emp_Nameforedit);
		contentValues.put(DatabaseForDemo.EMPLOYEE_PASSWORD,
				employee.getmPassword());
		contentValues.put(DatabaseForDemo.EMPLOYEE_CARD_SWIPE_ID,
				employee.getmCardswipeId());
		contentValues.put(DatabaseForDemo.EMPLOYEE_CUSTOMER,
				employee.getmCustomer());
		contentValues.put(DatabaseForDemo.EMPLOYEE_HOURLY_WAGE,
				employee.getmHoursWage());
		contentValues.put(DatabaseForDemo.EMPLOYEE_CC_TIPS,
				employee.getEmployee_cc_tips());
		contentValues.put(DatabaseForDemo.EMPLOYEE_DISABLE,
				employee.getEmployee_disable());
		contentValues.put(DatabaseForDemo.EMPLOYEE_ADMIN_CARD,
				employee.getEmployee_admin_card());
		contentValues.put(DatabaseForDemo.SECURITY_QUESTION,
				"1");
		contentValues.put(DatabaseForDemo.SECURITY_ANSWER,
				"1");
		contentValues.put(DatabaseForDemo.SERVER_PASSWORD,
			"12");
		dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.EMPLOYEE_TABLE, null,
				contentValues);
		contentValues.clear();
		Toast.makeText(this, "Saved successfully!", Toast.LENGTH_SHORT).show();
		//if (Parameters.mfindServer_url) {
			try {
				JSONObject data = new JSONObject();
				JSONObject jsonobj = new JSONObject();

				jsonobj.put(DatabaseForDemo.UNIQUE_ID, randomNumberforedit);
				jsonobj.put(DatabaseForDemo.CREATED_DATE,cuurentTimeforedit);
				jsonobj.put(DatabaseForDemo.MODIFIED_DATE, modifytimeforedit);
				jsonobj.put(DatabaseForDemo.MODIFIED_IN, "Local");
				jsonobj.put(DatabaseForDemo.EMPLOYEE_DEPARTMENT,
						employee.getmDepartment());
				jsonobj.put(DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID, emp_emp_Idforedit);
				jsonobj.put(DatabaseForDemo.EMPLOYEE_DISPLAY_NAME,
						employee.getmDisplayName());
				jsonobj.put(DatabaseForDemo.EMPLOYEE_PASSWORD,
						employee.getmPassword());
				jsonobj.put(DatabaseForDemo.EMPLOYEE_CARD_SWIPE_ID,
						employee.getmCardswipeId());
				jsonobj.put(DatabaseForDemo.EMPLOYEE_CUSTOMER,
						employee.getmCustomer());
				jsonobj.put(DatabaseForDemo.EMPLOYEE_HOURLY_WAGE,
						employee.getmHoursWage());
				jsonobj.put(DatabaseForDemo.EMPLOYEE_CC_TIPS,
						employee.getEmployee_cc_tips());
				jsonobj.put(DatabaseForDemo.EMPLOYEE_DISABLE,
						employee.getEmployee_disable());
				jsonobj.put(DatabaseForDemo.EMPLOYEE_ADMIN_CARD,
						employee.getEmployee_admin_card());
				jsonobj.put(DatabaseForDemo.SECURITY_QUESTION,
						"1");
				jsonobj.put(DatabaseForDemo.SECURITY_ANSWER,
						"1");
				jsonobj.put(DatabaseForDemo.SERVER_PASSWORD,
					"12");
				JSONArray fields = new JSONArray();
				fields.put(0, jsonobj);
				data.put("fields", fields);
				datavalforedit = data.toString();
				System.out.println("data val is:" + datavalforedit);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}

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
								"abcdefg", DatabaseForDemo.EMPLOYEE_TABLE,
								Parameters.currentTime(),
								Parameters.currentTime(), datavalforedit, "");
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

								dbforloginlogoutWriteEmployee.execSQL(deletequery);
								dbforloginlogoutWriteEmployee.execSQL(insertquery);
								System.out.println("queries executed" + ii);
							}
						} catch (JSONException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}

						String select = "select *from "
								+ DatabaseForDemo.MISCELLANEOUS_TABLE;
						Cursor cursor = 		dbforloginlogoutReadEmployee.rawQuery(select, null);
						if (cursor.getCount() > 0) {
							dbforloginlogoutWriteEmployee.execSQL("update "
									+ DatabaseForDemo.MISCELLANEOUS_TABLE
									+ " set "
									+ DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL
									+ "=\"" + servertiem + "\"");
							cursor.close();
						} else {
							cursor.close();
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
							dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.MISCELLANEOUS_TABLE,
									null, contentValues1);
						}
						datavalforedit = "";
					}
				}).start();
			} else {

				ContentValues contentValues1 = new ContentValues();
				contentValues1.put(DatabaseForDemo.QUERY_TYPE, "insert");
				contentValues1.put(DatabaseForDemo.PENDING_USER_ID, Parameters.userid);
				contentValues1.put(DatabaseForDemo.PAGE_URL, "saveinfo.php");
				contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING,
						DatabaseForDemo.EMPLOYEE_TABLE);
				contentValues1.put(DatabaseForDemo.CURRENT_TIME_PENDING,
						Parameters.currentTime());
				contentValues1.put(DatabaseForDemo.PARAMETERS, datavalforedit);
				dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null,
						contentValues1);
				datavalforedit = "";
			}
			}

		//}

	}

	
	@SuppressWarnings("deprecation")
	@Override
	public void onDeleteClicked(View v, final String string) {
		// TODO Auto-generated method stub
		final AlertDialog alertDialog1 = new AlertDialog.Builder(
				EmployeeActivity.this).create();
		LayoutInflater mInflater = LayoutInflater.from(EmployeeActivity.this);
		View layout = mInflater.inflate(R.layout.delete_popup, null);
		Button ok = (Button) layout.findViewById(R.id.ok);
		Button cancel = (Button) layout.findViewById(R.id.cancel);

		alertDialog1.setTitle("Delete");

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				deleteEmployeeTablesforedit(DatabaseForDemo.EMPLOYEE_TABLE,
						string);

				deleteEmployeeTablesforedit(DatabaseForDemo.EMP_PERMISSIONS_TABLE,
						string);

				deleteEmployeeTablesforedit(DatabaseForDemo.EMP_PERSONAL_TABLE, string);
				deleteEmployeeTablesforedit(DatabaseForDemo.EMP_PAYROLL_TABLE, string);
				deleteEmployeeTablesforedit(DatabaseForDemo.EMP_STORE_TABLE, string);

			/*	DatabaseForDemo sqlDB = new DatabaseForDemo(
						EmployeeActivity.this);
				SQLiteDatabase sqliteDatabase = sqlDB.getWritableDatabase();
				String where = DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID + "=?";
				sqliteDatabase.delete(DatabaseForDemo.EMPLOYEE_TABLE, where,
						new String[] { string });

				Toast.makeText(EmployeeActivity.this, "deleted", 1000)
						.show();
				sqlDB.close();
				sqliteDatabase.close();

				DatabaseForDemo sqlDB1 = new DatabaseForDemo(
						EmployeeActivity.this);
				SQLiteDatabase sqliteDatabase1 = sqlDB1.getWritableDatabase();
				String where1 = DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID + "=?";
				sqliteDatabase1.delete(DatabaseForDemo.EMP_PERMISSIONS_TABLE,
						where1, new String[] { string });
				sqlDB1.close();
				sqliteDatabase1.close();

				DatabaseForDemo sqlDB12 = new DatabaseForDemo(
						EmployeeActivity.this);
				SQLiteDatabase sqliteDatabase12 = sqlDB12.getWritableDatabase();
				String where12 = DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID + "=?";
				sqliteDatabase12.delete(DatabaseForDemo.EMP_PERSONAL_TABLE,
						where12, new String[] { string });
				sqlDB12.close();
				sqliteDatabase12.close();

				DatabaseForDemo sqlDB123 = new DatabaseForDemo(
						EmployeeActivity.this);
				SQLiteDatabase sqliteDatabase123 = sqlDB123
						.getWritableDatabase();
				String where123 = DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID + "=?";
				sqliteDatabase123.delete(DatabaseForDemo.EMP_PAYROLL_TABLE,
						where123, new String[] { string });
				sqlDB123.close();
				sqliteDatabase123.close();
*/
				id_data.clear();
				desc_data.clear();
				total_id_data.clear();
				total_desc_data.clear();
				//onCreate(savedInstanceState);
				listUpdate();
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
	
	public void count(int j) {
		testforid = 1;
		stLoop = (j - 1) * pagecount;
		endLoop = j * pagecount;
		if (endLoop >= totalcount) {
			endLoop = totalcount;
		}
		id_data = getData(stLoop, endLoop, total_id_data);
		desc_data = getData(stLoop, endLoop, total_desc_data);
		EmployeeEditAdapter adapter = new EmployeeEditAdapter(
				EmployeeActivity.this, id_data, desc_data);
		Log.v("lop", "" + desc_data);
		adapter.setListener(this);
		Log.v("lop", "" + desc_data);
		tax_listforedit.setAdapter(adapter);

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

	public void listUpdate() {
		id_data.clear();
		desc_data.clear();
		total_id_data.clear();
		total_desc_data.clear();
		llforedit.removeAllViews();

		String selectQuery = "SELECT  * FROM " + DatabaseForDemo.EMPLOYEE_TABLE;
		Cursor cursor = 	dbforloginlogoutReadEmployee.rawQuery(selectQuery, null);
		if (cursor != null) {
			if (cursor.moveToFirst()) {
				do {
					String name = cursor
							.getString(cursor
									.getColumnIndex(DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID));

					total_id_data.add(name);
					String city = cursor
							.getString(cursor
									.getColumnIndex(DatabaseForDemo.EMPLOYEE_DISPLAY_NAME));

					total_desc_data.add(city);

				} while (cursor.moveToNext());
			}

		}
		EmployeeEditAdapter adapter = new EmployeeEditAdapter(this, id_data,
				desc_data);
		adapter.setListener(this);
		tax_listforedit.setAdapter(adapter);
		cursor.close();
		totalcount = total_id_data.size();
		System.out.println("total count ve is:" + totalcount);

		int to = totalcount / pagecount;
		int too = totalcount % pagecount;
		int i = to;
		if (too != 0) {
			i = to + 1;
		}
		count(1);
		llforedit.setWeightSum(i);
		System.out.println("total count value :" + i);
		for (j = 1; j <= i; j++) {
			final Button btn = new Button(this);
			btn.setText("" + j);
			btn.setId(j);
			btn.setBackgroundResource(R.drawable.pnormal);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f);

			LinearLayout space = new LinearLayout(this);
			LayoutParams lp1 = new LayoutParams(1, LayoutParams.WRAP_CONTENT);
			llforedit.addView(btn, lp);
			llforedit.addView(space, lp1);
			((Button) findViewById(1))
					.setBackgroundResource(R.drawable.pactive);
			((Button) findViewById(1)).setTextColor(Color.WHITE);
			btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					for (int l = 1; l <= j - 1; l++) {
						((Button) findViewById(l))
								.setBackgroundResource(R.drawable.pnormal);
						((Button) findViewById(l)).setTextColor(Color.BLACK);
						System.out.println("total btn is:" + l);
					}
					btn.setBackgroundResource(R.drawable.pactive);
					btn.setTextColor(Color.WHITE);
					System.out.println("total count id is:" + btn.getId());
					count(Integer.parseInt(btn.getText().toString().trim()));
					testforid = Integer.parseInt(btn.getText().toString()
							.trim().trim());
				}

			});

		}
	}

	
	View.OnClickListener cancelOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {

		}
	};

	View.OnClickListener permissionClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			
			final AlertDialog alertDialog12 = new AlertDialog.Builder(
					EmployeeActivity.this,android.R.style.Theme_Translucent_NoTitleBar).create();
			LayoutInflater mInflater2 = LayoutInflater
					.from(EmployeeActivity.this);
		     
			final View layout2 = mInflater2.inflate(R.layout.permitions_employee,
					null);
			Button permitionssave = (Button) layout2
					.findViewById(R.id.permitionssave);
			Button permisstioncancel = (Button) layout2
					.findViewById(R.id.permisstioncancel);
			final RadioGroup mInventory = (RadioGroup) layout2
					.findViewById(R.id.nventory);
			final RadioGroup mCustomers = (RadioGroup) layout2
					.findViewById(R.id.ustomers);
			final RadioGroup mReports = (RadioGroup) layout2
					.findViewById(R.id.eports);
			final RadioGroup mDiscounts = (RadioGroup) layout2
					.findViewById(R.id.iscounts);
			final RadioGroup mSettings = (RadioGroup) layout2
					.findViewById(R.id.ettings);
			final RadioGroup mPriceChanges = (RadioGroup) layout2
					.findViewById(R.id.riceChanges);
			final RadioGroup mAllowExit = (RadioGroup) layout2
					.findViewById(R.id.llowExit);
			final RadioGroup mVendorPayouts = (RadioGroup) layout2
					.findViewById(R.id.endorPayouts);
			final RadioGroup mDeleteItems = (RadioGroup) layout2
					.findViewById(R.id.eleteItems);
			final RadioGroup mVoidInvoices = (RadioGroup) layout2
					.findViewById(R.id.oidInvoices);
			final RadioGroup mTransactions = (RadioGroup) layout2
					.findViewById(R.id.ransactions);
			final RadioGroup mHoldPrint = (RadioGroup) layout2
					.findViewById(R.id.oldPrint);
			final RadioGroup mCreditcards = (RadioGroup) layout2
					.findViewById(R.id.reditcards);
			final RadioGroup mEndCash = (RadioGroup) layout2
					.findViewById(R.id.ndCash);
			
			if(permissionval){
				mInventory.check(R.id.radio0a);
				mCustomers.check(R.id.radio0b);
				mReports.check(R.id.radio0c);
				mDiscounts.check(R.id.radio0d);
				mSettings.check(R.id.radio0e);
				mPriceChanges.check(R.id.radio0f);
				mAllowExit.check(R.id.radio0g);
				mVendorPayouts.check(R.id.radio0h);
				mDeleteItems.check(R.id.radio0i);
				mVoidInvoices.check(R.id.radio0j);
				mTransactions.check(R.id.radio0k);
				mHoldPrint.check(R.id.radio0l);
				mCreditcards.check(R.id.radio0m);
				mEndCash.check(R.id.radio0n);
			}else{
				
			}
			
			permitionssave.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
                
					
					 checkk = mInventory.getCheckedRadioButtonId();
					radioButton = (RadioButton) layout2.findViewById(checkk);
					checkk = mCustomers.getCheckedRadioButtonId();
					permissionsemployee.setmInventoryT(radioButton.getText().toString().trim());
					radioButton = (RadioButton) layout2.findViewById(checkk);
					checkk = mReports.getCheckedRadioButtonId();
					permissionsemployee.setmCustomersT(radioButton.getText().toString().trim());
					radioButton = (RadioButton) layout2.findViewById(checkk);
					permissionsemployee.setmReportsT(radioButton.getText().toString().trim());
					checkk = mDiscounts.getCheckedRadioButtonId();
					radioButton = (RadioButton) layout2.findViewById(checkk);
					permissionsemployee.setmDiscountsT(radioButton.getText().toString().trim());
					checkk = mSettings.getCheckedRadioButtonId();
					radioButton = (RadioButton) layout2.findViewById(checkk);
					permissionsemployee.setmSettingsT(radioButton.getText().toString().trim());
					checkk = mPriceChanges
								.getCheckedRadioButtonId();
					radioButton = (RadioButton) layout2.findViewById(checkk);
					permissionsemployee.setmPriceChangesT(radioButton.getText()
							.toString());
					checkk = mAllowExit.getCheckedRadioButtonId();
					radioButton = (RadioButton) layout2.findViewById(checkk);
					permissionsemployee.setmAllowExitT(radioButton.getText().toString().trim());
					checkk = mVendorPayouts
								.getCheckedRadioButtonId();
					radioButton = (RadioButton) layout2.findViewById(checkk);
					permissionsemployee.setmVendorPayoutsT(radioButton.getText()
							.toString().trim());
					checkk = mDeleteItems.getCheckedRadioButtonId();
					radioButton = (RadioButton) layout2.findViewById(checkk);
					permissionsemployee.setmDeleteItemsT(radioButton.getText()
							.toString().trim());
					checkk = mVoidInvoices
								.getCheckedRadioButtonId();
					radioButton = (RadioButton) layout2.findViewById(checkk);
					permissionsemployee.setmVoidInvoicesT(radioButton.getText()
							.toString().trim());
					checkk = mTransactions
							.getCheckedRadioButtonId();
					radioButton = (RadioButton) layout2.findViewById(checkk);
					permissionsemployee.setmTransactionsT(radioButton.getText()
							.toString().trim());
					checkk = mHoldPrint.getCheckedRadioButtonId();
					radioButton = (RadioButton) layout2.findViewById(checkk);
					permissionsemployee.setmHoldPrintT(radioButton.getText().toString().trim());
					checkk = mCreditcards.getCheckedRadioButtonId();
					radioButton = (RadioButton) layout2.findViewById(checkk);
					permissionsemployee.setmCreditcardsT(radioButton.getText()
							.toString().trim());
					checkk = mEndCash.getCheckedRadioButtonId();
					radioButton = (RadioButton) layout2.findViewById(checkk);
					permissionsemployee.setmEndCashT(radioButton.getText().toString().trim());
					alertDialog12.dismiss();

				}
			});
			permisstioncancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					alertDialog12.dismiss();

				}
			});

			alertDialog12.setView(layout2);
			alertDialog12.show();
		}
	};
	View.OnClickListener storeinfoonClockListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			final AlertDialog alertDialog12 = new AlertDialog.Builder(
					EmployeeActivity.this).create();
			LayoutInflater mInflater2 = LayoutInflater
					.from(EmployeeActivity.this);
			View layout2 = mInflater2.inflate(R.layout.storeinfo_employee, null);
			final ListView storelist=(ListView) layout2.findViewById(R.id.storelist);
			Button store_save = (Button) layout2
					.findViewById(R.id.savestore);
			Button store_cancel = (Button) layout2
					.findViewById(R.id.cancelstore);
			  ArrayList<String> storearray=new ArrayList<String>();
			  final ArrayList<String> storearrayid=new ArrayList<String>();
			if (Parameters.isTableExists(dbforloginlogoutReadEmployee, DatabaseForDemo.STORE_TABLE)) {
		
			Cursor mCursor = dbforloginlogoutReadEmployee.rawQuery(
					"select * from " + DatabaseForDemo.STORE_TABLE, null);
			System.out.println(mCursor);
			if (mCursor != null) {
				if (mCursor.moveToFirst()) {
					do {
						String storename = mCursor.getString(mCursor
								.getColumnIndex(DatabaseForDemo.STORE_NAME));
						String storeid = mCursor.getString(mCursor
								.getColumnIndex(DatabaseForDemo.STORE_ID));
						storearray.add(storename);
						storearrayid.add(storeid);
					} while (mCursor.moveToNext());
				}
			}
			mCursor.close();
			}
			
			final ArrayAdapter<String> adapter=new ArrayAdapter<String>(EmployeeActivity.this, 
					android.R.layout.simple_list_item_multiple_choice,storearray);
			storelist.setChoiceMode(AbsListView.CHOICE_MODE_MULTIPLE);
 		storelist.setAdapter(adapter);
 		
			store_save.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					
					SparseBooleanArray checked = storelist.getCheckedItemPositions();
			        ArrayList<String> selectedItems = new ArrayList<String>();
			        for (int i = 0; i < checked.size(); i++) {
			            // Item position in adapter
			            int position = checked.keyAt(i);
			            // Add sport if it is checked i.e.) == TRUE!
			            if (checked.valueAt(i)){
			                selectedItems.add(adapter.getItem(position));
			                item_store_id += storearrayid.get(position)+"," ;
			                item_store_name += adapter.getItem(position)+",";
			            }
			        }
			 
			        String[] outputStrArr = new String[selectedItems.size()];
			 
			        for (int i = 0; i < selectedItems.size(); i++) {
			            outputStrArr[i] = selectedItems.get(i);
			        }
			        alertDialog12.dismiss();
				}
			});
			store_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					alertDialog12.dismiss();

				}
			});
			alertDialog12.setView(layout2);
			alertDialog12.show();
		}
	};
	
	View.OnClickListener personalClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			final AlertDialog alertDialog12 = new AlertDialog.Builder(
					EmployeeActivity.this).create();
			LayoutInflater mInflater2 = LayoutInflater
					.from(EmployeeActivity.this);
			View layout2 = mInflater2.inflate(R.layout.personal_employee, null);
			final EditText emp_name, emp_id, emp_email, emp_phone, emp_birth, emp_address, emp_city, emp_country, emp_state, emp_postal;

			emp_name = (EditText) layout2.findViewById(R.id.emp_name);
			emp_id = (EditText) layout2.findViewById(R.id.emp_id);
			emp_email = (EditText) layout2.findViewById(R.id.emp_email);
			emp_phone = (EditText) layout2.findViewById(R.id.emp_phone);
			emp_birth = (EditText) layout2.findViewById(R.id.emp_birth);
			emp_address = (EditText) layout2.findViewById(R.id.emp_address);
			emp_city = (EditText) layout2.findViewById(R.id.emp_city);
			emp_country = (EditText) layout2.findViewById(R.id.emp_country);
			emp_state = (EditText) layout2.findViewById(R.id.emp_state);
			emp_postal = (EditText) layout2.findViewById(R.id.emp_postalcode);

			Button personal_save = (Button) layout2
					.findViewById(R.id.personal_save);
			Button personal_cancel = (Button) layout2
					.findViewById(R.id.personal_cancel);
			personal_save.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub

					
					personalemployee.setEmp_name(emp_name.getText().toString().trim());
					personalemployee.setEmp_id(emp_id.getText().toString().trim());
					personalemployee.setEmp_email(emp_email.getText().toString().trim());
					personalemployee.setEmp_phone(emp_phone.getText().toString().trim());
					personalemployee.setEmp_birth(emp_birth.getText().toString().trim());
					personalemployee.setEmp_address(emp_address.getText().toString().trim()
							.trim());
					personalemployee.setEmp_city(emp_city.getText().toString().trim());
					personalemployee.setEmp_country(emp_country.getText().toString().trim()
							.trim());
					personalemployee.setEmp_state(emp_state.getText().toString().trim());
					personalemployee.setEmp_postal(emp_postal.getText().toString().trim()
							.trim());

					alertDialog12.dismiss();

				}
			});
			personal_cancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					alertDialog12.dismiss();

				}
			});
			alertDialog12.setView(layout2);
			alertDialog12.show();
		}
	};
	View.OnClickListener payrollClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			final AlertDialog alertDialog12 = new AlertDialog.Builder(
					EmployeeActivity.this).create();
			LayoutInflater mInflater2 = LayoutInflater
					.from(EmployeeActivity.this);
			View layout2 = mInflater2.inflate(R.layout.payroll_employee, null);
			Button payrollsave = (Button) layout2
					.findViewById(R.id.payrollsave);
			Button payrollcancel = (Button) layout2
					.findViewById(R.id.payrollcancel);
			final EditText federal, amount, statea, stateAmount, credits;
			final Spinner filingstatus, exempt;
			final CheckBox excludeCheck;
			excludeCheck = (CheckBox) layout2.findViewById(R.id.excludeCheck);
			federal = (EditText) layout2.findViewById(R.id.federal);
			amount = (EditText) layout2.findViewById(R.id.amount);
			statea = (EditText) layout2.findViewById(R.id.statea);
			stateAmount = (EditText) layout2.findViewById(R.id.stateAmount);
			credits = (EditText) layout2.findViewById(R.id.credits);
			filingstatus = (Spinner) layout2.findViewById(R.id.fillingStatus);
			exempt = (Spinner) layout2.findViewById(R.id.exempt);

			payrollsave.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					

					payrollemployee.setAmount(amount.getText().toString().trim());
					payrollemployee.setCredits(credits.getText().toString().trim());
					payrollemployee.setExcludeCheck(""+excludeCheck.isChecked());
				//	payrollemployee.setExempt(exempt.getSelectedItem().toString().trim());
					payrollemployee.setFederal(federal.getText().toString().trim());
					//payrollemployee.setFilingstatus(filingstatus.getSelectedItem().toString().trim());
					payrollemployee.setStatea(statea.getText().toString().trim());
					payrollemployee.setStateAmount(stateAmount.getText().toString().trim()
							.trim());
					alertDialog12.dismiss();

				}
			});
			payrollcancel.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					alertDialog12.dismiss();

				}
			});
			alertDialog12.setView(layout2);
			alertDialog12.show();
		}
	};
	View.OnClickListener addOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View view) {
			String id=mEmployeeId.getText().toString().trim();
			String namee=mDisplayName.getText().toString().trim();
			if(id.length()<3){
				Toast.makeText(EmployeeActivity.this, "Enter Id above 3 characters", Toast.LENGTH_LONG).show();
			}else{
				if(namee.length()<3){
					Toast.makeText(EmployeeActivity.this, "Enter Name above 3 characters", Toast.LENGTH_LONG).show();
				}else{
			ArrayList<String> total_id_data=new ArrayList<String>();
			ArrayList<String> total_desc_data=new ArrayList<String>();

			String selectQuery = "SELECT  * FROM " + DatabaseForDemo.EMPLOYEE_TABLE;

			Cursor cursor = dbforloginlogoutReadEmployee.rawQuery(selectQuery, null);
			if (cursor != null) {
				if (cursor.moveToFirst()) {
					do {
						String name = cursor.getString(cursor
								.getColumnIndex(DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID));
						
						total_id_data.add(name);
						String city = cursor.getString(cursor
								.getColumnIndex(DatabaseForDemo.EMPLOYEE_DISPLAY_NAME));
					
						total_desc_data.add(city);
						
						
					} while (cursor.moveToNext());
				}
				
			}
			cursor.close();
			
			if(total_id_data.contains(id)){
				Toast.makeText(getApplicationContext(), "ID Already Exists ", 2000).show();
			}else{
				if(total_desc_data.contains(namee)){
					Toast.makeText(getApplicationContext(), "Name Already Exists ", 2000).show();
				}else{
			Employee employee = new Employee();
			employee.setmDepartment(mDepartment.getText().toString().trim());
			employee.setmEmployeeId(id);
			employee.setmPassword(Parameters.MD5(mPassword.getText().toString().trim()));
			employee.setmDisplayName(namee);
			employee.setmCardswipeId(mCardswipeId.getText().toString().trim());
			employee.setmCustomer(mCustomer.getText().toString().trim());
			employee.setmHoursWage(mHourlyWage.getText().toString().trim());
			employee.setEmployee_cc_tips("" + cc_tips.isChecked());
			employee.setEmployee_admin_card("" + admin_card.isChecked());
			employee.setEmployee_disable("" + disable.isChecked());
			
			if( admin_card.isChecked()){
					permissionsAll("Enable");
			}
			insertEmployeeDetails(employee);
			insertPeyrollDetails();
			insertPermisstionsDetails();
			 insertPersonalDetails();
			 storeInfoDetails();
			 permissionsAll("Disable");
				}
			}
				}
		}
		}
	};

	private void insertEmployeeDetails(Employee employee) {
		emp_emp_Id=employee.getmEmployeeId();
		cuurentTime= Parameters.currentTime();
		randomNumber=Parameters.randomValue();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseForDemo.UNIQUE_ID,  randomNumber);
		contentValues.put(DatabaseForDemo.CREATED_DATE, cuurentTime);
		contentValues.put(DatabaseForDemo.MODIFIED_DATE,  cuurentTime);
		contentValues.put(DatabaseForDemo.MODIFIED_IN, "Local");
		contentValues.put(DatabaseForDemo.EMPLOYEE_DEPARTMENT,
				employee.getmDepartment());
		contentValues.put(DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID,
				emp_emp_Id);
		contentValues.put(DatabaseForDemo.EMPLOYEE_DISPLAY_NAME,
				employee.getmDisplayName());
		contentValues.put(DatabaseForDemo.EMPLOYEE_PASSWORD,
				employee.getmPassword());
		contentValues.put(DatabaseForDemo.EMPLOYEE_CARD_SWIPE_ID,
				employee.getmCardswipeId());
		contentValues.put(DatabaseForDemo.EMPLOYEE_CUSTOMER,
				employee.getmCustomer());
		contentValues.put(DatabaseForDemo.EMPLOYEE_HOURLY_WAGE,
				employee.getmHoursWage());
		contentValues.put(DatabaseForDemo.EMPLOYEE_CC_TIPS,
				employee.getEmployee_cc_tips());
		contentValues.put(DatabaseForDemo.EMPLOYEE_DISABLE,
				employee.getEmployee_disable());
		contentValues.put(DatabaseForDemo.EMPLOYEE_ADMIN_CARD,
				employee.getEmployee_admin_card());
		contentValues.put(DatabaseForDemo.SECURITY_QUESTION,
				"1");
		contentValues.put(DatabaseForDemo.SECURITY_ANSWER,
				"1");
		contentValues.put(DatabaseForDemo.SERVER_PASSWORD,
			"12");
		dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.EMPLOYEE_TABLE, null,
				contentValues);
		contentValues.clear();
		Toast.makeText(this, "Saved successfully!", Toast.LENGTH_SHORT).show();
	//	if(Parameters.mfindServer_url){
			try {
			JSONObject data = new JSONObject();
			JSONObject jsonobj = new JSONObject();
			
			jsonobj.put(DatabaseForDemo.UNIQUE_ID,  randomNumber);
			jsonobj.put(DatabaseForDemo.CREATED_DATE, cuurentTime);
			jsonobj.put(DatabaseForDemo.MODIFIED_DATE, cuurentTime);
			jsonobj.put(DatabaseForDemo.MODIFIED_IN, "Local");
			jsonobj.put(DatabaseForDemo.EMPLOYEE_DEPARTMENT,
					employee.getmDepartment());
			jsonobj.put(DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID,
					emp_emp_Id);
			jsonobj.put(DatabaseForDemo.EMPLOYEE_DISPLAY_NAME,
					employee.getmDisplayName());
			jsonobj.put(DatabaseForDemo.EMPLOYEE_PASSWORD,
					employee.getmPassword());
			jsonobj.put(DatabaseForDemo.EMPLOYEE_CARD_SWIPE_ID,
					employee.getmCardswipeId());
			jsonobj.put(DatabaseForDemo.EMPLOYEE_CUSTOMER,
					employee.getmCustomer());
			jsonobj.put(DatabaseForDemo.EMPLOYEE_HOURLY_WAGE,
					employee.getmHoursWage());
			jsonobj.put(DatabaseForDemo.EMPLOYEE_CC_TIPS,
					employee.getEmployee_cc_tips());
			jsonobj.put(DatabaseForDemo.EMPLOYEE_DISABLE,
					employee.getEmployee_disable());
			jsonobj.put(DatabaseForDemo.EMPLOYEE_ADMIN_CARD,
					employee.getEmployee_admin_card());
			jsonobj.put(DatabaseForDemo.SECURITY_QUESTION,
					"1");
			jsonobj.put(DatabaseForDemo.SECURITY_ANSWER,
					"1");
			jsonobj.put(DatabaseForDemo.SERVER_PASSWORD,
				"12");
			JSONArray fields = new JSONArray();
			fields.put(0, jsonobj);
				data.put("fields", fields);
				dataval = data.toString();
				System.out.println("data val is:"+dataval);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(Parameters.OriginalUrl.equals("")){
				System.out.println("there is no server url val");
			}else{
		boolean isnet = Parameters.isNetworkAvailable(getApplicationContext());
		if(isnet){
			new Thread(new Runnable() {
			    @Override
				public void run() {
    				JsonPostMethod jsonpost = new JsonPostMethod();
    				String response = jsonpost.postmethodfordirect("admin", "abcdefg", DatabaseForDemo.EMPLOYEE_TABLE, Parameters.currentTime(), Parameters.currentTime(), dataval, "");
    				System.out.println("response test is:"+response);
    				String servertiem = null;
    				try {
						JSONObject obj = new JSONObject(response);
						JSONObject responseobj = obj.getJSONObject("response");
						servertiem = responseobj.getString("server-time");
						System.out.println("servertime is:"+servertiem);
						JSONArray array = obj.getJSONArray("insert-queries");
						System.out.println("array list length for insert is:"+array.length());
						JSONArray array2 = obj.getJSONArray("delete-queries");
						System.out.println("array2 list length for delete is:"+array2.length());
						for(int jj = 0,ii = 0; jj<array2.length() && ii<array.length(); jj++,ii++){
							String deletequerytemp = array2.getString(jj);
							String deletequery1 = deletequerytemp.replace("'", "\"");
							String deletequery = deletequery1.replace("\\\"", "'");
							System.out.println("delete query"+jj+" is :"+deletequery);
							
							String insertquerytemp = array.getString(ii);
							String insertquery1 = insertquerytemp.replace("'", "\"");
							String insertquery = insertquery1.replace("\\\"", "'");
							System.out.println("delete query"+jj+" is :"+insertquery);
							
							dbforloginlogoutWriteEmployee.execSQL(deletequery);
							dbforloginlogoutWriteEmployee.execSQL(insertquery);
							System.out.println("queries executed"+ii);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			
					String select = "select *from "+DatabaseForDemo.MISCELLANEOUS_TABLE;
					Cursor cursor = 		dbforloginlogoutReadEmployee.rawQuery(select, null);
					if(cursor.getCount()>0){
						dbforloginlogoutWriteEmployee.execSQL("update "+DatabaseForDemo.MISCELLANEOUS_TABLE+" set "+DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL+"=\""+servertiem+"\"");
						cursor.close();
					}else{
						cursor.close();
						ContentValues contentValues1 = new ContentValues();
    					contentValues1.put(DatabaseForDemo.MISCEL_STORE,  "store1");
    					contentValues1.put(DatabaseForDemo.MISCEL_PAGEURL, "");
    					contentValues1.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL, Parameters.currentTime());
    					contentValues1.put(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL, Parameters.currentTime());
    					dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.MISCELLANEOUS_TABLE, null, contentValues1);
					}
    				dataval = "";
			    }
			  }).start();
		}else{
			ContentValues contentValues1 = new ContentValues();
			contentValues1.put(DatabaseForDemo.QUERY_TYPE,  "insert");
			contentValues1.put(DatabaseForDemo.PENDING_USER_ID, Parameters.userid);
			contentValues1.put(DatabaseForDemo.PAGE_URL,  "saveinfo.php");
			contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING, DatabaseForDemo.EMPLOYEE_TABLE);
			contentValues1.put(DatabaseForDemo.CURRENT_TIME_PENDING, Parameters.currentTime());
			contentValues1.put(DatabaseForDemo.PARAMETERS, dataval);
			dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null, contentValues1);
			dataval = "";
		}
			
			}	
	//}
		resetView();
	}

	private void changeState(boolean enabled) {
		mDepartment.setEnabled(enabled);
		mEmployeeId.setEnabled(enabled);
		mPassword.setEnabled(enabled);
		mDisplayName.setEnabled(enabled);
		mCardswipeId.setEnabled(enabled);
		mCustomer.setEnabled(enabled);
		mHourlyWage.setEnabled(enabled);
	}

	private void resetView() {
		mDepartment.setText("");
		mEmployeeId.setText("");
		mPassword.setText("");
		mDisplayName.setText("");
		mCardswipeId.setText("");
		mCustomer.setText("");
		mHourlyWage.setText("");
		cc_tips.setChecked(false);
		disable.setChecked(false);
		admin_card.setChecked(false);
	}

	/*@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);

		if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK
				&& null != data) {
			Uri selectedImage = data.getData();
			String[] filePathColumn = { MediaColumns.DATA };

			Cursor cursor = getContentResolver().query(selectedImage,
					filePathColumn, null, null, null);
			cursor.moveToFirst();

			int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
			String picturePath = cursor.getString(columnIndex);
			cursor.close();

		//	mImageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
			Bitmap bm = BitmapFactory.decodeFile(picturePath);

			
			 * File filename; try { String path1 =
			 * android.os.Environment.getExternalStorageDirectory() .toString();
			 * File file1 = new File(path1 + "/harinath"); if (!file1.exists())
			 * file1.mkdirs(); filename = new File(file1.getAbsolutePath() + "/"
			 * + "hari" + ".jpg"); FileOutputStream out = new
			 * FileOutputStream(filename);
			 * bm.compress(Bitmap.CompressFormat.JPEG, 90, out); out.flush();
			 * out.close(); ContentValues image = new ContentValues();
			 * image.put(MediaColumns.TITLE, "harinath");
			 * image.put(MediaColumns.DISPLAY_NAME, "hari");
			 * image.put(ImageColumns.DESCRIPTION, "App Image");
			 * image.put(MediaColumns.DATE_ADDED, System.currentTimeMillis());
			 * image.put(MediaColumns.MIME_TYPE, "image/jpg");
			 * image.put(ImageColumns.ORIENTATION, 0); File parent =
			 * filename.getParentFile();
			 * image.put(Images.ImageColumns.BUCKET_ID, parent.toString()
			 * .toLowerCase().hashCode());
			 * image.put(Images.ImageColumns.BUCKET_DISPLAY_NAME,
			 * parent.getName() .toLowerCase()); image.put(MediaColumns.SIZE,
			 * filename.length()); image.put(MediaColumns.DATA,
			 * filename.getAbsolutePath()); Uri result =
			 * getContentResolver().insert(
			 * MediaStore.Images.Media.EXTERNAL_CONTENT_URI, image);
			 * Toast.makeText(getApplicationContext(), "File is Saved in  " +
			 * filename, Toast.LENGTH_SHORT).show(); Log.v("hari",
			 * ""+MediaStore.Images.Media.EXTERNAL_CONTENT_URI); Log.v("path",
			 * ""+filename.getAbsolutePath());
			 * 
			 * } catch (Exception e) { e.printStackTrace(); }
			 
		}
	}
*/
	private void insertPermisstionsDetails() {
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseForDemo.UNIQUE_ID,  randomNumber);
		contentValues.put(DatabaseForDemo.CREATED_DATE, cuurentTime);
		contentValues.put(DatabaseForDemo.MODIFIED_DATE,  cuurentTime);
		contentValues.put(DatabaseForDemo.MODIFIED_IN, "Local");
		contentValues.put(DatabaseForDemo.EMP_INVENTORY,
				permissionsemployee.getmInventoryT());
		contentValues.put(DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID,
				emp_emp_Id);
		contentValues.put(DatabaseForDemo.EMP_CUSTOMERS,
				permissionsemployee.getmCustomersT());
		contentValues.put(DatabaseForDemo.EMP_REPORTS,
				permissionsemployee.getmReportsT());
		contentValues.put(DatabaseForDemo.EMP_DISCOUNTS,
				permissionsemployee.getmDiscountsT());
		contentValues.put(DatabaseForDemo.EMP_SETTINGS,
				permissionsemployee.getmSettingsT());
		contentValues.put(DatabaseForDemo.EMP_PRICE,
				permissionsemployee.getmPriceChangesT());
		contentValues.put(DatabaseForDemo.EMP_EXIT,
				permissionsemployee.getmAllowExitT());
		contentValues.put(DatabaseForDemo.EMP_PAYOUTS,
				permissionsemployee.getmVendorPayoutsT());
		contentValues.put(DatabaseForDemo.EMP_DELETE,
				permissionsemployee.getmDeleteItemsT());
		contentValues.put(DatabaseForDemo.EMP_VOID,
				permissionsemployee.getmVoidInvoicesT());
		contentValues.put(DatabaseForDemo.EMP_TRANSACTIONS,
				permissionsemployee.getmTransactionsT());
		contentValues.put(DatabaseForDemo.EMP_HOLDPRINTS,
				permissionsemployee.getmHoldPrintT());
		contentValues.put(DatabaseForDemo.EMP_CREDIT,
				permissionsemployee.getmCreditcardsT());
		contentValues.put(DatabaseForDemo.EMP_ENDCASH,
				permissionsemployee.getmEndCashT());
		dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.EMP_PERMISSIONS_TABLE, null,
				contentValues);
		contentValues.clear();
	//	if(Parameters.mfindServer_url){
			try {
			JSONObject data = new JSONObject();
			JSONObject jsonobj = new JSONObject();
			jsonobj.put(DatabaseForDemo.UNIQUE_ID,  randomNumber);
			jsonobj.put(DatabaseForDemo.CREATED_DATE, cuurentTime);
			jsonobj.put(DatabaseForDemo.MODIFIED_DATE,  cuurentTime);
			jsonobj.put(DatabaseForDemo.MODIFIED_IN, "Local");
			jsonobj.put(DatabaseForDemo.EMP_INVENTORY,
					permissionsemployee.getmInventoryT());
			jsonobj.put(DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID,
					emp_emp_Id);
			jsonobj.put(DatabaseForDemo.EMP_CUSTOMERS,
					permissionsemployee.getmCustomersT());
			jsonobj.put(DatabaseForDemo.EMP_REPORTS,
					permissionsemployee.getmReportsT());
			jsonobj.put(DatabaseForDemo.EMP_DISCOUNTS,
					permissionsemployee.getmDiscountsT());
			jsonobj.put(DatabaseForDemo.EMP_SETTINGS,
					permissionsemployee.getmSettingsT());
			jsonobj.put(DatabaseForDemo.EMP_PRICE,
					permissionsemployee.getmPriceChangesT());
			jsonobj.put(DatabaseForDemo.EMP_EXIT,
					permissionsemployee.getmAllowExitT());
			jsonobj.put(DatabaseForDemo.EMP_PAYOUTS,
					permissionsemployee.getmVendorPayoutsT());
			jsonobj.put(DatabaseForDemo.EMP_DELETE,
					permissionsemployee.getmDeleteItemsT());
			jsonobj.put(DatabaseForDemo.EMP_VOID,
					permissionsemployee.getmVoidInvoicesT());
			jsonobj.put(DatabaseForDemo.EMP_TRANSACTIONS,
					permissionsemployee.getmTransactionsT());
			jsonobj.put(DatabaseForDemo.EMP_HOLDPRINTS,
					permissionsemployee.getmHoldPrintT());
			jsonobj.put(DatabaseForDemo.EMP_CREDIT,
					permissionsemployee.getmCreditcardsT());
			jsonobj.put(DatabaseForDemo.EMP_ENDCASH,
					permissionsemployee.getmEndCashT());
			
			JSONArray fields = new JSONArray();
			fields.put(0, jsonobj);
				data.put("fields", fields);
				dataval = data.toString();
				System.out.println("data val is:"+dataval);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(Parameters.OriginalUrl.equals("")){
				System.out.println("there is no server url val");
			}else{
		boolean isnet = Parameters.isNetworkAvailable(getApplicationContext());
		if(isnet){
			new Thread(new Runnable() {
			    @Override
				public void run() {
    				JsonPostMethod jsonpost = new JsonPostMethod();
    				String response = jsonpost.postmethodfordirect("admin", "abcdefg", DatabaseForDemo.EMP_PERMISSIONS_TABLE, Parameters.currentTime(), Parameters.currentTime(), dataval, "");
    				System.out.println("response test is:"+response);
    				String servertiem = null;
    				try {
						JSONObject obj = new JSONObject(response);
						JSONObject responseobj = obj.getJSONObject("response");
						servertiem = responseobj.getString("server-time");
						System.out.println("servertime is:"+servertiem);
						JSONArray array = obj.getJSONArray("insert-queries");
						System.out.println("array list length for insert is:"+array.length());
						JSONArray array2 = obj.getJSONArray("delete-queries");
						System.out.println("array2 list length for delete is:"+array2.length());
						for(int jj = 0,ii = 0; jj<array2.length() && ii<array.length(); jj++,ii++){
							String deletequerytemp = array2.getString(jj);
							String deletequery1 = deletequerytemp.replace("'", "\"");
							String deletequery = deletequery1.replace("\\\"", "'");
							System.out.println("delete query"+jj+" is :"+deletequery);
							
							String insertquerytemp = array.getString(ii);
							String insertquery1 = insertquerytemp.replace("'", "\"");
							String insertquery = insertquery1.replace("\\\"", "'");
							System.out.println("delete query"+jj+" is :"+insertquery);
							
							dbforloginlogoutWriteEmployee.execSQL(deletequery);
							dbforloginlogoutWriteEmployee.execSQL(insertquery);
							System.out.println("queries executed"+ii);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			
					String select = "select *from "+DatabaseForDemo.MISCELLANEOUS_TABLE;
					Cursor cursor = dbforloginlogoutReadEmployee.rawQuery(select, null);
					if(cursor.getCount()>0){
						dbforloginlogoutWriteEmployee.execSQL("update "+DatabaseForDemo.MISCELLANEOUS_TABLE+" set "+DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL+"=\""+servertiem+"\"");
					}else{
						ContentValues contentValues1 = new ContentValues();
    					contentValues1.put(DatabaseForDemo.MISCEL_STORE,  "store1");
    					contentValues1.put(DatabaseForDemo.MISCEL_PAGEURL, "");
    					contentValues1.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL, Parameters.currentTime());
    					contentValues1.put(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL, Parameters.currentTime());
    					dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.MISCELLANEOUS_TABLE, null, contentValues1);
					}
    				dataval = "";
			    }
			  }).start();
		}else{
			
			ContentValues contentValues1 = new ContentValues();
			contentValues1.put(DatabaseForDemo.QUERY_TYPE,  "insert");
			contentValues1.put(DatabaseForDemo.PENDING_USER_ID, Parameters.userid);
			contentValues1.put(DatabaseForDemo.PAGE_URL,  "saveinfo.php");
			contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING, DatabaseForDemo.EMP_PERMISSIONS_TABLE);
			contentValues1.put(DatabaseForDemo.CURRENT_TIME_PENDING, Parameters.currentTime());
			contentValues1.put(DatabaseForDemo.PARAMETERS, dataval);
			dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null, contentValues1);
			dataval = "";
		}
		}
	//	}			
			
	
	}
	private void insertPersonalDetails() {
	
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseForDemo.UNIQUE_ID,  randomNumber);
		contentValues.put(DatabaseForDemo.CREATED_DATE, cuurentTime);
		contentValues.put(DatabaseForDemo.MODIFIED_DATE,  cuurentTime);
		contentValues.put(DatabaseForDemo.MODIFIED_IN, "Local");
		contentValues.put(DatabaseForDemo.EMP_NAME,
				personalemployee.getEmp_name());
		contentValues.put(DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID,
				emp_emp_Id);
		contentValues.put(DatabaseForDemo.EMP_ID,
				personalemployee.getEmp_id());
		contentValues.put(DatabaseForDemo.EMP_EMAIL,
				personalemployee.getEmp_email());
		contentValues.put(DatabaseForDemo.EMP_PHONE,
				personalemployee.getEmp_phone());
		contentValues.put(DatabaseForDemo.EMP_BIRTH,
				personalemployee.getEmp_birth());
		contentValues.put(DatabaseForDemo.EMP_ADDRESS,
				personalemployee.getEmp_address());
		contentValues.put(DatabaseForDemo.EMP_CITY,
				personalemployee.getEmp_city());
		contentValues.put(DatabaseForDemo.EMP_COUNTRY,
				personalemployee.getEmp_country());
		contentValues.put(DatabaseForDemo.EMP_STATE,
				personalemployee.getEmp_state());
		contentValues.put(DatabaseForDemo.EMP_POSTAL,
				personalemployee.getEmp_postal());
		dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.EMP_PERSONAL_TABLE, null,
				contentValues);
		contentValues.clear();
	//	if(Parameters.mfindServer_url){
			try {
			JSONObject data = new JSONObject();
			JSONObject jsonobj = new JSONObject();
			jsonobj.put(DatabaseForDemo.UNIQUE_ID,  randomNumber);
			jsonobj.put(DatabaseForDemo.CREATED_DATE, cuurentTime);
			jsonobj.put(DatabaseForDemo.MODIFIED_DATE,  cuurentTime);
			jsonobj.put(DatabaseForDemo.MODIFIED_IN, "Local");
			jsonobj.put(DatabaseForDemo.EMP_NAME,
					personalemployee.getEmp_name());
			jsonobj.put(DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID,
					emp_emp_Id);
			jsonobj.put(DatabaseForDemo.EMP_ID,
					personalemployee.getEmp_id());
			jsonobj.put(DatabaseForDemo.EMP_EMAIL,
					personalemployee.getEmp_email());
			jsonobj.put(DatabaseForDemo.EMP_PHONE,
					personalemployee.getEmp_phone());
			jsonobj.put(DatabaseForDemo.EMP_BIRTH,
					personalemployee.getEmp_birth());
			jsonobj.put(DatabaseForDemo.EMP_ADDRESS,
					personalemployee.getEmp_address());
			jsonobj.put(DatabaseForDemo.EMP_CITY,
					personalemployee.getEmp_city());
			jsonobj.put(DatabaseForDemo.EMP_COUNTRY,
					personalemployee.getEmp_country());
			jsonobj.put(DatabaseForDemo.EMP_STATE,
					personalemployee.getEmp_state());
			jsonobj.put(DatabaseForDemo.EMP_POSTAL,
					personalemployee.getEmp_postal());
			
			JSONArray fields = new JSONArray();
			fields.put(0, jsonobj);
				data.put("fields", fields);
				dataval = data.toString();
				System.out.println("data val is:"+dataval);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(Parameters.OriginalUrl.equals("")){
				System.out.println("there is no server url val");
			}else{

		boolean isnet = Parameters.isNetworkAvailable(getApplicationContext());
		if(isnet){
			new Thread(new Runnable() {
			    @Override
				public void run() {
    				JsonPostMethod jsonpost = new JsonPostMethod();
    				String response = jsonpost.postmethodfordirect("admin", "abcdefg", DatabaseForDemo.EMP_PERSONAL_TABLE, Parameters.currentTime(), Parameters.currentTime(), dataval, "");
    				String servertiem = null;
    				try {
						JSONObject obj = new JSONObject(response);
						JSONObject responseobj = obj.getJSONObject("response");
						servertiem = responseobj.getString("server-time");
						System.out.println("servertime is:"+servertiem);
						JSONArray array = obj.getJSONArray("insert-queries");
						System.out.println("array list length for insert is:"+array.length());
						JSONArray array2 = obj.getJSONArray("delete-queries");
						System.out.println("array2 list length for delete is:"+array2.length());
						for(int jj = 0,ii = 0; jj<array2.length() && ii<array.length(); jj++,ii++){
							String deletequerytemp = array2.getString(jj);
							String deletequery1 = deletequerytemp.replace("'", "\"");
							String deletequery = deletequery1.replace("\\\"", "'");
							System.out.println("delete query"+jj+" is :"+deletequery);
							
							String insertquerytemp = array.getString(ii);
							String insertquery1 = insertquerytemp.replace("'", "\"");
							String insertquery = insertquery1.replace("\\\"", "'");
							System.out.println("delete query"+jj+" is :"+insertquery);
							
							dbforloginlogoutWriteEmployee.execSQL(deletequery);
							dbforloginlogoutWriteEmployee.execSQL(insertquery);
							System.out.println("queries executed"+ii);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			
					String select = "select *from "+DatabaseForDemo.MISCELLANEOUS_TABLE;
					Cursor cursor = 		dbforloginlogoutReadEmployee.rawQuery(select, null);
					if(cursor.getCount()>0){
						dbforloginlogoutWriteEmployee.execSQL("update "+DatabaseForDemo.MISCELLANEOUS_TABLE+" set "+DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL+"=\""+servertiem+"\"");
						cursor.close();
					}else{
						cursor.close();
						ContentValues contentValues1 = new ContentValues();
    					contentValues1.put(DatabaseForDemo.MISCEL_STORE,  "store1");
    					contentValues1.put(DatabaseForDemo.MISCEL_PAGEURL, "");
    					contentValues1.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL, Parameters.currentTime());
    					contentValues1.put(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL, Parameters.currentTime());
    					dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.MISCELLANEOUS_TABLE, null, contentValues1);
					}
    				dataval = "";
			    }
			  }).start();
		}else{
			
			ContentValues contentValues1 = new ContentValues();
			contentValues1.put(DatabaseForDemo.QUERY_TYPE,  "insert");
			contentValues1.put(DatabaseForDemo.PENDING_USER_ID, Parameters.userid);
			contentValues1.put(DatabaseForDemo.PAGE_URL,  "saveinfo.php");
			contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING, DatabaseForDemo.EMP_PERSONAL_TABLE);
			contentValues1.put(DatabaseForDemo.CURRENT_TIME_PENDING, Parameters.currentTime());
			contentValues1.put(DatabaseForDemo.PARAMETERS, dataval);
			dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null, contentValues1);
			dataval = "";
		}
		}
	//	}			
	}
	private void insertPeyrollDetails() {
		
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseForDemo.UNIQUE_ID,  randomNumber);
		contentValues.put(DatabaseForDemo.CREATED_DATE, cuurentTime);
		contentValues.put(DatabaseForDemo.MODIFIED_DATE,  cuurentTime);
		contentValues.put(DatabaseForDemo.MODIFIED_IN, "Local");
		contentValues.put(DatabaseForDemo.EMP_FEDERAL,
				payrollemployee.getFederal());
		contentValues.put(DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID,
				emp_emp_Id);
		contentValues.put(DatabaseForDemo.EMP_AMOUNT,
				payrollemployee.getAmount());
		contentValues.put(DatabaseForDemo.EMP_STATEA,
				payrollemployee.getStatea());
		contentValues.put(DatabaseForDemo.EMP_STATEAMOUNT,
				payrollemployee.getStateAmount());
		contentValues.put(DatabaseForDemo.EMP_CREDITS,
				payrollemployee.getCredits());
		//contentValues.put(SQLITEDatabase.EMP_FILLINGSTATUS,payrollemployee.getFilingstatus());
		//contentValues.put(SQLITEDatabase.EMP_EXEMPT,payrollemployee.getExempt());
		contentValues.put(DatabaseForDemo.EMP_FILLINGSTATUS,"fill");
		contentValues.put(DatabaseForDemo.EMP_EXEMPT,"exe");
		contentValues.put(DatabaseForDemo.EMP_EXCLUDECHECK,
				payrollemployee.getExcludeCheck());
		dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.EMP_PAYROLL_TABLE, null,
				contentValues);
		contentValues.clear();
	//	if(Parameters.mfindServer_url){
			try {
			JSONObject data = new JSONObject();
			JSONObject jsonobj = new JSONObject();
			jsonobj.put(DatabaseForDemo.CREATED_DATE, cuurentTime);
			jsonobj.put(DatabaseForDemo.MODIFIED_DATE,  cuurentTime);
			jsonobj.put(DatabaseForDemo.MODIFIED_IN, "Local");
			jsonobj.put(DatabaseForDemo.EMP_FEDERAL,
					payrollemployee.getFederal());
			jsonobj.put(DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID,
					emp_emp_Id);
			jsonobj.put(DatabaseForDemo.EMP_AMOUNT,
					payrollemployee.getAmount());
			jsonobj.put(DatabaseForDemo.EMP_STATEA,
					payrollemployee.getStatea());
			jsonobj.put(DatabaseForDemo.EMP_STATEAMOUNT,
					payrollemployee.getStateAmount());
			jsonobj.put(DatabaseForDemo.EMP_CREDITS,
					payrollemployee.getCredits());
			//jsonobj.put(SQLITEDatabase.EMP_FILLINGSTATUS,payrollemployee.getFilingstatus());
			//jsonobj.put(SQLITEDatabase.EMP_EXEMPT,payrollemployee.getExempt());
			jsonobj.put(DatabaseForDemo.EMP_FILLINGSTATUS,"fill");
			jsonobj.put(DatabaseForDemo.EMP_EXEMPT,"exe");
			jsonobj.put(DatabaseForDemo.EMP_EXCLUDECHECK,
					payrollemployee.getExcludeCheck());
			
			JSONArray fields = new JSONArray();
			fields.put(0, jsonobj);
				data.put("fields", fields);
				dataval = data.toString();
				System.out.println("data val is:"+dataval);
			} catch (JSONException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
			if(Parameters.OriginalUrl.equals("")){
				System.out.println("there is no server url val");
			}else{

		boolean isnet = Parameters.isNetworkAvailable(getApplicationContext());
		if(isnet){
			new Thread(new Runnable() {
			    @Override
				public void run() {
    				JsonPostMethod jsonpost = new JsonPostMethod();
    				String response = jsonpost.postmethodfordirect("admin", "abcdefg", DatabaseForDemo.EMP_PAYROLL_TABLE, Parameters.currentTime(), Parameters.currentTime(), dataval, "");
    				System.out.println("response test is:"+response);
    				String servertiem = null;
    				try {
						JSONObject obj = new JSONObject(response);
						JSONObject responseobj = obj.getJSONObject("response");
						servertiem = responseobj.getString("server-time");
						System.out.println("servertime is:"+servertiem);
						JSONArray array = obj.getJSONArray("insert-queries");
						System.out.println("array list length for insert is:"+array.length());
						JSONArray array2 = obj.getJSONArray("delete-queries");
						System.out.println("array2 list length for delete is:"+array2.length());
						for(int jj = 0,ii = 0; jj<array2.length() && ii<array.length(); jj++,ii++){
							String deletequerytemp = array2.getString(jj);
							String deletequery1 = deletequerytemp.replace("'", "\"");
							String deletequery = deletequery1.replace("\\\"", "'");
							System.out.println("delete query"+jj+" is :"+deletequery);
							
							String insertquerytemp = array.getString(ii);
							String insertquery1 = insertquerytemp.replace("'", "\"");
							String insertquery = insertquery1.replace("\\\"", "'");
							System.out.println("delete query"+jj+" is :"+insertquery);
							
							dbforloginlogoutWriteEmployee.execSQL(deletequery);
							dbforloginlogoutWriteEmployee.execSQL(insertquery);
							System.out.println("queries executed"+ii);
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
    			
					String select = "select *from "+DatabaseForDemo.MISCELLANEOUS_TABLE;
					Cursor cursor = 		dbforloginlogoutReadEmployee.rawQuery(select, null);
					if(cursor.getCount()>0){
						dbforloginlogoutWriteEmployee.execSQL("update "+DatabaseForDemo.MISCELLANEOUS_TABLE+" set "+DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL+"=\""+servertiem+"\"");
						cursor.close();
					}else{
						cursor.close();
						ContentValues contentValues1 = new ContentValues();
    					contentValues1.put(DatabaseForDemo.MISCEL_STORE,  "store1");
    					contentValues1.put(DatabaseForDemo.MISCEL_PAGEURL, "");
    					contentValues1.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL, Parameters.currentTime());
    					contentValues1.put(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL, Parameters.currentTime());
    					dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.MISCELLANEOUS_TABLE, null, contentValues1);
					}
    				dataval = "";
			    }
			  }).start();
		}else{
			ContentValues contentValues1 = new ContentValues();
			contentValues1.put(DatabaseForDemo.QUERY_TYPE,  "insert");
			contentValues1.put(DatabaseForDemo.PENDING_USER_ID, Parameters.userid);
			contentValues1.put(DatabaseForDemo.PAGE_URL,  "saveinfo.php");
			contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING, DatabaseForDemo.EMP_PAYROLL_TABLE);
			contentValues1.put(DatabaseForDemo.CURRENT_TIME_PENDING, Parameters.currentTime());
			contentValues1.put(DatabaseForDemo.PARAMETERS, dataval);
			dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null, contentValues1);
			dataval = "";
		}
		}
	//	}			
	}
	private void storeInfoDetails() {
		
		
ContentValues contentValues = new ContentValues();
contentValues.put(DatabaseForDemo.UNIQUE_ID,  randomNumber);
contentValues.put(DatabaseForDemo.CREATED_DATE, cuurentTime);
contentValues.put(DatabaseForDemo.MODIFIED_DATE,  cuurentTime);
contentValues.put(DatabaseForDemo.MODIFIED_IN, "Local");
contentValues.put(DatabaseForDemo.EMP_STORE_ID,item_store_id);
contentValues.put(DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID,emp_emp_Id);
contentValues.put(DatabaseForDemo.EMP_STORE_NAME,item_store_name);		
dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.EMP_STORE_TABLE, null,
contentValues);
contentValues.clear();
//if(Parameters.mfindServer_url){
	try {
	JSONObject data = new JSONObject();
	JSONObject jsonobj = new JSONObject();
	jsonobj.put(DatabaseForDemo.UNIQUE_ID,  randomNumber);
	jsonobj.put(DatabaseForDemo.CREATED_DATE, cuurentTime);
	jsonobj.put(DatabaseForDemo.MODIFIED_DATE,  cuurentTime);
	jsonobj.put(DatabaseForDemo.MODIFIED_IN, "Local");
	jsonobj.put(DatabaseForDemo.EMP_STORE_ID,item_store_id);
	jsonobj.put(DatabaseForDemo.EMPLOYEE_EMPLOYEE_ID,emp_emp_Id);
	jsonobj.put(DatabaseForDemo.EMP_STORE_NAME,item_store_name);	
	
	JSONArray fields = new JSONArray();
	fields.put(0, jsonobj);
		data.put("fields", fields);
		dataval = data.toString();
		System.out.println("data val is:"+dataval);
	} catch (JSONException e1) {
		// TODO Auto-generated catch block
		e1.printStackTrace();
	}
	
	if(Parameters.OriginalUrl.equals("")){
		System.out.println("there is no server url val");
	}else{

boolean isnet = Parameters.isNetworkAvailable(getApplicationContext());
if(isnet){
	new Thread(new Runnable() {
	    @Override
		public void run() {
			JsonPostMethod jsonpost = new JsonPostMethod();
			String response = jsonpost.postmethodfordirect("admin", "abcdefg", DatabaseForDemo.EMP_STORE_TABLE, Parameters.currentTime(), Parameters.currentTime(), dataval, "");
			System.out.println("response test is:"+response);
			String servertiem = null;
			try {
				JSONObject obj = new JSONObject(response);
				JSONObject responseobj = obj.getJSONObject("response");
				servertiem = responseobj.getString("server-time");
				System.out.println("servertime is:"+servertiem);
				JSONArray array = obj.getJSONArray("insert-queries");
				System.out.println("array list length for insert is:"+array.length());
				JSONArray array2 = obj.getJSONArray("delete-queries");
				System.out.println("array2 list length for delete is:"+array2.length());
				for(int jj = 0,ii = 0; jj<array2.length() && ii<array.length(); jj++,ii++){
					String deletequerytemp = array2.getString(jj);
					String deletequery1 = deletequerytemp.replace("'", "\"");
					String deletequery = deletequery1.replace("\\\"", "'");
					System.out.println("delete query"+jj+" is :"+deletequery);
					
					String insertquerytemp = array.getString(ii);
					String insertquery1 = insertquerytemp.replace("'", "\"");
					String insertquery = insertquery1.replace("\\\"", "'");
					System.out.println("delete query"+jj+" is :"+insertquery);
					
					dbforloginlogoutWriteEmployee.execSQL(deletequery);
					dbforloginlogoutWriteEmployee.execSQL(insertquery);
					System.out.println("queries executed"+ii);
				}
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
			String select = "select *from "+DatabaseForDemo.MISCELLANEOUS_TABLE;
			Cursor cursor =dbforloginlogoutReadEmployee.rawQuery(select, null);
			if(cursor.getCount()>0){
				dbforloginlogoutWriteEmployee.execSQL("update "+DatabaseForDemo.MISCELLANEOUS_TABLE+" set "+DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL+"=\""+servertiem+"\"");
				cursor.close();
			}else{
				cursor.close();
				ContentValues contentValues1 = new ContentValues();
				contentValues1.put(DatabaseForDemo.MISCEL_STORE,  "store1");
				contentValues1.put(DatabaseForDemo.MISCEL_PAGEURL, "");
				contentValues1.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL, Parameters.currentTime());
				contentValues1.put(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL, Parameters.currentTime());
				dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.MISCELLANEOUS_TABLE, null, contentValues1);
			}
			dataval = "";
	    }
	  }).start();
}else{
	ContentValues contentValues1 = new ContentValues();
	contentValues1.put(DatabaseForDemo.QUERY_TYPE,  "insert");
	contentValues1.put(DatabaseForDemo.PENDING_USER_ID, Parameters.userid);
	contentValues1.put(DatabaseForDemo.PAGE_URL,  "saveinfo.php");
	contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING, DatabaseForDemo.EMP_STORE_TABLE);
	contentValues1.put(DatabaseForDemo.CURRENT_TIME_PENDING, Parameters.currentTime());
	contentValues1.put(DatabaseForDemo.PARAMETERS, dataval);
	dbforloginlogoutWriteEmployee.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null, contentValues1);
	dataval = "";
}
}
//}			
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
	void permissionsAll(String value){
		permissionsemployee.setmInventoryT(value);
		permissionsemployee.setmCustomersT(value);
		permissionsemployee.setmReportsT(value);
		permissionsemployee.setmDiscountsT(value);
		permissionsemployee.setmSettingsT(value);
		permissionsemployee.setmPriceChangesT(value);
		permissionsemployee.setmAllowExitT(value);
		permissionsemployee.setmVendorPayoutsT(value);
		permissionsemployee.setmDeleteItemsT(value);
		permissionsemployee.setmVoidInvoicesT(value);
		permissionsemployee.setmTransactionsT(value);
		permissionsemployee.setmHoldPrintT(value);
		permissionsemployee.setmCreditcardsT(value);
		permissionsemployee.setmEndCashT(value);
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		sqlDBEmployee.close();
		super.onDestroy();
	}
}
