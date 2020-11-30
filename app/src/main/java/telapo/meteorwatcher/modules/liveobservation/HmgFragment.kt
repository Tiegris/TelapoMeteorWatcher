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
import telapo.meteorwatcher.dal.model.observation.Observation


class HmgFragment(val o: Context? = null, val start: Boolean = false) : AppCompatDialogFragment() {
    private var hmg: EditText? = null
    private var lm: EditText? = null
    private var cycle: TextView? = null

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(requireContext())
            .setView(contentView)
            .setPositiveButton(R.string.strOk) { _, _ ->
                run {
                    if (hmg!!.text.isNotEmpty() && lm!!.text.isNotEmpty()) {
                        if (Observation.ActiveObservation?.CycleLives!!) {
                            Observation.ActiveObservation?.LatestCycle?.Hmg =
                                hmg?.text.toString().toInt()
                            Observation.ActiveObservation?.LatestCycle?.Lm =
                                lm?.text.toString().toInt()
                        }
                        else
                            Observation.ActiveObservation?.NewCycle(hmg?.text.toString().toInt(),lm?.text.toString().toInt())

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
            lm = view.findViewById(R.id.tbLm)
            cycle?.setText(Observation.ActiveObservation?.GetCycleDuration())

            if(Observation.ActiveObservation?.CycleLives!!) {
                hmg?.setText(Observation.ActiveObservation?.LatestCycle?.Hmg.toString())
                lm?.setText(Observation.ActiveObservation?.LatestCycle?.Lm.toString())
            }
            return view
        }


}