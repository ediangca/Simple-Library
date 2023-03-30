package com.kodego.diangca.ebrahim.activity12databasecrud

import android.R
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kodego.diangca.ebrahim.activity12databasecrud.adapter.BorrowerAdapter
import com.kodego.diangca.ebrahim.activity12databasecrud.databinding.FragmentBorrowerBinding
import com.kodego.diangca.ebrahim.activity12databasecrud.model.Borrower


class BorrowerFragment(var mainActivity: MainActivity) : Fragment() {

    private var _binding: FragmentBorrowerBinding? = null
    private val binding get() = _binding!!

    var borrowers: ArrayList<Borrower> = ArrayList()
    private lateinit var borrowerAdapter: BorrowerAdapter

    private var departments = arrayOf(
        "Select Department",
        "IIT (Information Technology)",
        "IMB (Marine Biology)",
        "IED (Education)",
        "IPA (Public Ad)",
        "IE (ENGINEERING)"
    )
    private lateinit var depatmentAdapter: ArrayAdapter<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    private fun init() {

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("ATTACH_BORROWER_FRAGMENT", "BORROWER_FRAGMENT_ATTACHED")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ATTACH_BORROWER_RESUME", "BORROWER_FRAGMENT_RESUMED")
        borrowers = mainActivity.borrowers
    }

    override fun onPause() {
        super.onPause()
        Log.d("ATTACH_BORROWER_PAUSE", "BORROWER_FRAGMENT_PAUSED")
        mainActivity.borrowers = borrowers
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBorrowerBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initComponent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun initComponent() {

        borrowers = mainActivity.borrowers

//        restoreBook()

        Log.d("ATTACH_BORROWER_INIT", "BORROWER_FRAGMENT_INIT >>> ${borrowers.size}")

        borrowerAdapter = BorrowerAdapter(mainActivity, borrowers)

        binding.list.layoutManager = LinearLayoutManager(mainActivity)
        binding.list.adapter = borrowerAdapter

        binding.list.adapter!!.notifyDataSetChanged()
        borrowerAdapter!!.notifyDataSetChanged()

        depatmentAdapter =
            ArrayAdapter(mainActivity, R.layout.simple_list_item_1, departments)
        binding.spinnerDepartment.adapter = depatmentAdapter
        binding.spinnerFilterDepartment.adapter = depatmentAdapter

        depatmentAdapter.notifyDataSetChanged()

        binding.btnAdd.setOnClickListener {
            btnAddOnClickListener()
        }

        binding.spinnerFilterDepartment.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onItemSelected(adapterView: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //this method is fired when an item is selected
                val item = adapterView!!.getItemAtPosition(position).toString()
                if(position > -1) {
                    Toast.makeText(
                        adapterView.context,
                        item,
                        Toast.LENGTH_SHORT
                    ).show()
                    searchBookOnKeyListener(binding.searchBook.text.toString())
                }
            }
            override fun onNothingSelected(adapterView: AdapterView<*>?) {

            }
        }


        binding.searchBook.setOnKeyListener { v, keyCode, event ->
            Log.d("LIST_BOOK_SELECTED_KEY_LISTENER", "${binding.searchBook.text}")
            searchBookOnKeyListener(binding.searchBook.text.toString())
        }
    }


    private fun btnAddOnClickListener() {
        val borrowerId = binding.borrowerId.text
        val firstName = binding.firstName.text
        val lastName = binding.lastName.text
        val departmentPosition = binding.spinnerDepartment.selectedItemPosition
        if (borrowerId.isNullOrEmpty() || firstName.isNullOrEmpty() || lastName.isNullOrEmpty() || departmentPosition==0) {
            Snackbar.make(binding.root, "Please check empty fields!", Snackbar.LENGTH_SHORT).show()
            return
        } else {
            if (!mainActivity.borrowDAO.existBorrower(borrowerId.toString(), firstName.toString(), lastName.toString())) {
                val borrower = Borrower(firstName.toString(), lastName.toString())
                borrower.borrowerId = borrowerId.toString()
                borrower.department = departments[departmentPosition]
                mainActivity.borrowDAO.addBorrower(borrower)
//                Snackbar.make(binding.root,"Borrower has been successfully added !",Snackbar.LENGTH_SHORT).show()
                clearFields()
            }else{
                Snackbar.make(binding.root, "Either Borrower ID or Borrower already exist!", Snackbar.LENGTH_SHORT).show()
            }
        }
    }

    private fun exist(borrowerId: String, firstName: String, lastName: String): Boolean {
        for (borrower in borrowers) {
            if (borrower.borrowerId.equals(borrowerId, true)) {
                Snackbar.make(
                    binding.root,
                    "Borrower ID is already registered !",
                    Snackbar.LENGTH_SHORT
                ).show()
                return true
            }
            if (borrower.firstName.equals(firstName, true) && borrower.lastName.equals(
                    lastName,
                    true
                )
            ) {
                Snackbar.make(
                    binding.root,
                    "Borrower is already registered !",
                    Snackbar.LENGTH_SHORT
                ).show()
                return true
            }
        }
        return false
    }

    @SuppressLint("NotifyDataSetChanged")
    public fun clearFields() {
        binding.borrowerId.text = null
        binding.firstName.text = null
        binding.lastName.text = null
        binding.searchBook.text = null
        binding.spinnerDepartment.setSelection(0)

        searchBookOnKeyListener(binding.searchBook.text.toString())
        binding.list.adapter!!.notifyDataSetChanged()
        borrowerAdapter!!.notifyDataSetChanged()
        binding.list.requestFocus()
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun searchBookOnKeyListener(filter: String): Boolean {

        var borrowerList = ArrayList<Borrower>(10)
        var filterDepartmentPosition = binding.spinnerFilterDepartment.selectedItemPosition


        for (borrower in borrowers) {
            if (filterDepartmentPosition > 0) {
                if (!filter.isNullOrEmpty() && (borrower.firstName!!.contains(
                        filter,
                        true
                    ) || borrower.lastName!!.contains(filter, true))
                    && borrower.department.equals(departments[filterDepartmentPosition])
                ) {
                    borrowerList.add(borrower)
                } else {
                    if (filter.isNullOrEmpty() && borrower.department.equals(departments[filterDepartmentPosition])) {
                        borrowerList.add(borrower)
                    }
                }
            } else {
                if (!filter.isNullOrEmpty()) {
                    if (borrower.firstName!!.contains(filter, true) || borrower.lastName!!.contains(
                            filter,
                            true
                        )
                    ) {
                        borrowerList.add(borrower)
                    }
                } else {
                    borrowerList.add(borrower)
                }
            }
        }
        if(filterDepartmentPosition == 0) {
            borrowerList = mainActivity.borrowDAO.searchBorrowerByFilter(filter)
        }else if(filterDepartmentPosition > 0){
            borrowerList = mainActivity.borrowDAO.searchBorrowerByDepartmentWithFilter(departments[filterDepartmentPosition], filter)
        }
        borrowerAdapter = BorrowerAdapter(mainActivity, borrowerList)

        binding.list.layoutManager = LinearLayoutManager(mainActivity)
        binding.list.adapter = borrowerAdapter

        binding.list.adapter!!.notifyDataSetChanged()
        borrowerAdapter!!.notifyDataSetChanged()

        return false
    }

}