package com.example.baseadapterii

import android.app.Dialog
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.widget.doOnTextChanged
import com.example.baseadapterii.databinding.ActivityMainBinding
import com.example.baseadapterii.databinding.CustomDialogBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var customBinding : CustomDialogBinding
    private lateinit var list : ArrayList<Student>
    private lateinit var listAdapter: ListAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        list = ArrayList()
        list.add(Student("Alistar","1","Maths"))
        list.add(Student("Ram","2","English"))
        binding.fab.setOnClickListener {
            val customDialog = Dialog(this)
            customBinding = CustomDialogBinding.inflate(layoutInflater)
            customDialog.setContentView(customBinding.root)
            customDialog.setCancelable(false)
            customDialog.show()
            customBinding.positiveButton.setOnClickListener {
                if(customBinding.etName.text?.trim()?.isEmpty()!!){
                    customBinding.textInputLayout1.error = "Enter Name.."
                }
                else if(customBinding.etRoll.text?.trim()?.isEmpty()!!){
                    customBinding.textInputLayout2.error = "Enter Roll Number.."
                }
                else if(customBinding.etSubject.text?.trim()?.isEmpty()!!){
                    customBinding.textInputLayout3.error = "Enter Subject.."
                }
                else{
                    list.add(Student(customBinding.etName.text?.trim().toString(),
                        customBinding.etRoll.text?.trim().toString(),customBinding.etSubject.text?.trim().toString()))
                    listAdapter.notifyDataSetChanged()
                    Toast.makeText(this@MainActivity,"New Item Added..", Toast.LENGTH_SHORT).show()
                    customDialog.dismiss()
                }
            }
            customBinding.negativeButton.setOnClickListener {
                customDialog.dismiss()
            }
            customBinding.etName.doOnTextChanged { _, _, _, _ ->
                customBinding.textInputLayout1.isErrorEnabled = false
            }
            customBinding.etRoll.doOnTextChanged { _, _, _, _ ->
                customBinding.textInputLayout2.isErrorEnabled = false
            }
            customBinding.etSubject.doOnTextChanged { _, _, _, _ ->
                customBinding.textInputLayout3.isErrorEnabled = false
            }
        }
        binding.listView.setOnItemLongClickListener { _, _, position, _ ->
            val alertDialog = AlertDialog.Builder(this)
            alertDialog.setCancelable(false)
            alertDialog.setTitle("Conformation!!")
            alertDialog.setMessage("Do you want to delete this..")
            alertDialog.setPositiveButton("Yes"){_,_ ->
                list.removeAt(position)
                listAdapter.notifyDataSetChanged()
            }
            alertDialog.setNegativeButton("No"){ _,_ ->}
            alertDialog.show()
            return@setOnItemLongClickListener true
        }
        binding.listView.setOnItemClickListener { _, _, position, _ ->
            customBinding = CustomDialogBinding.inflate(layoutInflater)
            val customUpdate = Dialog(this)
            customUpdate.setContentView(customBinding.root)
            customUpdate.setCancelable(false)
            customUpdate.show()
            customBinding.title.text = getString(R.string.update_student)
            customBinding.positiveButton.text = getString(R.string.update)
            customBinding.etName.setText(list[position].name)
            customBinding.etRoll.setText(list[position].roll)
            customBinding.etSubject.setText(list[position].subject)

            customBinding.positiveButton.setOnClickListener {
                if (customBinding.etName.text.trim().isEmpty()) {
                    customBinding.textInputLayout1.error = "Enter Name.."
                } else if (customBinding.etRoll.text.trim().isEmpty()) {
                    customBinding.textInputLayout2.error = "Enter Roll No."
                } else if (customBinding.etSubject.text.trim().isEmpty()) {
                    customBinding.textInputLayout3.error = "Enter Subject.."
                } else {
                    list[position] = Student(
                        customBinding.etName.text.toString().trim(),
                        customBinding.etRoll.text.toString().trim(),
                        customBinding.etSubject.text.toString().trim()
                    )
                    listAdapter.notifyDataSetChanged()
                    customUpdate.dismiss()
                }
            }
            customBinding.etName.doOnTextChanged { _, _, _, _ ->
                customBinding.textInputLayout1.isErrorEnabled = false
            }
            customBinding.etRoll.doOnTextChanged { _, _, _, _ ->
                customBinding.textInputLayout2.isErrorEnabled = false
            }
            customBinding.etSubject.doOnTextChanged { _, _, _, _ ->
                customBinding.textInputLayout3.isErrorEnabled = false
            }
        }
        listAdapter = ListAdapter(list)
        binding.listView.adapter = listAdapter
    }
}