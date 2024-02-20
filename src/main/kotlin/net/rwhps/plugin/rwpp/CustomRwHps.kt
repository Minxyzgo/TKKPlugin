@file:Suppress("INVISIBLE_MEMBER", "INVISIBLE_REFERENCE")

package net.rwhps.plugin.rwpp

import net.rwhps.server.net.core.ConnectionAgreement
import net.rwhps.server.net.core.IRwHps
import net.rwhps.server.net.core.TypeConnect
import net.rwhps.server.net.netconnectprotocol.RwHps
import net.rwhps.server.plugin.internal.headless.inject.core.GameEngine
import net.rwhps.server.plugin.internal.headless.inject.lib.PlayerConnectX
import net.rwhps.server.plugin.internal.headless.inject.net.TypeHessRwHps

class CustomRwHps(netType: IRwHps.NetType): RwHps(netType) {
    override val typeConnect: TypeConnect = TypeHessRwHps(
        RWPPProtocolServer(
            PlayerConnectX(
                GameEngine.netEngine,
                ConnectionAgreement()
            )
        )
    )
}