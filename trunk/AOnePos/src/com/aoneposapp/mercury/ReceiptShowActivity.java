package com.aoneposapp.mercury;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aoneposapp.PosMainActivity;
import com.aoneposapp.R;
import com.aoneposapp.ShowMsg;
import com.aoneposapp.starprinter.PrinterFunctions;
import com.aoneposapp.utils.Constants;
import com.aoneposapp.utils.DatabaseForDemo;
import com.aoneposapp.utils.MercuryValues;
import com.aoneposapp.utils.Parameters;
import com.epson.eposprint.Builder;
import com.epson.eposprint.EposException;
import com.epson.eposprint.Print;
import com.mercurypay.ws.sdk.MPSWebRequest;

public class ReceiptShowActivity extends Activity implements OnClickListener {

TextView approve_msg;
WebView receipt_view,receipt_web_holder,receipt_web_merchant;
Button print_holder,print_merchant,print_cancel,void_transaction;
Print printer;
Bitmap map_holder=null;
Bitmap map_merchant=null;
DatabaseForDemo sqlitereceipt;
SQLiteDatabase dbforloginlogoutWritereceipt,dbforloginlogoutReadreceipt;
String msg_intent="";
String merchant_id_intent="";
String ref_no_intent="";
String invoice_no_intent="";
String approval_code_intent="";
String card_no_intent="";
String exp_date_intent="";
String card_intent="";
String tran_id_intent="";
String price_intent="";
String sentamount_intent="", AcqRefData_intent="00", ProcessData_intent="00", enc_block_intent="00",  enc_key_intent="00", str_spin_cardType_intent="";
private static String mWSURL = "https://w1.mercurydev.net/ws/ws.asmx";
MagTekDemo magclassObj;
static String str_CmdStatus;
private static String mPassword ;
String 	mCreditTranCancel="";
String mpsResponse = null;
@Override
protected void onCreate(Bundle savedInstanceState) {
	super.onCreate(savedInstanceState);
	setContentView(R.layout.receipt_mercury);
	sqlitereceipt = new DatabaseForDemo(ReceiptShowActivity.this);
	dbforloginlogoutWritereceipt = sqlitereceipt.getWritableDatabase();
	dbforloginlogoutReadreceipt = sqlitereceipt.getReadableDatabase();
	Parameters.printerContext=ReceiptShowActivity.this;
	approve_msg=(TextView) findViewById(R.id.approved_msg);
	receipt_view=(WebView) findViewById(R.id.receipt_web);
	receipt_web_holder=(WebView) findViewById(R.id.receipt_web_holder);
	receipt_web_merchant=(WebView) findViewById(R.id.receipt_web_merchant);
	print_holder=(Button) findViewById(R.id.print_holder);
	void_transaction=(Button) findViewById(R.id.void_transaction);
	void_transaction.setOnClickListener(this);
	print_holder.setOnClickListener(this);
	print_merchant=(Button) findViewById(R.id.print_merchant);
	print_merchant.setOnClickListener(this);
	print_cancel=(Button) findViewById(R.id.print_cancel);
	print_cancel.setOnClickListener(this);
	headLineMethod("Card Merchant Copy", 100);
	receipt_web_merchant.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/printData/receipt_merchant.html");
	receipt_web_merchant.setDrawingCacheEnabled(true);
	receipt_web_merchant.getDrawingCache();
	
	headLineMethod("Card Holder Copy",1);
	receipt_web_holder.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/printData/receiptFile.html");
	receipt_web_holder.setDrawingCacheEnabled(true);
	receipt_web_holder.getDrawingCache();
	receipt_view.loadUrl("file://"+Environment.getExternalStorageDirectory()+"/printData/receiptFile.html");
	magclassObj=new MagTekDemo();
}

void headLineMethod(String bottom,int diff){
	String query = "select * from "+DatabaseForDemo.MERCHANT_TABLE;
	Cursor cursor = dbforloginlogoutReadreceipt.rawQuery(query, null);
	String name="";
	String address="";
	String address_edit2="";
	String address_phone="";
	
	String zip="";
	if(cursor!=null){
		if(cursor.getCount()>0){
			if(cursor.moveToFirst()){
				do{
					
					if(cursor.isNull(cursor.getColumnIndex(DatabaseForDemo.MERCHANT_NAME))){
					}else{
						name=cursor.getString(cursor.getColumnIndex(DatabaseForDemo.MERCHANT_NAME));
					}
					if(cursor.isNull(cursor.getColumnIndex(DatabaseForDemo.MERCHANT_ADDRESS))){
					}else{
						address=cursor.getString(cursor.getColumnIndex(DatabaseForDemo.MERCHANT_ADDRESS));
					}
					if(cursor.isNull(cursor.getColumnIndex(DatabaseForDemo.MERCHANT_ADDRESS2))){
					}else{
						 address_edit2 = cursor.getString(cursor.getColumnIndex(DatabaseForDemo.MERCHANT_ADDRESS2));
					}
					if(cursor.isNull(cursor.getColumnIndex(DatabaseForDemo.MERCHANT_PHONE))){
					}else{
						 address_phone = cursor.getString(cursor.getColumnIndex(DatabaseForDemo.MERCHANT_PHONE));
					}
					if(cursor.isNull(cursor.getColumnIndex(DatabaseForDemo.MERCHANT_ZIP))){
					}else{
					zip=cursor.getString(cursor.getColumnIndex(DatabaseForDemo.MERCHANT_ZIP));
					}
					
				}while(cursor.moveToNext());
          }
		}
		}
	cursor.close();
	createHtmlView(name, address,address_edit2,address_phone,zip, bottom, diff);
}

void createHtmlView(String name,String address,String address_edit2,String address_phone,String zip,String bottom,int diff){
	Bundle b=getIntent().getExtras();
	 msg_intent=b.getString("str_response","error");
	 merchant_id_intent=b.getString("merchant_id","null");
	 ref_no_intent=b.getString("ref_no","null");
	 invoice_no_intent=b.getString("invoice_no","null");
	 approval_code_intent=b.getString("approval_code","null");
	 card_no_intent=b.getString("card_no","null");
	 exp_date_intent=b.getString("exp_date","null");
	 card_intent=b.getString("card","null");
	 tran_id_intent=b.getString("tran_id","null");
	 price_intent=b.getString("price","null");
	 sentamount_intent=b.getString("sentamount","null");
	 AcqRefData_intent=b.getString("AcqRefData","null");
	 ProcessData_intent=b.getString("ProcessData","null");
	 enc_block_intent=b.getString("enc_block","null");
	 enc_key_intent=b.getString("enc_key","null");
	 str_spin_cardType_intent=b.getString("str_spin_cardType","null");
	approve_msg.setText(msg_intent);
	//+"<tr><th align='center' colspan='2'><span class='tablehead'></span></th></tr>"
	//+"<tr> <th align='center' colspan='2'>No refunds after 30 days</th></tr>"
	String webtext="<html> <head><style type='text/css'>body { background-color:none;font-family : Verdana;color:#000000;font-size:20px;} .tablehead{font-family : Verdana;color:#000000;font-size:32px;} table td{font-family : Verdana;color:#000000;font-size:28px; line-height:36px; padding:0px 4px;}</style></head><body on style='background-color:none;'><center><table id='page' cellspasing='3' width='100%' align='center' border='0'>"
	+"<tr> <th align='center' colspan='2'><span class='tablehead'>"+name+"</span></th></tr>"
	+"<tr><td align='center' colspan='2' style='font-size:22px'>"+address+" </td></tr>"
	+"<tr><td align='center' colspan='2' style='font-size:22px'>"+address_edit2+" </td></tr>"
	+"<tr><td align='center' colspan='2' style='font-size:22px'>"+address_phone+" </td></tr>"
	+"<tr><td align='center' colspan='2' style='font-size:22px'>"+zip+" </td></tr>"
	+"<tr><td align='center' colspan='2' style='font-size:22px'>MerchantID:"+merchant_id_intent+" </td></tr>"
	+"<tr><th align='center' colspan='2'><span class='tablehead'></span></th></tr>"
	+"<tr><th align='center' colspan='2'><span class='tablehead'></span></th></tr>"
	+"<tr><td align='left'>Ref No:</td><td align='right'>"+ref_no_intent+"</td></tr>"
	+"<tr><td align='left'>Invoice Id:</td><td align='right'>"+invoice_no_intent+"</td></tr>"
	+"<tr><td align='left'>Approval Code:</td><td align='right'>"+approval_code_intent+"</td></tr>"
	+"<tr><th align='center' colspan='2'><span class='tablehead'></span></th></tr>"
	+"<tr><th align='center' colspan='2'><span class='tablehead'></span></th></tr>"
	+"<tr><td align='left'>Card No:</td><td align='right'>"+card_no_intent+"</td></tr>"
	+"<tr><td align='left'>Exp Date:</td><td align='right'>"+exp_date_intent+"</td></tr>"
	+"<tr><td align='left'>Card:</td><td align='right'>"+card_intent+"</td></tr>"
	+"<tr><td align='left'>Tran Id:</td><td align='right'>"+tran_id_intent+"</td></tr>"
	+"<tr> <th align='center' colspan='2'><span class='tablehead'>Price: "+price_intent+"</span></th></tr>"
	+"<tr> <th align='center' colspan='2'>Signtature</th></tr>"
	+"<tr><th align='center' colspan='2'><span class='tablehead'>&nbsp;</span></th></tr>"
	+"<tr><th align='center' colspan='2'><span class='tablehead'>&nbsp;</span></th></tr>"
	+"<tr> <th align='center' colspan='2'><span class='tablehead'>"+bottom+"</span></th></tr>"
	+"<tr><th align='center' colspan='2'><span class='tablehead'></span></th></tr>"
	+"<tr><th align='center' colspan='2'><span class='tablehead'></span></th></tr>"
	 +"</table></center></body></html>";
	if(diff>99){
		createHtmlFile_merchant(webtext);
	}else{
		createHtmlFile_holder(webtext);
	}
}

void createHtmlFile_holder(String webtext){
	try {
		File myFile = new File("/sdcard/printData/receiptFile.html");
		myFile.createNewFile();
		FileOutputStream fOut = new FileOutputStream(myFile);
		OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
		myOutWriter.append(""+webtext);
		myOutWriter.close();
		fOut.close();
		System.out.println("html written successfully..");
	} catch (FileNotFoundException e) {
		e.printStackTrace();
		showAlertDialog(ReceiptShowActivity.this, "Print Text", "Getting an Error" ,
				false);
	} catch (IOException e) {
		showAlertDialog(ReceiptShowActivity.this, "Print Text", "Getting an Error" ,
				false);
		e.printStackTrace();
	}
}
void createHtmlFile_merchant(String webtext){
	try {
		File myFile = new File("/sdcard/printData/receipt_merchant.html");
		myFile.createNewFile();
		FileOutputStream fOut = new FileOutputStream(myFile);
		OutputStreamWriter myOutWriter = new OutputStreamWriter(fOut);
		myOutWriter.append(""+webtext);
		myOutWriter.close();
		fOut.close();
		System.out.println("html written successfully..");
	} catch (FileNotFoundException e) {
		e.printStackTrace();
		showAlertDialog(ReceiptShowActivity.this, "Print Text", "Getting an Error" ,
				false);
	} catch (IOException e) {
		showAlertDialog(ReceiptShowActivity.this, "Print Text", "Getting an Error" ,
				false);
		e.printStackTrace();
	}
}
@SuppressWarnings("deprecation")
public void showAlertDialog(Context context, String title, String message,
		Boolean status) {
	AlertDialog alertDialog = new AlertDialog.Builder(context).create();
	alertDialog.setTitle(title);
	alertDialog.setMessage(message);
	alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			dialog.dismiss();
		}
	});
	alertDialog.show();
}
public Drawable getBitmapFromView(View view) {
	try{
    Bitmap returnedBitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(),Bitmap.Config.ARGB_8888);
    Log.e("view "+view.getWidth(), "view "+view.getWidth());
    Canvas canvas = new Canvas(returnedBitmap);
    Drawable bgDrawable =view.getBackground();
    if (bgDrawable!=null) 
        bgDrawable.draw(canvas);
    else 
        canvas.drawColor(Color.WHITE);
    view.draw(canvas);
    Drawable d =new BitmapDrawable(getResources(),returnedBitmap);
    return d;
	}catch(Exception e){
		e.printStackTrace();
		 Drawable d =new BitmapDrawable();
		    return d;
	}
}

