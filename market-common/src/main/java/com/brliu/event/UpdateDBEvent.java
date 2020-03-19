package com.brliu.event;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.context.ApplicationEvent;

@Data
@EqualsAndHashCode(callSuper = true)
public class UpdateDBEvent extends ApplicationEvent {

    private static final long serialVersionUID = 2361213830243670418L;
    private Integer id;
    private String message;

    public UpdateDBEvent(Integer id, String message, Object source) {
        super(source);
        this.id = id;
        this.message = message;
    }

}
