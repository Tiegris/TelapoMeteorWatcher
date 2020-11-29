package telapo.meteorwatcher.dal.network

import android.os.Handler
import okhttp3.OkHttpClient
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import telapo.meteorwatcher.dal.model.scheme.Scheme

object NetworkManager {
    //private const val SERVICE_URL = "https://www.tigri.ddns.net"
    private const val SERVICE_URL = "http://192.168.1.65:8090/"

    private val api: SchemeApi

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(SERVICE_URL)
            .client(OkHttpClient.Builder().build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        api = retrofit.create(SchemeApi::class.java)
    }

    private fun <T> runCallOnBackgroundThread(
        call: Call<T>,
        onSuccess: (T) -> Unit,
        onError: (Throwable) -> Unit
    ) {
        val handler = Handler()
        Thread {
            try {
                val response = call.execute().body()!!
                handler.post { onSuccess(response) }

            } catch (e: Exception) {
                e.printStackTrace()
                handler.post { onError(e) }
            }
        }.start()
    }

    fun SyncObservations() {

    }

    private fun fetchSchemes(
        onSuccess: (List<Scheme>) -> Unit,
        onError: (Throwable) -> Unit
    ){
        val request = api.GetSchemes()
        runCallOnBackgroundThread(request, onSuccess, onError)
    }

    interface ISchemeListReceiver {
        fun ReceiveSchemes(schemes: List<Scheme>)
        fun HandleError(throwable: Throwable)
    }

    fun FetchSchemes(receiver : ISchemeListReceiver ) {
        fetchSchemes(receiver::ReceiveSchemes , receiver::HandleError)
    }


}