public static Bitmap drawableToBitmap(Drawable drawable) {
    if (drawable instanceof BitmapDrawable) {
        return ((BitmapDrawable)drawable).getBitmap();
    }

    int width = drawable.getIntrinsicWidth();
    width = width > 0 ? width : 1;
    int height = drawable.getIntrinsicHeight();
    height = height > 0 ? height : 1;

    Bitmap bitmap = Bitmap.createBitmap(width, height, Config.ARGB_8888);
    Canvas canvas = new Canvas(bitmap); 
    drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
    drawable.draw(canvas);

    return bitmap;
}


@Override
public void onClick(View v) {
	// TODO Auto-generated method stub
	switch (v.getId()) {
	case R.id.print_holder:
		map_holder=drawableToBitmap(getBitmapFromView(receipt_web_holder));
		printText( "printer1",  "", map_holder);
		break;
case R.id.print_merchant:
	map_merchant=drawableToBitmap(getBitmapFromView(receipt_web_merchant));
	printText( "printer1",  "", map_merchant);
		break;
case R.id.print_cancel:
	Intent i=new Intent();
	i.putExtra("price",price_intent);
	i.putExtra("sentamount",sentamount_intent);
	setResult(2, i);
	finish();
		break;
case R.id.void_transaction:
	showVoidPopup("Cancel Transaction","Are you sure that you want to cancel this transaction ? The amount will be reversed to the customer",false);
		break;
		
	}
}

