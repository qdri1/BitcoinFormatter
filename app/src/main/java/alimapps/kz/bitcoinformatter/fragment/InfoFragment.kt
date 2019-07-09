package alimapps.kz.bitcoinformatter.fragment

import alimapps.kz.bitcoinformatter.R
import alimapps.kz.bitcoinformatter.classes.Consts
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alimapps.kz.bitcoinformatter.databinding.FragmentInfoBinding
import alimapps.kz.bitcoinformatter.util.TFormatter
import alimapps.kz.bitcoinformatter.viewmodel.InfoViewModel
import android.content.Context
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IAxisValueFormatter
import kotlinx.android.synthetic.main.fragment_info.*
import java.math.RoundingMode
import java.text.DecimalFormat
import java.util.*

class InfoFragment : Fragment() {

    private lateinit var binding: FragmentInfoBinding
    private lateinit var viewModel: InfoViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProviders.of(this).get(InfoViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentInfoBinding.inflate(inflater)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        first.setOnClickListener { viewModel.onRadioButtonClick(Consts.CODE_KZT) }
        second.setOnClickListener { viewModel.onRadioButtonClick(Consts.CODE_USD) }
        third.setOnClickListener { viewModel.onRadioButtonClick(Consts.CODE_EUR) }

        week_r.setOnClickListener { viewModel.onRadioButtonClick(TFormatter.TYPE_WEEK) }
        month_r.setOnClickListener { viewModel.onRadioButtonClick(TFormatter.TYPE_MONTH) }
        year_r.setOnClickListener { viewModel.onRadioButtonClick(TFormatter.TYPE_YEAR) }

        setupChart()
    }

    private fun setupChart() {
        chart.description.isEnabled = false
        chart.axisRight.isEnabled = false
        chart.xAxis.granularity = 1f

        chart.xAxis.valueFormatter = object : IAxisValueFormatter {
            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                val historical = viewModel.historicalLiveData.value
                if (historical != null) {
                    if (value < 0 || value >= historical.size)
                        return ""

                    return TFormatter.getDateAsDay(historical[value.toInt()].date)
                }
                return ""
            }
        }

        chart.axisLeft.valueFormatter = object : IAxisValueFormatter {
            override fun getFormattedValue(value: Float, axis: AxisBase?): String {
                val df = DecimalFormat("#")
                df.roundingMode = RoundingMode.CEILING
                return TFormatter.formatPrice(df.format(value), viewModel.currentCode)
            }
        }

        viewModel.historicalLiveData.observe(this, Observer { priceHistoryList ->
            val priceList = ArrayList<BarEntry>()
            for ((i, historical) in priceHistoryList.withIndex()) {
                priceList.add(BarEntry(i.toFloat(), historical.value.toFloat()))
            }
            val dataSet = BarDataSet(priceList, getString(R.string.label_history))
            val data = BarData(dataSet)
            data.setValueFormatter { value, _, _, _ ->
                TFormatter.formatPrice(value.toString(), viewModel.currentCode)
            }
            chart.data = data
            chart.invalidate()
        })
    }

}
