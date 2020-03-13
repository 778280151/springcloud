package com.zcj.spring_oauthcenter.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zcj.spring_oauthcenter.po.TbPermission;
import com.zcj.spring_oauthcenter.po.TbPermissionExample;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TbPermissionDao extends BaseMapper<TbPermission> {
    long countByExample(TbPermissionExample example);

    int deleteByExample(TbPermissionExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbPermission record);

    int insertSelective(TbPermission record);

    List<TbPermission> selectByExample(TbPermissionExample example);

    TbPermission selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbPermission record, @Param("example") TbPermissionExample example);

    int updateByExample(@Param("record") TbPermission record, @Param("example") TbPermissionExample example);

    int updateByPrimaryKeySelective(TbPermission record);

    int updateByPrimaryKey(TbPermission record);


    List<Map> queryByUserid(Long userId);
}
