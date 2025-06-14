package ru.itk.kafka.partitions.dto;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder(setterPrefix = "with")
public class WebLogMessage {
    private Long id;

    private String message;

}
