package com.weixin.Utils;

import com.weixin.VO.ResultVO;

public class ResultVOUtil {

    public static ResultVO success(Object object){

        ResultVO resultVO=new ResultVO();
        resultVO.setCode(0);
        resultVO.setMsg("成功");
        resultVO.setData(object);
        return resultVO;
    }


    public static ResultVO success(){
        return null;
    }

    public static ResultVO error(Integer code,String msg ){
        ResultVO resultVO=new ResultVO();
        resultVO.setCode(1);
        resultVO.setMsg("失败");
        return  resultVO;
    }
}
