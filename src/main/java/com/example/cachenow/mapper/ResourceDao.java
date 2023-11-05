package com.example.cachenow.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cachenow.domain.Resource;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author Ctrlcv工程师
 * @since 2023-10-31
 */
@Repository
public interface ResourceDao extends BaseMapper<Resource> {
     @Select("select * from cloud_user.Resource where title = #{title}")
     List<Resource> findByTitle(String title);
}
