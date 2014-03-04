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

public class RecallListAdapter extends BaseAdapter{

	private Context c;
	private ArrayList<String> idR ;
	private ArrayList<String> amtR;
	private ArrayList<String> emyR;
	private ArrayList<String> cosmR;
	private ArrayList<String> typeR;
	private ArrayList<String> dateR;
	int i=2; int z=0;
	private int[] colors = new int[] { 197214228, 170190206 };
	
	public RecallListAdapter(Context c, ArrayList<String> itemno, 
			ArrayList<String> itemname, ArrayList<String> pricecharge, 
			ArrayList<String> stock, ArrayList<String> desc2, ArrayList<String> vendor) {
		this.c = c;
		this.idR = itemno;
		this.amtR = itemname;
		this.emyR = pricecharge;
		this.cosmR = stock;
		this.typeR = desc2;
		this.dateR = vendor;
	}

	@Override
	public int getCount() {
		return idR.size();
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
			v = vi.inflate(R.layout.recallinvoice_popup, null);
		}
		int j=i%2;
        Log.e("tagdd",j+"....."+i);
        v.setBackgroundColor(colors[j]);
        i++;
        final TextView text0 = (TextView)v.findViewById(R.id.textView0);
		  final TextView text1 = (TextView)v.findViewById(R.id.textView1);
		  final TextView text2 = (TextView)v.findViewById(R.id.textView2);
		  final TextView text3 = (TextView)v.findViewById(R.id.textView3);
		  final TextView text4 = (TextView)v.findViewById(R.id.textView4);
		  final TextView text5 = (TextView)v.findViewById(R.id.textView5);
		  final TextView text6 = (TextView)v.findViewById(R.id.textView6);
		  text0.setText((position+1)+"");
		  text1.setText(idR.get(position)+"" );
		  text2.setText(typeR.get(position)+"");
		  text3.setText(amtR.get(position)+"");
		  text4.setText(dateR.get(position)+"");
		  text5.setText(emyR.get(position)+"");
		  text6.setText(cosmR.get(position)+"");
		  text0.setTextSize(18);
		  text1.setTextSize(18);
		  text2.setTextSize(18);
		  text3.setTextSize(18);
		  text4.setTextSize(18);
		  text5.setTextSize(18);
		  text6.setTextSize(18);
			return v;
	}

}

