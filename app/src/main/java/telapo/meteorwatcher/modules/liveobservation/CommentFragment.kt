package telapo.meteorwatcher.modules.liveobservation

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDialogFragment
import telapo.meteorwatcher.R
import telapo.meteorwatcher.dal.model.observation.Comment
import telapo.meteorwatcher.utility.Formater
import telapo.meteorwatcher.utility.Time

interface ICommentable {
    fun AddComment(c: Comment)
}

class CommentFragment(val o: ICommentable) : AppCompatDialogFragment() {
    private var comment: EditText? = null
    private var ts: TextView? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            //.setTitle(R.string.strComment)
            .setView(contentView)
            .setPositiveButton(R.string.strOk) { _, _ ->
                run {
                    o.AddComment(Comment( ts?.text.toString(), comment?.text.toString() ))
                    Toast.makeText(o as Activity,
                        "Comment saved",
                        Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton(R.string.strCancel, null)
            .create()
    }

    override fun onStart() {
        super.onStart()
        dialog?.window?.setBackgroundDrawableResource(R.color.colorBackground)
    }

    private val contentView: View
        get() {
            val view: View = LayoutInflater.from(context).inflate(R.layout.fragment_comment, null)
            ts = view.findViewById(R.id.tvTimeStamp)
            comment = view.findViewById(R.id.tbComment)
            ts?.setText(Formater.GetDateTime(Time.Utc))

            return view
        }

}