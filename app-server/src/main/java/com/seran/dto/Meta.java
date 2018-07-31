package com.seran.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Meta implements Serializable {

    private static final long serialVersionUID = 5563597607379483396L;

    private Integer total_count;
    private Integer pageable_count;
    private boolean is_end;
    
}
