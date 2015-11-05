package atom.mobile.frame.util;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cn on 2015/6/25.
 */
public class JsonUtil {

    private static  ObjectMapper objectMapper = null;

    private static ObjectMapper getInstance(){
            if (objectMapper==null){
                synchronized (ObjectMapper.class) {
                    if (objectMapper==null){
                        objectMapper = new ObjectMapper();
                    }
                }
            }

            return objectMapper;
        }

    public static Map getJsonObject(String obj) {
        ObjectMapper mapper = getInstance();
        Map<String, Object> result = new HashMap();
        try {
            if(obj != null)
                result = mapper.readValue(obj, Map.class);
        } catch(IOException e) {
            throw new RuntimeException("getJsonObject字符串解析出错！",e);
        }
        return result;

    }

    public static List getJsonList(String obj) {
        ObjectMapper mapper = getInstance();
        List result = null;
        try {
            if(obj != null)
                result = mapper.readValue(obj, List.class);
        } catch(IOException e) {
            throw new RuntimeException("getJsonObject字符串解析出错！",e);
        }
        return result;

    }

    public static Object getJsonObjectValue(Object jsonObject, Object key) {
        return ((Map) jsonObject).get(key);
    }

    public static String mapToJson(Map<String, Object> map) {
        try {
            return  getInstance().writeValueAsString(map);
        } catch(IOException e) {
            throw new RuntimeException("JSON字符串解析出错！",e);
        }
    }


    public static String listToJson(List list) {
        try {
            return  getInstance().writeValueAsString(list);
        } catch(IOException e) {
            throw new RuntimeException("JSON字符串解析出错！");
        }
    }
}
