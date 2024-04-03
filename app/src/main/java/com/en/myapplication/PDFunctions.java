package com.en.myapplication;

import android.util.Log;

public class PDFunctions {
    public static native int Clear(int i);

    public static native int ClosePort(String str);

    public static native int DisplayCounter();

    public static native int Initialize();

    public static native int MoveCursor(int i);

    public static native int OpenPort(String str, int i);

    public static native int PortDiscovery(String str, int i);

    public static native int SelectCodePage(int i);

    public static native int SelectInternationalCharacter(int i);

    public static native int SetCounter(int i, int i2);

    public static native int ShowString(String str);

    public static native int SpecifyMode(int i);

    public static native int myClosePort(int i);

    public static native int myOpenPort(int i, int i2);


static {
    try {
        Log.d("JNILoad", "Trying to load libPosPDdrv.so");
        String arch = System.getProperty("os.arch");

        System.loadLibrary("PosPDdrv");
    } catch (UnsatisfiedLinkError e) {
        Log.e("JNILoad", "WARNING: Could not load libPosPDdrv.so:::"+e.getMessage());
    }
}




}
