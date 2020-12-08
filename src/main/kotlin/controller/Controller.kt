package controller

import repository.*
import view.*
import java.text.SimpleDateFormat
import java.util.*

fun start() {
    var currentMenuItem = -1
    while (currentMenuItem != 0) {
        printMainMenu()
        currentMenuItem = readLine()!!.toIntOrNull() ?: return

        when (currentMenuItem) {
            1 -> getAllEntities()
            2 -> getEntity()
            3 -> createEntity()
            4 -> updateEntity()
            5 -> deleteEntity()
            6 -> startWorkWithManyToMany()
        }
    }
}

fun getAllEntities() {
    printOptionsMenu()

    when (readLine()?.toIntOrNull()) {
        1 -> getAllAnimals().onEach { println(it) }
        2 -> getAllHospitals().onEach { println(it) }
        3 -> getAllDoctors().onEach { println(it) }
        4 -> getAllDoctorsWithAnimals().onEach { println(it) }
    }
}

fun getEntity() {
    printOptionsMenu()
    val option = readLine()?.toIntOrNull() ?: return
    printEnterId()
    val id = readLine()?.toIntOrNull() ?: return

    when (option) {
        1 -> getAnimal(id).also { if(it != null) println(it) else printNotExist() }
        2 -> getHospital(id).also { if(it != null) println(it) else printNotExist() }
        3 -> getDoctor(id).also { if(it != null) println(it) else printNotExist() }
    }
}

fun createEntity() {
    printOptionsMenu()
    when (readLine()?.toIntOrNull()) {
        1 -> {
            printEnterAnimalNickname()
            val nickname = readLine() ?: return
            printEnterAnimalKind()
            val kind = readLine() ?: return
            printEnterAnimalProblem()
            val problem = readLine() ?: return
            createAnimal(nickname, kind, problem)
        }
        2 -> {
            printEnterHospitalAverageScore()
            val averageScore = readLine()?.toFloatOrNull() ?: return
            printEnterHospitalAddress()
            val address = readLine() ?: return
            createHospital(averageScore, address)
        }
        3 -> {
            printEnterDoctorHospitalId()
            val hospitalId = readLine()?.toIntOrNull() ?: return
            printEnterDoctorName()
            val name = readLine() ?: return
            printEnterDoctorSurname()
            val surname = readLine() ?: return
            printEnterDoctorSpecialization()
            val specialization = readLine() ?: return
            printEnterDoctorBirthdate()
            val birthdateString = readLine() ?: return
            val birthdate: Date
            try {
                birthdate = SimpleDateFormat("yyyy-MM-dd").parse(birthdateString)
            } catch (e: Exception) { return }
            createDoctor(hospitalId, name, surname, specialization, birthdate)
        }
    }
}

fun updateEntity() {
    printOptionsMenu()

    when (readLine()?.toIntOrNull()) {
        1 -> {
            printEnterId()
            val id = readLine()?.toIntOrNull() ?: return
            printEnterAnimalNickname()
            val nickname = readLine() ?: return
            printEnterAnimalKind()
            val kind = readLine() ?: return
            printEnterAnimalProblem()
            val problem = readLine() ?: return
            updateAnimal(id, nickname, kind, problem)
        }
        2 -> {
            printEnterId()
            val id = readLine()?.toIntOrNull() ?: return
            printEnterHospitalAverageScore()
            val averageScore = readLine()?.toFloatOrNull() ?: return
            printEnterHospitalAddress()
            val address = readLine() ?: return
            updateHospital(id, averageScore, address)
        }
        3 -> {
            printEnterId()
            val id = readLine()?.toIntOrNull() ?: return
            printEnterDoctorHospitalId()
            val hospitalId = readLine()?.toIntOrNull() ?: return
            printEnterDoctorName()
            val name = readLine() ?: return
            printEnterDoctorSurname()
            val surname = readLine() ?: return
            printEnterDoctorSpecialization()
            val specialization = readLine() ?: return
            printEnterDoctorBirthdate()
            val birthdateString = readLine() ?: return
            val birthdate: Date
            try {
                birthdate = SimpleDateFormat("yyyy-MM-dd").parse(birthdateString)
            } catch (e: Exception) { return }
            updateDoctor(id, hospitalId, name, surname, specialization, birthdate)
        }
    }
}

fun deleteEntity() {
    printOptionsMenu()
    printOptionsMenu()
    val option = readLine()?.toIntOrNull() ?: return
    printEnterId()
    val id = readLine()?.toIntOrNull() ?: return

    when (option) {
        1 -> deleteAnimal(id)
        2 -> deleteHospital(id)
        3 -> deleteDoctor(id)
    }
}

fun startWorkWithManyToMany() {
    printManyToManyOptionsMenu()
    when(readLine()?.toIntOrNull()) {
        1 -> getAllDoctorsWithAnimals().forEach { println(it) }
        2 -> readAnimalsOfDoctor()
        3 -> readDoctorsOfAnimal()
        4 -> delete()
    }
}


fun readAnimalsOfDoctor() {
    printEnterDoctorId()
    val doctorId = readLine()?.toIntOrNull() ?: return
    getAnimalsOfDoctor(doctorId)?.let { println(it) }
}

fun readDoctorsOfAnimal() {
    printEnterAnimalId()
    val animalId = readLine()?.toIntOrNull() ?: return
    getDoctorsOfAnimal(animalId)?.let { println(it) }
}

fun delete() {
    printEnterAnimalId()
    val animalId = readLine()?.toIntOrNull() ?: return
    printEnterDoctorId()
    val doctorId = readLine()?.toIntOrNull() ?: return
    deleteDoctorHasAnimal(doctorId, animalId)
}