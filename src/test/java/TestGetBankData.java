import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.logging.Logger;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import com.klst.iban.IbanToBankData;
import com.klst.iban.Result.BankData;
import com.klst.iban.Result.SepaData;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestGetBankData {

	private static final Logger LOG = Logger.getLogger(TestGetBankData.class.getName());
	
	private static final String API_KEY = "testKey";
	private static final String AD_IBAN = "AD1200012030200359100100";
	
    @BeforeClass
    public static void staticSetup() {
//    	testAddress = null;
    }
    
	@Before 
    public void setup() {
//		LOG.info("testAddress:"+ testAddress);
    }

    @Test
    public void invalidApiKey() {
		IbanToBankData test = new IbanToBankData();
		BankData bankData =	test.retrieveBankData(AD_IBAN);
		assertNull(bankData);
		
		test = new IbanToBankData(API_KEY);
		bankData =	test.retrieveBankData(AD_IBAN);
		assertNull(bankData);		
    }
    
    @Test
    public void validIban() {
		IbanToBankData test = new IbanToBankData(API_KEY);
		BankData bankData =	test.retrieveBankData(AD_IBAN);
		assertNotNull(bankData);
		LOG.info("BankData:"+ bankData);
		assertNotNull(bankData.getBic());
//		int bs = bankData.getBankSupports();
//		int sct = bs & SepaData.SCT;
//		LOG.info("bs:"+ bs + ", sct:"+ sct);
//		assertTrue(sct>0);
		assertTrue((bankData.getBankSupports() & SepaData.SCT)>0); // bank supports SEPA Credit Transfer
    }

}
