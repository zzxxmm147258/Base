package com.hibo.bas.coderule.util;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Component;

import com.hibo.bas.coderule.model.CodeModel;
import com.hibo.bas.coderule.model.CodeRule;
import com.hibo.bas.coderule.nume.CodeRuleNume;
import com.hibo.bas.coderule.service.ICodeService;
import com.hibo.bas.util.StrUtil;
import com.hibo.cms.sys.cache.Utils.SysCacheManager;

/** 
 * <p>标题：</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2016年4月19日 下午1:52:33</p>
 * <p>类全名：com.hibo.bas.coderule.util.CodeUtil</p>
 * 作者：周雷
 * 初审：周雷
 * 复审：周雷
 */
@Component
public class CodeUtil {
	private static ICodeService codeService;

	@Resource
	private void setCodeService(ICodeService codeService) {
		CodeUtil.codeService = codeService;
	}

	/**
	 * @功能:获取sql
	 * @描述:
	 * @作者:周雷
	 * @时间:2016年4月19日 下午2:26:53
	 * @param codeModel
	 * @return
	 */
	private static String getSql(CodeModel codeModel) {
		String sql = "select max(" + codeModel.getFild() + ") as " + codeModel.getFild() + " from "
				+ codeModel.getTable();
		if (!StrUtil.isnull(codeModel.getFilter())) {
			sql = sql + " where " + codeModel.getFilter();
		}
		return sql;
	}

