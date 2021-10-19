package com.weljak.splittermobile.presentation.viewmodel.group

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.weljak.splittermobile.domain.usecase.group.*

class GroupViewModelFactory(
    private val app: Application,
    private val createGroupUseCase: CreateGroupUseCase,
    private val getCurrentUserGroupsUseCase: GetCurrentUserGroupsUseCase,
    private val getGroupByIdUseCase: GetGroupByIdUseCase,
    private val deleteGroupByIdUseCase: DeleteGroupByIdUseCase,
    private val findGroupsByNameUseCase: FindGroupsByNameUseCase,
    private val addUsersToGroupUseCase: AddUsersToGroupUseCase,
    private val removeMembersFromGroupUseCase: RemoveMembersFromGroupUseCase,
    private val leaveGroupUseCase: LeaveGroupUseCase
): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return GroupViewModel(
            app,
            createGroupUseCase,
            getCurrentUserGroupsUseCase,
            getGroupByIdUseCase,
            deleteGroupByIdUseCase,
            findGroupsByNameUseCase,
            addUsersToGroupUseCase,
            removeMembersFromGroupUseCase,
            leaveGroupUseCase
        ) as T
    }
}