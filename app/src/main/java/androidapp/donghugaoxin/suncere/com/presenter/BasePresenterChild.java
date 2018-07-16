package androidapp.donghugaoxin.suncere.com.presenter;

import androidapp.donghugaoxin.suncere.com.API.RetrofitSrevice;
import androidapp.donghugaoxin.suncere.com.Utils.ToastUtil;
import androidapp.donghugaoxin.suncere.com.entity.BaseModelBean;
import rx.Observable;
import suncere.androidapp.lib.mvp.api.ApiNetCallBack;
import suncere.androidapp.lib.mvp.presenter.BasePresenter;
import suncere.androidapp.lib.mvp.ui.MyApplication;
import suncere.androidapp.lib.mvp.ui.iview.IBaseView;
import suncere.androidapp.lib.utils.CatchManager;
import suncere.androidapp.lib.utils.NetWorkUtil;

import static suncere.androidapp.lib.utils.CatchManager.getDataFromCatch;

/**
 * Created by Hjo on 2017/6/23.
 */
public class BasePresenterChild<V extends IBaseView> extends BasePresenter<V> {

    public BasePresenterChild(V v) {
        super(v);
    }

    public RetrofitSrevice getRetrofitSrevice(){
        return getBaseRetrofitSrevice(RetrofitSrevice.class);
    }

    @Override
    public  String BaseUrl() {

//        return "http://202.104.69.202:9048/AQIMonitor/";
        return "http://202.104.69.202:9036/Grid/";
//        return "http://10.10.10.221:82/AQIMonitor/";
//        return " http://202.104.69.202:9048/AQIMonitor/";
    }

    @Override
    public void getCatchOrNetData(final String... args) {
        mIView.showRefresh();
        if ( ! NetWorkUtil.isNetWorkAvailable(MyApplication.getMyApplicationContext())) {
//            getCatchData (args);
        }else{
            getNetData(args);
        }
    }

    @Override
    public void getCatchOrNetData(Observable observable, String... args) {
        mIView.showRefresh();
        if ( ! NetWorkUtil.isNetWorkAvailable(MyApplication.getMyApplicationContext())) { // 无网络状态
            ToastUtil.showToastCenter("无网络连接！");
//            getCatchData (args);
        }else{
            getNetData(observable,args);
        }
    }


    protected  void  getNetData(final Observable observable , final String ...args){

        addSubscription(observable, new ApiNetCallBack() {
            @Override
            public void onSuccess(Object response) {
                if (response!=null){
                    BaseModelBean baseModelBean= (BaseModelBean) response;
                    mIView.getDataSuccess(baseModelBean.getData());
                    if (args!=null&&baseModelBean!=null)
                        CatchManager.putData2Cache(args.toString(),baseModelBean.getData());
                }else{
                    mIView.getDataSuccess(null);
                }
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

    @Override
    protected void getCatchData (String... args) {
        if (args!=null)
        mIView.getDataSuccess(getDataFromCatch( args.toString() ));
        else     mIView.getDataSuccess(null);
        mIView.finishRefresh();
    }

    @Override
    protected void getNetData(String... args) {

    }


}
