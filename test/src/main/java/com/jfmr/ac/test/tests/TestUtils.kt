package com.jfmr.ac.test.tests

import com.google.gson.Gson
import java.io.ByteArrayOutputStream
import java.io.InputStream
import java.lang.reflect.Type

object TestUtils {

    private fun readTextStream(inputStream: InputStream): String {
        val byteArraySize = 1024
        val invalidIndex = -1
        val offset = 0
        val result = ByteArrayOutputStream()
        val buffer = ByteArray(byteArraySize)
        var length: Int
        while (inputStream.read(buffer).also { length = it } != invalidIndex) {
            result.write(buffer, offset, length)
        }
        return result.toString(Charsets.UTF_8.displayName())
    }

    fun getObjectFromJson(fileName: String, type: Type): Any {
        val inputStream = this.javaClass.classLoader!!.getResourceAsStream(fileName)
        val resultString = readTextStream(inputStream)
        return Gson().fromJson(resultString, type)
    }

    fun getObjectAsString(fileName: String): String {
        val inputStream = this.javaClass.classLoader!!.getResourceAsStream(fileName)
        return readTextStream(inputStream).replace("\n", "")
    }

}