	/**
	 * CodeRule[] codeRule = {new CodeRule("HMBWYF",CodeRuleNume.COUSTOM),new CodeRule(CodeRuleNume.DATE_6),new CodeRule(3, CodeRuleNume.INCREASE)};
	 * code = CodeUtil.getRuleCode(new CodeModel("qpd_policy_apply","code",null, codeRule));
	 * @时间:2016年10月14日 下午1:58:39
	 * @param codeModel
	 * @return
	 */
	public static String getRuleCode(CodeModel codeModel){
		try {
			return codeService.getServiceCode(codeModel,0);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public static String getNewCode(CodeModel codeModel) {
		String newCode = "";
		String cacheNname = "CODE_RULE";
		String cacheKey = codeModel.getTable() + "_" + codeModel.getFild();
		String code = StrUtil.obj2str(SysCacheManager.getFromGlobalCache(cacheNname, cacheKey));
		if (StrUtil.isnull(code)) {
			List<Map<String, Object>> codes = codeService.selectCodeList(CodeUtil.getSql(codeModel));
			if (null != codes && codes.size() > 0) {
				if(null!=codes.get(0)){
					Map<String, Object> fldBean = codes.get(0);
					code = StrUtil.obj2str(fldBean.get(codeModel.getFild()));
					//code = StrUtil.isnull2(code)?StrUtil.obj2str(codes.get(0).get(codeModel.getFild().toLowerCase())):code;
					//code = StrUtil.isnull2(code)?StrUtil.obj2str(codes.get(0).get(codeModel.getFild().toUpperCase())):code;
				}
			}
		}
		String date = new SimpleDateFormat("YYYYMMddHHmmss").format(new Date());
		CodeRule[] codes = codeModel.getCodeRule();
		String lastCode = "";
		boolean isEcode = StrUtil.isnull(code);
		boolean isReset = false;
		int lastinc = -1;
		for (int i = codes.length - 1; i >= 0; i--) {
			CodeRule codeRule = codes[i];
			if (null == codeRule) {
				continue;
			}
			String value = codeRule.getValue();
			int k = StrUtil.isnull(value) ? codeRule.getNum() : value.length();
			if (codeRule.getType().equals(CodeRuleNume.INCREASE)) {
				lastinc = i;
				break;
			} else {
				lastCode = getOtherLStr(lastCode, codeRule.getType(), value, k, date);
			}
		}
		for (int i = 0; i < codes.length; i++) {
			CodeRule codeRule = codes[i];
			if (null == codeRule) {
				continue;
			}
			String value = codeRule.getValue();
			int k = StrUtil.isnull(value) ? codeRule.getNum() : value.length();
			if (codeRule.getType().equals(CodeRuleNume.INCREASE)) {
				if (StrUtil.isnull(code)) {
					newCode = newCode + CodeUtil.getNum(k, null, true);
					if (lastinc == i) {
						break;
					}
				} else {
					int fc = newCode.length();
					if (lastinc == i) {
						int al = code.length();
						int lc = lastCode.length();
						String ss = code.substring(fc, al - lc);
						isReset = isEcode||(!StrUtil.isnull(newCode)&&!code.startsWith(newCode));
						isReset = isReset||isEcode||(!StrUtil.isnull(lastCode)&&!code.endsWith(lastCode));
						ss = isReset?null:ss;
						k = isReset?k:(al - fc - lc);
						newCode = newCode + CodeUtil.getNum(k, ss, false);
						break;
					} else {
						String ss = code.substring(fc, fc + k);
						newCode = newCode + CodeUtil.getNum(k, ss, true);
					}
				}
			} else {
				newCode = CodeUtil.getOtherFStr(newCode, codeRule.getType(), value, k, date);
			}
		}
		newCode = newCode + lastCode;
		int time = 60;
		if (!StrUtil.isnull(newCode)) {
			SysCacheManager.putToGlobalCache(cacheNname, cacheKey, newCode, time);
		}
		return newCode;
	}

	private static String getNum(int k, String num, boolean isCut) {
		if (StrUtil.isnull(num)) {
			num = "1";
			for (int i = 0; i < k-1; i++) {
				num = "0" + num;
			}
		} else {
			num = new BigDecimal(num).add(BigDecimal.ONE).toString();
			int l = num.length();
			while (k > l) {
				l++;
				num = '0' + num;
			}
			num = isCut && (l > k) ? num.substring(l - k) : num;
		}
		return num;
	}

	private static String getOtherFStr(String str, CodeRuleNume codeRule, String value, int k, String date) {
		if (codeRule.equals(CodeRuleNume.RANDOM)) {
			str = str + StrUtil.randomStr(k);
		} else if (codeRule.equals(CodeRuleNume.COUSTOM)) {
			str = str + value;
		} else if (codeRule.equals(CodeRuleNume.DATE_14)) {
			str = str + date;
		} else if (codeRule.equals(CodeRuleNume.DATE_8)) {
			str = str + date.substring(0, 8);
		} else if (codeRule.equals(CodeRuleNume.DATE_6)) {
			str = str + date.substring(2, 8);
		} else if (codeRule.equals(CodeRuleNume.DAY_2)) {
			str = str + date.substring(6, 8);
		} else if (codeRule.equals(CodeRuleNume.MONTH_2)) {
			str = str + date.substring(4, 6);
		} else if (codeRule.equals(CodeRuleNume.YEAR_2)) {
			str = str + date.substring(2, 4);
		} else if (codeRule.equals(CodeRuleNume.YEAR_4)) {
			str = str + date.substring(0, 4);
		}
		return str;
	}

	private static String getOtherLStr(String lastCode, CodeRuleNume codeRule, String value, int k, String date) {
		String str = "";
		if (codeRule.equals(CodeRuleNume.RANDOM)) {
			str = StrUtil.randomStr(k) + str;
		} else if (codeRule.equals(CodeRuleNume.COUSTOM)) {
			str = value + str;
		} else if (codeRule.equals(CodeRuleNume.DATE_14)) {
			str = date + str;
		} else if (codeRule.equals(CodeRuleNume.DATE_8)) {
			str = date.substring(0, 8) + str;
		} else if (codeRule.equals(CodeRuleNume.DATE_6)) {
			str = date.substring(2, 8) + str;
		} else if (codeRule.equals(CodeRuleNume.DAY_2)) {
			str = date.substring(6, 8) + str;
		} else if (codeRule.equals(CodeRuleNume.MONTH_2)) {
			str = date.substring(4, 6) + str;
		} else if (codeRule.equals(CodeRuleNume.YEAR_2)) {
			str = date.substring(2, 4) + str;
		} else if (codeRule.equals(CodeRuleNume.YEAR_4)) {
			str = date.substring(0, 4) + str;
		}
		return str;
	}
}
