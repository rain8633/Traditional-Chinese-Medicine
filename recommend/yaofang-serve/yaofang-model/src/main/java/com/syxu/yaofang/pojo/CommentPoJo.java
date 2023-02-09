package com.syxu.yaofang.pojo;

import com.syxu.yaofang.model.Comment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentPoJo extends Comment {

    private String userName;

    private String dname;

    private String img;

    private String pic;


}
