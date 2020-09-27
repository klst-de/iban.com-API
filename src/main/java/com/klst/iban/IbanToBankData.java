package com.klst.iban;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.logging.Logger;

import javax.net.ssl.HttpsURLConnection;

import org.apache.commons.validator.routines.IBANValidator;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import com.klst.iban.Result.BankData;
import com.klst.iban.Result.SepaData;

public class IbanToBankData { 
	
	private static final Logger LOG = Logger.getLogger(IbanToBankData.class.getName());

	static final String IBAN_COM_URL = "https://api.iban.com/clients/api/v4/iban/";
    static final String USER_AGENT = "API Client/1.0";
    static final String FORMAT_XML = "&format=xml";
    static final String FORMAT_JSON = "&format=json";

	String api_key = null;
	String iban = null;
	
	public IbanToBankData() {
		this(null);
	}
	public IbanToBankData(String api_key) {
		this.api_key = api_key;
	}
	
	public BankData getBankData(String iban) {
		IBANValidator validator = IBANValidator.getInstance();
		if(!validator.isValid(iban)) return null;

		LOG.info(iban + " is valid.");		
		this.iban = iban;
		return getBankData();
	}
	
	/**
	 * Validate and retrieve bank data from an IBAN with https://www.iban.com/iban-checker
	 * 
	 * @param iban
	 * @return BankData object
	 */
	public BankData retrieveBankData(String iban) {
		if(this.api_key==null) {
			LOG.warning(iban + " - No api_key provided.");
			return null;
		}
		
        BankData bankData = null;
        try {
        	URL url = new URL(IBAN_COM_URL); // throws MalformedURLException
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection(); // throws IOException
	        //add request header
	        con.setRequestMethod("POST");
	        con.setRequestProperty("User-Agent", USER_AGENT);
	        con.setRequestProperty("Accept-Language", "en-US,en;q=0.5");
	        
	        String urlParameters = "api_key="+api_key+FORMAT_JSON+"&iban="+iban;
	        
	        // Send post request
	        con.setDoOutput(true); // true: use the URL connection for output,
	        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
	        wr.writeBytes(urlParameters);
	        wr.flush();
	        wr.close();

	        int responseCode = con.getResponseCode();
	        //LOG.info("Sending 'POST' request to URL "+IBAN_COM_URL + " parameters:"+urlParameters);
	        if(responseCode!=200) {
		        LOG.warning("Sending 'POST' request to URL "+IBAN_COM_URL + " parameters:"+urlParameters + " returns "+responseCode);	
		        return null;
	        }
	        //assertEquals(200, responseCode);
	 
	        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();
	 
	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();
	        
	        // json: {"bank_data":[],"sepa_data":[],"validations":[],"errors":[{"code":"301","message":"API Key is invalid"}]}
	        // xml: <?xml version="1.0"?><result><bank_data/><sepa_data/><validations><chars><code/><message/></chars><iban><code/><message/></iban><account><code/><message/></account><structure><code/><message/></structure><length><code/><message/></length><country_support><code/><message/></country_support></validations><errors><error><code>301</code><message>API Key is invalid</message></error></errors></result>
	        String jsonString = response.toString();
//	        LOG.info("response:\n"+jsonString+"<"); 
	        if(jsonString.isEmpty()) return null;
// Test:
//        	String jsonString = "{\"bank_data\":[],\"sepa_data\":[],\"validations\":[],\"errors\":[{\"code\":\"301\",\"message\":\"API Key is invalid\"}]}";

	        JSONParser jsonParser = new JSONParser();
	        Object o = jsonParser.parse(jsonString); // throws ParseException
	        JSONObject jo = (JSONObject) o;
	        
	        JSONArray errors = (JSONArray) jo.get("errors");
	        if(errors.size()>0) {
	            errors.forEach( error -> parseErrorObject( (JSONObject)error ) );
	            return null;
	        }

	        Object validations_o = jo.get("validations");
	        if(validations_o instanceof JSONObject) {
	        	parseValidationObject(iban, (JSONObject)validations_o, true );
	        }

	        Object bank_data_o = jo.get("bank_data");
	        if(bank_data_o instanceof JSONObject) {
	        	bankData = parseBankDataObject( (JSONObject)bank_data_o );
	        }

	        Object sepa_data_o = jo.get("sepa_data");
	        if(sepa_data_o instanceof JSONObject) {
	        	SepaData sepaData = parseSepaDataObject( (JSONObject)sepa_data_o );
	        	bankData.setBankSupports(sepaData.getBankSupports());
	        }
//        	if(bankData.getBic()!=null && !bankData.getBic().isEmpty()) LOG.info(""+bankData); 

        } catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
        return bankData;

	}

