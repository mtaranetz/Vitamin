package com.example.vitamin

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.FragmentManager

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [MenuFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MenuFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view: View = inflater.inflate(R.layout.fragment_menu, container, false)
        // Inflate the layout for this fragment
        val p1: ImageView = view.findViewById(R.id.person1)
//        p1.setOnClickListener {
//            val fM: FragmentManager =
//            FragmentManager fragmentManager = getFragmentManager()
//            fragmentManager.beginTransaction()
//                .remove(fragment1)
//                .add(R.id.fragment_container, fragment2)
//                .show(fragment3)
//                .hide(fragment4)
//                .commit();
//        }

        return view
    }
}