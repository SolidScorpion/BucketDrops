package apripachkin.com.bucketdrops.mainscreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;
import android.widget.Button;

import apripachkin.com.bucketdrops.R;
import apripachkin.com.bucketdrops.adapters.DialogAddListener;
import apripachkin.com.bucketdrops.adapters.Divider;
import apripachkin.com.bucketdrops.adapters.DropsAdapter;
import apripachkin.com.bucketdrops.adapters.SimpleTouchCallback;
import apripachkin.com.bucketdrops.beans.Drop;
import apripachkin.com.bucketdrops.widgets.BucketRecyclerView;
import io.realm.RealmResults;

public class MainActivity extends AppCompatActivity implements MainScreenView, DialogAddListener {
    private Toolbar toolbar;
    private Button addButton;
    private View emptyView;
    private BucketRecyclerView recyclerView;
    private MainScreenPresenter presenter;
    private DropsAdapter adapter;

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
        adapter = new DropsAdapter(this, presenter.getData(), this, presenter.getDB(), presenter.getData());
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
                presenter.buttonClick(getSupportFragmentManager());
            }
        });
    }

    @Override
    protected void onStop() {
        presenter.onStop();
        super.onStop();
    }

    @Override
    protected void onStart() {
        presenter.onStart();
        super.onStart();
    }

    @Override
    public void showData(RealmResults<Drop> data) {
        adapter.updateData(data);
    }

    @Override
    public void add() {
        presenter.buttonClick(getSupportFragmentManager());
    }
}
