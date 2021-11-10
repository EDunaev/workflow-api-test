package com.dunavi.odata;

import java.util.ArrayList;
import java.util.Collections;

//import com.dunavi.services.DefaultEntitiesService;
//import com.dunavi.services.EntitiesService;
import com.sap.cloud.sdk.service.prov.api.annotations.Function;
import com.sap.cloud.sdk.service.prov.api.operations.Query;
import com.sap.cloud.sdk.service.prov.api.request.OperationRequest;
import com.sap.cloud.sdk.service.prov.api.response.OperationResponse;
import com.sap.cloud.sdk.service.prov.api.response.QueryResponse;


public class UploadController {

   //private EntitiesService service = new DefaultEntitiesService();


   @Function(serviceName = "entities", Name = "StartUploadDocument")
   public OperationResponse getData(OperationRequest request) {
       System.out.println(("FunctionImport/StartUploadDocument :: " + request.getParameters()));  
        return OperationResponse.setSuccess()
                    .setPrimitiveData(Collections.singletonList(request.getParameters().get("Document").toString()))
                    .response();
       //service.createUploadDocument(new UploadDocument()).execute(destination)
      
       //return request.getParameters().get("Document").toString();
   }
    
}
