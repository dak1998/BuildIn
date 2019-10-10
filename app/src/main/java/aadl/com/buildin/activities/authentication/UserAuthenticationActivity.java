package aadl.com.buildin.activities.authentication;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;

import aadl.com.buildin.R;
import aadl.com.buildin.utils.NamedActionBar;

public class UserAuthenticationActivity extends FragmentActivity implements LoginFragment.OnFragmentInteractionListener{

    private static final int NUM_PAGES = 2;

    private ViewPager mPager;

    private NamedActionBar mActionBar;

    private void initializeView () {
        this.mActionBar = NamedActionBar.in(this).setTitle("User Authentication").setOnBackClickListener(new NamedActionBar.AnimatedBackAction());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_user_authentication);

        this.initializeView();

        mPager = findViewById(R.id.authentication_view_pager);
        PagerAdapter pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager());
        mPager.setAdapter(pagerAdapter);
    }

    @Override
    public void onLoginFragmentInteraction(LoginFragment.LoginActions action) {
        switch (action) {
            case SHOW_SIGN_UP:
                mPager.setCurrentItem(1, true);
                break;
            case SIGN_IN_SUCCESS:
                mActionBar.back();
                break;
        }

    }


    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        private String[] fragmentList = {"aadl.com.buildin.activities.authentication.LoginFragment", "aadl.com.buildin.activities.authentication.SignupFragment"};

        public ScreenSlidePagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            try {
                return (Fragment) Class.forName(fragmentList[position]).newInstance();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        public int getCount() {
            return NUM_PAGES;
        }
    }

}
