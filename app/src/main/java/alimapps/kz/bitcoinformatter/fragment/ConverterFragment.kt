package alimapps.kz.bitcoinformatter.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alimapps.kz.bitcoinformatter.classes.Consts
import alimapps.kz.bitcoinformatter.databinding.FragmentConverterBinding
import alimapps.kz.bitcoinformatter.viewmodel.ConverterViewModel
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_converter.*

class ConverterFragment : Fragment() {

    private lateinit var binding: FragmentConverterBinding
    private lateinit var viewModel: ConverterViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProviders.of(this).get(ConverterViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewModel.init()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentConverterBinding.inflate(inflater)
        binding.data = viewModel.converterData
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        first.setOnClickListener { viewModel.onRadioButtonClick(Consts.CODE_KZT) }
        second.setOnClickListener { viewModel.onRadioButtonClick(Consts.CODE_USD) }
        third.setOnClickListener { viewModel.onRadioButtonClick(Consts.CODE_EUR) }
    }

}
