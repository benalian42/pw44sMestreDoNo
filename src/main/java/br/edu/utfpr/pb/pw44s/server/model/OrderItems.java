package br.edu.utfpr.pb.pw44s.server.model; // Ajuste o pacote conforme sua estrutura

import jakarta.persistence.Embeddable;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Entity
@Table(name = "itens_do_pedido")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItems {

    @EmbeddedId
    private ItensDoPedidoId id;

    private BigDecimal price;

    private Integer quantity;

    @Embeddable
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class ItensDoPedidoId implements Serializable {
        private Long orderId;
        private Long productId;
    }
}
