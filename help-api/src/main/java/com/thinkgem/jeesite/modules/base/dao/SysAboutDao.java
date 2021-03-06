/**
 * Copyright &copy; 2012-2014 <a href="https://github.com/thinkgem/jeesite">JeeSite</a> All rights reserved.
 */
package com.thinkgem.jeesite.modules.base.dao;

import com.thinkgem.jeesite.common.persistence.CrudDao;
import com.thinkgem.jeesite.common.persistence.annotation.MyBatisDao;
import com.thinkgem.jeesite.modules.base.entity.SysAbout;

/**
 * 关于设置DAO接口
 * @author wcf
 * @version 2018-01-03
 */
@MyBatisDao
public interface SysAboutDao extends CrudDao<SysAbout> {
	
}