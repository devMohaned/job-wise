package com.job.wise.presentation.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.job.wise.R
import com.job.wise.data.models.Company


class CompanySpinnerAdapter(
    context: Context, val list: List<Company>
) : ArrayAdapter<Company>(
    context, 0,
    list
) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val company: Company = list[position]
        val row: View = LayoutInflater.from(context).inflate(
            R.layout.item_spinner_company, parent,
            false
        )
        (row.findViewById(R.id.itemSpinnerCompanyName) as TextView).text = company.name
        return row
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        val company: Company = list[position]
        val row: View = LayoutInflater.from(context).inflate(
            R.layout.item_spinner_company, parent,
            false
        )
        (row.findViewById(R.id.itemSpinnerCompanyName) as TextView).text = company.name
        return row
    }

}