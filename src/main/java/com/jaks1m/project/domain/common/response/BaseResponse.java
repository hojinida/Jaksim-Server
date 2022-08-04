package com.jaks1m.project.domain.common.response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
@Builder
public class BaseResponse<T>{
    private int status;
    private String code;
    private String message;
    private T body;

    public BaseResponse(int status, String message, String code,T body){
        this.message=message;
        this.code=code;
        this.status=status;
        this.body=body;
    }
}
