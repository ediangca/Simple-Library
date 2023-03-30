package com.kodego.diangca.ebrahim.activity12databasecrud.adapter

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kodego.diangca.ebrahim.activity12databasecrud.MainActivity
import com.kodego.diangca.ebrahim.activity12databasecrud.databinding.TransactionItemBinding
import com.kodego.diangca.ebrahim.activity12databasecrud.model.Borrower
import com.kodego.diangca.ebrahim.activity12databasecrud.model.Transaction

class TransactionAdapter(var mainActivity: MainActivity, var transactions: ArrayList<Transaction>) :
    RecyclerView.Adapter<TransactionAdapter.TransactionViewHolder>() {


    override fun getItemCount(): Int {
        return transactions.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): TransactionAdapter.TransactionViewHolder {
        val itemBinding =
            TransactionItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TransactionViewHolder(mainActivity.applicationContext, itemBinding)
    }

    override fun onBindViewHolder(holder: TransactionAdapter.TransactionViewHolder, position: Int) {
        holder.bindTransaction(transactions[position])
    }

    fun addStudent(librarian: Borrower) {

    }

    inner class TransactionViewHolder(
        private val context: Context,
        private val itemBinding: TransactionItemBinding
    ) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        var transaction = Transaction()
        fun bindTransaction(transaction: Transaction) {
            this.transaction = transaction
            itemBinding.borrowDetails.text = "${transaction.borrower!!.getDetails()} \n ${transaction.book!!.getDetails()}"
            itemBinding.profilePic.setImageResource(transaction.borrower!!.img)

            val imageView = itemBinding.profilePic
            var bitmap = (AppCompatResources.getDrawable(context, transaction.borrower!!.img) as BitmapDrawable).bitmap
            val imageRounded = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
            val canvas = Canvas(imageRounded)
            val paint = Paint()
            paint.isAntiAlias = true
            paint.shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            canvas.drawRoundRect(
                RectF(10F, 10F, bitmap.width.toFloat(), bitmap.height.toFloat()),
                200F, 200F, paint) // Round Image Corner 100 100 100 100
            imageView.setImageBitmap(imageRounded)

            itemBinding.btnRemove.setOnClickListener {
                btnRemoveOnClickListener(itemBinding, adapterPosition)
            }
        }

        override fun onClick(view: View?) {
            if (view!=null)
                Snackbar.make(
                    itemBinding.root,
                    "${itemBinding.profilePic} ${transaction.borrower!!.lastName}, ${transaction.borrower!!.firstName}",
                    Snackbar.LENGTH_SHORT
                ).show()
        }

    }

    private fun btnRemoveOnClickListener(itemBinding: TransactionItemBinding, positionAdapter: Int) {

        removeTransaction(itemBinding, positionAdapter)
    }

    private fun removeTransaction(itemBinding: TransactionItemBinding, positionAdapter: Int) {
        var alertDialogBuilder = AlertDialog.Builder(mainActivity)
        alertDialogBuilder.setTitle("Delete?")
        alertDialogBuilder.setMessage("Do you want to delete the selected data ?")
        alertDialogBuilder.setNegativeButton("Cancel", null)
        alertDialogBuilder.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
            override fun onClick(dialog: DialogInterface?, position: Int) {
                transactions.removeAt(positionAdapter)
                notifyItemRemoved(positionAdapter);
                notifyItemRangeChanged(positionAdapter, itemCount);
                Snackbar.make(
                    itemBinding.root,
                    "Data has been successfully removed.",
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }).show()
    }

}