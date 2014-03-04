package com.aoneposapp.utils;

import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.AsyncTask;
import android.util.Log;

public class JSONforDemoData extends AsyncTask<String, Void, String> {
	ProgressDialog progressDialog;
	GetJSONListener getJSONListener;
	static Context curContext;

	public JSONforDemoData(Context context) {
		curContext = context;
	}

	public static String connect(String url) {
		
		String name="", cost="", no="", dp="", vlu="", pri="", st="", 
				tax="", vndrNo="", vndrName="", qty="", price="", catid="";
		try{
		System.out.println("");
		DatabaseForDemo demofortax = new DatabaseForDemo(curContext);
		SQLiteDatabase dbtax = demofortax.getWritableDatabase();

		ContentValues values1 = new ContentValues();
		values1.put(DatabaseForDemo.TAX_NAME, "Tax1");
		values1.put(DatabaseForDemo.TAX_VALUE, "8.25");
		values1.put(DatabaseForDemo.CREATED_DATE, Parameters.currentTime());
		values1.put(DatabaseForDemo.MODIFIED_DATE, Parameters.currentTime());
		values1.put(DatabaseForDemo.UNIQUE_ID, Parameters.randomValue());
		values1.put(DatabaseForDemo.MODIFIED_IN, "Local");
		dbtax.insert(DatabaseForDemo.TAX_TABLE, null, values1);
		values1.clear();
		ContentValues values2 = new ContentValues();
		values2.put(DatabaseForDemo.TAX_NAME, "Tax2");
		values2.put(DatabaseForDemo.TAX_VALUE, "0");
		values2.put(DatabaseForDemo.CREATED_DATE, Parameters.currentTime());
		values2.put(DatabaseForDemo.MODIFIED_DATE, Parameters.currentTime());
		values2.put(DatabaseForDemo.UNIQUE_ID, Parameters.randomValue());
		values2.put(DatabaseForDemo.MODIFIED_IN, "Local");
		dbtax.insert(DatabaseForDemo.TAX_TABLE, null, values2);
		values2.clear();
		ContentValues values3 = new ContentValues();
		values3.put(DatabaseForDemo.TAX_NAME, "Tax3");
		values3.put(DatabaseForDemo.TAX_VALUE, "0");
		values3.put(DatabaseForDemo.CREATED_DATE, Parameters.currentTime());
		values3.put(DatabaseForDemo.MODIFIED_DATE, Parameters.currentTime());
		values3.put(DatabaseForDemo.UNIQUE_ID, Parameters.randomValue());
		values3.put(DatabaseForDemo.MODIFIED_IN, "Local");
		dbtax.insert(DatabaseForDemo.TAX_TABLE, null, values3);
		values3.clear();
		ContentValues values33 = new ContentValues();
		values33.put(DatabaseForDemo.TAX_NAME, "BarTax");
		values33.put(DatabaseForDemo.TAX_VALUE, "0");
		values33.put(DatabaseForDemo.CREATED_DATE, Parameters.currentTime());
		values33.put(DatabaseForDemo.MODIFIED_DATE, Parameters.currentTime());
		values33.put(DatabaseForDemo.UNIQUE_ID, Parameters.randomValue());
		values33.put(DatabaseForDemo.MODIFIED_IN, "Local");
		dbtax.insert(DatabaseForDemo.TAX_TABLE, null, values33);
		values33.clear();
		dbtax.close();
		demofortax.close();
		}catch(SQLException e){
			e.printStackTrace();
		}
		printerDemoData();
	    orDatabase();
		orDatabaseCat();
		micellaneousTable();
		int p = 0;
		try {

			POIFSFileSystem myFileSystem = new POIFSFileSystem(curContext.getAssets()
					.open("sampledata.xls"));
			Log.e(" before myFileSystem", "" + myFileSystem);
			HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
			Log.e(" before myWorkBook", "" + myWorkBook);
			HSSFSheet mySheet = myWorkBook.getSheetAt(0);
			Log.e(" before mySheet", "" + mySheet);

			Iterator<Row> rowIter = mySheet.rowIterator();
			Log.e(" before rowIter", "" + rowIter);

			while (rowIter.hasNext()) {
				HSSFRow myRow = (HSSFRow) rowIter.next();
				Log.e("  myRow", "" + myRow);
				Iterator<Cell> cellIter = myRow.cellIterator();
				Log.e("  cellIter", "" + cellIter);
				if (p <= 200) {
					p++;
					int i = 0;

					while (cellIter.hasNext()) {
						HSSFCell myCell = (HSSFCell) cellIter.next();
						Log.e("  myCell", "" + myCell);

						if (i == 0)
							name = "" + myCell;

						if (i == 1)
							no = "" + myCell;
						if (i == 2)
							dp = "" + myCell;
						if (i == 3)
							cost = "" + myCell;
						if (i == 4)
							st = "" + myCell;
						if (i == 5)
							vlu = "" + myCell;
						if (i == 6)
							price = "" + myCell;
						if (i == 7)
							vndrNo = "" + myCell;
						if (i == 8)
							vndrName = "" + myCell;
						if (i == 9)
							catid = "" + myCell;

						Log.w("FileUtils", "Cell Value: " + myCell.toString());

						i++;
					}
					String random = Parameters.randomValue();
					String now_date = Parameters.currentTime();
					DatabaseForDemo sqlsA1 = new DatabaseForDemo(curContext);
					SQLiteDatabase sqliteDatabasesA1 = sqlsA1.getWritableDatabase();
					ContentValues contentValues = new ContentValues();
					contentValues.put(DatabaseForDemo.INVENTORY_AVG_COST, cost);
					contentValues.put(DatabaseForDemo.INVENTORY_DEPARTMENT, dp);
					contentValues.put(DatabaseForDemo.INVENTORY_TAXONE, "Tax1");
					contentValues.put(DatabaseForDemo.INVENTORY_IN_STOCK, st);
					contentValues
							.put(DatabaseForDemo.INVENTORY_ITEM_NAME, name);
					contentValues.put(DatabaseForDemo.INVENTORY_ITEM_NO, no);
					contentValues.put(DatabaseForDemo.INVENTORY_PRICE_CHANGE,
							price);
					contentValues.put(DatabaseForDemo.INVENTORY_PRICE_TAX,
							(Double.parseDouble(price) + (Double
									.parseDouble(price) * 8.25 / 100)));
					contentValues.put(DatabaseForDemo.INVENTORY_QUANTITY, "1");
					contentValues.put(
							DatabaseForDemo.INVENTORY_SECOND_DESCRIPTION,
							"description");
					contentValues.put(DatabaseForDemo.INVENTORY_TOTAL_TAX,
							(Double.parseDouble(price) * 8.25 / 100));
					contentValues.put(DatabaseForDemo.UNIQUE_ID, random);
					contentValues.put(DatabaseForDemo.CREATED_DATE, now_date);
					contentValues.put(DatabaseForDemo.MODIFIED_DATE, now_date);
					contentValues.put(DatabaseForDemo.MODIFIED_IN, "Local");
					contentValues
							.put(DatabaseForDemo.INVENTORY_CATEGORY, catid);
					contentValues.put(DatabaseForDemo.INVENTORY_VENDOR, vndrNo);
					contentValues.put(DatabaseForDemo.INVENTORY_NOTES, "none");
					contentValues.put(DatabaseForDemo.CHECKED_VALUE, "true");
					sqliteDatabasesA1.insert(DatabaseForDemo.INVENTORY_TABLE,
							null, contentValues);
					contentValues.clear();
					ContentValues optional_info = new ContentValues();
					optional_info.put(DatabaseForDemo.UNIQUE_ID, random);
					optional_info.put(DatabaseForDemo.CREATED_DATE, now_date);
					optional_info.put(DatabaseForDemo.MODIFIED_DATE, now_date);
					optional_info.put(DatabaseForDemo.MODIFIED_IN, "Local");
					optional_info.put(DatabaseForDemo.BONUS_POINTS, "");
					optional_info.put(DatabaseForDemo.BARCODES, "");
					optional_info.put(DatabaseForDemo.LOCATION, "");
					optional_info.put(DatabaseForDemo.UNIT_SIZE, "");
					optional_info.put(DatabaseForDemo.UNIT_TYPE, "");
					optional_info.put(DatabaseForDemo.COMMISSION_OPTIONAL_INFO,
							"");
					optional_info.put(DatabaseForDemo.INVENTORY_ALLOW_BUYBACK,
							"no");
					optional_info.put(DatabaseForDemo.INVENTORY_PROMPT_PRICE,
							"no");
					optional_info.put(DatabaseForDemo.INVENTORY_FOODSTAMPABLE,
							"no");
					optional_info.put(
							DatabaseForDemo.INVENTORY_PRINT_ON_RECEIPT, "no");
					optional_info.put(
							DatabaseForDemo.INVENTORY_COUNT_THIS_ITEM, "no");
					optional_info.put(DatabaseForDemo.INVENTORY_MODIFIER_ITEM,
							"no");
					optional_info.put(DatabaseForDemo.INVENTORY_ITEM_NO, no);
					sqliteDatabasesA1.insert(
							DatabaseForDemo.OPTIONAL_INFO_TABLE, null,
							optional_info);
					sqlsA1.close();
					sqliteDatabasesA1.close();
				}
			}

		} 
		catch (SQLException ex) {
			ex.printStackTrace();
		}catch (Exception e1x) {
			e1x.printStackTrace();
		}

		String json = null;
		
	
		return json;

	}

