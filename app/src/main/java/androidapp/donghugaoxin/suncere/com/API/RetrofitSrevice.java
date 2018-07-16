package androidapp.donghugaoxin.suncere.com.API;


import java.util.List;

import androidapp.donghugaoxin.suncere.com.entity.BaseModelBean;
import androidapp.donghugaoxin.suncere.com.entity.SortBean;
import androidapp.donghugaoxin.suncere.com.entity.StationDataBean;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by Hjo on 2017/6/23.
 */
public interface RetrofitSrevice {

    // APP更新
    //    api/Update/GetAndroidAPKUpdate?version={version}
    @GET("VersionUpdate")
    Observable<String> updataAPP(@Query("VersionCode") String version);

    //http://202.104.69.202:9036/Grid/GetStationDataHour?stationType=5&sDate=2018-05-09%2009:00&eDate=2018-05-09%2009:00
    // http://10.10.10.221:82/AQiMonitor/AppLogOn?userName=liuhf&password=111

    @GET("GetStationDataHour")
    Observable<BaseModelBean<List<StationDataBean>>> getStationDataHour(@Query("stationType")String stationType);

    @GET("")
    Observable<BaseModelBean<List<SortBean>>> getSortDataHour();

}



