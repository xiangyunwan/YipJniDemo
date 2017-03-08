package com.yip.yipjnidemo;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.util.Log;

import java.io.ByteArrayInputStream;
import java.security.MessageDigest;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import static java.security.spec.MGF1ParameterSpec.SHA1;

/**
 * Created by zhangzhenzhong.zhangzhenzhong@le.com
 *
 * @version: 1.0
 * @date: 2017/3/6
 * @Copyright (c) 2017. LetvFinancial. All rights reserved. Inc.
 */
public class Signature {
    static {
        System.loadLibrary("jnitext");   //defaultConfig.ndk.moduleName
    }
    private  Context mContext;
    public Signature(Context context){
        mContext=context;
    }
    public  String  getSignatureInfo() {
//        Map<String, String> map = new HashMap<String, String>();
        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), PackageManager.GET_SIGNATURES);
            android.content.pm.Signature[] signs = packageInfo.signatures;
            android.content.pm.Signature sign = signs[0];
            byte[] signature = sign.toByteArray();

            X509Certificate cert = parseSignature(signature);

//            map.put("signName", cert.getSigAlgName());
//
//            map.put("pubKey", cert.getPublicKey().toString());
//
//            map.put("serialNumber", cert.getSerialNumber().toString());
//
//            map.put("sigAlgOID", cert.getSigAlgOID());

//            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//            map.put("startTime", sdf.format(cert.getNotBefore()));
//            map.put("endTime", sdf.format(cert.getNotAfter()));
//
//            map.put("subjectDN", cert.getSubjectDN().toString());
//
//            map.put("MD5", getMessageDigest("MD5", signature));
//
//            map.put("SHA1", getMessageDigest("SHA1", signature));
//
//            map.put("SHA256", getMessageDigest("SHA256", signature));

//            map.put("HASH", getMessageDigest("HASH", signature));

//            Set<Map.Entry<String, String>> entrySet = map.entrySet();
//            for (Map.Entry<String, String> entry : entrySet) {
//                Log.i("",String.format("%s=%s", entry.getKey(), entry.getValue()));
//            }
            return  getMessageDigest("SHA1", signature);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void compareSignature(CompareCallback compareCallback){
        if (getCLangString()==0){
            compareCallback.onSuccess();
        }else{
            compareCallback.onFail();
        }
    }
    private String getMessageDigest(String instance, byte[] signature) {
        String sinfo = null;
        try {
            MessageDigest md = MessageDigest.getInstance(instance);

            md.update(signature);

            byte[] digest = md.digest();

            sinfo = toHexString(digest);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return sinfo;
    }

    public X509Certificate parseSignature(byte[] signature) {
        X509Certificate cert = null;
        try {
            CertificateFactory certFactory = CertificateFactory.getInstance("X.509");
            cert = (X509Certificate) certFactory.generateCertificate(new ByteArrayInputStream(signature));
        } catch (CertificateException e) {
            e.printStackTrace();
        }
        return cert;
    }

    private void byte2hex(byte b, StringBuffer buf) {

        char[] hexChars = {'0', '1', '2', '3', '4', '5', '6', '7', '8',

                '9', 'A', 'B', 'C', 'D', 'E', 'F'};

        int high = ((b & 0xf0) >> 4);

        int low = (b & 0x0f);

        buf.append(hexChars[high]);

        buf.append(hexChars[low]);
    }


    /**
     * Converts a byte array to hex string
     */
    private String toHexString(byte[] block) {

        StringBuffer buf = new StringBuffer();

        int len = block.length;

        for (int i = 0; i < len; i++) {

            byte2hex(block[i], buf);

            if (i < len - 1) {

                buf.append(":");

            }

        }
        return buf.toString();
    }


    private native int getCLangString();

}
