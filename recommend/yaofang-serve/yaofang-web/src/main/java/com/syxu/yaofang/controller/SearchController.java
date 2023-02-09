package com.syxu.yaofang.controller;


import com.syxu.yaofang.model.Page;
import com.syxu.yaofang.model.Result;
import com.syxu.yaofang.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class SearchController {

    @Autowired
    private SearchService searchService;


    @GetMapping("/insertES")
    public Boolean parse() throws Exception {
        return searchService.insertES();
    }

    @GetMapping("/search/{keyword}/{pageNum}/{pageSize}")
    public Result search(@PathVariable("keyword") String keyword,@PathVariable("pageNum") Integer pageNum,@PathVariable("pageSize") Integer pageSize) throws IOException {
        Page<Map<String,Object>> list = searchService.searchHignLightPage(keyword, pageNum, pageSize);
        if(list!=null){
            return  Result.success(list);
        }
        else return Result.error();
    }
}
