package ch.semi.ledc.backend.interfaces

import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothDevice
import android.bluetooth.BluetoothSocket
import android.util.Log
import ch.semi.ledc.backend.Backend
import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.util.*

class BluetoothSerial : Backend {

    // Debugging
    private val TAG = "BLUETOOTH SERIAL"

    // Bluetooth
    private var mBluetoothAdapter: BluetoothAdapter? = null
    private var mSocket: BluetoothSocket? = null
    private var mDevice: BluetoothDevice? = null
    private var mOutputStream: OutputStream? = null
    private var mInputStream: InputStream? = null

    companion object {

        private var uniqueInstance: BluetoothSerial? = null

        val instance: BluetoothSerial
        get() {
            if (uniqueInstance == null){
                uniqueInstance = BluetoothSerial()
            }
            return uniqueInstance!!
        }

    }

    private fun findBT() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter()
        if (mBluetoothAdapter == null) {
            //			Toast.makeText(getApplicationContext(), "No Bluetooth Adapter available", Toast.LENGTH_LONG).show();
        }

        val pairedDevices = mBluetoothAdapter!!.bondedDevices
        if (pairedDevices.size > 0) {
            for (device in pairedDevices) {
                if (device.name == "RN42-CDB6") {
                    mDevice = device
                    break
                }
            }
        }
        Log.d(TAG, "Bluetooth Device found")
    }

    @Throws(IOException::class)
    private fun openBT() {
        val uuid = UUID.fromString("00001101-0000-1000-8000-00805f9b34fb") //Standard SerialPortService ID
        mSocket = mDevice!!.createRfcommSocketToServiceRecord(uuid)
        mSocket!!.connect()
        mOutputStream = mSocket!!.outputStream
        mInputStream = mSocket!!.inputStream

        //		this.mIsConnected=true;

        Log.d(TAG, "Connection established")

    }

    @Throws(IOException::class)
    private fun closeBT() {
        mOutputStream!!.close()
        mInputStream!!.close()
        mSocket!!.close()
    }

    override fun connect() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun disconnect() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun transfer() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


}