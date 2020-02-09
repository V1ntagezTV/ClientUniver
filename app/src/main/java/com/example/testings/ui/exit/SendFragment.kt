package com.example.testings.ui.exit

import android.os.Bundle
import androidx.fragment.app.Fragment

class SendFragment : Fragment() {
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity!!.finish()
    }
}