package com.huotu.health.entity.support;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Administrator on 2016/12/1.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TemplateId {
    @JsonProperty("idx")
    private long idx;
}
