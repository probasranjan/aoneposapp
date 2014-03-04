package com.aoneposapp.utils;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

public class DatabaseForDemo extends SQLiteOpenHelper {
	public static final String DB_NAME = "AonePosDemoDB";
	public static final int DB_VERSION = 4;

	private SQLiteDatabase sqliteDB;

	private static String DB_PATH;
	private final Context mContext;
	private SQLiteDatabase mDataBase;
    public static DatabaseForDemo instance;
	public static final String CATEGORY_TABLE = "category_details";
	public static final String CategoryId = "category_id";
	public static final String CategoryDesp = "category_description";

	public static final String DEPARTMENT_TABLE = "department_details";
	public static final String DepartmentID = "department_id";
	public static final String DepartmentDesp = "department_description";
	public static final String CategoryForDepartment = "category_id";
	public static final String FoodstampableForDept = "department_foodstampable";
	public static final String TaxValForDept = "department_totaltax";

	public static final String DEPARTMENT_PRINTER_COMMANDS = "department_printer_commands";
	public static final String PrinterForDept = "department_printer";
	public static final String TimeForDeptPrint = "department_print_time";

	public static final String COMMANDS_PRINTER_TABLE = "printer_commands_table";
	public static final String COMMANDS_PRINTER_NAME = "printer_name";
	public static final String COMMANDS_ITEM_NAME = "item_time";
	public static final String COMMANDS_TIME = "print_time";
	public static final String COMMANDS_MESSAGE = "message";
	public static final String COMMANDS_HOLDID = "hold_id";

	public static final String UNIQUE_ID = "unique_id";
	public static final String CREATED_DATE = "created_timestamp";
	public static final String MODIFIED_DATE = "modified_timestamp";
	public static final String MODIFIED_IN = "server_local";

	public static final String STOCK_MODIFICATION_TABLE = "stock_modification_history";
	public static final String MDF_ITEM_NO = "item_no";
	public static final String MDF_ITEM_NAME = "item_name";
	public static final String MDF_STOCK_COUNT = "stock_count";
	public static final String MDF_EMP_ID = "employee_id";
	public static final String MDF_STORE_ID = "store_id";
	public static final String MDF_RECIVING_STORE = "reciving_store";
	public static final String MDF_NOTES = "notes";
	
			public static final String MERCHANT_TABLE = "merchant_info_table";
			public static final String MERCHANT_NAME = "merchant_name";
			public static final String MERCHANT_ADDRESS = "merchant_address";
			public static final String MERCHANT_ADDRESS2 = "merchant_address2";
			public static final String MERCHANT_PHONE = "merchant_phone";
			public static final String MERCHANT_ZIP = "merchant_zipcode";
	
	public static final String MERCURY_PAY_TABLE = "mercury_pay_table";
	public static final String MERCURY_PRIMARY_URL = "mercury_primary_url";
	public static final String MERCURY_SECONDARY_URL = "mercury_secondary_url";
	public static final String MERCURY_MERCHANT_ID = "mercury_merchant_id";
	public static final String MERCURY_PASSWORD = "mercury_password";
	
	public static final String ADMIN_TABLE = "admin_details";
	public static final String USERID = "userid";
	public static final String PASSWORD = "password";
	public static final String FIRSTNAME = "firstname";
	public static final String LASTNAME = "lastname";
	public static final String PHONENUMBER = "phonenumber";
	public static final String EMAIL = "email";
	public static final String ADDRESS = "address";
	public static final String SERVER_PASSWORD = "server_password";
	public static final String SECURITY_QUESTION = "security_question";
	public static final String SECURITY_ANSWER = "security_answer";

	public static final String TAX_TABLE = "taxes";
	public static final String TAX_NAME = "taxes_name";
	public static final String TAX_VALUE = "taxes_value";

	public static final String VENDOR_TABLE = "vendor";
	public static final String VENDOR_NO = "vendor_no";
	public static final String VENDOR_FIRST_NAME = "vendor_first_name";
	public static final String VENDOR_LAST_NAME = "vendor_last_name";
	public static final String VENDOR_COMPANY_NAME = "vendor_company_name";
	public static final String VENDOR_TERMS = "vendor_terms";
	public static final String VENDOR_TAX_ID = "vendor_tax_id";
	public static final String VENDOR_FAX_NUMBER = "vendor_fax_number";
	public static final String VENDOR_TELEPHONE_NUMBER = "vendor_telephone_number";
	public static final String VENDOR_STREET_ADDRESS = "vendor_street_address";
	public static final String VENDOR_EXTENDED_ADDRESS = "vendor_extended_address";
	public static final String VENDOR_CITY = "vendor_city";
	public static final String VENDOR_STATE = "vendor_state";
	public static final String VENDOR_ZIP_CODE = "vendor_zip_code";
	public static final String VENDOR_COUNTRY = "vendor_country";
	public static final String VENDOR_FLAT_RENT_RATE = "vendor_flat_rent_rate";
	public static final String VENDOR_MIN_ORDER = "vendor_min_order";
	public static final String VENDOR_PO_DELIVERY_METHOD = "vendor_po_delivery_method";
	public static final String VENDOR_COMMISSION_PERCENT = "vendor_commission_percent";
	public static final String VENDOR_BILLABLE_DEPARTMENT = "vendor_billable_department";
	public static final String VENDOR_SOCIAL_SECURITY_NO = "vendor_social_security_no";
	public static final String VENDOR_EMAIL = "vendor_email";
	public static final String VENDOR_WEBSITE = "vendor_website";

	public static final String CREDITCARD_TABLE = "first_data_card";
	public static final String CREDIT_MERCHANT = "merchant_number";
	public static final String CREDIT_TERMINAL = "terminal_number";
	public static final String CREDIT_PRIMARY_URL = "primary_url";
	public static final String CREDIT_SECONDARY_URL = "secondary_url";
	public static final String CREDIT_USERNAME = "username";
	public static final String CREDIT_DEBITCARD = "password";
	public static final String CREDIT_REQUIRE_CVV2 = "clientno";
	public static final String CREDIT_TIME_OUT = "timeout_seconds";
	public static final String CREDIT_PAYMENT_NAME = "payment_processor_name";

	public static final String PaymentProcessorPreferences = "PaymentProcessorPreferences";
	public static final String PaymentProcessorName = "PaymentProcessorName";
	public static final String PaymentProcessSelectvalue = "PaymentProcessSelectvalue";

	public static final String CHECKED_VALUE = "check_value";

	public static final String PAYMENT_TABLE = "payments";
	public static final String PAYMENT_NAME = "payment_name";
	public static final String PAYMENT_VALUE = "taxenable_value";

	public static final String PENDING_QUERIES_TABLE = "pending_queries";
	public static final String QUERY_TYPE = "query_type";
	public static final String PENDING_USER_ID = "user_id";
	public static final String PAGE_URL = "page_url";
	public static final String PARAMETERS = "parameters";
	public static final String TABLE_NAME_PENDING = "table_name";
	public static final String CURRENT_TIME_PENDING = "current_time";

	public static final String MISCELLANEOUS_TABLE = "miscellaneous";
	public static final String MISCEL_STORE = "selected_store";
	public static final String MISCEL_UPDATE_LOCAL = "local_time";
	public static final String MISCEL_PAGEURL = "server_url";
	public static final String MISCEL_SERVER_UPDATE_LOCAL = "last_server_update_time";

	public static final String INVENTORY_TABLE = "inventorytable";
	public static final String INVENTORY_DEPARTMENT = "inventary_department";
	public static final String INVENTORY_ITEM_NO = "inventory_item_no";
	public static final String INVENTORY_ITEM_NAME = "inventary_item_name";
	public static final String INVENTORY_SECOND_DESCRIPTION = "inventary_second_description";
	public static final String INVENTORY_AVG_COST = "inventary_avg_cost";
	public static final String INVENTORY_PRICE_TAX = "inventary_price_tax";
	public static final String INVENTORY_PRICE_CHANGE = "inventary_price_change";
	public static final String INVENTORY_IN_STOCK = "inventary_in_stock";
	public static final String INVENTORY_QUANTITY = "inventary_quantity";
	public static final String INVENTORY_TAXONE = "inventary_taxone";
	public static final String INVENTORY_VENDOR = "inventary_vendor";
	public static final String INVENTORY_TOTAL_TAX = "inventory_total_tax";
	public static final String INVENTORY_NOTES = "inventary_notes";
	public static final String INVENTORY_CATEGORY = "category_id";

