package com.wooriyo.seekhan.FCM

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.wooriyo.seekhan.MyApplication

class MyFirebaseMessagingService: FirebaseMessagingService() {

    private val TAG = "FirebaseService"

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        MyApplication.pref.setToken(token)
        Log.d(TAG, "토큰 => $token")
    }

//    override fun onMessageReceived(remoteMessage: RemoteMessage) {
//        super.onMessageReceived(remoteMessage)
//
//        if(remoteMessage.data.isNotEmpty()){
//            Log.d(TAG, remoteMessage.data["body"].toString())
//            Log.d(TAG, remoteMessage.data["title"].toString())
//            sendNotification(remoteMessage)
//        }
//
//        else {
//            Log.d(TAG, "data가 비어있습니다. 메시지를 수신하지 못했습니다.")
//            Log.d(TAG, remoteMessage.data.toString())
//        }
//    }

//    private fun sendNotification(remoteMessage: RemoteMessage) {
//        // RequestCode, Id를 고유값으로 지정하여 알림이 개별 표시되도록 함
//        val uniId: Int = (System.currentTimeMillis() / 7).toInt()
//
//        // 일회용 PendingIntent
//        // PendingIntent : Intent 의 실행 권한을 외부의 어플리케이션에게 위임한다.
//        val intent = Intent(this, MainActivity::class.java)
//        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // Activity Stack 을 경로만 남긴다. A-B-C-D-B => A-B
//        val pendingIntent =
//            PendingIntent.getActivity(this, uniId, intent, PendingIntent.FLAG_ONE_SHOT)
//
//        // 알림 채널 이름
//        val channelId = "채널이름"
//
//        // 알림 소리
//        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
//
//        // 알림에 대한 UI 정보와 작업을 지정한다.
//        val notificationBuilder = NotificationCompat.Builder(this, channelId)
//            .setSmallIcon(R.mipmap.ic_launcher) // 아이콘 설정
//            .setContentTitle(remoteMessage.data["body"].toString()) // 제목
//            .setContentText(remoteMessage.data["title"].toString()) // 메시지 내용
//            .setAutoCancel(true)
//            .setSound(soundUri) // 알림 소리
//            .setContentIntent(pendingIntent) // 알림 실행 시 Intent
//
//        val notificationManager =
//            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
//
//        // 오레오 버전 이후에는 채널이 필요하다.
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//            val channel =
//                NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_DEFAULT)
//            notificationManager.createNotificationChannel(channel)
//        }
//
//        // 알림 생성
//        notificationManager.notify(uniId, notificationBuilder.build())
//    }
}