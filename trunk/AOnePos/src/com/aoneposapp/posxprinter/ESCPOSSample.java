package com.aoneposapp.posxprinter;

import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.andprn.jpos.command.ESCPOS;
import com.andprn.jpos.command.ESCPOSConst;
import com.andprn.jpos.printer.ESCPOSPrinter;

public class ESCPOSSample
{
	private ESCPOSPrinter posPtr;
	// 0x1B
	private final char ESC = ESCPOS.ESC;
	
	public ESCPOSSample()
	{
		posPtr = new ESCPOSPrinter();
	}
	
    public void sample1() throws UnsupportedEncodingException
    {
    	posPtr.printNormal(ESC+"|cA"+ESC+"|2CReceipt\r\n\r\n\r\n");
	    posPtr.printNormal(ESC+"|rATEL (123)-456-7890\n\n\n");
	    posPtr.printNormal(ESC+"|cAThank you for coming to our shop!\n");
	    posPtr.printNormal(ESC+"|cADate\n\n");
	    posPtr.printNormal("Chicken                             $10.00\n");
	    posPtr.printNormal("Hamburger                           $20.00\n");
	    posPtr.printNormal("Pizza                               $30.00\n");
	    posPtr.printNormal("Lemons                              $40.00\n");
	    posPtr.printNormal("Drink                               $50.00\n");
	    posPtr.printNormal("Excluded tax                       $150.00\n");
	    posPtr.printNormal(ESC+"|uCTax(5%)                              $7.50\n");
	    posPtr.printNormal(ESC+"|bC"+ESC+"|2CTotal         $157.50\n\n");
	    posPtr.printNormal("Payment                            $200.00\n");
	    posPtr.printNormal("Change                              $42.50\n\n");
	    posPtr.printBarCode("{Babc456789012", ESCPOSConst.BCS_Code128, 40, 512, ESCPOSConst.ALIGNMENT_CENTER, ESCPOSConst.HRI_TEXT_BELOW); // Print Barcode
	    posPtr.lineFeed(4);
	    posPtr.cutPaper();
    }
    
    public void sample2() throws UnsupportedEncodingException
    {
	    posPtr.printText("Receipt\r\n\r\n\r\n", ESCPOSConst.ALIGNMENT_CENTER, ESCPOSConst.FNT_DEFAULT, ESCPOSConst.TXT_2WIDTH);
	    posPtr.printText("TEL (123)-456-7890\r\n", ESCPOSConst.ALIGNMENT_RIGHT, ESCPOSConst.FNT_DEFAULT, ESCPOSConst.TXT_1WIDTH);
	    posPtr.printText("Thank you for coming to our shop!\r\n", ESCPOSConst.ALIGNMENT_CENTER, ESCPOSConst.FNT_DEFAULT, ESCPOSConst.TXT_1WIDTH);
	    posPtr.printText("Chicken                             $10.00\r\n", ESCPOSConst.ALIGNMENT_LEFT, ESCPOSConst.FNT_DEFAULT, ESCPOSConst.TXT_1WIDTH);
	    posPtr.printText("Hamburger                           $20.00\r\n", ESCPOSConst.ALIGNMENT_LEFT, ESCPOSConst.FNT_DEFAULT, ESCPOSConst.TXT_1WIDTH);
	    posPtr.printText("Pizza                               $30.00\r\n", ESCPOSConst.ALIGNMENT_LEFT, ESCPOSConst.FNT_DEFAULT, ESCPOSConst.TXT_1WIDTH);
	    posPtr.printText("Lemons                              $40.00\r\n", ESCPOSConst.ALIGNMENT_LEFT, ESCPOSConst.FNT_DEFAULT, ESCPOSConst.TXT_1WIDTH);
	    posPtr.printText("Drink                               $50.00\r\n\r\n", ESCPOSConst.ALIGNMENT_LEFT, ESCPOSConst.FNT_DEFAULT, ESCPOSConst.TXT_1WIDTH);
	    posPtr.printText("Excluded tax                       $150.00\r\n", ESCPOSConst.ALIGNMENT_LEFT, ESCPOSConst.FNT_DEFAULT, ESCPOSConst.TXT_1WIDTH);
	    posPtr.printText("Tax(5%)                              $7.50\r\n", ESCPOSConst.ALIGNMENT_LEFT, ESCPOSConst.FNT_UNDERLINE, ESCPOSConst.TXT_1WIDTH);
	    posPtr.printText("Total         $157.50\r\n\r\n", ESCPOSConst.ALIGNMENT_LEFT, ESCPOSConst.FNT_DEFAULT, ESCPOSConst.TXT_2WIDTH);
	    posPtr.printText("Payment                            $200.00\r\n", ESCPOSConst.ALIGNMENT_LEFT, ESCPOSConst.FNT_DEFAULT, ESCPOSConst.TXT_1WIDTH);
	    posPtr.printText("Change                              $42.50\r\n\r\n", ESCPOSConst.ALIGNMENT_LEFT, ESCPOSConst.FNT_DEFAULT, ESCPOSConst.TXT_1WIDTH);
	    posPtr.printBarCode("{Babc456789012", ESCPOSConst.BCS_Code128, 40, 512, ESCPOSConst.ALIGNMENT_CENTER, ESCPOSConst.HRI_TEXT_BELOW); // Print Barcode
	    posPtr.lineFeed(4);
	    posPtr.cutPaper();
    }
    
