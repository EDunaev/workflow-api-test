package com.dunavi.workflowapitest.controller;

import java.util.List;

import com.sap.cloud.sdk.cloudplatform.connectivity.HttpDestination;
import com.sap.cloud.sdk.services.scp.workflow.cf.api.WorkflowDefinitionsApi;
import com.sap.cloud.sdk.services.scp.workflow.cf.model.WorkflowDefinition;
import com.sap.cloud.sdk.cloudplatform.connectivity.ScpCfServiceDestinationLoader;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class WorkflowController {

    @GetMapping("/getDefinitions")
    public String getWorkflowDefinitions() {

        final String destinationName = "wm_workflow";

        HttpDestination httpDestination =  ScpCfServiceDestinationLoader.getDestinationForService(
                ScpCfServiceDestinationLoader.CfServices.WORKFLOW,
                destinationName);
        

        
        final List<WorkflowDefinition> workflowDefinitions =
                new WorkflowDefinitionsApi(httpDestination).queryDefinitions();


        System.out.println(workflowDefinitions.size());

        return "done: workflowDefinitions.size() = " + workflowDefinitions.size() + " workflowDefinitions.get(0): " + workflowDefinitions.get(0);        
    }
}
