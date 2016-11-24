package com.huotu.health.service;

import com.huotu.health.entity.Message;
import com.huotu.health.model.MessageListModel;

import java.util.List;

/**
 * 表单服务
 * Created by slt on 2016/11/17.
 */
public interface MessageService {
    List<MessageListModel> getMessagesModel(List<Message> messages);
}
