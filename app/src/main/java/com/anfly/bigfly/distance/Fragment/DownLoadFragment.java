package com.anfly.bigfly.distance.Fragment;

import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.anfly.bigfly.R;
import com.anfly.bigfly.base.BaseFragment;
import com.anfly.bigfly.distance.bean.FileBean;
import com.anfly.bigfly.distance.receiver.DownLoadReceiver;
import com.anfly.bigfly.utils.Constants;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author Anfly
 * @date 2019/5/22
 * description：
 */
public class DownLoadFragment extends BaseFragment {

    @BindView(R.id.tv_upload)
    TextView tvUpload;
    @BindView(R.id.tv_download_ok)
    TextView tvDownloadOk;
    @BindView(R.id.tv_download_retrofit)
    TextView tvDownloadRetrofit;
    @BindView(R.id.tv_download_con)
    TextView tvDownloadCon;
    @BindView(R.id.tv_download_service)
    TextView tvDownloadService;
    @BindView(R.id.tv_download_more)
    TextView tvDownloadMore;
    @BindView(R.id.pb0)
    ProgressBar pb0;
    @BindView(R.id.tv0)
    TextView tv0;
    @BindView(R.id.pb1)
    ProgressBar pb1;
    @BindView(R.id.tv1)
    TextView tv1;
    @BindView(R.id.pb2)
    ProgressBar pb2;
    @BindView(R.id.tv2)
    TextView tv2;
    private DownLoadReceiver downLoadReceiver;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_download;
    }

    @Override
    public void onResume() {
        super.onResume();
        downLoadReceiver = new DownLoadReceiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("download");
        getActivity().registerReceiver(downLoadReceiver, intentFilter);
    }

    @Override
    public void onPause() {
        super.onPause();
        mContext.unregisterReceiver(downLoadReceiver);
    }

    private void showPop() {
        View inflate = LayoutInflater.from(getActivity()).inflate(R.layout.pop_layout, null, false);
        final TextView tv_download = inflate.findViewById(R.id.tv_download);
        final ProgressBar pb_download = inflate.findViewById(R.id.pb_download);
        Button btn_start_download = inflate.findViewById(R.id.btn_start_download);
        final PopupWindow popupWindow = new PopupWindow(inflate, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        inflate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popupWindow.dismiss();
            }
        });
        btn_start_download.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                downLoadOK(tv_download, pb_download);
            }
        });
        popupWindow.setBackgroundDrawable(null);
        popupWindow.setOutsideTouchable(true);
        //在某个view的下方,如果有偏移量,在某个view下方开始加减对应偏移量即popwindow的位置
        popupWindow.setAnimationStyle(R.style.PopAnimation);
        popupWindow.showAsDropDown(tvDownloadOk, 0, 0);
    }

    private void downLoadOK(final TextView tv_download, final ProgressBar pb_download) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        Request request = new Request.Builder()
                .get()
                .url(Constants.DOWN_LOAD_URL)
                .build();

        Call call = okHttpClient.newCall(request);

        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("TAG", "onFailure:" + e.getMessage());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                ResponseBody body = response.body();
                InputStream inputStream = body.byteStream();
                long contentLength = body.contentLength();

                saveFile(tv_download, pb_download, inputStream, contentLength, Environment.getExternalStorageDirectory() + "/360/a.apk");
            }
        });
    }

    private void saveFile(final TextView tv_download, final ProgressBar pb_download, InputStream inputStream, final long contentLength, String path) {
        try {
            FileOutputStream fos = new FileOutputStream(new File(path));
            int len = -1;
            long count = 0;
            byte[] bytes = new byte[1024 * 20];

            while ((len = inputStream.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
                count += len;
                Log.e("TAG", count + " / " + contentLength);


                final long finalCount = count;
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        pb_download.setMax((int) contentLength);
                        pb_download.setProgress((int) finalCount);
                        tv_download.setText("下载进度：" + (int) (100 * finalCount / contentLength) + "%");
                    }
                });

            }

            fos.close();
            inputStream.close();

            getActivity().runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    tv_download.setText("下载完成");
                    Intent intent = new Intent("download");
                    intent.putExtra("download", "下载成功");
                    getActivity().sendBroadcast(intent);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadOk(File file) {
        //创建ok
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

        //封装file
        MediaType type = MediaType.parse("image/png");
        RequestBody requestBody1 = RequestBody.create(type, file);

        //封装参数
        MultipartBody multipartBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("key", "H1809C")
                .addFormDataPart("file", file.getName(), requestBody1)
                .build();

        //构建请求
        final Request request = new Request.Builder()
                .post(multipartBody)
                .url(Constants.UPLOAD_URL)
                .build();

        //获取call并执行请求
        okHttpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        Log.e("TAG", "onFailure" + e.getMessage());
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        String string = response.body().string();
                        final FileBean fileBean = new GsonBuilder().serializeNulls().create().fromJson(string, FileBean.class);
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (fileBean != null) {
                                    if (fileBean.getCode() == 200) {
                                        String url = fileBean.getData().getUrl();
                                    } else {
                                        Toast.makeText(getActivity(), fileBean.getRes(), Toast.LENGTH_SHORT).show();
                                    }
                                } else {
                                    Toast.makeText(getActivity(), fileBean.getRes(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
    }

    @OnClick({R.id.tv_upload, R.id.tv_download_ok, R.id.tv_download_retrofit, R.id.tv_download_con, R.id.tv_download_service, R.id.tv_download_more})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_upload:
                break;
            case R.id.tv_download_ok:
                showPop();
                break;
            case R.id.tv_download_retrofit:
                break;
            case R.id.tv_download_con:
                break;
            case R.id.tv_download_service:
                break;
            case R.id.tv_download_more:
                break;
        }
    }
}
