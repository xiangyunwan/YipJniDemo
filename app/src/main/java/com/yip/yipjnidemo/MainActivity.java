package com.yip.yipjnidemo;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends Activity {
    private TextView txtView;
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mContext=this;
        setContentView(R.layout.activity_main);
        txtView= (TextView) findViewById(R.id.txt_jnitext);

        Signature signature=new Signature(mContext);
//        Signature signature1=new Signature();
//        Log.i("signature",String.format("%s=%s",signature.getSignatureInfo(), signature.getSignatureInfo()));
        signature.compareSignature(new CompareCallback() {
            @Override
            public void  onSuccess() {
                txtView.setText("正确");
            }
            @Override
            public void onFail() {
                txtView.setText("错误");
            }
        });
    }
}
