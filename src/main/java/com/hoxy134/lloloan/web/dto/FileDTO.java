package com.hoxy134.lloloan.web.dto;

import lombok.*;

import java.io.Serializable;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class FileDTO implements Serializable {

    private String name;

    private String url;

}
