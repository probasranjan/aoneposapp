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

public class ImageAdapterForCategory extends BaseAdapter{

	private Context c;
	private ArrayList<String> id;
	private ArrayList<String> desc;
	int i=2; int z=0;
	private OnWidgetItemClicked listener;
	private int[] colors = new int[] { 197214228, 170190206 };
	
	public ImageAdapterForCategory(Context c, ArrayList<String> id, ArrayList<String> desc) {
		this.c = c;
		this.id = id;
		this.desc = desc;
		
		
	}

	@Override
	public int getCount() {
		return id.size();
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
		  final TextView showvalue = (TextView)v.findViewById(R.id.showvalue);
			final ImageView edit = (ImageView)v.findViewById(R.id.editimg);
			final ImageView delete = (ImageView)v.findViewById(R.id.deleteimg);
			//final ImageView duplicate = (ImageView)v.findViewById(R.id.duplicateimg);
			
			text.setText(id.get(position));
		    final String idval = id.get(position);
		    System.out.println(idval);
		    showvalue.setText(desc.get(position));
		    System.out.println(desc.get(position));
		    
		    edit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					System.out.println("edit is clicked");
					Log.e("tagdd", text.getText().toString()+"....."+listener);
					if(listener != null) listener.onDeleteClickedforCategory(v, text.getText().toString());
				}
			});
		    
		    /*duplicate.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					System.out.println("duplicate is clicked");
					Log.e("tagdd", text.getText().toString()+"....."+listener);
					if(listener != null) listener.onDuplicateClickedforCategory(v, text.getText().toString(),showvalue.getText().toString());
				}
			});*/
		    
		    delete.setOnClickListener(new OnClickListener() {
			
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					System.out.println("delete is clicked");
					Log.e("tagdd", text.getText().toString()+"....."+listener);
					if(listener != null) listener.onEditClickedforCategory(v, text.getText().toString(),showvalue.getText().toString());
				}
			});
			return v;
	}
				
	public interface OnWidgetItemClicked{
		
		void onEditClickedforCategory(View v, String string, String tag);
		void onDeleteClickedforCategory(View v, String string);
		//void onDuplicateClickedforCategory(View v, String string, String tag);

	}


}
