package cc.black.service.impl;

import cc.black.mapper.PhotoLibraryInfoMapper;
import cc.black.model.dto.PhotoLibraryInfoDTO;
import cc.black.model.vo.PhotoLibraryInfoVO;
import cc.black.service.PhotoLibraryInfoService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author sunjinnan
 * @date 2019年8月9日11:16:34
 */
@Service
public class PhotoLibraryInfoServiceImpl extends ServiceImpl<PhotoLibraryInfoMapper, PhotoLibraryInfoVO> implements PhotoLibraryInfoService {

    /**
     * 查询某个时间段数据
     * @param photoLibraryInfoDTO DTO对象
     * @return 返回某个时间段数据集合
     */
    @Override
    public List<PhotoLibraryInfoVO> list(PhotoLibraryInfoDTO photoLibraryInfoDTO) {
        return baseMapper.list(photoLibraryInfoDTO.getStartTime(),photoLibraryInfoDTO.getEndTime());
    }

}
