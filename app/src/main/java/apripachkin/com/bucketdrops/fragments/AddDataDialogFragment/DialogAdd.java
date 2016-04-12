package apripachkin.com.bucketdrops.fragments.AddDataDialogFragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;

import apripachkin.com.bucketdrops.R;

/**
 * Created by root on 12.04.16.
 */
public class DialogAdd extends DialogFragment implements AddDialogView {
    private EditText editText;
    private Button button;
    private DatePicker datePicker;
    private ImageButton imgBtnClose;
    private AddDialogPresenter presenter;

    public DialogAdd() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View inflate = inflater.inflate(R.layout.dialog, container, false);
        return inflate;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        presenter = new AddDialogPresenterImpl(this);
        imgBtnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.closeDialog();
            }
        });
    }

    private void initViews(View inflate) {
        editText = (EditText) inflate.findViewById(R.id.et_drop);
        button = (Button) inflate.findViewById(R.id.btn_add_it);
        datePicker = (DatePicker) inflate.findViewById(R.id.bpv_date);
        imgBtnClose = (ImageButton) inflate.findViewById(R.id.btn_close);
    }

    @Override
    public void closeDialog() {
        dismiss();
    }
}
