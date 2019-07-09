package alimapps.kz.bitcoinformatter.fragment


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import alimapps.kz.bitcoinformatter.R
import alimapps.kz.bitcoinformatter.adapter.BaseRecyclerViewAdapter
import alimapps.kz.bitcoinformatter.classes.TransactionItemData
import alimapps.kz.bitcoinformatter.databinding.FragmentSalesBinding
import alimapps.kz.bitcoinformatter.dialog.BaseDialogs
import alimapps.kz.bitcoinformatter.util.ObservableListChangedCallback
import alimapps.kz.bitcoinformatter.viewmodel.SalesViewModel
import android.content.Context
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.fragment_sales.*

class SalesFragment : Fragment() {

    private lateinit var binding: FragmentSalesBinding
    private lateinit var viewModel: SalesViewModel

    private val adapter: BaseRecyclerViewAdapter<TransactionItemData> by lazy {
        BaseRecyclerViewAdapter<TransactionItemData>(viewModel.transactions, R.layout.transaction_item)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        viewModel = ViewModelProviders.of(this).get(SalesViewModel::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setupEventListeners()
        viewModel.init()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = FragmentSalesBinding.inflate(inflater)
        binding.viewModel = viewModel
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        adapter.adapterClickListener = object : BaseRecyclerViewAdapter.AdapterClickListener<TransactionItemData> {
            override fun onAdapterItemClicked(position: Int, item: TransactionItemData) {
                BaseDialogs.transactionDialog(context!!, item).show()
            }
        }
        salesItems.adapter = adapter
    }

    private fun setupEventListeners() {
        viewModel.transactions.addOnListChangedCallback(ObservableListChangedCallback<TransactionItemData>(adapter))
    }

}
