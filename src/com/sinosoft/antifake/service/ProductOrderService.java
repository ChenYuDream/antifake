package com.sinosoft.antifake.service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springside.modules.persistence.DynamicSpecifications;
import org.springside.modules.persistence.SearchFilter;

import com.sinosoft.antifake.entity.ProductOrder;
import com.sinosoft.antifake.helpers.DateHelper;
import com.sinosoft.antifake.helpers.excel.ExcelWriter;
import com.sinosoft.antifake.helpers.excel.labelScrap.LabelScrapBuilder;
import com.sinosoft.antifake.helpers.excel.productOrder.ProductOrderBuilder;
import com.sinosoft.antifake.repository.ProductOrderDao;

//Spring Bean的标识.
@Component
// 默认将类中的所有public函数纳入事务管理.
@Transactional(readOnly = true)
public class ProductOrderService {

	@Autowired
	private ProductOrderDao productOrderDao;

	@PersistenceContext
	protected EntityManager em;
	
	

	/**
	 * 保存查询日志.
	 */
	@Transactional(readOnly = false)
	public void saveQueryLog(ProductOrder entity) {
		productOrderDao.save(entity);
	}

	/**
	 * 查询日志列表
	 */
	public Page<ProductOrder> getQueryLogList(Map<String, Object> searchParams, int pageNumber, int pageSize,
			String sortType) {

		PageRequest pageRequest = buildPageRequest(pageNumber, pageSize, sortType);
		Specification<ProductOrder> spec = buildSpecification(searchParams);
		return productOrderDao.findAll(spec, pageRequest);
	}
	
	// 读取数据库并导出报表
	public void exportXLS(Map<String, Object> searchParams, HttpServletResponse response)
			throws UnsupportedEncodingException, ParseException {

		// 1.创建一个 workbook
		HSSFWorkbook workbook = new HSSFWorkbook();
		// 2.创建一个 worksheet
		HSSFSheet worksheet = workbook.createSheet("log");
		// 3.创建title,data,headers
		ProductOrderBuilder.buildReport(worksheet);
		// 4.填充数据
		ProductOrderBuilder.fillReport(worksheet, getAllQueryLogList(searchParams));
		// 5.设置reponse参数
		String fileName = URLEncoder.encode(("antifake-产品订单管理 -"), "utf-8") + DateHelper.getCurrentDate() + ".xls";
		response.setHeader("Content-Disposition", "inline; filename=" + fileName);
		response.setContentType("application/vnd.ms-excel");
		// 6. 输出流
		ExcelWriter.write(response, worksheet);

	}

	// 私有方法开始---------------

	/**
	 * Excel 导出查询日志列表
	 */
	private List<ProductOrder> getAllQueryLogList(Map<String, Object> searchParams) {

		Specification<ProductOrder> spec = buildSpecification(searchParams);
		return productOrderDao.findAll(spec, new Sort(Direction.DESC, "createTime"));
	}

	/**
	 * 创建分页请求.
	 */
	private PageRequest buildPageRequest(int pageNumber, int pagzSize, String sortType) {

		Sort sort = null;
		if ("auto".equals(sortType)) {
			sort = new Sort(Direction.DESC, "createTime");
		} else if ("lableNo".equals(sortType)) {
			sort = new Sort(Direction.ASC, "lableNo");
		}
		return new PageRequest(pageNumber - 1, pagzSize, sort);
	}

	/**
	 * 创建动态查询条件组合.
	 */
	private Specification<ProductOrder> buildSpecification(Map<String, Object> searchParams) {
		Map<String, SearchFilter> filters = SearchFilter.parse(searchParams);
		// filters.put("user.id", new SearchFilter("user.id", Operator.EQ, userId));
		Specification<ProductOrder> spec = DynamicSpecifications.bySearchFilter(filters.values(), ProductOrder.class);
		return spec;
	}
}
