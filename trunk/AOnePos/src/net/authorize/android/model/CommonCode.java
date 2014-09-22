package net.authorize.android.model;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.graphics.Typeface;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils.TruncateAt;
import android.util.Log;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class CommonCode {

	private Context _context;
	private ProgressDialog progressDialog;
	
	
	public CommonCode(Context context) {
		_context = context;
	}

	/**
	 * This is used to check weather Internet is on or off
	 * @author Swapnil 
	 * @return
	 */
	public boolean checkInternet() {
		try {
			ConnectivityManager connectivity = (ConnectivityManager) _context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			
			if (connectivity != null) {
				NetworkInfo[] info = connectivity.getAllNetworkInfo();
				if (info != null)
					for (int i = 0; i < info.length; i++)
						//check if network is connected or device is in range
						if (info[i].getState() == NetworkInfo.State.CONNECTED) {
							return true;
						}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * This is used to check is gps enable or not
	 * @author Swapnil 
	 * @return
	 */
	public boolean checkGPS() {
		//create result variable
    	boolean canGetLocation = false;
        try {
        	//create location manager class
            LocationManager locationManager = (LocationManager) _context.getSystemService(Context.LOCATION_SERVICE);
            // getting GPS status
            boolean isGPSEnabled = locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
            // getting network status
            boolean isNetworkEnabled = locationManager.isProviderEnabled(LocationManager.NETWORK_PROVIDER);
 
            if (!isGPSEnabled && !isNetworkEnabled) {
                // no network provider is enabled
            	canGetLocation = false;
            } else {
                canGetLocation = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        //return
        return canGetLocation;
    }
	
	/**
	 * This is used to get normal font.
	 * @author Swapnil 
	 * @return AppleGaramond-Light font
	 */
	public Typeface getNormalFont()
	{
		return Typeface.createFromAsset(_context.getAssets(),"fonts/AppleGaramond-Light.ttf");
	}
	
	/**
	 * This is used to get Bold font.
	 * @author Swapnil 
	 * @return AppleGaramond-Light font
	 */
	public Typeface getBoldFont()
	{
		return Typeface.createFromAsset(_context.getAssets(),"fonts/AppleGaramond-Light.ttf");
	}
	
	/**
	 * This is used to check email format
	 * @author Swapnil 
	 * @param email
	 * @return
	 */
	public boolean checkEmail(String email) {
		boolean isValid = false;

		String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
		CharSequence inputStr = email;

		Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
		Matcher matcher = pattern.matcher(inputStr);
		if (matcher.matches()) {
			isValid = true;
		}
		return isValid;
	}
	
	/**
	 * This is used to show No Internet connection Toast
	 * @author Swapnil 
	 */
	public void showNoInternetConnection()
	{
		//get message from string.xml
		Toast.makeText(_context, "No Internet Connection", Toast.LENGTH_LONG).show();
	}
	
	/**
	 * This is used to show Data Found Toast
	 * @author Swapnil 
	 */
	public void showNoDataFound()
	{
		//get message from string.xml
		Toast.makeText(_context, "No Data Found", Toast.LENGTH_LONG).show();
	}
	
	/**
	 * This is used to show Messages from Resource file
	 * @author Swapnil 
	 * @param msgId
	 */
	public void showMessage(int msgId)
	{
		//get message from string.xml
		String message = _context.getString(msgId);
		Toast.makeText(_context, message, Toast.LENGTH_LONG).show();
	}
	
	/**
	 * This is used to show String Message
	 * @author Swapnil 
	 * @param message
	 */
	public void showMessage(String message)
	{
		Toast.makeText(_context, message, Toast.LENGTH_LONG).show();
	}
	
	
	
	/**
	 * This is used to get Date in specified format
	 * @author Swapnil 
	 * @param pattern in which date string is return
	 * @return date in String format
	 */
	public static String getDateString(String pattern)
	{
		//create date format
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String dateString = sdf.format(new Date());
		//return date
		return dateString;
	}
	
	/**
	 * This is used to convert date and time in MM-dd-yyyy HH:mm PM/AM
	 * @author Swapnil 
	 */
	public String[] getDateInFormate(String dateTimeString)
	{
		Log.e("Incoming Date", dateTimeString);
		//store date in 0 index and time in 1st index
		String dateTime[] = dateTimeString.split("\\s");
		//create result
		String result[] = new String[2];
		
		String date[] = dateTime[0].split("-");
		String time = dateTime[1];
		
		//create date format
		DateFormat outputFormat = new SimpleDateFormat("hh:mm:ss a");
		DateFormat inputFormat = new SimpleDateFormat("hh:mm:ss");
		
		Date d1 = null;
		try {
			d1 = inputFormat.parse(time);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		result[0] = date[1] + "-" + date[2] + "-" + date[0];
		result[1] = (d1 == null)?"":outputFormat.format(d1);
		
		// This is for display purpose only
		ArrayList<String> list = new ArrayList<String>();
		list.add(result[0]);
		list.add(result[1]);
		System.out.println("Outgoing Date"+list);
		
		return result;
	}
	
	/**
	 * This is used to get GMT date format
	 * @author Swapnil 
	 * @return date in gmt format
	 */
	public String getGMTDate()
	{
		DateFormat TWENTY_FOUR_TF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		TWENTY_FOUR_TF.setTimeZone(TimeZone.getTimeZone("gmt"));
		String gmtDate = TWENTY_FOUR_TF.format(new Date());
		
		return gmtDate;
	}
	
	
	/**
	 * This is used to format double value into 4 digits
	 * @param value 
	 * @return String value upto 4 decimal places
	 */
	public String formatTo4Digit(double value)
	{
		//create formatter
		DecimalFormat format = new DecimalFormat("##.####");
		//return formatted value
		return format.format(value);
	}
	
	/**
	 * This is used to format double value into 4 digits
	 * @param value 
	 * @return String value upto 4 decimal places
	 */
	public String formatTo4Digit(String string)
	{
		Log.e("Incoming value", string);
		// convert number into 
		double value = Double.parseDouble(string);
		//create formatter
		DecimalFormat format = new DecimalFormat("##.####");
		
		Log.e("Outgoing value", format.format(value));
		//return formatted value
		return format.format(value);
	}
	
	
	/**
	 * This is used to add marquee effect to textview
	 * @author Swapnil 
	 * @param textView
	 */
	public void addMarqueeEffect(TextView textView)
	{
		TextView tf = textView;
		tf.setEllipsize(TruncateAt.MARQUEE);
		tf.setMarqueeRepeatLimit(-1);
		tf.setSingleLine(true);
		tf.setSelected(true);
	}
	
	/**
	 * Class to show DatePicker Dialog
	 * @param EditText or TextView on which you have to set date
	 * @author Samadhan Medge
	 */
	public static class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
	{
		EditText editText;
		Calendar calendar;
		SimpleDateFormat sdf;
		String strDate;
		int sdk = android.os.Build.VERSION.SDK_INT, year, month, day;
		boolean isMinDate;
		TextView textView;
		public DatePickerFragment(EditText et, TextView tv, boolean isMinDate)
		{
			this.editText = et;
			this.textView = tv;
			this.isMinDate = isMinDate;
		}
		
		@SuppressLint({ "NewApi", "SimpleDateFormat" })
		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState) 
		{
			// Use the current date as the default date in the picker
			calendar = Calendar.getInstance();
			sdf = new SimpleDateFormat("dd/MM/yyyy");
			year = calendar.get(Calendar.YEAR);
			month = calendar.get(Calendar.MONTH);
			day = calendar.get(Calendar.DAY_OF_MONTH);
			strDate = sdf.format(calendar.getTime());
			Date d = null;
			try 
			{
				d = sdf.parse(strDate);
			} catch (ParseException e) 
			{
				e.printStackTrace();
			}
			DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(), this, year, month, day);
			if(sdk>=android.os.Build.VERSION_CODES.HONEYCOMB)
			{
				if(isMinDate)
					datePickerDialog.getDatePicker().setMinDate(d.getTime());
				else
					datePickerDialog.getDatePicker().setMaxDate(d.getTime());
			}
			// Create a new instance of DatePickerDialog and return it
			return datePickerDialog;
		}

		public void onDateSet(DatePicker view, int year, int month, int day)
		{
			if(isPastDate((((month+1)+"").length()==1?"0"+(month+1):(month+1))+"-"+day+"-"+year, "MM-dd-yyyy") == (isMinDate))
				Toast.makeText(getActivity(), "Invalid Date", Toast.LENGTH_SHORT).show();
			else
			{
				if(editText!=null)
					editText.setText((((month+1)+"").length()==1?"0"+(month+1):(month+1))+"-"+((day+"").length()==1?"0"+day:day+"")+"-"+year);
				else if(textView!=null)
					textView.setText((((month+1)+"").length()==1?"0"+(month+1):(month+1))+"-"+((day+"").length()==1?"0"+day:day+"")+"-"+year);
			}
		}
	}
	/**
	 * Class to show TimePicker Dialog
	 * @param EditText or TextView on which you have to set time
	 * @author Samadhan Medge
	 */
	public static class TimePickerFragment extends DialogFragment implements TimePickerDialog.OnTimeSetListener 
	{
		EditText editText;
		TextView textView;
		FragmentActivity activity;
		Context context;
		public TimePickerFragment( Context context,EditText editText, TextView textView, FragmentActivity activity)
		{
			this.editText = editText;
			this.textView = textView;
			this.context = context;
			this.activity = activity;
		}

		@Override
		public Dialog onCreateDialog(Bundle savedInstanceState)
		{
			// Use the current time as the default values for the picker
			final Calendar c = Calendar.getInstance();
			int hour = c.get(Calendar.HOUR_OF_DAY);
			int minute = c.get(Calendar.MINUTE);

			// Create a new instance of TimePickerDialog and return it
			return new TimePickerDialog(getActivity(), this, hour, minute,false);
		}

		public void onTimeSet(TimePicker view, int hourOfDay, int minute) 
		{
//			String str = "AM";
			if(hourOfDay>12)
			{
				hourOfDay = hourOfDay-12;
//				str = "PM";
			}
			if(editText!=null)
				editText.setText(((hourOfDay+"").length()==1?"0"+hourOfDay:hourOfDay+"")+":"+((minute+"").length()==1?"0"+minute:minute+""));
			else if(textView!=null)
				textView.setText(((hourOfDay+"").length()==1?"0"+hourOfDay:hourOfDay+"")+":"+((minute+"").length()==1?"0"+minute:minute+""));
		}
	}
	
	/**
	 * Method to check whether given date is past or not
	 * @param strDate
	 * @param strPattern
	 * @return true if given date is past else return false
	 * @author Samadhan Medge
	 */
	@SuppressLint("SimpleDateFormat")
	public static boolean isPastDate(String strDate, String strPattern)
	{
		long diff;
		SimpleDateFormat formatter = new SimpleDateFormat(strPattern);//MM-dd-yyyy
		try 
		{
			Calendar calendar = Calendar.getInstance();
			diff = getDateDiffString(strDate, formatter.format(calendar.getTime()));
			System.out.println("diff"+diff);
			if(diff<=0)
				return true;
			else
				return false;
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return false; 
	}
	/**
	 * @author Samadhan Medge
	 * @param str_input- input date string
	 * @param strSourceFormat- input date format
	 * @param strOputputFormat - format of output date string
	 * @return formatted date string
	 */
	public static String change_Date_Format(String str_input, String strSourceFormat, String strOputputFormat)
	{
		String desiredDateString = "";
		if(str_input!=null && !str_input.equalsIgnoreCase(""))
		{
			Date input_date= null;
			SimpleDateFormat sourceFormat  = new SimpleDateFormat(strSourceFormat);
			SimpleDateFormat desiredFormat = new SimpleDateFormat(strOputputFormat);
			try 
			{
				input_date =  sourceFormat .parse(str_input);
				desiredDateString = desiredFormat.format(input_date);
			}
			catch (ParseException e) 
			{
				e.printStackTrace();
			}
		}
		return desiredDateString;
	}
	
	/**
	 * Method for calculate date difference between two dates
	 * @param strEnterDate
	 * @param strTodayDate
	 * @return differnce between two dates
	 * @author Samadhan Medge
	 */
	public static long getDateDiffString(String strEnterDate, String strTodayDate)
	{
		Date dateToday= null, dateEnter = null;
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		try 
		{
			dateToday =  formatter.parse(strTodayDate);
			dateEnter = formatter.parse(strEnterDate);
		}
		catch (java.text.ParseException e) 
		{
			e.printStackTrace();
		} 

		long timeOne = dateToday.getTime();
		long timeTwo = dateEnter.getTime();
		long oneDay = 1000 * 60 * 60 * 24; // calculate diiference in days
		//	    long oneDay = 1000 * 60 * 60 ; // calculate diiference in hours
		//	    long oneDay = 1000 * 60  ; // calculate diiference in minutes
		//	    long oneDay = 1000; // calculate diiference in Seconds
		long delta = (timeTwo - timeOne) / oneDay;

		return delta;
	}

	/**
	 * This is used to get user email id Ragestered in mobile
	 * @param textView
	 */
	
	public static String getEmail(Context context) {
	    AccountManager accountManager = AccountManager.get(context); 
	    Account account = getAccount(accountManager);

	    if (account == null) {
	      return null;
	    } else {
	      return account.name;
	    }
	  }

	  private static Account getAccount(AccountManager accountManager) {
	    Account[] accounts = accountManager.getAccountsByType("com.google");
	    Account account;
	    if (accounts.length > 0) {
	      account = accounts[0];      
	    } else {
	      account = null;
	    }
	    return account;
	  }
}