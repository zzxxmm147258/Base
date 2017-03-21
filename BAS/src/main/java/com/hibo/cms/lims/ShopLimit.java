package com.hibo.cms.lims;

import com.hibo.bas.dbutil.SqlDatabase;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.sys.cache.Utils.SysCacheManager;
import com.hibo.cms.util.Envparam;

/** <p>标题：数据权限，店铺权限控制</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年12月25日 下午6:28:19</p>
 * <p>类全名：com.hibo.cms.lims.ShopLimit</p>
 * 作者：cgh
 * 初审：
 * 复审：
 */
public class ShopLimit {
	
	public static String getShopLim(){
		return getShopLim(null, null);
	}
	
	public static String getShopLim(String shopCol){
		return getShopLim(null, shopCol);
	}
	
	/**
	 * 
	 * @param userId  用户 ID
	 * @param shopCol 权限字段，当有别名时，需要指定 由 m.shop_id
	 * @return
	 */
	public static String getShopLim(String userId, String shopCol){
		if (StrUtil.isnull(userId)){
			userId = Envparam.getUserId();
			if (userId == null)
				return " 1=2 ";
		}
		if (StrUtil.isnull(shopCol))
			shopCol = "shop_id";
		
		String shopLim = StrUtil.obj2str(SysCacheManager.get("shopLimit",userId));
		if (!StrUtil.isnull(shopLim)){
			if ("no".equals(shopLim))
				return " 1=3 ";
			if ("yes".equals(shopLim))
				return " 1=1 ";
			return shopCol + " in ("+shopLim+")";
		}

		String shopList = "";
		if (Envparam.isAdmin() || Envparam.isMaintain()){
			//超级用户 和运维人员
			shopLim = " 1=1 ";
			shopList = "yes";
		}else{
			String sql = "select shop_id from bas_user_shop where userid='"+userId+"'";
			Object[] shopArr = SqlDatabase.sqlSelect2(sql, null);
			if (shopArr == null || shopArr.length == 0){
				shopLim = " 1=3 ";
				shopList = "no";
			}else{
				for (int i=0; i<shopArr.length; i++){
					if (i==0)
						shopList = "'"+shopArr[i]+"'";
					else
						shopList = ",'"+shopArr[i]+"'";
				}
				shopLim = shopCol + " in ("+shopList+")";
			}
		}
		SysCacheManager.put("shopLimit",userId, shopList, 3600);
		return shopLim;
	}
}
