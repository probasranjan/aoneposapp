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

public class ImageAdapterForVendor extends BaseAdapter{

	private Context c;
	private ArrayList<String> company;
	private ArrayList<String> vno;
	int i=2; int z=0;
	private OnWidgetItemClicked listener;
	private int[] colors = new int[] { 197214228, 170190206 };
	
	public ImageAdapterForVendor(Context c, ArrayList<String> company, ArrayList<String> vno) {
		this.c = c;
		this.company = company;
		this.vno = vno;
	}

	@Override
	public int getCount() {
		return vno.size();
	}

	@Override
	public Object getItem(int position) {
		return position;
	}

	@Override
	public long getItemId(int rowposition) {
		return rowposition;
	}
	
	public void setListener(OnWidgetItemClicked listener){
		this.listener = listener;
		 Log.e("tagdd", "....."+listener);
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.edit_list_details, null);
		}
		int j=i%2;
        Log.e("tagdd",j+"....."+i);
        v.setBackgroundColor(colors[j]);
        i++;
		final TextView text = (TextView)v.findViewById(R.id.showtext);
		final TextView name = (TextView)v.findViewById(R.id.showvalue);
			final ImageView edit = (ImageView)v.findViewById(R.id.editimg);
			final ImageView delete = (ImageView)v.findViewById(R.id.deleteimg);
			//final ImageView duplicate = (ImageView)v.findViewById(R.id.duplicateimg);
			
			text.setText(company.get(position));
		    final String idval = company.get(position);
		    name.setText(vno.get(position));
		    System.out.println(idval);
		    
		    edit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					System.out.println("edit is clicked");
					Log.e("tagdd", text.getText().toString()+"....."+listener);
					if(listener != null) listener.onDeleteClickedforVendor(v, name.getText().toString());
				}
			});
		    
		    delete.setOnClickListener(new OnClickListener() {
				
				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					System.out.println("delete is clicked");
					Log.e("tagdd", text.getText().toString()+"....."+listener);
					if(listener != null) listener.onEditClickedforVendor(v, name.getText().toString());
				}
			});
		    
		   /* duplicate.setOnClickListener(new OnClickListener() {
				
				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					System.out.println("duplicate is clicked");
					Log.e("tagdd", text.getText().toString()+"....."+listener);
					if(listener != null) listener.onDuplicateClickedforVendor(v, name.getText().toString());
				}
			});*/
			return v;
	}
				
	public interface OnWidgetItemClicked{
		
		void onEditClickedforVendor(View v, String string);
		//void onDuplicateClickedforVendor(View v, String string);
		void onDeleteClickedforVendor(View v, String string);

	}


}
