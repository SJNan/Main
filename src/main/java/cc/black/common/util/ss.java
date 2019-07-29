package cc.black.common.util;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ss {

    /**
     * 获取上传oss令牌
     * @param
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/get-balance-token")
    @ResponseBody
    public Map<String, String> getBlanceToken(){

        try{
            Map<String, String> map = PostObjectPolicy.getBalanceToken(balanceUrl);
            List<Map<String, String>> list= new ArrayList<Map<String,String>>();
            list.add(map);

        }catch (Exception e){
        }finally {
            return null;
        }
    }
}
