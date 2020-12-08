package repository

import models.*
import org.jetbrains.exposed.sql.StdOutSqlLogger
import org.jetbrains.exposed.sql.addLogger
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

data class Hospital(val id: Int, val averageScore: Float, val address: String)

fun HospitalDao.toHospital() = Hospital(id.value, averageScore, address)

fun getAllHospitals(): List<Hospital> {
    val hospitals = mutableListOf<Hospital>()
    transaction { HospitalDao.all().map { hospitals += it.toHospital() } }
    return hospitals
}

fun getHospital(hospitalId: Int): Hospital? {
    var hospital: Hospital? = null
    transaction { hospital = HospitalDao.findById(hospitalId)?.toHospital() }
    return hospital
}

fun createHospital(averageScore: Float, address: String) {
    transaction {
        HospitalDao.new {
            this.averageScore = averageScore
            this.address = address
        }
    }
}

fun updateHospital(hospitalId: Int, averageScore: Float, address: String) {
    transaction {
        HospitalDao.findById(hospitalId)?.apply {
            this.averageScore = averageScore
            this.address = address
        }
    }
}

fun deleteHospital(hospitalId: Int) {
    transaction {
        DoctorDao
            .find { DoctorTable.hospitalId eq hospitalId }
            .onEach { DoctorHasAnimalTable.deleteWhere { DoctorHasAnimalTable.doctorId eq it.id } }
            .onEach { it.delete() }

        HospitalTable.deleteWhere { HospitalTable.id eq hospitalId }
    }
}