	public static final String PRINTER_TABLE = "printer";
	public static final String PRINTER_TEXT = "headlines";
	public static final String PRINTER_LANGUAGE = "language";
	public static final String PRINTER_UNIT = "unit";
	public static final String PRINTER_SPACING = "spacing";
	public static final String PRINTER_FONT = "fontsize";
	public static final String PRINTER_ALIGN = "align";
	public static final String PRINTER_WSIZE = "Wsize";
	public static final String PRINTER_HSIZE = "Hsize";
	public static final String PRINTER_BOLD = "bold";
	public static final String PRINTER_UNDERLINE = "underline";
	public static final String PRINTER_XPOSITION = "Xposition";
	public static final String PRINTER_NAME = "printname";
	public static final String PRINTER_ID = "print_id";
	public static final String PRINTER_IP = "ipaddress";
	public static final String PRINTER_TYPE = "printer_type";
	
	public static final String STORE_TABLE = "store_details";
	public static final String STORE_NAME = "store_name";
	public static final String STORE_ID = "store_id";
	public static final String STORE_EMAIL = "email";
	public static final String STORE_NUMBER = "phonenumber";
	public static final String STORE_STREET = "street";
	public static final String STORE_CITY = "city";
	public static final String STORE_POSTAL = "postal";
	public static final String STORE_COUNTRY = "country";
	public static final String STORE_STATE = "state";
	public static final String STORE_DEFAULTTAX = "defaulttax";
	public static final String STORE_DISCOUNT = "discount";
	public static final String STORE_CURRENCY = "currency";

	public static final String INVOICE_TOTAL_TABLE = "invoice_total_table";
	public static final String INVOICE_ID = "invoice_id";
	public static final String INVOICE_STORE_ID = "store_id";
	public static final String INVOICE_TOTAL_AMT = "total_amt";
	public static final String INVOICE_STATUS = "status";
	public static final String INVOICE_HOLD_ID = "holdid";
	public static final String INVOICE_PAYMENT_TYPE = "payment_type";
	public static final String INVOICE_TOTAL_AVG = "total_avgcost";
	public static final String INVOICE_PROFIT = "total_profitt";
	public static final String INVOICE_EMPLOYEE = "employee";
	public static final String INVOICE_CHEQUE_NO = "cheque_no";
	public static final String INVOICE_CUSTOMER = "customer";
	public static final String ID = "_id";

	public static final String INVOICE_ITEMS_TABLE = "invoice_items_table";
	public static final String INVOICE_ITEM_ID = "item_id";
	public static final String INVOICE_DISCOUNT = "discount";
	public static final String INVOICE_TAX = "total_tax";
	public static final String INVOICE_ITEM_NAME = "item_name";
	public static final String INVOICE_DISCRIPTION = "item_desscription";
	public static final String INVOICE_YOUR_COST = "price_you_charge";
	public static final String INVOICE_AVG_COST = "avg_cost";
	public static final String INVOICE_QUANTITY = "item_quantity";
	public static final String INVOICE_DEPARTMETNT = "in_department";
	public static final String INVOICE_VENDOR = "in_vendor";

	public static final String EMPLOYEE_TABLE = "employee_table";
	public static final String EMPLOYEE_DEPARTMENT = "employee_department";
	public static final String EMPLOYEE_EMPLOYEE_ID = "employee_employee_id";
	public static final String EMPLOYEE_PASSWORD = "employee_password";
	public static final String EMPLOYEE_DISPLAY_NAME = "employee_display_name";
	public static final String EMPLOYEE_CARD_SWIPE_ID = "employee_card_swipeid";
	public static final String EMPLOYEE_CUSTOMER = "employee_customer";
	public static final String EMPLOYEE_HOURLY_WAGE = "employee_hourly_wage";
	public static final String EMPLOYEE_DISABLE = "employee_disable";
	public static final String EMPLOYEE_CC_TIPS = "employee_cc_tips";
	public static final String EMPLOYEE_ADMIN_CARD = "employee_admin_card";

	public static final String CUSTOMER_EXTENDED_INFO_TABLE = "customer_extended_info_table";
	public static final String CREDIT_CARD_TYPE = "credit_card_type";
	public static final String CREDIT_CARD_NUM = "credit_card_num";
	public static final String EXPIRATION = "expiration";
	public static final String DRIVING_LICENSE = "driving_license";
	public static final String EXP_DATE = "exp_date";
	public static final String CUSTOMER_MOBILE = "customer_mobile";
	public static final String CUSTOMER_FAX = "customer_fax";

	public static final String CUSTOMER_SHIPPING_TABLE = "customer_shipping_table";
	public static final String SHIPPING_FIRST_NAME = "shipping_first_name";
	public static final String SHIPPING_LAST_NAME = "shipping_last_name";
	public static final String SHIPPING_COMPANY_NAME = "shipping_company_name";
	public static final String SHIPPING_PHONE = "shipping_phone";
	public static final String SHIPPING_STREET = "shipping_street";
	public static final String SHIPPING_EXTENDED = "shipping_extended";
	public static final String SHIPPING_CITY = "shipping_city";
	public static final String SHIPPING_STATE = "shipping_state";
	public static final String SHIPPING_COUNTRY = "shipping_country";
	public static final String SHIPPING_ZIPCODE = "shipping_zipcode";

	public static final String CUSTOMER_STORES_TABLE = "customer_stores_table";
	public static final String STORE_ID_CUSTOMER = "store_id_customer";

	public static final String EMP_PERSONAL_TABLE = "employee_personal";
	public static final String EMP_NAME = "emp_name";
	public static final String EMP_ID = "emp_id";
	public static final String EMP_EMAIL = "emp_email";
	public static final String EMP_PHONE = "emp_phone";
	public static final String EMP_BIRTH = "emp_birth";
	public static final String EMP_ADDRESS = "emp_address";
	public static final String EMP_CITY = "emp_city";
	public static final String EMP_COUNTRY = "emp_country";
	public static final String EMP_STATE = "emp_state";
	public static final String EMP_POSTAL = "emp_postal";

	public static final String EMP_STORE_TABLE = "employee_store";
	public static final String EMP_STORE_NAME = "emp_store_name";
	public static final String EMP_STORE_ID = "emp_store_id";

	public static final String SPILT_LOCAL_TABLE = "spilt_local_table";
	public static final String SPILT_LIST = "spilt_list";
	public static final String SPILT_HOLDID = "spilt_holdid";
	public static final String SPILT_HOLDID_UNIQE = "spilt_hold_uniqe";
	
	public static final String EMP_PAYROLL_TABLE = "employee_payroll";
	public static final String EMP_FEDERAL = "federal";
	public static final String EMP_AMOUNT = "amount";
	public static final String EMP_STATEA = "statea";
	public static final String EMP_STATEAMOUNT = "stateAmount";
	public static final String EMP_CREDITS = "credits";
	public static final String EMP_FILLINGSTATUS = "filingstatus";
	public static final String EMP_EXEMPT = "exempt";
	public static final String EMP_EXCLUDECHECK = "excludeCheck";

	public static final String EMP_PERMISSIONS_TABLE = "employee_permissions";
	public static final String EMP_INVENTORY = "emp_inventory";
	public static final String EMP_CUSTOMERS = "emp_customers";
	public static final String EMP_REPORTS = "emp_reports";
	public static final String EMP_DISCOUNTS = "emp_discounts";
	public static final String EMP_SETTINGS = "emp_settings";
	public static final String EMP_PRICE = "emp_price_hanges";
	public static final String EMP_EXIT = "emp_allow_exit";
	public static final String EMP_PAYOUTS = "emp_vendor_payouts";
	public static final String EMP_DELETE = "emp_delete_items";
	public static final String EMP_VOID = "emp_void_invoices";
	public static final String EMP_TRANSACTIONS = "emp_transactions";
	public static final String EMP_HOLDPRINTS = "emp_holdprint";
	public static final String EMP_CREDIT = "emp_creditcards";
	public static final String EMP_ENDCASH = "emp_endcash";

	public static final String PRODUCT_PRINTERS_TABLE = "product_printer_table";
	public static final String PRINTER_VALUE = "printer_value";

