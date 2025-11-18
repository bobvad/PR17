package com.example.recycler_view_degtiannikov;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PopularActivity extends AppCompatActivity {

    RecyclerView CategoryList, CardList;
    TextView TvNamePage;
    CategoryAdapter CategoryAdapter;
    ArrayList<Category> Categories = new ArrayList<>();
    ArrayList<Item> Items = new ArrayList<>();
    public Context Context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popular);

        Context = this;
        Categories = CategoryContext.All();

        Bundle arguments = getIntent().getExtras();
        Integer IdCategory = Integer.valueOf(arguments.get("Category").toString());

        CategoryList = findViewById(R.id.category_list);
        CardList = findViewById(R.id.card_list);
        TvNamePage = findViewById(R.id.tv_name_page);

        if (IdCategory != -1) {
            Category SelectCategory = Categories.stream()
                    .filter(item -> item.Id == IdCategory)
                    .findFirst()
                    .orElse(null);
            SelectCategory.Active = true;
            TvNamePage.setText(SelectCategory.Name);

            CategoryAdapter = new CategoryAdapter(this, Categories, CategoryClick);
            CategoryList.setAdapter(CategoryAdapter);
        } else {
            CategoryList.setVisibility(View.GONE);

            TextView TvNameCategory = findViewById(R.id.tv_name_category);
            TvNameCategory.setVisibility(View.GONE);
        }

        Items = IdCategory == -1 ? ItemContext.All() : ItemContext.GetByCategory(IdCategory);
        CardList.setLayoutManager(new GridLayoutManager(this, 2));
        ItemAdapter CardAdapter = new ItemAdapter(this, Items);
        CardList.setAdapter(CardAdapter);
    }
    CategoryAdapter.OnClickInterface CategoryClick = new CategoryAdapter.OnClickInterface() {
        @Override
        public void setClick(View view, int position) {
            for (Category Item : Categories)
                Item.Active = false;

            Category SelectCategory = Categories.get(position);
            SelectCategory.Active = true;
            CategoryList.setAdapter(CategoryAdapter);

            TvNamePage.setText(SelectCategory.Name);

            Items = ItemContext.GetByCategory(SelectCategory.Id);
            ItemAdapter CardAdapter = new ItemAdapter(Context, Items);
            CardList.setAdapter(CardAdapter);
        }
    };

    public void ClosePopularActivity(View view) {
        finish();
    }
}