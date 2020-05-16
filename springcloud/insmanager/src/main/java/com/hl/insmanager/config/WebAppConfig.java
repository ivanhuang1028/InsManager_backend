package com.hl.insmanager.config;

import com.hl.insmanager.config.convert.StringToDateConverter;
import com.hl.insmanager.properties.system.ApiManagement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import java.util.List;

@Component
@Configuration
public class WebAppConfig extends WebMvcConfigurerAdapter {

    @Autowired
    private ApiManagement apiManagement;
    /**
     * 提前注入 jwt验证拦截器bean
     * @return
     */
    @Bean
    public HandlerInterceptor getAuthInterceptor() {
        return new AuthInterceptor();
    }

    /**
     * 加入格式化转换器
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToDateConverter());
    }

    /**
     * 引入拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
//        注册自定义的拦截器，添加拦截路径和排除拦截路径
        InterceptorRegistration interceptorRegistration=registry.addInterceptor(getAuthInterceptor()).addPathPatterns("/**");
        List<String> excludePathPatters = apiManagement.getExcludePathPatterns();
        if(excludePathPatters!=null && excludePathPatters.size()>0){
            interceptorRegistration.excludePathPatterns(excludePathPatters.toArray(new String[0]));
        }
//        super.addInterceptors(registry);
    }

}
