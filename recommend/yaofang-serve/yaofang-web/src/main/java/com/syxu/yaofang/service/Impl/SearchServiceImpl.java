package com.syxu.yaofang.service.Impl;

import com.alibaba.fastjson.JSON;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.syxu.yaofang.mapper.SearchMapper;
import com.syxu.yaofang.model.Page;
import com.syxu.yaofang.pojo.BingLiPoJo;
import com.syxu.yaofang.pojo.SearchPoJo;
import com.syxu.yaofang.service.SearchService;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.core.CountRequest;
import org.elasticsearch.client.core.CountResponse;
import org.elasticsearch.common.text.Text;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class SearchServiceImpl implements SearchService {


    @Autowired
    private RestHighLevelClient restHighLevelClient;

    @Autowired
    private SearchMapper searchMapper;


    @Override
    public Boolean insertES() throws IOException {
        List<SearchPoJo> searchPoJos = searchMapper.querySearchInfo();
        //把查询的数据放入es中
        BulkRequest bulkRequest = new BulkRequest();//批量插入
        bulkRequest.timeout("10m");

        for(int i=0;i<searchPoJos.size();i++){
            bulkRequest.add(new IndexRequest("zyinfo")
                    .source(JSON.toJSONString(searchPoJos.get(i)), XContentType.JSON));
        }
        BulkResponse bulkResponse = restHighLevelClient.bulk(bulkRequest, RequestOptions.DEFAULT);
        return !bulkResponse.hasFailures();
    }

    @Override
    public Page<Map<String, Object>> searchHignLightPage(String keyword, Integer pageNum, Integer pageSize) throws IOException {
        //条件搜索
        SearchRequest searchRequest = new SearchRequest("zyinfo");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        //分页
        if(pageNum==1){
            sourceBuilder.from(pageNum-1);
            sourceBuilder.size(pageSize);
        }else{
            sourceBuilder.from((pageNum-1)*pageSize);
            sourceBuilder.size(pageSize);
        }

        //精准匹配
//        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name",keyword);

        MatchPhraseQueryBuilder termQueryBuilder = QueryBuilders.matchPhraseQuery("name",keyword);
        //获取查询到的数量
        CountRequest countRequest = new CountRequest("zyinfo");
        countRequest.query(termQueryBuilder);
        CountResponse response = restHighLevelClient.count(countRequest, RequestOptions.DEFAULT);

        sourceBuilder.query(termQueryBuilder);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));

        //高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();
        highlightBuilder.field("name");
        highlightBuilder.requireFieldMatch(false);//关闭多个关键字高亮
        highlightBuilder.preTags("<span style='color:red'>");
        highlightBuilder.postTags("</span>");
        sourceBuilder.highlighter(highlightBuilder);
        searchRequest.source(sourceBuilder);
        SearchResponse searchResponse = restHighLevelClient.search(searchRequest, RequestOptions.DEFAULT);
        ArrayList<Map<String,Object>> list = new ArrayList<>();
        //解析结果
        for (SearchHit documentFields : searchResponse.getHits().getHits()) {
            //解析高亮字段
            //获取
            Map<String, HighlightField> highlightFields = documentFields.getHighlightFields();
            HighlightField title = highlightFields.get("name");
            Map<String, Object> sourceAsMap = documentFields.getSourceAsMap();//原来的结果
            //解析高亮的字段，将原来的字段换为我们高亮的字段
            if(title!=null){
                Text[] fragments = title.fragments();
                String newTitle="";
                for(Text text : fragments){
                    newTitle += text;
                }
                sourceAsMap.put("name",newTitle);//高亮字段替换掉原来的内容
            }
            list.add(sourceAsMap);
        }
        PageInfo<Map<String,Object>> pageInfo=new PageInfo<>(list);
        pageInfo.setTotal(response.getCount());
        pageInfo.setPageNum(pageNum);
        pageInfo.setPageSize(pageSize);
        return new Page<>(pageInfo.getPageNum(),pageInfo.getPageSize(),
               pageInfo.getList(),pageInfo.getPages(),pageInfo.getTotal());
    }
}
