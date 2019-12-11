package com.brliu.configuration;

import com.brliu.annotation.ConditionSwitch;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.util.Map;

public class CorsCondition implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        Map<String, Object> attributes = metadata.getAnnotationAttributes(ConditionSwitch.class.getName());
        return (Boolean) attributes.get("isOpen");
    }
}
