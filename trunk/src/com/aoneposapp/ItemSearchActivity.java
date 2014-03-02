package com.aoneposapp;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.database.Cursor;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.aoneposapp.adapters.SearchItemAdapter;
import com.aoneposapp.utils.DatabaseForDemo;
import com.aoneposapp.utils.Inventory;
import com.aoneposapp.utils.MyApplication;
import com.aoneposapp.utils.Parameters;

public class ItemSearchActivity extends Activity {
	private Button button_cancel, go_button;
	private ListView itemlistViewSearch;
	private Spinner catspr, dptspr, vndrspr;
	private SearchItemAdapter mAdapterSearch;
	private ArrayList<Inventory> mItemListSearch = new ArrayList<Inventory>();
	private Inventory mSelectedItemSearch;
	private AutoCompleteTextView actv;
	ArrayList<String> autoTextStringsItems = new ArrayList<String>();
	DatabaseForDemo demodbitem;

	@SuppressWarnings("deprecation")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.msearch);
		Parameters.printerContext=ItemSearchActivity.this;
		itemlistViewSearch = (ListView) findViewById(R.id.itemlistview1a);
		button_cancel = (Button) findViewById(R.id.button_lookcancel);
		actv = (AutoCompleteTextView) findViewById(R.id.editTextfield);
		go_button = (Button) findViewById(R.id.button3);
		catspr = (Spinner) findViewById(R.id.spinner1);
		dptspr = (Spinner) findViewById(R.id.spinner2);
		final ArrayList<String> deptspinnerdataSearch = new ArrayList<String>();
		final ArrayList<String> catspinnerdataSearch = new ArrayList<String>();
		final ArrayList<String> autoTextStrings = new ArrayList<String>();
		final ArrayList<String> autoTextStringsname = new ArrayList<String>();
		final ArrayList<String> autoTextStringsvendor = new ArrayList<String>();
		deptspinnerdataSearch.add("All");
		catspinnerdataSearch.add("All");
		demodbitem = new DatabaseForDemo(ItemSearchActivity.this);

		Cursor mCursor = demodbitem.getReadableDatabase().rawQuery(
				"select " + DatabaseForDemo.DepartmentID + " from "
						+ DatabaseForDemo.DEPARTMENT_TABLE, null);
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
		final ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				ItemSearchActivity.this, android.R.layout.simple_spinner_item,
				deptspinnerdataSearch);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
		dptspr.setAdapter(adapter);

		Cursor mCursor3 = demodbitem.getReadableDatabase().rawQuery(
				"select " + DatabaseForDemo.CategoryId + " from "
						+ DatabaseForDemo.CATEGORY_TABLE, null);
		if (mCursor3 != null) {
			if (mCursor3.moveToFirst()) {
				do {
					String catid = mCursor3.getString(mCursor3
							.getColumnIndex(DatabaseForDemo.CategoryId));
					catspinnerdataSearch.add(catid);
				} while (mCursor3.moveToNext());
			}
		}
		mCursor3.close();

		Cursor itemandNoCursor = demodbitem.getReadableDatabase().rawQuery(
				"select " + DatabaseForDemo.INVENTORY_ITEM_NO + " , "+DatabaseForDemo.INVENTORY_VENDOR+ " , "+
						 DatabaseForDemo.INVENTORY_ITEM_NAME + " from "
						+ DatabaseForDemo.INVENTORY_TABLE, null);
		if (itemandNoCursor != null) {
			if (itemandNoCursor.moveToFirst()) {
				do {

					autoTextStrings
							.add(itemandNoCursor.getString(itemandNoCursor
									.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NO)));
					autoTextStringsname
							.add(itemandNoCursor.getString(itemandNoCursor
									.getColumnIndex(DatabaseForDemo.INVENTORY_ITEM_NAME)));

					autoTextStringsvendor
							.add(itemandNoCursor.getString(itemandNoCursor
									.getColumnIndex(DatabaseForDemo.INVENTORY_VENDOR)));

				} while (itemandNoCursor.moveToNext());
			}
		}
		itemandNoCursor.close();
		autoTextStringsItems.addAll(autoTextStrings);
		autoTextStringsItems.addAll(autoTextStringsname);
		autoTextStringsItems.addAll(autoTextStringsvendor);

		ArrayAdapter<String> adapterauto = new ArrayAdapter<String>(this,
				android.R.layout.select_dialog_item, autoTextStringsItems);
		
		actv.setThreshold(1);
		actv.setAdapter(adapterauto);
		actv.setTextColor(Color.BLACK);
		
		ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(
				ItemSearchActivity.this, android.R.layout.simple_spinner_item,
				catspinnerdataSearch);
		adapter2.setDropDownViewResource(android.R.layout.simple_spinner_item);
		catspr.setAdapter(adapter2);
		catspr.setOnItemSelectedListener(new OnItemSelectedListener() {
			@Override
			public void onItemSelected(AdapterView<?> parentView,
					View selectedItemView, int position, long id) {
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
				Cursor mCursor = demodbitem.getReadableDatabase().rawQuery(qqqqqq
						, null);
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
				adapter.setNotifyOnChange(true);
				adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
				dptspr.setAdapter(adapter);
				}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		go_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				InputMethodManager imm = (InputMethodManager) getSystemService(MyApplication
						.getAppContext().INPUT_METHOD_SERVICE);
				imm.hideSoftInputFromWindow(getWindow().getCurrentFocus()
						.getWindowToken(), 0);
				String str = actv.getText().toString().trim();
				String selectQuery = null;
				if (str.length() > 0) {
					if (autoTextStrings.contains(str)) {
						selectQuery = "SELECT  * FROM "
								+ DatabaseForDemo.INVENTORY_TABLE + " where "
								+ DatabaseForDemo.INVENTORY_ITEM_NO + "=\""
								+ str + "\";";
					} else {
						if (autoTextStringsname.contains(str)) {
							selectQuery = "SELECT  * FROM "
									+ DatabaseForDemo.INVENTORY_TABLE
									+ " where "
									+ DatabaseForDemo.INVENTORY_ITEM_NAME
									+ "=\"" + str + "\";";
						} else {
							selectQuery = "SELECT  * FROM "
									+ DatabaseForDemo.INVENTORY_TABLE
									+ " where "
									+ DatabaseForDemo.INVENTORY_VENDOR + "=\""
									+ str + "\";";
						}
					}

				} else {
					String dp = dptspr.getSelectedItem().toString();
					String cat = catspr.getSelectedItem().toString();

					if (dp.equals("All") && cat.equals("All")) {
						selectQuery = "SELECT  * FROM "
								+ DatabaseForDemo.INVENTORY_TABLE;
					} else {
						if (!dp.equals("All") && !cat.equals("All")) {
							selectQuery = "SELECT  * FROM "
									+ DatabaseForDemo.INVENTORY_TABLE
									+ " where "
									+ DatabaseForDemo.INVENTORY_CATEGORY
									+ "=\"" + cat + "\" and "
									+ DatabaseForDemo.INVENTORY_DEPARTMENT
									+ "=\"" + dp + "\";";
						}

						if (dp.equals("All") && !cat.equals("All")) {
							selectQuery = "SELECT  * FROM "
									+ DatabaseForDemo.INVENTORY_TABLE
									+ " where "
									+ DatabaseForDemo.INVENTORY_CATEGORY
									+ "=\"" + cat + "\";";
							Log.v("ffff", selectQuery);
						}
						if (!dp.equals("All") && cat.equals("All")) {
							selectQuery = "SELECT  * FROM "
									+ DatabaseForDemo.INVENTORY_TABLE
									+ " where "
									+ DatabaseForDemo.INVENTORY_DEPARTMENT
									+ "=\"" + dp + "\";";
							Log.v("ffff", selectQuery);
						}

					}

				}

				List<Inventory> itemListSearch = demodbitem
						.getSelectInventoryList(selectQuery);
				if (itemListSearch.size() < -1) {
					Toast.makeText(getApplicationContext(), "there is no data",
							Toast.LENGTH_SHORT).show();
				}
				if (mItemListSearch == null) {
					mItemListSearch = new ArrayList<Inventory>();
				}
				mItemListSearch.clear();
				mItemListSearch.addAll(itemListSearch);
				if (mAdapterSearch == null) {
					mAdapterSearch = new SearchItemAdapter(
							ItemSearchActivity.this, mItemListSearch);
					itemlistViewSearch.setAdapter(mAdapterSearch);
				} else {
					mAdapterSearch.notifyDataSetChanged();
				}
			}
		});

		button_cancel.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				demodbitem.close();
				finish();
			}
		});
		itemlistViewSearch.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				mSelectedItemSearch = (Inventory) mAdapterSearch.getItem(arg2);
				String itemnum = mSelectedItemSearch.getItemNoAdd();
				Parameters.mSearchItemId = itemnum;
				Parameters.mSearchItemStatus = true;
				demodbitem.close();
				finish();
			}
		});
		addItemtoListSearch();
		
	}

	void addItemtoListSearch() {
		DatabaseForDemo db = new DatabaseForDemo(ItemSearchActivity.this);
		String selectQuery = "SELECT  * FROM "
				+ DatabaseForDemo.INVENTORY_TABLE + " limit 500 offset 0";
		List<Inventory> itemListSearch = demodbitem
				.getSelectInventoryList(selectQuery);

		if (itemListSearch == null) {
			Toast.makeText(getApplicationContext(), "there is no data", 1000)
					.show();
		}
		if (mItemListSearch == null) {
			mItemListSearch = new ArrayList<Inventory>();
		}

		mItemListSearch.addAll(itemListSearch);
		if (mAdapterSearch == null) {
			mAdapterSearch = new SearchItemAdapter(ItemSearchActivity.this,
					mItemListSearch);
			itemlistViewSearch.setAdapter(mAdapterSearch);
		} else {
			mAdapterSearch.notifyDataSetChanged();
		}
		db.close();
	}

	@Override
	public void onBackPressed() {
		// TODO Auto-generated method stub
		super.onBackPressed();
		demodbitem.close();
		finish();
	}
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		demodbitem.close();
		finish();
		super.onPause();
	}
	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		Parameters.printerContext=ItemSearchActivity.this;
		super.onResume();
	}
}
