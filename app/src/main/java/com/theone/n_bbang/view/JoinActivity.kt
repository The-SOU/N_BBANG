package com.theone.n_bbang.view

import android.app.Activity
import android.content.Intent
import android.content.res.ColorStateList
import android.telephony.PhoneNumberFormattingTextWatcher
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout.LayoutParams
import android.widget.TextView
import android.widget.Toast

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
import java.util.regex.Pattern

@AndroidEntryPoint
class JoinActivity : BaseActivity<ActivityJoinBinding>(R.layout.activity_join) {

    companion object {
        const val LOG_JOIN_ACTIVITY = "LOG_JOIN_ACTIVITY::"
    }

    private val joinViewModel by viewModels<JoinViewModel>()

    var id = ""
    var pw = ""
    var pwRe = ""
    var name = ""
    var phone = ""
    var nickname = ""
    var isCheckedPush = false

    val accentViewList = arrayListOf<accentView>()

    override fun init() {
        binding.activity = this

        binding.btnBack.setOnClickListener { finish() }

        fetchUserIdCheck()
        fetchUserCheck()
        fetchJoin()

        setLayoutAccent()
        setClickListener()

    }

    private fun fetchJoin() {
        lifecycleScope.launchWhenStarted {
            joinViewModel.getJoin.collectLatest { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        /** bind data to the view */
                        Timber.d("fetchJoin: ${response.data}")

                        val data = response.data ?: return@collectLatest

                        if (data.result == 1) {
                            val intent = Intent(this@JoinActivity, MainActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this@JoinActivity, data.msg, Toast.LENGTH_SHORT).show()
                        }
                    }
                    is NetworkResult.Error -> {
                        /** show error message */
                        Timber.d("fetchJoin: Error: ${response.message}")
                    }
                    is NetworkResult.Loading -> {
                        /** show a progress bar */
                        Timber.d("fetchJoin: Loading")
                    }
                }
            }
        }
    }

    private fun fetchUserCheck() {
        lifecycleScope.launchWhenStarted {
            joinViewModel.getUserCheck.collectLatest { response ->
                when (response) {
                    is NetworkResult.Success -> {
                        /** bind data to the view */
                        Timber.d("fetchUserCheck: ${response.data}")

                        val data = response.data ?: return@collectLatest

                        if (data.result == 1) {
                            binding.tvResultPhone.visibility = View.GONE
                        } else {
                            binding.tvResultPhone.visibility = View.VISIBLE
                            binding.tvResultPhone.text = data.msg
                        }
                    }
                    is NetworkResult.Error -> {
                        /** show error message */
                        Timber.d("fetchUserCheck: Error: ${response.message}")
                    }
                    is NetworkResult.Loading -> {
                        /** show a progress bar */
                        Timber.d("fetchUserCheck: Loading")
                    }
                }

            }
        }
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
                    val pattern = Pattern.compile("""^(?=.*[0-9])(?=.*[a-zA-Z])(?=.*[!@#$%^+\-=])(?=\S+$).*$""")
                    val matcher = pattern.matcher(pw)
                    if (matcher.matches() && pw.length in 8 ..12) {
                        binding.tvResultPw.visibility = View.GONE
                    } else {
                        binding.tvResultPw.visibility = View.VISIBLE
                        binding.tvResultPw.text = "영문, 숫자, 특수문자 혼합 8~12자리"
                    }
                }
                binding.etPwRe -> {
                    pwRe = charSequence.toString()
                    if (pwRe.equals(pw)) {
                        binding.tvResultPwRe.visibility = View.GONE
                    } else {
                        binding.tvResultPwRe.visibility = View.VISIBLE
                        binding.tvResultPwRe.text = "비밀번호가 일치하지 않아요."
                    }
                }
                binding.etName -> {
                    name = charSequence.toString()
                }
                binding.etPhone -> {
                    phone = charSequence.toString()
                    android.os.Handler().postDelayed({
                        joinViewModel.fetchUserCheck(name, phone)
                    }, 1000)
                }
                binding.etNickname -> {
                    nickname = charSequence.toString()
                }
            }

        }
    }
    private fun setClickListener() {
        /** id 입력 */
        binding.etId.addTextChangedListener(textWatcher(binding.etId))

        /** pw 입력 */
        binding.etPw.addTextChangedListener(textWatcher(binding.etPw))
        val maxLength = 12
        binding.etPw.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
            val pwRegex = """^[0-9a-zA-Z!@#$%^+\-=]*$"""
            val pwPattern = Pattern.compile(pwRegex)
            if (source.isNullOrBlank() || pwPattern.matcher(source).matches()) {
                return@InputFilter source
            }
            Toast.makeText(this@JoinActivity, "입력할 수 없는 문자입니다.", Toast.LENGTH_LONG).show()
            ""
        }, InputFilter.LengthFilter(maxLength))

        /** pw 재입력 */
        binding.etPwRe.addTextChangedListener(textWatcher(binding.etPwRe))
        binding.etPwRe.filters = arrayOf(InputFilter { source, _, _, _, _, _ ->
            val pwRegex = """^[0-9a-zA-Z!@#$%^+\-=]*$"""
            val pwPattern = Pattern.compile(pwRegex)
            if (source.isNullOrBlank() || pwPattern.matcher(source).matches()) {
                return@InputFilter source
            }
            Toast.makeText(this@JoinActivity, "입력할 수 없는 문자입니다.", Toast.LENGTH_LONG).show()
            ""
        }, InputFilter.LengthFilter(maxLength))

        /** 이름 입력 */
        binding.etName.addTextChangedListener(textWatcher(binding.etName))

        /** 휴대전화 번호 입력 */
        binding.etPhone.addTextChangedListener(textWatcher(binding.etPhone))
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

        binding.btnOk.setOnClickListener {
            for (i in 0 until accentViewList.size) {
                val accentViewData = accentViewList[i]
                if (accentViewData.editView.text.toString().isEmpty()) {
                    Toast.makeText(this@JoinActivity, "${accentViewData.nameView}을/를 다시 한 번 확인해주세요.", Toast.LENGTH_SHORT).show()
                    editViewRequestFocus(this@JoinActivity, accentViewData.editView)
                    return@setOnClickListener
                }
            }
            if (!pw.equals(pwRe)) {
                Toast.makeText(this@JoinActivity, "비밀번호를 다시 한 번 확인해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            joinViewModel.fetchJoinResponse(id, pw, name, phone, nickname, if(isCheckedPush) "Y" else "N")
        }

    }

    data class accentView(val layoutView: ConstraintLayout, val defaultView: TextView, val editView: EditText, val accentView: ConstraintLayout, val nameView: String)


    private fun setLayoutAccent() {
        accentViewList.add(accentView(binding.layoutDefaultId, binding.tvDefaultId, binding.etId , binding.layoutAccentId, "아이디"))
        accentViewList.add(accentView(binding.layoutDefaultPw, binding.tvDefaultPw, binding.etPw , binding.layoutAccentPw, "비밀번호"))
        accentViewList.add(accentView(binding.layoutDefaultPwRe, binding.tvDefaultPwRe, binding.etPwRe , binding.layoutAccentPwRe, "비밀번호"))
        accentViewList.add(accentView(binding.layoutDefaultName, binding.tvDefaultName, binding.etName , binding.layoutAccentName, "이름"))
        accentViewList.add(accentView(binding.layoutDefaultPhone, binding.tvDefaultPhone, binding.etPhone , binding.layoutAccentPhone, "휴대폰 번호"))
        accentViewList.add(accentView(binding.layoutDefaultNickname, binding.tvDefaultNickname, binding.etNickname , binding.layoutAccentNickname, "닉네임"))

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