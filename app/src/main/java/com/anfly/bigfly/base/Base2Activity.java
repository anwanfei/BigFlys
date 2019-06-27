package com.anfly.bigfly.base;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Toast;

import butterknife.ButterKnife;

/**
 * @author Anfly
 * @date 2019/4/23.
 * description：
 */
public abstract class Base2Activity<M extends BaseModel, V extends BaseView, P extends BasePresenter> extends AppCompatActivity {
    private Toast toast;
    private ProgressDialog progressDialog;
    protected P presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());

        presenter = initMvpPresenter();
        if (presenter != null) {
            presenter.setBaseModel(initMvpModel());
            presenter.setBaseView(initMvpView());
        }

        ButterKnife.bind(this);
        initView();
        initData(savedInstanceState);
        initListener();
    }

    protected abstract M initMvpModel();

    protected abstract V initMvpView();

    protected abstract P initMvpPresenter();

    private void initListener() {
    }

    protected void initView() {
    }

    protected abstract void initData(Bundle savedInstanceState);

    public abstract int getLayoutId();

    public void showTs(String msg) {
        if (toast != null) {
            toast.cancel();
            toast = null;
        }
        if (TextUtils.isEmpty(msg)) return;
        toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        toast.show();
    }

    //启动一个新的activity
    public void goToActivity(Class activity, Bundle bundle) {
        Intent intent = new Intent(this, activity);
        if (bundle != null) {
            intent.putExtra("data", bundle);
        }
        startActivity(intent);
    }

    public void closeCurActivity() {
        if (!isFinishing()) {
            finish();
        }
    }

    //设置背景透明度
    public void backgroundAlpha(float bgAlpha) {
        WindowManager.LayoutParams lp = getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        getWindow().setAttributes(lp);
    }

    //进度对话框
    public void showProgressDialog(String tips) {
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(tips);
        progressDialog.show();
    }

    public void hideProgressDialog() {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    //然后再重写三个方法
    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
