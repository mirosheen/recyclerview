package com.jnu.recyclerview;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class BookListMainActivity extends AppCompatActivity {
    //原来是主activity中有一个recyclerview，recyclerview中有很多item，所以有item_main.xml布局
    //现在改成主activity中有一个viewpager2，viewpager2中有fragment，并且viewpager2和导航栏联动，其中原来的recyclerview放在其中一个framgent中
    //viewpager2需要一个fragment适配器，和recyclerview需要一个adapter适配器类似，不过后者多了一个holder组装起来，这里fragment就是一个view
    public class PageViewFragmentAdapter extends FragmentStateAdapter {

        public PageViewFragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
            super(fragmentManager, lifecycle);
        }

        @NonNull
        @Override
        public Fragment createFragment(int position) {
            switch (position){
                case 0:
                    return ShopItemFragment.newInstance();
                case 1:
                    return BrowserFragment.newInstance();
            }
            return ShopItemFragment.newInstance();
        }

        @Override
        public int getItemCount() {
            return 2;
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewPager2 viewPager2=findViewById(R.id.viewpager2_main);//找到viewpager2，并且设置适配器
        viewPager2.setAdapter(new PageViewFragmentAdapter(getSupportFragmentManager(),getLifecycle()));

        //设置一个导航栏，并且设置一个连接器，可以设置导航栏的文字，最后把连接器连接上
        TabLayout tabLayout=findViewById(R.id.tablayout_main);
        TabLayoutMediator tabLayoutMediator=new TabLayoutMediator(tabLayout, viewPager2, new TabLayoutMediator.TabConfigurationStrategy() {
            @Override
            public void onConfigureTab(@NonNull TabLayout.Tab tab, int position) {
                switch (position){
                    case 0:
                        tab.setText(R.string.tab_caption_1_shopping);
                        break;
                    case 1:
                        tab.setText(R.string.tab_caption_2_browser);
                        break;
                }
            }
        });
        tabLayoutMediator.attach();
    }
}