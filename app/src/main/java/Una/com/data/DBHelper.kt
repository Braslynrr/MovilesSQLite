package Una.com.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import Una.com.logic.Enrollment
import Una.com.logic.Course
import Una.com.logic.Student

class DBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_STUDENT ($ID INTEGER PRIMARY KEY " +
                ",$NAME TEXT,$SURNAME TEXT,$AGE INTEGER)")
        db.execSQL("CREATE TABLE $TABLE_COURSE ($AGE INTEGER PRIMARY KEY " +
                ",$DESCIPRION TEXT,$CREDIT INTEGER)")
        db.execSQL("CREATE TABLE $TABLE_ENROLLMENT ($ID INTEGER PRIMARY KEY " +
                "AUTOINCREMENT,$STUDENT INTEGER,$COURSE INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int){
        db.execSQL("DROP TABLE IF EXISTS $TABLE_STUDENT")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_COURSE")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_ENROLLMENT")
        onCreate(db)
    }

    fun insertStudent(student: Student): Long {
        val db= this.writableDatabase
        val content= ContentValues()
        content.put(ID,student.ID)
        content.put(NAME,student.Nombre)
        content.put(SURNAME,student.Apellido)
        content.put(AGE,student.Edad)
        return db.insert(TABLE_STUDENT, null, content)
    }
    fun insertCourse(course: Course){
        val db= this.writableDatabase
        val content= ContentValues()
        content.put(ID,course.ID)
        content.put(DESCIPRION,course.Descripcion)
        content.put(CREDIT,course.Creditos)
        db.insert(TABLE_COURSE, null, content)
    }
    fun insertEnrollment(enrollment: Enrollment) {
        val db= this.writableDatabase
        val content= ContentValues()
        content.put(STUDENT,enrollment.student.ID)
        content.put(COURSE,enrollment.course.ID)
        db.insert(TABLE_COURSE, null, content)
    }
    fun updateStudent(student: Student):Boolean{
        val db= this.writableDatabase
        val content= ContentValues()
        content.put(ID,student.ID)
        content.put(NAME,student.Nombre)
        content.put(SURNAME,student.Apellido)
        content.put(AGE,student.Edad)
        db.update(TABLE_STUDENT, content, "ID = ?", arrayOf(student.ID.toString()))
        return true
    }
    fun updateCourse(course: Course):Boolean{
        val db= this.writableDatabase
        val content= ContentValues()
        content.put(DESCIPRION,course.Descripcion)
        content.put(CREDIT,course.Creditos)
        db.update(TABLE_COURSE, content, "ID = ?", arrayOf(course.ID.toString()))
        return true
    }
    fun updateEnrollment(enrollment: Enrollment):Boolean{
        val db= this.writableDatabase
        val content= ContentValues()
        content.put(STUDENT,enrollment.student.ID)
        content.put(COURSE,enrollment.course.ID)
        db.update(TABLE_ENROLLMENT, content, "ID = ?", arrayOf(enrollment.ID.toString()))
        return true
    }
    fun deleteStudent(student: Student):Int{
        val db= this.writableDatabase
        return db.delete(TABLE_STUDENT,"ID = ?", arrayOf(student.ID.toString()))
    }
    fun deleteCourse(course: Course):Int{
        val db= this.writableDatabase
        return db.delete(TABLE_COURSE,"ID=?", arrayOf(course.ID.toString()))
    }
    fun deleteEnrollment(enrollment: Enrollment):Int{
        val db= this.writableDatabase
        return db.delete(TABLE_ENROLLMENT,"ID= ? ", arrayOf(enrollment.ID.toString()))
    }
    fun findStudent(ID:String): Student {
        val db= this.writableDatabase
        val res:Cursor = db.rawQuery("Select * from $TABLE_STUDENT where ID=$ID ",null)
        res.moveToFirst()
        return Student(res.getString(0).toInt(),res.getString(1),res.getString(2),res.getString(3).toInt())
    }

    fun findCourse(ID: String): Course {
        val db= this.writableDatabase
        val res = db.rawQuery("Select * from $TABLE_COURSE where ID=$ID ",null)
        res.moveToFirst()
        return Course(res.getInt(0),res.getString(1),res.getInt(2))
    }

    fun findEnrollment(ID: String): Enrollment {
        val db= this.writableDatabase
        val res = db.rawQuery("Select * from $TABLE_ENROLLMENT where ID=$ID ",null)
        res.moveToFirst()
        return Enrollment(res.getInt(0),findStudent(res.getString(1)),findCourse(res.getString(2)))
    }


    fun allStudents():List<Student>{
        val list:MutableList<Student> = mutableListOf()
        val db= this.writableDatabase
        val res = db.rawQuery("Select * from $TABLE_STUDENT",null)
        if(res.moveToFirst()){
            list.add(Student(res.getInt(0),res.getString(1),res.getString(2),res.getInt(3)))
            while (res.moveToNext()) {
                list.add(Student(res.getInt(0),res.getString(1),res.getString(2),res.getInt(3)))
            }
        }
        return list
    }

        fun allCourses():List<Course>{
            val list:MutableList<Course> = mutableListOf()
            val db= this.writableDatabase
            val res = db.rawQuery("Select * from $TABLE_COURSE",null)
            if(res.moveToFirst()){
                while (res.moveToNext()) {
                    list.add(Course(res.getInt(0),res.getString(1),res.getInt(2)))
                }
            }
            return list
        }

        fun allEnrollment():List<Enrollment>{
            val list:MutableList<Enrollment> = mutableListOf()
            val db= this.writableDatabase
            val res = db.rawQuery("Select * from $TABLE_ENROLLMENT",null)
            if(res.moveToFirst()){
                while (res.moveToNext()) {
                    list.add(Enrollment(res.getInt(0),findStudent( res.getString(1)) ,findCourse(res.getString(2))))
                }
            }
            return list
        }
    companion object {
        val DATABASE_NAME = "students.db"
        val TABLE_STUDENT = "student"
        val TABLE_COURSE= "course"
        val TABLE_ENROLLMENT="enrollment "
        val NAME="NOMBRE"
        val SURNAME="APELLIDO"
        val AGE="EDAD"
        val ID="ID"
        val DESCIPRION="DESCRIPCION"
        val STUDENT= "STUDENT"
        val COURSE = "COURSE"
        val CREDIT="CREDITOS"
    }
}