    final static String CODE = "code";
    final static String MESSAGE = "message";
    final static String INVALID_KEY = "301"; // Account Error 	API Key is invalid

	String parseErrorObject(JSONObject errorOrValidation) {
		return parseErrorObject(errorOrValidation, true);
	}
	private String parseErrorObject(JSONObject error, boolean verbose) {
		String code = (String) error.get(CODE);
		String message = (String) error.get(MESSAGE);
		if(verbose) {
			if(code.startsWith("0")) {
				LOG.config(CODE+":"+code + ", " + MESSAGE+":"+message);
			} else {
				LOG.warning(CODE+":"+code + ", " + MESSAGE+":"+message);
			}
		}
		return code;
	}

    //201 	Validation Failed 	Account Number check digit not correct
/*

001 	Validation Success 	IBAN Check digit is correct
002 	Validation Success 	Account Number check digit is correct
003 	Validation Success 	IBAN Length is correct
004 	Validation Success 	Account Number check digit is not performed for this bank or branch
005 	Validation Success 	IBAN structure is correct
006 	Validation Success 	IBAN does not contain illegal characters
007 	Validation Success 	Country supports IBAN standard

201 	Validation Failed 	Account Number check digit not correct
202 	Validation Failed 	IBAN Check digit not correct
203 	Validation Failed 	IBAN Length is not correct
205 	Validation Failed 	IBAN structure is not correct
206 	Validation Failed 	IBAN contains illegal characters
207 	Validation Failed 	Country does not support IBAN standard

,"validations":{"chars":{"code":"006","message":"IBAN does not contain illegal characters"}
             ,"account":{"code":"002","message":"Account Number check digit is correct"} OR
             ,"account":{"code":"201","message":"Account Number check digit not correct"}
             ,"iban":{"code":"001","message":"IBAN Check digit is correct"} //  OR
             ,"iban":{"code":"202","message":"IBAN Check digit not correct"}
             ,"iban":{"code":"203","message":"IBAN Length is not correct. Kosovo IBAN must be 20 characters long.t"}
             ,"iban":{"code":"205","message":"IBAN structure is not correct"}
             ,"iban":{"code":"206","message":"IBAN contains illegal characters"}
             ,"structure":{"code":"005","message":"IBAN structure is correct"}
             ,"length":{"code":"003","message":"IBAN Length is correct"}
             ,"country_support":{"code":"007","message":"Country supports IBAN standard"} OR
             ,"country_support":{"code":"207","message":"Country does not support IBAN standard"}
             }

 */
	void parseValidationObject(String iban, JSONObject validation, boolean verbose) {
    	LOG.info("validations for iban "+iban); 
    	parseValidationObject( validation, verbose );		
	}
	void parseValidationObject(JSONObject validation) {
		parseValidationObject(validation, false);
	}
	void parseValidationObject(JSONObject validation, boolean verbose) {
		String code = parseErrorObject( (JSONObject)validation.get("chars"), verbose);
		code = parseErrorObject( (JSONObject)validation.get("account"), verbose);
		code = parseErrorObject( (JSONObject)validation.get("iban"), verbose);
		code = parseErrorObject( (JSONObject)validation.get("structure"), verbose);
		code = parseErrorObject( (JSONObject)validation.get("length"), verbose);
		code = parseErrorObject( (JSONObject) validation.get("country_support"), verbose);
		return;
	}

