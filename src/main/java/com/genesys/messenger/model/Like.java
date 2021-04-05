package com.genesys.messenger.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document
public class Like {
    @Id
    private String id;
    private String likedBy;
    private String messageId;
    @CreatedDate
    private Instant created;
    @LastModifiedDate
    private Instant updated;

}
