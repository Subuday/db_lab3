package repository

import models.AnimalTable
import models.DoctorHasAnimalTable
import models.DoctorTable
import models.HospitalTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

fun getAllDoctorsWithAnimals(): Set<Pair<Doctor, Animal>> {
    val doctorsWithAnimals = mutableSetOf<Pair<Doctor, Animal>>()
    transaction {
        DoctorHasAnimalTable
            .innerJoin(DoctorTable)
            .innerJoin(AnimalTable)
            .innerJoin(HospitalTable)
            .selectAll()
            .forEach {
                val animal = Animal(
                    id = it[AnimalTable.id].value,
                    nickname = it[AnimalTable.nickname],
                    kind = it[AnimalTable.kind],
                    problem = it[AnimalTable.problem]
                )

                val doctor = Doctor(
                    id = it[DoctorTable.id].value,
                    hospital = Hospital(
                        id = it[HospitalTable.id].value,
                        averageScore = it[HospitalTable.averageScore],
                        address = it[HospitalTable.address]
                    ),
                    name = it[DoctorTable.name],
                    surname = it[DoctorTable.surname],
                    specialization = it[DoctorTable.specialization],
                    birthdate = Date.from(it[DoctorTable.birthdate])
                )

                doctorsWithAnimals += (doctor to animal)
            }
    }
    return doctorsWithAnimals
}

fun getAnimalsOfDoctor(doctorId: Int): Pair<Doctor, List<Animal>>? {
    var doctor: Doctor? = null
    val animals = mutableListOf<Animal>()
    transaction {
        DoctorHasAnimalTable
            .innerJoin(DoctorTable)
            .innerJoin(AnimalTable)
            .innerJoin(HospitalTable)
            .select { DoctorHasAnimalTable.doctorId eq doctorId }
            .map {
                val animal = Animal(
                    id = it[AnimalTable.id].value,
                    nickname = it[AnimalTable.nickname],
                    kind = it[AnimalTable.kind],
                    problem = it[AnimalTable.problem]
                )

                val doctor = Doctor(
                    id = it[DoctorTable.id].value,
                    hospital = Hospital(
                        id = it[HospitalTable.id].value,
                        averageScore = it[HospitalTable.averageScore],
                        address = it[HospitalTable.address]
                    ),
                    name = it[DoctorTable.name],
                    surname = it[DoctorTable.surname],
                    specialization = it[DoctorTable.specialization],
                    birthdate = Date.from(it[DoctorTable.birthdate])
                )

                doctor to animal
            }
            .forEach { (_doctor, animal) ->
                doctor = _doctor
                animals += animal
            }
    }
    return if(doctor != null) doctor!! to animals else null
}

fun getDoctorsOfAnimal(animalId: Int): Pair<Animal, List<Doctor>>? {
    var animal: Animal? = null
    val doctors = mutableListOf<Doctor>()
    transaction {
        DoctorHasAnimalTable
            .innerJoin(DoctorTable)
            .innerJoin(AnimalTable)
            .innerJoin(HospitalTable)
            .select { DoctorHasAnimalTable.animalId eq animalId }
            .map {
                val animal = Animal(
                    id = it[AnimalTable.id].value,
                    nickname = it[AnimalTable.nickname],
                    kind = it[AnimalTable.kind],
                    problem = it[AnimalTable.problem]
                )

                val doctor = Doctor(
                    id = it[DoctorTable.id].value,
                    hospital = Hospital(
                        id = it[HospitalTable.id].value,
                        averageScore = it[HospitalTable.averageScore],
                        address = it[HospitalTable.address]
                    ),
                    name = it[DoctorTable.name],
                    surname = it[DoctorTable.surname],
                    specialization = it[DoctorTable.specialization],
                    birthdate = Date.from(it[DoctorTable.birthdate])
                )

                animal to doctor
            }
            .forEach { (_animal, doctor) ->
                animal = _animal
                doctors += doctor
            }
    }
    return if(animal != null) animal!! to doctors else null
}

fun deleteDoctorHasAnimal(doctorId: Int, animalId: Int) {
    transaction {
        addLogger(StdOutSqlLogger)
        DoctorHasAnimalTable
            .deleteWhere { (DoctorHasAnimalTable.doctorId eq doctorId) and (DoctorHasAnimalTable.animalId eq animalId) }
    }
}