# iban.com-API

A library for dealing with [iban.com/validation-api](https://www.iban.com/validation-api) to get information about banks.

Example: you have a valid iban `MC58 1166 8400 0112 3456 7890 191`
* you can see, this iban comes from Monaco, because the country code is MC
* there are many banks in Monaco 
* with some [wikipedia](https://en.wikipedia.org/wiki/International_Bank_Account_Number) information, you can discover the "bank_code":11668 and "branch_code":40001
* but which bank? What is the BIC/swift_code?

```java
IbanToBankData test = new IbanToBankData(API_KEY);
BankData bankData = test.getBankData("MC5811668400011234567890191");
System.out.println("BIC : "+bankData.getBic());
System.out.println("bank name : "+bankData.getBank());
System.out.println("address : "+bankData.getAddress());
System.out.println("support_codes : "+bankData.getBankSupports());
```

results to

```
BIC : BERLMCMCXXX
bank name : EDMOND DE ROTHSCHILD MONACO
address : 2 AV DE MONTE CARLO
support_codes :7
```

With the BIC/swift_code you can query the parent of the Monaco bank. It is [Edmond de Rothschild (Suisse) S.A.](https://search.gleif.org/#/record/52990049YMGDGTAMFX97) from Geneva.

## API_KEY

You need a key to use the [iban.com/validation-api](https://www.iban.com/validation-api). Of course, you can use a browser for a single validation. The intent of [this jar](https://github.com/klst-de/iban.com-API/releases/latest) is to integrate it in an application.

## SEPA support_codes

As of 2020, there are [36 members](https://github.com/klst-de/iban.com-API/blob/master/doc/EPC409-09%20EPC%20List%20of%20SEPA%20Scheme%20Countries%20v2.6%20-%20January%202020.pdf) in [SEPA / Single_Euro_Payments_Area](https://en.wikipedia.org/wiki/Single_Euro_Payments_Area), including Monaco.

There are 5 SEPA support schemes:
* `SCT -` Whether this bank supports SEPA Credit Transfer.      (de: Überweisungen)
* `SDD -` Whether this bank supports SEPA Direct Debit.         (de: Lastschrift)
* `COR1-` Whether this bank supports SEPA COR1.                 (de: Eillastschrift) 
* `B2B -` Whether this bank supports SEPA Business to Business. (de: Firmenlastschrift) 
* `SCC -` Whether this bank supports SEPA Card Clearing.

`support_codes :7` means `SCT + SDD + COR1`

## Requirements

The code depends to the following jars:
* json-simple-1.1.jar
* commons-validator-1.7.jar

## cmdline demo

* download the executable [jar-with-dependencies](https://github.com/klst-de/iban.com-API/releases/latest)
* start it:

```json
C:\proj>java -jar iban-to-bankdata-1.1.0-jar-with-dependencies.jar
Enter iban.com API key : <your API_KEY>
Enter iban : FI5542345670000081
iban FI5542345670000081
bank data [Bic:"ITELFIHHXXX", BankCode:423456, BranchCode:, Name:"SAVINGS BANK FINLAND", Address:"HEVOSENKENKA 3", BankSupports:7, Zip:"02600", City:"ESPOO"]

Enter iban :
Exit!
```
