package jp.android_group.asj.enpit_sample03;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;


public class MainActivity extends Activity {

    private static final int MY_PERMISSIONS_REQUEST_CALL_PHONE = 1;
    private static final String MY_ACTION_CALLPHONE = "jp.android_group.asj.enpit03.callphone";
    private String phoneNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button button = (Button)findViewById(R.id.submitButton);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                TextView phoneNumberTextView = (TextView)findViewById(R.id.phoneNumberEditText);
                phoneNumber = phoneNumberTextView.getText().toString();
                if (phoneNumber.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "電話番号を入力してください", Toast.LENGTH_LONG).show();
                    return;
                }
                startPermissionCheck();

            }
        });
    }
        /**
         * パミッション取得済の状態でこのメソッドを呼ぶ
         */
        private void doSomething() {
            boolean confirm = ((CheckBox)findViewById(R.id.confirmCheckBox)).isChecked();

            Intent sendIntent = new Intent(MY_ACTION_CALLPHONE);
            sendIntent.setData(Uri.parse("tel:"+phoneNumber));
            sendIntent.putExtra("confirm",confirm);

            startActivity(sendIntent);
        }

        void startPermissionCheck(){
            if (checkSelfPermission( Manifest.permission.CALL_PHONE)
                    != PackageManager.PERMISSION_GRANTED) {
                // Permission is not granted
                // Should we show an explanation?
                if (shouldShowRequestPermissionRationale(
                        Manifest.permission.CALL_PHONE)) {
                    // Show an explanation to the user *asynchronously* -- don't block
                    // this thread waiting for the user's response! After the user
                    // sees the explanation, try again to request the permission.
                    Toast.makeText(this,"パミッションがOFFになっているため実行できません",Toast.LENGTH_LONG).show();
                } else {
                    // No explanation needed; request the permission
                    requestPermissions(
                            new String[]{Manifest.permission.CALL_PHONE},
                            MY_PERMISSIONS_REQUEST_CALL_PHONE);

                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                }
            } else {
                // Permission has already been granted
                doSomething();
            }
        }


        @Override
        public void onRequestPermissionsResult(int requestCode,
        String permissions[], int[] grantResults) {
            switch (requestCode) {
                case MY_PERMISSIONS_REQUEST_CALL_PHONE: {
                    // If request is cancelled, the result arrays are empty.
                    if (grantResults.length > 0
                            && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                        // permission was granted, yay! Do the
                        // contacts-related task you need to do.
                        doSomething();
                    } else {
                        // permission denied, boo! Disable the
                        // functionality that depends on this permission.
                    }

                }
                // other 'case' lines to check for other
                // permissions this app might request.
            }
        }

}
