package net.authorize.android;

import java.math.BigDecimal;
import java.util.HashMap;

import net.authorize.Environment;
import net.authorize.ResponseReasonCode;
import net.authorize.TransactionType;
import net.authorize.android.model.AppConstant;
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
import android.widget.Toast;

import com.aoneposapp.R;

public class TransationActivity extends SimpleActivity {

	public static AuthNet authNetObj;
	private double total_val;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.setContentView(R.layout.blank);

		authNetObj = AuthNet .getInstance(Environment.SANDBOX,
						R.layout.authnet_mobile_merchant_auth_dialog,
						R.id.authnet_loginid_edit, R.id.authnet_password_edit,
						R.id.authnet_auth_cancel_button,
						R.id.authnet_auth_login_button);
		
//		Intent intent = getIntent();
//		
		String total = "" + AppConstant.arrayList_Details.get(0).get("Total");
		total_val = Double.parseDouble(total);
		launchAuthCaptureIntent();
	}

	/**
	 * Activity launcher for authCapture transactions.
	 */
	private void launchAuthCaptureIntent() {
		String refId = Long.toString(System.currentTimeMillis());
		BigDecimal totalAmount = new BigDecimal(total_val);

		/*
		 * listing these out only to show the required 'parameter set' that
		 * should be passed into the create Intent method.
		 */
		CreditCard creditCard = CreditCard.createCreditCard();

		creditCard.setCreditCardNumber("" + AppConstant.arrayList_Details.get(0).get("CardNumber"));
		creditCard.setExpirationMonth("" + AppConstant.arrayList_Details.get(0).get("EXPMonth"));
		creditCard.setExpirationYear("" + AppConstant.arrayList_Details.get(0).get("EXPYear"));
		creditCard.setCardCode("" + AppConstant.arrayList_Details.get(0).get("Cvv"));

		Order order = Order.createOrder();
		order.setDescription("Note" + AppConstant.arrayList_Items);

		order.setPurchaseOrderNumber("200");
		order.setInvoiceNumber(Long.toString(System.currentTimeMillis()));
		order.setTotalAmount(totalAmount);

		Customer customer = null;
		Address shippingAddress = null;
		ShippingCharges shippingCharges = null;
		EmailReceipt emailReceipt = null;
		HashMap<String, String> merchantDefinedFields = new HashMap<String, String>();
		merchantDefinedFields.put("notes", "sent from SampleActivity");

		System.out.println("merchantDefinedFields" + merchantDefinedFields);

		Intent authNetIntent = authNetObj.createAIMAuthCaptureIntent(this,
				refId, totalAmount, creditCard, order, customer,
				shippingAddress, shippingCharges, emailReceipt,
				merchantDefinedFields);
		/*
		 * final launch command to spawn the Activity and provide a callback
		 * where the result data can be processed
		 */
		System.out.println("AuthNet" + AuthNetActivityBase.EXTRA_DUPLICATE_TXN_WINDOW_SECS);
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

			public void resultTransactionFailed(Enum<?> txnType, Result result) {

				MessageType msgType = MessageType.E00000;
				StringBuilder errorBuilder = new StringBuilder( msgType.getValue()).append(": ").append( msgType.getText());

				net.authorize.aim.Result aimResult = (net.authorize.aim.Result) result;

				errorBuilder = new StringBuilder();
				if (aimResult.getTransactionResponseErrors().size() > 0) {
					for (ResponseReasonCode respReasonCode : aimResult .getTransactionResponseErrors()) {
						errorBuilder.append(respReasonCode.getResponseReasonCode())
								.append(": ")
								.append(respReasonCode.getReasonText())
								.append("\n");
					}
				} else if (aimResult.getMessages().size() > 0) {
					for (MessageType msgType1 : aimResult.getMessages()) {
						errorBuilder.append(msgType1.getValue()).append(": ") .append(msgType1.getText()).append("\n");
					}
				}
				// showAlert((TransactionType) txnType + " failed", errorBuilder.toString());
				Intent returnIntent = new Intent();
				returnIntent.putExtra("result","fail");
				setResult(RESULT_OK,returnIntent);
				finish();
			}

			public void resultTransactionSucceeded(Enum<?> txnType, Result result) {
				net.authorize.aim.Result aimResult = (net.authorize.aim.Result) result;
				System.out.println("success of transation SDK Activity");
//				 showAlert((TransactionType) txnType + " success",
//				 "authCode: " + aimResult.getAuthCode() + "\n" +
//				 "refTransId: " + aimResult.getTransId());
				Toast.makeText(TransationActivity.this, "Transation success", Toast.LENGTH_LONG).show();
			//	finish();
				Intent returnIntent = new Intent();
				returnIntent.putExtra("result","Success");
				setResult(RESULT_OK,returnIntent);
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
