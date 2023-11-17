package com.example.cachenow.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.cachenow.domain.File;
import org.apache.ibatis.annotations.Param;
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
 */@Repository
public interface FileDao extends BaseMapper<File> {

    @Select("SELECT * FROM cloud_user.File WHERE uploader_id = #{userId} LIMIT #{page.offset}, #{page.size}")
    List<File> selectFilesByUserId(@Param("userId") Long userId, @Param("page") Page page);
}
