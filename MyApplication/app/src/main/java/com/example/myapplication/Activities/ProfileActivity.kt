package com.example.myapplication.Activities

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.InputFilter
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import com.example.myapplication.databinding.ActivityProfileBinding
import com.google.firebase.database.*


class ProfileActivity : BaseActivity() {
    private lateinit var binding: ActivityProfileBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setVariable()
        initData()
        setupInputFilters()
    }

    private fun initData() {
        intent?.extras?.let { extras ->
            binding.Edname.setText(extras.getString("name"))
            binding.Edemailaddress.setText(extras.getString("email"))
            binding.Edpassword.setText(extras.getString("password"))
            binding.Edphonenumber.setText(extras.getString("phonenumber"))
        }
    }

    private fun setVariable() {
        binding.UpdateBtn.setOnClickListener(View.OnClickListener {
            val name = binding.Edname.getText().toString().trim()
            val phonenumber = binding.Edphonenumber.getText().toString().trim()
            val email = binding.Edemailaddress.getText().toString().trim()
            val password = binding.Edpassword.getText().toString().trim()
            if (password.length < 6) {
                Toast.makeText(
                    this@ProfileActivity,
                    "Password must be at least 6 characters long",
                    Toast.LENGTH_SHORT
                ).show()
                return@OnClickListener
            }
            if (phonenumber.length != 10) {
                Toast.makeText(
                    this@ProfileActivity,
                    "Phone Number must be 10 digits",
                    Toast.LENGTH_SHORT
                ).show()
                return@OnClickListener
            }
            if (!name.isEmpty() && !phonenumber.isEmpty() && !password.isEmpty()) {
                val authRef = FirebaseDatabase.getInstance().getReference("Authentication")
                authRef.orderByChild("EmailID").equalTo(email)
                    .addListenerForSingleValueEvent(object : ValueEventListener {
                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                            if (dataSnapshot.exists()) {
                                for (snapshot in dataSnapshot.getChildren()) {
                                    val userKey = snapshot.key // Get the unique key of the user
                                    val userRef = authRef.child(userKey!!)
                                    userRef.addListenerForSingleValueEvent(object :
                                        ValueEventListener {
                                        override fun onDataChange(dataSnapshot: DataSnapshot) {
                                            val existingName = dataSnapshot.child("Name").getValue(
                                                String::class.java
                                            )
                                            val existingPhoneNumber =
                                                dataSnapshot.child("Phone Number").getValue(
                                                    String::class.java
                                                )
                                            val existingPassword =
                                                dataSnapshot.child("Password").getValue(
                                                    String::class.java
                                                )
                                            if (name == existingName && phonenumber == existingPhoneNumber && password == existingPassword) {
                                                Toast.makeText(
                                                    this@ProfileActivity,
                                                    "Information already up-to-date",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                            } else {
                                                userRef.child("Name").setValue(name)
                                                userRef.child("Phone Number").setValue(phonenumber)
                                                userRef.child("Password").setValue(password)
                                                val sharedPreferences = getSharedPreferences(
                                                    "UserInfo",
                                                    Context.MODE_PRIVATE
                                                )
                                                val editor = sharedPreferences.edit()
                                                editor.putString("name", name)
                                                editor.putString("email", email)
                                                editor.putString("password", password)
                                                editor.putString("phonenumber", phonenumber)
                                                editor.apply()
                                                Toast.makeText(
                                                    this@ProfileActivity,
                                                    "Updated successfully",
                                                    Toast.LENGTH_SHORT
                                                ).show()
                                                startActivity(
                                                    Intent(
                                                        this@ProfileActivity,
                                                        MainActivity::class.java
                                                    )
                                                )
                                            }
                                        }

                                        override fun onCancelled(error: DatabaseError) {
                                            // Handle Errors
                                        }
                                    })
                                }
                            } else {
                                Toast.makeText(
                                    this@ProfileActivity,
                                    "User not found",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }

                        override fun onCancelled(error: DatabaseError) {
                            // Handle Errors
                        }
                    })
            }
        })

        binding.backBtn.setOnClickListener {
            startActivity(Intent(this@ProfileActivity, MainActivity::class.java))
        }

        binding.Edphonenumber.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                if (s.length > 10) {
                    binding.Edphonenumber.setText(s.subSequence(0, 10))
                    binding.Edphonenumber.setSelection(10)
                }
            }

            override fun afterTextChanged(s: Editable) {}
        })
    }
    private fun setupInputFilters() {
        val nameFilter = InputFilter { source, start, end, dest, dstart, dend ->
            for (i in start until end) {
                if (!Character.isLetter(source[i])) {
                    return@InputFilter ""
                }
            }
            null
        }
        val phoneFilter =
            InputFilter { source, start, end, dest, dstart, dend ->
                for (i in start until end) {
                    if (!Character.isDigit(source[i])) {
                        return@InputFilter ""
                    }
                }
                null
            }
        binding.Edname.setFilters(arrayOf(nameFilter))
        binding.Edphonenumber.setFilters(arrayOf<InputFilter>(phoneFilter))
    }
}
