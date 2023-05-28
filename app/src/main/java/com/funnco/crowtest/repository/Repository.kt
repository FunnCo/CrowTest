package com.funnco.crowtest.repository

import android.util.Log
import com.funnco.crowtest.common.model.common.QuestionModel
import com.funnco.crowtest.common.model.request.UserRegisterModel
import com.funnco.crowtest.common.model.request.SolvedTestModel
import com.funnco.crowtest.common.model.request.UserLoginModel
import com.funnco.crowtest.common.model.response.*
import com.funnco.crowtest.common.retrofit.RetrofitObject
import com.google.gson.Gson
import kotlinx.coroutines.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

object Repository {

    private var downloadedFinishedTests: List<ResponseSolvedTestInfo> = emptyList()
    private var downloadedTests: List<TestInfoModel> = emptyList()

    private lateinit var token: String

    fun register(userModel: UserRegisterModel, callback: (code: Int, token: TokenHolder?) -> Unit) {
        RetrofitObject.authAPI.registerUser(userModel).enqueue(object : Callback<TokenHolder> {
            override fun onResponse(call: Call<TokenHolder>, response: Response<TokenHolder>) {
                if (response.isSuccessful && response.code() == 200) {
                    token = "Bearer " + response.body()!!.token
                    Log.i(this@Repository.javaClass.simpleName, "Registration is successful")
                } else {
                    Log.e(
                        this@Repository.javaClass.simpleName,
                        "Registration is failed. \ncode: ${response.code()}\nmessage: ${response.message()}"
                    )
                }
                callback(response.code(), response.body())
            }

            override fun onFailure(call: Call<TokenHolder>, t: Throwable) {
                Log.e(
                    this@Repository.javaClass.simpleName,
                    "Registration is failed. \nerror: ${t.message}"
                )
                callback(-1, null)
            }
        })
    }

    fun login(credentials: UserLoginModel, callback: (code: Int, token: TokenHolder?) -> Unit) {
        RetrofitObject.authAPI.login(credentials).enqueue(object : Callback<TokenHolder> {
            override fun onResponse(call: Call<TokenHolder>, response: Response<TokenHolder>) {
                if (response.isSuccessful && response.code() == 200) {
                    token = "Bearer " + response.body()!!.token
                    Log.i(
                        this@Repository.javaClass.simpleName,
                        "Auth is successful with token ${token}"
                    )
                } else {
                    Log.e(
                        this@Repository.javaClass.simpleName,
                        "Auth is failed. \ncode: ${response.code()}\nmessage: ${response.message()}"
                    )
                }
                callback(response.code(), response.body())
            }

            override fun onFailure(call: Call<TokenHolder>, t: Throwable) {
                Log.e(this@Repository.javaClass.simpleName, "Auth is failed. \nerror: ${t.message}")
                callback(-1, null)
            }
        })
    }

