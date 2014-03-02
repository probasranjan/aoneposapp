package com.aoneposapp.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aoneposapp.R;

public class OrderingInfoIndividualVendorAdapter extends BaseAdapter {
	
	private Context c;
	private ArrayList<HashMap<String, String>> items;
	private OnWidgetItemClicked listener;
     
    public OrderingInfoIndividualVendorAdapter(Context c, ArrayList<HashMap<String, String>> items) {
    	this.c = c;
		this.items = items;
    }

	@Override
	
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	public void setListener(OnWidgetItemClicked listener){
		this.listener = listener;
		 Log.e("tagdd", "....."+listener);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		System.out.println("items length"+items.size());
		System.out.println(items.get(position));
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.prod_edit_list_details, null);
		}
        final TextView vendorname = (TextView)v.findViewById(R.id.itemno);
		  final TextView castper = (TextView)v.findViewById(R.id.itemname);
		  final TextView casecost = (TextView)v.findViewById(R.id.price);
		  final TextView preferred = (TextView)v.findViewById(R.id.stock);
		  final TextView partno = (TextView)v.findViewById(R.id.desc2);
		  final TextView nopercase = (TextView)v.findViewById(R.id.vendor);
			final ImageView edit = (ImageView)v.findViewById(R.id.editimg);
			final ImageView delete = (ImageView)v.findViewById(R.id.deleteimg);
			final ImageView duplicate = (ImageView)v.findViewById(R.id.duplicateimg);
			final ImageView instantpo = (ImageView)v.findViewById(R.id.instantpoimg);
			
			duplicate.setVisibility(View.GONE);
			instantpo.setVisibility(View.GONE);
			
		HashMap<String, String> map = items.get(position);
		vendorname.setText(map.get("vendorname"));
		castper.setText(map.get("costpercase"));
		casecost.setText(map.get("casecost"));
		preferred.setText(map.get("preferred"));
		System.out.println(map.get("preferred"));
		partno.setText(map.get("partno"));
		nopercase.setText(map.get("noincase"));
		
	    delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("edit is clicked");
				Log.e("tagdd", vendorname.getText().toString()+"....."+listener);
				if(listener != null) listener.onEditClickedfororderinginfo(v, vendorname.getText().toString(), position);
			}
		});
	    edit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("edit is clicked");
				Log.e("tagdd", vendorname.getText().toString()+"....."+listener);
				if(listener != null) listener.onDeleteClickedfororderinginfo(v, items.get(position));
			}
		});
		return v;
	}
	
	public interface OnWidgetItemClicked{
		
		void onDeleteClickedfororderinginfo(View v, HashMap<String, String> map);
		void onEditClickedfororderinginfo(View v, String string, int position);

	}

}