private void submit_cancelTrans() {
	// TODO Auto-generated method stub
	if(Parameters.isNetworkAvailable(ReceiptShowActivity.this)){
		DownloadWebPageTask task = new DownloadWebPageTask();
	    task.execute(str_spin_cardType_intent);
		}else{
			showPopup("Error ","Could not Establish the Connection",false);
		}
}
private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
	
	ProgressDialog mSpinnerProgress;
	
	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		mSpinnerProgress = new ProgressDialog(ReceiptShowActivity.this);
        mSpinnerProgress.setIndeterminate(true);
        mSpinnerProgress.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        mSpinnerProgress.setMessage("Processing...");
        mSpinnerProgress.setCancelable(false);
        mSpinnerProgress.setCanceledOnTouchOutside(false);
        mSpinnerProgress.show();
	}
	
    @Override
    protected String doInBackground(String... urls) {
      String response = "";
		
					try
					{
						final MPSWebRequest mpswr = new MPSWebRequest(mWSURL);
						if(urls[0].equals("CreditTransaction"))	{					
							mpswr.addParameter("tran", 	mCreditTranCancel);//Set WebServices 'tran' parameter to the XML transaction request
							Log.e("mCreditTran1111", mCreditTranCancel);
							mpswr.addParameter("pw", mPassword); 
							Log.e("mPassword", mPassword);//Set merchant's WebServices password
							mpswr.setWebMethodName("CreditTransaction");
						}
							if(urls[0].equals("GiftTransaction")){
								mpswr.addParameter("tran", mCreditTranCancel);
								mpswr.addParameter("pw", mPassword); //Set merchant's WebServices password
								mpswr.setWebMethodName("GiftTransaction");
							}
							mpsResponse = mpswr.sendRequest();
								
						System.out.println("Response :: "+mpsResponse.replace(">\t", ">\n\t"));
						
						response = mpsResponse.replace(">\t", ">\n\t");
						
					}
					catch (Exception e)
					{
						response = "Error";
						//showPopup("Exception error", e.toString());
					}
      return response;
    }

    @Override
    protected void onPostExecute(String result) {
    	Log.v("Log...response12 ",""+result);
    	
    	mSpinnerProgress.cancel();
    	
    	if(result.equals("Error")){
    		showPopup("Error ","Could Not Process the Payment",false);
    	}else{
    		parsexml_credit(result);
    	}
    	
    	
    }
    
  }
