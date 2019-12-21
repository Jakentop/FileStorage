package Controllor.Dir;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class tool {
    /**
     * 用于目录结果集的构建，返回一个键值对
     * @param NodeID
     * @param NodeName
     * @param Childs
     * @param Father
     * @return
     */
    public static Map resMap(int NodeID, String NodeName, List<Integer> Childs, int Father) {
        HashMap res = new HashMap();
        res.put("NodeID", NodeID);
        res.put("NodeName", NodeName);
        res.put("Childs", Childs);
        res.put("Father", Father);
        return res;
    }

}
