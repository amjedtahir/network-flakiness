## Network dependencies

- **JabRef** https://github.com/JabRef/jabref/tree/bb011c9313367a28990ae213b3920fe6cd10d1dc


examples:
Network connection is tested in `URLDownloadTest`
https://github.com/amjedtahir/jabref/tree/master/src/test/java/org/jabref/logic/net

test fail when connection is down
To replicate:
1. run tests in `org.jabref.logic.net` using the following command `gradle test --tests org.jabref.logic.net.*` with internet connection enabled.
2. rerun all tests `gradle test --tests org.jabref.logic.net.*` now with connection disabled.


Stacktrace

``` Test testCheckConnectionSuccess() FAILED

  kong.unirest.UnirestException: java.net.UnknownHostException: www.google.com
      at kong.unirest.DefaultInterceptor.onFail(DefaultInterceptor.java:43)
      at kong.unirest.CompoundInterceptor.lambda$onFail$2(CompoundInterceptor.java:54)..
```

```Test testFileDownload() FAILED

  java.net.UnknownHostException: www.google.com
      at java.base/sun.nio.ch.NioSocketImpl.connect(NioSocketImpl.java:567)
      at java.base/java.net.Socket.connect(Socket.java:645)
      at java.base/sun.net.NetworkClient.doConnect(NetworkClient.java:177)...
```

Tests still throw the same exception when using rules. See the example in `URLDownloadTest2` under https://github.com/amjedtahir/jabref/tree/master/src/test/java/org/jabref/logic/net 



