package com.guilhermesgb.robospiceretrofit.network.network.requests;

import java.lang.reflect.Method;

import retrofit.client.Response;

public class GuideItemsRequest extends ResolvedSignatureRequest {

    private static final String REQUEST_INTERFACE = "retrieveGuideItems";
    private int page = 1;

    public void setPage(int page) {
        this.page = page;
    }

    protected Response doLoadDataFromNetwork(Method requestInterface) throws Exception {
        System.out.println(String.format("Calling web service, retrieving guide items page %d...", page));
        return (Response) requestInterface.invoke(getService(), page);
    }

    protected String getRequestInterfaceName() {
        return REQUEST_INTERFACE;
    }

    protected String resolveRequestSignatureVariables(String currentMethodResolvedSignature) {
        return currentMethodResolvedSignature.replace("{page}", Integer.toString(page));
    }

}