	public static final String ORDERING_INFO_TABLE = "ordering_info_table";
	public static final String REORDER_QUANTITY = "reorder_quantity";
	public static final String REORDER_LEVEL = "reorder_level";
	public static final String REORDER_COST = "reorder_cost";
	public static final String VENDERPART_NO = "venderpart_no";
	public static final String COST_PER = "cost_per";
	public static final String CASE_COST = "case_cost";
	public static final String NO_IN_CASE = "no_in_case";
	public static final String TRANSFER_COST_MARKUP = "transfer_cost_markup";
	public static final String ENABLE_MARKUP = "endble_markup";
	public static final String PREFERRED = "preferred";

	public static final String CUSTOMER_TABLE = "customer_table";
	public static final String CUSTOMER_NO = "customer_no";
	public static final String CUSTOMER_FIRST_NAME = "customer_first_name";
	public static final String CUSTOMER_LAST_NAME = "customer_last_name";
	public static final String CUSTOMER_EMAIL = "customer_email";
	public static final String CUSTOMER_NOTES = "customer_notes";

	public static final String ALTERNATE_SKU_TABLE = "alternate_sku";
	public static final String ALTERNATE_SKU_VALUE = "alternate_sku_value";

	public static final String OPTIONAL_INFO_TABLE = "optional_info_table";
	public static final String INVENTORY_MODIFIER_ITEM = "inventary_modifier_item";
	public static final String INVENTORY_COUNT_THIS_ITEM = "inventary_count_this_item";
	public static final String INVENTORY_ALLOW_BUYBACK = "inventary_prompt_quantity";
	public static final String INVENTORY_PROMPT_PRICE = "inventary_prompt_price";
	public static final String INVENTORY_PRINT_ON_RECEIPT = "inventary_print_on_receipt";
	public static final String INVENTORY_FOODSTAMPABLE = "inventary_foodstampable";
	public static final String BONUS_POINTS = "bonus_points";
	public static final String BARCODES = "barcodes";
	public static final String LOCATION = "location";
	public static final String UNIT_SIZE = "unit_size";
	public static final String UNIT_TYPE = "unit_type";
	public static final String COMMISSION_OPTIONAL_INFO = "commission_optional_info";

	public static final String MODIFIER_TABLE = "modifier_table";
	public static final String MODIFIER_ITEM_NO = "modifier_item_no";

	public static final String CUSTOMER_GENERAL_INFO_TABLE = "customer_general_info_table";
	public static final String CUSTOMER_COMPANY_NAME = "customer_company_name";
	public static final String CUSTOMER_PRIMARY_PHONE = "customer_primary_phone";
	public static final String CUSTOMER_ALTERNATE_PHONE = "customer_alternate_phone";
	public static final String CUSTOMER_STREET1 = "customer_street1";
	public static final String CUSTOMER_STREET2 = "customer_street2";
	public static final String CUSTOMER_STATE = "customer_state";
	public static final String CUSTOMER_CITY = "customer_city";
	public static final String CUSTOMER_COUNTRY = "customer_country";
	public static final String CUSTOMER_ZIPCODE = "customer_zipcode";
	public static final String CUSTOMER_BIRTHDAY = "customer_birthday";
	
	public static final String SPLIT_INVOICE_TABLE = "split_invoice_table";
	public static final String SPLIT_INVOICE_ID = "invoice_id";
	public static final String SPLIT_PAYMENT_TYPE = "payment_type";
	public static final String SPLIT_AMOUNT = "amount";
	public static final String SPLIT_CHEQUE_NO = "cheque_no";
	public static final String SPLIT_ACCOUNT_NO = "account_no";
	
	public static final String LOGIN_LOGOUT_TABLE = "login_logout_table";
	public static final String LOGIN_EMPLOYEE_NAME = "login_employee_name";
	public static final String LOGIN_EMPLOYEE_ID = "login_employee_id";
	public static final String LOGIN_TIME = "login_time";
	public static final String LOGOUT_TIME = "logout_time";
	public static final String DIFF_MINUTES = "diff_minutes";
	public static final String DIFF_HOURS = "diff_hours";
	public static final String WAGES = "wages";
	public static final String SESSIONIDVAL = "sessioniduniqueval";

	public DatabaseForDemo(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		// TODO Auto-generated constructor stub
		if (android.os.Build.VERSION.SDK_INT >= 4.2) {
			DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
		} else {
			DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
		}
		this.mContext = context;
	}
	
