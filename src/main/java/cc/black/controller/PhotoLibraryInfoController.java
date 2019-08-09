package cc.black.controller;

import cc.black.common.support.BaseController;
import cc.black.common.util.JsonResult;
import cc.black.model.dto.PhotoLibraryInfoDTO;
import cc.black.model.vo.PhotoLibraryInfoVO;
import cc.black.service.PhotoLibraryInfoService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author sunjinnan
 * @date 2019年8月9日11:20:31
 */
@RestController
@RequestMapping("/photo-library-info")
public class PhotoLibraryInfoController extends BaseController {

    @Autowired
    private PhotoLibraryInfoService photoLibraryInfoService;

    @RequestMapping("/list")
    public JsonResult list(@RequestBody PhotoLibraryInfoDTO photoLibraryInfoDTO){
        JsonResult jsonResult=new JsonResult();
        try{
            List<PhotoLibraryInfoVO> list=photoLibraryInfoService.list(photoLibraryInfoDTO);
            Map map=new HashMap();
            map.put("list",list);
            map.put("count",list.size());
            jsonResult.setResult(map);
        }catch (Exception e){
            logger.error("", ExceptionUtils.getStackTrace(e));
            jsonResult.error();
        }finally {
            return jsonResult;
        }
    }
}
