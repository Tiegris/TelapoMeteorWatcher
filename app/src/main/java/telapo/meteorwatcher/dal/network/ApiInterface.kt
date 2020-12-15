package telapo.meteorwatcher.dal.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query
import telapo.meteorwatcher.dal.model.observation.Observation
import telapo.meteorwatcher.dal.model.scheme.Scheme

interface ApiInterface {

    @GET("http://tigri.ddns.net/api/schemes")
    fun GetSchemes(
        @Query("limit") limit: Int = 4
    ) : Call<List<Scheme>>

    @POST("http://tigri.ddns.net/api/upload")
    fun UploadObservation(
        @Body observation: Observation
    ) : Call<Observation>
}