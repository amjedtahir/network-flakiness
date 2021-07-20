## Network dependencies

- **JabRef** https://github.com/JabRef/jabref/tree/bb011c9313367a28990ae213b3920fe6cd10d1dc


examples:
Network connection is tested in `URLDownloadTest`
https://github.com/JabRef/jabref/blob/bb011c9313367a28990ae213b3920fe6cd10d1dc/src/test/java/org/jabref/logic/net/URLDownloadTest.java

test fail when connection is down
To replicate:
1. run tests in `org.jabref.logic.net` as `gradle test --tests org.jabref.logic.net.*` with connection enabled.
2. rerun all tests as `gradle test --tests org.jabref.logic.net.*` now with connection disabled.

throws an `AssertionFailedError`

Full error

``` Test testCheckConnectionSuccess() FAILED

  kong.unirest.UnirestException: java.net.UnknownHostException: www.google.com
      at kong.unirest.DefaultInterceptor.onFail(DefaultInterceptor.java:43)
      at kong.unirest.CompoundInterceptor.lambda$onFail$2(CompoundInterceptor.java:54)..
```

```Test testFileDownload() FAILED

  java.net.UnknownHostException: www.google.com
      at java.base/sun.nio.ch.NioSocketImpl.connect(NioSocketImpl.java:567)
      at java.base/java.net.Socket.connect(Socket.java:645)
      at java.base/sun.net.NetworkClient.doConnect(NetworkClient.java:177)...```
