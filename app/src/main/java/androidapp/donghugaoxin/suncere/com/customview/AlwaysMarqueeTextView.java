package androidapp.donghugaoxin.suncere.com.customview;

import android.content.Context;
import android.support.v7.widget.AppCompatTextView;
import android.util.AttributeSet;
import android.view.ViewDebug.ExportedProperty;


public class AlwaysMarqueeTextView extends AppCompatTextView {
	public AlwaysMarqueeTextView(Context context) {  
		super(context);  
	}  
	public AlwaysMarqueeTextView(Context context, AttributeSet attrs) {  
		super(context, attrs);  
	}  
	public AlwaysMarqueeTextView(Context context, AttributeSet attrs,
                                 int defStyle) {
		super(context, attrs, defStyle);  
	}  
	@Override
	@ExportedProperty(category = "focus")
	public boolean isFocused() {
		// TODO Auto-generated method stub
		return true;
	}

}  
