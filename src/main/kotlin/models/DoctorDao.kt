package models

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.`java-time`.timestamp

object DoctorTable : IntIdTable("doctor") {
    val hospitalId = reference("hospital_id", HospitalTable.id)
    val name = varchar("name", 250)
    val surname = varchar("surname", 250)
    val specialization = varchar("specialization", 250)
    val birthdate = timestamp("birthdate")
}

class DoctorDao(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<DoctorDao>(DoctorTable)
    var hospitalId by DoctorTable.hospitalId
    var name by DoctorTable.name
    var surname by DoctorTable.surname
    var specialization by DoctorTable.specialization
    var birthdate by DoctorTable.birthdate
}