package com.guilhermesgb.robospiceretrofit.network.requests;

import com.google.gson.JsonObject;
import com.guilhermesgb.robospiceretrofit.network.WordPressCMSRetrofitInterface;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import retrofit.http.RestMethod;

abstract public class ResolvedSignatureRequest extends RetrofitSpiceRequest<JsonObject,
        WordPressCMSRetrofitInterface> {

    private final Method requestInterface;
    private final RequestSignatureResolver requestSignatureResolver;
    private String currentResolvedRequestSignature;
    private final JsonObject requestSignatureVariables = new JsonObject();

    protected ResolvedSignatureRequest(Map<String, Object> requestParameters) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException, ClassCastException {
        super(JsonObject.class, WordPressCMSRetrofitInterface.class);
        requestInterface = getRequestInterface();
        requestSignatureResolver = prepareRequestSignatureResolver(requestInterface);
        for (Map.Entry<String, Object> parameterMapping : requestParameters.entrySet()) {
            try {
                Integer defaultIntegerValue = (Integer) parameterMapping.getValue();
                changeRequestSignatureIntegerVariable(parameterMapping.getKey(), defaultIntegerValue);
            }
            catch (ClassCastException exception) {
                @SuppressWarnings("ConstantConditions") String defaultStringValue =
                        (String) parameterMapping.getValue();
                changeRequestSignatureStringVariable(parameterMapping.getKey(), defaultStringValue);
            }
        }
        currentResolvedRequestSignature = getRequestSignature();
    }

    public void changeRequestSignatureIntegerVariable(String signatureVariable, Integer value) {
        requestSignatureVariables.addProperty(signatureVariable, value);
        currentResolvedRequestSignature = getRequestSignature();
    }

    public Integer getRequestSignatureIntegerVariableValue(String signatureVariable) {
        return requestSignatureVariables.get(signatureVariable).getAsInt();
    }

    public void changeRequestSignatureStringVariable(String signatureVariable, String value) {
        requestSignatureVariables.addProperty(signatureVariable, value);
        currentResolvedRequestSignature = getRequestSignature();
    }

    public String getRequestSignatureStringVariableValue(String signatureVariable) {
        return requestSignatureVariables.get(signatureVariable).getAsString();
    }

    @Override
    public JsonObject loadDataFromNetwork() throws Exception {
        JsonObject response = doLoadDataFromNetwork(requestInterface);
        System.out.println(String.format("%s.... called!", getRequestSignature()));
        String status = response.get("status").getAsString();
        if ("error".equals(status)) {
            cancel();
        }
        return response;
    }

    protected abstract JsonObject doLoadDataFromNetwork(Method requestInterface) throws Exception;

    private Method getRequestInterface() throws NoSuchMethodException {
        return getRequestInterfaceMethod(WordPressCMSRetrofitInterface.class);
    }

    protected abstract Method getRequestInterfaceMethod(Class<?> declaredInterface)
            throws NoSuchMethodException;

    private RequestSignatureResolver prepareRequestSignatureResolver(Method requestInterface)
            throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        for (Annotation annotation : requestInterface.getAnnotations()) {
            for (Class<?> declaredInterface : annotation.getClass().getInterfaces()) {
                for (Annotation metaAnnotation : declaredInterface.getAnnotations()) {
                    for (Class<?> declaredMetaInterface : metaAnnotation.getClass().getInterfaces()) {
                        if (declaredMetaInterface == RestMethod.class) {
                            return new RequestSignatureResolver(declaredInterface, annotation);
                        }
                    }
                }
            }
        }
        throw new NoSuchMethodException();
    }

    private String getRequestSignature() {
        currentResolvedRequestSignature = requestSignatureResolver.resolveRequestSignature();
        currentResolvedRequestSignature = parseRequestSignatureVars(currentResolvedRequestSignature);
        return currentResolvedRequestSignature;
    }

    protected abstract String parseRequestSignatureVars(String currentMethodResolvedSignature);

    public String getCurrentResolvedRequestSignature() {
        String requestSignature = currentResolvedRequestSignature;
        if (requestSignature != null) {
            requestSignature = requestSignature.replace("/", "-").replace("?", ".");
        }
        return requestSignature;
    }

    private class RequestSignatureResolver {

        protected final Class<?> declaredInterface;
        protected final Annotation annotation;

        protected RequestSignatureResolver(Class<?> declaredInterface, Annotation annotation)
                throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
            this.declaredInterface = declaredInterface;
            this.annotation = annotation;
            doResolveRequestSignature();
        }

        protected String resolveRequestSignature() {
            try {
                return doResolveRequestSignature();
            }
            catch (Exception exception) {
                return null;
            }
        }

        protected String doResolveRequestSignature() throws NoSuchMethodException,
                IllegalAccessException, InvocationTargetException {
            return String.format("%s %s", declaredInterface.getSimpleName(),
                    declaredInterface.getMethod("value").invoke(annotation));
        }

    }

}
