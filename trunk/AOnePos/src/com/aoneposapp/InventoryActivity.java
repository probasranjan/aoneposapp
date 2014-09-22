package com.aoneposapp;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.net.ParseException;
import android.os.Bundle;
import android.os.StrictMode;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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
import android.widget.SlidingDrawer;
import android.widget.SlidingDrawer.OnDrawerCloseListener;
import android.widget.SlidingDrawer.OnDrawerOpenListener;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import android.widget.Toast;

import com.aoneposapp.adapters.ImageAdapterForCategory;
import com.aoneposapp.adapters.ImageAdapterForDepartment;
import com.aoneposapp.adapters.ImageAdapterForProduct;
import com.aoneposapp.adapters.ImageAdapterForSalesHistory;
import com.aoneposapp.adapters.ImageAdapterForVendor;
import com.aoneposapp.adapters.ModifierIndividualItemAdapter;
import com.aoneposapp.adapters.OrderingInfoIndividualVendorAdapter;
import com.aoneposapp.adapters.PrinterArrayAdapter;
import com.aoneposapp.adapters.SkuArrayAdapter;
import com.aoneposapp.utils.DatabaseForDemo;
import com.aoneposapp.utils.JsonPostMethod;
import com.aoneposapp.utils.Parameters;
import com.aoneposapp.utils.ServerSyncClass;

public class InventoryActivity extends Activity implements
		android.view.View.OnClickListener,
		ImageAdapterForCategory.OnWidgetItemClicked,
		ImageAdapterForDepartment.OnWidgetItemClicked,
		ImageAdapterForVendor.OnWidgetItemClicked,
		ImageAdapterForProduct.OnWidgetItemClicked,
		SkuArrayAdapter.OnWidgetItemClicked,
		ModifierIndividualItemAdapter.OnWidgetItemClicked,
		OrderingInfoIndividualVendorAdapter.OnWidgetItemClicked,
		PrinterArrayAdapter.OnWidgetItemClicked {
	SlidingDrawer slidingDrawer;
	ImageView slideButton;
	ImageView image0;
	ImageView image1;
	ImageView image2;
	ImageView image3;
	ImageView image4;
	ImageView image5;
	ImageView image6;
	ImageView image7;
	ImageView image8;
	ImageView logout;
	ImageView logout2;
	int height;
	int wwidth;
	
	DecimalFormat df = new DecimalFormat("#.##");
	String offsetvals = "1";
	String uniqueidforprodedit = "";
	boolean status = true, statusforvendor = true, statusfortrue = true,
			statusforprinter = true;
	ListView skuslist;
	ListView modifierlist, vendorlist, printerlistview, saleslist;
	Button addcat, viewcat, savecat, cancelcat, product, category, vendor,
			department, optionalinfo, notes, modifiers, orderinginfo,
			saleshistory, printers, go_button;
	LinearLayout ll_add, ll_view;
	CheckBox tax1, tax2, tax3, bartax, tax1dept, tax2dept, tax3dept,
			bartaxdept, foodcheck;
	String modifieritemval = "", countthisitemval = "", printonreceiptval = "",
			allowbuybackval = "", foodstampableval = "", notesval = "",
			promptpriceval = "", printerval = "";
	String skuval = "", bonuspointsval = "", barcodeval = "",
			commissionval = "", locationval = "", unitsizeval = "",
			unittypeval = "";
	int commissionspinnerval = 0, printerposition = 0;
	String dataval = "", datavalforproduct1 = "", datavalforproduct2 = "",
			datavalforproduct3 = "", datavalforproduct4 = "",
			datavalforsku = "", datavalformodifier = "",
			datavalforordering = "";
	String printervalfordept = "", foodcheckval = "",
			taxvalforsavefordept = "", taxvalforsaveforprod = "",
			taxvalforsaveforduplicate = "", timeval = "";
	double taxvalforprod = 0, taxvalforduplicate = 0;
	String totalrecordselectQuery = "SELECT  * FROM "
			+ DatabaseForDemo.INVENTORY_TABLE;
	Spinner categoryidspinner, poMethod, departmentspinner, vendorspinner,
			printerspinner, printerspinnerminutes, printerspinnerseconds;
	EditText categoryid, categorydesc, deptid, deptdesc, v_no, v_terms,
			v_minorder, v_commission, v_company, v_flatrent, v_tax, v_billable,
			v_social, v_street, v_extended, v_city, v_state, v_zip, v_country,
			v_firstname, v_lastname, v_phone, v_fax, v_email, v_website,
			itemno, description1, description2, cost, pricecharge, pricetax,
			instock;
	DatabaseForDemo sqlDBInvetory;
	SQLiteDatabase dbforloginlogoutWriteInvetory,dbforloginlogoutReadInvetory;
	ListView list_View;
	ImageAdapterForProduct imageAdapterForProduct;
	String sortBy= "";
	boolean sortType = true;
	ArrayList<HashMap<String, String>> arrayList_Items;
	ArrayList<String> displayarray = new ArrayList<String>();
	ArrayList<String> pomethodList = new ArrayList<String>();
	ArrayList<String> no_data = new ArrayList<String>();
	ArrayList<String> terms_data = new ArrayList<String>();
	ArrayList<String> min_order_data = new ArrayList<String>();
	ArrayList<String> commission_data = new ArrayList<String>();
	ArrayList<String> company_data = new ArrayList<String>();
	ArrayList<String> rent_data = new ArrayList<String>();
	ArrayList<String> tax_data = new ArrayList<String>();
	ArrayList<String> billable_data = new ArrayList<String>();
	ArrayList<String> social_data = new ArrayList<String>();
	ArrayList<String> po_data = new ArrayList<String>();
	ArrayList<String> street_data = new ArrayList<String>();
	ArrayList<String> extended_data = new ArrayList<String>();
	ArrayList<String> city_data = new ArrayList<String>();
	ArrayList<String> state_data = new ArrayList<String>();
	ArrayList<String> zip_data = new ArrayList<String>();
	ArrayList<String> country_data = new ArrayList<String>();
	ArrayList<String> first_data = new ArrayList<String>();
	ArrayList<String> last_data = new ArrayList<String>();
	ArrayList<String> phone_data = new ArrayList<String>();
	ArrayList<String> fax_data = new ArrayList<String>();
	ArrayList<String> email_data = new ArrayList<String>();
	ArrayList<String> website_data = new ArrayList<String>();
	ArrayList<String> total_no_data = new ArrayList<String>();
	ArrayList<String> total_terms_data = new ArrayList<String>();
	ArrayList<String> total_min_order_data = new ArrayList<String>();
	ArrayList<String> total_commission_data = new ArrayList<String>();
	ArrayList<String> total_company_data = new ArrayList<String>();
	ArrayList<String> total_rent_data = new ArrayList<String>();
	ArrayList<String> total_tax_data = new ArrayList<String>();
	ArrayList<String> total_billable_data = new ArrayList<String>();
	ArrayList<String> total_social_data = new ArrayList<String>();
	ArrayList<String> total_po_data = new ArrayList<String>();
	ArrayList<String> total_street_data = new ArrayList<String>();
	ArrayList<String> total_extended_data = new ArrayList<String>();
	ArrayList<String> total_city_data = new ArrayList<String>();
	ArrayList<String> total_state_data = new ArrayList<String>();
	ArrayList<String> total_zip_data = new ArrayList<String>();
	ArrayList<String> total_country_data = new ArrayList<String>();
	ArrayList<String> total_first_data = new ArrayList<String>();
	ArrayList<String> total_last_data = new ArrayList<String>();
	ArrayList<String> total_phone_data = new ArrayList<String>();
	ArrayList<String> total_fax_data = new ArrayList<String>();
	ArrayList<String> total_email_data = new ArrayList<String>();
	ArrayList<String> total_website_data = new ArrayList<String>();
	ArrayList<String> id_data = new ArrayList<String>();
	ArrayList<String> desc_data = new ArrayList<String>();
	ArrayList<String> total_id_data = new ArrayList<String>();
	ArrayList<String> total_desc_data = new ArrayList<String>();
	ArrayList<String> cat_data = new ArrayList<String>();
	ArrayList<String> total_cat_data = new ArrayList<String>();
	ArrayList<String> itemno_data = new ArrayList<String>();
	ArrayList<String> itemdesc_data = new ArrayList<String>();
	ArrayList<String> pricecharge_data = new ArrayList<String>();
	ArrayList<String> stock_data = new ArrayList<String>();
	ArrayList<String> desc2_data = new ArrayList<String>();
	ArrayList<String> vendor_data = new ArrayList<String>();
	ArrayList<String> departmentforproduct_data = new ArrayList<String>();
	ArrayList<String> cost_data = new ArrayList<String>();
	ArrayList<String> pricetax_data = new ArrayList<String>();
	ArrayList<String> tax_product_data = new ArrayList<String>();
	ArrayList<String> total_itemno_data = new ArrayList<String>();
	ArrayList<String> total_itemdesc_data = new ArrayList<String>();
	ArrayList<String> total_pricecharge_data = new ArrayList<String>();
	ArrayList<String> total_stock_data = new ArrayList<String>();
	ArrayList<String> total_desc2_data = new ArrayList<String>();
	ArrayList<String> total_vendor_data = new ArrayList<String>();
	ArrayList<String> total_departmentforproduct_data = new ArrayList<String>();
	ArrayList<String> total_cost_data = new ArrayList<String>();
	ArrayList<String> total_pricetax_data = new ArrayList<String>();
	ArrayList<String> total_tax_product_data = new ArrayList<String>();
	ArrayList<String> printerlistfordisplay = new ArrayList<String>();
	ArrayList<String> autoTextStrings = new CustomStringList3();
	ArrayList<String> autoTextStringsname = new CustomStringList3();
	ArrayList<String> autoTextStringsvendor = new CustomStringList3();
	private Spinner catspr, dptspr, displayspr;
	private AutoCompleteTextView actv;
	int pagenum = 1, prevLength = 0;
	public static int pagecount = 20;
	public static int testforid = 1;
	int stLoop = 0;
	int endLoop = 0;
	int totalcount = 0;
	LinearLayout ll;
	int j;
	LinearLayout catlayout, deptlayout, vendorlayout, productlayout, detailslayout;
	ContentValues optional_info = new ContentValues();
	ArrayList<String> skuarray = new ArrayList<String>();
	ArrayList<String> taxlist = new ArrayList<String>();
	ArrayList<HashMap<String, String>> modifierindividualitemsarray = new ArrayList<HashMap<String, String>>();
	ArrayList<HashMap<String, String>> pricingvendorlistinorderinginfo = new ArrayList<HashMap<String, String>>();
	ArrayList<String> modifiersArrayserver = new ArrayList<String>();
	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.inventory_activity);
		Parameters.ServerSyncTimer();
		Parameters.printerContext=InventoryActivity.this;
		try{
		sqlDBInvetory=new DatabaseForDemo(InventoryActivity.this);
		dbforloginlogoutWriteInvetory = sqlDBInvetory.getWritableDatabase();
		dbforloginlogoutReadInvetory = sqlDBInvetory.getReadableDatabase();
		
		/*dbforloginlogoutWriteInvetory.execSQL("update "
				+ DatabaseForDemo.INVENTORY_TABLE + " set "
				+ DatabaseForDemo.INVENTORY_QUANTITY + "=\"0"
				+ "\" where "
				+ DatabaseForDemo.INVENTORY_QUANTITY + "=\"\"");*/
		String query = "select *from "+DatabaseForDemo.TAX_TABLE;
		
		Cursor taxcursor = dbforloginlogoutReadInvetory.rawQuery(query, null);
		if(taxcursor.getCount()>0){
			if(taxcursor!=null){
				if(taxcursor.moveToFirst()){
					do{
						if(taxcursor.isNull(taxcursor.getColumnIndex(DatabaseForDemo.TAX_NAME))){
							
						}else{
							taxlist.add(taxcursor.getString(taxcursor.getColumnIndex(DatabaseForDemo.TAX_NAME)));
						}
						/*if(taxcursor.isNull(taxcursor.getColumnIndex(DatabaseForDemo.TAX_VALUE))){
							
						}else{
							
						}*/
					}while(taxcursor.moveToNext());
				}
			}
		}
		taxcursor.close();
		if (android.os.Build.VERSION.SDK_INT > 9) {
			StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder() .permitAll().build();
			StrictMode.setThreadPolicy(policy);
		}
		Parameters.hai = false;
		// code for server synchronization call
	//	if (Parameters.hai) {
			if (Parameters.OriginalUrl.equals("")) {
			} else {
				boolean isnet = Parameters.isNetworkAvailable(InventoryActivity.this);
				if (isnet) {
					ServerSyncClass synccall = new ServerSyncClass();
					try {
						synccall.generalMethod();
					} catch (ParseException e2) {
						// TODO Auto-generated catch block
						e2.printStackTrace();
					} catch (java.text.ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

				} else {

				}
		//	}
		}
	//	Parameters.hai = true;

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
		product = (Button) findViewById(R.id.product);
		category = (Button) findViewById(R.id.category);
		department = (Button) findViewById(R.id.department);
		vendor = (Button) findViewById(R.id.vendors);
		TextView loginnameempid = (TextView)findViewById(R.id.loginnameval);
		loginnameempid.setText(Parameters.usertypeloginvalue);
		detailslayout = (LinearLayout) findViewById(R.id.inflatingLayout);

		image0.setOnClickListener(this);
		image1.setOnClickListener(this);
		image2.setOnClickListener(this);
		image3.setOnClickListener(this);
		image4.setOnClickListener(this);
		image5.setOnClickListener(this);
		image6.setOnClickListener(this);
		image7.setOnClickListener(this);
		image8.setOnClickListener(this);

		image0.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(InventoryActivity.this, PosMainActivity.class);
				startActivity(intent1);
				finish();
			}
		});
		image1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

			}
		});
		image2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(InventoryActivity.this, StoresActivity.class);
				startActivity(intent1);
				finish();
			}
		});
		image3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Parameters.customer_permission) {
					Intent intent1 = new Intent(InventoryActivity.this, CustomerActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog( InventoryActivity.this, "Sorry", "You are not authenticated to perform this operation.", false);
				}
			}
		});
		image4.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(InventoryActivity.this, EmployeeActivity.class);
				startActivity(intent1);
				finish();
			}
		});
		image5.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Parameters.reports_permission) {
					Intent intent1 = new Intent(InventoryActivity.this, ReportsActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog( InventoryActivity.this, "Sorry", "You are not authenticated to perform this operation.", false);
				}
			}
		});
		image6.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (Parameters.settings_permission) {
					Intent intent1 = new Intent(InventoryActivity.this, SettingsActivity.class);
					startActivity(intent1);
					finish();
				} else {
					showAlertDialog( InventoryActivity.this, "Sorry", "You are not authenticated to perform this operation.", false);
				}
			}
		});
		image7.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent1 = new Intent(InventoryActivity.this, ContactsActivity.class);
				startActivity(intent1);
				finish();
			}
		});
		image8.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Parameters.usertype.equals("admin")){
				Intent intent1 = new Intent(InventoryActivity.this, ProfileActivity.class);
				startActivity(intent1);
				finish();
				} else {
					showAlertDialog( InventoryActivity.this, "Sorry", "You are not authenticated to perform this operation.", false);
				}
			}
		});

		catspr = (Spinner) findViewById(R.id.spinner1);
		displayspr = (Spinner) findViewById(R.id.displayspinner);
		dptspr = (Spinner) findViewById(R.id.spinner2);
		go_button = (Button) findViewById(R.id.button3);
		actv = (AutoCompleteTextView) findViewById(R.id.editTextfield);

		final ArrayList<String> deptspinnerdataSearch = new ArrayList<String>();
		final ArrayList<String> catspinnerdataSearch = new ArrayList<String>();
		deptspinnerdataSearch.add("All");
		catspinnerdataSearch.add("All");

		Cursor mCursor1 = dbforloginlogoutReadInvetory.rawQuery( "select " + DatabaseForDemo.DepartmentID + " from " + DatabaseForDemo.DEPARTMENT_TABLE, null);
		if (mCursor1 != null) {
			if (mCursor1.moveToFirst()) {
				do {
					String catid = mCursor1.getString(mCursor1 .getColumnIndex(DatabaseForDemo.DepartmentID));
					deptspinnerdataSearch.add(catid);
				} while (mCursor1.moveToNext());
			}
		} else {
			Toast.makeText(getApplicationContext(), "there is no data", Toast.LENGTH_SHORT).show();
		}
		mCursor1.close();
		final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>( InventoryActivity.this, android.R.layout.simple_spinner_item, deptspinnerdataSearch);
		adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
		dptspr.setAdapter(adapter1);

		Cursor mCursor3 = dbforloginlogoutReadInvetory.rawQuery( "select " + DatabaseForDemo.CategoryId + " from " + DatabaseForDemo.CATEGORY_TABLE, null);
		System.out.println(mCursor3);
		if (mCursor3 != null) {
			if (mCursor3.moveToFirst()) {
				do {
					String catid = mCursor3.getString(mCursor3 .getColumnIndex(DatabaseForDemo.CategoryId));
					catspinnerdataSearch.add(catid);
				} while (mCursor3.moveToNext());
			}
		} else {
			Toast.makeText(getApplicationContext(), "there is no data", Toast.LENGTH_SHORT).show();
		}
		mCursor3.close();
		gettingCount();
		ArrayList<String> autoTextStringsItems = new ArrayList<String>();
		autoTextStringsItems.addAll(autoTextStrings);
		autoTextStringsItems .addAll(autoTextStringsname);
		autoTextStringsItems .addAll(autoTextStringsvendor);

		ArrayAdapter<String> adapterauto = new ArrayAdapter<String>( InventoryActivity.this, android.R.layout.select_dialog_item, autoTextStringsItems);
		actv.setThreshold(1);
		actv.setAdapter(adapterauto);
		System.out.println("1111111111111111111");
		actv.setTextColor(Color.BLACK);
		
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>( InventoryActivity.this, android.R.layout.simple_spinner_item, catspinnerdataSearch);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
		catspr.setAdapter(adapter2);
		
		catspr.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				displayarray.clear();
				ArrayAdapter<String> adapter2 = new ArrayAdapter<String>( InventoryActivity.this, android.R.layout.simple_spinner_item, displayarray);
				adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
				displayspr.setAdapter(adapter2);
				deptspinnerdataSearch.clear();
				deptspinnerdataSearch.add("All");
				String urlval = catspr.getSelectedItem().toString();
				String qqqqqq="select " + DatabaseForDemo.DepartmentID + " from " + DatabaseForDemo.DEPARTMENT_TABLE;
				if(!urlval.equals("All")){

					qqqqqq="select " + DatabaseForDemo.DepartmentID + " from " + DatabaseForDemo.DEPARTMENT_TABLE +" where "+DatabaseForDemo.CategoryForDepartment+ "=\"" + urlval + "\"";
				}
				Cursor mCursor = dbforloginlogoutReadInvetory.rawQuery(qqqqqq, null);
				// startManagingCursor(mCursor);
				System.out.println(mCursor);
				if (mCursor != null) {
					if (mCursor.moveToFirst()) {
						do {
							String catid = mCursor.getString(mCursor .getColumnIndex(DatabaseForDemo.DepartmentID));
							deptspinnerdataSearch.add(catid);
						} while (mCursor.moveToNext());
					}
				}
				mCursor.close();
				adapter1.setNotifyOnChange(true);
				adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
				dptspr.setAdapter(adapter1);
				return false;
			}
		});
		dptspr.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				displayarray.clear();
				ArrayAdapter<String> adapter2 = new ArrayAdapter<String>( InventoryActivity.this, android.R.layout.simple_spinner_item, displayarray);
				adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
				displayspr.setAdapter(adapter2);
				return false;
			}
		});

		go_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				itemno_data.clear();
				itemdesc_data.clear();
				pricecharge_data.clear();
				stock_data.clear();
				desc2_data.clear();
				vendor_data.clear();
				departmentforproduct_data.clear();
				cost_data.clear();
				pricetax_data.clear();
				tax_product_data.clear();
				total_itemno_data.clear();
				total_itemdesc_data.clear();
				total_pricecharge_data.clear();
				total_stock_data.clear();
				total_desc2_data.clear();
				total_vendor_data.clear();
				total_departmentforproduct_data.clear();
				total_cost_data.clear();
				total_pricetax_data.clear();
				total_tax_product_data.clear();
				ll.removeAllViews();
				String offsetvalingo = null, limitvalingo = "100", pages = null;
				System.out.println("5555555555555555555555");
				if (displayarray.size() == 0) {
					System.out.println("val is null in go");
					offsetvalingo = "1";
				} else {
					pages = displayspr.getSelectedItem().toString();
					System.out .println("pages offset val is///////////" + pages);
					String[] a = pages.split("-");
					System.out.println("the array values are:" + a.length);
					for (int i = 0; i < a.length; i++) {
						offsetvalingo = a[0].trim();
						System.out.println("offset val is///////////:" + offsetvalingo);
					}
				}
				System.out.println("66666666666666666666666666");
				String str = actv.getText().toString().trim();
				String selectQuery = null;
				System.out.println("auto text view val is:" + str);
				if (str.length() > 0)
				{
					if(containsCaseInsensitive(str, autoTextStrings))
//					if (autoTextStrings.contains(str))
					{
						selectQuery = "SELECT  * FROM "
								+ DatabaseForDemo.INVENTORY_TABLE + " where "
								+ DatabaseForDemo.INVENTORY_ITEM_NO + " like \""
								+ str + "%\"";
						System.out.println("selectQuery111: "+selectQuery);
					}
					else
					{
						if(containsCaseInsensitive(str, autoTextStringsname))
//						if (autoTextStringsname.contains(str))
						{
							selectQuery = "SELECT  * FROM "
									+ DatabaseForDemo.INVENTORY_TABLE
									+ " where "
									+ DatabaseForDemo.INVENTORY_ITEM_NAME
									+ " like \"" + str + "%\"";
							System.out.println("selectQuery222: "+selectQuery);
						}
						else 
						{
							selectQuery = "SELECT  * FROM "
									+ DatabaseForDemo.ORDERING_INFO_TABLE
									+ " where " + DatabaseForDemo.VENDERPART_NO
									+ " like \"" + str + "%\"";
							System.out.println("selectQuery333: "+selectQuery);
						}

					}

				} 
				else 
				{
					String dp = dptspr.getSelectedItem().toString();
					String cat = catspr.getSelectedItem().toString();
					Log.d("dp", "" + dp);
					Log.d("cat", "" + cat);

					if (dp.equals("All") && cat.equals("All"))
					{
						selectQuery = "SELECT  * FROM " + DatabaseForDemo.INVENTORY_TABLE;
					} 
					else
					{
						if (!dp.equals("All") && !cat.equals("All"))
						{
							selectQuery = "SELECT  * FROM "
									+ DatabaseForDemo.INVENTORY_TABLE
									+ " where "
									+ DatabaseForDemo.INVENTORY_CATEGORY + " like \""
									+ cat + "%\" and "
									+ DatabaseForDemo.INVENTORY_DEPARTMENT
									+ " like \"" + dp + "%\"";
							System.out.println("selectQuery444: "+selectQuery);
						}

						if (dp.equals("All") && !cat.equals("All")) 
						{
//							selectQuery = "SELECT  * FROM "
//									+ DatabaseForDemo.INVENTORY_TABLE
//									+ " where "
//									+ DatabaseForDemo.INVENTORY_CATEGORY + " like \""
//									+ cat + "%\"";
							selectQuery = "SELECT  * FROM "
									+ DatabaseForDemo.INVENTORY_TABLE
									+ " where "
									+ DatabaseForDemo.INVENTORY_DEPARTMENT + " like \""
									+ cat + "%\"";
							
							System.out.println("selectQuery555: "+selectQuery);
						}
						if (!dp.equals("All") && cat.equals("All")) 
						{
							selectQuery = "SELECT  * FROM "
									+ DatabaseForDemo.INVENTORY_TABLE
									+ " where "
									+ DatabaseForDemo.INVENTORY_DEPARTMENT
									+ " like \"" + dp + "%\"";
							System.out.println("selectQuery666: "+selectQuery);
						}

					}

				}
				Log.v("ffff", selectQuery);
				Cursor mCursor = dbforloginlogoutReadInvetory.rawQuery( selectQuery, null);
				int totalnoofrecords = mCursor.getCount();
				if (mCursor != null) {
					if (mCursor.moveToFirst()) {
						do {
							String itemno = mCursor.getString(mCursor .getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NO));
							total_itemno_data.add(itemno);
							String itemname = mCursor.getString(mCursor .getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NAME));
							total_itemdesc_data.add(itemname);
							String desc2 = mCursor.getString(mCursor .getColumnIndex(DatabaseForDemo.INVENTORY_SECOND_DESCRIPTION));
							total_desc2_data.add(desc2);
							String pricecharge = mCursor.getString(mCursor .getColumnIndex(DatabaseForDemo.INVENTORY_PRICE_CHANGE));
							total_pricecharge_data.add(pricecharge);
							String instock = mCursor.getString(mCursor .getColumnIndex(DatabaseForDemo.INVENTORY_IN_STOCK));
							total_stock_data.add(instock);
						} while (mCursor.moveToNext());
					}
				}
				mCursor.close();
				System.out.println("first cursor is executed");
				for (int ii = 0; ii < total_itemno_data.size(); ii++) {

					String selectQueryforvendor = "SELECT  "
							+ DatabaseForDemo.VENDERPART_NO + " FROM "
							+ DatabaseForDemo.ORDERING_INFO_TABLE + " where "
							+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\""
							+ total_itemno_data.get(ii) + "\"" + " AND "
							+ DatabaseForDemo.PREFERRED + "=\"true\"";

					Cursor mCursorforvendor = dbforloginlogoutReadInvetory .rawQuery(selectQueryforvendor, null);
					if (mCursorforvendor.getCount() > 0) {
						if (mCursorforvendor != null) {
							if (mCursorforvendor.moveToFirst()) {
								do {
									if (mCursorforvendor.isNull(mCursorforvendor .getColumnIndex(DatabaseForDemo.VENDERPART_NO))) {

									} else {
										String vendornum = mCursorforvendor.getString(mCursorforvendor .getColumnIndex(DatabaseForDemo.VENDERPART_NO));
										System.out.println("vendro no is:" + vendornum);
										total_vendor_data.add(vendornum);
									}
								} while (mCursorforvendor.moveToNext());
							}
						}
					} else {
						total_vendor_data.add("");
					}
					mCursorforvendor.close();
				}
				
				displayarray.clear();
				System.out.println("total records val is:" + totalnoofrecords);
				int resultsperset = 100;
				int noofsets = 0;
				int min = 1;
				// if(totalnoofrecords>200){
				noofsets = totalnoofrecords / resultsperset;
				int reminder = totalnoofrecords % resultsperset;
				if (reminder > 0) {
					noofsets = noofsets + 1;
				}
				System.out.println("noofrecords:" + noofsets);
				for (int i = 1; i <= noofsets; i++) {
					String displaystring;
					if (i == noofsets) {
						displaystring = min + " - " + totalnoofrecords;
					} else {
						displaystring = min + " - " + (i * resultsperset);
					}
					min = (i * resultsperset) + 1;
					System.out.println("display string value is:" + displaystring);
					displayarray.add(displaystring);
				}

				System.out.println(displayarray);
				ArrayAdapter<String> adapter2 = new ArrayAdapter<String>( InventoryActivity.this, android.R.layout.simple_spinner_item, displayarray);
				adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
				displayspr.setAdapter(adapter2);

				if (selectQuery.equals("SELECT  * FROM " + DatabaseForDemo.INVENTORY_TABLE)) {
					listUpdateforproduct(offsetvalingo, "100", selectQuery);
					gettingCount();
					displayspr.setSelection(displayarray.indexOf(pages));
				} else {
					listUpdateforproduct(offsetvalingo, "100", selectQuery);
					displayspr.setSelection(displayarray.indexOf(pages));
				}
			}
		});

		product.setBackgroundResource(R.drawable.highlightedtopmenuitem);
		department.setBackgroundResource(R.drawable.toprightmenu);
		vendor.setBackgroundResource(R.drawable.toprightmenu);
		category.setBackgroundResource(R.drawable.toprightmenu);
		product.setTextColor(Color.BLACK);
		department.setTextColor(Color.WHITE);
		vendor.setTextColor(Color.WHITE);
		category.setTextColor(Color.WHITE);

		addcat = (Button) findViewById(R.id.addcat);
		viewcat = (Button) findViewById(R.id.viewcat);
		ll_add = (LinearLayout) findViewById(R.id.add_ll);
		ll_view = (LinearLayout) findViewById(R.id.view_ll);

		addcat.setBackgroundColor(Color.parseColor("#3c6586"));
		viewcat.setBackgroundColor(Color.parseColor("#cbcbcb"));
		ll_add.setVisibility(View.VISIBLE);
		ll_view.setVisibility(View.GONE);
		addcat.setTextColor(Color.WHITE);
		viewcat.setTextColor(Color.BLACK);

		list_View = (ListView) findViewById(R.id.listView1);
		ll = (LinearLayout) findViewById(R.id.linearLayout1);
		Button heading = (Button) findViewById(R.id.button2);
		heading.setText("Product Details");
		list_View.setItemsCanFocus(false);

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
				System.out.println("000000000000000000000");
				// TODO Auto-generated method stub
				addcat.setBackgroundColor(Color.parseColor("#cbcbcb"));
				viewcat.setBackgroundColor(Color.parseColor("#3c6586"));
				ll_add.setVisibility(View.GONE);
				ll_view.setVisibility(View.VISIBLE);
				addcat.setTextColor(Color.BLACK);
				viewcat.setTextColor(Color.WHITE);
				gettingCount();
				ArrayList<String> autoTextStringsItems = new ArrayList<String>();
				for (int i = 0; i < autoTextStrings.size(); i++)
				{
					if(!autoTextStringsItems.contains(autoTextStrings.get(i))){
						autoTextStringsItems.add(autoTextStrings.get(i));
					}
				}
				
				for (int i = 0; i < autoTextStringsname.size(); i++)
				{
					if(!autoTextStringsItems.contains(autoTextStringsname.get(i))){
						autoTextStringsItems.add(autoTextStringsname.get(i));
					}
				}
				
				for (int i = 0; i < autoTextStringsvendor.size(); i++)
				{
					if(!autoTextStringsItems.contains(autoTextStringsvendor.get(i))){
						autoTextStringsItems.add(autoTextStringsvendor.get(i));
					}
				}
				
//				autoTextStringsItems.addAll(autoTextStrings);
//				autoTextStringsItems.addAll(autoTextStringsname);
//				autoTextStringsItems.addAll(autoTextStringsvendor);

				ArrayAdapter<String> adapterauto = new ArrayAdapter<String>( InventoryActivity.this, android.R.layout.select_dialog_item, autoTextStringsItems);
				// Getting the instance of AutoCompleteTextView

				actv.setThreshold(1);// will start working from first character
				actv.setAdapter(adapterauto);// setting the adapter data into
												// the AutoCompleteTextView
				System.out.println("1111112222222222");
				actv.setTextColor(Color.BLACK);

				
				ArrayAdapter<String> adapter2 = new ArrayAdapter<String>( InventoryActivity.this, android.R.layout.simple_spinner_item, displayarray);
				adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
				displayspr.setAdapter(adapter2);

				listUpdateforproduct(offsetvals, "100", totalrecordselectQuery);
				
				((TextView)findViewById(R.id.itemname)).setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v) 
					{
						sortArrayList(arrayList_Items, "itemname");
						imageAdapterForProduct.notifyDataSetChanged();
					}
				});
				
				((TextView)findViewById(R.id.itemno)).setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v) 
					{
						sortArrayList(arrayList_Items, "itemno");
						imageAdapterForProduct.notifyDataSetChanged();
					}
				});
				
				((TextView)findViewById(R.id.price)).setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v) 
					{
						sortArrayList(arrayList_Items, "pricecharge");
						imageAdapterForProduct.notifyDataSetChanged();
					}
				});
				
				((TextView)findViewById(R.id.stock)).setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v) 
					{
						sortArrayList(arrayList_Items, "stock");
						imageAdapterForProduct.notifyDataSetChanged();
					}
				});
				
				((TextView)findViewById(R.id.desc2)).setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v) 
					{
						sortArrayList(arrayList_Items, "desc2");
						imageAdapterForProduct.notifyDataSetChanged();
					}
				});
				
				((TextView)findViewById(R.id.tv_vendor)).setOnClickListener(new OnClickListener()
				{
					@Override
					public void onClick(View v) 
					{
						sortArrayList(arrayList_Items, "vendor");
						imageAdapterForProduct.notifyDataSetChanged();
					}
				});
			}
		});
		
		departmentspinner = (Spinner) findViewById(R.id.departmentspinner);
		vendorspinner = (Spinner) findViewById(R.id.vendor);
		tax1 = (CheckBox) findViewById(R.id.checkBox1);
		tax2 = (CheckBox) findViewById(R.id.checkBox2);
		tax3 = (CheckBox) findViewById(R.id.checkBox3);
		bartax = (CheckBox) findViewById(R.id.checkBox4);
		itemno = (EditText) findViewById(R.id.item_no);
		description1 = (EditText) findViewById(R.id.description);
		description2 = (EditText) findViewById(R.id.description2);
		tax1.setText(taxlist.get(0));
		tax2.setText(taxlist.get(1));
		tax3.setText(taxlist.get(2));
		description2.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(description2.getWindowToken(), 0);
				}
				return false;
			}
		});
		cost = (EditText) findViewById(R.id.cost);
		pricecharge = (EditText) findViewById(R.id.price_charge);
		pricetax = (EditText) findViewById(R.id.price_tax);
		instock = (EditText) findViewById(R.id.instock);
		savecat = (Button) findViewById(R.id.savecat);
		cancelcat = (Button) findViewById(R.id.cancelcat);
		optionalinfo = (Button) findViewById(R.id.optionalinfo);
		notes = (Button) findViewById(R.id.notes);
		modifiers = (Button) findViewById(R.id.modifiers);
		orderinginfo = (Button) findViewById(R.id.orderinginfo);
		saleshistory = (Button) findViewById(R.id.saleshistory);
		printers = (Button) findViewById(R.id.printers);
		final ArrayList<String> deptspinnerdata = new ArrayList<String>();
		final ArrayList<String> vendorspinnerdata = new ArrayList<String>();
		deptspinnerdata.clear();
		vendorspinnerdata.clear();
		tax1.setChecked(true);

		// if(Parameters.usertype.equals("demo")){

		
		Cursor mCursor = dbforloginlogoutReadInvetory.rawQuery( "select " + DatabaseForDemo.DepartmentID + " from " + DatabaseForDemo.DEPARTMENT_TABLE, null);
		System.out.println(mCursor);
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					String catid = mCursor.getString(mCursor .getColumnIndex(DatabaseForDemo.DepartmentID));
					deptspinnerdata.add(catid);
				} while (mCursor.moveToNext());
			}
			
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>( InventoryActivity.this, android.R.layout.simple_spinner_item, deptspinnerdata);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			departmentspinner.setAdapter(adapter);
		} else {
			Toast.makeText(getApplicationContext(), "This is no profile data in demo login", Toast.LENGTH_SHORT) .show();
		}
		mCursor.close();
		/*
		 * Cursor mCursor2 =
		 * dbforloginlogoutReadInvetory.rawQuery("select "+DatabaseForDemo
		 * .VENDOR_NO+" from "+DatabaseForDemo.VENDOR_TABLE, null);
		 * System.out.println(mCursor2); if(mCursor2!=null){
		 * if(mCursor2.moveToFirst()){ do{ String catid =
		 * mCursor2.getString(mCursor2
		 * .getColumnIndex(DatabaseForDemo.VENDOR_NO));
		 * vendorspinnerdata.add(catid); }while(mCursor2.moveToNext()); }
		 * mCursor2.close();  ArrayAdapter<String> adapter = new
		 * ArrayAdapter<String>(InventoryActivity.this,
		 * android.R.layout.simple_spinner_item, vendorspinnerdata);
		 * adapter.setDropDownViewResource
		 * (android.R.layout.simple_spinner_item);
		 * vendorspinner.setAdapter(adapter); }else{
		 * Toast.makeText(getApplicationContext(),
		 * "This is no profile data in demo login", Toast.LENGTH_SHORT).show();
		 * }
		 */

		/*
		 * }else{ Toast.makeText(getApplicationContext(),
		 * "This is not demo login", Toast.LENGTH_SHORT).show(); }
		 */

		departmentspinner.setOnItemSelectedListener(new OnItemSelectedListener() {

					@Override
					public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
						// TODO Auto-generated method stub
						tax1.setChecked(false);
						tax2.setChecked(false);
						tax3.setChecked(false);
						bartax.setChecked(false);
						// System.out.println("hi padma");
						String deptspinnerval = departmentspinner .getSelectedItem().toString();
						// System.out.println("how r u padma");
						if (deptspinnerval.equals("")) {

						} else {
							Cursor mCursor2 = dbforloginlogoutReadInvetory .rawQuery(
											"select "
													+ DatabaseForDemo.FoodstampableForDept
													+ ","
													+ DatabaseForDemo.TaxValForDept
													+ " from "
													+ DatabaseForDemo.DEPARTMENT_TABLE
													+ " where "
													+ DatabaseForDemo.DepartmentID
													+ "=\"" + deptspinnerval
													+ "\"", null);
							System.out.println(mCursor2);
							if (mCursor2 != null) {
								if (mCursor2.moveToFirst()) {
									do {
										if (mCursor2.isNull(mCursor2 .getColumnIndex(DatabaseForDemo.FoodstampableForDept))) {
											foodstampableval = "no";
										} else {
											foodstampableval = mCursor2.getString(mCursor2 .getColumnIndex(DatabaseForDemo.FoodstampableForDept));
										}
										if (mCursor2.isNull(mCursor2 .getColumnIndex(DatabaseForDemo.FoodstampableForDept))) {
											String taxval = "";
										} else {
											String taxval = mCursor2.getString(mCursor2 .getColumnIndex(DatabaseForDemo.TaxValForDept));
											String mystring = taxval;
											String[] a = mystring.split(",");
											System.out .println("the array values are:" + a.length);
											for (int i = 0; i < a.length; i++) {
												String substr = a[i];
												if (substr.equals("Tax1")) {
													tax1.setChecked(true);
												} else if (substr.equals("Tax2")) {
													tax2.setChecked(true);
												} else if (substr .equals("Tax3")) {
													tax3.setChecked(true);
												} else if (substr .equals("BarTax")) {
													bartax.setChecked(true);
												}
											}

										}
									} while (mCursor2.moveToNext());
								}
								
							}
							mCursor2.close();
						}
						
					}

					@Override
					public void onNothingSelected(AdapterView<?> arg0) {
						// TODO Auto-generated method stub

					}
				});

		pricecharge.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				try{
				double taxval = 0;
				String costval = pricecharge.getText().toString();
				double cost = 0;
				if (costval.equals("")) {
					cost = 0;
				} else {
					cost = Double.valueOf(costval);
					if (tax1.isChecked()) {

						String query = "select * from "
								+ DatabaseForDemo.TAX_TABLE + " where "
								+ DatabaseForDemo.TAX_NAME + "=\""
								+ tax1.getText().toString() + "\"";
						System.out.println(query);

						Cursor cursor = dbforloginlogoutWriteInvetory.rawQuery(query, null);
						if (cursor != null) {
							if (cursor.moveToFirst()) {
								do {
									double taxvalpercent = Double.valueOf(df.format(cursor.getDouble(cursor .getColumnIndex(DatabaseForDemo.TAX_VALUE))));
									System.out.println("     " + taxvalpercent);
									taxval = cost * (taxvalpercent / 100);
								} while (cursor.moveToNext());
							}
							
							
						} else {
							Toast.makeText(getApplicationContext(), "No Tax value", Toast.LENGTH_SHORT).show();
						}
						cursor.close();
					} else {
						taxval = taxval + 0;
					}
					if (tax2.isChecked()) {

						String query = "select " + DatabaseForDemo.TAX_VALUE
								+ " from " + DatabaseForDemo.TAX_TABLE
								+ " where " + DatabaseForDemo.TAX_NAME + "=\""
								+ tax2.getText().toString() + "\"";
						System.out.println(query);
						Cursor cursor = dbforloginlogoutReadInvetory.rawQuery(query, null);
						if (cursor != null) {
							if (cursor.moveToFirst()) {
								do {
									double taxvalpercent = Double.valueOf(df
											.format(cursor.getDouble(cursor
											.getColumnIndex(DatabaseForDemo.TAX_VALUE))));
									System.out.println("     " + taxvalpercent);
									taxval = taxval
											+ (cost * (taxvalpercent / 100));
								} while (cursor.moveToNext());
							}
							
						} else {
							Toast.makeText(getApplicationContext(),
									"No Tax value", Toast.LENGTH_SHORT).show();
						}
						cursor.close();
					} else {
						taxval = taxval + 0;
					}
					if (tax3.isChecked()) {

						String query = "select " + DatabaseForDemo.TAX_VALUE
								+ " from " + DatabaseForDemo.TAX_TABLE
								+ " where " + DatabaseForDemo.TAX_NAME + "=\""
								+ tax3.getText().toString() + "\"";
						System.out.println(query);

						Cursor cursor = dbforloginlogoutReadInvetory.rawQuery(query, null);
						if (cursor != null) {
							if (cursor.moveToFirst()) {
								do {
									double taxvalpercent= Double.valueOf(df
											.format(cursor.getDouble(cursor
											.getColumnIndex(DatabaseForDemo.TAX_VALUE))));
									System.out.println("     " + taxvalpercent);
									taxval = taxval
											+ (cost * (taxvalpercent / 100));
								} while (cursor.moveToNext());
							}
							
						} else {
							Toast.makeText(getApplicationContext(),
									"No Tax value", Toast.LENGTH_SHORT).show();
						}
						cursor.close();
					} else {
						taxval = taxval + 0;
					}
					if (bartax.isChecked()) {
						taxval = taxval + 0;
						System.out.println("     " + taxval);
					} else {
						taxval = taxval + 0;
					}

					pricetax.setText("" + (taxval + cost));
				}
				} catch (NumberFormatException e) {
					  e.printStackTrace();
					} catch (Exception e1) {
						  e1.printStackTrace();
						}
			}
		});

		optionalinfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String itemnumber = itemno.getText().toString();
				if (itemnumber.equals("")) {
					Toast.makeText(
							getApplicationContext(),
							"You must enter an item number before saving this item.",
							Toast.LENGTH_SHORT).show();
				} else {

					final Dialog dialog = new Dialog(InventoryActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.optional_info_layout);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);
					final CheckBox modifieritemcheck = (CheckBox) dialog
							.findViewById(R.id.modifieritem);
					final CheckBox countthisitemcheck = (CheckBox) dialog
							.findViewById(R.id.countthisitem);
					final CheckBox printonreceiptcheck = (CheckBox) dialog
							.findViewById(R.id.printonreceipt);
					final CheckBox allowbuybackcheck = (CheckBox) dialog
							.findViewById(R.id.allowbuyback);
					final CheckBox foodstamplescheck = (CheckBox) dialog
							.findViewById(R.id.foodstamples);
					final CheckBox promptpricecheck = (CheckBox) dialog
							.findViewById(R.id.promptprice);
					skuslist = (ListView) dialog
							.findViewById(R.id.alternateskuslist);
					final Button addsku = (Button) dialog
							.findViewById(R.id.addsku);
					final EditText bonuspointsed = (EditText) dialog
							.findViewById(R.id.bonuspoints);
					final EditText barcodesed = (EditText) dialog
							.findViewById(R.id.barcodes);
					final EditText commissioned = (EditText) dialog
							.findViewById(R.id.commissioned);
					final EditText locationed = (EditText) dialog
							.findViewById(R.id.location);
					final EditText unitsizeed = (EditText) dialog
							.findViewById(R.id.unitsize);
					final EditText unittypeed = (EditText) dialog
							.findViewById(R.id.unittype);
					final Spinner commissionspinner = (Spinner) dialog
							.findViewById(R.id.commission);

					bonuspointsed.setText(bonuspointsval);
					barcodesed.setText(barcodeval);
					commissioned.setText(commissionval);
					locationed.setText(locationval);
					unitsizeed.setText(unitsizeval);
					unittypeed.setText(unittypeval);
					commissionspinner.setSelection(commissionspinnerval);
					if (modifieritemval.equals("yes")) {
						modifieritemcheck.setChecked(true);
					} else {
						modifieritemcheck.setChecked(false);
					}
					if (countthisitemval.equals("yes")) {
						countthisitemcheck.setChecked(true);
					} else {
						countthisitemcheck.setChecked(false);
					}
					if (printonreceiptval.equals("yes")) {
						printonreceiptcheck.setChecked(true);
					} else {
						printonreceiptcheck.setChecked(false);
					}
					if (allowbuybackval.equals("yes")) {
						allowbuybackcheck.setChecked(true);
					} else {
						allowbuybackcheck.setChecked(false);
					}
					if (foodstampableval.equals("yes")) {
						foodstamplescheck.setChecked(true);
					} else {
						foodstamplescheck.setChecked(false);
					}
					if (promptpriceval.equals("yes")) {
						promptpricecheck.setChecked(true);
					} else {
						promptpricecheck.setChecked(false);
					}
					if (skuarray != null) {
						SkuArrayAdapter skuadapter = new SkuArrayAdapter(
								getApplicationContext(), skuarray);
						skuadapter.setListener(InventoryActivity.this);
						skuslist.setAdapter(skuadapter);
					}

					addsku.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							System.out.println("add is clicked");
							final AlertDialog alertDialog1 = new AlertDialog.Builder(
									InventoryActivity.this).create();
							LayoutInflater mInflater = LayoutInflater
									.from(InventoryActivity.this);
							View layout = mInflater.inflate(R.layout.add_sku,
									null);
							Button ok = (Button) layout.findViewById(R.id.save);
							Button cancel = (Button) layout
									.findViewById(R.id.cancel);
							final EditText sku = (EditText) layout
									.findViewById(R.id.sku);

							alertDialog1.setTitle("Add SKU");

							ok.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub
									skuval = sku.getText().toString();
									if (skuval.equals("")) {
										Toast.makeText(getApplicationContext(),
												"Please enter sku value",
												Toast.LENGTH_SHORT).show();
									} else {
										if (skuarray.contains(skuval)) {
											Toast.makeText(
													getApplicationContext(),
													"SKU already exist",
													Toast.LENGTH_SHORT).show();
										} else {
											String selectQuery = "SELECT  * FROM "
													+ DatabaseForDemo.ALTERNATE_SKU_TABLE
													+ " where "
													+ DatabaseForDemo.ALTERNATE_SKU_VALUE
													+ "=\"" + skuval + "\"";
											Cursor mCursor = dbforloginlogoutReadInvetory.rawQuery(
													selectQuery, null);
											if (mCursor.getCount() > 0) {
												Toast.makeText(
														getApplicationContext(),
														"SKU already exist for an item",
														Toast.LENGTH_SHORT)
														.show();
												
											} else {
												
												skuarray.add(skuval);
												if (skuarray != null) {
													SkuArrayAdapter skuadapter = new SkuArrayAdapter(
															getApplicationContext(),
															skuarray);
													skuadapter
															.setListener(InventoryActivity.this);
													skuslist.setAdapter(skuadapter);
												}
											}
											mCursor.close();
											alertDialog1.dismiss();
										}
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

						}
					});

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							bonuspointsval = bonuspointsed.getText().toString();
							barcodeval = barcodesed.getText().toString();
							commissionval = commissioned.getText().toString();
							locationval = locationed.getText().toString();
							unitsizeval = unitsizeed.getText().toString();
							unittypeval = unittypeed.getText().toString();
							commissionspinnerval = commissionspinner
									.getSelectedItemPosition();
							if (modifieritemcheck.isChecked()) {
								modifieritemval = "yes";
							} else {
								modifieritemval = "no";
							}
							if (countthisitemcheck.isChecked()) {
								countthisitemval = "yes";
							} else {
								countthisitemval = "no";
							}
							if (printonreceiptcheck.isChecked()) {
								printonreceiptval = "yes";
							} else {
								printonreceiptval = "no";
							}
							if (allowbuybackcheck.isChecked()) {
								allowbuybackval = "yes";
							} else {
								allowbuybackval = "no";
							}
							if (foodstamplescheck.isChecked()) {
								foodstampableval = "yes";
							} else {
								foodstampableval = "no";
							}
							if (promptpricecheck.isChecked()) {
								promptpriceval = "yes";
							} else {
								promptpriceval = "no";
							}

							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		notes.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String itemnumber = itemno.getText().toString();
				if (itemnumber.equals("")) {
					Toast.makeText(
							getApplicationContext(),
							"You must enter an item number before saving this item.",
							Toast.LENGTH_SHORT).show();
				} else {
					final Dialog dialog = new Dialog(InventoryActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.notes_layout);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);
					final EditText notesedit = (EditText) dialog
							.findViewById(R.id.notestext);

					notesedit.setText(notesval);

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							notesval = notesedit.getText().toString();
							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		modifiers.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String itemnumber = itemno.getText().toString();
				if (itemnumber.equals("")) {
					Toast.makeText(
							getApplicationContext(),
							"You must enter an item number before saving this item.",
							Toast.LENGTH_SHORT).show();
				} else {
					final Dialog dialog = new Dialog(InventoryActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.modifiers_layout);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);
					final Spinner modifieritems = (Spinner) dialog
							.findViewById(R.id.modifieritemspinner);
					modifierlist = (ListView) dialog
							.findViewById(R.id.modifieritemslist);
					ArrayList<String> modifieritemspinnerarray = new ArrayList<String>();
					modifieritemspinnerarray.clear();
					modifieritemspinnerarray.add("Select");
					String selectQuery = "SELECT  "
							+ DatabaseForDemo.INVENTORY_ITEM_NO + " FROM "
							+ DatabaseForDemo.OPTIONAL_INFO_TABLE + " where "
							+ DatabaseForDemo.INVENTORY_MODIFIER_ITEM
							+ "=\"yes\"";
					Cursor mCursor =dbforloginlogoutReadInvetory.rawQuery(selectQuery, null);
					if (mCursor != null) {
						if (mCursor.moveToFirst()) {
							do {
								String itemid = mCursor.getString(mCursor
										.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NO));
								modifieritemspinnerarray.add(itemid);
							} while (mCursor.moveToNext());
						}
						
						
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(
								InventoryActivity.this,
								android.R.layout.simple_spinner_item,
								modifieritemspinnerarray);
						adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
						modifieritems.setAdapter(adapter);
					}
					mCursor.close();
					if (modifierindividualitemsarray != null) {
						ModifierIndividualItemAdapter adapterformod = new ModifierIndividualItemAdapter(
								InventoryActivity.this,
								modifierindividualitemsarray);
						adapterformod.setListener(InventoryActivity.this);
						modifierlist.setAdapter(adapterformod);
					}

					modifieritems
							.setOnItemSelectedListener(new OnItemSelectedListener() {

								@Override
								public void onItemSelected(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									// TODO Auto-generated method stub
									String itemnoinspinner = modifieritems
											.getSelectedItem().toString();
									System.out
									.println("itemnoinspinner"+itemnoinspinner);
									if (itemnoinspinner.equals("")
											|| itemnoinspinner.equals("Select")) {

									} else {
										System.out.println("else is executed");
										for (int i = 0; i < modifierindividualitemsarray
												.size(); i++) {
											if (modifierindividualitemsarray
													.get(i).containsValue(
															itemnoinspinner)) {
												Toast.makeText(
														InventoryActivity.this,
														"Modifier already exist",
														Toast.LENGTH_SHORT)
														.show();
												System.out
														.println("same is found is executed");
												status = false;
												break;
											}
										}
										if (status == true) {
											String selectQuery = "SELECT  "
													+ DatabaseForDemo.INVENTORY_ITEM_NO
													+ ", "
													+ DatabaseForDemo.INVENTORY_ITEM_NAME
													+ " FROM "
													+ DatabaseForDemo.INVENTORY_TABLE
													+ " where "
													+ DatabaseForDemo.INVENTORY_ITEM_NO
													+ "=\"" + itemnoinspinner
													+ "\"";
											Cursor mCursor =dbforloginlogoutReadInvetory.rawQuery(
													selectQuery, null);
											if (mCursor != null) {
												if (mCursor.moveToFirst()) {
													do {
														HashMap<String, String> map = new HashMap<String, String>();
														String itemid, itemname;
														itemid = mCursor
																.getString(mCursor
																		.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NO));
														itemname = mCursor
																.getString(mCursor
																		.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NAME));
														map.put("itemno",
																itemid);
														map.put("itemname",
																itemname);
														modifierindividualitemsarray
																.add(map);
														Log.v(""+itemid,""+itemname);
													} while (mCursor
															.moveToNext());
												}
											}
											mCursor.close();
											if (modifierindividualitemsarray != null) {
												ModifierIndividualItemAdapter adapterformod = new ModifierIndividualItemAdapter(
														InventoryActivity.this,
														modifierindividualitemsarray);
												adapterformod
														.setListener(InventoryActivity.this);
												modifierlist
														.setAdapter(adapterformod);
											}
										}
									}
									status = true;
								}

								@Override
								public void onNothingSelected(
										AdapterView<?> arg0) {
									// TODO Auto-generated method stub

								}
							});

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		orderinginfo.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String itemnumber = itemno.getText().toString();
				if (itemnumber.equals("")) {
					Toast.makeText(
							getApplicationContext(),
							"You must enter an item number before saving this item.",
							Toast.LENGTH_SHORT).show();
				} else {
					final Dialog dialog = new Dialog(InventoryActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.ordering_info_layout);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);
					final Spinner vendortotaldetails = (Spinner) dialog
							.findViewById(R.id.vendorinorderinginfo);
					vendorlist = (ListView) dialog
							.findViewById(R.id.orderinginfolist);
					ArrayList<String> vendorspinnerdatafororderinginfo = new ArrayList<String>();
					vendorspinnerdatafororderinginfo.clear();
					vendorspinnerdatafororderinginfo.add("Select");
					Cursor mCursor = dbforloginlogoutReadInvetory.rawQuery(
							"select " + DatabaseForDemo.VENDOR_COMPANY_NAME
									+ " from " + DatabaseForDemo.VENDOR_TABLE,
							null);
					System.out.println(mCursor);
					if (mCursor != null) {
						if (mCursor.moveToFirst()) {
							do {
								String catid = mCursor.getString(mCursor
										.getColumnIndex(DatabaseForDemo.VENDOR_COMPANY_NAME));
								vendorspinnerdatafororderinginfo.add(catid);
							} while (mCursor.moveToNext());
						}
					}
					mCursor.close();
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							InventoryActivity.this,
							android.R.layout.simple_spinner_item,
							vendorspinnerdatafororderinginfo);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
					vendortotaldetails.setAdapter(adapter);

					if (pricingvendorlistinorderinginfo != null) {
						OrderingInfoIndividualVendorAdapter adapterformod = new OrderingInfoIndividualVendorAdapter(
								InventoryActivity.this,
								pricingvendorlistinorderinginfo);
						adapterformod.setListener(InventoryActivity.this);
						vendorlist.setAdapter(adapterformod);
					}

					vendortotaldetails
							.setOnItemSelectedListener(new OnItemSelectedListener() {

								@Override
								public void onItemSelected(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									// TODO Auto-generated method stub
									final String vendorinspinner = vendortotaldetails
											.getSelectedItem().toString();
									if (vendorinspinner.equals("")
											|| vendorinspinner.equals("Select")) {

									} else {
										System.out.println("else is executed");
										for (int i = 0; i < pricingvendorlistinorderinginfo
												.size(); i++) {
											if (pricingvendorlistinorderinginfo
													.get(i).containsValue(
															vendorinspinner)) {
												Toast.makeText(
														InventoryActivity.this,
														"Vendor already exist",
														Toast.LENGTH_SHORT)
														.show();
												System.out
														.println("same is found is executed");
												statusforvendor = false;
												break;
											}
										}
										if (statusforvendor == true) {
											System.out
													.println("different is found");

											final AlertDialog alertDialog1 = new AlertDialog.Builder(
													InventoryActivity.this)
													.create();
											LayoutInflater mInflater = LayoutInflater
													.from(InventoryActivity.this);
											View layout = mInflater
													.inflate(
															R.layout.orderinginfopopups,
															null);
											Button ok = (Button) layout
													.findViewById(R.id.save);
											Button cancel = (Button) layout
													.findViewById(R.id.cancel);
											final EditText partno = (EditText) layout
													.findViewById(R.id.partno);
											final EditText costperase = (EditText) layout
													.findViewById(R.id.cost_case);
											final EditText casecost = (EditText) layout
													.findViewById(R.id.case_cost);
											final EditText noincase = (EditText) layout
													.findViewById(R.id.num_in_case);
											noincase.setOnEditorActionListener(new OnEditorActionListener() {
												@Override
												public boolean onEditorAction(TextView v, int actionId,
														KeyEvent event) {
													if (actionId == EditorInfo.IME_ACTION_DONE) {
														InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
														imm.hideSoftInputFromWindow(noincase.getWindowToken(), 0);
													}
													return false;
												}
											});
											final RadioButton trueval = (RadioButton) layout
													.findViewById(R.id.radiobuttonyes);
											final RadioButton falseval = (RadioButton) layout
													.findViewById(R.id.radiobuttonno);

											alertDialog1
													.setTitle("Add Vendor details");

											ok.setOnClickListener(new OnClickListener() {

												@Override
												public void onClick(View arg0) {
													// TODO Auto-generated
													// method stub
													String prferredtextval = "";
													if (trueval.isChecked()) {
														System.out
																.println("preferred val is true");
														prferredtextval = "true";
													}
													if (falseval.isChecked()) {
														System.out
																.println("preferred val is false");
														prferredtextval = "false";
													}
													if (prferredtextval
															.equals("true")) {
														int positionval = 0;
														for (int i = 0; i < pricingvendorlistinorderinginfo
																.size(); i++) {
															if (pricingvendorlistinorderinginfo
																	.get(i)
																	.containsValue(
																			"true")) {
																Toast.makeText(
																		InventoryActivity.this,
																		"true already exist",
																		Toast.LENGTH_SHORT)
																		.show();
																System.out
																		.println("same is found is executed");
																statusfortrue = false;
																positionval = i;
																System.out
																		.println("position val is:"
																				+ positionval);
																break;
															}
														}
														if (statusfortrue == true) {
															System.out
																	.println("true is not there");
															HashMap<String, String> map = new HashMap<String, String>();
															map.put("vendorname",
																	vendorinspinner);
															map.put("partno",
																	partno.getText()
																			.toString());
															map.put("costpercase",
																	costperase
																			.getText()
																			.toString());
															map.put("casecost",
																	casecost.getText()
																			.toString());
															map.put("noincase",
																	noincase.getText()
																			.toString());
															map.put("preferred",
																	prferredtextval);
															pricingvendorlistinorderinginfo
																	.add(map);
														} else {
															System.out
																	.println("true is there");
															pricingvendorlistinorderinginfo
																	.get(positionval)
																	.put("preferred",
																			"false");
															System.out
																	.println("prferred value is changed");
															HashMap<String, String> map = new HashMap<String, String>();
															map.put("vendorname",
																	vendorinspinner);
															map.put("partno",
																	partno.getText()
																			.toString());
															map.put("costpercase",
																	costperase
																			.getText()
																			.toString());
															map.put("casecost",
																	casecost.getText()
																			.toString());
															map.put("noincase",
																	noincase.getText()
																			.toString());
															map.put("preferred",
																	prferredtextval);
															pricingvendorlistinorderinginfo
																	.add(map);
														}
													} else {
														System.out
																.println("preferred val is false");
														HashMap<String, String> map = new HashMap<String, String>();
														map.put("vendorname",
																vendorinspinner);
														map.put("partno",
																partno.getText()
																		.toString());
														map.put("costpercase",
																costperase
																		.getText()
																		.toString());
														map.put("casecost",
																casecost.getText()
																		.toString());
														map.put("noincase",
																noincase.getText()
																		.toString());
														map.put("preferred",
																prferredtextval);
														pricingvendorlistinorderinginfo
																.add(map);
													}

													if (pricingvendorlistinorderinginfo != null) {
														OrderingInfoIndividualVendorAdapter adapterformod = new OrderingInfoIndividualVendorAdapter(
																InventoryActivity.this,
																pricingvendorlistinorderinginfo);
														adapterformod
																.setListener(InventoryActivity.this);
														vendorlist
																.setAdapter(adapterformod);
													}
													statusfortrue = true;
													alertDialog1.dismiss();
												}
											});
											cancel.setOnClickListener(new OnClickListener() {

												@Override
												public void onClick(View arg0) {
													// TODO Auto-generated
													// method stub
													alertDialog1.dismiss();
												}
											});
											alertDialog1.setView(layout);
											alertDialog1.show();
										}
									}
									statusforvendor = true;
								}

								@Override
								public void onNothingSelected(
										AdapterView<?> arg0) {
									// TODO Auto-generated method stub

								}
							});

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		saleshistory.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String itemnumber = itemno.getText().toString();
				if (itemnumber.equals("")) {
					Toast.makeText(
							getApplicationContext(),
							"You must enter an item number before saving this item.",
							Toast.LENGTH_SHORT).show();
				} else {
					final Dialog dialog = new Dialog(InventoryActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.sales_history_layout);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);
					saleslist = (ListView) dialog
							.findViewById(R.id.pricelevelslist);

					ArrayList<String> dateandtimelist = new ArrayList<String>();
					ArrayList<String> storeidlist = new ArrayList<String>();
					ArrayList<String> invoicelist = new ArrayList<String>();
					ArrayList<String> quantitylist = new ArrayList<String>();
					ArrayList<String> pricelist = new ArrayList<String>();
					ArrayList<String> costlist = new ArrayList<String>();
					ArrayList<String> custlist = new ArrayList<String>();
					ArrayList<String> seriallist = new ArrayList<String>();

					

					String query = "SELECT *from "
							+ DatabaseForDemo.INVOICE_ITEMS_TABLE + " where "
							+ DatabaseForDemo.INVOICE_ITEM_ID + "=\""
							+ itemnumber + "\"";
					Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(query, null);

					if (cursor.getCount() > 0) {
						if (cursor != null) {
							if (cursor.moveToFirst()) {
								do {
									int i = 1;
									if (cursor.isNull(cursor
											.getColumnIndex(DatabaseForDemo.CREATED_DATE))) {
										dateandtimelist.add("");
									} else {
										String dateandtime = cursor.getString(cursor
												.getColumnIndex(DatabaseForDemo.CREATED_DATE));
										dateandtimelist.add(dateandtime);
									}
									if (cursor.isNull(cursor
											.getColumnIndex(DatabaseForDemo.INVOICE_STORE_ID))) {
										storeidlist.add("");
									} else {
										String storeid = cursor.getString(cursor
												.getColumnIndex(DatabaseForDemo.INVOICE_STORE_ID));
										storeidlist.add(storeid);
									}
									if (cursor.isNull(cursor
											.getColumnIndex(DatabaseForDemo.INVOICE_ID))) {
										invoicelist.add("");
									} else {
										String invoiceid = cursor.getString(cursor
												.getColumnIndex(DatabaseForDemo.INVOICE_ID));
										invoicelist.add(invoiceid);
									}
									if (cursor.isNull(cursor
											.getColumnIndex(DatabaseForDemo.INVOICE_QUANTITY))) {
										quantitylist.add("");
									} else {
										String qtylist = cursor.getString(cursor
												.getColumnIndex(DatabaseForDemo.INVOICE_QUANTITY));
										quantitylist.add(qtylist);
									}
									if (cursor.isNull(cursor
											.getColumnIndex(DatabaseForDemo.INVOICE_YOUR_COST))) {
										pricelist.add("");
									} else {
										String price = cursor.getString(cursor
												.getColumnIndex(DatabaseForDemo.INVOICE_YOUR_COST));
										pricelist.add(price);
									}
									if (cursor.isNull(cursor
											.getColumnIndex(DatabaseForDemo.INVOICE_AVG_COST))) {
										costlist.add("");
									} else {
										String cost = cursor.getString(cursor
												.getColumnIndex(DatabaseForDemo.INVOICE_AVG_COST));
										costlist.add(cost);
									}
									seriallist.add("" + i);
									i++;
								} while (cursor.moveToNext());
							}
						}
					}
					cursor.close();
					for (int ii = 0; ii < invoicelist.size(); ii++) {
						String selectQueryforshipping = "SELECT  * FROM "
								+ DatabaseForDemo.INVOICE_TOTAL_TABLE
								+ " where " + DatabaseForDemo.INVOICE_ID + "=\""
								+ invoicelist.get(ii) + "\"";

						Cursor mCursorforshipping =dbforloginlogoutReadInvetory.rawQuery(
								selectQueryforshipping, null);

						if (mCursorforshipping.getCount() > 0) {
							if (mCursorforshipping != null) {
								if (mCursorforshipping.moveToFirst()) {
									do {
										if (mCursorforshipping.isNull(mCursorforshipping
												.getColumnIndex(DatabaseForDemo.INVOICE_CUSTOMER))) {
											custlist.add("");
										} else {
											String address = mCursorforshipping
													.getString(mCursorforshipping
															.getColumnIndex(DatabaseForDemo.INVOICE_CUSTOMER));
											custlist.add(address);
										}
									} while (mCursorforshipping.moveToNext());
								}
							}
						}
						mCursorforshipping.close();
					}

					ImageAdapterForSalesHistory adapter = new ImageAdapterForSalesHistory(
							InventoryActivity.this, dateandtimelist,
							storeidlist, invoicelist, quantitylist, pricelist,
							costlist, custlist, seriallist);
					saleslist.setAdapter(adapter);

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		printers.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String itemnumber = itemno.getText().toString();
				if (itemnumber.equals("")) {
					Toast.makeText(
							getApplicationContext(),
							"You must enter an item number before saving this item.",
							Toast.LENGTH_SHORT).show();
				} else {
					final Dialog dialog = new Dialog(InventoryActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.printers_layout);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);
					final Spinner spinnerforprinter = (Spinner) dialog
							.findViewById(R.id.printeritemspinner);
					printerlistview = (ListView) dialog
							.findViewById(R.id.printeritemslist);

					final ArrayList<String> printerlist = new ArrayList<String>();
					printerlist.clear();
					printerlist.add("None");

					String selectQuery = "SELECT *from "
							+ DatabaseForDemo.PRINTER_TABLE;

					Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(selectQuery, null);
					int count = cursor.getCount();
					if (count > 0) {
						if (cursor != null) {
							if (cursor.moveToFirst()) {
								do {
									if (cursor.isNull(cursor
											.getColumnIndex(DatabaseForDemo.PRINTER_ID))) {

									} else {
										String vendornum = cursor.getString(cursor
												.getColumnIndex(DatabaseForDemo.PRINTER_ID));
										printerlist.add(vendornum);
									}
								} while (cursor.moveToNext());
							}
						}
					}
					cursor.close();
					
					

					if (printerlist != null) {
						ArrayAdapter<String> adapter = new ArrayAdapter<String>(
								InventoryActivity.this,
								android.R.layout.simple_spinner_item,
								printerlist);
						adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
						spinnerforprinter.setAdapter(adapter);
					}

					spinnerforprinter
							.setOnItemSelectedListener(new OnItemSelectedListener() {

								@Override
								public void onItemSelected(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									// TODO Auto-generated method stub
									String printer = spinnerforprinter
											.getSelectedItem().toString();
									if (printer.equals("")
											|| printer.equals("None")) {

									} else {
										if (printerlistfordisplay
												.contains(printer)) {
											Toast.makeText(
													getApplicationContext(),
													"Printer already exist for this product",
													Toast.LENGTH_SHORT).show();
										} else {
											printerlistfordisplay.add(printer);
											if (printerlistfordisplay != null) {
												PrinterArrayAdapter skuadapter = new PrinterArrayAdapter(
														getApplicationContext(),
														printerlistfordisplay);
												skuadapter
														.setListener(InventoryActivity.this);
												printerlistview
														.setAdapter(skuadapter);
											}
										}
									}
								}

								@Override
								public void onNothingSelected(
										AdapterView<?> arg0) {
									// TODO Auto-generated method stub

								}
							});

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		cancelcat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				departmentspinner.setSelection(0);
				vendorspinner.setSelection(0);
				tax1.setChecked(true);
				tax2.setChecked(false);
				tax3.setChecked(false);
				bartax.setChecked(false);
				itemno.setText("");
				description1.setText("");
				description2.setText("");
				cost.setText("");
				pricecharge.setText("");
				pricetax.setText("");
				instock.setText("");
				modifieritemval = "";
				countthisitemval = "";
				printonreceiptval = "";
				allowbuybackval = "";
				foodstampableval = "";
				notesval = "";
				promptpriceval = "'";
				skuval = "";
				bonuspointsval = "";
				barcodeval = "";
				commissionval = "";
				locationval = "";
				unitsizeval = "";
				unittypeval = "";
				printervalfordept = "";
				foodcheckval = "";
				taxvalforsavefordept = "";
				taxvalforsaveforprod = "";
				taxvalforduplicate = 0;
				taxvalforsaveforduplicate = "";
				taxvalforprod = 0;
				commissionspinnerval = 0;
				// optional_info.clear();
				skuarray.clear();
				modifierindividualitemsarray.clear();
				pricingvendorlistinorderinginfo.clear();
			}
		});

		savecat.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
try{
				final String itemnumber = itemno.getText().toString();
				final String itemname = description1.getText().toString();
				final String description = description2.getText().toString();
				final String costvalforsave = cost.getText().toString();
				final String price_charge = pricecharge.getText().toString();
				String price_tax = pricetax.getText().toString();
				final String instockval = instock.getText().toString();

				if (deptspinnerdata.isEmpty()) {
					Toast.makeText(
							InventoryActivity.this,
							"Please add atleast a single department and vendor",
							Toast.LENGTH_SHORT).show();
				} else {

					final String departmentofitem = departmentspinner
							.getSelectedItem().toString();
					// final String vendorval =
					// vendorspinner.getSelectedItem().toString();
					if (departmentofitem.equals("") || itemnumber.equals("")
							|| itemname.equals("") || description.equals("")
							|| price_charge.equals("")
							|| costvalforsave.equals("")) {
						Toast.makeText(InventoryActivity.this,
								"Please enter details", Toast.LENGTH_SHORT)
								.show();
					} else {
						String costval = pricecharge.getText().toString();
						taxvalforprod = 0;
						if (tax1.isChecked()) {
							taxvalforsaveforprod = taxvalforsaveforprod
									+ tax1.getText().toString() + ",";
							double cost = Double.valueOf(costval);

							String query = "select * from "
									+ DatabaseForDemo.TAX_TABLE + " where "
									+ DatabaseForDemo.TAX_NAME + "=\""
									+ tax1.getText().toString() + "\"";
							System.out.println(query);
							Cursor cursor = dbforloginlogoutReadInvetory.rawQuery(query, null);
							if (cursor != null) {
								if (cursor.moveToFirst()) {
									do {
										double taxvalpercent = Double.valueOf(df
												.format(cursor.getDouble(cursor
														.getColumnIndex(DatabaseForDemo.TAX_VALUE))));
										System.out.println("     "
												+ taxvalpercent);
										taxvalforprod = cost
												* (taxvalpercent / 100);
									} while (cursor.moveToNext());
								}
							} else {
								Toast.makeText(getApplicationContext(),
										"No Tax value", Toast.LENGTH_SHORT)
										.show();
							}
							cursor.close();
						}
						if (tax2.isChecked()) {
							taxvalforsaveforprod = taxvalforsaveforprod
									+ tax2.getText().toString() + ",";
							double cost = Double.valueOf(costval);

							String query = "select "
									+ DatabaseForDemo.TAX_VALUE + " from "
									+ DatabaseForDemo.TAX_TABLE + " where "
									+ DatabaseForDemo.TAX_NAME + "=\""
									+ tax2.getText().toString() + "\"";
							System.out.println(query);
							Cursor cursor = dbforloginlogoutReadInvetory.rawQuery(query, null);
							if (cursor != null) {
								if (cursor.moveToFirst()) {
									do {
										double taxvalpercent = Double.valueOf(df
												.format(cursor.getDouble(cursor
														.getColumnIndex(DatabaseForDemo.TAX_VALUE))));
										System.out.println("     "
												+ taxvalpercent);
										taxvalforprod = taxvalforprod
												+ (cost * (taxvalpercent / 100));
									} while (cursor.moveToNext());
								}
							} else {
								Toast.makeText(getApplicationContext(),
										"No Tax value", Toast.LENGTH_SHORT)
										.show();
							}
							cursor.close();
						}
						if (tax3.isChecked()) {
							taxvalforsaveforprod = taxvalforsaveforprod
									+ tax3.getText().toString() + ",";
							double cost = Double.valueOf(costval);

							String query = "select "
									+ DatabaseForDemo.TAX_VALUE + " from "
									+ DatabaseForDemo.TAX_TABLE + " where "
									+ DatabaseForDemo.TAX_NAME + "=\""
									+ tax3.getText().toString() + "\"";
							System.out.println(query);

							Cursor cursor = dbforloginlogoutReadInvetory.rawQuery(query, null);
							if (cursor != null) {
								if (cursor.moveToFirst()) {
									do {
										double taxvalpercent = Double.valueOf(df
												.format(cursor.getDouble(cursor
														.getColumnIndex(DatabaseForDemo.TAX_VALUE))));
										System.out.println("     "
												+ taxvalpercent);
										taxvalforprod = taxvalforprod
												+ (cost * (taxvalpercent / 100));
									} while (cursor.moveToNext());
								}
							} else {
								Toast.makeText(getApplicationContext(),
										"No Tax value", Toast.LENGTH_SHORT)
										.show();
							}
							cursor.close();
						}
						if (bartax.isChecked()) {
							taxvalforsaveforprod = taxvalforsaveforprod
									+ bartax.getText().toString();
							taxvalforprod = taxvalforprod + 0;
							System.out.println("     " + taxvalforprod);
						}
						System.out.println(taxvalforsaveforprod);
						System.out.println("taxvalue is padma padma:"
								+ taxvalforprod);
						
						if(price_tax.equals(price_charge)){
							Double taxd = Double.parseDouble(price_tax)+taxvalforprod;
							price_tax = taxd.toString();
							System.out.println("pricetaxval is:"+price_tax);
						}else{
							
						}

						// if(Parameters.usertype.equals("demo")){
						final String uniqueidval = Parameters.randomValue();
						
						String selectQuery = "SELECT  * FROM "
								+ DatabaseForDemo.INVENTORY_TABLE + " where "
								+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\""
								+ itemnumber + "\"";

						Cursor mCursor =dbforloginlogoutReadInvetory.rawQuery(selectQuery, null);
						if (mCursor.getCount() > 0) {
							Toast.makeText(InventoryActivity.this,
									"Product already exist", Toast.LENGTH_SHORT)
									.show();
							
						} else {
							
							ContentValues contentValues = new ContentValues();
							contentValues.put(DatabaseForDemo.UNIQUE_ID,
									uniqueidval);
							contentValues.put(DatabaseForDemo.CREATED_DATE,
									Parameters.currentTime());
							contentValues.put(DatabaseForDemo.MODIFIED_DATE,
									Parameters.currentTime());
							contentValues.put(DatabaseForDemo.MODIFIED_IN,
									"Local");
							contentValues.put(
									DatabaseForDemo.INVENTORY_DEPARTMENT,
									departmentofitem);
							contentValues.put(
									DatabaseForDemo.INVENTORY_ITEM_NO,
									itemnumber);
							contentValues.put(
									DatabaseForDemo.INVENTORY_ITEM_NAME,
									itemname);
							contentValues
									.put(DatabaseForDemo.INVENTORY_SECOND_DESCRIPTION,
											description);
							contentValues.put(
									DatabaseForDemo.INVENTORY_AVG_COST,
									costvalforsave);
							contentValues.put(
									DatabaseForDemo.INVENTORY_PRICE_CHANGE,
									price_charge);
							contentValues.put(
									DatabaseForDemo.INVENTORY_PRICE_TAX,
									price_tax);
							contentValues.put(
									DatabaseForDemo.INVENTORY_IN_STOCK,
									instockval);
							// contentValues.put(DatabaseForDemo.INVENTORY_VENDOR,
							// vendorval);
							contentValues.put(DatabaseForDemo.INVENTORY_TAXONE,
									taxvalforsaveforprod);
							contentValues.put(DatabaseForDemo.INVENTORY_NOTES,
									notesval);
							contentValues.put(
									DatabaseForDemo.INVENTORY_QUANTITY, "1");
							contentValues.put(DatabaseForDemo.CHECKED_VALUE,
									"true");
							contentValues.put(
									DatabaseForDemo.INVENTORY_TOTAL_TAX,
									taxvalforprod);
							dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.INVENTORY_TABLE, null,
									contentValues);

							optional_info = new ContentValues();
							optional_info.put(DatabaseForDemo.UNIQUE_ID,
									uniqueidval);
							optional_info.put(DatabaseForDemo.CREATED_DATE,
									Parameters.currentTime());
							optional_info.put(DatabaseForDemo.MODIFIED_DATE,
									Parameters.currentTime());
							optional_info.put(DatabaseForDemo.MODIFIED_IN,
									"Local");
							optional_info.put(DatabaseForDemo.BONUS_POINTS,
									bonuspointsval);
							optional_info.put(DatabaseForDemo.BARCODES,
									barcodeval);
							optional_info.put(DatabaseForDemo.LOCATION,
									locationval);
							optional_info.put(DatabaseForDemo.UNIT_SIZE,
									unitsizeval);
							optional_info.put(DatabaseForDemo.UNIT_TYPE,
									unittypeval);
							optional_info.put(
									DatabaseForDemo.COMMISSION_OPTIONAL_INFO,
									commissionval);
							optional_info.put(
									DatabaseForDemo.INVENTORY_ALLOW_BUYBACK,
									allowbuybackval);
							optional_info.put(
									DatabaseForDemo.INVENTORY_PROMPT_PRICE,
									promptpriceval);
							optional_info.put(
									DatabaseForDemo.INVENTORY_FOODSTAMPABLE,
									foodstampableval);
							optional_info.put(
									DatabaseForDemo.INVENTORY_PRINT_ON_RECEIPT,
									printonreceiptval);
							optional_info.put(
									DatabaseForDemo.INVENTORY_COUNT_THIS_ITEM,
									countthisitemval);
							optional_info.put(
									DatabaseForDemo.INVENTORY_MODIFIER_ITEM,
									modifieritemval);
							optional_info.put(
									DatabaseForDemo.INVENTORY_ITEM_NO,
									itemnumber);
							dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.OPTIONAL_INFO_TABLE,
									null, optional_info);

							for (int i = 0; i < skuarray.size(); i++) {
								ContentValues alternate_sku = new ContentValues();
								alternate_sku.put(DatabaseForDemo.UNIQUE_ID,
										uniqueidval);
								alternate_sku.put(DatabaseForDemo.CREATED_DATE,
										Parameters.currentTime());
								alternate_sku.put(
										DatabaseForDemo.MODIFIED_DATE,
										Parameters.currentTime());
								alternate_sku.put(DatabaseForDemo.MODIFIED_IN,
										"Local");
								alternate_sku.put(
										DatabaseForDemo.ALTERNATE_SKU_VALUE,
										skuarray.get(i));
								alternate_sku.put(
										DatabaseForDemo.INVENTORY_ITEM_NO,
										itemno.getText().toString());
								dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.ALTERNATE_SKU_TABLE,
										null, alternate_sku);
							}

							modifiersArrayserver.clear();
							for (int i = 0; i < modifierindividualitemsarray.size(); i++) {
								String rrrr=Parameters.randomValue();
								ContentValues modifier_value = new ContentValues();
								modifier_value.put(DatabaseForDemo.UNIQUE_ID,
										rrrr);
								modifier_value.put(DatabaseForDemo.CREATED_DATE,
										Parameters.currentTime());
								modifier_value.put(DatabaseForDemo.MODIFIED_DATE,
										Parameters.currentTime());
								modifier_value.put(DatabaseForDemo.MODIFIED_IN,
										"Local");
								modifier_value.put(
										DatabaseForDemo.MODIFIER_ITEM_NO,
										modifierindividualitemsarray.get(i).get(
												"itemno"));
								Log.e("itemno", ""+modifierindividualitemsarray.get(i).get(
										"itemno"));
								modifier_value.put(
										DatabaseForDemo.INVENTORY_ITEM_NAME,
										modifierindividualitemsarray.get(i).get(
												"itemname"));
								Log.e("itemname", ""+modifierindividualitemsarray.get(i).get(
										"itemname"));
								modifier_value.put(
										DatabaseForDemo.INVENTORY_ITEM_NO,
										itemnumber);
								Log.e("itemname", ""+itemnumber);
								dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.MODIFIER_TABLE, null,
										modifier_value);
								try {
									JSONObject data = new JSONObject();
									JSONObject jsonobj = new JSONObject();
									JSONArray fields = new JSONArray();
										jsonobj.put(DatabaseForDemo.UNIQUE_ID,
												rrrr);
										jsonobj.put(DatabaseForDemo.CREATED_DATE,
												Parameters.currentTime());
										jsonobj.put(DatabaseForDemo.MODIFIED_DATE,
												Parameters.currentTime());
										jsonobj
												.put(DatabaseForDemo.MODIFIED_IN, "Local");
										jsonobj.put(
												DatabaseForDemo.MODIFIER_ITEM_NO,
												modifierindividualitemsarray.get(i).get(
														"itemno"));
										jsonobj.put(
												DatabaseForDemo.INVENTORY_ITEM_NAME,
												modifierindividualitemsarray.get(i).get(
														"itemname"));
										jsonobj.put(
												DatabaseForDemo.INVENTORY_ITEM_NO,
												itemnumber);
										
										Log.e("nn srihari "+modifierindividualitemsarray.get(i)
												.get("itemname"),"id "+modifierindividualitemsarray.get(i)
												.get("itemno"));
										fields.put(0, jsonobj);
									data.put("fields", fields);
									datavalforproduct3 = data.toString();
									modifiersArrayserver.add(datavalforproduct3);
								} catch (JSONException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}
							}
							
							
							for (int i = 0; i < pricingvendorlistinorderinginfo
									.size(); i++) {
								ContentValues ordering_info = new ContentValues();
								ordering_info.put(DatabaseForDemo.UNIQUE_ID,
										uniqueidval);
								ordering_info.put(DatabaseForDemo.CREATED_DATE,
										Parameters.currentTime());
								ordering_info.put(
										DatabaseForDemo.MODIFIED_DATE,
										Parameters.currentTime());
								ordering_info.put(DatabaseForDemo.MODIFIED_IN,
										"Local");
								ordering_info.put(
										DatabaseForDemo.VENDOR_COMPANY_NAME,
										pricingvendorlistinorderinginfo.get(i)
												.get("vendorname"));
								ordering_info.put(
										DatabaseForDemo.VENDERPART_NO,
										pricingvendorlistinorderinginfo.get(i)
												.get("partno"));
								ordering_info.put(DatabaseForDemo.COST_PER,
										pricingvendorlistinorderinginfo.get(i)
												.get("costperase"));
								ordering_info.put(DatabaseForDemo.CASE_COST,
										pricingvendorlistinorderinginfo.get(i)
												.get("casecost"));
								ordering_info.put(DatabaseForDemo.NO_IN_CASE,
										pricingvendorlistinorderinginfo.get(i)
												.get("noincase"));
								ordering_info.put(DatabaseForDemo.PREFERRED,
										pricingvendorlistinorderinginfo.get(i)
												.get("preferred"));
								ordering_info.put(
										DatabaseForDemo.INVENTORY_ITEM_NO,
										itemnumber);
								dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.ORDERING_INFO_TABLE,
										null, ordering_info);
							}

							
							
							Toast.makeText(InventoryActivity.this,
									"Product inserted successfully",
									Toast.LENGTH_SHORT).show();

							try {
								JSONObject data = new JSONObject();
								JSONObject jsonobj = new JSONObject();
								jsonobj.put(
										DatabaseForDemo.INVENTORY_DEPARTMENT,
										departmentofitem);
								jsonobj.put(DatabaseForDemo.INVENTORY_ITEM_NO,
										itemnumber);
								jsonobj.put(
										DatabaseForDemo.INVENTORY_ITEM_NAME,
										itemname);
								jsonobj.put(
										DatabaseForDemo.INVENTORY_SECOND_DESCRIPTION,
										description);
								jsonobj.put(DatabaseForDemo.INVENTORY_AVG_COST,
										costvalforsave);
								jsonobj.put(
										DatabaseForDemo.INVENTORY_PRICE_CHANGE,
										price_charge);
								jsonobj.put(
										DatabaseForDemo.INVENTORY_PRICE_TAX,
										price_tax);
								jsonobj.put(DatabaseForDemo.INVENTORY_IN_STOCK,
										instockval);
								// jsonobj.put(DatabaseForDemo.INVENTORY_VENDOR,
								// vendorval);
								jsonobj.put(DatabaseForDemo.INVENTORY_TAXONE,
										taxvalforsaveforprod);
								jsonobj.put(DatabaseForDemo.INVENTORY_NOTES,
										notesval);
								jsonobj.put(DatabaseForDemo.INVENTORY_QUANTITY,
										"1");
								jsonobj.put(
										DatabaseForDemo.INVENTORY_TOTAL_TAX,
										taxvalforprod);
								jsonobj.put(
										DatabaseForDemo.CHECKED_VALUE,
										"true");
								jsonobj.put(DatabaseForDemo.UNIQUE_ID,
										uniqueidval);
								JSONArray fields = new JSONArray();
								fields.put(0, jsonobj);
								data.put("fields", fields);
								dataval = data.toString();
								
							} catch (JSONException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							try {
								JSONObject data = new JSONObject();
								JSONObject jsonobj = new JSONObject();
								jsonobj.put(DatabaseForDemo.BONUS_POINTS,
										bonuspointsval);
								jsonobj.put(DatabaseForDemo.BARCODES,
										barcodeval);
								jsonobj.put(DatabaseForDemo.LOCATION,
										locationval);
								jsonobj.put(DatabaseForDemo.UNIT_SIZE,
										unitsizeval);
								jsonobj.put(DatabaseForDemo.UNIT_TYPE,
										unittypeval);
								jsonobj.put(
										DatabaseForDemo.COMMISSION_OPTIONAL_INFO,
										commissionval);
								jsonobj.put(
										DatabaseForDemo.INVENTORY_ALLOW_BUYBACK,
										allowbuybackval);
								jsonobj.put(
										DatabaseForDemo.INVENTORY_PROMPT_PRICE,
										promptpriceval);
								jsonobj.put(
										DatabaseForDemo.INVENTORY_FOODSTAMPABLE,
										foodstampableval);
								jsonobj.put(
										DatabaseForDemo.INVENTORY_PRINT_ON_RECEIPT,
										printonreceiptval);
								jsonobj.put(
										DatabaseForDemo.INVENTORY_COUNT_THIS_ITEM,
										countthisitemval);
								jsonobj.put(
										DatabaseForDemo.INVENTORY_MODIFIER_ITEM,
										modifieritemval);
								jsonobj.put(DatabaseForDemo.INVENTORY_ITEM_NO,
										itemnumber);
								jsonobj.put(DatabaseForDemo.UNIQUE_ID,
										uniqueidval);
								JSONArray fields = new JSONArray();
								fields.put(0, jsonobj);
								data.put("fields", fields);
								datavalforproduct1 = data.toString();
								
							} catch (JSONException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							try {
								JSONObject data = new JSONObject();
								JSONObject jsonobj = new JSONObject();
								JSONArray fields = new JSONArray();

								for (int i = 0; i < skuarray.size(); i++) {
									fields.put(i, jsonobj);
									jsonobj.put(
											DatabaseForDemo.ALTERNATE_SKU_VALUE,
											skuarray.get(i));
									jsonobj.put(
											DatabaseForDemo.INVENTORY_ITEM_NO,
											itemnumber);
									jsonobj.put(DatabaseForDemo.UNIQUE_ID,
											uniqueidval);
								}
								data.put("fields", fields);
								datavalforproduct2 = data.toString();
								
							} catch (JSONException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}


							try {
								JSONObject data = new JSONObject();
								JSONObject jsonobj = new JSONObject();
								JSONArray fields = new JSONArray();

								for (int i = 0; i < pricingvendorlistinorderinginfo
										.size(); i++) {
									fields.put(i, jsonobj);
									jsonobj.put(
											DatabaseForDemo.VENDOR_COMPANY_NAME,
											pricingvendorlistinorderinginfo
													.get(i).get("vendorname"));
									jsonobj.put(DatabaseForDemo.VENDERPART_NO,
											pricingvendorlistinorderinginfo
													.get(i).get("partno"));
									jsonobj.put(DatabaseForDemo.COST_PER,
											pricingvendorlistinorderinginfo
													.get(i).get("costperase"));
									jsonobj.put(DatabaseForDemo.CASE_COST,
											pricingvendorlistinorderinginfo
													.get(i).get("casecost"));
									jsonobj.put(DatabaseForDemo.NO_IN_CASE,
											pricingvendorlistinorderinginfo
													.get(i).get("noincase"));
									jsonobj.put(DatabaseForDemo.PREFERRED,
											pricingvendorlistinorderinginfo
													.get(i).get("preferred"));
									jsonobj.put(
											DatabaseForDemo.INVENTORY_ITEM_NO,
											itemnumber);
									jsonobj.put(DatabaseForDemo.UNIQUE_ID,
											uniqueidval);
								}
								data.put("fields", fields);
								datavalforproduct4 = data.toString();
								
							} catch (JSONException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}

							if (Parameters.OriginalUrl.equals("")) {
								System.out
										.println("there is no server url val");
							} else {
								boolean isnet = Parameters.isNetworkAvailable(InventoryActivity.this);
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
												System.out.println("array_list length for insert is:"
														+ array.length());
												JSONArray array2 = obj
														.getJSONArray("delete-queries");
												System.out.println("array2_list length for delete is:"
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

													dbforloginlogoutWriteInvetory.execSQL(deletequery);
													dbforloginlogoutWriteInvetory.execSQL(insertquery);
													System.out
															.println("queries executed"
																	+ ii);
												}
											} catch (JSONException e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}

											String responseproduct1 = jsonpost
													.postmethodfordirect(
															"admin",
															"abcdefg",
															DatabaseForDemo.OPTIONAL_INFO_TABLE,
															Parameters
																	.currentTime(),
															Parameters
																	.currentTime(),
															datavalforproduct1,
															"");
											System.out
													.println("response test is:"
															+ responseproduct1);
											try {
												JSONObject obj = new JSONObject(
														responseproduct1);
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

													dbforloginlogoutWriteInvetory.execSQL(deletequery);
													dbforloginlogoutWriteInvetory.execSQL(insertquery);
													System.out
															.println("queries executed"
																	+ ii);
												}
											} catch (JSONException e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}

											String responseproduct2 = jsonpost
													.postmethodfordirect(
															"admin",
															"abcdefg",
															DatabaseForDemo.ALTERNATE_SKU_TABLE,
															Parameters
																	.currentTime(),
															Parameters
																	.currentTime(),
															datavalforproduct2,
															"");
											System.out
													.println("response test is:"
															+ responseproduct2);
											try {
												JSONObject obj = new JSONObject(
														responseproduct2);
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

													dbforloginlogoutWriteInvetory.execSQL(deletequery);
													dbforloginlogoutWriteInvetory.execSQL(insertquery);
													System.out
															.println("queries executed"
																	+ ii);
												}
											} catch (JSONException e) {
												// TODO Auto-generated catch
												// block
												e.printStackTrace();
											}

											for (int i = 0; i < modifiersArrayserver
													.size(); i++) {
											String responseproduct3 = jsonpost
													.postmethodfordirect(
															"admin",
															"abcdefg",
															DatabaseForDemo.MODIFIER_TABLE,
															Parameters
																	.currentTime(),
															Parameters
																	.currentTime(),
																	modifiersArrayserver.get(i),
															"");
											System.out
													.println("response test is:"
															+ responseproduct3);
											try {
												JSONObject obj = new JSONObject(
														responseproduct3);
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
															.replace(
																	"'",
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
															.replace(
																	"'",
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

												dbforloginlogoutWriteInvetory.execSQL(deletequery);
												dbforloginlogoutWriteInvetory.execSQL(insertquery);
													System.out
															.println("queries executed"
																	+ ii);
												}
											} catch (Exception e) {

											}
											}

											String responseproduct4 = jsonpost
													.postmethodfordirect(
															"admin",
															"abcdefg",
															DatabaseForDemo.ORDERING_INFO_TABLE,
															Parameters
																	.currentTime(),
															Parameters
																	.currentTime(),
															datavalforproduct4,
															"");
											System.out
													.println("response test is:"
															+ responseproduct4);
											String servertiem = null;
											try {
												JSONObject obj = new JSONObject(
														responseproduct4);
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

													dbforloginlogoutWriteInvetory.execSQL(deletequery);
													dbforloginlogoutWriteInvetory.execSQL(insertquery);
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
											Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(select,
													null);
											if (cursor.getCount() > 0) {
												dbforloginlogoutWriteInvetory.execSQL("update "
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
												dbforloginlogoutWriteInvetory.insert(
														DatabaseForDemo.MISCELLANEOUS_TABLE,
														null, contentValues1);

											}
										cursor.close();
											dataval = "";
											datavalforproduct1 = "";
											datavalforproduct2 = "";
											datavalforproduct3 = "";
											datavalforproduct4 = "";
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
											DatabaseForDemo.INVENTORY_TABLE);
									contentValues1
											.put(DatabaseForDemo.CURRENT_TIME_PENDING,
													Parameters.currentTime());
									contentValues1
											.put(DatabaseForDemo.PARAMETERS,
													dataval);
									dbforloginlogoutWriteInvetory.insert(
											DatabaseForDemo.PENDING_QUERIES_TABLE,
											null, contentValues1);

									ContentValues contentValues2 = new ContentValues();
									contentValues2.put(
											DatabaseForDemo.QUERY_TYPE,
											"insert");
									contentValues2.put(
											DatabaseForDemo.PENDING_USER_ID,
											Parameters.userid);
									contentValues2.put(
											DatabaseForDemo.PAGE_URL,
											"saveinfo.php");
									contentValues2
											.put(DatabaseForDemo.TABLE_NAME_PENDING,
													DatabaseForDemo.OPTIONAL_INFO_TABLE);
									contentValues2
											.put(DatabaseForDemo.CURRENT_TIME_PENDING,
													Parameters.currentTime());
									contentValues2.put(
											DatabaseForDemo.PARAMETERS,
											datavalforproduct1);
									dbforloginlogoutWriteInvetory.insert(
											DatabaseForDemo.PENDING_QUERIES_TABLE,
											null, contentValues2);

									ContentValues contentValues3 = new ContentValues();
									contentValues3.put(
											DatabaseForDemo.QUERY_TYPE,
											"insert");
									contentValues3.put(
											DatabaseForDemo.PENDING_USER_ID,
											Parameters.userid);
									contentValues3.put(
											DatabaseForDemo.PAGE_URL,
											"saveinfo.php");
									contentValues3
											.put(DatabaseForDemo.TABLE_NAME_PENDING,
													DatabaseForDemo.ALTERNATE_SKU_TABLE);
									contentValues3
											.put(DatabaseForDemo.CURRENT_TIME_PENDING,
													Parameters.currentTime());
									contentValues3.put(
											DatabaseForDemo.PARAMETERS,
											datavalforproduct2);
									dbforloginlogoutWriteInvetory.insert(
											DatabaseForDemo.PENDING_QUERIES_TABLE,
											null, contentValues3);

									ContentValues contentValues4 = new ContentValues();
									contentValues4.put(
											DatabaseForDemo.QUERY_TYPE,
											"insert");
									contentValues4.put(
											DatabaseForDemo.PENDING_USER_ID,
											Parameters.userid);
									contentValues4.put(
											DatabaseForDemo.PAGE_URL,
											"saveinfo.php");
									contentValues4.put(
											DatabaseForDemo.TABLE_NAME_PENDING,
											DatabaseForDemo.MODIFIER_TABLE);
									contentValues4
											.put(DatabaseForDemo.CURRENT_TIME_PENDING,
													Parameters.currentTime());
									contentValues4.put(
											DatabaseForDemo.PARAMETERS,
											datavalforproduct3);
									dbforloginlogoutWriteInvetory.insert(
											DatabaseForDemo.PENDING_QUERIES_TABLE,
											null, contentValues4);

									ContentValues contentValues5 = new ContentValues();
									contentValues5.put(
											DatabaseForDemo.QUERY_TYPE,
											"insert");
									contentValues5.put(
											DatabaseForDemo.PENDING_USER_ID,
											Parameters.userid);
									contentValues5.put(
											DatabaseForDemo.PAGE_URL,
											"saveinfo.php");
									contentValues5
											.put(DatabaseForDemo.TABLE_NAME_PENDING,
													DatabaseForDemo.ORDERING_INFO_TABLE);
									contentValues5
											.put(DatabaseForDemo.CURRENT_TIME_PENDING,
													Parameters.currentTime());
									contentValues5.put(
											DatabaseForDemo.PARAMETERS,
											datavalforproduct4);
									dbforloginlogoutWriteInvetory.insert(
											DatabaseForDemo.PENDING_QUERIES_TABLE,
											null, contentValues5);
									
									
									dataval = "";
									datavalforproduct1 = "";
									datavalforproduct2 = "";
									datavalforproduct3 = "";
									datavalforproduct4 = "";
								}

							}
							departmentspinner.setSelection(0);
							itemno.setText("");
							description1.setText("");
							description2.setText("");
							cost.setText("");
							pricecharge.setText("");
							pricetax.setText("");
							instock.setText("");
							vendorspinner.setSelection(0);
							tax1.setChecked(true);
							tax2.setChecked(false);
							tax3.setChecked(false);
							bartax.setChecked(false);
							modifieritemval = "";
							allowbuybackval = "";
							countthisitemval = "";
							foodstampableval = "";
							promptpriceval = "";
							notesval = "";
							printonreceiptval = "";
							bonuspointsval = "";
							barcodeval = "";
							commissionval = "";
							locationval = "";
							unitsizeval = "";
							unittypeval = "";
							printervalfordept = "";
							foodcheckval = "";
							taxvalforsavefordept = "";
							taxvalforsaveforprod = "";
							taxvalforduplicate = 0;
							taxvalforsaveforduplicate = "";
							taxvalforprod = 0;
							optional_info.clear();
							skuarray.clear();
							modifierindividualitemsarray.clear();
							pricingvendorlistinorderinginfo.clear();
						}
						mCursor.close();
						/*
						 * }else{ Toast.makeText(InventoryActivity.this,
						 * "This is not demo login", Toast.LENGTH_SHORT).show();
						 * }
						 */
					}
				}
} catch (NumberFormatException e) {
	  e.printStackTrace();
	} catch (Exception e1) {
		  e1.printStackTrace();
		}
			}
		});

		logout.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Parameters.methodForLogout(InventoryActivity.this);
				finish();
			}
		});
		logout2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Parameters.methodForLogout(InventoryActivity.this);
				finish();
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
			}
		});

		product.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				modifieritemval = "";
				countthisitemval = "";
				printonreceiptval = "";
				allowbuybackval = "";
				foodstampableval = "";
				notesval = "";
				promptpriceval = "";
				skuval = "";
				bonuspointsval = "";
				barcodeval = "";
				commissionval = "";
				locationval = "";
				unitsizeval = "";
				unittypeval = "";
				printervalfordept = "";
				foodcheckval = "";
				taxvalforsavefordept = "";
				taxvalforsaveforprod = "";
				taxvalforduplicate = 0;
				taxvalforsaveforduplicate = "";
				commissionspinnerval = 0;
				taxvalforprod = 0;
				// optional_info.clear();
				skuarray.clear();
				modifierindividualitemsarray.clear();
				pricingvendorlistinorderinginfo.clear();
				detailslayout.removeAllViews();
				product.setBackgroundResource(R.drawable.highlightedtopmenuitem);
				department.setBackgroundResource(R.drawable.toprightmenu);
				vendor.setBackgroundResource(R.drawable.toprightmenu);
				category.setBackgroundResource(R.drawable.toprightmenu);
				product.setTextColor(Color.BLACK);
				department.setTextColor(Color.WHITE);
				vendor.setTextColor(Color.WHITE);
				category.setTextColor(Color.WHITE);

				LayoutInflater inflater = getLayoutInflater();
				final View layout = inflater.inflate(R.layout.product,
						(ViewGroup) v.findViewById(R.id.inflatingLayout));

				addcat = (Button) layout.findViewById(R.id.addcat);
				viewcat = (Button) layout.findViewById(R.id.viewcat);
				ll_add = (LinearLayout) layout.findViewById(R.id.add_ll);
				ll_view = (LinearLayout) layout.findViewById(R.id.view_ll);

				addcat.setBackgroundColor(Color.parseColor("#3c6586"));
				viewcat.setBackgroundColor(Color.parseColor("#cbcbcb"));
				ll_add.setVisibility(View.VISIBLE);
				ll_view.setVisibility(View.GONE);
				addcat.setTextColor(Color.WHITE);
				viewcat.setTextColor(Color.BLACK);

				list_View = (ListView) layout.findViewById(R.id.listView1);
				ll = (LinearLayout) layout.findViewById(R.id.linearLayout1);
				Button heading = (Button) layout.findViewById(R.id.button2);
				heading.setText("Product Details");
				list_View.setItemsCanFocus(false);

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

				departmentspinner = (Spinner) layout
						.findViewById(R.id.department);
				vendorspinner = (Spinner) layout.findViewById(R.id.vendor);
				tax1 = (CheckBox) layout.findViewById(R.id.checkBox1);
				tax2 = (CheckBox) layout.findViewById(R.id.checkBox2);
				tax3 = (CheckBox) layout.findViewById(R.id.checkBox3);
				bartax = (CheckBox) layout.findViewById(R.id.checkBox4);
				tax1.setText(taxlist.get(0));
				tax2.setText(taxlist.get(1));
				tax3.setText(taxlist.get(2));
				itemno = (EditText) layout.findViewById(R.id.item_no);
				description1 = (EditText) layout.findViewById(R.id.description);
				description2 = (EditText) layout
						.findViewById(R.id.description2);
				description2.setOnEditorActionListener(new OnEditorActionListener() {
					@Override
					public boolean onEditorAction(TextView v, int actionId,
							KeyEvent event) {
						if (actionId == EditorInfo.IME_ACTION_DONE) {
							InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
							imm.hideSoftInputFromWindow(description2.getWindowToken(), 0);
						}
						return false;
					}
				});
				cost = (EditText) layout.findViewById(R.id.cost);
				pricecharge = (EditText) layout.findViewById(R.id.price_charge);
				pricetax = (EditText) layout.findViewById(R.id.price_tax);
				instock = (EditText) layout.findViewById(R.id.instock);
				savecat = (Button) layout.findViewById(R.id.savecat);
				cancelcat = (Button) layout.findViewById(R.id.cancelcat);
				optionalinfo = (Button) layout.findViewById(R.id.optionalinfo);
				notes = (Button) layout.findViewById(R.id.notes);
				modifiers = (Button) layout.findViewById(R.id.modifiers);
				orderinginfo = (Button) layout.findViewById(R.id.orderinginfo);
				saleshistory = (Button) layout.findViewById(R.id.saleshistory);
				printers = (Button) layout.findViewById(R.id.printers);
				final ArrayList<String> deptspinnerdata = new ArrayList<String>();
				final ArrayList<String> vendorspinnerdata = new ArrayList<String>();
				deptspinnerdata.clear();
				vendorspinnerdata.clear();
				catspr = (Spinner) layout.findViewById(R.id.spinner1);
				dptspr = (Spinner) layout.findViewById(R.id.spinner2);
				displayspr = (Spinner) layout.findViewById(R.id.displayspinner);
				go_button = (Button) layout.findViewById(R.id.button3);
				actv = (AutoCompleteTextView) layout
						.findViewById(R.id.editTextfield);
				final ArrayList<String> deptspinnerdataSearch = new ArrayList<String>();
				final ArrayList<String> catspinnerdataSearch = new ArrayList<String>();
				deptspinnerdataSearch.add("All");
				catspinnerdataSearch.add("All");
				tax1.setChecked(true);

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
						gettingCount();
						ArrayList<String> autoTextStringsItems = new ArrayList<String>();
						autoTextStringsItems.addAll(autoTextStrings);
						autoTextStringsItems
								.addAll(autoTextStringsname);
						autoTextStringsItems
								.addAll(autoTextStringsvendor);

						ArrayAdapter<String> adapterauto = new ArrayAdapter<String>(
								InventoryActivity.this,
								android.R.layout.select_dialog_item,
								autoTextStringsItems);
						// Getting the instance of AutoCompleteTextView

						actv.setThreshold(1);// will start working from first
												// character
						actv.setAdapter(adapterauto);// setting the adapter data
														// into the
														// AutoCompleteTextView
						System.out.println("11111113333333333333333");
						actv.setTextColor(Color.BLACK);

						
						ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
								InventoryActivity.this,
								android.R.layout.simple_spinner_item,
								displayarray);
						adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
						displayspr.setAdapter(adapter2);

						listUpdateforproduct(offsetvals, "100",
								totalrecordselectQuery);

					}
				});
				// if(Parameters.usertype.equals("demo")){

				
				Cursor mCursor = dbforloginlogoutReadInvetory.rawQuery(
						"select " + DatabaseForDemo.DepartmentID + " from "
								+ DatabaseForDemo.DEPARTMENT_TABLE, null);
				System.out.println(mCursor);
				if (mCursor != null) {
					if (mCursor.moveToFirst()) {
						do {
							String catid = mCursor.getString(mCursor
									.getColumnIndex(DatabaseForDemo.DepartmentID));
							deptspinnerdata.add(catid);
						} while (mCursor.moveToNext());
					}
					
					
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							InventoryActivity.this,
							android.R.layout.simple_spinner_item,
							deptspinnerdata);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
					departmentspinner.setAdapter(adapter);
				} else {
					Toast.makeText(getApplicationContext(),
							"This is no profile data in demo login",
							Toast.LENGTH_SHORT).show();
				}
				mCursor.close();
				Cursor mCursor1 = dbforloginlogoutReadInvetory.rawQuery(
						"select " + DatabaseForDemo.DepartmentID + " from "
								+ DatabaseForDemo.DEPARTMENT_TABLE, null);
				System.out.println(mCursor1);
				if (mCursor1 != null) {
					if (mCursor1.moveToFirst()) {
						do {
							String catid = mCursor1.getString(mCursor1
									.getColumnIndex(DatabaseForDemo.DepartmentID));
							deptspinnerdataSearch.add(catid);
						} while (mCursor1.moveToNext());
					}
				} else {
					Toast.makeText(getApplicationContext(), "there is no data",
							Toast.LENGTH_SHORT).show();
				}
				mCursor1.close();
				final ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(
						InventoryActivity.this,
						android.R.layout.simple_spinner_item,
						deptspinnerdataSearch);
				adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
				dptspr.setAdapter(adapter1);

				Cursor mCursor3 = dbforloginlogoutReadInvetory.rawQuery(
						"select " + DatabaseForDemo.CategoryId + " from "
								+ DatabaseForDemo.CATEGORY_TABLE, null);
				System.out.println(mCursor3);
				if (mCursor3 != null) {
					if (mCursor3.moveToFirst()) {
						do {
							String catid = mCursor3.getString(mCursor3
									.getColumnIndex(DatabaseForDemo.CategoryId));
							catspinnerdataSearch.add(catid);
						} while (mCursor3.moveToNext());
					}
				} else {
					Toast.makeText(getApplicationContext(), "there is no data",
							Toast.LENGTH_SHORT).show();
				}
				mCursor3.close();
				ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
						InventoryActivity.this,
						android.R.layout.simple_spinner_item,
						catspinnerdataSearch);
				adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
				catspr.setAdapter(adapter2);
				/*
				 * Cursor mCursor2 =
				 * dbforloginlogoutReadInvetory.rawQuery("select "
				 * +DatabaseForDemo
				 * .VENDOR_NO+" from "+DatabaseForDemo.VENDOR_TABLE, null);
				 * System.out.println(mCursor2); if(mCursor2!=null){
				 * if(mCursor2.moveToFirst()){ do{ String catid =
				 * mCursor2.getString
				 * (mCursor2.getColumnIndex(DatabaseForDemo.VENDOR_NO));
				 * vendorspinnerdata.add(catid); }while(mCursor2.moveToNext());
				 * } mCursor2.close();  ArrayAdapter<String>
				 * adapter = new ArrayAdapter<String>(InventoryActivity.this,
				 * android.R.layout.simple_spinner_item, vendorspinnerdata);
				 * adapter
				 * .setDropDownViewResource(android.R.layout.simple_spinner_item
				 * ); vendorspinner.setAdapter(adapter); }else{
				 * Toast.makeText(getApplicationContext(),
				 * "This is no profile data in demo login",
				 * Toast.LENGTH_SHORT).show(); }
				 */

				/*
				 * }else{ Toast.makeText(getApplicationContext(),
				 * "This is not demo login", Toast.LENGTH_SHORT).show(); }
				 */

				catspr.setOnTouchListener(new OnTouchListener() {
					
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						displayarray.clear();
						ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
								InventoryActivity.this,
								android.R.layout.simple_spinner_item, displayarray);
						adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
						displayspr.setAdapter(adapter2);
						deptspinnerdataSearch.clear();
						deptspinnerdataSearch.add("All");
						String urlval = catspr.getSelectedItem().toString();
						String qqqqqq="select " + DatabaseForDemo.DepartmentID + " from "
								+ DatabaseForDemo.DEPARTMENT_TABLE;
						if(!urlval.equals("All")){

							qqqqqq="select " + DatabaseForDemo.DepartmentID + " from "
									+ DatabaseForDemo.DEPARTMENT_TABLE +" where "+DatabaseForDemo.CategoryForDepartment+ "=\""
											+ urlval + "\"";
						}
						Cursor mCursor = dbforloginlogoutReadInvetory.rawQuery(qqqqqq, null);
						// startManagingCursor(mCursor);
						System.out.println(mCursor);
						if (mCursor != null) {
							if (mCursor.moveToFirst()) {
								do {
									String catid = mCursor.getString(mCursor
											.getColumnIndex(DatabaseForDemo.DepartmentID));
									deptspinnerdataSearch.add(catid);
								} while (mCursor.moveToNext());
							}
						}
						mCursor.close();
						adapter1.setNotifyOnChange(true);
						adapter1.setDropDownViewResource(android.R.layout.simple_spinner_item);
						dptspr.setAdapter(adapter1);
						return false;
					}
				});
				dptspr.setOnTouchListener(new OnTouchListener() {
					@Override
					public boolean onTouch(View v, MotionEvent event) {
						// TODO Auto-generated method stub
						displayarray.clear();
						ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
								InventoryActivity.this,
								android.R.layout.simple_spinner_item, displayarray);
						adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
						displayspr.setAdapter(adapter2);
						return false;
					}
				});
				

				go_button.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						itemno_data.clear();
						itemdesc_data.clear();
						pricecharge_data.clear();
						stock_data.clear();
						desc2_data.clear();
						vendor_data.clear();
						departmentforproduct_data.clear();
						cost_data.clear();
						pricetax_data.clear();
						tax_product_data.clear();
						total_itemno_data.clear();
						total_itemdesc_data.clear();
						total_pricecharge_data.clear();
						total_stock_data.clear();
						total_desc2_data.clear();
						total_vendor_data.clear();
						total_departmentforproduct_data.clear();
						total_cost_data.clear();
						total_pricetax_data.clear();
						total_tax_product_data.clear();
						ll.removeAllViews();
						String offsetvalingo = null, limitvalingo = "100", pages = null;
						System.out.println("5555555555555555555555");
						if (displayarray.size() == 0) {
							System.out.println("val is null in go");
							offsetvalingo = "1";
						} else {
							pages = displayspr.getSelectedItem().toString();
							System.out.println("pages offset val is///////////"
									+ pages);
							String[] a = pages.split("-");
							System.out.println("the array values are:"
									+ a.length);
							for (int i = 0; i < a.length; i++) {
								offsetvalingo = a[0].trim();
								System.out.println("offset val is///////////:"
										+ offsetvalingo);
							}
						}
						System.out.println("66666666666666666666666666");
						String str = actv.getText().toString().trim();
						String selectQuery = null;
						System.out.println("auto text view val is:" + str);
						if (str.length() > 0) 
						{
							System.out.println("1111111111111");
							if (autoTextStrings.contains(str))
							{
								System.out.println("2222222222222");
								selectQuery = "SELECT  * FROM "
										+ DatabaseForDemo.INVENTORY_TABLE
										+ " where upper("
										+ DatabaseForDemo.INVENTORY_ITEM_NO
										+ ")= upper(\"" + str + "\")";
								System.out.println("selectQuery11"+selectQuery);
							}
							else 
							{
								System.out.println("33333333333333");
								if (autoTextStringsname .contains(str)) 
								{
									System.out.println("44444444444444");
									selectQuery = "SELECT  * FROM "
											+ DatabaseForDemo.INVENTORY_TABLE
											+ " where upper("
											+ DatabaseForDemo.INVENTORY_ITEM_NAME
											+ ")= upper(\"" + str + "\")";
									System.out.println("selectQuery22"+selectQuery);
								} 
								else 
								{
									System.out.println("55555555555555555");
									selectQuery = "SELECT  * FROM "
											+ DatabaseForDemo.ORDERING_INFO_TABLE
											+ " where upper("
											+ DatabaseForDemo.VENDERPART_NO
											+ ")= upper(\"" + str + "\")";
									System.out.println("selectQuery33"+selectQuery);
								}

							}

						} 
						else 
						{
							System.out.println("6666666666666");
							String dp = dptspr.getSelectedItem().toString();
							String cat = catspr.getSelectedItem().toString();
							Log.d("dp", "" + dp);
							Log.d("cat", "" + cat);

							if (dp.equals("All") && cat.equals("All"))
							{
								System.out.println("77777777777777777");
								selectQuery = "SELECT  * FROM "
										+ DatabaseForDemo.INVENTORY_TABLE;
								System.out.println("selectQuery44"+selectQuery);
							}
							else
							{
								System.out.println("888888888888888888");
								if (!dp.equals("All") && !cat.equals("All"))
								{
									System.out.println("9999999999999999");
									selectQuery = "SELECT  * FROM "
											+ DatabaseForDemo.INVENTORY_TABLE
											+ " where  upper("
											+ DatabaseForDemo.INVENTORY_CATEGORY
											+ ")= upper(\""
											+ cat
											+ ")\" and  upper("
											+ DatabaseForDemo.INVENTORY_DEPARTMENT
											+ ")= upper(\"" + dp + "\")";
									System.out.println("selectQuery55"+selectQuery);
								}

								if (dp.equals("All") && !cat.equals("All")) 
								{
									System.out.println("111111111111111000000000");
									selectQuery = "SELECT  * FROM "
											+ DatabaseForDemo.INVENTORY_TABLE
											+ " where  upper("
											+ DatabaseForDemo.INVENTORY_CATEGORY
											+ ")= upper(\"" + cat + "\")";
									System.out.println("selectQuery66"+selectQuery);
								}
								if (!dp.equals("All") && cat.equals("All")) 
								{
									System.out.println("11111111111122222222222222222");
									selectQuery = "SELECT  * FROM "
											+ DatabaseForDemo.INVENTORY_TABLE
											+ " where  upper("
											+ DatabaseForDemo.INVENTORY_DEPARTMENT
											+ ")= upper(\"" + dp + "\")";
									System.out.println("selectQuery77"+selectQuery);
								}

							}

						}
						
						Log.v("ffff", ""+selectQuery);
						Cursor mCursor = dbforloginlogoutReadInvetory
								.rawQuery(selectQuery, null);
						int totalnoofrecords = mCursor.getCount();
						if (mCursor != null) {
							if (mCursor.moveToFirst()) {
								do {
									String itemno = mCursor.getString(mCursor
											.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NO));
									total_itemno_data.add(itemno);
									String itemname = mCursor.getString(mCursor
											.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NAME));
									total_itemdesc_data.add(itemname);
									String desc2 = mCursor.getString(mCursor
											.getColumnIndex(DatabaseForDemo.INVENTORY_SECOND_DESCRIPTION));
									total_desc2_data.add(desc2);
									String pricecharge = mCursor.getString(mCursor
											.getColumnIndex(DatabaseForDemo.INVENTORY_PRICE_CHANGE));
									total_pricecharge_data.add(pricecharge);
									String instock = mCursor.getString(mCursor
											.getColumnIndex(DatabaseForDemo.INVENTORY_IN_STOCK));
									total_stock_data.add(instock);
								} while (mCursor.moveToNext());
							}
						}
						mCursor.close();
						System.out.println("first cursor is executed");
						for (int ii = 0; ii < total_itemno_data.size(); ii++) {

							String selectQueryforvendor = "SELECT  "
									+ DatabaseForDemo.VENDERPART_NO + " FROM "
									+ DatabaseForDemo.ORDERING_INFO_TABLE
									+ " where "
									+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\""
									+ total_itemno_data.get(ii) + "\"" + " AND "
									+ DatabaseForDemo.PREFERRED + "=\"true\"";

							Cursor mCursorforvendor = dbforloginlogoutReadInvetory.rawQuery(
											selectQueryforvendor, null);
							if (mCursorforvendor.getCount() > 0) {
								if (mCursorforvendor != null) {
									if (mCursorforvendor.moveToFirst()) {
										do {
											if (mCursorforvendor.isNull(mCursorforvendor
													.getColumnIndex(DatabaseForDemo.VENDERPART_NO))) {

											} else {
												String vendornum = mCursorforvendor
														.getString(mCursorforvendor
																.getColumnIndex(DatabaseForDemo.VENDERPART_NO));
												System.out
														.println("vendro no is:"
																+ vendornum);
												total_vendor_data
														.add(vendornum);
											}
										} while (mCursorforvendor.moveToNext());
									}
								}
							} else {
								total_vendor_data.add("");
							}
							mCursorforvendor.close();
						}
						
						displayarray.clear();
						
						System.out.println("total records val is:"
								+ totalnoofrecords);
						int resultsperset = 20;
						int noofsets = 0;
						int min = 1;
						// if(totalnoofrecords>200){
						noofsets = totalnoofrecords / resultsperset;
						int reminder = totalnoofrecords % resultsperset;
						if (reminder > 0) {
							noofsets = noofsets + 1;
						}
						System.out.println("noofrecords:" + noofsets);
						for (int i = 1; i <= noofsets; i++) {
							String displaystring;
							if (i == noofsets) {
								displaystring = min + " - " + totalnoofrecords;
							} else {
								displaystring = min + " - "
										+ (i * resultsperset);
							}
							min = (i * resultsperset) + 1;
							System.out.println("display string value is:"
									+ displaystring);
							displayarray.add(displaystring);
						}

						System.out.println(displayarray);
						ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
								InventoryActivity.this,
								android.R.layout.simple_spinner_item,
								displayarray);
						adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
						displayspr.setAdapter(adapter2);

						if (selectQuery.equals("SELECT  * FROM "
								+ DatabaseForDemo.INVENTORY_TABLE)) {
							listUpdateforproduct(offsetvalingo, "100",
									selectQuery);
							gettingCount();
							displayspr.setSelection(displayarray.indexOf(pages));
						} else {
							listUpdateforproduct(offsetvalingo, "100",
									selectQuery);
							displayspr.setSelection(displayarray.indexOf(pages));
						}
					}
				});

				departmentspinner
						.setOnItemSelectedListener(new OnItemSelectedListener() {

							@Override
							public void onItemSelected(AdapterView<?> arg0,
									View arg1, int arg2, long arg3) {
								// TODO Auto-generated method stub
								tax1.setChecked(false);
								tax2.setChecked(false);
								tax3.setChecked(false);
								bartax.setChecked(false);
								String deptspinnerval = departmentspinner
										.getSelectedItem().toString();
								if (deptspinnerval.equals("")) {

								} else {
									Cursor mCursor2 = dbforloginlogoutWriteInvetory.rawQuery(
													"select "/*
															 * +DatabaseForDemo.
															 * PrinterForDept
															 * +","
															 */
															+ DatabaseForDemo.FoodstampableForDept
															+ ","
															+ DatabaseForDemo.TaxValForDept
															+ " from "
															+ DatabaseForDemo.DEPARTMENT_TABLE
															+ " where "
															+ DatabaseForDemo.DepartmentID
															+ "=\""
															+ deptspinnerval
															+ "\"", null);
									System.out.println(mCursor2);
									if (mCursor2 != null) {
										if (mCursor2.moveToFirst()) {
											do {
												/*
												 * if(mCursor2.isNull(mCursor2.
												 * getColumnIndex
												 * (DatabaseForDemo
												 * .PrinterForDept))){
												 * printerval = ""; }else{
												 * printerval =
												 * mCursor2.getString
												 * (mCursor2.getColumnIndex
												 * (DatabaseForDemo
												 * .PrinterForDept)); }
												 */
												foodstampableval = mCursor2.getString(mCursor2
														.getColumnIndex(DatabaseForDemo.FoodstampableForDept));
												String taxval = mCursor2.getString(mCursor2
														.getColumnIndex(DatabaseForDemo.TaxValForDept));
												String mystring = taxval;
												String[] a = mystring
														.split(",");
												System.out
														.println("the array values are:"
																+ a.length);
												for (int i = 0; i < a.length; i++) {
													String substr = a[i];
													if (substr.equals("Tax1")) {
														tax1.setChecked(true);
													} else if (substr
															.equals("Tax2")) {
														tax2.setChecked(true);
													} else if (substr
															.equals("Tax3")) {
														tax3.setChecked(true);
													} else if (substr
															.equals("BarTax")) {
														bartax.setChecked(true);
													}
												}
											} while (mCursor2.moveToNext());
										}
									}
									mCursor2.close();
								}
							}

							@Override
							public void onNothingSelected(AdapterView<?> arg0) {
								// TODO Auto-generated method stub

							}
						});

				cancelcat.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						departmentspinner.setSelection(0);
						vendorspinner.setSelection(0);
						tax1.setChecked(true);
						tax2.setChecked(false);
						tax3.setChecked(false);
						bartax.setChecked(false);
						itemno.setText("");
						description1.setText("");
						description2.setText("");
						cost.setText("");
						pricecharge.setText("");
						pricetax.setText("");
						instock.setText("");
						modifieritemval = "";
						countthisitemval = "";
						printonreceiptval = "";
						allowbuybackval = "";
						foodstampableval = "";
						notesval = "";
						promptpriceval = "'";
						skuval = "";
						bonuspointsval = "";
						barcodeval = "";
						commissionval = "";
						locationval = "";
						unitsizeval = "";
						unittypeval = "";
						printervalfordept = "";
						foodcheckval = "";
						taxvalforprod = 0;
						taxvalforduplicate = 0;
						taxvalforsaveforduplicate = "";
						taxvalforsavefordept = "";
						taxvalforsaveforprod = "";
						commissionspinnerval = 0;
						// optional_info.clear();
						skuarray.clear();
						modifierindividualitemsarray.clear();
						pricingvendorlistinorderinginfo.clear();
					}
				});

				savecat.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						try{
						final String itemnumber = itemno.getText().toString();
						final String itemname = description1.getText()
								.toString();
						final String description = description2.getText()
								.toString();
						final String costvalforsave = cost.getText().toString();
						final String price_charge = pricecharge.getText()
								.toString();
						String price_tax = pricetax.getText().toString();
						final String instockval = instock.getText().toString();

						if (deptspinnerdata.isEmpty()) {
							Toast.makeText(
									InventoryActivity.this,
									"Please add atleast a single department and vendor",
									Toast.LENGTH_SHORT).show();
						} else {

							final String departmentofitem = departmentspinner
									.getSelectedItem().toString();
							// final String vendorval =
							// vendorspinner.getSelectedItem().toString();
							taxvalforsaveforprod = "";
							if (departmentofitem.equals("")
									|| itemnumber.equals("")
									|| itemname.equals("")
									|| description.equals("")
									|| price_charge.equals("")
									|| costvalforsave.equals("")) {
								Toast.makeText(InventoryActivity.this,
										"Please enter details",
										Toast.LENGTH_SHORT).show();
							} else {
								String costval = pricecharge.getText().toString();
								taxvalforprod = 0;
								if (tax1.isChecked()) {
									taxvalforsaveforprod = taxvalforsaveforprod
											+ tax1.getText().toString() + ",";
									double cost = Double.valueOf(costval);

									String query = "select * from "
											+ DatabaseForDemo.TAX_TABLE
											+ " where "
											+ DatabaseForDemo.TAX_NAME + "=\""
											+ tax1.getText().toString() + "\"";
									System.out.println(query);

									Cursor cursor = dbforloginlogoutReadInvetory.rawQuery(query, null);
									if (cursor != null) {
										if (cursor.moveToFirst()) {
											do {
												double taxvalpercent = Double.valueOf(df
														.format(cursor.getDouble(cursor
																.getColumnIndex(DatabaseForDemo.TAX_VALUE))));
												System.out.println("     "
														+ taxvalpercent);
												taxvalforprod = cost
														* (taxvalpercent / 100);
											} while (cursor.moveToNext());
										}
									} else {
										Toast.makeText(getApplicationContext(),
												"No Tax value",
												Toast.LENGTH_SHORT).show();
									}
									cursor.close();
								}
								if (tax2.isChecked()) {
									taxvalforsaveforprod = taxvalforsaveforprod
											+ tax2.getText().toString() + ",";
									double cost = Double.valueOf(costval);

									String query = "select "
											+ DatabaseForDemo.TAX_VALUE
											+ " from "
											+ DatabaseForDemo.TAX_TABLE
											+ " where "
											+ DatabaseForDemo.TAX_NAME + "=\""
											+ tax2.getText().toString() + "\"";
									System.out.println(query);
									
									Cursor cursor = dbforloginlogoutReadInvetory.rawQuery(query, null);
									if (cursor != null) {
										if (cursor.moveToFirst()) {
											do {
												double taxvalpercent = Double.valueOf(df
														.format(cursor.getDouble(cursor
																.getColumnIndex(DatabaseForDemo.TAX_VALUE))));
												System.out.println("     "
														+ taxvalpercent);
												taxvalforprod = taxvalforprod
														+ (cost * (taxvalpercent / 100));
											} while (cursor.moveToNext());
										}
									} else {
										Toast.makeText(getApplicationContext(),
												"No Tax value",
												Toast.LENGTH_SHORT).show();
									}
									cursor.close();
								}
								if (tax3.isChecked()) {
									taxvalforsaveforprod = taxvalforsaveforprod
											+ tax3.getText().toString() + ",";
									double cost = Double.valueOf(costval);

									String query = "select "
											+ DatabaseForDemo.TAX_VALUE
											+ " from "
											+ DatabaseForDemo.TAX_TABLE
											+ " where "
											+ DatabaseForDemo.TAX_NAME + "=\""
											+ tax3.getText().toString() + "\"";
									System.out.println(query);
									
									Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(query, null);
									if (cursor != null) {
										if (cursor.moveToFirst()) {
											do {
												double taxvalpercent = Double.valueOf(df
														.format(cursor.getDouble(cursor
																.getColumnIndex(DatabaseForDemo.TAX_VALUE))));
												System.out.println("     "
														+ taxvalpercent);
												taxvalforprod = taxvalforprod
														+ (cost * (taxvalpercent / 100));
											} while (cursor.moveToNext());
										}
									} else {
										Toast.makeText(getApplicationContext(),
												"No Tax value",
												Toast.LENGTH_SHORT).show();
									}
									cursor.close();
								}
								if (bartax.isChecked()) {
									taxvalforsaveforprod = taxvalforsaveforprod
											+ bartax.getText().toString();
									taxvalforprod = taxvalforprod + 0;
									System.out.println("     " + taxvalforprod);
								}
								System.out.println(taxvalforsaveforprod);
								System.out.println("taxvalue is padma padma:"
										+ taxvalforprod);
								
								if(price_tax.equals(price_charge)){
									Double taxd = Double.parseDouble(price_tax)+taxvalforprod;
									price_tax = taxd.toString();
									System.out.println("pricetaxval is:"+price_tax);
								}else{
									
								}

								// if(Parameters.usertype.equals("demo")){
								final String uniqueidval = Parameters
										.randomValue();
								
								String selectQuery = "SELECT  * FROM "
										+ DatabaseForDemo.INVENTORY_TABLE
										+ " where "
										+ DatabaseForDemo.INVENTORY_ITEM_NO
										+ "=\"" + itemnumber + "\"";

								Cursor mCursor =dbforloginlogoutReadInvetory.rawQuery(selectQuery, null);
								if (mCursor.getCount() > 0) {
									Toast.makeText(InventoryActivity.this,
											"Product already exist",
											Toast.LENGTH_SHORT).show();
									
								} else {
									
									ContentValues contentValues = new ContentValues();
									contentValues.put(
											DatabaseForDemo.UNIQUE_ID,
											uniqueidval);
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
											.put(DatabaseForDemo.INVENTORY_DEPARTMENT,
													departmentofitem);
									contentValues.put(
											DatabaseForDemo.INVENTORY_ITEM_NO,
											itemnumber);
									contentValues
											.put(DatabaseForDemo.INVENTORY_ITEM_NAME,
													itemname);
									contentValues
											.put(DatabaseForDemo.INVENTORY_SECOND_DESCRIPTION,
													description);
									contentValues.put(
											DatabaseForDemo.INVENTORY_AVG_COST,
											costvalforsave);
									contentValues
											.put(DatabaseForDemo.INVENTORY_PRICE_CHANGE,
													price_charge);
									contentValues
											.put(DatabaseForDemo.INVENTORY_PRICE_TAX,
													price_tax);
									contentValues.put(
											DatabaseForDemo.INVENTORY_IN_STOCK,
											instockval);
									// contentValues.put(DatabaseForDemo.INVENTORY_VENDOR,
									// vendorval);
									contentValues.put(
											DatabaseForDemo.INVENTORY_TAXONE,
											taxvalforsaveforprod);
									contentValues.put(
											DatabaseForDemo.INVENTORY_QUANTITY,
											"1");
									contentValues.put(
											DatabaseForDemo.INVENTORY_NOTES,
											notesval);
									contentValues.put(
											DatabaseForDemo.CHECKED_VALUE,
											"true");
									contentValues
											.put(DatabaseForDemo.INVENTORY_TOTAL_TAX,
													taxvalforprod);
									dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.INVENTORY_TABLE,
											null, contentValues);

									optional_info = new ContentValues();
									optional_info.put(
											DatabaseForDemo.UNIQUE_ID,
											uniqueidval);
									optional_info.put(
											DatabaseForDemo.CREATED_DATE,
											Parameters.currentTime());
									optional_info.put(
											DatabaseForDemo.MODIFIED_DATE,
											Parameters.currentTime());
									optional_info.put(DatabaseForDemo.MODIFIED_IN,
											"Local");
									optional_info.put(
											DatabaseForDemo.BONUS_POINTS,
											bonuspointsval);
									optional_info.put(DatabaseForDemo.BARCODES,
											barcodeval);
									optional_info.put(DatabaseForDemo.LOCATION,
											locationval);
									optional_info.put(
											DatabaseForDemo.UNIT_SIZE,
											unitsizeval);
									optional_info.put(
											DatabaseForDemo.UNIT_TYPE,
											unittypeval);
									optional_info
											.put(DatabaseForDemo.COMMISSION_OPTIONAL_INFO,
													commissionval);
									optional_info
											.put(DatabaseForDemo.INVENTORY_ALLOW_BUYBACK,
													allowbuybackval);
									optional_info
											.put(DatabaseForDemo.INVENTORY_PROMPT_PRICE,
													promptpriceval);
									optional_info
											.put(DatabaseForDemo.INVENTORY_FOODSTAMPABLE,
													foodstampableval);
									optional_info
											.put(DatabaseForDemo.INVENTORY_PRINT_ON_RECEIPT,
													printonreceiptval);
									optional_info
											.put(DatabaseForDemo.INVENTORY_COUNT_THIS_ITEM,
													countthisitemval);
									optional_info
											.put(DatabaseForDemo.INVENTORY_MODIFIER_ITEM,
													modifieritemval);
									optional_info.put(
											DatabaseForDemo.INVENTORY_ITEM_NO,
											itemno.getText().toString());
									dbforloginlogoutWriteInvetory.insert(
											DatabaseForDemo.OPTIONAL_INFO_TABLE,
											null, optional_info);

									for (int i = 0; i < skuarray.size(); i++) {
										ContentValues alternate_sku = new ContentValues();
										alternate_sku.put(
												DatabaseForDemo.UNIQUE_ID,
												uniqueidval);
										alternate_sku.put(
												DatabaseForDemo.CREATED_DATE,
												Parameters.currentTime());
										alternate_sku.put(
												DatabaseForDemo.MODIFIED_DATE,
												Parameters.currentTime());
										alternate_sku.put(
												DatabaseForDemo.MODIFIED_IN,
												"Local");
										alternate_sku
												.put(DatabaseForDemo.ALTERNATE_SKU_VALUE,
														skuarray.get(i));
										alternate_sku
												.put(DatabaseForDemo.INVENTORY_ITEM_NO,
														itemno.getText()
																.toString());
										dbforloginlogoutWriteInvetory.insert(
												DatabaseForDemo.ALTERNATE_SKU_TABLE,
												null, alternate_sku);
									}

									for (int i = 0; i < pricingvendorlistinorderinginfo
											.size(); i++) {
										ContentValues ordering_info = new ContentValues();
										ordering_info.put(
												DatabaseForDemo.UNIQUE_ID,
												uniqueidval);
										ordering_info.put(
												DatabaseForDemo.CREATED_DATE,
												Parameters.currentTime());
										ordering_info.put(
												DatabaseForDemo.MODIFIED_DATE,
												Parameters.currentTime());
										ordering_info.put(
												DatabaseForDemo.MODIFIED_IN,
												"Local");
										ordering_info
												.put(DatabaseForDemo.VENDOR_COMPANY_NAME,
														pricingvendorlistinorderinginfo
																.get(i)
																.get("vendorname"));
										ordering_info.put(
												DatabaseForDemo.VENDERPART_NO,
												pricingvendorlistinorderinginfo
														.get(i).get("partno"));
										ordering_info.put(
												DatabaseForDemo.COST_PER,
												pricingvendorlistinorderinginfo
														.get(i).get(
																"costperase"));
										ordering_info
												.put(DatabaseForDemo.CASE_COST,
														pricingvendorlistinorderinginfo
																.get(i)
																.get("casecost"));
										ordering_info
												.put(DatabaseForDemo.NO_IN_CASE,
														pricingvendorlistinorderinginfo
																.get(i)
																.get("noincase"));
										ordering_info.put(
												DatabaseForDemo.PREFERRED,
												pricingvendorlistinorderinginfo
														.get(i)
														.get("preferred"));
										ordering_info
												.put(DatabaseForDemo.INVENTORY_ITEM_NO,
														itemnumber);
										dbforloginlogoutWriteInvetory.insert(
												DatabaseForDemo.ORDERING_INFO_TABLE,
												null, ordering_info);
									}

									
									
									Toast.makeText(InventoryActivity.this,
											"Product inserted successfully",
											Toast.LENGTH_SHORT).show();

									try {
										JSONObject data = new JSONObject();
										JSONObject jsonobj = new JSONObject();
										jsonobj.put(
												DatabaseForDemo.INVENTORY_DEPARTMENT,
												departmentofitem);
										jsonobj.put(
												DatabaseForDemo.INVENTORY_ITEM_NO,
												itemnumber);
										jsonobj.put(
												DatabaseForDemo.INVENTORY_ITEM_NAME,
												itemname);
										jsonobj.put(
												DatabaseForDemo.INVENTORY_SECOND_DESCRIPTION,
												description);
										jsonobj.put(
												DatabaseForDemo.INVENTORY_AVG_COST,
												costvalforsave);
										jsonobj.put(
												DatabaseForDemo.INVENTORY_PRICE_CHANGE,
												price_charge);
										jsonobj.put(
												DatabaseForDemo.INVENTORY_PRICE_TAX,
												price_tax);
										jsonobj.put(
												DatabaseForDemo.INVENTORY_IN_STOCK,
												instockval);
										// jsonobj.put(DatabaseForDemo.INVENTORY_VENDOR,
										// vendorval);
										jsonobj.put(
												DatabaseForDemo.INVENTORY_TAXONE,
												taxvalforsaveforprod);
										jsonobj.put(
												DatabaseForDemo.INVENTORY_NOTES,
												notesval);
										jsonobj.put(
												DatabaseForDemo.INVENTORY_QUANTITY,
												"1");
										jsonobj.put(
												DatabaseForDemo.INVENTORY_TOTAL_TAX,
												taxvalforprod);
										jsonobj.put(
												DatabaseForDemo.CHECKED_VALUE,
												"true");
										jsonobj.put(DatabaseForDemo.UNIQUE_ID,
												uniqueidval);
										JSONArray fields = new JSONArray();
										fields.put(0, jsonobj);
										data.put("fields", fields);
										dataval = data.toString();
									} catch (JSONException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}

									try {
										JSONObject data = new JSONObject();
										JSONObject jsonobj = new JSONObject();
										jsonobj.put(
												DatabaseForDemo.BONUS_POINTS,
												bonuspointsval);
										jsonobj.put(DatabaseForDemo.BARCODES,
												barcodeval);
										jsonobj.put(DatabaseForDemo.LOCATION,
												locationval);
										jsonobj.put(DatabaseForDemo.UNIT_SIZE,
												unitsizeval);
										jsonobj.put(DatabaseForDemo.UNIT_TYPE,
												unittypeval);
										jsonobj.put(
												DatabaseForDemo.COMMISSION_OPTIONAL_INFO,
												commissionval);
										jsonobj.put(
												DatabaseForDemo.INVENTORY_ALLOW_BUYBACK,
												allowbuybackval);
										jsonobj.put(
												DatabaseForDemo.INVENTORY_PROMPT_PRICE,
												promptpriceval);
										jsonobj.put(
												DatabaseForDemo.INVENTORY_FOODSTAMPABLE,
												foodstampableval);
										jsonobj.put(
												DatabaseForDemo.INVENTORY_PRINT_ON_RECEIPT,
												printonreceiptval);
										jsonobj.put(
												DatabaseForDemo.INVENTORY_COUNT_THIS_ITEM,
												countthisitemval);
										jsonobj.put(
												DatabaseForDemo.INVENTORY_MODIFIER_ITEM,
												modifieritemval);
										jsonobj.put(
												DatabaseForDemo.INVENTORY_ITEM_NO,
												itemnumber);
										jsonobj.put(DatabaseForDemo.UNIQUE_ID,
												uniqueidval);
										JSONArray fields = new JSONArray();
										fields.put(0, jsonobj);
										data.put("fields", fields);
										datavalforproduct1 = data.toString();
									} catch (JSONException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}

									try {
										JSONObject data = new JSONObject();
										JSONObject jsonobj = new JSONObject();
										JSONArray fields = new JSONArray();

										for (int i = 0; i < skuarray.size(); i++) {
											fields.put(i, jsonobj);
											jsonobj.put(
													DatabaseForDemo.ALTERNATE_SKU_VALUE,
													skuarray.get(i));
											jsonobj.put(
													DatabaseForDemo.INVENTORY_ITEM_NO,
													itemnumber);
											jsonobj.put(
													DatabaseForDemo.UNIQUE_ID,
													uniqueidval);
										}
										data.put("fields", fields);
										datavalforproduct2 = data.toString();
										
									} catch (JSONException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}

								/*	try {
										JSONObject data = new JSONObject();
										JSONObject jsonobj = new JSONObject();
										JSONArray fields = new JSONArray();
										modifiersArrayserver.clear();
										for (int i = 0; i < modifierindividualitemsarray
												.size(); i++) {
											jsonobj.put(DatabaseForDemo.UNIQUE_ID,
													uniqueidforprodedit);
											jsonobj.put(DatabaseForDemo.CREATED_DATE,
													Parameters.currentTime());
											jsonobj.put(DatabaseForDemo.MODIFIED_DATE,
													Parameters.currentTime());
											jsonobj
													.put(DatabaseForDemo.MODIFIED_IN, "Local");
											jsonobj.put(
													DatabaseForDemo.MODIFIER_ITEM_NO,
													modifierindividualitemsarray.get(i).get(
															"itemno"));
											jsonobj.put(
													DatabaseForDemo.INVENTORY_ITEM_NAME,
													modifierindividualitemsarray.get(i).get(
															"itemname"));
											jsonobj.put(
													DatabaseForDemo.INVENTORY_ITEM_NO,
													itemnumber);
											jsonobj.put(
													DatabaseForDemo.UNIQUE_ID,
													uniqueidval);
											Log.e("nn srihari "+modifierindividualitemsarray.get(i)
													.get("itemname"),"id "+modifierindividualitemsarray.get(i)
													.get("itemno"));
											fields.put(0, jsonobj);
										data.put("fields", fields);
										datavalforproduct3 = data.toString();
										modifiersArrayserver.add(datavalforproduct3);
										}
									} catch (JSONException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}*/

									try {
										JSONObject data = new JSONObject();
										JSONObject jsonobj = new JSONObject();
										JSONArray fields = new JSONArray();

										for (int i = 0; i < pricingvendorlistinorderinginfo
												.size(); i++) {
											fields.put(i, jsonobj);
											jsonobj.put(
													DatabaseForDemo.VENDOR_COMPANY_NAME,
													pricingvendorlistinorderinginfo
															.get(i)
															.get("vendorname"));
											jsonobj.put(
													DatabaseForDemo.VENDERPART_NO,
													pricingvendorlistinorderinginfo
															.get(i).get(
																	"partno"));
											jsonobj.put(
													DatabaseForDemo.COST_PER,
													pricingvendorlistinorderinginfo
															.get(i)
															.get("costperase"));
											jsonobj.put(
													DatabaseForDemo.CASE_COST,
													pricingvendorlistinorderinginfo
															.get(i).get(
																	"casecost"));
											jsonobj.put(
													DatabaseForDemo.NO_IN_CASE,
													pricingvendorlistinorderinginfo
															.get(i).get(
																	"noincase"));
											jsonobj.put(
													DatabaseForDemo.PREFERRED,
													pricingvendorlistinorderinginfo
															.get(i)
															.get("preferred"));
											jsonobj.put(
													DatabaseForDemo.INVENTORY_ITEM_NO,
													itemnumber);
											jsonobj.put(
													DatabaseForDemo.UNIQUE_ID,
													uniqueidval);
										}
										data.put("fields", fields);
										datavalforproduct4 = data.toString();
										
									} catch (JSONException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}

									if (Parameters.OriginalUrl.equals("")) {
										System.out
												.println("there is no server url val");
									} else {
										boolean isnet = Parameters.isNetworkAvailable(InventoryActivity.this);
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
															System.out
																	.println("delete query"
																			+ jj
																			+ " is :"
																			+ deletequery);

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
															System.out
																	.println("delete query"
																			+ jj
																			+ " is :"
																			+ insertquery);

														dbforloginlogoutWriteInvetory.execSQL(deletequery);
														dbforloginlogoutWriteInvetory.execSQL(insertquery);
															System.out
																	.println("queries executed"
																			+ ii);
														}
													} catch (Exception e) {

													}

													String responseproduct1 = jsonpost
															.postmethodfordirect(
																	"admin",
																	"abcdefg",
																	DatabaseForDemo.OPTIONAL_INFO_TABLE,
																	Parameters
																			.currentTime(),
																	Parameters
																			.currentTime(),
																	datavalforproduct1,
																	"");
													System.out
															.println("response test is:"
																	+ responseproduct1);
													try {
														JSONObject obj = new JSONObject(
																responseproduct1);
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
																	.replace(
																			"'",
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
																	.replace(
																			"'",
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

														dbforloginlogoutWriteInvetory.execSQL(deletequery);
														dbforloginlogoutWriteInvetory.execSQL(insertquery);
															System.out
																	.println("queries executed"
																			+ ii);
														}
													} catch (Exception e) {

													}

													String responseproduct2 = jsonpost
															.postmethodfordirect(
																	"admin",
																	"abcdefg",
																	DatabaseForDemo.ALTERNATE_SKU_TABLE,
																	Parameters
																			.currentTime(),
																	Parameters
																			.currentTime(),
																	datavalforproduct2,
																	"");
													System.out
															.println("response test is:"
																	+ responseproduct2);
													try {
														JSONObject obj = new JSONObject(
																responseproduct2);
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
																	.replace(
																			"'",
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
																	.replace(
																			"'",
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

														dbforloginlogoutWriteInvetory.execSQL(deletequery);
														dbforloginlogoutWriteInvetory.execSQL(insertquery);
															System.out
																	.println("queries executed"
																			+ ii);
														}
													} catch (Exception e) {

													}
													for (int i = 0; i < modifiersArrayserver
															.size(); i++) {
													String responseproduct3 = jsonpost
															.postmethodfordirect(
																	"admin",
																	"abcdefg",
																	DatabaseForDemo.MODIFIER_TABLE,
																	Parameters
																			.currentTime(),
																	Parameters
																			.currentTime(),
																			modifiersArrayserver.get(i),
																	"");
													System.out
															.println("response test is:"
																	+ responseproduct3);
													try {
														JSONObject obj = new JSONObject(
																responseproduct3);
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
																	.replace(
																			"'",
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
																	.replace(
																			"'",
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

														dbforloginlogoutWriteInvetory.execSQL(deletequery);
														dbforloginlogoutWriteInvetory.execSQL(insertquery);
															System.out
																	.println("queries executed"
																			+ ii);
														}
													} catch (Exception e) {

													}
													}
													String responseproduct4 = jsonpost
															.postmethodfordirect(
																	"admin",
																	"abcdefg",
																	DatabaseForDemo.ORDERING_INFO_TABLE,
																	Parameters
																			.currentTime(),
																	Parameters
																			.currentTime(),
																	datavalforproduct4,
																	"");
													System.out
															.println("response test is:"
																	+ responseproduct4);
													String servertiem = null;
													try {
														JSONObject obj = new JSONObject(
																responseproduct4);
														JSONObject responseobj = obj
																.getJSONObject("response");
														servertiem = responseobj
																.getString("server-time");
														System.out
																.println("servertime is:"
																		+ servertiem);
														JSONArray array = obj
																.getJSONArray("insert-queries");
														System.out.println("array_list length for insert is:"
																+ array.length());
														JSONArray array2 = obj
																.getJSONArray("delete-queries");
														System.out.println("array2_list length for delete is:"
																+ array2.length());
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
															System.out
																	.println("delete query"
																			+ jj
																			+ " is :"
																			+ deletequery);

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
															System.out
																	.println("delete query"
																			+ jj
																			+ " is :"
																			+ insertquery);

														dbforloginlogoutWriteInvetory.execSQL(deletequery);
														dbforloginlogoutWriteInvetory.execSQL(insertquery);
															System.out
																	.println("queries executed"
																			+ ii);
														}
													} catch (JSONException e) {
														// TODO Auto-generated
														// catch block
														e.printStackTrace();
													}

													String select = "select *from "
															+ DatabaseForDemo.MISCELLANEOUS_TABLE;
													Cursor cursor = dbforloginlogoutReadInvetory
															.rawQuery(select,
																	null);
													if (cursor.getCount() > 0) {
													dbforloginlogoutWriteInvetory.execSQL("update "
																+ DatabaseForDemo.MISCELLANEOUS_TABLE
																+ " set "
																+ DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL
																+ "=\""
																+ servertiem
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
														dbforloginlogoutWriteInvetory.insert(
																DatabaseForDemo.MISCELLANEOUS_TABLE,
																null,
																contentValues1);
														
													}
													cursor.close();
													dataval = "";
													datavalforproduct1 = "";
													datavalforproduct2 = "";
													datavalforproduct3 = "";
													datavalforproduct4 = "";
												}
											}).start();
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
															DatabaseForDemo.INVENTORY_TABLE);
											contentValues1
													.put(DatabaseForDemo.CURRENT_TIME_PENDING,
															Parameters
																	.currentTime());
											contentValues1.put(
													DatabaseForDemo.PARAMETERS,
													dataval);
											dbforloginlogoutWriteInvetory.insert(
													DatabaseForDemo.PENDING_QUERIES_TABLE,
													null, contentValues1);

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
															DatabaseForDemo.OPTIONAL_INFO_TABLE);
											contentValues2
													.put(DatabaseForDemo.CURRENT_TIME_PENDING,
															Parameters
																	.currentTime());
											contentValues2.put(
													DatabaseForDemo.PARAMETERS,
													datavalforproduct1);
											dbforloginlogoutWriteInvetory.insert(
													DatabaseForDemo.PENDING_QUERIES_TABLE,
													null, contentValues2);

											ContentValues contentValues3 = new ContentValues();
											contentValues3.put(
													DatabaseForDemo.QUERY_TYPE,
													"insert");
											contentValues3
													.put(DatabaseForDemo.PENDING_USER_ID,
															Parameters.userid);
											contentValues3.put(
													DatabaseForDemo.PAGE_URL,
													"saveinfo.php");
											contentValues3
													.put(DatabaseForDemo.TABLE_NAME_PENDING,
															DatabaseForDemo.ALTERNATE_SKU_TABLE);
											contentValues3
													.put(DatabaseForDemo.CURRENT_TIME_PENDING,
															Parameters
																	.currentTime());
											contentValues3.put(
													DatabaseForDemo.PARAMETERS,
													datavalforproduct2);
											dbforloginlogoutWriteInvetory.insert(
													DatabaseForDemo.PENDING_QUERIES_TABLE,
													null, contentValues3);

											ContentValues contentValues4 = new ContentValues();
											contentValues4.put(
													DatabaseForDemo.QUERY_TYPE,
													"insert");
											contentValues4
													.put(DatabaseForDemo.PENDING_USER_ID,
															Parameters.userid);
											contentValues4.put(
													DatabaseForDemo.PAGE_URL,
													"saveinfo.php");
											contentValues4
													.put(DatabaseForDemo.TABLE_NAME_PENDING,
															DatabaseForDemo.MODIFIER_TABLE);
											contentValues4
													.put(DatabaseForDemo.CURRENT_TIME_PENDING,
															Parameters
																	.currentTime());
											contentValues4.put(
													DatabaseForDemo.PARAMETERS,
													datavalforproduct3);
											dbforloginlogoutWriteInvetory.insert(
													DatabaseForDemo.PENDING_QUERIES_TABLE,
													null, contentValues4);

											ContentValues contentValues5 = new ContentValues();
											contentValues5.put(
													DatabaseForDemo.QUERY_TYPE,
													"insert");
											contentValues5
													.put(DatabaseForDemo.PENDING_USER_ID,
															Parameters.userid);
											contentValues5.put(
													DatabaseForDemo.PAGE_URL,
													"saveinfo.php");
											contentValues5
													.put(DatabaseForDemo.TABLE_NAME_PENDING,
															DatabaseForDemo.ORDERING_INFO_TABLE);
											contentValues5
													.put(DatabaseForDemo.CURRENT_TIME_PENDING,
															Parameters
																	.currentTime());
											contentValues5.put(
													DatabaseForDemo.PARAMETERS,
													datavalforproduct4);
											dbforloginlogoutWriteInvetory.insert(
													DatabaseForDemo.PENDING_QUERIES_TABLE,
													null, contentValues5);
											
											
											dataval = "";
											datavalforproduct1 = "";
											datavalforproduct2 = "";
											datavalforproduct3 = "";
											datavalforproduct4 = "";
										}
									}

									departmentspinner.setSelection(0);
									itemno.setText("");
									description1.setText("");
									description2.setText("");
									cost.setText("");
									pricecharge.setText("");
									pricetax.setText("");
									instock.setText("");
									vendorspinner.setSelection(0);
									tax1.setChecked(true);
									tax2.setChecked(false);
									tax3.setChecked(false);
									bartax.setChecked(false);
									modifieritemval = "";
									allowbuybackval = "";
									countthisitemval = "";
									foodstampableval = "";
									promptpriceval = "";
									notesval = "";
									printonreceiptval = "";
									bonuspointsval = "";
									barcodeval = "";
									commissionval = "";
									locationval = "";
									unitsizeval = "";
									unittypeval = "";
									printervalfordept = "";
									foodcheckval = "";
									taxvalforprod = 0;
									taxvalforduplicate = 0;
									taxvalforsaveforduplicate = "";
									taxvalforsavefordept = "";
									taxvalforsaveforprod = "";
									optional_info.clear();
									skuarray.clear();
									modifierindividualitemsarray.clear();
									pricingvendorlistinorderinginfo.clear();

								}
								mCursor.close();
								/*
								 * }else{ Toast.makeText(InventoryActivity.this,
								 * "This is not demo login",
								 * Toast.LENGTH_SHORT).show(); }
								 */
							}
						}
						} catch (NumberFormatException e) {
							  e.printStackTrace();
							} catch (Exception e1) {
								  e1.printStackTrace();
								}
					}
				});

				pricecharge.setOnFocusChangeListener(new OnFocusChangeListener() {

					@Override
					public void onFocusChange(View v, boolean hasFocus) {
						try{
						// TODO Auto-generated method stub
						double taxval = 0;
						String costval = pricecharge.getText().toString();
						double cost = 0;
						if (costval.equals("")) {
							cost = 0;
						} else {
							cost = Double.valueOf(costval);
							if (tax1.isChecked()) {
								String query = "select * from "
										+ DatabaseForDemo.TAX_TABLE + " where "
										+ DatabaseForDemo.TAX_NAME + "=\""
										+ tax1.getText().toString() + "\"";
								System.out.println(query);
								Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(query, null);
								if (cursor != null) {
									if (cursor.moveToFirst()) {
										do {
											double taxvalpercent = Double.valueOf(df
													.format(cursor.getDouble(cursor
															.getColumnIndex(DatabaseForDemo.TAX_VALUE))));
											System.out.println("     "
													+ taxvalpercent);
											taxval = cost
													* (taxvalpercent / 100);
										} while (cursor.moveToNext());
									}
									
									
								} else {
									Toast.makeText(getApplicationContext(),
											"No Tax value", Toast.LENGTH_SHORT)
											.show();
								}
								cursor.close();
							} else {
								taxval = taxval + 0;
							}
							if (tax2.isChecked()) {

								String query = "select "
										+ DatabaseForDemo.TAX_VALUE + " from "
										+ DatabaseForDemo.TAX_TABLE + " where "
										+ DatabaseForDemo.TAX_NAME + "=\""
										+ tax2.getText().toString() + "\"";
								System.out.println(query);
								
								
										
								Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(query, null);
								if (cursor != null) {
									if (cursor.moveToFirst()) {
										do {
											double taxvalpercent = Double.valueOf(df
													.format(cursor.getDouble(cursor
															.getColumnIndex(DatabaseForDemo.TAX_VALUE))));
											System.out.println("     "
													+ taxvalpercent);
											taxval = taxval
													+ (cost * (taxvalpercent / 100));
										} while (cursor.moveToNext());
									}
									
									
								} else {
									Toast.makeText(getApplicationContext(),
											"No Tax value", Toast.LENGTH_SHORT)
											.show();
								}
								cursor.close();
							} else {
								taxval = taxval + 0;
							}
							if (tax3.isChecked()) {

								String query = "select "
										+ DatabaseForDemo.TAX_VALUE + " from "
										+ DatabaseForDemo.TAX_TABLE + " where "
										+ DatabaseForDemo.TAX_NAME + "=\""
										+ tax3.getText().toString() + "\"";
								System.out.println(query);
								
								Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(query, null);
								if (cursor != null) {
									if (cursor.moveToFirst()) {
										do {
											double taxvalpercent = Double.valueOf(df
													.format(cursor.getDouble(cursor
															.getColumnIndex(DatabaseForDemo.TAX_VALUE))));
											System.out.println("     "
													+ taxvalpercent);
											taxval = taxval
													+ (cost * (taxvalpercent / 100));
										} while (cursor.moveToNext());
									}
									
									
								} else {
									Toast.makeText(getApplicationContext(),
											"No Tax value", Toast.LENGTH_SHORT)
											.show();
								}
								cursor.close();
							} else {
								taxval = taxval + 0;
							}
							if (bartax.isChecked()) {
								taxval = taxval + 0;
								System.out.println("     " + taxval);
							} else {
								taxval = taxval + 0;
							}

							pricetax.setText("" + (taxval + cost));
						}
					
					} catch (NumberFormatException e) {
						  e.printStackTrace();
						} catch (Exception e1) {
							  e1.printStackTrace();
							}
					}
				});

				optionalinfo.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String itemnumber = itemno.getText().toString();
						if (itemnumber.equals("")) {
							Toast.makeText(
									getApplicationContext(),
									"You must enter an item number before saving this item.",
									Toast.LENGTH_SHORT).show();
						} else {

							final Dialog dialog = new Dialog(InventoryActivity.this);
							dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
							dialog.setContentView(R.layout.optional_info_layout);
							Button save = (Button) dialog
									.findViewById(R.id.savecat);
							Button cancel = (Button) dialog
									.findViewById(R.id.cancelcat);
							final CheckBox modifieritemcheck = (CheckBox) dialog
									.findViewById(R.id.modifieritem);
							final CheckBox countthisitemcheck = (CheckBox) dialog
									.findViewById(R.id.countthisitem);
							final CheckBox printonreceiptcheck = (CheckBox) dialog
									.findViewById(R.id.printonreceipt);
							final CheckBox allowbuybackcheck = (CheckBox) dialog
									.findViewById(R.id.allowbuyback);
							final CheckBox foodstamplescheck = (CheckBox) dialog
									.findViewById(R.id.foodstamples);
							final CheckBox promptpricecheck = (CheckBox) dialog
									.findViewById(R.id.promptprice);
							skuslist = (ListView) dialog
									.findViewById(R.id.alternateskuslist);
							final Button addsku = (Button) dialog
									.findViewById(R.id.addsku);
							final EditText bonuspointsed = (EditText) dialog
									.findViewById(R.id.bonuspoints);
							final EditText barcodesed = (EditText) dialog
									.findViewById(R.id.barcodes);
							final EditText commissioned = (EditText) dialog
									.findViewById(R.id.commissioned);
							final EditText locationed = (EditText) dialog
									.findViewById(R.id.location);
							final EditText unitsizeed = (EditText) dialog
									.findViewById(R.id.unitsize);
							final EditText unittypeed = (EditText) dialog
									.findViewById(R.id.unittype);
							final Spinner commissionspinner = (Spinner) dialog
									.findViewById(R.id.commission);

							bonuspointsed.setText(bonuspointsval);
							barcodesed.setText(barcodeval);
							commissioned.setText(commissionval);
							locationed.setText(locationval);
							unitsizeed.setText(unitsizeval);
							unittypeed.setText(unittypeval);
							commissionspinner
									.setSelection(commissionspinnerval);
							if (modifieritemval.equals("yes")) {
								modifieritemcheck.setChecked(true);
							} else {
								modifieritemcheck.setChecked(false);
							}
							if (countthisitemval.equals("yes")) {
								countthisitemcheck.setChecked(true);
							} else {
								countthisitemcheck.setChecked(false);
							}
							if (printonreceiptval.equals("yes")) {
								printonreceiptcheck.setChecked(true);
							} else {
								printonreceiptcheck.setChecked(false);
							}
							if (allowbuybackval.equals("yes")) {
								allowbuybackcheck.setChecked(true);
							} else {
								allowbuybackcheck.setChecked(false);
							}
							if (foodstampableval.equals("yes")) {
								foodstamplescheck.setChecked(true);
							} else {
								foodstamplescheck.setChecked(false);
							}
							if (promptpriceval.equals("yes")) {
								promptpricecheck.setChecked(true);
							} else {
								promptpricecheck.setChecked(false);
							}
							if (skuarray != null) {
								SkuArrayAdapter skuadapter = new SkuArrayAdapter(
										getApplicationContext(), skuarray);
								skuadapter.setListener(InventoryActivity.this);
								skuslist.setAdapter(skuadapter);
							}

							addsku.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									System.out.println("add is clicked");
									final AlertDialog alertDialog1 = new AlertDialog.Builder(
											InventoryActivity.this).create();
									LayoutInflater mInflater = LayoutInflater
											.from(InventoryActivity.this);
									View layout = mInflater.inflate(
											R.layout.add_sku, null);
									Button ok = (Button) layout
											.findViewById(R.id.save);
									Button cancel = (Button) layout
											.findViewById(R.id.cancel);
									final EditText sku = (EditText) layout
											.findViewById(R.id.sku);

									alertDialog1.setTitle("Add SKU");

									ok.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View arg0) {
											// TODO Auto-generated method stub
											skuval = sku.getText().toString();
											if (skuval.equals("")) {
												Toast.makeText(
														getApplicationContext(),
														"Please enter sku value",
														Toast.LENGTH_SHORT)
														.show();
											} else {
												if (skuarray.contains(skuval)) {
													Toast.makeText(
															getApplicationContext(),
															"SKU already exist",
															Toast.LENGTH_SHORT)
															.show();
												} else {
													String selectQuery = "SELECT  * FROM "
															+ DatabaseForDemo.ALTERNATE_SKU_TABLE
															+ " where "
															+ DatabaseForDemo.ALTERNATE_SKU_VALUE
															+ "=\""
															+ skuval
															+ "\"";
													Cursor mCursor = 
															dbforloginlogoutReadInvetory.rawQuery(
																	selectQuery,
																	null);
													if (mCursor.getCount() > 0) {
														Toast.makeText(
																getApplicationContext(),
																"SKU already exist for an item",
																Toast.LENGTH_SHORT)
																.show();
														
													} else {
														
														skuarray.add(skuval);
														if (skuarray != null) {
															SkuArrayAdapter skuadapter = new SkuArrayAdapter(
																	getApplicationContext(),
																	skuarray);
															skuadapter
																	.setListener(InventoryActivity.this);
															skuslist.setAdapter(skuadapter);
														}
													}
													mCursor.close();
													alertDialog1.dismiss();
												}
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

								}
							});

							save.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									bonuspointsval = bonuspointsed.getText()
											.toString();
									barcodeval = barcodesed.getText()
											.toString();
									commissionval = commissioned.getText()
											.toString();
									locationval = locationed.getText()
											.toString();
									unitsizeval = unitsizeed.getText()
											.toString();
									unittypeval = unittypeed.getText()
											.toString();
									commissionspinnerval = commissionspinner
											.getSelectedItemPosition();
									if (modifieritemcheck.isChecked()) {
										modifieritemval = "yes";
									} else {
										modifieritemval = "no";
									}
									if (countthisitemcheck.isChecked()) {
										countthisitemval = "yes";
									} else {
										countthisitemval = "no";
									}
									if (printonreceiptcheck.isChecked()) {
										printonreceiptval = "yes";
									} else {
										printonreceiptval = "no";
									}
									if (allowbuybackcheck.isChecked()) {
										allowbuybackval = "yes";
									} else {
										allowbuybackval = "no";
									}
									if (foodstamplescheck.isChecked()) {
										foodstampableval = "yes";
									} else {
										foodstampableval = "no";
									}
									if (promptpricecheck.isChecked()) {
										promptpriceval = "yes";
									} else {
										promptpriceval = "no";
									}

									dialog.dismiss();
								}
							});

							cancel.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							});
							dialog.show();
						}
					}
				});

				notes.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String itemnumber = itemno.getText().toString();
						if (itemnumber.equals("")) {
							Toast.makeText(
									getApplicationContext(),
									"You must enter an item number before saving this item.",
									Toast.LENGTH_SHORT).show();
						} else {
							final Dialog dialog = new Dialog(InventoryActivity.this);

							dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
							dialog.setContentView(R.layout.notes_layout);
							Button save = (Button) dialog
									.findViewById(R.id.savecat);
							Button cancel = (Button) dialog
									.findViewById(R.id.cancelcat);
							final EditText notesedit = (EditText) dialog
									.findViewById(R.id.notestext);

							notesedit.setText(notesval);

							save.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									notesval = notesedit.getText().toString();
									dialog.dismiss();
								}
							});

							cancel.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							});
							dialog.show();
						}
					}
				});

				modifiers.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						final String itemnumber = itemno.getText().toString();
						if (itemnumber.equals("")) {
							Toast.makeText(
									getApplicationContext(),
									"You must enter an item number before saving this item.",
									Toast.LENGTH_SHORT).show();
						} else {
							final Dialog dialog = new Dialog(InventoryActivity.this);
							dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
							dialog.setContentView(R.layout.modifiers_layout);
							Button save = (Button) dialog
									.findViewById(R.id.savecat);
							Button cancel = (Button) dialog
									.findViewById(R.id.cancelcat);
							final Spinner modifieritems = (Spinner) dialog
									.findViewById(R.id.modifieritemspinner);
							modifierlist = (ListView) dialog
									.findViewById(R.id.modifieritemslist);
							ArrayList<String> modifieritemspinnerarray = new ArrayList<String>();
							modifieritemspinnerarray.clear();
							modifieritemspinnerarray.add("Select");
							
							String selectQuery = "SELECT  "
									+ DatabaseForDemo.INVENTORY_ITEM_NO
									+ " FROM "
									+ DatabaseForDemo.OPTIONAL_INFO_TABLE
									+ " where "
									+ DatabaseForDemo.INVENTORY_MODIFIER_ITEM
									+ "=\"yes\"";
							Cursor mCursor =dbforloginlogoutReadInvetory.rawQuery(selectQuery, null);
							if (mCursor != null) {
								if (mCursor.moveToFirst()) {
									do {
										String itemid = mCursor.getString(mCursor
												.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NO));
										modifieritemspinnerarray.add(itemid);
									} while (mCursor.moveToNext());
								}
								
								
								ArrayAdapter<String> adapter = new ArrayAdapter<String>(
										InventoryActivity.this,
										android.R.layout.simple_spinner_item,
										modifieritemspinnerarray);
								adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
								modifieritems.setAdapter(adapter);
							}
							mCursor.close();
							if (modifierindividualitemsarray != null) {
								ModifierIndividualItemAdapter adapterformod = new ModifierIndividualItemAdapter(
										InventoryActivity.this,
										modifierindividualitemsarray);
								adapterformod
										.setListener(InventoryActivity.this);
								modifierlist.setAdapter(adapterformod);
							}

							modifieritems
									.setOnItemSelectedListener(new OnItemSelectedListener() {

										@Override
										public void onItemSelected(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
											// TODO Auto-generated method stub
											String itemnoinspinner = modifieritems
													.getSelectedItem()
													.toString();
											if (itemnoinspinner.equals("")
													|| itemnoinspinner
															.equals("Select")) {

											} else {
												System.out
														.println("else is executed");
												for (int i = 0; i < modifierindividualitemsarray
														.size(); i++) {
													if (modifierindividualitemsarray
															.get(i)
															.containsValue(
																	itemnoinspinner)) {
														Toast.makeText(
																InventoryActivity.this,
																"Modifier already exist",
																Toast.LENGTH_SHORT)
																.show();
														System.out
																.println("same is found is executed");
														status = false;
														break;
													}
												}
												if (status == true) {
													String selectQuery = "SELECT  "
															+ DatabaseForDemo.INVENTORY_ITEM_NO
															+ ", "
															+ DatabaseForDemo.INVENTORY_ITEM_NAME
															+ " FROM "
															+ DatabaseForDemo.INVENTORY_TABLE
															+ " where "
															+ DatabaseForDemo.INVENTORY_ITEM_NO
															+ "=\""
															+ itemnoinspinner
															+ "\"";
													Cursor mCursor = dbforloginlogoutReadInvetory
															.rawQuery(
																	selectQuery,
																	null);
													if (mCursor != null) {
														if (mCursor
																.moveToFirst()) {
															do {
																HashMap<String, String> map = new HashMap<String, String>();
																String itemid, itemname;
																itemid = mCursor
																		.getString(mCursor
																				.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NO));
																itemname = mCursor
																		.getString(mCursor
																				.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NAME));
																map.put("itemno",
																		itemid);
																map.put("itemname",
																		itemname);
																modifierindividualitemsarray
																		.add(map);
															} while (mCursor
																	.moveToNext());
														}
														
														
													}
													mCursor.close();
													if (modifierindividualitemsarray != null) {
														ModifierIndividualItemAdapter adapterformod = new ModifierIndividualItemAdapter(
																InventoryActivity.this,
																modifierindividualitemsarray);
														adapterformod
																.setListener(InventoryActivity.this);
														modifierlist
																.setAdapter(adapterformod);
													}
												}
											}
											status = true;
										}

										@Override
										public void onNothingSelected(
												AdapterView<?> arg0) {
											// TODO Auto-generated method stub

										}
									});

							save.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							});

							cancel.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							});
							dialog.show();
						}
					}
				});

				orderinginfo.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String itemnumber = itemno.getText().toString();
						if (itemnumber.equals("")) {
							Toast.makeText(
									getApplicationContext(),
									"You must enter an item number before saving this item.",
									Toast.LENGTH_SHORT).show();
						} else {
							final Dialog dialog = new Dialog(InventoryActivity.this);

							dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
							dialog.setContentView(R.layout.ordering_info_layout);
							Button save = (Button) dialog
									.findViewById(R.id.savecat);
							Button cancel = (Button) dialog
									.findViewById(R.id.cancelcat);
							final Spinner vendortotaldetails = (Spinner) dialog
									.findViewById(R.id.vendorinorderinginfo);
							vendorlist = (ListView) dialog
									.findViewById(R.id.orderinginfolist);
							ArrayList<String> vendorspinnerdatafororderinginfo = new ArrayList<String>();
							vendorspinnerdatafororderinginfo.clear();
							vendorspinnerdatafororderinginfo.add("Select");

							Cursor mCursor =dbforloginlogoutReadInvetory.rawQuery(
											"select "
													+ DatabaseForDemo.VENDOR_COMPANY_NAME
													+ " from "
													+ DatabaseForDemo.VENDOR_TABLE,
											null);
							System.out.println(mCursor);
							if (mCursor != null) {
								if (mCursor.moveToFirst()) {
									do {
										String catid = mCursor.getString(mCursor
												.getColumnIndex(DatabaseForDemo.VENDOR_COMPANY_NAME));
										vendorspinnerdatafororderinginfo
												.add(catid);
									} while (mCursor.moveToNext());
								}
								
								
							}
							mCursor.close();
							ArrayAdapter<String> adapter = new ArrayAdapter<String>(
									InventoryActivity.this,
									android.R.layout.simple_spinner_item,
									vendorspinnerdatafororderinginfo);
							adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
							vendortotaldetails.setAdapter(adapter);

							if (pricingvendorlistinorderinginfo != null) {
								OrderingInfoIndividualVendorAdapter adapterformod = new OrderingInfoIndividualVendorAdapter(
										InventoryActivity.this,
										pricingvendorlistinorderinginfo);
								adapterformod
										.setListener(InventoryActivity.this);
								vendorlist.setAdapter(adapterformod);
							}

							vendortotaldetails
									.setOnItemSelectedListener(new OnItemSelectedListener() {

										@Override
										public void onItemSelected(
												AdapterView<?> arg0, View arg1,
												int arg2, long arg3) {
											// TODO Auto-generated method stub
											final String vendorinspinner = vendortotaldetails
													.getSelectedItem()
													.toString();
											if (vendorinspinner.equals("")
													|| vendorinspinner
															.equals("Select")) {

											} else {
												System.out
														.println("else is executed");
												for (int i = 0; i < pricingvendorlistinorderinginfo
														.size(); i++) {
													if (pricingvendorlistinorderinginfo
															.get(i)
															.containsValue(
																	vendorinspinner)) {
														Toast.makeText(
																InventoryActivity.this,
																"Vendor already exist",
																Toast.LENGTH_SHORT)
																.show();
														System.out
																.println("same is found is executed");
														statusforvendor = false;
														break;
													}
												}
												if (statusforvendor == true) {
													System.out
															.println("different is found");

													final AlertDialog alertDialog1 = new AlertDialog.Builder(
															InventoryActivity.this)
															.create();
													LayoutInflater mInflater = LayoutInflater
															.from(InventoryActivity.this);
													View layout = mInflater
															.inflate(
																	R.layout.orderinginfopopups,
																	null);
													Button ok = (Button) layout
															.findViewById(R.id.save);
													Button cancel = (Button) layout
															.findViewById(R.id.cancel);
													final EditText partno = (EditText) layout
															.findViewById(R.id.partno);
													final EditText costperase = (EditText) layout
															.findViewById(R.id.cost_case);
													final EditText casecost = (EditText) layout
															.findViewById(R.id.case_cost);
													final EditText noincase = (EditText) layout
															.findViewById(R.id.num_in_case);
													noincase.setOnEditorActionListener(new OnEditorActionListener() {
														@Override
														public boolean onEditorAction(TextView v, int actionId,
																KeyEvent event) {
															if (actionId == EditorInfo.IME_ACTION_DONE) {
																InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
																imm.hideSoftInputFromWindow(noincase.getWindowToken(), 0);
															}
															return false;
														}
													});
													final RadioButton trueval = (RadioButton) layout
															.findViewById(R.id.radiobuttonyes);
													final RadioButton falseval = (RadioButton) layout
															.findViewById(R.id.radiobuttonno);

													alertDialog1
															.setTitle("Add Vendor details");

													ok.setOnClickListener(new OnClickListener() {

														@Override
														public void onClick(
																View arg0) {
															// TODO
															// Auto-generated
															// method stub
															String prferredtextval = "";
															if (trueval
																	.isChecked()) {
																System.out
																		.println("preferred val is true");
																prferredtextval = "true";
															}
															if (falseval
																	.isChecked()) {
																System.out
																		.println("preferred val is false");
																prferredtextval = "false";
															}
															if (prferredtextval
																	.equals("true")) {
																int positionval = 0;
																for (int i = 0; i < pricingvendorlistinorderinginfo
																		.size(); i++) {
																	if (pricingvendorlistinorderinginfo
																			.get(i)
																			.containsValue(
																					"true")) {
																		Toast.makeText(
																				InventoryActivity.this,
																				"true already exist",
																				Toast.LENGTH_SHORT)
																				.show();
																		System.out
																				.println("same is found is executed");
																		statusfortrue = false;
																		positionval = i;
																		System.out
																				.println("position val is:"
																						+ positionval);
																		break;
																	}
																}
																if (statusfortrue == true) {
																	System.out
																			.println("true is not there");
																	HashMap<String, String> map = new HashMap<String, String>();
																	map.put("vendorname",
																			vendorinspinner);
																	map.put("partno",
																			partno.getText()
																					.toString());
																	map.put("costpercase",
																			costperase
																					.getText()
																					.toString());
																	map.put("casecost",
																			casecost.getText()
																					.toString());
																	map.put("noincase",
																			noincase.getText()
																					.toString());
																	map.put("preferred",
																			prferredtextval);
																	pricingvendorlistinorderinginfo
																			.add(map);
																} else {
																	System.out
																			.println("true is there");
																	pricingvendorlistinorderinginfo
																			.get(positionval)
																			.put("preferred",
																					"false");
																	System.out
																			.println("prferred value is changed");
																	HashMap<String, String> map = new HashMap<String, String>();
																	map.put("vendorname",
																			vendorinspinner);
																	map.put("partno",
																			partno.getText()
																					.toString());
																	map.put("costpercase",
																			costperase
																					.getText()
																					.toString());
																	map.put("casecost",
																			casecost.getText()
																					.toString());
																	map.put("noincase",
																			noincase.getText()
																					.toString());
																	map.put("preferred",
																			prferredtextval);
																	pricingvendorlistinorderinginfo
																			.add(map);
																}
															} else {
																System.out
																		.println("preferred val is false");
																HashMap<String, String> map = new HashMap<String, String>();
																map.put("vendorname",
																		vendorinspinner);
																map.put("partno",
																		partno.getText()
																				.toString());
																map.put("costpercase",
																		costperase
																				.getText()
																				.toString());
																map.put("casecost",
																		casecost.getText()
																				.toString());
																map.put("noincase",
																		noincase.getText()
																				.toString());
																map.put("preferred",
																		prferredtextval);
																pricingvendorlistinorderinginfo
																		.add(map);
															}

															if (pricingvendorlistinorderinginfo != null) {
																OrderingInfoIndividualVendorAdapter adapterformod = new OrderingInfoIndividualVendorAdapter(
																		InventoryActivity.this,
																		pricingvendorlistinorderinginfo);
																adapterformod
																		.setListener(InventoryActivity.this);
																vendorlist
																		.setAdapter(adapterformod);
															}
															statusfortrue = true;
															alertDialog1
																	.dismiss();
														}
													});
													cancel.setOnClickListener(new OnClickListener() {

														@Override
														public void onClick(
																View arg0) {
															// TODO
															// Auto-generated
															// method stub
															alertDialog1
																	.dismiss();
														}
													});
													alertDialog1
															.setView(layout);
													alertDialog1.show();
												}
											}
											statusforvendor = true;
										}

										@Override
										public void onNothingSelected(
												AdapterView<?> arg0) {
											// TODO Auto-generated method stub

										}
									});

							save.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							});

							cancel.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							});
							dialog.show();
						}
					}
				});

				saleshistory.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String itemnumber = itemno.getText().toString();
						if (itemnumber.equals("")) {
							Toast.makeText(
									getApplicationContext(),
									"You must enter an item number before saving this item.",
									Toast.LENGTH_SHORT).show();
						} else {
							final Dialog dialog = new Dialog(InventoryActivity.this);

							dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
							dialog.setContentView(R.layout.sales_history_layout);
							Button save = (Button) dialog
									.findViewById(R.id.savecat);
							Button cancel = (Button) dialog
									.findViewById(R.id.cancelcat);
							saleslist = (ListView) dialog
									.findViewById(R.id.pricelevelslist);

							ArrayList<String> dateandtimelist = new ArrayList<String>();
							ArrayList<String> storeidlist = new ArrayList<String>();
							ArrayList<String> invoicelist = new ArrayList<String>();
							ArrayList<String> quantitylist = new ArrayList<String>();
							ArrayList<String> pricelist = new ArrayList<String>();
							ArrayList<String> costlist = new ArrayList<String>();
							ArrayList<String> custlist = new ArrayList<String>();
							ArrayList<String> seriallist = new ArrayList<String>();

							String query = "SELECT *from "
									+ DatabaseForDemo.INVOICE_ITEMS_TABLE
									+ " where "
									+ DatabaseForDemo.INVOICE_ITEM_ID + "=\""
									+ itemnumber + "\"";
							Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(query, null);

							if (cursor.getCount() > 0) {
								if (cursor != null) {
									if (cursor.moveToFirst()) {
										do {
											int i = 1;
											if (cursor.isNull(cursor
													.getColumnIndex(DatabaseForDemo.CREATED_DATE))) {
												dateandtimelist.add("");
											} else {
												String dateandtime = cursor.getString(cursor
														.getColumnIndex(DatabaseForDemo.CREATED_DATE));
												dateandtimelist
														.add(dateandtime);
											}
											if (cursor.isNull(cursor
													.getColumnIndex(DatabaseForDemo.INVOICE_STORE_ID))) {
												storeidlist.add("");
											} else {
												String storeid = cursor.getString(cursor
														.getColumnIndex(DatabaseForDemo.INVOICE_STORE_ID));
												storeidlist.add(storeid);
											}
											if (cursor.isNull(cursor
													.getColumnIndex(DatabaseForDemo.INVOICE_ID))) {
												invoicelist.add("");
											} else {
												String invoiceid = cursor.getString(cursor
														.getColumnIndex(DatabaseForDemo.INVOICE_ID));
												invoicelist.add(invoiceid);
											}
											if (cursor.isNull(cursor
													.getColumnIndex(DatabaseForDemo.INVOICE_QUANTITY))) {
												quantitylist.add("");
											} else {
												String qtylist = cursor.getString(cursor
														.getColumnIndex(DatabaseForDemo.INVOICE_QUANTITY));
												quantitylist.add(qtylist);
											}
											if (cursor.isNull(cursor
													.getColumnIndex(DatabaseForDemo.INVOICE_YOUR_COST))) {
												pricelist.add("");
											} else {
												String price = cursor.getString(cursor
														.getColumnIndex(DatabaseForDemo.INVOICE_YOUR_COST));
												pricelist.add(price);
											}
											if (cursor.isNull(cursor
													.getColumnIndex(DatabaseForDemo.INVOICE_AVG_COST))) {
												costlist.add("");
											} else {
												String cost = cursor.getString(cursor
														.getColumnIndex(DatabaseForDemo.INVOICE_AVG_COST));
												costlist.add(cost);
											}
											seriallist.add("" + i);
											i++;
										} while (cursor.moveToNext());
									}
								}
							}
							cursor.close();
							for (int ii = 0; ii < invoicelist.size(); ii++) {
								String selectQueryforshipping = "SELECT  * FROM "
										+ DatabaseForDemo.INVOICE_TOTAL_TABLE
										+ " where "
										+ DatabaseForDemo.INVOICE_ID
										+ "=\""
										+ invoicelist.get(ii) + "\"";

								Cursor mCursorforshipping =dbforloginlogoutReadInvetory.rawQuery(
										selectQueryforshipping, null);

								if (mCursorforshipping.getCount() > 0) {
									if (mCursorforshipping != null) {
										if (mCursorforshipping.moveToFirst()) {
											do {
												if (mCursorforshipping
														.isNull(mCursorforshipping
																.getColumnIndex(DatabaseForDemo.INVOICE_CUSTOMER))) {
													custlist.add("");
												} else {
													String address = mCursorforshipping
															.getString(mCursorforshipping
																	.getColumnIndex(DatabaseForDemo.INVOICE_CUSTOMER));
													custlist.add(address);
												}
											} while (mCursorforshipping
													.moveToNext());
										}
									}
								}
								mCursorforshipping.close();
							}

							ImageAdapterForSalesHistory adapter = new ImageAdapterForSalesHistory(
									InventoryActivity.this, dateandtimelist,
									storeidlist, invoicelist, quantitylist,
									pricelist, costlist, custlist, seriallist);
							saleslist.setAdapter(adapter);

							save.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							});

							cancel.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							});
							dialog.show();
						}
					}
				});

				printers.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						String itemnumber = itemno.getText().toString();
						if (itemnumber.equals("")) {
							Toast.makeText(
									getApplicationContext(),
									"You must enter an item number before saving this item.",
									Toast.LENGTH_SHORT).show();
						} else {
							final Dialog dialog = new Dialog(InventoryActivity.this);

							dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
							dialog.setContentView(R.layout.printers_layout);
							Button save = (Button) dialog
									.findViewById(R.id.savecat);
							Button cancel = (Button) dialog
									.findViewById(R.id.cancelcat);

							save.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							});

							cancel.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View v) {
									// TODO Auto-generated method stub
									dialog.dismiss();
								}
							});
							dialog.show();
						}
					}
				});

				detailslayout.addView(layout);
			}
		});

		category.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				modifieritemval = "";
				countthisitemval = "";
				printonreceiptval = "";
				allowbuybackval = "";
				foodstampableval = "";
				notesval = "";
				promptpriceval = "";
				skuval = "";
				bonuspointsval = "";
				barcodeval = "";
				commissionval = "";
				locationval = "";
				unitsizeval = "";
				unittypeval = "";
				printervalfordept = "";
				foodcheckval = "";
				taxvalforsavefordept = "";
				taxvalforsaveforprod = "";
				taxvalforduplicate = 0;
				taxvalforsaveforduplicate = "";
				commissionspinnerval = 0;
				taxvalforprod = 0;
				optional_info.clear();
				skuarray.clear();
				modifierindividualitemsarray.clear();
				pricingvendorlistinorderinginfo.clear();
				detailslayout.removeAllViews();
				product.setBackgroundResource(R.drawable.toprightmenu);
				department.setBackgroundResource(R.drawable.toprightmenu);
				vendor.setBackgroundResource(R.drawable.toprightmenu);
				category.setBackgroundResource(R.drawable.highlightedtopmenuitem);
				product.setTextColor(Color.WHITE);
				department.setTextColor(Color.WHITE);
				vendor.setTextColor(Color.WHITE);
				category.setTextColor(Color.BLACK);
				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.category,
						(ViewGroup) v.findViewById(R.id.inflatingLayout));

				addcat = (Button) layout.findViewById(R.id.addcat);
				viewcat = (Button) layout.findViewById(R.id.viewcat);
				ll_add = (LinearLayout) layout.findViewById(R.id.add_ll);
				ll_view = (LinearLayout) layout.findViewById(R.id.view_ll);

				addcat.setBackgroundColor(Color.parseColor("#3c6586"));
				viewcat.setBackgroundColor(Color.parseColor("#cbcbcb"));
				ll_add.setVisibility(View.VISIBLE);
				ll_view.setVisibility(View.GONE);
				addcat.setTextColor(Color.WHITE);
				viewcat.setTextColor(Color.BLACK);

				list_View = (ListView) layout.findViewById(R.id.listView1);
				ll = (LinearLayout) layout.findViewById(R.id.linearLayout1);
				Button heading = (Button) layout.findViewById(R.id.button2);
				heading.setText("Category Details");
				list_View.setItemsCanFocus(false);

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
						total_desc_data.clear();
						total_id_data.clear();
						id_data.clear();
						desc_data.clear();
						listUpdateforcategory();
					}
				});

				categoryid = (EditText) layout.findViewById(R.id.cat_id);
				categorydesc = (EditText) layout.findViewById(R.id.cat_desc);
				savecat = (Button) layout.findViewById(R.id.savecat);
				cancelcat = (Button) layout.findViewById(R.id.cancelcat);

				cancelcat.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						categoryid.setText("");
						categorydesc.setText("");
					}
				});

				savecat.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						final String id = categoryid.getText().toString();
						final String desc = categorydesc.getText().toString();
						final String uniqueidval = Parameters.randomValue();

						if (id.equals("")) {
							Toast.makeText(InventoryActivity.this,
									"Please enter category id",
									Toast.LENGTH_SHORT).show();
						} else {
							// if(Parameters.usertype.equals("demo")){

							
							String selectQuery = "SELECT  * FROM "
									+ DatabaseForDemo.CATEGORY_TABLE
									+ " where " + DatabaseForDemo.CategoryId
									+ "=\"" + id + "\"";

							Cursor mCursor =dbforloginlogoutReadInvetory.rawQuery(selectQuery, null);
							if (mCursor.getCount() > 0) {
								Toast.makeText(InventoryActivity.this,
										"Category id already exist",
										Toast.LENGTH_SHORT).show();
								
							} else {
								
								ContentValues contentValues = new ContentValues();
								contentValues.put(DatabaseForDemo.UNIQUE_ID,
										uniqueidval);
								contentValues.put(DatabaseForDemo.CREATED_DATE,
										Parameters.currentTime());
								contentValues.put(
										DatabaseForDemo.MODIFIED_DATE,
										Parameters.currentTime());
								contentValues.put(DatabaseForDemo.MODIFIED_IN,
										"Local");
								contentValues.put(DatabaseForDemo.CategoryId,
										id);
								contentValues.put(DatabaseForDemo.CategoryDesp,
										desc);
								dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.CATEGORY_TABLE, null,
										contentValues);
								categoryid.setText("");
								categorydesc.setText("");
								
								

								try {
									JSONObject data = new JSONObject();
									JSONObject jsonobj = new JSONObject();
									jsonobj.put(DatabaseForDemo.CategoryId, id);
									jsonobj.put(DatabaseForDemo.CategoryDesp,
											desc);
									jsonobj.put(DatabaseForDemo.UNIQUE_ID,
											uniqueidval);
									JSONArray fields = new JSONArray();
									fields.put(0, jsonobj);
									data.put("fields", fields);
									dataval = data.toString();
									System.out
											.println("data val is:" + fields);
								} catch (JSONException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

								Toast.makeText(InventoryActivity.this,
										"Category inserted successfully",
										Toast.LENGTH_SHORT).show();

								if (Parameters.OriginalUrl.equals("")) {
									System.out
											.println("there is no server url val");
								} else {
									boolean isnet = Parameters.isNetworkAvailable(InventoryActivity.this);
									if (isnet) {
										new Thread(new Runnable() {
											@Override
											public void run() {
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
													System.out.println("array_list length for insert is:"
															+ array.length());
													JSONArray array2 = obj
															.getJSONArray("delete-queries");
													System.out.println("array2_list length for delete is:"
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

													dbforloginlogoutWriteInvetory.execSQL(deletequery);
													dbforloginlogoutWriteInvetory.execSQL(insertquery);
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
												Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(
														select, null);
												if (cursor.getCount() > 0) {
												dbforloginlogoutWriteInvetory.execSQL("update "
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
													dbforloginlogoutWriteInvetory.insert(
															DatabaseForDemo.MISCELLANEOUS_TABLE,
															null,
															contentValues1);
													
												}
												dataval = "";
											}
										}).start();
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
										dbforloginlogoutWriteInvetory.insert(
												DatabaseForDemo.PENDING_QUERIES_TABLE,
												null, contentValues1);
										
										
										dataval = "";
									}
								}
							}
							mCursor.close();
							/*
							 * }else{ Toast.makeText(InventoryActivity.this,
							 * "This is not demo login",
							 * Toast.LENGTH_SHORT).show(); }
							 */
						}
					}
				});

				detailslayout.addView(layout);
			}
		});

		department.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				modifieritemval = "";
				countthisitemval = "";
				printonreceiptval = "";
				allowbuybackval = "";
				foodstampableval = "";
				notesval = "";
				promptpriceval = "";
				skuval = "";
				bonuspointsval = "";
				barcodeval = "";
				commissionval = "";
				locationval = "";
				unitsizeval = "";
				unittypeval = "";
				printervalfordept = "";
				taxvalforduplicate = 0;
				taxvalforsaveforduplicate = "";
				foodcheckval = "";
				taxvalforsavefordept = "";
				taxvalforsaveforprod = "";
				commissionspinnerval = 0;
				taxvalforprod = 0;
				optional_info.clear();
				skuarray.clear();
				modifierindividualitemsarray.clear();
				pricingvendorlistinorderinginfo.clear();
				detailslayout.removeAllViews();
				product.setBackgroundResource(R.drawable.toprightmenu);
				department
						.setBackgroundResource(R.drawable.highlightedtopmenuitem);
				vendor.setBackgroundResource(R.drawable.toprightmenu);
				category.setBackgroundResource(R.drawable.toprightmenu);
				product.setTextColor(Color.WHITE);
				department.setTextColor(Color.BLACK);
				vendor.setTextColor(Color.WHITE);
				category.setTextColor(Color.WHITE);
				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.department,
						(ViewGroup) v.findViewById(R.id.inflatingLayout));

				deptid = (EditText) layout.findViewById(R.id.dept_id);
				deptdesc = (EditText) layout.findViewById(R.id.dept_desc);
				categoryidspinner = (Spinner) layout.findViewById(R.id.cat_id);
				printerspinner = (Spinner) layout
						.findViewById(R.id.printerspinner);
				printerspinnerminutes = (Spinner) layout
						.findViewById(R.id.printerspinnermints);
				printerspinnerseconds = (Spinner) layout
						.findViewById(R.id.printerspinnerseconds);
				foodcheck = (CheckBox) layout.findViewById(R.id.foodcheck);
				tax1dept = (CheckBox) layout.findViewById(R.id.checkBox1);
				tax2dept = (CheckBox) layout.findViewById(R.id.checkBox2);
				tax3dept = (CheckBox) layout.findViewById(R.id.checkBox3);
				tax1dept.setText(taxlist.get(0));
				tax2dept.setText(taxlist.get(1));
				tax3dept.setText(taxlist.get(2));
				bartaxdept = (CheckBox) layout.findViewById(R.id.checkBox4);
				final ArrayList<String> spinnerData = new ArrayList<String>();
				spinnerData.clear();
				list_View = (ListView) layout.findViewById(R.id.listView1);
				ll = (LinearLayout) layout.findViewById(R.id.linearLayout1);
				Button heading = (Button) layout.findViewById(R.id.button2);
				heading.setText("Department Details");
				list_View.setItemsCanFocus(false);

				addcat = (Button) layout.findViewById(R.id.addcat);
				viewcat = (Button) layout.findViewById(R.id.viewcat);
				ll_add = (LinearLayout) layout.findViewById(R.id.add_ll);
				ll_view = (LinearLayout) layout.findViewById(R.id.view_ll);

				addcat.setBackgroundColor(Color.parseColor("#3c6586"));
				viewcat.setBackgroundColor(Color.parseColor("#cbcbcb"));
				ll_add.setVisibility(View.VISIBLE);
				ll_view.setVisibility(View.GONE);
				addcat.setTextColor(Color.WHITE);
				viewcat.setTextColor(Color.BLACK);

				savecat = (Button) layout.findViewById(R.id.savecat);
				cancelcat = (Button) layout.findViewById(R.id.cancelcat);

				// if(Parameters.usertype.equals("demo")){

				Cursor mCursor = dbforloginlogoutReadInvetory.rawQuery(
						"select " + DatabaseForDemo.CategoryId + " from "
								+ DatabaseForDemo.CATEGORY_TABLE, null);
				System.out.println(mCursor);
				if (mCursor != null) {
					if (mCursor.moveToFirst()) {
						do {
							String catid = mCursor.getString(mCursor
									.getColumnIndex(DatabaseForDemo.CategoryId));
							spinnerData.add(catid);
						} while (mCursor.moveToNext());
					}
					
					
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							InventoryActivity.this,
							android.R.layout.simple_spinner_item, spinnerData);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
					categoryidspinner.setAdapter(adapter);
				} else {
					Toast.makeText(getApplicationContext(),
							"This is no profile data in demo login",
							Toast.LENGTH_SHORT).show();
				}
				mCursor.close();
				/*
				 * }else{ Toast.makeText(getApplicationContext(),
				 * "This is not demo login", Toast.LENGTH_SHORT).show(); }
				 */

				ArrayList<String> printerlist = new ArrayList<String>();
				printerlist.clear();
				printerlist.add("None");
				
				String selectQuery = "SELECT *from "
						+ DatabaseForDemo.PRINTER_TABLE;

				Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(selectQuery, null);
				int count = cursor.getCount();
				if (count > 0) {
					if (cursor != null) {
						if (cursor.moveToFirst()) {
							do {
								if (cursor.isNull(cursor
										.getColumnIndex(DatabaseForDemo.PRINTER_ID))) {

								} else {
									String vendornum = cursor.getString(cursor
											.getColumnIndex(DatabaseForDemo.PRINTER_ID));
									printerlist.add(vendornum);
								}
							} while (cursor.moveToNext());
						}
					}
				}
				cursor.close();
				
				

				if (printerlist != null) {
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							InventoryActivity.this,
							android.R.layout.simple_spinner_item, printerlist);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
					printerspinner.setAdapter(adapter);
				}

				addcat.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						spinnerData.clear();
						addcat.setBackgroundColor(Color.parseColor("#3c6586"));
						viewcat.setBackgroundColor(Color.parseColor("#cbcbcb"));
						ll_add.setVisibility(View.VISIBLE);
						ll_view.setVisibility(View.GONE);
						addcat.setTextColor(Color.WHITE);
						viewcat.setTextColor(Color.BLACK);
						Cursor mCursor = dbforloginlogoutReadInvetory.rawQuery(
								"select " + DatabaseForDemo.CategoryId
										+ " from "
										+ DatabaseForDemo.CATEGORY_TABLE, null);
						System.out.println(mCursor);
						if (mCursor != null) {
							if (mCursor.moveToFirst()) {
								do {
									String catid = mCursor.getString(mCursor
											.getColumnIndex(DatabaseForDemo.CategoryId));
									spinnerData.add(catid);
								} while (mCursor.moveToNext());
							}
							
							
							ArrayAdapter<String> adapter = new ArrayAdapter<String>(
									InventoryActivity.this,
									android.R.layout.simple_spinner_item,
									spinnerData);
							adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
							categoryidspinner.setAdapter(adapter);
						}
						mCursor.close();
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
						total_desc_data.clear();
						total_id_data.clear();
						total_cat_data.clear();
						id_data.clear();
						desc_data.clear();
						cat_data.clear();
						listUpdatefordepartment();
					}
				});

				cancelcat.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						deptid.setText("");
						deptdesc.setText("");
						// categoryid.setSelection(0);
						// printerspinner.setSelection(0);
						foodcheck.setChecked(false);
						tax1dept.setChecked(false);
						tax2dept.setChecked(false);
						tax3dept.setChecked(false);
						bartaxdept.setChecked(false);
					}
				});

				savecat.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						final String uniqueval = Parameters.randomValue();
						final String id = deptid.getText().toString();
						final String desc = deptdesc.getText().toString();
						String printertime = "";
						if (printerspinner.getSelectedItem().toString()
								.equals("")
								|| printerspinner.getSelectedItem().toString()
										.equals("None")) {
							printervalfordept = "None";
						} else {
							printervalfordept = printerspinner
									.getSelectedItem().toString();
						}
						if (printerspinnerminutes.getSelectedItem().toString()
								.equals("")) {
							printertime = "";
						} else {
							printertime = printerspinnerminutes
									.getSelectedItem().toString();
						}
						if (printerspinnerseconds.getSelectedItem().toString()
								.equals("")) {
							printertime = printertime + "";
						} else {
							printertime = printertime
									+ ":"
									+ printerspinnerseconds.getSelectedItem()
											.toString();
						}
						System.out.println("printer time val is kkk:"
								+ printertime);
						timeval = printertime;
						System.out.println("timeval is:" + timeval);
						System.out.println("printer val is:"
								+ printervalfordept);
						if (foodcheck.isChecked()) {
							foodcheckval = "yes";
						} else {
							foodcheckval = "no";
						}
						if (tax1dept.isChecked()) {
							taxvalforsavefordept = taxvalforsavefordept
									+ tax1dept.getText().toString() + ",";
						}
						if (tax2dept.isChecked()) {
							taxvalforsavefordept = taxvalforsavefordept
									+ tax2dept.getText().toString() + ",";
						}
						if (tax3dept.isChecked()) {
							taxvalforsavefordept = taxvalforsavefordept
									+ tax3dept.getText().toString() + ",";
						}
						if (bartaxdept.isChecked()) {
							taxvalforsavefordept = taxvalforsavefordept
									+ bartaxdept.getText().toString() + ",";
						}
						if (spinnerData.isEmpty()) {
							Toast.makeText(InventoryActivity.this,
									"Please add atleast a single category",
									Toast.LENGTH_SHORT).show();
						} else {
							final String category = categoryidspinner
									.getSelectedItem().toString();
							if (id.equals("") || category.equals("")) {
								Toast.makeText(
										InventoryActivity.this,
										"Please enter category id and department id",
										Toast.LENGTH_SHORT).show();
							} else {
								// if(Parameters.usertype.equals("demo")){
								
			

								String selectQuery = "SELECT  * FROM "
										+ DatabaseForDemo.DEPARTMENT_TABLE
										+ " where "
										+ DatabaseForDemo.DepartmentID + "=\""
										+ id + "\"";

								Cursor mCursor =dbforloginlogoutReadInvetory.rawQuery(selectQuery, null);
								if (mCursor.getCount() > 0) {
									Toast.makeText(InventoryActivity.this,
											"Department id already exist",
											Toast.LENGTH_SHORT).show();
									
								} else {
									
									ContentValues contentValues = new ContentValues();
									contentValues.put(
											DatabaseForDemo.UNIQUE_ID,
											uniqueval);
									contentValues.put(
											DatabaseForDemo.CREATED_DATE,
											Parameters.currentTime());
									contentValues.put(
											DatabaseForDemo.MODIFIED_DATE,
											Parameters.currentTime());
									contentValues.put(
											DatabaseForDemo.MODIFIED_IN,
											"Local");
									contentValues.put(
											DatabaseForDemo.DepartmentID, id);
									contentValues.put(
											DatabaseForDemo.DepartmentDesp,
											desc);
									contentValues
											.put(DatabaseForDemo.CategoryForDepartment,
													category);
									/*
									 * contentValues.put(DatabaseForDemo.
									 * PrinterForDept, printervalfordept);
									 */
									contentValues
											.put(DatabaseForDemo.FoodstampableForDept,
													foodcheckval);
									contentValues.put(
											DatabaseForDemo.TaxValForDept,
											taxvalforsavefordept);
									contentValues.put(
											DatabaseForDemo.CHECKED_VALUE,
											"true");
									dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.DEPARTMENT_TABLE,
											null, contentValues);

									ContentValues contentValues1 = new ContentValues();
									contentValues1.put(
											DatabaseForDemo.UNIQUE_ID,
											uniqueval);
									contentValues1.put(
											DatabaseForDemo.CREATED_DATE,
											Parameters.currentTime());
									contentValues1.put(
											DatabaseForDemo.MODIFIED_DATE,
											Parameters.currentTime());
									contentValues1.put(
											DatabaseForDemo.MODIFIED_IN,
											"Local");
									contentValues1.put(
											DatabaseForDemo.PrinterForDept,
											printervalfordept);
									contentValues1.put(
											DatabaseForDemo.TimeForDeptPrint,
											timeval);
									contentValues1.put(
											DatabaseForDemo.DepartmentID, id);
									dbforloginlogoutWriteInvetory.insert(
											DatabaseForDemo.DEPARTMENT_PRINTER_COMMANDS,
											null, contentValues1);
									deptid.setText("");
									deptdesc.setText("");
									categoryidspinner.setSelection(0);
									tax1dept.setChecked(false);
									tax2dept.setChecked(false);
									tax3dept.setChecked(false);
									bartaxdept.setChecked(false);
									foodcheck.setChecked(false);
									printerspinner.setSelection(0);
									
									

									try {
										JSONObject data = new JSONObject();
										JSONObject jsonobj = new JSONObject();
										jsonobj.put(
												DatabaseForDemo.DepartmentID,
												id);
										jsonobj.put(
												DatabaseForDemo.DepartmentDesp,
												desc);
										jsonobj.put(
												DatabaseForDemo.CategoryForDepartment,
												category);
										jsonobj.put(
												DatabaseForDemo.CHECKED_VALUE,
												"true");
										jsonobj.put(
												DatabaseForDemo.FoodstampableForDept,
												foodcheckval);
										jsonobj.put(
												DatabaseForDemo.TaxValForDept,
												taxvalforsavefordept);
										jsonobj.put(DatabaseForDemo.UNIQUE_ID,
												uniqueval);
										JSONArray fields = new JSONArray();
										fields.put(0, jsonobj);
										data.put("fields", fields);
										dataval = data.toString();
										
									} catch (JSONException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}

									Toast.makeText(InventoryActivity.this,
											"Department inserted successfully",
											Toast.LENGTH_SHORT).show();

									if (Parameters.OriginalUrl.equals("")) {
										System.out
												.println("there is no server url val");
									} else {
										boolean isnet = Parameters.isNetworkAvailable(InventoryActivity.this);
										if (isnet) {
											new Thread(new Runnable() {
												@Override
												public void run() {
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
														System.out.println("array_list length for insert is:"
																+ array.length());
														JSONArray array2 = obj
																.getJSONArray("delete-queries");
														System.out.println("array2_list length for delete is:"
																+ array2.length());
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
															System.out
																	.println("delete query"
																			+ jj
																			+ " is :"
																			+ deletequery);

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
															System.out
																	.println("delete query"
																			+ jj
																			+ " is :"
																			+ insertquery);

														dbforloginlogoutWriteInvetory.execSQL(deletequery);
														dbforloginlogoutWriteInvetory.execSQL(insertquery);
															System.out
																	.println("queries executed"
																			+ ii);

														}
													} catch (JSONException e) {
														// TODO Auto-generated
														// catch block
														e.printStackTrace();
													}

													String select = "select *from "
															+ DatabaseForDemo.MISCELLANEOUS_TABLE;
													Cursor cursor = dbforloginlogoutReadInvetory
															.rawQuery(select,
																	null);
													if (cursor.getCount() > 0) {
													dbforloginlogoutWriteInvetory.execSQL("update "
																+ DatabaseForDemo.MISCELLANEOUS_TABLE
																+ " set "
																+ DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL
																+ "=\""
																+ servertiem
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
														dbforloginlogoutWriteInvetory.insert(
																DatabaseForDemo.MISCELLANEOUS_TABLE,
																null,
																contentValues1);

													}
													dataval = "";
													
												}
											}).start();
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
											dbforloginlogoutWriteInvetory.insert(
													DatabaseForDemo.PENDING_QUERIES_TABLE,
													null, contentValues2);
											
											
											dataval = "";
										}
									}
								}
								mCursor.close();
								/*
								 * }else{ Toast.makeText(InventoryActivity.this,
								 * "This is not demo login",
								 * Toast.LENGTH_SHORT).show(); }
								 */
							}
						}
					}
				});

				detailslayout.addView(layout);
			}
		});

		vendor.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				modifieritemval = "";
				countthisitemval = "";
				printonreceiptval = "";
				allowbuybackval = "";
				foodstampableval = "";
				notesval = "";
				promptpriceval = "";
				skuval = "";
				bonuspointsval = "";
				barcodeval = "";
				commissionval = "";
				locationval = "";
				unitsizeval = "";
				printervalfordept = "";
				foodcheckval = "";
				taxvalforduplicate = 0;
				taxvalforsaveforduplicate = "";
				taxvalforsavefordept = "";
				taxvalforsaveforprod = "";
				unittypeval = "";
				commissionspinnerval = 0;
				taxvalforprod = 0;
				optional_info.clear();
				skuarray.clear();
				modifierindividualitemsarray.clear();
				pricingvendorlistinorderinginfo.clear();
				detailslayout.removeAllViews();
				product.setBackgroundResource(R.drawable.toprightmenu);
				department.setBackgroundResource(R.drawable.toprightmenu);
				vendor.setBackgroundResource(R.drawable.highlightedtopmenuitem);
				category.setBackgroundResource(R.drawable.toprightmenu);
				product.setTextColor(Color.WHITE);
				department.setTextColor(Color.WHITE);
				vendor.setTextColor(Color.BLACK);
				category.setTextColor(Color.WHITE);
				LayoutInflater inflater = getLayoutInflater();
				View layout = inflater.inflate(R.layout.vendor,
						(ViewGroup) v.findViewById(R.id.inflatingLayout));
				pomethodList.clear();
				pomethodList.add("Print");
				pomethodList.add("Fax");
				pomethodList.add("Email");

				v_no = (EditText) layout.findViewById(R.id.v_no);
				v_terms = (EditText) layout.findViewById(R.id.v_terms);
				v_minorder = (EditText) layout.findViewById(R.id.v_min_order);
				v_commission = (EditText) layout
						.findViewById(R.id.v_commission);
				v_company = (EditText) layout.findViewById(R.id.v_company);
				v_flatrent = (EditText) layout.findViewById(R.id.v_flatrent);
				v_tax = (EditText) layout.findViewById(R.id.v_tax);
				v_billable = (EditText) layout.findViewById(R.id.v_billable);
				v_social = (EditText) layout.findViewById(R.id.v_social);
				v_street = (EditText) layout.findViewById(R.id.v_street);
				v_extended = (EditText) layout.findViewById(R.id.v_extended);
				v_city = (EditText) layout.findViewById(R.id.v_city);
				v_state = (EditText) layout.findViewById(R.id.v_state);
				v_zip = (EditText) layout.findViewById(R.id.v_zip);
				v_country = (EditText) layout.findViewById(R.id.v_country);
				v_firstname = (EditText) layout.findViewById(R.id.v_firstname);
				v_lastname = (EditText) layout.findViewById(R.id.v_lastname);
				v_phone = (EditText) layout.findViewById(R.id.v_phone);
				v_fax = (EditText) layout.findViewById(R.id.v_fax);
				v_email = (EditText) layout.findViewById(R.id.v_email);
				v_website = (EditText) layout.findViewById(R.id.v_website);
				poMethod = (Spinner) layout.findViewById(R.id.v_po);
				savecat = (Button) layout.findViewById(R.id.save);
				cancelcat = (Button) layout.findViewById(R.id.cancel);
				ArrayAdapter<String> adapter = new ArrayAdapter<String>(
						InventoryActivity.this,
						android.R.layout.simple_spinner_item, pomethodList);
				poMethod.setAdapter(adapter);

				list_View = (ListView) layout.findViewById(R.id.listView1);
				ll = (LinearLayout) layout.findViewById(R.id.linearLayout1);
				Button heading = (Button) layout.findViewById(R.id.button2);
				heading.setText("Vendor Details");
				list_View.setItemsCanFocus(false);
				dataclear();

				addcat = (Button) layout.findViewById(R.id.addcat);
				viewcat = (Button) layout.findViewById(R.id.viewcat);
				ll_add = (LinearLayout) layout.findViewById(R.id.add_ll);
				ll_view = (LinearLayout) layout.findViewById(R.id.view_ll);

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
						listUpdateforvendor();
					}
				});

				cancelcat.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						v_no.setText("");
						v_terms.setText("");
						v_minorder.setText("");
						v_commission.setText("");
						v_company.setText("");
						v_flatrent.setText("");
						v_tax.setText("");
						v_billable.setText("");
						v_social.setText("");
						v_street.setText("");
						v_extended.setText("");
						v_city.setText("");
						v_state.setText("");
						v_zip.setText("");
						v_country.setText("");
						v_firstname.setText("");
						v_lastname.setText("");
						v_phone.setText("");
						v_fax.setText("");
						v_email.setText("");
						v_website.setText("");
						poMethod.setSelection(0);
					}
				});

				savecat.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						final String uniqueval = Parameters.randomValue();
						final String v_noval = v_no.getText().toString();
						final String v_termsval = v_terms.getText().toString();
						final String v_minorderval = v_minorder.getText()
								.toString();
						final String v_commissionval = v_commission.getText()
								.toString();
						final String v_companyval = v_company.getText()
								.toString();
						final String v_flarentval = v_flatrent.getText()
								.toString();
						final String v_taxval = v_tax.getText().toString();
						final String v_billableval = v_billable.getText()
								.toString();
						final String v_socialval = v_social.getText()
								.toString();
						final String v_streetval = v_street.getText()
								.toString();
						final String v_extendedval = v_extended.getText()
								.toString();
						final String v_cityval = v_city.getText().toString();
						final String v_stateval = v_state.getText().toString();
						final String v_zipval = v_zip.getText().toString();
						final String v_countryval = v_country.getText()
								.toString();
						final String v_firstnameval = v_firstname.getText()
								.toString();
						final String v_lastnameval = v_lastname.getText()
								.toString();
						final String v_phoneval = v_phone.getText().toString();
						final String v_faxval = v_fax.getText().toString();
						final String v_emailval = v_email.getText().toString();
						final String v_websiteval = v_website.getText()
								.toString();
						final String poMethodval = poMethod.getSelectedItem()
								.toString();
						if (v_no.getText().toString().equals("")) {
							Toast.makeText(InventoryActivity.this,
									"Please enter vendor number",
									Toast.LENGTH_SHORT).show();
						} else {
							// if(Parameters.usertype.equals("demo")){
							String selectQuery = "SELECT  * FROM "
									+ DatabaseForDemo.VENDOR_TABLE + " where "
									+ DatabaseForDemo.VENDOR_NO + "=\""
									+ v_no.getText().toString() + "\"";

							Cursor mCursor =dbforloginlogoutReadInvetory.rawQuery(selectQuery, null);
							if (mCursor.getCount() > 0) {
								Toast.makeText(InventoryActivity.this,
										"Vendor id already exist",
										Toast.LENGTH_SHORT).show();
								
							} else {
								
								ContentValues contentValues = new ContentValues();
								contentValues.put(DatabaseForDemo.UNIQUE_ID,
										uniqueval);
								contentValues.put(DatabaseForDemo.CREATED_DATE,
										Parameters.currentTime());
								contentValues.put(
										DatabaseForDemo.MODIFIED_DATE,
										Parameters.currentTime());
								contentValues.put(DatabaseForDemo.MODIFIED_IN,
										"Local");
								contentValues.put(DatabaseForDemo.VENDOR_NO,
										v_no.getText().toString());
								contentValues.put(DatabaseForDemo.VENDOR_TERMS,
										v_terms.getText().toString());
								contentValues.put(
										DatabaseForDemo.VENDOR_MIN_ORDER,
										v_minorder.getText().toString());
								contentValues
										.put(DatabaseForDemo.VENDOR_COMMISSION_PERCENT,
												v_commission.getText()
														.toString());
								contentValues.put(
										DatabaseForDemo.VENDOR_COMPANY_NAME,
										v_company.getText().toString());
								contentValues.put(
										DatabaseForDemo.VENDOR_FLAT_RENT_RATE,
										v_flatrent.getText().toString());
								contentValues.put(
										DatabaseForDemo.VENDOR_TAX_ID, v_tax
												.getText().toString());
								contentValues
										.put(DatabaseForDemo.VENDOR_BILLABLE_DEPARTMENT,
												v_billable.getText().toString());
								contentValues
										.put(DatabaseForDemo.VENDOR_SOCIAL_SECURITY_NO,
												v_social.getText().toString());
								contentValues.put(
										DatabaseForDemo.VENDOR_STREET_ADDRESS,
										v_street.getText().toString());
								contentValues
										.put(DatabaseForDemo.VENDOR_EXTENDED_ADDRESS,
												v_extended.getText().toString());
								contentValues.put(DatabaseForDemo.VENDOR_CITY,
										v_city.getText().toString());
								contentValues.put(DatabaseForDemo.VENDOR_STATE,
										v_state.getText().toString());
								contentValues.put(
										DatabaseForDemo.VENDOR_ZIP_CODE, v_zip
												.getText().toString());
								contentValues.put(
										DatabaseForDemo.VENDOR_COUNTRY,
										v_country.getText().toString());
								contentValues.put(
										DatabaseForDemo.VENDOR_FIRST_NAME,
										v_firstname.getText().toString());
								contentValues.put(
										DatabaseForDemo.VENDOR_LAST_NAME,
										v_lastname.getText().toString());
								contentValues
										.put(DatabaseForDemo.VENDOR_TELEPHONE_NUMBER,
												v_phone.getText().toString());
								contentValues.put(
										DatabaseForDemo.VENDOR_FAX_NUMBER,
										v_fax.getText().toString());
								contentValues.put(DatabaseForDemo.VENDOR_EMAIL,
										v_email.getText().toString());
								contentValues.put(
										DatabaseForDemo.VENDOR_WEBSITE,
										v_website.getText().toString());
								contentValues
										.put(DatabaseForDemo.VENDOR_PO_DELIVERY_METHOD,
												poMethod.getSelectedItem()
														.toString());

								dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.VENDOR_TABLE, null,
										contentValues);

								
								
								Toast.makeText(InventoryActivity.this,
										"Vendor inserted successfully",
										Toast.LENGTH_SHORT).show();

								try {
									JSONObject data = new JSONObject();
									JSONObject jsonobj = new JSONObject();
									jsonobj.put(DatabaseForDemo.VENDOR_NO,
											v_noval);
									jsonobj.put(
											DatabaseForDemo.VENDOR_MIN_ORDER,
											v_minorderval);
									jsonobj.put(DatabaseForDemo.VENDOR_TERMS,
											v_termsval);
									jsonobj.put(
											DatabaseForDemo.VENDOR_PO_DELIVERY_METHOD,
											poMethodval);
									jsonobj.put(
											DatabaseForDemo.VENDOR_COMMISSION_PERCENT,
											v_commissionval);
									jsonobj.put(
											DatabaseForDemo.VENDOR_COMPANY_NAME,
											v_companyval);
									jsonobj.put(
											DatabaseForDemo.VENDOR_FLAT_RENT_RATE,
											v_flarentval);
									jsonobj.put(DatabaseForDemo.VENDOR_TAX_ID,
											v_taxval);
									jsonobj.put(
											DatabaseForDemo.VENDOR_BILLABLE_DEPARTMENT,
											v_billableval);
									jsonobj.put(
											DatabaseForDemo.VENDOR_SOCIAL_SECURITY_NO,
											v_socialval);
									jsonobj.put(
											DatabaseForDemo.VENDOR_STREET_ADDRESS,
											v_streetval);
									jsonobj.put(
											DatabaseForDemo.VENDOR_EXTENDED_ADDRESS,
											v_extendedval);
									jsonobj.put(DatabaseForDemo.VENDOR_CITY,
											v_cityval);
									jsonobj.put(DatabaseForDemo.VENDOR_STATE,
											v_stateval);
									jsonobj.put(
											DatabaseForDemo.VENDOR_ZIP_CODE,
											v_zipval);
									jsonobj.put(DatabaseForDemo.VENDOR_COUNTRY,
											v_countryval);
									jsonobj.put(
											DatabaseForDemo.VENDOR_FIRST_NAME,
											v_firstnameval);
									jsonobj.put(
											DatabaseForDemo.VENDOR_LAST_NAME,
											v_lastnameval);
									jsonobj.put(
											DatabaseForDemo.VENDOR_TELEPHONE_NUMBER,
											v_phoneval);
									jsonobj.put(
											DatabaseForDemo.VENDOR_FAX_NUMBER,
											v_faxval);
									jsonobj.put(DatabaseForDemo.VENDOR_EMAIL,
											v_emailval);
									jsonobj.put(DatabaseForDemo.VENDOR_WEBSITE,
											v_websiteval);
									jsonobj.put(DatabaseForDemo.UNIQUE_ID,
											uniqueval);
									JSONArray fields = new JSONArray();
									fields.put(0, jsonobj);
									data.put("fields", fields);
									dataval = data.toString();
									
								} catch (JSONException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}

								if (Parameters.OriginalUrl.equals("")) {
									System.out
											.println("there is no server url val");
								} else {
									boolean isnet = Parameters.isNetworkAvailable(InventoryActivity.this);
									if (isnet) {
										new Thread(new Runnable() {
											@Override
											public void run() {
												JsonPostMethod jsonpost = new JsonPostMethod();
												String response = jsonpost
														.postmethodfordirect(
																"admin",
																"abcdefg",
																DatabaseForDemo.VENDOR_TABLE,
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
													System.out.println("array_list length for insert is:"
															+ array.length());
													JSONArray array2 = obj
															.getJSONArray("delete-queries");
													System.out.println("array2_list length for delete is:"
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

													dbforloginlogoutWriteInvetory.execSQL(deletequery);
													dbforloginlogoutWriteInvetory.execSQL(insertquery);
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
												Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(
														select, null);
												if (cursor.getCount() > 0) {
												dbforloginlogoutWriteInvetory.execSQL("update "
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
													dbforloginlogoutWriteInvetory.insert(
															DatabaseForDemo.MISCELLANEOUS_TABLE,
															null,
															contentValues1);
													
												}
												cursor.close();
												dataval = "";
											}
										}).start();
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
														DatabaseForDemo.VENDOR_TABLE);
										contentValues1
												.put(DatabaseForDemo.CURRENT_TIME_PENDING,
														Parameters
																.currentTime());
										contentValues1.put(
												DatabaseForDemo.PARAMETERS,
												dataval);
										dbforloginlogoutWriteInvetory.insert(
												DatabaseForDemo.PENDING_QUERIES_TABLE,
												null, contentValues1);
										
										
										dataval = "";
									}
								}

								v_no.setText("");
								v_terms.setText("");
								v_minorder.setText("");
								v_commission.setText("");
								v_company.setText("");
								v_flatrent.setText("");
								v_tax.setText("");
								v_billable.setText("");
								v_social.setText("");
								v_street.setText("");
								v_extended.setText("");
								v_city.setText("");
								v_state.setText("");
								v_zip.setText("");
								v_country.setText("");
								v_firstname.setText("");
								v_lastname.setText("");
								v_phone.setText("");
								v_fax.setText("");
								v_email.setText("");
								v_website.setText("");
								poMethod.setSelection(0);
							}
							mCursor.close();
						}
					}
				});

				detailslayout.addView(layout);
			}
		});
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
	protected String capitalize_string(String s) 
	{
		System.out.println("source.length(): "+s.length());
//		    StringBuffer res = new StringBuffer();
//
//		    String[] strArr = source.split(" ");
//		    for (String str : strArr) {
//		        char[] stringArray = str.trim().toCharArray();
//		        stringArray[0] = Character.toUpperCase(stringArray[0]);
//		        str = new String(stringArray);
//
//		        res.append(str).append("");
//		    }
//		    System.out.println("res.length(): "+res.length());
//		    return res.toString().trim();
		
		final StringBuilder result = new StringBuilder(s.length());
		String[] words = s.split("\\s");
		for(int i=0,l=words.length;i<l;++i) {
		  if(i>0) result.append(" ");      
		  result.append(Character.toUpperCase(words[i].charAt(0)))
		        .append(words[i].substring(1));

		}
		System.out.println("result.length()"+result.toString().length());
		return result.toString();
	}
	@Override
	public void onDeleteClickedforsku(View v, String text) {
		skuarray.remove(text);
		if (skuarray != null) {
			SkuArrayAdapter skuadapter = new SkuArrayAdapter(
					getApplicationContext(), skuarray);
			skuadapter.setListener(InventoryActivity.this);
			skuslist.setAdapter(skuadapter);
		}
		Toast.makeText(InventoryActivity.this, "Sku deleted successfully",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDeleteClickedforprinter(View v, String text) {
		printerlistfordisplay.remove(text);
		if (printerlistfordisplay != null) {
			PrinterArrayAdapter skuadapter = new PrinterArrayAdapter(
					getApplicationContext(), printerlistfordisplay);
			skuadapter.setListener(InventoryActivity.this);
			printerlistview.setAdapter(skuadapter);
		}
		Toast.makeText(InventoryActivity.this, "Sku deleted successfully",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onDeleteClickedformodifier(View v, HashMap<String, String> map) {
		modifierindividualitemsarray.remove(map);
		if (modifierindividualitemsarray != null) {
			ModifierIndividualItemAdapter modifieradapter = new ModifierIndividualItemAdapter(
					getApplicationContext(), modifierindividualitemsarray);
			modifieradapter.setListener(InventoryActivity.this);
			modifierlist.setAdapter(modifieradapter);
		}
		Toast.makeText(InventoryActivity.this, "Deleted successfully",
				Toast.LENGTH_SHORT).show();
	}

	public void countforproduct(int j) {
		testforid = 1;
		stLoop = (j - 1) * pagecount;
		endLoop = j * pagecount;
		if (endLoop >= totalcount) {
			endLoop = totalcount;
		}
		itemno_data = getDataforproduct(stLoop, endLoop, total_itemno_data);
		itemdesc_data = getDataforproduct(stLoop, endLoop, total_itemdesc_data);
		pricecharge_data = getDataforproduct(stLoop, endLoop,
				total_pricecharge_data);
		stock_data = getDataforproduct(stLoop, endLoop, total_stock_data);
		desc2_data = getDataforproduct(stLoop, endLoop, total_desc2_data);
		vendor_data = getDataforproduct(stLoop, endLoop, total_vendor_data);
		arrayList_Items = new ArrayList<HashMap<String,String>>();
		for (int i = 0; i < itemno_data.size(); i++)
		{
			HashMap<String,String> hashMap = new HashMap<String, String>();
			hashMap.put("itemno", itemno_data.get(i));
			hashMap.put("itemname", itemdesc_data.get(i));
			hashMap.put("pricecharge", pricecharge_data.get(i));
			hashMap.put("stock", stock_data.get(i));
			hashMap.put("desc2", desc2_data.get(i));
			hashMap.put("vendor", vendor_data.get(i));
			arrayList_Items.add(hashMap);
		}
		
//		ImageAdapterForProduct adapter = new ImageAdapterForProduct(this,
//				itemno_data, itemdesc_data, pricecharge_data, stock_data,
//				desc2_data, vendor_data);
		imageAdapterForProduct = new ImageAdapterForProduct(this,arrayList_Items);
		imageAdapterForProduct.setListener(this);
		System.out.println("lllllllliiiiiiiiiiiiiiiiiii");
		list_View.setAdapter(imageAdapterForProduct);
	}

	public ArrayList<String> getDataforproduct(int offset, int limit,
			ArrayList<String> list) {
		ArrayList<String> newList = new ArrayList<String>(limit);
		int end = limit;

		if (end > list.size()) {
			end = list.size();
		}
		newList.addAll(list.subList(offset, end));
		return newList;
	}

	public void gettingCount() {
		autoTextStrings.clear();
		autoTextStringsname.clear();
		autoTextStringsvendor.clear();
		displayarray.clear();
		try{
		Cursor totalcursor =dbforloginlogoutReadInvetory.rawQuery(
				"select " + DatabaseForDemo.INVENTORY_ITEM_NO + " , "+DatabaseForDemo.INVENTORY_VENDOR+ " , "
						+ DatabaseForDemo.INVENTORY_ITEM_NAME + " from "
						+ DatabaseForDemo.INVENTORY_TABLE, null);
		
		int totalnoofrecords = totalcursor.getCount();
		if (totalcursor != null) {
			if (totalcursor.moveToFirst()) {
				do {
					String itemno = totalcursor.getString(totalcursor
							.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NO));
					// total_itemno_data.add(itemno);
					autoTextStrings.add(itemno);
					String itemname = totalcursor
							.getString(totalcursor
									.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NAME));
					// total_itemdesc_data.add(itemname);
					autoTextStringsname.add(itemname);
					autoTextStringsvendor
					.add(totalcursor.getString(totalcursor
							.getColumnIndex(DatabaseForDemo.INVENTORY_VENDOR)));
				} while (totalcursor.moveToNext());
			}
		}
		totalcursor.close();
		for (int jj = 0; jj < autoTextStrings.size(); jj++) {

			String totalselectQueryforvendor = "SELECT  "
					+ DatabaseForDemo.VENDERPART_NO + " FROM "
					+ DatabaseForDemo.ORDERING_INFO_TABLE + " where "
					+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\""
					+ autoTextStrings.get(jj) + "\"" + " AND "
					+ DatabaseForDemo.PREFERRED + "=\"true\"";

			Cursor totalmCursorforvendor =dbforloginlogoutReadInvetory.rawQuery(
					totalselectQueryforvendor, null);
			if (totalmCursorforvendor.getCount() > 0) {
				if (totalmCursorforvendor != null) {
					if (totalmCursorforvendor.moveToFirst()) {
						do {
							if (totalmCursorforvendor
									.isNull(totalmCursorforvendor
											.getColumnIndex(DatabaseForDemo.VENDERPART_NO))) {

							} else {
								String vendornum = totalmCursorforvendor
										.getString(totalmCursorforvendor
												.getColumnIndex(DatabaseForDemo.VENDERPART_NO));
								autoTextStringsvendor.add(vendornum);
							}
						} while (totalmCursorforvendor.moveToNext());
					}
				}
			}
			totalmCursorforvendor.close();
		}
		
		
		int resultsperset = 100;
		int noofsets = 0;
		int min = 1;
		// if(totalnoofrecords>200){
		noofsets = totalnoofrecords / resultsperset;
		int reminder = totalnoofrecords % resultsperset;
		if (reminder > 0) {
			noofsets = noofsets + 1;
		}
		for (int i = 1; i <= noofsets; i++) {
			String displaystring;
			if (i == noofsets) {
				displaystring = min + " - " + totalnoofrecords;
			} else {
				displaystring = min + " - " + (i * resultsperset);
			}
			min = (i * resultsperset) + 1;
			displayarray.add(displaystring);
		}
		}catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	}

	public void listUpdateforproduct(String offset, String limit, String query) {

		itemno_data.clear();
		itemdesc_data.clear();
		pricecharge_data.clear();
		stock_data.clear();
		desc2_data.clear();
		vendor_data.clear();
		departmentforproduct_data.clear();
		cost_data.clear();
		pricetax_data.clear();
		tax_product_data.clear();
		total_itemno_data.clear();
		total_itemdesc_data.clear();
		total_pricecharge_data.clear();
		total_stock_data.clear();
		total_desc2_data.clear();
		total_vendor_data.clear();
		total_departmentforproduct_data.clear();
		total_cost_data.clear();
		total_pricetax_data.clear();
		total_tax_product_data.clear();
		ll.removeAllViews();

		
		try{

		int offsetint = Integer.parseInt(offset);

		// String selectQuery = "SELECT  * FROM " +
		// DatabaseForDemo.INVENTORY_TABLE+" limit "+limit+" offset "+(Integer.parseInt(offset)-1);
		String selectQuery = query + " limit " + limit + " offset "
				+ (Integer.parseInt(offset) - 1);

		Cursor mCursor =dbforloginlogoutReadInvetory.rawQuery(selectQuery, null);

		// }else{
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					// String departmentforproduct =
					// mCursor.getString(mCursor.getColumnIndex(DatabaseForDemo.INVENTORY_DEPARTMENT));
					// total_departmentforproduct_data.add(departmentforproduct);
					String itemno = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NO));
					total_itemno_data.add(itemno);
					// Parameters.autoTextStrings.add(itemno);
					String itemname = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NAME));
					total_itemdesc_data.add(itemname);
					// Parameters.autoTextStringsname.add(itemname);
					String desc2 = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.INVENTORY_SECOND_DESCRIPTION));
					total_desc2_data.add(desc2);
					// String cost =
					// mCursor.getString(mCursor.getColumnIndex(DatabaseForDemo.INVENTORY_AVG_COST));
					// total_cost_data.add(cost);
					String pricecharge = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.INVENTORY_PRICE_CHANGE));
					total_pricecharge_data.add(pricecharge);
					// String pricetax =
					// mCursor.getString(mCursor.getColumnIndex(DatabaseForDemo.INVENTORY_PRICE_TAX));
					// total_pricetax_data.add(pricetax);
					String instock = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.INVENTORY_IN_STOCK));
					total_stock_data.add(instock);
					// String vendor =
					// mCursor.getString(mCursor.getColumnIndex(DatabaseForDemo.INVENTORY_VENDOR));
					// System.out.println("vendor val is:"+vendor);
					// String taxprod =
					// mCursor.getString(mCursor.getColumnIndex(DatabaseForDemo.INVENTORY_PRICE_TAX));
					// total_tax_product_data.add(taxprod);
				} while (mCursor.moveToNext());
			}
		}
		mCursor.close();
		for (int ii = 0; ii < total_itemno_data.size(); ii++) {

			String selectQueryforvendor = "SELECT  "
					+ DatabaseForDemo.VENDERPART_NO + " FROM "
					+ DatabaseForDemo.ORDERING_INFO_TABLE + " where "
					+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\""
					+ total_itemno_data.get(ii) + "\"" + " AND "
					+ DatabaseForDemo.PREFERRED + "=\"true\"";

			Cursor mCursorforvendor =dbforloginlogoutReadInvetory.rawQuery(selectQueryforvendor, null);
			if (mCursorforvendor.getCount() > 0) {
				if (mCursorforvendor != null) {
					if (mCursorforvendor.moveToFirst()) {
						do {
							if (mCursorforvendor
									.isNull(mCursorforvendor
											.getColumnIndex(DatabaseForDemo.VENDERPART_NO))) {

							} else {
								String vendornum = mCursorforvendor
										.getString(mCursorforvendor
												.getColumnIndex(DatabaseForDemo.VENDERPART_NO));
								total_vendor_data.add(vendornum);
								// Parameters.autoTextStringsvendor.add(vendornum);
							}
						} while (mCursorforvendor.moveToNext());
					}
				}
			} else {
				total_vendor_data.add("");
			}
			mCursorforvendor.close();
		}

		/*
		 * int totalnoofrecords = mCursor.getCount();
		 * System.out.println("total records val is:"+totalnoofrecords); int
		 * resultsperset = 20; int noofsets = 0; int min = 1;
		 * //if(totalnoofrecords>200){ noofsets =
		 * totalnoofrecords/resultsperset; int reminder =
		 * totalnoofrecords%resultsperset; if(reminder>0){ noofsets =
		 * noofsets+1; } System.out.println("noofrecords:"+noofsets); for(int
		 * i=1; i<=noofsets; i++){ String displaystring; if(i==noofsets){
		 * displaystring = min+" - "+totalnoofrecords; }else{ displaystring =
		 * min+" - "+(i*resultsperset); } min = (int) ((i*resultsperset)+1);
		 * System.out.println("display string value is:"+displaystring);
		 * displayarray.add(displaystring); } System.out.println(displayarray);
		 */
		totalcount = total_itemno_data.size();
		int to = totalcount / pagecount;
		int too = totalcount % pagecount;
		int i = to;
		if (too != 0) {
			i = to + 1;
		}
		countforproduct(1);
		ll.setWeightSum(i);

		for (j = 1; j <= i; j++) {
			final Button btn = new Button(this);
			btn.setText("" + j);
			btn.setId(j);
			btn.setBackgroundResource(R.drawable.pnormal);
			LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
					LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, 1f);

			LinearLayout space = new LinearLayout(this);
			LayoutParams lp1 = new LayoutParams(1, LayoutParams.WRAP_CONTENT);
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
						((Button) findViewById(l)).setTextColor(Color.BLACK);
					}
					btn.setBackgroundResource(R.drawable.pactive);
					btn.setTextColor(Color.WHITE);
					countforproduct(Integer.parseInt(btn.getText().toString()
							.trim()));
					testforid = Integer.parseInt(btn.getText().toString()
							.trim());
				}

			});
		}
		// }
		}catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}

	@Override
	public void onInstantPoClickedforProduct(View v, final String itemno,
			final String stock) {
		final AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
		final EditText stocked,stock_notes;
		Button save, cancel;

		LayoutInflater mInflater = LayoutInflater.from(this);
		View layout = mInflater.inflate(R.layout.instant_po_details, null);

		stocked = (EditText) layout.findViewById(R.id.stock);
		stock_notes = (EditText) layout.findViewById(R.id.stock_notes);
		
		stocked.setText(stock);
		save = (Button) layout.findViewById(R.id.save);
		cancel = (Button) layout.findViewById(R.id.cancel);

		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try{
				String stockval = stocked.getText().toString();
				String notes = stock_notes.getText().toString();
				
				if (stockval.equals("")) {
					Toast.makeText(getApplicationContext(),
							"Please enter some stock value", Toast.LENGTH_SHORT)
							.show();
				} else {
					if (notes.equals("")) {
						notes="   ";
					}
					String stockold=stockgetHostory(itemno);
					dbforloginlogoutWriteInvetory.execSQL("update "
							+ DatabaseForDemo.INVENTORY_TABLE + " set "
							+ DatabaseForDemo.CREATED_DATE + "=\""
							+ Parameters.currentTime() + "\", "
							+ DatabaseForDemo.MODIFIED_DATE + "=\""
							+ Parameters.currentTime() + "\", "
							+ DatabaseForDemo.MODIFIED_IN + "=\"" + "Local"
							+ "\", " + DatabaseForDemo.INVENTORY_IN_STOCK + "=\""
							+ stockval + "\" where "
							+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\"" + itemno
							+ "\"");
					Double stockDiff=Double.valueOf(stockval)-Double.valueOf(stockold);
					Toast.makeText(InventoryActivity.this,
							"Stock saved successfully", Toast.LENGTH_SHORT)
							.show();
					stockModificationHostory(itemno,""+df.format(stockDiff),notes);
					final ArrayList<JSONObject> getlist = new ArrayList<JSONObject>();
					String selectQueryforinstantpo = "SELECT  * FROM "
							+ DatabaseForDemo.INVENTORY_TABLE + " where "
							+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\"" + itemno
							+ "\"";

					Cursor mCursorforvendor1 = dbforloginlogoutReadInvetory.rawQuery(
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
									String itemno = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NO));
									jsonobj.put(
											DatabaseForDemo.INVENTORY_ITEM_NO,
											itemno);
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
									String note = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.INVENTORY_NOTES));
									jsonobj.put(
											DatabaseForDemo.INVENTORY_NOTES,
											note);
									String t_tax = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.INVENTORY_TOTAL_TAX));
									jsonobj.put(
											DatabaseForDemo.INVENTORY_TOTAL_TAX,
											t_tax);
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
							
						}
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					if (Parameters.OriginalUrl.equals("")) {
						System.out.println("there is no server url val");
					} else {
						boolean isnet = Parameters.isNetworkAvailable(InventoryActivity.this);
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
												.println("array_list length for insert is:"
														+ array.length());
										JSONArray array2 = obj
												.getJSONArray("delete-queries");
										System.out
												.println("array2_list length for delete is:"
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

										dbforloginlogoutWriteInvetory.execSQL(deletequery);
										dbforloginlogoutWriteInvetory.execSQL(insertquery);
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
									Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(select, null);
									if (cursor.getCount() > 0) {
									dbforloginlogoutWriteInvetory.execSQL("update "
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
										dbforloginlogoutWriteInvetory.insert(
												DatabaseForDemo.MISCELLANEOUS_TABLE,
												null, contentValues1);
										
									}
									cursor.close();
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
							dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.PENDING_QUERIES_TABLE,
									null, contentValues1);
							dataval = "";
						}
					}
					gettingCount();
					listUpdateforproduct(offsetvals, "100",
							totalrecordselectQuery);
					alertDialog1.dismiss();
				}
				} catch (NumberFormatException e) {
					  e.printStackTrace();
					} catch (Exception e1) {
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

	}
	String stockitem_name="item_name";
	String stockgetHostory(String item_no){
		String stock="";
		String selectQueryforinstantpo = "SELECT  "+DatabaseForDemo.INVENTORY_ITEM_NAME+", "+DatabaseForDemo.INVENTORY_IN_STOCK+" FROM "
				+ DatabaseForDemo.INVENTORY_TABLE + " where "
				+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\"" + item_no
				+ "\"";

		Cursor mCursorfor1 = dbforloginlogoutReadInvetory.rawQuery(
				selectQueryforinstantpo, null);
		if (mCursorfor1 != null) {
			if (mCursorfor1.moveToFirst()) {
				do {
					if(mCursorfor1.isNull(mCursorfor1
							.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NAME))){
							}else{
								stockitem_name = mCursorfor1.getString(mCursorfor1
								.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NAME));
							}
					if(mCursorfor1.isNull(mCursorfor1
							.getColumnIndex(DatabaseForDemo.INVENTORY_IN_STOCK))){
						stock="0";
							}else{
					stock = mCursorfor1.getString(mCursorfor1
								.getColumnIndex(DatabaseForDemo.INVENTORY_IN_STOCK));
							}
				} while (mCursorfor1.moveToNext());
			}
		}
		mCursorfor1.close();
		Log.e("stock oldvalue",stockitem_name+" .... "+stock);
		return stock;
	}
	void stockModificationHostory(String item_no, String stockcount , String notes){
		ContentValues stock_contentValue = new ContentValues();
		stock_contentValue.put(
				DatabaseForDemo.UNIQUE_ID,
				Parameters.randomValue());
		stock_contentValue.put(
				DatabaseForDemo.CREATED_DATE,
				Parameters.currentTime());
		stock_contentValue.put(
				DatabaseForDemo.MODIFIED_DATE,
				Parameters.currentTime());
		stock_contentValue.put(
				DatabaseForDemo.MODIFIED_IN,
				"Local");
		stock_contentValue.put(
				DatabaseForDemo.MDF_ITEM_NO,
				item_no);
		stock_contentValue.put(DatabaseForDemo.MDF_ITEM_NAME,
				stockitem_name);
		stock_contentValue.put(DatabaseForDemo.MDF_STOCK_COUNT,
				""+stockcount);
		stock_contentValue.put(
				DatabaseForDemo.MDF_EMP_ID,
				Parameters.userid);
		stock_contentValue.put(
				DatabaseForDemo.MDF_STORE_ID,
				"Null");
		stock_contentValue
				.put(DatabaseForDemo.MDF_RECIVING_STORE,
						"Null");
		stock_contentValue
		.put(DatabaseForDemo.MDF_NOTES,
				notes);
		dbforloginlogoutWriteInvetory.insert(
				DatabaseForDemo.STOCK_MODIFICATION_TABLE,
				null, stock_contentValue);
		
		
		final ArrayList<JSONObject> getlistq = new ArrayList<JSONObject>();
		try {
		JSONObject getlistw = new JSONObject();
		getlistw.put(
				DatabaseForDemo.UNIQUE_ID,
				Parameters.randomValue());
		getlistw.put(
				DatabaseForDemo.CREATED_DATE,
				Parameters.currentTime());
		getlistw.put(
				DatabaseForDemo.MODIFIED_DATE,
				Parameters.currentTime());
		getlistw.put(
				DatabaseForDemo.MODIFIED_IN,
				"Local");
		getlistw.put(
				DatabaseForDemo.MDF_ITEM_NO,
				item_no);
		getlistw.put(DatabaseForDemo.MDF_ITEM_NAME,
				stockitem_name);
		getlistw.put(DatabaseForDemo.MDF_STOCK_COUNT,
				stockcount);
		getlistw.put(
				DatabaseForDemo.MDF_EMP_ID,
				Parameters.userid);
		getlistw.put(
				DatabaseForDemo.MDF_STORE_ID,
				"Null");
			getlistw
					.put(DatabaseForDemo.MDF_RECIVING_STORE,
							"Null");
			getlistw.put(DatabaseForDemo.MDF_NOTES,
					notes);
			getlistq.add(getlistw);
		} catch (JSONException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}
		
		try {
			JSONObject data = new JSONObject();
			JSONArray fields = new JSONArray();
			for (int i = 0; i < getlistq.size(); i++) {
				fields.put(i, getlistq.get(i));
				data.put("fields", fields);
				dataval = data.toString();
				Log.e("chintha",""+dataval);
			}
		} catch (JSONException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (Parameters.OriginalUrl.equals("")) {
			System.out.println("there is no server url val");
		} else {
			boolean isnet = Parameters.isNetworkAvailable(InventoryActivity.this);
			if (isnet) {
				new Thread(new Runnable() {
					@Override
					public void run() {
						JsonPostMethod jsonpost = new JsonPostMethod();
						String response = jsonpost
								.postmethodfordirect(
										"admin",
										"abcdefg",
										DatabaseForDemo.STOCK_MODIFICATION_TABLE,
										Parameters.currentTime(),
										Parameters.currentTime(),
										dataval, "");
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
									.println("array_list length for insert is:"
											+ array.length());
							JSONArray array2 = obj
									.getJSONArray("delete-queries");
							System.out
									.println("array2_list length for delete is:"
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

							dbforloginlogoutWriteInvetory.execSQL(deletequery);
							dbforloginlogoutWriteInvetory.execSQL(insertquery);
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
						Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(select, null);
						if (cursor.getCount() > 0) {
						dbforloginlogoutWriteInvetory.execSQL("update "
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
							dbforloginlogoutWriteInvetory.insert(
									DatabaseForDemo.MISCELLANEOUS_TABLE,
									null, contentValues1);
							
						}
						cursor.close();
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
						DatabaseForDemo.STOCK_MODIFICATION_TABLE);
				contentValues1.put(
						DatabaseForDemo.CURRENT_TIME_PENDING,
						Parameters.currentTime());
				contentValues1.put(DatabaseForDemo.PARAMETERS,
						dataval);
				dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.PENDING_QUERIES_TABLE,
						null, contentValues1);
				dataval = "";
			}
		}
		
	}
	
	@Override
	public void onEditClickedforProduct(View v, final String itemno,
			String itemname, String price, String stock, String desc2,
			String vendor) {
		// TODO Auto-generated method stub
		try{
		final AlertDialog alertDialog1 = new AlertDialog.Builder(
				InventoryActivity.this).create();
		Log.e("tagdd", ".....gggggg");
		final EditText itemnoed, itemnameed, desc2ed, costed, pricechargeed, pricetaxed, stocked;
		final CheckBox tax1check, tax2check, tax3check, bartaxcheck;
		final Spinner departmentforproduct, vendorproduct;
		Button save, cancel, optionalinfoed, notesed, modifiersed, orderinginfoed, saleshistoryed, printersed;

		LayoutInflater mInflater = LayoutInflater.from(InventoryActivity.this);
		View layout = mInflater.inflate(R.layout.product_details, null);

		departmentforproduct = (Spinner) layout.findViewById(R.id.department);
		itemnoed = (EditText) layout.findViewById(R.id.item_no);
		itemnoed.setEnabled(false);
		itemnameed = (EditText) layout.findViewById(R.id.description);
		desc2ed = (EditText) layout.findViewById(R.id.description2);
		desc2ed.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(desc2ed.getWindowToken(), 0);
				}
				return false;
			}
		});
		costed = (EditText) layout.findViewById(R.id.cost);
		pricechargeed = (EditText) layout.findViewById(R.id.price_charge);
		pricetaxed = (EditText) layout.findViewById(R.id.price_tax);
		stocked = (EditText) layout.findViewById(R.id.instock);
		stocked.setEnabled(false);
		vendorproduct = (Spinner) layout.findViewById(R.id.vendorprod);

		optionalinfoed = (Button) layout.findViewById(R.id.optionalinfo);
		notesed = (Button) layout.findViewById(R.id.notes);
		modifiersed = (Button) layout.findViewById(R.id.modifiers);
		orderinginfoed = (Button) layout.findViewById(R.id.orderinginfo);
		saleshistoryed = (Button) layout.findViewById(R.id.saleshistory);
		printersed = (Button) layout.findViewById(R.id.printers);

		tax1check = (CheckBox) layout.findViewById(R.id.checkBox1);
		tax2check = (CheckBox) layout.findViewById(R.id.checkBox2);
		tax3check = (CheckBox) layout.findViewById(R.id.checkBox3);
		tax1check.setText(taxlist.get(0));
		tax2check.setText(taxlist.get(1));
		tax3check.setText(taxlist.get(2));
		bartaxcheck = (CheckBox) layout.findViewById(R.id.checkBox4);
		save = (Button) layout.findViewById(R.id.save);
		cancel = (Button) layout.findViewById(R.id.cancel);
		ArrayList<String> deptspinnerdata = new ArrayList<String>();
		ArrayList<String> vendorspinnerdata = new ArrayList<String>();
		deptspinnerdata.clear();
		vendorspinnerdata.clear();
		skuarray.clear();
		modifierindividualitemsarray.clear();
		pricingvendorlistinorderinginfo.clear();

		// if(Parameters.usertype.equals("demo")){

		Cursor mCursor1 = dbforloginlogoutReadInvetory.rawQuery(
				"select " + DatabaseForDemo.DepartmentID + " from "
						+ DatabaseForDemo.DEPARTMENT_TABLE, null);
		System.out.println(mCursor1);
		if (mCursor1 != null) {
			if (mCursor1.moveToFirst()) {
				do {
					String catid = mCursor1.getString(mCursor1
							.getColumnIndex(DatabaseForDemo.DepartmentID));
					deptspinnerdata.add(catid);
				} while (mCursor1.moveToNext());
			}
		}
		mCursor1.close();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				InventoryActivity.this, android.R.layout.simple_spinner_item,
				deptspinnerdata);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		departmentforproduct.setAdapter(adapter);
		
		// 
		// 
		/*
		 * }else{ Toast.makeText(getApplicationContext(),
		 * "This is no profile data in demo login", Toast.LENGTH_SHORT).show();
		 * }
		 */

		/*
		 * Cursor mCursor2 =
		 * dbforloginlogoutReadInvetory.rawQuery("select "+DatabaseForDemo
		 * .VENDOR_NO+" from "+DatabaseForDemo.VENDOR_TABLE, null);
		 * System.out.println(mCursor2); if(mCursor2!=null){
		 * if(mCursor2.moveToFirst()){ do{ String catid =
		 * mCursor2.getString(mCursor2
		 * .getColumnIndex(DatabaseForDemo.VENDOR_NO));
		 * vendorspinnerdata.add(catid); }while(mCursor2.moveToNext()); }
		 * mCursor2.close();  ArrayAdapter<String> adapter = new
		 * ArrayAdapter<String>(InventoryActivity.this,
		 * android.R.layout.simple_spinner_item, vendorspinnerdata);
		 * adapter.setDropDownViewResource
		 * (android.R.layout.simple_spinner_item); //
		 * vendorproduct.setAdapter(adapter); }else{
		 * Toast.makeText(getApplicationContext(),
		 * "This is no profile data in demo login", Toast.LENGTH_SHORT).show();
		 * }
		 */

		/*
		 * }else{ Toast.makeText(getApplicationContext(),
		 * "This is not demo login", Toast.LENGTH_SHORT).show(); }
		 */

		
		

		String selectQuery = "SELECT  * FROM "
				+ DatabaseForDemo.INVENTORY_TABLE + " where "
				+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\"" + itemno + "\"";

		Cursor mCursor =dbforloginlogoutReadInvetory.rawQuery(selectQuery, null);
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					
					String departmentval = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.INVENTORY_DEPARTMENT));
					departmentforproduct.setSelection(deptspinnerdata
							.indexOf(departmentval));
					String itemnoval = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NO));
					itemnoed.setText(itemnoval);
					String itemnameval = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NAME));
					itemnameed.setText(itemnameval);
					String desc2val = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.INVENTORY_SECOND_DESCRIPTION));
					desc2ed.setText(desc2val);
					String costval = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.INVENTORY_AVG_COST));
					costed.setText(costval);
					String pricechargeval = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.INVENTORY_PRICE_CHANGE));
					pricechargeed.setText(pricechargeval);
					String pricetaxval = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.INVENTORY_PRICE_TAX));
					
					String texss=mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.INVENTORY_TAXONE));
					String[] parts = texss.split(",");
					double tax=0.0;
					for(int t=0;t<parts.length;t++){
						String ttt=parts[t]; 
						Log.e("",""+ttt);
						String query = "select * from "
								+ DatabaseForDemo.TAX_TABLE + " where "
								+ DatabaseForDemo.TAX_NAME + "=\""
								+ ttt + "\"";
						System.out.println(query);
						Cursor cursortax =dbforloginlogoutReadInvetory.rawQuery(query, null);
						if (cursortax != null) {
							if (cursortax.moveToFirst()) {
								do {
									double taxvalpercent =cursortax.getDouble(cursortax
											.getColumnIndex(DatabaseForDemo.TAX_VALUE));
									System.out.println("taxvalpercent     " + taxvalpercent);
									tax += taxvalpercent;
									System.out.println(" tax    " + tax);
								} while (cursortax.moveToNext());
							}
					}
						cursortax.close();
					}
					tax = (Double.valueOf(pricechargeval) * tax) / 100;
					tax = (Double.valueOf(pricechargeval) +tax);
					pricetaxed.setText(df.format(tax));
					
					String instockval = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.INVENTORY_IN_STOCK));
					stocked.setText(instockval);
					// String vendorval =
					// mCursor.getString(mCursor.getColumnIndex(DatabaseForDemo.INVENTORY_VENDOR));
					// vendorproduct.setSelection(vendorspinnerdata.indexOf(vendorval));
					if (mCursor.isNull(mCursor
							.getColumnIndex(DatabaseForDemo.INVENTORY_TAXONE))) {
					} else {
						String taxval = mCursor
								.getString(mCursor
										.getColumnIndex(DatabaseForDemo.INVENTORY_TAXONE));
						String mystring = taxval;
						String[] a = mystring.split(",");
						System.out.println("the array values are:" + a.length);
						for (int i = 0; i < a.length; i++) {
							String substr = a[i];
							if (substr.equals("Tax1")) {
								tax1check.setChecked(true);
							} else if (substr.equals("Tax2")) {
								tax2check.setChecked(true);
							} else if (substr.equals("Tax3")) {
								tax3check.setChecked(true);
							} else if (substr.equals("BarTax")) {
								bartaxcheck.setChecked(true);
							}
						}
					}
					if (mCursor.isNull(mCursor
							.getColumnIndex(DatabaseForDemo.INVENTORY_NOTES))) {
						notesval = "";
					} else {
						notesval = mCursor
								.getString(mCursor
										.getColumnIndex(DatabaseForDemo.INVENTORY_NOTES));
					}
				} while (mCursor.moveToNext());
			}
		}
		mCursor.close();
		String selectQueryforoptionalinfo = "SELECT  * FROM "
				+ DatabaseForDemo.OPTIONAL_INFO_TABLE + " where "
				+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\"" + itemno + "\"";

		Cursor mCursoroptional =dbforloginlogoutReadInvetory.rawQuery(selectQueryforoptionalinfo, null);
		if (mCursoroptional != null) {
			if (mCursoroptional.moveToFirst()) {
				do {
					if (mCursoroptional
							.isNull(mCursoroptional
									.getColumnIndex(DatabaseForDemo.INVENTORY_MODIFIER_ITEM))) {
						modifieritemval = "no";
					} else {
						modifieritemval = mCursoroptional
								.getString(mCursoroptional
										.getColumnIndex(DatabaseForDemo.INVENTORY_MODIFIER_ITEM));
					}
					if (mCursoroptional
							.isNull(mCursoroptional
									.getColumnIndex(DatabaseForDemo.INVENTORY_COUNT_THIS_ITEM))) {
						countthisitemval = "no";
					} else {
						countthisitemval = mCursoroptional
								.getString(mCursoroptional
										.getColumnIndex(DatabaseForDemo.INVENTORY_COUNT_THIS_ITEM));
					}
					if (mCursoroptional
							.isNull(mCursoroptional
									.getColumnIndex(DatabaseForDemo.INVENTORY_PRINT_ON_RECEIPT))) {
						printonreceiptval = "no";
					} else {
						printonreceiptval = mCursoroptional
								.getString(mCursoroptional
										.getColumnIndex(DatabaseForDemo.INVENTORY_PRINT_ON_RECEIPT));
					}
					if (mCursoroptional
							.isNull(mCursoroptional
									.getColumnIndex(DatabaseForDemo.INVENTORY_ALLOW_BUYBACK))) {
						allowbuybackval = "no";
					} else {
						allowbuybackval = mCursoroptional
								.getString(mCursoroptional
										.getColumnIndex(DatabaseForDemo.INVENTORY_ALLOW_BUYBACK));
					}
					if (mCursoroptional
							.isNull(mCursoroptional
									.getColumnIndex(DatabaseForDemo.INVENTORY_FOODSTAMPABLE))) {
						foodstampableval = "no";
					} else {
						foodstampableval = mCursoroptional
								.getString(mCursoroptional
										.getColumnIndex(DatabaseForDemo.INVENTORY_FOODSTAMPABLE));
					}
					if (mCursoroptional
							.isNull(mCursoroptional
									.getColumnIndex(DatabaseForDemo.INVENTORY_PROMPT_PRICE))) {
						promptpriceval = "no";
					} else {
						promptpriceval = mCursoroptional
								.getString(mCursoroptional
										.getColumnIndex(DatabaseForDemo.INVENTORY_PROMPT_PRICE));
					}
					if (mCursoroptional.isNull(mCursoroptional
							.getColumnIndex(DatabaseForDemo.BONUS_POINTS))) {
						bonuspointsval = "";
					} else {
						bonuspointsval = mCursoroptional
								.getString(mCursoroptional
										.getColumnIndex(DatabaseForDemo.BONUS_POINTS));
					}
					if (mCursoroptional.isNull(mCursoroptional
							.getColumnIndex(DatabaseForDemo.BARCODES))) {
						barcodeval = "";
					} else {
						barcodeval = mCursoroptional.getString(mCursoroptional
								.getColumnIndex(DatabaseForDemo.BARCODES));
					}
					if (mCursoroptional.isNull(mCursoroptional
							.getColumnIndex(DatabaseForDemo.LOCATION))) {
						locationval = "";
					} else {
						locationval = mCursoroptional.getString(mCursoroptional
								.getColumnIndex(DatabaseForDemo.LOCATION));
					}
					if (mCursoroptional.isNull(mCursoroptional
							.getColumnIndex(DatabaseForDemo.UNIT_SIZE))) {
						unitsizeval = "";
					} else {
						unitsizeval = mCursoroptional.getString(mCursoroptional
								.getColumnIndex(DatabaseForDemo.UNIT_SIZE));
					}
					if (mCursoroptional.isNull(mCursoroptional
							.getColumnIndex(DatabaseForDemo.UNIT_TYPE))) {
						unittypeval = "";
					} else {
						unittypeval = mCursoroptional.getString(mCursoroptional
								.getColumnIndex(DatabaseForDemo.UNIT_TYPE));
					}
					if (mCursoroptional
							.isNull(mCursoroptional
									.getColumnIndex(DatabaseForDemo.COMMISSION_OPTIONAL_INFO))) {
						commissionval = "";
					} else {
						commissionval = mCursoroptional
								.getString(mCursoroptional
										.getColumnIndex(DatabaseForDemo.COMMISSION_OPTIONAL_INFO));
					}
				} while (mCursoroptional.moveToNext());
			}
		}
		mCursoroptional.close();
		String selectQueryforalternatesku = "SELECT  * FROM "
				+ DatabaseForDemo.ALTERNATE_SKU_TABLE + " where "
				+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\"" + itemno + "\"";

		Cursor mCursoralternate =dbforloginlogoutReadInvetory.rawQuery(selectQueryforalternatesku, null);
		if (mCursoralternate != null) {
			if (mCursoralternate.moveToFirst()) {
				do {
					skuval = mCursoralternate
							.getString(mCursoralternate
									.getColumnIndex(DatabaseForDemo.ALTERNATE_SKU_VALUE));
					skuarray.add(skuval);
				} while (mCursoralternate.moveToNext());
			}
		}
		mCursoralternate.close();
		String selectQueryformodifier = "SELECT  * FROM "
				+ DatabaseForDemo.MODIFIER_TABLE + " where "
				+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\"" + itemno + "\"";

		Cursor mCursormodifier =dbforloginlogoutReadInvetory.rawQuery(selectQueryformodifier, null);
		if (mCursormodifier != null) {
			if (mCursormodifier.moveToFirst()) {
				do {
					HashMap<String, String> map = new HashMap<String, String>();
					String itemid = mCursormodifier.getString(mCursormodifier
							.getColumnIndex(DatabaseForDemo.MODIFIER_ITEM_NO));
					String itemnamedata = mCursormodifier
							.getString(mCursormodifier
									.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NAME));
					map.put("itemno", itemid);
					map.put("itemname", itemnamedata);
					modifierindividualitemsarray.add(map);
				} while (mCursormodifier.moveToNext());
			}
		}
		mCursormodifier.close();
		String selectQueryfororderinginfo = "SELECT  * FROM "
				+ DatabaseForDemo.ORDERING_INFO_TABLE + " where "
				+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\"" + itemno + "\"";

		Cursor mCursororderinginfo =dbforloginlogoutReadInvetory.rawQuery(selectQueryfororderinginfo,
				null);
		if (mCursororderinginfo != null) {
			if (mCursororderinginfo.moveToFirst()) {
				do {
					HashMap<String, String> map = new HashMap<String, String>();
					String companyname = mCursororderinginfo
							.getString(mCursororderinginfo
									.getColumnIndex(DatabaseForDemo.VENDOR_COMPANY_NAME));
					String costper = mCursororderinginfo
							.getString(mCursororderinginfo
									.getColumnIndex(DatabaseForDemo.COST_PER));
					String casecost = mCursororderinginfo
							.getString(mCursororderinginfo
									.getColumnIndex(DatabaseForDemo.CASE_COST));
					String noincase = mCursororderinginfo
							.getString(mCursororderinginfo
									.getColumnIndex(DatabaseForDemo.NO_IN_CASE));
					String preferred = mCursororderinginfo
							.getString(mCursororderinginfo
									.getColumnIndex(DatabaseForDemo.PREFERRED));
					String partno = mCursororderinginfo
							.getString(mCursororderinginfo
									.getColumnIndex(DatabaseForDemo.VENDERPART_NO));
					map.put("vendorname", companyname);
					map.put("partno", partno);
					map.put("costpercase", costper);
					map.put("casecost", casecost);
					map.put("noincase", noincase);
					map.put("preferred", preferred);
					pricingvendorlistinorderinginfo.add(map);
				} while (mCursororderinginfo.moveToNext());
			}
		}
		mCursororderinginfo.close();
		pricechargeed.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				double taxval = 0;
				String costval = pricechargeed.getText().toString();
				double cost = 0;
				if (costval.equals("")) {
					cost = 0;
				} else {
					cost = Double.valueOf(costval);
					if (tax1check.isChecked()) {

						String query = "select * from "
								+ DatabaseForDemo.TAX_TABLE + " where "
								+ DatabaseForDemo.TAX_NAME + "=\""
								+ tax1check.getText().toString() + "\"";
						System.out.println(query);
						
						Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(query, null);
						if (cursor != null) {
							if (cursor.moveToFirst()) {
								do {
									double taxvalpercent = Double.valueOf(df
											.format(cursor.getDouble(cursor
											.getColumnIndex(DatabaseForDemo.TAX_VALUE))));
									System.out.println(" xxx    " + cursor.getString(cursor
											.getColumnIndex(DatabaseForDemo.TAX_VALUE)));
									System.out.println("     " + taxvalpercent);
									taxval = cost * (taxvalpercent / 100);
								} while (cursor.moveToNext());
							}
							
							
						} else {
							Toast.makeText(getApplicationContext(),
									"No Tax value", Toast.LENGTH_SHORT).show();
						}
						cursor.close();
					} else {
						taxval = taxval + 0;
					}
					if (tax2check.isChecked()) {

						String query = "select " + DatabaseForDemo.TAX_VALUE
								+ " from " + DatabaseForDemo.TAX_TABLE
								+ " where " + DatabaseForDemo.TAX_NAME + "=\""
								+ tax2check.getText().toString() + "\"";
						System.out.println(query);
						
					
						
						Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(query, null);
						if (cursor != null) {
							if (cursor.moveToFirst()) {
								do {
									double taxvalpercent = Double.valueOf(df
											.format(cursor.getDouble(cursor
											.getColumnIndex(DatabaseForDemo.TAX_VALUE))));
									System.out.println("     " + taxvalpercent);
									taxval = taxval
											+ (cost * (taxvalpercent / 100));
								} while (cursor.moveToNext());
							}
							
							
						} else {
							Toast.makeText(getApplicationContext(),
									"No Tax value", Toast.LENGTH_SHORT).show();
						}
					cursor.close();
					} else {
						taxval = taxval + 0;
					}
					if (tax3check.isChecked()) {

						String query = "select " + DatabaseForDemo.TAX_VALUE
								+ " from " + DatabaseForDemo.TAX_TABLE
								+ " where " + DatabaseForDemo.TAX_NAME + "=\""
								+ tax3check.getText().toString() + "\"";
						System.out.println(query);
						
					
						

						Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(query, null);
						if (cursor != null) {
							if (cursor.moveToFirst()) {
								do {
									double taxvalpercent = Double.valueOf(df
											.format(cursor.getDouble(cursor
											.getColumnIndex(DatabaseForDemo.TAX_VALUE))));
									System.out.println("     " + taxvalpercent);
									taxval = taxval
											+ (cost * (taxvalpercent / 100));
								} while (cursor.moveToNext());
							}
							
							
						} else {
							Toast.makeText(getApplicationContext(),
									"No Tax value", Toast.LENGTH_SHORT).show();
						}
						cursor.close();
					} else {
						taxval = taxval + 0;
					}
					if (bartaxcheck.isChecked()) {
						taxval = taxval + 0;
						System.out.println("     " + taxval);
					} else {
						taxval = taxval + 0;
					}

					pricetaxed.setText("" + (taxval + cost));
				}
			}
		});

		optionalinfoed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String itemnumber = itemnoed.getText().toString();
				if (itemnumber.equals("")) {
					Toast.makeText(
							getApplicationContext(),
							"You must enter an item number before saving this item.",
							Toast.LENGTH_SHORT).show();
				} else {

					final Dialog dialog = new Dialog(InventoryActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.optional_info_layout);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);
					final CheckBox modifieritemcheck = (CheckBox) dialog
							.findViewById(R.id.modifieritem);
					final CheckBox countthisitemcheck = (CheckBox) dialog
							.findViewById(R.id.countthisitem);
					final CheckBox printonreceiptcheck = (CheckBox) dialog
							.findViewById(R.id.printonreceipt);
					final CheckBox allowbuybackcheck = (CheckBox) dialog
							.findViewById(R.id.allowbuyback);
					final CheckBox foodstamplescheck = (CheckBox) dialog
							.findViewById(R.id.foodstamples);
					final CheckBox promptpricecheck = (CheckBox) dialog
							.findViewById(R.id.promptprice);
					skuslist = (ListView) dialog
							.findViewById(R.id.alternateskuslist);
					final Button addsku = (Button) dialog
							.findViewById(R.id.addsku);
					final EditText bonuspointsed = (EditText) dialog
							.findViewById(R.id.bonuspoints);
					final EditText barcodesed = (EditText) dialog
							.findViewById(R.id.barcodes);
					final EditText commissioned = (EditText) dialog
							.findViewById(R.id.commissioned);
					final EditText locationed = (EditText) dialog
							.findViewById(R.id.location);
					final EditText unitsizeed = (EditText) dialog
							.findViewById(R.id.unitsize);
					final EditText unittypeed = (EditText) dialog
							.findViewById(R.id.unittype);
					final Spinner commissionspinner = (Spinner) dialog
							.findViewById(R.id.commission);

					bonuspointsed.setText(bonuspointsval);
					barcodesed.setText(barcodeval);
					commissioned.setText(commissionval);
					locationed.setText(locationval);
					unitsizeed.setText(unitsizeval);
					unittypeed.setText(unittypeval);
					commissionspinner.setSelection(commissionspinnerval);
					if (modifieritemval.equals("yes")) {
						modifieritemcheck.setChecked(true);
					} else {
						modifieritemcheck.setChecked(false);
					}
					if (countthisitemval.equals("yes")) {
						countthisitemcheck.setChecked(true);
					} else {
						countthisitemcheck.setChecked(false);
					}
					if (printonreceiptval.equals("yes")) {
						printonreceiptcheck.setChecked(true);
					} else {
						printonreceiptcheck.setChecked(false);
					}
					if (allowbuybackval.equals("yes")) {
						allowbuybackcheck.setChecked(true);
					} else {
						allowbuybackcheck.setChecked(false);
					}
					if (foodstampableval.equals("yes")) {
						foodstamplescheck.setChecked(true);
					} else {
						foodstamplescheck.setChecked(false);
					}
					if (promptpriceval.equals("yes")) {
						promptpricecheck.setChecked(true);
					} else {
						promptpricecheck.setChecked(false);
					}
					if (skuarray != null) {
						SkuArrayAdapter skuadapter = new SkuArrayAdapter(
								getApplicationContext(), skuarray);
						skuadapter.setListener(InventoryActivity.this);
						skuslist.setAdapter(skuadapter);
					}

					addsku.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							System.out.println("add is clicked");
							final AlertDialog alertDialog1 = new AlertDialog.Builder(
									InventoryActivity.this).create();
							LayoutInflater mInflater = LayoutInflater
									.from(InventoryActivity.this);
							View layout = mInflater.inflate(R.layout.add_sku,
									null);
							Button ok = (Button) layout.findViewById(R.id.save);
							Button cancel = (Button) layout
									.findViewById(R.id.cancel);
							final EditText sku = (EditText) layout
									.findViewById(R.id.sku);

							alertDialog1.setTitle("Add SKU");

							ok.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub
									skuval = sku.getText().toString();
									if (skuval.equals("")) {
										Toast.makeText(getApplicationContext(),
												"Please enter sku value",
												Toast.LENGTH_SHORT).show();
									} else {
										if (skuarray.contains(skuval)) {
											Toast.makeText(
													getApplicationContext(),
													"SKU already exist",
													Toast.LENGTH_SHORT).show();
										} else {
											
				
											
													
											String selectQuery = "SELECT  * FROM "
													+ DatabaseForDemo.ALTERNATE_SKU_TABLE
													+ " where "
													+ DatabaseForDemo.ALTERNATE_SKU_VALUE
													+ "=\"" + skuval + "\"";
											Cursor mCursor =dbforloginlogoutReadInvetory.rawQuery(
													selectQuery, null);
											if (mCursor.getCount() > 0) {
												Toast.makeText(
														getApplicationContext(),
														"SKU already exist for an item",
														Toast.LENGTH_SHORT)
														.show();
												
											} else {
												
												skuarray.add(skuval);
												if (skuarray != null) {
													SkuArrayAdapter skuadapter = new SkuArrayAdapter(
															getApplicationContext(),
															skuarray);
													skuadapter
															.setListener(InventoryActivity.this);
													skuslist.setAdapter(skuadapter);
												}
											}
											mCursor.close();
											alertDialog1.dismiss();
										}
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

						}
					});

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							bonuspointsval = bonuspointsed.getText().toString();
							barcodeval = barcodesed.getText().toString();
							commissionval = commissioned.getText().toString();
							locationval = locationed.getText().toString();
							unitsizeval = unitsizeed.getText().toString();
							unittypeval = unittypeed.getText().toString();
							commissionspinnerval = commissionspinner
									.getSelectedItemPosition();
							if (modifieritemcheck.isChecked()) {
								modifieritemval = "yes";
							} else {
								modifieritemval = "no";
							}
							if (countthisitemcheck.isChecked()) {
								countthisitemval = "yes";
							} else {
								countthisitemval = "no";
							}
							if (printonreceiptcheck.isChecked()) {
								printonreceiptval = "yes";
							} else {
								printonreceiptval = "no";
							}
							if (allowbuybackcheck.isChecked()) {
								allowbuybackval = "yes";
							} else {
								allowbuybackval = "no";
							}
							if (foodstamplescheck.isChecked()) {
								foodstampableval = "yes";
							} else {
								foodstampableval = "no";
							}
							if (promptpricecheck.isChecked()) {
								promptpriceval = "yes";
							} else {
								promptpriceval = "no";
							}

							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		notesed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String itemnumber = itemnoed.getText().toString();
				if (itemnumber.equals("")) {
					Toast.makeText(
							getApplicationContext(),
							"You must enter an item number before saving this item.",
							Toast.LENGTH_SHORT).show();
				} else {
					final Dialog dialog = new Dialog(InventoryActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.notes_layout);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);
					final EditText notesedit = (EditText) dialog
							.findViewById(R.id.notestext);
					notesedit.setText(notesval);

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							notesval = notesedit.getText().toString();
							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		modifiersed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String itemnumber = itemnoed.getText().toString();
				if (itemnumber.equals("")) {
					Toast.makeText(
							getApplicationContext(),
							"You must enter an item number before saving this item.",
							Toast.LENGTH_SHORT).show();
				} else {
					final Dialog dialog = new Dialog(InventoryActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.modifiers_layout);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);
					final Spinner modifieritems = (Spinner) dialog
							.findViewById(R.id.modifieritemspinner);
					modifierlist = (ListView) dialog
							.findViewById(R.id.modifieritemslist);
					final ArrayList<String> modifieritemspinnerarray = new ArrayList<String>();
					ArrayList<String> modifieritemspinnerarrayname = new ArrayList<String>();
					modifieritemspinnerarray.add("Select");
					modifieritemspinnerarrayname.add("Select");
					String selectQueryformodifierspinner = "SELECT  "
							+ DatabaseForDemo.INVENTORY_ITEM_NO + " FROM "
							+ DatabaseForDemo.OPTIONAL_INFO_TABLE + " where "
							+ DatabaseForDemo.INVENTORY_MODIFIER_ITEM
							+ "=\"yes\"";
					Cursor mCursorformodifierspinner =dbforloginlogoutReadInvetory.rawQuery(
							selectQueryformodifierspinner, null);
					if (mCursorformodifierspinner != null) {
						if (mCursorformodifierspinner.moveToFirst()) {
							do {
								String itemid = mCursorformodifierspinner.getString(mCursorformodifierspinner
										.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NO));
								modifieritemspinnerarray.add(itemid);
							} while (mCursorformodifierspinner.moveToNext());
						}
					}
					mCursorformodifierspinner.close();
					for(int kk=0;kk<modifieritemspinnerarray.size();kk++){
					String modifierspinnerforname = "SELECT  "
							+ DatabaseForDemo.INVENTORY_ITEM_NAME+ " FROM "
							+ DatabaseForDemo.INVENTORY_TABLE + " where "
							+ DatabaseForDemo.INVENTORY_ITEM_NO
							+ "=\""+modifieritemspinnerarray.get(kk)+"\"";
					Cursor cursorforname =dbforloginlogoutReadInvetory.rawQuery(
							modifierspinnerforname, null);
					if (cursorforname != null) {
						if (cursorforname.moveToFirst()) {
								String itemname = cursorforname.getString(cursorforname
										.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NAME));
								modifieritemspinnerarrayname.add(itemname);
						}
					}
					}
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							InventoryActivity.this,
							android.R.layout.simple_spinner_item,
							modifieritemspinnerarrayname);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
					modifieritems.setAdapter(adapter);

					if (modifierindividualitemsarray != null) {
						ModifierIndividualItemAdapter adapterformod = new ModifierIndividualItemAdapter(
								InventoryActivity.this,
								modifierindividualitemsarray);
						adapterformod.setListener(InventoryActivity.this);
						modifierlist.setAdapter(adapterformod);
					}

					modifieritems
							.setOnItemSelectedListener(new OnItemSelectedListener() {

								@Override
								public void onItemSelected(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									// TODO Auto-generated method stub
//									int itemnoinspinner = modifieritems.getSelectedItemPosition();
									try{
									Log.v("lesngh", ""+modifieritems.getSelectedItemPosition());
									String itemnoinspinner=modifieritemspinnerarray.get(modifieritems.getSelectedItemPosition());
									if (itemnoinspinner.equals("")
											|| itemnoinspinner.equals("Select")) {

									} else {
										System.out.println("else is executed");
										for (int i = 0; i < modifierindividualitemsarray
												.size(); i++) {
											if (modifierindividualitemsarray
													.get(i).containsValue(
															itemnoinspinner)) {
												Toast.makeText(
														InventoryActivity.this,
														"Modifier already exist",
														Toast.LENGTH_SHORT)
														.show();
												System.out
														.println("same is found is executed");
												status = false;
												break;
											}
										}
										if (status == true) {
											String selectQuery = "SELECT  "
													+ DatabaseForDemo.INVENTORY_ITEM_NO
													+ ", "
													+ DatabaseForDemo.INVENTORY_ITEM_NAME
													+ " FROM "
													+ DatabaseForDemo.INVENTORY_TABLE
													+ " where "
													+ DatabaseForDemo.INVENTORY_ITEM_NO
													+ "=\"" + itemnoinspinner
													+ "\"";
											Cursor mCursor =dbforloginlogoutReadInvetory.rawQuery(
													selectQuery, null);
											if (mCursor != null) {
												if (mCursor.moveToFirst()) {
													do {
														HashMap<String, String> map = new HashMap<String, String>();
														String itemid, itemname;
														itemid = mCursor
																.getString(mCursor
																		.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NO));
														itemname = mCursor
																.getString(mCursor
																		.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NAME));
														map.put("itemno",
																itemid);
														map.put("itemname",
																itemname);
														modifierindividualitemsarray
																.add(map);
													} while (mCursor
															.moveToNext());
												}
												
												
											}
											mCursor.close();
											if (modifierindividualitemsarray != null) {
												ModifierIndividualItemAdapter adapterformod = new ModifierIndividualItemAdapter(
														InventoryActivity.this,
														modifierindividualitemsarray);
												adapterformod
														.setListener(InventoryActivity.this);
												modifierlist
														.setAdapter(adapterformod);
											}
										}
									}
									status = true;
									}catch (Exception e) {
										// TODO: handle exception
										e.printStackTrace();
									}
								}

								@Override
								public void onNothingSelected(
										AdapterView<?> arg0) {
									// TODO Auto-generated method stub

								}
							});

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		orderinginfoed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String itemnumber = itemnoed.getText().toString();
				if (itemnumber.equals("")) {
					Toast.makeText(
							getApplicationContext(),
							"You must enter an item number before saving this item.",
							Toast.LENGTH_SHORT).show();
				} else {
					final Dialog dialog = new Dialog(InventoryActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.ordering_info_layout);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);
					final Spinner vendortotaldetails = (Spinner) dialog
							.findViewById(R.id.vendorinorderinginfo);
					vendorlist = (ListView) dialog
							.findViewById(R.id.orderinginfolist);
					ArrayList<String> vendorspinnerdatafororderinginfo = new ArrayList<String>();
					vendorspinnerdatafororderinginfo.clear();
					vendorspinnerdatafororderinginfo.add("Select");

					
					Cursor mCursor = dbforloginlogoutReadInvetory.rawQuery(
							"select " + DatabaseForDemo.VENDOR_COMPANY_NAME
									+ " from " + DatabaseForDemo.VENDOR_TABLE,
							null);
					System.out.println(mCursor);
					if (mCursor != null) {
						if (mCursor.moveToFirst()) {
							do {
								String catid = mCursor.getString(mCursor
										.getColumnIndex(DatabaseForDemo.VENDOR_COMPANY_NAME));
								vendorspinnerdatafororderinginfo.add(catid);
							} while (mCursor.moveToNext());
						}
						
						
					}
					mCursor.close();
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							InventoryActivity.this,
							android.R.layout.simple_spinner_item,
							vendorspinnerdatafororderinginfo);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
					vendortotaldetails.setAdapter(adapter);

					if (pricingvendorlistinorderinginfo != null) {
						OrderingInfoIndividualVendorAdapter adapterformod = new OrderingInfoIndividualVendorAdapter(
								InventoryActivity.this,
								pricingvendorlistinorderinginfo);
						adapterformod.setListener(InventoryActivity.this);
						vendorlist.setAdapter(adapterformod);
					}

					vendortotaldetails
							.setOnItemSelectedListener(new OnItemSelectedListener() {

								@Override
								public void onItemSelected(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									// TODO Auto-generated method stub
									final String vendorinspinner = vendortotaldetails
											.getSelectedItem().toString();
									if (vendorinspinner.equals("")
											|| vendorinspinner.equals("Select")) {

									} else {
										System.out.println("else is executed");
										for (int i = 0; i < pricingvendorlistinorderinginfo
												.size(); i++) {
											if (pricingvendorlistinorderinginfo
													.get(i).containsValue(
															vendorinspinner)) {
												Toast.makeText(
														InventoryActivity.this,
														"Vendor already exist",
														Toast.LENGTH_SHORT)
														.show();
												System.out
														.println("same is found is executed");
												statusforvendor = false;
												break;
											}
										}
										if (statusforvendor == true) {
											System.out
													.println("different is found");

											final AlertDialog alertDialog1 = new AlertDialog.Builder(
													InventoryActivity.this)
													.create();
											LayoutInflater mInflater = LayoutInflater
													.from(InventoryActivity.this);
											View layout = mInflater
													.inflate(
															R.layout.orderinginfopopups,
															null);
											Button ok = (Button) layout
													.findViewById(R.id.save);
											Button cancel = (Button) layout
													.findViewById(R.id.cancel);
											final EditText partno = (EditText) layout
													.findViewById(R.id.partno);
											final EditText costperase = (EditText) layout
													.findViewById(R.id.cost_case);
											final EditText casecost = (EditText) layout
													.findViewById(R.id.case_cost);
											final EditText noincase = (EditText) layout
													.findViewById(R.id.num_in_case);
											final RadioButton trueval = (RadioButton) layout
													.findViewById(R.id.radiobuttonyes);
											final RadioButton falseval = (RadioButton) layout
													.findViewById(R.id.radiobuttonno);
											noincase.setOnEditorActionListener(new OnEditorActionListener() {
												@Override
												public boolean onEditorAction(TextView v, int actionId,
														KeyEvent event) {
													if (actionId == EditorInfo.IME_ACTION_DONE) {
														InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
														imm.hideSoftInputFromWindow(noincase.getWindowToken(), 0);
													}
													return false;
												}
											});
											alertDialog1
													.setTitle("Add Vendor details");

											ok.setOnClickListener(new OnClickListener() {

												@Override
												public void onClick(View arg0) {
													// TODO Auto-generated
													// method stub
													String prferredtextval = "";
													if (trueval.isChecked()) {
														System.out
																.println("preferred val is true");
														prferredtextval = "true";
													}
													if (falseval.isChecked()) {
														System.out
																.println("preferred val is false");
														prferredtextval = "false";
													}
													if (prferredtextval
															.equals("true")) {
														int positionval = 0;
														for (int i = 0; i < pricingvendorlistinorderinginfo
																.size(); i++) {
															if (pricingvendorlistinorderinginfo
																	.get(i)
																	.containsValue(
																			"true")) {
																Toast.makeText(
																		InventoryActivity.this,
																		"true already exist",
																		Toast.LENGTH_SHORT)
																		.show();
																System.out
																		.println("same is found is executed");
																statusfortrue = false;
																positionval = i;
																System.out
																		.println("position val is:"
																				+ positionval);
																break;
															}
														}
														if (statusfortrue == true) {
															System.out
																	.println("true is not there");
															HashMap<String, String> map = new HashMap<String, String>();
															map.put("vendorname",
																	vendorinspinner);
															map.put("partno",
																	partno.getText()
																			.toString());
															map.put("costpercase",
																	costperase
																			.getText()
																			.toString());
															map.put("casecost",
																	casecost.getText()
																			.toString());
															map.put("noincase",
																	noincase.getText()
																			.toString());
															map.put("preferred",
																	prferredtextval);
															pricingvendorlistinorderinginfo
																	.add(map);
														} else {
															System.out
																	.println("true is there");
															pricingvendorlistinorderinginfo
																	.get(positionval)
																	.put("preferred",
																			"false");
															System.out
																	.println("prferred value is changed");
															HashMap<String, String> map = new HashMap<String, String>();
															map.put("vendorname",
																	vendorinspinner);
															map.put("partno",
																	partno.getText()
																			.toString());
															map.put("costpercase",
																	costperase
																			.getText()
																			.toString());
															map.put("casecost",
																	casecost.getText()
																			.toString());
															map.put("noincase",
																	noincase.getText()
																			.toString());
															map.put("preferred",
																	prferredtextval);
															pricingvendorlistinorderinginfo
																	.add(map);
														}
													} else {
														System.out
																.println("preferred val is false");
														HashMap<String, String> map = new HashMap<String, String>();
														map.put("vendorname",
																vendorinspinner);
														map.put("partno",
																partno.getText()
																		.toString());
														map.put("costpercase",
																costperase
																		.getText()
																		.toString());
														map.put("casecost",
																casecost.getText()
																		.toString());
														map.put("noincase",
																noincase.getText()
																		.toString());
														map.put("preferred",
																prferredtextval);
														pricingvendorlistinorderinginfo
																.add(map);
													}

													if (pricingvendorlistinorderinginfo != null) {
														OrderingInfoIndividualVendorAdapter adapterformod = new OrderingInfoIndividualVendorAdapter(
																InventoryActivity.this,
																pricingvendorlistinorderinginfo);
														adapterformod
																.setListener(InventoryActivity.this);
														vendorlist
																.setAdapter(adapterformod);
													}
													statusfortrue = true;
													alertDialog1.dismiss();
												}
											});
											cancel.setOnClickListener(new OnClickListener() {

												@Override
												public void onClick(View arg0) {
													// TODO Auto-generated
													// method stub
													alertDialog1.dismiss();
												}
											});
											alertDialog1.setView(layout);
											alertDialog1.show();
										}
									}
									statusforvendor = true;
								}

								@Override
								public void onNothingSelected(
										AdapterView<?> arg0) {
									// TODO Auto-generated method stub

								}
							});

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		saleshistoryed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String itemnumber = itemnoed.getText().toString();
				if (itemnumber.equals("")) {
					Toast.makeText(
							getApplicationContext(),
							"You must enter an item number before saving this item.",
							Toast.LENGTH_SHORT).show();
				} else {
					final Dialog dialog = new Dialog(InventoryActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.sales_history_layout);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);

					saleslist = (ListView) dialog
							.findViewById(R.id.pricelevelslist);

					ArrayList<String> dateandtimelist = new ArrayList<String>();
					ArrayList<String> storeidlist = new ArrayList<String>();
					ArrayList<String> invoicelist = new ArrayList<String>();
					ArrayList<String> quantitylist = new ArrayList<String>();
					ArrayList<String> pricelist = new ArrayList<String>();
					ArrayList<String> costlist = new ArrayList<String>();
					ArrayList<String> custlist = new ArrayList<String>();
					ArrayList<String> seriallist = new ArrayList<String>();

					
		
					

					String query = "SELECT *from "
							+ DatabaseForDemo.INVOICE_ITEMS_TABLE + " where "
							+ DatabaseForDemo.INVOICE_ITEM_ID + "=\""
							+ itemnumber + "\"";
					Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(query, null);
					int i = 1;
					if (cursor.getCount() > 0) {
						if (cursor != null) {
							if (cursor.moveToFirst()) {
								do {
									if (cursor.isNull(cursor
											.getColumnIndex(DatabaseForDemo.CREATED_DATE))) {
										dateandtimelist.add("");
									} else {
										String dateandtime = cursor.getString(cursor
												.getColumnIndex(DatabaseForDemo.CREATED_DATE));
										dateandtimelist.add(dateandtime);
									}
									if (cursor.isNull(cursor
											.getColumnIndex(DatabaseForDemo.INVOICE_STORE_ID))) {
										storeidlist.add("");
									} else {
										String storeid = cursor.getString(cursor
												.getColumnIndex(DatabaseForDemo.INVOICE_STORE_ID));
										storeidlist.add(storeid);
									}
									if (cursor.isNull(cursor
											.getColumnIndex(DatabaseForDemo.INVOICE_ID))) {
										invoicelist.add("");
									} else {
										String invoiceid = cursor.getString(cursor
												.getColumnIndex(DatabaseForDemo.INVOICE_ID));
										invoicelist.add(invoiceid);
									}
									if (cursor.isNull(cursor
											.getColumnIndex(DatabaseForDemo.INVOICE_QUANTITY))) {
										quantitylist.add("");
									} else {
										String qtylist = cursor.getString(cursor
												.getColumnIndex(DatabaseForDemo.INVOICE_QUANTITY));
										quantitylist.add(qtylist);
									}
									if (cursor.isNull(cursor
											.getColumnIndex(DatabaseForDemo.INVOICE_YOUR_COST))) {
										pricelist.add("");
									} else {
										String price = cursor.getString(cursor
												.getColumnIndex(DatabaseForDemo.INVOICE_YOUR_COST));
										pricelist.add(price);
									}
									if (cursor.isNull(cursor
											.getColumnIndex(DatabaseForDemo.INVOICE_AVG_COST))) {
										costlist.add("");
									} else {
										String cost = cursor.getString(cursor
												.getColumnIndex(DatabaseForDemo.INVOICE_AVG_COST));
										costlist.add(cost);
									}
									seriallist.add("" + i);
									i++;
								} while (cursor.moveToNext());
							}
						}
					}
					cursor.close();
					for (int ii = 0; ii < invoicelist.size(); ii++) {
						String selectQueryforshipping = "SELECT  * FROM "
								+ DatabaseForDemo.INVOICE_TOTAL_TABLE
								+ " where " + DatabaseForDemo.INVOICE_ID + "=\""
								+ invoicelist.get(ii) + "\"";

						Cursor mCursorforshipping =dbforloginlogoutReadInvetory.rawQuery(
								selectQueryforshipping, null);

						if (mCursorforshipping.getCount() > 0) {
							if (mCursorforshipping != null) {
								if (mCursorforshipping.moveToFirst()) {
									do {
										if (mCursorforshipping.isNull(mCursorforshipping
												.getColumnIndex(DatabaseForDemo.INVOICE_CUSTOMER))) {
											custlist.add("");
										} else {
											String address = mCursorforshipping
													.getString(mCursorforshipping
															.getColumnIndex(DatabaseForDemo.INVOICE_CUSTOMER));
											custlist.add(address);
										}
									} while (mCursorforshipping.moveToNext());
								}
							}
						}
						mCursorforshipping.close();
					}

					ImageAdapterForSalesHistory adapter = new ImageAdapterForSalesHistory(
							InventoryActivity.this, dateandtimelist,
							storeidlist, invoicelist, quantitylist, pricelist,
							costlist, custlist, seriallist);
					saleslist.setAdapter(adapter);

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		printersed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String itemnumber = itemnoed.getText().toString();
				if (itemnumber.equals("")) {
					Toast.makeText(
							getApplicationContext(),
							"You must enter an item number before saving this item.",
							Toast.LENGTH_SHORT).show();
				} else {
					final Dialog dialog = new Dialog(InventoryActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.printers_layout);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try{
				final String itemno = itemnoed.getText().toString();
				String taxvalforsave = "";
				String costval = pricechargeed.getText().toString();
				String pricetaxval = pricetaxed.getText().toString();
				double taxval = 0;
				if (tax1check.isChecked()) {
					taxvalforsave = taxvalforsave
							+ tax1check.getText().toString() + ",";
					double cost = Double.valueOf(costval);

					String query = "select * from " + DatabaseForDemo.TAX_TABLE
							+ " where " + DatabaseForDemo.TAX_NAME + "=\""
							+ tax1check.getText().toString() + "\"";
					System.out.println(query);
					
				
					

					Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(query, null);
					if (cursor != null) {
						if (cursor.moveToFirst()) {
							do {
								double taxvalpercent = Double.valueOf(df
										.format(cursor.getDouble(cursor
										.getColumnIndex(DatabaseForDemo.TAX_VALUE))));
								System.out.println("     " + taxvalpercent);
								taxval = cost * (taxvalpercent / 100);
							} while (cursor.moveToNext());
						}
						
						
					} else {
						Toast.makeText(getApplicationContext(), "No Tax value",
								Toast.LENGTH_SHORT).show();
					}
					cursor.close();
				}
				if (tax2check.isChecked()) {
					taxvalforsave = taxvalforsave
							+ tax2check.getText().toString() + ",";
					double cost = Double.valueOf(costval);

					String query = "select " + DatabaseForDemo.TAX_VALUE
							+ " from " + DatabaseForDemo.TAX_TABLE + " where "
							+ DatabaseForDemo.TAX_NAME + "=\""
							+ tax2check.getText().toString() + "\"";
					System.out.println(query);
					
				
					
					Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(query, null);
					if (cursor != null) {
						if (cursor.moveToFirst()) {
							do {
								double taxvalpercent = Double.valueOf(df
										.format(cursor.getDouble(cursor
										.getColumnIndex(DatabaseForDemo.TAX_VALUE))));
								System.out.println("     " + taxvalpercent);
								taxval = taxval
										+ (cost * (taxvalpercent / 100));
							} while (cursor.moveToNext());
						}
						
						
					} else {
						Toast.makeText(getApplicationContext(), "No Tax value",
								Toast.LENGTH_SHORT).show();
					}
					cursor.close();
				}
				if (tax3check.isChecked()) {
					taxvalforsave = taxvalforsave
							+ tax3check.getText().toString() + ",";
					double cost = Double.valueOf(costval);

					String query = "select " + DatabaseForDemo.TAX_VALUE
							+ " from " + DatabaseForDemo.TAX_TABLE + " where "
							+ DatabaseForDemo.TAX_NAME + "=\""
							+ tax3check.getText().toString() + "\"";
					System.out.println(query);
					
				
					

					Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(query, null);
					if (cursor != null) {
						if (cursor.moveToFirst()) {
							do {
								double taxvalpercent = Double.valueOf(df
										.format(cursor.getDouble(cursor
										.getColumnIndex(DatabaseForDemo.TAX_VALUE))));
								System.out.println("     " + taxvalpercent);
								taxval = taxval
										+ (cost * (taxvalpercent / 100));
							} while (cursor.moveToNext());
						}
						
						
					} else {
						Toast.makeText(getApplicationContext(), "No Tax value",
								Toast.LENGTH_SHORT).show();
					}
					cursor.close();
				}
				if (bartaxcheck.isChecked()) {
					taxvalforsave = taxvalforsave
							+ bartaxcheck.getText().toString();
					taxval = taxval + 0;
					System.out.println("     " + taxval);
				}
				System.out.println(taxvalforsave);
				System.out.println("taxvalue is padma padma:" + taxval);
				
				if(costval.equals(pricetaxval)){
					Double tpricetaxval = Double.parseDouble(pricetaxval)+taxval;
					pricetaxval = tpricetaxval.toString();
					System.out.println("pricetax in if:"+pricetaxval);
				}else{
					System.out.println("pricetax in else:"+pricetaxval);
				}

				if (departmentforproduct.getSelectedItem().toString()
						.equals("")
						|| itemnoed.getText().toString().equals("")
						|| itemnameed.getText().toString().equals("")
						|| desc2ed.getText().toString().equals("")
						|| costed.getText().toString().equals("")
						|| pricechargeed.getText().toString().equals("")
						|| pricetaxed.getText().toString().equals("")
						|| stocked.getText().toString().equals("")) {
					Toast.makeText(InventoryActivity.this,
							"Please enter details", Toast.LENGTH_SHORT).show();
				} else {
					
		
					
							
					dbforloginlogoutWriteInvetory.execSQL("update "
							+ DatabaseForDemo.INVENTORY_TABLE + " set "
							+ DatabaseForDemo.CREATED_DATE + "=\""
							+ Parameters.currentTime() + "\", "
							+ DatabaseForDemo.MODIFIED_DATE + "=\""
							+ Parameters.currentTime() + "\", "
							+ DatabaseForDemo.MODIFIED_IN + "=\"" + "Local"
							+ "\", " + DatabaseForDemo.INVENTORY_DEPARTMENT
							+ "=\""
							+ departmentforproduct.getSelectedItem().toString()
							+ "\", " + DatabaseForDemo.INVENTORY_ITEM_NO + "=\""
							+ itemnoed.getText().toString() + "\", "
							+ DatabaseForDemo.INVENTORY_ITEM_NAME + "=\""
							+ itemnameed.getText().toString() + "\", "
							+ DatabaseForDemo.INVENTORY_SECOND_DESCRIPTION
							+ "=\"" + desc2ed.getText().toString() + "\", "
							+ DatabaseForDemo.INVENTORY_AVG_COST + "=\""
							+ costed.getText().toString() + "\", "
							+ DatabaseForDemo.INVENTORY_PRICE_CHANGE+ "=\""+ pricechargeed.getText().toString()+ "\", "
							+ DatabaseForDemo.INVENTORY_PRICE_TAX+ "=\""+ pricetaxval+ "\", "
							+ DatabaseForDemo.INVENTORY_IN_STOCK
							+ "=\""
							+ stocked.getText().toString()
							 + "\", "+DatabaseForDemo.INVENTORY_VENDOR+"=\"" +
							 "vendor"
							+ "\", " + DatabaseForDemo.INVENTORY_TAXONE + "=\""
							+ taxvalforsave
							+ "\", " + DatabaseForDemo.INVENTORY_QUANTITY + "=\""
							+ "1" + "\", "
							+ DatabaseForDemo.INVENTORY_TOTAL_TAX + "=\""
							+ taxval + "\", " + DatabaseForDemo.INVENTORY_NOTES
							+ "=\"" + notesval + "\" where "
							+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\"" + itemno
							+ "\"");
					String query1 = "select " + DatabaseForDemo.CREATED_DATE
							+ " from " + DatabaseForDemo.OPTIONAL_INFO_TABLE + " where "
							+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\""
							+ itemno + "\"";
					Cursor cursordd =dbforloginlogoutReadInvetory.rawQuery(query1, null);
					if(cursordd!=null &&cursordd.getCount()>0){
					dbforloginlogoutWriteInvetory.execSQL("update "
							+ DatabaseForDemo.OPTIONAL_INFO_TABLE + " set "
							+ DatabaseForDemo.CREATED_DATE + "=\""
							+ Parameters.currentTime() + "\", "
							+ DatabaseForDemo.MODIFIED_DATE + "=\""
							+ Parameters.currentTime() + "\", "
							+ DatabaseForDemo.MODIFIED_IN + "=\"" + "Local"
							+ "\", " + DatabaseForDemo.INVENTORY_ITEM_NO + "=\""
							+ itemnoed.getText().toString() + "\", "
							+ DatabaseForDemo.INVENTORY_MODIFIER_ITEM + "=\""
							+ modifieritemval + "\", "
							+ DatabaseForDemo.INVENTORY_ALLOW_BUYBACK + "=\""
							+ allowbuybackval + "\", "
							+ DatabaseForDemo.INVENTORY_COUNT_THIS_ITEM + "=\""
							+ countthisitemval + "\", "
							+ DatabaseForDemo.INVENTORY_FOODSTAMPABLE + "=\""
							+ foodstampableval + "\", "
							+ DatabaseForDemo.INVENTORY_PROMPT_PRICE + "=\""
							+ promptpriceval + "\", "
							+ DatabaseForDemo.INVENTORY_PRINT_ON_RECEIPT + "=\""
							+ printonreceiptval + "\", "
							+ DatabaseForDemo.BARCODES + "=\"" + barcodeval
							+ "\", " + DatabaseForDemo.BONUS_POINTS + "=\""
							+ bonuspointsval + "\", "
							+ DatabaseForDemo.COMMISSION_OPTIONAL_INFO + "=\""
							+ commissionval + "\", " + DatabaseForDemo.LOCATION
							+ "=\"" + locationval + "\", "
							+ DatabaseForDemo.UNIT_SIZE + "=\"" + unitsizeval
							+ "\", " + DatabaseForDemo.UNIT_TYPE + "=\""
							+ unittypeval + "\" where "
							+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\"" + itemno
							+ "\"");
					}else{
					//harinath
					optional_info = new ContentValues();
					optional_info.put(
							DatabaseForDemo.UNIQUE_ID,
							Parameters.randomValue());
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
							bonuspointsval);
					optional_info.put(DatabaseForDemo.BARCODES,
							barcodeval);
					optional_info.put(DatabaseForDemo.LOCATION,
							locationval);
					optional_info.put(
							DatabaseForDemo.UNIT_SIZE,
							unitsizeval);
					optional_info.put(
							DatabaseForDemo.UNIT_TYPE,
							unittypeval);
					optional_info
							.put(DatabaseForDemo.COMMISSION_OPTIONAL_INFO,
									commissionval);
					optional_info
							.put(DatabaseForDemo.INVENTORY_ALLOW_BUYBACK,
									allowbuybackval);
					optional_info
							.put(DatabaseForDemo.INVENTORY_PROMPT_PRICE,
									promptpriceval);
					optional_info
							.put(DatabaseForDemo.INVENTORY_FOODSTAMPABLE,
									foodstampableval);
					optional_info
							.put(DatabaseForDemo.INVENTORY_PRINT_ON_RECEIPT,
									printonreceiptval);
					optional_info
							.put(DatabaseForDemo.INVENTORY_COUNT_THIS_ITEM,
									countthisitemval);
					optional_info
							.put(DatabaseForDemo.INVENTORY_MODIFIER_ITEM,
									modifieritemval);
					optional_info.put(
							DatabaseForDemo.INVENTORY_ITEM_NO,
							itemno);
					dbforloginlogoutWriteInvetory.insert(
							DatabaseForDemo.OPTIONAL_INFO_TABLE,
							null, optional_info);
					//harinath
					}
					
Log.v("modifieritemval",""+modifieritemval);
					String select = "select " + DatabaseForDemo.UNIQUE_ID
							+ " from " + DatabaseForDemo.INVENTORY_TABLE
							+ " where " + DatabaseForDemo.INVENTORY_ITEM_NO
							+ "=\"" + itemno + "\"";
					Cursor selectcursor = dbforloginlogoutWriteInvetory.rawQuery(select, null);
					if (selectcursor.getCount() > 0) {
						if (selectcursor != null) {
							if (selectcursor.moveToFirst()) {
								do {
									uniqueidforprodedit = selectcursor.getString(selectcursor
											.getColumnIndex(DatabaseForDemo.UNIQUE_ID));
								} while (selectcursor.moveToNext());
							}
							
						}
					} else {

					}
					selectcursor.close();
					String whereforalternatesku = DatabaseForDemo.INVENTORY_ITEM_NO
							+ "=?";
					dbforloginlogoutWriteInvetory.delete(DatabaseForDemo.ALTERNATE_SKU_TABLE,
							whereforalternatesku, new String[] { itemno });

					for (int i = 0; i < skuarray.size(); i++) {
						ContentValues alternate_sku = new ContentValues();
						alternate_sku.put(DatabaseForDemo.UNIQUE_ID,
								uniqueidforprodedit);
						alternate_sku.put(DatabaseForDemo.CREATED_DATE,
								Parameters.currentTime());
						alternate_sku.put(DatabaseForDemo.MODIFIED_DATE,
								Parameters.currentTime());
						alternate_sku.put(DatabaseForDemo.MODIFIED_IN, "Local");
						alternate_sku.put(DatabaseForDemo.ALTERNATE_SKU_VALUE,
								skuarray.get(i));
						alternate_sku.put(DatabaseForDemo.INVENTORY_ITEM_NO,
								itemnoed.getText().toString());
						dbforloginlogoutWriteInvetory.insert(
								DatabaseForDemo.ALTERNATE_SKU_TABLE, null,
								alternate_sku);
					}

					String whereformodifier = DatabaseForDemo.INVENTORY_ITEM_NO
							+ "=?";
					dbforloginlogoutWriteInvetory.delete(DatabaseForDemo.MODIFIER_TABLE,
							whereformodifier, new String[] { itemno });

					/*for (int i = 0; i < modifierindividualitemsarray.size(); i++) {
						ContentValues modifier_value = new ContentValues();
						modifier_value.put(DatabaseForDemo.UNIQUE_ID,
								uniqueidforprodedit);
						modifier_value.put(DatabaseForDemo.CREATED_DATE,
								Parameters.currentTime());
						modifier_value.put(DatabaseForDemo.MODIFIED_DATE,
								Parameters.currentTime());
						modifier_value
								.put(DatabaseForDemo.MODIFIED_IN, "Local");
						modifier_value.put(
								DatabaseForDemo.MODIFIER_ITEM_NO,
								modifierindividualitemsarray.get(i).get(
										"itemno"));
						modifier_value.put(
								DatabaseForDemo.INVENTORY_ITEM_NAME,
								modifierindividualitemsarray.get(i).get(
										"itemname"));
						modifier_value.put(DatabaseForDemo.INVENTORY_ITEM_NO,
								itemnoed.getText().toString());
						dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.MODIFIER_TABLE,
								null, modifier_value);
					}*/
					modifiersArrayserver.clear();
					for (int i = 0; i < modifierindividualitemsarray.size(); i++) {
						String rrrr=Parameters.randomValue();
						ContentValues modifier_value = new ContentValues();
						modifier_value.put(DatabaseForDemo.UNIQUE_ID,
								rrrr);
						modifier_value.put(DatabaseForDemo.CREATED_DATE,
								Parameters.currentTime());
						modifier_value.put(DatabaseForDemo.MODIFIED_DATE,
								Parameters.currentTime());
						modifier_value.put(DatabaseForDemo.MODIFIED_IN,
								"Local");
						modifier_value.put(
								DatabaseForDemo.MODIFIER_ITEM_NO,
								modifierindividualitemsarray.get(i).get(
										"itemno"));
						Log.e("itemno", ""+modifierindividualitemsarray.get(i).get(
								"itemno"));
						modifier_value.put(
								DatabaseForDemo.INVENTORY_ITEM_NAME,
								modifierindividualitemsarray.get(i).get(
										"itemname"));
						Log.e("itemname", ""+modifierindividualitemsarray.get(i).get(
								"itemname"));
						modifier_value.put(
								DatabaseForDemo.INVENTORY_ITEM_NO,
								itemnoed.getText().toString());
						Log.e("itemname", ""+itemnoed.getText().toString());
						dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.MODIFIER_TABLE, null,
								modifier_value);
						try {
							JSONObject data = new JSONObject();
							JSONObject jsonobj = new JSONObject();
							JSONArray fields = new JSONArray();
								jsonobj.put(DatabaseForDemo.UNIQUE_ID,
										rrrr);
								jsonobj.put(DatabaseForDemo.CREATED_DATE,
										Parameters.currentTime());
								jsonobj.put(DatabaseForDemo.MODIFIED_DATE,
										Parameters.currentTime());
								jsonobj
										.put(DatabaseForDemo.MODIFIED_IN, "Local");
								jsonobj.put(
										DatabaseForDemo.MODIFIER_ITEM_NO,
										modifierindividualitemsarray.get(i).get(
												"itemno"));
								jsonobj.put(
										DatabaseForDemo.INVENTORY_ITEM_NAME,
										modifierindividualitemsarray.get(i).get(
												"itemname"));
								jsonobj.put(
										DatabaseForDemo.INVENTORY_ITEM_NO,
										itemnoed.getText().toString());
								
								Log.e("nn srihari "+modifierindividualitemsarray.get(i)
										.get("itemname"),"id "+modifierindividualitemsarray.get(i)
										.get("itemno"));
								fields.put(0, jsonobj);
							data.put("fields", fields);
							datavalforproduct3 = data.toString();
							modifiersArrayserver.add(datavalforproduct3);
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
					}
					
					
					String wherefororderinginfo = DatabaseForDemo.INVENTORY_ITEM_NO
							+ "=?";
					dbforloginlogoutWriteInvetory.delete(DatabaseForDemo.ORDERING_INFO_TABLE,
							wherefororderinginfo, new String[] { itemno });

					for (int i = 0; i < pricingvendorlistinorderinginfo.size(); i++) {
						ContentValues ordering_info = new ContentValues();
						ordering_info.put(DatabaseForDemo.UNIQUE_ID,
								uniqueidforprodedit);
						ordering_info.put(DatabaseForDemo.CREATED_DATE,
								Parameters.currentTime());
						ordering_info.put(DatabaseForDemo.MODIFIED_DATE,
								Parameters.currentTime());
						ordering_info.put(DatabaseForDemo.MODIFIED_IN, "Local");
						ordering_info.put(
								DatabaseForDemo.VENDOR_COMPANY_NAME,
								pricingvendorlistinorderinginfo.get(i).get(
										"vendorname"));
						ordering_info.put(
								DatabaseForDemo.VENDERPART_NO,
								pricingvendorlistinorderinginfo.get(i).get(
										"partno"));
						ordering_info.put(
								DatabaseForDemo.COST_PER,
								pricingvendorlistinorderinginfo.get(i).get(
										"costperase"));
						ordering_info.put(
								DatabaseForDemo.CASE_COST,
								pricingvendorlistinorderinginfo.get(i).get(
										"casecost"));
						ordering_info.put(
								DatabaseForDemo.NO_IN_CASE,
								pricingvendorlistinorderinginfo.get(i).get(
										"noincase"));
						ordering_info.put(
								DatabaseForDemo.PREFERRED,
								pricingvendorlistinorderinginfo.get(i).get(
										"preferred"));
						ordering_info.put(DatabaseForDemo.INVENTORY_ITEM_NO,
								itemnoed.getText().toString());
						dbforloginlogoutWriteInvetory.insert(
								DatabaseForDemo.ORDERING_INFO_TABLE, null,
								ordering_info);
					}

					Toast.makeText(InventoryActivity.this,
							"Product saved successfully", Toast.LENGTH_SHORT)
							.show();

					final ArrayList<JSONObject> getlistformain = new ArrayList<JSONObject>();
					String selectQueryforinstantpo = "SELECT  * FROM "
							+ DatabaseForDemo.INVENTORY_TABLE + " where "
							+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\"" + itemno
							+ "\"";

					Cursor mCursorforvendor1 = dbforloginlogoutWriteInvetory.rawQuery(
							selectQueryforinstantpo, null);

					if (mCursorforvendor1 != null) {
						if (mCursorforvendor1.moveToFirst()) {
							do {
								try {
									JSONObject jsonobj = new JSONObject();
									String itemnoval = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NO));
									jsonobj.put(
											DatabaseForDemo.INVENTORY_ITEM_NO,
											itemnoval);
									String department = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.INVENTORY_DEPARTMENT));
									jsonobj.put(
											DatabaseForDemo.INVENTORY_DEPARTMENT,
											department);
									String itemname = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NAME));
									jsonobj.put(
											DatabaseForDemo.INVENTORY_ITEM_NAME,
											itemname);
									String desc = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.INVENTORY_SECOND_DESCRIPTION));
									jsonobj.put(
											DatabaseForDemo.INVENTORY_SECOND_DESCRIPTION,
											desc);
									String avgcost = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.INVENTORY_AVG_COST));
									jsonobj.put(
											DatabaseForDemo.INVENTORY_AVG_COST,
											avgcost);
									String pricechange = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.INVENTORY_PRICE_CHANGE));
									jsonobj.put(
											DatabaseForDemo.INVENTORY_PRICE_CHANGE,
											pricechange);
									String taxprice = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.INVENTORY_PRICE_TAX));
									jsonobj.put(
											DatabaseForDemo.INVENTORY_PRICE_TAX,
											taxprice);
									String instock = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.INVENTORY_IN_STOCK));
									jsonobj.put(
											DatabaseForDemo.INVENTORY_IN_STOCK,
											instock);
									String vendor = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.INVENTORY_VENDOR));
									 jsonobj.put(DatabaseForDemo.INVENTORY_VENDOR,""+
									 vendor);
									 String qtyz = mCursorforvendor1.getString(mCursorforvendor1
												.getColumnIndex(DatabaseForDemo.INVENTORY_QUANTITY));
									 jsonobj.put(DatabaseForDemo.INVENTORY_QUANTITY,
											 qtyz);
									 String t_tax = mCursorforvendor1.getString(mCursorforvendor1
												.getColumnIndex(DatabaseForDemo.INVENTORY_TOTAL_TAX));
									 jsonobj.put(DatabaseForDemo.INVENTORY_TOTAL_TAX,
											 t_tax);
									String taxone = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.INVENTORY_TAXONE));
									jsonobj.put(
											DatabaseForDemo.INVENTORY_TAXONE,
											taxone);
									jsonobj.put(DatabaseForDemo.CHECKED_VALUE,
											"true");
									String notes = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.INVENTORY_NOTES));
									jsonobj.put(
											DatabaseForDemo.INVENTORY_NOTES,
											notes);
									String uniqueidval = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.UNIQUE_ID));
									jsonobj.put(DatabaseForDemo.UNIQUE_ID,
											uniqueidval);
									getlistformain.add(jsonobj);
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
							} while (mCursorforvendor1.moveToNext());
						}
					}
					mCursorforvendor1.close();

					final ArrayList<JSONObject> getlist = new ArrayList<JSONObject>();
					String selectQueryforinstantpo1 = "SELECT  * FROM "
							+ DatabaseForDemo.OPTIONAL_INFO_TABLE + " where "
							+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\"" + itemno
							+ "\"";

					Cursor mCursorforvendor2 = dbforloginlogoutWriteInvetory.rawQuery(
							selectQueryforinstantpo1, null);

					if (mCursorforvendor2 != null) {
						if (mCursorforvendor2.moveToFirst()) {
							do {
								try {
									JSONObject jsonobj = new JSONObject();
									String itemnoval = mCursorforvendor2.getString(mCursorforvendor2
											.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NO));
									jsonobj.put(
											DatabaseForDemo.INVENTORY_ITEM_NO,
											itemnoval);
									String department = mCursorforvendor2.getString(mCursorforvendor2
											.getColumnIndex(DatabaseForDemo.LOCATION));
									jsonobj.put(DatabaseForDemo.LOCATION,
											department);
									String itemname = mCursorforvendor2.getString(mCursorforvendor2
											.getColumnIndex(DatabaseForDemo.INVENTORY_ALLOW_BUYBACK));
									jsonobj.put(
											DatabaseForDemo.INVENTORY_ALLOW_BUYBACK,
											itemname);
									String desc = mCursorforvendor2.getString(mCursorforvendor2
											.getColumnIndex(DatabaseForDemo.INVENTORY_COUNT_THIS_ITEM));
									jsonobj.put(
											DatabaseForDemo.INVENTORY_COUNT_THIS_ITEM,
											desc);
									String avgcost = mCursorforvendor2.getString(mCursorforvendor2
											.getColumnIndex(DatabaseForDemo.INVENTORY_FOODSTAMPABLE));
									jsonobj.put(
											DatabaseForDemo.INVENTORY_FOODSTAMPABLE,
											avgcost);
									String pricechange = mCursorforvendor2.getString(mCursorforvendor2
											.getColumnIndex(DatabaseForDemo.INVENTORY_PROMPT_PRICE));
									jsonobj.put(
											DatabaseForDemo.INVENTORY_PROMPT_PRICE,
											pricechange);
									String taxprice = mCursorforvendor2.getString(mCursorforvendor2
											.getColumnIndex(DatabaseForDemo.INVENTORY_PRINT_ON_RECEIPT));
									jsonobj.put(
											DatabaseForDemo.INVENTORY_PRINT_ON_RECEIPT,
											taxprice);
									String instock = mCursorforvendor2.getString(mCursorforvendor2
											.getColumnIndex(DatabaseForDemo.BARCODES));
									jsonobj.put(DatabaseForDemo.BARCODES,
											instock);
									String vendor = mCursorforvendor2.getString(mCursorforvendor2
											.getColumnIndex(DatabaseForDemo.BONUS_POINTS));
									jsonobj.put(DatabaseForDemo.BONUS_POINTS,
											vendor);
									String taxone = mCursorforvendor2.getString(mCursorforvendor2
											.getColumnIndex(DatabaseForDemo.COMMISSION_OPTIONAL_INFO));
									jsonobj.put(
											DatabaseForDemo.COMMISSION_OPTIONAL_INFO,
											taxone);
									String notes = mCursorforvendor2.getString(mCursorforvendor2
											.getColumnIndex(DatabaseForDemo.UNIT_SIZE));
									jsonobj.put(DatabaseForDemo.UNIT_SIZE,
											notes);
									String notes1 = mCursorforvendor2.getString(mCursorforvendor2
											.getColumnIndex(DatabaseForDemo.UNIT_TYPE));
									jsonobj.put(DatabaseForDemo.UNIT_TYPE,
											notes1);
									String notes3 = mCursorforvendor2.getString(mCursorforvendor2
											.getColumnIndex(DatabaseForDemo.INVENTORY_MODIFIER_ITEM));
									Log.v("modify",""+notes3);
									jsonobj.put(
											DatabaseForDemo.INVENTORY_MODIFIER_ITEM,
											notes3);
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
					System.out
							.println("optional info record getting is completed");

					try {
						JSONObject data = new JSONObject();
						JSONArray fields = new JSONArray();
						for (int i = 0; i < getlistformain.size(); i++) {
							fields.put(i, getlistformain.get(i));
							data.put("fields", fields);
							dataval = data.toString();
						}
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try {
						JSONObject data = new JSONObject();
						JSONArray fields = new JSONArray();
						for (int i = 0; i < getlist.size(); i++) {
							fields.put(i, getlist.get(i));
							data.put("fields", fields);
							datavalforproduct1 = data.toString();
						}
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try {
						JSONArray unique_ids = new JSONArray();
						unique_ids.put(0, uniqueidforprodedit);
						datavalforsku = unique_ids.toString();
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try {
						JSONArray unique_ids = new JSONArray();
						unique_ids.put(0, uniqueidforprodedit);
						datavalformodifier = unique_ids.toString();
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try {
						JSONArray unique_ids = new JSONArray();
						unique_ids.put(0, uniqueidforprodedit);
						datavalforordering = unique_ids.toString();
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					try {
						JSONObject data = new JSONObject();
						JSONObject jsonobj = new JSONObject();
						JSONArray fields = new JSONArray();

						for (int i = 0; i < skuarray.size(); i++) {
							fields.put(i, jsonobj);
							jsonobj.put(DatabaseForDemo.ALTERNATE_SKU_VALUE,
									skuarray.get(i));
							jsonobj.put(DatabaseForDemo.INVENTORY_ITEM_NO,
									itemno);
							jsonobj.put(DatabaseForDemo.UNIQUE_ID,
									uniqueidforprodedit);
						}
						data.put("fields", fields);
						datavalforproduct2 = data.toString();
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					/*try {
						JSONObject data = new JSONObject();
						JSONObject jsonobj = new JSONObject();
						JSONArray fields = new JSONArray();
						modifiersArrayserver.clear();
						for (int i = 0; i < modifierindividualitemsarray
								.size(); i++) {
							jsonobj.put(DatabaseForDemo.UNIQUE_ID,
									uniqueidforprodedit);
							jsonobj.put(DatabaseForDemo.CREATED_DATE,
									Parameters.currentTime());
							jsonobj.put(DatabaseForDemo.MODIFIED_DATE,
									Parameters.currentTime());
							jsonobj
									.put(DatabaseForDemo.MODIFIED_IN, "Local");
							jsonobj.put(
									DatabaseForDemo.MODIFIER_ITEM_NO,
									modifierindividualitemsarray.get(i).get(
											"itemno"));
							jsonobj.put(
									DatabaseForDemo.INVENTORY_ITEM_NAME,
									modifierindividualitemsarray.get(i).get(
											"itemname"));
							jsonobj.put(
									DatabaseForDemo.INVENTORY_ITEM_NO,
									itemno);
							Log.e("nn srihari "+modifierindividualitemsarray.get(i)
									.get("itemname"),"id "+modifierindividualitemsarray.get(i)
									.get("itemno"));
							fields.put(0, jsonobj);
						data.put("fields", fields);
						datavalforproduct3 = data.toString();
						modifiersArrayserver.add(datavalforproduct3);
						}
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}*/

					try {
						JSONObject data = new JSONObject();
						JSONObject jsonobj = new JSONObject();
						JSONArray fields = new JSONArray();

						for (int i = 0; i < pricingvendorlistinorderinginfo
								.size(); i++) {
							fields.put(i, jsonobj);
							jsonobj.put(
									DatabaseForDemo.VENDOR_COMPANY_NAME,
									pricingvendorlistinorderinginfo.get(i).get(
											"vendorname"));
							jsonobj.put(
									DatabaseForDemo.VENDERPART_NO,
									pricingvendorlistinorderinginfo.get(i).get(
											"partno"));
							jsonobj.put(
									DatabaseForDemo.COST_PER,
									pricingvendorlistinorderinginfo.get(i).get(
											"costperase"));
							jsonobj.put(
									DatabaseForDemo.CASE_COST,
									pricingvendorlistinorderinginfo.get(i).get(
											"casecost"));
							jsonobj.put(
									DatabaseForDemo.NO_IN_CASE,
									pricingvendorlistinorderinginfo.get(i).get(
											"noincase"));
							jsonobj.put(
									DatabaseForDemo.PREFERRED,
									pricingvendorlistinorderinginfo.get(i).get(
											"preferred"));
							jsonobj.put(DatabaseForDemo.INVENTORY_ITEM_NO,
									itemno);
							jsonobj.put(DatabaseForDemo.UNIQUE_ID,
									uniqueidforprodedit);
						}
						data.put("fields", fields);
						datavalforproduct4 = data.toString();
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					if (Parameters.OriginalUrl.equals("")) {
						System.out.println("there is no server url val");
					} else {
						boolean isnet = Parameters.isNetworkAvailable(InventoryActivity.this);
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
									
								
									
											
									try {
										JSONObject obj = new JSONObject(
												response);
										JSONArray array = obj
												.getJSONArray("insert-queries");
										System.out
												.println("array_list length for insert is:"
														+ array.length());
										JSONArray array2 = obj
												.getJSONArray("delete-queries");
										System.out
												.println("array2_list length for delete is:"
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

										dbforloginlogoutWriteInvetory.execSQL(deletequery);
										dbforloginlogoutWriteInvetory.execSQL(insertquery);
											System.out
													.println("queries executed"
															+ ii);

										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									String responseproduct1 = jsonpost
											.postmethodfordirect(
													"admin",
													"abcdefg",
													DatabaseForDemo.OPTIONAL_INFO_TABLE,
													Parameters.currentTime(),
													Parameters.currentTime(),
													datavalforproduct1, "true");
									System.out.println("response test is:"
											+ responseproduct1);
									try {
										JSONObject obj = new JSONObject(
												responseproduct1);
										JSONArray array = obj
												.getJSONArray("insert-queries");
										System.out
												.println("array_list length for insert is:"
														+ array.length());
										JSONArray array2 = obj
												.getJSONArray("delete-queries");
										System.out
												.println("array2_list length for delete is:"
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

										dbforloginlogoutWriteInvetory.execSQL(deletequery);
										dbforloginlogoutWriteInvetory.execSQL(insertquery);
											System.out
													.println("queries executed"
															+ ii);

										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									String responseforsku = jsonpost
											.postmethodfordirectdelete(
													"admin",
													"abcdefg",
													DatabaseForDemo.ALTERNATE_SKU_TABLE,
													Parameters.currentTime(),
													Parameters.currentTime(),
													datavalforsku);
									System.out.println("response test is:"
											+ responseforsku);
									/*
									 * try { JSONObject obj = new
									 * JSONObject(responseforsku); JSONArray
									 * array =
									 * obj.getJSONArray("insert-queries");
									 * System.out.println(
									 * "array_list length for insert is:"
									 * +array.length()); JSONArray array2 =
									 * obj.getJSONArray("delete-queries");
									 * System.out.println(
									 * "array2_list length for delete is:"
									 * +array2.length()); for(int jj = 0,ii = 0;
									 * jj<array2.length() && ii<array.length();
									 * jj++,ii++){ String deletequerytemp =
									 * array2.getString(jj); String deletequery1
									 * = deletequerytemp.replace("'", "\"");
									 * String deletequery =
									 * deletequery1.replace("\\\"", "'");
									 * System.
									 * out.println("delete query"+jj+" is :"
									 * +deletequery);
									 * 
									 * String insertquerytemp =
									 * array.getString(ii); String insertquery1
									 * = insertquerytemp.replace("'", "\"");
									 * String insertquery =
									 * insertquery1.replace("\\\"", "'");
									 * System.
									 * out.println("delete query"+jj+" is :"
									 * +insertquery);
									 * 
									 *dbforloginlogoutReadInvetory.execSQL(deletequery);
									 *dbforloginlogoutReadInvetory.execSQL(insertquery);
									 * System.out.println
									 * ("queries executed"+ii);
									 * 
									 * } } catch (JSONException e) { // TODO
									 * Auto-generated catch block
									 * e.printStackTrace(); }
									 */

									String responseformodifier = jsonpost
											.postmethodfordirectdelete(
													"admin",
													"abcdefg",
													DatabaseForDemo.CATEGORY_TABLE,
													Parameters.currentTime(),
													Parameters.currentTime(),
													datavalformodifier);
									System.out.println("response test is:"
											+ responseformodifier);
									/*
									 * try { JSONObject obj = new
									 * JSONObject(responseformodifier);
									 * JSONArray array =
									 * obj.getJSONArray("insert-queries");
									 * System.out.println(
									 * "array list length for insert is:"
									 * +array.length()); JSONArray array2 =
									 * obj.getJSONArray("delete-queries");
									 * System.out.println(
									 * "array2 list length for delete is:"
									 * +array2.length()); for(int jj = 0,ii = 0;
									 * jj<array2.length() && ii<array.length();
									 * jj++,ii++){ String deletequerytemp =
									 * array2.getString(jj); String deletequery1
									 * = deletequerytemp.replace("'", "\"");
									 * String deletequery =
									 * deletequery1.replace("\\\"", "'");
									 * System.
									 * out.println("delete query"+jj+" is :"
									 * +deletequery);
									 * 
									 * String insertquerytemp =
									 * array.getString(ii); String insertquery1
									 * = insertquerytemp.replace("'", "\"");
									 * String insertquery =
									 * insertquery1.replace("\\\"", "'");
									 * System.
									 * out.println("delete query"+jj+" is :"
									 * +insertquery);
									 * 
									 *dbforloginlogoutReadInvetory.execSQL(deletequery);
									 *dbforloginlogoutReadInvetory.execSQL(insertquery);
									 * System.out.println
									 * ("queries executed"+ii);
									 * 
									 * } } catch (JSONException e) { // TODO
									 * Auto-generated catch block
									 * e.printStackTrace(); }
									 */

									String responseforordering = jsonpost
											.postmethodfordirectdelete(
													"admin",
													"abcdefg",
													DatabaseForDemo.CATEGORY_TABLE,
													Parameters.currentTime(),
													Parameters.currentTime(),
													datavalforordering);
									System.out.println("response test is:"
											+ responseforordering);
									/*
									 * try { JSONObject obj = new
									 * JSONObject(responseforordering);
									 * JSONArray array =
									 * obj.getJSONArray("insert-queries");
									 * System.out.println(
									 * "array list length for insert is:"
									 * +array.length()); JSONArray array2 =
									 * obj.getJSONArray("delete-queries");
									 * System.out.println(
									 * "array2 list length for delete is:"
									 * +array2.length()); for(int jj = 0,ii = 0;
									 * jj<array2.length() && ii<array.length();
									 * jj++,ii++){ String deletequerytemp =
									 * array2.getString(jj); String deletequery1
									 * = deletequerytemp.replace("'", "\"");
									 * String deletequery =
									 * deletequery1.replace("\\\"", "'");
									 * System.
									 * out.println("delete query"+jj+" is :"
									 * +deletequery);
									 * 
									 * String insertquerytemp =
									 * array.getString(ii); String insertquery1
									 * = insertquerytemp.replace("'", "\"");
									 * String insertquery =
									 * insertquery1.replace("\\\"", "'");
									 * System.
									 * out.println("delete query"+jj+" is :"
									 * +insertquery);
									 * 
									 *dbforloginlogoutReadInvetory.execSQL(deletequery);
									 *dbforloginlogoutReadInvetory.execSQL(insertquery);
									 * System.out.println
									 * ("queries executed"+ii);
									 * 
									 * } } catch (JSONException e) { // TODO
									 * Auto-generated catch block
									 * e.printStackTrace(); }
									 */

									String responseproduct2 = jsonpost
											.postmethodfordirect(
													"admin",
													"abcdefg",
													DatabaseForDemo.ALTERNATE_SKU_TABLE,
													Parameters.currentTime(),
													Parameters.currentTime(),
													datavalforproduct2, "");
									System.out.println("response test is:"
											+ responseproduct2);
									try {
										JSONObject obj = new JSONObject(
												responseproduct2);
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

										dbforloginlogoutWriteInvetory.execSQL(deletequery);
										dbforloginlogoutWriteInvetory.execSQL(insertquery);
											System.out
													.println("queries executed"
															+ ii);

										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									for (int i = 0; i < modifiersArrayserver
											.size(); i++) {
									String responseproduct3 = jsonpost
											.postmethodfordirect(
													"admin",
													"abcdefg",
													DatabaseForDemo.MODIFIER_TABLE,
													Parameters
															.currentTime(),
													Parameters
															.currentTime(),
															modifiersArrayserver.get(i),
													"");
									System.out
											.println("response test is:"
													+ responseproduct3);
									try {
										JSONObject obj = new JSONObject(
												responseproduct3);
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
													.replace(
															"'",
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
													.replace(
															"'",
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

										dbforloginlogoutWriteInvetory.execSQL(deletequery);
										dbforloginlogoutWriteInvetory.execSQL(insertquery);
											System.out
													.println("queries executed"
															+ ii);
										}
									} catch (Exception e) {

									}
									}

									String responseproduct4 = jsonpost
											.postmethodfordirect(
													"admin",
													"abcdefg",
													DatabaseForDemo.ORDERING_INFO_TABLE,
													Parameters.currentTime(),
													Parameters.currentTime(),
													datavalforproduct4, "");
									System.out.println("response test is:"
											+ responseproduct4);

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

										dbforloginlogoutWriteInvetory.execSQL(deletequery);
										dbforloginlogoutWriteInvetory.execSQL(insertquery);
											System.out
													.println("queries executed"
															+ ii);

										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									String select = "select *from "
											+ DatabaseForDemo.MISCELLANEOUS_TABLE;
									Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(select, null);
									if (cursor.getCount() > 0) {
									dbforloginlogoutWriteInvetory.execSQL("update "
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
										dbforloginlogoutWriteInvetory.insert(
												DatabaseForDemo.MISCELLANEOUS_TABLE,
												null, contentValues1);
										
									}
									cursor.close();

									dataval = "";
									datavalforproduct1 = "";
									datavalforproduct2 = "";
									datavalforproduct3 = "";
									datavalforproduct4 = "";
									datavalforordering = "";
									datavalformodifier = "";
									datavalforsku = "";
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
									DatabaseForDemo.INVENTORY_TABLE);
							contentValues1.put(
									DatabaseForDemo.CURRENT_TIME_PENDING,
									Parameters.currentTime());
							contentValues1.put(DatabaseForDemo.PARAMETERS,
									dataval);
							dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.PENDING_QUERIES_TABLE,
									null, contentValues1);

							ContentValues contentValues2 = new ContentValues();
							contentValues2.put(DatabaseForDemo.QUERY_TYPE,
									"update");
							contentValues2.put(DatabaseForDemo.PENDING_USER_ID,
									Parameters.userid);
							contentValues2.put(DatabaseForDemo.PAGE_URL,
									"saveinfo.php");
							contentValues2.put(
									DatabaseForDemo.TABLE_NAME_PENDING,
									DatabaseForDemo.OPTIONAL_INFO_TABLE);
							contentValues2.put(
									DatabaseForDemo.CURRENT_TIME_PENDING,
									Parameters.currentTime());
							contentValues2.put(DatabaseForDemo.PARAMETERS,
									datavalforproduct1);
							dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.PENDING_QUERIES_TABLE,
									null, contentValues2);

							ContentValues contentValues3 = new ContentValues();
							contentValues3.put(DatabaseForDemo.QUERY_TYPE,
									"delete");
							contentValues3.put(DatabaseForDemo.PENDING_USER_ID,
									Parameters.userid);
							contentValues3.put(DatabaseForDemo.PAGE_URL,
									"deleteinfo.php");
							contentValues3.put(
									DatabaseForDemo.TABLE_NAME_PENDING,
									DatabaseForDemo.ALTERNATE_SKU_TABLE);
							contentValues3.put(
									DatabaseForDemo.CURRENT_TIME_PENDING,
									Parameters.currentTime());
							contentValues3.put(DatabaseForDemo.PARAMETERS,
									datavalforsku);
							dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.PENDING_QUERIES_TABLE,
									null, contentValues3);

							ContentValues contentValues4 = new ContentValues();
							contentValues4.put(DatabaseForDemo.QUERY_TYPE,
									"delete");
							contentValues4.put(DatabaseForDemo.PENDING_USER_ID,
									Parameters.userid);
							contentValues4.put(DatabaseForDemo.PAGE_URL,
									"deleteinfo.php");
							contentValues4.put(
									DatabaseForDemo.TABLE_NAME_PENDING,
									DatabaseForDemo.MODIFIER_TABLE);
							contentValues4.put(
									DatabaseForDemo.CURRENT_TIME_PENDING,
									Parameters.currentTime());
							contentValues4.put(DatabaseForDemo.PARAMETERS,
									datavalformodifier);
							dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.PENDING_QUERIES_TABLE,
									null, contentValues4);

							ContentValues contentValues5 = new ContentValues();
							contentValues5.put(DatabaseForDemo.QUERY_TYPE,
									"delete");
							contentValues5.put(DatabaseForDemo.PENDING_USER_ID,
									Parameters.userid);
							contentValues5.put(DatabaseForDemo.PAGE_URL,
									"deleteinfo.php");
							contentValues5.put(
									DatabaseForDemo.TABLE_NAME_PENDING,
									DatabaseForDemo.ORDERING_INFO_TABLE);
							contentValues5.put(
									DatabaseForDemo.CURRENT_TIME_PENDING,
									Parameters.currentTime());
							contentValues5.put(DatabaseForDemo.PARAMETERS,
									datavalforordering);
							dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.PENDING_QUERIES_TABLE,
									null, contentValues5);

							ContentValues contentValues6 = new ContentValues();
							contentValues6.put(DatabaseForDemo.QUERY_TYPE,
									"insert");
							contentValues6.put(DatabaseForDemo.PENDING_USER_ID,
									Parameters.userid);
							contentValues6.put(DatabaseForDemo.PAGE_URL,
									"saveinfo.php");
							contentValues6.put(
									DatabaseForDemo.TABLE_NAME_PENDING,
									DatabaseForDemo.ALTERNATE_SKU_TABLE);
							contentValues6.put(
									DatabaseForDemo.CURRENT_TIME_PENDING,
									Parameters.currentTime());
							contentValues6.put(DatabaseForDemo.PARAMETERS,
									datavalforproduct2);
							dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.PENDING_QUERIES_TABLE,
									null, contentValues6);

							ContentValues contentValues7 = new ContentValues();
							contentValues7.put(DatabaseForDemo.QUERY_TYPE,
									"insert");
							contentValues7.put(DatabaseForDemo.PENDING_USER_ID,
									Parameters.userid);
							contentValues7.put(DatabaseForDemo.PAGE_URL,
									"saveinfo.php");
							contentValues7.put(
									DatabaseForDemo.TABLE_NAME_PENDING,
									DatabaseForDemo.MODIFIER_TABLE);
							contentValues7.put(
									DatabaseForDemo.CURRENT_TIME_PENDING,
									Parameters.currentTime());
							contentValues7.put(DatabaseForDemo.PARAMETERS,
									datavalforproduct3);
							dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.PENDING_QUERIES_TABLE,
									null, contentValues7);

							ContentValues contentValues8 = new ContentValues();
							contentValues8.put(DatabaseForDemo.QUERY_TYPE,
									"insert");
							contentValues8.put(DatabaseForDemo.PENDING_USER_ID,
									Parameters.userid);
							contentValues8.put(DatabaseForDemo.PAGE_URL,
									"saveinfo.php");
							contentValues8.put(
									DatabaseForDemo.TABLE_NAME_PENDING,
									DatabaseForDemo.ORDERING_INFO_TABLE);
							contentValues8.put(
									DatabaseForDemo.CURRENT_TIME_PENDING,
									Parameters.currentTime());
							contentValues8.put(DatabaseForDemo.PARAMETERS,
									datavalforproduct4);
							dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.PENDING_QUERIES_TABLE,
									null, contentValues8);

							
							
							dataval = "";
							datavalforproduct1 = "";
							datavalforproduct2 = "";
							datavalforproduct3 = "";
							datavalforproduct4 = "";
							datavalforordering = "";
							datavalformodifier = "";
							datavalforsku = "";
						}
					}

					
					modifieritemval = "";
					countthisitemval = "";
					printonreceiptval = "";
					allowbuybackval = "";
					foodstampableval = "";
					promptpriceval = "";
					notesval = "";
					bonuspointsval = "";
					barcodeval = "";
					commissionval = "";
					locationval = "";
					unitsizeval = "";
					unittypeval = "";
					skuval = "";
					printervalfordept = "";
					taxvalforsaveforprod = "";
					foodcheckval = "";
					taxvalforsavefordept = "";
					taxvalforduplicate = 0;
					taxvalforsaveforduplicate = "";
					taxvalforprod = 0;
					optional_info.clear();
					skuarray.clear();
					modifierindividualitemsarray.clear();
					pricingvendorlistinorderinginfo.clear();
					gettingCount();
					listUpdateforproduct(offsetvals, "100",
							totalrecordselectQuery);
					alertDialog1.dismiss();
				}
				} catch (NumberFormatException e) {
					  e.printStackTrace();
					} catch (Exception e1) {
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

		// Showing Alert Message
		alertDialog1.show();
		} catch (NumberFormatException e) {
			  e.printStackTrace();
			} catch (Exception e1) {
				  e1.printStackTrace();
				}
	}

	@Override
	public void onDuplicateClickedforProduct(View v, final String itemno,
			String itemname, String price, String stock, String desc2,
			String vendor) {
		// TODO Auto-generated method stub
		final AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
		Log.e("tagdd", ".....gggggg");
		final EditText itemnoed, itemnameed, desc2ed, costed, pricechargeed, pricetaxed, stocked;
		final CheckBox tax1check, tax2check, tax3check, bartaxcheck;
		final Spinner departmentforproduct, vendorproduct;
		Button save, cancel, optionalinfoed, notesed, modifiersed, orderinginfoed, saleshistoryed, printersed;

		LayoutInflater mInflater = LayoutInflater.from(this);
		View layout = mInflater.inflate(R.layout.product_details, null);

		departmentforproduct = (Spinner) layout.findViewById(R.id.department);
		itemnoed = (EditText) layout.findViewById(R.id.item_no);
		itemnameed = (EditText) layout.findViewById(R.id.description);
		desc2ed = (EditText) layout.findViewById(R.id.description2);
		desc2ed.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(desc2ed.getWindowToken(), 0);
				}
				return false;
			}
		});
		costed = (EditText) layout.findViewById(R.id.cost);
		pricechargeed = (EditText) layout.findViewById(R.id.price_charge);
		pricetaxed = (EditText) layout.findViewById(R.id.price_tax);
		stocked = (EditText) layout.findViewById(R.id.instock);
		vendorproduct = (Spinner) layout.findViewById(R.id.vendorprod);

		optionalinfoed = (Button) layout.findViewById(R.id.optionalinfo);
		notesed = (Button) layout.findViewById(R.id.notes);
		modifiersed = (Button) layout.findViewById(R.id.modifiers);
		orderinginfoed = (Button) layout.findViewById(R.id.orderinginfo);
		saleshistoryed = (Button) layout.findViewById(R.id.saleshistory);
		printersed = (Button) layout.findViewById(R.id.printers);

		tax1check = (CheckBox) layout.findViewById(R.id.checkBox1);
		tax2check = (CheckBox) layout.findViewById(R.id.checkBox2);
		tax3check = (CheckBox) layout.findViewById(R.id.checkBox3);
		tax1check.setText(taxlist.get(0));
		tax2check.setText(taxlist.get(1));
		tax3check.setText(taxlist.get(2));
		bartaxcheck = (CheckBox) layout.findViewById(R.id.checkBox4);
		save = (Button) layout.findViewById(R.id.save);
		cancel = (Button) layout.findViewById(R.id.cancel);
		ArrayList<String> deptspinnerdata = new ArrayList<String>();
		ArrayList<String> vendorspinnerdata = new ArrayList<String>();
		deptspinnerdata.clear();
		vendorspinnerdata.clear();
		skuarray.clear();
		modifierindividualitemsarray.clear();
		pricingvendorlistinorderinginfo.clear();

		// if(Parameters.usertype.equals("demo")){

		Cursor mCursor = dbforloginlogoutReadInvetory.rawQuery(
				"select " + DatabaseForDemo.DepartmentID + " from "
						+ DatabaseForDemo.DEPARTMENT_TABLE, null);
		System.out.println(mCursor);
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					String catid = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.DepartmentID));
					deptspinnerdata.add(catid);
				} while (mCursor.moveToNext());
			}
			

			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					InventoryActivity.this,
					android.R.layout.simple_spinner_item, deptspinnerdata);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			departmentforproduct.setAdapter(adapter);
		} else {
			Toast.makeText(getApplicationContext(),
					"This is no profile data in demo login", Toast.LENGTH_SHORT)
					.show();
		}
		mCursor.close();
		Cursor mCursor2 = dbforloginlogoutReadInvetory.rawQuery(
				"select " + DatabaseForDemo.VENDOR_NO + " from "
						+ DatabaseForDemo.VENDOR_TABLE, null);
		System.out.println(mCursor2);
		if (mCursor2 != null) {
			if (mCursor2.moveToFirst()) {
				do {
					String catid = mCursor2.getString(mCursor2
							.getColumnIndex(DatabaseForDemo.VENDOR_NO));
					vendorspinnerdata.add(catid);
				} while (mCursor2.moveToNext());
			}
			
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(
					InventoryActivity.this,
					android.R.layout.simple_spinner_item, vendorspinnerdata);
			adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
			vendorproduct.setAdapter(adapter);
		} else {
			Toast.makeText(getApplicationContext(),
					"This is no profile data in demo login", Toast.LENGTH_SHORT)
					.show();
		}
		mCursor2.close();
		System.out.println("hari");
		/*
		 * }else{ Toast.makeText(getApplicationContext(),
		 * "This is not demo login", Toast.LENGTH_SHORT).show(); }
		 */

		
		try{

		String selectQuery = "SELECT  * FROM "
				+ DatabaseForDemo.INVENTORY_TABLE + " where "
				+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\"" + itemno + "\"";
		System.out.println("hari2");
		Cursor mCursor5 =dbforloginlogoutReadInvetory.rawQuery(selectQuery, null);
		if (mCursor5 != null) {
			if (mCursor5.moveToFirst()) {
				do {
					System.out.println("hari3");
					String departmentval = mCursor5
							.getString(mCursor5
									.getColumnIndex(DatabaseForDemo.INVENTORY_DEPARTMENT));
					departmentforproduct.setSelection(deptspinnerdata
							.indexOf(departmentval));
					System.out.println("hari8");
					String itemnoval = mCursor5.getString(mCursor5
							.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NO));
					itemnoed.setText(itemnoval);
					String itemnameval = mCursor5
							.getString(mCursor5
									.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NAME));
					System.out.println("hari4");
					itemnameed.setText(itemnameval);
					String desc2val = mCursor5
							.getString(mCursor5
									.getColumnIndex(DatabaseForDemo.INVENTORY_SECOND_DESCRIPTION));
					desc2ed.setText(desc2val);
					String costval = mCursor5
							.getString(mCursor5
									.getColumnIndex(DatabaseForDemo.INVENTORY_AVG_COST));
					costed.setText(costval);
					System.out.println("harig87");
					String pricechargeval = mCursor5
							.getString(mCursor5
									.getColumnIndex(DatabaseForDemo.INVENTORY_PRICE_CHANGE));
					pricechargeed.setText(pricechargeval);
					String pricetaxval = mCursor5
							.getString(mCursor5
									.getColumnIndex(DatabaseForDemo.INVENTORY_PRICE_TAX));
					System.out.println("harif5");
					String texss=mCursor5.getString(mCursor5
							.getColumnIndex(DatabaseForDemo.INVENTORY_TAXONE));
					System.out.println("harif5eeee");
					String[] parts = texss.split(",");
					System.out.println("harif5qqqq     " +parts.length);
					double tax=0.0;
					for(int t=0;t<parts.length;t++){
						System.out.println("harifbbb5");
						String ttt=parts[t]; 
						Log.e("",""+ttt);
						String query = "select * from "
								+ DatabaseForDemo.TAX_TABLE + " where "
								+ DatabaseForDemo.TAX_NAME + "=\""
								+ ttt + "\"";
						System.out.println(query);
						Cursor cursortax =dbforloginlogoutReadInvetory.rawQuery(query, null);
						if (cursortax != null) {
							if (cursortax.moveToFirst()) {
								do {
									double taxvalpercent =cursortax.getDouble(cursortax
											.getColumnIndex(DatabaseForDemo.TAX_VALUE));
									System.out.println("taxvalpercent     " + taxvalpercent);
									tax += taxvalpercent;
									System.out.println(" tax    " + tax);
								} while (cursortax.moveToNext());
							}
					}
					}
					System.out.println(" tax    1111");
					tax = (Double.valueOf(pricechargeval) * tax) / 100;
					tax = (Double.valueOf(pricechargeval) +tax);
					pricetaxed.setText(df.format(tax));
					String instockval = mCursor5
							.getString(mCursor5
									.getColumnIndex(DatabaseForDemo.INVENTORY_IN_STOCK));
					stocked.setText(instockval);
					String vendorval = mCursor5.getString(mCursor5
							.getColumnIndex(DatabaseForDemo.INVENTORY_VENDOR));
					// vendorproduct.setSelection(vendorspinnerdata.indexOf(vendorval));
					if (mCursor5.isNull(mCursor5
							.getColumnIndex(DatabaseForDemo.INVENTORY_TAXONE))) {
						System.out.println(" tax yyy   " + tax);
					} else {
						System.out.println(" tax   zzzz " + tax);
						String taxval = mCursor5
								.getString(mCursor5
										.getColumnIndex(DatabaseForDemo.INVENTORY_TAXONE));
						String mystring = taxval;
						String[] a = mystring.split(",");
						System.out.println("the array values are:" + a.length);
						for (int i = 0; i < a.length; i++) {
							String substr = a[i];
							if (substr.equals("Tax1")) {
								tax1check.setChecked(true);
							} else if (substr.equals("Tax2")) {
								tax2check.setChecked(true);
							} else if (substr.equals("Tax3")) {
								tax3check.setChecked(true);
							} else if (substr.equals("BarTax")) {
								bartaxcheck.setChecked(true);
							}
						}
					}
					System.out.println(" tax  xvfx  " + tax);
					if (mCursor5.isNull(mCursor5
							.getColumnIndex(DatabaseForDemo.INVENTORY_NOTES))) {
						notesval = "";
					} else {
						notesval = mCursor5
								.getString(mCursor5
										.getColumnIndex(DatabaseForDemo.INVENTORY_NOTES));
					}
				} while (mCursor5.moveToNext());
			}
		}
		mCursor5.close();
		} catch (NumberFormatException e) {
			  e.printStackTrace();
			} catch (Exception e1) {
				  e1.printStackTrace();
				}
		String selectQueryforoptionalinfo = "SELECT  * FROM "
				+ DatabaseForDemo.OPTIONAL_INFO_TABLE + " where "
				+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\"" + itemno + "\"";

		Cursor mCursoroptional =dbforloginlogoutReadInvetory.rawQuery(selectQueryforoptionalinfo, null);
		if (mCursoroptional != null) {
			if (mCursoroptional.moveToFirst()) {
				do {
					if (mCursoroptional
							.isNull(mCursoroptional
									.getColumnIndex(DatabaseForDemo.INVENTORY_MODIFIER_ITEM))) {
						modifieritemval = "no";
					} else {
						modifieritemval = mCursoroptional
								.getString(mCursoroptional
										.getColumnIndex(DatabaseForDemo.INVENTORY_MODIFIER_ITEM));
					}
					if (mCursoroptional
							.isNull(mCursoroptional
									.getColumnIndex(DatabaseForDemo.INVENTORY_COUNT_THIS_ITEM))) {
						countthisitemval = "no";
					} else {
						countthisitemval = mCursoroptional
								.getString(mCursoroptional
										.getColumnIndex(DatabaseForDemo.INVENTORY_COUNT_THIS_ITEM));
					}
					if (mCursoroptional
							.isNull(mCursoroptional
									.getColumnIndex(DatabaseForDemo.INVENTORY_PRINT_ON_RECEIPT))) {
						printonreceiptval = "no";
					} else {
						printonreceiptval = mCursoroptional
								.getString(mCursoroptional
										.getColumnIndex(DatabaseForDemo.INVENTORY_PRINT_ON_RECEIPT));
					}
					if (mCursoroptional
							.isNull(mCursoroptional
									.getColumnIndex(DatabaseForDemo.INVENTORY_ALLOW_BUYBACK))) {
						allowbuybackval = "no";
					} else {
						allowbuybackval = mCursoroptional
								.getString(mCursoroptional
										.getColumnIndex(DatabaseForDemo.INVENTORY_ALLOW_BUYBACK));
					}
					if (mCursoroptional
							.isNull(mCursoroptional
									.getColumnIndex(DatabaseForDemo.INVENTORY_FOODSTAMPABLE))) {
						foodstampableval = "no";
					} else {
						foodstampableval = mCursoroptional
								.getString(mCursoroptional
										.getColumnIndex(DatabaseForDemo.INVENTORY_FOODSTAMPABLE));
					}
					if (mCursoroptional
							.isNull(mCursoroptional
									.getColumnIndex(DatabaseForDemo.INVENTORY_PROMPT_PRICE))) {
						promptpriceval = "no";
					} else {
						promptpriceval = mCursoroptional
								.getString(mCursoroptional
										.getColumnIndex(DatabaseForDemo.INVENTORY_PROMPT_PRICE));
					}
					if (mCursoroptional.isNull(mCursoroptional
							.getColumnIndex(DatabaseForDemo.BONUS_POINTS))) {
						bonuspointsval = "";
					} else {
						bonuspointsval = mCursoroptional
								.getString(mCursoroptional
										.getColumnIndex(DatabaseForDemo.BONUS_POINTS));
					}
					if (mCursoroptional.isNull(mCursoroptional
							.getColumnIndex(DatabaseForDemo.BARCODES))) {
						barcodeval = "";
					} else {
						barcodeval = mCursoroptional.getString(mCursoroptional
								.getColumnIndex(DatabaseForDemo.BARCODES));
					}
					if (mCursoroptional.isNull(mCursoroptional
							.getColumnIndex(DatabaseForDemo.LOCATION))) {
						locationval = "";
					} else {
						locationval = mCursoroptional.getString(mCursoroptional
								.getColumnIndex(DatabaseForDemo.LOCATION));
					}
					if (mCursoroptional.isNull(mCursoroptional
							.getColumnIndex(DatabaseForDemo.UNIT_SIZE))) {
						unitsizeval = "";
					} else {
						unitsizeval = mCursoroptional.getString(mCursoroptional
								.getColumnIndex(DatabaseForDemo.UNIT_SIZE));
					}
					if (mCursoroptional.isNull(mCursoroptional
							.getColumnIndex(DatabaseForDemo.UNIT_TYPE))) {
						unittypeval = "";
					} else {
						unittypeval = mCursoroptional.getString(mCursoroptional
								.getColumnIndex(DatabaseForDemo.UNIT_TYPE));
					}
					if (mCursoroptional
							.isNull(mCursoroptional
									.getColumnIndex(DatabaseForDemo.COMMISSION_OPTIONAL_INFO))) {
						commissionval = "";
					} else {
						commissionval = mCursoroptional
								.getString(mCursoroptional
										.getColumnIndex(DatabaseForDemo.COMMISSION_OPTIONAL_INFO));
					}
				} while (mCursoroptional.moveToNext());
			}
		}
		mCursoroptional.close();
		String selectQueryforalternatesku = "SELECT  * FROM "
				+ DatabaseForDemo.ALTERNATE_SKU_TABLE + " where "
				+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\"" + itemno + "\"";

		Cursor mCursoralternate =dbforloginlogoutReadInvetory.rawQuery(selectQueryforalternatesku, null);
		if (mCursoralternate != null) {
			if (mCursoralternate.moveToFirst()) {
				do {
					skuval = mCursoralternate
							.getString(mCursoralternate
									.getColumnIndex(DatabaseForDemo.ALTERNATE_SKU_VALUE));
					skuarray.add(skuval);
				} while (mCursoralternate.moveToNext());
			}
		}
		mCursoralternate.close();
		String selectQueryformodifier = "SELECT  * FROM "
				+ DatabaseForDemo.MODIFIER_TABLE + " where "
				+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\"" + itemno + "\"";

		Cursor mCursormodifier =dbforloginlogoutReadInvetory.rawQuery(selectQueryformodifier, null);
		if (mCursormodifier != null) {
			if (mCursormodifier.moveToFirst()) {
				do {
					HashMap<String, String> map = new HashMap<String, String>();
					String itemid = mCursormodifier.getString(mCursormodifier
							.getColumnIndex(DatabaseForDemo.MODIFIER_ITEM_NO));
					String itemnamedata = mCursormodifier
							.getString(mCursormodifier
									.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NAME));
					map.put("itemno", itemid);
					map.put("itemname", itemnamedata);
					modifierindividualitemsarray.add(map);
				} while (mCursormodifier.moveToNext());
			}
		}
		mCursormodifier.close();
		String selectQueryfororderinginfo = "SELECT  * FROM "
				+ DatabaseForDemo.ORDERING_INFO_TABLE + " where "
				+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\"" + itemno + "\"";

		Cursor mCursororderinginfo =dbforloginlogoutReadInvetory.rawQuery(selectQueryfororderinginfo,
				null);
		if (mCursororderinginfo != null) {
			if (mCursororderinginfo.moveToFirst()) {
				do {
					HashMap<String, String> map = new HashMap<String, String>();
					String companyname = mCursororderinginfo
							.getString(mCursororderinginfo
									.getColumnIndex(DatabaseForDemo.VENDOR_COMPANY_NAME));
					String costper = mCursororderinginfo
							.getString(mCursororderinginfo
									.getColumnIndex(DatabaseForDemo.COST_PER));
					String casecost = mCursororderinginfo
							.getString(mCursororderinginfo
									.getColumnIndex(DatabaseForDemo.CASE_COST));
					String noincase = mCursororderinginfo
							.getString(mCursororderinginfo
									.getColumnIndex(DatabaseForDemo.NO_IN_CASE));
					String preferred = mCursororderinginfo
							.getString(mCursororderinginfo
									.getColumnIndex(DatabaseForDemo.PREFERRED));
					String partno = mCursororderinginfo
							.getString(mCursororderinginfo
									.getColumnIndex(DatabaseForDemo.VENDERPART_NO));
					map.put("vendorname", companyname);
					map.put("partno", partno);
					map.put("costpercase", costper);
					map.put("casecost", casecost);
					map.put("noincase", noincase);
					map.put("preferred", preferred);
					pricingvendorlistinorderinginfo.add(map);
				} while (mCursororderinginfo.moveToNext());
			}
		}
		mCursororderinginfo.close();
		pricechargeed.setOnFocusChangeListener(new OnFocusChangeListener() {

			@Override
			public void onFocusChange(View v, boolean hasFocus) {
				// TODO Auto-generated method stub
				try{
				double taxval = 0;
				String costval = pricechargeed.getText().toString();
				double cost = 0;
				if (costval.equals("")) {
					cost = 0;
				} else {
					cost = Double.valueOf(costval);
					if (tax1check.isChecked()) {

						String query = "select * from "
								+ DatabaseForDemo.TAX_TABLE + " where "
								+ DatabaseForDemo.TAX_NAME + "=\""
								+ tax1check.getText().toString() + "\"";
						System.out.println(query);
						
					
						

						Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(query, null);
						if (cursor != null) {
							if (cursor.moveToFirst()) {
								do {
									double taxvalpercent = Double.valueOf(df
											.format(cursor.getDouble(cursor
											.getColumnIndex(DatabaseForDemo.TAX_VALUE))));
									System.out.println("     " + taxvalpercent);
									taxval = cost * (taxvalpercent / 100);
								} while (cursor.moveToNext());
							}
							
							
						} else {
							Toast.makeText(getApplicationContext(),
									"No Tax value", Toast.LENGTH_SHORT).show();
						}

					} else {
						taxval = taxval + 0;
					}
					if (tax2check.isChecked()) {

						String query = "select " + DatabaseForDemo.TAX_VALUE
								+ " from " + DatabaseForDemo.TAX_TABLE
								+ " where " + DatabaseForDemo.TAX_NAME + "=\""
								+ tax2check.getText().toString() + "\"";
						System.out.println(query);
						
					
						
						Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(query, null);
						if (cursor != null) {
							if (cursor.moveToFirst()) {
								do {
									double taxvalpercent = Double.valueOf(df
											.format(cursor.getDouble(cursor
											.getColumnIndex(DatabaseForDemo.TAX_VALUE))));
									System.out.println("     " + taxvalpercent);
									taxval = taxval
											+ (cost * (taxvalpercent / 100));
								} while (cursor.moveToNext());
							}
							
							
						} else {
							Toast.makeText(getApplicationContext(),
									"No Tax value", Toast.LENGTH_SHORT).show();
						}

					} else {
						taxval = taxval + 0;
					}
					if (tax3check.isChecked()) {

						String query = "select " + DatabaseForDemo.TAX_VALUE
								+ " from " + DatabaseForDemo.TAX_TABLE
								+ " where " + DatabaseForDemo.TAX_NAME + "=\""
								+ tax3check.getText().toString() + "\"";
						System.out.println(query);
						
					
						

						Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(query, null);
						if (cursor != null) {
							if (cursor.moveToFirst()) {
								do {
									double taxvalpercent = Double.valueOf(df
											.format(cursor.getDouble(cursor
											.getColumnIndex(DatabaseForDemo.TAX_VALUE))));
									System.out.println("     " + taxvalpercent);
									taxval = taxval
											+ (cost * (taxvalpercent / 100));
								} while (cursor.moveToNext());
							}
							
							
						} else {
							Toast.makeText(getApplicationContext(),
									"No Tax value", Toast.LENGTH_SHORT).show();
						}
cursor.close();
					} else {
						taxval = taxval + 0;
					}
					if (bartaxcheck.isChecked()) {
						taxval = taxval + 0;
						System.out.println("     " + taxval);
					} else {
						taxval = taxval + 0;
					}

					pricetaxed.setText("" + (taxval + cost));
				}
				} catch (NumberFormatException e) {
					  e.printStackTrace();
					} catch (Exception e1) {
						  e1.printStackTrace();
						}
			}
		});

		optionalinfoed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String itemnumber = itemnoed.getText().toString();
				if (itemnumber.equals("")) {
					Toast.makeText(
							getApplicationContext(),
							"You must enter an item number before saving this item.",
							Toast.LENGTH_SHORT).show();
				} else {

					final Dialog dialog = new Dialog(InventoryActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.optional_info_layout);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);
					final CheckBox modifieritemcheck = (CheckBox) dialog
							.findViewById(R.id.modifieritem);
					final CheckBox countthisitemcheck = (CheckBox) dialog
							.findViewById(R.id.countthisitem);
					final CheckBox printonreceiptcheck = (CheckBox) dialog
							.findViewById(R.id.printonreceipt);
					final CheckBox allowbuybackcheck = (CheckBox) dialog
							.findViewById(R.id.allowbuyback);
					final CheckBox foodstamplescheck = (CheckBox) dialog
							.findViewById(R.id.foodstamples);
					final CheckBox promptpricecheck = (CheckBox) dialog
							.findViewById(R.id.promptprice);
					skuslist = (ListView) dialog
							.findViewById(R.id.alternateskuslist);
					final Button addsku = (Button) dialog
							.findViewById(R.id.addsku);
					final EditText bonuspointsed = (EditText) dialog
							.findViewById(R.id.bonuspoints);
					final EditText barcodesed = (EditText) dialog
							.findViewById(R.id.barcodes);
					final EditText commissioned = (EditText) dialog
							.findViewById(R.id.commissioned);
					final EditText locationed = (EditText) dialog
							.findViewById(R.id.location);
					final EditText unitsizeed = (EditText) dialog
							.findViewById(R.id.unitsize);
					final EditText unittypeed = (EditText) dialog
							.findViewById(R.id.unittype);
					final Spinner commissionspinner = (Spinner) dialog
							.findViewById(R.id.commission);

					bonuspointsed.setText(bonuspointsval);
					barcodesed.setText(barcodeval);
					commissioned.setText(commissionval);
					locationed.setText(locationval);
					unitsizeed.setText(unitsizeval);
					unittypeed.setText(unittypeval);
					commissionspinner.setSelection(commissionspinnerval);
					if (modifieritemval.equals("yes")) {
						modifieritemcheck.setChecked(true);
					} else {
						modifieritemcheck.setChecked(false);
					}
					if (countthisitemval.equals("yes")) {
						countthisitemcheck.setChecked(true);
					} else {
						countthisitemcheck.setChecked(false);
					}
					if (printonreceiptval.equals("yes")) {
						printonreceiptcheck.setChecked(true);
					} else {
						printonreceiptcheck.setChecked(false);
					}
					if (allowbuybackval.equals("yes")) {
						allowbuybackcheck.setChecked(true);
					} else {
						allowbuybackcheck.setChecked(false);
					}
					if (foodstampableval.equals("yes")) {
						foodstamplescheck.setChecked(true);
					} else {
						foodstamplescheck.setChecked(false);
					}
					if (promptpriceval.equals("yes")) {
						promptpricecheck.setChecked(true);
					} else {
						promptpricecheck.setChecked(false);
					}
					if (skuarray != null) {
						SkuArrayAdapter skuadapter = new SkuArrayAdapter(
								getApplicationContext(), skuarray);
						skuadapter.setListener(InventoryActivity.this);
						skuslist.setAdapter(skuadapter);
					}

					addsku.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							System.out.println("add is clicked");
							final AlertDialog alertDialog1 = new AlertDialog.Builder(
									InventoryActivity.this).create();
							LayoutInflater mInflater = LayoutInflater
									.from(InventoryActivity.this);
							View layout = mInflater.inflate(R.layout.add_sku,
									null);
							Button ok = (Button) layout.findViewById(R.id.save);
							Button cancel = (Button) layout
									.findViewById(R.id.cancel);
							final EditText sku = (EditText) layout
									.findViewById(R.id.sku);

							alertDialog1.setTitle("Add SKU");

							ok.setOnClickListener(new OnClickListener() {

								@Override
								public void onClick(View arg0) {
									// TODO Auto-generated method stub
									skuval = sku.getText().toString();
									if (skuval.equals("")) {
										Toast.makeText(getApplicationContext(),
												"Please enter sku value",
												Toast.LENGTH_SHORT).show();
									} else {
										if (skuarray.contains(skuval)) {
											Toast.makeText(
													getApplicationContext(),
													"SKU already exist",
													Toast.LENGTH_SHORT).show();
										} else {
											
				
											
													
											String selectQuery = "SELECT  * FROM "
													+ DatabaseForDemo.ALTERNATE_SKU_TABLE
													+ " where "
													+ DatabaseForDemo.ALTERNATE_SKU_VALUE
													+ "=\"" + skuval + "\"";
											Cursor mCursor =dbforloginlogoutReadInvetory.rawQuery(
													selectQuery, null);
											if (mCursor.getCount() > 0) {
												Toast.makeText(
														getApplicationContext(),
														"SKU already exist for an item",
														Toast.LENGTH_SHORT)
														.show();
												
											} else {
												
												skuarray.add(skuval);
												if (skuarray != null) {
													SkuArrayAdapter skuadapter = new SkuArrayAdapter(
															getApplicationContext(),
															skuarray);
													skuadapter
															.setListener(InventoryActivity.this);
													skuslist.setAdapter(skuadapter);
												}
											}
											mCursor.close();
											alertDialog1.dismiss();
										}
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

						}
					});

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							bonuspointsval = bonuspointsed.getText().toString();
							barcodeval = barcodesed.getText().toString();
							commissionval = commissioned.getText().toString();
							locationval = locationed.getText().toString();
							unitsizeval = unitsizeed.getText().toString();
							unittypeval = unittypeed.getText().toString();
							commissionspinnerval = commissionspinner
									.getSelectedItemPosition();
							if (modifieritemcheck.isChecked()) {
								modifieritemval = "yes";
							} else {
								modifieritemval = "no";
							}
							if (countthisitemcheck.isChecked()) {
								countthisitemval = "yes";
							} else {
								countthisitemval = "no";
							}
							if (printonreceiptcheck.isChecked()) {
								printonreceiptval = "yes";
							} else {
								printonreceiptval = "no";
							}
							if (allowbuybackcheck.isChecked()) {
								allowbuybackval = "yes";
							} else {
								allowbuybackval = "no";
							}
							if (foodstamplescheck.isChecked()) {
								foodstampableval = "yes";
							} else {
								foodstampableval = "no";
							}
							if (promptpricecheck.isChecked()) {
								promptpriceval = "yes";
							} else {
								promptpriceval = "no";
							}

							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		notesed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String itemnumber = itemnoed.getText().toString();
				if (itemnumber.equals("")) {
					Toast.makeText(
							getApplicationContext(),
							"You must enter an item number before saving this item.",
							Toast.LENGTH_SHORT).show();
				} else {
					final Dialog dialog = new Dialog(InventoryActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.notes_layout);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);
					final EditText notesedit = (EditText) dialog
							.findViewById(R.id.notestext);
					notesedit.setText(notesval);

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							notesval = notesedit.getText().toString();
							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		modifiersed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				final String itemnumber = itemnoed.getText().toString();
				if (itemnumber.equals("")) {
					Toast.makeText(
							getApplicationContext(),
							"You must enter an item number before saving this item.",
							Toast.LENGTH_SHORT).show();
				} else {
					final Dialog dialog = new Dialog(InventoryActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.modifiers_layout);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);
					final Spinner modifieritems = (Spinner) dialog
							.findViewById(R.id.modifieritemspinner);
					modifierlist = (ListView) dialog
							.findViewById(R.id.modifieritemslist);
					ArrayList<String> modifieritemspinnerarray = new ArrayList<String>();
					modifieritemspinnerarray.add("Select");
					
		
					
					String selectQueryformodifierspinner = "SELECT  "
							+ DatabaseForDemo.INVENTORY_ITEM_NO + " FROM "
							+ DatabaseForDemo.OPTIONAL_INFO_TABLE + " where "
							+ DatabaseForDemo.INVENTORY_MODIFIER_ITEM
							+ "=\"yes\"";
					Cursor mCursorformodifierspinner =dbforloginlogoutReadInvetory.rawQuery(
							selectQueryformodifierspinner, null);
					if (mCursorformodifierspinner != null) {
						if (mCursorformodifierspinner.moveToFirst()) {
							do {
								String itemid = mCursorformodifierspinner.getString(mCursorformodifierspinner
										.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NO));
								modifieritemspinnerarray.add(itemid);
							} while (mCursorformodifierspinner.moveToNext());
						}
					}
					mCursorformodifierspinner.close();
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							InventoryActivity.this,
							android.R.layout.simple_spinner_item,
							modifieritemspinnerarray);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
					modifieritems.setAdapter(adapter);

					if (modifierindividualitemsarray != null) {
						ModifierIndividualItemAdapter adapterformod = new ModifierIndividualItemAdapter(
								InventoryActivity.this,
								modifierindividualitemsarray);
						adapterformod.setListener(InventoryActivity.this);
						modifierlist.setAdapter(adapterformod);
					}

					modifieritems
							.setOnItemSelectedListener(new OnItemSelectedListener() {

								@Override
								public void onItemSelected(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									// TODO Auto-generated method stub
									String itemnoinspinner = modifieritems
											.getSelectedItem().toString();
									if (itemnoinspinner.equals("")
											|| itemnoinspinner.equals("Select")) {

									} else {
										System.out.println("else is executed");
										for (int i = 0; i < modifierindividualitemsarray
												.size(); i++) {
											if (modifierindividualitemsarray
													.get(i).containsValue(
															itemnoinspinner)) {
												Toast.makeText(
														InventoryActivity.this,
														"Modifier already exist",
														Toast.LENGTH_SHORT)
														.show();
												System.out
														.println("same is found is executed");
												status = false;
												break;
											}
										}
										if (status == true) {
											
				
											
						
											String selectQuery = "SELECT  "
													+ DatabaseForDemo.INVENTORY_ITEM_NO
													+ ", "
													+ DatabaseForDemo.INVENTORY_ITEM_NAME
													+ " FROM "
													+ DatabaseForDemo.INVENTORY_TABLE
													+ " where "
													+ DatabaseForDemo.INVENTORY_ITEM_NO
													+ "=\"" + itemnoinspinner
													+ "\"";
											Cursor mCursor =dbforloginlogoutReadInvetory.rawQuery(
													selectQuery, null);
											if (mCursor != null) {
												if (mCursor.moveToFirst()) {
													do {
														HashMap<String, String> map = new HashMap<String, String>();
														String itemid, itemname;
														itemid = mCursor
																.getString(mCursor
																		.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NO));
														itemname = mCursor
																.getString(mCursor
																		.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NAME));
														map.put("itemno",
																itemid);
														map.put("itemname",
																itemname);
														modifierindividualitemsarray
																.add(map);
													} while (mCursor
															.moveToNext());
												}
												
												
											}
											mCursor.close();
											if (modifierindividualitemsarray != null) {
												ModifierIndividualItemAdapter adapterformod = new ModifierIndividualItemAdapter(
														InventoryActivity.this,
														modifierindividualitemsarray);
												adapterformod
														.setListener(InventoryActivity.this);
												modifierlist
														.setAdapter(adapterformod);
											}

										}
									}
									status = true;
								}

								@Override
								public void onNothingSelected(
										AdapterView<?> arg0) {
									// TODO Auto-generated method stub

								}
							});

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		orderinginfoed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String itemnumber = itemnoed.getText().toString();
				if (itemnumber.equals("")) {
					Toast.makeText(
							getApplicationContext(),
							"You must enter an item number before saving this item.",
							Toast.LENGTH_SHORT).show();
				} else {
					final Dialog dialog = new Dialog(InventoryActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.ordering_info_layout);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);
					final Spinner vendortotaldetails = (Spinner) dialog
							.findViewById(R.id.vendorinorderinginfo);
					vendorlist = (ListView) dialog
							.findViewById(R.id.orderinginfolist);
					ArrayList<String> vendorspinnerdatafororderinginfo = new ArrayList<String>();
					vendorspinnerdatafororderinginfo.clear();
					vendorspinnerdatafororderinginfo.add("Select");

					
		
					Cursor mCursor = dbforloginlogoutReadInvetory.rawQuery(
							"select " + DatabaseForDemo.VENDOR_COMPANY_NAME
									+ " from " + DatabaseForDemo.VENDOR_TABLE,
							null);
					System.out.println(mCursor);
					if (mCursor != null) {
						if (mCursor.moveToFirst()) {
							do {
								String catid = mCursor.getString(mCursor
										.getColumnIndex(DatabaseForDemo.VENDOR_COMPANY_NAME));
								vendorspinnerdatafororderinginfo.add(catid);
							} while (mCursor.moveToNext());
						}
						
						
					}
					mCursor.close();
					ArrayAdapter<String> adapter = new ArrayAdapter<String>(
							InventoryActivity.this,
							android.R.layout.simple_spinner_item,
							vendorspinnerdatafororderinginfo);
					adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
					vendortotaldetails.setAdapter(adapter);

					if (pricingvendorlistinorderinginfo != null) {
						OrderingInfoIndividualVendorAdapter adapterformod = new OrderingInfoIndividualVendorAdapter(
								InventoryActivity.this,
								pricingvendorlistinorderinginfo);
						adapterformod.setListener(InventoryActivity.this);
						vendorlist.setAdapter(adapterformod);
					}

					vendortotaldetails
							.setOnItemSelectedListener(new OnItemSelectedListener() {

								@Override
								public void onItemSelected(AdapterView<?> arg0,
										View arg1, int arg2, long arg3) {
									// TODO Auto-generated method stub
									final String vendorinspinner = vendortotaldetails
											.getSelectedItem().toString();
									if (vendorinspinner.equals("")
											|| vendorinspinner.equals("Select")) {

									} else {
										System.out.println("else is executed");
										for (int i = 0; i < pricingvendorlistinorderinginfo
												.size(); i++) {
											if (pricingvendorlistinorderinginfo
													.get(i).containsValue(
															vendorinspinner)) {
												Toast.makeText(
														InventoryActivity.this,
														"Vendor already exist",
														Toast.LENGTH_SHORT)
														.show();
												System.out
														.println("same is found is executed");
												statusforvendor = false;
												break;
											}
										}
										if (statusforvendor == true) {
											System.out
													.println("different is found");

											final AlertDialog alertDialog1 = new AlertDialog.Builder(
													InventoryActivity.this)
													.create();
											LayoutInflater mInflater = LayoutInflater
													.from(InventoryActivity.this);
											View layout = mInflater
													.inflate(
															R.layout.orderinginfopopups,
															null);
											Button ok = (Button) layout
													.findViewById(R.id.save);
											Button cancel = (Button) layout
													.findViewById(R.id.cancel);
											final EditText partno = (EditText) layout
													.findViewById(R.id.partno);
											final EditText costperase = (EditText) layout
													.findViewById(R.id.cost_case);
											final EditText casecost = (EditText) layout
													.findViewById(R.id.case_cost);
											final EditText noincase = (EditText) layout
													.findViewById(R.id.num_in_case);
											final RadioButton trueval = (RadioButton) layout
													.findViewById(R.id.radiobuttonyes);
											final RadioButton falseval = (RadioButton) layout
													.findViewById(R.id.radiobuttonno);
											noincase.setOnEditorActionListener(new OnEditorActionListener() {
												@Override
												public boolean onEditorAction(TextView v, int actionId,
														KeyEvent event) {
													if (actionId == EditorInfo.IME_ACTION_DONE) {
														InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
														imm.hideSoftInputFromWindow(noincase.getWindowToken(), 0);
													}
													return false;
												}
											});
											alertDialog1
													.setTitle("Add Vendor details");

											ok.setOnClickListener(new OnClickListener() {

												@Override
												public void onClick(View arg0) {
													// TODO Auto-generated
													// method stub
													String prferredtextval = "";
													if (trueval.isChecked()) {
														System.out
																.println("preferred val is true");
														prferredtextval = "true";
													}
													if (falseval.isChecked()) {
														System.out
																.println("preferred val is false");
														prferredtextval = "false";
													}
													if (prferredtextval
															.equals("true")) {
														int positionval = 0;
														for (int i = 0; i < pricingvendorlistinorderinginfo
																.size(); i++) {
															if (pricingvendorlistinorderinginfo
																	.get(i)
																	.containsValue(
																			"true")) {
																Toast.makeText(
																		InventoryActivity.this,
																		"true already exist",
																		Toast.LENGTH_SHORT)
																		.show();
																System.out
																		.println("same is found is executed");
																statusfortrue = false;
																positionval = i;
																System.out
																		.println("position val is:"
																				+ positionval);
																break;
															}
														}
														if (statusfortrue == true) {
															System.out
																	.println("true is not there");
															HashMap<String, String> map = new HashMap<String, String>();
															map.put("vendorname",
																	vendorinspinner);
															map.put("partno",
																	partno.getText()
																			.toString());
															map.put("costpercase",
																	costperase
																			.getText()
																			.toString());
															map.put("casecost",
																	casecost.getText()
																			.toString());
															map.put("noincase",
																	noincase.getText()
																			.toString());
															map.put("preferred",
																	prferredtextval);
															pricingvendorlistinorderinginfo
																	.add(map);
														} else {
															System.out
																	.println("true is there");
															pricingvendorlistinorderinginfo
																	.get(positionval)
																	.put("preferred",
																			"false");
															System.out
																	.println("prferred value is changed");
															HashMap<String, String> map = new HashMap<String, String>();
															map.put("vendorname",
																	vendorinspinner);
															map.put("partno",
																	partno.getText()
																			.toString());
															map.put("costpercase",
																	costperase
																			.getText()
																			.toString());
															map.put("casecost",
																	casecost.getText()
																			.toString());
															map.put("noincase",
																	noincase.getText()
																			.toString());
															map.put("preferred",
																	prferredtextval);
															pricingvendorlistinorderinginfo
																	.add(map);
														}
													} else {
														System.out
																.println("preferred val is false");
														HashMap<String, String> map = new HashMap<String, String>();
														map.put("vendorname",
																vendorinspinner);
														map.put("partno",
																partno.getText()
																		.toString());
														map.put("costpercase",
																costperase
																		.getText()
																		.toString());
														map.put("casecost",
																casecost.getText()
																		.toString());
														map.put("noincase",
																noincase.getText()
																		.toString());
														map.put("preferred",
																prferredtextval);
														pricingvendorlistinorderinginfo
																.add(map);
													}

													if (pricingvendorlistinorderinginfo != null) {
														OrderingInfoIndividualVendorAdapter adapterformod = new OrderingInfoIndividualVendorAdapter(
																InventoryActivity.this,
																pricingvendorlistinorderinginfo);
														adapterformod
																.setListener(InventoryActivity.this);
														vendorlist
																.setAdapter(adapterformod);
													}
													statusfortrue = true;
													alertDialog1.dismiss();
												}
											});
											cancel.setOnClickListener(new OnClickListener() {

												@Override
												public void onClick(View arg0) {
													// TODO Auto-generated
													// method stub
													alertDialog1.dismiss();
												}
											});
											alertDialog1.setView(layout);
											alertDialog1.show();
										}
									}
									statusforvendor = true;
								}

								@Override
								public void onNothingSelected(
										AdapterView<?> arg0) {
									// TODO Auto-generated method stub

								}
							});

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		saleshistoryed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String itemnumber = itemnoed.getText().toString();
				if (itemnumber.equals("")) {
					Toast.makeText(
							getApplicationContext(),
							"You must enter an item number before saving this item.",
							Toast.LENGTH_SHORT).show();
				} else {
					final Dialog dialog = new Dialog(InventoryActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.sales_history_layout);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);

					saleslist = (ListView) dialog
							.findViewById(R.id.pricelevelslist);

					ArrayList<String> dateandtimelist = new ArrayList<String>();
					ArrayList<String> storeidlist = new ArrayList<String>();
					ArrayList<String> invoicelist = new ArrayList<String>();
					ArrayList<String> quantitylist = new ArrayList<String>();
					ArrayList<String> pricelist = new ArrayList<String>();
					ArrayList<String> costlist = new ArrayList<String>();
					ArrayList<String> custlist = new ArrayList<String>();
					ArrayList<String> seriallist = new ArrayList<String>();

					
		
					

					String query = "SELECT *from "
							+ DatabaseForDemo.INVOICE_ITEMS_TABLE + " where "
							+ DatabaseForDemo.INVOICE_ITEM_ID + "=\""
							+ itemnumber + "\"";
					Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(query, null);

					if (cursor.getCount() > 0) {
						if (cursor != null) {
							if (cursor.moveToFirst()) {
								do {
									int i = 1;
									if (cursor.isNull(cursor
											.getColumnIndex(DatabaseForDemo.CREATED_DATE))) {
										dateandtimelist.add("");
									} else {
										String dateandtime = cursor.getString(cursor
												.getColumnIndex(DatabaseForDemo.CREATED_DATE));
										dateandtimelist.add(dateandtime);
									}
									if (cursor.isNull(cursor
											.getColumnIndex(DatabaseForDemo.INVOICE_STORE_ID))) {
										storeidlist.add("");
									} else {
										String storeid = cursor.getString(cursor
												.getColumnIndex(DatabaseForDemo.INVOICE_STORE_ID));
										storeidlist.add(storeid);
									}
									if (cursor.isNull(cursor
											.getColumnIndex(DatabaseForDemo.INVOICE_ID))) {
										invoicelist.add("");
									} else {
										String invoiceid = cursor.getString(cursor
												.getColumnIndex(DatabaseForDemo.INVOICE_ID));
										invoicelist.add(invoiceid);
									}
									if (cursor.isNull(cursor
											.getColumnIndex(DatabaseForDemo.INVOICE_QUANTITY))) {
										quantitylist.add("");
									} else {
										String qtylist = cursor.getString(cursor
												.getColumnIndex(DatabaseForDemo.INVOICE_QUANTITY));
										quantitylist.add(qtylist);
									}
									if (cursor.isNull(cursor
											.getColumnIndex(DatabaseForDemo.INVOICE_YOUR_COST))) {
										pricelist.add("");
									} else {
										String price = cursor.getString(cursor
												.getColumnIndex(DatabaseForDemo.INVOICE_YOUR_COST));
										pricelist.add(price);
									}
									if (cursor.isNull(cursor
											.getColumnIndex(DatabaseForDemo.INVOICE_AVG_COST))) {
										costlist.add("");
									} else {
										String cost = cursor.getString(cursor
												.getColumnIndex(DatabaseForDemo.INVOICE_AVG_COST));
										costlist.add(cost);
									}
									seriallist.add("" + i);
									i++;
								} while (cursor.moveToNext());
							}
						}
					}
					cursor.close();
					for (int ii = 0; ii < invoicelist.size(); ii++) {
						String selectQueryforshipping = "SELECT  * FROM "
								+ DatabaseForDemo.INVOICE_TOTAL_TABLE
								+ " where " + DatabaseForDemo.INVOICE_ID + "=\""
								+ invoicelist.get(ii) + "\"";

						Cursor mCursorforshipping =dbforloginlogoutReadInvetory.rawQuery(
								selectQueryforshipping, null);

						if (mCursorforshipping.getCount() > 0) {
							if (mCursorforshipping != null) {
								if (mCursorforshipping.moveToFirst()) {
									do {
										if (mCursorforshipping.isNull(mCursorforshipping
												.getColumnIndex(DatabaseForDemo.INVOICE_CUSTOMER))) {
											custlist.add("");
										} else {
											String address = mCursorforshipping
													.getString(mCursorforshipping
															.getColumnIndex(DatabaseForDemo.INVOICE_CUSTOMER));
											custlist.add(address);
										}
									} while (mCursorforshipping.moveToNext());
								}
							}
						}
						mCursorforshipping.close();
					}

					ImageAdapterForSalesHistory adapter = new ImageAdapterForSalesHistory(
							InventoryActivity.this, dateandtimelist,
							storeidlist, invoicelist, quantitylist, pricelist,
							costlist, custlist, seriallist);
					saleslist.setAdapter(adapter);

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		printersed.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				String itemnumber = itemnoed.getText().toString();
				if (itemnumber.equals("")) {
					Toast.makeText(
							getApplicationContext(),
							"You must enter an item number before saving this item.",
							Toast.LENGTH_SHORT).show();
				} else {
					final Dialog dialog = new Dialog(InventoryActivity.this);
					dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
					dialog.setContentView(R.layout.printers_layout);
					Button save = (Button) dialog.findViewById(R.id.savecat);
					Button cancel = (Button) dialog
							.findViewById(R.id.cancelcat);

					save.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});

					cancel.setOnClickListener(new OnClickListener() {

						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							dialog.dismiss();
						}
					});
					dialog.show();
				}
			}
		});

		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				try{
				final String uniqueidval = Parameters.randomValue();
				final String itemnumber = itemnoed.getText().toString();
				final String itemname = itemnameed.getText().toString();
				final String description = desc2ed.getText().toString();
				final String costvalforsave = costed.getText().toString();
				final String price_charge = pricechargeed.getText().toString();
				String price_tax = pricetaxed.getText().toString();
				final String instockval = stocked.getText().toString();
				final String depart = departmentforproduct.getSelectedItem()
						.toString();
				// final String vendor =
				// vendorproduct.getSelectedItem().toString();
				taxvalforsaveforduplicate = "";
				String costval = pricechargeed.getText().toString();
				taxvalforduplicate = 0;
				if (tax1check.isChecked()) {
					taxvalforsaveforduplicate = taxvalforsaveforduplicate
							+ tax1check.getText().toString() + ",";
					double cost = Double.valueOf(costval);

					String query = "select * from " + DatabaseForDemo.TAX_TABLE
							+ " where " + DatabaseForDemo.TAX_NAME + "=\""
							+ tax1check.getText().toString() + "\"";
					System.out.println(query);
					
				
					

					Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(query, null);
					if (cursor != null) {
						if (cursor.moveToFirst()) {
							do {
								double taxvalpercent = Double.valueOf(df
										.format(cursor.getDouble(cursor
										.getColumnIndex(DatabaseForDemo.TAX_VALUE))));
								System.out.println("     " + taxvalpercent);
								taxvalforduplicate = cost
										* (taxvalpercent / 100);
							} while (cursor.moveToNext());
						}
						
						
					} else {
						Toast.makeText(getApplicationContext(), "No Tax value",
								Toast.LENGTH_SHORT).show();
					}
cursor.close();
				}
				if (tax2check.isChecked()) {
					taxvalforsaveforduplicate = taxvalforsaveforduplicate
							+ tax2check.getText().toString() + ",";
					double cost = Double.valueOf(costval);

					String query = "select " + DatabaseForDemo.TAX_VALUE
							+ " from " + DatabaseForDemo.TAX_TABLE + " where "
							+ DatabaseForDemo.TAX_NAME + "=\""
							+ tax2check.getText().toString() + "\"";
					System.out.println(query);
					
				
					
					Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(query, null);
					if (cursor != null) {
						if (cursor.moveToFirst()) {
							do {
								double taxvalpercent = Double.valueOf(df
										.format(cursor.getDouble(cursor
										.getColumnIndex(DatabaseForDemo.TAX_VALUE))));
								System.out.println("     " + taxvalpercent);
								taxvalforduplicate = taxvalforduplicate
										+ (cost * (taxvalpercent / 100));
							} while (cursor.moveToNext());
						}
						
						
					} else {
						Toast.makeText(getApplicationContext(), "No Tax value",
								Toast.LENGTH_SHORT).show();
					}
					cursor.close();
				}
				if (tax3check.isChecked()) {
					taxvalforsaveforduplicate = taxvalforsaveforduplicate
							+ tax3check.getText().toString() + ",";
					double cost = Double.valueOf(costval);

					String query = "select " + DatabaseForDemo.TAX_VALUE
							+ " from " + DatabaseForDemo.TAX_TABLE + " where "
							+ DatabaseForDemo.TAX_NAME + "=\""
							+ tax3check.getText().toString() + "\"";
					System.out.println(query);
					
				
					

					Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(query, null);
					if (cursor != null) {
						if (cursor.moveToFirst()) {
							do {
								double taxvalpercent = Double.valueOf(df
										.format(cursor.getDouble(cursor
										.getColumnIndex(DatabaseForDemo.TAX_VALUE))));
								System.out.println("     " + taxvalpercent);
								taxvalforduplicate = taxvalforduplicate
										+ (cost * (taxvalpercent / 100));
							} while (cursor.moveToNext());
						}
						
						
					} else {
						Toast.makeText(getApplicationContext(), "No Tax value",
								Toast.LENGTH_SHORT).show();
					}
					cursor.close();
				}
				if (bartaxcheck.isChecked()) {
					taxvalforsaveforduplicate = taxvalforsaveforduplicate
							+ bartaxcheck.getText().toString();
					taxvalforduplicate = taxvalforduplicate + 0;
					System.out.println("     " + taxvalforduplicate);
				}
				System.out.println(taxvalforsaveforduplicate);
				System.out.println("taxvalue is padma padma:"
						+ taxvalforduplicate);
				
				if(costval.equals(price_tax)){
					Double tpricetaxval = Double.parseDouble(price_tax)+taxvalforduplicate;
					price_tax = tpricetaxval.toString();
					System.out.println("pricetax in if:"+price_tax);
				}else{
					System.out.println("pricetax in else:"+price_tax);
				}

				if (departmentforproduct.getSelectedItem().toString()
						.equals("")
						|| itemnoed.getText().toString().equals("")
						|| itemnameed.getText().toString().equals("")
						|| desc2ed.getText().toString().equals("")
						|| costed.getText().toString().equals("")
						|| pricechargeed.getText().toString().equals("")
						|| pricetaxed.getText().toString().equals("")
						|| stocked.getText().toString().equals("")) {
					Toast.makeText(InventoryActivity.this,
							"Please enter details", Toast.LENGTH_SHORT).show();
				} else {

					
		
					
					String selectQuery = "SELECT  * FROM "
							+ DatabaseForDemo.INVENTORY_TABLE + " where "
							+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\""
							+ itemnoed.getText().toString() + "\"";

					Cursor mCursor =dbforloginlogoutReadInvetory.rawQuery(selectQuery, null);
					if (mCursor.getCount() > 0) {
						Toast.makeText(InventoryActivity.this,
								"Product already exist", Toast.LENGTH_SHORT)
								.show();
						
					} else {
						
						ContentValues contentValues = new ContentValues();
						contentValues.put(DatabaseForDemo.UNIQUE_ID,
								uniqueidval);
						contentValues.put(DatabaseForDemo.CREATED_DATE,
								Parameters.currentTime());
						contentValues.put(DatabaseForDemo.MODIFIED_DATE,
								Parameters.currentTime());
						contentValues.put(DatabaseForDemo.MODIFIED_IN, "Local");
						contentValues.put(DatabaseForDemo.INVENTORY_DEPARTMENT,
								depart);
						contentValues.put(DatabaseForDemo.INVENTORY_ITEM_NO,
								itemnumber);
						contentValues.put(DatabaseForDemo.INVENTORY_ITEM_NAME,
								itemname);
						contentValues.put(
								DatabaseForDemo.INVENTORY_SECOND_DESCRIPTION,
								description);
						contentValues.put(DatabaseForDemo.INVENTORY_AVG_COST,
								costvalforsave);
						contentValues.put(
								DatabaseForDemo.INVENTORY_PRICE_CHANGE,
								price_charge);
						contentValues.put(DatabaseForDemo.INVENTORY_PRICE_TAX,
								price_tax);
						contentValues.put(DatabaseForDemo.INVENTORY_IN_STOCK,
								instockval);
						// contentValues.put(DatabaseForDemo.INVENTORY_VENDOR,
						// vendor);
						contentValues.put(DatabaseForDemo.INVENTORY_TAXONE,
								taxvalforsaveforduplicate);
						contentValues.put(DatabaseForDemo.INVENTORY_QUANTITY,
								"1");
						contentValues
								.put(DatabaseForDemo.CHECKED_VALUE, "true");
						contentValues.put(DatabaseForDemo.INVENTORY_TOTAL_TAX,
								taxvalforduplicate);
						contentValues.put(DatabaseForDemo.INVENTORY_NOTES,
								notesval);
						dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.INVENTORY_TABLE, null,
								contentValues);

						optional_info = new ContentValues();
						optional_info.put(DatabaseForDemo.UNIQUE_ID,
								uniqueidval);
						optional_info.put(DatabaseForDemo.CREATED_DATE,
								Parameters.currentTime());
						optional_info.put(DatabaseForDemo.MODIFIED_DATE,
								Parameters.currentTime());
						optional_info.put(DatabaseForDemo.MODIFIED_IN, "Local");
						optional_info.put(DatabaseForDemo.BONUS_POINTS,
								bonuspointsval);
						optional_info.put(DatabaseForDemo.BARCODES, barcodeval);
						optional_info
								.put(DatabaseForDemo.LOCATION, locationval);
						optional_info.put(DatabaseForDemo.UNIT_SIZE,
								unitsizeval);
						optional_info.put(DatabaseForDemo.UNIT_TYPE,
								unittypeval);
						optional_info.put(
								DatabaseForDemo.COMMISSION_OPTIONAL_INFO,
								commissionval);
						optional_info.put(
								DatabaseForDemo.INVENTORY_ALLOW_BUYBACK,
								allowbuybackval);
						optional_info.put(
								DatabaseForDemo.INVENTORY_PROMPT_PRICE,
								promptpriceval);
						optional_info.put(
								DatabaseForDemo.INVENTORY_FOODSTAMPABLE,
								foodstampableval);
						optional_info.put(
								DatabaseForDemo.INVENTORY_PRINT_ON_RECEIPT,
								printonreceiptval);
						optional_info.put(
								DatabaseForDemo.INVENTORY_COUNT_THIS_ITEM,
								countthisitemval);
						optional_info.put(
								DatabaseForDemo.INVENTORY_MODIFIER_ITEM,
								modifieritemval);
						optional_info.put(DatabaseForDemo.INVENTORY_ITEM_NO,
								itemnumber);
						dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.OPTIONAL_INFO_TABLE, null,
								optional_info);

						for (int i = 0; i < skuarray.size(); i++) {
							ContentValues alternate_sku = new ContentValues();
							alternate_sku.put(DatabaseForDemo.UNIQUE_ID,
									uniqueidval);
							alternate_sku.put(DatabaseForDemo.CREATED_DATE,
									Parameters.currentTime());
							alternate_sku.put(DatabaseForDemo.MODIFIED_DATE,
									Parameters.currentTime());
							alternate_sku.put(DatabaseForDemo.MODIFIED_IN,
									"Local");
							alternate_sku.put(
									DatabaseForDemo.ALTERNATE_SKU_VALUE,
									skuarray.get(i));
							alternate_sku.put(
									DatabaseForDemo.INVENTORY_ITEM_NO,
									itemnumber);
							dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.ALTERNATE_SKU_TABLE,
									null, alternate_sku);
						}
						modifiersArrayserver.clear();
						for (int i = 0; i < modifierindividualitemsarray.size(); i++) {
							String rrrr=Parameters.randomValue();
							ContentValues modifier_value = new ContentValues();
							modifier_value.put(DatabaseForDemo.UNIQUE_ID,
									rrrr);
							modifier_value.put(DatabaseForDemo.CREATED_DATE,
									Parameters.currentTime());
							modifier_value.put(DatabaseForDemo.MODIFIED_DATE,
									Parameters.currentTime());
							modifier_value.put(DatabaseForDemo.MODIFIED_IN,
									"Local");
							modifier_value.put(
									DatabaseForDemo.MODIFIER_ITEM_NO,
									modifierindividualitemsarray.get(i).get(
											"itemno"));
							Log.e("itemno", ""+modifierindividualitemsarray.get(i).get(
									"itemno"));
							modifier_value.put(
									DatabaseForDemo.INVENTORY_ITEM_NAME,
									modifierindividualitemsarray.get(i).get(
											"itemname"));
							Log.e("itemname", ""+modifierindividualitemsarray.get(i).get(
									"itemname"));
							modifier_value.put(
									DatabaseForDemo.INVENTORY_ITEM_NO,
									itemnumber);
							Log.e("itemname", ""+itemnumber);
							dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.MODIFIER_TABLE, null,
									modifier_value);
							try {
								JSONObject data = new JSONObject();
								JSONObject jsonobj = new JSONObject();
								JSONArray fields = new JSONArray();
									jsonobj.put(DatabaseForDemo.UNIQUE_ID,
											rrrr);
									jsonobj.put(DatabaseForDemo.CREATED_DATE,
											Parameters.currentTime());
									jsonobj.put(DatabaseForDemo.MODIFIED_DATE,
											Parameters.currentTime());
									jsonobj
											.put(DatabaseForDemo.MODIFIED_IN, "Local");
									jsonobj.put(
											DatabaseForDemo.MODIFIER_ITEM_NO,
											modifierindividualitemsarray.get(i).get(
													"itemno"));
									jsonobj.put(
											DatabaseForDemo.INVENTORY_ITEM_NAME,
											modifierindividualitemsarray.get(i).get(
													"itemname"));
									jsonobj.put(
											DatabaseForDemo.INVENTORY_ITEM_NO,
											itemnumber);
									
									Log.e("nn srihari "+modifierindividualitemsarray.get(i)
											.get("itemname"),"id "+modifierindividualitemsarray.get(i)
											.get("itemno"));
									fields.put(0, jsonobj);
								data.put("fields", fields);
								datavalforproduct3 = data.toString();
								modifiersArrayserver.add(datavalforproduct3);
							} catch (JSONException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}

						for (int i = 0; i < pricingvendorlistinorderinginfo
								.size(); i++) {
							ContentValues ordering_info = new ContentValues();
							ordering_info.put(DatabaseForDemo.UNIQUE_ID,
									uniqueidval);
							ordering_info.put(DatabaseForDemo.CREATED_DATE,
									Parameters.currentTime());
							ordering_info.put(DatabaseForDemo.MODIFIED_DATE,
									Parameters.currentTime());
							ordering_info.put(DatabaseForDemo.MODIFIED_IN,
									"Local");
							ordering_info.put(
									DatabaseForDemo.VENDOR_COMPANY_NAME,
									pricingvendorlistinorderinginfo.get(i).get(
											"vendorname"));
							ordering_info.put(
									DatabaseForDemo.VENDERPART_NO,
									pricingvendorlistinorderinginfo.get(i).get(
											"partno"));
							ordering_info.put(
									DatabaseForDemo.COST_PER,
									pricingvendorlistinorderinginfo.get(i).get(
											"costperase"));
							ordering_info.put(
									DatabaseForDemo.CASE_COST,
									pricingvendorlistinorderinginfo.get(i).get(
											"casecost"));
							ordering_info.put(
									DatabaseForDemo.NO_IN_CASE,
									pricingvendorlistinorderinginfo.get(i).get(
											"noincase"));
							ordering_info.put(
									DatabaseForDemo.PREFERRED,
									pricingvendorlistinorderinginfo.get(i).get(
											"preferred"));
							ordering_info.put(
									DatabaseForDemo.INVENTORY_ITEM_NO,
									itemnumber);
							dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.ORDERING_INFO_TABLE,
									null, ordering_info);
						}

						try {
							JSONObject data = new JSONObject();
							JSONObject jsonobj = new JSONObject();
							jsonobj.put(DatabaseForDemo.INVENTORY_DEPARTMENT,
									depart);
							jsonobj.put(DatabaseForDemo.INVENTORY_ITEM_NO,
									itemnumber);
							jsonobj.put(DatabaseForDemo.INVENTORY_ITEM_NAME,
									itemname);
							jsonobj.put(
									DatabaseForDemo.INVENTORY_SECOND_DESCRIPTION,
									description);
							jsonobj.put(DatabaseForDemo.INVENTORY_AVG_COST,
									costvalforsave);
							jsonobj.put(DatabaseForDemo.INVENTORY_PRICE_CHANGE,
									price_charge);
							jsonobj.put(DatabaseForDemo.INVENTORY_PRICE_TAX,
									price_tax);
							jsonobj.put(DatabaseForDemo.INVENTORY_IN_STOCK,
									instockval);
							// jsonobj.put(DatabaseForDemo.INVENTORY_VENDOR,
							// vendor);
							jsonobj.put(DatabaseForDemo.INVENTORY_TAXONE,
									taxvalforsaveforduplicate);
							jsonobj.put(DatabaseForDemo.INVENTORY_NOTES,
									notesval);
							jsonobj.put(DatabaseForDemo.INVENTORY_QUANTITY, "1");
							jsonobj.put(DatabaseForDemo.INVENTORY_TOTAL_TAX,
									taxvalforduplicate);
							jsonobj.put(
									DatabaseForDemo.CHECKED_VALUE,
									"true");
							jsonobj.put(DatabaseForDemo.UNIQUE_ID, uniqueidval);
							JSONArray fields = new JSONArray();
							fields.put(0, jsonobj);
							data.put("fields", fields);
							dataval = data.toString();
							
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						try {
							JSONObject data = new JSONObject();
							JSONObject jsonobj = new JSONObject();
							jsonobj.put(DatabaseForDemo.BONUS_POINTS,
									bonuspointsval);
							jsonobj.put(DatabaseForDemo.BARCODES, barcodeval);
							jsonobj.put(DatabaseForDemo.LOCATION, locationval);
							jsonobj.put(DatabaseForDemo.UNIT_SIZE, unitsizeval);
							jsonobj.put(DatabaseForDemo.UNIT_TYPE, unittypeval);
							jsonobj.put(
									DatabaseForDemo.COMMISSION_OPTIONAL_INFO,
									commissionval);
							jsonobj.put(
									DatabaseForDemo.INVENTORY_ALLOW_BUYBACK,
									allowbuybackval);
							jsonobj.put(DatabaseForDemo.INVENTORY_PROMPT_PRICE,
									promptpriceval);
							jsonobj.put(
									DatabaseForDemo.INVENTORY_FOODSTAMPABLE,
									foodstampableval);
							jsonobj.put(
									DatabaseForDemo.INVENTORY_PRINT_ON_RECEIPT,
									printonreceiptval);
							jsonobj.put(
									DatabaseForDemo.INVENTORY_COUNT_THIS_ITEM,
									countthisitemval);
							jsonobj.put(
									DatabaseForDemo.INVENTORY_MODIFIER_ITEM,
									modifieritemval);
							jsonobj.put(DatabaseForDemo.INVENTORY_ITEM_NO,
									itemnumber);
							jsonobj.put(DatabaseForDemo.UNIQUE_ID, uniqueidval);
							JSONArray fields = new JSONArray();
							fields.put(0, jsonobj);
							data.put("fields", fields);
							datavalforproduct1 = data.toString();
							
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						try {
							JSONObject data = new JSONObject();
							JSONObject jsonobj = new JSONObject();
							JSONArray fields = new JSONArray();

							for (int i = 0; i < skuarray.size(); i++) {
								fields.put(i, jsonobj);
								jsonobj.put(
										DatabaseForDemo.ALTERNATE_SKU_VALUE,
										skuarray.get(i));
								jsonobj.put(DatabaseForDemo.INVENTORY_ITEM_NO,
										itemnumber);
								jsonobj.put(DatabaseForDemo.UNIQUE_ID,
										uniqueidval);
							}
							data.put("fields", fields);
							datavalforproduct2 = data.toString();
							
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

					

						try {
							JSONObject data = new JSONObject();
							JSONObject jsonobj = new JSONObject();
							JSONArray fields = new JSONArray();

							for (int i = 0; i < pricingvendorlistinorderinginfo
									.size(); i++) {
								fields.put(i, jsonobj);
								jsonobj.put(
										DatabaseForDemo.VENDOR_COMPANY_NAME,
										pricingvendorlistinorderinginfo.get(i)
												.get("vendorname"));
								jsonobj.put(DatabaseForDemo.VENDERPART_NO,
										pricingvendorlistinorderinginfo.get(i)
												.get("partno"));
								jsonobj.put(DatabaseForDemo.COST_PER,
										pricingvendorlistinorderinginfo.get(i)
												.get("costperase"));
								jsonobj.put(DatabaseForDemo.CASE_COST,
										pricingvendorlistinorderinginfo.get(i)
												.get("casecost"));
								jsonobj.put(DatabaseForDemo.NO_IN_CASE,
										pricingvendorlistinorderinginfo.get(i)
												.get("noincase"));
								jsonobj.put(DatabaseForDemo.PREFERRED,
										pricingvendorlistinorderinginfo.get(i)
												.get("preferred"));
								jsonobj.put(DatabaseForDemo.INVENTORY_ITEM_NO,
										itemnumber);
								jsonobj.put(DatabaseForDemo.UNIQUE_ID,
										uniqueidval);
							}
							data.put("fields", fields);
							datavalforproduct4 = data.toString();
							
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						if (Parameters.OriginalUrl.equals("")) {
							System.out.println("there is no server url val");
						} else {
							boolean isnet = Parameters.isNetworkAvailable(InventoryActivity.this);
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
														Parameters
																.currentTime(),
														Parameters
																.currentTime(),
														dataval, "");
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
												System.out
														.println("delete query"
																+ jj + " is :"
																+ deletequery);

												String insertquerytemp = array
														.getString(ii);
												String insertquery1 = insertquerytemp
														.replace("'", "\"");
												String insertquery = insertquery1
														.replace("\\\"", "'");
												System.out
														.println("delete query"
																+ jj + " is :"
																+ insertquery);

											dbforloginlogoutWriteInvetory.execSQL(deletequery);
											dbforloginlogoutWriteInvetory.execSQL(insertquery);
												System.out
														.println("queries executed"
																+ ii);

											}
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

										String responseproduct1 = jsonpost
												.postmethodfordirect(
														"admin",
														"abcdefg",
														DatabaseForDemo.OPTIONAL_INFO_TABLE,
														Parameters
																.currentTime(),
														Parameters
																.currentTime(),
														datavalforproduct1, "");
										System.out.println("response test is:"
												+ responseproduct1);
										try {
											JSONObject obj = new JSONObject(
													responseproduct1);

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
												System.out
														.println("delete query"
																+ jj + " is :"
																+ deletequery);

												String insertquerytemp = array
														.getString(ii);
												String insertquery1 = insertquerytemp
														.replace("'", "\"");
												String insertquery = insertquery1
														.replace("\\\"", "'");
												System.out
														.println("delete query"
																+ jj + " is :"
																+ insertquery);

											dbforloginlogoutWriteInvetory.execSQL(deletequery);
											dbforloginlogoutWriteInvetory.execSQL(insertquery);
												System.out
														.println("queries executed"
																+ ii);

											}
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

										String responseproduct2 = jsonpost
												.postmethodfordirect(
														"admin",
														"abcdefg",
														DatabaseForDemo.ALTERNATE_SKU_TABLE,
														Parameters
																.currentTime(),
														Parameters
																.currentTime(),
														datavalforproduct2, "");
										System.out.println("response test is:"
												+ responseproduct2);
										try {
											JSONObject obj = new JSONObject(
													responseproduct2);

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
												System.out
														.println("delete query"
																+ jj + " is :"
																+ deletequery);

												String insertquerytemp = array
														.getString(ii);
												String insertquery1 = insertquerytemp
														.replace("'", "\"");
												String insertquery = insertquery1
														.replace("\\\"", "'");
												System.out
														.println("delete query"
																+ jj + " is :"
																+ insertquery);

											dbforloginlogoutWriteInvetory.execSQL(deletequery);
											dbforloginlogoutWriteInvetory.execSQL(insertquery);
												System.out
														.println("queries executed"
																+ ii);

											}
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

										for (int i = 0; i < modifiersArrayserver
												.size(); i++) {
										String responseproduct3 = jsonpost
												.postmethodfordirect(
														"admin",
														"abcdefg",
														DatabaseForDemo.MODIFIER_TABLE,
														Parameters
																.currentTime(),
														Parameters
																.currentTime(),
																modifiersArrayserver.get(i),
														"");
										System.out
												.println("response test is:"
														+ responseproduct3);
										try {
											JSONObject obj = new JSONObject(
													responseproduct3);
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
														.replace(
																"'",
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
														.replace(
																"'",
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

											dbforloginlogoutWriteInvetory.execSQL(deletequery);
											dbforloginlogoutWriteInvetory.execSQL(insertquery);
												System.out
														.println("queries executed"
																+ ii);
											}
										} catch (Exception e) {

										}
										}

										String responseproduct4 = jsonpost
												.postmethodfordirect(
														"admin",
														"abcdefg",
														DatabaseForDemo.ORDERING_INFO_TABLE,
														Parameters
																.currentTime(),
														Parameters
																.currentTime(),
														datavalforproduct4, "");
										System.out.println("response test is:"
												+ responseproduct4);

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
												System.out
														.println("delete query"
																+ jj + " is :"
																+ deletequery);

												String insertquerytemp = array
														.getString(ii);
												String insertquery1 = insertquerytemp
														.replace("'", "\"");
												String insertquery = insertquery1
														.replace("\\\"", "'");
												System.out
														.println("delete query"
																+ jj + " is :"
																+ insertquery);

											dbforloginlogoutWriteInvetory.execSQL(deletequery);
											dbforloginlogoutWriteInvetory.execSQL(insertquery);
												System.out
														.println("queries executed"
																+ ii);

											}
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

										String select = "select *from "
												+ DatabaseForDemo.MISCELLANEOUS_TABLE;
										Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(select,
												null);
										if (cursor.getCount() > 0) {
										dbforloginlogoutWriteInvetory.execSQL("update "
													+ DatabaseForDemo.MISCELLANEOUS_TABLE
													+ " set "
													+ DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL
													+ "=\"" + servertiem + "\"");
											
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
											dbforloginlogoutWriteInvetory.insert(
													DatabaseForDemo.MISCELLANEOUS_TABLE,
													null, contentValues1);
											
										}
										cursor.close();
										dataval = "";
										datavalforproduct1 = "";
										datavalforproduct2 = "";
										datavalforproduct3 = "";
										datavalforproduct4 = "";
									}
								}).start();
							} else {
								
	
								
			

								ContentValues contentValues1 = new ContentValues();
								contentValues1.put(DatabaseForDemo.QUERY_TYPE,
										"insert");
								contentValues1.put(
										DatabaseForDemo.PENDING_USER_ID,
										Parameters.userid);
								contentValues1.put(DatabaseForDemo.PAGE_URL,
										"saveinfo.php");
								contentValues1.put(
										DatabaseForDemo.TABLE_NAME_PENDING,
										DatabaseForDemo.INVENTORY_TABLE);
								contentValues1.put(
										DatabaseForDemo.CURRENT_TIME_PENDING,
										Parameters.currentTime());
								contentValues1.put(DatabaseForDemo.PARAMETERS,
										dataval);
								dbforloginlogoutWriteInvetory.insert(
										DatabaseForDemo.PENDING_QUERIES_TABLE,
										null, contentValues1);

								ContentValues contentValues2 = new ContentValues();
								contentValues2.put(DatabaseForDemo.QUERY_TYPE,
										"insert");
								contentValues2.put(
										DatabaseForDemo.PENDING_USER_ID,
										Parameters.userid);
								contentValues2.put(DatabaseForDemo.PAGE_URL,
										"saveinfo.php");
								contentValues2.put(
										DatabaseForDemo.TABLE_NAME_PENDING,
										DatabaseForDemo.OPTIONAL_INFO_TABLE);
								contentValues2.put(
										DatabaseForDemo.CURRENT_TIME_PENDING,
										Parameters.currentTime());
								contentValues2.put(DatabaseForDemo.PARAMETERS,
										datavalforproduct1);
								dbforloginlogoutWriteInvetory.insert(
										DatabaseForDemo.PENDING_QUERIES_TABLE,
										null, contentValues2);

								ContentValues contentValues3 = new ContentValues();
								contentValues3.put(DatabaseForDemo.QUERY_TYPE,
										"insert");
								contentValues3.put(
										DatabaseForDemo.PENDING_USER_ID,
										Parameters.userid);
								contentValues3.put(DatabaseForDemo.PAGE_URL,
										"saveinfo.php");
								contentValues3.put(
										DatabaseForDemo.TABLE_NAME_PENDING,
										DatabaseForDemo.ALTERNATE_SKU_TABLE);
								contentValues3.put(
										DatabaseForDemo.CURRENT_TIME_PENDING,
										Parameters.currentTime());
								contentValues3.put(DatabaseForDemo.PARAMETERS,
										datavalforproduct2);
								dbforloginlogoutWriteInvetory.insert(
										DatabaseForDemo.PENDING_QUERIES_TABLE,
										null, contentValues3);

								ContentValues contentValues4 = new ContentValues();
								contentValues4.put(DatabaseForDemo.QUERY_TYPE,
										"insert");
								contentValues4.put(
										DatabaseForDemo.PENDING_USER_ID,
										Parameters.userid);
								contentValues4.put(DatabaseForDemo.PAGE_URL,
										"saveinfo.php");
								contentValues4.put(
										DatabaseForDemo.TABLE_NAME_PENDING,
										DatabaseForDemo.MODIFIER_TABLE);
								contentValues4.put(
										DatabaseForDemo.CURRENT_TIME_PENDING,
										Parameters.currentTime());
								contentValues4.put(DatabaseForDemo.PARAMETERS,
										datavalforproduct3);
								dbforloginlogoutWriteInvetory.insert(
										DatabaseForDemo.PENDING_QUERIES_TABLE,
										null, contentValues4);

								ContentValues contentValues5 = new ContentValues();
								contentValues5.put(DatabaseForDemo.QUERY_TYPE,
										"insert");
								contentValues5.put(
										DatabaseForDemo.PENDING_USER_ID,
										Parameters.userid);
								contentValues5.put(DatabaseForDemo.PAGE_URL,
										"saveinfo.php");
								contentValues5.put(
										DatabaseForDemo.TABLE_NAME_PENDING,
										DatabaseForDemo.ORDERING_INFO_TABLE);
								contentValues5.put(
										DatabaseForDemo.CURRENT_TIME_PENDING,
										Parameters.currentTime());
								contentValues5.put(DatabaseForDemo.PARAMETERS,
										datavalforproduct4);
								dbforloginlogoutWriteInvetory.insert(
										DatabaseForDemo.PENDING_QUERIES_TABLE,
										null, contentValues5);
								
								
								dataval = "";
								datavalforproduct1 = "";
								datavalforproduct2 = "";
								datavalforproduct3 = "";
								datavalforproduct4 = "";
							}
						}
						modifieritemval = "";
						allowbuybackval = "";
						countthisitemval = "";
						foodstampableval = "";
						promptpriceval = "";
						notesval = "";
						printonreceiptval = "";
						bonuspointsval = "";
						barcodeval = "";
						commissionval = "";
						locationval = "";
						unitsizeval = "";
						unittypeval = "";
						printervalfordept = "";
						foodcheckval = "";
						taxvalforsavefordept = "";
						taxvalforsaveforprod = "";
						taxvalforprod = 0;
						taxvalforduplicate = 0;
						taxvalforsaveforduplicate = "";
						optional_info.clear();
						skuarray.clear();
						modifierindividualitemsarray.clear();
						pricingvendorlistinorderinginfo.clear();
						
						
						gettingCount();
						listUpdateforproduct(offsetvals, "100",
								totalrecordselectQuery);
						alertDialog1.dismiss();
					}
					mCursor.close();
				}
				} catch (NumberFormatException e) {
					  e.printStackTrace();
					} catch (Exception e1) {
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

		// Showing Alert Message
		alertDialog1.show();
	}

	@Override
	public void onDeleteClickedforProduct(View v, final String id) {
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
				// TODO Auto-generated method stub

				
	
				

				final ArrayList<String> getlist = new ArrayList<String>();
				String selectQueryforinstantpo = "SELECT  * FROM "
						+ DatabaseForDemo.INVENTORY_TABLE + " where "
						+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\"" + id + "\"";

				Cursor mCursorforvendor1 = dbforloginlogoutWriteInvetory.rawQuery(
						selectQueryforinstantpo, null);
				if (mCursorforvendor1 != null) {
					if (mCursorforvendor1.moveToFirst()) {
						do {
							String uniqueid = mCursorforvendor1.getString(mCursorforvendor1
									.getColumnIndex(DatabaseForDemo.UNIQUE_ID));
							getlist.add(uniqueid);
						} while (mCursorforvendor1.moveToNext());
					}
				}
				mCursorforvendor1.close();
				String where = DatabaseForDemo.INVENTORY_ITEM_NO + "=?";
				dbforloginlogoutWriteInvetory.delete(DatabaseForDemo.INVENTORY_TABLE, where,
						new String[] { id });

				String whereforoptionalinfo = DatabaseForDemo.INVENTORY_ITEM_NO
						+ "=?";
				dbforloginlogoutWriteInvetory.delete(DatabaseForDemo.OPTIONAL_INFO_TABLE,
						whereforoptionalinfo, new String[] { id });

				String whereforalternatesku = DatabaseForDemo.INVENTORY_ITEM_NO
						+ "=?";
				dbforloginlogoutWriteInvetory.delete(DatabaseForDemo.ALTERNATE_SKU_TABLE,
						whereforalternatesku, new String[] { id });

				String whereformodifier = DatabaseForDemo.INVENTORY_ITEM_NO
						+ "=?" + " OR " + DatabaseForDemo.MODIFIER_ITEM_NO
						+ "=?";
				dbforloginlogoutWriteInvetory.delete(DatabaseForDemo.MODIFIER_TABLE,
						whereformodifier, new String[] { id, id });

				String wherefororderinginfo = DatabaseForDemo.INVENTORY_ITEM_NO
						+ "=?";
				dbforloginlogoutWriteInvetory.delete(DatabaseForDemo.ORDERING_INFO_TABLE,
						wherefororderinginfo, new String[] { id });

				Toast.makeText(InventoryActivity.this,
						"Product deleted successfully", Toast.LENGTH_SHORT)
						.show();

				try {
					JSONArray unique_ids = new JSONArray();
					for (int i = 0; i < getlist.size(); i++) {
						unique_ids.put(i, getlist.get(i));
						dataval = unique_ids.toString();
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					JSONArray unique_ids = new JSONArray();
					for (int i = 0; i < getlist.size(); i++) {
						unique_ids.put(i, getlist.get(i));
						datavalforproduct1 = unique_ids.toString();
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					JSONArray unique_ids = new JSONArray();
					for (int i = 0; i < getlist.size(); i++) {
						unique_ids.put(i, getlist.get(i));
						datavalforproduct2 = unique_ids.toString();
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					JSONArray unique_ids = new JSONArray();
					for (int i = 0; i < getlist.size(); i++) {
						unique_ids.put(i, getlist.get(i));
						datavalforproduct3 = unique_ids.toString();
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				try {
					JSONArray unique_ids = new JSONArray();
					for (int i = 0; i < getlist.size(); i++) {
						unique_ids.put(i, getlist.get(i));
						datavalforproduct4 = unique_ids.toString();
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (Parameters.OriginalUrl.equals("")) {
					System.out.println("there is no server url val");
				} else {
					boolean isnet = Parameters.isNetworkAvailable(InventoryActivity.this);
					if (isnet) {
						new Thread(new Runnable() {
							@Override
							public void run() {

								JsonPostMethod jsonpost = new JsonPostMethod();
								String response = jsonpost
										.postmethodfordirectdelete(
												"admin",
												"abcdefg",
												DatabaseForDemo.INVENTORY_TABLE,
												Parameters.currentTime(),
												Parameters.currentTime(),
												dataval);
								System.out.println("response test is:"
										+ response);
								
							
								
										

								/*
								 * try { JSONObject obj = new
								 * JSONObject(response);
								 * 
								 * JSONArray array =
								 * obj.getJSONArray("insert-queries");
								 * System.out
								 * .println("array list length for insert is:"
								 * +array.length()); JSONArray array2 =
								 * obj.getJSONArray("delete-queries");
								 * System.out
								 * .println("array2 list length for delete is:"
								 * +array2.length()); for(int jj = 0,ii = 0;
								 * jj<array2.length() && ii<array.length();
								 * jj++,ii++){ String deletequerytemp =
								 * array2.getString(jj); String deletequery1 =
								 * deletequerytemp.replace("'", "\""); String
								 * deletequery = deletequery1.replace("\\\"",
								 * "'");
								 * System.out.println("delete query"+jj+" is :"
								 * +deletequery);
								 * 
								 * String insertquerytemp = array.getString(ii);
								 * String insertquery1 =
								 * insertquerytemp.replace("'", "\""); String
								 * insertquery = insertquery1.replace("\\\"",
								 * "'");
								 * System.out.println("delete query"+jj+" is :"
								 * +insertquery);
								 * 
								 *dbforloginlogoutReadInvetory.execSQL(deletequery);
								 *dbforloginlogoutReadInvetory.execSQL(insertquery);
								 * System.out.println("queries executed"+ii);
								 * 
								 * } } catch (JSONException e) { // TODO
								 * Auto-generated catch block
								 * e.printStackTrace(); }
								 */

								String response1 = jsonpost
										.postmethodfordirectdelete(
												"admin",
												"abcdefg",
												DatabaseForDemo.OPTIONAL_INFO_TABLE,
												Parameters.currentTime(),
												Parameters.currentTime(),
												datavalforproduct1);
								System.out.println("response test is:"
										+ response1);
								/*
								 * try { JSONObject obj = new
								 * JSONObject(response1);
								 * 
								 * JSONArray array =
								 * obj.getJSONArray("insert-queries");
								 * System.out
								 * .println("array list length for insert is:"
								 * +array.length()); JSONArray array2 =
								 * obj.getJSONArray("delete-queries");
								 * System.out
								 * .println("array2 list length for delete is:"
								 * +array2.length()); for(int jj = 0,ii = 0;
								 * jj<array2.length() && ii<array.length();
								 * jj++,ii++){ String deletequerytemp =
								 * array2.getString(jj); String deletequery1 =
								 * deletequerytemp.replace("'", "\""); String
								 * deletequery = deletequery1.replace("\\\"",
								 * "'");
								 * System.out.println("delete query"+jj+" is :"
								 * +deletequery);
								 * 
								 * String insertquerytemp = array.getString(ii);
								 * String insertquery1 =
								 * insertquerytemp.replace("'", "\""); String
								 * insertquery = insertquery1.replace("\\\"",
								 * "'");
								 * System.out.println("delete query"+jj+" is :"
								 * +insertquery);
								 * 
								 *dbforloginlogoutReadInvetory.execSQL(deletequery);
								 *dbforloginlogoutReadInvetory.execSQL(insertquery);
								 * System.out.println("queries executed"+ii);
								 * 
								 * } } catch (JSONException e) { // TODO
								 * Auto-generated catch block
								 * e.printStackTrace(); }
								 */

								String response2 = jsonpost
										.postmethodfordirectdelete(
												"admin",
												"abcdefg",
												DatabaseForDemo.ALTERNATE_SKU_TABLE,
												Parameters.currentTime(),
												Parameters.currentTime(),
												datavalforproduct2);
								System.out.println("response test is:"
										+ response2);
								/*
								 * try { JSONObject obj = new
								 * JSONObject(response2);
								 * 
								 * JSONArray array =
								 * obj.getJSONArray("insert-queries");
								 * System.out
								 * .println("array list length for insert is:"
								 * +array.length()); JSONArray array2 =
								 * obj.getJSONArray("delete-queries");
								 * System.out
								 * .println("array2 list length for delete is:"
								 * +array2.length()); for(int jj = 0,ii = 0;
								 * jj<array2.length() && ii<array.length();
								 * jj++,ii++){ String deletequerytemp =
								 * array2.getString(jj); String deletequery1 =
								 * deletequerytemp.replace("'", "\""); String
								 * deletequery = deletequery1.replace("\\\"",
								 * "'");
								 * System.out.println("delete query"+jj+" is :"
								 * +deletequery);
								 * 
								 * String insertquerytemp = array.getString(ii);
								 * String insertquery1 =
								 * insertquerytemp.replace("'", "\""); String
								 * insertquery = insertquery1.replace("\\\"",
								 * "'");
								 * System.out.println("delete query"+jj+" is :"
								 * +insertquery);
								 * 
								 *dbforloginlogoutReadInvetory.execSQL(deletequery);
								 *dbforloginlogoutReadInvetory.execSQL(insertquery);
								 * System.out.println("queries executed"+ii);
								 * 
								 * } } catch (JSONException e) { // TODO
								 * Auto-generated catch block
								 * e.printStackTrace(); }
								 */

								String response3 = jsonpost
										.postmethodfordirectdelete("admin",
												"abcdefg",
												DatabaseForDemo.MODIFIER_TABLE,
												Parameters.currentTime(),
												Parameters.currentTime(),
												datavalforproduct3);
								System.out.println("response test is:"
										+ response3);
								/*
								 * try { JSONObject obj = new
								 * JSONObject(response3);
								 * 
								 * JSONArray array =
								 * obj.getJSONArray("insert-queries");
								 * System.out
								 * .println("array list length for insert is:"
								 * +array.length()); JSONArray array2 =
								 * obj.getJSONArray("delete-queries");
								 * System.out
								 * .println("array2 list length for delete is:"
								 * +array2.length()); for(int jj = 0,ii = 0;
								 * jj<array2.length() && ii<array.length();
								 * jj++,ii++){ String deletequerytemp =
								 * array2.getString(jj); String deletequery1 =
								 * deletequerytemp.replace("'", "\""); String
								 * deletequery = deletequery1.replace("\\\"",
								 * "'");
								 * System.out.println("delete query"+jj+" is :"
								 * +deletequery);
								 * 
								 * String insertquerytemp = array.getString(ii);
								 * String insertquery1 =
								 * insertquerytemp.replace("'", "\""); String
								 * insertquery = insertquery1.replace("\\\"",
								 * "'");
								 * System.out.println("delete query"+jj+" is :"
								 * +insertquery);
								 * 
								 *dbforloginlogoutReadInvetory.execSQL(deletequery);
								 *dbforloginlogoutReadInvetory.execSQL(insertquery);
								 * System.out.println("queries executed"+ii);
								 * 
								 * } } catch (JSONException e) { // TODO
								 * Auto-generated catch block
								 * e.printStackTrace(); }
								 */

								String response4 = jsonpost
										.postmethodfordirectdelete(
												"admin",
												"abcdefg",
												DatabaseForDemo.ORDERING_INFO_TABLE,
												Parameters.currentTime(),
												Parameters.currentTime(),
												datavalforproduct4);
								System.out.println("response test is:"
										+ response4);

								String servertiem = null;
								try {
									JSONObject obj = new JSONObject(response);
									JSONObject responseobj = obj
											.getJSONObject("response");
									servertiem = responseobj
											.getString("server-time");
									System.out.println("servertime is:"
											+ servertiem);
									/*
									 * JSONArray array =
									 * obj.getJSONArray("insert-queries");
									 * System.out.println(
									 * "array list length for insert is:"
									 * +array.length()); JSONArray array2 =
									 * obj.getJSONArray("delete-queries");
									 * System.out.println(
									 * "array2 list length for delete is:"
									 * +array2.length()); for(int jj = 0,ii = 0;
									 * jj<array2.length() && ii<array.length();
									 * jj++,ii++){ String deletequerytemp =
									 * array2.getString(jj); String deletequery1
									 * = deletequerytemp.replace("'", "\"");
									 * String deletequery =
									 * deletequery1.replace("\\\"", "'");
									 * System.
									 * out.println("delete query"+jj+" is :"
									 * +deletequery);
									 * 
									 * String insertquerytemp =
									 * array.getString(ii); String insertquery1
									 * = insertquerytemp.replace("'", "\"");
									 * String insertquery =
									 * insertquery1.replace("\\\"", "'");
									 * System.
									 * out.println("delete query"+jj+" is :"
									 * +insertquery);
									 * 
									 *dbforloginlogoutReadInvetory.execSQL(deletequery);
									 *dbforloginlogoutReadInvetory.execSQL(insertquery);
									 * System.out.println
									 * ("queries executed"+ii);
									 * 
									 * }
									 */
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								String select = "select *from "
										+ DatabaseForDemo.MISCELLANEOUS_TABLE;
								Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(select, null);
								if (cursor.getCount() > 0) {
								dbforloginlogoutWriteInvetory.execSQL("update "
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
											DatabaseForDemo.MISCEL_PAGEURL, "");
									contentValues1
											.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL,
													Parameters.currentTime());
									contentValues1
											.put(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL,
													Parameters.currentTime());
									dbforloginlogoutWriteInvetory.insert(
											DatabaseForDemo.MISCELLANEOUS_TABLE,
											null, contentValues1);
									
								}
								cursor.close();
								dataval = "";
								datavalforproduct1 = "";
								datavalforproduct2 = "";
								datavalforproduct3 = "";
								datavalforproduct4 = "";
							}
						}).start();
					} else {
						
			
						

						ContentValues contentValues1 = new ContentValues();
						contentValues1
								.put(DatabaseForDemo.QUERY_TYPE, "delete");
						contentValues1.put(DatabaseForDemo.PENDING_USER_ID,
								Parameters.userid);
						contentValues1.put(DatabaseForDemo.PAGE_URL,
								"deleteinfo.php");
						contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING,
								DatabaseForDemo.INVENTORY_TABLE);
						contentValues1.put(
								DatabaseForDemo.CURRENT_TIME_PENDING,
								Parameters.currentTime());
						contentValues1.put(DatabaseForDemo.PARAMETERS, dataval);
						dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null,
								contentValues1);

						ContentValues contentValues2 = new ContentValues();
						contentValues2
								.put(DatabaseForDemo.QUERY_TYPE, "delete");
						contentValues2.put(DatabaseForDemo.PENDING_USER_ID,
								Parameters.userid);
						contentValues2.put(DatabaseForDemo.PAGE_URL,
								"deleteinfo.php");
						contentValues2.put(DatabaseForDemo.TABLE_NAME_PENDING,
								DatabaseForDemo.OPTIONAL_INFO_TABLE);
						contentValues2.put(
								DatabaseForDemo.CURRENT_TIME_PENDING,
								Parameters.currentTime());
						contentValues2.put(DatabaseForDemo.PARAMETERS,
								datavalforproduct1);
						dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null,
								contentValues2);

						ContentValues contentValues3 = new ContentValues();
						contentValues3
								.put(DatabaseForDemo.QUERY_TYPE, "delete");
						contentValues3.put(DatabaseForDemo.PENDING_USER_ID,
								Parameters.userid);
						contentValues3.put(DatabaseForDemo.PAGE_URL,
								"deleteinfo.php");
						contentValues3.put(DatabaseForDemo.TABLE_NAME_PENDING,
								DatabaseForDemo.ALTERNATE_SKU_TABLE);
						contentValues3.put(
								DatabaseForDemo.CURRENT_TIME_PENDING,
								Parameters.currentTime());
						contentValues3.put(DatabaseForDemo.PARAMETERS,
								datavalforproduct2);
						dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null,
								contentValues3);

						ContentValues contentValues4 = new ContentValues();
						contentValues4
								.put(DatabaseForDemo.QUERY_TYPE, "delete");
						contentValues4.put(DatabaseForDemo.PENDING_USER_ID,
								Parameters.userid);
						contentValues4.put(DatabaseForDemo.PAGE_URL,
								"deleteinfo.php");
						contentValues4.put(DatabaseForDemo.TABLE_NAME_PENDING,
								DatabaseForDemo.MODIFIER_TABLE);
						contentValues4.put(
								DatabaseForDemo.CURRENT_TIME_PENDING,
								Parameters.currentTime());
						contentValues4.put(DatabaseForDemo.PARAMETERS,
								datavalforproduct3);
						dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null,
								contentValues4);

						ContentValues contentValues5 = new ContentValues();
						contentValues5
								.put(DatabaseForDemo.QUERY_TYPE, "delete");
						contentValues5.put(DatabaseForDemo.PENDING_USER_ID,
								Parameters.userid);
						contentValues5.put(DatabaseForDemo.PAGE_URL,
								"deleteinfo.php");
						contentValues5.put(DatabaseForDemo.TABLE_NAME_PENDING,
								DatabaseForDemo.ORDERING_INFO_TABLE);
						contentValues5.put(
								DatabaseForDemo.CURRENT_TIME_PENDING,
								Parameters.currentTime());
						contentValues5.put(DatabaseForDemo.PARAMETERS,
								datavalforproduct4);
						dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null,
								contentValues5);
						
						
						dataval = "";
						datavalforproduct1 = "";
						datavalforproduct2 = "";
						datavalforproduct3 = "";
						datavalforproduct4 = "";
					}
				}
				
				gettingCount();
				listUpdateforproduct(offsetvals, "100", totalrecordselectQuery);
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

	public void countforcategory(int j) {
		testforid = 1;
		stLoop = (j - 1) * pagecount;
		endLoop = j * pagecount;
		if (endLoop >= totalcount) {
			endLoop = totalcount;
		}
		id_data = getDataforcategory(stLoop, endLoop, total_id_data);
		desc_data = getDataforcategory(stLoop, endLoop, total_desc_data);

		ImageAdapterForCategory adapter = new ImageAdapterForCategory(this,
				id_data, desc_data);
		adapter.setListener(this);
		list_View.setAdapter(adapter);
	}

	public ArrayList<String> getDataforcategory(int offset, int limit,
			ArrayList<String> list) {
		ArrayList<String> newList = new ArrayList<String>(limit);
		int end = limit;

		if (end > list.size()) {
			end = list.size();
		}
		newList.addAll(list.subList(offset, end));
		return newList;
	}

	public void listUpdateforcategory() {
		total_desc_data.clear();
		total_id_data.clear();
		id_data.clear();
		desc_data.clear();
		ll.removeAllViews();

		
		

		String selectQuery = "SELECT  * FROM " + DatabaseForDemo.CATEGORY_TABLE;

		Cursor mCursor =dbforloginlogoutReadInvetory.rawQuery(selectQuery, null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					
					String catid = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.CategoryId));
					total_id_data.add(catid);
					String catdesc = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.CategoryDesp));
					total_desc_data.add(catdesc);
				} while (mCursor.moveToNext());
			}
			
			
			
			totalcount = total_id_data.size();
			System.out.println("total count value is:" + totalcount);

			int to = totalcount / pagecount;
			int too = totalcount % pagecount;
			int i = to;
			if (too != 0) {
				i = to + 1;
			}
			countforcategory(1);
			ll.setWeightSum(i);

			for (j = 1; j <= i; j++) {
				final Button btn = new Button(this);
				btn.setText("" + j);
				btn.setId(j);
				btn.setBackgroundResource(R.drawable.pnormal);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
						1f);

				LinearLayout space = new LinearLayout(this);
				LayoutParams lp1 = new LayoutParams(1,
						LayoutParams.WRAP_CONTENT);
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
						}
						btn.setBackgroundResource(R.drawable.pactive);
						btn.setTextColor(Color.WHITE);
						countforcategory(Integer.parseInt(btn.getText()
								.toString().trim()));
						testforid = Integer.parseInt(btn.getText().toString()
								.trim());
					}

				});

			}

		}
		mCursor.close();
	}

	@Override
	public void onEditClickedforCategory(View v, final String id, String desc) {
		// TODO Auto-generated method stub
		final AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
		Log.e("tagdd", ".....gggggg");
		final EditText cat_id;
		final EditText cat_desc;
		Button save, cancel;

		LayoutInflater mInflater = LayoutInflater.from(this);
		View layout = mInflater.inflate(R.layout.category_details, null);
		cat_id = (EditText) layout.findViewById(R.id.cat_id);
		cat_id.setEnabled(false);
		cat_id.setText(id);
		cat_desc = (EditText) layout.findViewById(R.id.cat_desc);
		cat_desc.setText(desc);

		save = (Button) layout.findViewById(R.id.save);
		cancel = (Button) layout.findViewById(R.id.cancel);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (cat_id.getText().length() > 0) {
					
		
					
							
					dbforloginlogoutWriteInvetory.execSQL("update "
							+ DatabaseForDemo.CATEGORY_TABLE + " set "
							+ DatabaseForDemo.CREATED_DATE + "=\""
							+ Parameters.currentTime() + "\", "
							+ DatabaseForDemo.MODIFIED_DATE + "=\""
							+ Parameters.currentTime() + "\", "
							+ DatabaseForDemo.MODIFIED_IN + "=\"" + "Local"
							+ "\", " + DatabaseForDemo.CategoryId + "=\""
							+ cat_id.getText().toString() + "\", "
							+ DatabaseForDemo.CategoryDesp + "=\""
							+ cat_desc.getText().toString() + "\" where "
							+ DatabaseForDemo.CategoryId + "=\"" + id + "\"");
					Toast.makeText(InventoryActivity.this,
							"Category saved successfully", Toast.LENGTH_SHORT)
							.show();

					final ArrayList<JSONObject> getlist = new ArrayList<JSONObject>();
					String selectQueryforinstantpo = "SELECT  * FROM "
							+ DatabaseForDemo.CATEGORY_TABLE + " where "
							+ DatabaseForDemo.CategoryId + "=\"" + id + "\"";

					Cursor mCursorforvendor1 = dbforloginlogoutWriteInvetory.rawQuery(
							selectQueryforinstantpo, null);
					if (mCursorforvendor1 != null) {
						if (mCursorforvendor1.moveToFirst()) {
							do {
								try {
									JSONObject jsonobj = new JSONObject();
									String departmentforproduct = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.CategoryId));
									jsonobj.put(DatabaseForDemo.CategoryId,
											departmentforproduct);
									String itemname = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.CategoryDesp));
									jsonobj.put(DatabaseForDemo.CategoryDesp,
											itemname);
									String uniqueid = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.UNIQUE_ID));
									jsonobj.put(DatabaseForDemo.UNIQUE_ID,
											uniqueid);
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
							
						}
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					if (Parameters.OriginalUrl.equals("")) {
						System.out.println("there is no server url val");
					} else {
						boolean isnet = Parameters.isNetworkAvailable(InventoryActivity.this);
						if (isnet) {
							new Thread(new Runnable() {
								@Override
								public void run() {
									JsonPostMethod jsonpost = new JsonPostMethod();
									String response = jsonpost
											.postmethodfordirect(
													"admin",
													"abcdefg",
													DatabaseForDemo.CATEGORY_TABLE,
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

										dbforloginlogoutWriteInvetory.execSQL(deletequery);
										dbforloginlogoutWriteInvetory.execSQL(insertquery);
											System.out
													.println("queries executed"
															+ ii);

										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									String select = "select *from "
											+ DatabaseForDemo.MISCELLANEOUS_TABLE;
									Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(select, null);
									if (cursor.getCount() > 0) {
									dbforloginlogoutWriteInvetory.execSQL("update "
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
										dbforloginlogoutWriteInvetory.insert(
												DatabaseForDemo.MISCELLANEOUS_TABLE,
												null, contentValues1);

									}
									cursor.close();
									dataval = "";
									
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
									DatabaseForDemo.CATEGORY_TABLE);
							contentValues1.put(
									DatabaseForDemo.CURRENT_TIME_PENDING,
									Parameters.currentTime());
							contentValues1.put(DatabaseForDemo.PARAMETERS,
									dataval);
							dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.PENDING_QUERIES_TABLE,
									null, contentValues1);
							
							
							dataval = "";
						}
					}
					
					
					listUpdateforcategory();
					alertDialog1.dismiss();

				} else {
					Toast.makeText(InventoryActivity.this,
							"Please enter category id", Toast.LENGTH_SHORT)
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
	}

	@Override
	public void onDeleteClickedforCategory(View v, final String id) {
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
				// TODO Auto-generated method stub

				
	
				

				final ArrayList<String> getlist = new ArrayList<String>();
				String selectQueryforinstantpo = "SELECT  * FROM "
						+ DatabaseForDemo.CATEGORY_TABLE + " where "
						+ DatabaseForDemo.CategoryId + "=\"" + id + "\"";

				Cursor mCursorforvendor1 = dbforloginlogoutWriteInvetory.rawQuery(
						selectQueryforinstantpo, null);
				if (mCursorforvendor1 != null) {
					if (mCursorforvendor1.moveToFirst()) {
						do {
							String uniqueid = mCursorforvendor1.getString(mCursorforvendor1
									.getColumnIndex(DatabaseForDemo.UNIQUE_ID));
							getlist.add(uniqueid);
						} while (mCursorforvendor1.moveToNext());
					}
				}
				mCursorforvendor1.close();
				String where = DatabaseForDemo.CategoryId + "=?";
				dbforloginlogoutWriteInvetory.delete(DatabaseForDemo.CATEGORY_TABLE, where,
						new String[] { id });

				Toast.makeText(InventoryActivity.this,
						"Category deleted successfully", Toast.LENGTH_SHORT)
						.show();

				try {
					JSONArray unique_ids = new JSONArray();
					for (int i = 0; i < getlist.size(); i++) {
						unique_ids.put(i, getlist.get(i));
						dataval = unique_ids.toString();
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (Parameters.OriginalUrl.equals("")) {
					System.out.println("there is no server url val");
				} else {
					boolean isnet = Parameters.isNetworkAvailable(InventoryActivity.this);
					if (isnet) {
						new Thread(new Runnable() {
							@Override
							public void run() {
								JsonPostMethod jsonpost = new JsonPostMethod();
								String response = jsonpost
										.postmethodfordirectdelete("admin",
												"abcdefg",
												DatabaseForDemo.CATEGORY_TABLE,
												Parameters.currentTime(),
												Parameters.currentTime(),
												dataval);
								System.out.println("response test is:"
										+ response);
								
							
								
										
								String servertiem = null;
								try {
									JSONObject obj = new JSONObject(response);
									JSONObject responseobj = obj
											.getJSONObject("response");
									servertiem = responseobj
											.getString("server-time");
									System.out.println("servertime is:"
											+ servertiem);
									/*
									 * JSONArray array =
									 * obj.getJSONArray("insert-queries");
									 * System.out.println(
									 * "array list length for insert is:"
									 * +array.length()); JSONArray array2 =
									 * obj.getJSONArray("delete-queries");
									 * System.out.println(
									 * "array2 list length for delete is:"
									 * +array2.length()); for(int jj = 0,ii = 0;
									 * jj<array2.length() && ii<array.length();
									 * jj++,ii++){ String deletequerytemp =
									 * array2.getString(jj); String deletequery1
									 * = deletequerytemp.replace("'", "\"");
									 * String deletequery =
									 * deletequery1.replace("\\\"", "'");
									 * System.
									 * out.println("delete query"+jj+" is :"
									 * +deletequery);
									 * 
									 * String insertquerytemp =
									 * array.getString(ii); String insertquery1
									 * = insertquerytemp.replace("'", "\"");
									 * String insertquery =
									 * insertquery1.replace("\\\"", "'");
									 * System.
									 * out.println("delete query"+jj+" is :"
									 * +insertquery);
									 * 
									 *dbforloginlogoutReadInvetory.execSQL(deletequery);
									 *dbforloginlogoutReadInvetory.execSQL(insertquery);
									 * System.out.println
									 * ("queries executed"+ii);
									 * 
									 * }
									 */
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								String select = "select *from "
										+ DatabaseForDemo.MISCELLANEOUS_TABLE;
								Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(select, null);
								if (cursor.getCount() > 0) {
								dbforloginlogoutWriteInvetory.execSQL("update "
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
											DatabaseForDemo.MISCEL_PAGEURL, "");
									contentValues1
											.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL,
													Parameters.currentTime());
									contentValues1
											.put(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL,
													Parameters.currentTime());
									dbforloginlogoutWriteInvetory.insert(
											DatabaseForDemo.MISCELLANEOUS_TABLE,
											null, contentValues1);
									
								}
								cursor.close();
								dataval = "";
							}
						}).start();
					} else {
						
			
						

						ContentValues contentValues1 = new ContentValues();
						contentValues1
								.put(DatabaseForDemo.QUERY_TYPE, "delete");
						contentValues1.put(DatabaseForDemo.PENDING_USER_ID,
								Parameters.userid);
						contentValues1.put(DatabaseForDemo.PAGE_URL,
								"deleteinfo.php");
						contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING,
								DatabaseForDemo.CATEGORY_TABLE);
						contentValues1.put(
								DatabaseForDemo.CURRENT_TIME_PENDING,
								Parameters.currentTime());
						contentValues1.put(DatabaseForDemo.PARAMETERS, dataval);
						dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null,
								contentValues1);
						
						
						dataval = "";
					}
				}
				
				
				listUpdateforcategory();
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

	public void countfordepartment(int j) {
		testforid = 1;
		stLoop = (j - 1) * pagecount;
		endLoop = j * pagecount;
		if (endLoop >= totalcount) {
			endLoop = totalcount;
		}
		id_data = getDatafordepartment(stLoop, endLoop, total_id_data);
		desc_data = getDatafordepartment(stLoop, endLoop, total_desc_data);
		cat_data = getDatafordepartment(stLoop, endLoop, total_cat_data);

		ImageAdapterForDepartment adapter = new ImageAdapterForDepartment(this,
				id_data, desc_data, cat_data);
		adapter.setListener(this);
		list_View.setAdapter(adapter);
	}

	public ArrayList<String> getDatafordepartment(int offset, int limit,
			ArrayList<String> list) {
		ArrayList<String> newList = new ArrayList<String>(limit);
		int end = limit;

		if (end > list.size()) {
			end = list.size();
		}
		newList.addAll(list.subList(offset, end));
		return newList;
	}

	@Override
	public void onEditClickedforDepartment(View v, final String id,
			String desc, String categoryval) {
		// TODO Auto-generated method stub
		System.out.println("padma id is:" + id);
		System.out.println("padma desc is:" + desc);
		System.out.println("category val is padam:" + categoryval);
		final AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
		Log.e("tagdd", ".....gggggg");
		final EditText dept_id;
		final EditText dept_desc;
		final Spinner categoryspinn, printerspinner, printerspinnermin, printerspinnersec;
		final CheckBox foodcheck, tax1dept, tax2dept, tax3dept, bartaxdept;
		Button save, cancel;
		String printerdata = "None", timeminutes = "0", timeseconds = "0";

		LayoutInflater mInflater = LayoutInflater.from(this);
		View layout = mInflater.inflate(R.layout.department_details, null);
		dept_id = (EditText) layout.findViewById(R.id.dept_id);
		dept_id.setEnabled(false);
		dept_id.setText(id);
		dept_desc = (EditText) layout.findViewById(R.id.dept_desc);
		dept_desc.setText(desc);
		categoryspinn = (Spinner) layout.findViewById(R.id.cat_id);
		foodcheck = (CheckBox) layout.findViewById(R.id.foodcheck);
		printerspinner = (Spinner) layout.findViewById(R.id.printerspinner);
		printerspinnermin = (Spinner) layout
				.findViewById(R.id.printerspinnermints);
		printerspinnersec = (Spinner) layout
				.findViewById(R.id.printerspinnerseconds);
		tax1dept = (CheckBox) layout.findViewById(R.id.checkBox1);
		tax2dept = (CheckBox) layout.findViewById(R.id.checkBox2);
		tax3dept = (CheckBox) layout.findViewById(R.id.checkBox3);
		tax1dept.setText(taxlist.get(0));
		tax2dept.setText(taxlist.get(1));
		tax3dept.setText(taxlist.get(2));
		bartaxdept = (CheckBox) layout.findViewById(R.id.checkBox4);
		String[] printerarrayminutes = getResources().getStringArray(
				R.array.minutes_array);
		List<String> printerarraylistminutes = Arrays
				.asList(printerarrayminutes);
		String[] printerarrayseconds = getResources().getStringArray(
				R.array.seconds_array);
		List<String> printerarraylistseconds = Arrays
				.asList(printerarrayseconds);

		
		Cursor mCursor2 = dbforloginlogoutReadInvetory.rawQuery(
				"select " + DatabaseForDemo.FoodstampableForDept + ","
						+ DatabaseForDemo.TaxValForDept + " from "
						+ DatabaseForDemo.DEPARTMENT_TABLE + " where "
						+ DatabaseForDemo.DepartmentID + "=\"" + id + "\"", null);
		// System.out.println(mCursor2);
		if (mCursor2.getCount() > 0) {
			if (mCursor2 != null) {
				if (mCursor2.moveToFirst()) {
					do {
						String foodcheckdata = mCursor2
								.getString(mCursor2
										.getColumnIndex(DatabaseForDemo.FoodstampableForDept));
						if (foodcheckdata.equals("yes")) {
							foodcheck.setChecked(true);
						} else {
							foodcheck.setChecked(false);
						}
						String taxval = mCursor2.getString(mCursor2
								.getColumnIndex(DatabaseForDemo.TaxValForDept));
						String mystring = taxval;
						String[] a = mystring.split(",");
						System.out.println("the array values are:" + a.length);
						for (int i = 0; i < a.length; i++) {
							String substr = a[i];
							if (substr.equals("Tax1")) {
								tax1dept.setChecked(true);
							} else if (substr.equals("Tax2")) {
								tax2dept.setChecked(true);
							} else if (substr.equals("Tax3")) {
								tax3dept.setChecked(true);
							} else if (substr.equals("BarTax")) {
								bartaxdept.setChecked(true);
							}
						}
					} while (mCursor2.moveToNext());
				}

			}
		}
		mCursor2.close();
		Cursor mCursor3 = dbforloginlogoutReadInvetory.rawQuery(
				"select *from " + DatabaseForDemo.DEPARTMENT_PRINTER_COMMANDS
						+ " where " + DatabaseForDemo.DepartmentID + "=\"" + id
						+ "\"", null);
		if (mCursor3.getCount() > 0) {
			if (mCursor3 != null) {
				if (mCursor3.moveToFirst()) {
					do {
						if (mCursor3
								.isNull(mCursor3
										.getColumnIndex(DatabaseForDemo.PrinterForDept))) {
							printerdata = "None";
						} else {
							printerdata = mCursor3
									.getString(mCursor3
											.getColumnIndex(DatabaseForDemo.PrinterForDept));
						}
						if (mCursor3
								.isNull(mCursor3
										.getColumnIndex(DatabaseForDemo.TimeForDeptPrint))) {
							timeminutes = "0";
							timeseconds = "0";
						} else {
							String timeval = mCursor3
									.getString(mCursor3
											.getColumnIndex(DatabaseForDemo.TimeForDeptPrint));
							String mystring = timeval;
							String[] a = mystring.split(":");
							System.out.println("the array values are is:"
									+ a.length);
							timeminutes = a[0];
							timeseconds = a[1];
						}
					} while (mCursor3.moveToNext());
				}
			}
		}
		mCursor3.close();
		System.out.println("minutes data is:" + timeminutes);
		System.out.println("seconds data is:" + timeseconds);
		printerspinnermin.setSelection(printerarraylistminutes
				.indexOf(timeminutes));
		printerspinnersec.setSelection(printerarraylistseconds
				.indexOf(timeseconds));
		ArrayList<String> printerlistval = new ArrayList<String>();
		printerlistval.clear();
		printerlistval.add("None");
		Cursor mCursor4 = dbforloginlogoutReadInvetory.rawQuery(
				"select " + DatabaseForDemo.PRINTER_ID + " from "
						+ DatabaseForDemo.PRINTER_TABLE, null);
		System.out.println(mCursor4);
		if (mCursor4.getCount() > 0) {
			if (mCursor4 != null) {
				if (mCursor4.moveToFirst()) {
					do {
						if (mCursor4.isNull(mCursor4
								.getColumnIndex(DatabaseForDemo.PRINTER_ID))) {

						} else {
							String catid = mCursor4
									.getString(mCursor4
											.getColumnIndex(DatabaseForDemo.PRINTER_ID));
							printerlistval.add(catid);
						}
					} while (mCursor4.moveToNext());
				}
			}
		}
		mCursor4.close();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, printerlistval);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		printerspinner.setAdapter(adapter);
		System.out.println("printer data val is:" + printerdata);
		printerspinner.setSelection(printerlistval.indexOf(printerdata));

		ArrayList<String> spinnerData = new ArrayList<String>();
		spinnerData.clear();

		Cursor mCursor = dbforloginlogoutReadInvetory.rawQuery(
				"select " + DatabaseForDemo.CategoryId + " from "
						+ DatabaseForDemo.CATEGORY_TABLE, null);
		System.out.println(mCursor);
		if (mCursor.getCount() > 0) {
			if (mCursor != null) {
				if (mCursor.moveToFirst()) {
					do {

						String catid = mCursor.getString(mCursor
								.getColumnIndex(DatabaseForDemo.CategoryId));
						spinnerData.add(catid);
						System.out.println("cat val is added" + catid);
					} while (mCursor.moveToNext());
				}
			}
		}
		mCursor.close();
		
		ArrayAdapter<String> adaptercat = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, spinnerData);
		adaptercat
				.setDropDownViewResource(android.R.layout.simple_spinner_item);
		categoryspinn.setAdapter(adaptercat);
		categoryspinn.setSelection(spinnerData.indexOf(categoryval));

		save = (Button) layout.findViewById(R.id.save);
		cancel = (Button) layout.findViewById(R.id.cancel);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (dept_id.getText().length() > 0
						&& categoryspinn.getSelectedItem().toString().length() > 0) {
					String foodcheckval = "";
					String printertime = "";
					String printerval = "";
					if (printerspinner.getSelectedItem().toString().equals("")
							|| printerspinner.getSelectedItem().toString()
									.equals("None")) {
						printerval = "";
					} else {
						printerval = printerspinner.getSelectedItem()
								.toString();
					}
					if (printerspinnermin.getSelectedItem().toString()
							.equals("")) {
						printertime = "";
					} else {
						printertime = printerspinnermin.getSelectedItem()
								.toString();
					}
					if (printerspinnersec.getSelectedItem().toString()
							.equals("")) {
						printertime = printertime + "";
					} else {
						printertime = printertime
								+ ":"
								+ printerspinnersec.getSelectedItem()
										.toString();
					}

					if (foodcheck.isChecked()) {
						foodcheckval = "yes";
					} else {
						foodcheckval = "no";
					}
					String taxvalforsave = "";
					if (tax1dept.isChecked()) {
						taxvalforsave = taxvalforsave
								+ tax1dept.getText().toString() + ",";
					}
					if (tax2dept.isChecked()) {
						taxvalforsave = taxvalforsave
								+ tax2dept.getText().toString() + ",";
					}
					if (tax3dept.isChecked()) {
						taxvalforsave = taxvalforsave
								+ tax3dept.getText().toString() + ",";
					}
					if (bartaxdept.isChecked()) {
						taxvalforsave = taxvalforsave
								+ bartaxdept.getText().toString() + ",";
					}
					
		
					
							
					dbforloginlogoutWriteInvetory.execSQL("update "
							+ DatabaseForDemo.DEPARTMENT_TABLE + " set "
							+ DatabaseForDemo.CREATED_DATE + "=\""
							+ Parameters.currentTime() + "\", "
							+ DatabaseForDemo.MODIFIED_DATE + "=\""
							+ Parameters.currentTime() + "\", "
							+ DatabaseForDemo.MODIFIED_IN + "=\"" + "Local"
							+ "\", " + DatabaseForDemo.DepartmentID + "=\""
							+ dept_id.getText().toString() + "\", "
							+ DatabaseForDemo.DepartmentDesp + "=\""
							+ dept_desc.getText().toString() + "\", "
							+ DatabaseForDemo.CategoryForDepartment + "=\""
							+ categoryspinn.getSelectedItem().toString()
							+ "\", " + DatabaseForDemo.FoodstampableForDept
							+ "=\"" + foodcheckval + "\", "
							+ DatabaseForDemo.CHECKED_VALUE + "=\"" + "true"
							+ "\", " + DatabaseForDemo.TaxValForDept + "=\""
							+ taxvalforsave + "\" where "
							+ DatabaseForDemo.DepartmentID + "=\"" + id + "\"");

					String query = "SELECT *from "
							+ DatabaseForDemo.DEPARTMENT_PRINTER_COMMANDS
							+ " where " + DatabaseForDemo.DepartmentID + "=\""
							+ id + "\"";
					Cursor enquiry = dbforloginlogoutWriteInvetory.rawQuery(query, null);
					if (enquiry.getCount() > 0) {
						dbforloginlogoutWriteInvetory.execSQL("update "
								+ DatabaseForDemo.DEPARTMENT_PRINTER_COMMANDS
								+ " set " + DatabaseForDemo.CREATED_DATE + "=\""
								+ Parameters.currentTime() + "\", "
								+ DatabaseForDemo.MODIFIED_DATE + "=\""
								+ Parameters.currentTime() + "\", "
								+ DatabaseForDemo.MODIFIED_IN + "=\"" + "Local"
								+ "\", " + DatabaseForDemo.DepartmentID + "=\""
								+ dept_id.getText().toString() + "\", "
								+ DatabaseForDemo.PrinterForDept + "=\""
								+ printerval + "\", "
								+ DatabaseForDemo.TimeForDeptPrint + "=\""
								+ printertime + "\" where "
								+ DatabaseForDemo.DepartmentID + "=\"" + id
								+ "\"");
						Toast.makeText(InventoryActivity.this,
								"Department saved successfully",
								Toast.LENGTH_SHORT).show();
					} else {
						ContentValues contentValues1 = new ContentValues();
						contentValues1.put(DatabaseForDemo.UNIQUE_ID,
								Parameters.randomValue());
						contentValues1.put(DatabaseForDemo.CREATED_DATE,
								Parameters.currentTime());
						contentValues1.put(DatabaseForDemo.MODIFIED_DATE,
								Parameters.currentTime());
						contentValues1
								.put(DatabaseForDemo.MODIFIED_IN, "Local");
						contentValues1.put(DatabaseForDemo.PrinterForDept,
								printerval);
						contentValues1.put(DatabaseForDemo.TimeForDeptPrint,
								printertime);
						contentValues1.put(DatabaseForDemo.DepartmentID, id);
						dbforloginlogoutWriteInvetory.insert(
								DatabaseForDemo.DEPARTMENT_PRINTER_COMMANDS,
								null, contentValues1);
					}
					enquiry.close();
					final ArrayList<JSONObject> getlist = new ArrayList<JSONObject>();
					String selectQueryforinstantpo = "SELECT  * FROM "
							+ DatabaseForDemo.DEPARTMENT_TABLE + " where "
							+ DatabaseForDemo.DepartmentID + "=\"" + id + "\"";

					Cursor mCursorforvendor1 = dbforloginlogoutWriteInvetory.rawQuery(
							selectQueryforinstantpo, null);
					if (mCursorforvendor1 != null) {
						if (mCursorforvendor1.moveToFirst()) {
							do {
								try {
									JSONObject jsonobj = new JSONObject();
									String departmentforproduct = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.DepartmentID));
									jsonobj.put(DatabaseForDemo.DepartmentID,
											departmentforproduct);
									String itemname = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.DepartmentDesp));
									jsonobj.put(DatabaseForDemo.DepartmentDesp,
											itemname);
									String departmentforproduct1 = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.CategoryForDepartment));
									jsonobj.put(
											DatabaseForDemo.CategoryForDepartment,
											departmentforproduct1);
									String itemname1 = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.CHECKED_VALUE));
									jsonobj.put(DatabaseForDemo.CHECKED_VALUE,
											itemname1);
									String departmentforproduct2 = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.FoodstampableForDept));
									jsonobj.put(
											DatabaseForDemo.FoodstampableForDept,
											departmentforproduct2);
									String itemname2 = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.TaxValForDept));
									jsonobj.put(DatabaseForDemo.TaxValForDept,
											itemname2);
									String uniqueid = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.UNIQUE_ID));
									jsonobj.put(DatabaseForDemo.UNIQUE_ID,
											uniqueid);
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
							
						}
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					if (Parameters.OriginalUrl.equals("")) {
						System.out.println("there is no server url val");
					} else {
						boolean isnet = Parameters.isNetworkAvailable(InventoryActivity.this);
						if (isnet) {
							new Thread(new Runnable() {
								@Override
								public void run() {
									JsonPostMethod jsonpost = new JsonPostMethod();
									String response = jsonpost
											.postmethodfordirect(
													"admin",
													"abcdefg",
													DatabaseForDemo.DEPARTMENT_TABLE,
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

											dbforloginlogoutWriteInvetory.execSQL(deletequery);
											dbforloginlogoutWriteInvetory.execSQL(insertquery);
											System.out
													.println("queries executed"
															+ ii);

										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									String select = "select *from "
											+ DatabaseForDemo.MISCELLANEOUS_TABLE;
									Cursor cursor = dbforloginlogoutReadInvetory.rawQuery(select,
											null);
									if (cursor.getCount() > 0) {
										dbforloginlogoutWriteInvetory
												.execSQL("update "
														+ DatabaseForDemo.MISCELLANEOUS_TABLE
														+ " set "
														+ DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL
														+ "=\"" + servertiem
														+ "\"");
										
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
										
										dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.MISCELLANEOUS_TABLE,
														null, contentValues1);
									}
									cursor.close();
									dataval = "";
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
									DatabaseForDemo.DEPARTMENT_TABLE);
							contentValues1.put(
									DatabaseForDemo.CURRENT_TIME_PENDING,
									Parameters.currentTime());
							contentValues1.put(DatabaseForDemo.PARAMETERS,
									dataval);
							dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.PENDING_QUERIES_TABLE,
									null, contentValues1);
							
							
							dataval = "";
						}
					}

					listUpdatefordepartment();
					alertDialog1.dismiss();
				} else {
					Toast.makeText(InventoryActivity.this,
							"Please enter id and category", Toast.LENGTH_SHORT)
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
	}

	@Override
	public void onDuplicateClickedforDepartment(View v, final String id,
			String desc, String categoryval) {
		// TODO Auto-generated method stub
		final AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
		Log.e("tagdd", ".....gggggg");
		final EditText dept_id;
		final EditText dept_desc;
		final Spinner categoryspinn, printerspinner, printerspinnermin, printerspinnersec;
		final CheckBox foodcheck, tax1dept, tax2dept, tax3dept, bartaxdept;
		Button save, cancel;
		String printerdata = "None", timeminutes = "0", timeseconds = "0";

		LayoutInflater mInflater = LayoutInflater.from(this);
		View layout = mInflater.inflate(R.layout.department_details, null);
		dept_id = (EditText) layout.findViewById(R.id.dept_id);
		dept_id.setText(id);
		dept_desc = (EditText) layout.findViewById(R.id.dept_desc);
		categoryspinn = (Spinner) layout.findViewById(R.id.cat_id);
		dept_desc.setText(desc);
		foodcheck = (CheckBox) layout.findViewById(R.id.foodcheck);
		printerspinner = (Spinner) layout.findViewById(R.id.printerspinner);
		printerspinnermin = (Spinner) layout
				.findViewById(R.id.printerspinnermints);
		printerspinnersec = (Spinner) layout
				.findViewById(R.id.printerspinnerseconds);
		tax1dept = (CheckBox) layout.findViewById(R.id.checkBox1);
		tax2dept = (CheckBox) layout.findViewById(R.id.checkBox2);
		tax3dept = (CheckBox) layout.findViewById(R.id.checkBox3);
		tax1dept.setText(taxlist.get(0));
		tax2dept.setText(taxlist.get(1));
		tax3dept.setText(taxlist.get(2));
		bartaxdept = (CheckBox) layout.findViewById(R.id.checkBox4);
		ArrayList<String> printerlistval = new ArrayList<String>();
		printerlistval.add("None");
		String[] printerarrayminutes = getResources().getStringArray(
				R.array.minutes_array);
		List<String> printerarraylistminutes = Arrays
				.asList(printerarrayminutes);
		String[] printerarrayseconds = getResources().getStringArray(
				R.array.seconds_array);
		List<String> printerarraylistseconds = Arrays
				.asList(printerarrayseconds);

		
		Cursor mCursor2 = dbforloginlogoutReadInvetory.rawQuery(
				"select " + DatabaseForDemo.FoodstampableForDept + ","
						+ DatabaseForDemo.TaxValForDept + " from "
						+ DatabaseForDemo.DEPARTMENT_TABLE + " where "
						+ DatabaseForDemo.DepartmentID + "=\"" + id + "\"", null);
		System.out.println(mCursor2);
		if (mCursor2.getCount() > 0) {
			if (mCursor2 != null) {
				if (mCursor2.moveToFirst()) {
					do {
						String foodcheckdata = mCursor2
								.getString(mCursor2
										.getColumnIndex(DatabaseForDemo.FoodstampableForDept));
						if (foodcheckdata.equals("yes")) {
							foodcheck.setChecked(true);
						} else {
							foodcheck.setChecked(false);
						}
						String taxval = mCursor2.getString(mCursor2
								.getColumnIndex(DatabaseForDemo.TaxValForDept));
						String mystring = taxval;
						String[] a = mystring.split(",");
						System.out.println("the array values are:" + a.length);
						for (int i = 0; i < a.length; i++) {
							String substr = a[i];
							if (substr.equals("Tax1")) {
								tax1dept.setChecked(true);
							} else if (substr.equals("Tax2")) {
								tax2dept.setChecked(true);
							} else if (substr.equals("Tax3")) {
								tax3dept.setChecked(true);
							} else if (substr.equals("BarTax")) {
								bartaxdept.setChecked(true);
							}
						}
					} while (mCursor2.moveToNext());
				}
			}
		}
mCursor2.close();
		Cursor mCursor3 = dbforloginlogoutReadInvetory.rawQuery(
				"select *from " + DatabaseForDemo.DEPARTMENT_PRINTER_COMMANDS
						+ " where " + DatabaseForDemo.DepartmentID + "=\"" + id
						+ "\"", null);
		if (mCursor3.getCount() > 0) {
			if (mCursor3 != null) {
				if (mCursor3.moveToFirst()) {
					do {
						if (mCursor3
								.isNull(mCursor3
										.getColumnIndex(DatabaseForDemo.PrinterForDept))) {
							printerdata = "None";
						} else {
							printerdata = mCursor3
									.getString(mCursor3
											.getColumnIndex(DatabaseForDemo.PrinterForDept));
						}
						if (mCursor3
								.isNull(mCursor3
										.getColumnIndex(DatabaseForDemo.TimeForDeptPrint))) {
							timeminutes = "0";
							timeseconds = "0";
						} else {
							String timeval = mCursor3
									.getString(mCursor3
											.getColumnIndex(DatabaseForDemo.TimeForDeptPrint));
							String mystring = timeval;
							String[] a = mystring.split(":");
							System.out.println("the array values are:"
									+ a.length);
							timeminutes = a[0];
							timeseconds = a[1];
						}
					} while (mCursor3.moveToNext());
				}
			}
		}
		mCursor3.close();
		printerspinnermin.setSelection(printerarraylistminutes
				.indexOf(timeminutes));
		printerspinnersec.setSelection(printerarraylistseconds
				.indexOf(timeseconds));

		Cursor mCursor4 = dbforloginlogoutReadInvetory.rawQuery(
				"select " + DatabaseForDemo.PRINTER_ID + " from "
						+ DatabaseForDemo.PRINTER_TABLE, null);
		System.out.println(mCursor4);
		if (mCursor4 != null) {
			if (mCursor4.moveToFirst()) {
				do {
					if (mCursor4.isNull(mCursor4
							.getColumnIndex(DatabaseForDemo.PRINTER_ID))) {

					} else {
						String catid = mCursor4.getString(mCursor4
								.getColumnIndex(DatabaseForDemo.PRINTER_ID));
						printerlistval.add(catid);
					}
				} while (mCursor4.moveToNext());
			}
		}
		mCursor4.close();
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, printerlistval);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		printerspinner.setAdapter(adapter);
		printerspinner.setSelection(printerlistval.indexOf(printerdata));

		ArrayList<String> spinnerData = new ArrayList<String>();
		spinnerData.clear();

		Cursor mCursor = dbforloginlogoutReadInvetory.rawQuery(
				"select " + DatabaseForDemo.CategoryId + " from "
						+ DatabaseForDemo.CATEGORY_TABLE, null);
		System.out.println(mCursor);
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					String catid = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.CategoryId));
					spinnerData.add(catid);
				} while (mCursor.moveToNext());
			}
		}
		mCursor.close();
		
		ArrayAdapter<String> adaptercat = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, spinnerData);
		adaptercat
				.setDropDownViewResource(android.R.layout.simple_spinner_item);
		categoryspinn.setAdapter(adaptercat);
		categoryspinn.setSelection(spinnerData.indexOf(categoryval));

		save = (Button) layout.findViewById(R.id.save);
		cancel = (Button) layout.findViewById(R.id.cancel);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				final String uniqueid = Parameters.randomValue();
				if (dept_id.getText().length() > 0) {
					String foodcheckval = "";
					String printertime = "";
					String printerval = "";
					if (printerspinner.getSelectedItem().toString().equals("")
							|| printerspinner.getSelectedItem().toString()
									.equals("None")) {
						printerval = "";
					} else {
						printerval = printerspinner.getSelectedItem()
								.toString();
					}
					if (printerspinnermin.getSelectedItem().toString()
							.equals("")) {
						printertime = "";
					} else {
						printertime = printerspinnermin.getSelectedItem()
								.toString();
					}
					if (printerspinnersec.getSelectedItem().toString()
							.equals("")) {
						printertime = printertime + "";
					} else {
						printertime = printertime
								+ ":"
								+ printerspinnersec.getSelectedItem()
										.toString();
					}
					if (foodcheck.isChecked()) {
						foodcheckval = "yes";
					} else {
						foodcheckval = "no";
					}
					String taxvalforsave = "";
					if (tax1dept.isChecked()) {
						taxvalforsave = taxvalforsave
								+ tax1dept.getText().toString() + ",";
					}
					if (tax2dept.isChecked()) {
						taxvalforsave = taxvalforsave
								+ tax2dept.getText().toString() + ",";
					}
					if (tax3dept.isChecked()) {
						taxvalforsave = taxvalforsave
								+ tax3dept.getText().toString() + ",";
					}
					if (bartaxdept.isChecked()) {
						taxvalforsave = taxvalforsave
								+ bartaxdept.getText().toString() + ",";
					}
					
		
					

					String selectQuery = "SELECT  * FROM "
							+ DatabaseForDemo.DEPARTMENT_TABLE + " where "
							+ DatabaseForDemo.DepartmentID + "=\""
							+ dept_id.getText().toString() + "\"";

					Cursor mCursor =dbforloginlogoutReadInvetory.rawQuery(selectQuery, null);
					if (mCursor.getCount() > 0) {
						Toast.makeText(InventoryActivity.this,
								"Department id already exist",
								Toast.LENGTH_SHORT).show();
						
					} else {
						
						ContentValues contentValues = new ContentValues();
						contentValues.put(DatabaseForDemo.UNIQUE_ID, uniqueid);
						contentValues.put(DatabaseForDemo.CREATED_DATE,
								Parameters.currentTime());
						contentValues.put(DatabaseForDemo.MODIFIED_DATE,
								Parameters.currentTime());
						contentValues.put(DatabaseForDemo.MODIFIED_IN, "Local");
						contentValues.put(DatabaseForDemo.DepartmentID, dept_id
								.getText().toString());
						contentValues.put(DatabaseForDemo.DepartmentDesp,
								dept_desc.getText().toString());
						contentValues.put(
								DatabaseForDemo.CategoryForDepartment,
								categoryspinn.getSelectedItem().toString());
						// contentValues.put(DatabaseForDemo.PrinterForDept,
						// printerval);
						contentValues.put(DatabaseForDemo.FoodstampableForDept,
								foodcheckval);
						contentValues.put(DatabaseForDemo.TaxValForDept,
								taxvalforsave);
						contentValues
								.put(DatabaseForDemo.CHECKED_VALUE, "true");
						dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.DEPARTMENT_TABLE, null,
								contentValues);
						Toast.makeText(InventoryActivity.this,
								"Department inserted successfully",
								Toast.LENGTH_SHORT).show();

						ContentValues contentValues1 = new ContentValues();
						contentValues1.put(DatabaseForDemo.UNIQUE_ID, uniqueid);
						contentValues1.put(DatabaseForDemo.CREATED_DATE,
								Parameters.currentTime());
						contentValues1.put(DatabaseForDemo.MODIFIED_DATE,
								Parameters.currentTime());
						contentValues1
								.put(DatabaseForDemo.MODIFIED_IN, "Local");
						contentValues1.put(DatabaseForDemo.PrinterForDept,
								printerval);
						contentValues1.put(DatabaseForDemo.TimeForDeptPrint,
								printertime);
						contentValues1.put(DatabaseForDemo.DepartmentID,
								dept_id.getText().toString());
						dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.DEPARTMENT_PRINTER_COMMANDS,
								null, contentValues1);
						try {
							JSONObject data = new JSONObject();
							JSONObject jsonobj = new JSONObject();
							jsonobj.put(DatabaseForDemo.DepartmentID, dept_id
									.getText().toString());
							jsonobj.put(DatabaseForDemo.DepartmentDesp,
									dept_desc.getText().toString());
							jsonobj.put(DatabaseForDemo.CategoryForDepartment,
									categoryspinn.getSelectedItem().toString());
							// jsonobj.put(DatabaseForDemo.PrinterForDept,
							// printerval);
							jsonobj.put(DatabaseForDemo.FoodstampableForDept,
									foodcheckval);
							jsonobj.put(DatabaseForDemo.TaxValForDept,
									taxvalforsave);
							jsonobj.put(DatabaseForDemo.UNIQUE_ID, uniqueid);
							JSONArray fields = new JSONArray();
							fields.put(0, jsonobj);
							data.put("fields", fields);
							dataval = data.toString();
							
						} catch (JSONException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}

						Toast.makeText(InventoryActivity.this,
								"Department inserted successfully",
								Toast.LENGTH_SHORT).show();
						
						
						if (Parameters.OriginalUrl.equals("")) {
							System.out.println("there is no server url val");
						} else {
							boolean isnet = Parameters.isNetworkAvailable(InventoryActivity.this);
							if (isnet) {
								new Thread(new Runnable() {
									@Override
									public void run() {
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
												System.out
														.println("delete query"
																+ jj + " is :"
																+ deletequery);

												String insertquerytemp = array
														.getString(ii);
												String insertquery1 = insertquerytemp
														.replace("'", "\"");
												String insertquery = insertquery1
														.replace("\\\"", "'");
												System.out
														.println("delete query"
																+ jj + " is :"
																+ insertquery);

												dbforloginlogoutWriteInvetory.execSQL(deletequery);
												dbforloginlogoutWriteInvetory.execSQL(insertquery);
												System.out
														.println("queries executed"
																+ ii);

											}
										} catch (JSONException e) {
											// TODO Auto-generated catch block
											e.printStackTrace();
										}

										String select = "select *from "
												+ DatabaseForDemo.MISCELLANEOUS_TABLE;
										Cursor cursor = dbforloginlogoutWriteInvetory.rawQuery(
												select, null);
										if (cursor.getCount() > 0) {
											dbforloginlogoutWriteInvetory
													.execSQL("update "
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
											
													dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.MISCELLANEOUS_TABLE,
															null,
															contentValues1);

										}
										cursor.close();
										dataval = "";
									}
								}).start();
							} else {
								
								ContentValues contentValues2 = new ContentValues();
								contentValues2.put(DatabaseForDemo.QUERY_TYPE,
										"insert");
								contentValues2.put(
										DatabaseForDemo.PENDING_USER_ID,
										Parameters.userid);
								contentValues2.put(DatabaseForDemo.PAGE_URL,
										"saveinfo.php");
								contentValues2.put(
										DatabaseForDemo.TABLE_NAME_PENDING,
										DatabaseForDemo.DEPARTMENT_TABLE);
								contentValues2.put(
										DatabaseForDemo.CURRENT_TIME_PENDING,
										Parameters.currentTime());
								contentValues2.put(DatabaseForDemo.PARAMETERS,
										dataval);
								dbforloginlogoutWriteInvetory.insert(
										DatabaseForDemo.PENDING_QUERIES_TABLE,
										null, contentValues2);
								
								
								dataval = "";
							}
						}
					}
					mCursor.close();
					alertDialog1.dismiss();
					listUpdatefordepartment();
				} else {
					Toast.makeText(InventoryActivity.this, "Please enter id",
							Toast.LENGTH_SHORT).show();
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
	}

	@Override
	public void onDeleteClickedforDepartment(View v, final String id) {
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
				// TODO Auto-generated method stub

				
	
				

				final ArrayList<String> getlist = new ArrayList<String>();
				String selectQueryforinstantpo = "SELECT  * FROM "
						+ DatabaseForDemo.DEPARTMENT_TABLE + " where "
						+ DatabaseForDemo.DepartmentID + "=\"" + id + "\"";

				Cursor mCursorforvendor1 = dbforloginlogoutWriteInvetory.rawQuery(
						selectQueryforinstantpo, null);
				if (mCursorforvendor1 != null) {
					if (mCursorforvendor1.moveToFirst()) {
						do {
							String uniqueid = mCursorforvendor1.getString(mCursorforvendor1
									.getColumnIndex(DatabaseForDemo.UNIQUE_ID));
							getlist.add(uniqueid);
						} while (mCursorforvendor1.moveToNext());
					}
				}
				mCursorforvendor1.close();
				String where = DatabaseForDemo.DepartmentID + "=?";
				dbforloginlogoutWriteInvetory.delete(DatabaseForDemo.DEPARTMENT_TABLE, where,
						new String[] { id });

				Toast.makeText(InventoryActivity.this,
						"Department deleted successfully", Toast.LENGTH_SHORT)
						.show();
				try {
					JSONArray unique_ids = new JSONArray();
					for (int i = 0; i < getlist.size(); i++) {
						unique_ids.put(i, getlist.get(i));
						dataval = unique_ids.toString();
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (Parameters.OriginalUrl.equals("")) {
					System.out.println("there is no server url val");
				} else {
					boolean isnet = Parameters.isNetworkAvailable(InventoryActivity.this);
					if (isnet) {
						new Thread(new Runnable() {
							@Override
							public void run() {
								JsonPostMethod jsonpost = new JsonPostMethod();
								String response = jsonpost
										.postmethodfordirectdelete(
												"admin",
												"abcdefg",
												DatabaseForDemo.DEPARTMENT_TABLE,
												Parameters.currentTime(),
												Parameters.currentTime(),
												dataval);
								System.out.println("response test is:"
										+ response);
								
							
								
										
								String servertiem = null;
								try {
									JSONObject obj = new JSONObject(response);
									JSONObject responseobj = obj
											.getJSONObject("response");
									servertiem = responseobj
											.getString("server-time");
									System.out.println("servertime is:"
											+ servertiem);
									/*
									 * JSONArray array =
									 * obj.getJSONArray("insert-queries");
									 * System.out.println(
									 * "array list length for insert is:"
									 * +array.length()); JSONArray array2 =
									 * obj.getJSONArray("delete-queries");
									 * System.out.println(
									 * "array2 list length for delete is:"
									 * +array2.length()); for(int jj = 0,ii = 0;
									 * jj<array2.length() && ii<array.length();
									 * jj++,ii++){ String deletequerytemp =
									 * array2.getString(jj); String deletequery1
									 * = deletequerytemp.replace("'", "\"");
									 * String deletequery =
									 * deletequery1.replace("\\\"", "'");
									 * System.
									 * out.println("delete query"+jj+" is :"
									 * +deletequery);
									 * 
									 * String insertquerytemp =
									 * array.getString(ii); String insertquery1
									 * = insertquerytemp.replace("'", "\"");
									 * String insertquery =
									 * insertquery1.replace("\\\"", "'");
									 * System.
									 * out.println("delete query"+jj+" is :"
									 * +insertquery);
									 * 
									 *dbforloginlogoutReadInvetory.execSQL(deletequery);
									 *dbforloginlogoutReadInvetory.execSQL(insertquery);
									 * System.out.println
									 * ("queries executed"+ii);
									 * 
									 * }
									 */
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								String select = "select *from "
										+ DatabaseForDemo.MISCELLANEOUS_TABLE;
								Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(select, null);
								if (cursor.getCount() > 0) {
								dbforloginlogoutWriteInvetory.execSQL("update "
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
											DatabaseForDemo.MISCEL_PAGEURL, "");
									contentValues1
											.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL,
													Parameters.currentTime());
									contentValues1
											.put(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL,
													Parameters.currentTime());
									dbforloginlogoutWriteInvetory.insert(
											DatabaseForDemo.MISCELLANEOUS_TABLE,
											null, contentValues1);
									
								}
								cursor.close();
								dataval = "";
							}
						}).start();
					} else {
						
			
						

						ContentValues contentValues1 = new ContentValues();
						contentValues1
								.put(DatabaseForDemo.QUERY_TYPE, "delete");
						contentValues1.put(DatabaseForDemo.PENDING_USER_ID,
								Parameters.userid);
						contentValues1.put(DatabaseForDemo.PAGE_URL,
								"deleteinfo.php");
						contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING,
								DatabaseForDemo.DEPARTMENT_TABLE);
						contentValues1.put(
								DatabaseForDemo.CURRENT_TIME_PENDING,
								Parameters.currentTime());
						contentValues1.put(DatabaseForDemo.PARAMETERS, dataval);
						dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null,
								contentValues1);
						
						
						dataval = "";
					}
				}
				
				
				listUpdatefordepartment();
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

	public void listUpdatefordepartment() {

		id_data.clear();
		desc_data.clear();
		cat_data.clear();
		total_cat_data.clear();
		total_desc_data.clear();
		total_id_data.clear();
		ll.removeAllViews();
		
		

		String selectQuery = "SELECT  * FROM "
				+ DatabaseForDemo.DEPARTMENT_TABLE;

		Cursor mCursor =dbforloginlogoutReadInvetory.rawQuery(selectQuery, null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					if(mCursor.isNull(mCursor.getColumnIndex(DatabaseForDemo.DepartmentID))){
						total_id_data.add("");
					}else{
						String deptid = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.DepartmentID));
						total_id_data.add(deptid);
					}
					if(mCursor.isNull(mCursor.getColumnIndex(DatabaseForDemo.DepartmentDesp))){
						total_desc_data.add("");
					}else{
					String deptdesc = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.DepartmentDesp));
					total_desc_data.add(deptdesc);
					}
					if(mCursor.isNull(mCursor.getColumnIndex(DatabaseForDemo.CategoryForDepartment))){
						total_cat_data.add("");
					}else{
					String catid = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.CategoryForDepartment));
					total_cat_data.add(catid);
					}
				} while (mCursor.moveToNext());
			}
			
			
			
			totalcount = total_id_data.size();
			System.out.println("total count value is:" + totalcount);

			int to = totalcount / pagecount;
			int too = totalcount % pagecount;
			int i = to;
			if (too != 0) {
				i = to + 1;
			}
			countfordepartment(1);
			ll.setWeightSum(i);

			for (j = 1; j <= i; j++) {
				final Button btn = new Button(this);
				btn.setText("" + j);
				btn.setId(j);
				btn.setBackgroundResource(R.drawable.pnormal);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
						1f);

				LinearLayout space = new LinearLayout(this);
				LayoutParams lp1 = new LayoutParams(1,
						LayoutParams.WRAP_CONTENT);
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
						}
						btn.setBackgroundResource(R.drawable.pactive);
						btn.setTextColor(Color.WHITE);
						countfordepartment(Integer.parseInt(btn.getText()
								.toString().trim()));
						testforid = Integer.parseInt(btn.getText().toString()
								.trim());
					}

				});

			}

		}
		mCursor.close();
	}

	public void countforvendor(int j) {
		testforid = 1;
		stLoop = (j - 1) * pagecount;
		endLoop = j * pagecount;
		if (endLoop >= totalcount) {
			endLoop = totalcount;
		}
		no_data = getDataforvendor(stLoop, endLoop, total_no_data);
		company_data = getDataforvendor(stLoop, endLoop, total_company_data);

		ImageAdapterForVendor adapter = new ImageAdapterForVendor(this,
				company_data, no_data);
		adapter.setListener(this);
		list_View.setAdapter(adapter);
	}

	public ArrayList<String> getDataforvendor(int offset, int limit,
			ArrayList<String> list) {
		ArrayList<String> newList = new ArrayList<String>(limit);
		int end = limit;

		if (end > list.size()) {
			end = list.size();
		}
		newList.addAll(list.subList(offset, end));
		return newList;
	}

	public void dataclear() {
		no_data.clear();
		terms_data.clear();
		min_order_data.clear();
		commission_data.clear();
		company_data.clear();
		rent_data.clear();
		tax_data.clear();
		billable_data.clear();
		social_data.clear();
		po_data.clear();
		street_data.clear();
		extended_data.clear();
		city_data.clear();
		state_data.clear();
		zip_data.clear();
		country_data.clear();
		first_data.clear();
		last_data.clear();
		phone_data.clear();
		fax_data.clear();
		email_data.clear();
		website_data.clear();

		total_no_data.clear();
		total_terms_data.clear();
		total_min_order_data.clear();
		total_commission_data.clear();
		total_company_data.clear();
		total_rent_data.clear();
		total_tax_data.clear();
		total_billable_data.clear();
		total_social_data.clear();
		total_po_data.clear();
		total_street_data.clear();
		total_extended_data.clear();
		total_city_data.clear();
		total_state_data.clear();
		total_zip_data.clear();
		total_country_data.clear();
		total_first_data.clear();
		total_last_data.clear();
		total_phone_data.clear();
		total_fax_data.clear();
		total_email_data.clear();
		total_website_data.clear();

	}

	@Override
	public void onEditClickedforVendor(View v, final String id) {
		// TODO Auto-generated method stub
		final AlertDialog alertDialog1 = new AlertDialog.Builder(this).create();
		final EditText v_no, v_terms, v_minorder, v_commission, v_company, v_flatrent, v_tax, v_billable, v_social, v_street, v_extended, v_city, v_state, v_zip, v_country, v_firstname, v_lastname, v_phone, v_fax, v_email, v_website;

		final Spinner poMethod;
		Button save, cancel;

		LayoutInflater mInflater = LayoutInflater.from(this);
		View layout = mInflater.inflate(R.layout.vendor_details, null);
		v_no = (EditText) layout.findViewById(R.id.v_no);
		v_terms = (EditText) layout.findViewById(R.id.v_terms);
		v_minorder = (EditText) layout.findViewById(R.id.v_min_order);
		v_commission = (EditText) layout.findViewById(R.id.v_commission);
		v_company = (EditText) layout.findViewById(R.id.v_company);
		v_flatrent = (EditText) layout.findViewById(R.id.v_flatrent);
		v_tax = (EditText) layout.findViewById(R.id.v_tax);
		v_billable = (EditText) layout.findViewById(R.id.v_billable);
		v_social = (EditText) layout.findViewById(R.id.v_social);
		v_street = (EditText) layout.findViewById(R.id.v_street);
		v_extended = (EditText) layout.findViewById(R.id.v_extended);
		v_city = (EditText) layout.findViewById(R.id.v_city);
		v_state = (EditText) layout.findViewById(R.id.v_state);
		v_zip = (EditText) layout.findViewById(R.id.v_zip);
		v_country = (EditText) layout.findViewById(R.id.v_country);
		v_firstname = (EditText) layout.findViewById(R.id.v_firstname);
		v_lastname = (EditText) layout.findViewById(R.id.v_lastname);
		v_phone = (EditText) layout.findViewById(R.id.v_phone);
		v_fax = (EditText) layout.findViewById(R.id.v_fax);
		v_email = (EditText) layout.findViewById(R.id.v_email);
		v_website = (EditText) layout.findViewById(R.id.v_website);
		poMethod = (Spinner) layout.findViewById(R.id.v_po);
		ArrayList<String> pomethodList = new ArrayList<String>();
		pomethodList.clear();
		pomethodList.add("Print");
		pomethodList.add("Fax");
		pomethodList.add("Email");
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, pomethodList);
		poMethod.setAdapter(adapter);
		String selectQuery = "SELECT  * FROM " + DatabaseForDemo.VENDOR_TABLE
				+ " where " + DatabaseForDemo.VENDOR_NO + "=\"" + id + "\"";

		Cursor mCursor =dbforloginlogoutReadInvetory.rawQuery(selectQuery, null);
		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					String vendorno = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_NO));
					v_no.setText(vendorno);
					String terms = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_TERMS));
					v_terms.setText(terms);
					String min = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_MIN_ORDER));
					v_minorder.setText(min);
					String commission = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.VENDOR_COMMISSION_PERCENT));
					v_commission.setText(commission);
					String company = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.VENDOR_COMPANY_NAME));
					v_company.setText(company);
					String rent = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.VENDOR_FLAT_RENT_RATE));
					v_flatrent.setText(rent);
					String tax = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_TAX_ID));
					v_tax.setText(tax);
					String billable = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.VENDOR_BILLABLE_DEPARTMENT));
					v_billable.setText(billable);
					String social = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.VENDOR_SOCIAL_SECURITY_NO));
					v_social.setText(social);
					String po = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.VENDOR_PO_DELIVERY_METHOD));
					poMethod.setSelection(pomethodList.indexOf(po));
					String street = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.VENDOR_STREET_ADDRESS));
					v_street.setText(street);
					String extended = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.VENDOR_EXTENDED_ADDRESS));
					v_extended.setText(extended);
					String city = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_CITY));
					v_city.setText(city);
					String state = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_STATE));
					v_state.setText(state);
					String zip = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_ZIP_CODE));
					v_zip.setText(zip);
					String country = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_COUNTRY));
					v_country.setText(country);
					String first = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_FIRST_NAME));
					v_firstname.setText(first);
					String last = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_LAST_NAME));
					v_lastname.setText(last);
					String phone = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.VENDOR_TELEPHONE_NUMBER));
					v_phone.setText(phone);
					String fax = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_FAX_NUMBER));
					v_fax.setText(fax);
					String email = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_EMAIL));
					v_email.setText(email);
					String website = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_WEBSITE));
					v_website.setText(website);
				} while (mCursor.moveToNext());
			}
			
			
			
		}
		mCursor.close();
		save = (Button) layout.findViewById(R.id.save);
		cancel = (Button) layout.findViewById(R.id.cancel);
		save.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub

				if (v_no.getText().length() > 0) {
					
		
					
							
					dbforloginlogoutWriteInvetory.execSQL("update vendor set "
							+ DatabaseForDemo.CREATED_DATE + "=\""
							+ Parameters.currentTime() + "\", "
							+ DatabaseForDemo.MODIFIED_DATE + "=\""
							+ Parameters.currentTime() + "\", "
							+ DatabaseForDemo.MODIFIED_IN + "=\"" + "Local"
							+ "\", " + DatabaseForDemo.VENDOR_NO + "=\""
							+ v_no.getText().toString() + "\", "
							+ DatabaseForDemo.VENDOR_FIRST_NAME + "=\""
							+ v_firstname.getText().toString() + "\", "
							+ DatabaseForDemo.VENDOR_LAST_NAME + "=\""
							+ v_lastname.getText().toString() + "\", "
							+ DatabaseForDemo.VENDOR_COMPANY_NAME + "=\""
							+ v_company.getText().toString() + "\", "
							+ DatabaseForDemo.VENDOR_TERMS + "=\""
							+ v_terms.getText().toString() + "\", "
							+ DatabaseForDemo.VENDOR_TAX_ID + "=\""
							+ v_tax.getText().toString() + "\", "
							+ DatabaseForDemo.VENDOR_FAX_NUMBER + "=\""
							+ v_fax.getText().toString() + "\", "
							+ DatabaseForDemo.VENDOR_TELEPHONE_NUMBER + "=\""
							+ v_phone.getText().toString() + "\", "
							+ DatabaseForDemo.VENDOR_STREET_ADDRESS + "=\""
							+ v_street.getText().toString() + "\", "
							+ DatabaseForDemo.VENDOR_EXTENDED_ADDRESS + "=\""
							+ v_extended.getText().toString() + "\", "
							+ DatabaseForDemo.VENDOR_CITY + "=\""
							+ v_city.getText().toString() + "\", "
							+ DatabaseForDemo.VENDOR_STATE + "=\""
							+ v_state.getText().toString() + "\", "
							+ DatabaseForDemo.VENDOR_ZIP_CODE + "=\""
							+ v_zip.getText().toString() + "\", "
							+ DatabaseForDemo.VENDOR_COUNTRY + "=\""
							+ v_country.getText().toString() + "\", "
							+ DatabaseForDemo.VENDOR_FLAT_RENT_RATE + "=\""
							+ v_flatrent.getText().toString() + "\", "
							+ DatabaseForDemo.VENDOR_MIN_ORDER + "=\""
							+ v_minorder.getText().toString() + "\", "
							+ DatabaseForDemo.VENDOR_COMMISSION_PERCENT + "=\""
							+ v_commission.getText().toString() + "\", "
							+ DatabaseForDemo.VENDOR_BILLABLE_DEPARTMENT + "=\""
							+ v_billable.getText().toString() + "\", "
							+ DatabaseForDemo.VENDOR_SOCIAL_SECURITY_NO + "=\""
							+ v_social.getText().toString() + "\", "
							+ DatabaseForDemo.VENDOR_EMAIL + "=\""
							+ v_email.getText().toString() + "\", "
							+ DatabaseForDemo.VENDOR_WEBSITE + "=\""
							+ v_website.getText().toString() + "\", "
							+ DatabaseForDemo.VENDOR_PO_DELIVERY_METHOD + "=\""
							+ poMethod.getSelectedItem().toString()
							+ "\" where " + DatabaseForDemo.VENDOR_NO + "=\""
							+ id + "\"");
					Toast.makeText(InventoryActivity.this,
							"Vendor saved successfully", Toast.LENGTH_SHORT)
							.show();

					final ArrayList<JSONObject> getlist = new ArrayList<JSONObject>();
					String selectQueryforinstantpo = "SELECT  * FROM "
							+ DatabaseForDemo.VENDOR_TABLE + " where "
							+ DatabaseForDemo.VENDOR_NO + "=\"" + id + "\"";

					Cursor mCursorforvendor1 = dbforloginlogoutWriteInvetory.rawQuery(
							selectQueryforinstantpo, null);
					if (mCursorforvendor1 != null) {
						if (mCursorforvendor1.moveToFirst()) {
							do {
								try {
									JSONObject jsonobj = new JSONObject();
									String departmentforproduct = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.VENDOR_NO));
									jsonobj.put(DatabaseForDemo.VENDOR_NO,
											departmentforproduct);
									String itemname = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.VENDOR_TERMS));
									jsonobj.put(DatabaseForDemo.VENDOR_TERMS,
											itemname);
									String departmentforproduct1 = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.VENDOR_MIN_ORDER));
									jsonobj.put(
											DatabaseForDemo.VENDOR_MIN_ORDER,
											departmentforproduct1);
									String itemname1 = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.VENDOR_COMMISSION_PERCENT));
									jsonobj.put(
											DatabaseForDemo.VENDOR_COMMISSION_PERCENT,
											itemname1);
									String departmentforproduct2 = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.VENDOR_COMPANY_NAME));
									jsonobj.put(
											DatabaseForDemo.VENDOR_COMPANY_NAME,
											departmentforproduct2);
									String itemname2 = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.VENDOR_FLAT_RENT_RATE));
									jsonobj.put(
											DatabaseForDemo.VENDOR_FLAT_RENT_RATE,
											itemname2);
									String tax = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.VENDOR_TAX_ID));
									jsonobj.put(DatabaseForDemo.VENDOR_TAX_ID,
											tax);
									String billable = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.VENDOR_BILLABLE_DEPARTMENT));
									jsonobj.put(
											DatabaseForDemo.VENDOR_BILLABLE_DEPARTMENT,
											billable);
									String social = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.VENDOR_SOCIAL_SECURITY_NO));
									jsonobj.put(
											DatabaseForDemo.VENDOR_SOCIAL_SECURITY_NO,
											social);
									String pomethod = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.VENDOR_PO_DELIVERY_METHOD));
									jsonobj.put(
											DatabaseForDemo.VENDOR_PO_DELIVERY_METHOD,
											pomethod);
									String street = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.VENDOR_STREET_ADDRESS));
									jsonobj.put(
											DatabaseForDemo.VENDOR_STREET_ADDRESS,
											street);
									String extended = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.VENDOR_EXTENDED_ADDRESS));
									jsonobj.put(
											DatabaseForDemo.VENDOR_EXTENDED_ADDRESS,
											extended);
									String city = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.VENDOR_CITY));
									jsonobj.put(DatabaseForDemo.VENDOR_CITY,
											city);
									String state = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.VENDOR_STATE));
									jsonobj.put(DatabaseForDemo.VENDOR_TERMS,
											state);
									String zipcode = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.VENDOR_ZIP_CODE));
									jsonobj.put(
											DatabaseForDemo.VENDOR_ZIP_CODE,
											zipcode);
									String country = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.VENDOR_COUNTRY));
									jsonobj.put(DatabaseForDemo.VENDOR_COUNTRY,
											country);
									String firstname = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.VENDOR_FIRST_NAME));
									jsonobj.put(
											DatabaseForDemo.VENDOR_FIRST_NAME,
											firstname);
									String lastname = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.VENDOR_LAST_NAME));
									jsonobj.put(
											DatabaseForDemo.VENDOR_LAST_NAME,
											lastname);
									String phone = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.VENDOR_TELEPHONE_NUMBER));
									jsonobj.put(
											DatabaseForDemo.VENDOR_TELEPHONE_NUMBER,
											phone);
									String email = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.VENDOR_EMAIL));
									jsonobj.put(DatabaseForDemo.VENDOR_EMAIL,
											email);
									String fax = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.VENDOR_FAX_NUMBER));
									jsonobj.put(
											DatabaseForDemo.VENDOR_FAX_NUMBER,
											fax);
									String web = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.VENDOR_WEBSITE));
									jsonobj.put(DatabaseForDemo.VENDOR_WEBSITE,
											web);
									String uniqueid = mCursorforvendor1.getString(mCursorforvendor1
											.getColumnIndex(DatabaseForDemo.UNIQUE_ID));
									jsonobj.put(DatabaseForDemo.UNIQUE_ID,
											uniqueid);
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
						}
					} catch (JSONException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}

					if (Parameters.OriginalUrl.equals("")) {
						System.out.println("there is no server url val");
					} else {
						boolean isnet = Parameters.isNetworkAvailable(InventoryActivity.this);
						if (isnet) {
							new Thread(new Runnable() {
								@Override
								public void run() {
									JsonPostMethod jsonpost = new JsonPostMethod();
									String response = jsonpost
											.postmethodfordirect(
													"admin",
													"abcdefg",
													DatabaseForDemo.VENDOR_TABLE,
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

										dbforloginlogoutWriteInvetory.execSQL(deletequery);
										dbforloginlogoutWriteInvetory.execSQL(insertquery);
											System.out
													.println("queries executed"
															+ ii);

										}
									} catch (JSONException e) {
										// TODO Auto-generated catch block
										e.printStackTrace();
									}

									String select = "select *from "
											+ DatabaseForDemo.MISCELLANEOUS_TABLE;
									Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(select, null);
									if (cursor.getCount() > 0) {
									dbforloginlogoutWriteInvetory.execSQL("update "
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
										dbforloginlogoutWriteInvetory.insert(
												DatabaseForDemo.MISCELLANEOUS_TABLE,
												null, contentValues1);
										
									}
									cursor.close();
									dataval = "";
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
									DatabaseForDemo.VENDOR_TABLE);
							contentValues1.put(
									DatabaseForDemo.CURRENT_TIME_PENDING,
									Parameters.currentTime());
							contentValues1.put(DatabaseForDemo.PARAMETERS,
									dataval);
							dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.PENDING_QUERIES_TABLE,
									null, contentValues1);
							dataval = "";
						}
					}
					
					
					listUpdateforvendor();
					alertDialog1.dismiss();
				} else {
					Toast.makeText(InventoryActivity.this,
							"Please enter vendor number", Toast.LENGTH_SHORT)
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
	}

	@Override
	public void onDeleteClickedforVendor(View v, final String id) {
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
				// TODO Auto-generated method stub
				final ArrayList<String> getlist = new ArrayList<String>();
				String selectQueryforinstantpo = "SELECT  * FROM "
						+ DatabaseForDemo.VENDOR_TABLE + " where "
						+ DatabaseForDemo.VENDOR_NO + "=\"" + id + "\"";

				Cursor mCursorforvendor1 = dbforloginlogoutWriteInvetory.rawQuery(
						selectQueryforinstantpo, null);
				if (mCursorforvendor1 != null) {
					if (mCursorforvendor1.moveToFirst()) {
						do {
							String uniqueid = mCursorforvendor1.getString(mCursorforvendor1
									.getColumnIndex(DatabaseForDemo.UNIQUE_ID));
							getlist.add(uniqueid);
						} while (mCursorforvendor1.moveToNext());
					}
				}
				mCursorforvendor1.close();
				String where = DatabaseForDemo.VENDOR_NO + "=?";
				dbforloginlogoutWriteInvetory.delete(DatabaseForDemo.VENDOR_TABLE, where,
						new String[] { id });

				Toast.makeText(InventoryActivity.this,
						"Vendor deleted successfully", Toast.LENGTH_SHORT)
						.show();

				try {
					JSONArray unique_ids = new JSONArray();
					for (int i = 0; i < getlist.size(); i++) {
						unique_ids.put(i, getlist.get(i));
						dataval = unique_ids.toString();
					}
				} catch (JSONException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

				if (Parameters.OriginalUrl.equals("")) {
					System.out.println("there is no server url val");
				} else {
					boolean isnet = Parameters.isNetworkAvailable(InventoryActivity.this);
					if (isnet) {
						new Thread(new Runnable() {
							@Override
							public void run() {
								JsonPostMethod jsonpost = new JsonPostMethod();
								String response = jsonpost
										.postmethodfordirectdelete("admin",
												"abcdefg",
												DatabaseForDemo.VENDOR_TABLE,
												Parameters.currentTime(),
												Parameters.currentTime(),
												dataval);
								System.out.println("response test is:"
										+ response);
								
								
										
								String servertiem = null;
								try {
									JSONObject obj = new JSONObject(response);
									JSONObject responseobj = obj
											.getJSONObject("response");
									servertiem = responseobj
											.getString("server-time");
									System.out.println("servertime is:"
											+ servertiem);
									/*
									 * JSONArray array =
									 * obj.getJSONArray("insert-queries");
									 * System.out.println(
									 * "array list length for insert is:"
									 * +array.length()); JSONArray array2 =
									 * obj.getJSONArray("delete-queries");
									 * System.out.println(
									 * "array2 list length for delete is:"
									 * +array2.length()); for(int jj = 0,ii = 0;
									 * jj<array2.length() && ii<array.length();
									 * jj++,ii++){ String deletequerytemp =
									 * array2.getString(jj); String deletequery1
									 * = deletequerytemp.replace("'", "\"");
									 * String deletequery =
									 * deletequery1.replace("\\\"", "'");
									 * System.
									 * out.println("delete query"+jj+" is :"
									 * +deletequery);
									 * 
									 * String insertquerytemp =
									 * array.getString(ii); String insertquery1
									 * = insertquerytemp.replace("'", "\"");
									 * String insertquery =
									 * insertquery1.replace("\\\"", "'");
									 * System.
									 * out.println("delete query"+jj+" is :"
									 * +insertquery);
									 * 
									 *dbforloginlogoutReadInvetory.execSQL(deletequery);
									 *dbforloginlogoutReadInvetory.execSQL(insertquery);
									 * System.out.println
									 * ("queries executed"+ii);
									 * 
									 * }
									 */
								} catch (JSONException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}

								String select = "select *from "
										+ DatabaseForDemo.MISCELLANEOUS_TABLE;
								Cursor cursor =dbforloginlogoutReadInvetory.rawQuery(select, null);
								if (cursor.getCount() > 0) {
								dbforloginlogoutWriteInvetory.execSQL("update "
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
											DatabaseForDemo.MISCEL_PAGEURL, "");
									contentValues1
											.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL,
													Parameters.currentTime());
									contentValues1
											.put(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL,
													Parameters.currentTime());
									dbforloginlogoutWriteInvetory.insert(
											DatabaseForDemo.MISCELLANEOUS_TABLE,
											null, contentValues1);
									
								}
								cursor.close();
								dataval = "";
							}

						}).start();
					} else {
						ContentValues contentValues1 = new ContentValues();
						contentValues1
								.put(DatabaseForDemo.QUERY_TYPE, "delete");
						contentValues1.put(DatabaseForDemo.PENDING_USER_ID,
								Parameters.userid);
						contentValues1.put(DatabaseForDemo.PAGE_URL,
								"deleteinfo.php");
						contentValues1.put(DatabaseForDemo.TABLE_NAME_PENDING,
								DatabaseForDemo.VENDOR_TABLE);
						contentValues1.put(
								DatabaseForDemo.CURRENT_TIME_PENDING,
								Parameters.currentTime());
						contentValues1.put(DatabaseForDemo.PARAMETERS, dataval);
						dbforloginlogoutWriteInvetory.insert(DatabaseForDemo.PENDING_QUERIES_TABLE, null,
								contentValues1);
						dataval = "";
					}
				}
				listUpdateforvendor();
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

	public void listUpdateforvendor() {
		dataclear();
		ll.removeAllViews();

		String selectQuery = "SELECT  * FROM " + DatabaseForDemo.VENDOR_TABLE;

		Cursor mCursor =dbforloginlogoutReadInvetory.rawQuery(selectQuery, null);

		if (mCursor != null) {
			if (mCursor.moveToFirst()) {
				do {
					String vendorno = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_NO));
					total_no_data.add(vendorno);
					String terms = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_TERMS));
					total_terms_data.add(terms);
					String min = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_MIN_ORDER));
					total_min_order_data.add(min);
					String commission = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.VENDOR_COMMISSION_PERCENT));
					total_commission_data.add(commission);
					String company = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.VENDOR_COMPANY_NAME));
					total_company_data.add(company);
					String rent = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.VENDOR_FLAT_RENT_RATE));
					total_rent_data.add(rent);
					String tax = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_TAX_ID));
					total_tax_data.add(tax);
					String billable = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.VENDOR_BILLABLE_DEPARTMENT));
					total_billable_data.add(billable);
					String social = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.VENDOR_SOCIAL_SECURITY_NO));
					total_social_data.add(social);
					String po = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.VENDOR_PO_DELIVERY_METHOD));
					total_po_data.add(po);
					String street = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.VENDOR_STREET_ADDRESS));
					total_street_data.add(street);
					String extended = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.VENDOR_EXTENDED_ADDRESS));
					total_extended_data.add(extended);
					String city = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_CITY));
					total_city_data.add(city);
					String state = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_STATE));
					total_state_data.add(state);
					String zip = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_ZIP_CODE));
					total_zip_data.add(zip);
					String country = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_COUNTRY));
					total_country_data.add(country);
					String first = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_FIRST_NAME));
					total_first_data.add(first);
					String last = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_LAST_NAME));
					total_last_data.add(last);
					String phone = mCursor
							.getString(mCursor
									.getColumnIndex(DatabaseForDemo.VENDOR_TELEPHONE_NUMBER));
					total_phone_data.add(phone);
					String fax = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_FAX_NUMBER));
					total_fax_data.add(fax);
					String email = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_EMAIL));
					total_email_data.add(email);
					String website = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.VENDOR_WEBSITE));
					total_website_data.add(website);
				} while (mCursor.moveToNext());
			}
			totalcount = total_no_data.size();
			System.out.println("total count value is:" + totalcount);

			int to = totalcount / pagecount;
			int too = totalcount % pagecount;
			int i = to;
			if (too != 0) {
				i = to + 1;
			}
			countforvendor(1);
			ll.setWeightSum(i);

			for (j = 1; j <= i; j++) {
				final Button btn = new Button(this);
				btn.setText("" + j);
				btn.setId(j);
				btn.setBackgroundResource(R.drawable.pnormal);
				LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
						LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT,
						1f);

				LinearLayout space = new LinearLayout(this);
				LayoutParams lp1 = new LayoutParams(1,
						LayoutParams.WRAP_CONTENT);
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
						}
						btn.setBackgroundResource(R.drawable.pactive);
						btn.setTextColor(Color.WHITE);
						countforvendor(Integer.parseInt(btn.getText()
								.toString().trim()));
						testforid = Integer.parseInt(btn.getText().toString()
								.trim());
					}

				});

			}

		}
		mCursor.close();
	}

	@Override
	public void onDeleteClickedfororderinginfo(View v,
			HashMap<String, String> map) {
		// TODO Auto-generated method stub
		pricingvendorlistinorderinginfo.remove(map);
		if (pricingvendorlistinorderinginfo != null) {
			OrderingInfoIndividualVendorAdapter modifieradapter = new OrderingInfoIndividualVendorAdapter(
					getApplicationContext(), pricingvendorlistinorderinginfo);
			modifieradapter.setListener(InventoryActivity.this);
			vendorlist.setAdapter(modifieradapter);
		}
		Toast.makeText(InventoryActivity.this, "Deleted successfully",
				Toast.LENGTH_SHORT).show();
	}

	@Override
	public void onEditClickedfororderinginfo(View v, final String vendorname,
			final int position) {
		// TODO Auto-generated method stub
		final AlertDialog alertDialog1 = new AlertDialog.Builder(
				InventoryActivity.this).create();
		LayoutInflater mInflater = LayoutInflater.from(InventoryActivity.this);
		View layout = mInflater.inflate(R.layout.orderinginfopopups, null);
		Button ok = (Button) layout.findViewById(R.id.save);
		Button cancel = (Button) layout.findViewById(R.id.cancel);
		final EditText partno = (EditText) layout.findViewById(R.id.partno);
		final EditText costperase = (EditText) layout
				.findViewById(R.id.cost_case);
		final EditText casecost = (EditText) layout
				.findViewById(R.id.case_cost);
		final EditText noincase = (EditText) layout
				.findViewById(R.id.num_in_case);
		noincase.setOnEditorActionListener(new OnEditorActionListener() {
			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				if (actionId == EditorInfo.IME_ACTION_DONE) {
					InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
					imm.hideSoftInputFromWindow(noincase.getWindowToken(), 0);
				}
				return false;
			}
		});
		partno.setText(pricingvendorlistinorderinginfo.get(position).get(
				"partno"));
		costperase.setText(pricingvendorlistinorderinginfo.get(position).get(
				"costpercase"));
		casecost.setText(pricingvendorlistinorderinginfo.get(position).get(
				"casecost"));
		noincase.setText(pricingvendorlistinorderinginfo.get(position).get(
				"noincase"));

		alertDialog1.setTitle("Add Vendor details");

		ok.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				pricingvendorlistinorderinginfo.remove(position);
				HashMap<String, String> map = new HashMap<String, String>();
				map.put("vendorname", vendorname);
				map.put("partno", partno.getText().toString());
				map.put("costpercase", costperase.getText().toString());
				map.put("casecost", casecost.getText().toString());
				map.put("noincase", noincase.getText().toString());
				map.put("preferred", "true");
				pricingvendorlistinorderinginfo.add(position, map);

				if (pricingvendorlistinorderinginfo != null) {
					OrderingInfoIndividualVendorAdapter adapterformod = new OrderingInfoIndividualVendorAdapter(
							InventoryActivity.this,
							pricingvendorlistinorderinginfo);
					adapterformod.setListener(InventoryActivity.this);
					vendorlist.setAdapter(adapterformod);
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
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		finish();
		super.onPause();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
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
	protected void onDestroy() {
		// TODO Auto-generated method stub
		sqlDBInvetory.close();
		super.onDestroy();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Parameters.printerContext=InventoryActivity.this;
		super.onResume();
	}
	
	public class CustomStringList3 extends ArrayList<String> {
	    @Override
	    public boolean contains(Object o)
	    {
	    	System.out.println("============================");
	        String paramStr = (String)o;
	        for (String s : this) {
	            if (paramStr.equalsIgnoreCase(s)) return true;
	        }
	        return false;
	    }
	}
	
	public boolean containsCaseInsensitive(String strToCompare, ArrayList<String>list)
	{
		for (int i = 0; i < list.size(); i++) 
		{
			if (list.get(i).toString().trim().toLowerCase(Locale.getDefault()).startsWith(strToCompare.toString().trim().toLowerCase(Locale.getDefault())))
			{
				return true;
			}
		}
		return false;
//	    for(String str:list)
//	    {
//	        if(str.equalsIgnoreCase(strToCompare))
//	        {
//	            return(true);
//	        }
//	    }
//	    return(false);
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void sortArrayList(ArrayList<HashMap<String,String>> arrayList, final String strHashMapKey) 
	{
		Collections.sort(arrayList, new Comparator()
		{
			@Override
			public int compare(Object o2, Object o1) 
			{
				HashMap<String,String> map1=(HashMap)o1;
				HashMap<String,String> map2=(HashMap)o2;
				String s1=(String)map1.get(strHashMapKey);
				String s2=(String)map2.get(strHashMapKey);
				if(sortType)
					return s2.compareTo(s1);
				else
					 return s1.compareTo(s2);
			}
		});
		sortType = !sortType;
	}
}