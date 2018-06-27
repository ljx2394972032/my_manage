package com.vigekoo.manage.sys.service;


import com.vigekoo.manage.sys.entity.SysRole;

import java.util.List;
import java.util.Map;

public interface SysRoleService {
	
	SysRole queryObject(Long id);
	
	List<SysRole> queryList(Map<String, Object> map);
	
	int queryTotal(Map<String, Object> map);
	
	void save(SysRole role);
	
	void update(SysRole role);
	
	void deleteBatch(Long[] ids);

}
