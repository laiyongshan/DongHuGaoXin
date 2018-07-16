package androidapp.donghugaoxin.suncere.com.presenter;

import suncere.androidapp.lib.mvp.api.ApiNetCallBack;
import suncere.androidapp.lib.mvp.ui.MyApplication;
import suncere.androidapp.lib.mvp.ui.iview.IBaseView;
import suncere.androidapp.lib.utils.NetWorkUtil;

/**
 * Created by Hjo on 2017/10/19.
 */

public class UpDataAPPPresenter extends BasePresenterChild <IBaseView> {

    public UpDataAPPPresenter(IBaseView iBaseView) {
        super(iBaseView);
    }

    public void updataAPP(String version){
        mIView.showRefresh();
        if (NetWorkUtil.isNetWorkAvailable(MyApplication.getMyApplicationContext())){
            getNetupdataAPP(version);
        }else{
            mIView.getDataFail("无网络连接！");
//            mIView.getDataSuccess( "无网络连接！");
            mIView.finishRefresh();
        }
    }
    private void getNetupdataAPP(String version ){
        addSubscription(getRetrofitSrevice().updataAPP(version), new ApiNetCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                mIView.getDataSuccess(response);
            }

            @Override
            public void onError(String msg) {
                mIView.getDataFail(msg);
            }
            @Override
            public void onFinish() {
                mIView.finishRefresh();
            }
        });

    }
}
