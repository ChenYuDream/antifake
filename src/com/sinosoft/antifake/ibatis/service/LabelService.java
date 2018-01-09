package com.sinosoft.antifake.ibatis.service;

import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.sinosoft.antifake.helpers.CacheUtil;
import com.sinosoft.antifake.ibatis.model.LabelScrap;
import com.sinosoft.antifake.ibatis.model.LableDetail;
import com.sinosoft.antifake.ibatis.model.LableSummary;
import com.sinosoft.antifake.ibatis.model.Tblrecipient;


public interface LabelService {

	String insert(LableSummary lableSummary);

	List querySummaryData(String recipientNo, String orderNo, String trackingNo, String reelNo);

	String insertDetail(LableDetail lableDetail);

	List queryDetailData(String reelNo, String orderNo);

	String addDataStatus(LableSummary lableSummary);

	String addScrapData(LabelScrap lableDetail,String userName) throws Exception;

	void bacthInsert(List<Map<String, String>> list);

	void insertDetailByBatch(List<Map<String, String>> insertList);

	void insertLabelOrderByBatch(List<Map<String, String>> list);

	void insertProductOrderByBatch(List<Map<String, String>> list);
	/**
	 * 
	 * @Description: 批量插入SFBD工厂防伪数据
	 * @param list
	 * @author wangxueqiang
	 * @date 2017年12月7日 下午4:15:52
	 *
	 */
	void insertSfbdSecurityByBatch(List<Map<String, String>> list);

	void approve(String serialNumber);

	void batchApprove(MultipartFile file) throws IllegalStateException, IOException;

	void productOrderRestBatchInsert(List<Map<String, String>> insertList);

	int removeProductOrder(String productionDate, String plantNo,
			String userName);

	String getRecipientNoByUserName(String userName);

	void updateUsedSerialNumberToRecipient(String recipientNo, int n);

	void updateStockSerialNumberToRecipient(String recipientNo, int total);

	void updateDeteleSerialNumberToRecipient(String recipientNo, int n);

	void checkWarnVakyeAndSendEmail(String recipientNo);
	
	void labelSummaryInsert(Map<String,String> map) throws SQLException;

	void bacthPOInsert(List<Map<String, String>> list);

	void bacthMOInsert(List<Map<String, String>> list);

	List<Tblrecipient> getTblrecipientByAnd(Tblrecipient tblrecipient,
			Object object);

	void countKPI()throws ParseException;

	void addDetailData(Map<String, String> map) throws SQLException;

	HashMap<String, String> putRecepitInfo();

	void POInsert(HashMap<String, String> map);

	void MOInsert(HashMap<String, String> map);
	
}
