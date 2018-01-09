package com.sinosoft.antifake.ibatis.action;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.sinosoft.antifake.ibatis.model.Tblserialnumber;
import com.sinosoft.antifake.ibatis.service.TblserialnumberService;

@Controller
@RequestMapping(value="/test")
public class TestAction {
	Logger logg = LoggerFactory.getLogger(TestAction.class);
	@Autowired
	TblserialnumberService serialnumberService;
	
	@RequestMapping(value="/do.do")
	public void test(String parameterData){
		/*logg.info(String.valueOf(userdao!=null));*/
		Gson gson = new Gson();
		List<Tblserialnumber> list = gson.fromJson(parameterData, new TypeToken<List<Tblserialnumber>>() {
		}.getType());
		Tblserialnumber tblserialnumber = list.get(0);
		logg.info(String.valueOf(serialnumberService.restInsert(tblserialnumber)!=null));
	}
}
