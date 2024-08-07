//package org.wine.userservice.common.config
//
//import io.r2dbc.postgresql.PostgresqlConnectionConfiguration
//import io.r2dbc.postgresql.PostgresqlConnectionFactory
//import io.r2dbc.spi.ConnectionFactory
//import org.springframework.context.annotation.Bean
//import org.springframework.context.annotation.Configuration
//import org.springframework.data.r2dbc.config.AbstractR2dbcConfiguration
//import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories
//
//
//@Configuration
//@EnableR2dbcRepositories
//class DatabaseConfig : AbstractR2dbcConfiguration() {
//    @Bean
//    override fun connectionFactory(): ConnectionFactory {
//        return PostgresqlConnectionFactory(
//            PostgresqlConnectionConfiguration.builder()
//                .host("ls-0c680c2adbc7c152941c70c39a18994bd30480ef.cpd3bfmvavpa.ap-northeast-2.rds.amazonaws.com")
//                .port(5432)
//                .username("dbmasteruser")
//                .password("testwinemall")
//                .database("userservice")
//                .build()
//        )
//    }
//}