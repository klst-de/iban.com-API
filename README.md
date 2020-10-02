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

## API_KEY

You need a key the use the [iban.com/validation-api](https://www.iban.com/validation-api)

## SEPA support_codes

As of 2020, there are 36 members in [SEPA / Single_Euro_Payments_Area](https://en.wikipedia.org/wiki/Single_Euro_Payments_Area), including Monaco.

There are 5 SEPA support schemes:
* `SCT -` Whether this bank supports SEPA Credit Transfer.      (de: Überweisungen)
* `SDD -` Whether this bank supports SEPA Direct Debit.         (de: Lastschrift)
* `COR1-` Whether this bank supports SEPA COR1.                 (de: Eillastschrift) 
* `B2B -` Whether this bank supports SEPA Business to Business. (de: Firmenlastschrift) 
* `SCC -` Whether this bank supports SEPA Card Clearing.

`support_codes :7` means `SCT + SDD + COR1`
