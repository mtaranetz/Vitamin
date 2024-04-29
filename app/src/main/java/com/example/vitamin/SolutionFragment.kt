package com.example.vitamin

import android.app.Activity
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import com.example.vitamin.databinding.FragmentSolutionBinding


class SolutionFragment : Fragment(), buttonClicked {
    //internal var activity: Activity? = null
    private var _binding: FragmentSolutionBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        context?.let { super.onAttach(it) }
        _binding = FragmentSolutionBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            citrusAnim.repeatCount = 1
            citrusAnim.playAnimation()
            var solutions = mutableListOf(
                "Это определённо того стоит!",
                "Вряд ли это хорошая идея...",
                "Ещё раз взвесь все за и против!",
                "Вселенная на твоей стороне =)",
                "За деньги - да!",
                "Категорически нет!")
            val textViewHandler : Handler = Handler()
            val switchTextOn = object : Runnable {
                override fun run() {
                    solutions.shuffle()
                    solutionTextView.text = solutions[0]
                    solutionTextView.isVisible = true
                }
            }
            textViewHandler.postDelayed(switchTextOn, 5000)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    override fun buttonClick(number: Number) {}


}