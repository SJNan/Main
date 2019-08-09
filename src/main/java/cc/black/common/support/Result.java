package cc.black.common.support;

import cc.black.common.util.JSONUtils;

import java.util.ArrayList;
import java.util.List;

/**
 *  所有返回结果
 * @author xiekaihua
 *
 * @param <T>
 */
public class Result<T> {
	
	private String returnCode;
	
	private String returnMsg;
	
	private Integer dataCount = 0;
	
	private List<T> resultList;
	
	public Result(String returnCode, String returnMsg){
		this.returnCode = returnCode;
		this.returnMsg = returnMsg;
	}
	
	public Result(){
		this.returnCode = Constants.SUCC;
		this.returnMsg = "操作成功！";
	}
	
	public Result(Integer count){
		this.returnCode = Constants.SUCC;
		this.returnMsg = "操作成功！";
		this.dataCount = count;
	}
	
	public Result(T result){
		this.returnCode = Constants.SUCC;
		this.returnMsg = "操作成功！";
		this.dataCount = 1;
		List list = new ArrayList();
		list.add(result);
		setResultList(list);
	}
	
	public Integer getDataCount() {
		return dataCount;
	}

	public void setDataCount(Integer dataCount) {
		this.dataCount = dataCount;
	}

	public List<T> getResultList() {
		return resultList;
	}

	public void setResultList(List<T> resultList) {
		
		this.resultList = resultList;
	}

	public String getReturnCode() {
		return returnCode;
	}

	public void setReturnCode(String returnCode) {
		this.returnCode = returnCode;
	}

	public String getReturnMsg() {
		return returnMsg;
	}

	public void setReturnMsg(String returnMsg) {
		this.returnMsg = returnMsg;
	}

	@Override
	public String toString() {
		try {
			return JSONUtils.obj2json(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.toString();
		
	}
}
