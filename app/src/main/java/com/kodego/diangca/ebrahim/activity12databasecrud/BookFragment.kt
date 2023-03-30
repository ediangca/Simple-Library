package com.kodego.diangca.ebrahim.activity12databasecrud

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.kodego.diangca.ebrahim.activity12databasecrud.adapter.BookAdapter
import com.kodego.diangca.ebrahim.activity12databasecrud.databinding.FragmentBookBinding
import com.kodego.diangca.ebrahim.activity12databasecrud.model.Book

class BookFragment(var mainActivity: MainActivity) : Fragment() {

    private var _binding: FragmentBookBinding? = null
    private val binding get() = _binding!!

    //    private lateinit var bookListAdapter: ArrayAdapter<*>
//    private var bookList = ArrayList<Book>()
    private var books = ArrayList<Book>()
    private lateinit var bookAdapter: BookAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d("ATTACH_BOOK_FRAGMENT", "BOOK_FRAGMENT_ATTACHED")
    }

    override fun onResume() {
        super.onResume()
        Log.d("ATTACH_BOOK_RESUME", "BOOK_FRAGMENT_RESUMED")
        books = mainActivity.books
    }

    override fun onPause() {
        super.onPause()
        Log.d("ATTACH_BOOK_PAUSE", "BOOK_FRAGMENT_PAUSED")
        mainActivity.books = books
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentBookBinding.inflate(layoutInflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        iniComponent()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }

    private fun iniComponent() {

        books = mainActivity.books

//        restoreBook()

        Log.d("ATTACH_BOOK_INIT", "BOOK_FRAGMENT_INIT >>> ${books.size}")

        bookAdapter = BookAdapter(mainActivity, books)

        binding.list.layoutManager = LinearLayoutManager(mainActivity)
        binding.list.adapter = bookAdapter

        binding.list.adapter!!.notifyDataSetChanged()
        bookAdapter!!.notifyDataSetChanged()

        binding.btnAdd.setOnClickListener {
            btnAddOnClickListener()
        }

        binding.searchBook.setOnKeyListener { v, keyCode, event ->
            Log.d("LIST_BOOK_SELECTED_KEY_LISTENER", "${binding.searchBook.text}")
            searchBookOnKeyListener(binding.searchBook.text.toString())
        }

    }

    private fun btnAddOnClickListener() {
        val bookName = binding.bookName.text
        val bookDesc = binding.bookDesc.text
        if (bookName.isNullOrEmpty() || bookDesc.isNullOrEmpty()) {
            Snackbar.make(binding.root, "Please check empty fields!", Snackbar.LENGTH_SHORT).show()
            return
        } else {
            books.add(Book(bookName.toString(), bookDesc.toString()))
            Snackbar.make(binding.root, "Book has been successfully added !", Snackbar.LENGTH_SHORT).show()
            clearFields()
        }
    }

    public fun clearFields() {
        binding.bookName.text = null
        binding.bookDesc.text = null
        binding.searchBook.text = null

        binding.list.adapter!!.notifyDataSetChanged()
        bookAdapter!!.notifyDataSetChanged()
        binding.list.requestFocus()
    }

    /* private fun restoreBook() {
         bookList = books
     }*/

    private fun searchBookOnKeyListener(filter: String): Boolean {

        var bookList = ArrayList<Book>()

        for (book in books) {
            if (book.bookName!!.contains(filter, true) || book.description!!.contains(filter, true)) {
                bookList.add(book)
            }
        }

        bookAdapter = BookAdapter(mainActivity, bookList)

        binding.list.layoutManager = LinearLayoutManager(mainActivity)
        binding.list.adapter = bookAdapter

        binding.list.adapter!!.notifyDataSetChanged()
        bookAdapter!!.notifyDataSetChanged()

        return false
    }

}