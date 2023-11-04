package com.example.cachenow.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.cachenow.domain.History;
import io.lettuce.core.dynamic.annotation.Param;
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
public interface HistoryDao extends BaseMapper<History> {
    @Select("SELECT * FROM cloud_user.History WHERE user_id = #{userId} LIMIT #{startIndex}, #{pageSize}")
    List<History> getHistorysByUserId(@Param("userId") Long userId,
                                      @Param("startIndex") Long startIndex,
                                      @Param("pageSize") Long pageSize);


}
