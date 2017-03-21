-- ----------------------------
-- 用户表
-- ----------------------------
INSERT  INTO users (userid,username,password,salt,starttime,endtime,locked) 
		VALUES('10000','zhoulei','zl',null,'2015-07-10','2035-07-10',null);
		
-- ----------------------------
-- 角色表
-- ----------------------------
INSERT  INTO role (roleid,rotype,rolename,description,availabe) 
		VALUES('10','管理员','admin',null,true);
INSERT  INTO role (roleid,rotype,rolename,description,availabe) 
		VALUES('20','用户','user',null,true);

-- -------------------------------
-- 角色用户表
-- -------------------------------		
INSERT  INTO role_users (roleid,userid) 
		VALUES('10000','10');
INSERT  INTO role_users (roleid,userid) 
		VALUES('10000','20');
-- ----------------------------
-- 菜单表
-- ----------------------------
INSERT  INTO menu (menuid,menuname,level,pmenuid,sysid,uri,availabe) 
		VALUES('00','系统管理','0','00','00','/sys/main',true);
INSERT  INTO menu (menuid,menuname,level,pmenuid,sysid,uri,availabe) 
		VALUES('10','用户管理','1','00','00','/sys/user',true);
INSERT  INTO menu (menuid,menuname,level,pmenuid,sysid,uri,availabe) 
		VALUES('20','角色管理','1','00','00','/sys/role',true);
INSERT  INTO menu (menuid,menuname,level,pmenuid,sysid,uri,availabe) 
		VALUES('30','资源管理','1','00','00','/sys/resource',true);
INSERT  INTO menu (menuid,menuname,level,pmenuid,sysid,uri,availabe) 
		VALUES('40','操作管理','1','00','00','/sys/operation',true);

-- -------------------------------
-- 角色菜单表
-- -------------------------------		
INSERT  INTO role_menu (roleid,menuid) 
		VALUES('10','00');
INSERT  INTO role_menu (roleid,menuid) 
		VALUES('10','10');
INSERT  INTO role_menu (roleid,menuid) 
		VALUES('10','20');
INSERT  INTO role_menu (roleid,menuid) 
		VALUES('10','30');
INSERT  INTO role_menu (roleid,menuid) 
		VALUES('10','40');
INSERT  INTO role_menu (roleid,menuid) 
		VALUES('20','00');
INSERT  INTO role_menu (roleid,menuid) 
		VALUES('20','20');
INSERT  INTO role_menu (roleid,menuid) 
		VALUES('20','40');

-- ----------------------------
-- 资源表
-- ----------------------------
INSERT INTO resource (resourceid,name,retype,reindex,parentids,availabe)
	   VALUES('10','用户','/main/add',0,'30',true);
INSERT INTO resource (resourceid,name,retype,reindex,parentids,availabe)
	   VALUES('20','用户','/main/update',1,'30',true);
INSERT INTO resource (resourceid,name,retype,reindex,parentids,availabe)
	   VALUES('30','用户','/main/**',2,'30',true);
		
-- -------------------------------
-- 角色资源表
-- -------------------------------	
INSERT  INTO role_resource (roreid,sysid,roleid,resourceid,operaid) 
		VALUES('10','00','10','10','10');
INSERT  INTO role_resource (roreid,sysid,roleid,resourceid,operaid) 
		VALUES('30','10','20','10','10');
INSERT  INTO role_resource (roreid,sysid,roleid,resourceid,operaid) 
		VALUES('50','10','20','20','20');
INSERT  INTO role_resource (roreid,sysid,roleid,resourceid,operaid) 
		VALUES('60','10','20','30','20');
INSERT  INTO role_resource (roreid,sysid,roleid,resourceid,operaid) 
		VALUES('70','10','20','30','10');

-- ----------------------------
-- 操作表
-- ----------------------------
INSERT INTO operation (operaid,operaname,operatype,operation,availabe)
	   VALUES('10','添加','uri','add',true);
INSERT INTO operation (operaid,operaname,operatype,operation,availabe)
	   VALUES('20','添加','uri','update',true);	

	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
	   
