package com.aoneposapp.adapters;


import java.util.ArrayList;

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

public class ImageAdapterForHold extends BaseAdapter{

	private Context c;
	private ArrayList<String> itemno;
	private ArrayList<String> itemname;
	private ArrayList<String> stock;
	private ArrayList<String> pricecharge;
	private ArrayList<String> desc2;
	private ArrayList<String> vendor;
	int i=2; int z=0;
	fetchButtomEditInterface listener;
	private int[] colors = new int[] { 197214228, 170190206 };
	
	public ImageAdapterForHold(Context c, ArrayList<String> itemno, 
			ArrayList<String> itemname, ArrayList<String> pricecharge, 
			ArrayList<String> stock, ArrayList<String> desc2, ArrayList<String> vendor) {
		this.c = c;
		this.itemno = itemno;
		this.itemname = itemname;
		this.pricecharge = pricecharge;
		this.stock = stock;
		this.desc2 = desc2;
		this.vendor = vendor;
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
	public void setListener(fetchButtomEditInterface listener){
		this.listener = listener;
	}
	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.prod_edit_list_details, null);
		}
		int j=i%2;
        Log.e("tagdd",j+"....."+i);
        v.setBackgroundColor(colors[j]);
        i++;
        final TextView itemnotext = (TextView)v.findViewById(R.id.itemno);
		  final TextView itemnametext = (TextView)v.findViewById(R.id.itemname);
		  final TextView pricetext = (TextView)v.findViewById(R.id.price);
		 final TextView stocktext = (TextView)v.findViewById(R.id.stock);
		  final TextView desc2text = (TextView)v.findViewById(R.id.desc2);
		  final TextView vendortext = (TextView)v.findViewById(R.id.vendor);
			final ImageView edit = (ImageView)v.findViewById(R.id.editimg);
			final ImageView delete = (ImageView)v.findViewById(R.id.deleteimg);
			final ImageView duplicate = (ImageView)v.findViewById(R.id.duplicateimg);
			final ImageView instantpo = (ImageView)v.findViewById(R.id.instantpoimg);
			
			duplicate.setVisibility(View.GONE);
			instantpo.setVisibility(View.GONE);
			itemnotext.setText(itemno.get(position));
			delete.setTag(itemno.get(position));
			edit.setTag(itemno.get(position));
		    final String itemnoval = itemno.get(position);
		    System.out.println(itemnoval);
		    itemnametext.setText(itemname.get(position));
		    System.out.println(itemname.get(position));
		    pricetext.setText(pricecharge.get(position));
		    System.out.println(pricecharge.get(position));
		    desc2text.setText(stock.get(position));
		    System.out.println(stock.get(position));
		    stocktext.setText(desc2.get(position));
		    System.out.println(desc2.get(position));
		    vendortext.setText(vendor.get(position));
		    System.out.println(vendor.get(position));
		    delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.e("tagdd", "....."+listener);
					if(listener != null) listener.onEditClickedforfetch(v,""+v.getTag());
				}
			});
 edit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.e("tagdd", "....."+listener);
					if(listener != null) listener.onClickedforfetchDelete(v,""+v.getTag());
				}
			});
			return v;
	}
public interface fetchButtomEditInterface{
		void onEditClickedforfetch(View v, String string);
		void onClickedforfetchDelete(View v, String string);
	}
}
