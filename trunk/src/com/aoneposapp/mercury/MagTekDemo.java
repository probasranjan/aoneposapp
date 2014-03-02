
package com.aoneposapp.mercury;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.CharacterData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.media.AudioManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aoneposapp.PosMainActivity;
import com.aoneposapp.R;
import com.aoneposapp.utils.DatabaseForDemo;
import com.aoneposapp.utils.Inventory;
import com.aoneposapp.utils.JsonPostMethod;
import com.aoneposapp.utils.MercuryValues;
import com.aoneposapp.utils.Parameters;
import com.magtek.mobile.android.scra.ConfigParam;
import com.magtek.mobile.android.scra.MTSCRAException;
import com.magtek.mobile.android.scra.MagTekSCRA;
import com.magtek.mobile.android.scra.ProcessMessageResponse;
import com.magtek.mobile.android.scra.SCRAConfiguration;
import com.magtek.mobile.android.scra.SCRAConfigurationDeviceInfo;
import com.magtek.mobile.android.scra.SCRAConfigurationReaderType;
import com.magtek.mobile.android.scra.StatusCode;
import com.mercurypay.ws.sdk.MPSWebRequest;

public class MagTekDemo extends Activity {
	 String mpsResponse = null;
	// Message types sent from the BluetoothChatService Handler
	public static final int MESSAGE_STATE_CHANGE = 1;
	public static final int MESSAGE_READ = 2;
	public static final int MESSAGE_WRITE = 3;
	public static final int MESSAGE_DEVICE_NAME = 4;
	public static final int MESSAGE_TOAST = 5;
	public static final int STATUS_IDLE = 1;
	public static final int STATUS_PROCESSCARD = 2;
//	private static final int MESSAGE_UPDATE_GUI = 6;
	public static final String CONFIGWS_URL = "https://deviceconfig.magensa.net/service.asmx";//Production URL

	private static final int CONFIGWS_READERTYPE = 0;
	private static final String CONFIGWS_USERNAME = "magtek";
	private static final String CONFIGWS_PASSWORD = "p@ssword";
String merchant_id1,ref_no,invoice_no,approval_code,card_no,exp_date,card,tran_id,price , sentamount;
String AuthCode = "000";
String AcqRefData = "000";
String ProcessData = "000";
String enc_block ="";
String enc_key="";
	private AudioManager mAudioMgr;	
	

	public static final String DEVICE_NAME = "device_name";
	public static final String CONFIG_FILE = "MTSCRADevConfig.cfg";
//	public static final String TOAST = "toast";
	public static final String PARTIAL_AUTH_INDICATOR = "1";
	private static final boolean mShowTitle = false;
	// Intent request codes
	private static final int REQUEST_CONNECT_DEVICE = 1;
	
	private MagTekSCRA mMTSCRA;
	//private int miDeviceType=MagTekSCRA.DEVICE_TYPE_NONE;
	private Handler mSCRADataHandler = new Handler(new SCRAHandlerCallback());
	final headSetBroadCastReceiver mHeadsetReceiver = new headSetBroadCastReceiver();
	final NoisyAudioStreamReceiver mNoisyAudioStreamReceiver = new NoisyAudioStreamReceiver();

	// Layout Views
	private TextView mTitleLeftTextView;
	private TextView mAppStatusTextView;
	private EditText mCardDataEditText;
	private TextView mInfoTextView;
//	private int miReadCount=0;
	private String mStringDebugData;
	private CheckBox mGetConfigFromWeb;
	
	private ImageButton mClearImageButton;
	private ImageButton mSubmitImageButton;
	String mStringLocalConfig;
	
	private int mIntCurrentDeviceStatus;


	private RelativeLayout mTitleLayout;

	// =============================================================================================================
    //private Boolean mBooleanBTConnect;

	private boolean mbAudioConnected;

	private long mLongTimerInterval;

	private int mIntCurrentStatus;

	private int mIntCurrentVolume;
	
	private String mStringAudioConfigResult;
	
	
	// private String mRegisterScorePCodeResponse;
	// =============================================================================================================
	// private SensorManager mSensorMgr;
	// =============================================================================================================
	Handler GUIUpdateTimerHandler;

	final Handler mUIProcessCardHandler = new Handler();

	Button im_webservice, back_button;
	TextView read2swipe;
	ImageView swipe_img;
	private static String mCreditTran = "<TStream>\n\t<Transaction>\n\t\t<MerchantID>118725340908147</MerchantID>\n\t\t<TranType>Credit</TranType>\n\t\t<TranCode>Sale</TranCode>\n\t\t<InvoiceNo>1</InvoiceNo>\n\t\t<RefNo>1</RefNo>\n\t\t<Memo>A One POS Version 3.0</Memo>\n\t\t<PartialAuth>Allow</PartialAuth>\n\t\t<Frequency>OneTime</Frequency>\n\t\t<RecordNo>RecordNumberRequested</RecordNo>\n\t\t<Account>\n\t\t\t<EncryptedFormat>MagneSafe</EncryptedFormat>\n\t\t\t<AccountSource>Swiped</AccountSource>\n\t\t\t<EncryptedBlock>F40DDBA1F645CC8DB85A6459D45AFF8002C244A0F74402B479ABC9915EC9567C81BE99CE4483AF3D</EncryptedBlock>\n\t\t\t<EncryptedKey>9012090B01C4F200002B</EncryptedKey>\n\t\t\t<Name>MPS TEST</Name>\n\t\t</Account>\n\t\t<Amount>\n\t\t\t<Purchase>1.00</Purchase>\n\t\t</Amount>\n\t\t<TerminalName>MPS Java SDK</TerminalName>\n\t\t<ShiftID>MPS Shift</ShiftID>\n\t\t<OperatorID>MPS Operator</OperatorID>\n\t</Transaction>\n</TStream>";
	private static String mGiftTran = "<TStream>\n\t<Transaction>\n\t\t<IpPort>9100</IpPort>\n\t\t<MesrchantID>118725340908147</MerchantID>\n\t\t<TranType>PrePaid</TranType>\n\t\t<TranCode>Sale</TranCode>\n\t\t<InvoiceNo>4</InvoiceNo>\n\t\t<RefNo>0001</RefNo>\n\t\t<Memo>A One POS Version 3.0</Memo>\n\t\t<Account>\n\t\t\t<EncryptedFormat>MagneSafe</EncryptedFormat>\n\t\t\t<AccountSource>Swiped</AccountSource>\n\t\t\t<EncryptedBlock>CF7F1CA56296E8E2083047007D85C388C9DA9A21936912995524CD4EE50E4C77</EncryptedBlock>\n\t\t\t<EncryptedKey>9500030000040C20001F</EncryptedKey>\n\t\t</Account>\n\t\t<Amount>\n\t\t\t<Purchase>1.00</Purchase>\n\t\t</Amount>\n\t\t<TerminalName>MPS Java SDK</TerminalName>\n\t\t<ShiftID>MPS Shift</ShiftID>\n\t\t<OperatorID>MPS Operator</OperatorID>\n\t</Transaction>\n</TStream>";
	private static String mWSURL = "https://w1.mercurydev.net/ws/ws.asmx";
	
	private static String mPassword ;
	
	EditText et_total_amount, operator_idedit;
	Spinner spin_cardType;
	String str_spin_cardType="";
	Button btn_connect_device;
	Button button_step1;
	DatabaseForDemo sqlitePos;
	SQLiteDatabase dbforloginlogoutWritePos,dbforloginlogoutReadPos;
	String dataval = "";
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Parameters.mercury_result="dffg";
		sqlitePos = new DatabaseForDemo(MagTekDemo.this);
		dbforloginlogoutWritePos = sqlitePos.getWritableDatabase();
		dbforloginlogoutReadPos = sqlitePos.getReadableDatabase();
		Parameters.printerContext=MagTekDemo.this;
		
		
		
		// Set up the window layout
		if (mShowTitle) {
			requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
		}// if(mShowTitle)
		else {
			getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		}
		setContentView(R.layout.main_mercury);

