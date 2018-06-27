package com.vigekoo.manage.sys.dao;

import com.vigekoo.manage.sys.entity.SysUserToken;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SysUserTokenDao extends BaseDao<SysUserToken> {
    
    SysUserToken queryByUserId(Long userId);

    SysUserToken queryByToken(String token);
	
}
