package com.hibo.bas.freemarker;

import java.util.Map;

/** <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月3日 下午1:55:33</p>
 * <p>类全名：com.hibo.bas.freemarker.IStaticService</p>
 * 作者：马骏达
 * 初审：
 * 复审：
 */
public interface IStaticService {

	/**
	 * 生成静态
	 * 
	 * @param templatePath
	 *            模板文件路径
	 * @param staticPath
	 *            静态文件路径
	 * @param model
	 *            数据
	 * @return 生成数量
	 */
	int build(String templatePath, String staticPath, Map<String, Object> model);
}
