package com.klst.iban;

import org.json.simple.JSONObject;

import com.klst.iban.Result.BankData;

public class Bank_Data {

	// names defined in IbanValidationApi.xsd element name="bank_data"
	// I intentionally do not use account, country and country_iso, 
	// because each iban starts with two chars country code, however it is not iso (see XK)
    static final String BIC = "bic";
    static final String BRANCH = "branch";
    static final String BANK = "bank"; // bank name
    static final String ADDRESS = "address";
    static final String CITY = "city";
    static final String STATE = "state";
    static final String ZIP = "zip";
    static final String PHONE = "phone";
    static final String FAX = "fax";
    static final String WWW = "www";
    static final String EMAIL = "email";
    static final String BANK_CODE = "bank_code"; // String BankData.bankIdentifier, int BankData.bankCode
    static final String BRANCH_CODE = "branch_code";
    
    // this names are used in the package, but are not defined in IbanValidationApi.xsd 
    static final String SWIFT_CODE = "swift_code"; // aka BIC
    static final String ID = "id"; // unique per country, value can be bankCode
    static final String SUPPORT_CODES = "support_codes";

 	static BankData getOptionalKey(JSONObject bank_data, String key, BankData bankData) throws Exception {
		Object value = bank_data.get(key);
		if(value!=null) {
			if(key.equals(BRANCH)) bankData.setBranch(value);
			else if(key.equals(BRANCH_CODE)) {
				bankData.branchCode = value; // no setter!
				// client responsibility to get numeric branch code:
//	            try {
//	            	bankData.branchCode = new Integer(Integer.parseInt(value.toString())); 
//	            } catch (NumberFormatException e) {
//	            	LOG.info(key+" "+value + " is not numeric.");
//	            }        
			}
			else if(key.equals(ADDRESS)) bankData.setAddress(value);
			else if(key.equals(STATE)) bankData.setState(value);
			else if(key.equals(ZIP)) bankData.setZipString((String)value);
			else if(key.equals(PHONE)) bankData.setPhone(value);
			else if(key.equals(FAX)) bankData.setFax(value);
			else if(key.equals(WWW)) bankData.setWww(value);
			else if(key.equals(EMAIL)) bankData.setEmail(value);
			else if(key.equals(SUPPORT_CODES)) {
				bankData.setBankSupports(Byte.parseByte(value.toString()));
			}
			else {
				throw new Exception("unsupported key "+key);
			}
		}
		return bankData;	
	}

}
