package com.seran.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meta {

    private Integer total_count;
    private Integer pageable_count;
    private boolean is_end;
    
}
