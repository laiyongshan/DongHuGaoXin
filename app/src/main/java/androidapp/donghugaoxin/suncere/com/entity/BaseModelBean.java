package androidapp.donghugaoxin.suncere.com.entity;

import suncere.androidapp.lib.mvp.entity.BaseBean;

/**
 * Created by Hjo on 2017/10/20.
 */

public class BaseModelBean<T> extends BaseBean {

    private T Data;
    private boolean Result;
    private String ErrorMessage;
    private boolean NoAuthority;
    private boolean LoginTimeOut;

    public T getData() {
        return Data;
    }

    public void setData(T data) {
        Data = data;
    }

    public boolean isResult() {
        return Result;
    }

    public void setResult(boolean result) {
        Result = result;
    }

    public String getErrorMessage() {
        return ErrorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        ErrorMessage = errorMessage;
    }

    public boolean isNoAuthority() {
        return NoAuthority;
    }

    public void setNoAuthority(boolean noAuthority) {
        NoAuthority = noAuthority;
    }

    public boolean isLoginTimeOut() {
        return LoginTimeOut;
    }

    public void setLoginTimeOut(boolean loginTimeOut) {
        LoginTimeOut = loginTimeOut;
    }
}
