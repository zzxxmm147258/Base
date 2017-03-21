package com.hibo.cms.sys.shiro.chain;

import org.apache.shiro.config.Ini;
import org.apache.shiro.config.Ini.Section;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;

import com.hibo.cms.sys.utils.login.LoginUtil;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年8月12日 上午11:15:09</p>
 * <p>类全名：com.hibo.cms.sys.shiro.chain.SysfilterChainDefinitions</p>
 * 作者：Administrator
 * 初审：
 * 复审：
 */
public class SysfilterChainDefinitions implements FactoryBean<Ini.Section>{
	
	private String filterChainDefinitions;
    
    public Section getObject() throws BeansException {  
  
        //获取所有Resource  
        Ini ini = new Ini();
        //加载默认的url  
        ini.load(filterChainDefinitions); 
        Ini.Section section = ini.getSection(Ini.DEFAULT_SECTION_NAME); 
        //添加配置文件设置的登陆器
        if(!LoginUtil.isLoginEmpty()){
        	for (String loginUri : LoginUtil.loginUriSet()) {
        		section .put(LoginUtil.getLoginControllerUrl(loginUri), ChainDefinitionsStringUtil.AUTHC_STRING);
        		section .put(LoginUtil.getLoginFilterUrl(loginUri), ChainDefinitionsStringUtil.USER_STRING);
			}
        }
        return section;  
    }  
  
    /** 
     * 通过filterChainDefinitions对默认的url过滤定义 
     *  
     * @param filterChainDefinitions 默认的url过滤定义 
     */  
    public void setFilterChainDefinitions(String filterChainDefinitions) {  
        this.filterChainDefinitions = filterChainDefinitions;  
    }  
  
    public Class<?> getObjectType() {  
        return this.getClass();  
    }  
  
    public boolean isSingleton() {  
        return false;  
    } 
}