	BankData parseBankDataObject(JSONObject bank_data) {
		BankData bankData = new BankData();
		String bic = (String) bank_data.get("bic");
		bankData.setBic(bic);
		String bank = (String) bank_data.get(BANK); // aka bank name
		bankData.setBank(bank);
		String city = (String) bank_data.get(CITY);
		bankData.setCity(city);
		String bank_code = (String) bank_data.get(BANK_CODE);
		bankData.setBankIdentifier(bank_code);
		// optional:
		bankData = getOptionalKey(bank_data, BRANCH, bankData);
		bankData = getOptionalKey(bank_data, ADDRESS, bankData);
		bankData = getOptionalKey(bank_data, STATE, bankData);
		bankData = getOptionalKey(bank_data, ZIP, bankData);
		bankData = getOptionalKey(bank_data, PHONE, bankData);
		bankData = getOptionalKey(bank_data, FAX, bankData);
		bankData = getOptionalKey(bank_data, WWW, bankData);
		bankData = getOptionalKey(bank_data, EMAIL, bankData);
		// country, country_iso
		// account
		return bankData;
	}
	
    static final String SWIFT_CODE = "swift_code"; // aka BIC
    static final String BANK = "bank"; // bank name
    static final String BANK_CODE = "bank_code"; // String BankData.bankIdentifier, int BankData.bankCode
    static final String ID = "id"; // unique per country, value can be bankCode
    static final String BRANCH = "branch";
    static final String BRANCH_CODE = "branch_code";
    static final String ADDRESS = "address";
    static final String STATE = "state";
    static final String CITY = "city";
    static final String ZIP = "zip";
    static final String PHONE = "phone";
    static final String FAX = "fax";
    static final String WWW = "www";
    static final String EMAIL = "email";
    static final String SUPPORT_CODES = "support_codes";
    
 	private BankData getOptionalKey(JSONObject bank_data, String key, BankData bankData) {
		Object value = bank_data.get(key);
		if(value!=null) {
			if(key.equals(BRANCH)) bankData.setBranch(value);
			else if(key.equals(ADDRESS)) bankData.setAddress(value);
			else if(key.equals(STATE)) bankData.setState(value);
			else if(key.equals(ZIP)) bankData.setZipString((String)value);
			else if(key.equals(PHONE)) bankData.setPhone(value);
			else if(key.equals(FAX)) bankData.setFax(value);
			else if(key.equals(WWW)) bankData.setWww(value);
			else if(key.equals(EMAIL)) bankData.setEmail(value);
			else {
				LOG.severe("unsupported key "+key);
			}
		}
		return bankData;	
	}

	private SepaData parseSepaDataObject(JSONObject sepa_data) {
		SepaData sepaData = new SepaData();
		sepaData.setSCT((String)sepa_data.get("SCT"));
		sepaData.setSDD((String)sepa_data.get("SDD"));
		sepaData.setCOR1((String)sepa_data.get("COR1"));
		sepaData.setB2B((String)sepa_data.get("B2B"));
		sepaData.setSCC((String)sepa_data.get("SCC"));
		return sepaData;
	}

	private BankData getBankData() {
		BankData bankData = new BankData();
		SepaData sepaData = new SepaData();
		String countryCode = iban.substring(0, 2);
		String bban = iban.substring(4);
		if(Bban.BBAN.get(countryCode)!=null) {
			Bban bData = Bban.BBAN.get(countryCode); // liefert eine Instanz mit Methode
			bankData = bData.getBankData(iban);
		} else {
			LOG.warning(iban + " NOT implemented.");			
		}
		LOG.info(iban + " -> bankData:"+bankData);
		return bankData;
	}

}
