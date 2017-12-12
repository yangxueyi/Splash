package com.example.zhang.splash;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.zhang.splash.adapter.GuideViewPagerAdapter;
import com.example.zhang.splash.utils.SpUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by YangXueYi
 * Time： 2017/12/11.
 */

@SuppressLint("Registered")
public class WelcomeGuideActivity extends AppCompatActivity implements View.OnClickListener{

    private Button startBtn;
    private List<View> views;
    private ViewPager vp;


    // 引导页图片资源
    private static final int[] pics = { R.layout.guide_view1,R.layout.guide_view2, R.layout.guide_view3};

    // 底部小点图片
    private ImageView[] dots;

    // 记录当前选中位置
    private int currentIndex;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_welcome);

        views = new ArrayList<View>();

        // 初始化引导页视图列表
        for (int i = 0; i < pics.length; i++) {
            View view = LayoutInflater.from(this).inflate(pics[i], null);

            if (i == pics.length - 1) {//最后一个界面有按钮
                startBtn = (Button) view.findViewById(R.id.btn_enter);
//                startBtn.setTag("enter");//给按钮添加一个标签
                startBtn.setOnClickListener(this);
            }
            views.add(view);
        }


        vp = (ViewPager) findViewById(R.id.vp);
        vp.setAdapter(new GuideViewPagerAdapter(views));
        //ViewPager的滚动监听
        vp.addOnPageChangeListener(new PageChangeListener());
        //添加底部圆点
        initDots();
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 如果切换到后台，就设置下次不进入功能引导页
//        SpUtils.putBoolean(WelcomeGuideActivity.this, SpUtils.FIRST_OPEN, false);
        finish();
    }
    @Override
    protected void onStop() {
        super.onStop();

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }


    private void initDots() {
        LinearLayout ll = (LinearLayout) findViewById(R.id.ll);
        dots = new ImageView[pics.length];
        // 循环取得小点图片
        for(int i = 0;i < pics.length; i++){
            // 得到一个LinearLayout下面的每一个子元素
            dots[i] = (ImageView) ll.getChildAt(i);
            dots[i].setEnabled(false);//都设置为黑色
            dots[i].setOnClickListener(this);
            dots[i].setTag(i);// 设置位置tag，方便取出与当前位置对应
        }
        currentIndex = 0;
        dots[currentIndex].setEnabled(true); // 设置为白色，即选中状态
    }



    @Override
    public void onClick(View v) {
        /*if (v.getTag().equals("enter")) {
            enterMainActivity();
            return;
        }*/
        if(v.getId() == R.id.btn_enter){
            enterMainActivity();
            return;
        }

        int position = (Integer) v.getTag();
        setCurView(position);
        setCurDot(position);
    }

    /**
     * 设置当前view
     * @param position
     */
    private void setCurView(int position) {
        if (position < 0 || position >= pics.length) {
            return;
        }
        vp.setCurrentItem(position);
    }

    /**
     * 设置当前指示点
     * @param position
     */
    private void setCurDot(int position) {
        if (position < 0 || position > pics.length || currentIndex == position) {
            return;
        }
        dots[position].setEnabled(true);
        dots[currentIndex].setEnabled(false);
        currentIndex = position;
    }

    private void enterMainActivity() {
        Intent intent = new Intent(WelcomeGuideActivity.this,MainActivity.class);
        startActivity(intent);
        SpUtils.putBoolean(WelcomeGuideActivity.this, SpUtils.FIRST_OPEN, false);
        finish();
    }

    class PageChangeListener implements OnPageChangeListener{

        //页面滚动
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        //选择页面
        @Override
        public void onPageSelected(int position) {
            // 设置底部小点选中状态
            setCurDot(position);
        }

        //页面滚动状态改变
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
