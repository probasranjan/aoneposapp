package com.aoneposapp.adapters;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aoneposapp.PosMainActivity;
import com.aoneposapp.R;

public class StoreItemAdapter extends BaseAdapter{
Context c;
ArrayList<String> arr=new ArrayList<String>();
	public StoreItemAdapter(PosMainActivity posMainActivity,
			int simpleSpinnerItem, ArrayList<String> storearray) {
		// TODO Auto-generated constructor stub
		this.c=posMainActivity;
		this.arr=storearray;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return arr.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return arr.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int arg0, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		
		

		
		 convertView = LayoutInflater.from(c).inflate(
					R.layout.itemmmmm, null);
		 TextView tv=(TextView) convertView.findViewById(R.id.textView1);
		 tv.setText(""+arr.get(arg0));
		return convertView;
	}

}
