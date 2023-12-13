package me.fitroh.londriforowner.data.dropdown

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView

class DropdownAdapter(context: Context, serviceList: List<Service>) :
    ArrayAdapter<Service>(context, 0, serviceList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup): View {
        val service = getItem(position)

        val view = convertView ?: LayoutInflater.from(context).inflate(
            android.R.layout.simple_spinner_dropdown_item, parent, false
        )

        val textView = view.findViewById<TextView>(android.R.id.text1)
        textView.text = service?.status

        return view
    }
}
