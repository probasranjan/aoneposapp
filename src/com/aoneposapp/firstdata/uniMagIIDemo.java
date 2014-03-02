package com.aoneposapp.firstdata;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Random;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;

import IDTech.MSR.XMLManager.StructConfigParameters;
import IDTech.MSR.uniMag.Common;
import IDTech.MSR.uniMag.StateList;
import IDTech.MSR.uniMag.uniMagReader;
import IDTech.MSR.uniMag.uniMagReader.ReaderType;
import IDTech.MSR.uniMag.uniMagReaderMsg;
import IDTech.MSR.uniMag.UniMagTools.uniMagReaderToolsMsg;
import IDTech.MSR.uniMag.UniMagTools.uniMagSDKTools;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.aoneposapp.R;
/*
 * 
 * File name: 	uniMagIIDemo.java
 * Author:		Eric.Yang
 * Time:		2011.10.21
 * 
 * Modified by Jimmy Mo on 2012.09.21
 * to make demo application more readable
 * 
 */

// interface uniMagReaderMsg should be implemented
// if firmware download is supported, uniMagReaderToolsMsg also needs to be implemented 
public class uniMagIIDemo extends Activity implements  uniMagReaderMsg ,uniMagReaderToolsMsg{
	String requestformatpayloadcontent = "";
	String transactionamt = "0000000868";
	String transactioncurrency = "840";
	String referencenum = "15000150150";
	String ordernum = "12000500";
	String request = "";
	String response = "";
	String tempXmlParsingString = "";
	String tempString = "";
	String track2data, cardtotaldata, cardNumber;
	String pindata = "7C253F3622D7732D";
	String keyserialnumber = "F876543210000CC0000B";
	String url = "https://stg.dw.us.fdcnet.biz/rc";
	private final static String HEX = "0123456789ABCDEF";
	HashMap<String, String> list=null;  
	// declaring the instance of the uniMagReader;
	private uniMagReader myUniMagReader = null;
	private uniMagSDKTools firmwareUpdateTool = null;
	
	private TextView connectStatusTextView; // displays status of UniMag Reader: Connected / Disconnected
	private TextView headerTextView; // short description of data displayed below
	private TextView textAreaTop;
	// private EditText textAreaBottom;
	//private Button btnCommand;
//	private Button btnSwipeCard;
	private boolean isReaderConnected = false;
	private boolean isExitButtonPressed = false;
	private boolean isWaitingForCommandResult=false;
	private boolean isSaveLogOptionChecked = false;
	private boolean isUseAutoConfigProfileChecked = false;
	private boolean isConnectWithCommand = true;
	
	//update the powerup status
	private int percent = 0;
	private long beginTime = 0;
	private long beginTimeOfAutoConfig = 0;
	private byte[] challengeResponse = null;

	private String popupDialogMsg = null;
	private boolean enableSwipeCard =false;
	private boolean autoconfig_running = false;	

	private String strMsrData = null;
	private byte[] msrData = null;
	private String statusText = null;
	private int challengeResult = 0;
	
	

	/*****************************************	
	CREATE TABLE profiles ( 
		search_date DATETIME,
		direction_output_wave INTEGER,
		input_frequency INTEGER,
		output_frequency INTEGER,
		record_buffer_size INTEGER,
		read_buffer_size INTEGER,
		wave_direction INTEGER,
		_low INTEGER,
		_high INTEGER,
		__low INTEGER,
		__high INTEGER,
		high_threshold INTEGER,
		low_threshold INTEGER,
		device_apm_base INTEGER,
		min INTEGER,
		max INTEGER,
		baud_rate INTEGER,
		preamble_factor INTEGER,
		set_default INTEGER)
		
	)
	*****************************************/
	
	static private final int REQUEST_GET_XML_FILE = 1;
	static private final int REQUEST_GET_BIN_FILE = 2;
	static private final int REQUEST_GET_ENCRYPTED_BIN_FILE = 3;
	
	//property for the menu item.
	static final private int START_SWIPE_CARD 	= Menu.FIRST;
	static final private int SETTINGS_ITEM 		= Menu.FIRST + 2;
	static final private int SUB_SAVE_LOG_ITEM 	= Menu.FIRST + 3;
	static final private int SUB_USE_AUTOCONFIG_PROFILE = Menu.FIRST + 4;
	static final private int SUB_USE_COMMAND_TO_CONNECT = Menu.FIRST + 5;
	static final private int SUB_LOAD_XML 		= Menu.FIRST + 6;
	static final private int SUB_LOAD_BIN 		= Menu.FIRST + 7;
	static final private int SUB_START_AUTOCONFIG= Menu.FIRST + 8;
	static final private int SUB_STOP_AUTOCONFIG = Menu.FIRST + 10;
	static final private int SUB_ATTACHED_TYPE 	= Menu.FIRST + 103;
	static final private int SUB_SUPPORT_STATUS	= Menu.FIRST + 104;
	static final private int DELETE_LOG_ITEM 	= Menu.FIRST + 11;   
	static final private int ABOUT_ITEM 		= Menu.FIRST + 12;  
	static final private int EXIT_IDT_APP 		= Menu.FIRST + 13;
	static final private int SUB_LOAD_ENCRYPTED_BIN = Menu.FIRST + 14;
    
	private MenuItem itemStartSC = null;
	private MenuItem itemSubSaveLog = null;
	private MenuItem itemSubUseAutoConfigProfile = null;
	private MenuItem itemSubUseCommandToConnect = null;
	private MenuItem itemSubLoadXML = null;
	private MenuItem itemSubSetBinFile = null;
	private MenuItem itemSubSetEncryptedBinFile = null;
	private MenuItem itemSubStartAutoConfig = null;
	private MenuItem itemSubStopAutoConfig = null;
	private MenuItem itemDelLogs = null;   
	private MenuItem itemAbout = null;
	private MenuItem itemExitApp = null;
	
	private SubMenu sub = null;
    
	private UniMagTopDialog dlgTopShow = null ;
	private UniMagTopDialog dlgSwipeTopShow = null ;
	private UniMagTopDialogYESNO dlgYESNOTopShow = null ;

