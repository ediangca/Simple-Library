package com.kodego.diangca.ebrahim.activity12databasecrud.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kodego.diangca.ebrahim.activity12databasecrud.MainActivity
import com.kodego.diangca.ebrahim.activity12databasecrud.databinding.BookItemBinding
import com.kodego.diangca.ebrahim.activity12databasecrud.model.Book

class BookAdapter(var mainActivity: MainActivity, var books: ArrayList<Book>) :
    RecyclerView.Adapter<BookAdapter.BookViewHolder>() {


    override fun getItemCount(): Int {
        return books.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BookAdapter.BookViewHolder {
        val itemBinding =
            BookItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BookViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BookAdapter.BookViewHolder, position: Int) {
        holder.bindBook(books[position])
    }

    inner class BookViewHolder(private val itemBinding: BookItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        var book = Book()
        fun bindBook(book: Book) {
            this.book = book
            itemBinding.bookName.text = book.bookName
            itemBinding.bookDesc.text = book.description

            itemBinding.btnRemove.setOnClickListener {
                btnRemoveOnClickListener(itemBinding, adapterPosition, book)
            }
        }

        override fun onClick(v: View?) {
            if (v!=null)
                Snackbar.make(
                    itemBinding.root,
                    "${itemBinding.bookName} ${itemBinding.bookDesc}",
                    Snackbar.LENGTH_SHORT
                ).show()
        }
    }

    private fun btnRemoveOnClickListener(
        itemBinding: BookItemBinding,
        adapterPosition: Int,
        book: Book,
    ) {
        var alertDialogBuilder = AlertDialog.Builder(mainActivity)
        alertDialogBuilder.setTitle("Delete?")
        alertDialogBuilder.setMessage("Do you want to delete the selected book ?")
        alertDialogBuilder.setNegativeButton("Cancel", null)
        alertDialogBuilder.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, position: Int) {
                books.removeAt(adapterPosition)
                mainActivity.removeBookToMain(book)
                //mainActivity.books.removeAt(adapterPosition)
                notifyItemRemoved(adapterPosition);
                notifyItemRangeChanged(adapterPosition, itemCount);
                Snackbar.make(
                    itemBinding.root,
                    "Data has been successfully removed.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }).show()
    }

}