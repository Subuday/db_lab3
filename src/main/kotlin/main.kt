import controller.start
import org.jetbrains.exposed.sql.Database

fun main(args: Array<String>) {
    Database.connect(
        "jdbc:postgresql://localhost:5432/postgres", driver = "org.postgresql.Driver",
        user = "postgres", password = "postgres"
    )
    start()
}