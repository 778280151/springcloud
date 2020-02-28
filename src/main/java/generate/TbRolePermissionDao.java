package generate;

import generate.TbRolePermission;
import generate.TbRolePermissionExample;
import generate.TbRolePermissionWithBLOBs;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TbRolePermissionDao {
    long countByExample(TbRolePermissionExample example);

    int deleteByExample(TbRolePermissionExample example);

    int insert(TbRolePermissionWithBLOBs record);

    int insertSelective(TbRolePermissionWithBLOBs record);

    List<TbRolePermissionWithBLOBs> selectByExampleWithBLOBs(TbRolePermissionExample example);

    List<TbRolePermission> selectByExample(TbRolePermissionExample example);

    int updateByExampleSelective(@Param("record") TbRolePermissionWithBLOBs record, @Param("example") TbRolePermissionExample example);

    int updateByExampleWithBLOBs(@Param("record") TbRolePermissionWithBLOBs record, @Param("example") TbRolePermissionExample example);

    int updateByExample(@Param("record") TbRolePermission record, @Param("example") TbRolePermissionExample example);
}