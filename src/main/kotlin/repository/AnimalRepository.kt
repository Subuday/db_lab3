package repository

import models.AnimalDao
import models.DoctorHasAnimalTable
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.transactions.transaction

data class Animal(val id: Int, val nickname: String, val kind: String, val problem: String)

fun AnimalDao.toAnimal() = Animal(id.value, nickname, kind, problem)

fun getAllAnimals(): List<Animal> {
    val animals = mutableListOf<Animal>()
    transaction { AnimalDao.all().toList().map { animals += it.toAnimal() } }
    return animals
}

fun getAnimal(animalId: Int): Animal? {
    var animal: Animal? = null
    transaction { animal = AnimalDao.findById(animalId)?.toAnimal() }
    return animal
}

fun createAnimal(nickname: String, kind: String, problem: String) {
    transaction {
        AnimalDao.new {
            this.nickname = nickname
            this.kind = kind
            this.problem = problem
        }
    }
}

fun updateAnimal(animalId: Int, nickname: String, kind: String, problem: String) {
    transaction {
        AnimalDao.findById(animalId)?.apply {
            this.nickname = nickname
            this.kind = kind
            this.problem = problem
        }
    }
}

fun deleteAnimal(animalId: Int) {
    transaction {
        DoctorHasAnimalTable.deleteWhere { DoctorHasAnimalTable.animalId eq animalId }
        AnimalDao.findById(animalId)?.delete()
    }
}