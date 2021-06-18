package una.lab05.data

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.lifecycle.MutableLiveData
import una.lab05.logic.Enrollment
import una.lab05.logic.Course
import una.lab05.logic.Student

class DBHelper(context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {
    val Slist = MutableLiveData<List<Student>>()
    val Clist = MutableLiveData<List<Course>>()
    val Elist = MutableLiveData<List<Enrollment>>()
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("CREATE TABLE $TABLE_STUDENT ($ID INTEGER PRIMARY KEY " +
                ",$NAME TEXT,$SURNAME TEXT,$AGE INTEGER)")
        db.execSQL("CREATE TABLE $TABLE_COURSE ($ID INTEGER PRIMARY KEY " +
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

    fun insertStudent(Student: Student): Long {
        val db= this.writableDatabase
        val content= ContentValues()
        content.put(ID,Student.ID)
        content.put(NAME,Student.Nombre)
        content.put(SURNAME,Student.Apellido)
        content.put(AGE,Student.Edad)
        val out = db.insert(TABLE_STUDENT, null, content)
        if (out.toInt()!=-1){
            Slist.value= allStudents()
        }
        return out
    }
    fun insertCourse(course: Course):Long{
        val db= this.writableDatabase
        val content= ContentValues()
        content.put(ID,course.ID)
        content.put(DESCIPRION,course.Descripcion)
        content.put(CREDIT,course.Creditos)
        val out=db.insert(TABLE_COURSE, null, content)
        if(out.toInt()!=-1)
           Clist.value=allCourses()
        return out
    }
    fun insertEnrollment(enrollment: Enrollment):Long{
        val db= this.writableDatabase
        val content= ContentValues()
        content.put(STUDENT,enrollment.Student.ID)
        content.put(COURSE,enrollment.course.ID)
        val out=db.insert(TABLE_ENROLLMENT, null, content)
        if (out.toInt()!=-1)
            Elist.value=allEnrollment()
        return out
    }
    fun updateStudent(Student: Student):Int{
        val db= this.writableDatabase
        val content= ContentValues()
        content.put(ID,Student.ID)
        content.put(NAME,Student.Nombre)
        content.put(SURNAME,Student.Apellido)
        content.put(AGE,Student.Edad)
        val out =db.update(TABLE_STUDENT, content, "ID = ?", arrayOf(Student.ID.toString()))
        if (out!=-1){
            Slist.value= allStudents()
        }
        return out
    }
    fun updateCourse(course: Course):Int{
        val db= this.writableDatabase
        val content= ContentValues()
        content.put(DESCIPRION,course.Descripcion)
        content.put(CREDIT,course.Creditos)
        val out =db.update(TABLE_COURSE, content, "ID = ?", arrayOf(course.ID.toString()))
        if (out!=-1){
            Slist.value= allStudents()
        }
        return out
    }
    fun updateEnrollment(enrollment: Enrollment):Boolean{
        val db= this.writableDatabase
        val content= ContentValues()
        content.put(STUDENT,enrollment.Student.ID)
        content.put(COURSE,enrollment.course.ID)
        db.update(TABLE_ENROLLMENT, content, "ID = ?", arrayOf(enrollment.ID.toString()))
        return true
    }
    fun deleteStudent(Student: Student):Int{
        val db= this.writableDatabase
        return db.delete(TABLE_STUDENT,"ID = ?", arrayOf(Student.ID.toString()))
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
        val res:Cursor = db.rawQuery("Select * from $TABLE_STUDENT where ID=$ID",null)
        res.moveToFirst()
        var student=Student(res.getString(0).toInt(),res.getString(1),res.getString(2),res.getString(3).toInt())
        res.close()
        return student
    }

    fun findCourse(ID: String): Course {
        val db = this.writableDatabase
        val res = db.rawQuery("Select * from $TABLE_COURSE where ID=$ID ", null)
        res.moveToFirst()
        var course = Course(res.getInt(0), res.getString(1), res.getInt(2))
        res.close()
        return course
    }

    fun findEnrollment(ID: String): Enrollment {
        val db= this.writableDatabase
        val res = db.rawQuery("Select * from $TABLE_ENROLLMENT where ID=$ID ",null)
        res.moveToFirst()
        var enrollment=Enrollment(res.getInt(0),findStudent(res.getString(1)),findCourse(res.getString(2)))
        res.close()
        return enrollment
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
        list.sortBy{x->x.ID}
        res.close()
        return list
    }
    fun allCourses():List<Course>{
        val list:MutableList<Course> = mutableListOf()
        val db= this.writableDatabase
        val res = db.rawQuery("Select * from $TABLE_COURSE",null)
        if(res.moveToFirst()){
            list.add(Course(res.getInt(0),res.getString(1),res.getInt(2)))
            while (res.moveToNext()) {
                list.add(Course(res.getInt(0),res.getString(1),res.getInt(2)))
            }
        }
        res.close()
        return list
    }

    fun allEnrollment():List<Enrollment>{
        val list:MutableList<Enrollment> = mutableListOf()
        val db= this.writableDatabase
        val res = db.rawQuery("Select * from $TABLE_ENROLLMENT",null)
        if(res.moveToFirst()){
            list.add(Enrollment(res.getInt(0),findStudent( res.getString(1)) ,findCourse(res.getString(2))))
            while (res.moveToNext()) {
                list.add(Enrollment(res.getInt(0),findStudent( res.getString(1)) ,findCourse(res.getString(2))))
            }
        }
        res.close()
        return list
    }

    fun filterStudent(newText: String?) {
        var aux=Slist.value
        var lista:List<Student> = aux!!.filter{ it.ID.toString().toUpperCase().contains(newText.toString().toUpperCase()) }
        lista= lista + aux.filter{ it.Edad.toString().toUpperCase().contains(newText.toString().toUpperCase()) }
        lista= lista + aux.filter{ it.Nombre.toUpperCase().contains(newText.toString().toUpperCase()) }
        lista= lista + aux.filter{ it.Apellido.toUpperCase().contains(newText.toString().toUpperCase()) }
        Slist.value= lista.distinct()
    }

    fun filterCourse(newText: String?) {
        var aux=Clist.value
        var lista:List<Course> = aux!!.filter{ it.ID.toString().toUpperCase().contains(newText.toString().toUpperCase()) }
        lista= lista + aux.filter{ it.Descripcion.toString().toUpperCase().contains(newText.toString().toUpperCase()) }
        lista= lista + aux.filter{ it.Creditos.toString().toUpperCase().contains(newText.toString().toUpperCase()) }
        Clist.value= lista.distinct()
    }

    fun filterEnrollment(newText: String?) {
        var aux=Elist.value
        var lista:List<Enrollment> = aux!!.filter{ it.ID.toString().toUpperCase().contains(newText.toString().toUpperCase()) }
        lista= lista + aux.filter{ it.Student.toString().toUpperCase().contains(newText.toString().toUpperCase()) }
        lista= lista + aux.filter{ it.course.toString().toUpperCase().contains(newText.toString().toUpperCase()) }
        Elist.value= lista.distinct()
    }

    init {
        Slist.value=allStudents()
        Clist.value=allCourses()
        Elist.value=allEnrollment()
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