package com.example.demo.exception;

import com.example.demo.enums.ResultEnums;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.lang.annotation.DeclareAnnotation;

/**
 * sell项目相关异常
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SellException extends RuntimeException {
    private Integer code;

    public SellException(ResultEnums resultEnums) {
        super(resultEnums.getMsg());
        this.code = resultEnums.getCode();
    }

    public SellException(Integer code, String msg) {
        super(msg);
        this.code = code;
    }
}
