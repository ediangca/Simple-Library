package com.kodego.diangca.ebrahim.activity12databasecrud

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewbinding.ViewBinding
import com.google.android.material.snackbar.Snackbar
import com.kodego.diangca.ebrahim.activity12databasecrud.adapter.TransactionAdapter
import com.kodego.diangca.ebrahim.activity12databasecrud.databinding.BookDirectoryDialogBinding
import com.kodego.diangca.ebrahim.activity12databasecrud.databinding.BorrowerDirectoryDialogBinding
import com.kodego.diangca.ebrahim.activity12databasecrud.databinding.FragmentHomeBinding
import com.kodego.diangca.ebrahim.activity12databasecrud.model.Book
import com.kodego.diangca.ebrahim.activity12databasecrud.model.Borrower
import com.kodego.diangca.ebrahim.activity12databasecrud.model.Transaction
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class HomeFragment(var mainActivity: MainActivity) : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private lateinit var bookDirectoryDialogBinding: BookDirectoryDialogBinding
    private lateinit var borrowerDirectoryDialogBinding: BorrowerDirectoryDialogBinding


    private lateinit var bookListAdapter: ArrayAdapter<*>

    //    private lateinit var bookAdapter: BookAdapter
    private lateinit var book: Book
    private var books = ArrayList<Book>()
    private var bookList = ArrayList<String>()
    private var selectedBookPosition = -1
