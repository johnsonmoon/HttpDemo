package tools;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;

/**
 * Created by xuyihao on 2016/5/13.
 * @author johnson
 * @description 网络请求工具类
 * @attention
 */
public class HttpUtil {

    /**
     * fileds
     * */

    /**
     * constructor
     * */

    /**
     * @method
     * @description
     * @return
     * @param
     * */
    public void fileUpload(String[] uploadFiles, String actionURL){
        String end = "/r/n";
        String twoHyphens = "--";
        String bounary = "-------------------HKUGADG524564adgyuyJohnson56484";
        try{
            URL url = new URL(actionURL);
            HttpURLConnection connection = (HttpURLConnection)url.openConnection();

        }catch (IOException e){
            e.printStackTrace();
        }
    }


}
