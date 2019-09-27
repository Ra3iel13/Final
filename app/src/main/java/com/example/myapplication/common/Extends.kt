package com.example.myapplication.common

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.LayoutRes
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot:Boolean=false): View {
    return LayoutInflater.from(context).inflate(layoutRes,this,attachToRoot)
}
fun ImageView.loadUrl(url:String){
    Picasso.get().load(url).into(this)
}
fun Context.toast(context: Context =applicationContext, massage: String, duration: Int = Toast.LENGTH_LONG){
    Toast.makeText(context,massage,duration).show()
}
fun <T> Call<T>.enqueue(callback: CallBackKt<T>.() -> Unit) {
    val callBackKt = CallBackKt<T>()
    callback.invoke(callBackKt)
    this.enqueue(callBackKt)
}

class CallBackKt<T>: Callback<T> {

    var onResponse: ((Response<T>) -> Unit)? = null
    var onFailure: ((t: Throwable?) -> Unit)? = null

    override fun onFailure(call: Call<T>, t: Throwable) {
        onFailure?.invoke(t)
    }

    override fun onResponse(call: Call<T>, response: Response<T>) {
        onResponse?.invoke(response)
    }

}
