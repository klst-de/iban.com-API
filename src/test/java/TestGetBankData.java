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
	
	private static final String API_KEY = API_Key_Provider.API_KEY;
	
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
	private static final String DE_IBAN = "DE89370400440532013000";
	private static final String DK_IBAN = "DK5000400440116243";
	private static final String DO_IBAN = "DO28BAGR00000001212453611324";
	private static final String EE_IBAN = "EE382200221020145685";
	private static final String EG_IBAN = "EG380019000500000000263180002";
	private static final String ES_IBAN = "ES9121000418450200051332";
	private static final String FI_IBAN = "FI2112345600000785";
	private static final String FO_IBAN = "FO6264600001631634";
	private static final String FR_IBAN = "FR1420041010050500013M02606";
	private static final String GB_IBAN = "GB89CHAS60924241305901";
	private static final String GE_IBAN = "GE29NB0000000101904917";
	private static final String GI_IBAN = "GI75NWBK000000007099453";
	private static final String GL_IBAN = "GL8964710001000206";
	private static final String GR_IBAN = "GR1601101250000000012300695";
	private static final String GT_IBAN = "GT82TRAJ01020000001210029690";
	private static final String HR_IBAN = "HR1210010051863000160";
	private static final String HU_IBAN = "HU42117730161111101800000000";
	private static final String IE_IBAN = "IE29AIBK93115212345678";
	private static final String IL_IBAN = "IL620108000000099999999";
	private static final String IQ_IBAN = "IQ98NBIQ850123456789012";
	private static final String IS_IBAN = "IS140159260076545510730339";
	private static final String IT_IBAN = "IT60X0542811101000000123456";
	private static final String JO_IBAN = "JO94CBJO0010000000000131000302";
	private static final String KW_IBAN = "KW81CBKU0000000000001234560101";
	private static final String KZ_IBAN = "KZ86125KZT5004100100";
	private static final String LB_IBAN = "LB62099900000001001901229114";
	private static final String LC_IBAN = "LC55HEMM000100010012001200023015";
	private static final String LI_IBAN = "LI21088100002324013AA";
	private static final String LT_IBAN = "LT407300010099679931"; // caritas.lt
	private static final String LU_IBAN = "LU280019400644750000";
	private static final String LV_IBAN = "LV46HABA0551008657797"; // caritas.lv
	private static final String LY_IBAN = "LY83002048000020100120361"; // TODO
	private static final String MC_IBAN = "MC5811668400011234567890191"; // selbstgeneriert
	private static final String MD_IBAN = "MD24AG000225100013104168";
	private static final String ME_IBAN = "ME25505000012345678951";
	private static final String MK_IBAN = "MK07250120000058984";
	private static final String MR_IBAN = "MR1300020001010000123456753";
	private static final String MT_IBAN = "MT84MALT011000012345MTLCAST001S";
	private static final String MU_IBAN = "MU17BOMM0101101030300200000MUR";
	private static final String NL_IBAN = "NL91ABNA0417164300";
	private static final String NO_IBAN = "NO9386011117947";
	private static final String PK_IBAN = "PK36SCBL0000001123456702";
	private static final String PL_IBAN = "PL61109010140000071219812874";
	private static final String PS_IBAN = "PS92PALS000000000400123456702";
	private static final String PT_IBAN = "PT50003300000000539169561"; // caritas.pt
	private static final String QA_IBAN = "QA58DOHB00001234567890ABCDEFG";
	private static final String RO_IBAN = "RO44BRDE410SV20462054100"; // crucearosie.ro/
	private static final String RS_IBAN = "RS35260005601001611379";
	private static final String SA_IBAN = "SA0380000000608010167519";
	private static final String SC_IBAN = "SC18SSCB11010000000000001497USD";
	private static final String SE_IBAN = "SE4550000000058398257466";
	private static final String SI_IBAN = "SI56021400015556761"; // karitas.si                                      
	private static final String SK_IBAN = "SK6911001234561234567890"; // selbstgeneriert : Account Number check digit not correct
	private static final String SM_IBAN = "SM86U0322509800000000270100";
	private static final String ST_IBAN = "ST23000100010051845310146";
	private static final String SV_IBAN = "SV62CENR00000000000000700025";
	private static final String TL_IBAN = "TL380050601000086313706"; // redcross.tl liefert leere BIC
	private static final String TN_IBAN = "TN5910006035183598478831";
	private static final String TR_IBAN = "TR710020500000696117500104";
	private static final String UA_IBAN = "UA213223130000026007233566001";
	private static final String VA_IBAN = "VA59001123000012345678";
	private static final String VG_IBAN = "VG96VPVG0000012345678901";
	private static final String XK_IBAN = "XK051212012345678906";
	// https://www.iban.com/structure/ Countries which have Partial/Experimental use of the IBAN system :
	private static final String AO_IBAN = "AO06004400006729503010102";    // "bank_code":"0044","branch_code":"0000"
	private static final String BF_IBAN = "BF42BF0840101300463574000390"; // "bank_code":"084","branch_code":"01013"
	private static final String BI_IBAN = "BI43201011067444";             // "bank_code":"201","branch_code":""
	private static final String BJ_IBAN = "BJ66BJ0610100100144390000769"; // "bank_code":"BJ061","branch_code":"01001"
	private static final String CF_IBAN = "CF4220001000010120069700160";  // "bank_code":"20001","branch_code":"00001"
	private static final String CG_IBAN = "CG3930011000101013451300019";  // "bank_code":"30011","branch_code":"00010"
	private static final String CI_IBAN = "CI93CI0080111301134291200589"; // "bank_code":"008","branch_code":"01113"
	private static final String CM_IBAN = "CM2110002000300277976315008";  // "bank_code":"10002","branch_code":"00030"
	private static final String CV_IBAN = "CV64000500000020108215144";    // "bank_code":"0005","branch_code":"0000"
	private static final String DJ_IBAN = "DJ2110002010010409943020008";  // "bank_code":"10002","branch_code":"01001"
	private static final String DZ_IBAN = "DZ580002100001113000000570";   // "bank_code":"00021","branch_code":"100001"
	private static final String GA_IBAN = "GA2140021010032001890020126";  // "bank_code":"40021","branch_code":"01003"
	private static final String GQ_IBAN = "GQ7050002001003715228190196";  // "bank_code":"50002","branch_code":"00100"
	private static final String GW_IBAN = "GW04GW1430010181800637601";    // "bank_code":"GW143","branch_code":"00101"
	private static final String HN_IBAN = "HN54PISA00000000000000123124"; // "bank_code":"PISA","branch_code":""
	private static final String IR_IBAN = "IR710570029971601460641001";   // "bank_code":"057","branch_code":"997160"
	private static final String KM_IBAN = "KM4600005000010010904400137";  // "bank_code":"00005","branch_code":"00001"
	private static final String MA_IBAN = "MA64011519000001205000534921"; // "bank_code":"151","branch_code":"90000"
	private static final String MG_IBAN = "MG4600005030071289421016045";  // "bank_code":"000","branch_code":""
	private static final String ML_IBAN = "ML13ML0160120102600100668497"; // "bank_code":"ML016","branch_code":"01201"
	private static final String MZ_IBAN = "MZ59000301080016367102371";    // "bank_code":"0003","branch_code":"0108"
	private static final String NE_IBAN = "NE58NE0380100100130305000268"; // "bank_code":"038","branch_code":"01001"
	private static final String NI_IBAN = "NI92BAMC000000000000000003123123"; // "bank_code":"BAMC","branch_code":""
	private static final String SN_IBAN = "SN08SN0100152000048500003035"; // "bank_code":"010","branch_code":"01520"
	private static final String TD_IBAN = "TD8960002000010271091600153";  // "bank_code":"60002","branch_code":"00001"
	private static final String TG_IBAN = "TG53TG0090604310346500400070"; // "bank_code":"TG009","branch_code":"06043"
																
	final static List<String> SEPA_COUNTRIES = Arrays.asList
			( AD_IBAN // in iban_registry_0.pdf kein SEPA!
			, AT_IBAN
			, BE_IBAN
			, BG_IBAN
			, CH_IBAN
			, CY_IBAN
			, CZ_IBAN
			, DE_IBAN
			, DK_IBAN
			, EE_IBAN
			, ES_IBAN
			, FI_IBAN
			, FR_IBAN // SEPA country also includes GF, GP, MQ, YT, RE, PM, BL, MF
			, GB_IBAN // Country code includes other countries/territories IM, JE, GG
			, GI_IBAN
			, GR_IBAN
			, HR_IBAN
			, HU_IBAN
			, IE_IBAN
			, IS_IBAN
			, IT_IBAN
			, LI_IBAN
			, LT_IBAN
			, LU_IBAN
			, LV_IBAN
			, MC_IBAN
			, MT_IBAN
			, NL_IBAN
			, NO_IBAN
			, PL_IBAN
			, PT_IBAN
			, RO_IBAN
			, SE_IBAN
			, SI_IBAN
			, SK_IBAN
			, SM_IBAN
			, VA_IBAN
			);
	
    @BeforeClass
    public static void staticSetup() {
//    	testAddress = null;
    }
    
	IbanToBankData ibanToBankData;
	BankData bankData;
	
	@Before 
    public void setup() {
		ibanToBankData = new IbanToBankData(API_KEY);
    }

    @Test
    public void invalidApiKey() {
		IbanToBankData test = new IbanToBankData();
		BankData bankData =	test.retrieveBankData(AD_IBAN);
		assertNull(bankData);
		
		test = new IbanToBankData("API_KEY");
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
    public void aValidExperimentalIban() {
    	checkNonSepaIban(AO_IBAN);
    	checkNonSepaIban(BF_IBAN);
    	checkNonSepaIban(BI_IBAN);
    	checkNonSepaIban(BJ_IBAN);
    	checkNonSepaIban(CF_IBAN);
    	checkNonSepaIban(CG_IBAN);
    	checkNonSepaIban(CI_IBAN);
    	checkNonSepaIban(CM_IBAN);
    	checkNonSepaIban(CV_IBAN);
    	checkNonSepaIban(DJ_IBAN);
    	checkNonSepaIban(DZ_IBAN);
    	checkNonSepaIban(GA_IBAN);
    	checkNonSepaIban(GQ_IBAN);
    	checkNonSepaIban(GW_IBAN);
    	checkNonSepaIban(HN_IBAN);
    	checkNonSepaIban(IR_IBAN);
    	checkNonSepaIban(KM_IBAN);
    	checkNonSepaIban(MA_IBAN);
    	checkNonSepaIban(MG_IBAN);
    	checkNonSepaIban(ML_IBAN);
    	checkNonSepaIban(MZ_IBAN);
    	checkNonSepaIban(NE_IBAN);
    	checkNonSepaIban(NI_IBAN);
    	checkNonSepaIban(SN_IBAN);
    	checkNonSepaIban(TD_IBAN);
    	checkNonSepaIban(TG_IBAN);
    }


    @Test
    public void aValidIban() {
		IbanToBankData test = new IbanToBankData(API_KEY);
		bankData = test.getBankData(AD_IBAN);
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
		
		checkSepaIban(test, bankData, AT_IBAN); // BankSupports:15		
		
		bankData = test.retrieveBankData(AZ_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());

		bankData = test.retrieveBankData(BA_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());

		checkSepaIban(test, bankData, BE_IBAN); // BankSupports:7		
		checkSepaIban(test, bankData, BG_IBAN); // BankSupports:1		

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

		checkSepaIban(test, bankData, CH_IBAN); // BankSupports:15		
		
		bankData = test.retrieveBankData(CR_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());

		checkSepaIban(test, bankData, CY_IBAN); // BankSupports:7		
		checkSepaIban(test, bankData, CZ_IBAN); // BankSupports:15
		checkSepaIban(test, bankData, DE_IBAN); // BankSupports:31
		checkSepaIban(test, bankData, DK_IBAN); // BankSupports:1
		
		bankData = test.retrieveBankData(DO_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());

		checkSepaIban(test, bankData, EE_IBAN); // BankSupports:1
		
		bankData = test.retrieveBankData(EG_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());

		checkSepaIban(test, bankData, ES_IBAN); // BankSupports:15
		checkSepaIban(test, bankData, FI_IBAN); // BankSupports:15

		bankData = test.retrieveBankData(FO_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		
		checkSepaIban(test, bankData, FR_IBAN); // BankSupports:15
		checkSepaIban(test, bankData, GB_IBAN); // BankSupports:15

		bankData = test.retrieveBankData(GE_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		
		checkSepaIban(test, bankData, GI_IBAN); // BankSupports:1
		
		bankData = test.retrieveBankData(GL_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		
		checkSepaIban(test, bankData, GR_IBAN); // BankSupports:15
		
		bankData = test.retrieveBankData(GT_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
				
		checkSepaIban(test, bankData, HR_IBAN); // BankSupports:1
		checkSepaIban(test, bankData, HU_IBAN); // BankSupports:1
		checkSepaIban(test, bankData, IE_IBAN); // BankSupports:7
		
		bankData = test.retrieveBankData(IL_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		
		bankData = test.retrieveBankData(IQ_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		
		checkSepaIban(test, bankData, IS_IBAN); // BankSupports:1
		checkSepaIban(test, bankData, IT_IBAN); // BankSupports:15
		
		bankData = test.retrieveBankData(JO_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		
		bankData = test.retrieveBankData(KW_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		
		bankData = test.retrieveBankData(KZ_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		
		bankData = test.retrieveBankData(LB_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		
		bankData = test.retrieveBankData(LC_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		
		checkSepaIban(test, bankData, LI_IBAN); // BankSupports:7
		checkSepaIban(test, bankData, LT_IBAN); // BankSupports:7
		checkSepaIban(test, bankData, LU_IBAN); // BankSupports:15
		checkSepaIban(test, bankData, LV_IBAN); // BankSupports:1

//		bankData = test.retrieveBankData(LY_IBAN); // TODO added to iban_registry on 09/01/2020
//		assertNotNull(bankData);
//		LOG.info("retrieveBankData:"+ bankData);
//		assertNotNull(bankData.getBic()); // exception
		
		checkSepaIban(test, bankData, MC_IBAN); // BankSupports:7
		
		bankData = test.retrieveBankData(MD_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		
		bankData = test.retrieveBankData(ME_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		
		bankData = test.retrieveBankData(MK_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		
		bankData = test.retrieveBankData(MR_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		
		checkSepaIban(test, bankData, MT_IBAN); // BankSupports:15

		bankData = test.retrieveBankData(MU_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		
		checkSepaIban(test, bankData, NL_IBAN); // BankSupports:15
		checkSepaIban(test, bankData, NO_IBAN); // BankSupports:15

		bankData = test.retrieveBankData(PK_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		
		checkSepaIban(test, bankData, PL_IBAN); // BankSupports:1

		bankData = test.retrieveBankData(PS_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
				
		checkSepaIban(test, bankData, PT_IBAN); // BankSupports:15

		bankData = test.retrieveBankData(QA_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		
		checkSepaIban(test, bankData, RO_IBAN); // BankSupports:1

		bankData = test.retrieveBankData(RS_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());

		bankData = test.retrieveBankData(SA_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());

		bankData = test.retrieveBankData(SC_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		
		checkSepaIban(test, bankData, SE_IBAN); // BankSupports:15
		checkSepaIban(test, bankData, SI_IBAN); // BankSupports:15
		checkSepaIban(test, bankData, SK_IBAN); // BankSupports:15
		checkSepaIban(test, bankData, SM_IBAN); // BankSupports:7

		bankData = test.retrieveBankData(ST_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());

		bankData = test.retrieveBankData(SV_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());

		bankData = test.retrieveBankData(TL_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData.toString());
		assertNotNull(bankData.getBic());

		bankData = test.retrieveBankData(TN_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());

		bankData = test.retrieveBankData(TR_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());

		bankData = test.retrieveBankData(UA_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		
		checkSepaIban(test, bankData, VA_IBAN); // BankSupports:1

		bankData = test.retrieveBankData(VG_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());

		bankData = test.retrieveBankData(XK_IBAN);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
   }

    void checkSepaIban(IbanToBankData test, BankData bankData, String iban) {
    	bankData = test.getBankData(iban);
		LOG.info("getBankData:"+ bankData);
		bankData = test.retrieveBankData(iban);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData);
		assertNotNull(bankData.getBic());
		assertTrue((bankData.getBankSupports() & SepaData.SCT)>0); // bank supports SEPA Credit Transfer
    }

    void checkNonSepaIban(String iban) {
    	bankData = ibanToBankData.retrieveBankData(iban);
		assertNotNull(bankData);
		LOG.info("retrieveBankData:"+ bankData.toString());
		assertNotNull(bankData.getBic());
		// country, country_iso und account
		// werden bewußt in IbanToBankData.parseBankDataObject nicht gesetzt!
    }
    
}
