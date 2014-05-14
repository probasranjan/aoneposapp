package net.authorize.android.aim;

import java.math.BigDecimal;
import java.util.HashMap;

import net.authorize.Environment;
import net.authorize.ResponseCode;
import net.authorize.ResponseReasonCode;
import net.authorize.TransactionType;
import net.authorize.android.AuthNet;
import net.authorize.android.AuthNetActivityBase;
import net.authorize.android.SDKActivity;
import net.authorize.android.SimpleActivity;
import net.authorize.android.button.AuthNetButton;
import net.authorize.android.model.AppConstant;
import net.authorize.data.Customer;
import net.authorize.data.EmailReceipt;
import net.authorize.data.Order;
import net.authorize.data.ShippingAddress;
import net.authorize.data.ShippingCharges;
import net.authorize.data.creditcard.CreditCard;
import net.authorize.xml.Result;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;

import com.aoneposapp.R;

public class AIMActivity extends SimpleActivity implements OnClickListener {

  // buttons
  private static final int AUTH_ONLY_BUTTON_ID = 0x998;
  private AuthNetButton authOnlyButton;

  // AIM request containers
  private static String refId = "ref" + System.currentTimeMillis();
  private static BigDecimal totalAmount = new BigDecimal(AppConstant.arrayList_Details.get(0).get("Total"));
  private static CreditCard creditCard;
  private static String refTransId;
  private static String authCode;
  private static Order order;
  private static ShippingCharges shippingCharges;
  private static Customer customer;
  private static ShippingAddress shippingAddress;
  private static EmailReceipt emailReceipt;
  private static HashMap<String, String> merchantDefinedFields = new HashMap<String, String>();

  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    // load merchant info
    setContentView(R.layout.aim_buttons);

    initAuthNet();
    LinearLayout layout = (LinearLayout) findViewById(R.id.AuthOnlyLayout);
    layout.addView(authOnlyButton);
    // footer
//    SDKActivity.addFooter(this,
//        (LinearLayout) findViewById(R.id.AIMFooterLayout),
//        R.string.aim_footer_text);
    
    launchAuthOnlyIntent();
  }

  public void onClick(View dialog) {
	  if (dialog == (AuthNetButton) findViewById(AUTH_ONLY_BUTTON_ID)) {
	      launchAuthOnlyIntent();
	    }
  }

  /**
   * Activity launcher for authOnly transactions.
   * 
   */
  private void launchAuthOnlyIntent() {

    Intent authNetIntent = authNetObj.createAIMAuthOnlyIntent(this, refId,
        totalAmount, creditCard, order, customer, shippingAddress,
        shippingCharges, emailReceipt, merchantDefinedFields);
    authNetIntent.putExtra(AuthNetActivityBase.EXTRA_EMAIL_CUSTOMER, true);
    authNetIntent.putExtra(AuthNetActivityBase.EXTRA_DUPLICATE_TXN_WINDOW_SECS,0);

    launchSubActivity(authNetIntent, createPaymentIntentResultCallback());
  }

  /**
   * Creates a payment intent ResultCallback.
   * 
   * @return ResultCallback
   */
  private ResultCallback createPaymentIntentResultCallback() {
    return new SimpleActivity.ResultCallback() {

      public void resultTransactionCanceled(Enum<?> txnType) {
        txnCanceled((TransactionType) txnType);
      }

      public void resultTransactionFailed(Enum<?> txnType, Result result) {

        net.authorize.aim.Result aimResult = (net.authorize.aim.Result) result;

        StringBuilder errorBuilder = new StringBuilder();
        if (aimResult.getTransactionResponseErrors().size() > 0) {
          for (ResponseReasonCode rrCode : aimResult
              .getTransactionResponseErrors()) {
            ResponseCode rCode = rrCode.getResponseCode();
            errorBuilder.append(rCode.name()).append("(")
                .append(rrCode.getResponseReasonCode()).append("): ")
                .append(rCode.getDescription()).append("\n")
                .append(rrCode.getReasonText()).append("\n");
          }
        }
        txnFailed((TransactionType) txnType, errorBuilder.toString());
      }

      public void resultTransactionSucceeded(Enum<?> txnType, Result result) {

        net.authorize.aim.Result aimResult = (net.authorize.aim.Result) result;
        AIMActivity.refTransId = result != null ? aimResult.getTransId() : "-1";
        AIMActivity.authCode = aimResult.getAuthCode();
        AIMActivity.refTransId = aimResult.getTransId();
        txnSucceeded((TransactionType) txnType, AIMActivity.refTransId);
      }
    };
  }

  /**
   * Initialize the BuyNow button.
   */
  private void initAuthNet() {
    authNetObj = AuthNet.getInstance(Environment.SANDBOX,
            R.layout.authnet_mobile_merchant_auth_dialog, R.id.authnet_loginid_edit,
            R.id.authnet_password_edit, R.id.authnet_auth_cancel_button,
            R.id.authnet_auth_login_button);;

    // create order
    order = Order.createOrder();
    order.setDescription("Note"+AppConstant.arrayList_Items);
   
    order.setPurchaseOrderNumber("200");
    order.setInvoiceNumber(Long.toString(System.currentTimeMillis()));
    order.setTotalAmount(totalAmount);

//    creditCard = CreditCard.createCreditCard();
//    creditCard.setCreditCardNumber("4111111111111111");
//    Calendar expCal = Calendar.getInstance();
//    expCal.add(Calendar.YEAR, 4);
//    creditCard.setExpirationDate(expCal.getTime());
    
    creditCard = CreditCard.createCreditCard();
    creditCard.setCreditCardNumber(""+AppConstant.arrayList_Details.get(0).get("CardNumber"));
    creditCard.setExpirationMonth(""+AppConstant.arrayList_Details.get(0).get("EXPMonth"));
    creditCard.setExpirationYear(""+AppConstant.arrayList_Details.get(0).get("EXPYear"));
    creditCard.setCardCode("100");
 

    merchantDefinedFields = new HashMap<String, String>();
    merchantDefinedFields.put("notes", "150");

    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
        LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
    layoutParams.setMargins(10, 10, 10, 10);
    layoutParams.height = 45;

    // prep the authOnly button
    authOnlyButton = authNetObj.getButton(this,getString(R.string.aim_auth_only), 12);
    authOnlyButton.setLayoutParams(layoutParams);
    authOnlyButton.setId(AUTH_ONLY_BUTTON_ID);
    authOnlyButton.setOnClickListener(this);


  }

  /**
   * Show the transactionID
   * 
   * @param txnType
   * @param transactionID
   */
  private void txnSucceeded(TransactionType txnType, String transactionID) {
    showAlert(this, txnType.getXmlValue() + "\ntransaction succeeded.","Transaction id: " + transactionID);
  }

  /**
   * Show that the payment failed with the errorCode + message.
   * 
   * @param txnType
   * @param errorMessage
   */
  private void txnFailed(TransactionType txnType, String errorMessage) {
    showAlert(this, txnType.getXmlValue() + "\ntransaction failed.", errorMessage);
  }

  /**
   * Show that the payment was canceled.
   * 
   * @param txnType
   */
  private void txnCanceled(TransactionType txnType) {
    showAlert(this, txnType.getXmlValue() + "\ntransaction was canceled.", "");
  }

  /**
   * @return the refTransId
   */
  public static String getRefTransId() {
    return refTransId;
  }

}