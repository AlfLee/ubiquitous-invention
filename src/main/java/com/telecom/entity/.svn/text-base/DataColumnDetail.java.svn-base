package com.telecom.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

/**
 * 类hibernate 自动更新表结构或者创建表
 * 数据 列详细 表
 * @author Administrator
 *
 */
@Table(name="data_column_detail")
public class DataColumnDetail extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * id
	 */
	@Column(name = "id",type = MySqlTypeConstant.VARCHAR,length = 111,isKey=true)
	private String id;
	
	/**
	 * 数据id
	 */
	@Column(name = "data_id",type = MySqlTypeConstant.VARCHAR,length = 111)
	private String dataId;
	
	/**
	 * 第1行第n列（即：第n列的变量名）
	 */
	@Column(name = "data_column_name",type = MySqlTypeConstant.VARCHAR,length = 111)
	private String dataColumnName;
	
	/**
	 * 该列的所有数据
	 */
	@Column(name = "column_datas",type = MySqlTypeConstant.TEXT)
	private String columnDatas;

	/**
	 * 修改时间
	 */
	@Column(name = "modify_time",type = MySqlTypeConstant.VARCHAR,length = 111)
	private String modifyTime;
	
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDataColumnName() {
		return dataColumnName;
	}

	public void setDataColumnName(String dataColumnName) {
		this.dataColumnName = dataColumnName;
	}

	public String getColumnDatas() {
		return columnDatas;
	}

	public void setColumnDatas(String columnDatas) {
		this.columnDatas = columnDatas;
	}

	public String getDataId() {
		return dataId;
	}

	public void setDataId(String dataId) {
		this.dataId = dataId;
	}

	public String getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(String modifyTime) {
		this.modifyTime = modifyTime;
	}

	
}
