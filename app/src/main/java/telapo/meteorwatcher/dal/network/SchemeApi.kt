package telapo.meteorwatcher.dal.network

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import telapo.meteorwatcher.dal.model.scheme.Scheme

interface SchemeApi {

    @GET("/api/schemes")
    fun GetSchemes(
        @Query("limit") limit: Int = 4
    ) : Call<List<Scheme>>
}