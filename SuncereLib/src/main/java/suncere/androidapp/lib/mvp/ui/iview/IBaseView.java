package suncere.androidapp.lib.mvp.ui.iview;

/**
 * Created by Hjo on 2017/5/11.
 */

public interface IBaseView {
    void showRefresh();
    void getDataSuccess(Object response);
    void getDataFail(String msg);
    void finishRefresh();


}
