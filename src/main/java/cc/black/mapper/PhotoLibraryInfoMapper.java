package cc.black.mapper;

import cc.black.model.vo.PhotoLibraryInfoVO;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author sunjinna
 * @date 2019年8月9日10:37:51
 */
public interface PhotoLibraryInfoMapper extends BaseMapper<PhotoLibraryInfoVO> {

    /**
     * 查询某个时间段数据
     * @param startTime 开始时间
     * @param endTime 结束时间
     * @return 返回某个时间段数据集合
     */
    List<PhotoLibraryInfoVO> list(@Param(value = "startTime") String startTime,@Param(value = "endTime") String endTime);

}
