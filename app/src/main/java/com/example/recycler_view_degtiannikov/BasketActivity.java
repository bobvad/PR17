package com.example.recycler_view_degtiannikov;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class BasketActivity extends AppCompatActivity {

    public iOnClickInterface Delete = new iOnClickInterface() {
        @Override
        public void setClick(View view, int position) {
            MainActivity.init.BasketList.remove(position);
            BasketRV.setAdapter(BasketAdapter);
        }
    };

    public iOnClickInterface EventCost = new iOnClickInterface() {
        @Override
        public void setClick(View view, int position) {
            CostCalculation();
        }
    };

    public RecyclerView BasketRV;
    public TextView tvSum, tvAllSum;
    BasketAdapter BasketAdapter;
    Context Context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_basket);

        Context = this;
        BasketRV = findViewById(R.id.basket_list);

        tvSum = findViewById(R.id.tv_sum);
        tvAllSum = findViewById(R.id.tv_all_sum);

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(SwipeAdapter);
        itemTouchHelper.attachToRecyclerView(BasketRV);

        BasketAdapter = new BasketAdapter(this, MainActivity.init.BasketList, Delete, EventCost);
        BasketRV.setAdapter(BasketAdapter);

        CostCalculation();
    }

    ItemTouchHelper.SimpleCallback SwipeAdapter = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

        @Override
        public boolean onMove(
                RecyclerView recyclerView,
                RecyclerView.ViewHolder viewHolder,
                RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int swipeDir) {
            BasketRV.setAdapter(BasketAdapter);
        }

        @Override
        public void onChildDraw(
                Canvas c,
                RecyclerView recyclerView,
                RecyclerView.ViewHolder viewHolder,
                float dX,
                float dY,
                int actionState,
                boolean isCurrentlyActive) {

            Resources r = getResources();
            float px = TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 58, r.getDisplayMetrics());

            LinearLayout bthDelete = viewHolder.itemView.findViewById(R.id.ll_delete);
            LinearLayout bthCount = viewHolder.itemView.findViewById(R.id.ll_count);

            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
                if (dX < -px) {
                    bthDelete.setVisibility(View.VISIBLE);
                    bthCount.setVisibility(View.GONE);
                } else if (dX > px) {
                    bthDelete.setVisibility(View.GONE);
                    bthCount.setVisibility(View.VISIBLE);
                }
            }

            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
        }
    };

    public void CostCalculation() {
        float ItemPrice = 0;
        for (Basket Item : MainActivity.init.BasketList) {
            ItemPrice += Item.Item.Price * Item.Count;
        }

        tvSum.setText("₽" + ItemPrice);
        ItemPrice += 60.20;
        tvAllSum.setText("₽" + ItemPrice);
    }

    public void ClosePopularActivity(View view) {
        finish();
    }
}