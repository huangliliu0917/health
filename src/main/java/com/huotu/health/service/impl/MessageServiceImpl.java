package com.huotu.health.service.impl;

import com.huotu.health.entity.Message;
import com.huotu.health.model.MessageListModel;
import com.huotu.health.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 资讯服务实现
 * Created by Administrator on 2016/11/17.
 */
@Service
public class MessageServiceImpl implements MessageService {
    @Override
    public List<MessageListModel> getMessagesModel(List<Message> messages) {
        List<MessageListModel> models=new ArrayList<>();
        messages.forEach(message -> {
            MessageListModel model=new MessageListModel();
            model.setId(message.getId());
            model.setTitle(message.getTitle());
            model.setEnabled(message.isEnabled());
            model.setDate(message.getDate());
            models.add(model);
        });
        return models;
    }
}
