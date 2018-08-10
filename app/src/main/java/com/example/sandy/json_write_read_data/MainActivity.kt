package com.example.sandy.json_write_read_data

import android.content.Context
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStreamReader

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        insert.setOnClickListener {
            var g = Gson( )
            var emp = Employee(et1.text.toString().toInt(),et2.text.toString(),
                    et3.text.toString(),et4.text.toString())

            var list = mutableListOf<Employee>()

            list.add(emp)

            var emps = Employees(list)

            var json_resp = g.toJson(emps)  //converting kotlin object to GSON

            var fos =   openFileOutput("employees.json",Context.MODE_PRIVATE)
            fos.write(json_resp.toByteArray())

            fos.flush() //flush buffer

            fos.close()

        }

        read.setOnClickListener {
            var fis = openFileInput("employees.json")
            var g = Gson( )
            var emps:Employees = g.fromJson(InputStreamReader(fis),
                    Employees::class.java)
            var employees:MutableList<Employee> = emps.employees
            var temp_list = mutableListOf<String>()
            for( emp in employees){
                temp_list.add(emp.id.toString()+"\t"+emp.name+"\n"+
                        emp.desig+"\t"+emp.dept)
            }
            var adapter = ArrayAdapter<String>(this@MainActivity,android.R.layout.simple_list_item_single_choice,temp_list)
            lview.adapter = adapter
        }
    }
}
