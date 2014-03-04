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

public class EmployeeEditAdapter extends BaseAdapter{

	private Context c;
	private ArrayList<String> id;
	private ArrayList<String> desc;
	int i=2; int z=0;
	private OnWidgetItemClicked listener;
	private int[] colors = new int[] { 197214228, 170190206 };
	
	public EmployeeEditAdapter(Context c, ArrayList<String> id, ArrayList<String> desc) {
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
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		View v = convertView;
		if (v == null) {
			LayoutInflater vi = (LayoutInflater)c.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			v = vi.inflate(R.layout.employee_details, null);
		}
		int j=i%2;
        v.setBackgroundColor(colors[j]);
        i++;
		final TextView text = (TextView)v.findViewById(R.id.empname);
		  final TextView showvalue = (TextView)v.findViewById(R.id.empaddress);
		  final ImageView storeview = (ImageView)v.findViewById(R.id.empview);
			final ImageView edit = (ImageView)v.findViewById(R.id.empedit);
			final ImageView delete = (ImageView)v.findViewById(R.id.empdelete);
		
		    final String idval = id.get(position);
		    System.out.println(idval);
		    text.setText(idval);
		    showvalue.setText(desc.get(position));
		    System.out.println(desc.get(position));
		    
		    
		    storeview.setOnClickListener(new OnClickListener() {
				
				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					System.out.println("View is clicked");
					Log.e("tagdd", text.getText().toString()+"....."+listener);
					if(listener != null) listener.onViewClicked(v, text.getText().toString().trim());
				}
			});
		    edit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					System.out.println("edit is clicked");
					Log.e("tagdd", text.getText().toString()+"....."+listener);
					if(listener != null) listener.onEditClicked(v, text.getText().toString().trim());
					
				}
			});
		    
		    delete.setOnClickListener(new OnClickListener() {
				@SuppressWarnings("deprecation")
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					System.out.println("delete is clicked");
					Log.e("tagdd", text.getText().toString()+"....."+listener);
					if(listener != null) listener.onDeleteClicked(v, text.getText().toString().trim());
				}
			});
			return v;
	}
				
	public interface OnWidgetItemClicked{
		void onViewClicked(View v, String string);
		void onEditClicked(View v, String string);
		void onDeleteClicked(View v, String string);

	}


}
