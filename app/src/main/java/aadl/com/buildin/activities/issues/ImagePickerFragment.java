package aadl.com.buildin.activities.issues;


import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.esafirm.imagepicker.features.ImagePicker;
import com.esafirm.imagepicker.model.Image;

import java.io.ByteArrayOutputStream;
import java.util.List;
import java.util.Objects;

import aadl.com.buildin.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class ImagePickerFragment extends Fragment {

    enum PickerAction {
        IMAGE_PICKER_CLICK,
        IMAGE_PICKED,
        IMAGE_CANCELLED
    }

    private ImageView mImageView;

    private OnFragmentInteractionListener mListener;

    public ImagePickerFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_image_picker, container, false);

        ImageButton button = view.findViewById(R.id.add_image_button);


        mImageView = view.findViewById(R.id.selected_image);



        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.create(ImagePickerFragment.this).single().start(12);
//                ImagePickerFragment.this.mListener.onPickerFragmentInteraction(PickerAction.IMAGE_PICKER_CLICK, "");
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        List<Image> images = ImagePicker.getImages(data);
        if (images != null && !images.isEmpty()) {
            Bitmap bitmap = BitmapFactory.decodeFile(images.get(0).getPath());
            mImageView.setImageBitmap(bitmap);

            ByteArrayOutputStream stream=new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.PNG, 65, stream);
            byte[] image=stream.toByteArray();

            String img_str = Base64.encodeToString(image, 0);

            this.mListener.onPickerFragmentInteraction(PickerAction.IMAGE_PICKED, img_str);

        } else {
            mImageView.setImageBitmap(null);
            this.mListener.onPickerFragmentInteraction(PickerAction.IMAGE_CANCELLED, "");
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public interface OnFragmentInteractionListener {
        void onPickerFragmentInteraction(PickerAction action, String data);
    }

}
