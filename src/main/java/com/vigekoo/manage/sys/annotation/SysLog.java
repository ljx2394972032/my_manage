package com.vigekoo.manage.sys.annotation;

import java.lang.annotation.*;

/**
 * @author ljx
 * @Description: 系统日志注解
 * @date 2017-6-23 15:07
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SysLog {
    String value() default "";
}
