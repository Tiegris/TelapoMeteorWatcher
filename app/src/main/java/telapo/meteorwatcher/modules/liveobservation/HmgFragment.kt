package telapo.meteorwatcher.modules.liveobservation

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatDialogFragment
import telapo.meteorwatcher.R
import telapo.meteorwatcher.dal.model.Observation


class HmgFragment(val o: Context? = null, val start: Boolean = false) : AppCompatDialogFragment() {
    private var hmg: EditText? = null
    private var cycle: TextView? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setView(contentView)
            .setPositiveButton(R.string.strOk) { _, _ ->
                run {
                    if (hmg!!.text.isNotEmpty()) {
                        if (Observation.ActiveObservation?.CycleLives!!)
                            Observation.ActiveObservation?.Cycles?.last()?.Hmg =
                                hmg?.text.toString().toInt()
                        else
                            Observation.ActiveObservation?.NewCycle(hmg?.text.toString().toInt())

                        if (this.start) {
                            val intent = Intent(o!!, ObservationActivity::class.java)
                            startActivity(intent)
                        }
                    }
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
            val view: View = LayoutInflater.from(context).inflate(R.layout.fragment_hmg, null)
            cycle = view.findViewById(R.id.tvCycleFromTo)
            hmg = view.findViewById(R.id.tbHmg)
            cycle?.setText(Observation.ActiveObservation?.GetCycleDuration())

            if(Observation.ActiveObservation?.CycleLives!!)
                 hmg?.text.let { Observation.ActiveObservation?.Cycles?.last()?.Hmg.toString() }

            return view
        }


}