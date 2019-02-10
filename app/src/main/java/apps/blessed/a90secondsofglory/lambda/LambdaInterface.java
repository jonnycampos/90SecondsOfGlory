package apps.blessed.a90secondsofglory.lambda;

import com.amazonaws.mobileconnectors.lambdainvoker.LambdaFunction;

public interface LambdaInterface {

    /**
     * Invoke the Lambda function "AndroidBackendLambdaFunction".
     * The function name is the method name.
     */
    @LambdaFunction
    ResponseClass game_metric(RequestClass request);


    @LambdaFunction
    ResponseMetricsByProvince getMetricByProvince(RequestMetricsByProvince request);
}
