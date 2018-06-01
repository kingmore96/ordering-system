package com.example.demo.utils;

import com.example.demo.VO.ResultVO;
import com.example.demo.enums.ResultEnums;

/**
 * 生成ResultVO的工具类
 */
public class ResultVOUtil {

    /**
     * 成功，且需要返回Data
     * @param data
     * @return
     */
    public static ResultVO success(Object data){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultEnums.SUCCESS.getCode());
        resultVO.setData(data);
        resultVO.setMsg(ResultEnums.SUCCESS.getMsg());
        return resultVO;
    }

    /**
     * 成功，不需要返回data
     * @return
     */
    public static ResultVO success(){
        return success(null);
    }

    /**
     * 失败，不返回数据
     * @return
     */
    public static ResultVO fail(){
        ResultVO resultVO = new ResultVO();
        resultVO.setCode(ResultEnums.FAIL.getCode());
        resultVO.setMsg(ResultEnums.FAIL.getMsg());
        resultVO.setData(null);
        return resultVO;
    }

    public static ResultVO exception(Integer code,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(null);
        resultVO.setMsg(msg);
        resultVO.setCode(code);
        return resultVO;
    }
}
