package com.abhishekjnvk.smsforwarder.services

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.provider.Telephony
import android.telephony.SmsMessage


class SmsListener : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action != Telephony.Sms.Intents.SMS_RECEIVED_ACTION) return
        val bundle = intent.extras!!
        val pduObjects = bundle["pdus"] as Array<*>? ?: return
        for (messageObj in pduObjects) {
            val currentMessage = SmsMessage.createFromPdu(
                messageObj as ByteArray,
                bundle["format"] as String
            )
            val senderNumber = currentMessage.displayOriginatingAddress
            val forwardContent = currentMessage.displayMessageBody
            if (currentMessage.messageClass == SmsMessage.MessageClass.CLASS_0) return

            val sharedPreferences = context.getSharedPreferences("sharedPreferences", Context.MODE_PRIVATE)
            val backendurl = sharedPreferences.getString("backendURL", "https://abhishekjnvk.loca.lt")
            if (backendurl != null) {
                NetworkCall().saveToServer(senderNumber,forwardContent,backendurl)

            }
        }

    }
}

