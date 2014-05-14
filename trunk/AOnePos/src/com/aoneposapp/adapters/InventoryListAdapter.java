package com.aoneposapp.adapters;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aoneposapp.PosMainActivity;
import com.aoneposapp.R;
import com.aoneposapp.utils.Inventory;
import com.aoneposapp.utils.Parameters;

public class InventoryListAdapter extends BaseAdapter{
	DecimalFormat df = new DecimalFormat("#.##");
	Context mContext;
	ArrayList<Inventory> mInventoryList;
	private OnDeleteClicked listener;
	public InventoryListAdapter(Context context,List<Inventory> list) {
		mContext = context;
		mInventoryList = (ArrayList<Inventory>) list;
		System.out.println("mInventoryList"+mInventoryList);
	}
	public void setListener(OnDeleteClicked listener){
		this.listener = listener;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mInventoryList.size();
	}

	@Override
	public Object getItem(int position) {
		return mInventoryList.get(position);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, final ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item_item, null);

			holder = new ViewHolder();
			holder.textViewSerialNo = (TextView) convertView.findViewById(R.id.serialNo);
			holder.textViewItemNo = (TextView) convertView.findViewById(R.id.itemNo);
			holder.textViewItemInfo = (TextView) convertView.findViewById(R.id.itemInfo);
			holder.textViewQuantity = (TextView) convertView.findViewById(R.id.quantity);
			holder.textViewPrice = (TextView) convertView.findViewById(R.id.price);
            holder.deleterow=(ImageView) convertView.findViewById(R.id.deleterow);
            holder.changeprice = (TextView) convertView.findViewById(R.id.changeprice);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}

		holder.textViewSerialNo.setText(String.valueOf(position + 1));
		holder.textViewItemNo.setText(mInventoryList.get(position).getItemNoAdd());
		holder.textViewItemInfo.setText(mInventoryList.get(position).getItemNameAdd());
        holder.textViewQuantity.setText(mInventoryList.get(position).getQuantity());
        holder.changeprice.setText(""+mInventoryList.get(position).getPriceYouChange().toString());
        holder.deleterow.setTag(""+position);
        String qtyStr = mInventoryList.get(position).getQuantity();
        
		Double qty = 1.0;
		if(qtyStr != null && qtyStr.length() > 0) {
			qty =Double.valueOf(qtyStr);
		}
		
		double price = Double.valueOf(mInventoryList.get(position).getPriceYouChange()) * qty;
	//	price = Math.round(price * 100)/100;
		holder.textViewPrice.setText(String.valueOf(df.format(price)));

		holder.deleterow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(Parameters.delete_items_permission){
				Log.v("deleterow",""+v.getTag());
				Log.v("deleterow",""+listener);
				if(listener != null) listener.onDeleteClicked(v, v.getTag().toString());
				}else{
					showAlertDialog(arg2.getContext(), "Sorry", "You are not authenticated to perform this operation.", false);
				}
			}
		});
		return convertView;
	}
	
	static class ViewHolder {
		TextView textViewSerialNo;
		TextView textViewItemNo;
		TextView textViewItemInfo;
		TextView textViewQuantity;
		TextView textViewPrice;
		TextView changeprice;
		ImageView deleterow;
	}
public interface OnDeleteClicked{

		void onDeleteClicked(View v, String string);

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
