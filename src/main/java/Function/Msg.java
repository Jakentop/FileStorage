package Function;

import Model.User;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SimplePropertyPreFilter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zyz
 * 将对象转换为json格式封装，设置项目统一的json返回接口，此类方法为静态方法不可实例化
 * 其中每个方法中status表示状态码，此处状态码具体在接口文档中规范，但是Msg.OK表示为通过，Msg.ERR表示为参数不完整错误
 * 其他错误请按照接口文档给定相应的状态值
 *
 */
public abstract class Msg {

    /**
     * 状态集合
     */
    /**
     * OK 请求成功
     */
    public final static Integer OK=200;
    /**
     * ERR 请求方法错误，请查看接口文档
     */
    public final static Integer ERR=555;
    /**
     * LoginAuth 当前没有登录需要登录权限
     */
    public final static Integer LoginAuth = 556;
    /**
     * NoAuth 当前用户没有权限
     */
    public final static Integer NoAuth=557;

    /**
     * UnKnow 未知错误，应该是服务器出现问题
     */
    public final static Integer UnKnow=599;

    /**
     * 将一个字符串封装为json格式并序列化
     *
     * @param Status 状态为类中的状态
     * @param Title 消息标题
     * @param message 消息信息
     * @return String json序列化的字符串,格式{"status":"状态","title"}
     */
    public static String ParseStr(int Status, String Title, String message) {

        Map<String,Object> map = new HashMap<String,Object>();
        map.put("status", Status);
        map.put("title", Title);
        map.put("type", "string");
        map.put("data", message);
        return JSON.toJSONString(map);
    }

    /**
     *  将一个字典数据转换为json字符串
     * @param Status 状态
     * @param Title 标题
     * @param map 数据字典
     * @return json格式字符串
     */
    public static String ParseMap(int Status, String Title, Object map) {
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("status", Status);
        ret.put("title", Title);
        ret.put("type", "map");
        ret.put("data", map);
        return JSON.toJSONString(ret);
    }

    /**
     * 将一个列表数据转换为json字符串
     * @param Status
     * @param Title
     * @param list
     * @return
     */
    public static String ParseList(int Status, String Title, List list) {
        Map<String, Object> ret = new HashMap<String, Object>();
        ret.put("status", Status);
        ret.put("title", Title);
        ret.put("type", "list");
        ret.put("data", list);
        return JSON.toJSONString(ret);
    }

    /**
     * 获取对象的某个部分，并转换为标准的返回格式，直接调用
     * @param Status 返回的状态码int
     * @param Title 标题
     * @param object 返回的对象，可以是一个字典，或者一个Bean
     * @param properties 需要包含在对象内的参数
     * @return
     */
    public static String ParseInclude(int Status, String Title, Object object, String... properties) {

        Map res = Include(object, properties);
        return ParseMap(Status, Title, res);
    }

    /**
     * 获取对象的某个部分，并转换为dic的json格式(包含）
     * @param object 需要序列化的对象
     * @param properties 需要选择的原型
     * @return 返回一个选择封装后的Map对象
     */
    public static Map Include(Object object, String...properties) {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        for (String add : properties) {
            filter.getIncludes().add(add);
        }
        return JSONObject.parseObject(JSON.toJSONString(object, filter), Map.class);
    }

    /**
     * 获取排除了对象的某个部分，并转换为标准的返回格式，直接调用
     * @param Status 返回的状态码int
     * @param Title 标题
     * @param object 返回的对象，可以是一个字典，或者一个Bean
     * @param properties 需要排除在对象内的参数
     * @return
     */
    public static String ParseExclude(int Status, String Title, Object object, String... properties) {

        Map res = Exclude(object, properties);
        return ParseMap(Status, Title, res);
    }

    /**
     * 获取对象的某个部分，并转换为dic的json格式(排除)
     * @param object 需要序列化的对象
     * @param properties 需要排除的原型
     * @return 返回一个选择封装后的Map对象
     */
    public static Map Exclude(Object object, String...properties) {
        SimplePropertyPreFilter filter = new SimplePropertyPreFilter();
        for (String add : properties) {
            filter.getExcludes().add(add);
        }
        return JSONObject.parseObject(JSON.toJSONString(object, filter), Map.class);
    }

}