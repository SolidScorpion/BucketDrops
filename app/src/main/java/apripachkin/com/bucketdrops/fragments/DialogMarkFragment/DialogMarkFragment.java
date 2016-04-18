package apripachkin.com.bucketdrops.fragments.DialogMarkFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import apripachkin.com.bucketdrops.R;
import apripachkin.com.bucketdrops.adapters.CompleteListener;

/**
 * Created by root on 14.04.16.
 */
public class DialogMarkFragment extends DialogFragment implements DialogMarkFragmentView {
    public static final String POSITION = "position";
    private ImageButton imgButton;
    private Button button;
    private DialogMarkFragmentPresenter presenter;
    private int position;
    private CompleteListener completeListener;

    public static DialogMarkFragment getInstance(int position) {
        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        DialogMarkFragment dialogMarkFragment = new DialogMarkFragment();
        dialogMarkFragment.setArguments(args);
        return dialogMarkFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.dialog_mark, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        imgButton = (ImageButton) view.findViewById(R.id.btn_close);
        imgButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });
        button = (Button) view.findViewById(R.id.btn_completed);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (completeListener != null) {
                    completeListener.onComplete(position);
                    dismiss();
                }
            }
        });
        Bundle arguments = getArguments();
        if (arguments != null) {
            position = arguments.getInt(POSITION);
        }
    }

    @Override
    public void close() {
        dismiss();
    }

    public void setCompleteListener(CompleteListener completeListener) {
        this.completeListener = completeListener;
    }
}
