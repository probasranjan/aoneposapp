package net.authorize.android;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
 




import com.aoneposapp.R;

import net.authorize.TransactionType;
import net.authorize.Environment;
import net.authorize.ResponseReasonCode;
import net.authorize.android.AuthNet;
import net.authorize.android.AuthNetActivityBase;
import net.authorize.android.SimpleActivity;
import net.authorize.android.button.AuthNetButton;
import net.authorize.android.model.SystemSession;
import net.authorize.data.Address;
import net.authorize.data.Customer;
import net.authorize.data.EmailReceipt;
import net.authorize.data.Order;
import net.authorize.data.ShippingCharges;
import net.authorize.data.creditcard.CreditCard;
import net.authorize.xml.MessageType;
import net.authorize.xml.Result;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
 
public class SDKActivity extends SimpleActivity {
    /**
     * Called when the activity is first created.
     */
	
	public static AuthNet authNetObj;
	private SystemSession session;
	
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // load merchant info
        setContentView(R.layout.main);
 
        session = new SystemSession(SDKActivity.this);
        
        authNetObj = AuthNet.getInstance(Environment.SANDBOX,
            R.layout.authnet_mobile_merchant_auth_dialog, R.id.authnet_loginid_edit,
            R.id.authnet_password_edit, R.id.authnet_auth_cancel_button,
            R.id.authnet_auth_login_button);
        
       
        
        // prepare the authCapture button
//        AuthNetButton authCaptureButton = authNetObj.getButton(this,"Authorize and Capture");
//        authCaptureButton.setOnClickListener(new OnClickListener() {
// 
//            public void onClick(View arg0) {
//               
//            }
//        });
        launchAuthCaptureIntent();
//        LinearLayout layout = (LinearLayout) findViewById(R.id.MainLayout);
//        layout.addView(authCaptureButton);
    }
 
    /**
     * Activity launcher for authCapture transactions.
     */
    private void launchAuthCaptureIntent() {
        String refId = Long.toString(System.currentTimeMillis());
        BigDecimal totalAmount = new BigDecimal(19.99);
 
        /*
         * listing these out only to show the required
         * 'parameter set' that should be passed into
         * the create Intent method.
         */
        CreditCard creditCard = CreditCard.createCreditCard();
        creditCard.setCreditCardNumber("4111111111111111");
        Calendar expCal = Calendar.getInstance();
        expCal.add(Calendar.YEAR, 4);
        creditCard.setExpirationDate(expCal.getTime());
 
        Order order = Order.createOrder();
        order.setTotalAmount(new BigDecimal(0.01));
        order.setDescription("Android Test Order");
 
        Customer customer = null;
        Address shippingAddress = null;
        ShippingCharges shippingCharges = null;
        EmailReceipt emailReceipt = null;
        HashMap<String, String> merchantDefinedFields = new HashMap<String, String>();
        merchantDefinedFields.put("notes", "sent from SampleActivity");
        
        System.out.println("merchantDefinedFields" + merchantDefinedFields);
 
        Intent authNetIntent = authNetObj.createAIMAuthCaptureIntent(this, refId,totalAmount, creditCard, order, customer, shippingAddress,shippingCharges, emailReceipt, merchantDefinedFields);
        /*
         * final launch command to spawn the Activity and provide a
         * callback where the result data can be processed
         */
        System.out.println("AuthNet"+AuthNetActivityBase.EXTRA_DUPLICATE_TXN_WINDOW_SECS);
        authNetIntent.putExtra( AuthNetActivityBase.EXTRA_DUPLICATE_TXN_WINDOW_SECS, 5);
        launchSubActivity(authNetIntent, createPaymentIntentResultCallback());
    }
 
    /**
     * Creates a payment Intent ResultCallback.
     *
     * @return ResultCallback
     */
    private ResultCallback createPaymentIntentResultCallback() {
        return new SimpleActivity.ResultCallback() {
 
            public void resultTransactionCanceled(Enum<?> txnType) {
                showAlert((TransactionType) txnType + " canceled", "");
            }
 
            public void resultTransactionFailed(Enum<?> txnType,
                Result result) {
 
                MessageType msgType = MessageType.E00000;
                StringBuilder errorBuilder = new StringBuilder(msgType.getValue()) .append(": ").append(msgType.getText());
 
                net.authorize.aim.Result aimResult = (net.authorize.aim.Result) result;
 
                errorBuilder = new StringBuilder();
                if (aimResult.getTransactionResponseErrors().size() > 0) {
                    for (ResponseReasonCode respReasonCode : aimResult .getTransactionResponseErrors()) {
                        errorBuilder.append(respReasonCode.getResponseReasonCode())
                            .append(": ").append(respReasonCode.getReasonText()).append("\n");
                    }
                } else if (aimResult.getMessages().size() > 0) {
                    for (MessageType msgType1 : aimResult.getMessages()) {
                        errorBuilder.append(msgType1.getValue()).append(": ") .append(msgType1.getText()).append("\n");
                    }
                }
                showAlert((TransactionType) txnType + " failed", errorBuilder.toString());
            }
 
            public void resultTransactionSucceeded(Enum<?> txnType, Result result) {
            	session.addData("Athorize", "login");
                net.authorize.aim.Result aimResult = (net.authorize.aim.Result) result;
                System.out.println("success of transation SDK Activity");
             //   showAlert((TransactionType) txnType + " success", "authCode: " + aimResult.getAuthCode() + "\n" + "refTransId: " + aimResult.getTransId());
                finish();
            }
        };
    }
 
    /**
     * Create a simple alert dialog.
     *
     * @param title
     * @param message
     */
    private void showAlert(String title, String message) {
        // Show an error to the user
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(message).setTitle(title)
            .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                }
            });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