String str_Purchase1;
private void parsexml_credit(String gift_trans){
	// TODO Auto-generated method stub
	try{
	String str_response = "TRANSACTION Details";
	
	String xml = gift_trans; // getting XML
    Document doc = magclassObj.getDomElement(xml); // getting DOM element

    NodeList nl = doc.getElementsByTagName("RStream");
    
    
    // looping through all item nodes <item>
    for (int i = 0; i < nl.getLength(); i++) {
        // creating new HashMap
    	        	
        Element e = (Element) nl.item(i);
        String str_TextResponse="";
//        CmdResponse
        NodeList node_CmdResponse = e.getElementsByTagName("CmdResponse");            
        for(int j=0; j<node_CmdResponse.getLength(); j++){

        	Element e1 = (Element)node_CmdResponse.item(j);

        	
        	NodeList node_CmdStatus = e1.getElementsByTagName("CmdStatus");
            Element line = (Element) node_CmdStatus.item(0);
            str_CmdStatus  = magclassObj.getCharacterDataFromElement(line);
            
            
            str_response = str_response+"\n CmdStatus :: "+str_CmdStatus;
                            
            NodeList node_ResponseOrigin = e1.getElementsByTagName("ResponseOrigin");
            Element line_ResponseOrigin = (Element) node_ResponseOrigin.item(0);
            String str_ResponseOrigin  = magclassObj.getCharacterDataFromElement(line_ResponseOrigin);
            
//            System.out.println("str_ResponseOrigin :: "+str_ResponseOrigin);
            
            str_response = str_response+"\n ResponseOrigin :: "+str_ResponseOrigin;
            
        	NodeList node_TextResponse = e1.getElementsByTagName("TextResponse");
            Element line_TextResponse = (Element) node_TextResponse.item(0);
             str_TextResponse  = magclassObj.getCharacterDataFromElement(line_TextResponse);
            
          /*  System.out.println("str_TextResponses :: "+str_TextResponse);
            
            str_response = str_response+"\n TextResponses :: "+str_TextResponse;
            
            NodeList node_UserTraceData = e1.getElementsByTagName("UserTraceData");
            Element line_UserTraceData = (Element) node_UserTraceData.item(0);
            String str_UserTraceData  = magclassObj.getCharacterDataFromElement(line_UserTraceData);
            
            System.out.println("str_UserTraceData :: "+str_UserTraceData);
            
//            str_response = str_response+"\n UserTraceData :: "+str_UserTraceData;
            
            NodeList node_DSIXReturnCode = e1.getElementsByTagName("DSIXReturnCode");
            Element line_DSIXReturnCode= (Element) node_DSIXReturnCode.item(0);
            String str_DSIXReturnCode  = magclassObj.getCharacterDataFromElement(line_DSIXReturnCode);
            
            System.out.println("str_DSIXReturnCode :: "+str_DSIXReturnCode);*/
            
//            str_response = str_response+"\n DSIXReturnCode :: "+str_DSIXReturnCode;
        }
        
      
        if(str_CmdStatus.equals("Approved")){
//        	TranRespose
        /*	NodeList node_TranResponse = e.getElementsByTagName("TranResponse");

        	for(int j=0; j<node_TranResponse.getLength(); j++){
        		
        		Element e1 = (Element)node_TranResponse.item(j);

        	
        		NodeList node_MerchantID = e1.getElementsByTagName("MerchantID");
        		Element line_MerchantID = (Element) node_MerchantID.item(0);
        		String str_MerchantID  = magclassObj.getCharacterDataFromElement(line_MerchantID);
            
//        		System.out.println("str_MerchantID :: "+str_MerchantID);
            
        		 str_response = str_response+"\n MerchantID :: "+str_MerchantID;
        		NodeList node_AcctNo = e1.getElementsByTagName("AcctNo");
        		Element line_AcctNon = (Element) node_AcctNo.item(0);
        		String str_AcctNo  = magclassObj.getCharacterDataFromElement(line_AcctNon);
            
        		System.out.println("str_AcctNo :: "+str_AcctNo);
            
        		 str_response = str_response+"\n AcctNo :: "+str_AcctNo;
        		NodeList node_ExpDate = e1.getElementsByTagName("ExpDate");
        		Element line_ExpDate = (Element) node_ExpDate.item(0);
        		String str_ExpDatee  = magclassObj.getCharacterDataFromElement(line_ExpDate);
        		
        		System.out.println("str_ExpDatee :: "+str_ExpDatee);
        		
        		str_response = str_response+"\n ExpDatee :: "+str_ExpDatee;
        	
        		NodeList node_CardType = e1.getElementsByTagName("CardType");
        		Element line_CardType = (Element) node_CardType.item(0);
        		String str_CardType  = magclassObj.getCharacterDataFromElement(line_CardType);
            
        		System.out.println("str_CardType :: "+str_CardType);
        		
        		str_response = str_response+"\n CardType :: "+str_CardType;
        		NodeList node_TranCode = e1.getElementsByTagName("TranCode");
        		Element line_TranCode= (Element) node_TranCode.item(0);
        		String str_TranCode  = magclassObj.getCharacterDataFromElement(line_TranCode);
            
        		System.out.println("str_TranCode :: "+str_TranCode);
            
        		str_response = str_response+"\n TranCode :: "+str_TranCode;
        		NodeList node_AuthCod = e1.getElementsByTagName("AuthCode");
        		Element line_AuthCod = (Element) node_AuthCod.item(0);
        		String str_AuthCod  = magclassObj.getCharacterDataFromElement(line_AuthCod);
            
        		System.out.println("str_AuthCod :: "+str_AuthCod);
        		
        		str_response = str_response+"\n AuthCod :: "+str_AuthCod;
            
        		NodeList node_CaptureStatus = e1.getElementsByTagName("CaptureStatus");
        		Element line_CaptureStatus = (Element) node_CaptureStatus.item(0);
        		String str_CaptureStatus  = magclassObj.getCharacterDataFromElement(line_CaptureStatus);
            
        		System.out.println("str_CaptureStatus :: "+str_CaptureStatus);
            
        		str_response = str_response+"\n CaptureStatus :: "+str_CaptureStatus;
        		
        		NodeList node_RefNo = e1.getElementsByTagName("RefNo");
        		Element line_RefNoe = (Element) node_RefNo.item(0);
        		String str_RefNo  = magclassObj.getCharacterDataFromElement(line_RefNoe);
            
        		System.out.println("str_RefNo :: "+str_RefNo);
        		str_response = str_response+"\n RefNo :: "+str_RefNo;
            
        		NodeList node_InvoiceNo = e1.getElementsByTagName("InvoiceNo");
        		Element line_InvoiceNo = (Element) node_InvoiceNo.item(0);
        		String str_InvoiceNo  = magclassObj.getCharacterDataFromElement(line_InvoiceNo);
            
        		System.out.println("str_InvoiceNo :: "+str_InvoiceNo);
        		str_response = str_response+"\n InvoiceNo :: "+str_InvoiceNo;
            
        		NodeList node_OperatorID = e1.getElementsByTagName("OperatorID");
        		Element line_OperatorID= (Element) node_OperatorID.item(0);
        		String str_OperatorID  = magclassObj.getCharacterDataFromElement(line_OperatorID);
            
        		System.out.println("str_OperatorID :: "+str_OperatorID);
        		
        		str_response = str_response+"\n OperatorID :: "+str_OperatorID;
            
        		NodeList node_Memo = e1.getElementsByTagName("Memo");
        		Element line_Memo= (Element) node_Memo.item(0);
        		String str_Memo  = magclassObj.getCharacterDataFromElement(line_Memo);
            
        		System.out.println("str_Memo :: "+str_Memo);
            
//        		str_response = str_response+"\n Memo :: "+str_Memo;
            
//            	Amount                
        		NodeList node_Amount = e1.getElementsByTagName("Amount");
            
        			for(int amt=0; amt<node_Amount.getLength(); amt++){
            	
        				Element e_amt = (Element)node_TranResponse.item(j);
            	
        				NodeList node_Purchase = e_amt.getElementsByTagName("Purchase");
        				Element line_Purchase= (Element) node_Purchase.item(0);
        				str_Purchase1  = magclassObj.getCharacterDataFromElement(line_Purchase);
                 
                 
        				str_response = str_response+"\n Purchase :: "+str_Purchase1;
        				try{
        				Parameters.sentAmount=str_Purchase1;
        				}catch(NumberFormatException e21){
        					Log.v("sentamount", ""+e21.printStackTrace());
        				}
        				NodeList node_Authorize = e_amt.getElementsByTagName("Authorize");
        				Element line_Authorize= (Element) node_Authorize.item(0);
        				String str_Authorize  = magclassObj.getCharacterDataFromElement(line_Authorize);
                 
        				System.out.println("str_Authorize :: "+str_Authorize);
            				Parameters.sentAmount=""+str_Authorize;
        				str_response = str_response+"\n Authorize :: "+str_Authorize;
        			}
            
            
        		NodeList node_AcqRefData = e1.getElementsByTagName("AcqRefData");
        		Element line_AcqRefData = (Element) node_AcqRefData.item(0);
        		String str_AcqRefData  = magclassObj.getCharacterDataFromElement(line_AcqRefData);
        		System.out.println("str_AcqRefData :: "+str_AcqRefData);
            
//        		str_response = str_response+"\n AcqRefData :: "+str_AcqRefData;
            
        		NodeList node_RecordNo = e1.getElementsByTagName("RecordNo");
        		Element line_RecordNo = (Element) node_RecordNo.item(0);
        		String str_RecordNo  = magclassObj.getCharacterDataFromElement(line_RecordNo);
            
        		System.out.println("str_RecordNo :: "+str_RecordNo);
        		
//        		str_response = str_response+"\n RecordNo :: "+str_RecordNo;
            
        		NodeList node_ProcessData = e1.getElementsByTagName("ProcessData");
            	Element line_ProcessData= (Element) node_ProcessData.item(0);
            	String str_ProcessData  = magclassObj.getCharacterDataFromElement(line_ProcessData);
            	System.out.println("str_ProcessData :: "+str_ProcessData);
            	
//            	str_response = str_response+"\n ProcessData :: "+str_ProcessData;
                            	
        	}
    //    	updateinvoicetable(Parameters.generateRandomNumber(), "complete", "", "Credit/Debit", "");
	//		updateTheInvoice(Parameters.generateRandomNumber(), str_Purchase, "", "", "Credit/Debit");
        	
        
          //  showPopupApproved("Payment Successfull", str_response);
*/        // approve_msg.setText("Void Successfull \n"+str_response);
        	 Parameters.mercury_result="VoidApproved";
         showPopupApproved("Success ","Transaction has been Successfully Cancelled",false);
        }else{
        	Parameters.mercury_result="Error";
        	showPopup("Payment Error", str_TextResponse,false);
        }
      
    }
    
    }catch(NullPointerException n){
			showPopup("Error ","Could Not Process the Payment",false);
		}catch (Exception e) {
			// TODO: handle exception
			showPopup("Error ","Could Not Process the Payment", false);
		}
	
}
@SuppressWarnings("deprecation")
protected void showPopup(final String title, String string2,final boolean value) {
	// TODO Auto-generated method stub
	
	final AlertDialog alertbox = new AlertDialog.Builder(this).create();
	alertbox.setTitle(title);
	alertbox.setMessage(string2);
	alertbox.setButton("OK", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) { 
            // continue with delete
        	alertbox.dismiss();
        }
     });
   
    alertbox.setIcon(R.drawable.aone_logo_action);
     alertbox.show();
	
}
protected void showPopupApproved(final String title, String string2, final boolean value) {
	// TODO Auto-generated method stub
	
	AlertDialog 	pop=new AlertDialog.Builder(this)
    .setTitle(title)
    .setMessage(string2)
    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) { 
        		finish();
        }
     })
    .setIcon(R.drawable.aone_logo_action)
     .show();
	pop.setCanceledOnTouchOutside(false);
	pop.setCancelable(false);
}
 AlertDialog aaaa;
