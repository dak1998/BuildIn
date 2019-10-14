package aadl.com.buildin.activities.issues;

import android.content.res.Resources;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import aadl.com.buildin.R;
import aadl.com.buildin.exceptions.FormGuardException;
import aadl.com.buildin.exceptions.ImagePickerFragmentException;
import aadl.com.buildin.utils.FormHelper;
import aadl.com.buildin.utils.NamedActionBar;

public class CreateNewIssueActivity extends AppCompatActivity implements ImagePickerFragment.OnFragmentInteractionListener {

    private NamedActionBar mActionBar;
    private ViewPager mPager;

    private ScreenSlidePagerAdapter mPagerAdapter;

    private Map<Integer,String> imageDataList = new HashMap<>();


    private FormHelper _helper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_issue);

        this.initializeView();

        mPager = findViewById(R.id.image_upload_pager);
        _helper = new FormHelper(this);

        mPagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(mPagerAdapter);
        mPager.setClipToPadding(false);
        mPager.setPadding(dp2px(getResources(),25), 0, dp2px(getResources(),25), 0);
        mPager.setPageMargin(dp2px(getResources(),-30));
    }

    private void initializeView () {
        this.mActionBar = NamedActionBar.in(this).setTitle("Create a new Issue").setOnBackClickListener(new NamedActionBar.AnimatedBackAction());
    }

    @Override
    public void onPickerFragmentInteraction(ImagePickerFragment.PickerAction action, String data) {
        switch (action) {

            case IMAGE_PICKED:
                try {
                    this.mPagerAdapter.appendNewFragment();

                    this.mPagerAdapter.notifyDataSetChanged();
                    this.imageDataList.put(this.mPager.getCurrentItem(), data);

                    this.mPager.setCurrentItem(this.mPagerAdapter.getCount()-1,true);

                } catch (ImagePickerFragmentException e) {
                    Toast.makeText(this,e.getMessage(),Toast.LENGTH_LONG).show();
                }
                break;
            case IMAGE_CANCELLED:

                this.imageDataList.put(this.mPager.getCurrentItem(), "");

                break;
        }
    }

    public void onCreateIssueSubmit(View view) {
        try {
            String title = FormHelper.guard(_helper.getInput(R.id.title));
            String description =FormHelper.guard((_helper.getInput(R.id.description)));


        } catch (FormGuardException fe) {
            Snackbar.make(view, fe.getMessage(), Snackbar.LENGTH_LONG).show();
        }
    }

    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

        private final static int MAX_IMAGES = 3;

        private List<Fragment> fragmentHolderList = new ArrayList<>();

        ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
            this.appendNewFragment(new ImagePickerFragment());
        }

        void appendNewFragment() throws ImagePickerFragmentException {
            if (this.getCount() >= MAX_IMAGES) {
                throw new ImagePickerFragmentException("Max items selected [Count "+ this.getCount() +"]");
            }
            this.appendNewFragment(new ImagePickerFragment());

        }

        void appendNewFragment(Fragment fragment) {
            this.fragmentHolderList.add(fragment);
        }


        @Override
        public Fragment getItem(int position) {
            return this.fragmentHolderList.get(position);
        }

        @Override
        public int getCount() {
            return this.fragmentHolderList.size();
        }


    }

    public int dp2px(Resources resource, int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, resource.getDisplayMetrics());
    }
}
