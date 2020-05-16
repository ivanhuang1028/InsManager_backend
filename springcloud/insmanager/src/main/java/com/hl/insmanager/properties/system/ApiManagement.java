package com.hl.insmanager.properties.system;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author ivan.huang
 * 系统API管理
 */
@Data
@ConfigurationProperties(prefix = "api-management")
@Component
public class ApiManagement {
    private List<String> excludePathPatterns;
}
