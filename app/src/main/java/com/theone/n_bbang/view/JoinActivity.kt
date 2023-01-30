package com.theone.n_bbang.view

import android.content.res.ColorStateList
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.view.View.OnFocusChangeListener
import android.widget.TextView
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import androidx.core.widget.addTextChangedListener
import com.theone.n_bbang.R
import com.theone.n_bbang.base.BaseActivity
import com.theone.n_bbang.databinding.ActivityJoinBinding
import com.theone.n_bbang.viewmodel.JoinViewModel
import com.theone.n_bbang.viewmodel.LoginViewModel

class JoinActivity : BaseActivity<ActivityJoinBinding>(R.layout.activity_join) {

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

        setClickListener()
    }

    private fun setClickListener() {
        setLayoutAccent()
        /** id 입력 */
        binding.etId.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                id = charSequence.toString()
            }
        })

        /** pw 입력 */
        binding.etPw.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                pw = charSequence.toString()
            }
        })

        /** 이름 입력 */
        binding.etName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                name = charSequence.toString()
            }
        })

        /** 휴대전화 번호 입력 */
        binding.etPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                phone = charSequence.toString()
            }
        })

        /** 닉네임 입력 */
        binding.etNickname.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {}
            override fun onTextChanged(charSequence: CharSequence?, p1: Int, p2: Int, p3: Int) {
                nickname = charSequence.toString()
            }
        })

        /** 체크 푸시 */
        binding.layoutCheckboxPush.setOnClickListener {
            if (isCheckedPush) {
                binding.viewCheckboxPush.setBackgroundResource(R.drawable.border_gray_radius_checkbox)
                binding.imgCheck.visibility = View.GONE
                isCheckedPush = false
            } else {
                binding.viewCheckboxPush.setBackgroundResource(R.drawable.radius_base_5)
                binding.viewCheckboxPush.backgroundTintList = ColorStateList.valueOf(ContextCompat.getColor(this, R.color.colorPrimaryVer2))
                binding.imgCheck.visibility = View.VISIBLE
                isCheckedPush = true
            }
        }
    }

    private fun setLayoutAccent() {
        val tvArrayList = arrayListOf(
            binding.tvDefaultId,
            binding.tvDefaultPw,
            binding.tvDefaultName,
            binding.tvDefaultPhone,
            binding.tvDefaultNickname
        )
        val etArrayList = arrayListOf(
            binding.etId,
            binding.etPw,
            binding.etName,
            binding.etPhone,
            binding.etNickname
        )
        val accentArrayList = arrayListOf(
            binding.layoutAccentId,
            binding.layoutAccentPw,
            binding.layoutAccentName,
            binding.layoutAccentPhone,
            binding.layoutAccentNickname
        )
        tvArrayList.mapIndexed { position, textView ->
            tvArrayList[position].setOnClickListener {
                etArrayList[position].performClick()
            }
        }
        etArrayList.mapIndexed { position, editText ->
            editText.setOnFocusChangeListener(object : OnFocusChangeListener{
                override fun onFocusChange(view: View?, isFocus: Boolean) {
                    if (isFocus) {
                        accentArrayList[position].visibility = View.VISIBLE
                    } else {
                        accentArrayList[position].visibility = View.GONE
                    }
                }

            })
        }
    }
}