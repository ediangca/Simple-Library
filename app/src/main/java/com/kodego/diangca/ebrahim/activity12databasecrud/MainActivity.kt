package com.kodego.diangca.ebrahim.activity12databasecrud

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.kodego.diangca.ebrahim.activity12databasecrud.adapter.BorrowerAdapter
import com.kodego.diangca.ebrahim.activity12databasecrud.dao.BorrowerDAO
import com.kodego.diangca.ebrahim.activity12databasecrud.dao.BorrowerDAOSQLImp
import com.kodego.diangca.ebrahim.activity12databasecrud.databinding.ActivityMainBinding
import com.kodego.diangca.ebrahim.activity12databasecrud.model.Book
import com.kodego.diangca.ebrahim.activity12databasecrud.model.Borrower
import com.kodego.diangca.ebrahim.activity12databasecrud.model.Transaction
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding
    private lateinit var userAdapter: BorrowerAdapter
    private var librarians: ArrayList<Borrower> = ArrayList()


    var transactions: ArrayList<Transaction> = ArrayList()


//    private lateinit var borrowDAO: BorrowerDAO
//    private lateinit var borrower: Borrower
    var books: ArrayList<Book> = ArrayList()


    lateinit var borrowDAO: BorrowerDAOSQLImp
    private lateinit var borrower: Borrower
    var borrowers: ArrayList<Borrower> = ArrayList()

    private lateinit var mainFrame: FragmentTransaction
    private lateinit var homeFragment: HomeFragment
    private lateinit var bookFragment: BookFragment
    private lateinit var borrowerFragment: BorrowerFragment

    private fun initBooks() {
        books.add(Book("English-1", "Basic English"))
        books.add(Book("Math-1", "Algebra"))
        books.add(Book("Math-2", "Trigonometry"))
        books.add(Book("Math-3", "Calculus"))
        books.add(Book("Science-1", "Geology"))
        books.add(Book("Science-2", "Biology"))
        books.add(Book("Science-3", "Chemistry"))
        books.add(Book("Science-4", "Physics"))
        books.add(Book("Filipino-1", "Basic Filipino"))

        showListBook()
    }

    private fun showListBook() {
        for (book in books) {
            println(book.getDetails())
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        iniComponent()
        initBooks()

    }

    private fun iniComponent() {
        borrowDAO = BorrowerDAOSQLImp(applicationContext)
        borrowers = borrowDAO.getBorrowers()

        homeFragment = HomeFragment(this)
        bookFragment = BookFragment(this)
        borrowerFragment = BorrowerFragment(this)


        mainFrame = supportFragmentManager.beginTransaction()
        mainFrame.replace(R.id.mainFrameLayout, HomeFragment(this))
        mainFrame.commit()


        binding.mainNav.setOnItemSelectedListener {
            mainNavOnItemSelectedListener(it)
        }

    }

    private fun mainNavOnItemSelectedListener(it: MenuItem): Boolean {
        Log.d("MENU ITEM ", "ID: ${it.itemId}")
        when (it.itemId) {
            R.id.navHome -> {
                openFragment(homeFragment)
                return true
            }
            R.id.navBook -> {
                openFragment(bookFragment)
                return true
            }
            R.id.navSettings -> {
                openFragment(borrowerFragment)
                return true
            }
            R.id.navLogout -> {
                logout()
                return true
            }
        }
        return false
    }

    private fun openFragment(fragment: Fragment) {
        mainFrame = supportFragmentManager.beginTransaction()
        mainFrame.replace(R.id.mainFrameLayout, fragment);
        mainFrame.addToBackStack(null);
        mainFrame.commit();
    }

    private fun logout() {
        var nextForm = Intent(this, MenuActivity::class.java)
        startActivity(Intent(nextForm))
        finish()
    }

    fun removeBookToMain(book: Book) {
        Log.d("REMOVE_BOOK", "REMOVED ${book.bookName} - ${book.description}")
        books.remove(book)
        bookFragment.clearFields()
    }

    fun removeBorrowerToMain(borrower: Borrower) {
        Log.d("REMOVE_BOOK", "REMOVED ${borrower.firstName} - ${borrower.lastName}")
        borrowers.remove(borrower)
        borrowerFragment.clearFields()
    }

}