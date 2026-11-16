package br.edu.utfpr.pb.pw44s.server.config;

import org.hibernate.boot.model.naming.CamelCaseToUnderscoresNamingStrategy;
import org.hibernate.boot.model.naming.Identifier;
import org.hibernate.engine.jdbc.env.spi.JdbcEnvironment;

/**
 * Mantém colunas em snake_case (padrão do Spring), mas preserva o nome das tabelas
 * exatamente como declarado em @Table (ex.: "itensDoPedido").
 */
public class TableNameKeepingNamingStrategy extends CamelCaseToUnderscoresNamingStrategy {

    @Override
    public Identifier toPhysicalTableName(Identifier name, JdbcEnvironment context) {
        return name;
    }
}
