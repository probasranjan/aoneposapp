package com.aoneposapp.mercury;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.widget.TextView;

import com.aoneposapp.R;
import com.mercurypay.ws.sdk.MPSWebRequest;

public class MercuryWSExample extends Activity{

	private final String mCreditTran = "<TStream>\n\t<Transaction>\n\t\t<MerchantID>118725340908147</MerchantID>\n\t\t<TranType>Credit</TranType>\n\t\t<TranCode>Sale</TranCode>\n\t\t<InvoiceNo>1</InvoiceNo>\n\t\t<RefNo>1</RefNo>\n\t\t<Memo>MPS Example XML v1.0 - Java SDK</Memo>\n\t\t<PartialAuth>Allow</PartialAuth>\n\t\t<Frequency>OneTime</Frequency>\n\t\t<RecordNo>RecordNumberRequested</RecordNo>\n\t\t<Account>\n\t\t\t<EncryptedFormat>MagneSafe</EncryptedFormat>\n\t\t\t<AccountSource>Swiped</AccountSource>\n\t\t\t<EncryptedBlock>F40DDBA1F645CC8DB85A6459D45AFF8002C244A0F74402B479ABC9915EC9567C81BE99CE4483AF3D</EncryptedBlock>\n\t\t\t<EncryptedKey>9012090B01C4F200002B</EncryptedKey>\n\t\t\t<Name>MPS TEST</Name>\n\t\t</Account>\n\t\t<Amount>\n\t\t\t<Purchase>1.00</Purchase>\n\t\t</Amount>\n\t\t<TerminalName>MPS Java SDK</TerminalName>\n\t\t<ShiftID>MPS Shift</ShiftID>\n\t\t<OperatorID>MPS Operator</OperatorID>\n\t</Transaction>\n</TStream>",
			mGiftTran = "<TStream>\n\t<Transaction>\n\t\t<IpPort>9100</IpPort>\n\t\t<MerchantID>118725340908147</MerchantID>\n\t\t<TranType>PrePaid</TranType>\n\t\t<TranCode>Sale</TranCode>\n\t\t<InvoiceNo>4</InvoiceNo>\n\t\t<RefNo>0001</RefNo>\n\t\t<Memo>MPS Example XML v1.0 - Java SDK</Memo>\n\t\t<Account>\n\t\t\t<EncryptedFormat>MagneSafe</EncryptedFormat>\n\t\t\t<AccountSource>Swiped</AccountSource>\n\t\t\t<EncryptedBlock>CF7F1CA56296E8E2083047007D85C388C9DA9A21936912995524CD4EE50E4C77</EncryptedBlock>\n\t\t\t<EncryptedKey>9500030000040C20001F</EncryptedKey>\n\t\t</Account>\n\t\t<Amount>\n\t\t\t<Purchase>1.00</Purchase>\n\t\t</Amount>\n\t\t<TerminalName>MPS Java SDK</TerminalName>\n\t\t<ShiftID>MPS Shift</ShiftID>\n\t\t<OperatorID>MPS Operator</OperatorID>\n\t</Transaction>\n</TStream>";
	private String mWSURL = "https://w1.mercurydev.net/ws/ws.asmx";
	TextView tv_text;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.web_service);
		
		tv_text = (TextView)findViewById(R.id.txt_header);
		
		tv_text.setText(""+mCreditTran);
		
		submitRequest();
			
	}
	
	
	/**
	 * Submit XML request defined in taDSIXML using com.mercurypay.sdk.MPSWebRequest
	 */
	private void submitRequest()
	{
//		try
//		{
//			changeControlState(false);
			tv_text.setText("Sending Request...");
//			new Thread(new Runnable()
//			{
//				@Override
//				public void run()
//				{
					try
					{
						System.out.println("gygy :: "+mWSURL);
						System.out.println("gygy 11 :: "+mCreditTran);
						MPSWebRequest mpswr = new MPSWebRequest(mWSURL);
						mpswr.addParameter("tran",tv_text.getText().toString().trim()); //Set WebServices 'tran' parameter to the XML transaction request
						mpswr.addParameter("pw", "XYZ"); //Set merchant's WebServices password
						mpswr.setWebMethodName("CreditTransaction");//(String)cmbWebMethod.getSelectedItem()); //Set WebServices webmethod to selected type
//						mpswr.setTimeout(20000); //Set request timeout to 10 seconds
						
						String mpsResponse = mpswr.sendRequest();
						tv_text.setText("");
						showPopup("Response XML String",mpsResponse.replace(">\t", ">\n\t"));
					}
					catch (Exception e)
					{
						showPopup("Exception error", e.toString());
					}
					finally
					{

						tv_text.setText("finally");
//						changeControlState(true);
						@SuppressWarnings("unused")  // Properly disposing of current thread
						Thread curthread = Thread.currentThread();
						curthread = null;
					}
//				}
//			}).start();
//		}
//		catch (Exception e)
//		{
//			showPopup("Exception error22", e.toString());
//		}
	}


	protected void showPopup(String title, String string2) {
		// TODO Auto-generated method stub
		
		new AlertDialog.Builder(this)
	    .setTitle(title)
	    .setMessage(string2)
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // continue with delete
	        }
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // do nothing
	        }
	     })
	    .setIcon(R.drawable.app_icon)
	     .show();
		
	}
}
