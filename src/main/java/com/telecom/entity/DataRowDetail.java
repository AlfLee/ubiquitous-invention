package com.telecom.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

/**
 * 类hibernate 自动更新表结构或者创建表
 * 数据 行详细 表(保存了所有行数据，一行即为库里的一条数据)
 * @author Administrator
 *
 */
@Table(name="data_rows_detail")
public class DataRowDetail extends BaseModel {

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
	 * 行数据（一行的数据）
	 */
	@Column(name = "row_datas",type = MySqlTypeConstant.TEXT)
	private String rowDatas;

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

	public String getRowDatas() {
		return rowDatas;
	}

	public void setRowDatas(String rowDatas) {
		this.rowDatas = rowDatas;
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
