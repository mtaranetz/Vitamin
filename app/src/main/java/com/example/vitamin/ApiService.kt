import com.example.vitamin.LoginResponse
import com.example.vitamin.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {
    @POST("/add_user")
    fun addUser(@Body user: User): Call<Void>

    @POST("/login_user")
    fun loginUser(@Body loginRequest: Map<String, String>): Call<LoginResponse>

    @POST("/get_user")
    fun getUser(@Body request: Map<String, String>): Call<User>
}


