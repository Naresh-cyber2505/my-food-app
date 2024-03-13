package com.example.myfoodapp

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.myfoodapp.databinding.FragmentOtpBinding

class OtpFragment : Fragment() {

    private lateinit var binding: FragmentOtpBinding
    private lateinit var userName : String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOtpBinding.inflate(layoutInflater)


        getUserName()
        customizingEnteringOTP()

        onBackButtonClicked()

        return binding.root
    }

    private fun onBackButtonClicked() {
        binding.tbOtpFragment.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_otpFragment_to_signInFragment)
        }
    }

    private fun customizingEnteringOTP() {

        val editText = arrayOf(binding.etOtp1,binding.etOtp2,binding.etOtp3,binding.etOtp4,binding.etOtp5,binding.etOtp6)
        for (i in editText.indices){
            editText[i].addTextChangedListener(object : TextWatcher{
                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int
                ) {

                }

                override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

                }


                override fun afterTextChanged(s: Editable?) {
                    if (s?.length == 1){
                        if (i < editText.size - 1){
                            editText[i + 1].requestFocus()
                        }
                    }else if (s?.length == 0){
                        if (i > 0){
                            editText[i - 1].requestFocus()
                        }
                    }
                }

            })
        }

    }

    private fun getUserName() {
        val bundle = arguments
        userName = bundle?.getString("number").toString()

        binding.tvUserNumber.text = userName

    }

}