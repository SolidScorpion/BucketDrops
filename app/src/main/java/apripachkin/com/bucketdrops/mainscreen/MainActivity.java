package apripachkin.com.bucketdrops.mainscreen;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import apripachkin.com.bucketdrops.R;
import apripachkin.com.bucketdrops.adapters.DropsAdapter;

public class MainActivity extends AppCompatActivity implements MainScreenView {
    private Toolbar toolbar;
    private Button addButton;
    private RecyclerView recyclerView;
    private MainScreenPresenter presenter;

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
        recyclerView = (RecyclerView) findViewById(R.id.rv_drops);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(new DropsAdapter(this));
        addButton = (Button) findViewById(R.id.btn_add);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.buttonClick(getSupportFragmentManager());
            }
        });
    }

}
