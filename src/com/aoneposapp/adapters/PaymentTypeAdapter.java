package com.aoneposapp.adapters;

import java.util.ArrayList;
import java.util.HashMap;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aoneposapp.R;

public class PaymentTypeAdapter extends BaseAdapter {
    ArrayList<HashMap<String, String>> listdata;
    Context context;
    String editedtext;
     
    public PaymentTypeAdapter(Context context, ArrayList<HashMap<String, String>> items) {
    	//this.getList = getList;
        super();
        this.context = context;
        this.listdata = items;
    }
 
    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {
    	View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.payedsofar, null);
		}
		
		HashMap<String, String> map = new HashMap<String, String>();
		map = listdata.get(position);
		TextView tv1 = (TextView)v.findViewById(R.id.type);
		TextView tv2 = (TextView)v.findViewById(R.id.amount);
		TextView tv3 = (TextView)v.findViewById(R.id.details);
		
		tv1.setText(map.get("paymentType"));
		tv2.setText(map.get("amount"));
		tv3.setText(map.get("details"));
		
      return v;
    }

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listdata.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return listdata.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}
    
}