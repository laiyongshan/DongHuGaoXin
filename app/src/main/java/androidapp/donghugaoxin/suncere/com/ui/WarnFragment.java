package androidapp.donghugaoxin.suncere.com.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import java.util.ArrayList;
import java.util.List;

import androidapp.donghugaoxin.suncere.com.R;
import androidapp.donghugaoxin.suncere.com.adapter.PicAdapter;
import androidapp.donghugaoxin.suncere.com.presenter.BasePresenterChild;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import suncere.androidapp.lib.mvp.ui.baseui.MvpFragment;
import suncere.androidapp.lib.mvp.ui.iview.IBaseView;


/**
 * @author lys
 * @time 2018/5/23 16:54
 * @desc:报警页面
 */

public class WarnFragment extends MvpFragment<BasePresenterChild> implements IBaseView, SwipeRefreshLayout.OnRefreshListener {

    private View view;

    @BindView(R.id.pic_rv)
    RecyclerView pic_rv;

    @BindView(R.id.take_pic_iv)
    ImageView take_pic_iv;

    @BindView(R.id.commit_btn)
    Button commit_btn;

    @BindView(R.id.warn_condition_et)
    EditText warn_condition_et;

    List<Uri> mSelecteds = new ArrayList<>();

    PicAdapter mPicAdapter;

    public final int TAKE_PIC_REQUEST_CODE = 100;
    private static final int RC_CAMERA_PERM = 123;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_warn, null);
        ButterKnife.bind(this, view);

        initView();
        return view;
    }

    private void initView() {

        mPicAdapter = new PicAdapter(mSelecteds, getActivity());
        mPicAdapter.openLoadAnimation();
        mPicAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                switch (view.getId()) {
                    case R.id.cancel_pic_iv:
                        mSelecteds.remove(position);
                        mPicAdapter.setNewData(mSelecteds);
                        if (mSelecteds.isEmpty())
                            pic_rv.setVisibility(View.GONE);
                        if (mSelecteds.size() < 3)
                            take_pic_iv.setVisibility(View.VISIBLE);

                        commitStatus();
                        break;
                }
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        pic_rv.setLayoutManager(linearLayoutManager);
        pic_rv.setAdapter(mPicAdapter);

        warn_condition_et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                commitStatus();
            }
        });

    }

    //提交按钮的状态变化
    private void commitStatus() {
        if ((!mSelecteds.isEmpty()) && (!warn_condition_et.getText().toString().trim().equals(""))) {
            commit_btn.setBackgroundResource(R.drawable.bg_commit_button1);
            commit_btn.setClickable(true);
        } else {
            commit_btn.setBackgroundResource(R.drawable.bg_commit_button2);
            commit_btn.setClickable(false);
        }
    }

    @OnClick({R.id.take_pic_iv,R.id.commit_btn})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.take_pic_iv:
                cameraTask();
                break;

            case R.id.commit_btn://提交按鈕/

                break;
        }
    }


    public void cameraTask() {

        Matisse.from(WarnFragment.this)
                .choose(MimeType.ofImage()) // 选择 mime 的类型
                .countable(true)
                .capture(true)
                .captureStrategy(
                        new CaptureStrategy(true, "androidapp.donghugaoxin.suncere.com.fileProvider"))
                .maxSelectable(3) // 图片选择的最多数量
                .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED)
                .thumbnailScale(0.85f) // 缩略图的比例
                .imageEngine(new GlideEngine()) // 使用的图片加载引擎
                .forResult(TAKE_PIC_REQUEST_CODE); // 设置作为标记的请求码
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == TAKE_PIC_REQUEST_CODE) {
            if (data != null) {
                mSelecteds.addAll(Matisse.obtainResult(data));
                if (!mSelecteds.isEmpty())
                    pic_rv.setVisibility(View.VISIBLE);
                if (mSelecteds.size() >= 3)
                    take_pic_iv.setVisibility(View.GONE);
                else
                    take_pic_iv.setVisibility(View.VISIBLE);
                mPicAdapter.setNewData(mSelecteds);

                commitStatus();
                Log.d("Matisse", "mSelected: " + mSelecteds.size());

            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onRefresh() {

    }

    @Override
    public void showRefresh() {

    }

    @Override
    public void getDataSuccess(Object response) {

    }

    @Override
    public void getDataFail(String msg) {

    }

    @Override
    public void finishRefresh() {

    }

    @Override
    protected BasePresenterChild createPresenter() {
        return null;
    }


}
