package com.aoneposapp.adapters;


import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.aoneposapp.R;

public class ImageAdapterForSalesHistory extends BaseAdapter{

	private Context c;
	private ArrayList<String> itemno;
	private ArrayList<String> itemname;
	private ArrayList<String> stock;
	private ArrayList<String> pricecharge;
	private ArrayList<String> desc2;
	private ArrayList<String> vendor;
	private ArrayList<String> extra1;
	private ArrayList<String> extra2;
	int i=2; int z=0;
	private int[] colors = new int[] { 197214228, 170190206 };
	
	public ImageAdapterForSalesHistory(Context c, ArrayList<String> itemno, 
			ArrayList<String> itemname, ArrayList<String> pricecharge, 
			ArrayList<String> stock, ArrayList<String> desc2, ArrayList<String> vendor,
			ArrayList<String> extra1, ArrayList<String> extra2) {
		this.c = c;
		this.itemno = itemno;
		this.itemname = itemname;
		this.pricecharge = pricecharge;
		this.stock = stock;
		this.desc2 = desc2;
		this.vendor = vendor;
		this.extra1 = extra1;
		this.extra2 = extra2;
	}

	@Override
	public int getCount() {
		return itemno.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int rowposition) {
		return rowposition;
	}
	
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.report_listitems1, null);
		}
		int j=i%2;
        Log.e("tagdd",j+"....."+i);
        v.setBackgroundColor(colors[j]);
        i++;
        	final TextView text1 = (TextView) v.findViewById(R.id.textView1);
		  final TextView text2 = (TextView) v.findViewById(R.id.textView2);
		  final TextView text3 = (TextView) v.findViewById(R.id.textView3);
		  final TextView text4 = (TextView) v.findViewById(R.id.textView4);
		  final TextView text5 = (TextView) v.findViewById(R.id.textView5);
		  final TextView text6 = (TextView) v.findViewById(R.id.textView6);
		  final TextView text7 = (TextView) v.findViewById(R.id.textView7); 
		  final TextView text8 = (TextView) v.findViewById(R.id.textView8);
		  
		  text1.setText(itemno.get(position));
		    final String itemnoval = itemno.get(position);
		    System.out.println(itemnoval);
		    text2.setText(itemname.get(position));
		    System.out.println(itemname.get(position));
		    text3.setText(pricecharge.get(position));
		    System.out.println(pricecharge.get(position));
		    text4.setText(stock.get(position));
		    System.out.println(stock.get(position));
		    text5.setText(desc2.get(position));
		    System.out.println(desc2.get(position));
		    text6.setText(vendor.get(position));
		    System.out.println(vendor.get(position));
		    text7.setText(extra1.get(position));
		    System.out.println(extra1.get(position));
		    text8.setText(extra2.get(position));
		    System.out.println(extra2.get(position));
		    
			return v;
	}

}