	 public static synchronized SQLiteOpenHelper getHelper(Context context)
	    {
	        if (instance == null)
	            instance = new DatabaseForDemo(context);
	        return instance;
	    }
	@Override
	public void onCreate(android.database.sqlite.SQLiteDatabase db) {
		// TODO Auto-generated method stub

		String loginlogouttime = "create table if not exists " + LOGIN_LOGOUT_TABLE
				+ " ( " + BaseColumns._ID
				+ " integer primary key autoincrement, " + LOGIN_EMPLOYEE_NAME
				+ " text, " + LOGIN_EMPLOYEE_ID + " text, " + LOGIN_TIME
				+ " text, " + LOGOUT_TIME + " text, " + WAGES+ " text, "+ DIFF_MINUTES
				+ " text, "+ DIFF_HOURS+ " text, "+SESSIONIDVAL+" text,"+ UNIQUE_ID + " INTEGER, " + CREATED_DATE
				+ " text, " + MODIFIED_DATE + " text, " + MODIFIED_IN
				+ " text);";
		
		String customerdetails = "create table if not exists " + CUSTOMER_TABLE
				+ " ( " + ID + " integer primary key autoincrement, "
				+ UNIQUE_ID + " text, " + CREATED_DATE + " text, "
				+ MODIFIED_DATE + " text, " + MODIFIED_IN + " text, "
				+ CUSTOMER_NO + " text, " + CUSTOMER_LAST_NAME + " text, "
				+ CUSTOMER_EMAIL + " text, " + CUSTOMER_NOTES + " text, "
				+ CUSTOMER_FIRST_NAME + " text);";
		
		String emp_store = "create table if not exists " + EMP_STORE_TABLE
				+ " ( " + BaseColumns._ID
				+ " integer primary key autoincrement, " + EMPLOYEE_EMPLOYEE_ID
				+ " text, " + EMP_STORE_ID + " text, " + EMP_STORE_NAME
				+ " text, " + UNIQUE_ID + " INTEGER, " + CREATED_DATE
				+ " text, " + MODIFIED_DATE + " text, " + MODIFIED_IN
				+ " text);";

		String printer_commdands_dept = "create table if not exists "
				+ DEPARTMENT_PRINTER_COMMANDS + " ( " + BaseColumns._ID
				+ " integer primary key autoincrement, " + DepartmentID
				+ " text, " + PrinterForDept + " text, " + TimeForDeptPrint
				+ " text, " + UNIQUE_ID + " INTEGER, " + CREATED_DATE
				+ " text, " + MODIFIED_DATE + " text, " + MODIFIED_IN
				+ " text);";

		String printer_commdands = "create table if not exists "
				+ COMMANDS_PRINTER_TABLE + " ( " + BaseColumns._ID
				+ " integer primary key autoincrement, " + COMMANDS_ITEM_NAME
				+ " text, " + UNIQUE_ID + " text, " + COMMANDS_PRINTER_NAME
				+ " text, " + COMMANDS_TIME + " text, " + COMMANDS_HOLDID
				+ " text, " + COMMANDS_MESSAGE + " text);";
		
		String split_local = "create table if not exists "
				+ SPILT_LOCAL_TABLE + " ( " + BaseColumns._ID
				+ " integer primary key autoincrement, " + SPILT_LIST
				+ " text, " + SPILT_HOLDID + " text, " + SPILT_HOLDID_UNIQE + " text);";
		
		String employeePermissionsDetails = "create table if not exists "
				+ EMP_PERMISSIONS_TABLE + " ( " + BaseColumns._ID
				+ " integer primary key autoincrement, " + UNIQUE_ID
				+ " text, " + CREATED_DATE + " text, " + MODIFIED_DATE
				+ " text, " + MODIFIED_IN + " text, " + EMP_INVENTORY
				+ " text, " + EMPLOYEE_EMPLOYEE_ID + " text, " + EMP_CUSTOMERS
				+ " text, " + EMP_REPORTS + " text, " + EMP_DISCOUNTS
				+ " text, " + EMP_SETTINGS + " text, " + EMP_PRICE + " text, "
				+ EMP_EXIT + " text, " + EMP_PAYOUTS + " text, " + EMP_DELETE
				+ " text, " + EMP_VOID + " text, " + EMP_TRANSACTIONS
				+ " text, " + EMP_HOLDPRINTS + " text, " + EMP_CREDIT
				+ " text, " + EMP_ENDCASH + " text);";

		String printerDetails = "create table if not exists " + PRINTER_TABLE
				+ " ( " + BaseColumns._ID
				+ " integer primary key autoincrement, " + PRINTER_TEXT
				+ " text, " + PRINTER_FONT + " text, " + PRINTER_ALIGN
				+ " text, " + PRINTER_SPACING + " text, " + PRINTER_LANGUAGE
				+ " text, " + PRINTER_WSIZE + " text, " + PRINTER_HSIZE
				+ " text, " + PRINTER_BOLD + " text, " + PRINTER_UNDERLINE
				+ " text, " + PRINTER_XPOSITION + " text, " + PRINTER_UNIT
				+ " text, " + PRINTER_NAME + " text, " + PRINTER_IP + " text, "
				+ PRINTER_ID + " text, " + PRINTER_TYPE+ " text);";

		String employeePayrollDetails = "create table if not exists "
				+ EMP_PAYROLL_TABLE + " ( " + BaseColumns._ID
				+ " integer primary key autoincrement, " + UNIQUE_ID
				+ " text, " + CREATED_DATE + " text, " + MODIFIED_DATE
				+ " text, " + MODIFIED_IN + " text, " + EMP_FEDERAL + " text, "
				+ EMPLOYEE_EMPLOYEE_ID + " text, " + EMP_AMOUNT + " text, "
				+ EMP_STATEA + " text, " + EMP_STATEAMOUNT + " text, "
				+ EMP_CREDITS + " text, " + EMP_FILLINGSTATUS + " text, "
				+ EMP_EXEMPT + " text, " + EMP_EXCLUDECHECK + " text);";

		String employeePersonalDetails = "create table if not exists "
				+ EMP_PERSONAL_TABLE + " ( " + BaseColumns._ID
				+ " integer primary key autoincrement, " + UNIQUE_ID
				+ " text, " + CREATED_DATE + " text, " + MODIFIED_DATE
				+ " text, " + MODIFIED_IN + " text, " + EMP_NAME + " text, "
				+ EMPLOYEE_EMPLOYEE_ID + " text, " + EMP_ID + " text, "
				+ EMP_EMAIL + " text, " + EMP_PHONE + " text, " + EMP_BIRTH
				+ " text, " + EMP_ADDRESS + " text, " + EMP_CITY + " text, "
				+ EMP_COUNTRY + " text, " + EMP_STATE + " text, " + EMP_POSTAL
				+ " text);";

		String employeeDetails = "create table if not exists " + EMPLOYEE_TABLE
				+ " ( " + BaseColumns._ID
				+ " integer primary key autoincrement, " + UNIQUE_ID
				+ " text, " + CREATED_DATE + " text, " + MODIFIED_DATE
				+ " text, " + MODIFIED_IN + " text, " + EMPLOYEE_DEPARTMENT
				+ " text, " + EMPLOYEE_EMPLOYEE_ID + " text, "
				+ EMPLOYEE_PASSWORD + " text, " + EMPLOYEE_DISPLAY_NAME
				+ " text, " + EMPLOYEE_CARD_SWIPE_ID + " text, "
				+ EMPLOYEE_CUSTOMER + " text, " + EMPLOYEE_HOURLY_WAGE
				+ " text, " + EMPLOYEE_DISABLE + " text, " + EMPLOYEE_CC_TIPS + " text, "
				+ EMPLOYEE_ADMIN_CARD + " text, " + SECURITY_QUESTION
				+ " text, " + SECURITY_ANSWER + " text, " + SERVER_PASSWORD
				+ " text);";
	
		
		
		String invoice_items = "create table if not exists "
				+ INVOICE_ITEMS_TABLE + " ( " + BaseColumns._ID
				+ " integer primary key autoincrement, " + INVOICE_ID
				+ " text, " + INVOICE_ITEM_ID + " text, " + INVOICE_DISCOUNT
				+ " text, " + INVOICE_TAX + " text, " + INVOICE_ITEM_NAME
				+ " text, " + INVOICE_AVG_COST + " text, " + INVOICE_YOUR_COST
				+ " text, " + UNIQUE_ID + " text, " + CREATED_DATE + " text, "
				+ INVOICE_QUANTITY + " text, " + INVOICE_PAYMENT_TYPE
				+ " text, " + INVOICE_DISCRIPTION + " text, "
				+ INVOICE_DEPARTMETNT + " text, " + INVOICE_VENDOR + " text, "
				+ INVOICE_STORE_ID + " text, " + INVOICE_STATUS  + " text, " + INVOICE_EMPLOYEE
				+ " text, " + INVOICE_CUSTOMER + " text);";

		String invoice_total = "create table if not exists "
				+ INVOICE_TOTAL_TABLE + " ( " + BaseColumns._ID
				+ " integer primary key autoincrement, " + INVOICE_ID
				+ " text, " + INVOICE_PROFIT + " text, " + INVOICE_TOTAL_AMT
				+ " text, " + INVOICE_STATUS + " text, " + INVOICE_EMPLOYEE
				+ " text, " + INVOICE_CUSTOMER + " text, "
				+ INVOICE_PAYMENT_TYPE + " text, " + UNIQUE_ID + " text, "
				+ CREATED_DATE + " text, " + INVOICE_TOTAL_AVG + " text, "
				+ INVOICE_HOLD_ID + " text, " + INVOICE_CHEQUE_NO + " text, " + INVOICE_STORE_ID + " text);";

		String dp_details = "create table if not exists " + PaymentProcessorPreferences
				+ " ( " + PaymentProcessorName + " text , " + PaymentProcessSelectvalue
				+ " text);";
		
		String insertpaymentprocess = "insert into "+PaymentProcessorPreferences+" ("+PaymentProcessorName+", "+PaymentProcessSelectvalue
				+") values(\"First Data\", \"First Data\")";

		/*String item_check_details = "create table if not exists "
				+ ITEM_CHECKBOX_TABLE + " ( " + ITEM_ITEM_NO
				+ "  text primary key , " + ITEM_ITEM_VALUE + " text);";*/

		String PaymentDetails = "create table if not exists " + PAYMENT_TABLE
				+ " ( " + BaseColumns._ID
				+ " integer primary key autoincrement, " + UNIQUE_ID
				+ " INTEGER, " + CREATED_DATE + " text, " + MODIFIED_DATE
				+ " text, " + MODIFIED_IN + " text, " + PAYMENT_NAME
				+ " text, " + PAYMENT_VALUE + " text);";

		String CreditCardDetails = "create table if not exists "
				+ CREDITCARD_TABLE + " ( " + BaseColumns._ID
				+ " integer primary key autoincrement, " + UNIQUE_ID
				+ " INTEGER, " + CREATED_DATE + " text, " + MODIFIED_DATE
				+ " text, " + MODIFIED_IN + " text, " + CREDIT_PRIMARY_URL
				+ " text, " + CREDIT_SECONDARY_URL + " text, "+ CREDIT_USERNAME 
				+ " text, "+ CREDIT_TIME_OUT + " text, "
				+ CREDIT_MERCHANT + " text, " + CREDIT_PAYMENT_NAME + " text, " + CREDIT_TERMINAL + " text, "
				+ CREDIT_DEBITCARD + " text, " + CREDIT_REQUIRE_CVV2
				+ " text);";

		/*String customerdetails = "create table if not exists " + CUSTOMER_TABLE
				+ " ( " + ID + " integer primary key autoincrement, "
				+ UNIQUE_ID + " text, " + CREATED_DATE + " text, "
				+ MODIFIED_DATE + " text, " + MODIFIED_IN + " text, "
				+ CUSTOMER_NO + " text, " + CUSTOMER_LAST_NAME + " text, "
				+ CUSTOMER_EMAIL + " text, " + CUSTOMER_NOTES + " text, "
				+ CUSTOMER_FIRST_NAME + " text);";*/

		String customergeneralinfodetails = "create table if not exists "
				+ CUSTOMER_GENERAL_INFO_TABLE + " ( " + ID
				+ " integer primary key autoincrement, " + UNIQUE_ID
				+ " text, " + CREATED_DATE + " text, " + MODIFIED_DATE
				+ " text, " + MODIFIED_IN + " text, " + CUSTOMER_COMPANY_NAME
				+ " text, " + CUSTOMER_PRIMARY_PHONE + " text, "
				+ CUSTOMER_ALTERNATE_PHONE + " text, " + CUSTOMER_STREET1
				+ " text, " + CUSTOMER_STREET2 + " text, " + CUSTOMER_STATE
				+ " text, " + CUSTOMER_CITY + " text, " + CUSTOMER_COUNTRY
				+ " text, " + CUSTOMER_ZIPCODE + " text, " + CUSTOMER_BIRTHDAY
				+ " text, " + CUSTOMER_NO + " text);";

		String StoreDetails = "create table if not exists " + STORE_TABLE
				+ " ( " + BaseColumns._ID
				+ " integer primary key autoincrement, " + STORE_NAME
				+ " text, " + STORE_ID + " text, " + STORE_EMAIL + " text, "
				+ STORE_NUMBER + " text, " + STORE_STREET + " text, "
				+ STORE_CITY + " text, " + STORE_POSTAL + " text, "
				+ STORE_COUNTRY + " text, " + STORE_STATE + " text, "
				+ STORE_DEFAULTTAX + " text, " + STORE_DISCOUNT + " text, "
				+ STORE_CURRENCY + " text, " + UNIQUE_ID + " text, "
				+ CREATED_DATE + " text, " + MODIFIED_DATE + " text, "
				+ MODIFIED_IN + " text);";

		String customeredtendedinfodetails = "create table if not exists "
				+ CUSTOMER_EXTENDED_INFO_TABLE + " ( " + ID
				+ " integer primary key autoincrement, " + UNIQUE_ID
				+ " text, " + CREATED_DATE + " text, " + MODIFIED_DATE
				+ " text, " + MODIFIED_IN + " text, " + CREDIT_CARD_TYPE
				+ " text, " + CREDIT_CARD_NUM + " text, " + EXPIRATION
				+ " text, " + DRIVING_LICENSE + " text, " + EXP_DATE
				+ " text, " + CUSTOMER_MOBILE + " text, " + CUSTOMER_FAX
				+ " text, " + CUSTOMER_NO + " text);";

		String customershippingdetails = "create table if not exists "
				+ CUSTOMER_SHIPPING_TABLE + " ( " + ID
				+ " integer primary key autoincrement, " + UNIQUE_ID
				+ " text, " + CREATED_DATE + " text, " + MODIFIED_DATE
				+ " text, " + MODIFIED_IN + " text, " + SHIPPING_FIRST_NAME
				+ " text, " + SHIPPING_LAST_NAME + " text, "
				+ SHIPPING_COMPANY_NAME + " text, " + SHIPPING_PHONE
				+ " text, " + SHIPPING_STREET + " text, " + SHIPPING_EXTENDED
				+ " text, " + SHIPPING_CITY + " text, " + SHIPPING_STATE
				+ " text, " + SHIPPING_COUNTRY + " text, " + SHIPPING_ZIPCODE
				+ " text, " + CUSTOMER_NO + " text);";

		String customerstoresdetails = "create table if not exists "
				+ CUSTOMER_STORES_TABLE + " ( " + ID
				+ " integer primary key autoincrement, " + UNIQUE_ID
				+ " text, " + CREATED_DATE + " text, " + MODIFIED_DATE
				+ " text, " + MODIFIED_IN + " text, " + CUSTOMER_NO + " text, "
				+ STORE_ID_CUSTOMER + " text);";

		String productprinterdetails = "create table if not exists "
				+ PRODUCT_PRINTERS_TABLE + " ( " + ID
				+ " integer primary key autoincrement, " + UNIQUE_ID
				+ " text, " + CREATED_DATE + " text, " + MODIFIED_DATE
				+ " text, " + MODIFIED_IN + " text, " + INVENTORY_ITEM_NO
				+ " text, " + PRINTER_VALUE + " text);";

		String Product_ordering_info_Details = "create table if not exists "
				+ ORDERING_INFO_TABLE + " ( " + ID
				+ " integer primary key autoincrement, " + VENDERPART_NO
				+ " text, " + VENDOR_COMPANY_NAME + " text, " + COST_PER
				+ " text, " + CASE_COST + " text, " + NO_IN_CASE + " text, "
				+ PREFERRED + " text, " + UNIQUE_ID + " text, " + CREATED_DATE
				+ " text, " + INVENTORY_ITEM_NO + " text, " + MODIFIED_IN
				+ " text, " + MODIFIED_DATE + " text);";

		String AdminDetails = "create table if not exists " + ADMIN_TABLE
				+ " ( " + ID + " integer primary key autoincrement, " + USERID
				+ " text, " + PASSWORD + " text, " + FIRSTNAME + " text, "
				+ LASTNAME + " text, " + PHONENUMBER + " text, " + EMAIL
				+ " text, " + ADDRESS + " text, " + SERVER_PASSWORD + " text, "
				+ UNIQUE_ID + " text, " + CREATED_DATE + " text, "
				+ SECURITY_QUESTION + " text, " + SECURITY_ANSWER + " text, "
				+ MODIFIED_IN + " text, " + MODIFIED_DATE + " text);";

		String insertDemoRecord = "insert into "
				+ ADMIN_TABLE
				+ "("
				+ USERID
				+ ","
				+ PASSWORD
				+ ","
				+ FIRSTNAME
				+ ","
				+ LASTNAME
				+ ","
				+ PHONENUMBER
				+ ","
				+ EMAIL
				+ ","
				+ ADDRESS
				+ ","
				+ UNIQUE_ID
				+ ","
				+ CREATED_DATE
				+ ","
				+ MODIFIED_DATE
				+ ") values (\"01\", \"admin\", \"momidala\", \"padmavathi\", \"1234567890\", "
				+ "\"xyz@gmail.com\", \"hyderabad\", 123456789012345, \"2013-20-12 10-12\", \"2013-20-12 10-12\")";
		
		

		String TaxDetails = "create table if not exists " + TAX_TABLE + " ( "
				+ ID + " integer primary key autoincrement, " + UNIQUE_ID
				+ " text, " + CREATED_DATE + " text, " + MODIFIED_DATE
				+ " text, " + MODIFIED_IN + " text, " + TAX_NAME + " text, "
				+ TAX_VALUE + " INTEGER);";

		String alternateskudetails = "create table if not exists "
				+ ALTERNATE_SKU_TABLE + " ( " + ID
				+ " integer primary key autoincrement, " + UNIQUE_ID
				+ " text, " + CREATED_DATE + " text, " + MODIFIED_DATE
				+ " text, " + MODIFIED_IN + " text, " + INVENTORY_ITEM_NO
				+ " text, " + ALTERNATE_SKU_VALUE + " text);";

		String modifierdetails = "create table if not exists " + MODIFIER_TABLE
				+ " ( " + ID + " integer primary key autoincrement, "
				+ UNIQUE_ID + " text, " + CREATED_DATE + " text, "
				+ MODIFIED_DATE + " text, " + MODIFIED_IN + " text, "
				+ INVENTORY_ITEM_NO + " text, " + INVENTORY_ITEM_NAME
				+ " text, " + MODIFIER_ITEM_NO + " text);";

		String CategoryDetails = "create table if not exists " + CATEGORY_TABLE
				+ " ( " + ID + " integer primary key autoincrement, "
				+ UNIQUE_ID + " text, " + CREATED_DATE + " text, "
				+ MODIFIED_DATE + " text, " + MODIFIED_IN + " text, "
				+ CategoryId + " text, " + CategoryDesp + " text);";

		String DepartmentDetails = "create table if not exists "
				+ DEPARTMENT_TABLE + " ( " + ID
				+ " integer primary key autoincrement, " + UNIQUE_ID
				+ " text, " + CREATED_DATE + " text, " + MODIFIED_DATE
				+ " text, " + MODIFIED_IN + " text, " + DepartmentID
				+ " text, " + DepartmentDesp + " text, " + FoodstampableForDept
				+ " text, " + TaxValForDept + " text, " + CHECKED_VALUE
				+ " text, " + CategoryForDepartment + " text);";

		String Product_Optional_Info_Details = "create table if not exists "
				+ OPTIONAL_INFO_TABLE + " ( " + ID
				+ " integer primary key autoincrement, " + UNIQUE_ID
				+ " text, " + CREATED_DATE + " text, " + MODIFIED_DATE
				+ " text, " + MODIFIED_IN + " text, " + BONUS_POINTS
				+ " text, " + BARCODES + " text, " + LOCATION + " text, "
				+ UNIT_SIZE + " text, " + UNIT_TYPE + " text, "
				+ INVENTORY_ITEM_NO + " text, " + COMMISSION_OPTIONAL_INFO
				+ " text, " + INVENTORY_MODIFIER_ITEM + " text, "
				+ INVENTORY_COUNT_THIS_ITEM + " text, "
				+ INVENTORY_ALLOW_BUYBACK + " text, " + INVENTORY_PROMPT_PRICE
				+ " text, " + INVENTORY_PRINT_ON_RECEIPT + " text, "
				+ INVENTORY_FOODSTAMPABLE + " text);";

		String VendorDetails = "create table if not exists " + VENDOR_TABLE
				+ " ( " + ID + " integer primary key autoincrement, "
				+ VENDOR_NO + " text, " + VENDOR_COMPANY_NAME + " text, "
				+ VENDOR_PO_DELIVERY_METHOD + " text, " + VENDOR_TERMS
				+ " text, " + VENDOR_FLAT_RENT_RATE + " text, " + VENDOR_TAX_ID
				+ " text, " + VENDOR_MIN_ORDER + " text, "
				+ VENDOR_COMMISSION_PERCENT + " text, "
				+ VENDOR_BILLABLE_DEPARTMENT + " text, "
				+ VENDOR_SOCIAL_SECURITY_NO + " text, " + VENDOR_STREET_ADDRESS
				+ " text, " + VENDOR_EXTENDED_ADDRESS + " text, " + VENDOR_CITY
				+ " text, " + VENDOR_STATE + " text, " + VENDOR_ZIP_CODE
				+ " text, " + VENDOR_COUNTRY + " text, " + VENDOR_FIRST_NAME
				+ " text, " + VENDOR_LAST_NAME + " text, "
				+ VENDOR_TELEPHONE_NUMBER + " text, " + VENDOR_FAX_NUMBER
				+ " text, " + VENDOR_EMAIL + " text, " + VENDOR_WEBSITE
				+ " text, " + UNIQUE_ID + " text, " + CREATED_DATE + " text, "
				+ MODIFIED_DATE + " text, " + MODIFIED_IN + " text);";

		String inventoryDetails = "create table if not exists "
				+ INVENTORY_TABLE + " ( " + ID
				+ " integer primary key autoincrement, " + INVENTORY_ITEM_NAME
				+ " text, " + INVENTORY_DEPARTMENT + " text, "
				+ INVENTORY_ITEM_NO + " text, " + INVENTORY_AVG_COST
				+ " text, " + INVENTORY_IN_STOCK + " text, "
				+ INVENTORY_PRICE_CHANGE + " text, " + INVENTORY_PRICE_TAX
				+ " text, " + INVENTORY_QUANTITY + " text, " + INVENTORY_TAXONE
				+ " text, " + INVENTORY_VENDOR + " text, " + CHECKED_VALUE
				+ " text, " + INVENTORY_SECOND_DESCRIPTION + " text, "
				+ INVENTORY_CATEGORY + " text, " + UNIQUE_ID + " text, "
				+ INVENTORY_TOTAL_TAX + " text, " + CREATED_DATE + " text, "
				+ MODIFIED_DATE + " text, " + INVENTORY_NOTES + " text, "
				+ MODIFIED_IN + " text);";

		String pendingqueries = "create table if not exists "
				+ PENDING_QUERIES_TABLE + " ( " + BaseColumns._ID
				+ " integer primary key autoincrement, " + QUERY_TYPE
				+ " text, " + PENDING_USER_ID + " text, " + PAGE_URL
				+ " text, " + TABLE_NAME_PENDING + " text, "
				+ CURRENT_TIME_PENDING + " text, " + PARAMETERS + " text);";

		String miscellaneousDetails = "create table if not exists "
				+ MISCELLANEOUS_TABLE + " ( " + BaseColumns._ID
				+ " integer primary key autoincrement, " + MISCEL_STORE
				+ " text, " + MISCEL_PAGEURL + " text, " + MISCEL_UPDATE_LOCAL
				+ " text, " + MISCEL_SERVER_UPDATE_LOCAL + " text);";

		String split_table = "create table if not exists " + SPLIT_INVOICE_TABLE
				+ " ( " + SPLIT_INVOICE_ID
				+ " text, " + SPLIT_PAYMENT_TYPE + " text, " + SPLIT_AMOUNT
				+ " text, " + SPLIT_ACCOUNT_NO + " text, " + SPLIT_CHEQUE_NO + " INTEGER, " + CREATED_DATE
				+ " text, " + MODIFIED_DATE + " text, " + UNIQUE_ID + " text, " + MODIFIED_IN
				+ " text);";
		
		db.execSQL(split_table);
		db.execSQL(AdminDetails);
		db.execSQL(pendingqueries);
		db.execSQL(miscellaneousDetails);
		db.execSQL(insertDemoRecord);
		db.execSQL(TaxDetails);
		db.execSQL(DepartmentDetails);
		db.execSQL(CategoryDetails);
		db.execSQL(VendorDetails);
		db.execSQL(inventoryDetails);
		db.execSQL(Product_Optional_Info_Details);
		db.execSQL(Product_ordering_info_Details);
		db.execSQL(alternateskudetails);
		db.execSQL(productprinterdetails);
		db.execSQL(modifierdetails);
		db.execSQL(customerdetails);
		db.execSQL(customergeneralinfodetails);
		db.execSQL(customeredtendedinfodetails);
		db.execSQL(customershippingdetails);
		db.execSQL(customerstoresdetails);
		db.execSQL(emp_store);
		db.execSQL(CreditCardDetails);
		db.execSQL(employeePermissionsDetails);
		db.execSQL(employeePayrollDetails);
		db.execSQL(employeePersonalDetails);
		db.execSQL(employeeDetails);
		db.execSQL(invoice_total);
		db.execSQL(invoice_items);
		db.execSQL(dp_details);
		db.execSQL(PaymentDetails);
		db.execSQL(printerDetails);
		db.execSQL(StoreDetails);
		db.execSQL(printer_commdands_dept);
		db.execSQL(printer_commdands);
		db.execSQL(split_local);
		db.execSQL(insertpaymentprocess);
		db.execSQL(loginlogouttime);
	}

