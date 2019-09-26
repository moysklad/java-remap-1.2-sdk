package com.lognex.api.clients.endpoints;

import com.lognex.api.clients.StateClient;

public interface HasStatesEndpoint extends Endpoint {
    @ApiChainElement
    default StateClient states() {
        return new StateClient(api(), path());
    }
}
