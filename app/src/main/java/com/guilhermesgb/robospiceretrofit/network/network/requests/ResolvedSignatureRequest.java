package com.guilhermesgb.robospiceretrofit.network.network.requests;

import com.guilhermesgb.robospiceretrofit.network.WordPressCMSRetrofitInterface;
import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

import retrofit.client.Response;
import retrofit.http.RestMethod;

abstract public class ResolvedSignatureRequest extends RetrofitSpiceRequest<Response, WordPressCMSRetrofitInterface> {

    private String currentResolvedRequestSignature;
    private Map<String, Object> requestSignatureVariables;

    public ResolvedSignatureRequest() {
        super(Response.class, WordPressCMSRetrofitInterface.class);
    }

    @Override
    public Response loadDataFromNetwork() throws Exception {
        final Method requestInterface = getRequestInterface();
        Response response = doLoadDataFromNetwork(requestInterface);
        System.out.println(String.format("%s.... called!", getRequestSignature(requestInterface)));
        return response;
    }

    protected abstract Response doLoadDataFromNetwork(Method requestInterface) throws Exception;

    private Method getRequestInterface() throws NoSuchMethodException {
        for (Class<?> declaredInterface : getService().getClass().getInterfaces()) {
            if (declaredInterface == WordPressCMSRetrofitInterface.class) {
                return declaredInterface.getMethod(getRequestInterfaceName(), Integer.class);
            }
        }
        throw new NoSuchMethodException();
    }

    protected abstract String getRequestInterfaceName();

    private String getRequestSignature(Method requestInterface) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        for (Annotation annotation : requestInterface.getAnnotations()) {
            for (Class<?> declaredInterface : annotation.getClass().getInterfaces()) {
                for (Annotation metaAnnotation : declaredInterface.getAnnotations()) {
                    for (Class<?> declaredMetaInterface : metaAnnotation.getClass().getInterfaces()) {
                        if (declaredMetaInterface == RestMethod.class) {
                            currentResolvedRequestSignature = String.format("%s %s", declaredInterface
                                    .getSimpleName(), declaredInterface.getMethod("value")
                                    .invoke(annotation));
                            currentResolvedRequestSignature =
                                    resolveRequestSignatureVariables(currentResolvedRequestSignature);
                            return currentResolvedRequestSignature;
                        }
                    }
                }
            }
        }
        throw new NoSuchMethodException();
    }

    protected abstract String resolveRequestSignatureVariables(String currentMethodResolvedSignature);

    public String getCurrentResolvedRequestSignature() {
        String requestSignature = currentResolvedRequestSignature;
        if (requestSignature != null) {
            requestSignature = requestSignature.replace("/", "-").replace("?", ".");
        }
        return requestSignature;
    }

}
