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

public class SkuArrayAdapter extends BaseAdapter {
	
	private Context c;
	private ArrayList<String> items;
	private OnWidgetItemClicked listener;
     
    public SkuArrayAdapter(Context c, ArrayList<String> items) {
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
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		System.out.println("items length"+items.size());
		System.out.println(items.get(position));
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.sku_list_item, null);
		}
		final TextView text = (TextView)v.findViewById(R.id.showtext);
		final ImageView delete = (ImageView)v.findViewById(R.id.deleteimg);
		final String skuval = items.get(position);
		System.out.println("skuval is"+skuval);
	    text.setText(skuval);
	    delete.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				System.out.println("edit is clicked");
				Log.e("tagdd", text.getText().toString()+"....."+listener);
				if(listener != null) listener.onDeleteClickedforsku(v, text.getText().toString());
			}
		});
		return v;
	}
	
	public interface OnWidgetItemClicked{
		
		void onDeleteClickedforsku(View v, String string);

	}

}


