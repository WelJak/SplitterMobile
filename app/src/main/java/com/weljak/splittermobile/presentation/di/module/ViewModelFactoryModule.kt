package com.weljak.splittermobile.presentation.di.module

import android.app.Application
import com.weljak.splittermobile.domain.usecase.expense.*
import com.weljak.splittermobile.domain.usecase.friend.*
import com.weljak.splittermobile.domain.usecase.group.*
import com.weljak.splittermobile.domain.usecase.user.AuthenticateUserUseCase
import com.weljak.splittermobile.domain.usecase.user.GetUserDetailsUseCase
import com.weljak.splittermobile.domain.usecase.user.RegisterUserUseCase
import com.weljak.splittermobile.presentation.viewmodel.expense.ExpenseViewModelFactory
import com.weljak.splittermobile.presentation.viewmodel.friend.FriendViewModelFactory
import com.weljak.splittermobile.presentation.viewmodel.group.GroupViewModelFactory
import com.weljak.splittermobile.presentation.viewmodel.user.UserViewModelFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ViewModelFactoryModule {
    @Singleton
    @Provides
    fun provideUserViewModelFactory(
        app:Application,
        authenticateUserUseCase: AuthenticateUserUseCase,
        registerUserUseCase: RegisterUserUseCase,
        getUserDetailsUseCase: GetUserDetailsUseCase
    ): UserViewModelFactory {
        return  UserViewModelFactory(
            app,
            authenticateUserUseCase,
            registerUserUseCase,
            getUserDetailsUseCase
        )
    }

    @Singleton
    @Provides
    fun provideFriendViewModelFactory(
        app:Application,
        getCurrentUserFriendListUseCase: GetCurrentUserFriendListUseCase,
        createFriendRequestUseCase: CreateFriendRequestUseCase,
        getCurrentUserSentFriendRequestsUseCase: GetCurrentUserSentFriendRequestsUseCase,
        getCurrentUserReceivedFriendRequestsUseCase: GetCurrentUserReceivedFriendRequestsUseCase,
        confirmFriendRequestUseCase: ConfirmFriendRequestUseCase,
        declineFriendRequestUseCase: DeclineFriendRequestUseCase
    ): FriendViewModelFactory {
        return FriendViewModelFactory(
            app,
            getCurrentUserFriendListUseCase,
            createFriendRequestUseCase,
            getCurrentUserSentFriendRequestsUseCase,
            getCurrentUserReceivedFriendRequestsUseCase,
            confirmFriendRequestUseCase,
            declineFriendRequestUseCase
        )
    }

    @Singleton
    @Provides
    fun provideExpenseViewModelFactory(
        app: Application,
        createExpenseUseCase: CreateExpenseUseCase,
        deleteExpenseUseCase: DeleteExpenseUseCase,
        getCurrentUserUnsettledExpensesUseCase: GetCurrentUserUnsettledExpensesUseCase,
        getExpensesByCriteriaUseCase: GetExpensesByCriteriaUseCase,
        getExpensesByGroupNameUseCase: GetExpensesByGroupNameUseCase,
        settleExpenseUseCase: SettleExpenseUseCase
    ): ExpenseViewModelFactory {
        return ExpenseViewModelFactory(
            app,
            createExpenseUseCase,
            deleteExpenseUseCase,
            getCurrentUserUnsettledExpensesUseCase,
            getExpensesByCriteriaUseCase,
            getExpensesByGroupNameUseCase,
            settleExpenseUseCase
        )
    }

    @Singleton
    @Provides
    fun provideGroupViewModelFactory(
        app: Application,
        createGroupUseCase: CreateGroupUseCase,
        getCurrentUserGroupsUseCase: GetCurrentUserGroupsUseCase,
        getGroupByIdUseCase: GetGroupByIdUseCase,
        deleteGroupByIdUseCase: DeleteGroupByIdUseCase,
        findGroupsByNameUseCase: FindGroupsByNameUseCase,
        addUsersToGroupUseCase: AddUsersToGroupUseCase,
        removeMembersFromGroupUseCase: RemoveMembersFromGroupUseCase,
        leaveGroupUseCase: LeaveGroupUseCase
    ): GroupViewModelFactory {
        return GroupViewModelFactory(
            app,
            createGroupUseCase,
            getCurrentUserGroupsUseCase,
            getGroupByIdUseCase,
            deleteGroupByIdUseCase,
            findGroupsByNameUseCase,
            addUsersToGroupUseCase,
            removeMembersFromGroupUseCase,
            leaveGroupUseCase
        )
    }
}