	private Bitmap getBitmapFromURL(String src)
	{
		try
		{
			URL url = new URL(src);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setDoInput(true);
			connection.connect();
			InputStream input = connection.getInputStream();
			Bitmap myBitmap = BitmapFactory.decodeStream(input);
			return myBitmap;
		}
		catch (IOException e)
		{
			Log.e("ESCSample",e.getMessage());
			return null;
		}
	}

    
    public void imageTest(Context context, Bitmap map1) throws IOException
    {
//    	posPtr.printBitmap("//sdcard//temp//test//car_s.jpg", ESCPOSConst.ALIGNMENT_CENTER);
//    	posPtr.printBitmap("//sdcard//temp//test//danmark_windmill.jpg", ESCPOSConst.ALIGNMENT_LEFT);
//    	posPtr.printBitmap("//sdcard//temp//test//denmark_flag.jpg", ESCPOSConst.ALIGNMENT_RIGHT);
    
    	// Bitmap from Stream. (It needs to Internet connection.)
    //	Bitmap bitmap = getBitmapFromURL("https://www.google.com/images/srpr/logo3w.png");
    	System.out.println("map1   1111111 "+map1);
    	Bitmap bitmap = map1;
    	
    	System.out.println("map1  22222 "+bitmap);
    	if(bitmap != null)
    	posPtr.printBitmap(bitmap, ESCPOSConst.ALIGNMENT_CENTER);
    	//	posPtr.printBitmap(bitmap, ESCPOSConst.ALIGNMENT_CENTER, ESCPOSConst.BITMAP_NORMAL);
    	posPtr.lineFeed(1);
 	    posPtr.cutPaper();
    }
    
    public void westernLatinCharTest() throws UnsupportedEncodingException
    {    	
    	final char [] diff = {0x23,0x24,0x40,0x5B,0x5C,0x5D,0x5E,0x6C,0x7B,0x7C,0x7D,0x7E,
   			 0xA4,0xA6,0xA8,0xB4,0xB8,0xBC,0xBD,0xBE};
    	String ad = new String(diff);
    	posPtr.printText(ad+"\r\n\r\n", ESCPOSConst.ALIGNMENT_LEFT, ESCPOSConst.FNT_DEFAULT, ESCPOSConst.TXT_1WIDTH);    		
    }
    
    public void barcode1DTest() throws UnsupportedEncodingException
    {
    	String barCodeData = "123456789012";
    	
    	posPtr.printString("UPCA\r\n");
    	posPtr.printBarCode(barCodeData, ESCPOSConst.BCS_UPCA, 70, 3, ESCPOSConst.ALIGNMENT_CENTER, ESCPOSConst.HRI_TEXT_BELOW);
    	posPtr.printString("UPCE\r\n");
    	posPtr.printBarCode(barCodeData, ESCPOSConst.BCS_UPCE, 70, 3, ESCPOSConst.ALIGNMENT_CENTER, ESCPOSConst.HRI_TEXT_BELOW);
    	posPtr.printString("EAN8\r\n");
    	posPtr.printBarCode("1234567", ESCPOSConst.BCS_EAN8, 70, 3, ESCPOSConst.ALIGNMENT_CENTER, ESCPOSConst.HRI_TEXT_BELOW);
    	posPtr.printString("EAN13\r\n");
    	posPtr.printBarCode(barCodeData, ESCPOSConst.BCS_EAN13, 70, 3, ESCPOSConst.ALIGNMENT_CENTER, ESCPOSConst.HRI_TEXT_BELOW);
    	posPtr.printString("CODE39\r\n");
    	posPtr.printBarCode("ABCDEFGHI", ESCPOSConst.BCS_Code39, 70, 3, ESCPOSConst.ALIGNMENT_CENTER, ESCPOSConst.HRI_TEXT_BELOW);
    	posPtr.printString("ITF\r\n");
    	posPtr.printBarCode(barCodeData, ESCPOSConst.BCS_ITF, 70, 3, ESCPOSConst.ALIGNMENT_CENTER, ESCPOSConst.HRI_TEXT_BELOW);
    	posPtr.printString("CODABAR\r\n");
    	posPtr.printBarCode(barCodeData, ESCPOSConst.BCS_Codabar, 70, 3, ESCPOSConst.ALIGNMENT_CENTER, ESCPOSConst.HRI_TEXT_BELOW);
    	posPtr.printString("CODE93\r\n");
    	posPtr.printBarCode(barCodeData, ESCPOSConst.BCS_Code93, 70, 3, ESCPOSConst.ALIGNMENT_CENTER, ESCPOSConst.HRI_TEXT_BELOW);
    	posPtr.printString("CODE128\r\n");
    	posPtr.printBarCode("{BNo.{C4567890120", ESCPOSConst.BCS_Code128, 70, 3, ESCPOSConst.ALIGNMENT_CENTER, ESCPOSConst.HRI_TEXT_BELOW);
	    posPtr.lineFeed(4);
	    posPtr.cutPaper();
    }
    
    public void barcode2DTest() throws UnsupportedEncodingException
    {
    	String data = "ABCDEFGHIJKLMN";
    	posPtr.printString("PDF417\r\n");
    	posPtr.printPDF417(data, data.length(), 0, 10, ESCPOSConst.ALIGNMENT_LEFT);
    	posPtr.printString("QRCode\r\n");
    	posPtr.printQRCode(data, data.length(), 3, ESCPOSConst.QRCODE_EC_LEVEL_L, ESCPOSConst.ALIGNMENT_CENTER);
    	posPtr.lineFeed(4);
	    posPtr.cutPaper();
    }
}