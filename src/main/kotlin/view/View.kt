package view

fun printMainMenu() {
    println("Main menu. Choose your options: ")
    println("1.Read list of entities");
    println("2.Read entity");
    println("3.Create entity")
    println("4.Update entity")
    println("5.Delete entity")
    println("6. Work with many to many connection")
    println("0.Exit")
}


fun printOptionsMenu() {
    println("Choose an entity:")
    println("1.Animal")
    println("2.Hospital")
    println("3.Doctor")
}

fun printManyToManyOptionsMenu() {
    println("Many to many menu. Choose your options: ")
    println("1.Read all")
    println("2.Read animals of doctor")
    println("3.Read doctors of animals")
    println("4. Delete")
}

fun printEnterId() {
    println("Enter id: ")
}

fun printNotExist() {
    println("Not exist")
}

fun printEnterAnimalNickname() {
    println("Enter nickname: ")
}

fun printEnterAnimalKind() {
    println("Enter kind: ")
}

fun printEnterAnimalProblem() {
    println("Enter problem: ")
}

fun printEnterHospitalAverageScore() {
    println("Enter average score: ")
}

fun printEnterHospitalAddress() {
    println("Enter address: ")
}

fun printEnterDoctorHospitalId() {
    println("Enter hospital id: ")
}

fun printEnterDoctorName() {
    println("Enter name: ")
}

fun printEnterDoctorSurname() {
    println("Enter surname: ")
}

fun printEnterDoctorSpecialization() {
    println("Enter specialization: ")
}

fun printEnterDoctorBirthdate() {
    println("Enter birthdate (yyyy-MM-dd): ")
}

fun printEnterAnimalId() {
    println("Enter animalId: ")
}

fun printEnterDoctorId() {
    println("Enter doctorId: ")
}