	private StructConfigParameters profile = null;
	private ProfileDatabase profileDatabase = null;
	private Handler handler = new Handler();
	String paymenttypespinnerval;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);
    	setContentView(R.layout.main1);
    	
    	Parameters.responseCodeDetails();
		
		final Spinner paymenttypespinner = (Spinner)findViewById(R.id.paymenttypespinner);
		Button cancel = (Button)findViewById(R.id.cancel);
		
		cancel.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				onBackPressed();
				finish();
			}
		});
		
		paymenttypespinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1,
					int arg2, long arg3) {
				// TODO Auto-generated method stub
				paymenttypespinnerval = paymenttypespinner.getSelectedItem().toString();
				System.out.println("spinnerval is:"+paymenttypespinnerval);
				if(paymenttypespinnerval.equals(Parameters.PaymntTypeCREDIT)){
					
					showAlertDialog(uniMagIIDemo.this, "Swipe", "Please swipe the card", false, "credit");
							
				}else if(paymenttypespinnerval.equals(Parameters.PaymntTypeDEBIT)){
					
					showAlertDialog(uniMagIIDemo.this, "Swipe", "Please swipe the card", false, "debit");
					
				}else if(paymenttypespinnerval.equals(Parameters.PaymntTypePLDEBIT)){
					
					//showAlertDialog(uniMagIIDemo.this, "Swipe", "Please swipe the card", false, "pldebit");
					
				}else if(paymenttypespinnerval.equals(Parameters.PaymntTypeEBT)){
					
					showAlertDialog(uniMagIIDemo.this, "Swipe", "Please swipe the card", false, "ebt");
				}
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
    	profileDatabase = new ProfileDatabase(this);
    	profileDatabase.initializeDB();
    	isUseAutoConfigProfileChecked = profileDatabase.getIsUseAutoConfigProfile();
    	initializeUI();
    	initializeReader();
		
    	String strManufacture = myUniMagReader.getInfoManufacture();
    	String strModel = myUniMagReader.getInfoModel();
    	String strSDKVerInfo = myUniMagReader.getSDKVersionInfo();
    	String strOSVerInfo = android.os.Build.VERSION.RELEASE;
		
    	textAreaTop.setText("Phone: "+strManufacture+"\n"+"Model: "+strModel+"\n"+"SDK Ver: "+strSDKVerInfo+"\nOS Version: "+strOSVerInfo);
		
    	// to prevent screen timeout
    	getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
	}
    
    protected void generateRandomNumber() {
		// TODO Auto-generated method stub
    	 Random randomGenerator = new Random();
 	     int randomInt = randomGenerator.nextInt(1000000000);
 	    referencenum = Integer.toString(randomInt); 	    
	}
    
    protected void generateRandomNumber1() {
		// TODO Auto-generated method stub
    	 Random randomGenerator = new Random();
 	     int randomInt = randomGenerator.nextInt(10000000);
 	    ordernum = Integer.toString(randomInt); 	    
	}
    
	@Override
	protected void onPause() {
		if(myUniMagReader!=null)
		{
			//stop swipe card when the application go to background
			myUniMagReader.stopSwipeCard();			
		}
		hideTopDialog();
		hideSwipeTopDialog();
		super.onPause();
	}
	@Override
	protected void onResume() {
		if(myUniMagReader!=null)
		{
			if(isSaveLogOptionChecked==true)
				myUniMagReader.setSaveLogEnable(true);
			else
				myUniMagReader.setSaveLogEnable(false);
		}
		if(itemStartSC!=null)
			itemStartSC.setEnabled(true); 
		isWaitingForCommandResult=false;
		super.onResume();
	}
	@Override
    protected void onDestroy() {
		myUniMagReader.release();
		profileDatabase.closeDB();
		super.onDestroy();
		if (isExitButtonPressed)
		{
			android.os.Process.killProcess(android.os.Process.myPid());
		}
    }
	@SuppressWarnings("deprecation")
	public void showAlertDialog(Context context, String title, String message,
			Boolean status, final String methodtext) {
		AlertDialog alertDialog = new AlertDialog.Builder(context).create();
		
		alertDialog.setTitle(title);
		alertDialog.setMessage(message);
		alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if(methodtext.equals("credit")){
					if (myUniMagReader!=null)
					{	
						if (!isWaitingForCommandResult) 
						{
							if(myUniMagReader.startSwipeCard())
							{
								headerTextView.setText("MSR Data");
								textAreaTop.setText("");
								//textAreaBottom.setText("");
								Log.d("Demo Info >>>>>","to startSwipeCard");
							}
							else
								Log.d("Demo Info >>>>>","cannot startSwipeCard");
						}
					}
					dialog.dismiss();
					
				}else if(methodtext.equals("debit")){
					if (myUniMagReader!=null)
					{	
						if (!isWaitingForCommandResult) 
						{
							if(myUniMagReader.startSwipeCard())
							{
								headerTextView.setText("MSR Data");
								textAreaTop.setText("");
								//textAreaBottom.setText("");
								Log.d("Demo Info >>>>>","to startSwipeCard");
							}
							else
								Log.d("Demo Info >>>>>","cannot startSwipeCard");
						}
					}
					dialog.dismiss();
					
				}else if(methodtext.equals("pldebit")){
					/*if (myUniMagReader!=null)
					{	
						if (!isWaitingForCommandResult) 
						{
							if(myUniMagReader.startSwipeCard())
							{
								headerTextView.setText("MSR Data");
								textAreaTop.setText("");
							//	textAreaBottom.setText("");
								Log.d("Demo Info >>>>>","to startSwipeCard");
							}
							else
								Log.d("Demo Info >>>>>","cannot startSwipeCard");
						}
					}*/
					dialog.dismiss();
					
					
				}else if(methodtext.equals("ebt")){
					if (myUniMagReader!=null)
					{	
						if (!isWaitingForCommandResult) 
						{
							if(myUniMagReader.startSwipeCard())
							{
								headerTextView.setText("MSR Data");
								textAreaTop.setText("");
								//textAreaBottom.setText("");
								Log.d("Demo Info >>>>>","to startSwipeCard");
							}
							else
								Log.d("Demo Info >>>>>","cannot startSwipeCard");
						}
					}
					dialog.dismiss();
					
				}else{
					/*if (myUniMagReader!=null)
					{	
						if (!isWaitingForCommandResult) 
						{
							if(myUniMagReader.startSwipeCard())
							{
								headerTextView.setText("MSR Data");
								textAreaTop.setText("");
							//	textAreaBottom.setText("");
								Log.d("Demo Info >>>>>","to startSwipeCard");
							}
							else
								Log.d("Demo Info >>>>>","cannot startSwipeCard");
						}
					}*/
					dialog.dismiss();
					if(list.get("RespCode").equals("000")){
						//Intent intent = new Intent(uniMagIIDemo.this, PosMainActivity.class);
						System.out.println("the response is 000");
						Intent i = new Intent();
						setResult(9876, i);
						finish();
						//startActivityForResult(intent, 1234);
					}else{
						System.out.println("the response is something");
						Intent i = new Intent();
						setResult(12345, i);
						finish();
					}
				}
			}
		});
		alertDialog.show();
	}
    @Override
	public synchronized void onActivityResult(final int requestCode, int resultCode, final Intent data) {
    	System.out.println("unimag onresult is called");
        if (resultCode == Activity.RESULT_OK) {

        	String strTmpFileName = data.getStringExtra(FileDialog.RESULT_PATH);;
            if (requestCode == REQUEST_GET_XML_FILE) {
	    		
	    		if(!isFileExist(strTmpFileName))
	    		{ 
	    			headerTextView.setText("Warning");
	    			textAreaTop.setText("Please copy the XML file 'IDT_uniMagCfg.xml' into root path of SD card.");
	    		//	textAreaBottom.setText("");
	    			return  ;
	    		}
	    		if (!strTmpFileName.endsWith(".xml")){
	    			headerTextView.setText("Warning");
	    			textAreaTop.setText("Please select a file with .xml file extension.");
	    		//	textAreaBottom.setText("");
	    			return  ;
	    		}
	    		
	    		/////////////////////////////////////////////////////////////////////////////////
	    		// loadingConfigurationXMLFile() method may connect to server to download xml file.
	    		// Network operation is prohibited in the UI Thread if target API is 11 or above.
	    		// If target API is 11 or above, please use AsyncTask to avoid errors.
	    	    myUniMagReader.setXMLFileNameWithPath(strTmpFileName);
	    	    if (myUniMagReader.loadingConfigurationXMLFile(false)) {
		    	    headerTextView.setText("Command Info");
		    	    textAreaTop.setText("Reload XML file succeeded.");
		    	 //   textAreaBottom.setText("");
	    	    }
	    	    else {
	    			headerTextView.setText("Warning");
	    			textAreaTop.setText("Please select a correct file and try again.");
	    		//	textAreaBottom.setText("");
	    	    }
            } 
            else if (requestCode == REQUEST_GET_BIN_FILE)
            {
 	    		if(!isFileExist(strTmpFileName))
	    		{ 
 	    			headerTextView.setText("Warning");
 	    			textAreaTop.setText("Please copy the BIN file into the SD card root path.");
 	    		//	textAreaBottom.setText("");
	    			return  ;
	    		} 
				//set BIN file
		        if(true==firmwareUpdateTool.setFirmwareBINFile(strTmpFileName))
		        {
		        	headerTextView.setText("Command Info");
		        	textAreaTop.setText("Set the BIN file succeeded.");
		       // 	textAreaBottom.setText("");
	    		}
		        else
		        {
		        	headerTextView.setText("Command Info");
		        	textAreaTop.setText("Failed to set the BIN file, please check the file format.");
		        //	textAreaBottom.setText("");
		        }
            }
            else if(requestCode == REQUEST_GET_ENCRYPTED_BIN_FILE)
            {

 	    		if(!isFileExist(strTmpFileName))
	    		{ 
 	    			headerTextView.setText("Warning");
 	    			textAreaTop.setText("Please copy the BIN file into the SD card root path.");
 	    		//	textAreaBottom.setText("");
	    			return  ;
	    		} 
				//set BIN file
		        if(true==firmwareUpdateTool.setFirmwareEncryptedBINFile(strTmpFileName))
		        {
		        	headerTextView.setText("Command Info");
		        	textAreaTop.setText("Set the Encrypted BIN file succeeded.");
		        //	textAreaBottom.setText("");
	    		}
		        else
		        {
		        	headerTextView.setText("Command Info");
		        	textAreaTop.setText("Failed to set the Encrypted BIN file, please check the file format.");
		        //	textAreaBottom.setText("");
		        }
            }
        } 
    }

	private void initializeUI()
	{
	//	btnSwipeCard = (Button)findViewById(R.id.btn_swipeCard);
	//	btnCommand = (Button)findViewById(R.id.btn_command);
		textAreaTop = (TextView)findViewById(R.id.text_area_top);
	//	textAreaBottom = (EditText)findViewById(R.id.text_area_bottom);
		connectStatusTextView = (TextView)findViewById(R.id.status_text);
		headerTextView = (TextView)findViewById(R.id.header_text);
	
		headerTextView.setText("MSR Data");
		connectStatusTextView.setText("DISCONNECTED");
		getWindow().setFlags(WindowManager.LayoutParams. FLAG_FULLSCREEN, WindowManager.LayoutParams. FLAG_FULLSCREEN);

		// Set Listener for "Swipe Card" Button
		/*btnSwipeCard.setOnClickListener(new OnClickListener(){  
			public void onClick(View v) {
				if (myUniMagReader!=null)
				{	
					if (!isWaitingForCommandResult) 
					{
						if(myUniMagReader.startSwipeCard())
						{
							headerTextView.setText("MSR Data");
							textAreaTop.setText("");
						//	textAreaBottom.setText("");
							Log.d("Demo Info >>>>>","to startSwipeCard");
						}
						else
							Log.d("Demo Info >>>>>","cannot startSwipeCard");
					}
				}
			}  
		});  */

		// Set Listener for "Command" Button
	/*	btnCommand.setOnClickListener(new OnClickListener(){  
			public void onClick(View v) {
				if (!isWaitingForCommandResult)
				{
					DlgSettingOption myDlg = new DlgSettingOption (uniMagIIDemo.this,myUniMagReader);
					myDlg.DisplayDlg();
				}
			}  
		});  */
	}

	private void initializeReader()
	{

		if(myUniMagReader!=null){
			myUniMagReader.unregisterListen();
			myUniMagReader.release();
			myUniMagReader = null;
		}
		if (isConnectWithCommand)
			myUniMagReader = new uniMagReader(this,this,true);
		else 
			myUniMagReader = new uniMagReader(this,this);
			
		myUniMagReader.setVerboseLoggingEnable(true);
        myUniMagReader.registerListen();
        
        //load the XML configuratin file
        String fileNameWithPath = getConfigurationFileFromRaw();
        if(!isFileExist(fileNameWithPath)) { 
        	fileNameWithPath = null; 
        }

        if (isUseAutoConfigProfileChecked) {
			if (profileDatabase.updateProfileFromDB()) {
				this.profile = profileDatabase.getProfile();
				Toast.makeText(this, "AutoConfig profile has been loaded.", Toast.LENGTH_LONG).show();
				handler.post(doConnectUsingProfile);
			}
			else {
				Toast.makeText(this, "No profile found. Please run AutoConfig first.", Toast.LENGTH_LONG).show();
			}
        } else {
	        /////////////////////////////////////////////////////////////////////////////////
			// Network operation is prohibited in the UI Thread if target API is 11 or above.
			// If target API is 11 or above, please use AsyncTask to avoid errors.
	        myUniMagReader.setXMLFileNameWithPath(fileNameWithPath);
	        myUniMagReader.loadingConfigurationXMLFile(true);
		    /////////////////////////////////////////////////////////////////////////////////
        }
        //Initializing SDKTool for firmware update
        firmwareUpdateTool = new uniMagSDKTools(this,this);
        firmwareUpdateTool.setUniMagReader(myUniMagReader);
        myUniMagReader.setSDKToolProxy(firmwareUpdateTool.getSDKToolProxy());
	}
	private String getConfigurationFileFromRaw( ){
		return getXMLFileFromRaw("idt_unimagcfg_default.xml");
		}
	private String getAutoConfigProfileFileFromRaw( ){
		//share the same copy with the configuration file
		return getXMLFileFromRaw("idt_unimagcfg_default.xml");
		}
	    
	// If 'idt_unimagcfg_default.xml' file is found in the 'raw' folder, it returns the file path.
	private String getXMLFileFromRaw(String fileName ){
		//the target filename in the application path
		String fileNameWithPath = null;
		fileNameWithPath = fileName;
	
		try {
			InputStream in = getResources().openRawResource(R.raw.idt_unimagcfg_default);
			int length = in.available();
			byte [] buffer = new byte[length];
			in.read(buffer);    	   
			in.close();
			deleteFile(fileNameWithPath);
			FileOutputStream fout = openFileOutput(fileNameWithPath, MODE_PRIVATE);
			fout.write(buffer);
			fout.close();
    	   
			// to refer to the application path
			File fileDir = this.getFilesDir();
			fileNameWithPath = fileDir.getParent() + java.io.File.separator + fileDir.getName();
			fileNameWithPath += java.io.File.separator+"idt_unimagcfg_default.xml";
	   	   
		} catch(Exception e){
			e.printStackTrace();
			fileNameWithPath = null;
		}
		return fileNameWithPath;
	}
	
    @Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
    	if ((keyCode == KeyEvent.KEYCODE_BACK||KeyEvent.KEYCODE_HOME==keyCode||KeyEvent.KEYCODE_SEARCH==keyCode)){
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}

	@Override
	public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
   	if ((keyCode == KeyEvent.KEYCODE_BACK||KeyEvent.KEYCODE_HOME==keyCode||KeyEvent.KEYCODE_SEARCH==keyCode)){
    		 
			return false;
		}	return super.onKeyMultiple(keyCode, repeatCount, event);
	}

	@Override
	public boolean onKeyUp(int keyCode, KeyEvent event) {
    	if ((keyCode == KeyEvent.KEYCODE_BACK||KeyEvent.KEYCODE_HOME==keyCode||KeyEvent.KEYCODE_SEARCH==keyCode)){
 			return false;
		}
    	return super.onKeyUp(keyCode, event);
	}

    @Override
	public boolean onOptionsItemSelected(MenuItem item) {
    	switch(item.getItemId())
	    {
       		// when the 'swipe card' menu item clicked
	    	case (START_SWIPE_CARD):
	    	{
	    		headerTextView.setText("MSR Data");
	    		textAreaTop.setText("");
	    	//	textAreaBottom.setText("");
        		//itemStartSC.setEnabled(false); 
        	
        		if(myUniMagReader!=null)
        			myUniMagReader.startSwipeCard();
	    		break;
	    	}
	    	// when the 'exit' menu item clicked
	    	case (EXIT_IDT_APP):
	    	{
	    		isExitButtonPressed = true;
        		if(myUniMagReader!=null)
        		{
        			myUniMagReader.unregisterListen();
        			myUniMagReader.stopSwipeCard();
        			myUniMagReader.release();
        		}
        		finish();
	    		break;
	    	}
	    	// If save log option is already enabled, put a check mark whenever settings menu is clicked.   
	    	case (SETTINGS_ITEM):
	    	{
	    		if(itemSubSaveLog!=null)
	    			itemSubSaveLog.setChecked(isSaveLogOptionChecked);
	    		if(itemSubUseAutoConfigProfile!=null)
	    			itemSubUseAutoConfigProfile.setChecked(isUseAutoConfigProfileChecked);
	    		if(itemSubUseCommandToConnect!=null)
	    			itemSubUseCommandToConnect.setChecked(isConnectWithCommand);
	    		break;
	    	}
	    	// deleting log files in the sd card.
	    	case (DELETE_LOG_ITEM):
	    	{
        		if(myUniMagReader!=null)
        			myUniMagReader.deleteLogs();
	    		break;		    		
	    	}
	    	// showing manufacturer, model number, SDK version, and OS Version information if clicked.
	    	case (ABOUT_ITEM):
	    	{
	    		showAboutInfo();
	    		break;		    		
	    	}
	    	// user can manually load a configuration file (xml), which should be located in the sd card.  
	    	case (SUB_LOAD_XML):
	    	{
	    		String strTmpFileName = getMyStorageFilePath();
	    		if (strTmpFileName == null)
	    		{
	    			headerTextView.setText("Warning");
	    			textAreaTop.setText("Please insert SD card.");
	    		//	textAreaBottom.setText("");
	    			return false;
	    		}
            	FileDialog fileDialog = new FileDialog();
            	Intent intent = new Intent( getBaseContext(), fileDialog.getClass());
				intent.putExtra(FileDialog.START_PATH, Environment.getExternalStorageDirectory().getPath());
				startActivityForResult(intent, REQUEST_GET_XML_FILE);
	    		break;
	    	}
	    	// in order to update firmware of reader, user needs to set a firmware file (.bin) first.
	    	// this menu allows to user to update firmware from v1.x to later version (v2.x or v3.x).
	    	case (SUB_LOAD_BIN):
	    	{
	    		headerTextView.setText("Command Info");

	    		String strTmpFileName = getMyStorageFilePath();
	    		if (strTmpFileName == null)
	    		{
	    			headerTextView.setText("Warning");
	    			textAreaTop.setText("Please insert SD card.");
	    		//	textAreaBottom.setText("");
	    			return false;
	    		}
            	FileDialog fileDialog = new FileDialog();
            	Intent intent = new Intent( getBaseContext(), fileDialog.getClass());
				intent.putExtra(FileDialog.START_PATH, Environment.getExternalStorageDirectory().getPath());
				startActivityForResult(intent, REQUEST_GET_BIN_FILE);
	    		break;
	    	}
	    	// Bin file should be encrypted in order to update from v2.x or v3.x to later version. 
	    	case (SUB_LOAD_ENCRYPTED_BIN):
	    	{
	    		headerTextView.setText("Command Info");

	    		String strTmpFileName = getMyStorageFilePath();
	    		if (strTmpFileName == null)
	    		{
	    			headerTextView.setText("Warning");
	    			textAreaTop.setText("Please insert SD card.");
	    		//	textAreaBottom.setText("");
	    			return false;
	    		}
            	FileDialog fileDialog = new FileDialog();
            	Intent intent = new Intent( getBaseContext(), fileDialog.getClass());
				intent.putExtra(FileDialog.START_PATH, Environment.getExternalStorageDirectory().getPath());
				startActivityForResult(intent, REQUEST_GET_ENCRYPTED_BIN_FILE);
	    		break;
	    	}
	    	case (SUB_START_AUTOCONFIG):
	    	{
	    		String fileNameWithPath = getAutoConfigProfileFileFromRaw();
    	        if(!isFileExist(fileNameWithPath)) {
    	        	fileNameWithPath = null; 
    	        }  
	    		boolean startAcRet = myUniMagReader.startAutoConfig(fileNameWithPath,true);
	    		if (startAcRet)
	    		{
    	    		strProgressInfo=null;
    	    		handler.post(doUpdateAutoConfigProgressInfo);
    	    		percent = 0;
    	    		beginTime = getCurrentTime();  
    	    		autoconfig_running = true;
	    		}
	    		break;
	    	}
	    	case (SUB_STOP_AUTOCONFIG):
	    	{
	    		if(autoconfig_running==true)
	    		{
		    		myUniMagReader.stopAutoConfig();
		    		myUniMagReader.unregisterListen();
		    		myUniMagReader.release();                       
	
		    		percent = 0;
		    		// Reinitialize the reader if AutoConfig has been stopped.
		    		initializeReader();
		    		autoconfig_running = false;
	    		}
	    		break;
	    	}  
	    	// when the 'save option' menu item clicked 
	    	case (SUB_SAVE_LOG_ITEM):
	    	{
	    		if(item.isChecked())
	    		{
	    			myUniMagReader.setSaveLogEnable(false);		   
	    			item.setChecked(false);
	    			isSaveLogOptionChecked = false;

	    		}
	    		else
	    		{
	    			//cannot enable the item when you are swiping the card.
	    			if(myUniMagReader.isSwipeCardRunning()==true)
	    			{
	    				item.setChecked(true);
	    				myUniMagReader.setSaveLogEnable(true);
	    				isSaveLogOptionChecked = true;
	    			}
	    		} 
	    		break;
	    	}
	    	
	    	case (SUB_USE_AUTOCONFIG_PROFILE):
	    	{
	    		if (!isReaderConnected) {
		    		if (item.isChecked()) {
		    			item.setChecked(false);
		    			isUseAutoConfigProfileChecked = false;
		    			
		    			profileDatabase.uncheckOnUseAutoConfigProfile();
		    			// change back to default profile
	    				initializeReader();
	    				
		    		} else {
		    			if (profileDatabase.updateProfileFromDB()) {
		    				this.profile = profileDatabase.getProfile();
		    				item.setChecked(true);
		    				isUseAutoConfigProfileChecked = true;
		    				profileDatabase.checkOnUseAutoConfigProfile();
		    			} else {
		    				AlertDialog.Builder builder = new AlertDialog.Builder(this);
		    				builder.setTitle("Warning");
		    				builder.setMessage("No profile found. Please run AutoConfig first.");
		    				builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
								@Override
								public void onClick(DialogInterface dialog, int which) {
								}
							});
		    				AlertDialog alert = builder.create();		
		    				alert.show();	 		    				
		    			}
		    		}
	    		} else {
    				AlertDialog.Builder builder = new AlertDialog.Builder(this);
    				builder.setTitle("Warning");
    				builder.setMessage("Please detach the reader in order to change a profile.");
    				builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});
    				AlertDialog alert = builder.create();		
    				alert.show();		    			
	    		}
	    		
	    		break;
	    	}
	    	case (SUB_USE_COMMAND_TO_CONNECT):
	    	{
	    		if (!isReaderConnected) {
	    			if (item.isChecked()) {
	    				isConnectWithCommand = false;
	    				initializeReader();
	    			} else {
//	    				isConnectWithCommand = true;
//	    				initializeReader();
	    				
	    				AlertDialog.Builder builder = new AlertDialog.Builder(this);
	    				builder.setTitle("Caution");
	    				builder.setMessage("Please note that older generation of UniMag Readers (UniMag & UniMag Pro) won't be connected if this option checked.");
	    				builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog, int which) {
							}
						});
	    				
	    				builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
							
							@Override
							public void onClick(DialogInterface dialog, int which) {
			    				isConnectWithCommand = true;
			    				initializeReader();
							}
						});
	    				AlertDialog alert = builder.create();		
	    				alert.show();
	    			}
	    		} else {
    				AlertDialog.Builder builder = new AlertDialog.Builder(this);
    				builder.setTitle("Information");
    				builder.setMessage("Please detach the reader in order to change the setting.");
    				builder.setNegativeButton("OK", new DialogInterface.OnClickListener() {
						@Override
						public void onClick(DialogInterface dialog, int which) {
						}
					});

    				AlertDialog alert = builder.create();		
    				alert.show();	    			
	    		}
	    		
		    	break;
	    	}
	    	// displays attached reader type
	    	case SUB_ATTACHED_TYPE:
	    		ReaderType art = myUniMagReader.getAttachedReaderType();
	    		if(art==ReaderType.UNKNOWN)
	    		{
		    		textAreaTop.setText("To get Attached Reader type, waiting for response.");
		    		//textAreaBottom.setText("");
 	    		}
	    		else
	    		{
		    		textAreaTop.setText("Attached Reader:\n   "+getReaderName(art));
		    	//	textAreaBottom.setText("");
	    		}
	    		break;
	    	// displays support status of all ID Tech readers  
	    	case SUB_SUPPORT_STATUS:
	    		//print a list of reader:supported status pairs
	    		textAreaTop.setText("Reader support status from cfg:\n");
	    		for (ReaderType rt : ReaderType.values()) {
	    			if (rt!=ReaderType.UNKNOWN && rt!=ReaderType.UM_OR_PRO)
	    				textAreaTop.append(getReaderName(rt)+" : "+myUniMagReader.getSupportStatus(rt)+"\n");
	    		}
	    		//textAreaBottom.setText("");
	    		break;
    	}
       	return super.onOptionsItemSelected(item);
	}

    @Override
	public boolean onCreateOptionsMenu(Menu menu) {
		itemStartSC = menu.add(0,START_SWIPE_CARD, Menu.NONE, "Swipe Card");
		itemStartSC.setEnabled(true); 
		sub = menu.addSubMenu(0,SETTINGS_ITEM,Menu.NONE,"Settings");
		itemSubSaveLog = sub.add(0,SUB_SAVE_LOG_ITEM,Menu.NONE,"Save Log option");
		itemSubUseAutoConfigProfile = sub.add(1, SUB_USE_AUTOCONFIG_PROFILE, Menu.NONE, "Use AutoConfig profile");
		itemSubUseCommandToConnect = sub.add(1, SUB_USE_COMMAND_TO_CONNECT, Menu.NONE, "Command to Connect");
		itemSubLoadXML = sub.add(1,SUB_LOAD_XML,Menu.NONE,"Reload XML");
		itemSubSetBinFile = sub.add(2,SUB_LOAD_BIN,Menu.NONE,"Set BIN file");
		itemSubSetEncryptedBinFile = sub.add(3,SUB_LOAD_ENCRYPTED_BIN,Menu.NONE,"Set Encrypted BIN file");
		
		itemSubStartAutoConfig = sub.add(4,SUB_START_AUTOCONFIG,Menu.NONE,"Start AutoConfig");
		itemSubStopAutoConfig = sub.add(6,SUB_STOP_AUTOCONFIG,Menu.NONE,"Stop AutoConfig");
		sub.add(Menu.NONE,SUB_ATTACHED_TYPE,Menu.NONE,"Get attached type");
		sub.add(Menu.NONE,SUB_SUPPORT_STATUS,Menu.NONE,"Get support status");
		itemSubSaveLog.setCheckable(true);
		itemSubUseAutoConfigProfile.setCheckable(true);
		itemSubUseCommandToConnect.setCheckable(true);
		itemSubLoadXML.setEnabled(true); 
		itemSubSetBinFile.setEnabled(true); 
		itemSubSetEncryptedBinFile.setEnabled(true); 
		
		itemSubStartAutoConfig.setEnabled(true); 
		itemSubStopAutoConfig.setEnabled(true); 
		itemDelLogs = menu.add(0,DELETE_LOG_ITEM,Menu.NONE,"Delete Logs");
		itemDelLogs.setEnabled(true); 
		itemAbout = menu.add(0,ABOUT_ITEM,Menu.NONE,"About");
		itemAbout.setEnabled(true); 
		itemExitApp = menu.add(0,EXIT_IDT_APP,Menu.NONE,"Exit");
		itemExitApp.setEnabled(true); 
		return super.onCreateOptionsMenu(menu);
	}

    // Returns reader name based on abbreviations 
    private String getReaderName(ReaderType rt){
    	switch(rt){
    	case UM:
    		return "UniMag";
    	case UM_PRO:
    		return "UniMag Pro";
    	case UM_II:
    		return "UniMag II";
    	case SHUTTLE:
    		return "Shuttle";
    	case UM_OR_PRO:
    		return "UniMag or UniMag Pro";
    	}
    	return "Unknown";
    	
    }
    //for uniMagReader.getAttachedReaderType()
    public ReaderType getAttachedReaderType(int uniMagUnit) {
    	switch (uniMagUnit) {
    	case StateList.uniMag2G3GPro:
    		return ReaderType.UM_OR_PRO;
    	case StateList.uniMagII:
    		return ReaderType.UM_II;
    	case StateList.uniMagShuttle:
    		return ReaderType.SHUTTLE;
    	case StateList.uniMagUnkown:
    	default:
    		return ReaderType.UNKNOWN;
    	}
    }
    private void showAboutInfo()
    {
		String strManufacture = myUniMagReader.getInfoManufacture();
		String strModel = myUniMagReader.getInfoModel();
		String strSDKVerInfo = myUniMagReader.getSDKVersionInfo();
		String strXMLVerInfo = myUniMagReader.getXMLVersionInfo();

		headerTextView.setText("SDK Info");
		//textAreaBottom.setText("");
		String strOSVerInfo = android.os.Build.VERSION.RELEASE;
    	textAreaTop.setText("Phone: "+strManufacture+"\n"+"Model: "+strModel+"\n"+"SDK Ver: "+strSDKVerInfo+"\nXML Ver: "+strXMLVerInfo+"\nOS Version: "+strOSVerInfo);

    }    
	private Runnable doShowTimeoutMsg = new Runnable()
	{
		@Override
		public void run()
		{
			if(itemStartSC!=null&&enableSwipeCard==true)
				itemStartSC.setEnabled(true); 
			enableSwipeCard = false;
			showDialog(popupDialogMsg);
		}

	};
	// shows messages on the popup dialog
	private void showDialog(String strTitle)
	{
		try
		{
	        AlertDialog.Builder builder = new AlertDialog.Builder(this);
	        builder.setTitle("UniMag");
	        builder.setMessage(strTitle);
	        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
	
	            @Override
				public void onClick(DialogInterface dialog, int which) {
	                dialog.dismiss();
	            }
	        });
	        builder.create().show();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	};	
 
	private Runnable doShowTopDlg = new Runnable()
	{
		@Override
		public void run()
		{
			showTopDialog(popupDialogMsg);
		}
	};	
	private Runnable doHideTopDlg = new Runnable()
	{
		@Override
		public void run()
		{
			hideTopDialog( );
		}

	};	
	private Runnable doShowSwipeTopDlg = new Runnable()
	{
		@Override
		public void run()
		{
			showSwipeTopDialog( );
		}
	};	
	private Runnable doShowYESNOTopDlg = new Runnable()
	{
		@Override
		public void run()
		{
			showYesNoDialog( );
		}
	};	
	private Runnable doHideSwipeTopDlg = new Runnable()
	{
		@Override
		public void run()
		{
			hideSwipeTopDialog( );
		}
	};	
	// displays result of commands, autoconfig, timeouts, firmware update progress and results.
	private Runnable doUpdateStatus = new Runnable()
	{
		@Override
		public void run()
		{
			try
			{
				textAreaTop.setText(statusText);
				headerTextView.setText("Command Info");
	    		if(msrData!=null)
	    		{
	            StringBuffer hexString = new StringBuffer();
	            
	            hexString.append("<");
	            String fix = null;
	            for (int i = 0; i < msrData.length; i++) {
	            	fix = Integer.toHexString(0xFF & msrData[i]);
	            	if(fix.length()==1)
	            		fix = "0"+fix;
	                hexString.append(fix);
	                if((i+1)%4==0&&i!=(msrData.length-1))
	                	hexString.append(' ');
	            }
	            hexString.append(">");
	         //   textAreaBottom.setText(hexString.toString());
	    		}
	    		else{
	    		//	textAreaBottom.setText("");
			}}
			catch(Exception ex)
			{
				ex.printStackTrace();
				
			}
		}
	};
	// displays result of commands, autoconfig, timeouts, firmware update progress and results.
	private Runnable doUpdateAutoConfigProgress = new Runnable()
	{
		@Override
		public void run()
		{
			try
			{
				textAreaTop.setText(statusText);
				headerTextView.setText("Command Info");
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				
			}
		}
	};
	String strProgressInfo = "";
	// displays result of commands, autoconfig, timeouts, firmware update progress and results.
	private Runnable doUpdateAutoConfigProgressInfo = new Runnable()
	{
		@Override
		public void run()
		{
			try
			{

	    		if(strProgressInfo!=null)
	    		{
	        //    textAreaBottom.setText(strProgressInfo);
	    		}
	    		else{
	    	//		textAreaBottom.setText("");
			}}
			catch(Exception ex)
			{
				ex.printStackTrace();
				
			}
		}
	};
	// displays result of get challenge command 
	private Runnable doUpdateChallengeData = new Runnable()
	{
		@Override
		public void run()
		{
			try
			{
				textAreaTop.setText(statusText);
				headerTextView.setText("Command Info");
	    		if(cmdGetChallenge_Succeed_WithChallengData==challengeResult)
	    		{
	    		//	textAreaBottom.setText("");
				//	textAreaBottom.setText( textAreaBottom.getText(), BufferType.EDITABLE);
			//		textAreaBottom.setEnabled(true);
			//		textAreaBottom.setClickable(true);
			//		textAreaBottom.setFocusable(true);
				}
	    		else if (cmdGetChallenge_Succeed_WithFileVersion==challengeResult)
	    		{
	    		//	textAreaBottom.setText("");
	    		//	textAreaBottom.setText( textAreaBottom.getText(), BufferType.EDITABLE);
	    		//	textAreaBottom.setEnabled(true);
	    		//	textAreaBottom.setClickable(true);
	    		//	textAreaBottom.setFocusable(true);
				}
	    		else{
	    		//	textAreaBottom.setText("");
			}}
			catch(Exception ex)
			{
				ex.printStackTrace();
				
			}
		}
	};
	// displays data from card swiping
	private Runnable doUpdateTVS = new Runnable()
	{
		@Override
		public void run()
		{
			try
			{
				CardData cd = new CardData(msrData);
				if(itemStartSC!=null)
					itemStartSC.setEnabled(true); 
				textAreaTop.setText(strMsrData);
				
				if(strMsrData.length()>0){
					int indexoftrack2start = strMsrData.indexOf(";");
					int indexoftrack2end = strMsrData.lastIndexOf("?");
					track2data = strMsrData.substring(indexoftrack2start+1, indexoftrack2end);
					int indexofequalto = track2data.indexOf("=");
					cardNumber = track2data.substring(0, indexofequalto);
					System.out.println("card number is:"+cardNumber);
					System.out.println("track2 data is:"+track2data);
				}
				if(paymenttypespinnerval.equals("Credit")){
					generateRandomNumber();
					generateRandomNumber1();
					creditRequestMethod();
				}else if(paymenttypespinnerval.equals("Debit")){
					generateRandomNumber();
					generateRandomNumber1();
					final AlertDialog alertDialog12 = new AlertDialog.Builder(
							uniMagIIDemo.this).create();
					LayoutInflater mInflater2 = LayoutInflater.from(uniMagIIDemo.this);
					View layout2 = mInflater2.inflate(R.layout.change_show1, null);
					alertDialog12.setTitle("Please enter PIN number");
					final EditText totalchange = (EditText) layout2
							.findViewById(R.id.changedit);
					
					Button okchange = (Button) layout2.findViewById(R.id.changeok);
					okchange.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							System.out.println("pindata val is:"+totalchange.getText().toString());
							alertDialog12.dismiss();
							debitRequestMethod();
						}
					});
					Button cancelchange = (Button) layout2.findViewById(R.id.changecancel);
					cancelchange.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							alertDialog12.dismiss();
						}
					});
					alertDialog12.setView(layout2);
					alertDialog12.getWindow().setSoftInputMode(
							WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
					alertDialog12.show();
					
				}else if(paymenttypespinnerval.equals("PLDebit")){
					//pldebitRequestMethod();
				}else if(paymenttypespinnerval.equals("EBT")){
					generateRandomNumber();
					generateRandomNumber1();
					final AlertDialog alertDialog12 = new AlertDialog.Builder(
							uniMagIIDemo.this).create();
					LayoutInflater mInflater2 = LayoutInflater.from(uniMagIIDemo.this);
					View layout2 = mInflater2.inflate(R.layout.change_show, null);
					alertDialog12.setTitle("Please enter PIN number");
					final EditText totalchange = (EditText) layout2
							.findViewById(R.id.changedit);
					
					Button okchange = (Button) layout2.findViewById(R.id.changeok);
					okchange.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							System.out.println("pindata val is:"+totalchange.getText().toString());
							/*try {
								pindata = encrypt(totalchange.getText().toString(), "MyPin");
								System.out.println("pindata encyrpted val is:"+pindata);
								pindata = pindata.substring(0, 16);
								System.out.println("decrypted val is:"+decrypt("MyPin", pindata));
							} catch (Exception e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}*/
							alertDialog12.dismiss();
							ebtRequestMethod();
						}
					});
					Button cancelchange = (Button) layout2.findViewById(R.id.changecancel);
					cancelchange.setOnClickListener(new OnClickListener() {
						@Override
						public void onClick(View v) {
							// TODO Auto-generated method stub
							alertDialog12.dismiss();
						}
					});
					alertDialog12.setView(layout2);
					alertDialog12.getWindow().setSoftInputMode(
							WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
					alertDialog12.show();
					
				}
				
	            StringBuffer hexString = new StringBuffer();
	            hexString.append("<");
	            String fix = null;
	            for (int i = 0; i < msrData.length; i++) {
	            	fix = Integer.toHexString(0xFF & msrData[i]);
	            	if(fix.length()==1)
	            		fix = "0"+fix;
	                hexString.append(fix);
	                if((i+1)%4==0&&i!=(msrData.length-1))
	                	hexString.append(' ');
	            }
	            hexString.append(">");
	          //  textAreaBottom.setText(hexString.toString()+"\n\n"+cd.toString());
				adjustTextView();
