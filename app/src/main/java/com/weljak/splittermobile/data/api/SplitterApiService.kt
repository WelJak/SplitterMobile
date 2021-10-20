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
import com.weljak.splittermobile.data.model.group.CreateGroupForm
import com.weljak.splittermobile.data.model.group.Group
import com.weljak.splittermobile.data.model.group.ManageGroupMembershipRequest
import com.weljak.splittermobile.data.model.user.RegisterUserRequest
import com.weljak.splittermobile.data.model.user.UserDetails
import com.weljak.splittermobile.data.util.Endpoints
import com.weljak.splittermobile.data.util.Headers
import retrofit2.Response
import retrofit2.http.*

interface SplitterApiService {
    //USER
    @POST(Endpoints.USER_LOGIN_ENDPOINT)
    suspend fun authenticateUser(@Body authenticationRequest: AuthenticationRequest):Response<SplitterApiResponse<AuthenticationResponse>>

    @POST(Endpoints.USER_REGISTER_ENDPOINT)
    suspend fun registerUser(@Body registerUserRequest: RegisterUserRequest): Response<SplitterApiResponse<UserDetails>>

    @GET(Endpoints.USER_DETAILS_ENDPOINT)
    suspend fun getUserDetails(@Path("username") username: String, @Header(Headers.AUTH_HEADER_NAME)token:String): Response<SplitterApiResponse<UserDetails>>


    //FRIEND
    @GET(Endpoints.FRIENDS_ENDPOINT)
    suspend fun getCurrentUserFriendList(@Header(Headers.AUTH_HEADER_NAME) token:String): Response<SplitterApiResponse<Friendship>>

    @POST(Endpoints.CREATE_FRIENDS_REQUEST_ENDPOINT)
    suspend fun createFriendRequest(@Header(Headers.AUTH_HEADER_NAME) token:String, @Body friendshipRequestCreationForm: FriendshipRequestCreationForm): Response<SplitterApiResponse<FriendshipRequest>>

    @GET(Endpoints.SENT_FRIEND_REQUESTS_ENDPOINT)
    suspend fun getCurrentUserSentFriendRequests(@Header(Headers.AUTH_HEADER_NAME) token:String): Response<SplitterApiResponse<List<FriendshipRequest>>>

    @GET(Endpoints.RECEIVED_FRIEND_REQUESTS_ENDPOINT)
    suspend fun getCurrentUserReceivedFriendRequests(@Header(Headers.AUTH_HEADER_NAME) token:String): Response<SplitterApiResponse<List<FriendshipRequest>>>

    @GET(Endpoints.CONFIRM_FRIENDS_REQUEST_ENDPOINT)
    suspend fun confirmFriendRequest(
        @Header(Headers.AUTH_HEADER_NAME) token: String,
        @Path("reqId") reqId: String,
        @Path("confirmationId") confirmationId: String
    ): Response<SplitterApiResponse<ConfirmationResponse>>

    @DELETE(Endpoints.CANCEL_FRIENDS_REQUEST_ENDPOINT)
    suspend fun declineFriendRequest(@Header(Headers.AUTH_HEADER_NAME) token: String, @Path("reqId") reqId: String): Response<SplitterApiResponse<Void>>


    //EXPENSE
    @POST(Endpoints.CREATE_EXPENSE_ENDPOINT)
    suspend fun createExpense(@Header(Headers.AUTH_HEADER_NAME) token: String, @Body expenseCreationForm: ExpenseCreationForm): Response<SplitterApiResponse<Expense>>

    @PUT(Endpoints.SETTLE_UP_EXPENSE_ENDPOINT)
    suspend fun settleExpense(@Header(Headers.AUTH_HEADER_NAME) token: String, @Path("id") expenseId: String): Response<SplitterApiResponse<Expense>>

    @DELETE(Endpoints.DELETE_EXPENSE_ENDPOINT)
    suspend fun deleteExpense(@Header(Headers.AUTH_HEADER_NAME) token: String, @Path("id") expenseId: String): Response<SplitterApiResponse<Void>>

    @GET(Endpoints.FIND_EXPENSES_ENDPOINT)
    suspend fun getExpensesByCriteria(
        @Header(Headers.AUTH_HEADER_NAME) token: String,
        @Query("paidBy") paidBy: String,
        @Query("type") type: ExpenseType ?= null,
        @Query("status") status:ExpenseStatus ?= null
    ): Response<SplitterApiResponse<List<Expense>>>

    @GET(Endpoints.FIND_EXPENSES_BY_GROUP_NAME_ENDPOINT)
    suspend fun getExpensesByGroupName(
        @Header(Headers.AUTH_HEADER_NAME) token: String,
        @Path("groupName") groupName: String
    ): Response<SplitterApiResponse<List<Expense>>>

    @GET(Endpoints.GET_CURRENT_USER_UNSETTLED_EXPENSES)
    suspend fun getCurrentUserUnsettledExpenses(@Header(Headers.AUTH_HEADER_NAME) token: String): Response<SplitterApiResponse<List<Expense>>>

    @POST(Endpoints.CREATE_GROUP_ENDPOINT)
    suspend fun createGroup(
        @Header(Headers.AUTH_HEADER_NAME) token: String,
        @Body createGroupForm: CreateGroupForm
    ): Response<SplitterApiResponse<Group>>

    @GET(Endpoints.GET_CURRENT_USER_GROUPS_ENDPOINT)
    suspend fun getCurrentUserGroups(@Header(Headers.AUTH_HEADER_NAME) token: String): Response<SplitterApiResponse<List<Group>>>

    @GET(Endpoints.FIND_GROUP_BY_ID_ENDPOINT)
    suspend fun getGroupById(
        @Header(Headers.AUTH_HEADER_NAME) token: String,
        @Path("id") id: String
    ): Response<SplitterApiResponse<Group>>

    @DELETE(Endpoints.DELETE_GROUP_ENDPOINT)
    suspend fun deleteGroupById(
        @Header(Headers.AUTH_HEADER_NAME) token: String,
        @Path("id") id: String
    ): Response<SplitterApiResponse<Void>>

    @GET(Endpoints.FIND_GROUPS_BY_NAME_ENDPOINT)
    suspend fun findGroupsByName(
        @Header(Headers.AUTH_HEADER_NAME) token: String,
        @Path("name") name: String
    ): Response<SplitterApiResponse<List<Group>>>

    @PUT(Endpoints.ADD_USERS_TO_GROUP_ENDPOINT)
    suspend fun addUsersToGroup(
        @Header(Headers.AUTH_HEADER_NAME) token: String,
        @Path("id") id: String,
        @Body toAdd: ManageGroupMembershipRequest
    ): Response<SplitterApiResponse<Group>>

    @HTTP(method = "DELETE", path = Endpoints.REMOVE_MEMBERS_FROM_GROUP_ENDPOINT, hasBody = true)
    suspend fun removeMembersFromGroup(
        @Header(Headers.AUTH_HEADER_NAME) token: String,
        @Path("id") id: String,
        @Body toDelete: ManageGroupMembershipRequest
    ): Response<SplitterApiResponse<Group>>

    @GET(Endpoints.LEAVE_GROUP_ENDPOINT)
    suspend fun leaveGroup(
        @Header(Headers.AUTH_HEADER_NAME) token: String,
        @Path("id") id: String
    ): Response<SplitterApiResponse<Void>>
}
