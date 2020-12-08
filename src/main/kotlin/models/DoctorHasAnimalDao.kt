package models

import org.jetbrains.exposed.sql.Table

object DoctorHasAnimalTable : Table("doctor_has_animal") {
    val doctorId = reference("doctor_id", DoctorTable.id)
    val animalId = reference("animal_id", AnimalTable.id)
    override val primaryKey: PrimaryKey
        get() = PrimaryKey(DoctorTable.id, AnimalTable.id)
}