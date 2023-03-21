package com.example.notesapp

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.notesapp.models.Note
import com.example.notesapp.databinding.ActivityAddNoteBinding
import java.text.SimpleDateFormat
import java.util.*

class AddNoteActivity : AppCompatActivity() {

    private val binding: ActivityAddNoteBinding by lazy {
        ActivityAddNoteBinding.inflate(layoutInflater)
    }

    private lateinit var note: Note
    private lateinit var oldNote: Note
    private var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        try {
            //oldNote = intent.getSerializableExtra("current_note", Note::class.java)
            oldNote = intent.getSerializableExtra("current_note") as Note
            binding.etTitle.setText(oldNote.title)
            binding.etNote.setText(oldNote.note)
            isUpdate = true
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }

        binding.imgCheck.setOnClickListener {
            val title = binding.etTitle.text.toString()
            val note = binding.etNote.text.toString()
            if (title.isNotEmpty() || note.isNotEmpty()) {
                val formatter = SimpleDateFormat("EEE, d MMM yyyy HH:MM a", Locale.US)

                if (isUpdate) {
                    this.note = Note(
                        oldNote.id, title, note, formatter.format(Date())
                    )
                } else {
                    this.note = Note(
                        null, title, note, formatter.format(Date())
                    )
                }

                val intent = Intent()
                intent.putExtra("note", this.note)
                setResult(Activity.RESULT_OK, intent)
                finish()

            } else {
                Toast.makeText(this@AddNoteActivity, "Please enter some data", Toast.LENGTH_LONG)
                    .show()
                return@setOnClickListener
            }
        }

        binding.imgBackArrow.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
        }

    }
}