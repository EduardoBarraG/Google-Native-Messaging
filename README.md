# Google Chrome Native Message.

## Description

Extensions and apps can exchange messages with native applications using an API that is similar to the other message passing APIs. Native applications that support this feature must register a native messaging host that knows how to communicate with the extension. More info [here](https://developer.chrome.com/extensions/nativeMessaging) 

This repository contains an example for communicates a Chrome extension with an application made in Java.

## Getting started

* Clone the repo with `git clone https://github.com/edobarrag/Google-Native-Messaging.git `
* [Read the information](https://developer.chrome.com/extensions/nativeMessaging) to learn about the components of examples.

### What's included

```
Google-Native-Messaging/
├── extension/
│   ├── icon-128.png
│   ├── main.html
│   ├── main.js
│   ├── manifest.json
├── host/
│   ├── com.google.native.chat.json
│   ├── host.jar
│   ├── install_host.bat
│   └── native-messaging-chat.bat
└── src/
    ├── App.java
    └── Main.java
```

## extension

To load the extension follow the steps in the next [tutorial](https://developer.chrome.com/extensions/getstarted#unpacked)

## host	

In this folder run install_host.bat to install the native messaging host.

## src
	
The folder app has 2 classes and App.java Main.java . The first class only starts the application, however App has all the logic of the host. This class contains two main methods read() and write(), which read and write messages to the extension. The exchange of messages is done through the standard input / output . For the text of the messages Gson object is used .

Enjoy code =)

## important notes.

You should add to your java project the Gson library. The library You can download it [here.](https://github.com/google/gson)

