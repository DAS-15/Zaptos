package com.example.docode.Repository

import android.content.Context
import android.widget.Toast
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.MutableLiveData
import com.android.volley.Request
import com.android.volley.toolbox.JsonArrayRequest
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import com.example.zaptos.MasterViewModel
import com.example.zaptos.models.ItemModel
import com.google.gson.Gson


class MainRepository {

    suspend fun getNikeItems(
        context: Context,
        nikeList: MutableLiveData<MutableState<SnapshotStateList<ItemModel>>>
    ): MutableLiveData<MutableState<SnapshotStateList<ItemModel>>> {
        val gson = Gson()
        val url = "https://zaptos-server.herokuapp.com/nike"
        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                Toast.makeText(context, "Fetched data successfully", Toast.LENGTH_LONG).show()
                for (i in 0 until response.length()) {
//                if (response.getJSONObject(i)["in_24_hours"] == "Yes") {
                    nikeList.value?.value?.add(
                        gson.fromJson(
                            response.getJSONObject(i).toString(),
                            ItemModel::class.java
                        )
                    )
//                }
//                    Toast.makeText(
//                        context,
//                        response[i].toString(),
//                        Toast.LENGTH_LONG
//                    ).show()
                }
            }, {
                Toast.makeText(context, "Unable to fetch data!!!", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)

        return nikeList
    }

    suspend fun getAdidasItems(
        context: Context,
        adidasList: MutableLiveData<MutableState<SnapshotStateList<ItemModel>>>
    ): MutableLiveData<MutableState<SnapshotStateList<ItemModel>>> {
        val gson = Gson()
        val url = "https://zaptos-server.herokuapp.com/adidas"
        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                Toast.makeText(context, "Fetched data successfully", Toast.LENGTH_LONG).show()
                for (i in 0 until response.length()) {
//                if (response.getJSONObject(i)["in_24_hours"] == "Yes") {
                    adidasList.value?.value?.add(
                        gson.fromJson(
                            response.getJSONObject(i).toString(),
                            ItemModel::class.java
                        )
                    )
//                }
//                    Toast.makeText(
//                        context,
//                        response[i].toString(),
//                        Toast.LENGTH_LONG
//                    ).show()
                }
            }, {
                Toast.makeText(context, "Unable to fetch data!!!", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)

        return adidasList
    }

    suspend fun getPumaItems(
        context: Context,
        pumaList: MutableLiveData<MutableState<SnapshotStateList<ItemModel>>>
    ): MutableLiveData<MutableState<SnapshotStateList<ItemModel>>> {
        val gson = Gson()
        val url = "https://zaptos-server.herokuapp.com/puma"
        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonArrayRequest(
            Request.Method.GET, url, null,
            { response ->
                Toast.makeText(context, "Fetched data successfully", Toast.LENGTH_LONG).show()
                for (i in 0 until response.length()) {
//                if (response.getJSONObject(i)["in_24_hours"] == "Yes") {
                    pumaList.value?.value?.add(
                        gson.fromJson(
                            response.getJSONObject(i).toString(),
                            ItemModel::class.java
                        )
                    )
//                }
//                    Toast.makeText(
//                        context,
//                        response[i].toString(),
//                        Toast.LENGTH_LONG
//                    ).show()
                }
            }, {
                Toast.makeText(context, "Unable to fetch data!!!", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)

        return pumaList
    }

    suspend fun getPaymentLink(
        context: Context,
        _paymentLink: MutableLiveData<MutableState<String>>,
        cart_id: String
    ): MutableLiveData<MutableState<String>> {
        val url = "https://zaptos-server.herokuapp.com/getPaymentLink/$cart_id"
        val queue = Volley.newRequestQueue(context)
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,
            { response ->
                Toast.makeText(context, "Fetched payment url successfully", Toast.LENGTH_LONG).show()
                _paymentLink.value?.value = response.get("url").toString()
                MasterViewModel.myurl = response.get("url").toString()
            }, {
                Toast.makeText(context, "Unable to fetch data!!!", Toast.LENGTH_LONG).show()
            }
        )
        queue.add(jsonObjectRequest)

        return _paymentLink
    }

}

