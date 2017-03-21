package com.hibo.cms.table;

import java.io.InputStream;

import com.hibo.bas.file.FileSystem;
import com.hibo.bas.ms.poi.XlsReadDataSet;
import com.hibo.bas.util.*;

/** <p>标题：用Excel表结构文件生成表结构描述</p>
 * <p>功能： </p>
 * <p>版权： Copyright © 2015 HIBO</p>
 * <p>公司: 北京瀚铂科技有限公司</p>
 * <p>创建日期：2015年11月19日 上午11:22:42</p>
 * <p>类全名：com.hibo.cms.table.ExcelCreatTable</p>
 * 作者：cgh
 * 初审：
 * 复审：
 */
public class ExcelCreatTable {

	public static void readExcel(){
		String filePath = "file:///D:";
		String fileName = "活动报名-签到表结构.xlsx";
		String sheetList = "活动报名签到表attr";
		boolean bCreat = true;   //true 新建表，false 只生成字段
		//只生成字段时，指定日期，只生成这天以后 新增的字段
		java.util.Date nowDate = DateUtil.toDate("2015-11-17");
		int beginRow = 2;
		String[] sheetName = StrUtil.splitString(sheetList, ',');
		try{
			for(int k=0;k < sheetName.length;k++){
				final FileSystem fs = FileSystem.getFileSystem(filePath);
				final InputStream is = fs.openFile(fileName);
				final XlsReadDataSet readDataSet = new XlsReadDataSet(null, is, true, sheetName[k], null, 0x20);
				int rowCount = readDataSet.getRowCount();
				int count = 0;
				String tableName = StrUtil.obj2str(readDataSet.getValue(0,1));
				Object tableComm = readDataSet.getValue(0,5);
				String xml = "表："+tableName +" 新增字段";
				if (bCreat){
					tableName = tableName.toLowerCase();
					xml = "\t\t<table name=\""+tableName+"\" comment=\""+tableComm+"\">";
				}
				for (int i = beginRow; i < rowCount; i++)
				{
					if (!bCreat){
						java.util.Date date1 = DateUtil.toDate(readDataSet.getValue(i,9));
						if (date1 == null || DateUtil.diffDate(date1,nowDate) < 0){
							continue;
						}
					}
					String colName = StrUtil.obj2str(readDataSet.getValue(i, 0));
					if (colName == null || colName.trim().length() == 0)
						break;
					colName = colName.toLowerCase();
					String com = StrUtil.obj2str(readDataSet.getValue(i, 1));
					String type = StrUtil.obj2str(readDataSet.getValue(i, 2));
					type = type.toLowerCase();
					int size = StrUtil.obj2int(readDataSet.getValue(i, 3),-1);
					int dec = StrUtil.obj2int(readDataSet.getValue(i, 4),-1);
					String key = StrUtil.obj2str(readDataSet.getValue(i, 5));
					String isNull = StrUtil.obj2str(readDataSet.getValue(i, 6));
//					/<field name="id" type="varchar" size="32" decimal="0"  primarykey="Y" isnull="Y" comment="ID主键"/>
					xml += "\n\t\t\t<field name=\""+colName+"\" type=\""+type+"\"";
					if (size >= 0)
						xml += " size=\""+size+"\"";
					if (dec >= 0)
						xml += " decimal=\""+dec+"\"";
					if ("Y".equals(key) || "y".equals(key))
						xml += " primarykey=\"Y\"";
					if ("Y".equals(isNull) || "y".equals(isNull))
						xml += " isnull=\"Y\"";
					xml += " comment=\""+com+"\"/>";
				}
				if (bCreat)
					xml += "\n\t\t</table>";
				System.err.println(xml);
				System.err.println();
			}
			
		}catch(Exception ex){
			ex.printStackTrace();
		} 		
	}
	
	public static void main(String[] args) throws Exception{
		readExcel();
	}
}
