package aadl.com.buildin.utils;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import aadl.com.buildin.exceptions.FormGuardException;

public class FormHelper {

    private Object parentInstance;
    private Parent parent;

    enum Mode {
        EditText,
        TextView
    }

    enum Parent {
        Activity,
        Fragment
    }

    public FormHelper(View view) {
        this.parent = Parent.Fragment;
        this.parentInstance = view;
    }

    public FormHelper(Activity activity) {
        this.parent = Parent.Activity;
        this.parentInstance = activity;
    }

    public String getInput (int id, Mode mode) {

        try {
            View view;

            switch (this.parent) {
                case Activity:
                    view = ((Activity) this.parentInstance).findViewById(id);
                    break;
                case Fragment:
                    view = ((View) this.parentInstance).findViewById(id);
                    break;
                default:
                    throw new IllegalStateException("Unexpected value: " + this.parent);
            }

            switch (mode) {
                case EditText:
                    return ((EditText) view).getText().toString();
                case TextView:
                    return ((TextView) view).getText().toString();
            }
        } catch (Exception ne) {
            return "";
        }
        return "";
    }

    public String getInput (int id) {
        return this.getInput(id, Mode.EditText);
    }

    public static String guard(String string) throws FormGuardException {
        if (string.length() < 1) {
            Log.e("ERROR", string);
            throw new FormGuardException("Can't be empty");
        }
        return string;
    }
}
