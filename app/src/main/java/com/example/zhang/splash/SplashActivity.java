package com.example.zhang.splash;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.ImageView;

import com.example.zhang.splash.utils.SpUtils;

import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;

/**
 * Created by YangXueYi
 * Time： 2017/12/11.
 */

@SuppressLint("Registered")
public class SplashActivity extends AppCompatActivity {

    private ImageView mIVEntry;
    private static final float SCALE_END = 1.15F;
    private static final int ANIM_TIME = 2000;

    //图片的集合，随机产生一张图片，进行动画
    private static final int[] Imgs={
            R.drawable.welcomimg1,R.drawable.welcomimg2,
            R.drawable.welcomimg3,R.drawable.welcomimg4,
            R.drawable.welcomimg5, R.drawable.welcomimg6,
            R.drawable.welcomimg7,R.drawable.welcomimg8,
            R.drawable.welcomimg9,R.drawable.welcomimg10,
            R.drawable.welcomimg11,R.drawable.welcomimg12};

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //判断是不是第一次进入程序
        Boolean isFirstOpen = SpUtils.getBoolean(this, SpUtils.FIRST_OPEN,true);
        //如果是第一次启动，先进入引导界面
        if(isFirstOpen){
            Intent intent = new Intent(this,WelcomeGuideActivity.class);
            startActivity(intent);
            finish();
            Log.e( "onCreate: ","isFirstOpen = "+ isFirstOpen );
            return;
        }

        Log.e( "onCreate: ","isFirstOpen = 114111" + isFirstOpen );
        //如果不是第一次启动，则正常显示
        setContentView(R.layout.activity_splash);
        initView();
        startMainActivity();

    }
    private void initView() {
        mIVEntry = (ImageView) findViewById(R.id.iv_entry);
    }

    private void startMainActivity() {
        Random random = new Random(SystemClock.elapsedRealtime());//SystemClock.elapsedRealtime():从开始到现在的毫秒数（包括睡眠时间）
        mIVEntry.setImageResource(Imgs[random.nextInt(Imgs.length)]);
        Observable.timer(1000, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        //动画展示图片（缩放动画）
                        startAnim();
                    }
                });
    }

    private void startAnim() {

        ObjectAnimator animatorX = ObjectAnimator.ofFloat(mIVEntry, "scaleX", 1f, SCALE_END);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(mIVEntry, "scaleY", 1f, SCALE_END);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIM_TIME).play(animatorX).with(animatorY);
        set.start();
        set.addListener(new AnimatorListenerAdapter(){
            @Override
            public void onAnimationEnd(Animator animation){
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                SplashActivity.this.finish();
            }
        });

    }

    /**
     * 屏蔽物理返回按钮
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode==KeyEvent.KEYCODE_BACK){
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
