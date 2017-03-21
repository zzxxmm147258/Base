package com.hibo.bas.fileplugin;

import java.io.File;
import java.util.List;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.springframework.stereotype.Component;

import com.hibo.bas.fileplugin.model.FileInfo;

/**
 * <p>
 * 标题：
 * </p>
 * <p>
 * 功能：
 * </p>
 * <p>
 * 版权： Copyright © 2015 HIBO
 * </p>
 * <p>
 * 公司: 北京瀚铂科技有限公司
 * </p>
 * <p>
 * 创建日期：2015年8月4日 上午9:46:46
 * </p>
 * <p>
 * 类全名：com.hibo.ebusi.plugin.StoragePlugin
 * </p>
 * 作者：马骏达 初审： 复审：
 * 
 * @param <T>
 */
public abstract class StoragePlugin implements Comparable<StoragePlugin> {

	/**
	 * 获取ID
	 * 
	 * @return ID
	 */
	public final String getId() {
		return getClass().getAnnotation(Component.class).value();
	}

	/**
	 * 获取名称
	 * 
	 * @return 名称
	 */
	public abstract String getName();

	/**
	 * 获取版本
	 * 
	 * @return 版本
	 */
	public abstract String getVersion();

	/**
	 * 获取作者
	 * 
	 * @return 作者
	 */
	public abstract String getAuthor();

	/**
	 * 获取网址
	 * 
	 * @return 网址
	 */
	public abstract String getSiteUrl();

	/**
	 * 获取安装URL
	 * 
	 * @return 安装URL
	 */
	public abstract String getInstallUrl();

	/**
	 * 获取卸载URL
	 * 
	 * @return 卸载URL
	 */
	public abstract String getUninstallUrl();

	/**
	 * 获取设置URL
	 * 
	 * @return 设置URL
	 */
	public abstract String getSettingUrl();

	// /**
	// * 获取是否已安装
	// *
	// * @return 是否已安装
	// */
	// public boolean getIsInstalled() {
	// return pluginConfigService.pluginIdExists(getId());
	// }

	// /**
	// * 获取插件配置
	// *
	// * @return 插件配置
	// */
	// public PluginConfig getPluginConfig() {
	// return pluginConfigService.findByPluginId(getId());
	// }

	/**
	 * 获取是否已启用
	 *
	 * @return 是否已启用
	 */
	public boolean getIsEnabled() {
		// PluginConfig pluginConfig = getPluginConfig();
		// return pluginConfig != null ? pluginConfig.getIsEnabled() : false;
		return true;
	}

	// /**
	// * 获取属性值
	// *
	// * @param name
	// * 属性名称
	// * @return 属性值
	// */
	// public String getAttribute(String name) {
	// PluginConfig pluginConfig = getPluginConfig();
	// return pluginConfig != null ? pluginConfig.getAttribute(name) : null;
	// }

	// /**
	// * 获取排序
	// *
	// * @return 排序
	// */
	// public Integer getOrder() {
	// PluginConfig pluginConfig = getPluginConfig();
	// return pluginConfig != null ? pluginConfig.getOrder() : null;
	// }

	/**
	 * 文件上传
	 * 
	 * @param path
	 *            上传路径
	 * @param file
	 *            上传文件
	 * @param contentType
	 *            文件类型
	 */
	public abstract void upload(String path, File file, String contentType);

	/**
	 * 获取访问URL
	 * 
	 * @param path
	 *            上传路径
	 * @return 访问URL
	 */
	public abstract String getUrl(String path);

	/**
	 * 文件浏览
	 * 
	 * @param path
	 *            浏览路径
	 * @return 文件信息
	 */
	public abstract List<FileInfo> browser(String path);

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		if (this == obj) {
			return true;
		}
		StoragePlugin other = (StoragePlugin) obj;
		return new EqualsBuilder().append(getId(), other.getId()).isEquals();
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37).append(getId()).toHashCode();
	}

	// public int compareTo(StoragePlugin storagePlugin) {
	// return new CompareToBuilder().append(getOrder(),
	// storagePlugin.getOrder())
	// .append(getId(), storagePlugin.getId()).toComparison();
	// }
}
