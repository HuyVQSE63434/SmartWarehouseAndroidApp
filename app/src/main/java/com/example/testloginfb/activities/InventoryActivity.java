package com.example.testloginfb.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.example.testloginfb.R;
import com.example.testloginfb.adapters.RecycleViewInventoryAdapter;
import com.example.testloginfb.models.StoreMaterial;
import com.example.testloginfb.presenters.InventoryPresenter;
import com.example.testloginfb.room.entities.StaffEntity;
import com.example.testloginfb.views.InventoryView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class InventoryActivity extends BaseActivity implements SearchView.OnQueryTextListener, RecycleViewInventoryAdapter.OnDetailListener, InventoryView {
    private RecyclerView recyclerView;
    private Toolbar toolbarInventory;
    private ImageView imageViewSearch;
    private RecyclerView.LayoutManager layoutManager;

    private static List<StoreMaterial> listStoreMaterials = new ArrayList<>();
    private RecycleViewInventoryAdapter recycleAdapter;
    private InventoryPresenter mInventoryPresenter;
    private StaffEntity msStaffEntity;


    @Override
    protected void onResume() {
        super.onResume();

        initialData();
    }

    private void initialData() {
        listStoreMaterials = new ArrayList<>();

        mInventoryPresenter = new InventoryPresenter(this, this, this.getApplication());
        mInventoryPresenter.HandleLoadLocalStaff();
        showProgressHUD();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setTheme(R.style.AppTheme2);
        setContentView(R.layout.activity_inventory_main);



        recyclerView = findViewById(R.id.recycleViewSearch);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recycleAdapter = new RecycleViewInventoryAdapter(listStoreMaterials, this);
        recyclerView.setAdapter(recycleAdapter);
        DividerItemDecoration dividerHorizontal =
                new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerHorizontal);


        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(id == android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.search_menu, menu);
        MenuItem.OnActionExpandListener onActionExpand = new MenuItem.OnActionExpandListener() {

            @Override
            public boolean onMenuItemActionExpand(MenuItem item) {
                return true;
            }

            @Override
            public boolean onMenuItemActionCollapse(MenuItem item) {
                return true;
            }
        };
        listStoreMaterials = new ArrayList<>();
        MenuItem searchItem = menu.findItem(R.id.action_search);

        searchItem.setOnActionExpandListener(onActionExpand);

        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        Toast.makeText(InventoryActivity.this, query, Toast.LENGTH_SHORT).show();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {

        if (newText != null) {

            String userInput = newText.toUpperCase();
            List<StoreMaterial> newList = new ArrayList<>();
            for (StoreMaterial list : listStoreMaterials) {
                if (list.getMaterialName().getDetailName().toUpperCase().contains(userInput)||list.getStore().getDetailId().toUpperCase().contains(userInput)) {
                    newList.add(list);
                }



            }
            recycleAdapter.updateList(newList);
        }


        return true;
    }

    @Override
    public void onItemClick(int position) {
        listStoreMaterials.get(position);

        Intent intent = new Intent(getApplicationContext(), ShowInventoryDetailActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("id_material",listStoreMaterials.get(position).getMaterialName().getDetailId());
        bundle.putString("object_value", listStoreMaterials.get(position).getStore().getDetailId());
        bundle.putString("detail_name", listStoreMaterials.get(position).getMaterialName().getDetailName());
        bundle.putLong("detail_amount", listStoreMaterials.get(position).getInventoryAmmount());
        bundle.putString("unit",listStoreMaterials.get(position).getUnit());
        intent.putExtras(bundle);

        startActivity(intent);
    }

    @Override
    public void loadInventory(List<StoreMaterial> storeMaterials) {

        this.listStoreMaterials.addAll(storeMaterials);
        recycleAdapter.updateList(listStoreMaterials);
    }

    @Override
    public void done() {
        dismissProgressHUDNow();
    }

    @Override
    public void showMessage(String message) {
    }

    @Override
    public void loadLocalStaff(StaffEntity staffEntity) {

        this.msStaffEntity = staffEntity;
        mInventoryPresenter.handleLoadInventory(msStaffEntity.getStoreId(),msStaffEntity.getAuthToken());

    }
}
