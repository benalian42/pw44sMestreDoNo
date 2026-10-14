package br.edu.utfpr.pb.pw44s.server.dto;

import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderItemDTO {

    @NotNull
    private Long productId;

    private String productName;

    private BigDecimal price;

    @NotNull
    private Integer quantity;
}
