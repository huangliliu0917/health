package com.huotu.health.service.impl;

import com.huotu.health.common.DateUtils;
import com.huotu.health.entity.Message;
import com.huotu.health.model.MessageListModel;
import com.huotu.health.service.MessageService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
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
            model.setPutAway(message.isPutAway());
            model.setSummary(message.getSummary());
            model.setPictureUrl(message.getPictureUrl());
            model.setTitle(message.getTitle());
            model.setEnabled(message.isEnabled());
            model.setDate(message.getDate());
            model.setStick(message.isStick());
            model.setPutAwayDate(message.getPutAwayDate());
            model.setPutAwayDateFormat(message.getPutAwayDate()==null?"":DateUtils.fromToday(message.getPutAwayDate()));
            models.add(model);
        });
        return models;
    }

    private String DateFormat(Date date){

        return "";
    };
}
