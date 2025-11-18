package com.example.recycler_view_degtiannikov;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public Context Context;
    ArrayList<Basket> BasketList = new ArrayList<>();
    ArrayList<Item> Items;

    public iOnClickInterface AddBasker = new iOnClickInterface() {
        @Override
        public void setClick(View view, int position) {

            Basket Item = BasketList.stream()
                    .filter(item -> item.Item.Id == position)
                    .findAny()
                    .orElse(null);


            Item FindItem = Items.stream().filter(item -> item.Id == position)
                    .findAny()
                    .orElse(null);

            if (Item == null) {
                Item = new Basket(FindItem, 1);
                BasketList.add(Item);
            } else {
                Item.Count++;
            }

            Toast.makeText(Context, "Товар добавлен в корзину", Toast.LENGTH_SHORT).show();
        }
    };
    public void OpenBasketView(View view)
    {
        Intent newIntent = new Intent(this,BasketActivity.class);
        startActivity(newIntent);
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context = this;
        MainActivity init = this;
        ArrayList<Category> Categories = CategoryContext.All();
        Items = ItemContext.All();

        RecyclerView CategoryList = findViewById(R.id.category_list);
        RecyclerView CardList = findViewById(R.id.card_list);

        CategoryAdapter CategoryAdapter = new CategoryAdapter(this, Categories, Click);
        CategoryList.setAdapter(CategoryAdapter);

        ItemAdapter CardAdapter = new ItemAdapter(this, Items,AddBasker);
        CardList.setAdapter(CardAdapter);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        MenuNavigation fragment = new MenuNavigation();
        ft.add(R.id.menu_navigation, fragment);
        ft.commit();
    }

    public void OpenPopularView(View view) {
        Intent newIntent = new Intent(this, PopularActivity.class);
        newIntent.putExtra("Category", -1);
        startActivity(newIntent);
    }

    CategoryAdapter.OnClickInterface Click = new CategoryAdapter.OnClickInterface() {
        @Override
        public void setClick(View view, int position) {
            Intent newIntent = new Intent(Context, PopularActivity.class);
            newIntent.putExtra("Category", position);
            startActivity(newIntent);
        }
    };
}