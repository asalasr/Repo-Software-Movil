package com.example.vinilos.ui

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.vinilos.R
import com.example.vinilos.models.CollectorComment
import com.example.vinilos.models.CommentCollector
import com.example.vinilos.viewmodels.CommentViewModel


class FormComment : AppCompatActivity() {

    private lateinit var commentViewModelClass: CommentViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_form_comment)


        val spinnerGender: Spinner = findViewById(R.id.spinner_rating)
        ArrayAdapter.createFromResource(
            this,
            R.array.rating_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerGender.adapter = adapter
        }

    }

    fun onCLickCreateComment(view: View) {

        val comment: CommentCollector? = validateFormComment()

        if (comment != null) {
            Log.i("FormComment", "Comentario válido")
            Toast.makeText(applicationContext, "Creando comentario...", Toast.LENGTH_LONG).show()

            var id = intent.getStringExtra("idalbum")?.toInt()
            Log.i("AlbumId", "Id albuim" + id)
            if (id == null) {
                id = 100
            }
            commentViewModelClass = CommentViewModel(this.application, id)

            commentViewModelClass.startPostCreate(comment, {
                Log.i("FormComment", "Comentario válidoCreo comentario $it")
                if (it) {
                    showResponseAlert(
                        R.string.commentCreationSuccess,
                        android.R.drawable.ic_dialog_info,
                        true
                    )
                }
            }, {
                showResponseAlert(
                    R.string.commentCreationFail,
                    android.R.drawable.ic_dialog_alert,
                    false
                )
            })
        } else {
            return
        }
    }


    fun showResponseAlert(message: Int, icon: Int, goBack: Boolean) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle(R.string.comment_alert_creation_title)
        builder.setMessage(message)
        builder.setIcon(icon)
        builder.setPositiveButton("OK") { dialogInterface, which ->
            if (goBack) {
                //Cambiar para la vista que se necesita
                val intent = Intent(this, ListAlbums::class.java)
                startActivity(intent)
            }
        }
        val alertDialog: AlertDialog = builder.create()
        alertDialog.setCancelable(false)
        alertDialog.show()
    }

    fun validateFormComment(): CommentCollector? {

        val rating: Spinner = findViewById(R.id.spinner_rating)
        val comment: TextView = findViewById(R.id.editTextComment)

        var commentObj: CommentCollector? = null

        var error = false

        if (comment.text.toString().trim().isEmpty()) {
            comment.setError(getString(R.string.campo_obligatorio))
            comment.requestFocus()
            error = true
        }else if (comment.text.toString().length >=500){
            comment.setError(getString(R.string.max_caracteres))
            comment.requestFocus()
            error = true
        }

        if (rating.selectedItem.toString().equals(getString(R.string.comment_spinner_text))) {
            val spinnerError: TextView = rating.selectedView as TextView
            spinnerError.setError("");
            spinnerError.setTextColor(Color.RED);
            spinnerError.setText(getString(R.string.campo_obligatorio));
            error = true
        }

        if (!error){
            commentObj = CommentCollector(
                rating.selectedItem.toString().toInt(), comment.text.toString(),
                CollectorComment(100)
            )
        }

        return commentObj

    }

}