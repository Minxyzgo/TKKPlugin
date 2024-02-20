package net.rwhps.plugin.rwpp

import net.rwhps.server.io.packet.Packet
import net.rwhps.server.plugin.internal.headless.inject.lib.PlayerConnectX
import net.rwhps.server.plugin.internal.headless.inject.net.GameVersionServer

class RWPPProtocolServer(playerConnectX: PlayerConnectX) : GameVersionServer(playerConnectX) {
    override fun receivePacket(packet: Packet) {
        println("got!!!!!" + packet.type.typeInt)
    }
}