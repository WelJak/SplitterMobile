package com.weljak.splittermobile.presentation.di.module

import com.weljak.splittermobile.domain.repository.expense.ExpenseRepository
import com.weljak.splittermobile.domain.repository.friend.FriendRepository
import com.weljak.splittermobile.domain.repository.group.GroupRepository
import com.weljak.splittermobile.domain.repository.user.UserRepository
import com.weljak.splittermobile.domain.usecase.expense.*
import com.weljak.splittermobile.domain.usecase.friend.*
import com.weljak.splittermobile.domain.usecase.group.*
import com.weljak.splittermobile.domain.usecase.user.AuthenticateUserUseCase
import com.weljak.splittermobile.domain.usecase.user.GetUserDetailsUseCase
import com.weljak.splittermobile.domain.usecase.user.RegisterUserUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UseCaseModule {
    @Singleton
    @Provides
    fun provideAuthenticateUserUseCase(userRepository: UserRepository): AuthenticateUserUseCase {
        return AuthenticateUserUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun provideRegisterUserUseCase(userRepository: UserRepository): RegisterUserUseCase {
        return RegisterUserUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun provideGetUserDetailsUseCase(userRepository: UserRepository): GetUserDetailsUseCase {
        return GetUserDetailsUseCase(userRepository)
    }

    @Singleton
    @Provides
    fun provideGetCurrentUserFriendListUseCase(friendRepository: FriendRepository): GetCurrentUserFriendListUseCase {
        return GetCurrentUserFriendListUseCase(friendRepository)
    }

    @Singleton
    @Provides
    fun provideCreateFriendRequestUseCase(friendRepository: FriendRepository): CreateFriendRequestUseCase {
        return CreateFriendRequestUseCase(friendRepository)
    }

    @Singleton
    @Provides
    fun provideGetCurrentUserReceivedFriendRequestsUseCase(friendRepository: FriendRepository): GetCurrentUserReceivedFriendRequestsUseCase {
        return GetCurrentUserReceivedFriendRequestsUseCase(friendRepository)
    }

    @Singleton
    @Provides
    fun provideGetCurrentUserSentFriendRequestsUseCase(friendRepository: FriendRepository): GetCurrentUserSentFriendRequestsUseCase {
        return GetCurrentUserSentFriendRequestsUseCase(friendRepository)
    }

    @Singleton
    @Provides
    fun provideConfirmFriendRequestUseCase(friendRepository: FriendRepository): ConfirmFriendRequestUseCase {
        return ConfirmFriendRequestUseCase(friendRepository)
    }

    @Singleton
    @Provides
    fun provideDeclineFriendRequestUseCase(friendRepository: FriendRepository): DeclineFriendRequestUseCase {
        return DeclineFriendRequestUseCase(friendRepository)
    }

    @Singleton
    @Provides
    fun provideCreateExpenseUseCase(expenseRepository: ExpenseRepository): CreateExpenseUseCase {
        return CreateExpenseUseCase(expenseRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteExpenseUseCase(expenseRepository: ExpenseRepository): DeleteExpenseUseCase {
        return DeleteExpenseUseCase(expenseRepository)
    }

    @Singleton
    @Provides
    fun provideGetCurrentUserUnsettledExpensesUseCase(expenseRepository: ExpenseRepository): GetCurrentUserUnsettledExpensesUseCase {
        return GetCurrentUserUnsettledExpensesUseCase(expenseRepository)
    }

    @Singleton
    @Provides
    fun provideGetExpensesByCriteriaUseCase(expenseRepository: ExpenseRepository): GetExpensesByCriteriaUseCase {
        return GetExpensesByCriteriaUseCase(expenseRepository)
    }

    @Singleton
    @Provides
    fun provideGetExpensesByGroupNameUseCase(expenseRepository: ExpenseRepository): GetExpensesByGroupNameUseCase {
        return GetExpensesByGroupNameUseCase(expenseRepository)
    }

    @Singleton
    @Provides
    fun provideSettleExpenseUseCase(expenseRepository: ExpenseRepository): SettleExpenseUseCase {
        return SettleExpenseUseCase(expenseRepository)
    }

    @Singleton
    @Provides
    fun provideAddUsersToGroupUseCase(groupRepository: GroupRepository): AddUsersToGroupUseCase {
        return AddUsersToGroupUseCase(groupRepository)
    }

    @Singleton
    @Provides
    fun provideCreateGroupUseCase(groupRepository: GroupRepository): CreateGroupUseCase {
        return CreateGroupUseCase(groupRepository)
    }

    @Singleton
    @Provides
    fun provideDeleteGroupByIdUseCase(groupRepository: GroupRepository): DeleteGroupByIdUseCase {
        return DeleteGroupByIdUseCase(groupRepository)
    }

    @Singleton
    @Provides
    fun provideFindGroupsByNameUseCase(groupRepository: GroupRepository): FindGroupsByNameUseCase {
        return FindGroupsByNameUseCase(groupRepository)
    }

    @Singleton
    @Provides
    fun provideGetCurrentUserGroupsUseCase(groupRepository: GroupRepository): GetCurrentUserGroupsUseCase {
        return GetCurrentUserGroupsUseCase(groupRepository)
    }

    @Singleton
    @Provides
    fun provideGetGroupByIdUseCase(groupRepository: GroupRepository): GetGroupByIdUseCase {
        return GetGroupByIdUseCase(groupRepository)
    }

    @Singleton
    @Provides
    fun provideLeaveGroupUseCase(groupRepository: GroupRepository): LeaveGroupUseCase {
        return LeaveGroupUseCase(groupRepository)
    }

    @Singleton
    @Provides
    fun provideRemoveMembersFromGroupUseCase(groupRepository: GroupRepository): RemoveMembersFromGroupUseCase {
        return RemoveMembersFromGroupUseCase(groupRepository)
    }
}
