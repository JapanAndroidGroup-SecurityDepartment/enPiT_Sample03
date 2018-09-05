package jp.android_group.asj.enpit_sample03;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import static android.Manifest.permission.CALL_PHONE;

public class CallPhoneActivity extends Activity {

    private Uri uri;

    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call_phone);

        //Intent 受け取り
        boolean confirm = getIntent().getBooleanExtra("confirm",false);

        //Logに流れるようにgetDataにしてみた。
        uri = getIntent().getData();
        if(uri == null) finish();

        String phoneNumber = uri.getSchemeSpecificPart();

        TextView phoneNumberTextView = findViewById(R.id.phoneNumberTextView);
        phoneNumberTextView.setText(phoneNumber);

        //ACTION_CALL 電話をかける前にとめない
        //ACTION_DIAL 電話をかける前に止める
        if(!confirm){
            doSomething();
        }

        Button button = findViewById(R.id.submitButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doSomething();
            }
        });
    }

    /**
     * 外部から直接Activityが呼ばれた用
     */
    void doSomething(){
        if (checkSelfPermission( CALL_PHONE)== PackageManager.PERMISSION_GRANTED){
            Intent sendIntent = new Intent(Intent.ACTION_CALL, uri);
            startActivity(sendIntent);
        }
        finish();
    }
}
