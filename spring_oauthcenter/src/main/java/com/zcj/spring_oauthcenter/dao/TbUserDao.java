package com.zcj.spring_oauthcenter.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zcj.spring_oauthcenter.po.TbUser;
import com.zcj.spring_oauthcenter.po.TbUserExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbUserDao extends BaseMapper<TbUser> {
    long countByExample(TbUserExample example);

    int deleteByExample(TbUserExample example);

    int deleteByPrimaryKey(Long id);

    int insert(TbUser record);

    int insertSelective(TbUser record);

    List<TbUser> selectByExample(TbUserExample example);

    TbUser selectByPrimaryKey(Long id);

    int updateByExampleSelective(@Param("record") TbUser record, @Param("example") TbUserExample example);

    int updateByExample(@Param("record") TbUser record, @Param("example") TbUserExample example);

    int updateByPrimaryKeySelective(TbUser record);

    int updateByPrimaryKey(TbUser record);
}