//    private var resultBookList = ArrayList<Book>()

    private lateinit var borrowerListAdapter: ArrayAdapter<*>

    //    private lateinit var borrowerAdapter: BorrowerAdapter
    private lateinit var borrower: Borrower
    private var borrowers = ArrayList<Borrower>()
    private var borrowerList = ArrayList<String>()
    private var selectedBorrowerPosition = -1

    private lateinit var transaction: Transaction
    private lateinit var adapterTransaction: TransactionAdapter
    private var transactions: ArrayList<Transaction> = ArrayList()
    private var simpleDateFormat = SimpleDateFormat("yyyy-MM-d")
    private var dateBorrowed = Date()
    private var dateReturn = Date()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentHomeBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initComponent()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("HOME_FRAGMENT_TRANSACTIONS", "ON_ATTACH ${transactions.size}")
        Log.d("HOME_FRAGMENT_BOOKS", "ON_ATTACH ${books.size}")
    }

    override fun onResume() {
        super.onResume()

        transactions = mainActivity.transactions
        Log.d("HOME_FRAGMENT_INIT", "HOME_FRAGMENT_INIT ${transactions.size}")
        adapterTransaction = TransactionAdapter(mainActivity, transactions)
        binding.list.layoutManager = LinearLayoutManager(mainActivity.applicationContext)
        binding.list.adapter = adapterTransaction
        binding.list.adapter!!.notifyDataSetChanged()

        books = mainActivity.books
        borrowers = mainActivity.borrowers
    }

    override fun onPause() {
        super.onPause()
        mainActivity.transactions = transactions
        Log.d("HOME_FRAGMENT", "ON_PAUSE ${transactions.size}")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d("HOME_FRAGMENT", "ON_DESTROY_VIEW ${transactions.size}")
    }


    private fun initComponent() {

        adapterTransaction = TransactionAdapter(mainActivity, transactions)

        binding.list.layoutManager = LinearLayoutManager(mainActivity.applicationContext)
        binding.list.adapter = adapterTransaction

        binding.dateBorrowed.setText(SimpleDateFormat("yyyy-MM-d").format(Date()).toString())
        binding.dateReturn.setText(SimpleDateFormat("yyyy-MM-d").format(Date()).toString())

        books = mainActivity.books
        borrowers = mainActivity.borrowers

        binding.btnBook.setOnClickListener {
            btnBookOnClickListener()
        }
        binding.btnBorrower.setOnClickListener {
            btnBorrowerOnClickListener()
        }
        binding.btnAdd.setOnClickListener {
            btnAddOnClickListener()
        }

        binding.btnNew.setOnClickListener {
            btnNewOnClickListener()
        }

        binding.btnBorrowed.setOnClickListener {

            datePickerDialog1()
            Log.d("DATE_PICKER_SELECTED", "$dateBorrowed")
        }
        binding.btnReturn.setOnClickListener {

            datePickerDialog2()
            Log.d("DATE_PICKER_SELECTED", "$dateReturn")

        }

    }

    private fun restoreBook() {
        bookList.clear()
        for (book in books) {
            bookList.add("${book.bookName} - ${book.description}")
            println("BOOK_LIST ${book.bookName.toString()}")
        }
    }

    private fun restoreBorrower() {
        borrowerList.clear()
        for (borrower in borrowers) {
            borrowerList.add(borrower.getDetails())
            println("BORROWER_LIST ${borrower.getDetails()}")
        }
    }

    private fun btnNewOnClickListener() {
        if (binding.linearForm.visibility==View.GONE) {
            binding.linearForm.visibility = View.VISIBLE
            binding.btnNew.setImageResource(R.drawable.vector_cancel)
        } else {
            binding.linearForm.visibility = View.GONE
            binding.btnNew.setImageResource(R.drawable.vector_add)
            clearfields()
        }
    }

    private fun datePickerDialog1() {

        val getDate: Calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            mainActivity,
            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val selectDate = Calendar.getInstance()
                selectDate.set(Calendar.YEAR, year)
                selectDate.set(Calendar.MONTH, month)
                selectDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                dateBorrowed = selectDate.time
                val date = simpleDateFormat.format(selectDate.time)
                binding.dateBorrowed.setText(date)
            },
            getDate.get(Calendar.YEAR),
            getDate.get(Calendar.MONTH),
            getDate.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        datePicker.show()
    }


    private fun datePickerDialog2() {

        val getDate: Calendar = Calendar.getInstance()
        val datePicker = DatePickerDialog(
            mainActivity,
            android.R.style.Theme_Holo_Light_Dialog_MinWidth,
            DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
                val selectDate = Calendar.getInstance()
                selectDate.set(Calendar.YEAR, year)
                selectDate.set(Calendar.MONTH, month)
                selectDate.set(Calendar.DAY_OF_MONTH, dayOfMonth)

                dateReturn = selectDate.time
                val date = simpleDateFormat.format(selectDate.time)
                binding.dateReturn.setText(date)
            },
            getDate.get(Calendar.YEAR),
            getDate.get(Calendar.MONTH),
            getDate.get(Calendar.DAY_OF_MONTH)
        )
        datePicker.window!!.setBackgroundDrawableResource(android.R.color.transparent)
        datePicker.show()
    }

    private fun btnBookOnClickListener() {
        showBookDirectory()
    }

    private fun btnBorrowerOnClickListener() {
        showBorrowerDirectory()
    }


    @SuppressLint("NotifyDataSetChanged")
    private fun btnAddOnClickListener() {
        val booktext = binding.book.text.toString()
        val borrowertext = binding.borrower.text.toString()
        val dateBorrowedText = binding.dateBorrowed.text.toString()
        val dateReturnText = binding.dateReturn.text.toString()
        if (booktext.isNullOrEmpty() || borrowertext.isNullOrEmpty() || dateBorrowedText.isNullOrEmpty() || dateReturnText.isNullOrEmpty() ||
            dateBorrowedText.equals("date borrowed", true) || dateReturnText.equals(
                "date return",
                true
            )
        ) {
            Snackbar.make(binding.root, "Please check empty fields", Snackbar.LENGTH_SHORT).show()
            return
        } else {
            transaction = Transaction(book, borrower)
            transaction.dateBorrowed = dateBorrowed
            transaction.dateReturn = dateReturn
            transactions.add(transaction)

            clearfields()
        }
    }

    private fun clearfields() {

        binding.book.text = null
        binding.borrower.text = null
        binding.dateBorrowed.setText(SimpleDateFormat("yyyy-MM-d").format(Date()).toString())
        binding.dateReturn.setText(SimpleDateFormat("yyyy-MM-d").format(Date()).toString())

        binding.list.adapter!!.notifyDataSetChanged()
        binding.list.requestFocus()
    }

    private fun showBookDirectory() {

        //Inflate the dialog as custom view
        restoreBook()

        bookDirectoryDialogBinding = BookDirectoryDialogBinding.inflate(layoutInflater)

        bookListAdapter =
            ArrayAdapter(mainActivity, android.R.layout.simple_list_item_1, bookList)

        // DataBind ListView with items from ArrayAdapter
        bookDirectoryDialogBinding.listBook.adapter = bookListAdapter


        // DataBind RecycleView with items from ArrayAdapter
//        bookAdapter = BookAdapter(resultBookList)

        bookDirectoryDialogBinding.filter.setOnKeyListener { v, keyCode, event ->
            Log.d("LIST_BOOK_SELECTED_KEY_LISTENER", "${bookDirectoryDialogBinding.filter.text}")
            filterOnKeyListener(bookDirectoryDialogBinding.filter.text.toString(), "Book")
        }

        bookDirectoryDialogBinding.listBook.setOnItemClickListener { parent, view, position, id ->
            selectedBookPosition = position
            Log.d("LIST_BOOK_SELECTED_LISTENER_2", "$position -- ${bookList[position]}")

        }


        showAlertDialog(bookDirectoryDialogBinding, "Books").show()
    }

    private fun showBorrowerDirectory() {

        //Inflate the dialog as custom view
        restoreBorrower()

        borrowerDirectoryDialogBinding = BorrowerDirectoryDialogBinding.inflate(layoutInflater)

        borrowerListAdapter =
            ArrayAdapter(mainActivity, android.R.layout.simple_list_item_1, borrowerList)

        // DataBind ListView with items from ArrayAdapter
        borrowerDirectoryDialogBinding.listBorrower.adapter = borrowerListAdapter


        // DataBind RecycleView with items from ArrayAdapter
//        bookAdapter = BookAdapter(resultBookList)

        borrowerDirectoryDialogBinding.filter.setOnKeyListener { v, keyCode, event ->
            Log.d("FILTER_BORROWER_KEY_LISTENER", "${borrowerDirectoryDialogBinding.filter.text}")
            filterOnKeyListener(borrowerDirectoryDialogBinding.filter.text.toString(), "Borrower")
        }

        borrowerDirectoryDialogBinding.listBorrower.setOnItemClickListener { parent, view, position, id ->
            selectedBorrowerPosition = position
            Log.d("LIST_BORROWER_SELECTED_LISTENER", "$position -- ${borrowerList[position]}")
//            selectBook(position)

        }

        showAlertDialog(borrowerDirectoryDialogBinding,"Borrowers").show()
    }

    fun showAlertDialog(
        view: ViewBinding, title: String,
    ): AlertDialog {
        val alertDialog: AlertDialog? = activity?.let {

            val builder = AlertDialog.Builder(activity)
            builder.setView(view.root)

            builder.apply {
                setPositiveButton("Select",
                    DialogInterface.OnClickListener { dialog, id ->
                        // User clicked OK button
                        when (title) {
                            "Books" -> {
                                if(selectedBookPosition > -1) {
                                    binding.book.setText(bookList[selectedBookPosition])
                                }
                            }
                            "Borrowers" -> {
                                if(selectedBorrowerPosition > -1) {
                                    binding.borrower.setText(borrowerList[selectedBorrowerPosition])
                                }
                            }
                            else -> {
                                selectedBookPosition = -1
                                selectedBorrowerPosition = -1
                            }
                        }
                    })
                setNegativeButton("Cancel",
                    DialogInterface.OnClickListener { dialog, id ->
                        // User cancelled the dialog
                    })
            }
            // Set other dialog properties

            // Create the AlertDialog
            builder.create()
        }

        return alertDialog!!
    }

    private fun filterOnKeyListener(filter: String, directory: String): Boolean {
        var bookList = ArrayList<String>(10)
        var borrowerList = ArrayList<String>(10)
        selectedBookPosition = -1
        selectedBorrowerPosition = -1

        when (directory) {
            "Book" -> {
                for (book in books) {
                    if (!filter.isNullOrEmpty() && book.bookName!!.contains(filter,true) || book.description!!.contains(filter, true)) {
                        this.book = book
                        bookList.add("${book.bookName} - ${book.description}")
                    }else{
                        bookList = this.bookList
                    }
                }
                bookListAdapter.notifyDataSetChanged();
            }
            "Borrower" -> {
                for (borrower in borrowers) {
                    if (!filter.isNullOrEmpty() && borrower.firstName!!.contains(filter, true) || borrower.lastName!!.contains(filter,true)) {
                        this.borrower = borrower
                        borrowerList.add(borrower.getDetails())
                    }else{
                        borrowerList = this.borrowerList
                    }
                }
                borrowerListAdapter.notifyDataSetChanged();
            }
        }

        return false
    }


}
