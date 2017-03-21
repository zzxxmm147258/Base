package com.hibo.bas.spring;

/**
 * 
 */

import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.support.ConfigurableWebBindingInitializer;
import org.springframework.web.bind.support.WebBindingInitializer;
import org.springframework.web.context.request.WebRequest;

/**
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年7月30日 下午3:40:46</p>
 * <p>类全名：com.hibo.bas.spring.ExtendsConfigurableWebBindingInitializer</p>
 * 作者：cuizhimin
 * 初审：
 * 复审：
 */
public class ExtendsConfigurableWebBindingInitializer extends ConfigurableWebBindingInitializer {

	private WebBindingInitializer[] bindingInitializers;
	
	public WebBindingInitializer[] getBindingInitializers() {
		return bindingInitializers;
	}

	public void setBindingInitializers(WebBindingInitializer[] bindingInitializers) {
		this.bindingInitializers = bindingInitializers;
	}

	@Override
	public void initBinder(WebDataBinder binder, WebRequest request) {
		if (this.bindingInitializers != null) {
			for (WebBindingInitializer initializer : this.bindingInitializers) {
				initializer.initBinder(binder, request);
			}
		}
		super.initBinder(binder, request);
	}
}
