package com.syxu.yaofang.service;

import com.syxu.yaofang.model.Page;
import com.syxu.yaofang.pojo.SearchPoJo;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public interface SearchService {

    Boolean insertES() throws IOException;

 Page<Map<String,Object>> searchHignLightPage(String keyword, Integer pageNum, Integer pageSize) throws IOException;
}
