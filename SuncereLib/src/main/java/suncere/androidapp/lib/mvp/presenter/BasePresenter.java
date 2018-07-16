package suncere.androidapp.lib.mvp.presenter;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;
import suncere.androidapp.lib.mvp.api.ApiNetManager;
import suncere.androidapp.lib.mvp.ui.iview.IBaseView;

/**
 * Created by Hjo on 2017/5/11.
 */

public abstract class BasePresenter<V extends IBaseView>  {
    public V mIView;

    private CompositeSubscription mCompositeSubscription;


    public BasePresenter(V v){
        attrchIView(v);
    }
    /**
     * 使用的IView接口
     * @param IView
     */
    public void attrchIView(V IView ){

        this.mIView=IView;
    }

    public abstract String  BaseUrl();

    /**
     *   当需要时  可进行拓展
     * @param args
     */
    public abstract void  getCatchOrNetData(String ...args);

    public abstract void  getCatchOrNetData(Observable observable,String ...args);
//    protected abstract void  getData(String ...args);

    /**
     *
     * @param args   如果有需要使用到标志位  则约定最后一个字段为tag
     */
    protected abstract void  getCatchData(String ...args);
    /**
     *
     * @param args   如果有需要使用到标志位  则约定最后一个字段为tag
     */
    protected abstract void  getNetData(String ...args);

    protected  <T> T getBaseRetrofitSrevice(Class<T> Tclass){
        return  ApiNetManager.getInstence().getRetrofitService( Tclass,BaseUrl());
    }


    public void detachView() {
        this.mIView = null;
        onUnsubscribe();
    }

    //RXjava取消注册，以避免内存泄露
    public void onUnsubscribe() {
        if (mCompositeSubscription != null && mCompositeSubscription.hasSubscriptions()) {
            mCompositeSubscription.unsubscribe();
        }
    }


    public void addSubscription(Observable observable, Subscriber subscriber) {
        if (mCompositeSubscription == null) {
            mCompositeSubscription = new CompositeSubscription();
        }
        mCompositeSubscription.add(observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(subscriber));
    }


}
