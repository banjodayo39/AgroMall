package com.banjodayo.agromall.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.banjodayo.agromall.data.FarmerDAO
import com.banjodayo.agromall.data.FarmerList
import com.banjodayo.agromall.data.FarmerRepository
import com.banjodayo.agromall.utils.*
import com.nhaarman.mockitokotlin2.timeout
import io.mockk.coEvery
import io.mockk.mockk
import io.mockk.verify
import junit.extensions.TestSetup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.core.logger.Level
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import java.util.*

@ExperimentalCoroutinesApi
class FarmerViewModelTest: KoinTest{

    private lateinit var viewModel: FarmerViewModel
    private lateinit var responseHandler: ResponseHandler
    private lateinit var repository : FarmerRepository
    private lateinit var dao: FarmerDAO
    private val repoResponse  = mockk<Resource<ApiResponse<FarmerList>>>()


    @Rule
    @JvmField
    val instantExecutorRule = InstantTaskExecutorRule()

    @Rule
    @JvmField
    var mainCoroutineRule = MainCoroutineRule()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        printLogger(Level.DEBUG)
        modules(
            module {
                single { dao}
                single { responseHandler }
                factory{ FarmerRepository() }
            }
        )
    }

    @Before
    fun setUp(){
        dao = mockk(relaxed = true)
        repository = mockk()
        responseHandler = mockk(relaxed = true)
        viewModel = FarmerViewModel()

        coEvery { repository.getFarmerData() } returns repoResponse
    }

    @Test
    fun testViewModelFetch() = mainCoroutineRule.runBlockingTest{
        val observer = mockk<Observer<Resource<ApiResponse<FarmerList>>>>(relaxed= true)
        repository.getFarmerData()
        viewModel.getFarmerData().observeForever(observer)
        delay(10)
       com.nhaarman.mockitokotlin2.verify(observer, timeout(50)).onChanged(Resource.loading(null))
        com.nhaarman.mockitokotlin2.verify(observer, timeout(50)).onChanged(Resource.loading(repoResponse.data))
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
    }
}