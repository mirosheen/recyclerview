package com.jnu.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class BookListMainActivity extends AppCompatActivity {

    private ArrayList<String> mainStringSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewMain=findViewById(R.id.recycle_view_books);
        //设置布局
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        mainStringSet=new ArrayList<String>();
        for(int i=0;i<10;i++){
            mainStringSet.add("item "+i);
        }
        //String []mainDataSet= new String[]{"item 1","item 2","item 3","item 4","item 5"};
        //设置数据接收渲染器
        MainRecycleViewAdapter mainRecycleViewAdapter=new MainRecycleViewAdapter(mainStringSet);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);

    }
    //adapter重写三个方法，并且还得在内部类设置viewholder类
    public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder> {
        //private String[]localDataset;
        private ArrayList<String>localDataset;
        //创建viewholder，针对每一个item生成一个viewholder
        public  class ViewHolder extends RecyclerView.ViewHolder{
            private final TextView textView;
            public ViewHolder(View view) {
                super(view);
                textView = (TextView) view.findViewById(R.id.textView_item_caption);
            }
            public TextView getTextView() {
                return textView;
            }
        }
        public MainRecycleViewAdapter(ArrayList<String> dataset){
            localDataset=dataset;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            View view= LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_main,viewGroup,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            holder.getTextView().setText(localDataset.get(position));
        }

        @Override
        public int getItemCount() {
            return localDataset.size();
        }
    }



}