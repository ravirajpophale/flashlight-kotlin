package flashlight.ravirajpophale.com

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.from)

        // Check if message contains a data payload.
        if (remoteMessage.data.size > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.data)
        }
        if (remoteMessage.notification != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.notification!!.body)
        }
    }

    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")
        sendRegistrationToServer()
    }

    private fun sendRegistrationToServer() {
        // TODO: Implement this method to send token to your app server.
    }

    companion object {
        private const val TAG = "MyFirebaseMsgService"
    }
}