package com.jnu.recyclerview;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContract;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.jnu.recyclerview.data.shopItem;

import java.util.ArrayList;

public class BookListMainActivity extends AppCompatActivity {

    public static final int menu_id_add = 1;
    public static final int menu_id_delete = 2;
    public static final int menu_id_update = 3;
    private ArrayList<com.jnu.recyclerview.data.shopItem> shopItems;
    private MainRecycleViewAdapter mainRecycleViewAdapter;
    //设置一个数据传输器，用于输入页面和主页面之间数据的传回,根据类型intent设置的模版，返回结果为result作为参数，然后执行匿名函数
    private ActivityResultLauncher<Intent> addDataLauncher=registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(null!=result){
                    //获取数据页面的数据intent
                    Intent intent=result.getData();
                    if(result.getResultCode()==ShopItemActivity.RESULT_CODE_SUCCESS){
                        Bundle bundle=intent.getExtras();
                        String title=bundle.getString("title");
                        double price=bundle.getDouble("price");
                        //在对应的位置添加一个，然后在通知更新器更新
                        shopItems.add(1,new shopItem(title,price,R.drawable.ic_launcher_background));
                        mainRecycleViewAdapter.notifyItemInserted(1);
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerViewMain=findViewById(R.id.recycle_view_books);
        //设置布局
        LinearLayoutManager linearLayoutManager=new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerViewMain.setLayoutManager(linearLayoutManager);

        shopItems=new ArrayList<shopItem>();
        for(int i=0;i<20;i++){
            shopItems.add(new shopItem("item"+i,Math.random()*10,i% 2 ==0?R.drawable.clock:R.drawable.pencils));
        }
        //String []mainDataSet= new String[]{"item 1","item 2","item 3","item 4","item 5"};
        //设置数据接收渲染器
        mainRecycleViewAdapter = new MainRecycleViewAdapter(shopItems);
        recyclerViewMain.setAdapter(mainRecycleViewAdapter);

    }

    @Override
    public boolean onContextItemSelected(@NonNull MenuItem item) {
        //菜单menu的选项执行事件
        switch (item.getItemId())
        {
            case menu_id_add:
                //新建一个页面，用于显示数据输入
                Intent intent=new Intent(this,ShopItemActivity.class);
                //把这个页面的结果设置到数据传输器
                addDataLauncher.launch(intent);
//                //在对应的位置添加一个，然后在通知更新器更新
//                shopItems.add(item.getOrder(),new shopItem("added"+item.getOrder(),Math.random()*10,R.drawable.ic_launcher_background));
//                mainRecycleViewAdapter.notifyItemInserted(item.getOrder());
                break;
            case menu_id_delete:
                AlertDialog alertDialog=new AlertDialog.Builder(this)
                        .setTitle(R.string.confirmation)
                        .setMessage(R.string.sure_to_delete_item)
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {

                            }
                        })
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                shopItems.remove(item.getOrder());
                                mainRecycleViewAdapter.notifyItemRemoved(item.getOrder());
                            }
                        })
                        .create();
                alertDialog.show();
                break;
            case menu_id_update:
                shopItems.get(item.getOrder()).setTitle(getString(R.string.update_title));
                mainRecycleViewAdapter.notifyItemChanged(item.getOrder());
                break;

        }
        return super.onContextItemSelected(item);
    }

    //adapter重写三个方法，并且还得在内部类设置viewholder类
    public class MainRecycleViewAdapter extends RecyclerView.Adapter<MainRecycleViewAdapter.ViewHolder> {
        //private String[]localDataset;
        private ArrayList<shopItem> localDataset;
        //创建viewholder，针对每一个item生成一个viewholder,相当一个容器，里面的东西自定义
        public  class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
            private final TextView textViewTitle;
            private final TextView textViewPrice;
            private final ImageView imageView;

            public ViewHolder(View view) {
                super(view);
                //找到传进来的大view中的小构件
                imageView=view.findViewById(R.id.imageView_item_image);
                textViewTitle = view.findViewById(R.id.textView_item_caption);
                textViewPrice = view.findViewById(R.id.textView_item_price);

                //设置这个holder的监听事件
                view.setOnCreateContextMenuListener(this);
            }
            public TextView getTextViewPrice() {
                return textViewPrice;
            }
            public TextView getTextViewTitle() {
                return textViewTitle;
            }

            public ImageView getImageView() {
                return imageView;
            }

            @Override
            public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                //监听事件的菜单选项样式，那个选项，哪一个item，显示信息
                contextMenu.add(0,menu_id_add,getAdapterPosition(),"add"+getAdapterPosition());
                contextMenu.add(0, menu_id_delete,getAdapterPosition(),"delete"+getAdapterPosition());
                contextMenu.add(0, menu_id_update,getAdapterPosition(),"update"+getAdapterPosition());
            }
        }
        public MainRecycleViewAdapter(ArrayList<shopItem> dataset){
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
            holder.getTextViewTitle().setText(localDataset.get(position).getTitle());
            holder.getTextViewPrice().setText(localDataset.get(position).getPrice().toString());
            holder.getImageView().setImageResource(localDataset.get(position).getResourceId());

        }

        @Override
        public int getItemCount() {
            return localDataset.size();
        }
    }



}