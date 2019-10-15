package aadl.com.buildin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserState;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.UserStateListener;

import java.util.Map;

import aadl.com.buildin.R;
import aadl.com.buildin.utils.NamedActionBar;

public class SettingsActivity extends AppCompatActivity {

    private NamedActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        final TextView name = findViewById(R.id.name);
        final TextView phone = findViewById(R.id.phone);



        AWSMobileClient.getInstance().getUserAttributes(new Callback<Map<String, String>>() {
            @Override
            public void onResult(Map<String, String> result) {
                final String phoneData = result.get("phone_number");
                final String nameData = result.get("name");
                System.out.println(result);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        name.setText(nameData);
                        phone.setText(phoneData);
                    }
                });
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });
        initializeView();
    }

    private void initializeView () {
        this.mActionBar = NamedActionBar.in(this).setTitle("My Page").setOnBackClickListener(new NamedActionBar.AnimatedBackAction());
    }

    public void onSignoutClick(View view) {
        AWSMobileClient.getInstance().signOut();
        Toast.makeText(this,"Successfully Logged Out", Toast.LENGTH_LONG).show();
        this.mActionBar.back();

    }
}
