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
    private ArrayList<Book> mainStringSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewMain=findViewById(R.id.recycle_view_books);
        //设置布局
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        mainStringSet=new ArrayList<Book>();

        mainStringSet.add(new Book("软件项目管理案例教程（第4版）", R.drawable.book_2));
        mainStringSet.add(new Book("创新工程实践", R.drawable.book_no_name));
        mainStringSet.add(new Book("信息安全数学基础（第2版）", R.drawable.book_1));

        //String []mainDataSet= new String[]{"item 1","item 2","item 3","item 4","item 5"};
        //设置数据接收渲染器
        MainRecycleViewAdapter mainRecycleViewAdapter=new MainRecycleViewAdapter(mainStringSet);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);

    }

    public ArrayList<Book> getListBooks(){
        return mainStringSet;
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
        private ArrayList<Book>localDataset;
        //创建viewholder，针对每一个item生成一个viewholder,相当一个容器，里面的东西自定义
        public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textView;
            private final ImageView imageView;

            public ViewHolder(View view) {
                super(view);
                //找到传进来的大view中的小构件
                imageView=view.findViewById(R.id.image_view_book_cover);
                textView = view.findViewById(R.id.text_view_book_title);

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
        public MainRecycleViewAdapter(ArrayList<Book> dataset){
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
            holder.getTextView().setText(localDataset.get(position).getTitle());
            holder.getImageView().setImageResource(localDataset.get(position).getCoverResourceId());
        }

        @Override
        public int getItemCount() {
            return localDataset.size();
        }
    }

    public class Book{
        private String title;
        private int image_R_id;
        public Book(String t, int id){
            title=t;
            image_R_id=id;
        }
        String getTitle(){
            return title;
        }
        int getCoverResourceId(){
            return image_R_id;
        }
    }

}