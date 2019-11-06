package com.example.exam17;


import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class fragment2 extends Fragment {
    RecyclerView recyclerView;
    ArrayList<Item> list;
    ItemAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment2, container, false);
        // RecycleView 찾기
        recyclerView = view.findViewById(R.id.recycler);
        // DB에서 데이터 조회해서 list메 add 하기
        DBHelper helper = new DBHelper(getContext());
        SQLiteDatabase db = helper.getReadableDatabase();

        String sql = "SELECT * FROM tb_gallery";
        Cursor cursor = db.rawQuery(sql, null);

        list = new ArrayList<>();
        while (cursor.moveToNext()) {
            Item item = new Item();
            item.image = cursor.getString(1);
            item.title = cursor.getString(2);
            item.count = cursor.getString(3);
            list.add(item);
        }

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    class ItemAdapter extends RecyclerView.Adapter<ItemViewHolder> {

        @NonNull
        @Override
        public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
            return new ItemViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ItemViewHolder holder, int position, @NonNull List<Object> payloads) {
            Item item = list.get(position);
            holder.titleView.setText(item.title);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }
    }

    class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView titleView, countView;
        ImageView imageView

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            // item 화면에 있는 view 객체 찾기
            titleView = itemView.findViewById(R.id.title);
            countView = itemView.findViewById(R.id.count);
            imageView = itemView.findViewById(R.id.image);
        }
    }

    class MyItemDecoration extends RecyclerView.ItemDecoration {
        @Override
        public void getItemOffsets(@NonNull Rect outRect, @NonNull View view, @NonNull RecyclerView parent, @NonNull RecyclerView.State state) {
            super.getItemOffsets(outRect, view, parent, state);
            int position = parent.getChildAdapterPosition(view);
            if (position % 2 == 0)
                view.setBackgroundColor(Color.GRAY);
            outRect.set(20, 0, 100, 100);
        }
    }
}