		if (mShowTitle)
		{
			getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE,
					R.layout.custom_title);
		}// if(mShowTitle)

		// Set up the custom title
		if (mShowTitle) 
		{
			mTitleLeftTextView = (TextView) findViewById(R.id.title_left_text);
			mTitleLeftTextView.setText(R.string.app_title);
			mTitleLayout = (RelativeLayout) findViewById(R.id.relative_layout_title);
			mTitleLayout.setVisibility(View.INVISIBLE);
		}// if(mShowTitle)

		mAppStatusTextView = (TextView) findViewById(R.id.textview_app_status);
		mInfoTextView = (TextView) findViewById(R.id.textview_info);
		mCardDataEditText= (EditText) findViewById(R.id.edittext_carddata);
		mGetConfigFromWeb=(CheckBox) findViewById(R.id.checkbox_getconfig);
		mClearImageButton = (ImageButton) findViewById(R.id.imagebutton_clear);
		mSubmitImageButton= (ImageButton) findViewById(R.id.imagebutton_submit);
		
		spin_cardType = (Spinner)findViewById(R.id.spin_cardtype);
		et_total_amount = (EditText)findViewById(R.id.et_amount);
		operator_idedit = (EditText)findViewById(R.id.operator_id);
		operator_idedit.setText("MPS Operator");
		if(Parameters.usertype.equals("admin")){
			operator_idedit.setEnabled(true);
		}else{
			operator_idedit.setEnabled(false);
		}
		et_total_amount.setText(String.format("%.2f",  Parameters.Cridetamount));
		btn_connect_device = (Button)findViewById(R.id.btn_connect_device);
		button_step1 = (Button)findViewById(R.id.button1_step1);
		List<String> list = new ArrayList<String>();
	//	list.add("--Select--");
		list.add("CreditTransaction");
		list.add("GiftTransaction");
		
		ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>
        (this, android.R.layout.simple_spinner_item,list);
		
		spin_cardType.setAdapter(dataAdapter);
		
			btn_connect_device.setVisibility(View.VISIBLE);
			button_step1.setVisibility(View.VISIBLE);
		
		btn_connect_device.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 clearScreen();
		           if(!mMTSCRA.isDeviceConnected())
		    	   {
		               if(mbAudioConnected)
		               {
		          		   mMTSCRA.setDeviceType(MagTekSCRA.DEVICE_TYPE_AUDIO);
		            	   openDevice();
		               }
		    	   }
				
			}
		});
		button_step1.setOnClickListener(new OnClickListener() {
			
			public void onClick(View v) {
				// TODO Auto-generated method stub
				 clearScreen();
		           if(!mMTSCRA.isDeviceConnected())
		    	   {
		               if(mbAudioConnected)
		               {
		          		   mMTSCRA.setDeviceType(MagTekSCRA.DEVICE_TYPE_AUDIO);
		            	   openDevice();
		               }
		    	   }
				
			}
		});
		back_button = (Button)findViewById(R.id.back_button);
		back_button.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		im_webservice = (Button)findViewById(R.id.imagebutton_webservice);// mcp for Webservice	
		im_webservice.setVisibility(View.GONE);		
		read2swipe = (TextView)findViewById(R.id.read_swipe);
		swipe_img = (ImageView)findViewById(R.id.swipe_img);
		swipe_img.setVisibility(View.GONE);
		read2swipe.setVisibility(View.GONE);
		im_webservice.setOnClickListener(new OnClickListener() {		
			public void onClick(View v) {
				// TODO Auto-generated method stub		

				if(mMTSCRA!=null){
					MercuryValues mercury_values =sqlitePos.mercuryDetails();
					mWSURL = mercury_values.getPrimaryURL();				
					String merchant_id = mercury_values.getMerchantID();
					
					mPassword = mercury_values.getPassword();
					
				 enc_block = mMTSCRA.getTrack2();
				 enc_key = mMTSCRA.getKSN();

				String total_amt = et_total_amount.getText().toString().trim();
				String opr_id=operator_idedit.getText().toString().trim();
				mCreditTran = "<TStream>\n\t<Transaction>\n\t\t<MerchantID>"+merchant_id+
						"</MerchantID>\n\t\t<TranType>Credit</TranType>\n\t\t<TranCode>Sale</TranCode>\n\t\t<InvoiceNo>"+Parameters.invoiceid_mercury
						+"</InvoiceNo>\n\t\t<RefNo>1</RefNo>\n\t\t<Memo>A One POS Version 3.0</Memo>\n\t\t<PartialAuth>Allow</PartialAuth>\n\t\t<Frequency>OneTime</Frequency>" +
						"\n\t\t<RecordNo>RecordNumberRequested</RecordNo>\n\t\t<Account>\n\t\t\t<EncryptedFormat>MagneSafe</EncryptedFormat>\n\t\t\t" +
						"<AccountSource>Swiped</AccountSource>\n\t\t\t<EncryptedBlock>"+enc_block+"</EncryptedBlock>\n\t\t\t<EncryptedKey>"+enc_key
						+"</EncryptedKey>\n\t\t\t<Name>MPS TEST</Name>\n\t\t</Account>\n\t\t<Amount>\n\t\t\t<Purchase>"+total_amt
						+"</Purchase>\n\t\t</Amount>\n\t\t<TerminalName>MPS Java SDK</TerminalName>\n\t\t<ShiftID>MPS Shift</ShiftID>\n\t\t<OperatorID>"
						+"MPS Operator"+"</OperatorID>\n\t</Transaction>\n</TStream>";
				
				
				mGiftTran = "<TStream>\n\t<Transaction>\n\t\t<IpPort>9100</IpPort>\n\t\t<MesrchantID>"+merchant_id+"</MerchantID>\n\t\t<TranType>PrePaid</TranType>\n\t\t<TranCode>Sale</TranCode>\n\t\t<InvoiceNo>"+Parameters.invoiceid_mercury+"</InvoiceNo>\n\t\t<RefNo>0001</RefNo>\n\t\t<Memo>A One POS Version 3.0</Memo>\n\t\t<Account>\n\t\t\t<EncryptedFormat>MagneSafe</EncryptedFormat>\n\t\t\t<AccountSource>Swiped</AccountSource>\n\t\t\t<EncryptedBlock>"+enc_block+"</EncryptedBlock>\n\t\t\t<EncryptedKey>"+enc_key+"</EncryptedKey>\n\t\t</Account>\n\t\t<Amount>\n\t\t\t<Purchase>"+total_amt+"</Purchase>\n\t\t</Amount>\n\t\t<TerminalName>MPS Java SDK</TerminalName>\n\t\t<ShiftID>MPS Shift</ShiftID>\n\t\t<OperatorID>MPS Operator</OperatorID>\n\t</Transaction>\n</TStream>";
				
				if(et_total_amount.length()!=0){
					
				//	if(spin_cardType.getSelectedItemPosition()!=0){
						
						submitRequest();
						
//					}else{
//						Toast.makeText(getApplicationContext(), "Please Select Card Type", Toast.LENGTH_LONG).show();
//					}
					
				}else{
					Toast.makeText(getApplicationContext(), "Please Enter Amount", Toast.LENGTH_LONG).show();
				}
				
				}else{
					Toast.makeText(getApplicationContext(), "Error getting the Card Details", Toast.LENGTH_LONG).show();
				}
				
			}
		});

		mMTSCRA = new MagTekSCRA(mSCRADataHandler);
		mAudioMgr = (AudioManager) getSystemService(Context.AUDIO_SERVICE);			
		setStatus(R.string.status_default, Color.RED);
		InitializeData();
		
		mIntCurrentVolume = mAudioMgr.getStreamVolume(AudioManager.STREAM_MUSIC);


		mClearImageButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{
				clearAll();
			}
		});

		mSubmitImageButton.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v)
			{
				debugMsg("Android.Model=" + android.os.Build.MODEL);
				debugMsg("Android.Device=" + android.os.Build.DEVICE);
				debugMsg("Android.Product=" + android.os.Build.PRODUCT);
				
				String mStringBody="";
				try
				{
					String strVersion = "";
					PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
					strVersion =  pInfo.versionName;
					mStringBody = "App.Version=" + strVersion + "\nSDK.Version=" + mMTSCRA.getSDKVersion() + "\n"; 
					
				}
				catch(Exception ex)
				{
					
				}		
				mStringBody += "Android.Model=" + android.os.Build.MODEL + "\n";
				mStringBody += "Android.Device=" + android.os.Build.DEVICE + "\n";
				mStringBody += "Android.Product=" + android.os.Build.PRODUCT + "\n";
				mStringBody += mStringDebugData;
				mCardDataEditText.setText(mStringBody);
				
				}
		});
		

		Timer tTimer = new Timer();

		TimerTask tTimerTask = new TimerTask() {
			public void run() {
				if (mMTSCRA.isDeviceConnected()) {
					if (mLongTimerInterval >= 2) {
						if (mMTSCRA.isDeviceConnected())
						{
							if (mIntCurrentStatus == STATUS_IDLE) 
							{
								setStatus(R.string.status_default, Color.GREEN);
							}
						}// if(mDeviceStatus==BluetoothChatService.STATE_CONNECTED)
						else
						{
							setStatus(R.string.status_default, Color.RED);
						}
						mLongTimerInterval = 0;
					}// if(mTimerInterval >= 2)

				}// if(mDeviceStatus==BluetoothChatService.STATE_CONNECTED)
				else 
				{

					if ((mIntCurrentStatus == STATUS_IDLE)&&(mIntCurrentDeviceStatus == MagTekSCRA.DEVICE_STATE_DISCONNECTED))
					{
						setStatus(R.string.status_default, Color.RED);
					}
				}
				mLongTimerInterval++;
			}
		};
		tTimer.scheduleAtFixedRate(tTimerTask, 0, 1000);
		displayInfo();
		

	}
	String getConfigurationLocal()
	{
		String strXMLConfig="";
		try
		{
			strXMLConfig = ReadSettings(getApplicationContext(),CONFIG_FILE);
			if(strXMLConfig==null)strXMLConfig="";
		}
		catch (Exception ex)
		{
		}
		
		return strXMLConfig;
	
	}
	void setConfigurationLocal(String lpstrConfig)
	{
		try
		{
			WriteSettings(getApplicationContext(),lpstrConfig,CONFIG_FILE);
		}
		catch (Exception ex)
		{
			
		}
		
	}
	void dumpWebConfigResponse(ProcessMessageResponse lpMessageResponse)
	{
		String strDisplay="";
		try
		{
            
				if(lpMessageResponse!=null)
				{
					if(lpMessageResponse.Payload!=null)
					{
						if(lpMessageResponse.Payload.StatusCode!= null)
						{
							if(lpMessageResponse.Payload.StatusCode.Number==0)
							{
								if(lpMessageResponse.Payload.SCRAConfigurations.size() > 0)
								{
									for (int i=0; i < lpMessageResponse.Payload.SCRAConfigurations.size();i++)
									{
										SCRAConfiguration tConfig = (SCRAConfiguration) lpMessageResponse.Payload.SCRAConfigurations.elementAt(i);
										strDisplay="********* Config:" + Integer.toString(i+1) + "***********\n";
										
										strDisplay+="DeviceInfo:Model:" + tConfig.DeviceInfo.getProperty(SCRAConfigurationDeviceInfo.PROP_MODEL) + "\n";
										strDisplay+="DeviceInfo:Device:" + tConfig.DeviceInfo.getProperty(SCRAConfigurationDeviceInfo.PROP_DEVICE) + "\n";
										strDisplay+="DeviceInfo:Firmware:" + tConfig.DeviceInfo.getProperty(SCRAConfigurationDeviceInfo.PROP_FIRMWARE) + "\n";
										strDisplay+="DeviceInfo.Platform:" + tConfig.DeviceInfo.getProperty(SCRAConfigurationDeviceInfo.PROP_PLATFORM) + "\n";
										strDisplay+="DeviceInfo:Product:" + tConfig.DeviceInfo.getProperty(SCRAConfigurationDeviceInfo.PROP_PRODUCT) + "\n";
										strDisplay+="DeviceInfo:Release:" + tConfig.DeviceInfo.getProperty(SCRAConfigurationDeviceInfo.PROP_RELEASE) + "\n";
										strDisplay+="DeviceInfo:SDK:" + tConfig.DeviceInfo.getProperty(SCRAConfigurationDeviceInfo.PROP_SDK) + "\n";
										strDisplay+="DeviceInfo:Status:" + tConfig.DeviceInfo.getProperty(SCRAConfigurationDeviceInfo.PROP_STATUS)+ "\n";
										//Status = 0 Unknown
										//Status = 1 Tested and Passed 
										//Status = 2 Tested and Failed 
										strDisplay+="ReaderType.Name:" + tConfig.ReaderType.getProperty(SCRAConfigurationReaderType.PROP_NAME) + "\n";
										strDisplay+="ReaderType.Type:" + tConfig.ReaderType.getProperty(SCRAConfigurationReaderType.PROP_TYPE) + "\n";
										strDisplay+="ReaderType.Version:" + tConfig.ReaderType.getProperty(SCRAConfigurationReaderType.PROP_VERSION) + "\n";
										strDisplay+="ReaderType.SDK:" + tConfig.ReaderType.getProperty(SCRAConfigurationReaderType.PROP_SDK) + "\n";
										strDisplay+="StatusCode.Description:" + tConfig.StatusCode.Description + "\n";
										strDisplay+="StatusCode.Number:" + tConfig.StatusCode.Number + "\n";
										strDisplay+="StatusCode.Version:" + tConfig.StatusCode.Version + "\n";
										for (int j=0; j < tConfig.ConfigParams.size();j++)
										{
											strDisplay+="ConfigParam.Name:" + ((ConfigParam)tConfig.ConfigParams.elementAt(j)).Name + "\n";
											strDisplay+="ConfigParam.Type:" + ((ConfigParam)tConfig.ConfigParams.elementAt(j)).Type + "\n";
											strDisplay+="ConfigParam.Value:" + ((ConfigParam)tConfig.ConfigParams.elementAt(j)).Value + "\n";
										}//for (int j=0; j < tConfig.ConfigParams.size();j++)
										strDisplay+="*********  Config:" + Integer.toString(i+1) + "***********\n";
										debugMsg(strDisplay);
									}//for (int i=0; i < lpMessageResponse.Payload.SCRAConfigurations.size();i++)
									//debugMsg(strDisplay);
								}//if(lpMessageResponse.Payload.SCRAConfigurations.size() > 0)
								
							}//if(lpMessageResponse.Payload.StatusCode.Number==0)
							strDisplay= "Payload.StatusCode.Version:" + lpMessageResponse.Payload.StatusCode.getProperty(StatusCode.PROP_VERSION) + "\n";
							strDisplay+="Payload.StatusCode.Number:" + lpMessageResponse.Payload.StatusCode.getProperty(StatusCode.PROP_NUMBER) + "\n";
							strDisplay+="Payload.StatusCode.Description:" + lpMessageResponse.Payload.StatusCode.getProperty(StatusCode.PROP_DESCRIPTION) + "\n";
							debugMsg(strDisplay);
						}//if(lpMessageResponse.Payload.StatusCode!= null)
							
					}//if(lpMessageResponse.Payload!=null)
				}//if(lpMessageResponse!=null)
				else
				{
					debugMsg("Configuration Not Found");
				}
			
		}
		catch(Exception ex)
		{
			debugMsg("Exception:" + ex.getMessage());
		}
		
	}
	
	void dumpWebConfigResponse(String lpstrXML)
	{
		debugMsg(lpstrXML);
		
	}

	void setAudioConfigManual()throws MTSCRAException
	{
    	String model = android.os.Build.MODEL.toUpperCase();
		try
		{
	    	if(model.contains("DROID RAZR") || model.toUpperCase().contains("XT910"))
	        {
				   debugMsg("Found Setting for :"  + model); 
				   mMTSCRA.setConfigurationParams("INPUT_SAMPLE_RATE_IN_HZ=48000,");
				   setStatusMessage("Found Setting for :"  + model + ":INPUT_SAMPLE_RATE_IN_HZ=48000");
	        }
	        else if ((model.equals("DROID PRO"))||
	        		 (model.equals("MB508"))||
	        		 (model.equals("DROIDX"))||
	        		 (model.equals("DROID2"))||
	        		 (model.equals("MB525")))
	        {
				  debugMsg("Found Setting for :"  + model); 
				  setStatusMessage("Found Setting for :"  + model + ":INPUT_SAMPLE_RATE_IN_HZ=32000");
				  mMTSCRA.setConfigurationParams("INPUT_SAMPLE_RATE_IN_HZ=32000,");
	        }    	
	        else if ((model.equals("GT-I9300"))||//S3 GSM Unlocked
	        		 (model.equals("SPH-L710"))||//S3 Sprint
	        		 (model.equals("SGH-T999"))||//S3 T-Mobile
	        		 (model.equals("SCH-I535"))||//S3 Verizon
	        		 (model.equals("SCH-R530"))||//S3 US Cellular
	        		 (model.equals("SAMSUNG-SGH-I747"))||// S3 AT&T
	        		 (model.equals("M532"))||//Fujitsu
	        		 (model.equals("GT-N7100"))||//Notes 2 
	        		 (model.equals("GT-N7105"))||//Notes 2 
	        		 (model.equals("SAMSUNG-SGH-I317"))||// Notes 2
	        		 (model.equals("SCH-I605"))||// Notes 2
	        		 (model.equals("SCH-R950"))||// Notes 2
	        		 (model.equals("SGH-T889"))||// Notes 2
	        		 (model.equals("SPH-L900"))||// Notes 2
	        		 (model.equals("SAMSUNG-SGH-I337"))||// S4
	        		 (model.equals("GT-P3113")))//Galaxy Tab 2, 7.0
	        		
	        {
				  setStatusMessage("Found Setting for :"  + model + ":INPUT_AUDIO_SOURCE=VRECOG");
				  debugMsg("Found Setting for :"  + model); 
	        	  mMTSCRA.setConfigurationParams("INPUT_AUDIO_SOURCE=VRECOG,");
	        }
	        else if ((model.equals("XT907")))
	        {
				  debugMsg("Found Setting for :"  + model); 
				  setStatusMessage("Found Setting for :"  + model + ":INPUT_WAVE_FORM=0");
				  mMTSCRA.setConfigurationParams("INPUT_WAVE_FORM=0,");
	        }    	
	        else
	        {
				  setStatusMessage("Using Default Settings For :"  + model);
	        }
		}
		catch(MTSCRAException ex)
		{
			debugMsg("Exception:" + ex.getMessage());
			throw new MTSCRAException(ex.getMessage());
		}
		
	}
	String setupAudioParameters()throws MTSCRAException
    {
		mStringLocalConfig="";
		String strResult="OK";
		
		try
		{
            
			//Option 1
			/*
			if (mGetConfigFromWeb.isChecked())
			{
			  debugMsg("Retrieve Configuration From Web....");
			  mMTSCRA.setConfiguration(CONFIGWS_READERTYPE,null,CONFIGWS_URL,10000);//Call Web Service to retrieve XML
			  return;

			}
			*/
			

			//Option 2
			/*
			if (mGetConfigFromWeb.isChecked())
			{
			  debugMsg("Retrieve Configuration From Web....");
			  
			  ProcessMessageResponse pResponse = mMTSCRA.getConfigurationResponse(CONFIGWS_USERNAME,CONFIGWS_PASSWORD,CONFIGWS_READERTYPE,null,CONFIGWS_URL,10000);
			  if(pResponse!=null)
			  {
				  dumpWebConfigResponse(pResponse);
				  mMTSCRA.setConfigurationResponse(pResponse); 
			  }
			  return;
			}
			*/
			
			setStatusMessage("Setting up Audio");
			
			//Option 3
			
			String strXMLConfig="";
			if (!mGetConfigFromWeb.isChecked())
			{
				strXMLConfig = getConfigurationLocal();//retrieve saved configuration. This is optional but useful if the web service connection
				   //is not available or sluggish for some reason. It is important to provide a way to 
				   //sync the local configuration to server configuration to keep the local phone config updated
			}


			if (strXMLConfig.length() <= 0)
			{
				if (mGetConfigFromWeb.isChecked())
				{
					debugMsg("Retrieve Configuration From Web....");
					setStatusMessage("Retrieve Configuration From Web");
					SCRAConfigurationDeviceInfo pDeviceInfo = new SCRAConfigurationDeviceInfo();
					pDeviceInfo.setProperty(SCRAConfigurationDeviceInfo.PROP_PLATFORM,"Android");
					pDeviceInfo.setProperty(SCRAConfigurationDeviceInfo.PROP_MODEL,android.os.Build.MODEL.toUpperCase());
					//pDeviceInfo.setProperty(SCRAConfigurationDeviceInfo.PROP_MODEL,"SPH-L720");
				    strXMLConfig = mMTSCRA.getConfigurationXML(CONFIGWS_USERNAME,CONFIGWS_PASSWORD,CONFIGWS_READERTYPE,pDeviceInfo,CONFIGWS_URL,10000);//Call Web Service to retrieve XML
					if (strXMLConfig.length() > 0)
					{
					    setStatusMessage("Configuration Received From Server\n******************************\n" + strXMLConfig + "\n******************************\n");
						ProcessMessageResponse pResponse = mMTSCRA.getConfigurationResponse(strXMLConfig);
						if(pResponse!=null)
						{
							dumpWebConfigResponse(pResponse);
							debugMsg("Setting Configuration From Response....");
							mMTSCRA.setConfigurationResponse(pResponse);
						}
						mStringLocalConfig=strXMLConfig;
						setStatusMessage("SDK Configuration Was Set Successful.\nPlease Swipe A Card....\n");
						return strResult;
					}//if (strXMLConfig.length() > 0)
					else
					{
					    setStatusMessage("No Configuration Received, Using Default");
					    strResult="Error:" + "No Configuration Received, Using Default";
						return strResult;
						
					}
				}
				else
				{
					setAudioConfigManual();					
				}
			}
			else
			{
				debugMsg("Setting Configuration Locally From XML....");
			    setStatusMessage("Configuration Saved Locally\n******************************\n" + strXMLConfig + "\n******************************\n");
				dumpWebConfigResponse(strXMLConfig);
				mMTSCRA.setConfigurationXML(strXMLConfig);//Convert XML to Response Object
				mStringLocalConfig=strXMLConfig;
				return strResult;
			}
			
		}
		catch(MTSCRAException ex)
		{
			debugMsg("Exception:" + ex.getMessage());
			strResult = "Error:" +  ex.getMessage();
			setStatusMessage("Failed Retrieving Configuration From Server:" + strResult);
			//throw new MTSCRAException(ex.getMessage());
		}
		return strResult;
    }
	@Override
	public void onStart() {
		super.onStart();
		// If BT is not on, request that it be enabled.
		
	}

	@Override
	public synchronized void onResume() {
		super.onResume();
		this.registerReceiver(mHeadsetReceiver, new IntentFilter(Intent.ACTION_HEADSET_PLUG));
		this.registerReceiver(mNoisyAudioStreamReceiver, new IntentFilter(AudioManager.ACTION_AUDIO_BECOMING_NOISY));

		// Performing this check in onResume() covers the case in which BT was
		// not enabled during onStart(), so we were paused to enable it...
		// onResume() will be called when ACTION_REQUEST_ENABLE activity
		// returns.
	}


	@Override
	public synchronized void onPause() {
		super.onPause();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		// Stop the Bluetooth chat services
		unregisterReceiver(mHeadsetReceiver);
		unregisterReceiver(mNoisyAudioStreamReceiver);
		if (mMTSCRA != null)
			closeDevice();
	
	}
	private void openDevice()
	{
		if(mMTSCRA.getDeviceType()==MagTekSCRA.DEVICE_TYPE_AUDIO)
		{
			Thread tSetupAudioParams = new Thread() {
				public void run()
				{
					try
					{
						mStringAudioConfigResult = setupAudioParameters();
					}
					catch(Exception ex)
					{
						mStringAudioConfigResult = "Error:" + ex.getMessage();	
					}
					mUIProcessCardHandler.post(mUISetupAudioParamsResults);
				}
			};
			tSetupAudioParams.start();
			
		}
		else
		{
			mMTSCRA.openDevice();
		}
	}
	final  Runnable mUISetupAudioParamsResults = new Runnable() {
		public void run() {
			try 
			{
				if(!mStringAudioConfigResult.equalsIgnoreCase("OK"))
				{
					//web configuration failed use local
					//The code below is only needed if configuration needs to be set manually
					//for some reason
					debugMsg("Setting Configuration Manually....");
					try
					{
						setAudioConfigManual();
						
					}
					catch(MTSCRAException ex)
					{
						debugMsg("Exception:" + ex.getMessage());
						throw new MTSCRAException(ex.getMessage());
					}
					
				}
				mMTSCRA.openDevice();
			} catch (Exception ex) {

			}

		}
	};
	private void closeDevice()
	{
		mMTSCRA.closeDevice();
	}

	private void ClearCardDataBuffer() {
		mMTSCRA.clearBuffers();

	}


	private void ClearScreen() 
	{
		mCardDataEditText.setText("");
	}
	private void setStatus(int lpiStatus, int lpiColor) 
	{
		StatusTextUpdateHandler.sendEmptyMessage(lpiStatus);
		StatusColorUpdateHandler.sendEmptyMessage(lpiColor);
	}

	
	


	private void displayResponseData()
    {
    	
		String strDisplay="";
		String strDisplay2="";
		
		String strResponse =  mMTSCRA.getResponseData();
		if(strResponse!=null)
		{
			strDisplay =  strDisplay + "Response.Length=" +strResponse.length()+ "\n";		
		}
		
		strDisplay =  strDisplay + "EncryptionStatus=" + mMTSCRA.getEncryptionStatus() + "\n";		
		strDisplay =  strDisplay + "SDK.Version=" + mMTSCRA.getSDKVersion() + "\n";
		strDisplay =  strDisplay + "Reader.Type=" + mMTSCRA.getDeviceType() + "\n";
		strDisplay =  strDisplay + "Track.Status=" + mMTSCRA.getTrackDecodeStatus() + "\n";
		strDisplay =  strDisplay + "KSN=" + mMTSCRA.getKSN()+ "\n";
		strDisplay =  strDisplay + "Track1.Masked=" + mMTSCRA.getTrack1Masked() + "\n";
		strDisplay =  strDisplay + "Track2.Masked=" + mMTSCRA.getTrack2Masked() + "\n";
		strDisplay =  strDisplay + "Track3.Masked=" + mMTSCRA.getTrack3Masked() + "\n";
		strDisplay =  strDisplay + "Track1.Encrypted=" + mMTSCRA.getTrack1() + "\n";
		strDisplay =  strDisplay + "Track2.Encrypted=" + mMTSCRA.getTrack2() + "\n";
		strDisplay =  strDisplay + "Track3.Encrypted=" + mMTSCRA.getTrack3() + "\n";  
		strDisplay =  strDisplay + "MagnePrint.Encrypted=" + mMTSCRA.getMagnePrint() + "\n";  
		strDisplay =  strDisplay + "MagnePrint.Status=" + mMTSCRA.getMagnePrintStatus() + "\n";  
		strDisplay =  strDisplay + "Card.IIN=" + mMTSCRA.getCardIIN() + "\n";
		strDisplay =  strDisplay + "Card.Name=" + mMTSCRA.getCardName() + "\n";
		strDisplay =  strDisplay + "Card.Last4=" + mMTSCRA.getCardLast4() + "\n";    	        	
		strDisplay =  strDisplay + "Card.ExpDate=" + mMTSCRA.getCardExpDate() + "\n";
		strDisplay =  strDisplay + "Card.SvcCode=" + mMTSCRA.getCardServiceCode() + "\n";
		strDisplay =  strDisplay + "Card.PANLength=" + mMTSCRA.getCardPANLength() + "\n";    
		strDisplay =  strDisplay + "Device.Serial=" + mMTSCRA.getDeviceSerial()+ "\n"; 
		strDisplay =  strDisplay  + "SessionID=" + mMTSCRA.getSessionID() + "\n";	
		
		
		strDisplay2=  strDisplay2+"Track2.Masked=" + mMTSCRA.getTrack2Masked() + "\n";
		strDisplay2=  strDisplay2+ "Track2.Encrypted=" + mMTSCRA.getTrack2() + "\n";
		strDisplay2=  strDisplay2+ "KSN=" + mMTSCRA.getKSN()+ "\n";
		
		String decrypted = decrypt(mMTSCRA.getKSN(), mMTSCRA.getTrack2());
		strDisplay =  strDisplay  + "real track data is=" + decrypted + "\n";	
		
		switch(mMTSCRA.getDeviceType())
		{
		case MagTekSCRA.DEVICE_TYPE_AUDIO:
			strDisplay =  strDisplay  + "Card.Status=" + mMTSCRA.getCardStatus() + "\n";
			strDisplay =  strDisplay  + "Firmware.Partnumber=" + mMTSCRA.getFirmware()+ "\n";
			strDisplay =  strDisplay  + "MagTek.SN=" + mMTSCRA.getMagTekDeviceSerial()+ "\n";
			strDisplay =  strDisplay  + "TLV.Version=" + mMTSCRA.getTLVVersion()+ "\n";
			strDisplay =  strDisplay  + "HashCode=" + mMTSCRA.getHashCode()+ "\n";
			String tstrTkStatus = mMTSCRA.getTrackDecodeStatus();
			String tstrTk1Status="01";
			String tstrTk2Status="01";
			String tstrTk3Status="01";
			
			if(tstrTkStatus.length() >=6)
			{
				tstrTk1Status=tstrTkStatus.substring(0,2);
				tstrTk2Status=tstrTkStatus.substring(2,4);
				tstrTk3Status=tstrTkStatus.substring(4,6);
				debugMsg("Track1.Status=" + tstrTk1Status );
				debugMsg("Track2.Status=" + tstrTk2Status );
				debugMsg("Track3.Status=" + tstrTk3Status );
				if ((!tstrTk1Status.equalsIgnoreCase("01"))&&
					(!tstrTk2Status.equalsIgnoreCase("01"))&&	
					(!tstrTk3Status.equalsIgnoreCase("01")))
				{
					closeDevice();
				}
			}
			else
			{
				closeDevice();
			}
			break;
		case MagTekSCRA.DEVICE_TYPE_BLUETOOTH:
			strDisplay =  strDisplay  + "CardDataCRC=" + mMTSCRA.getCardDataCRC() + "\n";
			
			break;
		default:
			break;
		
		};
		if(strResponse!=null)
		{
			strDisplay =  strDisplay + "Response.Raw=" + strResponse + "\n";		
		}
		
		mStringDebugData = strDisplay;
		mCardDataEditText.setText(strDisplay2);
		read2swipe.setVisibility(View.VISIBLE);
		swipe_img.setVisibility(View.VISIBLE);
		im_webservice.setVisibility(View.VISIBLE); // mcp for Webservice
    }    
	
	/*public String decrypt(String key, String data) {
        if (key == null || data == null)
            return null;
        try {
            byte[] dataBytes = Base64.decode(data, base64Mode);
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes(charsetName));
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance(algorithm);
            SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);
            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] dataBytesDecrypted = (cipher.doFinal(dataBytes));
            return new String(dataBytesDecrypted);
        } catch (Exception e) {
            return null;
        }
    }*/
	
	public String decrypt(String key, String data) {
        if (key == null || data == null){
        	Toast.makeText(getApplicationContext(), "data or key is null", Toast.LENGTH_SHORT).show();
        	System.out.println("data or key is null");
            return null;
        }
        try {
            byte[] dataBytes = Base64.decode(data, Base64.DEFAULT);
            DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF8"));
            SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("AES");
            SecretKey secretKey = secretKeyFactory.generateSecret(desKeySpec);
            Cipher cipher = Cipher.getInstance("AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            byte[] dataBytesDecrypted = (cipher.doFinal(dataBytes));
            Toast.makeText(getApplicationContext(), "decryption data is"+dataBytesDecrypted, Toast.LENGTH_SHORT).show();
            System.out.println("decryption data is"+dataBytesDecrypted);
            return new String(dataBytesDecrypted);
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
	}
	
	/*public static String decryptPBE(SecretKey secret, String ciphertext,
		      byte[] iv) throws Exception {
		    Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		    cipher.init(Cipher.DECRYPT_MODE, secret, new IvParameterSpec(iv));
		    return new String(cipher.doFinal(ciphertext.getBytes()), "UTF-8");
		  }*/
	
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		switch (requestCode) {
			case REQUEST_CONNECT_DEVICE: {
				// When DeviceListActivity returns with a device to connect
				if (resultCode == Activity.RESULT_OK) 
				{
				    String address = data.getExtras().getString(
							DeviceListActivity.EXTRA_DEVICE_ADDRESS);
					mMTSCRA.setDeviceType(MagTekSCRA.DEVICE_TYPE_BLUETOOTH);
					// if you know the address, you can directly specify here
					// in that case you would not need a UI to show the list
					// of BT devices
					mMTSCRA.setDeviceID(address);
					openDevice();
	
	
				}
			}
				break;
		};
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.option_menu, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.scan:
			return true;
		case R.id.bluetooth:
			// Launch the DeviceListActivity to see devices and do scan
            if(!mMTSCRA.isDeviceConnected())
    	    {
    			Intent serverIntent = new Intent(this, DeviceListActivity.class);
    			startActivityForResult(serverIntent, REQUEST_CONNECT_DEVICE);
    	    }
			return true;
		case R.id.audio:
  		   clearScreen();
           if(!mMTSCRA.isDeviceConnected())
    	   {
               if(mbAudioConnected)
               {
          		   mMTSCRA.setDeviceType(MagTekSCRA.DEVICE_TYPE_AUDIO);
            	   openDevice();
               }
    	   }
			
			return true;
		case R.id.disconn:
	  		   clearScreen();
	           if(mMTSCRA.isDeviceConnected())
	    	   {
					closeDevice();
	    	   }
				
				return true;

		case R.id.exit:
			// Ensure this device is discoverable by others
			// ensureDiscoverable();
			if (mMTSCRA != null)
				closeDevice();

			setResult(Activity.RESULT_OK);
			this.finish();
			return true;
		}
		return false;
	}

	private Handler StatusTextUpdateHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case R.string.status_default:
				if(mMTSCRA.isDeviceConnected())
				{
					mAppStatusTextView.setText(R.string.title_connected);
				}
				else
				{
					mAppStatusTextView.setText(R.string.title_not_connected);
				}
				break;
			default:
				mAppStatusTextView.setText(msg.what);
				
				if(mAppStatusTextView.getText().toString().equals("Not connected"))
				{
					btn_connect_device.setVisibility(View.VISIBLE);
					swipe_img.setVisibility(View.GONE);
					read2swipe.setVisibility(View.GONE);
				}else{
					btn_connect_device.setVisibility(View.GONE);
					button_step1.setVisibility(View.GONE);
					swipe_img.setVisibility(View.VISIBLE);
					read2swipe.setVisibility(View.VISIBLE);
				}
				
				break;
			}
			mLongTimerInterval = 0;

		}
	};
	private Handler StatusColorUpdateHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			mAppStatusTextView.setBackgroundColor(msg.what);
			mLongTimerInterval = 0;
		}
	};
	
    void ShowSoftKeyboard (EditText lpEditText)
    {
		InputMethodManager objInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		// only will trigger it if no physical keyboard is open
		objInputManager.showSoftInput(lpEditText, InputMethodManager.SHOW_IMPLICIT);					

    }
    void HideSoftKeyboard (EditText lpEditText)
    {
		//Hide Keyboard
		InputMethodManager objInputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		objInputManager.hideSoftInputFromWindow(lpEditText.getWindowToken(), 0);
    	
    }
	private void InitializeData() 
	{
	    mMTSCRA.clearBuffers();
		mLongTimerInterval = 0;
//		miReadCount=0;
		mbAudioConnected=false;
		mIntCurrentVolume=0;
		mIntCurrentStatus = STATUS_IDLE;
		mIntCurrentDeviceStatus = MagTekSCRA.DEVICE_STATE_DISCONNECTED;
		
		mStringDebugData ="";
		mStringAudioConfigResult="";
		
	}
	private void debugMsg(String lpstrMessage)
	{
		Log.i("MagTekSCRA.Demo:",lpstrMessage);
		
	}
	private void clearScreen() 
	{
		mCardDataEditText.setText("");
	}
	private void clearAll() 
	{
		ClearCardDataBuffer();
		ClearScreen();
		mIntCurrentStatus = STATUS_IDLE;
//		miReadCount = 0;
		mStringDebugData="";
		displayInfo();

	}
	private void displayInfo()
	{
		//ActivityManager tActivityManager =(ActivityManager) getApplicationContext().getSystemService(Context.ACTIVITY_SERVICE);
		//MemoryInfo tMemoryInfo = new ActivityManager.MemoryInfo();
		//tActivityManager.getMemoryInfo(tMemoryInfo);		
		//String strLog = "SwipeCount=" + miReadCount + ",Memory=" + tMemoryInfo.availMem;
		String strVersion = "";
		
		try
		{
			PackageInfo pInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
			strVersion =  pInfo.versionName;
			
		}
		catch(Exception ex)
		{
			
		}
		String strLog = "App.Version=" +strVersion + ",SDK.Version=" + mMTSCRA.getSDKVersion(); 
		//debugMsg(strLog);
		mInfoTextView.setText(strLog);
		//tMemoryInfo=null;
		//tActivityManager=null;
		
	}
	private void maxVolume()
	{
		mAudioMgr.setStreamVolume(AudioManager.STREAM_MUSIC,mAudioMgr.getStreamMaxVolume(AudioManager.STREAM_MUSIC),AudioManager.FLAG_SHOW_UI);	
			    	
	                
	}
	private void minVolume()
	{
		mAudioMgr.setStreamVolume(AudioManager.STREAM_MUSIC,mIntCurrentVolume, AudioManager.FLAG_SHOW_UI);
		
	}
	private class SCRAHandlerCallback implements Callback {
        public boolean handleMessage(Message msg) 
        {
        	
        	try
        	{
            	switch (msg.what) 
            	{
    			case MagTekSCRA.DEVICE_MESSAGE_STATE_CHANGE:
    				switch (msg.arg1) {
    				case MagTekSCRA.DEVICE_STATE_CONNECTED:
    					mIntCurrentStatus = STATUS_IDLE;
    					mIntCurrentDeviceStatus = MagTekSCRA.DEVICE_STATE_CONNECTED;    					
    					maxVolume();
    					setStatus(R.string.title_connected, Color.GREEN);
    					break;
    				case MagTekSCRA.DEVICE_STATE_CONNECTING:
    					mIntCurrentDeviceStatus = MagTekSCRA.DEVICE_STATE_CONNECTING;
    					setStatus(R.string.title_connecting, Color.YELLOW);
    					break;
    				case MagTekSCRA.DEVICE_STATE_DISCONNECTED:
    					mIntCurrentDeviceStatus = MagTekSCRA.DEVICE_STATE_DISCONNECTED;
    					setStatus(R.string.title_not_connected, Color.RED);
    					minVolume();
    					break;
    				}
    				break;
    			case MagTekSCRA.DEVICE_MESSAGE_DATA_START:
    	        	if (msg.obj != null) 
    	        	{
    	        		debugMsg("Transfer started");
    	        		mCardDataEditText.setText("Card Swiped...");
    	                return true;
    	            }
    				break;  
    			case MagTekSCRA.DEVICE_MESSAGE_DATA_CHANGE:
    	        	if (msg.obj != null) 
    	        	{
    	        		debugMsg("Transfer ended");
//    	        		miReadCount++;
    	        		displayInfo();
    	        		displayResponseData();
    	        		msg.obj=null;
    	        		if(mStringLocalConfig.length() > 0)
    	        		{
    						setConfigurationLocal(mStringLocalConfig);//optional but can be useful to retrieve from locally and get it from server only certain times
    						mStringLocalConfig="";
    	        		}

    	                return true;
    	            }
    				break;  
    			case MagTekSCRA.DEVICE_MESSAGE_DATA_ERROR:
	        		mCardDataEditText.setText("Card Swipe Error... Please Swipe Again.\n");
	                return true;
    			default:
    	        	if (msg.obj != null) 
    	        	{
    	                return true;
    	            }
    				break;
            	};
        		
        	}
        	catch(Exception ex)
        	{
        		
        	}
        	
            return false;
        	
        	
        }
    }	
	
	public class NoisyAudioStreamReceiver extends BroadcastReceiver
    {
    	@Override
    	public void onReceive(Context context, Intent intent)
    	{
    		/* If the device is unplugged, this will immediately detect that action,
    		 * and close the device.
    		 */
    		if(AudioManager.ACTION_AUDIO_BECOMING_NOISY.equals(intent.getAction()))
    		{
            	mbAudioConnected=false;
            	if(mMTSCRA.getDeviceType()==MagTekSCRA.DEVICE_TYPE_AUDIO)
            	{
            		if(mMTSCRA.isDeviceConnected())
            		{
            			closeDevice();
            			clearScreen();
            		}
            	}
    		}
    	}
    }
	
	public class headSetBroadCastReceiver extends BroadcastReceiver
    {

        @Override
        public void onReceive(Context context, Intent intent) {

            // TODO Auto-generated method stub

        	try
        	{
                String action = intent.getAction();
                //Log.i("Broadcast Receiver", action);
                if( (action.compareTo(Intent.ACTION_HEADSET_PLUG))  == 0)   //if the action match a headset one
                {
                    int headSetState = intent.getIntExtra("state", 0);      //get the headset state property
                    int hasMicrophone = intent.getIntExtra("microphone", 0);//get the headset microphone property
  				    //mCardDataEditText.setText("Headset.Detected=" + headSetState + ",Microphone.Detected=" + hasMicrophone);

                    if( (headSetState == 1) && (hasMicrophone == 1))        //headset was unplugged & has no microphone
                    {
                    	mbAudioConnected=true;
                    }
                    else 
                    {
                    	mbAudioConnected=false;
                    	if(mMTSCRA.getDeviceType()==MagTekSCRA.DEVICE_TYPE_AUDIO)
                    	{
                    		if(mMTSCRA.isDeviceConnected())
                    		{
                    			closeDevice();
                    			clearScreen();
                    		}
                    	}
                	
                    }

                }           
        		
        	}
        	catch(Exception ex)
        	{
        		
        	}

        }

    }	
	
	public static String ReadSettings(Context context, String file) throws IOException {
		FileInputStream fis = null;
		InputStreamReader isr = null;
		String data = null;
		fis = context.openFileInput(file);
		isr = new InputStreamReader(fis);
		char[] inputBuffer = new char[fis.available()];
		isr.read(inputBuffer);
		data = new String(inputBuffer);
		isr.close();
		fis.close();
		return data;
	}
	
	public static void WriteSettings(Context context, String data, String file) throws IOException {
		FileOutputStream fos= null;
		OutputStreamWriter osw = null;
		fos= context.openFileOutput(file,Context.MODE_PRIVATE);
		osw = new OutputStreamWriter(fos);
		osw.write(data);
		osw.close();
		fos.close();
	}
	private Handler StatusMessageTextUpdateHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) 
		{
			 Bundle tBundle = msg.getData();
			 String tData = tBundle.getString("XXDataXX");			
			 mCardDataEditText.setText(tData);
		}
	};
	
	private void setStatusMessage(String lpstrStatus) 
	{
		String tTemp = mCardDataEditText.getText().toString();
		if(tTemp!=null)
		{
			tTemp+=lpstrStatus + "\n";
			Message msg = new Message();
		    Bundle tBundle = new Bundle();
		    tBundle.putString("XXDataXX",tTemp);
		    msg.setData(tBundle);
			StatusMessageTextUpdateHandler.sendMessage(msg);
		}
	}
	
	
	private void submitRequest()
	{
		im_webservice.setVisibility(View.GONE);
		read2swipe.setVisibility(View.GONE);
		swipe_img.setVisibility(View.GONE);
		if(Parameters.isNetworkAvailable(MagTekDemo.this)){
		DownloadWebPageTask task = new DownloadWebPageTask();
		str_spin_cardType = spin_cardType.getSelectedItem().toString().trim();
	    task.execute(str_spin_cardType);
		}else{
			Log.e("errorw","ffg");
			showPopup("Error ","Could not Establish the Connection",false);
		}
	    }
	
	private class DownloadWebPageTask extends AsyncTask<String, Void, String> {
		
		ProgressDialog mSpinnerProgress;
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			mSpinnerProgress = new ProgressDialog(MagTekDemo.this);
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
								mpswr.addParameter("tran",mCreditTran);//Set WebServices 'tran' parameter to the XML transaction request
								Log.e("mCreditTran1111", mCreditTran);
								mpswr.addParameter("pw", mPassword); 
								Log.e("mPassword", mPassword);//Set merchant's WebServices password
								mpswr.setWebMethodName("CreditTransaction");
							}
								if(urls[0].equals("GiftTransaction")){
									mpswr.addParameter("tran", mGiftTran);
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
							e.printStackTrace();
							//showPopup("Exception error", e.toString());
						}
	      return response;
	    }

	    @Override
	    protected void onPostExecute(String result) {
	    	Log.v("Log...response12 ",""+result);
	    	
	    	mSpinnerProgress.cancel();
	    	
	    	if(result.equals("Error")){
	    		Log.e("qerror", "asasasa");
	    		showPopup("Error ","Could Not Process the Payment",false);
	    	}else{
	    		parsexml_credit(result);
	    	}
	    	
	    	
	    }
	    
	  }

	 

	static String str_CmdStatus;
	private void parsexml_gift(String str_xml) {
		// TODO Auto-generated method stub
		
		String str_response = "GIFT CARD TRANSACTION Details ";
		
		String xml = str_xml; // getting XML
        Document doc = getDomElement(xml); // getting DOM element
 
        NodeList nl = doc.getElementsByTagName("RStream");
        
        
        // looping through all item nodes <item>
        for (int i = 0; i < nl.getLength(); i++) {
            // creating new HashMap
        	        	
            Element e = (Element) nl.item(i);
            
//            CmdResponse
            NodeList node_CmdResponse = e.getElementsByTagName("CmdResponse");            
            for(int j=0; j<node_CmdResponse.getLength(); j++){

            	Element e1 = (Element)node_CmdResponse.item(j);

            	
            	NodeList node_CmdStatus = e1.getElementsByTagName("CmdStatus");
                Element line = (Element) node_CmdStatus.item(0);
                str_CmdStatus  = getCharacterDataFromElement(line);
                
                System.out.println("str_CmdStatus :: "+str_CmdStatus);
                
                str_response = str_response+"\n CmdStatus :: "+str_CmdStatus;
                                
                NodeList node_ResponseOrigin = e1.getElementsByTagName("ResponseOrigin");
                Element line_ResponseOrigin = (Element) node_ResponseOrigin.item(0);
                String str_ResponseOrigin  = getCharacterDataFromElement(line_ResponseOrigin);
                
                System.out.println("str_ResponseOrigin :: "+str_ResponseOrigin);
                
                str_response = str_response+"\n ResponseOrigin :: "+str_ResponseOrigin;
                
            	NodeList node_TextResponse = e1.getElementsByTagName("TextResponse");
                Element line_TextResponse = (Element) node_TextResponse.item(0);
                String str_TextResponse  = getCharacterDataFromElement(line_TextResponse);
                
                System.out.println("str_TextResponses :: "+str_TextResponse);
                
                str_response = str_response+"\n TextResponses :: "+str_TextResponse;
                

                
                NodeList node_DSIXReturnCode = e1.getElementsByTagName("DSIXReturnCode");
                Element line_DSIXReturnCode= (Element) node_DSIXReturnCode.item(0);
                String str_DSIXReturnCode  = getCharacterDataFromElement(line_DSIXReturnCode);
                
                System.out.println("str_DSIXReturnCode :: "+str_DSIXReturnCode);
                
                str_response = str_response+"\n DSIXReturnCode :: "+str_DSIXReturnCode;
            }
            
            
            
            if(str_CmdStatus.equals("Approved")){
            	
//            	TranRespose
            	NodeList node_TranResponse = e.getElementsByTagName("TranResponse");

            	for(int j=0; j<node_TranResponse.getLength(); j++){
            		
            		Element e1 = (Element)node_TranResponse.item(j);

            	
            		NodeList node_MerchantID = e1.getElementsByTagName("MerchantID");
            		Element line_MerchantID = (Element) node_MerchantID.item(0);
            		String str_MerchantID  = getCharacterDataFromElement(line_MerchantID);
                
            		System.out.println("str_MerchantID :: "+str_MerchantID);
                
            		 str_response = str_response+"\n MerchantID :: "+str_MerchantID;
                
            		NodeList node_AcctNo = e1.getElementsByTagName("TranType");
            		Element line_AcctNon = (Element) node_AcctNo.item(0);
            		String str_AcctNo  = getCharacterDataFromElement(line_AcctNon);
                
            		System.out.println("str_TranType :: "+str_AcctNo);
                
            		 str_response = str_response+"\n TranType :: "+str_AcctNo;
                
            		NodeList node_ExpDate = e1.getElementsByTagName("TranCode");
            		Element line_ExpDate = (Element) node_ExpDate.item(0);
            		String str_ExpDatee  = getCharacterDataFromElement(line_ExpDate);
            		
            		System.out.println("str_TranCode :: "+str_ExpDatee);
            		
            		str_response = str_response+"\n TranCode :: "+str_ExpDatee;
            		
            	
            		NodeList node_CardType = e1.getElementsByTagName("InvoiceNo");
            		Element line_CardType = (Element) node_CardType.item(0);
            		String str_CardType  = getCharacterDataFromElement(line_CardType);
                
            		System.out.println("str_InvoiceNoe :: "+str_CardType);
            		
            		str_response = str_response+"\n InvoiceNo :: "+str_CardType;
                
            		NodeList node_TranCode = e1.getElementsByTagName("TranCode");
            		Element line_TranCode= (Element) node_TranCode.item(0);
            		String str_TranCode  = getCharacterDataFromElement(line_TranCode);
                
            		System.out.println("str_TranCode :: "+str_TranCode);
                
            		str_response = str_response+"\n TranCode :: "+str_TranCode;
            		
            		NodeList node_AuthCod = e1.getElementsByTagName("TerminalName");
            		Element line_AuthCod = (Element) node_AuthCod.item(0);
            		String str_AuthCod  = getCharacterDataFromElement(line_AuthCod);
                
            		System.out.println("str_TerminalName :: "+str_AuthCod);
            		
            		str_response = str_response+"\n TerminalName :: "+str_AuthCod;
                
                
            		NodeList node_CaptureStatus = e1.getElementsByTagName("OperatorID");
            		Element line_CaptureStatus = (Element) node_CaptureStatus.item(0);
            		String str_CaptureStatus  = getCharacterDataFromElement(line_CaptureStatus);
                
            		System.out.println("str_OperatorID :: "+str_CaptureStatus);
                
            		str_response = str_response+"\n OperatorID :: "+str_CaptureStatus;
            		
            		NodeList node_RefNo = e1.getElementsByTagName("AccNo");
            		Element line_RefNoe = (Element) node_RefNo.item(0);
            		String str_RefNo  = getCharacterDataFromElement(line_RefNoe);
                
            		System.out.println("str_AccNo :: "+str_RefNo);
                
            		str_response = str_response+"\n AccNo :: "+str_RefNo;
                
            		
                
//                	Amount                
            		NodeList node_Amount = e1.getElementsByTagName("Amount");
                
            			for(int amt=0; amt<node_Amount.getLength(); amt++){
                	
            				Element e_amt = (Element)node_TranResponse.item(j);
                	
            				NodeList node_Purchase = e_amt.getElementsByTagName("Purchase");
            				Element line_Purchase= (Element) node_Purchase.item(0);
            				String str_Purchase  = getCharacterDataFromElement(line_Purchase);
                     
            				System.out.println("str_Purchase :: "+str_Purchase);
                     
            				str_response = str_response+"\n Purchase :: "+str_Purchase;
            				
            				NodeList node_Authorize = e_amt.getElementsByTagName("Authorize");
            				Element line_Authorize= (Element) node_Authorize.item(0);
            				String str_Authorize  = getCharacterDataFromElement(line_Authorize);
                     
            				System.out.println("str_Authorize :: "+str_Authorize);
            				
            				str_response = str_response+"\n Authorize :: "+str_Authorize;
            				
               				NodeList node_Balance = e_amt.getElementsByTagName("Balance");
            				Element line_Balance= (Element) node_Balance.item(0);
            				String str_Balance  = getCharacterDataFromElement(line_Balance);
                     
            				System.out.println("str_Authorize :: "+str_Balance);
            				
            				str_response = str_response+"\n Balance :: "+str_Balance;
            			}
                                	
            	}
            
            }
        }
        
        showPopup("RESPONSE", str_response,true);
		
	}
	
	private void parsexml_credit(String gift_trans){
		// TODO Auto-generated method stub
		String str_response = "TRANSACTION Details";
		try{
		
		
		String xml = gift_trans; // getting XML
        Document doc = getDomElement(xml); // getting DOM element
 
        NodeList nl = doc.getElementsByTagName("RStream");
        
        
        // looping through all item nodes <item>
        for (int i = 0; i < nl.getLength(); i++) {
            // creating new HashMap
        	        	
            Element e = (Element) nl.item(i);
            
//            CmdResponse
            NodeList node_CmdResponse = e.getElementsByTagName("CmdResponse");            
            for(int j=0; j<node_CmdResponse.getLength(); j++){

            	Element e1 = (Element)node_CmdResponse.item(j);

            	
            	NodeList node_CmdStatus = e1.getElementsByTagName("CmdStatus");
                Element line = (Element) node_CmdStatus.item(0);
                str_CmdStatus  = getCharacterDataFromElement(line);
                
                System.out.println("str_CmdStatus :: "+str_CmdStatus);
                
                str_response = str_response+"\n CmdStatus :: "+str_CmdStatus;
                                
                NodeList node_ResponseOrigin = e1.getElementsByTagName("ResponseOrigin");
                Element line_ResponseOrigin = (Element) node_ResponseOrigin.item(0);
                String str_ResponseOrigin  = getCharacterDataFromElement(line_ResponseOrigin);
                
//                System.out.println("str_ResponseOrigin :: "+str_ResponseOrigin);
                
                str_response = str_response+"\n ResponseOrigin :: "+str_ResponseOrigin;
                
            	NodeList node_TextResponse = e1.getElementsByTagName("TextResponse");
                Element line_TextResponse = (Element) node_TextResponse.item(0);
                String str_TextResponse  = getCharacterDataFromElement(line_TextResponse);
                
                System.out.println("str_TextResponses :: "+str_TextResponse);
                
                str_response = str_response+"\n TextResponses :: "+str_TextResponse;
                
                NodeList node_UserTraceData = e1.getElementsByTagName("UserTraceData");
                Element line_UserTraceData = (Element) node_UserTraceData.item(0);
                String str_UserTraceData  = getCharacterDataFromElement(line_UserTraceData);
                
                System.out.println("str_UserTraceData :: "+str_UserTraceData);
                
//                str_response = str_response+"\n UserTraceData :: "+str_UserTraceData;
                
                NodeList node_DSIXReturnCode = e1.getElementsByTagName("DSIXReturnCode");
                Element line_DSIXReturnCode= (Element) node_DSIXReturnCode.item(0);
                String str_DSIXReturnCode  = getCharacterDataFromElement(line_DSIXReturnCode);
                
                System.out.println("str_DSIXReturnCode :: "+str_DSIXReturnCode);
                
//                str_response = str_response+"\n DSIXReturnCode :: "+str_DSIXReturnCode;
            }
            
            
            
            if(str_CmdStatus.equals("Approved")){
//            	TranRespose
            	NodeList node_TranResponse = e.getElementsByTagName("TranResponse");

            	for(int j=0; j<node_TranResponse.getLength(); j++){
            		
            		Element e1 = (Element)node_TranResponse.item(j);

            	
            		NodeList node_MerchantID = e1.getElementsByTagName("MerchantID");
            		Element line_MerchantID = (Element) node_MerchantID.item(0);
            		String str_MerchantID  = getCharacterDataFromElement(line_MerchantID);
                
//            		System.out.println("str_MerchantID :: "+str_MerchantID);
                
            		 str_response = str_response+"\n MerchantID :: "+str_MerchantID;
            		 merchant_id1=str_MerchantID;
            		NodeList node_AcctNo = e1.getElementsByTagName("AcctNo");
            		Element line_AcctNon = (Element) node_AcctNo.item(0);
            		String str_AcctNo  = getCharacterDataFromElement(line_AcctNon);
                
            		System.out.println("str_AcctNo :: "+str_AcctNo);
                
            		 str_response = str_response+"\n AcctNo :: "+str_AcctNo;
            		 card_no=str_AcctNo;
            		NodeList node_ExpDate = e1.getElementsByTagName("ExpDate");
            		Element line_ExpDate = (Element) node_ExpDate.item(0);
            		String str_ExpDatee  = getCharacterDataFromElement(line_ExpDate);
            		
            		System.out.println("str_ExpDatee :: "+str_ExpDatee);
            		
            		str_response = str_response+"\n ExpDatee :: "+str_ExpDatee;
            		exp_date=str_ExpDatee;
            	
            		NodeList node_CardType = e1.getElementsByTagName("CardType");
            		Element line_CardType = (Element) node_CardType.item(0);
            		String str_CardType  = getCharacterDataFromElement(line_CardType);
                
            		System.out.println("str_CardType :: "+str_CardType);
            		
            		str_response = str_response+"\n CardType :: "+str_CardType;
            		card=str_CardType;
            		NodeList node_TranCode = e1.getElementsByTagName("TranCode");
            		Element line_TranCode= (Element) node_TranCode.item(0);
            		String str_TranCode  = getCharacterDataFromElement(line_TranCode);
                
            		System.out.println("str_TranCode :: "+str_TranCode);
                
            		str_response = str_response+"\n TranCode :: "+str_TranCode;
            		tran_id=str_TranCode;
            		NodeList node_AuthCod = e1.getElementsByTagName("AuthCode");
            		Element line_AuthCod = (Element) node_AuthCod.item(0);
            		String str_AuthCod  = getCharacterDataFromElement(line_AuthCod);
                
            		System.out.println("str_AuthCod :: "+str_AuthCod);
            		
            		str_response = str_response+"\n AuthCod :: "+str_AuthCod;
            		approval_code=str_AuthCod;
                
            		NodeList node_CaptureStatus = e1.getElementsByTagName("CaptureStatus");
            		Element line_CaptureStatus = (Element) node_CaptureStatus.item(0);
            		String str_CaptureStatus  = getCharacterDataFromElement(line_CaptureStatus);
                
            		System.out.println("str_CaptureStatus :: "+str_CaptureStatus);
                
            		str_response = str_response+"\n CaptureStatus :: "+str_CaptureStatus;
            		
            		NodeList node_RefNo = e1.getElementsByTagName("RefNo");
            		Element line_RefNoe = (Element) node_RefNo.item(0);
            		String str_RefNo  = getCharacterDataFromElement(line_RefNoe);
                
            		System.out.println("str_RefNo :: "+str_RefNo);
            		ref_no=str_RefNo;
            		str_response = str_response+"\n RefNo :: "+str_RefNo;
                
            		NodeList node_InvoiceNo = e1.getElementsByTagName("InvoiceNo");
            		Element line_InvoiceNo = (Element) node_InvoiceNo.item(0);
            		String str_InvoiceNo  = getCharacterDataFromElement(line_InvoiceNo);
                
            		System.out.println("str_InvoiceNo :: "+str_InvoiceNo);
            		invoice_no=str_InvoiceNo;
            		str_response = str_response+"\n InvoiceNo :: "+str_InvoiceNo;
                
            		NodeList node_OperatorID = e1.getElementsByTagName("OperatorID");
            		Element line_OperatorID= (Element) node_OperatorID.item(0);
            		String str_OperatorID  = getCharacterDataFromElement(line_OperatorID);
                
            		System.out.println("str_OperatorID :: "+str_OperatorID);
            		
            		str_response = str_response+"\n OperatorID :: "+str_OperatorID;
                
            		NodeList node_Memo = e1.getElementsByTagName("Memo");
            		Element line_Memo= (Element) node_Memo.item(0);
            		String str_Memo  = getCharacterDataFromElement(line_Memo);
                
            		System.out.println("str_Memo :: "+str_Memo);
                
//            		str_response = str_response+"\n Memo :: "+str_Memo;
                
//                	Amount                
            		NodeList node_Amount = e1.getElementsByTagName("Amount");
                
            			for(int amt=0; amt<node_Amount.getLength(); amt++){
                	
            				Element e_amt = (Element)node_TranResponse.item(j);
                	
            				NodeList node_Purchase = e_amt.getElementsByTagName("Purchase");
            				Element line_Purchase= (Element) node_Purchase.item(0);
            				str_Purchase  = getCharacterDataFromElement(line_Purchase);
                     
            				System.out.println("str_Purchase :: "+str_Purchase);
                     
            				str_response = str_response+"\n Purchase :: "+str_Purchase;
            				Parameters.sentAmount=str_Purchase;
            				NodeList node_Authorize = e_amt.getElementsByTagName("Authorize");
            				Element line_Authorize= (Element) node_Authorize.item(0);
            				String str_Authorize  = getCharacterDataFromElement(line_Authorize);
                     
            				System.out.println("str_Authorize :: "+str_Authorize);
            				price=str_Authorize;
                				Parameters.sentAmount=str_Authorize;
            				str_response = str_response+"\n Authorize :: "+str_Authorize;
            			}
                
                try{
            		NodeList node_AcqRefData = e1.getElementsByTagName("AcqRefData");
            		Element line_AcqRefData = (Element) node_AcqRefData.item(0);
            		String str_AcqRefData  = getCharacterDataFromElement(line_AcqRefData);
            		AcqRefData=str_AcqRefData;
            		System.out.println("str_AcqRefData :: "+str_AcqRefData);
                
//            		str_response = str_response+"\n AcqRefData :: "+str_AcqRefData;
                
            		NodeList node_RecordNo = e1.getElementsByTagName("RecordNo");
            		Element line_RecordNo = (Element) node_RecordNo.item(0);
            		String str_RecordNo  = getCharacterDataFromElement(line_RecordNo);
                
            		System.out.println("str_RecordNo :: "+str_RecordNo);
            		
//            		str_response = str_response+"\n RecordNo :: "+str_RecordNo;
                
            		NodeList node_ProcessData = e1.getElementsByTagName("ProcessData");
                	Element line_ProcessData= (Element) node_ProcessData.item(0);
                	String str_ProcessData  = getCharacterDataFromElement(line_ProcessData);
                	ProcessData=str_ProcessData;
                	System.out.println("str_ProcessData :: "+str_ProcessData);
                }catch(NullPointerException e4){
                	Log.e("seeeerror","cvxcvdf");
                	e4.printStackTrace();
                }catch (Exception e2) {
					// TODO: handle exception
                	Log.e("seeeerror","cvxcvdfd");
                	e2.printStackTrace();
				}
//                	str_response = str_response+"\n ProcessData :: "+str_ProcessData;
                                	
            	}
        //    	updateinvoicetable(Parameters.generateRandomNumber(), "complete", "", "Credit/Debit", "");
		//		updateTheInvoice(Parameters.generateRandomNumber(), str_Purchase, "", "", "Credit/Debit");
           
            }else{
            	Parameters.mercury_result="Error";
            	showPopup("Payment Error", str_response,false);
            }
            
        }
        
	    }catch(NullPointerException n){
	    	Log.e("seeeerrssor","NullPointerExceptionggggg");
				showPopup("Error ","Could Not Process the Payment",false);
			}catch (Exception e) {
				// TODO: handle exception
				Log.e("seeeerrssodddr","NullPointerExceptionggggg");
				showPopup("Error ","Could Not Process the Payment", false);
			}
		if(str_CmdStatus.equals("Approved")){
			  Parameters.mercury_result="Approved";
              //  showPopupApproved("Payment Successfull", str_response);
                Intent intent1=new Intent(MagTekDemo.this,ReceiptShowActivity.class);
                intent1.putExtra("str_response", str_response);
                intent1.putExtra("merchant_id", ""+merchant_id1);
                intent1.putExtra("ref_no", ""+ref_no);
                intent1.putExtra("invoice_no", ""+invoice_no);
                intent1.putExtra("approval_code", ""+approval_code);
                intent1.putExtra("card_no", ""+card_no);
                intent1.putExtra("exp_date", ""+exp_date);
                intent1.putExtra("card", ""+card);
                intent1.putExtra("tran_id", ""+tran_id);
                intent1.putExtra("price", ""+price);
                intent1.putExtra("sentamount", ""+sentamount);
                intent1.putExtra("AcqRefData", ""+AcqRefData);
                intent1.putExtra("ProcessData",""+ProcessData);
                intent1.putExtra("enc_block",""+ enc_block);
                intent1.putExtra("enc_key", ""+enc_key);
                intent1.putExtra("str_spin_cardType", ""+str_spin_cardType);
                
                startActivity(intent1);
                finish();
		}
	}
	
	String str_Purchase;
	
	public static String getCharacterDataFromElement(Element e)
	{
	    Node child = e.getFirstChild();
	    if (child instanceof CharacterData)
	    {
	        CharacterData cd = (CharacterData) child;
	        return cd.getData();
	    }
	    return "";
	}
	
	public Document getDomElement(String xml){
        Document doc = null;
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        try {
 
            DocumentBuilder db = dbf.newDocumentBuilder();
 
            InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(xml));
                doc = db.parse(is);
 
            } catch (ParserConfigurationException e) {
                Log.e("Error: ", e.getMessage());
                return null;
            } catch (SAXException e) {
                Log.e("Error: ", e.getMessage());
                return null;
            } catch (IOException e) {
                Log.e("Error: ", e.getMessage());
                return null;
            }
                // return DOM
            return doc;
    }
	
	public String getValue(Element item, String str) {     
	    NodeList n = ((Document) item).getElementsByTagName(str);       
	    return this.getElementValue(n.item(0));
	}
	 
	public final String getElementValue( Node elem ) {
	         Node child;
	         if( elem != null){
	             if (elem.hasChildNodes()){
	                 for( child = elem.getFirstChild(); child != null; child = child.getNextSibling() ){
	                     if( child.getNodeType() == Node.TEXT_NODE  ){
	                         return child.getNodeValue();
	                     }
	                 }
	             }
	         }
	         return "";
	  } 
	protected void showPopupApproved(final String title, String string2, final boolean value) {
		// TODO Auto-generated method stub
		
		new AlertDialog.Builder(this)
	    .setTitle(title)
	    .setMessage(string2)
	    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	if(value)        	
	        		finish();
	        	
	        }
	     })
	    .setIcon(R.drawable.aone_logo_action)
	     .show();
	}
	
	protected void showPopup(final String title, String string2,final boolean value) {
		// TODO Auto-generated method stub
		
		new AlertDialog.Builder(this)
	    .setTitle(title)
	    .setMessage(string2)
	    .setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	            // continue with delete
	        	if(value)      	
	        		finish();
	        }
	     })
	    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
	        public void onClick(DialogInterface dialog, int which) { 
	        	finish();
	        }
	     })
	    .setIcon(R.drawable.aone_logo_action)
	     .show();
		
	}
	@Override
	public void onBackPressed() {
		
	}
	
}
