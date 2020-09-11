package com.weixin.VO;


import lombok.Data;

/*
* 请求返回的最外层对象
* */
@Data
public class ResultVO<T> {

    /*错误码*/
    private Integer code;

    /*提示信息*/
    private String msg;

    /*具体的内容*/
    private T data;
}
