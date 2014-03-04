package com.aoneposapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aoneposapp.R;

public class ListEditAdapter extends CursorAdapter{
Context context;
int i=2; int z=0;
private OnWidgetItemClicked listener;
private int[] colors = new int[] { 197214228, 170190206 };
	@SuppressWarnings("deprecation")
	public ListEditAdapter(Context context, Cursor c) {
		super(context, c);
		// TODO Auto-generated constructor stub
		this.context=context;
	}
	public void setListener(OnWidgetItemClicked listener){
		this.listener = listener;
	}
	@Override
	public void bindView(View v, Context arg1, Cursor arg2) {
		// TODO Auto-generated method stub
		  String pos = arg2.getString(5);
		  String tag = arg2.getString(6);
		  final TextView text = (TextView)v.findViewById(R.id.showtext);
		  final TextView showvalue = (TextView)v.findViewById(R.id.showvalue);
			final ImageView edit = (ImageView)v.findViewById(R.id.editimg);
			final ImageView delete = (ImageView)v.findViewById(R.id.deleteimg);
			
			
			z++;
			text.setText(pos);
			showvalue.setText(tag);
			if(tag.equals("NO"))
			showvalue.setText("Tax Disable");
			if(tag.equals("YES"))
				showvalue.setText("Tax Enable");
			
			text.setTag(tag);
			delete.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.e("tagdd", text.getText().toString()+"....."+listener);
					if(listener != null) listener.onEditClicked(v, text.getText().toString(),text.getTag().toString());
				}
			});
			edit.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Log.e("tagdd", text.getText().toString()+"....."+listener);
					if(listener != null) listener.onDeleteClicked(v, text.getText().toString());
				}
			});
	}

	@Override
	public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
		// TODO Auto-generated method stub
			 LayoutInflater inflater = LayoutInflater.from(arg2.getContext());
		        View retView = inflater.inflate(R.layout.edit_list_details, arg2, false);
		        int j=i%2;
		        Log.e("tagdd",j+"....."+i);
		        retView.setBackgroundColor(colors[j]);
		        i++;
		        bindView(retView,arg0,arg1);
		        Log.e("tagdd", "....."+listener);
		        return retView;
		       
	}
public interface OnWidgetItemClicked{
		
		
		
		void onEditClicked(View v, String string, String tag);

		void onDeleteClicked(View v, String string);

	}
}
