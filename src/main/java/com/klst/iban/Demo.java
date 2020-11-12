package com.klst.iban;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import com.klst.iban.Result.BankData;

public class Demo {

	private static Demo instance = null; // SINGLETON
	
	public static Demo getInstance() {
		if (instance == null) {
			instance = new Demo();
		}
		return instance;
	}

	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	IbanToBankData ibanToBankData = null;
	
	public void run() {
		System.out.print("Enter iban.com API key : ");
		try {
			String input = br.readLine();
			if (input.isEmpty()) {
                System.out.println("Exit!");
                System.exit(0);
            }
			ibanToBankData = new IbanToBankData(input);
			while (true) {

                System.out.print("Enter iban : ");
                input = br.readLine();

    			if (input.isEmpty()) {
                    System.out.println("Exit!");
                    System.exit(0);
                }

            	BankData bankData = ibanToBankData.getBankData(input);

                System.out.println("iban "+input +
                		(bankData==null ? " : API key or iban is not valid!" : "\nbank data "+bankData.toString()) + "\n");
			}
            
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}

	public static void main(String[] args) throws Exception {
		Demo.getInstance().run();
	}
}
