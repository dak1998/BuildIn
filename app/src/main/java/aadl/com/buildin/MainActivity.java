package aadl.com.buildin;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.SignInUIOptions;
import com.amazonaws.mobile.client.UserStateDetails;

import java.util.ArrayList;
import java.util.List;

import aadl.com.buildin.activities.authentication.UserAuthenticationActivity;
import aadl.com.buildin.adapters.HomeItemAdapter;
import aadl.com.buildin.models.HomeItem;
import aadl.com.buildin.utils.Redirector;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "BuildIn";


    private ImageButton mUserImageButton;

    private RecyclerView mHomeItemsView;
    private HomeItemAdapter mHomeItemAdapter;
    private List<HomeItem> mHomeItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mUserImageButton = findViewById(R.id.user_image_button);

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {

                    @Override
                    public void onResult(UserStateDetails userStateDetails) {
                        Log.i("INIT", "onResult: " + userStateDetails.getUserState());
                    }

                    @Override
                    public void onError(Exception e) {
                        Log.e("INIT", "Initialization error.", e);
                    }
                }
        );

        mHomeItemsView = findViewById(R.id.recycler_view);

        mHomeItems = new ArrayList<>();
        mHomeItemAdapter = new HomeItemAdapter(this, mHomeItems);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        mHomeItemsView.setLayoutManager(mLayoutManager);

        mHomeItemsView.setItemAnimator(new DefaultItemAnimator());
        mHomeItemsView.setAdapter(mHomeItemAdapter);

        fakeHomeItems();

        mHomeItemAdapter.notifyDataSetChanged();
    }

    private void fakeHomeItems () {
        mHomeItems.add(new HomeItem("Events", "http://192.168.0.100:8999/calendar.png", MainActivity.class));
        mHomeItems.add(new HomeItem("Notices", "http://192.168.0.100:8999/protest.png", MainActivity.class));
        mHomeItems.add(new HomeItem("Issues", "http://192.168.0.100:8999/pros-and-cons.png", MainActivity.class));
        mHomeItems.add(new HomeItem("Contact Guards", "http://192.168.0.100:8999/policeman.png", MainActivity.class));
        mHomeItems.add(new HomeItem("Pay Maintenance Bill", "http://192.168.0.100:8999/payment.png", MainActivity.class));

    }

    public void onUserImageClick(View view) {
        Redirector.from(this).to(UserAuthenticationActivity.class).withAnimation().go();
    }
}
