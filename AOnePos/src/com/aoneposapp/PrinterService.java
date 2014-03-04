package com.aoneposapp;

import java.util.Timer;
import java.util.TimerTask;

import com.aoneposapp.utils.DatabaseForDemo;

import android.app.Service;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.IBinder;
import android.util.Log;

public class PrinterService extends Service {

	private static final String TAG = "com.example.aoneposssss";

	@Override
	public void onCreate() {
		Log.i(TAG, "Service onCreate");
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		Timer timer = new Timer(false);
		TimerTask timerTask = new TimerTask() {
			@Override
			public void run() {

				DatabaseForDemo dadd = new DatabaseForDemo(
						getBaseContext());
				SQLiteDatabase dddddd = dadd.getWritableDatabase();
				Log.v("hhhhhfdd454544dddddddhhhh",
						"ffffhhhhhhhhhhhhhhhhf");
				String sql = "select * from "
						+ DatabaseForDemo.COMMANDS_PRINTER_TABLE;
				Cursor mCursor = dddddd.rawQuery(sql, null);
				if (mCursor != null) {
					if (mCursor.getCount() > 0) {
						if (mCursor.moveToFirst()) {
							Log.v("tt", "hhh");
							do {
								String catid = mCursor.getString(mCursor
										.getColumnIndex(DatabaseForDemo.COMMANDS_TIME));
								long tine = Long.valueOf(catid);
								long noww = System.currentTimeMillis();
								String check = mCursor.getString(mCursor
										.getColumnIndex(DatabaseForDemo.UNIQUE_ID));
								if (noww > tine) {
									String printerid = mCursor.getString(mCursor
											.getColumnIndex(DatabaseForDemo.COMMANDS_PRINTER_NAME));

									String vaul = "\n    "
											+ (mCursor.getString(mCursor
													.getColumnIndex(DatabaseForDemo.COMMANDS_ITEM_NAME)))
											+ "\n"
											+ (mCursor.getString(mCursor
													.getColumnIndex(DatabaseForDemo.COMMANDS_MESSAGE)) + "\n \n \n");
									if (printerid.equals("None")) {

									} else {
										Log.v("tt", "ramsitha");
									//	PosMainActivity.printText(printerid, vaul);
									}
									String where = DatabaseForDemo.UNIQUE_ID
											+ "=?";
									dddddd.delete(
											DatabaseForDemo.COMMANDS_PRINTER_TABLE,
											where,
											new String[] { check });
								}
								Log.v("tt", "hhdddddh");
							} while (mCursor.moveToNext());
							Log.v("tt", "hhggggggggggggh");
						}
					}
					Log.v("hhxxxxxxxxx4dddddddhhhh", "fffff");
					mCursor.close();
					dddddd.close();
					dadd.close();
				}

			}
		};
		timer.schedule(timerTask, 0, 20000);
	
		return Service.START_STICKY;
	}

	@Override
	public IBinder onBind(Intent arg0) {
		// TODO Auto-generated method stub
		Log.i(TAG, "Service onBind");
		return null;
	}

	@Override
	public void onDestroy() {
		Log.i(TAG, "Service onDestroy");
	}

}
