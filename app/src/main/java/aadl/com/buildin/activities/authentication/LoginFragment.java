package aadl.com.buildin.activities.authentication;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import aadl.com.buildin.R;


public class LoginFragment extends Fragment {

    private OnFragmentInteractionListener mListener;

    enum LoginActions {
        SIGN_IN_SUCCESS,
        SIGN_IN_FAILED,
        SHOW_SIGN_UP
    }

    private TextView mShowSignUp;
    private TextView mPhone;
    private TextView mPassword;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_login, container, false);

        this.mShowSignUp = view.findViewById(R.id.show_sign_up);
        this.mPhone = view.findViewById(R.id.phone_text);
        this.mPassword = view.findViewById(R.id.password_text);
        Button mLoginButton = view.findViewById(R.id.login_button);

        mShowSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mListener != null) {
                    LoginFragment.this.mListener.onLoginFragmentInteraction(LoginActions.SHOW_SIGN_UP);
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




    public interface OnFragmentInteractionListener {
        void onLoginFragmentInteraction(LoginFragment.LoginActions action);
    }
}

