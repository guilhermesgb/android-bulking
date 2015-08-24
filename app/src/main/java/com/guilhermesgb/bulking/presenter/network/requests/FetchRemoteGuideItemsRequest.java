package com.guilhermesgb.bulking.presenter.network.requests;

import com.google.gson.JsonObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class FetchRemoteGuideItemsRequest extends ResolvedSignatureRequest {

    private static final String REQUEST_INTERFACE = "retrieveGuideItems";
    private static final Map<String, Object> REQUEST_PARAMS = new HashMap<>();
    static {
        REQUEST_PARAMS.put("page", 1);
    }

    protected FetchRemoteGuideItemsRequest() throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, ClassCastException {
        super(REQUEST_PARAMS);
    }

    protected FetchRemoteGuideItemsRequest(int page) throws NoSuchMethodException, IllegalAccessException,
            InvocationTargetException, ClassCastException {
        this();
        setPage(page);
    }

    public void setPage(int page) {
        changeRequestSignatureIntegerVariable("page", page);
    }

    public int getPage() {
        return getRequestSignatureIntegerVariableValue("page");
    }

    public FetchRemoteGuideItemsRequest getNext() {
        return FetchRemoteGuideItemsRequest.buildRequest(getPage() + 1);
    }

    protected JsonObject doLoadDataFromNetwork(Method requestInterface) throws Exception {
        int page = getPage();
        System.out.println(String.format("Calling web service, retrieving guide items page %d...", page));
        return (JsonObject) requestInterface.invoke(getService(), page);
    }

    protected Method getRequestInterfaceMethod(Class<?> declaredInterface) throws NoSuchMethodException {
        return declaredInterface.getMethod(REQUEST_INTERFACE, Integer.class);
    }

    protected String parseRequestSignatureVars(String currentMethodResolvedSignature) {
        int page = getPage();
        return currentMethodResolvedSignature.replace("{page}", Integer.toString(page));
    }

    public static FetchRemoteGuideItemsRequest buildRequest(Integer... initialPage) {
        try {
            if (initialPage.length == 0) {
                return new FetchRemoteGuideItemsRequest();
            }
            else {
                return new FetchRemoteGuideItemsRequest(initialPage[0]);
            }
        }
        catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

}
