package com.dunavi;

import com.sap.cloud.sdk.service.prov.v2.rt.core.CloudSDKODataServiceFactory;

import org.apache.olingo.odata2.api.ODataCallback;
import org.apache.olingo.odata2.api.processor.ODataErrorCallback;

@SuppressWarnings("unchecked")
public class ODataServiceFactory extends CloudSDKODataServiceFactory {

    @Override
    public <T extends ODataCallback> T getCallback(Class<T> callbackInterface) {

        ODataCallback callback = callbackInterface.isAssignableFrom(ODataErrorCallback.class)
                ? new CustomODataErrorCallback()
                : super.getCallback(callbackInterface);
        return (T) callback;
    }
    
}
