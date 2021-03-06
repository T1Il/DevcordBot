/*
 * Copyright 2020 Daniel Scherf & Michael Rittmeister & Julian König
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package com.github.devcordde.devcordbot.command.impl

import com.github.devcordde.devcordbot.command.PermissionHandler
import com.github.devcordde.devcordbot.command.permission.Permission
import com.github.devcordde.devcordbot.command.permission.PermissionState
import com.github.devcordde.devcordbot.database.DevCordUser
import net.dv8tion.jda.api.entities.Member

/**
 * Implementation of [PermissionHandler] that checks the users roles.
 */
class RolePermissionHandler(
    private val botOwners: List<String>
) : PermissionHandler {
    private val moderatorPattern = "(?i)moderator|admin(istrator)?".toRegex()
    private val adminPattern = "(?i)admin(istrator)?".toRegex()

    override fun isCovered(
        permission: Permission,
        executor: Member?,
        devCordUser: DevCordUser?,
        acknowledgeBlacklist: Boolean
    ): PermissionState {
        executor ?: return PermissionState.DECLINED
        if (executor.id in botOwners) return PermissionState.ACCEPTED
        if (acknowledgeBlacklist && requireNotNull(devCordUser) { "Devcorduser must not be null if blacklist ist acknowledged" }.blacklisted) return PermissionState.IGNORED
        return when (permission) {
            Permission.ANY -> PermissionState.ACCEPTED
            Permission.MODERATOR -> if (executor.roles.any { it.name.matches(moderatorPattern) }) PermissionState.ACCEPTED else PermissionState.DECLINED
            Permission.ADMIN -> if (executor.roles.any { it.name.matches(adminPattern) }) PermissionState.ACCEPTED else PermissionState.DECLINED
            Permission.BOT_OWNER -> PermissionState.DECLINED
        }
    }
}
