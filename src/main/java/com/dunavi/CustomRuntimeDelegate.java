package com.dunavi;

import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Maps;
import com.sap.cloud.sdk.service.prov.api.security.AuthorizationRulesContainer;
import com.sap.cloud.sdk.service.prov.v2.rt.cdx.CDXRuntimeDelegate;
import com.sap.gateway.core.api.provider.data.IDataProvider;
import com.sap.gateway.core.api.provider.model.AbstractModelProvider;
import com.sap.gateway.core.api.srvrepo.IServiceInfo;

import org.apache.olingo.odata2.api.edm.provider.Schema;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataContext;

public class CustomRuntimeDelegate extends CDXRuntimeDelegate {
    
    @Override
    public IDataProvider getDataProvider(IServiceInfo serviceDesc) {

        CustomDataProvider customDataProvider =
                new CustomDataProvider(serviceDesc);
        customDataProvider.setDataSource(connectionProvider);
        customDataProvider.setUserContextParam(userContextProvider);
        return customDataProvider;
    }

    @Override
    public AbstractModelProvider getModelProvider(IServiceInfo serviceDesc, ODataContext context) {

        // Preventing the CSNUtil NPE during Runtime
        try {
            AuthorizationRulesContainer.setAuthorizationDefinition(Maps.newHashMap());
            AbstractModelProvider modelProvider = super.getModelProvider(serviceDesc, context);
            List<String> servicesAndEntitiesWithoutAuthRule = new ArrayList<>();
            for (Schema s : modelProvider.getSchemas()) {
                s.getEntityTypes().stream().map(et -> getPrefixedName(et.getName(), serviceDesc, s.getNamespace()))
                        .forEach(n -> servicesAndEntitiesWithoutAuthRule.add(n));
                s.getEntityContainers().stream()
                        .flatMap(ec -> ec.getFunctionImports().stream())
                        .map(fi -> getPrefixedName(fi.getName(), serviceDesc, s.getNamespace()))
                        .forEach(n -> servicesAndEntitiesWithoutAuthRule.add(n));
            }
            AuthorizationRulesContainer.setServicesAndEntitiesWithoutAuthRule(servicesAndEntitiesWithoutAuthRule);
            return modelProvider;
        } catch (ODataException e) {
            throw new RuntimeException(e);
        }
    }

    private String getPrefixedName(String name, IServiceInfo service, String schemaNamespace) {

        return (service.getNamespace() != null ? service.getNamespace() : schemaNamespace) + "." + name;
    }
}
