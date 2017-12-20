package com.major.udid;

import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.util.UUID;

/**
 * TODO
 * Created by MEI on 2017/9/21.
 * <p>
 * https://stackoverflow.com/questions/2785485/is-there-a-unique-android-device-id
 */
@SuppressWarnings("all")
public class DeviceUtil{

    public static String getAndroidId(Context context){
        String m_szAndroidID = Settings.Secure.getString(context.getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        return m_szAndroidID;
    }

    public static String getDeviceId(Context context){
        TelephonyManager TelephonyMgr = (TelephonyManager)context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        if(TelephonyMgr != null){
            String szImei = TelephonyMgr.getDeviceId();
            return szImei;
        }
        return null;
    }

    // xiaomi 即使连接 wifi 也不能获取到 mac
    public static String getWLANMAC(Context ctx){
        WifiManager wm = (WifiManager)ctx.getApplicationContext().getSystemService(Context.WIFI_SERVICE);
        if(wm != null){
            WifiInfo connectionInfo = wm.getConnectionInfo();
            if(connectionInfo != null){
                String m_szWLANMAC = connectionInfo.getMacAddress();
                return m_szWLANMAC;
            }
        }
        return null;
    }

    public static String getWLANMACShell(){
        String macSerial = null;
        String str = "";
        try{
            Process pp = Runtime.getRuntime().exec("cat /sys/class/net/wlan0/address ");
            InputStreamReader ir = new InputStreamReader(pp.getInputStream());
            LineNumberReader input = new LineNumberReader(ir);
            for(; null != str; ){
                str = input.readLine();
                if(str != null){
                    macSerial = str.trim();// 去空格
                    break;
                }
            }
            return macSerial;
        } catch(IOException ex){
//            ex.printStackTrace();
            return null;
        }
    }

    public static String getBTMAC(){
        BluetoothAdapter m_BluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        String m_szBTMAC = null;
        if(m_BluetoothAdapter != null){
            m_szBTMAC = m_BluetoothAdapter.getAddress();
        }
        return m_szBTMAC;
    }

    public static String getPseudoID(){
        String serial = null;
        String m_szDevIDShort = "35" +
                                Build.BOARD.length() % 10 + Build.BRAND.length() % 10 +
                                Build.CPU_ABI.length() % 10 + Build.DEVICE.length() % 10 +
                                Build.DISPLAY.length() % 10 + Build.HOST.length() % 10 +
                                Build.ID.length() % 10 + Build.MANUFACTURER.length() % 10 +
                                Build.MODEL.length() % 10 + Build.PRODUCT.length() % 10 +
                                Build.TAGS.length() % 10 + Build.TYPE.length() % 10 +
                                Build.USER.length() % 10; //13 位

        Log.i("tag_uc", "getPseudoID: " + m_szDevIDShort);
        try{
            serial = Build.class.getField("SERIAL").get(null).toString();
            //API>=9 使用serial号
            return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
        } catch(Exception exception){
            //serial需要一个初始化
            serial = "serial"; // 随便一个初始化
        }
        //使用硬件信息拼凑出来的15位号码
        return new UUID(m_szDevIDShort.hashCode(), serial.hashCode()).toString();
    }

    public static String getBuildInfo(){
        return "BOARD: " + Build.BOARD
               + "\nBRAND: " + Build.BRAND
               + "\nCPU_ABI: " + Build.CPU_ABI
               + "\nDEVICE: " + Build.DEVICE
               + "\nDISPLAY: " + Build.DISPLAY
               + "\nHOST: " + Build.HOST
               + "\nID: " + Build.ID
               + "\nMANUFACTURER: " + Build.MANUFACTURER
               + "\nMODEL: " + Build.MODEL
               + "\nPRODUCT: " + Build.PRODUCT
               + "\nTAGS: " + Build.TAGS
               + "\nTYPE: " + Build.TYPE
               + "\nSERIAL: " + Build.SERIAL
               + "\nUSER: " + Build.USER;
    }

    public static String getDeviceId2(Context context){
        TelephonyManager tm = (TelephonyManager)context.getApplicationContext().getSystemService(Context.TELEPHONY_SERVICE);
        String tmDevice = tm.getDeviceId();
        String androidId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long)tmDevice.hashCode() << 32));
        return deviceUuid.toString();
    }
}
