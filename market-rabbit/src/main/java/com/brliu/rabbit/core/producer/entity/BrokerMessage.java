package com.brliu.rabbit.core.producer.entity;

import com.brliu.rabbit.api.Message;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;


/**
 * 消息记录表实体映射
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BrokerMessage implements Serializable {
	
	private static final long serialVersionUID = 7447792462810110841L;

	private String messageId;

    private Message message;

    private Integer tryCount = 0;

    private String status;

    private Date nextRetry;

    private Date createTime;

    private Date updateTime;

}