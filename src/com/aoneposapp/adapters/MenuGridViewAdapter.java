package com.aoneposapp.adapters;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.aoneposapp.ContactsActivity;
import com.aoneposapp.CustomerActivity;
import com.aoneposapp.EmployeeActivity;
import com.aoneposapp.InventoryActivity;
import com.aoneposapp.MenuActivity;
import com.aoneposapp.PosMainActivity;
import com.aoneposapp.ProfileActivity;
import com.aoneposapp.R;
import com.aoneposapp.ReportsActivity;
import com.aoneposapp.SettingsActivity;
import com.aoneposapp.StoresActivity;
import com.aoneposapp.utils.Parameters;

public class MenuGridViewAdapter extends BaseAdapter {
	private Context mContext;
	public Integer[] mThumbIds = { R.drawable.posbutton,
			R.drawable.inventorynormal, R.drawable.storepressed,
			R.drawable.customeractive, R.drawable.employeenormal,
			R.drawable.reportsnormal, R.drawable.settingsnormal,
			R.drawable.contactsnormal, R.drawable.profilebutton };
	public Integer[] clicked = { R.drawable.pospressed,
			R.drawable.inventoryactive, R.drawable.storeactive,
			R.drawable.customernormal, R.drawable.employeepressed,
			R.drawable.reportsactive, R.drawable.settingspressed,
			R.drawable.contactsactive, R.drawable.profileactive };

	public MenuGridViewAdapter(MenuActivity menuActivity) {
		// TODO Auto-generated constructor stub
		mContext = menuActivity;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mThumbIds.length;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mThumbIds[arg0];
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View arg1, final ViewGroup arg2) {
		// TODO Auto-generated method stub
		final ImageView imageView = new ImageView(mContext);
		imageView.setImageResource(mThumbIds[position]);
		imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
		imageView.setLayoutParams(new GridView.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		imageView.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				imageView.setImageResource(clicked[position]);

				if(position==0){
					Intent intent = new Intent(arg2.getContext(),
							PosMainActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					arg2.getContext().startActivity(intent);
				}else if(position==1){
					if(Parameters.inventory_permission){
						Intent intent = new Intent(arg2.getContext(),
						InventoryActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
						arg2.getContext().startActivity(intent);
					}else{
						imageView.setImageResource(mThumbIds[position]);
						showAlertDialog(arg2.getContext(), "Sorry", "You are not authenticated to perform this operation.", false);
					}
				}else if(position==2){
					Log.v("rggg", ""+Parameters.usertype);
					if(Parameters.stores_permission){
					Intent intent = new Intent(arg2.getContext(),
							StoresActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					arg2.getContext().startActivity(intent);
				}else{
					imageView.setImageResource(mThumbIds[position]);
						showAlertDialog(arg2.getContext(), "Sorry", "You are not authenticated to perform this operation.", false);
					}
				}else if(position==3){
					if(Parameters.customer_permission){
					Intent intent = new Intent(arg2.getContext(),
							CustomerActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					arg2.getContext().startActivity(intent);
					}else{
						imageView.setImageResource(mThumbIds[position]);
						showAlertDialog(arg2.getContext(), "Sorry", "You are not authenticated to perform this operation.", false);
					}
				}else if(position==4){
					Log.v("rggg", ""+Parameters.usertype);
					if(Parameters.employee_permission){
					Intent intent = new Intent(arg2.getContext(),
							EmployeeActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					arg2.getContext().startActivity(intent);
					}else{
						imageView.setImageResource(mThumbIds[position]);
							showAlertDialog(arg2.getContext(), "Sorry", "You are not authenticated to perform this operation.", false);
						}
				}else if(position==5){
					if(Parameters.reports_permission){
					Intent intent = new Intent(arg2.getContext(),
							ReportsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					arg2.getContext().startActivity(intent);
				}else{
					imageView.setImageResource(mThumbIds[position]);
						showAlertDialog(arg2.getContext(), "Sorry", "You are not authenticated to perform this operation.", false);
					}
				}else if(position==6){
					if(Parameters.settings_permission){
					Intent intent = new Intent(arg2.getContext(),
							SettingsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					arg2.getContext().startActivity(intent);
					}else{
						imageView.setImageResource(mThumbIds[position]);
						showAlertDialog(arg2.getContext(), "Sorry", "You are not authenticated to perform this operation.", false);
					}
				}else if(position==7){
					Intent intent = new Intent(arg2.getContext(),
							ContactsActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					arg2.getContext().startActivity(intent);
				}else if(position==8){
					Log.v("rggg", ""+Parameters.usertype);
					if(Parameters.profile_permission){
					Intent intent = new Intent(arg2.getContext(),
							ProfileActivity.class).setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
					arg2.getContext().startActivity(intent);
					}else{
						imageView.setImageResource(mThumbIds[position]);
						showAlertDialog(arg2.getContext(), "Sorry", "You are not authenticated to perform this operation.", false);
					}
				}

			}
		});
		return imageView;
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
}