	@Override
	public void onPreExecute() {
		progressDialog = new ProgressDialog(curContext);
		progressDialog.setMessage("Loading..Please wait..");
		progressDialog.setCancelable(false);
		progressDialog.setIndeterminate(true);
		progressDialog.show();

	}

	@Override
	protected String doInBackground(String... urls) {
		return connect(urls[0]);
	}

	@Override
	protected void onPostExecute(String json) {
		progressDialog.dismiss();
	}
	static void printerDemoData(){
		try{
		DatabaseForDemo sqlDBpri = new DatabaseForDemo(curContext);
		SQLiteDatabase sqliteDatabasepri = sqlDBpri.getWritableDatabase();
		ContentValues contentValues = new ContentValues();
		contentValues.put(DatabaseForDemo.PRINTER_TEXT, "      AONEPOS" );
		contentValues.put(DatabaseForDemo.PRINTER_UNIT, "30" );
		contentValues.put(DatabaseForDemo.PRINTER_SPACING, "30");
		contentValues.put(DatabaseForDemo.PRINTER_FONT, "0" );
		contentValues
				.put(DatabaseForDemo.PRINTER_ALIGN, "0"  );
		contentValues
		.put(DatabaseForDemo.PRINTER_LANGUAGE, "0"  );
		contentValues
				.put(DatabaseForDemo.PRINTER_WSIZE, "1"   );
		contentValues
				.put(DatabaseForDemo.PRINTER_HSIZE, "1"  );
		contentValues.put(DatabaseForDemo.PRINTER_BOLD, "0" );
		contentValues.put(DatabaseForDemo.PRINTER_UNDERLINE, "0" );
		contentValues.put(DatabaseForDemo.PRINTER_XPOSITION, "0" );
		contentValues.put(DatabaseForDemo.PRINTER_TYPE, "EPSON" );
		contentValues.put(DatabaseForDemo.PRINTER_NAME, "TM-T82II");
		contentValues.put(DatabaseForDemo.PRINTER_IP, "192.168.1.168");
		contentValues.put(DatabaseForDemo.PRINTER_ID,"printer1" );
		sqliteDatabasepri.insert(DatabaseForDemo.PRINTER_TABLE, null,
				contentValues);
		contentValues.clear();
		
		
		contentValues.put(DatabaseForDemo.PRINTER_TEXT, "      AONEPOS" );
		contentValues.put(DatabaseForDemo.PRINTER_UNIT, "30" );
		contentValues.put(DatabaseForDemo.PRINTER_SPACING, "30");
		contentValues.put(DatabaseForDemo.PRINTER_FONT, "0" );
		contentValues
				.put(DatabaseForDemo.PRINTER_ALIGN, "0"  );
		contentValues
		.put(DatabaseForDemo.PRINTER_LANGUAGE, "0"  );
		contentValues
				.put(DatabaseForDemo.PRINTER_WSIZE, "1"   );
		contentValues
				.put(DatabaseForDemo.PRINTER_HSIZE, "1"  );
		contentValues.put(DatabaseForDemo.PRINTER_BOLD, "0" );
		contentValues.put(DatabaseForDemo.PRINTER_UNDERLINE, "0" );
		contentValues.put(DatabaseForDemo.PRINTER_XPOSITION, "0" );
		contentValues.put(DatabaseForDemo.PRINTER_TYPE, "STAR" );
		contentValues.put(DatabaseForDemo.PRINTER_NAME, "TSP100");
		contentValues.put(DatabaseForDemo.PRINTER_IP, "192.168.1.12");
		contentValues.put(DatabaseForDemo.PRINTER_ID,"printer2" );
		sqliteDatabasepri.insert(DatabaseForDemo.PRINTER_TABLE, null,
				contentValues);
		contentValues.clear();
		sqlDBpri.close();
		sqliteDatabasepri.close();
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	private static void orDatabase() {
		try{
		String cat_id = "", dp_dp = "", dp_id = "", food = "";
		DatabaseForDemo sqlDBD1 = new DatabaseForDemo(curContext);
		SQLiteDatabase sqliteDatabaseD1 = sqlDBD1.getWritableDatabase();
		Log.v("gfdkg", "super");

		Log.v("gfdkg", "super1");
		String selectQuery = "SELECT  * FROM "
				+ DatabaseForDemo.INVENTORY_TABLE;
		Cursor mCursor = sqliteDatabaseD1.rawQuery(selectQuery, null);
		//Parameters.mfindServer_url = true;
		if (mCursor != null) {
			Log.v("gfdkg", "super333");
			String server_Url = "nnn";
			if (mCursor.getCount() < 10) {
				Log.v("gfdkg", "super2");
				int p = 0;
				try {

					POIFSFileSystem myFileSystem = new POIFSFileSystem(
							curContext.getAssets().open("department.xls"));
					HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
					HSSFSheet mySheet = myWorkBook.getSheetAt(0);
					Iterator<Row> rowIter = mySheet.rowIterator();
					while (rowIter.hasNext()) {
						HSSFRow myRow = (HSSFRow) rowIter.next();
						Iterator<Cell> cellIter = myRow.cellIterator();
						if (p <= 13) {
							p++;
							int i = 0;

							while (cellIter.hasNext()) {
								HSSFCell myCell = (HSSFCell) cellIter.next();
								Log.e("  myCell", "" + myCell);
								if (i == 0)
									cat_id = "" + myCell;
								if (i == 1)
									dp_id = "" + myCell;
								if (i == 2)
									dp_dp = "" + myCell;
								if (i == 3)
									food = "" + myCell;

								i++;
							}
							String random = Parameters.randomValue();
							String now_date = Parameters.currentTime();

							ContentValues contentValues = new ContentValues();

							contentValues.put(DatabaseForDemo.UNIQUE_ID,
									Parameters.randomValue());
							contentValues.put(DatabaseForDemo.CREATED_DATE,
									Parameters.currentTime());
							contentValues.put(DatabaseForDemo.MODIFIED_DATE,
									Parameters.currentTime());
							contentValues.put(DatabaseForDemo.MODIFIED_IN,
									"Local");
							contentValues.put(DatabaseForDemo.DepartmentID,
									dp_id);
							contentValues.put(DatabaseForDemo.DepartmentDesp,
									dp_dp);
							contentValues.put(
									DatabaseForDemo.CategoryForDepartment,
									cat_id);

							contentValues.put(
									DatabaseForDemo.FoodstampableForDept, "no");
							contentValues.put(DatabaseForDemo.TaxValForDept,
									"");
							contentValues.put(DatabaseForDemo.CHECKED_VALUE,
									"true");
							sqliteDatabaseD1.insert(
									DatabaseForDemo.DEPARTMENT_TABLE, null,
									contentValues);
						}
					}

				} catch (Exception ex) {
				}

			} else {

			}
		}
		mCursor.close();
		sqliteDatabaseD1.close();
		sqlDBD1.close();
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	private static void orDatabaseCat() {
		try{
		String cat_id = "", cat_cat = "";
		DatabaseForDemo sqlDBE1 = new DatabaseForDemo(curContext);
		SQLiteDatabase sqliteDatabaseE1 = sqlDBE1.getWritableDatabase();
		Log.v("gfdkg", "super");

		Log.v("gfdkg", "super1");
		String selectQuery = "SELECT  * FROM "
				+ DatabaseForDemo.INVENTORY_TABLE;
		Cursor mCursor = sqliteDatabaseE1.rawQuery(selectQuery, null);
	//	Parameters.mfindServer_url = true;
		if (mCursor != null) {
			Log.v("gfdkg", "super333");
			if (mCursor.getCount() < 10) {
				Log.v("gfdkg", "super2");
				int p = 0;
				try {

					POIFSFileSystem myFileSystem = new POIFSFileSystem(
							curContext.getAssets().open("categoryaaa.xls"));
					Log.e(" before myFileSystem", "" + myFileSystem);
					// Create a workbookusing the File System
					HSSFWorkbook myWorkBook = new HSSFWorkbook(myFileSystem);
					Log.e(" before myWorkBook", "" + myWorkBook);
					HSSFSheet mySheet = myWorkBook.getSheetAt(0);
					Iterator<Row> rowIter = mySheet.rowIterator();
					while (rowIter.hasNext()) {
						HSSFRow myRow = (HSSFRow) rowIter.next();
						Iterator<Cell> cellIter = myRow.cellIterator();
						if (p <= 10) {
							p++;
							int i = 0;

							while (cellIter.hasNext()) {
								HSSFCell myCell = (HSSFCell) cellIter.next();
								Log.e("  myCell", "" + myCell);
								if (i == 0)
									cat_id = "" + myCell;
								if (i == 1)
									cat_cat = "" + myCell;
								i++;
							}

							ContentValues contentValues = new ContentValues();
							contentValues.put(DatabaseForDemo.UNIQUE_ID,
									Parameters.randomValue());
							contentValues.put(DatabaseForDemo.CREATED_DATE,
									Parameters.currentTime());
							contentValues.put(DatabaseForDemo.MODIFIED_DATE,
									Parameters.currentTime());
							contentValues.put(DatabaseForDemo.MODIFIED_IN,
									"Local");
							contentValues.put(DatabaseForDemo.CategoryId,
									cat_id);
							contentValues.put(DatabaseForDemo.CategoryDesp,
									cat_cat);
							sqliteDatabaseE1.insert(
									DatabaseForDemo.CATEGORY_TABLE, null,
									contentValues);

						}
					}

				} catch (Exception ex) {
				}

			} else {

			}
		}
		mCursor.close();
		sqliteDatabaseE1.close();
		sqlDBE1.close();
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	static void micellaneousTable() {
		try{
		DatabaseForDemo sqlDBK1 = new DatabaseForDemo(curContext);
		SQLiteDatabase sqliteDatabaseK1 = sqlDBK1.getWritableDatabase();
		String selectQuery = "SELECT  * FROM "
				+ DatabaseForDemo.MISCELLANEOUS_TABLE;
		Cursor mCursor = sqliteDatabaseK1.rawQuery(selectQuery, null);
		//Parameters.mfindServer_url = true;
		if (mCursor != null) {
			Log.v("gfdkg", "super333....." + mCursor.getCount());
			String server_Url = "nnn";
			if (mCursor.getCount() < 1) {
				
				ContentValues contentValues = new ContentValues();
				contentValues.put(DatabaseForDemo.MISCEL_STORE, "Store1");
				contentValues.put(DatabaseForDemo.MISCEL_PAGEURL, Parameters.OriginalUrl);
				contentValues.put(DatabaseForDemo.MISCEL_UPDATE_LOCAL,
						Parameters.currentTime());
				contentValues.put(DatabaseForDemo.MISCEL_SERVER_UPDATE_LOCAL,
						Parameters.currentTime());
				Log.i("select", "" + contentValues);
				sqliteDatabaseK1.insert(DatabaseForDemo.MISCELLANEOUS_TABLE,
						null, contentValues);
				contentValues.clear();
			} else {
				if (mCursor.moveToFirst()) {

					String url = mCursor.getString(mCursor
							.getColumnIndex(DatabaseForDemo.MISCEL_PAGEURL));
					if (url.length() > 3) {
						Parameters.OriginalUrl = url;
					}
				}
			}

		}
		mCursor.close();
		sqlDBK1.close();
		sqliteDatabaseK1.close();
		}catch(SQLException e){
			e.printStackTrace();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
}