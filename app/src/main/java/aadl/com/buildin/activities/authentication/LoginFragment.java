package aadl.com.buildin.activities.authentication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.Callback;
import com.amazonaws.mobile.client.results.SignInResult;

import aadl.com.buildin.R;
import aadl.com.buildin.exceptions.FormGuardException;
import aadl.com.buildin.utils.FormHelper;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;


public class LoginFragment extends Fragment implements View.OnClickListener{

    private OnFragmentInteractionListener mListener;
    private FormHelper mFormHelper;


    enum LoginActions {
        SIGN_IN_SUCCESS,
        SIGN_IN_FAILED,
        SHOW_SIGN_UP
    }

    private TextView mShowSignUp;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        this.mFormHelper = new FormHelper(view);

        this.mShowSignUp = view.findViewById(R.id.show_sign_up);
        Button mLoginButton = view.findViewById(R.id.login_button);
        mLoginButton.setOnClickListener(this);

        mShowSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    LoginFragment.this.mListener.onLoginFragmentInteraction(LoginActions.SHOW_SIGN_UP, "");
                }
            }
        });
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }


    @Override
    public void onClick(View view) {
        try {
            String phone = FormHelper.guard(this.mFormHelper.getInput(R.id.phone_text));
            String password = FormHelper.guard(this.mFormHelper.getInput(R.id.password_text));

            AWSMobileClient.getInstance().signIn(phone, password, null, new Callback<SignInResult>() {
                @Override
                public void onResult(final SignInResult signInResult) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("BuildIn", "Sign-in callback state: " + signInResult.getSignInState());
                            switch (signInResult.getSignInState()) {
                                case DONE:
                                    LoginFragment.this.mListener.onLoginFragmentInteraction(LoginActions.SIGN_IN_SUCCESS, "Sign-in done.");
                                    break;
                                case SMS_MFA:
                                    LoginFragment.this.mListener.onLoginFragmentInteraction(LoginActions.SIGN_IN_FAILED, "Please confirm sign-in with SMS.");
                                    break;
                                case NEW_PASSWORD_REQUIRED:
                                    LoginFragment.this.mListener.onLoginFragmentInteraction(LoginActions.SIGN_IN_FAILED, "Please confirm sign-in with new password.");
                                    break;
                                default:
                                    LoginFragment.this.mListener.onLoginFragmentInteraction(LoginActions.SIGN_IN_FAILED, "Unsupported sign-in confirmation: " + signInResult.getSignInState());
                                    break;
                            }
                        }
                    });
                }

                @Override
                public void onError(Exception e) {
                    Log.e("BuildIn", "Sign-in error", e);
                    LoginFragment.this.mListener.onLoginFragmentInteraction(LoginActions.SIGN_IN_FAILED, "Sign-in error: " +  e);

                }
            });
        } catch (FormGuardException fe) {
            LoginFragment.this.mListener.onLoginFragmentInteraction(LoginActions.SIGN_IN_FAILED, "Sign-in error: " +  fe);
        }
    }


    public interface OnFragmentInteractionListener {
        void onLoginFragmentInteraction(LoginFragment.LoginActions action, String data);
    }
}

