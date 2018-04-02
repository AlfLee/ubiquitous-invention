package com.telecom.entity;

import com.gitee.sunchenbin.mybatis.actable.annotation.Column;
import com.gitee.sunchenbin.mybatis.actable.annotation.Table;
import com.gitee.sunchenbin.mybatis.actable.constants.MySqlTypeConstant;

/**
 * 类hibernate 自动更新表结构或者创建表
 * 数据基本信息表
 * @author Administrator
 *
 */
@Table(name="data_info")
public class DataInfo extends BaseModel {

	private static final long serialVersionUID = 1L;
	
	/**
	 * 数据id
	 */
	@Column(name = "id",type = MySqlTypeConstant.VARCHAR,length = 111,isKey=true)
	private String id;
	
	/**
	 * 数据名称
	 */
	@Column(name = "data_name",type = MySqlTypeConstant.VARCHAR, length=111)
	private String dataName;
	
	/**
	 * 数据用途（1：分析，2：训练）
	 */
	@Column(name = "data_use_way",type = MySqlTypeConstant.INT)
	private int dataUseWay;
	
	/**
	 * 数据目的（1：诊断，2：预测）
	 */
	@Column(name = "data_target",type = MySqlTypeConstant.INT)
	private int dataTarget;

	/**
	 * 导入方式（1：文件导入，2：数据库导入，3：实时导入）
	 */
	@Column(name = "import_way",type = MySqlTypeConstant.INT, isNull=false)
	private int importWay;
	
	/**
	 * 导入时间（当前时间）
	 */
	@Column(name = "import_time",type = MySqlTypeConstant.BIGINT)
	private long importTime;
	
	/**
	 * 修改时间
	 */
	@Column(name = "modify_time",type = MySqlTypeConstant.BIGINT)
	private long modifyTime;
	
	/**
	 * 所属设备
	 */
	@Column(name = "equipment",type = MySqlTypeConstant.VARCHAR, length=111)
	private String equipment;

	/**
	 * 该属性用于接收字符串型时间
	 */
	private String import_time;
	
	public DataInfo() {}
	
	public DataInfo(String dataName, int dataUseWay, int dataTarget,int importWay, String equipment) {
		super();
		this.dataName = dataName;
		this.dataUseWay = dataUseWay;
		this.dataTarget = dataTarget;
		this.importWay = importWay;
		this.importTime = System.currentTimeMillis();
		this.equipment = equipment;
	}
	
	public DataInfo(String id,String dataName) {
		super();
		this.id=id;
		this.dataName = dataName;

	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getDataName() {
		return dataName;
	}

	public void setDataName(String dataName) {
		this.dataName = dataName;
	}

	public int getDataUseWay() {
		return dataUseWay;
	}
    
	public void setDataUseWay(int dataUseWay) {
		this.dataUseWay = dataUseWay;
	}
	
	public int getDataTarget() {
		return dataTarget;
	}

	public void setDataTarget(int dataTarget) {
		this.dataTarget = dataTarget;
	}
	public int getImportWay() {
		return importWay;
	}

	public void setImportWay(int importWay) {
		this.importWay = importWay;
	}

	public long getImportTime() {
		return importTime;
	}

	public void setImportTime(long importTime) {
		this.importTime = importTime;
	}

	public String getEquipment() {
		return equipment;
	}

	public void setEquipment(String equipment) {
		this.equipment = equipment;
	}

	public String getImport_time() {
		return import_time;
	}

	public void setImport_time(String import_time) {
		this.import_time = import_time;
	}

	public long getModifyTime() {
		return modifyTime;
	}

	public void setModifyTime(long modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	
}
