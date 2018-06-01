package com.example.demo.handle;

import com.example.demo.VO.ResultVO;
import com.example.demo.exception.SellException;
import com.example.demo.utils.ResultVOUtil;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ControllerAOP {

    @ExceptionHandler(SellException.class)
    public ResultVO handleSellException(SellException e){
        ResultVO exception = ResultVOUtil.exception(e.getCode(), e.getMessage());
        return exception;
    }
}
