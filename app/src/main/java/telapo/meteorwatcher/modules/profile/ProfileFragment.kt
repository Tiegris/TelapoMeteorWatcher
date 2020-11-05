package telapo.meteorwatcher.modules.profile

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatDialogFragment
import telapo.meteorwatcher.R
import telapo.meteorwatcher.dal.model.Profile


class ProfileFragment : AppCompatDialogFragment() {
    private var name: EditText? = null
    private var addr: EditText? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.strProfile)
            .setView(contentView)
            .setPositiveButton(R.string.strOk) { _, _ ->
               run {
                    Profile.Name = name?.text.toString()
                    Profile.AddressCity = addr?.text.toString()
                    Profile.Commit()
                }
            }
            .setNegativeButton(R.string.strCancel, null)
            .create()
    }

    private val contentView: View
        get() {
            val view: View = LayoutInflater.from(context).inflate(R.layout.fragment_profile, null)
            name = view.findViewById(R.id.tbName)
            addr = view.findViewById(R.id.tbAddress)
            name?.setText(Profile.Name)
            addr?.setText(Profile.AddressCity)

            return view
        }

}