protected void showVoidPopup(final String title, String string2,final boolean value) {
	// TODO Auto-generated method stub
	
	 aaaa=new AlertDialog.Builder(this)
    .setTitle(title)
    .setMessage(string2)
    .setPositiveButton("YES", new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) { 
            // continue with delete
        	DatabaseForDemo sqlitePos=new DatabaseForDemo(ReceiptShowActivity.this);
        	MercuryValues mercury_values =sqlitePos.mercuryDetails();
        	mWSURL = mercury_values.getPrimaryURL();				
        	String merchant_id = mercury_values.getMerchantID();
        	mPassword = mercury_values.getPassword();
        	
         	mCreditTranCancel="<TStream>\n\t<Transaction>\n\t\t<MerchantID>"+merchant_id_intent+"</MerchantID>\n\t\t<TranType>Credit" +
        			"</TranType>\n\t\t<TranCode>VoidSale</TranCode>\n\t\t<InvoiceNo>"+Parameters.invoiceid_mercury+"</InvoiceNo>\n\t\t<RefNo>"+ref_no_intent+"</RefNo>\n\t\t" +
        			"<Memo>A One POS Version 3.0</Memo>\n\t\t<PartialAuth>Allow</PartialAuth>\n\t\t<Frequency>OneTime" +
        			"</Frequency>\n\t\t<RecordNo>RecordNumberRequested</RecordNo>\n\t\t<Account>\n\t\t\t<EncryptedFormat>MagneSafe" +
        			"</EncryptedFormat>\n\t\t\t<AccountSource>Swiped</AccountSource>\n\t\t\t<EncryptedBlock>"
        			+enc_block_intent+"</EncryptedBlock>\n\t\t\t<EncryptedKey>"+enc_key_intent+"</EncryptedKey>\n\t\t\t<Name>MPS TEST</Name>" +
        					"\n\t\t</Account>\n\t\t<Amount>\n\t\t\t<Purchase>"+price_intent+"</Purchase>\n\t\t</Amount>\n\t\t" +
        							"<TerminalName>MPS Java SDK</TerminalName>\n\t\t<ShiftID>MPS Shift</ShiftID>\n\t\t" +
        							"<TranInfo><AcqRefData>"+AcqRefData_intent+"</AcqRefData>\n\t\t<ProcessData>"+ProcessData_intent+"</ProcessData>\n\t\t</TranInfo>"+
        							"<OperatorID>test</OperatorID>\n\t\t<AuthCode>"+approval_code_intent+"</AuthCode>\n\t</Transaction>\n</TStream>";
        	System.out.println("CancelTransaction :: "+mCreditTranCancel);
        	
        	submit_cancelTrans();
        	aaaa.dismiss();
        }
     })
    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
        public void onClick(DialogInterface dialog, int which) { 
        	aaaa.dismiss();
        }
     })
    .setIcon(R.drawable.aone_logo_action)
     .show();
	
}
private void printText(String printerid, String print_text,Bitmap map) {
	try{
	boolean forprintid = false;
	ArrayList<String> printVal = new ArrayList<String>();
		String selectQuery = "SELECT  * FROM "
				+ DatabaseForDemo.PRINTER_TABLE; // + " where "+
													// DatabaseForDemo.PRINTER_ID
													// + "='" + printerid +
													// "'";
		Cursor mCursor1 = dbforloginlogoutReadreceipt.rawQuery(selectQuery, null);
		int count = mCursor1.getColumnCount();
		System.out.println("hari  " + mCursor1);
		System.out.println("hari  " + count);
		if (mCursor1 != null) {
			Log.e("getCount", "parddhu1     v:" + mCursor1.getCount());
			if (mCursor1.getCount() > 0) {
				Log.e("Rama", "parddhu2 " + mCursor1.getCount());
				if (mCursor1.moveToFirst()) {
					Log.e("Rama", "parddhu3");
					do {
						if (printerid
								.equals(mCursor1.getString(mCursor1
										.getColumnIndex(DatabaseForDemo.PRINTER_ID)))) {
							for (int i = 0; i < count; i++) {
								String value = mCursor1.getString(i);
								printVal.add(value);
							}
							forprintid = true;
						}

					} while (mCursor1.moveToNext());
				}
				if (forprintid) {

				} else {
					if (mCursor1.moveToFirst()) {
						for (int i = 0; i < count; i++) {
							String value = mCursor1.getString(i);
							printVal.add(value);
						}
					}
				}
			} else {
				Toast.makeText(getApplicationContext(),
						"Set The Printer Settings", 1000).show();
			}
		}
		mCursor1.close();
	//}
	Log.e("Rama", "parddhu4");
	if (printVal.size() > 14) {
		PrintingNow(printVal, map);
	}
	} catch (NumberFormatException e) {
		  e.printStackTrace();
		} catch (SQLException e12) {
			  e12.printStackTrace();
			} catch (Exception e1) {
			  e1.printStackTrace();
			}
}

