package com.hibo.cms.sys.shiro.util;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.FilterChain;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.mgt.DefaultFilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainManager;
import org.apache.shiro.web.filter.mgt.FilterChainResolver;
import org.apache.shiro.web.filter.mgt.PathMatchingFilterChainResolver;
import org.apache.shiro.web.mgt.WebSecurityManager;
import org.apache.shiro.web.servlet.AbstractShiroFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.BeanInitializationException;

import com.hibo.cms.sys.shiro.chain.ChainDefinitionsStringUtil;
import com.hibo.cms.user.model.Shirolimit;
import com.hibo.cms.user.service.shirolimit.IShirolimitService;

/**
 * <p>标题：拦截器</p>
 * <p>功能： 拦截器匹配</p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年10月13日 下午12:08:54</p>
 * <p>类全名：com.hibo.cms.sys.shiro.util.SysShiroFilterFactoryBean</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
public class SysShiroFilterFactoryBean extends ShiroFilterFactoryBean {
	private static transient final Logger log = LoggerFactory.getLogger(SysShiroFilterFactoryBean.class);
	private static FilterChainManager sysFilterChainManager;
	private static IShirolimitService shirolimitService;
	
	@Resource
	public void setShirolimitService(IShirolimitService shirolimitService) {
		SysShiroFilterFactoryBean.shirolimitService = shirolimitService;
	}

	@Override
	protected AbstractShiroFilter createInstance() throws Exception {
		log.debug("Creating Shiro Filter instance.");
		
		SecurityManager securityManager = getSecurityManager();
		if (securityManager == null) {
			String msg = "SecurityManager property must be set.";
			throw new BeanInitializationException(msg);
		}

		if (!(securityManager instanceof WebSecurityManager)) {
			String msg = "The security manager does not implement the WebSecurityManager interface.";
			throw new BeanInitializationException(msg);
		}

		sysFilterChainManager = createFilterChainManager();
		FilterChainManager manager = initFilterChainManager();
		// Expose the constructed FilterChainManager by first wrapping it in a
		// FilterChainResolver implementation. The AbstractShiroFilter
		// implementations
		// do not know about FilterChainManagers - only resolvers:
		PathMatchingFilterChainResolver chainResolver = new PathMatchingFilterChainResolver(){
			
			@Override
			public FilterChainManager getFilterChainManager() {
				return SysShiroFilterFactoryBean.getSysFilterChainManager();
			}

			@Override
			public FilterChain getChain(ServletRequest request, ServletResponse response, FilterChain originalChain) {
		        FilterChainManager filterChainManager = getFilterChainManager();
		        if (!filterChainManager.hasChains()) {
		            return null;
		        }
		        String requestURI = getPathWithinApplication(request);
		        //the 'chain names' in this implementation are actually path patterns defined by the user.  We just use them
		        //as the chain name for the FilterChainManager's requirements
		        for (String pathPattern : filterChainManager.getChainNames()) {

		            // If the path does match, then pass on to the subclass implementation for specific checks:
		            if (pathMatches(pathPattern, requestURI)) {
		                if (log.isTraceEnabled()) {
		                    log.trace("Matched path pattern [" + pathPattern + "] for requestURI [" + requestURI + "].  " +
		                            "Utilizing corresponding filter chain...");
		                }
		                return filterChainManager.proxy(originalChain, pathPattern);
		            }
		        }

		        return null;
		    }

		};
		chainResolver.setFilterChainManager(manager);

		// Now create a concrete ShiroFilter instance and apply the acquired
		// SecurityManager and built
		// FilterChainResolver. It doesn't matter that the instance is an
		// anonymous inner class
		// here - we're just using it because it is a concrete
		// AbstractShiroFilter instance that accepts
		// injection of the SecurityManager and FilterChainResolver:
		return new SysSpringShiroFilter((WebSecurityManager) securityManager, chainResolver);

	}
	
	protected static FilterChainManager getSysFilterChainManager() {
		return sysFilterChainManager;
	}

	public static FilterChainManager initFilterChainManager() {
		DefaultFilterChainManager newmanager = new DefaultFilterChainManager();
		BeanUtils.copyProperties(sysFilterChainManager, newmanager);
        //添加系统拦截器
		try{
	        List<Shirolimit> ShirolimitList = shirolimitService.selectAllList();
	        for (Shirolimit shirolimit : ShirolimitList) {
	            newmanager.createChain(shirolimit.getUrl(), shirolimit.getLimits());
			}
        }catch(Exception e){
        	if (log.isInfoEnabled()) {
                log.info(e.getMessage());
            }
        }
        //最后添加系统用户校验
        newmanager.createChain("/admin/**", ChainDefinitionsStringUtil.USER_STRING+",roles[admin]");
        //过滤访问过程中的用户
        newmanager.createChain("/**", ChainDefinitionsStringUtil.USER_STRING);
        return newmanager;
	}

	private static final class SysSpringShiroFilter extends AbstractShiroFilter {
		
		protected SysSpringShiroFilter(WebSecurityManager webSecurityManager, FilterChainResolver resolver) {
			super();
			if (webSecurityManager == null) {
				throw new IllegalArgumentException("WebSecurityManager property cannot be null.");
			}
			setSecurityManager(webSecurityManager);
			if (resolver != null) {
				setFilterChainResolver(resolver);
			}
		}
	}

}