	@Override
	public void onUpgrade(android.database.sqlite.SQLiteDatabase db,
			int oldVersion, int newVersion) {
		// TODO Auto-generated method stub
		if(oldVersion<4){
			Log.e("new version exqt in database","rao");
			try{
			String modification_sock = "create table if not exists "
					+ STOCK_MODIFICATION_TABLE + " ( " + ID
					+ " integer primary key autoincrement, " + UNIQUE_ID
					+ " text, " + CREATED_DATE + " text, " + MODIFIED_DATE
					+ " text, " + MODIFIED_IN + " text, " + MDF_ITEM_NO
					+ " text, " + MDF_ITEM_NAME + " text, " + MDF_STOCK_COUNT
					+ " text, " + MDF_EMP_ID + " text, " + MDF_STORE_ID + " text, " + MDF_RECIVING_STORE
					+ " text, " + MDF_NOTES + " text);";
			
			String mercury_details = "create table if not exists "
					+ MERCURY_PAY_TABLE + " ( " + ID
					+ " integer primary key autoincrement, " + UNIQUE_ID
					+ " text, " + CREATED_DATE + " text, " + MODIFIED_DATE
					+ " text, " + MODIFIED_IN + " text, " + MERCURY_PRIMARY_URL
					+ " text, " + MERCURY_SECONDARY_URL + " text, " + MERCURY_MERCHANT_ID
					+ " text, " + MERCURY_PASSWORD + " text);";
		
			String merchant_details=  "create table if not exists "
					+ MERCHANT_TABLE + " ( " + ID
					+ " integer primary key autoincrement, " + UNIQUE_ID
					+ " text, " + CREATED_DATE + " text, " + MODIFIED_DATE
					+ " text, " + MODIFIED_IN + " text, " + MERCHANT_NAME
					+ " text, " + MERCHANT_ADDRESS
					+ " text, " + MERCHANT_ADDRESS2
					+ " text, " + MERCHANT_PHONE
					+ " text, " + MERCHANT_ZIP + " text);";
					
			db.execSQL(mercury_details);
			db.execSQL(merchant_details);
			db.execSQL(modification_sock);
//				String invoie_total="ALTER TABLE "+INVOICE_TOTAL_TABLE+" ADD COLUMN "+MODIFIED_DATE+" text;";
//			String invoie_item="ALTER TABLE "+INVOICE_ITEMS_TABLE+" ADD COLUMN "+MODIFIED_DATE+" text;";
//			db.execSQL(invoie_total);
//			db.execSQL(invoie_item);
			}catch(SQLiteException exception){
				exception.getLocalizedMessage();
			}
		}
	}