void PrintingNow(ArrayList<String> printVal, Bitmap printingImage) {
	try{
	if (printVal.get(15).equals("EPSON")) {
		Builder builder = null;
		String method = "";
		Log.e("Rama", "parddhu5");
		try {
			// create builder
			method = "Builder";
			try {
				builder = new Builder("" + printVal.get(12), 0,
						getApplicationContext());
			} catch (Exception e) {

			}
			 if(printingImage == null){
				 Log.e("Rama", "parddhu4336");
		            ShowMsg.showError(R.string.errmsg_noimage, ReceiptShowActivity.this);
		            return ;
		        }
			 Log.v(Math.min(542, printingImage.getWidth())+"", printingImage.getHeight()+"H  W"+ printingImage.getWidth());
			  builder.addImage(printingImage, 0, 0, Math.min(542, printingImage.getWidth()), printingImage.getHeight(), Builder.COLOR_1, 
					  Builder.MODE_MONO, Builder.HALFTONE_THRESHOLD, 1.0);
			  builder.addCut(Builder.CUT_FEED);
			Log.e("Rama", "parddhu6");
			/*harinath     
		    method = "addTextFont";
			builder.addTextFont(Integer.parseInt(printVal.get(2)));

			method = "addTextAlign";
			builder.addTextAlign(Integer.parseInt(printVal.get(3)));

			method = "addTextLineSpace";
			builder.addTextLineSpace(Integer.parseInt(printVal.get(4)));
			Log.e("Rama", "parddhu7");
			method = "addTextLang";
			builder.addTextLang(Integer.parseInt(printVal.get(5)));

			method = "addTextSize";
			builder.addTextSize(Integer.parseInt(printVal.get(6)) + 1,
					Integer.parseInt(printVal.get(7)) + 1);

			method = "addTextStyle";
			builder.addTextStyle(Builder.FALSE,
					Integer.parseInt(printVal.get(9)),
					Integer.parseInt(printVal.get(8)), Builder.COLOR_1);
			Log.e("Rama", "parddhu8");
			method = "addTextPosition";
			builder.addTextPosition(Integer.parseInt(printVal.get(10)));

			method = "addText";
			builder.addText("" + printVal.get(1) + " \n " + printingText);

			method = "addFeedUnit";
			builder.addFeedUnit(Integer.parseInt(printVal.get(11)));
			Log.e("Rama", "parddhu9");  harinath*/
			// send builder data
			int[] status = new int[1];
			int[] battery = new int[1];
			Log.e("Rama", "parddhu10");
			try {

				int deviceType = Print.DEVTYPE_TCP;
				try {
					Log.e("Rama", "parddhu11");
					printer = new Print(ReceiptShowActivity.this);
					printer.openPrinter(deviceType, "" + printVal.get(13),
							Print.TRUE, 1000);

				} catch (Exception e) {
					Log.e("Rama", "parddhu12");
					ShowMsg.showException(e, "openPrinter",
							ReceiptShowActivity.this);
					return;
				}
				printer.sendData(builder, Constants.SEND_TIMEOUT, status,
						battery);
				printer.closePrinter();
				Log.e("Rama", "parddhu13");
			} catch (EposException e) {
				ShowMsg.showStatus(e.getErrorStatus(),
						e.getPrinterStatus(), e.getBatteryStatus(),
						ReceiptShowActivity.this);
				printer.closePrinter();
			}
		} catch (Exception e) {
			ShowMsg.showException(e, method, ReceiptShowActivity.this);
			try {
				Log.e("Rama", "parddhu14");
				printer.closePrinter();
			} catch (EposException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			printer = null;
		}
		Log.e("Rama", "parddhu15");
		// remove builder
		if (builder != null) {
			try {
				builder.clearCommandBuffer();
				builder = null;
			} catch (Exception e) {
				builder = null;
			}
		}
		Log.e("Rama", "parddhu16");
		/*
		 * try { Log.e("Rama", "parddhu17"); //printer.closePrinter(); }
		 * catch (EposException e) { // TODO Auto-generated catch block
		 * e.printStackTrace(); }
		 */
		printer = null;
	} else {
		try {
			boolean compressionEnable = false;
			String commandType  = "Raster";
			String typp="TCP:";
			Log.e("Rama", "qqqqqqq");
			if(printVal.get(2).equals("BT:")){
				typp="BT:";
			}
			 if(printingImage == null){
				 Log.e("Rama", "parddhu4336");
		            ShowMsg.showError(R.string.errmsg_noimage, ReceiptShowActivity.this);
		            return ;
		        }
			PrinterFunctions.PrintBitmapImage(ReceiptShowActivity.this, typp+""+printVal.get(13), "", getResources(), printingImage, 546, compressionEnable);
        //	PrinterFunctions.PrintSampleReceipt(ReportsActivity.this,  "TCP:"+printVal.get(13),   "",  commandType, getResources(), "3inch (78mm)", printingText);
		} catch (Exception e) {
			System.out.println("error "+e);
		}
	}
	Log.e("Rama", "parddhu20");
	} catch (NumberFormatException e) {
		  e.printStackTrace();
		} catch (SQLException e12) {
			  e12.printStackTrace();
			} catch (Exception e1) {
			  e1.printStackTrace();
			}
}
@Override
public void onBackPressed() {
}
}
