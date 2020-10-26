package com.banjodayo.agromall.data

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.paging.DataSource
import androidx.paging.PagedList
import androidx.paging.toLiveData
import com.banjodayo.agromall.api.FarmerApiService
import com.banjodayo.agromall.utils.*
import io.mockk.*
import kotlinx.coroutines.*
import kotlinx.coroutines.test.resetMain
import org.junit.After
import org.junit.Test
import org.junit.Before
import org.junit.Rule
import org.koin.core.logger.Level
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule

@ExperimentalCoroutinesApi
class FarmerRepositoryTest : KoinTest {

    private lateinit var repository: FarmerRepository
    private lateinit var dao: FarmerDAO
    private lateinit var api: FarmerApiService
    private lateinit var responseHandler : ResponseHandler
    private val farmer =  Farmer("id1",
        "fristname", "surname", "address", "state", "city", "")
    private val farmerList = listOf<Farmer>(
        farmer
    )

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
                single { responseHandler }
                single { dao}
                single { api }
                factory{ FarmerRepository() }
            }
        )
    }

    @Before
    fun setUp(){
        dao = mockk(relaxed = true)
        api = mockk()
        responseHandler = mockk(relaxed = true)
        repository = FarmerRepository()
    }

    /*
    * // test repository saves results into the database.
        val dbData = MutableLiveData<List<Repo>>()
        every { dao.getRepos() } returns dbData
        // mock api response
        val repos = createRepos()
        val repoResponse = ApiUtil.successCall(repos)
        every { client.getRepos() } returns repoResponse

        // mock the observer
        val observer = mockk<Observer<Resource<List<Repo>>>>(relaxed = true)
        repository.getRepos().observeForever(observer)
        // we are getting repos from db
        verify { observer.onChanged(Resource.loading(null)) }
        verify { dao.getRepos() }
        // make sure no web calls at this point
        verify { client.getRepos() wasNot Called }

        // return null data from db
        dbData.postValue(null)
        // verify data fetched from remote and saved
        verify { client.getRepos() }
        verify { dao.save(repos) }

        // db calls that there is new data
        dbData.postValue(repos)
        // new db data post to repo listener
        verify { observer.onChanged(Resource.success(repos)) }*/

    @Test
    fun testRepoFetchAndStore() = mainCoroutineRule.runBlockingTest{

        coEvery { api.getFarmerList().data?.farmers } returns farmerList
        api.getFarmerList().data?.farmers

        //verify we get data into the repo
        assert(repository.getFarmerData().data != null)

        //verify data is save into db
        verify { dao.addFarmer(farmer) }
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain() // reset main dispatcher to the original Main dispatcher
    }}