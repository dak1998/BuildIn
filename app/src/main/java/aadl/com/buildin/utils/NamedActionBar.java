package aadl.com.buildin.utils;

import android.app.Activity;
import android.content.Context;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import aadl.com.buildin.R;
import aadl.com.buildin.listeners.OnBackItemClick;

public class NamedActionBar {

    private Context mContext;
    private OnBackItemClick mOnBackClickListener;

    private TextView titleView;
    private ImageButton mBackButton;

    public static class AnimatedBackAction implements OnBackItemClick {

        @Override
        public void onBackClick(Context context) {
            ((Activity) context).finish();


            ((Activity) context).overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_right);
        }
    }

    private NamedActionBar (Context context) {
        this.mContext = context;
        this.titleView = ((Activity) mContext).findViewById(R.id.activity_title);
        this.mBackButton = ((Activity) mContext).findViewById(R.id.back_button);

        this.hookOnClickListener();
    }

    private void hookOnClickListener() {
        if (this.mBackButton != null) {

            this.mBackButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (NamedActionBar.this.mOnBackClickListener != null) {
                        NamedActionBar.this.mOnBackClickListener.onBackClick(NamedActionBar.this.mContext);
                    }
                }

            });
        }
    }

    public NamedActionBar setTitle(String title) {
        if (this.titleView != null) {
            this.titleView.setText(title);
        }

        return this;
    }

    public NamedActionBar setOnBackClickListener(OnBackItemClick onBackClickListener) {

        this.mOnBackClickListener = onBackClickListener;
        return this;
    }

    public void back() {
        this.mBackButton.callOnClick();
    }



    public static NamedActionBar in(Context context) {
        return new NamedActionBar(context);
    }
}
