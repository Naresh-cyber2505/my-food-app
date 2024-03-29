package com.example.myfoodapp

import android.os.Build
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import androidx.navigation.fragment.findNavController
import com.example.myfoodapp.databinding.FragmentSignInBinding
import com.example.myfoodapp.databinding.FragmentSplashBinding

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class SignInFragment : Fragment() {

    private lateinit var binding: FragmentSignInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        setStatusColor()
        binding = FragmentSignInBinding.inflate(layoutInflater)


        getUserNumber()

        onContinueButtonClick()

        return binding.root




    }

    private fun onContinueButtonClick() {

        binding.btnContinue.setOnClickListener {
            val number = binding.etusernumber.text.toString()

            if (number.isEmpty() || number.length != 10){
                Utils.showToast(requireContext(),"Please enter a valid phone number")
            }
            else{
                val bundle = Bundle()
                bundle.putString("number", number)
                findNavController().navigate(R.id.action_signInFragment_to_otpFragment, bundle)
            }
        }

    }

    private fun getUserNumber() {
        binding.etusernumber.addTextChangedListener { object : TextWatcher{
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                TODO("Not yet implemented")
            }

            override fun onTextChanged(number: CharSequence?, p1: Int, p2: Int, p3: Int) {

                val len = number?.length

                if (len == 10){
                    binding.btnContinue.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.green))
                }
                else{
                    binding.btnContinue.setBackgroundColor(ContextCompat.getColor(requireContext(),R.color.grayish_blue))
                }

            }

            override fun afterTextChanged(p0: Editable?) {
                TODO("Not yet implemented")
            }

        } }
    }


    private fun setStatusColor() {

        activity?.window?.apply {
            val statusBarColors = ContextCompat.getColor(requireContext(), R.color.yellow)
            statusBarColor = statusBarColors
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
            }
        }
    }

}