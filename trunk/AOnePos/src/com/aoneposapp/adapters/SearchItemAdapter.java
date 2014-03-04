
package com.aoneposapp.adapters;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aoneposapp.R;
import com.aoneposapp.utils.Inventory;

public class SearchItemAdapter extends BaseAdapter{
	private int[] colors = new int[] { 160240250, 227248163 };
	Context mContext;
	int i=2;
	ArrayList<Inventory> mInventoryList;
	public SearchItemAdapter(Context context,
			List<Inventory> list) {
		this.mContext = context;
		this.mInventoryList = (ArrayList<Inventory>) list;
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
	public View getView(int position, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder holder;

		if (convertView == null) {
			convertView = LayoutInflater.from(mContext).inflate(
					R.layout.search_sublist, null);

			holder = new ViewHolder();
			holder.textViewSerialNo = (TextView) convertView
					.findViewById(R.id.s_no);
			holder.textViewItemNo = (TextView) convertView
					.findViewById(R.id.itemno_list);
			holder.textViewItemInfo = (TextView) convertView
					.findViewById(R.id.itemname_list);
			holder.textViewPrice = (TextView) convertView
					.findViewById(R.id.priceyou_list);
			holder.textViewStock = (TextView) convertView
					.findViewById(R.id.instock_list);
			holder.textViewDept = (TextView) convertView
					.findViewById(R.id.desc_list);
			holder.textViewVndr = (TextView) convertView
					.findViewById(R.id.vendor_list);
			convertView.setTag(holder);

		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		  int j=i%2;
	        convertView.setBackgroundColor(colors[j]);
	        i++;
		holder.textViewSerialNo.setText(String.valueOf(position + 1));
		holder.textViewItemNo.setText(mInventoryList.get(position).getItemNoAdd().toString());
		holder.textViewItemInfo.setText(mInventoryList.get(position).getItemNameAdd().toString());
        holder.textViewPrice.setText(mInventoryList.get(position).getPriceYouChange().toString());
        holder.textViewStock.setText(mInventoryList.get(position).getInStock().toString().toString());
		holder.textViewDept.setText(mInventoryList.get(position).getSecondDescription().toString());
        holder.textViewVndr.setText(mInventoryList.get(position).getInventoryVndr().toString());
     //  Log.v("vls",mInventoryList.get(position).getInventoryVndr().toString()+"");
		
		
		//double price = Double.valueOf(mInventoryList.get(position).getPriceYouChange()) * qty;
		//holder.textViewPrice.setText(String.valueOf(price));
		/*holder.deleterow.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.v("deleterow",""+v.getTag());
				Log.v("deleterow",""+listener);
				if(listener != null) listener.onDeleteClicked(v, v.getTag().toString());
			}
		});*/
		return convertView;
	}
	
	static class ViewHolder {
		TextView textViewSerialNo;
		TextView textViewItemNo;
		TextView textViewItemInfo;
		TextView textViewPrice;
		TextView textViewStock;
		TextView textViewDept;
		TextView textViewVndr;
	}
	
}
