
	package com.aoneposapp.adapters;


	import java.util.ArrayList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.aoneposapp.R;

	public class Custom_Adapter extends BaseAdapter{

		private Context c;
		private ArrayList<String> custno;
		private ArrayList<String> firstname;
		private ArrayList<String> lastname;
		private ArrayList<String> address;
		private ArrayList<String> company;
		private ArrayList<String> phone;
		int i=2; int z=0;
		private int[] colors = new int[] { 197214228, 170190206 };
		
		public Custom_Adapter(Context c, ArrayList<String> itemno, 
				ArrayList<String> itemname, ArrayList<String> pricecharge, 
				ArrayList<String> stock, ArrayList<String> desc2, ArrayList<String> vendor) {
			this.c = c;
			this.custno = itemno;
			this.firstname = itemname;
			this.lastname = pricecharge;
			this.address = stock;
			this.company = desc2;
			this.phone = vendor;
		}

		@Override
		public int getCount() {
			return custno.size();
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
				v = vi.inflate(R.layout.prod_edit_list_details, null);
			}
			int j=i%2;
	        Log.e("tagdd",j+"....."+i);
	        v.setBackgroundColor(colors[j]);
	        i++;
	        final TextView custnotext = (TextView)v.findViewById(R.id.itemno);
			  final TextView firstnametext = (TextView)v.findViewById(R.id.itemname);
			  final TextView lastnametext = (TextView)v.findViewById(R.id.price);
			  final TextView addresstext = (TextView)v.findViewById(R.id.stock);
			  final TextView companytext = (TextView)v.findViewById(R.id.desc2);
			  final TextView phonetext = (TextView)v.findViewById(R.id.vendor);
			  
				final ImageView edit = (ImageView)v.findViewById(R.id.editimg);
				final ImageView delete = (ImageView)v.findViewById(R.id.deleteimg);
				final ImageView duplicate = (ImageView)v.findViewById(R.id.duplicateimg);
				final ImageView instantpo = (ImageView)v.findViewById(R.id.instantpoimg);
			
				edit.setVisibility(View.GONE);
				delete.setVisibility(View.GONE);
				duplicate.setVisibility(View.GONE);
				instantpo.setVisibility(View.GONE);
				
				custnotext.setText(""+position+1);
			    final String itemnoval = custno.get(position);
			    System.out.println(itemnoval);
			    firstnametext.setText(firstname.get(position));
			    System.out.println(firstname.get(position));
			    lastnametext.setText(lastname.get(position));
			    System.out.println(lastname.get(position));
			    addresstext.setText(address.get(position));
			    System.out.println(address.get(position));
			    companytext.setText(company.get(position));
			    System.out.println(company.get(position));
			    phonetext.setText(phone.get(position));
			    System.out.println(phone.get(position));
			   
				return v;
		}

	}