    fun sendAnswers(
        testId: String,
        solvedTestModel: SolvedTestModel,
        callback: (ResponseSolvedTestInfo?) -> Unit
    ) {

        RetrofitObject.testAPI.postAnswersOnTest(token, testId, solvedTestModel)
            .enqueue(object : Callback<ResponseSolvedTestInfo> {
                override fun onResponse(
                    call: Call<ResponseSolvedTestInfo>,
                    response: Response<ResponseSolvedTestInfo>
                ) {
                    if (response.isSuccessful && response.code() == 200) {
                        Log.i(
                            this@Repository.javaClass.simpleName,
                            "Request for posting answers is successful"
                        )
                        val tempList = downloadedFinishedTests.toMutableList()
                        tempList.add(response.body()!!)
                        downloadedFinishedTests = tempList
                        callback(response.body())
                    } else {
                        Log.e(
                            this@Repository.javaClass.simpleName,
                            "Request for posting answers is failed. \ncode: ${response.code()}\nmessage: ${response.raw()}"
                        )
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<ResponseSolvedTestInfo>, t: Throwable) {
                    Log.e(
                        this@Repository.javaClass.simpleName,
                        "Request for posting answers is failed. \nerror: ${t.message}"
                    )
                    callback(null)
                }
            })

    }

    fun getManyAuthors(ids: List<String>, callback: (response: List<UserInfoModel>) -> Unit) {
        MainScope().launch(Dispatchers.Main) {
            val resultList = mutableListOf<UserInfoModel>()
            for (author in ids) {
                suspendCoroutine {continuation ->
                    getAuthor(author) {
                        resultList.add(it!!)
                        continuation.resume("resultString")
                    }
                }
            }
            callback(resultList)
        }
    }

    fun loadQuestions(
        testId: String,
        callback: (questions: List<QuestionModel>?, test: TestInfoModel?) -> Unit
    ) {
        RetrofitObject.testAPI.getQuestions(token, testId).enqueue(object : Callback<List<Any>> {
            override fun onResponse(call: Call<List<Any>>, response: Response<List<Any>>) {
                if (response.isSuccessful && response.code() == 200) {
                    Log.i(
                        this@Repository.javaClass.simpleName,
                        "Request for getting questions is successful"
                    )

                    // Parsing ResponseBody to BaseQuestion
                    val resultList = mutableListOf<QuestionModel>()
                    for (item in response.body()!!) {
                        val gson = Gson()
                        val tempQuestion =
                            gson.fromJson(gson.toJson(item), QuestionModel::class.java)
                        resultList.add(tempQuestion)
                    }
                    callback(resultList, getTestById(testId))
                } else {
                    Log.e(
                        this@Repository.javaClass.simpleName,
                        "Request for getting questions is failed. \ncode: ${response.code()}\nmessage: ${response.message()}"
                    )
                    callback(null, null)
                }
            }

            override fun onFailure(call: Call<List<Any>>, t: Throwable) {
                Log.e(
                    this@Repository.javaClass.simpleName,
                    "Request for getting questions is failed. \nerror: ${t.message}"
                )
                callback(null, null)
            }

        })
    }

    fun getAuthor(userId: String, callback: (user: UserInfoModel?) -> Unit) {
        RetrofitObject.authAPI.getInfoAboutSomeone(token, userId)
            .enqueue(object : Callback<UserInfoModel> {
                override fun onResponse(
                    call: Call<UserInfoModel>,
                    response: Response<UserInfoModel>
                ) {
                    if (response.isSuccessful && response.code() == 200) {
                        Log.i(
                            this@Repository.javaClass.simpleName,
                            "Request for getting author info is successful"
                        )
                        callback(response.body())
                    } else {
                        Log.e(
                            this@Repository.javaClass.simpleName,
                            "Request for getting author info is failed. \ncode: ${response.code()}\nmessage: ${response.message()}"
                        )
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<UserInfoModel>, t: Throwable) {
                    Log.e(
                        this@Repository.javaClass.simpleName,
                        "Request for getting author info is failed. \nerror: ${t.message}"
                    )
                    callback(null)
                }

            })
    }

    fun getTestById(testId: String, response: (TestInfoModel) -> Unit) {
        val tempResult = downloadedTests.find { it.testId == testId }
        if (tempResult != null) {
            response(tempResult)
            return
        }

        loadTestById(testId) {
            response(it)
        }
    }

    fun getTestById(testId: String): TestInfoModel {
        return downloadedTests.find { it.testId == testId }!!
    }

    fun parseDoneTestsInfo(
        info: List<ResponseSolvedTestInfo>,
        callback: (result: List<TestInfoModel>) -> Unit
    ) {
        MainScope().launch {
            val resultList = mutableListOf<TestInfoModel>()
            for (entry in info) {
                suspendCoroutine { continuation ->
                    getTestById(entry.testId) {
                        resultList.add(it)
                        continuation.resume(it)
                    }
                }
                // callback(resultList)
            }
            callback(resultList)
        }
    }


    private fun loadTestById(testId: String, callback: (result: TestInfoModel) -> Unit) {
        RetrofitObject.testAPI.getTestInfo(token, testId).enqueue(object : Callback<TestInfoModel> {
            override fun onResponse(call: Call<TestInfoModel>, response: Response<TestInfoModel>) {
                val tempList = downloadedTests.toMutableList()
                tempList.add(response.body()!!)
                downloadedTests = tempList
                callback(response.body()!!)
            }

            override fun onFailure(call: Call<TestInfoModel>, t: Throwable) {
            }

        })
    }

    fun loadAvailableTests(callback: (List<TestInfoModel>?) -> Unit) {
        RetrofitObject.testAPI.getAvailable(token).enqueue(object : Callback<List<TestInfoModel>> {
            override fun onResponse(
                call: Call<List<TestInfoModel>>,
                response: Response<List<TestInfoModel>>
            ) {
                if (response.isSuccessful && response.code() == 200) {
                    Log.i(
                        this@Repository.javaClass.simpleName,
                        "Request for getting available tests is successful"
                    )
                    downloadedTests = response.body()!!
                    callback(response.body())
                } else {
                    Log.e(
                        this@Repository.javaClass.simpleName,
                        "Request for getting available tests is failed. \ncode: ${response.code()}\nmessage: ${response.message()}"
                    )
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<TestInfoModel>>, t: Throwable) {
                Log.e(
                    this@Repository.javaClass.simpleName,
                    "Request for getting finished tests is failed. \nerror: ${t.message}"
                )
                callback(null)
            }

        })
    }

    fun loadDoneTests(callback: (tests: List<ResponseSolvedTestInfo>?) -> Unit) {
        RetrofitObject.testAPI.getFinished(token)
            .enqueue(object : Callback<List<ResponseSolvedTestInfo>> {
                override fun onResponse(
                    call: Call<List<ResponseSolvedTestInfo>>,
                    response: Response<List<ResponseSolvedTestInfo>>
                ) {
                    if (response.isSuccessful && response.code() == 200) {
                        Log.i(
                            this@Repository.javaClass.simpleName,
                            "Request for getting finished tests is successful"
                        )
                        downloadedFinishedTests = response.body()!!
                        parseDoneTestsInfo(downloadedFinishedTests) {
                            downloadedFinishedTests.forEach { info ->
                                info.title = it.find { it.testId == info.testId }?.title ?: ""
                            }

                            callback(downloadedFinishedTests)
                        }

                    } else {
                        Log.e(
                            this@Repository.javaClass.simpleName,
                            "Request for getting finished tests is failed. \ncode: ${response.code()}\nmessage: ${response.message()}"
                        )
                        callback(null)
                    }
                }

                override fun onFailure(call: Call<List<ResponseSolvedTestInfo>>, t: Throwable) {
                    Log.e(
                        this@Repository.javaClass.simpleName,
                        "Request for getting finished tests is failed. \nerror: ${t.message}"
                    )
                    callback(null)
                }

            })
    }

    fun getUserInfo(callback: (code: Int, user: UserInfoModel?) -> Unit) {
        RetrofitObject.authAPI.getInfo(token).enqueue(object : Callback<UserInfoModel> {
            override fun onResponse(call: Call<UserInfoModel>, response: Response<UserInfoModel>) {
                if (response.isSuccessful && response.code() == 200) {
                    Log.i(
                        this@Repository.javaClass.simpleName,
                        "Request for getting user info is successful"
                    )
                    callback(200, response.body())
                } else {
                    Log.e(
                        this@Repository.javaClass.simpleName,
                        "Request for getting user info is failed. \ncode: ${response.code()}\nmessage: ${response.message()}"
                    )
                }
            }

            override fun onFailure(call: Call<UserInfoModel>, t: Throwable) {
                Log.e(
                    this@Repository.javaClass.simpleName,
                    "Request for getting user info is failed. \nerror: ${t.message}"
                )
            }

        })
    }

    fun getTestResults(testId: String): ResponseSolvedTestInfo {
        return downloadedFinishedTests.find { it.testId == testId }!!
    }

    fun getStats(callback: (result: StatsModel) -> Unit) {
        RetrofitObject.statsAPI.getStats(token).enqueue(object : Callback<StatsModel> {
            override fun onResponse(call: Call<StatsModel>, response: Response<StatsModel>) {
                if (response.isSuccessful && response.code() == 200) {
                    Log.i(
                        this@Repository.javaClass.simpleName,
                        "Request for getting stats is successful"
                    )
                    callback(response.body()!!)
                } else {
                    Log.e(
                        this@Repository.javaClass.simpleName,
                        "Request for getting stats is failed. \ncode: ${response.code()}\nmessage: ${response.message()}"
                    )
                }
            }

            override fun onFailure(call: Call<StatsModel>, t: Throwable) {
                Log.e(
                    this@Repository.javaClass.simpleName,
                    "Request for getting stats is failed. \nerror: ${t.message}"
                )
            }

        })
    }
}