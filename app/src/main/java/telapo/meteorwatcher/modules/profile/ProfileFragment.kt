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

class ProfileFragment(val receiver: ProfileFragment.IReceiver?) : AppCompatDialogFragment() {
    interface IReceiver {
        fun ReceviePing()
    }

    private var name: EditText? = null
    private var email: EditText? = null
    private var addr: EditText? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setTitle(R.string.strProfile)
            .setView(contentView)
            .setPositiveButton(R.string.strOk) { _, _ ->
               run {
                   Profile.Name = name?.text.toString()
                   Profile.Email = email?.text.toString()
                   Profile.AddressCity = addr?.text.toString()
                   Profile.Commit()
                   receiver?.ReceviePing()
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
            val view: View = LayoutInflater.from(context).inflate(R.layout.fragment_profile, null)
            name = view.findViewById(R.id.tbName)
            email = view.findViewById(R.id.tbEmail)
            addr = view.findViewById(R.id.tbAddress)
            name?.setText(Profile.Name)
            email?.setText(Profile.Email)
            addr?.setText(Profile.AddressCity)

            return view
        }

}