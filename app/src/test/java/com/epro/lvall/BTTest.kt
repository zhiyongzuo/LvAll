package com.epro.lvall

import android.bluetooth.BluetoothAdapter
import org.junit.Test

class BTTest {

    @Test
    fun testBTAddress() {
        val btAddress = "18:10:77:00:10:55"
        val isTrue = checkBluetoothAddress(btAddress)
        System.out.println(isTrue)
    }

    fun checkBluetoothAddress(address: String?): Boolean {
        val BT_ADDRESS_LENGTH = 17
        if (address == null || address.length != BT_ADDRESS_LENGTH) {
            return false
        }
        loop@ for (i in 0 until BT_ADDRESS_LENGTH) {
            val c = address[i]
            System.out.println(c)
            when (i % 3) {
                0, 1 -> {
                    if (c >= '0' && c <= '9' || c >= 'A' && c <= 'F') {
                        // hex character, OK
                        continue@loop
                    }
                    System.out.println(c)
                    return false
                }
                2 -> {
                    if (c == ':') {
                        continue@loop // OK
                    }
                    System.out.println(c)
                    return false
                }
            }
        }
        return true
    }
}