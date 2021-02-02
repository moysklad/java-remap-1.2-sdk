package ru.moysklad.remap_1_2.clients.endpoints;

import ru.moysklad.remap_1_2.clients.StateClient;

public interface HasStatesEndpoint extends Endpoint {
    @ApiChainElement
    default StateClient states() {
        return new StateClient(api(), path());
    }
}