//				myUniMagReader.WriteLogIntoFile(hexString.toString());
				myUniMagReader.WriteLogIntoFile(cd.toString());				
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	};
	private void adjustTextView()
	{
	//	int height = (textAreaTop.getHeight()+ textAreaBottom.getHeight())/2;
	//	textAreaTop.setHeight(height);
	//	textAreaBottom.setHeight(height);
	}	
	// displays a connection status of UniMag reader
	private Runnable doUpdateTV = new Runnable()
	{
		@Override
		public void run()
		{
			if(!isReaderConnected)
				connectStatusTextView.setText("DISCONNECTED");
			else
				connectStatusTextView.setText("CONNECTED");
		}
	};
	private Runnable doUpdateToast = new Runnable()
	{
		@Override
		public void run()
		{
			try{
				Context context = getApplicationContext();
				String msg = null;//"To start record the mic.";
				if(isReaderConnected)
				{
					msg = "<<CONNECTED>>";	
					int duration = Toast.LENGTH_SHORT ;
					Toast.makeText(context, msg, duration).show();
					if(itemStartSC!=null)
						itemStartSC.setEnabled(true); 
				}
			}catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
	};
	private Runnable doConnectUsingProfile = new Runnable()
	{
		@Override
		public void run() {
			if (myUniMagReader != null)
			{
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				myUniMagReader.connectWithProfile(profile);
			}
		}
	};

	/***
	 * Class: UniMagTopDialog
	 * Author: Eric Yang
	 * Date: 2010.10.12
	 * Function: to show the dialog on the top of the desktop.
	 * 
	 * *****/
	private class UniMagTopDialog extends Dialog{

		public UniMagTopDialog(Context context) {
			super(context);
		}

	    @Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
	    	if ((keyCode == KeyEvent.KEYCODE_BACK||KeyEvent.KEYCODE_HOME==keyCode||KeyEvent.KEYCODE_SEARCH==keyCode)){
				return false;
			}
			return super.onKeyDown(keyCode, event);
		}

		@Override
		public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
	    	if ((keyCode == KeyEvent.KEYCODE_BACK||KeyEvent.KEYCODE_HOME==keyCode||KeyEvent.KEYCODE_SEARCH==keyCode)){
	    		 
				return false;
			}	return super.onKeyMultiple(keyCode, repeatCount, event);
		}

		@Override
		public boolean onKeyUp(int keyCode, KeyEvent event) {
	    	if ((keyCode == KeyEvent.KEYCODE_BACK||KeyEvent.KEYCODE_HOME==keyCode||KeyEvent.KEYCODE_SEARCH==keyCode)){
	 			return false;
			}
	    	return super.onKeyUp(keyCode, event);
		}
	}
	private class UniMagTopDialogYESNO extends Dialog{

		public UniMagTopDialogYESNO(Context context) {
			super(context);
		}

	    @Override
		public boolean onKeyDown(int keyCode, KeyEvent event) {
	    	if ((keyCode == KeyEvent.KEYCODE_BACK||KeyEvent.KEYCODE_HOME==keyCode||KeyEvent.KEYCODE_SEARCH==keyCode)){
				return false;
			}
			return super.onKeyDown(keyCode, event);
		}

		@Override
		public boolean onKeyMultiple(int keyCode, int repeatCount, KeyEvent event) {
	    	if ((keyCode == KeyEvent.KEYCODE_BACK||KeyEvent.KEYCODE_HOME==keyCode||KeyEvent.KEYCODE_SEARCH==keyCode)){
	    		 
				return false;
			}	return super.onKeyMultiple(keyCode, repeatCount, event);
		}

		@Override
		public boolean onKeyUp(int keyCode, KeyEvent event) {
	    	if ((keyCode == KeyEvent.KEYCODE_BACK||KeyEvent.KEYCODE_HOME==keyCode||KeyEvent.KEYCODE_SEARCH==keyCode)){
	 			return false;
			}
	    	return super.onKeyUp(keyCode, event);
		}
	}
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
	    
	    if (newConfig.orientation ==
	        Configuration.ORIENTATION_LANDSCAPE)
	    {
	    	//you can make sure if you would change it
	    }
	    if (newConfig.orientation ==
	        Configuration.ORIENTATION_PORTRAIT)
	    {
	    	//you can make sure if you would change it
	    }
	    if (newConfig.keyboardHidden ==
	        Configuration.KEYBOARDHIDDEN_NO)
	    {
	    	//you can make sure if you need change it
	    }
		super.onConfigurationChanged(newConfig);
	}

	private void showTopDialog(String strTitle)
	{
		hideTopDialog();
		if(dlgTopShow==null)
			dlgTopShow = new UniMagTopDialog(this);
		try
		{
			Window win = dlgTopShow.getWindow();
			win.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
			dlgTopShow.setTitle("UniMag");
			dlgTopShow.setContentView(R.layout.dlgtopview );
			TextView myTV = (TextView)dlgTopShow.findViewById(R.id.TView_Info);
			
			myTV.setText(popupDialogMsg);
			dlgTopShow.setOnKeyListener(new OnKeyListener(){
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {
					if ((keyCode == KeyEvent.KEYCODE_BACK)){
						return false;
					}
					return true;
				}
			});
	        dlgTopShow.show();
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			dlgTopShow = null;
		}
	};	
	private void hideTopDialog( )
	{
		if(dlgTopShow!=null)
		{
			try{
 			dlgTopShow.hide();
 			dlgTopShow.dismiss();
			}
			catch(Exception ex)
			{
			
				ex.printStackTrace();
			}
 			dlgTopShow = null;
		}
	};	
	
	private void showSwipeTopDialog( )
	{
		hideSwipeTopDialog();
		try{
			
			if(dlgSwipeTopShow==null)
				dlgSwipeTopShow = new UniMagTopDialog(this);
			
			Window win = dlgSwipeTopShow.getWindow();
			win.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
			dlgSwipeTopShow.setTitle("UniMag");
			dlgSwipeTopShow.setContentView(R.layout.dlgswipetopview );
			TextView myTV = (TextView)dlgSwipeTopShow.findViewById(R.id.TView_Info);
			Button myBtn = (Button)dlgSwipeTopShow.findViewById(R.id.btnCancel);
			
			myTV.setText(popupDialogMsg);
			myBtn.setOnClickListener(new Button.OnClickListener()
			{
				@Override
				public void onClick(View v) {
					if(itemStartSC!=null)
						itemStartSC.setEnabled(true); 
					//stop swipe
					myUniMagReader.stopSwipeCard();
					if (dlgSwipeTopShow != null) {
						statusText = "Swipe card cancelled.";
						msrData = null;
						handler.post(doUpdateStatus);
						dlgSwipeTopShow.dismiss();
					}
				}
			});
			dlgSwipeTopShow.setOnKeyListener(new OnKeyListener(){
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {
					if ((keyCode == KeyEvent.KEYCODE_BACK)){
						return false;
					}
					return true;
				}
			});
			dlgSwipeTopShow.show();	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	};	
	private void showYesNoDialog( )
	{
		hideSwipeTopDialog();
		try{
			
			if(dlgYESNOTopShow==null)
				dlgYESNOTopShow = new UniMagTopDialogYESNO(this);
			
			Window win = dlgYESNOTopShow.getWindow();
			win.setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND,WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
			dlgYESNOTopShow.setTitle("Warning");
			 
			dlgYESNOTopShow.setContentView(R.layout.dlgtopview2bnt );
			TextView myTV = (TextView)dlgYESNOTopShow.findViewById(R.id.TView_Info);
			myTV.setTextColor(0xFF0FF000);
			Button myBtnYES = (Button)dlgYESNOTopShow.findViewById(R.id.btnYes);
			Button myBtnNO = (Button)dlgYESNOTopShow.findViewById(R.id.btnNo);
			
		//	myTV.setText("Warrning, Now will Update Firmware if you press 'YES' to update, or 'No' to cancel");
			myTV.setText("Upgrading the firmware might cause the device to not work properly. \nAre you sure you want to continue? ");
			myBtnYES.setOnClickListener(new Button.OnClickListener()
			{
				@Override
				public void onClick(View v) {
					updateFirmware_exTools();
					dlgYESNOTopShow.dismiss();
				}
			});
			myBtnNO.setOnClickListener(new Button.OnClickListener()
			{
				@Override
				public void onClick(View v) {
					dlgYESNOTopShow.dismiss();
				}
			});
			dlgYESNOTopShow.setOnKeyListener(new OnKeyListener(){
				@Override
				public boolean onKey(DialogInterface dialog, int keyCode,
						KeyEvent event) {
					if ((keyCode == KeyEvent.KEYCODE_BACK)){
						return false;
					}
					return true;
				}
			});
			dlgYESNOTopShow.show();	 
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	};	
	private void hideSwipeTopDialog( )
	{
		try
		{
			if(dlgSwipeTopShow!=null)
			{
				dlgSwipeTopShow.hide();
				dlgSwipeTopShow.dismiss();
				dlgSwipeTopShow = null;	
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
	};

	// implementing a method onReceiveMsgCardData, defined in uniMagReaderMsg interface
	// receiving card data here
	@Override
	public void onReceiveMsgCardData(byte flagOfCardData,byte[] cardData) {
		byte flag = (byte) (flagOfCardData&0x04);
//		Log.d("Demo Info >>>>> onReceive flagOfCardData="+flagOfCardData,"CardData="+ getHexStringFromBytes(cardData));

		if(flag==0x00){
			strMsrData = new String (cardData);
			cardtotaldata = strMsrData;
			
		}
		if(flag==0x04)
		{
			//You need to decrypt the data here first.
			strMsrData = new String (cardData);
			cardtotaldata = strMsrData;
		}
		
		msrData = new byte[cardData.length];
		System.arraycopy(cardData, 0, msrData, 0, cardData.length);
		enableSwipeCard = true;
		handler.post(doHideTopDlg);
		handler.post(doHideSwipeTopDlg);
		handler.post(doUpdateTVS);
	}
	
	// implementing a method onReceiveMsgConnected, defined in uniMagReaderMsg interface
	// receiving a message that the uniMag device has been connected	
	@Override
	public void onReceiveMsgConnected() {
		
		isReaderConnected = true;
		if(percent==0)
		{
			if(profile!=null)
			{
				if(profile.getModelNumber().length()>0)
					statusText = "Now the UniMag Unit is connected.("+getTimeInfoMs(beginTime)+"s, with profile "+profile.getModelNumber()+")";
				else statusText = "Now the UniMag Unit is connected.("+getTimeInfoMs(beginTime)+"s)";
			}
			else
				statusText = "Now the UniMag Unit is connected."+" ("+getTimeInfoMs(beginTime)+"s)";
		}
		else
		{
			if(profile!=null)
				statusText = "Now the UniMag Unit is connected.("+getTimeInfoMs(beginTime)+"s, "+"Profile found at "+percent +"% named "+profile.getModelNumber()+",auto config last " +getTimeInfoMs(beginTimeOfAutoConfig)+"s)";
			else
				statusText = "Now the UniMag Unit is connected."+" ("+getTimeInfoMs(beginTime)+"s, "+"Profile found at "+percent +"%,auto config last " +getTimeInfoMs(beginTimeOfAutoConfig)+"s)";
			percent = 0;
		}		
		handler.post(doHideTopDlg);
		handler.post(doHideSwipeTopDlg);
		handler.post(doUpdateTV);
		handler.post(doUpdateToast);
		msrData = null;
		handler.post(doUpdateStatus);	
		handler.post(doUpdateAutoConfigProgressInfo);

	}

	// implementing a method onReceiveMsgDisconnected, defined in uniMagReaderMsg interface
	// receiving a message that the uniMag device has been disconnected		
	@Override
	public void onReceiveMsgDisconnected() {
		percent=0;
		strProgressInfo=null;
		isReaderConnected = false;
		isWaitingForCommandResult=false;
		autoconfig_running=false;
		handler.post(doHideTopDlg);
		handler.post(doHideSwipeTopDlg);
		handler.post(doUpdateTV);
		showAboutInfo();
	}
	// implementing a method onReceiveMsgTimeout, defined in uniMagReaderMsg inteface
	// receiving a timeout message for powering up or card swipe		
	@Override
	public void onReceiveMsgTimeout(String strTimeoutMsg) {
		isWaitingForCommandResult=false;
		enableSwipeCard = true;
		handler.post(doHideTopDlg);
		handler.post(doHideSwipeTopDlg);
		statusText = strTimeoutMsg+"("+getTimeInfo(beginTime)+")";
		msrData = null;
		handler.post(doUpdateStatus);
	}
	// implementing a method onReceiveMsgToConnect, defined in uniMagReaderMsg interface
	// receiving a message when SDK starts powering up the UniMag device		
	@Override
	public void onReceiveMsgToConnect(){ 
		beginTime = System.currentTimeMillis();
		handler.post(doHideTopDlg);
		handler.post(doHideSwipeTopDlg);
		popupDialogMsg = "Powering up uniMag...";
		handler.post(doShowTopDlg);
	}
	// implementing a method onReceiveMsgToSwipeCard, defined in uniMagReaderMsg interface
	// receiving a message when SDK starts recording, then application should ask user to swipe a card		
	@Override
	public void onReceiveMsgToSwipeCard() {
		handler.post(doHideTopDlg);
		handler.post(doHideSwipeTopDlg);
		popupDialogMsg = "Please swipe card.";
		handler.post(doShowSwipeTopDlg);
	}
	// implementing a method onReceiveMsgProcessingCardData, defined in uniMagReaderMsg interface
	// receiving a message when SDK detects data coming from the UniMag reader
	// The main purpose is to give early notification to user to wait until SDK finishes processing card data.
	@Override
	public void onReceiveMsgProcessingCardData() {
		statusText = "Card data is being processed. Please wait.";
		msrData = null;
		handler.post(doUpdateStatus);
	}
	// this method has been depricated, and will not be called in this version of SDK. 
	@Override
	public void onReceiveMsgSDCardDFailed(String strSDCardFailed)
	{
		popupDialogMsg = strSDCardFailed;
		handler.post(doHideTopDlg);
		handler.post(doHideSwipeTopDlg);		
		handler.post(doShowTimeoutMsg);
	}
	// Setting a permission for user	
	@Override
	public boolean getUserGrant(int type, String strMessage) {
		Log.d("Demo Info >>>>> getUserGrant:",strMessage);
		boolean getUserGranted = false;
		switch(type)
		{
		case uniMagReaderMsg.typeToPowerupUniMag:
			//pop up dialog to get the user grant
			getUserGranted = true;
			break;
		case uniMagReaderMsg.typeToUpdateXML:
			//pop up dialog to get the user grant
			getUserGranted = true;
			break;
		case uniMagReaderMsg.typeToOverwriteXML:
			//pop up dialog to get the user grant
			getUserGranted = true;
			break;
		case uniMagReaderMsg.typeToReportToIdtech:
			//pop up dialog to get the user grant
			getUserGranted = true;
			break;
		default:
			getUserGranted = false;
			break;
		}
		return getUserGranted;
	}
	// implementing a method onReceiveMsgFailureInfo, defined in uniMagReaderMsg interface
	// receiving a message when SDK could not find a profile of the phone	
	@Override
	public void onReceiveMsgFailureInfo(int index, String strMessage) {
		isWaitingForCommandResult = false;
		
		// If AutoConfig found a profile before and saved into db, then retreive it and connect.
		if (profileDatabase.updateProfileFromDB()) {
			this.profile = profileDatabase.getProfile();
    		showAboutInfo();
			handler.post(doConnectUsingProfile);
		} else {
			statusText = "Failure index: "+index+", message: "+strMessage;
			msrData = null;
			handler.post(doUpdateStatus);
		}
		//Cannot support current phone in the XML file.
		//start to Auto Config the parameters
		if(myUniMagReader.startAutoConfig(false)==true)
		{
			beginTime = getCurrentTime();
		}
	}
	// implementing a method onReceiveMsgCommandResult, defined in uniMagReaderMsg interface
	// receiving a message when SDK is able to parse a response for commands from the reader
	@Override
	public void onReceiveMsgCommandResult(int commandID, byte[] cmdReturn) {
		Log.d("Demo Info >>>>> onReceive commandID="+commandID,",cmdReturn="+ getHexStringFromBytes(cmdReturn));
		isWaitingForCommandResult = false;
		
		if (cmdReturn.length > 1){
			if (6==cmdReturn[0]&&(byte)0x56==cmdReturn[1])
			{
				statusText = "Failed to send command. Attached reader is in boot loader mode. Format:<"+getHexStringFromBytes(cmdReturn)+">";
				handler.post(doUpdateStatus);
				return;
			}
		}
		
		switch(commandID)
		{
		case uniMagReaderMsg.cmdGetNextKSN:
			if(0==cmdReturn[0])
				statusText = "Get Next KSN timeout.";
			else if(6==cmdReturn[0])
				statusText = "Get Next KSN Succeed.";
			else
				statusText = "Get Next KSN failed.";
			break;
		case uniMagReaderMsg.cmdEnableAES:
			if(0==cmdReturn[0])
				statusText = "Turn on AES timeout.";
			else if(6==cmdReturn[0])
				statusText = "Turn on AES Succeed.";
			else
				statusText = "Turn on AES failed.";
			break;
		case uniMagReaderMsg.cmdEnableTDES:
			if(0==cmdReturn[0])
				statusText = "Turn on TDES timeout.";
			else if(6==cmdReturn[0])
				statusText = "Turn on TDES Succeed.";
			else
				statusText = "Turn on TDES failed.";
			break;
		case uniMagReaderMsg.cmdGetVersion:
			if(0==cmdReturn[0])
				statusText = "Get Version timeout.";
			else if(6==cmdReturn[0]&&2==cmdReturn[1]&&3==cmdReturn[cmdReturn.length-2])
			{
				statusText = null;
				byte cmdDataX[]  = new byte[cmdReturn.length-4];
				System.arraycopy(cmdReturn, 2, cmdDataX, 0, cmdReturn.length-4);
				statusText = "Get Version:"+new String(cmdDataX);
			}
			else
			{
				statusText = "Get Version failed, Error Format:<"+ getHexStringFromBytes(cmdReturn)+">";
			}
			break;
		case uniMagReaderMsg.cmdGetSerialNumber:
			if(0==cmdReturn[0])
				statusText = "Get Serial Number timeout.";
			else if(6==cmdReturn[0]&&2==cmdReturn[1]&&3==cmdReturn[cmdReturn.length-2])
			{
				statusText = null;
				byte cmdDataX[]  = new byte[cmdReturn.length-4];
				System.arraycopy(cmdReturn, 2, cmdDataX, 0, cmdReturn.length-4);
				statusText = "Get Serial Number:"+new String(cmdDataX);
			}
			else
			{
				statusText = "Get Serial Number failed, Error Format:<"+ getHexStringFromBytes(cmdReturn)+">";
			}
			break;
		case uniMagReaderMsg.cmdGetAttachedReaderType:
			int readerType = cmdReturn[0];
			ReaderType art = getAttachedReaderType(readerType);
			statusText = "Attached Reader:\n   "+getReaderName(art) ;
			msrData = null;
			handler.post(doUpdateStatus);
			return;
		
		case uniMagReaderMsg.cmdGetSettings:
			if(0==cmdReturn[0])
				statusText = "Get Setting timeout.";
			else if(6==cmdReturn[0]&&2==cmdReturn[1]&&3==cmdReturn[cmdReturn.length-2])
			{
				byte cmdDataX[]  = new byte[cmdReturn.length-4];
				System.arraycopy(cmdReturn, 2, cmdDataX, 0, cmdReturn.length-4);
				statusText = "Get Setting:"+ getHexStringFromBytes(cmdDataX);
				cmdDataX=null;
			}
			else
			{
				statusText = "Get Setting failed, Error Format:<"+ getHexStringFromBytes(cmdReturn)+">";
			}
			break;
		case uniMagReaderMsg.cmdClearBuffer :
			if(0==cmdReturn[0])
				statusText = "Clear Buffer timeout.";
			else if(6==cmdReturn[0] )
				statusText = "Clear Buffer Succeed.";
			else if(21==cmdReturn[0])
				statusText = "Clear Buffer failed.";
			else
			{
				statusText = "Clear Buffer, Error Format:<"+ getHexStringFromBytes(cmdReturn)+">";
			}
			break;

		default:
			break;
		}
		msrData = null;
		msrData = new byte[cmdReturn.length];
		System.arraycopy(cmdReturn, 0, msrData, 0, cmdReturn.length);
		handler.post(doUpdateStatus);
	}
	// implementing a method onReceiveMsgChallengeResult, defined in uniMagReaderToolsMsg interface
	// receiving a message when SDK is able to parse a response for get challenge command from the reader
	@Override
	public void onReceiveMsgChallengeResult(int returnCode,byte[] data) {
		isWaitingForCommandResult = false;
		switch(returnCode)
		{
		case uniMagReaderToolsMsg.cmdGetChallenge_Succeed_WithChallengData:
			challengeResult = cmdGetChallenge_Succeed_WithChallengData;
			//show the challenge data and enable edit the hex text view
			if(6==data[0]&&2==data[1]&&3==data[data.length-2])
			{
				statusText = null;
				byte cmdChallengeData[]  = new byte[8];
				System.arraycopy(data, 2, cmdChallengeData, 0, 8);
				byte cmdChallengeData_encyption[]  = new byte[8];
				System.arraycopy(data, 2, cmdChallengeData_encyption, 0, 8);
				
				byte cmdChallengeData_KSN[]  = new byte[10];
				System.arraycopy(data, 10, cmdChallengeData_KSN, 0, 10);
				statusText = "Challenge Data:<"+ 
							getHexStringFromBytes(cmdChallengeData)+"> "+"\n"+"KSN:<"+  
							getHexStringFromBytes(cmdChallengeData_KSN)+">"+"\n"+
							"please enter "+firmwareUpdateTool.getRequiredChallengeResponseLength()+"-byte challenge response below, as hex, then update firmware.";
			} 
			else {
				statusText = "Get Challenge failed, Error Format:<"+ getHexStringFromBytes(data)+">";
			}

			break;
		case uniMagReaderToolsMsg.cmdGetChallenge_Succeed_WithFileVersion:
			challengeResult = cmdGetChallenge_Succeed_WithFileVersion;
			if(6==data[0]&&((byte)0x56)==data[1] )
			{
				statusText = null;
				byte cmdFileVersion[]  = new byte[2];
				System.arraycopy(data, 2, cmdFileVersion, 0, 2);
				char fileVersionHigh=(char) cmdFileVersion[0];
				char fileVersionLow=(char) cmdFileVersion[1];
				
				statusText = "Already in boot load mode, and the file version is "+fileVersionHigh+"."+fileVersionLow+"\n" +
								"Please update firmware directly.";
			} else
			{
				statusText = "Get Challenge failed, Error Format:<"+ getHexStringFromBytes(data)+">";
			}

			break;
		case uniMagReaderToolsMsg.cmdGetChallenge_Failed:
			statusText = "Get Challenge failed, please try again.";

			break;
		case uniMagReaderToolsMsg.cmdGetChallenge_NeedSetBinFile:
			statusText = "Get Challenge failed, need to set BIN file first.";
			break;
		case uniMagReaderToolsMsg.cmdGetChallenge_Timeout:
			statusText = "Get Challenge timeout.";
			break;
		default:
			break;
		}
		msrData = null;
		handler.post(doUpdateChallengeData);
 		
	}
	// implementing a method onReceiveMsgUpdateFirmwareProgress, defined in uniMagReaderToolsMsg interface
	// receiving a message of firmware update progress	
	@Override
	public void onReceiveMsgUpdateFirmwareProgress(int progressValue) {
		Log.d("Demo Info >>>>> UpdateFirmwareProgress" ,"v = "+progressValue);
		statusText = "Updating firmware, "+progressValue+"% finished.";
		msrData = null;
		handler.post(doUpdateStatus);
		
	}
	// implementing a method onReceiveMsgUpdateFirmwareResult, defined in uniMagReaderToolsMsg interface
	// receiving a message when firmware update has been finished	
	@Override
	public void onReceiveMsgUpdateFirmwareResult(int result) {
		isWaitingForCommandResult = false;		

		switch(result)
		{
		case uniMagReaderToolsMsg.cmdUpdateFirmware_Succeed:
			statusText = "Update firmware succeed.";
			break;
		case uniMagReaderToolsMsg.cmdUpdateFirmware_NeedSetBinFile:
			statusText = "Update firmware failed, need to set BIN file first";
			break;
		case uniMagReaderToolsMsg.cmdUpdateFirmware_NeedGetChallenge:
			statusText = "Update firmware failed, need to get challenge first.";
			break;
		case uniMagReaderToolsMsg.cmdUpdateFirmware_Need8BytesData:
			statusText = "Update firmware failed, need input 8 bytes data.";
			break;
		case uniMagReaderToolsMsg.cmdUpdateFirmware_Need24BytesData:
			statusText = "Update firmware failed, need input 24 bytes data.";
			break;
		case uniMagReaderToolsMsg.cmdUpdateFirmware_EnterBootloadModeFailed:
			statusText = "Update firmware failed, cannot enter boot load mode.";
			break;
		case uniMagReaderToolsMsg.cmdUpdateFirmware_DownloadBlockFailed:
			statusText = "Update firmware failed, cannot download block data.";
			break;
		case uniMagReaderToolsMsg.cmdUpdateFirmware_EndDownloadBlockFailed:
			statusText = "Update firmware failed, cannot end download block.";
			break;
		case uniMagReaderToolsMsg.cmdUpdateFirmware_Timeout:
			statusText = "Update firmware timeout.";
			break;
		}
		Log.d("Demo Info >>>>> UpdateFirmwareResult" ,"v = "+result);
		msrData = null;
		handler.post(doUpdateStatus);
			
	}
	// implementing a method onReceiveMsgAutoConfigProgress, defined in uniMagReaderMsg interface
	// receiving a message of Auto Config progress	
	@Override
	public void onReceiveMsgAutoConfigProgress(int progressValue) {
		Log.d("Demo Info >>>>> AutoConfigProgress" ,"v = "+progressValue);
		percent = progressValue;
		statusText = "Searching the configuration automatically, "+progressValue+"% finished."+"("+getTimeInfo(beginTime)+")";
		msrData = null;
		beginTimeOfAutoConfig = beginTime;
		handler.post(doUpdateAutoConfigProgress);
	}
	@Override
	public void onReceiveMsgAutoConfigProgress(int percent, double result,
			String profileName) {
		if(strProgressInfo==null)
			strProgressInfo="("+profileName+ ") <"+percent+"%>,Result="+Common.getDoubleValue(result);
		else
			strProgressInfo+="\n("+profileName+ ") <"+percent+"%>,Result="+Common.getDoubleValue(result);
    	Log.d("**__@__**","demo = "+strProgressInfo);
		handler.post(doUpdateAutoConfigProgressInfo);
	}

	@Override
	public void onReceiveMsgAutoConfigCompleted(StructConfigParameters profile) {
		Log.d("Demo Info >>>>> AutoConfigCompleted" ,"A profile has been found, trying to connect...");
		autoconfig_running = false;
		beginTimeOfAutoConfig = beginTime;
		this.profile = profile;
		profileDatabase.setProfile(profile);
		profileDatabase.insertResultIntoDB();
		handler.post(doConnectUsingProfile);
	}

	public void getChallenge()
	{
		getChallenge_exTools();
	}
	
	public void updateFirmware()
	{
		if (isReaderConnected)
			handler.post(doShowYESNOTopDlg);
		else 
			Toast.makeText(this, "Please connect a reader first.", Toast.LENGTH_SHORT).show();
	}
	
	private void getChallenge_exTools()
	{
		if (firmwareUpdateTool != null)
		{
			if (firmwareUpdateTool.getChallenge() == true)
			{
				isWaitingForCommandResult = true;
				// show to get challenge
				statusText = " To Get Challenge, waiting for response.";
				msrData = null;
				handler.post(doUpdateStatus);
			}
		}
	}	
	
	private void updateFirmware_exTools()
	{
		if (firmwareUpdateTool != null)
		{
			/*String strData = textAreaBottom.getText().toString();
			
			if(strData.length()>0)
			{
				challengeResponse = getBytesFromHexString(strData);
				if(challengeResponse==null)
				{
					statusText = "Invalidate challenge data, please input hex data.";
					msrData = null;
					handler.post(doUpdateStatus);				
					return;
				}
			}
			else
				challengeResponse=null;
*/
			isWaitingForCommandResult = true;
			if (firmwareUpdateTool.updateFirmware(challengeResponse) == true)
			{
				statusText = " To Update Firmware, waiting for response.";
				msrData = null;
				handler.post(doUpdateStatus);				
			}
		}
	}	
	
	public void prepareToSendCommand(int cmdID)
	{
		isWaitingForCommandResult = true;
		switch(cmdID)
		{
		case uniMagReaderMsg.cmdGetNextKSN:
			statusText = " To Get Next KSN, wait for response.";
			break;
		case uniMagReaderMsg.cmdEnableAES:
			statusText = " To Turn on AES, wait for response.";
			break;
		case uniMagReaderMsg.cmdEnableTDES:
			statusText = " To Turn on TDES, wait for response.";
			break;
		case uniMagReaderMsg.cmdGetVersion:
			statusText = " To Get Version, wait for response.";
			break;
		case uniMagReaderMsg.cmdGetSettings:
			statusText = " To Get Setting, wait for response.";
			break;
		case uniMagReaderMsg.cmdGetSerialNumber:
			statusText = " To Get Serial Number, wait for response.";
			break;
		case uniMagReaderMsg.cmdClearBuffer:
			statusText = " To Clear Buffer, wait for response.";
			break;
		default:
			break;
		}
		msrData = null;
		handler.post(doUpdateStatus);
	}
	
	private String getHexStringFromBytes(byte []data)
    {
		if(data.length<=0) 
			return null;
		StringBuffer hexString = new StringBuffer();
		String fix = null;
		for (int i = 0; i < data.length; i++) {
			fix = Integer.toHexString(0xFF & data[i]);
			if(fix.length()==1)
				fix = "0"+fix;
			hexString.append(fix);
		}
		fix = null;
		fix = hexString.toString();
		return fix;
    }
	
    public byte[] getBytesFromHexString(String strHexData)
	{
	    if (1==strHexData.length()%2) {
	    	return null;
	    }
	    byte[] bytes = new byte[strHexData.length()/2];
	    try{
		    for (int i=0;i<strHexData.length()/2;i++) {
		    	bytes[i] = (byte) Integer.parseInt(strHexData.substring(i*2, (i+1)*2) , 16);
		    }
	    }
	    catch(Exception ex)
	    {
	    	ex.printStackTrace();
	    	return null;
	    }
	    return bytes;
	}
    
	static private String getMyStorageFilePath( ) {
		String path = null;
		if(isStorageExist())
			path = Environment.getExternalStorageDirectory().toString();
		return path;
	}
	
	private boolean isFileExist(String path) {
    	if(path==null)
    		return false;
	    File file = new File(path);
	    if (!file.exists()) {
	      return false ;
	    }
	    return true;
    }
	
	static private boolean isStorageExist() {
		//if the SD card exists
		boolean sdCardExist = Environment.getExternalStorageState().equals(android.os.Environment.MEDIA_MOUNTED);
		return sdCardExist;
	}
	
	private long getCurrentTime(){
		return System.currentTimeMillis();
	}
	
	private String getTimeInfo(long timeBase){
		int time = (int)(getCurrentTime()-timeBase)/1000;
		int hour = time/3600;
		int min = time/60;
		int sec= time%60;
		return  hour+":"+min+":"+sec;
	}
	
	private String getTimeInfoMs(long timeBase){
		float time = (float)(getCurrentTime()-timeBase)/1000;
		String strtime = String.format("%03f",time);
		return  strtime;
	}
	
	public String getcardtype(){
		if(cardNumber.startsWith("4") && (cardNumber.length()==13 || cardNumber.length()==16)){
			return "Visa";
		}else if((cardNumber.startsWith("34") || cardNumber.startsWith("35") || cardNumber.startsWith("36") 
				|| cardNumber.startsWith("37")) && cardNumber.length()==15){
			return "Amex";
		}else if((cardNumber.startsWith("51") || cardNumber.startsWith("52") || cardNumber.startsWith("53") 
				|| cardNumber.startsWith("54") || cardNumber.startsWith("55")) && cardNumber.length()==16){
			return "Mastercard";
		}else if(cardNumber.startsWith("6011") && cardNumber.length()==16){
			return "Discover";
		}else if(cardNumber.startsWith("35") && cardNumber.length()==16){
			return "JCB";
		}else if((cardNumber.startsWith("36") || cardNumber.startsWith("30") || cardNumber.startsWith("36")) && (cardNumber.length()==14 || cardNumber.length()==16)){
			return "Diners";
		}else{
			return "";
		}
	}
	
	public  String encrypt(String seed, String cleartext) throws Exception {
        byte[] rawKey = getRawKey(seed.getBytes());
        System.out.println("encrypt key of the given string is:"+rawKey.toString());
        keyserialnumber = rawKey.toString();
        byte[] result = encrypt(rawKey, cleartext.getBytes());
        return toHex(result);
	}
	
	public  String decrypt(String seed, String encrypted) throws Exception {
        byte[] rawKey = getRawKey(seed.getBytes());
        byte[] enc = toByte(encrypted);
        byte[] result = decrypt(rawKey, enc);
        return new String(result);
	}
	
	private  byte[] getRawKey(byte[] seed) throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("AES");
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        sr.setSeed(seed);
        kgen.init(128, sr); // 192 and 256 bits may not be available
        SecretKey skey = kgen.generateKey();
        byte[] raw = skey.getEncoded();
        return raw;
	}
	
	private  byte[] encrypt(byte[] raw, byte[] clear) throws Exception {
	    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
	        Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
	    byte[] encrypted = cipher.doFinal(clear);
	        return encrypted;
	}

	private  byte[] decrypt(byte[] raw, byte[] encrypted) throws Exception {
	    SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
	        Cipher cipher = Cipher.getInstance("AES");
	    cipher.init(Cipher.DECRYPT_MODE, skeySpec);
	    byte[] decrypted = cipher.doFinal(encrypted);
	        return decrypted;
	}
	
	public  String toHex(String txt) {
        return toHex(txt.getBytes());
	}
	public  String fromHex(String hex) {
        return new String(toByte(hex));
	}

	public  byte[] toByte(String hexString) {
        int len = hexString.length()/2;
        byte[] result = new byte[len];
        for (int i = 0; i < len; i++)
                result[i] = Integer.valueOf(hexString.substring(2*i, 2*i+2), 16).byteValue();
        return result;
	}

	public  String toHex(byte[] buf) {
        if (buf == null)
                return "";
        StringBuffer result = new StringBuffer(2*buf.length);
        for (int i = 0; i < buf.length; i++) {
                appendHex(result, buf[i]);
        }
        return result.toString();
	}
	
	private  void appendHex(StringBuffer sb, byte b) {
        sb.append(HEX.charAt((b>>4)&0x0f)).append(HEX.charAt(b&0x0f));
	}
	
	public String getVisaGroup(){
		String ebtString = "&lt;VisaGrp&gt;"+
				   "&lt;ACI&gt;Y&lt;/ACI&gt;"+
				   "&lt;/VisaGrp&gt;";
		return ebtString;
	}
	
	public void creditRequestMethod(){
		String gmftagstarting = getGMFTagData();
		String gmftagending = getGMFEndTagData();
		String creditrequeststarting = getCreditRequestTagData();
		String creditrequestending = getCreditRequestEndTagData();
		String commongroup = getCommonGroupXmlData(Parameters.PaymntTypeCREDIT, Parameters.TranTypeSale, 
												   Parameters.posentrymode_part1_Magnetic_Stripe_Track_Read, 
												   Parameters.posentrymode_part2_No_PIN_entry_capability, 
												   Parameters.poscondcode_Cardholder_Present_Card_Present, 
												   Parameters.termcatcode_Electronic_Payment_Terminal, 
												   Parameters.termentrycapablt_Magnetic_stripe_key_entry_and_chip, 
												   Parameters.termlocind_On_Premises, Parameters.cardcaptcap_terminal_has_card_capture_capability);
		String cardtype = getcardtype();
		String cardgroup = getCardGroupXmlDataforCredit(track2data, cardtype);
		String addtlgroup = getAddtlAmtGrpXmlData(Parameters.PartAuthrztnApprvlCapablt_partial_authorization);
		
		if(cardtype.equals("Visa")){
			String visagroupval = getVisaGroup();
			requestformatpayloadcontent = gmftagstarting
					  +creditrequeststarting
					  +commongroup
					  +cardgroup
					  +addtlgroup
					  +visagroupval
					  +creditrequestending
					  +gmftagending;
		}else{
			requestformatpayloadcontent = gmftagstarting
					  +creditrequeststarting
					  +commongroup
					  +cardgroup
					  +addtlgroup
					  +creditrequestending
					  +gmftagending;
		}
		System.out.println(requestformatpayloadcontent);
		String requeststarting = getrequestformatstarting(Parameters.DID, Parameters.App, Parameters.Auth, Parameters.ClentRef, Parameters.ServiceId);
		String requestending = getrequestformatending();
		request = requeststarting
				  +requestformatpayloadcontent
				  +requestending;
		System.out.println("request val is:"+request);
		sendingRequest(request);
	}
	
	public void debitRequestMethod(){
		String gmftagstarting = getGMFTagData();
		String gmftagending = getGMFEndTagData();
		String creditrequeststarting = getDebitRequestTagData();
		String creditrequestending = getDebitRequestEndTagData();
		String commongroup = getCommonGroupXmlData(Parameters.PaymntTypeDEBIT, Parameters.TranTypeSale, 
												   Parameters.posentrymode_part1_Magnetic_Stripe_Track_Read, 
												   Parameters.posentrymode_part2_PIN_entry_capability, 
												   Parameters.poscondcode_Cardholder_Present_Card_Present, 
												   Parameters.termcatcode_Electronic_Payment_Terminal, 
												   Parameters.termentrycapablt_Magnetic_stripe_key_entry_and_chip, 
												   Parameters.termlocind_On_Premises, Parameters.cardcaptcap_terminal_has_card_capture_capability);
		String cardgroup = getCardGroupXmlData(track2data);
		String addtlgroup = getAddtlAmtGrpXmlData(Parameters.PartAuthrztnApprvlCapablt_partial_authorization);
		String pingroup = getPinGroupXmlData(pindata, keyserialnumber);
		
		requestformatpayloadcontent = gmftagstarting
									  +creditrequeststarting
									  +commongroup
									  +cardgroup
									  +pingroup
									  +addtlgroup
									  +creditrequestending
									  +gmftagending;
		System.out.println(requestformatpayloadcontent);
		String requeststarting = getrequestformatstarting(Parameters.DID, Parameters.App, Parameters.Auth, Parameters.ClentRef, Parameters.ServiceId);
		String requestending = getrequestformatending();
		request = requeststarting
				  +requestformatpayloadcontent
				  +requestending;
		System.out.println("request val is:"+request);
		sendingRequest(request);
	}
	
	public void ebtRequestMethod(){
		String gmftagstarting = getGMFTagData();
		String gmftagending = getGMFEndTagData();
		String creditrequeststarting = getEBTRequestTagData();
		String creditrequestending = getEBTRequestEndTagData();
		String commongroup = getCommonGroupXmlData(Parameters.PaymntTypeEBT, Parameters.TranTypeSale, 
												   Parameters.posentrymode_part1_Magnetic_Stripe_Track_Read, 
												   Parameters.posentrymode_part2_No_PIN_entry_capability, 
												   Parameters.poscondcode_Cardholder_Present_Card_Present, 
												   Parameters.termcatcode_Electronic_Payment_Terminal, 
												   Parameters.termentrycapablt_Magnetic_stripe_key_entry_and_chip, 
												   Parameters.termlocind_On_Premises, Parameters.cardcaptcap_terminal_has_card_capture_capability);
		String cardgroup = getCardGroupXmlData(track2data);
		String addtlgroup = getAddtlAmtGrpXmlData(Parameters.PartAuthrztnApprvlCapablt_partial_authorization);
		String pingroup = getPinGroupXmlData(pindata, keyserialnumber);
		String ebtgroupString = EBTGroupData();
		
		requestformatpayloadcontent = gmftagstarting
									  +creditrequeststarting
									  +commongroup
									  +cardgroup
									  +pingroup
									  +addtlgroup
									  +ebtgroupString
									  +creditrequestending
									  +gmftagending;
		System.out.println(requestformatpayloadcontent);
		String requeststarting = getrequestformatstarting(Parameters.DID, Parameters.App, Parameters.Auth, Parameters.ClentRef, Parameters.ServiceId);
		String requestending = getrequestformatending();
		request = requeststarting
				  +requestformatpayloadcontent
				  +requestending;
		System.out.println("request val is:"+request);
		sendingRequest(request);
	}
	
	public void pldebitRequestMethod(){
		String gmftagstarting = getGMFTagData();
		String gmftagending = getGMFEndTagData();
		String creditrequeststarting = getPinlessDebitRequestTagData();
		String creditrequestending = getPinlessDebitRequestEndTagData();
		String commongroup = getCommonGroupXmlData(Parameters.PaymntTypePLDEBIT, Parameters.TranTypeSale, 
												   Parameters.posentrymode_part1_manual, 
												   Parameters.posentrymode_part2_No_PIN_entry_capability, 
												   Parameters.poscondcode_Cardholder_Not_Present_Ecommerce, 
												   Parameters.termcatcode_Electronic_Payment_Terminal, 
												   Parameters.termentrycapablt_Magnetic_stripe_key_entry_and_chip, 
												   Parameters.termlocind_On_Premises, Parameters.cardcaptcap_terminal_has_card_capture_capability);
		String cardgroup = getCardGroupXmlDataforPLDebit(Parameters.CardTypeAmex);
		String ecommString = ecommerceGroupData();
		
		requestformatpayloadcontent = gmftagstarting
									  +creditrequeststarting
									  +commongroup
									  +cardgroup
									  +ecommString
									  +creditrequestending
									  +gmftagending;
		System.out.println(requestformatpayloadcontent);
		String requeststarting = getrequestformatstarting(Parameters.DID, Parameters.App, Parameters.Auth, Parameters.ClentRef, Parameters.ServiceId);
		String requestending = getrequestformatending();
		request = requeststarting
				  +requestformatpayloadcontent
				  +requestending;
		System.out.println("request val is:"+request);
		sendingRequest(request);
	}
	
	public String getrequestformatstarting(String DID, String App, String Auth, String ClientRef, String serviceid){
		
		String requestformatstarting = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"+ 
									   "<Request Version=\"3\" ClientTimeout=\"3000\" " +
									   "xmlns=\"http://securetransport.dw/rcservice/xml\">"+
									   "<ReqClientID>"+
									   "<DID>"+DID+"</DID>"+
									   "<App>"+App+"</App>"+
									   "<Auth>"+Auth+"</Auth>"+
									   "<ClientRef>"+ClientRef+"</ClientRef>"+
									   "</ReqClientID>"+
									   "<Transaction>"+
									   "<ServiceID>"+serviceid+"</ServiceID>"+
									   "<Payload Encoding=\"xml_escape\">";
		return requestformatstarting;
	}
	
	public String getrequestformatending(){
		String requestformatending = "</Payload>"+
				  					 "</Transaction>"+
				  					 "</Request>";
		return requestformatending;
	}
	
	public String getGMFTagData(){
		String gmfTagInfo = "&lt;GMF xmlns=&quot;com/firstdata/Merchant/gmfV2.08&quot;&gt;";
		return gmfTagInfo;
	}
	
	public String getGMFEndTagData(){
		String gmfEndTagInfo = "&lt;/GMF&gt;";
		return gmfEndTagInfo;
	}
	
	public String getCreditRequestTagData(){
		String creditRequestTagInfo = "&lt;CreditRequest&gt;";
		return creditRequestTagInfo;
	}
	
	public String getCreditRequestEndTagData(){
		String creditRequestEndTagInfo = "&lt;/CreditRequest&gt;";
		return creditRequestEndTagInfo;
	}
	
	public String getDebitRequestTagData(){
		String debitRequestTagInfo = "&lt;DebitRequest&gt;";
		return debitRequestTagInfo;
	}
	
	public String getDebitRequestEndTagData(){
		String debitRequestEndTagInfo = "&lt;/DebitRequest&gt;";
		return debitRequestEndTagInfo;
	}
	
	public String getPinlessDebitRequestTagData(){
		String pinlessDebitRequestTagInfo = "&lt;PinlessDebitRequest&gt;";
		return pinlessDebitRequestTagInfo;
	}
	
	public String getPinlessDebitRequestEndTagData(){
		String pinlessDebitRequestEndTagInfo = "&lt;/PinlessDebitRequest&gt;";
		return pinlessDebitRequestEndTagInfo;
	}
	
	public String getEBTRequestTagData(){
		String eBTRequestTagInfo = "&lt;EBTRequest&gt;";
		return eBTRequestTagInfo;
	}
	
	public String getEBTRequestEndTagData(){
		String eBTRequestEndTagInfo = "&lt;/EBTRequest&gt;";
		return eBTRequestEndTagInfo;
	}
	
	public String ecommerceGroupData(){
		String ecommString = "&lt;EcommGrp&gt;"+
							 "&lt;EcommTxnInd&gt;01&lt;/EcommTxnInd&gt;"+
							 "&lt;EcommURL&gt;Padmavathi009@google.com&lt;/EcommURL&gt;"+
							 "&lt;/EcommGrp&gt;";
		return ecommString;
	}
	
	public String EBTGroupData(){
		String ebtString = "&lt;EbtGrp&gt;"+
						   "&lt;EBTType&gt;EBTCash&lt;/EBTType&gt;"+
						   "&lt;/EbtGrp&gt;";
		return ebtString;
	}
	
	public String getCommonGroupXmlData(String paymenttype, String transactionType, String posentrypart1, 
										String posentrypart2, String poscondcode, String termcatcode, String termentrycap, 
										String termlocation, String cardcapturecapbulity){
		
		String requestcommongroup = "&lt;CommonGrp&gt;"+
									"&lt;PymtType&gt;"+paymenttype+"&lt;/PymtType&gt;"+
									"&lt;TxnType&gt;"+transactionType+"&lt;/TxnType&gt;"+
									"&lt;LocalDateTime&gt;"+Parameters.currentTime()+"&lt;/LocalDateTime&gt;"+
									"&lt;TrnmsnDateTime&gt;"+Parameters.currentTime()+"&lt;/TrnmsnDateTime&gt;"+
									"&lt;STAN&gt;"+Parameters.STAN+"&lt;/STAN&gt;"+
									"&lt;RefNum&gt;"+referencenum+"&lt;/RefNum&gt;"+
									"&lt;OrderNum&gt;"+ordernum+"&lt;/OrderNum&gt;"+
									"&lt;TPPID&gt;"+Parameters.TPPID+"&lt;/TPPID&gt;"+
									"&lt;TermID&gt;"+Parameters.TID+"&lt;/TermID&gt;"+
									"&lt;MerchID&gt;"+Parameters.MerchantId+"&lt;/MerchID&gt;"+
									"&lt;POSEntryMode&gt;"+posentrypart1+posentrypart2+"&lt;/POSEntryMode&gt;"+
									"&lt;POSCondCode&gt;"+poscondcode+"&lt;/POSCondCode&gt;"+
									"&lt;TermCatCode&gt;"+termcatcode+"&lt;/TermCatCode&gt;"+
									"&lt;TermEntryCapablt&gt;"+termentrycap+"&lt;/TermEntryCapablt&gt;"+
									"&lt;TxnAmt&gt;"+transactionamt+"&lt;/TxnAmt&gt;"+    //(if amount is 8.68 then we have to place 10 9's the value is 0000000868)
									"&lt;TxnCrncy&gt;"+transactioncurrency+"&lt;/TxnCrncy&gt;"+  //(this is the currency code for us dollars)
									"&lt;TermLocInd&gt;"+termlocation+"&lt;/TermLocInd&gt;"+
									"&lt;CardCaptCap&gt;"+cardcapturecapbulity+"&lt;/CardCaptCap&gt;"+
									"&lt;GroupID&gt;"+Parameters.GroupId+"&lt;/GroupID&gt;"+
									"&lt;/CommonGrp&gt;";
		return requestcommongroup;
	}
	
	public String getCardGroupXmlData(String track2data){
		
			String requestcardgroup = "&lt;CardGrp&gt;"+
									  "&lt;Track2Data&gt;"+track2data+"&lt;/Track2Data&gt;"+
									  "&lt;/CardGrp&gt;";
			return requestcardgroup;
	}
	
	public String getCardGroupXmlDataforCredit(String track2data, String cardtype){
		
		String requestcardgroup = "&lt;CardGrp&gt;"+
								  "&lt;Track2Data&gt;"+track2data+"&lt;/Track2Data&gt;"+
								  "&lt;CardType&gt;"+cardtype+"&lt;/CardType&gt;"+
								  "&lt;/CardGrp&gt;";
		return requestcardgroup;
	}
	
	public String getCardGroupXmlDataforPLDebit(String cardtype){
		
		String requestcardgroup = "&lt;CardGrp&gt;"+
								  "&lt;AcctNum&gt;4017779999999011&lt;/AcctNum&gt;"+
								  "&lt;CardExpiryDate&gt;201604&lt;/CardExpiryDate&gt;"+
								  "&lt;/CardGrp&gt;";
		return requestcardgroup;
	}
	
	public String getAddtlAmtGrpXmlData(String partial_authorization){
		
		String requestAddtlAmtGrp = "&lt;AddtlAmtGrp&gt;"+
									"&lt;PartAuthrztnApprvlCapablt&gt;"+partial_authorization+"&lt;/PartAuthrztnApprvlCapablt&gt;"+
									"&lt;/AddtlAmtGrp&gt;";
		return requestAddtlAmtGrp;
	}
	
	public String getPinGroupXmlData(String pindata, String keyserialnumber){
		
		String requestAddtlAmtGrp = "&lt;PINGrp&gt;"+
									"&lt;PINData&gt;"+pindata+"&lt;/PINData&gt;"+
									"&lt;KeySerialNumData&gt;"+keyserialnumber+"&lt;/KeySerialNumData&gt;"+
									"&lt;/PINGrp&gt;";
		return requestAddtlAmtGrp;
	}
	
	public void xmlresponseParsing(String response){
		
		 BufferedReader br=new BufferedReader(new StringReader(response));
         InputSource is=new InputSource(br);

         XMLParser parser=new XMLParser();
         SAXParserFactory factory=SAXParserFactory.newInstance();
         SAXParser sp;
		try {
			sp = factory.newSAXParser();
			 XMLReader reader=sp.getXMLReader();
	         reader.setContentHandler(parser);
	         reader.parse(is);
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
	}
	
	public void sendingRequest(final String request){
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				    	HttpClient httpclient = new DefaultHttpClient();
				    	HttpPost httppost = new HttpPost("https://stg.dw.us.fdcnet.biz/rc");
				    	
				    	System.out.println("request val is:"+request);
				    	try {
							StringEntity xmldata = new StringEntity(request);
							xmldata.setContentType("text/xml");
				    		
				    		httppost.setHeader("User-Agent", "AOne Pos v 4.0.3");
				    		httppost.setHeader("Connection","Keep-Alive");
				    		httppost.setHeader("Cache-Control","no-cache");
				    		
				    		httppost.setEntity(xmldata);
				    		
				    		HttpResponse responsedata = httpclient.execute(httppost);
							HttpEntity entity = responsedata.getEntity();
							String formattedresponse = EntityUtils.toString(entity);
				    		System.out.println("response val is:"+response);
				    		response = formattedresponse.replace("&lt;", "<").replace("&gt;", ">");
				    		System.out.println("reponse formated val is:"+response);
				    		xmlresponseParsing(response);
				    		System.out.println(list);
				    		Message msgObj = handler.obtainMessage();
				            Bundle b = new Bundle();
				            if(list.containsKey("ErrorData")){
				            	b.putString("message", ""+list.get("ErrorData"));
				            }if(list.containsKey("RespCode")){
				            	String responsecodeval = list.get("RespCode");
				            	b.putString("message", ""+Parameters.responsecodes.get(responsecodeval));
				            }
				            msgObj.setData(b);
				            handler.sendMessage(msgObj);
				            
							
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (ClientProtocolException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				    }
				    private final Handler handler = new Handler() {
		                
		                @Override
						public void handleMessage(Message msg) {
		                     
		                    String aResponse = msg.getData().getString("message");

		                    if ((null != aResponse)) {

		                       showAlertDialog(uniMagIIDemo.this, "Payment Response", aResponse, true, "");
		                       
		                    }
		                }
		            };
		 
				}).start();
	}
	
	public class XMLParser extends DefaultHandler{
	                
	    @Override
	    public void startDocument() throws SAXException {
	    	list = new HashMap<String, String>();
	        System.out.println("The document is started");
	    }
	            
	    @Override
	    public void endDocument() throws SAXException {
	        System.out.println("The document is ended");
	    }
	              
	    @Override
	    public void startElement(String uri, String localName, String qName,
	        Attributes attributes) throws SAXException {
	                 
	    	System.out.println("start element is found");
	    }
	    
	    @Override
	    public void endElement(String uri, String localName, String qName)
	         throws SAXException {
	    	System.out.println("end element is found");
	    	tempXmlParsingString = localName;
	    	if(tempXmlParsingString!=null){
		        list.put(tempXmlParsingString, tempString);
		    }
	    }
	     
	    @Override
	    public void characters(char[] ch, int start, int length)
	         throws SAXException {
	        tempString=new String(ch, start, length);
	        System.out.println("xml tag data is:"+tempString);
	    }
	  }
	
	@Override
	public void onBackPressed(){
		super.onBackPressed();
		System.out.println("is called");
		Intent i = new Intent();
		setResult(123456, i);
		finish();
	}
}