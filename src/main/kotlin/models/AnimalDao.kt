package models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable

object AnimalTable : IntIdTable("animal") {
    val nickname = varchar("nickname", 250)
    val kind = varchar("kind", 250)
    val problem = varchar("problem", 250)
}

class AnimalDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<AnimalDao>(AnimalTable)
    var nickname by AnimalTable.nickname
    var kind by AnimalTable.kind
    var problem by AnimalTable.problem
}