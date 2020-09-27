import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.Arrays;
import java.util.List;
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
	private static final String AE_IBAN = "AE070331234567890123456";
	private static final String AL_IBAN = "AL47212110090000000235698741";
	private static final String AT_IBAN = "AT572011140014400144"; // https://www.roteskreuz.at/home/
	                                     //AT611904300234573201 aus iban_registry_0.pdf - retrieveBankData liefert nix!!!!
	private static final String AZ_IBAN = "AZ21NABZ00000000137010001944";
	private static final String BA_IBAN = "BA391290079401028494";
	private static final String BE_IBAN = "BE88000000004141"; // https://www.caritasinternational.be/en/
	private static final String BG_IBAN = "BG80BNBG96611020345678";
	private static final String BH_IBAN = "BH67BMAG00001299123456";
	private static final String BR_IBAN = "BR1800360305000010009795493C1";
	private static final String BY_IBAN = "BY13NBRB3600900000002Z00AB00";
	private static final String CH_IBAN = "CH6909000000600070004"; // https://www.caritas.ch/de/startseite.html
	private static final String CR_IBAN = "CR05015202001026284066";
	private static final String CY_IBAN = "CY17002001280000001200527600";
	private static final String CZ_IBAN = "CZ6508000000192000145399";
							
	final static List<String> SEPA_COUNTRIES = Arrays.asList(AD_IBAN, AT_IBAN, BE_IBAN, BG_IBAN, CH_IBAN, CY_IBAN, CZ_IBAN);
	
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
    public void countryDoesDotSupportIBAN() {
		IbanToBankData test = new IbanToBankData(API_KEY);
		BankData bankData = test.retrieveBankData("US070331234567890123456"); // Country does not support IBAN standard
		assertNotNull(bankData);
		LOG.info("BankData:"+ bankData);
		assertNull(bankData.getBic());
    }
    
    @Test
    public void aValidIban() {
		IbanToBankData test = new IbanToBankData(API_KEY);
		BankData bankData = test.getBankData(AD_IBAN);
		LOG.info("getBankData:"+ bankData);
		bankData = test.retrieveBankData(AD_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		assertTrue((bankData.getBankSupports() & SepaData.SCT)>0); // bank supports SEPA Credit Transfer

		bankData = test.retrieveBankData(AE_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());

		bankData = test.retrieveBankData(AL_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());

		bankData = test.getBankData(AT_IBAN);
		LOG.info("getBankData:"+ bankData);
		bankData = test.retrieveBankData(AT_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		assertTrue((bankData.getBankSupports() & SepaData.SCT)>0); // bank supports SEPA Credit Transfer
		// BankSupports:15 
		
		bankData = test.retrieveBankData(AZ_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());

		bankData = test.retrieveBankData(BA_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());

		bankData = test.getBankData(BE_IBAN);
		LOG.info("getBankData:"+ bankData);
		bankData = test.retrieveBankData(BE_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		assertTrue((bankData.getBankSupports() & SepaData.SCT)>0); // bank supports SEPA Credit Transfer
		// BankSupports:7 
		
		bankData = test.getBankData(BG_IBAN);
		LOG.info("getBankData:"+ bankData);
		bankData = test.retrieveBankData(BG_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		assertTrue((bankData.getBankSupports() & SepaData.SCT)>0); // bank supports SEPA Credit Transfer
		// BankSupports:1

		bankData = test.retrieveBankData(BH_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());

		bankData = test.retrieveBankData(BR_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());

		bankData = test.retrieveBankData(BY_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());

		bankData = test.getBankData(CH_IBAN);
		LOG.info("getBankData:"+ bankData);
		bankData = test.retrieveBankData(CH_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		assertTrue((bankData.getBankSupports() & SepaData.SCT)>0); // bank supports SEPA Credit Transfer
		// BankSupports:15
		
		bankData = test.retrieveBankData(CR_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());

		bankData = test.getBankData(CY_IBAN);
		LOG.info("getBankData:"+ bankData);
		bankData = test.retrieveBankData(CY_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		assertTrue((bankData.getBankSupports() & SepaData.SCT)>0); // bank supports SEPA Credit Transfer
		// BankSupports:7
		
		bankData = test.getBankData(CZ_IBAN);
		LOG.info("getBankData:"+ bankData);
		bankData = test.retrieveBankData(CZ_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		assertTrue((bankData.getBankSupports() & SepaData.SCT)>0); // bank supports SEPA Credit Transfer
		// BankSupports:15
   }

}
