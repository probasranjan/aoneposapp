package net.authorize.android.dialog;


import java.util.ArrayList;
import java.util.HashMap;

import net.authorize.android.aim.AIMActivity;
import net.authorize.android.model.AppConstant;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.aoneposapp.R;


public class Dialog_ShoppingDetails {
	private Activity activity;
	private Context context;
	private Dialog mainDialog;
	private TextView textView_TotatlCost;
	private ListView listView;
	private ArrayList<HashMap<String, String>> arrayList_Details;
	private MultiSelectionAdapter<ArrayList<HashMap<String, String>>> adapter;
	private EditText editText_CreditCard,editText_ExpMonth,editText_ExpYear;
	private Button buttonOk;
	private static int total=0;
	public Dialog_ShoppingDetails(Context _context, Activity _activity,ArrayList<HashMap<String,String>> arrayList_Details) {
		this.context = _context;
		this.activity = _activity;
		this.mainDialog = new Dialog(context);
		mainDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		mainDialog.setContentView(R.layout.dialog_shoppingdetials);
		this.mainDialog.show();
		this.mainDialog.setCancelable(false);
		setHeader(this.mainDialog);
		init();
		for(int i=0;i<arrayList_Details.size();i++)
		{
			if(!arrayList_Details.get(i).get("Cost").equals(""))
			total+=Integer.parseInt(arrayList_Details.get(i).get("Cost"));
			AppConstant.arrayList_Items.add(arrayList_Details.get(i).get("Item"));
		}
		Log.e("Total",""+total);
		textView_TotatlCost.setText(""+total);
		adapter = new MultiSelectionAdapter<ArrayList<HashMap<String, String>>>(context, arrayList_Details);
		listView.setAdapter(adapter);
	}

	private void init() {
		buttonOk=(Button)mainDialog.findViewById(R.id.buttonOk);
		editText_CreditCard=(EditText)mainDialog.findViewById(R.id.editText_CreditCard);
		editText_ExpMonth=(EditText)mainDialog.findViewById(R.id.editText_ExpMonth);
		editText_ExpYear=(EditText)mainDialog.findViewById(R.id.editText_ExpYear);
		arrayList_Details=new ArrayList<HashMap<String,String>>();
		listView = (ListView)mainDialog.findViewById(R.id.lv_list);
		textView_TotatlCost=(TextView)mainDialog.findViewById(R.id.textView_TotatlCost);
		
		buttonOk.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				HashMap<String, String> map=new HashMap<String, String>();
				map.put("CardNumber",""+editText_CreditCard.getText().toString().trim());
				map.put("EXPMonth",""+editText_ExpMonth.getText().toString().trim());
				map.put("EXPYear",""+editText_ExpYear.getText().toString().trim());
				map.put("Total",""+total);
				AppConstant.arrayList_Details.add(map);
				activity.finish();
				Intent i=new Intent(context,AIMActivity.class);
				context.startActivity(i);
				
			}
		});
		
	}

	public void setHeader(final Dialog dialog) {
		// Header start
		TextView textViewHeaderTitle = (TextView) dialog.findViewById(R.id.TextViewHeaderTitleDialog);
		TextView textViewCloseHeaderDialog = (TextView) dialog.findViewById(R.id.textViewCloseHeaderDialog);
		ImageView imageViewCloseDialog = (ImageView) dialog.findViewById(R.id.imageViewCloseDialog);
		textViewHeaderTitle.setText("Shopping Details");
		imageViewCloseDialog.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				dialog.dismiss();
			}
		});
		textViewCloseHeaderDialog.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
		// header End
	}
	public class MultiSelectionAdapter<T> extends BaseAdapter {
		Context mContext;
		LayoutInflater mInflater;
		ArrayList<HashMap<String, String>> mList;
		SparseBooleanArray mSparseBooleanArray;
		View convertView;

		public MultiSelectionAdapter(Context context,ArrayList<HashMap<String, String>> list) {
			// TODO Auto-generated constructor stub
			this.mContext = context;
			mInflater = LayoutInflater.from(mContext);
			mSparseBooleanArray = new SparseBooleanArray();
			mList = new ArrayList<HashMap<String, String>>();
			this.mList = list;
		}

		public ArrayList<Integer> getCheckedItems() {
			ArrayList<Integer> mTempArry = new ArrayList<Integer>();
			for (int i = 0; i < mList.size(); i++) {
				if (mSparseBooleanArray.get(i))
					mTempArry.add(Integer.parseInt(mList.get(i).get("eid")));
			}
			return mTempArry;
		}

		@Override
		public int getCount() {
			return mList.size();
		}

		@Override
		public Object getItem(int position) {
			return mList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		private class ViewHolder {
			TextView item,cost;
		}
		
		@Override
		public View getView(final int position, View v,ViewGroup parent) {
			convertView=v;
			try {
				final ViewHolder holder;
				if (convertView == null) {
					convertView = mInflater.inflate(R.layout.row_textview, null);
					holder = new ViewHolder();

					holder.item = (TextView) convertView.findViewById(R.id.tv_Item);
					holder.cost=(TextView)convertView.findViewById(R.id.tv_cost);
					convertView.setTag(holder);
				} else {
					holder = (ViewHolder) convertView.getTag();
				}
				holder.item.setText(""+mList.get(position).get("Item"));
				holder.cost.setText(""+mList.get(position).get("Cost"));
				Log.e("Cost",""+mList.get(position).get("Cost"));
				
			} catch (Exception e) {
				e.getMessage();
			}
			return convertView;
		}
	}
	
}

