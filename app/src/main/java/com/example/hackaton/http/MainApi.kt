import com.example.hackaton.entity.AuthRequest
import com.example.hackaton.entity.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface MainApi {
    @POST("users/login")
    suspend fun auth(@Body authRequest: AuthRequest):Response<User>
//    @Headers("Content-Type: application/json")
//    @GET("auth/users")
//    suspend fun getProductsByNameAuth(@Header("Authorization") token: String,
}