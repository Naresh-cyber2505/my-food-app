package com.example.myfoodapp

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.myfoodapp.activity.UsersMainActivity
import com.example.myfoodapp.databinding.FragmentOtpBinding
import com.example.myfoodapp.models.Users
import com.example.myfoodapp.viewmodels.AuthViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class OtpFragment : Fragment() {

    private val viewModel: AuthViewModel by viewModels()

    private lateinit var binding: FragmentOtpBinding
    private lateinit var userNumber: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentOtpBinding.inflate(layoutInflater)


        getUserName()
        customizingEnteringOTP()
        sendOTP()
        onLoginButtonClicked()
        onBackButtonClicked()

        return binding.root
    }

    private fun onLoginButtonClicked() {

        binding.login.setOnClickListener {
            Utils.showDialog(requireContext(),"Signing you...")
            val editText = arrayOf(
                binding.etOtp1,
                binding.etOtp2,
                binding.etOtp3,
                binding.etOtp4,
                binding.etOtp5,
                binding.etOtp6
            )
            val otp = editText.joinToString(""){it.text.toString()}
            
            if (otp.length < editText.size){
                Utils.showToast(requireContext(),"Please enter right otp.")
            }else{
                editText.forEach { it.text?.clear(); it.clearFocus() }
                verifyOtp(otp)
            }
        }

    }

    private fun verifyOtp(otp: String) {
        val user = Users(uid = Utils.getCurrentUserId(), userPhoneNumber = userNumber, userAddress = null)
        viewModel.signInWithPhoneAuthCredential(otp,userNumber,user)

        lifecycleScope.launch {
            viewModel.isSignedInSuccessfully.collect{otp ->
                if (otp){
                    Utils.hideDialog()
                    Utils.showToast(requireContext(),"Logged In.")
                    startActivity(Intent(requireActivity(), UsersMainActivity::class.java))
                    requireActivity().finish()
                }
            }
        }
    }

    private fun sendOTP() {

        Utils.showDialog(requireContext(), "Sending OTP...")
        viewModel.apply {
            sendOTP(userNumber, requireActivity())
            lifecycleScope.launch {
                otpSent.collect {otpSent ->
                    if (otpSent) {
                        Utils.hideDialog()
                        Utils.showToast(requireContext(), "OTP sent to the number.")
                    }
                }
            }

        }

    }

    private fun onBackButtonClicked() {
        binding.tbOtpFragment.setNavigationOnClickListener {
            findNavController().navigate(R.id.action_otpFragment_to_signInFragment)
        }
    }

    private fun customizingEnteringOTP() {

        val editText = arrayOf(
            binding.etOtp1,
            binding.etOtp2,
            binding.etOtp3,
            binding.etOtp4,
            binding.etOtp5,
            binding.etOtp6
        )
        for (i in editText.indices) {
            editText[i].addTextChangedListener(object : TextWatcher {
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
                    if (s?.length == 1) {
                        if (i < editText.size - 1) {
                            editText[i + 1].requestFocus()
                        }
                    } else if (s?.length == 0) {
                        if (i > 0) {
                            editText[i - 1].requestFocus()
                        }
                    }
                }

            })
        }

    }

    private fun getUserName() {
        val bundle = arguments
        userNumber = bundle?.getString("number").toString()

        binding.tvUserNumber.text = userNumber

    }

}