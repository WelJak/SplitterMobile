package com.weljak.splittermobile.data.api

import com.weljak.splittermobile.data.model.api.ConfirmationResponse
import com.weljak.splittermobile.data.model.authentication.AuthenticationRequest
import com.weljak.splittermobile.data.model.authentication.AuthenticationResponse
import com.weljak.splittermobile.data.model.api.SplitterApiResponse
import com.weljak.splittermobile.data.model.expense.Expense
import com.weljak.splittermobile.data.model.expense.ExpenseCreationForm
import com.weljak.splittermobile.data.model.expense.ExpenseStatus
import com.weljak.splittermobile.data.model.expense.ExpenseType
import com.weljak.splittermobile.data.model.friend.Friendship
import com.weljak.splittermobile.data.model.friend.request.FriendshipRequest
import com.weljak.splittermobile.data.model.friend.request.FriendshipRequestCreationForm
import com.weljak.splittermobile.data.model.user.RegisterUserRequest
import com.weljak.splittermobile.data.model.user.UserDetails
import retrofit2.Response
import retrofit2.http.*

interface SplitterApiService {
    //USER
    @POST("user/auth/login")
    suspend fun authenticateUser(@Body authenticationRequest: AuthenticationRequest):Response<SplitterApiResponse<AuthenticationResponse>>

    @POST("user/create")
    suspend fun registerUser(@Body registerUserRequest: RegisterUserRequest): Response<SplitterApiResponse<UserDetails>>

    @GET("user/{username}")
    suspend fun getUserDetails(@Path("username") username: String, @Header("Authorization")token:String): Response<SplitterApiResponse<UserDetails>>


    //FRIEND
    @GET("friends")
    suspend fun getCurrentUserFriendList(@Header("Authorization") token:String): Response<SplitterApiResponse<Friendship>>

    @POST("friends/request/create")
    suspend fun createFriendRequest(@Header("Authorization") token:String, @Body friendshipRequestCreationForm: FriendshipRequestCreationForm): Response<SplitterApiResponse<FriendshipRequest>>

    @GET("friends/request/sent")
    suspend fun getCurrentUserSentFriendRequests(@Header("Authorization") token:String): Response<SplitterApiResponse<List<FriendshipRequest>>>

    @GET("friends/request/received")
    suspend fun getCurrentUserReceivedFriendRequests(@Header("Authorization") token:String): Response<SplitterApiResponse<List<FriendshipRequest>>>

    @GET("friends/request/confirm/{reqId}/{confirmationId}")
    suspend fun confirmFriendRequest(
        @Header("Authorization") token: String,
        @Path("reqId") reqId: String,
        @Path("confirmationId") confirmationId: String
    ): Response<SplitterApiResponse<ConfirmationResponse>>

    @DELETE("friends/request/cancel/{reqId}")
    suspend fun declineFriendRequest(@Header("Authorization") token: String, @Path("reqId") reqId: String): Response<SplitterApiResponse<Void>>


    //EXPENSE
    @POST("expense/create")
    suspend fun createExpense(@Header("Authorization") token: String, @Body expenseCreationForm: ExpenseCreationForm): Response<SplitterApiResponse<Expense>>

    @PUT("expense/settle/{id}")
    suspend fun settleExpense(@Header("Authorization") token: String, @Path("id") expenseId: String): Response<SplitterApiResponse<Expense>>

    @DELETE("expense/delete/{id}")
    suspend fun deleteExpense(@Header("Authorization") token: String, @Path("id") expenseId: String): Response<SplitterApiResponse<Void>>

    @GET("expense/all")
    suspend fun getExpensesByCriteria(
        @Header("Authorization") token: String,
        @Query("paidBy") paidBy: String,
        @Query("type") type: ExpenseType ?= null,
        @Query("status") status:ExpenseStatus ?= null
    ): Response<SplitterApiResponse<List<Expense>>>

    @GET("expense/{groupName}")
    suspend fun getExpensesByGroupName(
        @Header("Authorization") token: String,
        @Path("groupName") groupName: String
    ): Response<SplitterApiResponse<List<Expense>>>

    @GET("expense/all/unsettled")
    suspend fun getCurrentUserUnsettledExpenses(@Header("Authorization") token: String): Response<SplitterApiResponse<List<Expense>>>
}
