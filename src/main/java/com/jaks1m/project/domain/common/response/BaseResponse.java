package com.jaks1m.project.domain.common.response;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter @Setter
@NoArgsConstructor
public class BaseResponse<T>{
    private int status;
    private String message;
    private T body;
    @Builder
    public BaseResponse(int status, String message,T body){
        this.message=message;
        this.status=status;
        this.body=body;
    }
}
