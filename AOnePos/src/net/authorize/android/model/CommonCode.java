package net.authorize.android.model;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.ActivityManager.RunningServiceInfo;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.graphics.drawable.GradientDrawable;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.BatteryManager;
import android.text.TextUtils.TruncateAt;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

public class CommonCode 
{

	private Context _context;
	public CommonCode(Context context) 
	{
		_context = context;
	}

	/**
	 * This is used to check weather Internet is on or off
	 * @return true if internet is on else return false
	 */
	public boolean checkInternet() 
	{
		try 
		{
			ConnectivityManager connectivity = (ConnectivityManager) _context.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivity != null) 
			{
				NetworkInfo[] info = connectivity.getAllNetworkInfo();
				if (info != null)
				{
					for (int i = 0; i < info.length; i++)
					{
						//check if network is connected or device is in range
						if (info[i].getState() == NetworkInfo.State.CONNECTED) {
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return false;
	}

	/**
	 * This is used to check is gps enable or not
	
	 * @return true if gps is on else return false
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
	
	 * @return AppleGaramond-Light font
	 */
	public Typeface getNormalFont()
	{
		return Typeface.createFromAsset(_context.getAssets(), "fonts/AppleGaramond-Light.ttf");
	}

	/**
	 * This is used to get Bold font.
	
	 * @return AppleGaramond-Light font
	 */
	public Typeface getBoldFont()
	{
		return Typeface.createFromAsset(_context.getAssets(), "fonts/AppleGaramond-Light.ttf");
	}

	/**
	 * This is used to check email format

	 * @param email
	 * @return true if email valid else return false
	 */
	public boolean checkEmailValidOrNot(String email) {
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
	
	 */
	public void showNoInternetConnection()
	{
		//get message from string.xml
		Toast.makeText(_context, "No Internet Connection", Toast.LENGTH_LONG).show();
	}

	/**
	 * This is used to show Data Found Toast
	 
	 */
	public void showNoDataFound()
	{
		Toast.makeText(_context, "No Data Found", Toast.LENGTH_LONG).show();
	}

	/**
	 * This is used to show Messages from Resource file
	 
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
	
	 * @param message
	 */
	public void showMessage(String message)
	{
		Toast.makeText(_context, message, Toast.LENGTH_LONG).show();
	}

	/**
	 * This is used to get Date in specified format
	 * @param pattern in which date string is return
	 * @return date in String format
	 */
	public String getDateString(String pattern)
	{
		//create date format
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		String dateString = sdf.format(new Date());
		//return date
		return dateString;
	}
	
	/**
	 * Method to get GMT date string
	 * @param pattern in which date format required
	 * @return date string in GMT format
	 */
	public String get_GMT_DateString(String pattern)
	{
		Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
		Date currentLocalTime = cal.getTime();
		DateFormat date = new SimpleDateFormat(pattern);   
		date.setTimeZone(TimeZone.getTimeZone("GMT")); 
		String localTime = date.format(currentLocalTime); 
		return localTime;
	}
	
	/**
	 * Method for calculate date difference between two dates
	 * @param strEnterDate
	 * @param strTodayDate
	 * @return differnce between two dates
	 */
	public long getDateDiffString(String strEnterDate, String strTodayDate)
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
	 * This is used to convert date and time in MM-dd-yyyy HH:mm PM/AM
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
	 * @param str_input- input date string
	 * @param strSourceFormat- input date format
	 * @param strOputputFormat - format of output date string
	 * @return formatted date string
	 */
	public String change_Date_Format(String str_input, String strSourceFormat, String strOputputFormat)
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
	 * @param distance in miles
	 * @return meters
	 */
	public double ConvertMilesToMeters(double distance)
	{
		return Math.round((double)(distance) * 1609.34);
	}

	/**
	 * Methos for calculating distance between two locations
	 * @param lat1
	 * @param lon1
	 * @param lat2
	 * @param lon2
	 * @return distance
	 */
	public double calculate_distance(double lat1, double lon1,double lat2, double lon2) 
	{
		// Find the deltas
		double delta_lon = degtorad(lon2) - degtorad(lon1);

		// Find the Great Circle distance
		double distance = Math.acos(Math.sin(degtorad(lat1)) * Math.sin(degtorad(lat2)) + Math.cos(degtorad(lat1)) * Math.cos(degtorad(lat2)) *Math.cos(delta_lon)) * 3963.189;
		return distance;
	}

	public double degtorad(double val)
	{
		double pi = Math.PI;
		double de_ra = ((val)*(pi/180));
		return de_ra;
	}
	/**
	 * This method check whether service is running or not
	 * @param serviceClass - Service Class
	 * @return true if service is running else false
	 */
	public boolean isMyServiceRunning(Class<?> serviceClass) 
	{
		ActivityManager manager = (ActivityManager) _context.getSystemService(Context.ACTIVITY_SERVICE);
		for (RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE))
		{
			if (serviceClass.getName().equals(service.service.getClassName())) 
			{
				return true;
			}
		}
		return false;
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
	 * This is used to format double value into 2 digits
	 * @param value 
	 * @return String value upto 4 decimal places
	 */
	public String formatTo2Digit(String string)
	{
		double value = Double.parseDouble(string);
		DecimalFormat format = new DecimalFormat("##.##");
		return format.format(value);
	}


	/**
	 * This is used to add marquee effect to textview
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
	 * Method to get battery level of device
	 * @return battery level in percenatge
	 */
	public int getBatteryLevel()
	{
		IntentFilter ifilter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
		Intent batteryStatus = _context.registerReceiver(null, ifilter);
		return batteryStatus.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
	}
	
	/**
	 * Sort JSON Array according to key
	 * @param array
	 * @param str_sort_Key
	 * @return sorted json array
	 */
	public JSONArray sortJsonArray(JSONArray array, final String str_sort_Key) 
	{
		List<JSONObject> jsons = new ArrayList<JSONObject>();
		try 
		{
			for (int i = 0; i < array.length(); i++) 
			{
				jsons.add(array.getJSONObject(i));
			}
			Collections.sort(jsons, new Comparator<JSONObject>() 
					{
				@Override
				public int compare(JSONObject lhs, JSONObject rhs) 
				{
					try 
					{
						String lid= lhs.getString(str_sort_Key);

						String rid = rhs.getString(str_sort_Key);
						return lid.compareTo(rid);
					} 
					catch (JSONException e)
					{
						e.printStackTrace();
					}
					// Here you could parse string id to integer and then compare.
					return 0;
				}
					});
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return new JSONArray(jsons);
	}
	/**
	 * Method to convert dp to pixel
	 * @param dp
	 * @return pixel
	 */
	public float convertDpToPixel(float dp)
	{
	    Resources resources = _context.getResources();
	    DisplayMetrics metrics = resources.getDisplayMetrics();
	    float px = dp * (metrics.densityDpi / 160f);
	    return px;
	}
	/**
	 * Method to check whether given date is past or not
	 * @param strDate
	 * @param strPattern
	 * @return true if given date is past else return false
	 */
	@SuppressLint("SimpleDateFormat")
	public boolean isPastDate(String strDate, String strPattern)
	{
		long diff;
		SimpleDateFormat formatter = new SimpleDateFormat(strPattern);//MM-dd-yyyy
		try 
		{
			Calendar calendar = Calendar.getInstance();
			diff = getDateDiffString(strDate, formatter.format(calendar.getTime()));
			if(diff<0)
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
	 * Method to get Current date in given pattern 
	 * @param strPattern
	 * @return current date in given format
	 */
	@SuppressLint("SimpleDateFormat")
	public String getTodayDate(String strPattern)
	{
		String strDate = "";
		SimpleDateFormat formatter = new SimpleDateFormat(strPattern);
		try 
		{
			Calendar calendar = Calendar.getInstance();
			strDate = formatter.format(calendar.getTime());
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return strDate; 
	}
	/**
	 * Method to get date next or previous, if we pass 100 then it return date after 100days
	 * @param next_or_prev 0 for today, -1 for yesterday,1 for tomarrow....
	 * @return return desired date
	 */
	@SuppressLint("SimpleDateFormat")
	public String getNextOrPreviousDate(int next_or_prev)
	{
		String strDate = "";
		SimpleDateFormat formatter = new SimpleDateFormat("MM-dd-yyyy");
		try 
		{
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DATE, next_or_prev);
			strDate = formatter.format(calendar.getTime());
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return strDate; 
	}
	
	/**
	 * Method to create GradientDrable with  rounded corner
	 * @param startColor
	 * @param centerColor
	 * @param endColor
	 * @param cornerRadii
	 * @return GradientDrawable Object
	 */
	public GradientDrawable create_GradientDrawable(int startColor, int centerColor, int endColor, int cornerRadii)
	{
		/*
		int colors[] = { 0xff255779 , 0xff3e7492, 0xffa6c0cd };
		final GradientDrawable drawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
		drawable.setShape(GradientDrawable.RECTANGLE); 
		drawable.setCornerRadii(new float [] { 0, 0, 5, 5, 0, 0, 0, 0}); //top-left, top-right, bottom-right, bottom-left
		*/
		int colors[] = { startColor , centerColor, endColor };// 0xff255779 input color format in hex
		final GradientDrawable gradientDrawable = new GradientDrawable(GradientDrawable.Orientation.TOP_BOTTOM, colors);
		gradientDrawable.setShape(GradientDrawable.RECTANGLE); 
		gradientDrawable.setCornerRadii(new float [] { cornerRadii, cornerRadii,cornerRadii, cornerRadii, cornerRadii, cornerRadii, cornerRadii,cornerRadii}); //top-left, top-right, bottom-right, bottom-left
		return gradientDrawable;
	}
	/**
	 * Method for sorting arrayList
	 * @param Arraylist to be sort and hashmap key
	 * @return return sorted arralist
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	private void sortArrayList(ArrayList<HashMap<String, String>> arrayList, final String strHashMapKey) 
	{
		Collections.sort(arrayList, new Comparator()
		{
           @Override
           public int compare(Object o2, Object o1) 
           {
               HashMap<String,String> map1=(HashMap)o1;
               HashMap<String,String> map2=(HashMap)o2;
               String s1=(String)map1.get(strHashMapKey);
               String s2=(String)map2.get(strHashMapKey);
               return s1.compareTo(s2);
           }
       });
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
