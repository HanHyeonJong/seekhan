package com.wooriyo.seekhan.util

import android.util.Base64
import java.net.URLEncoder

class Encoder {
    companion object {
        fun urlEncode(str: String): String? {
            return URLEncoder.encode(str, "UTF-8")
        }

        fun base64Encode(str: String): String? {
            return Base64.encodeToString(str.toByteArray(), 0)
        }
    }
}