package com.sinosoft.antifake.web.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 记录导出
 *
 * @author yu_chen
 * @create 2018-01-03 17:05
 **/
@Controller
@RequestMapping("admin/export")
public class ExportController {


    @RequestMapping("page")
    public String toExportPage(){
        return "admin/exportRecord/exportSoaList";
    }
}
