package cc.black.service;

import cc.black.model.dto.PhotoLibraryInfoDTO;
import cc.black.model.vo.PhotoLibraryInfoVO;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * @author sunjinnan
 * @date 2019年8月9日11:03:45
 */
public interface PhotoLibraryInfoService extends IService<PhotoLibraryInfoVO> {

    /**
     * 查询某个时间段数据
     * @param photoLibraryInfoDTO DTO对象
     * @return 返回某个时间段数据集合
     */
    List<PhotoLibraryInfoVO> list(PhotoLibraryInfoDTO photoLibraryInfoDTO);

}
