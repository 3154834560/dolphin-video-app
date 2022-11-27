package com.example.dolphin.infrastructure.structs;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * @author 王景阳
 * @date 2022/11/24 15:41
 */
@EqualsAndHashCode
@Data
@Accessors(chain = true)
public class ToolListView {

    private int imageId;

    private String toolName;

    private Class<?> nextClass;

    private List<Integer> childLayoutIds;
}
