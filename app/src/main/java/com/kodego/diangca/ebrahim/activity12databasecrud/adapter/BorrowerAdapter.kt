package com.kodego.diangca.ebrahim.activity12databasecrud.adapter

import android.app.AlertDialog
import android.content.DialogInterface
import android.graphics.*
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.kodego.diangca.ebrahim.activity12databasecrud.MainActivity
import com.kodego.diangca.ebrahim.activity12databasecrud.databinding.BorrowerItemBinding
import com.kodego.diangca.ebrahim.activity12databasecrud.model.Borrower

class BorrowerAdapter(var mainActivity: MainActivity, var borrowers: ArrayList<Borrower>) :
    RecyclerView.Adapter<BorrowerAdapter.BorrowerViewHolder>() {


    override fun getItemCount(): Int {
        return borrowers.size
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): BorrowerAdapter.BorrowerViewHolder {
        val itemBinding =
            BorrowerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BorrowerViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: BorrowerAdapter.BorrowerViewHolder, position: Int) {
        holder.bindStudent(borrowers[position])
    }

    inner class BorrowerViewHolder(
        private val itemBinding: BorrowerItemBinding,
    ) :
        RecyclerView.ViewHolder(itemBinding.root), View.OnClickListener {

        var borrower = Borrower()
        fun bindStudent(borrower: Borrower) {
            this.borrower = borrower
            itemBinding.studentName.text =
                "${borrower.borrowerId} \n ${borrower.lastName}, ${borrower.firstName} \n ${borrower.department}"
//            itemBinding.profilePic.setImageResource(student.img)

            val imageView = itemBinding.profilePic
            var bitmap = (AppCompatResources.getDrawable(
                mainActivity,
                borrower.img
            ) as BitmapDrawable).bitmap
            val imageRounded = Bitmap.createBitmap(bitmap.width, bitmap.height, bitmap.config)
            val canvas = Canvas(imageRounded)
            val paint = Paint()
            paint.isAntiAlias = true
            paint.shader = BitmapShader(bitmap, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)
            canvas.drawRoundRect(
                RectF(10F, 10F, bitmap.width.toFloat(), bitmap.height.toFloat()),
                200F, 200F, paint
            ) // Round Image Corner 100 100 100 100
            imageView.setImageBitmap(imageRounded)

            itemBinding.btnRemove.setOnClickListener {
                btnRemoveOnClickListener(itemBinding, adapterPosition, borrower)
            }
        }

        override fun onClick(view: View?) {
            Toast.makeText(
                mainActivity,
                "${borrower.borrowerId} ${borrower.lastName}, ${borrower.firstName}",
                Toast.LENGTH_SHORT
            ).show()
        }

        private fun btnRemoveOnClickListener(
            itemBinding: BorrowerItemBinding,
            positionAdapter: Int,
            borrower: Borrower,
        ) {

            var alertDialogBuilder = AlertDialog.Builder(mainActivity)
            alertDialogBuilder.setTitle("Delete?")
            alertDialogBuilder.setMessage("Are you sure you want to delete ${borrowers[positionAdapter].lastName}, ${borrowers[positionAdapter].firstName}")
            alertDialogBuilder.setNegativeButton("Cancel", null)
            alertDialogBuilder.setPositiveButton("Yes", object : DialogInterface.OnClickListener {
                override fun onClick(dialog: DialogInterface?, position: Int) {
                    removerBorrower(borrower, positionAdapter)
                }
            }).show()
        }

    }

    private fun removerBorrower(borrower: Borrower, positionAdapter: Int) {
        mainActivity.borrowDAO.deleteBorrower(borrower.borrowerId!!)
        borrowers.removeAt(positionAdapter)
//        mainActivity.removeBorrowerToMain(borrower)
        notifyDataSetChanged()
        notifyItemRemoved(positionAdapter);
        notifyItemRangeChanged(positionAdapter, itemCount);
    }
}