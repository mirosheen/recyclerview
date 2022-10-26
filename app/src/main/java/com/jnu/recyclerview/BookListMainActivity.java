package com.jnu.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class BookListMainActivity extends AppCompatActivity {

    public static final int menu_id_add = 1;
    public static final int menu_id_delete = 2;
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
        for(int i=0;i<20;i++){
            mainStringSet.add("item "+i);
        }
        //String []mainDataSet= new String[]{"item 1","item 2","item 3","item 4","item 5"};
        //设置数据接收渲染器
        MainRecycleViewAdapter mainRecycleViewAdapter=new MainRecycleViewAdapter(mainStringSet);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //菜单menu的选项执行事件
        switch (item.getItemId())
        {
            case menu_id_add:
                Toast.makeText(this,"item add "+ item.getOrder()+" clicked",Toast.LENGTH_LONG).show();
                break;
            case menu_id_delete:
                Toast.makeText(this,"item delete "+ item.getOrder()+" clicked",Toast.LENGTH_LONG).show();
                break;

        }
        return super.onContextItemSelected(item);
    }

    //adapter重写三个方法，并且还得在内部类设置viewholder类
    public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder> {
        //private String[]localDataset;
        private ArrayList<String>localDataset;
        //创建viewholder，针对每一个item生成一个viewholder,相当一个容器，里面的东西自定义
        public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textView;
            private final ImageView imageView;

            public ViewHolder(View view) {
                super(view);
                //找到传进来的大view中的小构件
                imageView=view.findViewById(R.id.imageView_item_image);
                textView = view.findViewById(R.id.textView_item_caption);

                //设置这个holder的监听事件
                view.setOnCreateContextMenuListener(this);
            }
            public TextView getTextView() {
                return textView;
            }

            public ImageView getImageView() {
                return imageView;
            }

            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                //监听事件的菜单选项样式，那个选项，哪一个item，显示信息
                contextMenu.add(0,menu_id_add,getAdapterPosition(),"add"+getAdapterPosition());
                contextMenu.add(0, menu_id_delete,getAdapterPosition(),"delete"+getAdapterPosition());
            }
        }
        public MainRecycleViewAdapter(ArrayList<String> dataset){
            localDataset=dataset;
        }
        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
            //提取出view出来用在viewholder
            View view= LayoutInflater.from(viewGroup.getContext())
                    .inflate(R.layout.item_main,viewGroup,false);
            return new ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            //holder设置数据
            holder.getTextView().setText(localDataset.get(position));
            holder.getImageView().setImageResource(position% menu_id_delete ==menu_id_add?R.drawable.clock:R.drawable.pencils);
        }

        @Override
        public int getItemCount() {
            return localDataset.size();
        }
    }



}