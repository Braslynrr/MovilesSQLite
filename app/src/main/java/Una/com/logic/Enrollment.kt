package Una.com.logic

class Enrollment(ID:Int, student: Student, course: Course){
    var ID:Int=ID
    var student: Student =student
    var course: Course =course
    override fun toString(): String {
        return "$ID $student $course"
    }
}