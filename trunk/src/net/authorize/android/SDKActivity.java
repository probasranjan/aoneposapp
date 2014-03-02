package net.authorize.android;

import java.util.ArrayList;
import java.util.HashMap;

import net.authorize.Environment;
import net.authorize.android.dialog.Dialog_ShoppingDetails;
import net.authorize.util.HttpClient;
import net.authorize.util.StringUtils;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.aoneposapp.R;

public class SDKActivity extends Activity {
	private EditText editText_ItemName, editText_Cost;
	private Button button_Cart, button_Transaction;
	private ListView listView;
	private ArrayList<HashMap<String, String>> arrayList_Details;
	private MultiSelectionAdapter<ArrayList<HashMap<String, String>>> adapter;
	public static AuthNet authNet;
	static {
		HttpClient.HTTP_CONNECTION_TIMEOUT = 0;
		HttpClient.HTTP_SOCKET_CONNECTION_TIMEOUT = 0;
		Environment env = Environment.SANDBOX;

		authNet = AuthNet
				.getInstance(env, R.layout.authnet_mobile_merchant_auth_dialog,
						R.id.authnet_loginid_edit, R.id.authnet_password_edit,
						R.id.authnet_auth_cancel_button,
						R.id.authnet_auth_login_button);
	}

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		loadMainPage();
	}

	private void loadMainPage() {
		setContentView(R.layout.main);
		editText_ItemName=(EditText)findViewById(R.id.editText_ItemName);
		editText_Cost=(EditText)findViewById(R.id.editText_Cost);
		button_Cart=(Button)findViewById(R.id.button_Cart);
		button_Transaction=(Button)findViewById(R.id.button_Transaction);
		arrayList_Details=new ArrayList<HashMap<String,String>>();
		listView = (ListView) findViewById(R.id.lv_list);
		
		// AIM
		View aimButton = findViewById(R.id.button_Transaction);
		aimButton.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				Log.e("arrayList_Details",""+arrayList_Details);
				new Dialog_ShoppingDetails(SDKActivity.this,SDKActivity.this,arrayList_Details);
//				Intent intent = new Intent(v.getContext(), AIMActivity.class);
//				startActivity(intent);
			}
		});
		button_Cart.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				HashMap<String, String> map=new HashMap<String, String>();
				map.put("Item",""+editText_ItemName.getText().toString().trim());
				map.put("Cost",""+editText_Cost.getText().toString().trim());
				arrayList_Details.add(map);
				adapter = new MultiSelectionAdapter<ArrayList<HashMap<String, String>>>(SDKActivity.this, arrayList_Details);
				listView.setAdapter(adapter);
				editText_ItemName.setText("");
				editText_Cost.setText("");
			}
		});

		// footer
		addFooter(this, (LinearLayout) findViewById(R.id.MainFooterLayout), -1);
	}

	/**
	 * Add a footer to the context with the footerLayout.
	 * 
	 * @param context
	 * @param footerLayout
	 * @param subFooterStringId
	 *            = will look for the
	 */
	public static void addFooter(Context context, LinearLayout footerLayout,
			int subFooterStringId) {
		// footer
		LinearLayout.LayoutParams footerParams = new LinearLayout.LayoutParams(
				android.widget.LinearLayout.LayoutParams.FILL_PARENT,
				android.widget.LinearLayout.LayoutParams.MATCH_PARENT);
		footerParams.setMargins(0, 10, 0, 20);

		Resources resources = context.getResources();
		StringBuilder sb = new StringBuilder(
				resources.getString(R.string.footer_description));
		try {
			String subFooter = resources.getString(subFooterStringId);
			if (StringUtils.isNotEmpty(subFooter)) {
				sb.append("\n").append(subFooter);
			}
		} catch (Resources.NotFoundException nfe) {
			;
		}

		TextView authNetFooter = new TextView(context);
		authNetFooter.setGravity(Gravity.CENTER | Gravity.BOTTOM);
		authNetFooter.setText(sb.toString());
		authNetFooter.setLayoutParams(footerParams);
		footerLayout.addView(authNetFooter);
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
