package apripachkin.com.bucketdrops.mainscreen;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import apripachkin.com.bucketdrops.R;
import apripachkin.com.bucketdrops.adapters.CompleteListener;
import apripachkin.com.bucketdrops.adapters.DialogAddListener;
import apripachkin.com.bucketdrops.adapters.Divider;
import apripachkin.com.bucketdrops.adapters.DropsAdapter;
import apripachkin.com.bucketdrops.adapters.MarkListener;
import apripachkin.com.bucketdrops.adapters.SimpleTouchCallback;
import apripachkin.com.bucketdrops.beans.Drop;
import apripachkin.com.bucketdrops.fragments.AddDataDialogFragment.DialogAdd;
import apripachkin.com.bucketdrops.fragments.DialogMarkFragment.DialogMarkFragment;
import apripachkin.com.bucketdrops.widgets.BucketRecyclerView;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements MainScreenView, DialogAddListener, MarkListener {
    private Toolbar toolbar;
    private Button addButton;
    private View emptyView;
    private BucketRecyclerView recyclerView;
    private MainScreenPresenter presenter;
    private DropsAdapter adapter;
    private CompleteListener completeListener = new CompleteListener() {
        @Override
        public void onComplete(int position) {
            adapter.markComplete(position);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    private void initViews() {
        presenter = new MainScreenPresenterImpl(this);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        recyclerView = (BucketRecyclerView) findViewById(R.id.rv_drops);
        emptyView = findViewById(R.id.empty_drops);
        adapter = new DropsAdapter(this, presenter.getData(), this, presenter.getDB(), presenter.getData(), this);
        recyclerView.hideIfEmpty(toolbar);
        recyclerView.showIfEmpty(emptyView);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new Divider(this, LinearLayoutManager.VERTICAL));
        SimpleTouchCallback simpleTouchCallback = new SimpleTouchCallback(adapter);
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleTouchCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
        addButton = (Button) findViewById(R.id.btn_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddDialog(getSupportFragmentManager());
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        switch (itemId) {
            case R.id.action_add:
                showAddDialog(getSupportFragmentManager());
                return true;
            case R.id.action_show_complete:
                presenter.sortComplete();
                return true;
            case R.id.action_show_incomplete:
                presenter.sortIncomplete();
                return true;
            case R.id.action_sort_ascending_date:
                presenter.sortAscending();
                return true;
            case R.id.action_sort_descending_date:
                presenter.sortDescending();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.onStop();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.onStart();
    }

    @Override
    public void showData(RealmResults<Drop> data) {
        adapter.updateData(data);
    }

    @Override
    public void showAddDialog(FragmentManager fragmentManager) {
        DialogAdd dialogAdd = new DialogAdd();
        dialogAdd.show(fragmentManager, "AddFragment");
    }

    @Override
    public void showMarkDialog(FragmentManager fragmentManager, int position) {
        DialogMarkFragment instance = DialogMarkFragment.getInstance(position);
        instance.setCompleteListener(completeListener);
        instance.show(fragmentManager, "DialogMark");
    }

    @Override
    public void add() {
        showAddDialog(getSupportFragmentManager());
    }

    @Override
    public void onMark(int position) {
        showMarkDialog(getSupportFragmentManager(), position);
    }
}
