package com.example.flow

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.flowWithLifecycle
import androidx.lifecycle.lifecycleScope
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.flow.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class MainActivity() : AppCompatActivity(R.layout.activity_main) {

    private val viewModel by viewModels<MainViewModel>()

    private val binding by viewBinding(ActivityMainBinding::bind)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initButtons(binding, viewModel)
        subscribeToObservables(binding, viewModel)
    }

    private fun initButtons(
        activityMainBinding: ActivityMainBinding,
        mainViewModel: MainViewModel
    ) {

        activityMainBinding.btnLiveData.setOnClickListener {
            mainViewModel.triggerLiveData()
        }

        activityMainBinding.btnFlow.setOnClickListener {
            lifecycleScope.launch {
                mainViewModel.triggerFlow().collectLatest {
                    activityMainBinding.tvFlow.text = it.toString()
                }
            }
        }

        activityMainBinding.btnStateFlow.setOnClickListener {
            mainViewModel.triggerStateFlow()
        }

        activityMainBinding.btnSharedFlow.setOnClickListener {
            mainViewModel.triggerSharedFlow()
        }

        activityMainBinding.btnChannel.setOnClickListener {
            mainViewModel.triggerChanel()
        }
    }


    private fun subscribeToObservables(
        activityMainBinding: ActivityMainBinding,
        mainViewModel: MainViewModel
    ) {

        mainViewModel.liveData.observe(this) {
            activityMainBinding.tvLiveData.text = it
        }

        lifecycleScope.launchWhenStarted {
            mainViewModel.stateFlow.collectLatest {
                Snackbar.make(
                    activityMainBinding.root,
                    it,
                    Snackbar.LENGTH_SHORT
                ).show()

                activityMainBinding.tvStateFlow.text = it
            }
        }


            mainViewModel.sharedFlow
                .flowWithLifecycle(lifecycle, Lifecycle.State.STARTED)
                .onEach {
                    activityMainBinding.tvSharedFlow.text = it
                    Snackbar.make(
                        activityMainBinding.root,
                        it,
                        Snackbar.LENGTH_SHORT
                    ).show()
                }
                .flowOn(Dispatchers.Main)
                .launchIn(lifecycleScope)



        lifecycleScope.launchWhenStarted {
            mainViewModel.channel.collectLatest {
                activityMainBinding.tvChannel.text = it
                Snackbar.make(
                    activityMainBinding.root,
                    it,
                    Snackbar.LENGTH_SHORT
                ).show()
            }
        }
    }

}