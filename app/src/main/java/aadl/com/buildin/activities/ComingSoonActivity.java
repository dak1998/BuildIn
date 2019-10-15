package aadl.com.buildin.activities;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import aadl.com.buildin.R;
import aadl.com.buildin.utils.NamedActionBar;

public class ComingSoonActivity extends AppCompatActivity {

    private NamedActionBar mActionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coming_soon);
        initializeView();
    }

    private void initializeView () {
        this.mActionBar = NamedActionBar.in(this).setTitle("Uh Oh").setOnBackClickListener(new NamedActionBar.AnimatedBackAction());
    }
}