	// Open Database
	public void open() {
		sqliteDB = getWritableDatabase();

	}

	// Close Database
//	@Override
	public void close() {
		if (sqliteDB != null)
			sqliteDB.close();
	}

	public List<Inventory> getAllInventoryList(String itemNo,
			String department, String itemName, String searchItemName) {
		List<Inventory> inventoryList = new ArrayList<Inventory>();

		// Select All Query
try {
		String selectQuery = null;

		if (itemNo != null) {
			selectQuery = "SELECT  * FROM " + INVENTORY_TABLE + " WHERE "
					+ INVENTORY_ITEM_NO + "=\"" + itemNo + "\";";
		} else if (department != null) {
			selectQuery = "SELECT  * FROM " + INVENTORY_TABLE + " WHERE "
					+ INVENTORY_DEPARTMENT + "=\"" + department + "\";";
		} else if (itemName != null) {
			selectQuery = "SELECT  * FROM " + INVENTORY_TABLE + " WHERE "
					+ INVENTORY_ITEM_NAME + "=\"" + itemName + "\";";
		} else if (searchItemName != null) {
			selectQuery = "SELECT  * FROM " + INVENTORY_TABLE + " WHERE "
					+ INVENTORY_ITEM_NAME + " LIKE \"" + searchItemName + "%\";";
		} else {
			selectQuery = "SELECT  * FROM " + INVENTORY_TABLE;
		}

		SQLiteDatabase dbabcd = getReadableDatabase();
		Cursor cursor = dbabcd.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Inventory inventory = new Inventory();
				
				inventory.setDepartmentAdd(cursor.getString(cursor
						.getColumnIndex(INVENTORY_DEPARTMENT)));
				inventory.setItemNoAdd(cursor.getString(cursor
						.getColumnIndex(INVENTORY_ITEM_NO)));
				inventory.setInventoryVndr(cursor.getString(cursor
						.getColumnIndex(INVENTORY_VENDOR)));
				inventory.setItemNameAdd(cursor.getString(cursor
						.getColumnIndex(INVENTORY_ITEM_NAME)));
				inventory.setAvgCost(cursor.getString(cursor
						.getColumnIndex(INVENTORY_AVG_COST)));
				
				inventory.setPriceTax(cursor.getString(cursor
						.getColumnIndex(INVENTORY_PRICE_TAX)));
				inventory.setPriceYouChange(cursor.getString(cursor
						.getColumnIndex(INVENTORY_PRICE_CHANGE)));
				Log.v("INVENTORY_PRICE_CHANGE",""+cursor.getString(cursor
						.getColumnIndex(INVENTORY_PRICE_CHANGE)));
				inventory.setInStock(cursor.getString(cursor
						.getColumnIndex(INVENTORY_IN_STOCK)));
				inventory.setQuantity(cursor.getString(cursor
						.getColumnIndex(INVENTORY_QUANTITY)));
				inventory.setInventoryTaxOne(cursor.getString(cursor
						.getColumnIndex(INVENTORY_TAXONE)));
				inventory.setSecondDescription(cursor.getString(cursor
						.getColumnIndex(INVENTORY_SECOND_DESCRIPTION)));
				Double mSubTotal=0.0;
				try{
				 mSubTotal = Double.valueOf(cursor.getString(cursor
						.getColumnIndex(INVENTORY_PRICE_CHANGE)));
			} catch (NumberFormatException e) {
			    e.getLocalizedMessage();
			}
				String texss=cursor.getString(cursor
						.getColumnIndex(INVENTORY_TAXONE));
				String[] parts = texss.split(",");
				String part1 = parts[0]; 
				Log.v(""+part1,"" +"  hari   "+parts.length);
				double tax=0.0;
				for(int t=0;t<parts.length;t++){
					String ttt=parts[t]; 
					Log.e("",""+ttt);
					String query = "select * from "
							+ DatabaseForDemo.TAX_TABLE + " where "
							+ DatabaseForDemo.TAX_NAME + "=\""
							+ ttt + "\"";
					System.out.println(query);
					Cursor cursortax = dbabcd.rawQuery(query, null);
					if (cursortax != null) {
						if (cursortax.moveToFirst()) {
							do {
								double taxvalpercent =0.0;
								try{
									taxvalpercent =cursortax.getDouble(cursortax
												.getColumnIndex(DatabaseForDemo.TAX_VALUE));
								} catch (NumberFormatException e) {
								    e.getLocalizedMessage();
								}
								System.out.println("taxvalpercent     " + taxvalpercent);
								tax += taxvalpercent;
								System.out.println(" tax    " + tax);
							} while (cursortax.moveToNext());
						}
						cursortax.close();
				}
				}
				tax = (mSubTotal * tax) / 100;
				DecimalFormat df = new DecimalFormat("#.##");
				inventory.setInventoryTaxTotal(df.format(tax));
				inventoryList.add(inventory);
			} while (cursor.moveToNext());
		}

