package br.edu.utfpr.pb.pw44s.server.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Table(name = "produtos")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column( length = 89 )
    private String name;

    @NotNull
    @Column( length = 1024 )
    private String description;

    @NotNull
    private BigDecimal price;

    @NotNull
    private String imageUrl;

    @ManyToOne
    @JoinColumn( name = "categoryId", referencedColumnName = "id" )
    private Long categoryId;
}