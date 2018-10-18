package com.example.zhongzhoujianshe.ins;

import android.bluetooth.BluetoothDevice;
import android.os.Parcelable;


public class BluetoothPair {
    private BluetoothDevice device;

    public BluetoothPair(BluetoothDevice device) {
        this.device = device;
    }

    public BluetoothDevice getDevice() {
        return this.device;
    }
}
