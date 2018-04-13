package com.pigeon.spring.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 封装分页查询
 * @author liufeng
 * @date 2017-4-5
 */
public class QueryResult<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
     * 默认为第一页.
     */
    public static final int DEFAULT_PAGE_NUM = 1;

    /**
     * 默认每页记录数(15).
     */
    public static final int DEFAULT_PAGE_SIZE = 15;

    /**
     * 最大每页记录数(100).
     */
    public static final int MAX_PAGE_SIZE = 100;
	
	private int totalCount;							//总记录数
	private int totalPage;							//总页数
	private int currentPage = DEFAULT_PAGE_NUM;		//当前页数
	private int pageSize = DEFAULT_PAGE_SIZE;		//每页记录数
	private List<T> resultList;						//查询结果集
	
	public QueryResult(){
		
	}
	
	public QueryResult(int currentPage,int pageSize,int totalCount,List<T> resultList){
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		this.totalCount = totalCount;
		this.resultList = resultList;
	}
	
	/**
     * 计算总页数 .
     * 
     * @param totalCount
     *            总记录数.
     * @param pageSize
     *            每页记录数.
     * @return totalPage 总页数.
     */
    public static int countTotalPage(int totalCount, int pageSize) {
        if (totalCount % pageSize == 0) {
            // 刚好整除
            return totalCount / pageSize;
        } else {
            // 不能整除则总页数为：商 + 1
            return totalCount / pageSize + 1;
        }
    }
	
    /**
     * 校验当前页currentPage
     * @param totalCount
     * @param pageSize
     * @param currentPage
     * @return
     */
    public static int checkCurrentPage(int totalCount,int pageSize,int currentPage){
    	int totalPage = countTotalPage(totalCount,pageSize);
    	if(currentPage > totalPage){
    		if(totalPage < 1){
    			return 1;
    		}
    		return totalPage;
    	}else if(currentPage < 1){
    		return 1;
    	}else{
    		return currentPage;
    	}
    }
    
    /**
     * 校验输入的每页记录数是否合法
     * @param pageSize
     * @return
     */
    public static int checkPageSize(int pageSize){
    	if(pageSize > QueryResult.MAX_PAGE_SIZE){
    		return QueryResult.MAX_PAGE_SIZE;
    	}else if(pageSize < QueryResult.DEFAULT_PAGE_SIZE){
    		return QueryResult.DEFAULT_PAGE_SIZE;
    	}else{
    		return pageSize;
    	}
    }
    
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	public int getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public List<T> getResultList() {
		return resultList;
	}
	public void setResultList(List<T> resultList) {
		this.resultList = resultList;
	}
	
	
	
}
