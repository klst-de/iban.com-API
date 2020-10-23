package com.klst.iban;

import com.klst.iban.Result.BankData;

public interface IbanBankData {
	
	/**
	 * validates iban and get BankData without retrieving the information from iban.com api.
	 * 
	 * @param iban
	 * @return BankData object
	 */
	public BankData getBankData(String iban);

}
