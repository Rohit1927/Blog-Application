package com.myblog9.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentResponse {

        private List<CommentDto> content;
        private int pageNo;
        private int pageSize;
        private int totalPages;
        private int totalElements;
        private boolean last;
    }

