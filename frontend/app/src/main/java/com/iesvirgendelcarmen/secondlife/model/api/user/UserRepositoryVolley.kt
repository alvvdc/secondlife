package com.iesvirgendelcarmen.secondlife.model.api.user


import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.Token
import com.iesvirgendelcarmen.secondlife.model.User
import com.iesvirgendelcarmen.secondlife.model.UserWithoutId
import com.iesvirgendelcarmen.secondlife.model.api.VolleySingleton
import org.json.JSONObject


object UserRepositoryVolley: UserRepositoryDataSource {

    override fun getAllUsers(token: String, callback: UserRepositoryCallback.UsersCallback) {
        callback.onLoading()
        VolleySingleton.getInstance().requestQueue

        val stringRequest = object: StringRequest(Request.Method.GET, APIConfig.BASE_URL + "/" + APIConfig.USER_ROUTE, Listener { response ->
                val resp = Gson().fromJson<List<User>>(response, User::class.java)
                callback.onResponse(resp)
            },
            Response.ErrorListener { error ->
                callback.onError(error.message.toString())
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["x-access-token"] = token
                return headers
            }
        }
        VolleySingleton.getInstance().addToRequestQueue(stringRequest)
    }

    override fun getUser(id: String, token: String, callback: UserRepositoryCallback.UserCallback) {
        callback.onLoading()
        VolleySingleton.getInstance().requestQueue

        val stringRequest = object: StringRequest(Method.GET, APIConfig.BASE_URL + "/" + APIConfig.USER_ROUTE + "/" + id, Listener { response ->
            val resp = Gson().fromJson<User>(response, User::class.java)
            callback.onResponse(resp)
            },
            Response.ErrorListener { error ->
                callback.onError(error.message.toString())
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["x-access-token"] = token
                return headers
            }
        }
        VolleySingleton.getInstance().addToRequestQueue(stringRequest)
    }

    override fun editUser(user: User, token: String, callback: UserRepositoryCallback.UserCallback) {
        callback.onLoading()
        VolleySingleton.getInstance().requestQueue

        val jsonObject = JSONObject(Gson().toJson(user, User::class.java))

        val jsonObjectRequest = object: JsonObjectRequest(Method.PUT, APIConfig.BASE_URL + "/" + APIConfig.USER_ROUTE + "/" + user.id, jsonObject, Listener { response ->
            val resp = Gson().fromJson<User>(response.toString(), User::class.java)
            callback.onResponse(resp)
            Log.i("Alberto", response.toString())
        },
            Response.ErrorListener { error ->
                callback.onError(error.message.toString())
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["x-access-token"] = token
                return headers
            }
        }
        VolleySingleton.getInstance().addToRequestQueue(jsonObjectRequest)
    }

    override fun register(user: UserWithoutId, callback: UserRepositoryCallback.UserCallback) {
        callback.onLoading()
        VolleySingleton.getInstance().requestQueue

        val jsonObject = JSONObject(Gson().toJson(user, UserWithoutId::class.java))

        val jsonObjectRequest = JsonObjectRequest(Request.Method.POST, APIConfig.BASE_URL + "/register", jsonObject, Listener { response ->
            val resp = Gson().fromJson<User>(response.toString(), User::class.java)
            callback.onResponse(resp)
        },
            Response.ErrorListener { error ->
                callback.onError(error.message.toString())
            }
        )

        VolleySingleton.getInstance().addToRequestQueue(jsonObjectRequest)
    }


    override fun login(user: User, callback: UserRepositoryCallback.TokenCallback) {
        callback.onLoading()
        VolleySingleton.getInstance().requestQueue

        var json = JSONObject(Gson().toJson(user, User::class.java))

        val jsonRequest = object: JsonObjectRequest(Request.Method.POST, APIConfig.BASE_URL + "/login", json, Listener { response ->
            val resp = Gson().fromJson<Token>(response.toString(), Token::class.java)
            callback.onResponse(resp)
        },
            Response.ErrorListener { error ->
                callback.onError(error.message.toString())
            }
        ) {
        }
        VolleySingleton.getInstance().addToRequestQueue(jsonRequest)
    }

    override fun deleteUser(id: String, token: String, callback: UserRepositoryCallback.DeleteCallback) {
        callback.onLoading()
        VolleySingleton.getInstance().requestQueue

        val stringRequest = object: StringRequest(Method.DELETE, APIConfig.BASE_URL + "/" + APIConfig.USER_ROUTE + "/" + id, Listener { response ->
            val resp = Gson().fromJson<String>(response, String::class.java)
            callback.onResponse(resp)
        },
            Response.ErrorListener { error ->
                callback.onError(error.message.toString())
            }
        ) {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["x-access-token"] = token
                return headers
            }
        }
        VolleySingleton.getInstance().addToRequestQueue(stringRequest)
    }





}