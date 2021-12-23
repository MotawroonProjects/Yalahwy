package com.yalahwy.ui.activity_menu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.yalahwy.R;
import com.yalahwy.adapters.MenuProductAdapter;
import com.yalahwy.databinding.ActivityMenuBinding;
import com.yalahwy.language.Language;
import com.yalahwy.models.MenuDataModel;
import com.yalahwy.models.MenuModel;
import com.yalahwy.mvp.activity_menu_mvp.ActivityMenuPresenter;
import com.yalahwy.mvp.activity_menu_mvp.ActivityMenuView;

import java.util.ArrayList;
import java.util.List;

import io.paperdb.Paper;

public class MenuActivity extends AppCompatActivity implements ActivityMenuView {

    private ActivityMenuBinding binding;
    private String lang;
    private List<MenuModel> productModelList;
    private MenuProductAdapter adapter;
    private ActivityMenuPresenter presenter;
    private int selectedPos = -1;



    @Override
    protected void attachBaseContext(Context newBase)
    {
        Paper.init(newBase);
        super.attachBaseContext(Language.updateResources(newBase, Paper.book().read("lang", "ar")));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_menu);
        initView();

    }
    private void initView()
    {
        Paper.init(this);
        lang = Paper.book().read("lang","ar");
        productModelList = new ArrayList<>();
        binding.setLang(lang);
        binding.recView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MenuProductAdapter(productModelList,this);
        binding.recView.setAdapter(adapter);
        presenter = new ActivityMenuPresenter(this,this);
        presenter.getMenus();
        binding.llBack.setOnClickListener(view -> onBackPressed());


    }



    @Override
    public void onSuccess(MenuDataModel menuDataModel)
    {
        binding.setTotal(menuDataModel.getTotal());

        if (menuDataModel.getData().size()>0){
            productModelList.addAll(menuDataModel.getData());
            binding.tvNoData.setVisibility(View.GONE);
            adapter.notifyDataSetChanged();
            binding.llTotal.setVisibility(View.VISIBLE);
        }else {
            binding.tvNoData.setVisibility(View.VISIBLE);
            binding.llTotal.setVisibility(View.GONE);

        }
    }
    @Override
    public void onRemoveFavoriteSuccess()
    {
        if (productModelList.size()>0&&selectedPos!=-1){
            productModelList.remove(selectedPos);
            adapter.notifyItemRemoved(selectedPos);
            if (productModelList.size()>0){
                binding.tvNoData.setVisibility(View.GONE);
            }else {
                binding.tvNoData.setVisibility(View.VISIBLE);

            }
            selectedPos=-1;
        }
    }
    @Override
    public void onFailed(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }


    @Override
    public void onProgressShow()
    {
        productModelList.clear();
        adapter.notifyDataSetChanged();
        binding.tvNoData.setVisibility(View.GONE);
        binding.progBar.setVisibility(View.VISIBLE);
    }
    @Override
    public void onProgressHide()
    {
        binding.progBar.setVisibility(View.GONE);

    }
    @Override
    public void onBackPressed()
    {
        finish();
    }

    public void removeFavorite(MenuModel model, int adapterPosition) {
        selectedPos = adapterPosition;
        presenter.remove_favorite(model);

    }
}