package io.proj4.ezgas.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.sql.Connection
import java.sql.DriverManager
import java.sql.ResultSet

@Configuration
@Profile("dev")
class DevConfig {

    @Bean
    fun createStoredProcedures() {
        DriverManager.getConnection("jdbc:h2:mem:EzGas", "EzGas", "").run {
            createStatement().run {
                execute("CREATE ALIAS findIdsByLocation FOR \"io.proj4.ezgas.config.DevConfig.findIdsByLocation\" ")
                close()
            }

            close()
        }
    }

    companion object {
        @JvmStatic
        fun findIdsByLocation(conn: Connection, latitude: Double, longitude: Double, range: Float): ResultSet {
            return conn.prepareStatement("SELECT id FROM Station").executeQuery()
        }
    }
}