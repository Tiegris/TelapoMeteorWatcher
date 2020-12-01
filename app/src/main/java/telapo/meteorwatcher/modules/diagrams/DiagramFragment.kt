package telapo.meteorwatcher.modules.diagrams

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDialogFragment
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.utils.ColorTemplate
import kotlinx.android.synthetic.main.fragment_diagram.*
import telapo.meteorwatcher.R
import telapo.meteorwatcher.dal.model.observation.Observation


class DiagramFragment(val observation: Observation) : AppCompatDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_diagram, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val entries = mutableListOf<BarEntry>()
        var k = 0
        val swarmStat = observation.GetSwarmStatistics()
        for (i in swarmStat) {
            entries.add( BarEntry(k.toFloat(), i.Count.toFloat()))
            k++
        }
        val dataSet = BarDataSet(entries, "Meteors")
        dataSet.colors = ColorTemplate.MATERIAL_COLORS.toList()

        val data = BarData(dataSet)

        chartMain.data = data;
        chartMain.description.text=""
        chartMain.invalidate();
    }

}