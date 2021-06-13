package una.com.logic

class Enrollment(ID:Int, Student: Student, course: Course){
    var ID:Int=ID
    var Student: Student =Student
    var course: Course =course
    override fun toString(): String {
        return "$ID $Student $course"
    }
}