package com.example.simpletodo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.apache.commons.io.FileUtils
import java.io.File
import java.io.IOException
import java.nio.charset.Charset


class MainActivity : AppCompatActivity() {

    var taskList= mutableListOf<String>() //List of tasks as Strings
    lateinit var adapter:TaskItemAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)  // Connecting MainActivity to activity_main.xml

        //OnClickListener is an interface
       val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
           override fun onItemLongClicked(position: Int) {
               Log.i("LongPressed","onLongClickListener is executed")
              taskList.removeAt(position)
               adapter.notifyDataSetChanged()
               saveItems()
           }
       }
        loadItems()
        //Look up the recyclerView in the activity Layout
        val recyclerView=findViewById<RecyclerView>(R.id.recyclerView) //as recyclerView
        //Create Adapter passing in the sample user data
        adapter=TaskItemAdapter(taskList,onLongClickListener) //We are passing the tasks to the adapter to assign to each text field to get displayed in the View
        //Attach the Adapter to the recyclerView to populate the items
        recyclerView.adapter=adapter
        //Set LayoutManager to position the items
        recyclerView.layoutManager=LinearLayoutManager(this)
        val inputTextField=findViewById<EditText>(R.id.addTextField)
        findViewById<Button>(R.id.addButton).setOnClickListener {
            //Taking input from the user
            val userInput=inputTextField.text.toString()
            //Add the string to existing tasks
            taskList.add(userInput)
            // Notify the adapter that the data is updated and in the end
            adapter.notifyItemInserted(taskList.size-1)
            //Reset the text Field
            inputTextField.setText("")

            saveItems()
        }
    }


//Get the File we need
    fun getDataFile(): File {
    Log.i("getDataFile","Entered getDataFile()")
    //Every Line is going to represent a specific task in our lis of tasks
        return File(filesDir,"data.txt")
    }
///load the items by reading every line in the data
    fun loadItems(){
        try {
            Log.i("loadItems","Entered loadItems()")
            taskList =FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        }catch (ioException:IOException){
            ioException.printStackTrace()
        }
    }
    //save the items by writing items into data file
    fun saveItems(){
    try {
        Log.i("saveItems","Entered saveItems()")
        FileUtils.writeLines(getDataFile(),taskList)
    }catch (ioException:IOException){
        ioException.printStackTrace()
    }

    }
}



//Notes
/**
 * For storing the data of takes Files are used and modified in gradle
 *
 * save the data user inputted
 * save data by writing and reading from a file
 * : Create a method to get the file we need
 *
 *
 */
