package com.klst.ibanTest;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.LogManager;
import java.util.logging.Logger;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import com.klst.iban.IbanToBankData;
import com.klst.iban.Result.BankData;

public class MiniTest {
	
	// init after reread the logging configuration in logManager.readConfiguration!
	private static Logger LOG; //Logger.getLogger(MiniTest.class.getName());

	private static final String API_KEY = API_Key_Provider.API_KEY;

	static final String RESOURCE_PATH = "src/main/resources/";
	static LogManager logManager = LogManager.getLogManager(); // Singleton
	
	static {
        URL url = MiniTest.class.getClassLoader().getResource("testLogging.properties");
		try {
	        File file = new File(url.toURI());
			logManager.readConfiguration(new FileInputStream(file));
		} catch (IOException | URISyntaxException e) {
			LOG = Logger.getLogger(MiniTest.class.getName());
			LOG.warning(e.getMessage());
		}
		LOG = Logger.getLogger(MiniTest.class.getName());
	}
	
    @BeforeClass
	public static void staticSetup() {
//    	URL url = MiniTest.class.getClassLoader().getResource("testLogging.properties");
//		try {
//			File file = new File(url.toURI());
//			logManager.readConfiguration(new FileInputStream(file));
// Facility specific log properties funktionieren nicht bei FileInputStream(String name)
//			logManager.readConfiguration(new FileInputStream(RESOURCE_PATH + "testLogging.properties"));
//		} catch (IOException | URISyntaxException e) {
//			LOG = Logger.getLogger(MiniTest.class.getName());
//			LOG.warning(e.getMessage());
//		}
//		LOG = Logger.getLogger(MiniTest.class.getName());
    	LOG.config("staticSetup fertig");
	}
    
	@Before 
	public void setup() {
	}

    @Test
    public void noApiKey() {
    	LOG.config("config start");
    	LOG.info("info start prop java.util.logging.config.file="+logManager.getProperty("java.util.logging.config.file"));
    	IbanToBankData ibanToBankData = new IbanToBankData();
    	assertNull(ibanToBankData.retrieveBankData(null));
    }

    @Test
    public void invalidApiKey() {
    	IbanToBankData ibanToBankData = new IbanToBankData("invalidKey");
    	assertNull(ibanToBankData.retrieveBankData(null));
    	assertNull(ibanToBankData.getBankData("DE89370400440532013000"));
    }

    @Test
    public void withApiKey() {
    	LOG.fine("fine start");
    	String AD_IBAN = "AD1200012030200359100100";
    	IbanToBankData ibanToBankData = new IbanToBankData(); // no key
    	BankData bankData = ibanToBankData.getBankData(AD_IBAN);
    	LOG.info("AD partiall bankData:"+bankData);
    	assertEquals(1, bankData.getBankCode());
    	assertEquals("0001", bankData.getBankIdentifier());
    	Object branchCode = bankData.getBranchCode();
        if(branchCode!=null) try {
        	assertEquals(2030, Integer.parseInt(branchCode.toString()));
        } catch (NumberFormatException e) {
        	LOG.info("BranchCode "+branchCode + " is not numeric.");
        }        
    	
    	ibanToBankData = new IbanToBankData(API_KEY);
    	bankData = ibanToBankData.getBankData(AD_IBAN);
    	bankData = ibanToBankData.retrieveBankData(AD_IBAN);
    	assertEquals("BACAADADXXX", bankData.getBic());
    	assertEquals(1, bankData.getBankSupports());
    	LOG.info("AD bankData:"+bankData);    	

    	String GR_IBAN = "GR1601101250000000012300695";
    	bankData = ibanToBankData.getBankData(GR_IBAN);
    	assertEquals(11, bankData.getBankCode());
    	assertEquals("011", bankData.getBankIdentifier());
    	assertEquals("0125", bankData.getBranchCode());
    	
    	bankData = ibanToBankData.retrieveBankData("LY83002048000020100120361");
/*
{"bank_data":{"bic":null,"branch":null,"bank":null,"address":null,"city":null,"state":null,"zip":null,"phone":null,"fax":null,"www":null,"email":null
             ,"country":"Libya","country_iso":"LY","account":"000020100120361","bank_code":"002","branch_code":"048"}
,"sepa_data":{"SCT":"NO","SDD":"NO","COR1":"NO","B2B":"NO","SCC":"NO"}
,"validations":{"chars":{"code":"006","message":"IBAN does not contain illegal characters"}
             ,"account":{"code":"004","message":"Account Number check digit is not performed for this bank or branch"}
                ,"iban":{"code":"001","message":"IBAN Check digit is correct"}
           ,"structure":{"code":"005","message":"IBAN structure is correct"}
              ,"length":{"code":"003","message":"IBAN Length is correct"}
     ,"country_support":{"code":"007","message":"Country supports IBAN standard"}}
,"errors":[]}< 
 */
    	LOG.info("LY bankData:"+bankData);
    	assertEquals(2, bankData.getBankCode());
    	assertEquals("048", bankData.getBranchCode());
    	
    }

}
