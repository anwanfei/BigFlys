package com.anfly.bigfly.guidewelcom;

import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.anfly.bigfly.R;
import com.anfly.bigfly.base.BaseActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class WelcomActivity extends BaseActivity {

    @BindView(R.id.iv_welcom)
    ImageView ivWelcom;
    @BindView(R.id.tv_skip)
    TextView tvSkip;
    @BindView(R.id.tv_count_down)
    TextView tvCountDown;
    private CountDownTimer countDownTimer;

    @Override
    protected void initData(Bundle savedInstanceState) {
        animation();
        countDown();
    }

    private void countDown() {
        countDownTimer = new CountDownTimer(6000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                tvCountDown.setText("倒计时:" + String.valueOf(millisUntilFinished / 1000));
            }

            @Override
            public void onFinish() {
                gone();
            }
        }.start();
    }

    private void animation() {
        AnimationSet setAnimation = new AnimationSet(true);
        // 步骤1:创建组合动画对象(设置为true)
        // 步骤2:设置组合动画的属性
        // 特别说明以下情况
        // 因为在下面的旋转动画设置了无限循环(RepeatCount = INFINITE)
        // 所以动画不会结束，而是无限循环
        // 所以组合动画的下面两行设置是无效的
        setAnimation.setRepeatMode(Animation.RESTART);
        setAnimation.setRepeatCount(1);// 设置了循环一次,但无效

        // 步骤3:逐个创建子动画(方式同单个动画创建方式,此处不作过多描述)

        // 子动画1:旋转动画
        Animation rotate = new RotateAnimation(0, 360, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        rotate.setDuration(5000);
        //rotate.setRepeatMode(Animation.RESTART);
        //rotate.setRepeatCount(Animation.INFINITE);

        // 子动画2:平移动画
        Animation translate = new TranslateAnimation(TranslateAnimation.RELATIVE_TO_PARENT, -0.5f,
                TranslateAnimation.RELATIVE_TO_PARENT, 0,
                TranslateAnimation.RELATIVE_TO_SELF, 0
                , TranslateAnimation.RELATIVE_TO_SELF, 0);
        translate.setDuration(5000);

        // 子动画3:透明度动画
        Animation alpha = new AlphaAnimation(0, 1);
        alpha.setDuration(3000);


        // 子动画4:缩放动画
        Animation scale1 = new ScaleAnimation(0.5f, 1, 0.5f, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        scale1.setDuration(5000);


        // 步骤4:将创建的子动画添加到组合动画里
        setAnimation.addAnimation(alpha);
        setAnimation.addAnimation(rotate);
        setAnimation.addAnimation(translate);
        setAnimation.addAnimation(scale1);

        ivWelcom.startAnimation(setAnimation);
        setAnimation.setFillAfter(true);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_welcom;
    }

    @OnClick(R.id.tv_skip)
    public void onClick() {
        gone();
    }

    private void gone() {
        countDownTimer.cancel();
        goToActivity(GuideActivity.class, null);
        closeCurActivity();
    }

}