		// closing connection
		cursor.close();
		dbabcd.close();

		// returning lables
		Log.v("fgdgd", ""+inventoryList);
		
}catch(Exception e){
	e.getLocalizedMessage();
}
return inventoryList;
	}
	
	public List<Inventory> getAllInventoryListForName(String itemNo,
			String department, String itemName, String searchItemName,String aaaName) {
		List<Inventory> inventoryList = new ArrayList<Inventory>();
		try{
		String selectQuery = null;

		if (itemNo != null) {
			selectQuery = "SELECT  * FROM " + INVENTORY_TABLE + " WHERE "
					+ INVENTORY_ITEM_NO + "=\"" + itemNo + "\";";
		} else if (department != null) {
			selectQuery = "SELECT  * FROM " + INVENTORY_TABLE + " WHERE "
					+ INVENTORY_DEPARTMENT + "=\"" + department + "\";";
		} else if (itemName != null) {
			selectQuery = "SELECT  * FROM " + INVENTORY_TABLE + " WHERE "
					+ INVENTORY_ITEM_NAME + "=\"" + itemName + "\";";
		} else if (searchItemName != null) {
			selectQuery = "SELECT  * FROM " + INVENTORY_TABLE + " WHERE "
					+ INVENTORY_ITEM_NAME + " LIKE \"" + searchItemName + "%\";";
		} else {
			selectQuery = "SELECT  * FROM " + INVENTORY_TABLE;
		}

		SQLiteDatabase dbabcd = getReadableDatabase();
		Cursor cursor = dbabcd.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Inventory inventory = new Inventory();
				
				inventory.setDepartmentAdd(cursor.getString(cursor
						.getColumnIndex(INVENTORY_DEPARTMENT)));
				inventory.setItemNoAdd(cursor.getString(cursor
						.getColumnIndex(INVENTORY_ITEM_NO)));
				inventory.setInventoryVndr(cursor.getString(cursor
						.getColumnIndex(INVENTORY_VENDOR)));
				inventory.setItemNameAdd(cursor.getString(cursor
						.getColumnIndex(INVENTORY_ITEM_NAME))+" \n "+aaaName);
				inventory.setAvgCost(cursor.getString(cursor
						.getColumnIndex(INVENTORY_AVG_COST)));
				
				inventory.setPriceTax(cursor.getString(cursor
						.getColumnIndex(INVENTORY_PRICE_TAX)));
				inventory.setPriceYouChange(cursor.getString(cursor
						.getColumnIndex(INVENTORY_PRICE_CHANGE)));
				Log.v("INVENTORY_PRICE_CHANGE",""+cursor.getString(cursor
						.getColumnIndex(INVENTORY_PRICE_CHANGE)));
				inventory.setInStock(cursor.getString(cursor
						.getColumnIndex(INVENTORY_IN_STOCK)));
				inventory.setQuantity(cursor.getString(cursor
						.getColumnIndex(INVENTORY_QUANTITY)));
				inventory.setInventoryTaxOne(cursor.getString(cursor
						.getColumnIndex(INVENTORY_TAXONE)));
				inventory.setSecondDescription(cursor.getString(cursor
						.getColumnIndex(INVENTORY_SECOND_DESCRIPTION)));
				Double mSubTotal=0.0;
				try{
				 mSubTotal = Double.valueOf(cursor.getString(cursor
						.getColumnIndex(INVENTORY_PRICE_CHANGE)));
			} catch (NumberFormatException e) {
			    e.getLocalizedMessage();
			}
				String texss=cursor.getString(cursor
						.getColumnIndex(INVENTORY_TAXONE));
				String[] parts = texss.split(",");
				String part1 = parts[0]; 
				Log.v(""+part1,"" +"  hari   "+parts.length);
				double tax=0.0;
				for(int t=0;t<parts.length;t++){
					String ttt=parts[t]; 
					Log.e("",""+ttt);
					String query = "select * from "
							+ DatabaseForDemo.TAX_TABLE + " where "
							+ DatabaseForDemo.TAX_NAME + "=\""
							+ ttt + "\"";
					System.out.println(query);
					Cursor cursortax = dbabcd.rawQuery(query, null);
					if (cursortax != null) {
						if (cursortax.moveToFirst()) {
							do {
								double taxvalpercent =0.0;
								try{
									taxvalpercent =cursortax.getDouble(cursortax
												.getColumnIndex(DatabaseForDemo.TAX_VALUE));
								} catch (NumberFormatException e) {
								    e.getLocalizedMessage();
								}
								
								System.out.println("taxvalpercent     " + taxvalpercent);
								tax += taxvalpercent;
								System.out.println(" tax    " + tax);
							} while (cursortax.moveToNext());
						}
						cursortax.close();
				}
				}
				tax = (mSubTotal * tax) / 100;
				DecimalFormat df = new DecimalFormat("#.##");
				inventory.setInventoryTaxTotal(df.format(tax));
				inventoryList.add(inventory);
			} while (cursor.moveToNext());
		}

		// closing connection
		cursor.close();
		dbabcd.close();

		// returning lables
		Log.v("fgdgd", ""+inventoryList);
		
	}catch(Exception e){
		e.getLocalizedMessage();
	}
		return inventoryList;
	}
	public List<Inventory> getSelectInventoryList(String selectQuery) {
		List<Inventory> inventoryList = new ArrayList<Inventory>();
try{
		SQLiteDatabase dbabcdef = getReadableDatabase();
		Cursor cursor = dbabcdef.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Inventory inventory = new Inventory();

				inventory.setDepartmentAdd(cursor.getString(cursor
						.getColumnIndex(INVENTORY_DEPARTMENT)));
				inventory.setItemNoAdd(cursor.getString(cursor
						.getColumnIndex(INVENTORY_ITEM_NO)));
				inventory.setItemNameAdd(cursor.getString(cursor
						.getColumnIndex(INVENTORY_ITEM_NAME)));
				inventory.setAvgCost(cursor.getString(cursor
						.getColumnIndex(INVENTORY_AVG_COST)));
				inventory.setPriceTax(cursor.getString(cursor
						.getColumnIndex(INVENTORY_PRICE_TAX)));
				inventory.setPriceYouChange(cursor.getString(cursor
						.getColumnIndex(INVENTORY_PRICE_CHANGE)));
				inventory.setInStock(cursor.getString(cursor
						.getColumnIndex(INVENTORY_IN_STOCK)));
				inventory.setQuantity(cursor.getString(cursor
						.getColumnIndex(INVENTORY_QUANTITY)));
				inventory.setInventoryTaxOne(cursor.getString(cursor
						.getColumnIndex(INVENTORY_TAXONE)));
				inventory.setSecondDescription(cursor.getString(cursor
						.getColumnIndex(INVENTORY_SECOND_DESCRIPTION)));
				
				/*Double mSubTotal = Double.valueOf(cursor.getString(cursor
						.getColumnIndex(INVENTORY_AVG_COST)));

				Double tax = Double.valueOf(cursor.getString(cursor
						.getColumnIndex(INVENTORY_TOTAL_TAX)));*/
				inventoryList.add(inventory);
			} while (cursor.moveToNext());
		}

		// closing connection
		cursor.close();
		dbabcdef.close();
	}catch(Exception e){
		e.getLocalizedMessage();
	}
		// returning lables
		return inventoryList;
	}

	public List<Inventory> getSelectInvoiceItemList(String itemNo,
			String invoiceid) {
		List<Inventory> inventoryList = new ArrayList<Inventory>();
try{
		// Select All Query

		String selectQuery = null;

		if (itemNo != null) {
			selectQuery = "SELECT  * FROM " + INVOICE_ITEMS_TABLE + " WHERE "
					+ INVOICE_ITEM_ID + "=\"" + itemNo + "\" AND "
					+ DatabaseForDemo.INVOICE_ID + "=\"" + invoiceid + "\"";
		}

		SQLiteDatabase dbabcdefgh = getReadableDatabase();
		Cursor cursor = dbabcdefgh.rawQuery(selectQuery, null);

		// looping through all rows and adding to list
		if (cursor.moveToFirst()) {
			do {
				Inventory inventory = new Inventory();

				inventory.setItemNoAdd(cursor.getString(cursor
						.getColumnIndex(INVOICE_ITEM_ID)));
				inventory.setInventoryVndr(cursor.getString(cursor
						.getColumnIndex(INVOICE_VENDOR)));
				inventory.setItemNameAdd(cursor.getString(cursor
						.getColumnIndex(INVOICE_ITEM_NAME)));
				inventory.setAvgCost(cursor.getString(cursor
						.getColumnIndex(INVOICE_AVG_COST)));
				inventory.setPriceTax("77");
				inventory.setPriceYouChange(cursor.getString(cursor
						.getColumnIndex(INVOICE_YOUR_COST)));
				inventory.setInStock("66");
				inventory.setQuantity(cursor.getString(cursor
						.getColumnIndex(INVOICE_QUANTITY)));
				inventory.setInventoryTaxOne(cursor.getString(cursor
						.getColumnIndex(INVOICE_TAX)));
				inventory.setSecondDescription(cursor.getString(cursor
						.getColumnIndex(INVOICE_DISCRIPTION)));
				try{
				Double mSubTotal = Double.valueOf(cursor.getString(cursor
						.getColumnIndex(INVOICE_TAX)));
				inventory.setInventoryTaxTotal("" + mSubTotal);
			} catch (NumberFormatException e) {
			  e.getLocalizedMessage();
			}
				inventoryList.add(inventory);
			} while (cursor.moveToNext());
		}

		// closing connection
		cursor.close();
		dbabcdefgh.close();
	}catch(Exception e){
		e.getLocalizedMessage();
	}
		// returning lables
		return inventoryList;
	}
	public MercuryValues mercuryDetails(){
		MercuryValues values = new MercuryValues();
		String query = "select * from "+MERCURY_PAY_TABLE;
		SQLiteDatabase dbabcdefghd = getWritableDatabase();
		Cursor cursor = dbabcdefghd.rawQuery(query, null);
		if(cursor!=null){
			if(cursor.getCount()>0){
				if(cursor.moveToFirst()){
					do{
						
						if(cursor.isNull(cursor.getColumnIndex(MERCURY_PRIMARY_URL))){
						}else{
							values.setPrimaryURL(cursor.getString(cursor.getColumnIndex(MERCURY_PRIMARY_URL)));
						}
						if(cursor.isNull(cursor.getColumnIndex(MERCURY_SECONDARY_URL))){
						}else{
							values.setSecondaryURL(cursor.getString(cursor.getColumnIndex(MERCURY_SECONDARY_URL)));
						}
						if(cursor.isNull(cursor.getColumnIndex(MERCURY_MERCHANT_ID))){
						}else{
							values.setMerchantID(cursor.getString(cursor.getColumnIndex(MERCURY_MERCHANT_ID)));
						}
						if(cursor.isNull(cursor.getColumnIndex(MERCURY_PASSWORD))){
						}else{
							values.setPassword( cursor.getString(cursor.getColumnIndex(MERCURY_PASSWORD)));
						}
					}while(cursor.moveToNext());
	          }
			}
			}
		cursor.close();
		return values;
	}
}
