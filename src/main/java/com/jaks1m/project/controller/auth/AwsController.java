package com.jaks1m.project.controller.auth;

import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/aws")
public class AwsController {
    @GetMapping("/status")
    @ApiOperation(value = "Aws Health Check")
    public ResponseEntity<Void> healthCheck(){
        return ResponseEntity.status(200).build();
    }
}
