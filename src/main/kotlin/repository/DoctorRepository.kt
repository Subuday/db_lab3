package repository

import models.*
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction
import java.util.*

data class Doctor(
    val id: Int,
    val hospital: Hospital,
    val name: String,
    val surname: String,
    val specialization: String,
    val birthdate: Date
)

fun getAllDoctors(): List<Doctor> {
    val doctors = mutableListOf<Doctor>()
    transaction {
        DoctorTable.innerJoin(HospitalTable)
            .selectAll()
            .forEach {
                doctors += Doctor(
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
            }
    }
    return doctors
}

fun getDoctor(doctorId: Int): Doctor? {
    var doctor: Doctor? = null
    transaction {
        DoctorTable.innerJoin(HospitalTable)
            .select { DoctorTable.id eq doctorId }
            .firstOrNull()
            ?.let {
                doctor = Doctor(
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
            }
    }
    return doctor
}

fun createDoctor(hospitalId: Int, name: String, surname: String, specialization: String, birthdate: Date) {
    transaction {
        val hospital = HospitalDao.findById(hospitalId) ?: return@transaction
        DoctorDao.new {
            this.hospitalId = hospital.id
            this.name = name
            this.surname = surname
            this.specialization = specialization
            this.birthdate = birthdate.toInstant()
        }
    }
}

fun updateDoctor(
    doctorId: Int,
    hospitalId: Int,
    name: String,
    surname: String,
    specialization: String,
    birthdate: Date
) {
    transaction {
        val hospital = HospitalDao.findById(hospitalId) ?: return@transaction
        DoctorDao.findById(doctorId)?.apply {
            this.hospitalId = hospital.id
            this.name = name
            this.surname = surname
            this.specialization = specialization
            this.birthdate = birthdate.toInstant()
        }
    }
}

fun deleteDoctor(doctorId: Int) {
    transaction {
        DoctorHasAnimalTable.deleteWhere { DoctorHasAnimalTable.doctorId eq doctorId }
        DoctorDao.findById(doctorId)?.delete()
    }
}