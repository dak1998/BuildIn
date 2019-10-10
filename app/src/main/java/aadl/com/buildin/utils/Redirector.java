package aadl.com.buildin.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import aadl.com.buildin.R;

public class Redirector {
    private Context parent;

    private Class destination;

    private boolean animation = false;
    private boolean noHistory = false;

    public Redirector(Context context) {
        this.parent = context;
    }

    public static Redirector from(Context context) {
        return new Redirector(context);
    }

    public Redirector to(Class cls) {
        this.destination = cls;
        return this;
    }

    public Redirector withAnimation() {
        this.animation = true;
        return this;
    }

    public Redirector noHistory() {
        this.noHistory = true;
        return this;
    }


    public void go() {
        Intent i = new Intent(this.parent, this.destination);


        this.parent.startActivity(i);

        if (animation) {
            animate();
        }

        if (noHistory) {
            finish();
        }
    }

    private void finish() {
        ((Activity) this.parent).finish();
    }

    private void animate() {
        ((Activity) this.parent).overridePendingTransition(R.anim.slide_from_right, R.anim.slide_to_left);
    }
//
//    public void goToLogin() {
//        this.to(SigninActivity.class).withAnimation().go();
//    }
//
//    public void goToUserDetails() {
//        this.to(UserDetails.class).withAnimation().noHistory().go();
//    }
//
//    public void goToHome() {
//        this.to(Home.class).withAnimation().noHistory().go();
//    }
}