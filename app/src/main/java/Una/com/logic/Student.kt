package Una.com.logic

class Student(ID:Int, Nombre:String, Apellido:String, Edad:Int){
    var ID:Int= ID
    var Nombre:String=Nombre
    var Apellido:String=Apellido
    var Edad:Int=Edad
    override fun toString(): String {
        return "$ID $Nombre $Apellido $Edad"
    }
}