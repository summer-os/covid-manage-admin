package com.summer.manage.entity.system;


import com.summer.common.core.BaseEntity;
import com.summer.common.ibatis.DefaultStrategy;

/**
 * @Description：
 * @Author：sacher
 * @Create：2020/10/30 10:42 上午
 **/
public class SysRoleDept extends BaseEntity<DefaultStrategy> {
    /**
     * 角色ID
     */
    public Long roleId;

    /**
     * 部门ID
     */
    public Long deptId;

}
