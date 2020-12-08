package models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object HospitalTable : IntIdTable("hospital") {
    val averageScore = float("average_score")
    val address = varchar("address", 250)
}

class HospitalDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<HospitalDao>(HospitalTable)
    var averageScore by HospitalTable.averageScore
    var address by HospitalTable.address
}