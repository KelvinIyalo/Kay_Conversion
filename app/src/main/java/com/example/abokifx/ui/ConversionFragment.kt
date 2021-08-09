package com.example.abokifx.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.example.abokifx.R
import com.example.abokifx.databinding.FragmentConversionBinding
import com.example.abokifx.utilss.Resource
import com.example.abokifx.viewModel.FXViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.delay

@AndroidEntryPoint
class ConversionFragment : Fragment(R.layout.fragment_conversion) {
    lateinit var binding: FragmentConversionBinding
    private val viewModel: FXViewModel by viewModels()
    @SuppressLint("SetTextI18n")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = FragmentConversionBinding.inflate(layoutInflater)



        binding.layout.setOnClickListener {

            if (binding.editTextInput.text.toString().isEmpty()){
                binding.AmountEt.error = "Field Can not be Empty "
            }else{

                val algorithm = binding.autoCompleteTv.text.toString()
                val algorithm2 = binding.autoCompleteTv2.text.toString()
                viewModel.getCurrency(algorithm,algorithm2)
                Log.d("HomeFragment",algorithm)


                viewModel.currencyValue.observe(viewLifecycleOwner, Observer { Answer ->

                    when(Answer){
                        is Resource.Loading -> {
                            binding.costumeProgress.visibility = View.VISIBLE
                            binding.btnGenerate.visibility = View.GONE
                           // binding.progressBar.animate().alpha(1f).duration =600L
                            binding.ResultTv.animate().alpha(0f).duration =600L
                        }

                    is Resource.Success -> {
                        binding.btnGenerate.visibility = View.GONE
                        val amount = binding.editTextInput.text.toString().toFloat()
                        val final = Answer.data?.convertedResult!!.toFloat()
                        val calculated =  amount*final
                     //   binding.progressBar.animate().alpha(0f).duration =600L
                        binding.ResultTv.animate().alpha(1f).duration =600L
                        binding.costumeProgress.visibility = View.GONE
                        binding.btnGenerate.visibility = View.VISIBLE
                        binding.btnGenerate.text = "Successful"

                        binding.ResultTv.text = "$amount$algorithm = $calculated$algorithm2"

                        binding.btnGenerate.text = "GENERATE"
                     //   binding.layout.isClickable(true)
                    }
                        is Resource.Error -> {
                          //  binding.progressBar.animate().alpha(1f).duration =600L
                            binding.ResultTv.animate().alpha(0f).duration =600L
                            binding.costumeProgress.visibility = View.GONE
                            Log.d("HomeFragment2","An Error Occurred")
                            binding.btnGenerate.text = "RETRY"
                          //  binding.layout.isClickable(true)
                        }
                    }

                })
            }



        }


    return binding.root}




    override fun onResume() {
        super.onResume()
        val algorithms = resources.getStringArray(R.array.currency_codes)
        val arrayAdapter = ArrayAdapter(requireContext(), R.layout.layout_view,algorithms)
        val algorithms2 = resources.getStringArray(R.array.currency_codes)
        val arrayAdapter2 = ArrayAdapter(requireContext(), R.layout.layout_view,algorithms2)
        binding.autoCompleteTv.setAdapter(arrayAdapter)
        binding.autoCompleteTv2.setAdapter(arrayAdapter2)
    }

  }