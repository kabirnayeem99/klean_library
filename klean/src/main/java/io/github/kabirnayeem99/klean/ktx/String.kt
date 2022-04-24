package io.github.kabirnayeem99.klean.ktx

import android.os.Build
import android.telephony.PhoneNumberUtils
import android.text.Editable
import android.text.Html
import android.util.Log
import android.util.Patterns
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

private const val TAG = "String"

fun String.toDate(
    format: String,
    timeZone: TimeZone = TimeZone.getDefault(),
    locale: Locale = Locale.getDefault()
): Date? {
    val dateFormat = SimpleDateFormat(format, locale)
    dateFormat.timeZone = timeZone

    return try {
        dateFormat.parse(this)
    } catch (e: ParseException) {
        null
    }
}

fun String.toUTCDate(format: String): Date? {
    return toDate(
        format = format,
        timeZone = TimeZone.getTimeZone("UTC"),
        locale = Locale.US
    )
}

fun String.isEmail(): Boolean {
    return if (this.length < 5) {
        false
    } else !this.isEmpty() && this.matches("[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}".toRegex())
}

fun String.isPhone(): Boolean {
    return !this.isEmpty() && this.matches("^[+]?\\d{7,}$".toRegex())
}

fun String.isAlphanumeric(): Boolean {
    return this.replace(Regex("[a-zA-Z0-9]"), "").isEmpty()
}

fun String.fromHtml(): String {
    try {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Html.fromHtml(this, Html.FROM_HTML_MODE_LEGACY).toString()
        } else {
            Html.fromHtml(this).toString()
        }
    } catch (e: NullPointerException) {
        return ""
    }
}

fun String.nameInitials(): String {
    val components = this.split(" ")

    val initialsBuffer = StringBuffer()
    for (component in components) {
        val cleanComponent = component.replace("\\W".toRegex(), "")
        if (cleanComponent.isEmpty()) {
            continue
        }
        initialsBuffer.append(cleanComponent.firstOrNull() ?: "")
    }

    if (initialsBuffer.length > 2) {
        initialsBuffer.delete(1, initialsBuffer.length - 1);
    }
    return initialsBuffer.toString().toUpperCase();
}

fun String.extractAlphanumerics(): String {
    return this.replace("[^a-zA-Z0-9]".toRegex(), "")
}

fun Editable.extractAlphanumerics(): Editable {
    val alphanumericString = this.replace("[^a-zA-Z0-9]".toRegex(), "")
    return Editable.Factory.getInstance().newEditable(alphanumericString)
}

fun String.extractDigits(): String {
    return this.replace("\\D".toRegex(), "")
}

fun Editable.extractDigits(): Editable {
    val digitsOnly = this.toString().replace("\\D".toRegex(), "")
    return Editable.Factory.getInstance().newEditable(digitsOnly)
}

val String.base64Decoded
    get() = try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String(Base64.getDecoder().decode(toByteArray()))
        } else {
            null
        }
    } catch (e: IllegalArgumentException) {
        null
    }

/**
 * Using UTF-8 encoding
 */
val String.base64Encoded: String
    get() = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        Base64.getEncoder().encodeToString(toByteArray())
    } else {
        ""
    }

/**
 * Splits by spaces, newlines, and tabs only
 */
val String.camelCased: String
    get() {
        val split = toLowerCase().split(' ', '\n', '\t').toMutableList()
        if (split.size > 1) {
            for (i in 1..split.size - 1) {
                if (split[i].length > 1) {
                    val charArray = split[i].toCharArray()
                    charArray[0] = charArray[0].toUpperCase()
                    split[i] = String(charArray)
                }
            }
        }
        return split.joinToString("")
    }

val String.containsLetters get() = matches(".*[a-zA-Z].*".toRegex())

val String.containsNumbers get() = matches(".*[0-9].*".toRegex())


/**
 * Does not allow whitespace or symbols
 * Allows empty string
 */
val String.isAlphabetic get() = matches("^[a-zA-Z]*$".toRegex())

/**
 * Does not allow whitespace or symbols
 * Allows empty string
 */
val String.isNumeric get() = matches("^[0-9]*$".toRegex())


/**
 * If there is more than one most common character, this returns the character that occurred first in the String
 */
val String.mostCommonCharacter: Char?
    get() {
        if (length == 0) return null
        val map = HashMap<Char, Int>()
        for (char in toCharArray()) map.put(char, (map[char] ?: 0) + 1)
        var maxEntry = map.entries.elementAt(0)
        for (entry in map) maxEntry = if (entry.value > maxEntry.value) entry else maxEntry
        return maxEntry.key
    }


fun String?.formatAsPhoneNumber(): String {
    val phoneNumber = this
    if (phoneNumber.isNullOrBlank()) return ""
    return try {
        PhoneNumberUtils.formatNumber(phoneNumber, Locale.getDefault().country)
    } catch (e: Exception) {
        Log.e(
            TAG,
            "Failed to format $phoneNumber -> ${e.localizedMessage}"
        )
        ""
    }
}

fun String?.formatWebAddressToBeHttps(): String {

    val url = this

    if (url.isNullOrBlank()) return ""

    if (url.startsWith("https://")) return url

    return try {
        url.replace("http://", "https://")
    } catch (e: Exception) {
        Log.e(
            TAG,
            "Failed to format web address to be http -> ${e.localizedMessage}"
        )
        url.toString()
    }
}

fun String?.isValidEmail(): Boolean {

    val email = this

    if (email.isNullOrBlank()) return false

    return Patterns.EMAIL_ADDRESS.matcher(email).matches()

}