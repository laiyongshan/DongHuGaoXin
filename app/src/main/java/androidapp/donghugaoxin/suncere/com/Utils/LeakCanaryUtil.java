package androidapp.donghugaoxin.suncere.com.Utils;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import java.lang.reflect.Field;

/**
 * Created by Hjo on 2017/12/6 10:31.
 */

public final class LeakCanaryUtil {

    private LeakCanaryUtil() {

    }

    public static void fixMemoryLeak(@Nullable Activity activity) {
        if (activity == null) {
            return;
        }
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm == null) {
            return;
        }

        String[] arr = new String[]{"mCurRootView", "mServedView", "mNextServedView"};
        Field field;
        Object fieldValue;
        try {
            for (String param : arr) {
                field = imm.getClass().getDeclaredField(param);
                if (!field.isAccessible()) {
                    field.setAccessible(true);
                }
                fieldValue = field.get(imm);
                if (fieldValue instanceof View) {
                    View v = (View) fieldValue;
                    if (v.getContext() == activity) { // 被InputMethodManager持有引用的context是想要目标销毁的
                        field.set(imm, null);
                    } else {
                        // 不是想要目标销毁的，即为又进了另一层界面了，不要处理，避免影响原逻辑,也就不用继续for循环了
                        break;
                    }
                }
            }
        } catch (Exception ignore) {
            // nothing
        }
    }
}
