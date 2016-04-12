package apripachkin.com.bucketdrops.fragments.AddDataDialogFragment;

/**
 * Created by root on 12.04.16.
 */
public class AddDialogPresenterImpl implements AddDialogPresenter {
    private AddDialogView view;

    public AddDialogPresenterImpl(AddDialogView view) {
        this.view = view;
    }

    @Override
    public void closeDialog() {
        view.closeDialog();
    }
}
