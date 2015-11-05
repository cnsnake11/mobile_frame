package atom.mobile.plugins.choosedate;

import atom.mobile.frame.activity.BaseWebViewActivity;
import atom.mobile.frame.webview.invoker.EventHandler;
import atom.mobile.frame.webview.invoker.IWebViewInvoker;
import atom.mobile.lib.slidedate.SlideDateTimeListener;
import atom.mobile.lib.slidedate.SlideDateTimePicker;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by cn on 2015/7/22.
 */
public class ChooseDateWebviewInvoker implements IWebViewInvoker {

    private BaseWebViewActivity act;
    EventHandler eventHandler;

    @Override
    public void invoke(Map data, BaseWebViewActivity act, EventHandler eventHandler) throws Exception {

        this.act=act;
        this.eventHandler=eventHandler;

        Date date=new Date();
        String value= (String) data.get("value");
        if(value!=null&&!"".equals(value)){
            value=value.trim();
            SimpleDateFormat sdf;
            if(value.indexOf(" ")==-1){//没有空格 所以没有时分秒
                sdf=mFormatter;
            }else{
                sdf=mFormatter2;
            }

           try{
               date=sdf.parse(value);
           }catch (ParseException e){//值不合法，也不要影响弹出日期控件
               e.printStackTrace();
           }
        }


        new SlideDateTimePicker.Builder(act.getSupportFragmentManager())
                .setListener(listener)
                .setInitialDate(date)
                        //.setMinDate(minDate)
                        //.setMaxDate(maxDate)
                .setIs24HourTime(true)
                        //.setTheme(SlideDateTimePicker.HOLO_DARK)
                        //.setIndicatorColor(Color.parseColor("#990000"))
                .build()
                .show();
    }



    private SimpleDateFormat mFormatter = new SimpleDateFormat("yyyy-MM-dd");
    private SimpleDateFormat mFormatter2 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private SlideDateTimeListener listener = new SlideDateTimeListener() {

        @Override
        public void onDateTimeSet(Date date)
        {
//            Toast.makeText(act,
//                    mFormatter.format(date), Toast.LENGTH_SHORT).show();
            Map data=new HashMap();
            data.put("value",mFormatter2.format(date));
            eventHandler.fireEvent("chooseDateFinish",data);

        }

        // Optional cancel listener
        @Override
        public void onDateTimeCancel()
        {
//            Toast.makeText(act,
//                    "Canceled", Toast.LENGTH_SHORT).show();
        }
    };

}
