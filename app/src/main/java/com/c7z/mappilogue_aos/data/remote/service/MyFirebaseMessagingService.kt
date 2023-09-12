package com.c7z.mappilogue_aos.data.remote.service

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.c7z.mappilogue_aos.R
import com.c7z.mappilogue_aos.presentation.ui.main.MainActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage


class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = "FCM"

    // 새 토큰 생성
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "new token : $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // 메세지에 데이터가 포함되어 있는지 확인
        if (remoteMessage.data.isNotEmpty()) {
            Log.d(TAG, "Message data : ${remoteMessage.data}")
            sendNotification(remoteMessage.data["title"].toString(), remoteMessage.data["body"].toString())
        } else {
            remoteMessage.notification?.let {
                sendNotification(remoteMessage.notification!!.title.toString(), remoteMessage.notification!!.body.toString())
            }
        }
    }

    private fun sendNotification(title: String, body: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        // pendingIntent -> 인텐트의 실행 권한을 외부 어플리케이션에게 위임함
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_IMMUTABLE)

        val channelId = "Mappilogue" // 채널 이름
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION) // 알림 소리

        // 알림 정보
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle(title)
            .setContentText(body)
            .setAutoCancel(true)
            .setSound(soundUri)
            .setContentIntent(pendingIntent)

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 오레오 버전 이후에는 채널 생성
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        // 알림 생성
        notificationManager.notify(0, notificationBuilder.build())
    }
}