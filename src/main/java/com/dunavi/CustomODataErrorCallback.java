package com.dunavi;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.lang.reflect.InvocationTargetException;

import com.sap.cloud.sdk.service.prov.api.exception.ServiceSDKException;
import com.sap.gateway.core.api.producer.ErrorDocumentProducerFactory;
import com.sap.gateway.core.api.producer.IErrorDocumentProducer;

import org.apache.olingo.odata2.api.ODataServiceVersion;
import org.apache.olingo.odata2.api.commons.HttpStatusCodes;
import org.apache.olingo.odata2.api.commons.ODataHttpHeaders;
import org.apache.olingo.odata2.api.exception.ODataApplicationException;
import org.apache.olingo.odata2.api.exception.ODataException;
import org.apache.olingo.odata2.api.processor.ODataErrorCallback;
import org.apache.olingo.odata2.api.processor.ODataErrorContext;
import org.apache.olingo.odata2.api.processor.ODataResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomODataErrorCallback implements ODataErrorCallback {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomODataErrorCallback.class);

    @Override
    public ODataResponse handleError(ODataErrorContext context) throws ODataApplicationException {
        
        Throwable originalException = findRootException(context);
        context.setHttpStatus(HttpStatusCodes.INTERNAL_SERVER_ERROR);
        context.setErrorCode("E001");
        context.setMessage(originalException.getMessage());
        LOGGER.error("Unexpected error", originalException);
        return writeErrorDocument(context);
    }

    private Throwable findRootException(ODataErrorContext context) {

        Throwable originalException = context.getException();

        if (isODataExceptionAndServiceSDKException(originalException)) {
            ServiceSDKException sdkException = (ServiceSDKException) originalException.getCause();
            if (sdkException.getCause() instanceof InvocationTargetException) {
                InvocationTargetException invocationException = (InvocationTargetException) sdkException.getCause();
                originalException = invocationException.getTargetException();
            }
        }
        if (originalException.getClass().equals(RuntimeException.class) //
                && originalException.getCause() != null) {
            originalException = originalException.getCause();
        }
        return originalException;
    }

    private static boolean isODataExceptionAndServiceSDKException(Throwable originalException) {
        return originalException instanceof ODataException && originalException.getCause() instanceof ServiceSDKException;
    }

    /**
     * Workaround: copied from {@link CloudSDKODataErrorCallback}, since not
     * visible.
     **/
    private ODataResponse writeErrorDocument(ODataErrorContext context) throws ODataApplicationException {

        try {

            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();

            IErrorDocumentProducer producer = ErrorDocumentProducerFactory
                    .getErrorDocumentProducer(context.getContentType());

            producer.writeErrorDocument(outputStream, context);

            ODataResponse.ODataResponseBuilder response = ODataResponse.entity(new ByteArrayInputStream(outputStream.toByteArray()))
                    .header(ODataHttpHeaders.DATASERVICEVERSION, ODataServiceVersion.V10)
                    .status(context.getHttpStatus());

            response.contentHeader(context.getContentType());

            return response.build();

        } catch (Exception e) {
            LOGGER.error(e.getMessage(), e);
            throw new ODataApplicationException(e.getLocalizedMessage(), context.getLocale(), e);
        }
    }
}
