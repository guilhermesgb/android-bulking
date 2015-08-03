package com.guilhermesgb.robospiceretrofit.network.network.requests;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import retrofit.client.Response;

public class GuideItemsRequest extends ResolvedSignatureRequest {

    private static final String REQUEST_INTERFACE = "retrieveGuideItems";
    private static final Map<String, Object> REQUEST_PARAMS = new HashMap<>();
    static {
        REQUEST_PARAMS.put("page", 1);
    }

    public GuideItemsRequest() throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, ClassCastException {
        super(REQUEST_PARAMS);
    }

    public GuideItemsRequest(int page) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, ClassCastException {
        this();
        setPage(page);
    }

    public void setPage(int page) {
        changeRequestSignatureIntegerVariable("page", page);
    }

    protected Response doLoadDataFromNetwork(Method requestInterface) throws Exception {
        int page = getRequestSignatureIntegerVariableValue("page");
        System.out.println(String.format("Calling web service, retrieving guide items page %d...", page));
        return (Response) requestInterface.invoke(getService(), page);
    }

    protected Method getRequestInterfaceMethod(Class<?> declaredInterface) throws NoSuchMethodException {
        return declaredInterface.getMethod(REQUEST_INTERFACE, Integer.class);
    }

    protected String parseRequestSignatureVars(String currentMethodResolvedSignature) {
        int page = getRequestSignatureIntegerVariableValue("page");
        return currentMethodResolvedSignature.replace("{page}", Integer.toString(page));
    }

}
