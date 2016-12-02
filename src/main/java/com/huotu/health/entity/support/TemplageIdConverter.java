package com.huotu.health.entity.support;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/1.
 */
@Converter(autoApply = true)
public class TemplageIdConverter implements AttributeConverter<List<TemplateId>,String> {
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String convertToDatabaseColumn(List<TemplateId> templateIds) {
        if (templateIds == null)
            return null;
        try {
            TemplateId[] conditions = templateIds.toArray(new TemplateId[templateIds.size()]);
            return objectMapper.writeValueAsString(conditions);
        } catch (JsonProcessingException e) {
            throw new IllegalStateException("Broken JSON", e);
        }
    }

    @Override
    public List<TemplateId> convertToEntityAttribute(String s) {
        if (StringUtils.isEmpty(s))
            return null;
        try {
            List<TemplateId> templateIds=new ArrayList<>();
            JsonNode node = objectMapper.readTree(s);
            for (JsonNode n:node){
                TemplateId templateId  = objectMapper.treeToValue(n,TemplateId.class);
                templateIds.add(templateId);
            }
            return templateIds;
        } catch (IOException e) {
            throw new IllegalStateException("Broken JSON", e);
        }
    }
}
