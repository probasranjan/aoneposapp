package com.aoneposapp.adapters;

import android.content.Context;
import android.database.Cursor;
import android.support.v4.widget.CursorAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.aoneposapp.R;

public class InvoiceReportsAdapter extends CursorAdapter{
	Context context;
	int i=2; int z=0;
	private int[] colors = new int[] { 197214228, 170190206 };
		@SuppressWarnings("deprecation")
		public InvoiceReportsAdapter(Context context, Cursor c) {
			super(context, c);
			// TODO Auto-generated constructor stub
			this.context=context;
		}
		
		@Override
		public void bindView(View v, Context arg1, Cursor arg2) {
			// TODO Auto-generated method stub
		
			  String tv1 = arg2.getString(9);
			  String tv3 = arg2.getString(1);
			  String tv4 = arg2.getString(5);
			  String tv2 = arg2.getString(13);
			  String tv6 = arg2.getString(10);
			  String tv5 = arg2.getString(7);
			  String tv8 = arg2.getString(2);
			  String tv7 = arg2.getString(3);
			  
			  
			  final TextView text1 = (TextView) v.findViewById(R.id.textView1);
			  final TextView text2 = (TextView) v.findViewById(R.id.textView2);
			  final TextView text3 = (TextView) v.findViewById(R.id.textView3);
			  final TextView text4 = (TextView) v.findViewById(R.id.textView4);
			  final TextView text5 = (TextView) v.findViewById(R.id.textView5);
			  final TextView text6 = (TextView) v.findViewById(R.id.textView6);
			  final TextView text7 = (TextView) v.findViewById(R.id.textView7); 
			  final TextView text8 = (TextView) v.findViewById(R.id.textView8);
			  
				text1.setText(tv1);
				Log.e("1", ""+tv1);
				Log.e("2", ""+tv2);
				Log.e("3", ""+tv3);
				Log.e("4", ""+tv4);
				Log.e("5", ""+tv5);
				Log.e("6", ""+tv6);
				Log.e("7", ""+tv7);
				Log.e("8", ""+tv8);
				Log.e("", "");
				text2.setText(tv2);
				text3.setText(tv3);
				text4.setText(tv4);
				text5.setText(tv5);
				text6.setText(tv6);
				text7.setText(tv8);
				text8.setText(tv7);
				
			
				
		}

		@Override
		public View newView(Context arg0, Cursor arg1, ViewGroup arg2) {
			// TODO Auto-generated method stub
				 LayoutInflater inflater = LayoutInflater.from(arg2.getContext());
			        View retView = inflater.inflate(R.layout.report_listitems, arg2, false);
			        int j=i%2;
			        retView.setBackgroundColor(colors[j]);
			        i++;
			        bindView(retView,arg0,arg1);
			        return retView;
			       
		}
	
	}
