/*
 * Copyright 2020 Daniel Scherf & Michael Rittmeister
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

package com.github.seliba.devcordbot.commands.general

import com.github.seliba.devcordbot.command.AbstractCommand
import com.github.seliba.devcordbot.command.CommandCategory
import com.github.seliba.devcordbot.command.context.Arguments
import com.github.seliba.devcordbot.command.context.Context
import com.github.seliba.devcordbot.command.perrmission.Permissions
import com.github.seliba.devcordbot.constants.Embeds

/**
 * Lmgtfy command.
 */
class LmgtfyCommand : AbstractCommand() {
    override val aliases: List<String> = listOf("lmgtfy", "google")
    override val displayName: String = "lmgtfy"
    override val description: String = "Gibt einen lmgtfy-Link aus."
    override val usage: String = "[command] <text>"
    override val permissions: Permissions = Permissions.ANY
    override val category: CommandCategory = CommandCategory.GENERAL

    override fun execute(context: Context) {
        val arguments = context.args

        if (arguments.isEmpty()) {
            return context.respond(Embeds.command(this)).queue()
        }

        context.respond(createLink(arguments)).queue()
    }

    /**
     * Create a lmgtfy link.
     */
    private fun createLink(text: Arguments) = "https://lmgtfy.com/?q=${text.joinToString("+")}&iie=1"
}
