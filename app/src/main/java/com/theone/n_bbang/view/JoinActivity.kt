package com.theone.n_bbang.view

import android.app.Activity
import android.content.res.ColorStateList
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView

import androidx.activity.viewModels
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.google.android.material.textfield.TextInputEditText
import com.theone.domain.remote.NetworkResult
import com.theone.n_bbang.R
import com.theone.n_bbang.base.BaseActivity
import com.theone.n_bbang.databinding.ActivityJoinBinding
import com.theone.n_bbang.viewmodel.JoinViewModel
import com.theone.n_bbang.widget.Utils
import com.theone.n_bbang.widget.Utils.editViewRequestFocus
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.util.logging.Handler

@AndroidEntryPoint
class JoinActivity : BaseActivity<ActivityJoinBinding>(R.layout.activity_join) {

    companion object {
        const val LOG_JOIN_ACTIVITY = "LOG_JOIN_ACTIVITY::"
    }

    private val joinViewModel by viewModels<JoinViewModel>()

    var id = ""
    var pw = ""
    var name = ""
    var phone = ""
    var nickname = ""
    var isCheckedPush = false

    override fun init() {
        binding.activity = this

        binding.btnBack.setOnClickListener { finish() }

        fetchUserIdCheck()

        setClickListener()


    }

    private fun fetchUserIdCheck() {
        lifecycleScope.launchWhenStarted {
            joinViewModel.getUserIdCheck.collectLatest { response ->
                when(response) {
                    is NetworkResult.Success -> {
                        /** bind data to the view */
                        Timber.d("fetchUserIdCheck: ${response.data}")

                        val data = response.data ?: return@collectLatest

                        if (data.result == 1) {
                            binding.tvResultId.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.colorTextSuccess))
                        } else {
                            binding.tvResultId.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.colorTextWarning))
                        }

                        binding.tvResultId.visibility = View.VISIBLE
                        binding.tvResultId.text = data.msg
                    }
                    is NetworkResult.Error -> {
                        /** show error message */
                        Timber.d("fetchUserIdCheck: Error: ${response.message}")
                    }
                    is NetworkResult.Loading -> {
                        /** show a progress bar */
                        Timber.d("fetchUserIdCheck: Loading")
                    }
                }
            }
        }
    }



    fun textWatcher(view : EditText) = object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        override fun afterTextChanged(charSequence: Editable?) {
            when(view){
                binding.etId -> {
                    id = charSequence.toString()
                    android.os.Handler().postDelayed({
                        joinViewModel.fetchUserIdCheck(id)
                    }, 1000)
                }
                binding.etPw -> {
                    pw = charSequence.toString()
                }
                binding.etName -> {
                    name = charSequence.toString()
                }
                binding.etPhone -> {
                    phone = charSequence.toString()
                }
                binding.etNickname -> {
                    nickname = charSequence.toString()
                }
            }

        }
    }
    private fun setClickListener() {
        setLayoutAccent()
        /** id 입력 */
        binding.etId.addTextChangedListener(textWatcher(binding.etId))

        /** pw 입력 */
        binding.etPw.addTextChangedListener(textWatcher(binding.etPw))

        /** 이름 입력 */
        binding.etName.addTextChangedListener(textWatcher(binding.etName))

        /** 휴대전화 번호 입력 */
        binding.etPhone.addTextChangedListener(object : PhoneNumberFormattingTextWatcher(){})

        /** 닉네임 입력 */
        binding.etNickname.addTextChangedListener(textWatcher(binding.etNickname))

        /** 체크 푸시 */
        binding.layoutCheckboxPush.setOnClickListener {
            if (isCheckedPush) {
                binding.viewCheckboxPush.setBackgroundResource(R.drawable.border_gray_radius_checkbox)
                binding.imgCheck.visibility = View.GONE
                isCheckedPush = false
            } else {
                binding.viewCheckboxPush.setBackgroundResource(R.drawable.radius_primary_5)
                binding.imgCheck.visibility = View.VISIBLE
                isCheckedPush = true
            }
        }

    }

    data class accentView(val layoutView: ConstraintLayout, val defaultView : TextView ,val editView : EditText ,val accentView : ConstraintLayout )
    private fun setLayoutAccent() {
        val accentViewList = arrayListOf(
            accentView(binding.layoutDefaultId, binding.tvDefaultId, binding.etId , binding.layoutAccentId),
            accentView(binding.layoutDefaultPw, binding.tvDefaultPw, binding.etPw , binding.layoutAccentPw),
            accentView(binding.layoutDefaultPwRe, binding.tvDefaultPwRe, binding.etPwRe , binding.layoutAccentPwRe),
            accentView(binding.layoutDefaultName, binding.tvDefaultName, binding.etName , binding.layoutAccentName),
            accentView(binding.layoutDefaultPhone, binding.tvDefaultPhone, binding.etPhone , binding.layoutAccentPhone),
            accentView(binding.layoutDefaultNickname, binding.tvDefaultNickname, binding.etNickname , binding.layoutAccentNickname)
        )
        accentViewList.map { accentViewData ->
            accentViewData.layoutView.setOnClickListener {
                Timber.d("layoutView.setOnClickListener"+accentViewData.editView.tag.toString())
                editViewRequestFocus(this@JoinActivity, accentViewData.editView)
            }
            accentViewData.editView.setOnFocusChangeListener { view, hasFocus ->
                if (hasFocus) {
                    accentViewData.accentView.visibility = View.VISIBLE
                    accentViewData.defaultView.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.colorPrimaryVer2))
                } else {
                    accentViewData.accentView.visibility = View.GONE
                    accentViewData.defaultView.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.colorGrayLight))

                    if (accentViewData.editView.text.isNotEmpty()) {
                        if (accentViewData.editView == binding.etId) {
                            if (binding.tvResultId.textColors != ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorTextSuccess))) return@setOnFocusChangeListener
                        }
                        accentViewData.defaultView.setTextColor(ContextCompat.getColor(this@JoinActivity, R.color.colorPrimaryVer2))
                    }
                }
            }
        }
    }
}