package aadl.com.buildin;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.UserState;
import com.amazonaws.mobile.client.UserStateDetails;
import com.amazonaws.mobile.client.UserStateListener;

import java.util.ArrayList;
import java.util.List;

import aadl.com.buildin.activities.authentication.UserAuthenticationActivity;
import aadl.com.buildin.adapters.HomeItemAdapter;
import aadl.com.buildin.models.HomeItem;
import aadl.com.buildin.utils.Redirector;

public class MainActivity extends AppCompatActivity implements UserStateListener {

    private static final String TAG = "BuildIn";


    private ImageButton mUserImageButton;

    private RecyclerView mHomeItemsView;
    private HomeItemAdapter mHomeItemAdapter;
    private List<HomeItem> mHomeItems;
    private TextView mUserWelcome;

    final private Class defaultHomeItemDestination = UserAuthenticationActivity.class;

    private Class imageIconClass = defaultHomeItemDestination;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mUserImageButton = findViewById(R.id.user_image_button);
        mUserWelcome = findViewById(R.id.user_welcome);

        AWSMobileClient.getInstance().initialize(getApplicationContext(), new Callback<UserStateDetails>() {
            @Override
            public void onResult(UserStateDetails result) {
                MainActivity.this.onUserStateChanged(result);
            }

            @Override
            public void onError(Exception e) {
                e.printStackTrace();
            }
        });

        AWSMobileClient.getInstance().addUserStateListener(this);

        mHomeItemsView = findViewById(R.id.recycler_view);

        mHomeItems = new ArrayList<>();
        mHomeItemAdapter = new HomeItemAdapter(this, mHomeItems);

        RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
        mHomeItemsView.setLayoutManager(mLayoutManager);

        mHomeItemsView.setItemAnimator(new DefaultItemAnimator());
        mHomeItemsView.setAdapter(mHomeItemAdapter);



    }

    public void onUserImageClick(View view) {
        Redirector.from(this).to(imageIconClass).withAnimation().go();
    }


    @Override
    public void onUserStateChanged(UserStateDetails details) {

        UserState currentState = details.getUserState();
        this.setUserImageDestination(currentState);
        this.setHomeItems(currentState);
        this.setNavbarTitle(currentState);

    }

    private void setHomeItems(final UserState state) {

        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                MainActivity.this.mHomeItems.clear();
                MainActivity.this.mHomeItems.addAll(
                        state == UserState.SIGNED_IN
                                ? HomeItem.Helper.signInMenuItems()
                                : HomeItem.Helper.signInMenuItems()
                );

                mHomeItemAdapter.notifyDataSetChanged();
            }
        });
    }

    private void setNavbarTitle(final UserState state) {

        try {
            String name = AWSMobileClient.getInstance().getUserAttributes().get("name");
            final String text = "Welcome, " + (state == UserState.SIGNED_IN ? name.toUpperCase() : "User.");

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    try {
                        MainActivity.this.mUserWelcome.setText(text);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void setUserImageDestination (UserState state) {
        if (state == UserState.SIGNED_IN) {
            this.imageIconClass = MainActivity.class;

        } else {
            this.imageIconClass = UserAuthenticationActivity.class;
        }
    }
}


