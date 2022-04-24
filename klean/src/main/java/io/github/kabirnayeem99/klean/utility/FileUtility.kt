package io.github.kabirnayeem99.klean.utility

import android.content.Context
import android.net.Uri
import android.util.Log
import io.github.kabirnayeem99.klean.BuildConfig
import java.io.*


private const val TAG = "FileUtility"

/**
 * File utility to handle safely accessing file in the android file system.
 *
 * @property context
 * @constructor Create [FileUtility]
 */
class FileUtility(private val context: Context) {
    /**
     * If the uri is a content uri, then try to open the input stream and create a file from it. If the
     * file is created, then return the path of the file. Otherwise, return null
     *
     * @param uri The URI of the file you want to get the path from.
     * @return The path to the file.
     */
    fun getPathFromInputStreamUri(uri: Uri): String? {
        var filePath: String? = null
        uri.authority?.let {
            try {
                context.contentResolver.openInputStream(uri).use {
                    val photoFile: File? = createTemporalFileFrom(it)
                    filePath = photoFile?.path
                }
            } catch (e: FileNotFoundException) {
                Log.e(TAG, "Failed to get path from $uri, as the file was not found.")
            } catch (e: IOException) {
                Log.e(TAG, "Failed to get path from $uri, as there is no internet.")
            }
        }
        return filePath
    }

    /**
     * Create a temporal file from an input stream
     *
     * @param inputStream The input stream to read from.
     * @return the file that has been created from the inputStream
     */
    @Throws(IOException::class)
    private fun createTemporalFileFrom(inputStream: InputStream?): File? {
        var targetFile: File? = null
        return if (inputStream == null) targetFile
        else {
            var read: Int
            val buffer = ByteArray(8 * 1024)
            targetFile = createTemporalFile()
            FileOutputStream(targetFile).use { out ->
                while (inputStream.read(buffer).also { read = it } != -1) {
                    out.write(buffer, 0, read)
                }
                out.flush()
            }
            targetFile
        }
    }

    /**
     * Create a temporary file in the application's files directory with a unique name
     *
     * @return the file that has been created of [File] type.
     */
    private fun createTemporalFile(): File {
        val file = File(context.filesDir, "${BuildConfig.LIBRARY_PACKAGE_NAME}_temp_picture.jpg")
        Log.d(TAG, "file ->\nname -> ${file.name}\npath -> ${file.absolutePath}")
        return file
    }

}