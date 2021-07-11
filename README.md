# ScanLogin
Using Xposed to achieve Automatic confirmation login by scanning QRCode security login to QQ/TIM/QQInternational/QQLite.
Supported Automatic confirmation WeChat PC login.

## ScanLogin-X修改了啥
ScanLogin-X forked from [ScanLogin[(https://github.com/wangzailfm/ScanLogin)

目前唯一的改动是使微信自动登录只有在黑屏（锁屏）的情况下才生效。这么做是因为我希望自动勾上“同步最近的消息”，然而我试了通过XposedHook似乎点不到那个勾，所以我只能让它在亮屏时不生效，然后亮屏时的自动登录（主要是扫码后的），由大圣净化之类的使用Android辅助功能模拟点击API来实现的工具来操作，这类工具都有自动勾上“同步最近的消息”的功能。第一次扫完码由这类工具勾上“同步最近的消息”后，以后在黑屏状态下就能由ScanLogin-X来自动登录并且默认已勾上“同步最近的消息”了。

如果哪位大佬知道如何通过Xposed Hook勾上“同步最近的消息”欢迎Commit。

鉴于看起来原作已经几年没更新，看起来不维护了，所以我改了包名，避免跟原作冲突。


### View the article
[使用Xposed实现QQ/TIM自动确认电脑扫一扫登录](https://www.jowanxu.top/2017/10/12/%E4%BD%BF%E7%94%A8Xposed%E5%AE%9E%E7%8E%B0QQ-TIM%E8%87%AA%E5%8A%A8%E7%A1%AE%E8%AE%A4%E7%94%B5%E8%84%91%E6%89%AB%E4%B8%80%E6%89%AB%E7%99%BB%E5%BD%95/)

### Sample
#### TIM
![AutoLogin](./scanLoginTIM.gif)

#### WeChat
![autoConfirmLogin](./autoConfirmWeChatLogin.gif)

## Note
All apk is download by [coolApk](https://www.coolapk.com/).


## License
```
Copyright 2017 Jowan

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

	http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
