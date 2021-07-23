
## Network dependencies

### JabRef 
https://github.com/JabRef/jabref


**Examples:**

Network connection is tested in `URLDownloadTest`
https://github.com/JabRef/jabref/blob/bb011c9313367a28990ae213b3920fe6cd10d1dc/src/test/java/org/jabref/logic/net/URLDownloadTest.java

test fails when connection is down
To replicate:

1. run tests in `org.jabref.logic.net` as `gradle test --tests org.jabref.logic.net.*` with connection enabled.

2. rerun all tests as `gradle test --tests org.jabref.logic.net.*` now with connection disabled.

throws an `UnknownHostException` as

```
Test testCheckConnectionSuccess() FAILED
  kong.unirest.UnirestException: java.net.UnknownHostException: www.google.com
      at kong.unirest.DefaultInterceptor.onFail(DefaultInterceptor.java:43)
      at kong.unirest.CompoundInterceptor.lambda$onFail$2(CompoundInterceptor.java:54).. 
```
      
And

```
Test testFileDownload() FAILED
  java.net.UnknownHostException: www.google.com
      at java.base/sun.nio.ch.NioSocketImpl.connect(NioSocketImpl.java:567)
      at java.base/java.net.Socket.connect(Socket.java:645)
      at java.base/sun.net.NetworkClient.doConnect(NetworkClient.java:177)...
```

By using saflate, test that were flaky due to network connectivity successfully skipped.


### Apache pdfbox: 
https://github.com/apache/pdfbox 

Network connection is tested in `TestCreateSignature`
pdfbox/examples/src/test/java/org/apache/pdfbox/examples/pdmodel/TestCreateSignature

tests `testAddValidationInformation ` and `testCRL` fail when connection is down
To replicate:
1. run tests  `testCRL ` and ` testAddValidationInformation` as ` mvn test -Dtest=TestCreateSignature#testCRL `
with connection enabled.

2. rerun all tests with connection disabled.



Similar other tests (fail when internet connection is disabled)

`PDFieldTreeTest#test5044` :pdfbox/src/test/java/org/apache/pdfbox/pdmodel/interactive/form/PDFieldTreeTest

`TestRadioButtons#testPDFBox3656ByValidIndex` :examples/src/test/java/org/apache/pdfbox/examples/interactive/form/TestCreateSimpleForms


`PDAcroFormFromAnnotsTest#testFromAnnots4985DefaultMode` : pdfbox/src/test/java/org/apache/pdfbox/pdmodel/interactive/form/PDAcroFormFromAnnotsTest




### Results:


#### Flaky tests in PDFBox.pdfbox component

**33 tests fail with  UnknownHost exception**

```
RandomAccessReadBufferTest.testPDFBOX5111
PDFontTest.testPDFox5048
PDAcroFormFlattenTest.testFlatten
PDAcroFormFlattenTest.testFlatten
PDAcroFormFlattenTest.testFlatten
PDAcroFormFlattenTest.testFlatten
PDAcroFormFlattenTest.testFlatten
PDAcroFormFlattenTest.testFlatten
PDAcroFormFlattenTest.testFlatten
PDAcroFormFlattenTest.testFlatten
PDAcroFormFlattenTest.testFlatten
PDAcroFormFlattenTest.testFlatten
PDAcroFormFlattenTest.testFlatten
PDAcroFormFromAnnotsTest.testFromAnnots3891CreateFields
PDAcroFormFromAnnotsTest.testFromAnnots3891DontCreateFields
PDAcroFormFromAnnotsTest.testFromAnnots3891NullField
PDAcroFormFromAnnotsTest.testFromAnnots3891ValidateFont
PDAcroFormFromAnnotsTest.testFromAnnots4985CorrectionMode
PDAcroFormFromAnnotsTest.testFromAnnots4985DefaultMode
PDAcroFormFromAnnotsTest.testFromAnnots4985WithoutCorrectionMode
PDAcroFormGenerateAppearancesTest.testGetAcroForm
PDAcroFormGenerateAppearancesTest.testGetAcroForm
PDAcroFormGenerateAppearancesTest.testGetAcroForm
PDAcroFormTest.testIllegalFieldsDefinition
PDFieldTreeTest.test5044
TestRadioButtons.testPDFBox3656ByInvalidExportValue
TestRadioButtons.testPDFBox3656ByInvalidIndex
TestRadioButtons.testPDFBox3656ByValidExportValue
TestRadioButtons.testPDFBox3656ByValidIndex
TestRadioButtons.testPDFBox3656NotInUnison
TestRadioButtons.testPDFBox4617IndexForSetByIndex
TestRadioButtons.testPDFBox4617IndexForSetByOption
TestRadioButtons.testPDFBox4617IndexNoneSelected
```


***Note : by applying the rule,  another flaky behaviour is observed in some tests - some tests are correctly skipped but couple of them will throw an error. In the first run 3/33 have thrown an error . In the next run, 5/33 have thrown an error!***

**Note from Shawn:** test /pdfbox/pdfbox/src/test/java/org/apache/pdfbox/pdmodel/interactive/form/PDAcroFormFlattenTest.testFlatten fail due to java version. It failed on java 8 but passes on Java 11. Should be added to our list of environmental flakiness.



### unresolved issues:
The following UnknownHost exception is still thrown for the following tests :

```
PDAcroFormFromAnnotsTest.testFromAnnots3891NullField:294 » UnknownHost issues....
[ERROR]   PDAcroFormFromAnnotsTest.testFromAnnots3891ValidateFont:239 » UnknownHost issu...
[ERROR]   PDAcroFormFromAnnotsTest.testFromAnnots4985DefaultMode:63 » UnknownHost issues...
[ERROR]   PDAcroFormGenerateAppearancesTest.testGetAcroForm:51 » UnknownHost issues.apac...
[ERROR]   TestRadioButtons.testPDFBox3656ByValidExportValue:165 » UnknownHost issues.apa..
```
------------------------------------------------

Flaky tests in PDFBox.examples component

```
TestCreateSignature.testAddValidationInformation
TestCreateSignature.testCR
```

Both are successfully skipped with the use of saflate rules



## Environmental dependencies

- **flaky tests dataset**: http://mir.cs.illinois.edu/flakytests/summary.html

NOD tests

- **maven-surefire** https://github.com/apache/maven-surefire.git

examples:

https://github.com/apache/maven-surefire/blob/b9b2381a3dba6574bb69bd91d45fe0edea29c779/surefire-booter/src/test/java/org/apache/maven/surefire/booter/SystemUtilsTest.java
