package net.rwhps.plugin.rwpp

import net.rwhps.server.core.ServiceLoader
import net.rwhps.server.data.global.Data
import net.rwhps.server.func.StrCons
import net.rwhps.server.game.event.EventGlobalManage
import net.rwhps.server.game.event.EventManage
import net.rwhps.server.game.event.global.ServerHessLoadEvent
import net.rwhps.server.game.event.global.ServerLoadEvent
import net.rwhps.server.game.manage.HeadlessModuleManage
import net.rwhps.server.net.core.IRwHps
import net.rwhps.server.plugin.Plugin
import net.rwhps.server.util.classload.GameModularReusableLoadClass
import net.rwhps.server.util.game.GameStartInit
import net.rwhps.server.util.log.Log
import java.net.URLClassLoader
import java.util.*

@Suppress("unused")
class Main : Plugin() {

    override fun registerGlobalEvents(eventManage: EventGlobalManage) {
        eventManage.registerListener(ServerLoadEvent::class.java) {
            Data.SERVER_COMMAND.removeCommand("start")
            Data.SERVER_COMMAND.register("start", "serverCommands.start") { _: Array<String>?, log: StrCons ->
                if (Data.startServer) {
                    log("The server is not closed, please close")
                    return@register
                }
                log("RWPP Protocol Start")
                Data.startServer = true

                // Start Hess Core
                val load = GameModularReusableLoadClass(
                    Main::class.java.classLoader, Main::class.java.classLoader.parent
                )

                GameStartInit.init(load)
                Log.clog(Data.i18NBundle.getinput("server.load.headless"))
                // 设置 RW-HPS 主要使用的 Hess
                HeadlessModuleManage.hpsLoader = load.toString()
                GameStartInit.start(load)

                Log.set(Data.config.log.uppercase(Locale.getDefault()))
            }
        }

        Thread.currentThread().contextClassLoader = Main::class.java.classLoader
        ServiceLoader.addService(
            ServiceLoader.ServiceType.IRwHps,
            IRwHps.NetType.ServerProtocol.name,
            CustomRwHps::class.java
        )
    }
}