package com.guilhermesgb.robospiceretrofit.network.network.requests;

import com.guilhermesgb.robospiceretrofit.network.WordPressCMSRetrofitInterface;

import com.octo.android.robospice.request.retrofit.RetrofitSpiceRequest;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import retrofit.client.Response;
import retrofit.http.GET;
import retrofit.http.RestMethod;

public class GuideItemsRequest extends RetrofitSpiceRequest<Response, WordPressCMSRetrofitInterface> {

    private static final String REQUEST_INTERFACE = "retrieveGuideItems";
    private String currentMethodResolvedSignature;
    private int page = 1;

    public GuideItemsRequest() throws NoSuchMethodException {
        super(Response.class, WordPressCMSRetrofitInterface.class);
    }

    public void setPage(int page) {
        this.page = page;
    }

    @Override
    public Response loadDataFromNetwork() throws Exception {
        System.out.println(String.format("Calling web service, retrieving guide items page %d...", page));
        final Method requestInterface = getRequestInterface();
        Response response = (Response) requestInterface.invoke(getService(), page);
        System.out.println(String.format("%s.... called!", getRequestSignature(requestInterface)));
        return response;
    }

    private Method getRequestInterface() throws NoSuchMethodException {
        for (Class<?> declaredInterface : getService().getClass().getInterfaces()) {
            if (declaredInterface == WordPressCMSRetrofitInterface.class) {
                return declaredInterface.getMethod(REQUEST_INTERFACE, Integer.class);
            }
        }
        throw new NoSuchMethodException();
    }

    private String getRequestSignature(Method requestInterface) throws NoSuchMethodException,
            IllegalAccessException, InvocationTargetException {
        for (Annotation annotation : requestInterface.getAnnotations()) {
            for (Class<?> declaredInterface : annotation.getClass().getInterfaces()) {
                for (Annotation metaAnnotation : declaredInterface.getAnnotations()) {
                    for (Class<?> declaredMetaInterface : metaAnnotation.getClass().getInterfaces()) {
                        if (declaredMetaInterface == RestMethod.class) {
                            currentMethodResolvedSignature = String.format("%s %s", declaredInterface
                                    .getSimpleName(), declaredInterface.getMethod("value")
                                    .invoke(annotation)).replace("{page}", Integer.toString(page));
                            return currentMethodResolvedSignature;
                        }
                    }
                }
            }
        }
        throw new NoSuchMethodException();
    }

    public String getCurrentMethodResolvedSignature() {
        String signature = currentMethodResolvedSignature;
        if (signature != null) {
            signature = signature.replace("/", "-").replace("?", ".");
        }
        return signature;
    }

}
