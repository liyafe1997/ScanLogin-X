package top.scanloginxmod.scanlogin.hook

import android.app.Activity
import android.app.KeyguardManager
import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import android.widget.Button
import android.widget.Toast
import de.robv.android.xposed.XC_MethodHook
import de.robv.android.xposed.XposedHelpers
import de.robv.android.xposed.callbacks.XC_LoadPackage
import top.scanloginxmod.scanlogin.Constant
import top.scanloginxmod.scanlogin.getPreferenceBoolean
import top.scanloginxmod.scanlogin.tryHook


class HookWeChat {
    companion object {
        private const val WECHAT_HOOK_CLASS_NAME = "com.tencent.mm.plugin.webwx.ui.ExtDeviceWXLoginUI"
        private val TAG = HookWeChat::class.java.simpleName
    }

    /**
     * 自动确认微信电脑端登录

     * @param lpParam LoadPackageParam
     */
    fun autoConfirmWeChatLogin(lpParam: XC_LoadPackage.LoadPackageParam) {
        // 获取Class
        val loginClass = XposedHelpers.findClassIfExists(WECHAT_HOOK_CLASS_NAME, lpParam.classLoader)
                ?: return
        // 获取Class里面的Field
        val declaredFields = loginClass.declaredFields ?: return
        tryHook(TAG, Constant.HOOK_ERROR) {
            XposedHelpers.findAndHookMethod(loginClass, Constant.ON_CREATE, Bundle::class.java, object : XC_MethodHook() {
                @Throws(Throwable::class)
                override fun afterHookedMethod(param: MethodHookParam) {
                    val activity = param.thisObject as Activity
                    val enable = getPreferenceBoolean(activity, key = Constant.WECHAT_ENABLE)
                    if (!enable) return

                    val context: Context = activity.getApplicationContext()
                    val powerManager: PowerManager = context.getSystemService(Context.POWER_SERVICE) as PowerManager
                    val isScreenOn: Boolean = powerManager.isScreenOn()


                    val mKeyguardManager: KeyguardManager = context.getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager
                    val isKeyguard: Boolean = mKeyguardManager.inKeyguardRestrictedInputMode()

                    if (isKeyguard == true || isScreenOn == false) {
                        declaredFields.filter {
                            it.type.canonicalName.toString() == Constant.ANDROID_WIDGET_BUTTON
                        }.forEach {
                            it.isAccessible = true
                            val loginButton = it.get(param.thisObject) as Button
                            val loginButtonText = loginButton.text.toString()
                            if (Constant.WECHAT_LOGIN_TEXT == loginButtonText
                                    || Constant.WECHAT_LOGIN_TEXT_CF == loginButtonText
                                    || Constant.WECHAT_LOGIN_TEXT_EN == loginButtonText
                                    || Constant.WECHAT_LOGIN_TEXT_JP == loginButtonText) {
                                loginButton.performClick()
                                Toast.makeText(activity, Constant.AUTO_LOGIN, Toast.LENGTH_SHORT).show()
                            }
                        }
                    }
                }
            })
        }

    }

}