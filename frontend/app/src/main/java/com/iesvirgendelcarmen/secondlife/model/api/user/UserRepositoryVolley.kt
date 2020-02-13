package com.iesvirgendelcarmen.secondlife.model.api.user


import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.Response.Listener
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.RequestFuture
import com.android.volley.toolbox.StringRequest
import com.google.gson.Gson
import com.google.gson.JsonObject
import com.iesvirgendelcarmen.secondlife.config.APIConfig
import com.iesvirgendelcarmen.secondlife.model.Token
import com.iesvirgendelcarmen.secondlife.model.User
import com.iesvirgendelcarmen.secondlife.model.UserWithoutId
import com.iesvirgendelcarmen.secondlife.model.api.VolleySingleton
import org.json.JSONObject


object UserRepositoryVolley: UserRepositoryDataSource {

    override fun getAllUsers(callback: UserRepositoryCallback.UsersCallback) {
        callback.onLoading()
        VolleySingleton.getInstance().requestQueue

        val stringRequest = StringRequest(Request.Method.GET, APIConfig.BASE_URL + APIConfig.USER_ROUTE, Listener { response ->
                val resp = Gson().fromJson<List<User>>(response, User::class.java)
                callback.onResponse(resp)
            },
            Response.ErrorListener { error ->
                callback.onError(error.message.toString())
            }
        )
        VolleySingleton.getInstance().addToRequestQueue(stringRequest)
    }

    override fun getUser(id: String, token: String, callback: UserRepositoryCallback.UserCallback) {
        callback.onLoading()
        VolleySingleton.getInstance().requestQueue

        val stringRequest = object: StringRequest(Method.GET, APIConfig.BASE_URL + APIConfig.USER_ROUTE + id, Listener { response ->
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

        val stringRequest = object: StringRequest(Method.PUT, APIConfig.BASE_URL + APIConfig.USER_ROUTE + user.id, Listener { response ->
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

            override fun getBody(): ByteArray {
                return user.toString().toByteArray()
            }
        }
        VolleySingleton.getInstance().addToRequestQueue(stringRequest)
    }

    override fun register(user: UserWithoutId, callback: UserRepositoryCallback.UserCallback) {
        callback.onLoading()
        VolleySingleton.getInstance().requestQueue

        val stringRequest = object: StringRequest(Method.POST, APIConfig.BASE_URL + "/register", Listener { response ->
            val resp = Gson().fromJson<User>(response, User::class.java)
            callback.onResponse(resp)
        },
            Response.ErrorListener { error ->
                callback.onError(error.message.toString())
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params = HashMap<String, String>()
                params["email"] = user.email
                params["password"] = user.password
                return params
            }
        }
        VolleySingleton.getInstance().addToRequestQueue(stringRequest)
    }


    override fun login(user: User, callback: UserRepositoryCallback.TokenCallback) {
        callback.onLoading()
        VolleySingleton.getInstance().requestQueue

        var json = JSONObject()
        json.put("email", user.email)
        json.put("password", user.password)


        val jsonRequest = object: JsonObjectRequest(Request.Method.POST, APIConfig.BASE_URL + "/login", json, Listener { response ->
            //val resp = Gson().fromJson<Token>(response, User::class.java)
            //callback.onResponse(resp)
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

        val stringRequest = object: StringRequest(Method.DELETE, APIConfig.BASE_URL + APIConfig.USER_ROUTE + id, Listener { response ->
            val resp = Gson().fromJson<String>(response, User::class.java)
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