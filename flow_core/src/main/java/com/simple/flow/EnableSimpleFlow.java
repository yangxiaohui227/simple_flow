package com.simple.flow;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(ParseFlowXmlService.class)
public @interface EnableSimpleFlow {
}
