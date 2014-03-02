package com.aoneposapp.firstdata;

import IDTech.MSR.uniMag.uniMagReader;
import IDTech.MSR.uniMag.uniMagReaderMsg;
import android.app.Dialog;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.aoneposapp.R;

public	class DlgSettingOption extends Dialog implements android.view.View.OnClickListener{
	public DlgSettingOption(uniMagIIDemo context, uniMagReader myUR ){
        super(context);
        myUniMagReader = myUR;
        myMainUI = context;
	}
    public void DisplayDlg(){
     setContentView(R.layout.dlg_setting_options);
     InitializeUI();
     setProperty();
     setTitle("uniMag II Setting Options");//set the title
     show();//show the dialog 
    }
     //Set property of the dialog
    public void setProperty(){
         window=getWindow();//get the window£®
         WindowManager.LayoutParams wl = window.getAttributes();
         wl.x =0;//x pos of the dlg
         wl.y =30;//y pos of the dlg
         wl.alpha=0.9f;//alpha of the dialog window
         wl.gravity=Gravity.TOP;        
         window.setAttributes(wl);
	}
 
	@Override
	public void onClick(View arg0) {
		// TODO Auto-generated method stub
	  switch(arg0.getId()){
	  case R.id.dlg_btnBack:
		  break;
	  case R.id.dlg_btnTurnOnTEDS:
		  if(true==myUniMagReader.sendCommandEnableTDES())
		  myMainUI.prepareToSendCommand(uniMagReaderMsg.cmdEnableTDES);
		  else
			  Log.d("Demo Info >>>>>","cannot send command");
		  break;
	  case R.id.dlg_btnTurnOnAES:
		  if(true==myUniMagReader.sendCommandEnableAES())
			  myMainUI.prepareToSendCommand(uniMagReaderMsg.cmdEnableAES);
		  else
			  Log.d("Demo Info >>>>>","cannot send command");
		  break;
	  case R.id.dlg_btnGetVersion:
		  if(true==myUniMagReader.sendCommandGetVersion())
			  myMainUI.prepareToSendCommand(uniMagReaderMsg.cmdGetVersion);
		  else
			  Log.d("Demo Info >>>>>","cannot send command");
		  break;
	  case R.id.dlg_btnGetSetting:
		  if(true==myUniMagReader.sendCommandGetSettings())
			  myMainUI.prepareToSendCommand(uniMagReaderMsg.cmdGetSettings);
		  else
			  Log.d("Demo Info >>>>>","cannot send command");
		  break;
	  case R.id.dlg_btnGetSerialNumber:
		  if(true==myUniMagReader.sendCommandGetSerialNumber())
			  myMainUI.prepareToSendCommand(uniMagReaderMsg.cmdGetSerialNumber);
		  else
			  Log.d("Demo Info >>>>>","cannot send command");
		  break;
	  case R.id.dlg_btnClearBuffer:
		  
		  if(true==myUniMagReader.sendCommandClearBuffer())
			  myMainUI.prepareToSendCommand(uniMagReaderMsg.cmdClearBuffer);
		  else
			  Log.d("Demo Info >>>>>","cannot send command");

		  break;

	  case R.id.dlg_btnUpdateFirmware:
		  myMainUI.updateFirmware();
		  break;
	  case R.id.dlg_btnGetChallenge:
		  myMainUI.getChallenge();
		  break;
        default:
	 }
	dismiss();  
	}
	private void InitializeUI()
	{
		dlg_btnBack = (Button)findViewById(R.id.dlg_btnBack);
		dlg_btnTurnOnTEDS = (Button)findViewById(R.id.dlg_btnTurnOnTEDS);
		dlg_btnTurnOnAES = (Button)findViewById(R.id.dlg_btnTurnOnAES);
		dlg_btnGetVersion = (Button)findViewById(R.id.dlg_btnGetVersion);
		dlg_btnGetSetting = (Button)findViewById(R.id.dlg_btnGetSetting);
		dlg_btnGetSerialNumber = (Button)findViewById(R.id.dlg_btnGetSerialNumber);
		dlg_btnClearBuffer = (Button)findViewById(R.id.dlg_btnClearBuffer);
		btnUpdateFirmware=(Button)findViewById(R.id.dlg_btnUpdateFirmware);
		btnGetChallenge=(Button)findViewById(R.id.dlg_btnGetChallenge);
		dlg_btnBack.setOnClickListener(this);
	    dlg_btnTurnOnTEDS.setOnClickListener(this);
	    dlg_btnTurnOnAES.setOnClickListener(this);
	    dlg_btnGetVersion.setOnClickListener(this);
	    dlg_btnGetSetting.setOnClickListener(this);
	    dlg_btnGetSerialNumber.setOnClickListener(this);
	    dlg_btnClearBuffer.setOnClickListener(this);
	    btnUpdateFirmware.setOnClickListener(this);
	    btnGetChallenge.setOnClickListener(this);

	}
	private Button dlg_btnBack;
	private Button dlg_btnTurnOnTEDS;
	private Button dlg_btnTurnOnAES;
	private Button dlg_btnGetVersion;
	private Button dlg_btnGetSetting;
	private Button dlg_btnGetSerialNumber;
	private Button dlg_btnClearBuffer;
	private Button btnUpdateFirmware;
	private Button btnGetChallenge;
	private Window window=null;
	    
	private uniMagReader myUniMagReader = null ; 
	private uniMagIIDemo myMainUI = null;
}
 
