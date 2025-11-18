package com.example.recycler_view_degtiannikov;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public Context Context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Context = this;

        ArrayList<Category> Categories = CategoryContext.All();
        ArrayList<Item> Items = ItemContext.All();

        RecyclerView CategoryList = findViewById(R.id.category_list);
        RecyclerView CardList = findViewById(R.id.card_list);

        CategoryAdapter CategoryAdapter = new CategoryAdapter(this, Categories, Click);
        CategoryList.setAdapter(CategoryAdapter);

        ItemAdapter CardAdapter = new ItemAdapter(this, Items);
        CardList.setAdapter(CardAdapter);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        MenuNavigation fragment = new MenuNavigation();
        ft.add(R.id.menu_navigation, fragment);
        ft.commit();
    }

    public void OpenPopularView(View view) { // открытие популярных товаров
        Intent newIntent = new Intent(this, PopularActivity.class); // создаём intent
        newIntent.putExtra("Category", -1); // запоминаем категорию
        startActivity(newIntent); // открываем активность
    }

    iOnClickInterface Click = new iOnClickInterface() { // Создаём обработчик события при выборе категории
        @Override
        public void setClick(View view, int position) { // при нажатии на категорию
            Intent newIntent = new Intent(Context, PopularActivity.class); // создаём intent
            newIntent.putExtra("Category", position); // запоминаем категорию
            startActivity(newIntent); // открываем активность
        }
    };
}