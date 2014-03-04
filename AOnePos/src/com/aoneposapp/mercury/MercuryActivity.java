package com.aoneposapp.mercury;

import com.aoneposapp.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

public class MercuryActivity extends Activity{
	
	EditText edit_primaryurl,edit_secondayurl,edit_merchantID,edit_password;
	Button btn_login;
	
	DataBase_Transaction db_transaction;
	SharedPreferences prefs;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.urls_list);
		
		edit_primaryurl = (EditText)findViewById(R.id.et_primaryurl);
		edit_secondayurl = (EditText)findViewById(R.id.et_secondayurl);
		edit_merchantID = (EditText)findViewById(R.id.et_merchantID);
		edit_password = (EditText)findViewById(R.id.et_password);
		btn_login = (Button)findViewById(R.id.btnLogin);
		
		
		prefs = this.getSharedPreferences("pos", Context.MODE_PRIVATE);
		
//		db_transaction = new DataBase_Transaction(getApplicationContext());
//		
//		ContentValues values = new ContentValues();
//		values.put(DataBase_Transaction.KEY_PRIMARY, edit_primaryurl.getText().toString().trim()); 
//		values.put(DataBase_Transaction.KEY_SECONDARY, edit_secondayurl.getText().toString().trim()); 
//		values.put(DataBase_Transaction.KEY_MERCHANT_ID, edit_merchantID.getText().toString().trim()); 
//		values.put(DataBase_Transaction.KEY_MERCHANT_ID,edit_timeout.getText().toString().trim());
//		
//		db_transaction.Add_Contact(values);
		
		btn_login.setOnClickListener(new  OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
//				System.out.println("values :: "+db_transaction.Get_Contact(0));	
				Editor edi = prefs.edit();
				edi.putString("primaryURL", edit_primaryurl.getText().toString().trim());
				edi.putString("secondaryURL", edit_secondayurl.getText().toString().trim());
				edi.putString("merchantID", edit_merchantID.getText().toString().trim());
				edi.putString("password", edit_password.getText().toString().trim());
				edi.commit();
				
				
				Intent i = new Intent(MercuryActivity.this,MagTekDemo.class);
				startActivity(i);
				finish();

			}//Aone#2013
			
		});
		
		
		SharedPreferences esprefs = getApplicationContext().getSharedPreferences("pos", Context.MODE_PRIVATE);
		System.out.println("values :: "+ esprefs.getString("primaryURL", "defValue"));
		
		edit_primaryurl.setText(esprefs.getString("primaryURL", "https://w1.mercurydev.net/ws/ws.asmx"));
		edit_secondayurl.setText(esprefs.getString("secondaryURL", "https://w1.mercurydev.net/ws/ws.asmx"));
		edit_merchantID.setText(esprefs.getString("merchantID", ""));
		edit_password.setText(esprefs.getString("password", ""));
		
		
	} 

}
