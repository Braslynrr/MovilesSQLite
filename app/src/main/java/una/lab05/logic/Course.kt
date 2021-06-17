package una.lab05.logic

class Course(ID:Int,Descripcion:String,Creditos:Int){

    var ID:Int=ID
    var Descripcion:String=Descripcion
    var Creditos:Int=Creditos
    override fun toString(): String {
        return "$ID $Descripcion: $Creditos creditos"
    }
}