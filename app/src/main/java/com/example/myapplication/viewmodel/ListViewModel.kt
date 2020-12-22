package com.example.myapplication.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import com.example.myapplication.di.AppModule
import com.example.myapplication.di.CONTEXT_APP
import com.example.myapplication.di.DaggerViewModelComponent
import com.example.myapplication.di.TypeOfContext
import com.example.myapplication.model.Animal
import com.example.myapplication.model.AnimalApiService
import com.example.myapplication.model.ApiKey
import com.example.myapplication.util.SharedPreferenceHelper
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ListViewModel(application: Application): AndroidViewModel(application) {

    constructor(application: Application, test: Boolean = true): this(application) {
        injected = true
    }

    val animals by lazy { MutableLiveData<List<Animal>>() }
    val loadError by lazy { MutableLiveData<Boolean>() }
    val loading by lazy { MutableLiveData<Boolean>() }

    //    CompositeDisposable is just a class to keep all your disposables in the same place to you can dispose all of then at once.
    //    Disposable is  an interface for a class that can be disposed. Observable, Single, Completable and Maybe all implements this interface.
    //    It is important to dispose your observables to avoid memory leaks
    private val disposable = CompositeDisposable()
//    private val apiService = AnimalApiService()
//    Refactor to Dagger
    @Inject
    lateinit var apiService: AnimalApiService

    //    Store API Key If already have one
//    private val prefs = SharedPreferenceHelper(getApplication())
//    Refactor to Dagger
    @Inject
    @field:TypeOfContext(CONTEXT_APP)
    lateinit var prefs: SharedPreferenceHelper

    private var invalidApiKey = false
    private var injected = false

    fun inject() {
//        DaggerViewModelComponent.create().inject(this)
        if(!injected) {
            DaggerViewModelComponent.builder()
                .appModule(AppModule(getApplication()))
                .build()
                .inject(this)
        }
    }

    fun refresh() {
        inject()
        loading.value = true
        invalidApiKey = false
        val key: String? = prefs.getApiKey()
        if (key.isNullOrEmpty()) {
            getKey()
        } else {
            getAnimals(key)
        }
    }

    fun hardRefresh() {
        inject()
        loading.value = true
        getKey()
    }

    private fun getKey() {
        disposable.add(
            apiService.getApiKey()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<ApiKey>() {
                    override fun onSuccess(key: ApiKey) {
                        if(key.key.isNullOrEmpty()) {
                            loadError.value = true
                            loading.value = false
                        } else {
                            prefs.saveApiKey(key.key)
                            getAnimals(key.key)
                        }
                    }

                    override fun onError(e: Throwable) {
                        e.printStackTrace()
                        loading.value = false
                        loadError.value = true
                    }
                })
        )

    }

    private fun getAnimals(key: String) {
        disposable.add(
            apiService.getAnimals(key)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object: DisposableSingleObserver<List<Animal>>() {
                    override fun onSuccess(list: List<Animal>) {
                        loadError.value = false
                        animals.value = list
                        loading.value = false
                    }

                    override fun onError(e: Throwable) {
                        if (!invalidApiKey) {
                            invalidApiKey = true
                            getKey()
                        } else {
                            e.printStackTrace()
                            loading.value = false
                            animals.value = null
                            loadError.value = true
                        }
                    }
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }

    /*private fun getAnimals() {
        val a1 = Animal("Alligator")
        val a2 = Animal("Cat")
        val a3 = Animal("Dog")
        val a4 = Animal("Bee")
        val a5 = Animal("Elephant")
        val a6 = Animal("Flamingo")

        val animalList : ArrayList<Animal> = arrayListOf(a1, a2, a3, a4, a5, a6)

        animals.value = animalList
        loadError.value = false
        loading.value = false
    }*/
}