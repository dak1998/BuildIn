package aadl.com.buildin.activities.authentication;


import android.content.Context;
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
import com.amazonaws.mobile.client.results.SignUpResult;
import com.amazonaws.mobile.client.results.UserCodeDeliveryDetails;

import java.util.HashMap;
import java.util.Map;

import aadl.com.buildin.R;
import aadl.com.buildin.exceptions.FormGuardException;
import aadl.com.buildin.utils.FormHelper;

import static com.amazonaws.mobile.auth.core.internal.util.ThreadUtils.runOnUiThread;

public class SignupFragment extends Fragment implements View.OnClickListener {

    private OnFragmentInteractionListener mListener;
    private FormHelper mFormHelper;



    enum SignupActions {
        SIGN_UP_SUCCESS,
        SIGN_UP_FAILED,
        SHOW_SIGN_IN
    }


    public SignupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_signup, container, false);
        this.mFormHelper = new FormHelper(view);

        Button getConfirmationCode = view.findViewById(R.id.get_confirmation_code);

        getConfirmationCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    final String name = FormHelper.guard(SignupFragment.this.mFormHelper.getInput(R.id.given_name));
                    final String phone = FormHelper.guard(SignupFragment.this.mFormHelper.getInput(R.id.phone_text));
                    final String email = FormHelper.guard(SignupFragment.this.mFormHelper.getInput(R.id.email));
                    final String address = FormHelper.guard(SignupFragment.this.mFormHelper.getInput(R.id.address));
                    final String password = FormHelper.guard(SignupFragment.this.mFormHelper.getInput(R.id.password_text));

                    final Map<String, String> attributes = new HashMap<>();

                    attributes.put("address", address);
                    attributes.put("name", name);
                    attributes.put("phone_number", phone);
                    attributes.put("email", email);


                    AWSMobileClient.getInstance().signUp(phone, password, attributes, null, new Callback<SignUpResult>() {
                        @Override
                        public void onResult(SignUpResult result) {
                            Log.d("Buildin", "Sign-up callback state: " + result.getConfirmationState());
                            if (!result.getConfirmationState()) {
                                SignupFragment.this.mListener.onSignupFragmentInteraction(SignupActions.SIGN_UP_FAILED, "Confirmation Code has been sent on:" + result.getUserCodeDeliveryDetails().getDestination());
                            } else {
                                SignupFragment.this.mListener.onSignupFragmentInteraction(SignupActions.SIGN_UP_SUCCESS, null);
                            }
                        }

                        @Override
                        public void onError(Exception e) {

                            SignupFragment.this.mListener.onSignupFragmentInteraction(SignupActions.SIGN_UP_FAILED, e.getMessage());
                        }
                    });

                } catch (FormGuardException fe) {

                    SignupFragment.this.mListener.onSignupFragmentInteraction(SignupActions.SIGN_UP_FAILED, fe.getMessage());
                }
            }
        });

        Button signUpButton = view.findViewById(R.id.signup_button);

        signUpButton.setOnClickListener(this);

        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof SignupFragment.OnFragmentInteractionListener) {
            mListener = (SignupFragment.OnFragmentInteractionListener) context;
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
            final String phone = FormHelper.guard(this.mFormHelper.getInput(R.id.phone_text));
            final String code = FormHelper.guard(this.mFormHelper.getInput(R.id.passcode));
            AWSMobileClient.getInstance().confirmSignUp(phone, code, new Callback<SignUpResult>() {
                @Override
                public void onResult(final SignUpResult signUpResult) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Log.d("BuildIn", "Sign-up callback state: " + signUpResult.getConfirmationState());
                            if (!signUpResult.getConfirmationState()) {
                                final UserCodeDeliveryDetails details = signUpResult.getUserCodeDeliveryDetails();

                                SignupFragment.this.mListener.onSignupFragmentInteraction(SignupActions.SIGN_UP_FAILED, "Confirmation Code has been sent on:" + details.getDestination());
                            } else {
                                SignupFragment.this.mListener.onSignupFragmentInteraction(SignupActions.SIGN_UP_SUCCESS, "");
                            }
                        }
                    });
                }

                @Override
                public void onError(Exception e) {
                    Log.e("BuildIn", "Confirm sign-up error", e);
                    SignupFragment.this.mListener.onSignupFragmentInteraction(SignupActions.SIGN_UP_FAILED, e.getMessage());
                }
            });
        } catch (FormGuardException fe) {

            this.mListener.onSignupFragmentInteraction(SignupActions.SIGN_UP_FAILED, fe.getMessage());
        }
    }




    public interface OnFragmentInteractionListener {
        void onSignupFragmentInteraction(SignupActions action, String data);
    }


}
