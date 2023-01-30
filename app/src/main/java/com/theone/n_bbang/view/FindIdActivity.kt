package com.theone.n_bbang.view

import android.app.AlertDialog
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.widget.TextView
import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.lifecycleScope
import com.theone.domain.remote.FindResponse
import com.theone.domain.remote.NetworkResult
import com.theone.n_bbang.R
import com.theone.n_bbang.base.BaseActivity
import com.theone.n_bbang.databinding.ActivityFindIdBinding
import com.theone.n_bbang.viewmodel.FindViewModel
import com.theone.n_bbang.widget.Logger
import kotlinx.coroutines.flow.collectLatest

class FindIdActivity : BaseActivity<ActivityFindIdBinding>(R.layout.activity_find_id) {

    private val findViewModel by viewModels<FindViewModel>()

    var name = ""
    var phoneNumber = ""

    override fun init() {
        binding.activity = this

        fetchFindId()

        setClickListener()
    }

    private fun fetchFindId() {
        lifecycleScope.launchWhenStarted {
            findViewModel.getFindId.collectLatest { response ->
                when (response) {
                    is NetworkResult.Success -> { /** bind data to the view */
                        Logger.d("AutoLogin::", "fetchFindId : ${response.data}")
                        getFindId(response.data)
                    }
                    is NetworkResult.Error -> { /** show error message */
                        Logger.d("AutoLogin::", "fetchFindId : ${response.message}")

                    }
                    is NetworkResult.Loading -> { /** show a progress bar */
                        Logger.d("AutoLogin::", "fetchFindId : Loading")
                    }
                }
            }
        }
    }

    private fun getFindId(data: FindResponse.GetFindId?) {
        if (data != null) {
            if (data.result == 1) {
                showResultDialog(1, data.user_id)
            }
            else {
                showResultDialog(0, data.msg)
            }
        }
    }

    private fun showResultDialog(result: Int, msg: String) {
        val view = LayoutInflater.from(this).inflate(R.layout.popup_msg, null)
        val layout_popup = findViewById<ConstraintLayout>(R.id.layout_popup)
        val tv_title = findViewById<TextView>(R.id.tv_title)
        val tv_msg = findViewById<TextView>(R.id.tv_msg)
        val btn_ok = findViewById<ConstraintLayout>(R.id.btn_ok)

        layout_popup.clipToOutline = true
        tv_title.text = "아이디 찾기"

        val dialog = AlertDialog.Builder(this).create()
        when (result) {
            1 -> {
                tv_msg.text = msg
                btn_ok.setOnClickListener {
                    finish()
                }
                dialog.setView(view)
                dialog.show()
            }
            0 -> {
                tv_msg.text = msg
                btn_ok.setOnClickListener {
                    dialog.dismiss()
                }
                dialog.setView(view)
                dialog.show()
            }
        }
    }

    private fun setClickListener() {
        /** 이름 입력 */
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                name = charSequence.toString()

                if (name.isNotEmpty() && phoneNumber.length >= 12) {
                    binding.btnOk.isEnabled = true
                    binding.btnOk.isClickable = true
                }
                else {
                    binding.btnOk.isEnabled = false
                    binding.btnOk.isClickable = false
                }
            }
        })

        binding.etPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                phoneNumber = charSequence.toString()

                if (charSequence != null) {
                    if (charSequence.length > 8) {
                        binding.etPhone.setText("$charSequence" + "-")
                    }
                    else if (charSequence.length > 3) {
                        binding.etPhone.setText("$charSequence" + "-")
                    }
                }

                if (name.isNotEmpty() && phoneNumber.length >= 12) {
                    binding.btnOk.isEnabled = true
                    binding.btnOk.isClickable = true
                }
                else {
                    binding.btnOk.isEnabled = false
                    binding.btnOk.isClickable = false
                }
            }
        })

        binding.btnOk.setOnClickListener {
            val formatPhone = phoneNumber.replace("-", "")
            findViewModel.fetchFindIdResponse(name, formatPhone)
        }
    }

}