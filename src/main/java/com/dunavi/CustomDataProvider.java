package com.dunavi;

import com.sap.cloud.sdk.service.prov.v2.data.provider.CXSDataProvider;
import com.sap.gateway.core.api.srvrepo.IServiceInfo;

public class CustomDataProvider extends CXSDataProvider {

    public CustomDataProvider(IServiceInfo service) {
        super(service);
    }
    
}
