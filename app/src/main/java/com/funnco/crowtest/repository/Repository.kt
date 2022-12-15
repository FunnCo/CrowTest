package com.funnco.crowtest.repository

import android.util.Log
import com.funnco.crowtest.common.model.TestModel
import com.funnco.crowtest.common.model.TokenHolder
import com.funnco.crowtest.common.model.UserModel
import com.funnco.crowtest.common.model.question_models.*
import com.funnco.crowtest.common.retrofit.RetrofitObject
import com.google.gson.Gson
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

object Repository {

    private lateinit var downloadedFinishedTests: List<TestModel>
    private lateinit var downloadedAvailableTests: List<TestModel>

    private lateinit var savedUser: UserModel
    private lateinit var token: String

    fun register(userModel: UserModel, callback: (code: Int) -> Unit ){
        RetrofitObject.authAPI.registerUser(userModel).enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful && response.code()==200){
                    Log.i(this@Repository.javaClass.simpleName, "Registration is successful")
                } else {
                    Log.e(this@Repository.javaClass.simpleName, "Registration is failed. \ncode: ${response.code()}\nmessage: ${response.message()}")
                }
                callback(response.code())
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(this@Repository.javaClass.simpleName, "Registration is failed. \nerror: ${t.message}")
                callback(-1)
            }
        })
    }

    fun login(mail: String, password: String, callback: (code: Int, token: TokenHolder?) -> Unit ){
        RetrofitObject.authAPI.login(mail, password).enqueue(object : Callback<TokenHolder> {
            override fun onResponse(call: Call<TokenHolder>, response: Response<TokenHolder>) {
                if(response.isSuccessful && response.code()==200){
                    Log.i(this@Repository.javaClass.simpleName, "Auth is successful")
                    token = response.body()!!.token
                } else {
                    Log.e(this@Repository.javaClass.simpleName, "Auth is failed. \ncode: ${response.code()}\nmessage: ${response.message()}")
                }
                callback(response.code(), response.body())
            }

            override fun onFailure(call: Call<TokenHolder>, t: Throwable) {
                Log.e(this@Repository.javaClass.simpleName, "Auth is failed. \nerror: ${t.message}")
                callback(-1, null)
            }
        })
    }

    fun login(token: String, callback: (user: UserModel?) -> Unit ){
        this.token = token
        RetrofitObject.authAPI.login(token).enqueue(object : Callback<UserModel> {
            override fun onResponse(call: Call<UserModel>, response: Response<UserModel>) {
                if(response.isSuccessful && response.code()==200){
                    Log.i(this@Repository.javaClass.simpleName, "Auth is successful")
                    savedUser = response.body() as UserModel
                    callback(savedUser)
                } else {
                    Log.e(this@Repository.javaClass.simpleName, "Auth is failed. \ncode: ${response.code()}\nmessage: ${response.message()}")
                    callback(null)
                }

            }

            override fun onFailure(call: Call<UserModel>, t: Throwable) {
                Log.e(this@Repository.javaClass.simpleName, "Auth is failed. \nerror: ${t.message}")
                callback(null)
            }
        })
    }


    fun sendAnswers(testId: String, questions: List<BaseQuestion>, solvingTime: Double, callback: (TestModel?) -> Unit) {

        RetrofitObject.testAPI.postAnswersOnTest(token, getTestById(testId, false).id, solvingTime, questions).enqueue(object: Callback<Void>{
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if(response.isSuccessful && response.code()==200){
                    Log.i(this@Repository.javaClass.simpleName, "Request for posting answers is successful")
                    getTestById(testId, true){
                        callback(it)
                    }
                } else {
                    Log.e(this@Repository.javaClass.simpleName, "Request for posting answers is failed. \ncode: ${response.code()}\nmessage: ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Log.e(this@Repository.javaClass.simpleName, "Request for posting answers is failed. \nerror: ${t.message}")
                callback(null)
            }

        })

//        getTestById(testId, false){
//            response(it)
//        }
    }

    fun loadQuestions(
        testId: String,
        callback: (questions: List<BaseQuestion>?, test: TestModel?) -> Unit
    ) {
        RetrofitObject.testAPI.getQuestions(token, testId).enqueue(object : Callback<List<Any>>{
            override fun onResponse(call: Call<List<Any>>, response: Response<List<Any>>) {
                if(response.isSuccessful && response.code()==200){
                    Log.i(this@Repository.javaClass.simpleName, "Request for getting questions is successful")

                    // Parsing ResponseBody to BaseQuestion
                    val resultList = mutableListOf<BaseQuestion>()
                    for (item in response.body()!!){
                        val gson = Gson()
                        val tempQuestion = gson.fromJson(gson.toJson(item), BaseQuestion::class.java)
                        when(tempQuestion.type){
                            "one_answer" -> {
                                resultList.add(gson.fromJson(gson.toJson(item), OneAnswerQuestion::class.java))
                            }
                            "multiple_answer" -> {
                                resultList.add(gson.fromJson(gson.toJson(item), MultipleAnswerQuestion::class.java))
                            }
                            "input_answer" -> {
                                resultList.add(gson.fromJson(gson.toJson(item), InputQuestion::class.java))
                            }
                            "accordance_answer" -> {
                                resultList.add(gson.fromJson(gson.toJson(item), AccordanceQuestion::class.java))
                            }
                        }
                    }
                    callback(resultList, getTestById(testId, false))
                } else {
                    Log.e(this@Repository.javaClass.simpleName, "Request for getting questions is failed. \ncode: ${response.code()}\nmessage: ${response.message()}")
                    callback(null, null)
                }
            }

            override fun onFailure(call: Call<List<Any>>, t: Throwable) {
                Log.e(this@Repository.javaClass.simpleName, "Request for getting questions is failed. \nerror: ${t.message}")
                callback(null, null)
            }

        })


    }

    fun getTestById(testId: String, isFinished: Boolean, response: (TestModel) -> Unit) {
        if(isFinished){
            loadDoneTests { result ->
                response(result!!.find { it.id == testId }!!)
            }
        }
        else {
            loadAvailableTests { result ->
                response(result!!.find { it.id == testId }!!)
            }
        }
    }

    fun getTestById(testId: String, isFinished: Boolean):TestModel {
        return if(isFinished) downloadedFinishedTests.find { it.id == testId }!! else downloadedAvailableTests.find { it.id == testId }!!
    }

    fun loadAvailableTests(callback: (List<TestModel>?) -> Unit) {
        RetrofitObject.testAPI.getAvailable(token).enqueue(object: Callback<List<TestModel>>{
            override fun onResponse(
                call: Call<List<TestModel>>,
                response: Response<List<TestModel>>
            ) {
                if(response.isSuccessful && response.code()==200){
                    Log.i(this@Repository.javaClass.simpleName, "Request for getting available tests is successful")
                    downloadedAvailableTests = response.body()!!
                    callback(response.body())
                } else {
                    Log.e(this@Repository.javaClass.simpleName, "Request for getting available tests is failed. \ncode: ${response.code()}\nmessage: ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<TestModel>>, t: Throwable) {
                Log.e(this@Repository.javaClass.simpleName, "Request for getting finished tests is failed. \nerror: ${t.message}")
                callback(null)
            }

        })
    }

    fun loadDoneTests(callback: (tests: List<TestModel>?) -> Unit) {
        RetrofitObject.testAPI.getFinished(token).enqueue(object: Callback<List<TestModel>>{
            override fun onResponse(
                call: Call<List<TestModel>>,
                response: Response<List<TestModel>>
            ) {
                if(response.isSuccessful && response.code()==200){
                    Log.i(this@Repository.javaClass.simpleName, "Request for getting finished tests is successful")
                    downloadedFinishedTests = response.body()!!
                    callback(response.body())
                } else {
                    Log.e(this@Repository.javaClass.simpleName, "Request for getting finished tests is failed. \ncode: ${response.code()}\nmessage: ${response.message()}")
                    callback(null)
                }
            }

            override fun onFailure(call: Call<List<TestModel>>, t: Throwable) {
                Log.e(this@Repository.javaClass.simpleName, "Request for getting finished tests is failed. \nerror: ${t.message}")
                callback(null)
            }

        })

//        response(
//            listOf(
//                TestModel(
//                    "uuid1",
//                    "Параллельность плоскостей",
//                    "Самостоятельная работа по теме параллельность плоскостей",
//                    "01.12.2022",
//                    "01.12.2022",
//                    "01.12.2022",
//                    "5",
//                    30,
//                    11f
//                ),
//                TestModel(
//                    "uuid2",
//                    "Физкультура",
//                    "Разминка и подборка упражнений №5",
//                    "20.11.2022",
//                    "20.11.2022",
//                    "01.12.2022",
//                    "2",
//                    1,
//                    28.0f
//                ),
//                TestModel(
//                    "uuid3",
//                    "С++, циклы",
//                    "Проверочный тест",
//                    "01.12.2022",
//                    "01.12.2022",
//                    "01.12.2022",
//                    "3",
//                    15,
//                    12.4f
//                ),
//                TestModel(
//                    "uuid4",
//                    "Геном человека",
//                    "Домашняя работа по параграфу 12",
//                    "15.01.2023",
//                    "15.01.2023",
//                    "15.01.2023",
//                    "4",
//                    20,
//                    0.5f
//                ),
//            )
//        )
    }
}